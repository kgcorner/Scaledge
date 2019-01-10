package com.kgcorner.util;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtUtility {
    private static final String ISSUER = "scaledge";
    private JwtUtility(){}

    public static String createJWTToken(String salt, Map<String, String> claim, int expiresInSeconds) {
        Instant now = Instant.now();
        try {
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTCreator.Builder jwtBuilder = JWT.create()
                    .withIssuedAt(Date.from(now))
                    .withIssuer(ISSUER)
                    .withExpiresAt(Date.from(now.plusSeconds(expiresInSeconds)));
            for(Map.Entry<String, String> entry : claim.entrySet()) {
                jwtBuilder.withClaim(entry.getKey(), entry.getValue());
            }
            return jwtBuilder.sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Object validateToken(String salt, String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("user").asString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        catch (JWTVerificationException x) {
            throw new IllegalArgumentException(x.getMessage());
        }
    }


}
