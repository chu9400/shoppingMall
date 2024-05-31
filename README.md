# 쇼핑몰 프로젝트

이 저장소는 스프링으로 구현한 쇼핑몰 프로젝트입니다. 


---

## 현재 버전: v0.2.0 (2024-05-31)

- main 브런치
- dev 브런치 (작업 브런치)

---


## 소개

 - 이 프로젝트는 Spring을 학습하며 개발한 쇼핑몰입니다. 
 - 다양한 기능을 구현하며 Spring의 다양한 기능을 학습할 수 있습니다.

---

## 기능 목록

 - 상품 CRUD 기능
 - 상품 리뷰 작성 기능
 - 회원 가입 기능
 - 주문 기능
 - JWT 기반 로그인 기능

---


## 설치 및 실행

 - 작성 예정

---


## 작업 기여
 - 작성 예정

---
## 라이선스

 - 작성 예정


---

## 문제 해결
### DB 성능 저하 문제
- Spring Security를 사용하여 로그인 기능을 구현했습니다. 하지만 모든 페이지에서 로그인 정보를 요구하면서 DB 성능 저하 문제가 발생하고 있습니다. 
  이를 해결하기 위해, 로그인 정보를 클라이언트 측에서 관리할 수 있는 JWT(JSON Web Token) 기반의 로그인 기능으로 전환할 계획입니다. 
  이 방식은 서버의 부하를 줄이고 성능을 향상시킬 수 있습니다. 문제를 발견해주신 "JJunPro"님께 감사드립니다.

<br />

---

## JWT 로그인 구현
  위의 문제를 해결하기 위해 JWT 기반 로그인을 구현했습니다. JWT 로그인을 구현하려면 다음의 파일을 생성 및 수정해야 합니다:

  - [CookieUtil.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fconfig%2FCookieUtil.java)
  - [JwtAuthenticationFilter.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fconfig%2FJwtAuthenticationFilter.java)
  - [JwtUtil.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fconfig%2FJwtUtil.java)
  - [SecurityConfig.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fconfig%2FSecurityConfig.java)
  - [MyUserDetailsService.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fmember%2FMyUserDetailsService.java)
  - [CustomUser.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fmember%2FCustomUser.java)

### 요약
- 스프링 시큐리티 기능을 사용하기 위해 SecurityConfig 생성.
- CustomUser를 만들어서 시큐리티 로그인 정보에 추가할 것들 설정.
- MyUserDetailsService를 생성하여 CustomUser에서 추가한 정보를 포함한 "로그인 정보" 생성.
- 전역으로 JWT 인증을 위해 JwtAuthenticationFilter 생성.
- JWT 생성 및 파싱을 위해 JwtUtil 생성.
- 브라우저의 쿠키에 접근하기 위해 CookieUtil 생성.







자세한 설명은 [knowledge-notes.md](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fdocs%2Fknowledge-notes.md) 참고

---

<br />

