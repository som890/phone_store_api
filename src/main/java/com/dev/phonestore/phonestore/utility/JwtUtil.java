package com.dev.phonestore.phonestore.utility;

import com.dev.phonestore.phonestore.service.UserDetailServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


//It provides for creating adn verifying tokens
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static final byte[] SECRET_KEY_BYTES = generateSecretKey();
    private static final String SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY_BYTES);
    private static final int TOKEN_VALIDITY_SECONDS = 3600 * 5;

    private static byte[] generateSecretKey() {
        byte[] keyBytes = new byte[32]; // 32 bytes for 256-bit key
        new SecureRandom().nextBytes(keyBytes);
        return keyBytes;
    }
    //Get user from jwt
    public String getUsernameFromToken(String token) {
        String username = getClaimsFromToken(token, Claims::getSubject);
        logger.debug("Username : {}", username);
        return username;
    }

    private < T > T getClaimsFromToken(String token, Function < Claims, T > claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Lấy thông tin user từ token
    public Claims getAllClaims(String token) {
        return (Claims) Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parse(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUsernameFromToken(token);
        if (userName == null) {
            return false; //
        }

        if (userDetails == null) {
            return false;
        }

        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        Map < String, Object > claims = new HashMap < > ();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map < String, Object > claims, String subject) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(key)
                .compact();
    }

}