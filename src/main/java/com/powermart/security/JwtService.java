
package com.powermart.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.powermart.proxy.UserManagementClient;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	@Autowired
	UserManagementClient userManagementClient;

	private String secretKey="mz3/TvKClNyci1RdRbPH50PjbrFGvOq3hYzDoSSY9SM=";

	private long jwtExpiration=600*1000;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(String userName,List<String> roles) {
		return generateToken(new HashMap<>(), userName, roles);
	}

	public String generateToken(Map<String, Object> extraClaims, String userName,List<String> roles) {
		return buildToken(extraClaims ,roles, userName, jwtExpiration);
	}

	public long getExpirationTime() {
		return jwtExpiration;
	}

	private String buildToken(Map<String, Object> extraClaims,List<String> roles, String userName, long expiration) {
		extraClaims.put("roles", roles);
		return Jwts.builder().setClaims(extraClaims).setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	public boolean isTokenValid(String token, String username) {
		final String username2 = extractUsername(token);
		return (username2.equals(username)) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
//	private String generateKey() {
//		SecureRandom secureRandom = new SecureRandom();
//		byte[] key = new byte[32];
//		secureRandom.nextBytes(key);
//		String encodeKey = Base64.getEncoder().encodeToString(key);
//		return encodeKey;
//	}

}
