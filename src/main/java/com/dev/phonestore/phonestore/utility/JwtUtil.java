package com.dev.phonestore.phonestore.utility;

import com.dev.phonestore.phonestore.service.UserDetailServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


//It provides for creating adn verifying tokens
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static final String SECRET_KEY = "learnprogrammingyourselfdsfsidfhsdf53245523874523ikowsefhdnksdgnsldkfslkdfjskldgnsdfjsdgju43905345345klj5mk345lgd5644534";
    private static final int TOKEN_VALIDITY_SECONDS = 3600 * 5;

    //Get user from jwt
    public String getUsernameFromToken(String token) {
                return extractClaimsFromToken(token, Claims::getSubject);
    }

    private <T> T extractClaimsFromToken(String token, Function < Claims, T> claimsResolver) {
        try {
            Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }catch (Exception e) {
            return  null;
        }
    }

    public Claims extractAllClaims(String token) throws AccessDeniedException {
        try {
            return  Jwts.parser()
                    .verifyWith(generateHS512Key())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (SignatureException | ExpiredJwtException e){
            throw new AccessDeniedException("Access denied: "+ e.getMessage());
        }
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }
    public Date getExpirationDateFromToken(String token) {
        return extractClaimsFromToken(token, Claims::getExpiration);
    }


    private static SecretKey generateHS512Key() {
        // Assuming SECRET_KEY stores in regular Base64 format
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        int minimumKeyLength = Jwts.SIG.HS256.getKeyBitLength();
        String usedAlgorithm = "HS256";
        try {
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: The key size is not enough for the algorithm " + usedAlgorithm + ". Details: " + e.getMessage());
            return null;
        }
    }
    public String generateToken(UserDetails userDetails) {
        Map < String, Object > claims = new HashMap < > ();
        return createToken(claims, userDetails.getUsername());
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        // Ensure minimum 256-bit key size for security
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(generateHS512Key(), Jwts.SIG.HS512)
                .compact();
    }

}