# 使用自訂的table schema驗證
在2_1提到使用JDBC，也就是從資料庫撈取使用者資料來驗證和授權使用者，但table schema需依照spring security規定，若想要自訂table schema，就會需要用到UserDetailService。

### UserDetailsService
負責載入特定使用者資料的核心interface。spring security會把這個物件當成使用者資料的DAO層，並由AuthenticationProvider呼叫該物件的loadUserByUsername(String username)<sup><sub>*註1</sub></sup>。
[1] UserDetailsService source code
```java
public interface UserDetailsService {

	/**
	 * Locates the user based on the username. In the actual implementation, the search
	 * may possibly be case sensitive, or case insensitive depending on how the
	 * implementation instance is configured. In this case, the <code>UserDetails</code>
	 * object that comes back may have a username that is of a different case than what
	 * was actually requested..
	 * @param username the username identifying the user whose data is required.
	 * @return a fully populated user record (never <code>null</code>)
	 * @throws UsernameNotFoundException if the user could not be found or the user has no
	 * GrantedAuthority
	 */
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}

```

### UserDetails
主要提供使用者的資料，spring security不會直接操作這個物件。開發者可以將自訂的DAO回傳的資料設定到UserDetails物件，並讓UserDetailsService回傳。

### 實作
1. 覆寫UserDetailsService的loadUserByUsername(String username)
   此處的Repository為mock，若需要真的連到資料庫撈取資料可以使用JpaRepository。
```java
   	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserInfoEntity userInfo = userInfoRepository.getUserByName(username);
		if (userInfo == null) {
			LOG.info("the user name is not exist");
			throw new UsernameNotFoundException(username);
		}

		return User.builder()
					.username(userInfo.getName())
					.password("{noop}" + userInfo.getPassword())
					.roles(userInfo.getRole())
					.build();
	}
   
```
1. 設定WebSecurityConfigurerAdapter
```java
   	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
```


所有程式碼皆在demo/UserServiceDetails

<sub>*註1: strategy pattern 將行為當作argument傳入方法，java需要使用物件會是lamda表示法把方法傳入，若像JS是 first-class function則可以直接把方法傳入 </sub>

[1] https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/core/userdetails/UserDetailsService.java


參考網址:
https://matthung0807.blogspot.com/2019/09/spring-security-userdetailsservice.html