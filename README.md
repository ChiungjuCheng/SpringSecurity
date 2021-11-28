# 0_原理
spring security的驗證和授權流程和原理
* [0_0_介紹](/0_原理/0_0_介紹.md)
* [0_1_Filter架構](/0_原理/0_1_Filter架構.md)
* [0_2_Authentication架構](/0_原理/0_2_Authentication架構.md)
* [0_3_0_驗證檢查-Session](/0_原理/0_3_0_驗證檢查-Session.md)
* [0_3_1_Rememberme](/0_原理/0_3_1_Rememberme.md)
* [0_4_Authorization架構](/0_原理/0_4_Authorization架構.md)

# 1_MVC實作
MVC架構下使用Session驗證和授權
* [1_0_環境建置](/1_MVC實作/1_0_環境建置.md)
* [1_1_SpringBoot建置環境](/1_MVC實作/1_1_SpringBoot建置環境.md)
* [2_0_自訂驗證_authInMemory](/1_MVC實作/2_0_自訂驗證_authInMemory.md)
* [2_1_自訂驗證_authWithJDBC](/1_MVC實作/2_1_自訂驗證_authWithJDBC.md)
* [2_2_自訂驗證_使用UserDetailService](/1_MVC實作/2_2_自訂驗證_使用UserDetailService.md)
* [2_9_Logout](/1_MVC實作/2_9_Logout.md)

# 2_JWT驗證實作
使用Token驗證和授權
* [4_0_0_JWT驗證](/2_JWT驗證實作/4_0_0_JWT驗證.md)
* [4_1_實作](/2_JWT驗證實作/4_1_實作.md)
* [4_2_設定AuthenticationProvider](/2_JWT驗證實作/4_2_設定AuthenticationProvider.md)
* [4_3_2_建立SuccessfulHandler](/2_JWT驗證實作/4_3_1_建立AuthenticationProcessingFilter.md)
* [4_4_JWTService](/2_JWT驗證實作/4_4_JWTService.md)
* [4_5_拿取request的token](/2_JWT驗證實作/4_5_拿取request的token.md)
* [4_6_建立AccessDecisionVoter](/2_JWT驗證實作/4_6_建立AccessDecisionVoter.md)
* [4_7_設定資源權限](/2_JWT驗證實作/4_7_設定資源權限.md)

## 3_OAuth_2.0
Oauth2.0和自行驗證的流程有所不同，細節的流程放在各章節中，目前比較完整的是5_2_OAuthClient，其他待補.....
* [5_0_OAuth_2.0](/3_OAuth_2.0/5_0_OAuth_2.0.md)
* [5_1_OAuthLogin](/3_OAuth_2.0/5_1_OAuthLogin.md)
* [5_2_OAuthClient](/3_OAuth_2.0/5_2_OAuthClient.md)