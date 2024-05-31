package com.hanul.shoppingMall.config;

import com.hanul.shoppingMall.member.CustomUser;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter implements HandlerInterceptor {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Cookie jwtCookie = CookieUtil.getCookie(request, "jwtToken");

        if (jwtCookie != null) {
            String token = jwtCookie.getValue();
            Claims claims = JwtUtil.extractToken(token);

            if (claims != null) {
                String username = claims.get("username", String.class);
                String displayName = claims.get("displayName", String.class);
                Long memberId = claims.get("memberId", Double.class).longValue();

                List<SimpleGrantedAuthority> authorities = ((List<String>) claims.get("authorities"))
                        .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                CustomUser customUser = new CustomUser(username, "", authorities);
                customUser.setDisplayName(displayName);
                customUser.setId(Long.valueOf(memberId));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUser, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                request.setAttribute("customUser", customUser);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            CustomUser customUser = (CustomUser) request.getAttribute("customUser");
            if (customUser != null) {
                modelAndView.addObject("username", customUser.getUsername());
                modelAndView.addObject("displayName", customUser.getDisplayName());
                modelAndView.addObject("memberId", customUser.getId());
                modelAndView.addObject("authorities", customUser.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(", ")));
            }
        }
    }
}