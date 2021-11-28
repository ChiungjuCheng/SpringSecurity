# 建立AccessDecisionVoter
若是沒有登入過的authentication直接拒絕存取，而這個類別另外也有加入簡單的黑名單邏輯，檢查該使用者是否在黑名單中，若沒有才可以存取資源。
```java
/**
 * 決定current request是否具有權限
 * 黑名單
 * @author user
 */
public class CustomAccessDecisionVoter implements AccessDecisionVoter<Object> {

	private Logger LOG = Logger.getLogger(CustomAccessDecisionVoter.class.getName());
	
	@Autowired
	private BlackUserRepository blackUserRepository;
	
	public CustomAccessDecisionVoter() {}
	
	public CustomAccessDecisionVoter(BlackUserRepository blackUserRepository) {
		this.blackUserRepository = blackUserRepository;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {	
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	// 參考RoleVoter
	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		
		if(authentication == null) {
			return ACCESS_DENIED;
		}

		int result = ACCESS_GRANTED;		
		// 查黑名單
		Optional<String> blackUserOpt =  blackUserRepository.getBlackUserByName(authentication.getName());
		
		if(blackUserOpt.isPresent()) {
			result = ACCESS_DENIED;
		}
		
		return result;
	}
	
	Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
		return authentication.getAuthorities();
	}

}
```