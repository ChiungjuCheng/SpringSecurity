# 建立AuthenticationSuccessHandler
建立成功驗證後filter會呼叫的AuthenticationSuccessHandler，這裡只有很簡單的將登入者的資訊印出。
```java
@Component("LoginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private Logger LOG = Logger.getLogger(LoginSuccessHandler.class.getName());

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String name =  (String) request.getAttribute("name");
		LOG.info(name+"has successfully logged in");
	}

}
```

# 設定到AuthenticationProcessingFilter
若不是用HttpSecurity的Login配置方法，而是使用addFilterBefore，則可以直接將handler設定到自製的AuthenticationProcessingFilter的Bean中。

```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
		http.authorizeRequests()
				.accessDecisionManager(getAccessDecisionManager())
				.antMatchers("/login").permitAll()
				.antMatchers("/test/auth/admin").hasRole("ADMIN")
				.accessDecisionManager(getAccessDecisionManager())
				.anyRequest().authenticated();

        // AuthenticationProcessingFilter
		http.addFilterBefore(getLoginAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

		http.addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
	}
    
	public LoginAuthenticationProcessingFilter getLoginAuthenticationProcessingFilter() throws Exception {
		LoginAuthenticationProcessingFilter loginAuthenticationProcessingFilter = new LoginAuthenticationProcessingFilter("/login", authenticationManager());

        // 設定AuthenticationSuccessHandler
		loginAuthenticationProcessingFilter.setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler);
		return loginAuthenticationProcessingFilter;
	}

}
```