# Knowledge Notes

이 문서는 프로젝트를 진행하면서 알게 된 지식이나 중요한 개념을 정리한 것입니다.

---

### - 프로퍼티 보안
    - 개인정보 보호를 위한 프로퍼티 보안관리

    중요한 정보를 외부 파일로 분리하여 코드와 분리함으로써 보안성을 향상시킵니다.
    @PropertySource 어노테이션을 활용하여 외부 프로퍼티 파일을 로드합니다.
    중요한 정보를 변수화하여 코드에 직접 노출되지 않도록 합니다.


    예시 코드 : @PropertySource("classpath:파일경로")


    - ShoppingMallApplication.java 파일에 @PropertySource 코드를 작성해야함.





<br /><br />

---


## ProductDTO와 @ModelAttribute 사용

### ProductDTO 초기화

서버에 요청을 날릴 페이지에 미리 `new ProductDTO()` 객체를 생성해 두는 이유는 데이터 바인딩 및 유효성 검사에서 유리한 점이 많기 때문입니다.
- **데이터 바인딩**: 템플릿 엔진(예: Thymeleaf, JSP)이 빈 `ProductDTO` 객체를 참조하여 폼 필드에 값을 채울 수 있습니다.
- **유효성 검사**: 유효성 검사 오류가 발생했을 때, 사용자 입력 값을 유지하여 다시 폼에 표시할 수 있습니다.

```java
@GetMapping("/products/new")
public String showProductAddForm(Model model) {
    model.addAttribute("productDTO", new ProductDTO());
    return "product_add";
}
```

---

## @ModelAttribute 사용
@ModelAttribute 어노테이션을 사용하면 ProductDTO 객체가 요청 파라미터와 자동으로 바인딩되고, 모델에 자동으로 추가됩니다. 이는 코드의 간결성과 가독성을 높여줍니다.

자동 모델 추가: @ModelAttribute 어노테이션을 사용하면, ProductDTO 객체가 요청 파라미터와 자동으로 바인딩되고, 모델에 자동으로 추가됩니다.
코드 간결성: 이 어노테이션을 사용하면 model.addAttribute("productDTO", productDTO) 같은 코드를 추가로 쓰지 않아도 됩니다.

```java
@PostMapping("/products")
public String addProduct(
        @Validated @ModelAttribute ProductDTO productDTO,
        BindingResult result,
        Authentication auth,
        Model model
) {
    if (result.hasErrors()) {
        log.info("productDTO Error = {}", result.getAllErrors());
        model.addAttribute("productDTO", productDTO);
        return "product_add";
    }
    productService.saveProduct(productDTO, auth);
    return "redirect:/products/page/1";
}
```

---

## AJAX로 csrf 토큰 전송
### 1. html 헤더에 코드 추가
```html
<head>
    <meta charset="UTF-8">
    <meta name="csrf-token" th:content="${_csrf.token}"/> 
    <meta name="csrf-header" th:content="${_csrf.headerName}"/> 
</head>
```

<br />

### 2. AJAX 요청에 csrf 토큰 전송
```javascript
<script>
    // 자바스크립트 함수 정의
    function deleteBtn(productId) {
    
        // 메타 태그에서 CSRF 토큰과 헤더 이름 읽기
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="csrf-header"]').getAttribute('content');

        // fetch를 통해 DELETE 요청 전송
        fetch('/products/delete/' + productId, {
            method: 'DELETE',
            headers: {
                [csrfHeader]: csrfToken // CSRF 토큰을 요청 헤더에 추가
            }
        })
        .then(response => response.text()) // 응답을 텍스트로 변환
        .then((rep) => {
            console.log(rep); // 응답 콘솔에 출력
            location.reload(); // 페이지 리로드
        })
        .catch(error => console.error('Error:', error));
    }
</script>

```

- 이 방법으로 csrf 보안 기능을 유지하며 AJAX에서 서버로 요청을 보낼 수 있다. 

---
<br />

## 테이블에 중복 방지 규칙 

### 1. Entity 객체에 중복 방지 코드 추가
   ```java
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "parentId"})})
public class Review {
    
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long parentId;
} 
```
- 이 테이블에 username과 parentId라는 두 컬럼의 조합이 중복되지 않도록 하는 코드

<br />
   
### 2. 저장소 인터페이스에 코드 추가
```java
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUsernameAndParentId(String username, Long parentId);
}
```

