# 登出
在預設的情況下，會使用SecurityContextLogoutHandler來登出，主要會處理一下事項:
* 修改SecurityContextHolder
* 使 HttpSession 無效
* 移除 Authentication


```java
public class SecurityContextLogoutHandler implements LogoutHandler {

	protected final Log logger = LogFactory.getLog(this.getClass());

	private boolean invalidateHttpSession = true;

	private boolean clearAuthentication = true;

	/**
	 * Requires the request to be passed in.
	 * @param request from which to obtain a HTTP session (cannot be null)
	 * @param response not used (can be <code>null</code>)
	 * @param authentication not used (can be <code>null</code>)
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Assert.notNull(request, "HttpServletRequest required");
		if (this.invalidateHttpSession) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
				if (this.logger.isDebugEnabled()) {
					this.logger.debug(LogMessage.format("Invalidated session %s", session.getId()));
				}
			}
		}
		SecurityContext context = SecurityContextHolder.getContext(); // Obtain the current SecurityContext
		SecurityContextHolder.clearContext(); // Explicitly clears the context value from the current thread.
		if (this.clearAuthentication) {
			context.setAuthentication(null);
		}
	}
    // 以下省略
}

```
<sub>SecurityContextLogoutHandler source code</sub>

#### 實作登出
在WebSecurityConfigurerAdapter物件上做設定

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
			
	http.authorizeRequests()
			.anyRequest().authenticated()
			.and().csrf().disable()
		.formLogin()
			.loginPage("/customLoginPage")
			.loginProcessingUrl("/loginProcess")
			.defaultSuccessUrl("/loginSuccess", true)
			.permitAll()
		.and()
			.logout()
			.logoutUrl("/logoutProcess")
			.logoutSuccessUrl("/logoutSuccess")
			.permitAll();
	}
```
* logoutUrl("/logoutProcess") : 當user向/logoutProcess發送請求時，spring security就會處理登出。
* addLogoutHandler( LogoutHandler logoutHandler) : 若有一些想額外處理的邏輯，例如log 登出訊息則可以自行定義

程式碼參考: security.userservice.demo

自定義LogoutHandler參考
https://matthung0807.blogspot.com/2019/11/spring-boot-security-custom-logout.html

SecurityContextHolder
* https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/core/context/SecurityContextHolder.java
  
* https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authentication-securitycontextholder

