package net.annakat.restapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("files")
public class FileEntity {
    @Id
    private Integer id;
    private String name;
    private String location;
    private Status status;
}
