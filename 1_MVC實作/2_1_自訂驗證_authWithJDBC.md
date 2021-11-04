在上一小節的username和password是用java code產生存在記憶體內，這一小節則是要使用關聯性資料庫，撈出使用者的資料並作驗證。
#### 設定使用JDBC驗證 
新增pom.xml
```xml
<!-- JDBC -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
	<groupId>com.oracle.database.jdbc</groupId>
	<artifactId>ojdbc8</artifactId>
</dependency>
```
1. 使用DataSource做驗證時，Spring security會自動撈取帳號密碼還有權限的資料，因此需要設定符合defalt設定的table schema*:

```sql
CREATE TABLE USERS (
    USERNAME NVARCHAR2(128) PRIMARY KEY,
    PASSWORD NVARCHAR2(128) NOT NULL,
    ENABLED CHAR(1) CHECK (ENABLED IN ('Y','N') ) NOT NULL
);

CREATE TABLE AUTHORITIES (
    USERNAME NVARCHAR2(128) NOT NULL,
    AUTHORITY NVARCHAR2(128) NOT NULL
);
ALTER TABLE AUTHORITIES ADD CONSTRAINT AUTHORITIES_UNIQUE UNIQUE (USERNAME, AUTHORITY);
ALTER TABLE AUTHORITIES ADD CONSTRAINT AUTHORITIES_FK1 FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME) ENABLE;

``` 
|![tableModel](/picture/04_tableModel.png)|
-

創立帳號
```sql
insert into users values('test','123','Y');
insert into users values('test2','{noop}123','Y');
insert into authorities values('test2','ROLE_ADMIN');
insert into authorities values('test','ROLE_ADMIN');
```
   
2. 設定DataSource
   讀取放在resource資料夾底下的persistence-oracle.properties，並且設定DataSource
```java
@Configuration
@PropertySource("classpath:persistence-oracle.properties")
public class DataSourceConfig {
	
	@Autowired
	private Environment env;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Bean
	public DataSource securityDataSource() {
		
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(); 		
		// set driver
		dataSourceBuilder.driverClassName(env.getProperty("jdbc.driver"));
		// set url
		dataSourceBuilder.url(env.getProperty("jdbc.url"));
		// set user
		dataSourceBuilder.username(env.getProperty("jdbc.user"));
		// set password
		dataSourceBuilder.password(env.getProperty("jdbc.password"));
		
		return dataSourceBuilder.build();
	}
	
}
```
3. 設定WebSecurityConfigurerAdapter
設定在上一章節提到過的WebSecurityConfigurerAdapter，使其使用DataSource進行驗證

```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource securityDataSource;

    //設定使用者帳號、密碼和角色
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}
}
```
#### 測試登入
**未設定密碼編碼**
接著就可以從登入畫面開始測試，當使用帳號test2和密碼123時可以順利登入，但當使用test1並輸入密碼時則會顯示沒有PasswordEncoder的例外，如下圖
|![passwordEncoder](picture/03_PasswordEncodeExcption.png)|
-
password的前贅字是告訴spring security的要用哪種編碼的方式編譯密碼，{noop}表示使用名碼
|![userInfo](./picture/05_userTableInfo.png)|
-
**未設定authorities**
帳號一定要在authorities表格設定權限，否則會回傳Bad Credentials
|![badCredentials](picture/06_badCredentials.png)|
-

TODO : 找Role的資料


補充自訂的table schema:
https://www.baeldung.com/spring-security-jdbc-authentication

補充自訂的Role
https://dev.to/ashishrameshan/custom-role-based-permission-authorization-in-spring-boot-m7f

*Table Schema: 在spring secutity的官網上可以找到sql語法。
備註: 所有程式碼皆在Demo2