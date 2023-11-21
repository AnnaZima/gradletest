package net.annakat.restapp.rest;

import lombok.RequiredArgsConstructor;
import net.annakat.restapp.dto.UserDto;
import net.annakat.restapp.mapper.UserMapper;
import net.annakat.restapp.model.User;
import net.annakat.restapp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestControllerV1 {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public Mono<UserDto> getUser(@PathVariable("id") Integer id) {
        return userService.getById(id).map(userMapper::map);
    }


    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public Flux<UserDto> getAllUser() {
        return userService.getAll().map(userMapper::map);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Mono<UserDto> updateUser(@RequestBody UserDto dto) {
        User user = userMapper.map(dto);
        return userService.updateUser(user).map(userMapper::map);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Mono<Boolean> deleteUser(@PathVariable("id") Integer id) {
        return userService.deleteUser(id);
    }









}
