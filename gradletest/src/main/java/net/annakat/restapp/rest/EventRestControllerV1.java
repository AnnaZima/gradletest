package net.annakat.restapp.rest;

import lombok.RequiredArgsConstructor;
import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.User;
import net.annakat.restapp.service.EventService;
import net.annakat.restapp.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequiredArgsConstructor

public class EventRestControllerV1 {

    private final EventService eventService;
    private final UserService userService;


    @GetMapping
    public Mono<Event> getEvent(@RequestParam Integer id) {
        return eventService.getEventById(id);
    }

    @GetMapping
    public Flux<Event> getAllEventByUsername(@RequestBody String username) {
       Mono<User> user = userService.getByUserName(username);
        return eventService.getAllEventByUser(Objects.requireNonNull(user.block()).getId());
    }

    @PostMapping public Mono<Boolean> deleteEvent(@RequestParam Integer id) {
        return eventService.deleteEvent(id);
    }
}
