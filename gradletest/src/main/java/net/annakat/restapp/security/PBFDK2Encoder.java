package net.annakat.restapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PBFDK2Encoder implements PasswordEncoder {


    @Value("${jwt.password.encoder.secret}")
    String secret;
    @Value("${jwt.password.encoder.iteration}")
    Integer iteration;
    @Value("${jwt.password.encoder.keylength}")
    Integer keyLength;

    private static final String SECRET_KEY_INSTANCE = "PBKDF2WithHmacSHA512";

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            SecretKeyFactory instance = SecretKeyFactory.getInstance(SECRET_KEY_INSTANCE);
            byte[] bytes = instance.generateSecret(new PBEKeySpec(rawPassword.toString().toCharArray(),
                    secret.getBytes(), iteration, keyLength)).getEncoded();

            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
