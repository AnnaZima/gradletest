package net.annakat.restapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.annakat.restapp.model.Status;
import net.annakat.restapp.model.User;
import net.annakat.restapp.model.UserRole;
import net.annakat.restapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> registerUser(User user) {
        return userRepository.save(user.toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                .userRole(UserRole.ROLE_USER)
                        .enabled(true)
                        .status(Status.ACTIVE)
                        .build()
                ).doOnSuccess(u -> {
                    log.info("registerUser user^ {} created", u);
        });
    }


    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    public Mono<User> getById(Integer id) {
        return userRepository.findById(id);
    }


    public Mono<User> updateUser(User user) {
        Integer id = user.getId();
      return userRepository.findById(id).flatMap(
                u -> {
                    user.setEvents(u.getEvents());
                    user.setStatus(u.getStatus());
                  return userRepository.save(user);
                });
    }

    public Mono<Boolean> deleteUser(Integer id) {
        return userRepository.deleteUser(id);
    }

    public Mono<User> getByUserName(String name) {
        return userRepository.findByUserName(name);
    }
}
