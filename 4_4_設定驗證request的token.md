# 檢查每一個request的token
使用者通過驗證得到token後，在每一次request都需要帶著該token，而server端則需要有個類別能夠從request中解碼token，並且檢查該使用者是否具有存取資源的權限。

## 拿取request中的header Authentication 


## JWTAuthenticationFilter 檢查每一個requset

* OncePerRequestFilter
在3.0 每一個request有可能會有多個thread，而OncePerRequestFilter可以確保這個filter只會被執行一次。

驗證JWT參考
https://blog.softtek.com/en/token-based-api-authentication-with-spring-and-jwt