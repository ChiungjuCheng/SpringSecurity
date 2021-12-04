# MVC架構下的驗證
此處以MVC的架構自訂驗證，不論是驗證或驗證通過後，都直接回傳一個jsp網頁，為了讓server可以讓jsp渲染網頁，需要在pom.xml底下新增tomcat-embed-jasper，它的作用是編譯jsp並使其渲染到瀏覽器上，若沒有添加，會讓server端直接回傳一個jsp檔案給client下載。
```xml
<!-- required for JSP compilation -->
	<dependency>
		<groupId>org.apache.tomcat.embed</groupId>
		<artifactId>tomcat-embed-jasper</artifactId>
		<scope>provided</scope>
	</dependency>
```

# WebSecurityConfigurerAdapter介紹
在01_環境建置章節中，使用的是default的設定，只要是任何發送給spring boot的請求都會需要驗證，若想自訂需要被驗證的url則需要宣告一個繼承org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter的類別。
除了用java code做設定以外，也可以使用xml來做設定。
```java
<form-login 
  login-page='/customLoginPage' 
  login-processing-url="/loginProcess"
  default-target-url="/homepage.html"
  authentication-failure-url="/login.html?error=true" 
  always-use-default-target="true"/>
```
參考資料 : https://www.baeldung.com/spring-security-login


WebSecurityConfigurerAdapter 為abstract class，裡頭有許多可視為default method行為的方法，如設定需要被驗證的url方法:configure(HttpSecurity http)，以下為該方法的原始碼，如同其註解所解釋，若需要自訂，只需要override即可。
```java
    /**
	 * Override this method to configure the {@link HttpSecurity}. Typically subclasses
	 * should not invoke this method by calling super as it may override their
	 * configuration. The default configuration is:
	 *
	 * <pre>
	 * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	 * </pre>
	 *
	 * Any endpoint that requires defense against common vulnerabilities can be specified
	 * here, including public ones. See {@link HttpSecurity#authorizeRequests} and the
	 * `permitAll()` authorization rule for more details on public endpoints.
	 * @param http the {@link HttpSecurity} to modify
	 * @throws Exception if an error occurs
	 */
	protected void configure(HttpSecurity http) throws Exception {
		this.logger.debug("Using default configure(HttpSecurity). "
				+ "If subclassed this will potentially override subclass configure(HttpSecurity).");
		http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
		http.formLogin();
		http.httpBasic();
	}

```
# AbstractUserDetailsAuthenticationProvider and DaoAuthenticationProvider
這裡使用的Provider為AbstractUserDetailsAuthenticationProvider，其已經寫好的驗證的流程，但是交由子類別來Override掉其中的抽象方法，例如retrieveUser方法等，在default的流程中，則是使用DaoAuthenticationProvider來繼承並override。  
  

 節錄自AbstractUserDetailsAuthenticationProvider  
 [AbstractUserDetailsAuthenticationProvider source code](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/authentication/dao/AbstractUserDetailsAuthenticationProvider.java)
```java

	UserDetails user = this.userCache.getUserFromCache(username);
		if (user == null) {
			cacheWasUsed = false;
			try {
				// retrieveUser 交給子類別實作
				user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
			}
			catch (UsernameNotFoundException ex) {
				// 當Username不存在時拋出錯誤
				this.logger.debug("Failed to find user '" + username + "'");
				if (!this.hideUserNotFoundExceptions) {
					throw ex;
				}
				throw new BadCredentialsException(this.messages
						.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}
			Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
		}

```

節錄自DaoAuthenticationProvider 
[DaoAuthenticationProvider source code](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/authentication/dao/DaoAuthenticationProvider.java)
```java
	@Override
	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		prepareTimingAttackProtection();
		try {
			// 使用tUserDetailsService
			UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		}
		catch (UsernameNotFoundException ex) {
			mitigateAgainstTimingAttack(authentication);
			throw ex;
		}
		catch (InternalAuthenticationServiceException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

```



# 自訂驗證實作

**1. 設定所有的requst都需要先經過驗證(即登入) - 覆寫WebSecurityConfigurerAdapter中的configure(HttpSecurity http)**
設定所有的url都需要登入後才能傳送請求，覆寫
```java
// 設定需要驗證的url
@Override
protected void configure(HttpSecurity http) throws Exception {
			
	http.authorizeRequests()
			.anyRequest().authenticated()
		.and().csrf().disable() // 防止csrf
		.formLogin()
			.loginPage("/customLoginPage") 
			.loginProcessingUrl("/loginProcess") // 當傳送request到這個url時，
			.permitAll();
}

```
* loginPage("/customLoginPage") : 當使用者還未登入時導向此url。
* loginProcessingUrl("/loginProcess") : 當傳送request到這個url時spring security filter就會幫忙驗證username和password，不需要特定寫一個RequestMapping方法。

**2. 設定可以登入的帳號密碼 - 覆寫WebSecurityConfigurerAdapter中的configure(AuthenticationManagerBuilder auth)**
此處用簡單的存在記憶體中的帳號密碼做登入使用，在demo2再進一步的改使用資料庫內的帳號密碼，一定要使用Encoder，否則不會成功。
```java
// 設定使用者帳號、密碼和角色
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	UserBuilder user = User.withDefaultPasswordEncoder();
	auth.inMemoryAuthentication().withUser(user.username("test1").password("123").roles("ADMIN"));
}
```

**3. 設計登入頁面**
所有的請求在還未登入之前都會導向這一個jsp。
```xml
<form:form action="${pageContext.request.contextPath}/loginProcess">
	<p>
		User name <input type ="text" name="username"/>
	</p>
		
	<p>
		Password <input type ="text" name="password"/>
	</p>
		<input type ="submit" value="Login"/>
</form:form>
```
* action : 其url就對應到在第一個步驟所設定的loginProcessingUrl
* username、password : 這兩個input的name必須要固定，spring security filter會使用這兩個請求參數做驗證。

**4. 設定Controller**

```java
@Controller
public class LoginController {

	@GetMapping("/customLoginPage")
	public String login() {
		
		return "customlogin";
	}
}
```
* @GetMapping("/customLoginPage") : 對應到第一步驟的loginPage()

備註: 所有程式碼皆在Demo1




<!-- jwt 補充資
https://chikuwa-tech-study.blogspot.com/2021/06/spring-boot-username-password-authentication-and-jwt.html
 -->