package net.annakat.restapp.rest;

import lombok.RequiredArgsConstructor;
import net.annakat.restapp.dto.FileDto;
import net.annakat.restapp.mapper.FileMapper;
import net.annakat.restapp.mapper.UserMapper;
import net.annakat.restapp.model.FileEntity;
import net.annakat.restapp.model.User;
import net.annakat.restapp.security.CustomerPrincipal;
import net.annakat.restapp.service.EventService;
import net.annakat.restapp.service.FileService;
import net.annakat.restapp.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileRestControllerV1 {

    private final FileService fileService;
    private final UserService userService;
    private final FileMapper fileMapper;

    private Mono<User> getCurrentUser(Mono<SecurityContext> securityContext) {
        Mono<String> name = securityContext.map(SecurityContext::getAuthentication)
                .map(Authentication::getName);
        return userService.getByUserName(name.block());
    }


    @PostMapping()
    public Mono<FileDto> createFile (@RequestBody FileDto dto) {
        Mono<User> currentUser = getCurrentUser(ReactiveSecurityContextHolder.getContext());
        FileEntity fileEntity = fileMapper.map(dto);
        return fileService.saveFileEntity(currentUser, fileEntity).map(fileMapper::map);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public Mono<FileDto> getFile(@PathVariable("id") Integer id) {
      return fileService.getFileById(id).map(fileMapper::map);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('UPDATE_FILE') or hasRole('ADMIN') or hasAuthority('MODERATOR') or principal.username == #file.owner")
    public Mono<FileDto> updateFile(@RequestBody FileDto dto) {
        Mono<User> currentUser = getCurrentUser(ReactiveSecurityContextHolder.getContext());
        FileEntity fileEntity = fileMapper.map(dto);
        return fileService.updateFileEntity(currentUser, fileEntity).map(fileMapper::map);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('UPDATE_FILE') or hasRole('ADMIN') or hasAuthority('MODERATOR') or principal.username == #file.owner")
    public Mono<Boolean> deleteFile(@PathVariable("id") Integer id) {
        Mono<User> currentUser = getCurrentUser(ReactiveSecurityContextHolder.getContext());
        return fileService.deleteFileEntity(currentUser, id);
    }

    @GetMapping()

    public Flux<FileDto> getAllFile() {
        return fileService.getAll().map(fileMapper::map);
    }







}
