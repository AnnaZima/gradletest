package net.annakat.restapp.repository;

import net.annakat.restapp.model.Event;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EventRepository extends ReactiveCrudRepository<Event, Integer> {


    @Query("SELECT * FROM events WHERE user_id = :id")
    Flux<Event> findAllEventsOfUser(Integer id);

    @Modifying
    @Query("UPDATE events SET status = :DELETE WHERE id = :id")
    Mono<Boolean> deleteEvent(@Param("id") Integer id);


}
