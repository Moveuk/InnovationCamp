package com.ldu.spring_blogcrud.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ldu.spring_blogcrud.common.exceptions.ErrorCode;
import com.ldu.spring_blogcrud.common.exceptions.ExpiredTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static com.ldu.spring_blogcrud.security.jwt.JwtTokenUtils.*;

@Component
public class JwtDecoder {
    // JwtTokenUtils 에서 상수 값 받아와서 복호화 함.

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public DecodedJWT decodeJWT(String token) throws IllegalArgumentException {
        DecodedJWT decodedJWT = isValidToken(token)
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 Token입니다."));

        return decodedJWT;
    }

    public String decodeUsername(DecodedJWT decodedJWT) throws IllegalArgumentException {
        String username = decodedJWT
                .getClaim(CLAIM_USER_NAME)
                .asString();

        return username;
    }

    public String decodePassword(DecodedJWT decodedJWT) throws IllegalArgumentException {
        String password = decodedJWT
                .getClaim(CLAIM_PASSWORD)
                .asString();

        return password;
    }

    public void isExpired(DecodedJWT decodedJWT) throws IllegalArgumentException {
        Date expiredDate = decodedJWT
                .getClaim(CLAIM_EXPIRED_DATE)
                .asDate();

        Date now = new Date();
        if (expiredDate.before(now)) {
            throw new ExpiredTokenException("Token의 유효기간이 지났습니다.", ErrorCode.EXPIRED_TOKEN);
        }

    }

    public Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();

            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }
}
