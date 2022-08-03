package com.ldu.spring_blogcrud.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ldu.spring_blogcrud.security.UserDetailsImpl;

import java.util.Date;

public final class JwtTokenUtils {

    private static final int SEC = 1;
    private static final int MINUTE = 60 * SEC;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    // JWT 토큰의 유효기간: 15분 (단위: seconds)
    private static final int JWT_TOKEN_VALID_SEC = 10 * SEC;
    // JWT 토큰의 유효기간: 15분 (단위: milliseconds)
    private static final int JWT_TOKEN_VALID_MILLI_SEC = JWT_TOKEN_VALID_SEC * 1000;
    // JWT 토큰의 유효기간: 1주 (단위: seconds)
    private static final int JWT_REFRESH_TOKEN_VALID_SEC = 7 * DAY;
    // JWT 토큰의 유효기간: 1주 (단위: milliseconds)
    private static final int JWT_REFRESH_TOKEN_VALID_MILLI_SEC = JWT_REFRESH_TOKEN_VALID_SEC * 1000;

    public static final String CLAIM_EXPIRED_DATE = "EXPIRED_DATE";
    public static final String CLAIM_USER_NAME = "USER_NAME";
    public static final String CLAIM_PASSWORD = "PASSWORD";
    public static final String JWT_SECRET = "jwt_secret_!@#$%";

    public static String generateJwtToken(String username, String password) {
        String token = null;
        try {
            token = JWT.create()
                    // 토큰 발급자
                    .withIssuer("ldu")
                    // 토큰 payload 작성, key - value 형식, 객체도 가능
                    .withClaim(CLAIM_USER_NAME, username)
                    .withClaim(CLAIM_PASSWORD, password)
                     // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
                    .withClaim(CLAIM_EXPIRED_DATE, new Date(System.currentTimeMillis() + JWT_TOKEN_VALID_MILLI_SEC))
                    .sign(generateAlgorithm());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return token;
    }

    public static String generateRefreshToken() {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("ldu")
                    // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
                    .withClaim(CLAIM_EXPIRED_DATE, new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALID_MILLI_SEC))
                    .sign(generateAlgorithm());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return token;
    }

    private static Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    }
}
