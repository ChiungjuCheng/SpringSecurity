# 0_原理
spring security的驗證和授權流程筆記


# 1_MVC實作
MVC架構下使用Session驗證和授權
ch0_3_0 ~ 0_3_1
ch1~3

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