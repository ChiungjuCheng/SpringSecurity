# 使用User在其他平台的資料
為了拿到User在其他平台的資料以供自身應用程式使用，要先經過User同意授權後才能取用，有很多方式可以達到而此處則是介紹Authorization Code grant flow :  
1. 使用者進入Client application
2. 重導使用者到authorization server登入後同意授權給Client application使用資料
3. authorization server重導User回Client application並帶著Authorization Code
4. Client application則拿取Authorization Code去請求authorization server提供token
5. Client application帶著取得的token向resource server拿取User的資料

p.s security的官網上也有提供其他的要求授權flow

![OauthClientProcess](/picture/18_OauthClientProcess.png)

## 註冊 OAuth application
Authorization callback URL is set to http://localhost:8080/login/oauth2/code/client-id，當使用者驗證並且授權後，user-agent會被重導到這個url

接受到這個requst就會經過OAuth2AuthorizationRequestRedirectFilter
讓他初始化Authorization Request 並開始Authorization Code grant flow.

# OAuth2LoginAuthenticationFilter
授權後Authorization Server會重導user經過這個filter，並呼叫AuthenticationManager驗證，provider使用OAuth2AuthorizationCodeAuthenticationProvider。


https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/web/OAuth2LoginAuthenticationFilter.html

# OAuth2AuthorizationCodeAuthenticationProvider 
帶著code去請求Access Token

Access Token Request OAuth2AuthorizationCodeGrantRequestEntityConverter
AbstractOAuth2AuthorizationGrantRequestEntityConverter

# DefaultAuthorizationCodeTokenResponseClient 
拿Code去要access_token
# TokenType cannot be null
在標準中由於token_type是REQUIRED，Spring Security 5.1 就強迫一定要放TokenType，
不然會拋出exception，而底下為參考作法。

解決方法
https://github.com/jzheaux/messaging-app/blob/master/client-app/src/main/java/sample/config/SecurityConfig.java#L61

TokenType cannot be null exception原因
https://stackoverflow.com/questions/58629596/access-token-response-tokentype-cannot-be-null
https://github.com/spring-projects/spring-security/issues/5983#issuecomment-430620308

cover
https://github.com/jzheaux/messaging-app/blob/392a1eb724b7447928c750fb2e47c22ed26d144e/client-app/src/main/java/sample/web/CustomAccessTokenResponseConverter.java#L35

# OAuth2AuthorizationCodeAuthenticationProvider
OAuth2AuthorizationCodeAuthenticationProvider 會去呼叫OAuth2UserService.loadUser();
此時就會去打user-info
https://localhost:8081/oauth2/login/oauth2/code/cust

**OAuth2UserRequestEntityConverter 修改requst**


# Client application
參考
https://spring.io/blog/2018/03/06/using-spring-security-5-to-integrate-with-oauth-2-secured-services-such-as-facebook-and-github

https://github.com/spring-projects/spring-security-samples/tree/main/servlet/spring-boot/java/oauth2/webclient

Security Spring Rest API using OAuth2.0
https://dzone.com/articles/secure-spring-rest-api-using-oauth2

