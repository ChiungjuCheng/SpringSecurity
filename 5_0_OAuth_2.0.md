# 介紹
spring OAuth2介紹
https://www.tpisoftware.com/tpu/articleDetails/957
## Authorization Server
負責驗證並回傳token的server

## Client Application
向resource owner獲得許可後，拿著帳號密碼去找Authorization Server驗證。

## Spring Security OAuth2.0
在spring security的OAuth2.0框架中，提供OAuth_2.0 login、OAuth_2.0 Client和OAuth_2.0 server。

* OAuth_2.0 login 單純提供OAuth2.0登入，登入驗證完後
* 



spring 官網範例
https://spring.io/guides/tutorials/spring-boot-oauth2/

spring sample範例
https://github.com/spring-projects/spring-security-samples/tree/5.5.x/servlet/spring-boot/java/oauth2/login#github-login

建立oauth2 login
https://github.com/spring-projects/spring-security-samples/tree/5.5.x/servlet/spring-boot/java/oauth2/login#google-initial-setup

整合Oauth2和resource server
https://www.baeldung.com/spring-security-oauth2-jws-jwk