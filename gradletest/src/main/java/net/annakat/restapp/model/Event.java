package net.annakat.restapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Event {
    @Id
    private Integer id;
    private String eventName;
    private User user;
    private FileEntity file;
    private Status status;
}
