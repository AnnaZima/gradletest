package net.annakat.restapp.security;

import lombok.RequiredArgsConstructor;
import net.annakat.restapp.model.User;
import net.annakat.restapp.repository.UserRepository;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomerPrincipal customerPrincipal = (CustomerPrincipal) authentication.getPrincipal();
        return userRepository.findById(customerPrincipal.getId())
                .filter(User::isEnabled)
                .switchIfEmpty(Mono.error(new Throwable("")))
                .map(user -> authentication);
    }
}
