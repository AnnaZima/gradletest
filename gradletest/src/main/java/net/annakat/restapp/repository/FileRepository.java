package net.annakat.restapp.repository;

import net.annakat.restapp.model.FileEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FileRepository extends ReactiveCrudRepository<FileEntity, Integer> {

    @Modifying
    @Query("UPDATE files SET status = :DELETE WHERE id = :id")
    Mono<Boolean> deleteFile(@Param("id") Integer id);

}
