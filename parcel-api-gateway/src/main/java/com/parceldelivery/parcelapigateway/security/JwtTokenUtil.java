package com.parceldelivery.parcelapigateway.security;

import java.util.Date;
import java.util.List;


import com.parceldelivery.parcelapigateway.config.JwtConfig;
import com.parceldelivery.parcelapigateway.exception.JwtTokenIncorrectStructureException;
import com.parceldelivery.parcelapigateway.exception.JwtTokenMalformedException;
import com.parceldelivery.parcelapigateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    private final JwtConfig config;

    public JwtTokenUtil(JwtConfig config) {
        this.config = config;
    }

    public String generateToken(String id, Long userId, String role) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + config.getValidity() * 1000 * 60;
        Date exp = new Date(expMillis);
        claims.put("role",role);
        claims.put("userId",userId);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, config.getSecret()).compact();
    }

    public Claims validateToken(final String header) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            String[] parts = header.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new JwtTokenIncorrectStructureException("Incorrect Authentication Structure");
            }

           return Jwts.parser().setSigningKey(config.getSecret()).parseClaimsJws(parts[1]).getBody();

        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }

}
