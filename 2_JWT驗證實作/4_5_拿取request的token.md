# 檢查每一個request的token
使用者通過驗證得到token後，在每一次request都需要帶著該token，因此server端需要有個filter能夠從request中解碼token，並將token轉為Authentication後，檢查該使用者是否具有存取資源的權限。

## JWTAuthenticationFilter 檢查每一個requset
當有request要求存取資源時，這個filter會檢查requset是否有合法的token，若有則建立Authentication。OncePerRequestFilter則是確保這一個requset只會經過這個filter一次，不會因為重導而在執行一次檢查token的流程。
```java
/**
 * 將request中的token解析回Authentication
 * @author user
 */
public class JwtAuthFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Authentication authentication = JwtService.getAuthentication((HttpServletRequest) request,
				(HttpServletResponse) response);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

}
```

驗證JWT參考
https://blog.softtek.com/en/token-based-api-authentication-with-spring-and-jwt