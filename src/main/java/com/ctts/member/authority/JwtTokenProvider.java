package com.ctts.member.authority;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "authorization";
    private static final String BEARER_TYPE = "Bearer";
    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 30 * 60 * 1000L; //30분
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 3 * 60 * 60 * 1000L; //3시간

    private Key key;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * 토큰 생성
     */
    public TokenInfo generateToken(Authentication authentication) {
        // 권한 획득
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));


        //accessToken 생성
        Date expiredAccessToken = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorization", authorities)
                .setExpiration(expiredAccessToken)
                .signWith(this.key, SignatureAlgorithm.HS512)
                .compact();

        //refreshToken 생성
        Date refreshTokenExpiration = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiration)
                .signWith(this.key, SignatureAlgorithm.HS512)
                .compact();

        return TokenInfo.of(BEARER_TYPE, accessToken, refreshToken);
    }

    /**
     * 토큰 정보 추출
     */
//    public Authentication getAuthentication(String accessToken) {
//        // 토큰 복호화
//        Claims claims = parseClaims(accessToken);
//        if (!claims.containsKey(AUTHORITIES_KEY)) {
//            throw new RuntimeException("권한 정보가 없는 토큰");
//        }
//
//        // claims에서 권한 정보 가져오기
//        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new).toList();
//    }

    /**
     * 토큰 검증
     */
    public Boolean validateToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(accessToken);
            return true;
        } catch (SecurityException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * claim parse
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
