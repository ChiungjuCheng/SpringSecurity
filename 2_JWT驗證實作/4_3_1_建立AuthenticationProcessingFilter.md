# 建立AuthenticationProcessingFilter
建立請求登入時會使用的Filter，負責從HttpServletRequest拿取使用者傳入的資料並且將資料封裝到Authentication後呼叫AuthenticationManager進行驗證。

```java
/**
 * 設定有哪些請求需要被驗證
 * 當有request要求驗證時，此AuthenticationProcessingFilter就會負責從request拿取資料並且創立Authentication物件
 */
public class LoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	private Logger LOG = Logger.getLogger(LoginAuthenticationProcessingFilter.class.getName());

	/**
	 * 設定有哪些url視為要求驗證 constructor是用來設定有哪些請求適用這個filter
	 * @param url
	 * @param authManager
	 */
	public LoginAuthenticationProcessingFilter(String defaultFilterProcessesUrl,
			AuthenticationManager authenticationManager) {
		super(defaultFilterProcessesUrl);
		setAuthenticationManager(authenticationManager);
	}

	/**
	 * 將請求轉換成自定義的Authentication子類別
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		LOG.info("AbstractAuthenticationProcessingFilter attemptAuthentication.......");

		UserInfoEntity userInfoDTO = new ObjectMapper().readValue(request.getInputStream(), UserInfoEntity.class);

		TokenAuthentication tokenAuthentication =  new TokenAuthentication(userInfoDTO.getName(),userInfoDTO.getPassword());
		
		// 開始驗證，並回傳結果
		return getAuthenticationManager().authenticate(tokenAuthentication);

	}

}
```

當驗證通過時，會被呼叫successfulAuthentication方法，在該方法中則以使用者的資料製作token並放入HttpServletResponse中。

```java
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		LOG.info("AbstractAuthenticationProcessingFilter successfulAuthentication..... creating token");
        
		JwtService.addAuthenticationWithClam(response, authResult);
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authResult);
		SecurityContextHolder.setContext(context);
		
		// 呼叫父類別的successfulAuthentication，並使用SuccessHandler
		super.successfulAuthentication(request, response, chain, authResult);
	}
```

JwtService裡面含有製作token和從token中parse出資料的方法，詳情可以看[4_4_JWTService](/2_JWT驗證實作/4_4_JWTService.md)