- 메서드 명명 규칙
  - Prefix: 쿼리 메서드의 목적을 나타냅니다.
      - findBy: 특정 조건을 만족하는 데이터를 찾습니다.
      - existsBy: 특정 조건을 만족하는 데이터가 존재하는지 확인합니다.
      - countBy: 특정 조건을 만족하는 데이터의 개수를 셉니다.
      - deleteBy: 특정 조건을 만족하는 데이터를 삭제합니다.

<br />
<br />
   
### 3. 서비스와 컨트롤러 코드
```java
public void saveReview(ReviewDTO reviewDTO) {
   if (reviewRepository.existsByUsernameAndParentId(reviewDTO.getUsername(), reviewDTO.getParentId())) {
       throw new IllegalArgumentException("이미 리뷰를 작성하셨습니다.");
   }
       reviewRepository.save(new Review(reviewDTO.getUsername(), reviewDTO.getContent(), reviewDTO.getParentId()));
   }
```

```java
@PostMapping("/review")
   public String addReview(@Validated ReviewDTO reviewDTO, BindingResult bindingResult) {
   if (bindingResult.hasErrors()) {
       return "redirect:/products/" + reviewDTO.getParentId();
   }
   
   try {
       reviewService.saveReview(reviewDTO);
   } catch (IllegalArgumentException e) {
       return "redirect:/products/" + reviewDTO.getParentId() + "?error=" + e.getMessage();
   }
   
   return "redirect:/products/" + reviewDTO.getParentId();
}
```
   
### 4. 타임리프 코드
   
```html
    <div th:if="${param.error}">
        <p th:text="${param.error}">이미 리뷰를 작성하셨습니다.</p>
    </div>
```
## 5. 정리
- Entity 객체에 중복 방지 코드 추가: @UniqueConstraint 사용
- 저장소 인터페이스에 메서드 추가: existsByUsernameAndParentId
- 서비스와 컨트롤러에서 예외 처리
- 타임리프에서 에러 메시지 출력

<br />

---
<br />

## index 만들기
```java
@Entity
@Table(indexes = {
  @Index(name = "인덱스이름작명", columnList = "인덱스만들컬럼명1"),
  @Index(name = "인덱스이름작명", columnList = "인덱스만들컬럼명2")
})
public class Product {

} 
```
- 검색 성능 향상을 위해 full text index를 만들자.

<br />

---

## full text index 만들기
- DB에 mysql 쿼리문 직접 작성
  ```sql
    - CREATE FULLTEXT INDEX 인덱스이름작명 ON 테이블명(컬럼명) WITH PARSER ngram;
    
    - create fulltext index fulltext_index on shoppingmall.product(title) with parser ngram;
  ```
  
---

## FullTextIndex & Pagination 동시 사용
### 1. 저장소 코드
  ```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findPageBy(Pageable pageable);

    @Query(value = "SELECT * FROM product WHERE MATCH(title) AGAINST(?1)", nativeQuery = true)
    Page<Product> fullTextSearchProduct(String text, Pageable pageable);
}
```

<br />

### 2. 서비스 코드
```java
// FullTextIndex & Pagination
public Page<Product> getProductsAndPage(Integer pageNum, String searchText) {
    int pageSize = 5; // 한 페이지에 보여줄 아이템 수
    PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize); // PageRequest 생성
    Page<Product> productPage = productRepository.fullTextSearchProduct(searchText, pageRequest);
    return productPage;
}
```

### 3. 컨트롤러 코드
```java
 @GetMapping("/products/searchProduct/page/{pageNum}")
public String searchProduct(
        @PathVariable Integer pageNum,
        @RequestParam String searchText,
        Model model
) {

  Page<Product> productsAndPage = productService.getProductsAndPage(pageNum, searchText);
  model.addAttribute("productList", productsAndPage.getContent());
  model.addAttribute("searchText", searchText);
  model.addAttribute("currentPage", pageNum);
  model.addAttribute("totalPages", productsAndPage.getTotalPages());

  return "product_list_search";
}
```

### 4. 타임리프 코드
```html
<span th:each="pageNum : ${#numbers.sequence(1, totalPages)}">
    <a th:href="@{|/products/searchProduct/page/${pageNum}?searchText=${searchText}|}"
       th:text="${pageNum}"
       th:classappend="${pageNum == currentPage} ? 'active' : ''"></a>
</span>
```
- th:href 부분의 문법 중요.
- 프로젝트내 product_list_search.html 파일 참고


