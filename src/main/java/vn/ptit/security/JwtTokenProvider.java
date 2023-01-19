package vn.ptit.security;

import io.jsonwebtoken.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import vn.ptit.exception.InternalServerException;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    // generate token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    // get username from the token
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // validate JWT token
    @SneakyThrows
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new InternalServerException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new InternalServerException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new InternalServerException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new InternalServerException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new InternalServerException("JWT claims string is empty");
        }
    }

}
