package com.kepf.utils;

import com.kepf.services.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final String secret ="secretkey";

    public final  static long VALIDITY_DURATION =1000*60*60*5;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final  Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpirationDate(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }
    private Claims extractAllClaims(String token) {
        return  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String extractEmail(String token){

        return extractClaim(token, Claims::getSubject);
    }
    public String generateToken(MyUserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getEmail());

        return createToken(claims,userDetails.getEmail());

    }

    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ VALIDITY_DURATION ))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public Boolean validateToken(String token, MyUserDetails userDetails){
        final String email = extractEmail(token);
        return ( email.equals(userDetails.getEmail()) && isTokenExpired(token));
    }
}




