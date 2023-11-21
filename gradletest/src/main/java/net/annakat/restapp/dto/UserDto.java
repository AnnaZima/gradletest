package net.annakat.restapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.Status;
import net.annakat.restapp.model.UserRole;


import java.util.List;
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {

    private Integer id;
    private String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private UserRole userRole;
    private List<Event> events;
    private Status status;
    private boolean enabled;

}
