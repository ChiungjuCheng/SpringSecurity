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

AbstractSecurityInterceptor在啟動的時候就會呼叫supports(ConfigAttribute attribute)來決定AccessDecisionManager是否能夠處理ConfigAttribute。

Security Interceptor的實作類別則會呼叫supports(Class clazz)來確認被設定好的AccessDecisionManager 是否能夠處理將來被security interceptor的secure object型別。

### AccessDecisionVoter 
AccessDecisionManager是透過「詢問」一系列的<mark>voter</mark>的「意見」來決定是否給予權限或是拋出AccessDeniedException。

AccessDecisionVoter介面有三個方法 :
```java
int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attrs);

boolean supports(ConfigAttribute attribute);

boolean supports(Class clazz);
```
AccessDecisionVoter有三個static的方法，分別是ACCESS_ABSTAIN(沒意見,0)、ACCESS_DENIED (拒絕,-1)和ACCESS_GRANTED(授給予權限,1)，AccessDecisionVoter依照其vote的augument和security決策邏輯來決定要回傳哪一個static變數，讓AccessDecisionManager來做決定。

ConfigAttribute負責儲存security system相關的規則，例如可能含有hasRole("XXX")等等的，在WebSecurityConfigurerAdapter中設定的規則。裏頭含有一個方法 :
```java
String	getAttribute();
```


### AccessDecisionVoter 實作
* RoleVoter - 當ConfigAttribute擁有前綴字ROLE_時就會跳出來要投票，並和GrantedAuthority比對，若有相同的字串結果則通過授權，若沒有符合則會拒絕。當ConfigAttribute沒有前綴字ROLE_時，此voter會回傳abstain。
* AuthenticatedVoter - 
* Custom Voters - 自訂義的voter。

### AccessDecisionManager 實作
Spring Security有提供三種實作AccessDecisionManager :

* ConsensusBased - 會依照所有的非ACCESS_ABSTAIN來決定是否授權。
* AffirmativeBased - 只要有一個以上的ACCESS_GRANTED就會授權，同時忽略所有的ACCESS_DENIED。
* UnanimousBased - 只要有一個ACCESS_DENIED就會拒絕授權。

另外若所有的voters都是abstain，AccessDecisionManager的實作類別都有其參數來控制接下來的流程。

<mark>permitAll()是管理驗證，就算通過驗證後filter還是會繼續往下走，因此還是會要求要授權</mark>


# After Invocation Handling
secure object執行完後(例如對應到某個url的controller方法)，有時候需要修飾secure object的return資料。


參考資料
https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authorization