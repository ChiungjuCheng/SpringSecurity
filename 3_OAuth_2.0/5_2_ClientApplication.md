獲得Resource ower的授權和其驗證通過後，可以帶著Authentication server 提供的token去Resource server拿取資料。
# 設定


# Client applicatiom
TODO
https://spring.io/blog/2018/03/06/using-spring-security-5-to-integrate-with-oauth-2-secured-services-such-as-facebook-and-github

Security Spring Rest API using OAuth2.0
https://dzone.com/articles/secure-spring-rest-api-using-oauth2

授權後卻一直回到要求授權頁面
https://stackoverflow.com/questions/57761917/spring-5-security-oauth2-login-redirect-loop

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