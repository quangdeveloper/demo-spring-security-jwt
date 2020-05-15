package springsecurity.withjwt.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import springsecurity.withjwt.security.CustomUserDetail;

import java.util.Date;

@Slf4j
@Component
public class JWTTokenProvider {

    private final String JWT_SECRET = "loodaa";

    private final long JWT_EXPIRATION = 6000000L;

    public String generationJWTToken(CustomUserDetail customUserDetail) {

        Date now = new Date();
        /** thời gian tồn tại của ma token*/
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(Long.toString(customUserDetail.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

    }

    /**
     * lấy thông tin người dùng từ token
     */
    public Long getUserIDFromJWT(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    public Boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException ex1){
            log.error("invalid JWT token");
        }catch (ExpiredJwtException ex2){
            log.error("Expiry JWT Token");
        }catch(UnsupportedJwtException ex3){
            log.error("Unsupport JWT Token");
        }catch(IllegalArgumentException ex4){
            log.error("JWT claims string is Empty");
        }
        return  false;
    }


}
