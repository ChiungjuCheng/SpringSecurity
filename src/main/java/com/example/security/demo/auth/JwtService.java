package com.example.security.demo.auth;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 提供jwt相關功能 參考網址
 * https://github.com/auth0-blog/spring-boot-jwts/blob/47975c794b5995dbc183e0ac4f94c19b0a072113/src/main/java/com/example/security/TokenAuthenticationService.java
 * 
 * @author user
 */
@Component
public class JwtService {

	static final long EXPIRATIONTIME = 600000; // 10 min
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static void addAuthentication(HttpServletResponse res, String username) {
		String JWT = Jwts.builder().setSubject(username)
									.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
									.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	/**
	 * 使用Cliaims
	 * https://ithelp.ithome.com.tw/articles/10270362
	 */
	static void addAuthenticationWithClam(HttpServletResponse res, Authentication authResult) {
//		String KEY = "StockAPIStockAPIStockAPIStockAPIStockAPIStockAPI";
		TokenAuthentication tokenAuthentication = (TokenAuthentication) authResult;
//
			Claims claims = Jwts.claims();
						
			
			claims.put("name", authResult.getPrincipal());
			Collection<GrantedAuthority>  grantedAuthorityCollection =  tokenAuthentication.getAuthorities();
			List<String> authoritys = 	grantedAuthorityCollection.stream()
										.map(grantedAuthority -> grantedAuthority.getAuthority())
										.collect(Collectors.toList());
			
			claims.put("authority", authoritys);
	        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME));
//	        claims.setIssuer("KensStockAPI");
//
		String JWT =  Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET).compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	static Authentication getAuthentication(HttpServletRequest request,HttpServletResponse res) {
		String token = request.getHeader(HEADER_STRING);
		if(token == null) {
			token =  res.getHeader(HEADER_STRING);
		}
		
		if (token != null) {
			// parse the token. 
			// 過期就自動拋錯
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			
			String user =(String) claims.get("name");
			Collection<String> roles = (Collection<String>)claims.get("authority");

			Collection<SimpleGrantedAuthority> authoritys = roles.stream()
																.map(role->new SimpleGrantedAuthority(role))
																.collect(Collectors.toList());
			// TODO 傳入authority
			return user != null ? new UsernamePasswordAuthenticationToken(user, null, authoritys) : null;
		}
		return null;
	}
}
