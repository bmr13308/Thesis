package com.fitnest.posts.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	@Value("${SECRET_KEY}")
	private String SECRET_KEY;
	
	private static String USERIDKEY = "userId";

	 public int extractSubjectId(String token) {
		 Claims body = Jwts.parser()
                 .setSigningKey(SECRET_KEY)
                 .parseClaimsJws(token)
                 .getBody();
		 
	        return Integer.valueOf((String)body.get(USERIDKEY));
	    }
	
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //public Boolean validateToken(String token, UserDetails userDetails) {
      //  final String username = extractUsername(token);
        //return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    //}
}