---

## 주문 기능 개발하면서 배운 점
- @ManyToOne 쓰면 그 컬럼이 가리키는 테이블도 출력가능 (SQL JOIN)
- @ManyToOne 성능 문제는 "JOIN FETCH" 사용([SalesRepository.java](..%2Fsales%2FSalesRepository.java) 참고) 
- 반대로 @OneToMany 도 가능

---

## JWT 로그인 구현
위의 문제를 해결하기 위해 JWT 기반 로그인을 구현했습니다. JWT 로그인을 구현하려면 다음의 파일을 생성 및 수정해야 합니다:

- [CookieUtil.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fconfig%2FCookieUtil.java)
- [JwtAuthenticationFilter.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fconfig%2FJwtAuthenticationFilter.java)
- [JwtUtil.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fconfig%2FJwtUtil.java)
- [SecurityConfig.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fconfig%2FSecurityConfig.java)
- [MyUserDetailsService.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fmember%2FMyUserDetailsService.java)
- [CustomUser.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FshoppingMall%2Fmember%2FCustomUser.java)


<br />


## JWT 로그인 기능 전체 흐름

#### CookieUtil
- 쿠키를 쉽게 가져오거나 설정할 수 있는 유틸리티 클래스를 만듭니다.
- CookieUtil.java 파일 생성.


<br />


#### JwtAuthenticationFilter
- 요청을 가로채 JWT를 검증하고 인증 정보를 설정하는 필터를 만듭니다.
- JwtAuthenticationFilter.java 파일 생성.
- 이 필터는 모든 요청에 대해 JWT를 검증합니다.


<br />

#### JwtUtil
- JWT 토큰의 생성, 파싱 및 검증을 담당하는 유틸리티 클래스를 만듭니다.
- JwtUtil.java 파일 생성.


<br />

#### SecurityConfig
- Spring Security 설정 파일에서 JWT 필터를 등록하고, 필요한 보안 설정을 적용합니다.
- SecurityConfig.java 파일 수정.
- 이 설정 파일에서 JWT 필터를 등록하고, 세션 관리 정책을 무상태(stateless)로 설정합니다.

<br />

#### MyUserDetailsService
- 사용자 정보를 로드하는 서비스 클래스를 만듭니다.
- MyUserDetailsService.java 파일 생성.
- 사용자 정보를 데이터베이스에서 로드하고, 사용자 인증 정보를 반환합니다.

<br />

#### CustomUser 생성:
- 사용자 정보를 담는 커스텀 유저 클래스를 만듭니다.
- CustomUser.java 파일 생성.
- 사용자 ID와 표시 이름을 추가로 담는 유저 클래스를 정의합니다.

### 정리
- 스프링 시큐리티의 로그인 정보를 그대로 사용하되, 스프링 시큐리티의 로그인 정보에 값을 넣는 것은 jwt 방식으로 삽입.


- 순서
  - 스프링 시큐리티 기능을 사용하기 위해 SecurityConfig 생성.
  - CustomUser를 만들어서 시큐리티 로그인 정보에 추가할 것들 설정.
  - MyUserDetailsService를 생성하여 CustomUser에서 추가한 정보를 포함한 "로그인 정보" 생성.
  - 전역으로 JWT 인증을 위해 JwtAuthenticationFilter 생성.
  - JWT 생성 및 파싱을 위해 JwtUtil 생성.
  - 브라우저의 쿠키에 접근하기 위해 CookieUtil 생성.


- 자세히
  - SecurityConfig.java: Spring Security 설정 파일, JWT 필터를 등록하고 보안 설정을 적용.
  - CustomUser.java: 사용자 정보를 담는 커스텀 유저 클래스.
  - MyUserDetailsService.java: 사용자 정보를 로드하는 서비스 클래스.
  - JwtAuthenticationFilter.java: 요청을 가로채 JWT를 검증하고 인증 정보를 설정하는 필터.
  - JwtUtil.java: JWT 토큰의 생성, 파싱 및 검증을 담당하는 유틸리티 클래스.
  - CookieUtil.java: 쿠키를 쉽게 가져오거나 설정할 수 있는 유틸리티 클래스.
  
  
  
  


<br />

---

<br />