package net.annakat.restapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.annakat.restapp.exception.AuthenticationException;
import net.annakat.restapp.exception.UnauthorizedException;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Base64;

public class JwtHandler {

    private final String secrete;

    public JwtHandler(String secrete) {
        this.secrete = secrete;
    }

    public Mono<VerificationResult> checkToken(String accessToken){
        return Mono.just(verify(accessToken)).onErrorResume(e -> Mono.error(new UnauthorizedException(e.getMessage())));

    }

    private VerificationResult verify(String token) {
        Claims claims = getClaimsFromToken(token);
        final Instant expirationTime = claims.getExpiration().toInstant();
        if(expirationTime.isBefore(Instant.now())){
            throw new RuntimeException("Token expired");
        }
        return new VerificationResult(claims, token);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secrete.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }


    public static class VerificationResult{
        public Claims claims;
        public String token;

        public VerificationResult(Claims claims, String token) {
            this.claims = claims;
            this.token = token;
        }


    }
}
