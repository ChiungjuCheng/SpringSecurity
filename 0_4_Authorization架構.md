# Authorization
在Aunthentication有提到，AuthenticationManager會將GrantedAuthority放到Authentication物件裡面，表示授予該principle權限。接著AccessDecisionManager 就會從Aunthentication拿取GrantedAuthority串列來決定授權。

## GrantedAuthority
這個介面只有一個方法 :
```java
String getAuthority();
```
它能夠回傳一個代表權限的字串給AccessDecisionManager讀取。
這個方法可以回傳null，當回傳null時，就代表指使任一的AccessDecisionManager需要再去找另外能夠支援該GrantedAuthority實作的方法，來解讀其內容。

# Pre-Invocation Handling
secure objects在spring security的解釋為一個web請求或是方法執行(例如某個對應到url的方法)。spring security使用interceptor呼叫AccessDecisionManager來決定是否允許
執行，簡單來說應該就是要求這次執行的執行緒是否有權限可以要求執行某個動作。

## AbstractSecurityInterceptor
負責呼叫AccessDecisionManager

## AccessDecisionManager
AbstractSecurityInterceptor呼叫AccessDecisionManager，使其藉由voters來決定最終的存取控制。
AccessDecisionManager有三個方法 :
```java
void decide(Authentication authentication, Object secureObject,
    Collection<ConfigAttribute> attrs) throws AccessDeniedException;

boolean supports(ConfigAttribute attribute);

boolean supports(Class clazz);
```
decide會傳入所有能夠決策權限的所有相關資訊。 傳入secure Object則可以得到其arguments，例如若secure object是MethodInvocation，則可以得到client端傳入了甚麼arguments，並可利用其來判斷此次的principal是否擁有權限呼叫該方法。

## voter

<mark>permitAll()是管理驗證，就算通過驗證後filter還是會繼續往下走，因此還是會要求要授權</mark>


# After Invocation Handling
secure object執行完後(例如對應到某個url的controller方法)，有時候需要修飾secure object的return資料。

https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authorization

voter
https://stackoverflow.com/questions/11397627/what-is-the-actual-type-of-object-parameter-in-vote-method-of-spring-security-ac