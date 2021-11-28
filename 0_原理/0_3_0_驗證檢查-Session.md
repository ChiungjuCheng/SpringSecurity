# Session Management
HTTP session相關的功能，由SessionManagementFilter操作SessionAuthenticationStrategy介面來完成。主要是用來防止session-fixation protection attack、檢查session是否過期或是限制一個通過驗證的使用者能夠擁有多少個session。

# SessionManagementFilter
* 藉由查詢最近的SecurityContextHolder資料是否存在於SecurityContextRepository中，來得知此請求是否通過驗證。
* 若存在於repository則不做任何事
* 若不存在，但含有擁有Authentication的thread-local SecurityContext，則這個filter就會假定在之前的filter已經通過驗證，並直接使用設定過的SessionAuthenticationStrategy。
* 若該使用者沒有被驗證，則此filter會檢查其是否要求的是個invalid session ID(例如過期)，且會使用設定的InvalidSessionStrategy。
# SessionAuthenticationStrategy
* Allows pluggable support for HttpSession-related behaviour when an authentication occurs.
* Typical use would be to make sure a session exists or to change the session Id to guard against session-fixation attacks.
其子類別包含
![SessionAuthenticationStrategy](/picture/13_SessionAuthenticationStrategy_implement.png)

https://docs.spring.io/spring-security/site/docs/3.2.8.RELEASE/apidocs/org/springframework/security/web/authentication/session/SessionAuthenticationStrategy.html

參考資料  
https://docs.spring.io/spring-security/site/docs/current/reference/html5/#session-mgmt