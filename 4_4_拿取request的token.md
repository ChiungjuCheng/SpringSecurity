# 檢查每一個request的token
使用者通過驗證得到token後，在每一次request都需要帶著該token，因此server端需要有個filter能夠從request中解碼token，並將token轉為Authentication後，檢查該使用者是否具有存取資源的權限。

## JWTAuthenticationFilter 檢查每一個requset
* OncePerRequestFilter
在3.0 每一個request有可能會有多個thread，而OncePerRequestFilter可以確保這個filter只會被執行一次。

驗證JWT參考
https://blog.softtek.com/en/token-based-api-authentication-with-spring-and-jwt