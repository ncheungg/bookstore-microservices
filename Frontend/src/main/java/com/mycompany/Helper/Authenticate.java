/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import static javax.crypto.Cipher.SECRET_KEY;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author student
 */
public class Authenticate {
    
    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private static final String key = "extremelysecurestringabcdefghijklmnopqrstuvwxyz";
    private static final String secretString = Encoders.BASE64.encode(key.getBytes());
    
    private static final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretString);
    private static final Key signingKey = 
        new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    
    public static String createJWT(String issuer, int userID, String username, long ttlMillis) {
        
        //The JWT signature algorithm we will be using to sign the token
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("userID", Integer.toString(userID));
        userInfo.put("username", username);
        
        // encode userInfo hashmap to String payload
        String payload = Convert.convertWithGuava(userInfo);

        //Let's set the JWT Claims
        JwtBuilder builder;
        builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(payload)
                .setIssuer(issuer)
                .signWith(signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        String a = builder.compact();
        System.out.println(a);
        //Builds the JWT and serializes it to a compact, URL-safe string
        return a;
    }
    
    public static Map<String, String> decodeJWT(String jwt) throws UnsupportedEncodingException {
        Jws<Claims> jws = null;
        Map<String, String> userInfo = new HashMap<>();
        String payload;
        
        try {
            jws = Jwts.parserBuilder() // (1)
                    .setSigningKey(signingKey) // (2)
                    .build() // (3)
                    .parseClaimsJws(jwt); // (4)
            
            payload = jws.getBody().getSubject();
            userInfo.putAll(Convert.convertWithGuava(payload));
                  
        } catch (JwtException ex) {       // (5)

            ex.printStackTrace();
            
            userInfo.clear();
            userInfo.put("isLoggedIn", "false");
            return userInfo;
            
        }
        if (jws == null) {
            userInfo.clear();
            userInfo.put("isLoggedIn", "false");
            return userInfo;
        }
        
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if (jws.getBody().getExpiration().before(now)) {
            userInfo.clear();
            userInfo.put("isLoggedIn", "false");
            return userInfo;
        }

        userInfo.put("isLoggedIn", "true");
        return userInfo;

    }

}


