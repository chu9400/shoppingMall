package com.hanul.shoppingMall.config;

// 필요한 클래스들을 임포트합니다.
import com.hanul.shoppingMall.member.Member;
import com.hanul.shoppingMall.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

// 이 클래스는 전역적으로 모든 컨트롤러에 적용될 설정을 담고 있습니다.
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    // 멤버 데이터를 가져오기 위해 MemberRepository를 주입합니다.
    private final MemberRepository memberRepository;

    // 이 메서드는 모든 컨트롤러에 공통적으로 적용될 모델 속성을 설정합니다.
    @ModelAttribute
    public void addUserAttributes(Model model) {
        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자가 있고, 익명 사용자가 아니라면
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            // 인증된 사용자의 이름(아이디)을 가져옵니다.
            String username = auth.getName();

            // 데이터베이스에서 사용자의 정보를 조회합니다.
            Member member = memberRepository.findByUsername(username).orElse(null);

            // 사용자가 존재하면 모델에 사용자 ID와 이름을 추가합니다.
            if (member != null) {
                model.addAttribute("loggedInMemberId", member.getId());
                model.addAttribute("loggedInUsername", member.getUsername());
            }
        }
    }
}
