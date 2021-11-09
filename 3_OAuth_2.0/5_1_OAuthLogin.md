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

![github oauth application](/picture/15_registerOauthApplication.png)

## 流程解釋
到這裡其實就完成了一個簡單的OAuth2.0，這時候專案就可以起動，並在輸入localhost:8085時，Spring security就會試著找到authenticated物件，當失敗時就會重導到<mark>http://localhost:8080/oauth2/authorization/{registrationId}</mark>。

### OAuth2AuthorizationRequestRedirectFilter - authorization code grant flow的起始
就會對這個uri做出回應並建構出OAuth 2.0 Authorization Request，帶著client identifier, requested scope(s), state, response type和redirection URI，只要resource owner成功授權給client，就會將user-agent帶回redirection URI。此filter呼叫doFilterInternal，並將請求重導到 : 
```xml
https://github.com/login/oauth/authorize?
response_type=code&
client_id=<clientId>&scope=read:user&
state=<state>&
redirect_uri=http://localhost:8080/login/oauth2/code/github
```
**redirect_uri** 則需要和當初在OAuth2.0 provider註冊時填寫的一樣。
若Resource owner在Authorization server端登入成功並且授權client端，則請求就會帶著authorization code重導到註冊時填的網址:login/oauth2/code/github。
上述的行為其實可以簡單地看做client server發送授權請求，而Resource owner驗證授權後的redirect URI則可以看作是response。
### OAuth2LoginAuthenticationFilter
AbstractAuthenticationProcessingFilter的實作介面，負責處理authorization code grant flow的OAuth 2.0 Authorization Response，也就是使用者從Authentication server授權後，Authentication server回傳Client的respose，內容包含temporary code和state (與授權請求比對用來防止CSR)。

這個filter會建立**OAuth2LoginAuthenticationToken**，並交給AuthenticationManager驗證，當驗證成功後**OAuth2AuthenticationToken**就會被建立(代表End-User Principal)，並使用OAuth2AuthorizedClientRepository來儲存OAuth2AuthorizedClient介面(This class associates the Client to the Access Token granted/authorized by the Resource Owner.The primary purpose of an Authorized Client is to associate an Access Token credential to a Client and Resource Owner, who is the Principal that originally granted the authorization.)，最後OAuth2AuthenticationToken就會被存在SecurityContextRepository。



就會發送Post請求打GitHub API，並且拿到一個authentication token。


後續若有需求可以實作Client application，讓其拿著token去對resouce server送請求拿取授權的資料。

一些常見的OAuth2.0 provider，例如github, google, facebook和Okta，在spring security中都有一些default的client設定，像是authorization-uri, token-uri, and user-info-uri，不太可能經常變動，所以不用特別去設定。
因此才會看到上面的yml只有簡單的設定clientId和clientSecret。

在default中會使用session，當然也可以透過設定改成使用JWT。

**CommonOAuth2Provider**
若還是想要特別設定這些常見的Oauth2.0 provider，可以使用CommonOAuth2Provider介面，這個介面裏頭有定義一些常見的屬性為enum。
https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/oauth2/client/CommonOAuth2Provider.html

## 拿取UserInfo endpoint的資料
https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2login-advanced-userinfo-endpoint



TODO
使用Stateless的方式實作login
https://www.callicoder.com/spring-boot-security-oauth2-social-login-part-2/

打API
https://www.letswrite.tw/instagram-basic-display-api/

Instagram api
https://www.jyes.com.tw/news.php?act=view&id=2302
https://www.google.com/?code=AQAlB5UH5cLCkLwcf-P2XmOfKGh1glmahrS66FjTIFKyxD1oTGVDgZsyCmep7tmdX4ixiMKoted3zKOxIH4Fae6261AVImmSRPfyGGhZIOc3zLW-MGA1dlbN8EWMUftXHfUh0LwBxs19Jy83r51tnKqbBp8HpNyzdQqPidilTZ00bZ8d4N4UMlWZPW44uamhZygZ8xbL1bWakoTiycywfGm8VGc5csYbsJzfEM8M36kXGQ#_

https://www.jyes.com.tw/news.php?act=view&id=2302

# 參考網址:
login的實作
https://spring.io/guides/tutorials/spring-boot-oauth2/
https://medium.com/swlh/spring-boot-oauth2-login-with-github-88b178e0c004

Authorization Request介紹
https://www.oauth.com/oauth2-servers/authorization/the-authorization-request/

OAuth2AuthorizationRequestRedirectFilter source code
https://github.com/spring-projects/spring-security/blob/main/oauth2/oauth2-client/src/main/java/org/springframework/security/oauth2/client/web/OAuth2AuthorizationRequestRedirectFilter.java

OAuth2LoginAuthenticationFilter API 裡面有功能介紹
https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/web/OAuth2LoginAuthenticationFilter.html

stat parameter
https://auth0.com/docs/configure/attack-protection/state-parameters

OAuth2AuthorizedClientRepository
https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/web/OAuth2AuthorizedClientRepository.html

OAuth2AuthorizedClient
https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/OAuth2AuthorizedClient.html