package net.annakat.restapp.model;
import lombok.*;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table ("users")
public class User {

    @Id
    private Integer id;
    private String userName;
    private String password;
    private UserRole userRole;
    private List<Event> events;
    private Status status;
    private boolean enabled;



}
