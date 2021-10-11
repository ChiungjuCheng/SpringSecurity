package com.example.security.demo.auth;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

	static final long EXPIRATIONTIME = 60000; // 1 min
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
	static void addAuthenticationWithClam() {
//		String KEY = "StockAPIStockAPIStockAPIStockAPIStockAPIStockAPI";
//
//		Claims claims = Jwts.claims();
//		    claims.put("user_id", stockUser.getUser().getId());
//			claims.put("account", stockUser.getUsername());
//			claims.put("name", stockUser.getUser().getName());
//			claims.put("authority", stockUser.getUser().getAuthority());
//	        claims.setExpiration(calendar.getTime());
//	        claims.setIssuer("KensStockAPI");
//
//	        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());
//
//		return Jwts.builder().setClaims(claims).signWith(secretKey).compact();
	}

	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			// parse the token. 
			// 過期就自動拋錯
			String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
					.getSubject();

			return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
		}
		return null;
	}
}
