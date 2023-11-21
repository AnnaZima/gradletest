package net.annakat.restapp.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

public class TokenDetails {
    private Integer id;
    private String token;
    private Instant createdAt;
    private Instant expiresAt;


}
