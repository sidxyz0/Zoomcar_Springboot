package com.example.zoomcar.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.function.Function;

@Service
public class AuthenticationService {
    @Value("${jwt.secret}")
    private String secretKey;
    private String token;
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    //retrieve username from jwt token
    public String getUuidFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public String generateToken(AuthenticationModel authData) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, authData.getUuid());
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        this.token =  Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
        return this.token;
    }

    //validate token
    public Boolean validateToken(String token, AuthenticationModel authData) {
        if(token == null || !token.equals(this.token)){
            return false;
        }
        else{
            final String uuid = getUuidFromToken(token);
            return (uuid.equals(authData.getUuid()) && !isTokenExpired(token));
        }
    }
}
