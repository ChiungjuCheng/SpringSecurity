# Remember-Me Authentication
當瀏覽器全部關掉的時候會暫停使用者的session，並在使用者想要存取該應用程式時跳出登入的頁面，但可以藉由Remember-Me機制避免使用者重複登入。實現的方法是傳送cookie讓使用者儲存，當使用者要存取應用程式時，就靠該cookie識別，並且觸發自動登入。

Spring Security provides the necessary hooks for these operations to take place, and has two concrete remember-me implementations. One uses hashing to preserve the security of cookie-based tokens and the other uses a database or other persistent storage mechanism to store the generated tokens.



https://www.javatpoint.com/spring-security-remember-me
https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-rememberme