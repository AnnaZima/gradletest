package net.annakat.restapp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import net.annakat.restapp.exception.AuthenticationException;
import net.annakat.restapp.model.User;
import net.annakat.restapp.repository.UserRepository;
import net.annakat.restapp.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.*;

@Component
@RequiredArgsConstructor

public class SecurityService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expirationInSeconds;
    @Value("${jwt.issuer}")
    private String issuer;

    private TokenDetails generateToken (User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getUserRole());
        claims.put("username", user.getUserName());
        return generateToken(claims, user.getId().toString());
    }

    private TokenDetails generateToken(Map<String, Object> claims, String subject) {
        long expInMillis = expirationInSeconds * 1000L;
        long liveTime = Instant.now().toEpochMilli() + expInMillis;
        return generateToken(Instant.ofEpochSecond(liveTime), claims, subject);
    }

    private TokenDetails generateToken(Instant expirationsTime, Map<String, Object> claims, String subject) {
        Instant currentTime = Instant.now();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(subject)
                .setExpiration(Date.from(expirationsTime))
                .setIssuedAt(Date.from(currentTime))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();
        return TokenDetails.builder()
                .token(token)
                .createdAt(currentTime)
                .expiresAt(expirationsTime)
                .build();

    }

    public Mono<TokenDetails> authenticate(String name, String password) {

      return  userService.getByUserName(name)
                .flatMap(user -> {
                    if (!user.isEnabled()) {
                        return Mono.error(new AuthenticationException("Account disabled", "USER_ACCOUNT_DISABLED"));
                    }
                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        return Mono.error(new AuthenticationException("Invalid password", "INVALID_PASSWORD"));
                    }
                    return Mono.just(generateToken(user)
                            .toBuilder()
                            .id(user.getId())
                            .build());
                })
                .switchIfEmpty(Mono.error(new AuthenticationException("Invalid username", "INVALID_USERNAME")));
    }
}
