package net.annakat.restapp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import net.annakat.restapp.model.Status;


@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileDto {
    Integer id;
    String name;
    String location;
    Status status;
}
