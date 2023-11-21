package net.annakat.restapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.FileEntity;
import net.annakat.restapp.model.Status;
import net.annakat.restapp.model.User;
import net.annakat.restapp.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

   private final FileRepository fileRepository;
   private final EventService eventService;

   private Mono<Event> makeEvent(String name, Mono<FileEntity> file, Mono<User> user) {
       Event event = new Event();
       event.setEventName(name);
       event.setFile(file.block());
       event.setUser(user.block());
       event.setStatus(Status.ACTIVE);
       return eventService.createEvent(event);
   }


    public Flux<FileEntity> getAll() {
       return fileRepository.findAll();
    }

    public Mono<FileEntity> getFileById(Integer id) {
        return fileRepository.findById(id);
    }

    public Mono<FileEntity> saveFileEntity(Mono<User> user, FileEntity file) {
        Mono<FileEntity> save = fileRepository.save(file);
        Mono<Event> create = makeEvent("create", save, user);
        log.info("event: " + create);
        return save;
    }

    public Mono<FileEntity> updateFileEntity(Mono<User> user, FileEntity file) {
        Mono<FileEntity> savedFileEntity = fileRepository.findById(file.getId()).flatMap(f -> {
            file.setStatus(f.getStatus());
            file.setLocation(f.getLocation());
            return fileRepository.save(file);
        });
        Mono<Event> update = makeEvent("update", savedFileEntity, user);
        log.info("event: " + update);
        return savedFileEntity;
    }

    public Mono<Boolean> deleteFileEntity(Mono<User> user, Integer id) {
        Mono<FileEntity> byId = fileRepository.findById(id);
        Mono<Boolean> booleanMono = fileRepository.deleteFile(id);
        if(Boolean.TRUE.equals(booleanMono.block())){
            makeEvent("delete", byId,  user);
        }
        return booleanMono;
    }
}
