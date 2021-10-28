# OAuth 2.0 Login
用github做Single Sign On，範例在github.security。
使用 Authorization Code Grant 來實現。

## 在GitHub申請clientId和clientSecret
進入以下網址，可以註冊一個新的OAuth application
https://github.com/settings/applications/new

**redirect URI** 是通過驗證後，要讓user-agent(瀏覽器)重導到的uri。

## 設定pom.xml
在pom.xml放上需要的dependency，加上這個spring就會把OAuth 2.0做為default
```xml
<!-- Oauth2-->
<dependency>
    <groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
```
## 設定yml檔案
```yml
# github Oauth. clientId 和 clientSecret需要去官網查詢
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            clientId: your-Id
            clientSecret: your-secret
```

![github oauth application](./picture/15_registerOauthApplication.png)

## 流程解釋
到這裡其實就完成了一個簡單的OAuth2.0，這時候專案就可以起動，並在輸入localhost:8085時，Spring security就會試著找到authenticated物件，當失敗時就會重導到http://localhost:8080/oauth2/authorization/github，而**OAuth2AuthorizationRequestRedirectFilter**就會依照這個uri，去呼叫doFilterInternal，並將請求重導到 : 
```xml
https://github.com/login/oauth/authorize?
response_type=code&
client_id=<clientId>&scope=read:user&
state=<state>&
redirect_uri=http://localhost:8080/login/oauth2/code/github
```
**redirect_uri** 則會和註冊時填寫的一樣。
通過驗證後，請求就會重導到註冊時填的網址:login/oauth2/code/github，並帶著authentication code。此時**OAuth2LoginAuthenticationFilter**就會發送Post請求打GitHub API，並且拿到一個authentication token。

一些常見的OAuth2.0 provider，例如github, google, facebook和Okta，在spring security中都有一些default的client設定，像是authorization-uri, token-uri, and user-info-uri，不太可能經常變動，所以不用特別去設定。
因此才會看到上面的yml只有簡單的設定clientId和clientSecret。

**CommonOAuth2Provider**
若還是想要特別設定這些常見的Oauth2.0 provider，可以使用CommonOAuth2Provider介面，這個介面裏頭有定義一些常見的屬性為enum。
https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/oauth2/client/CommonOAuth2Provider.html

參考網址:
https://medium.com/swlh/spring-boot-oauth2-login-with-github-88b178e0c004

參考網址
login的實作
https://spring.io/guides/tutorials/spring-boot-oauth2/
