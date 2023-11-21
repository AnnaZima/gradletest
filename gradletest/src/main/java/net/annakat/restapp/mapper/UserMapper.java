package net.annakat.restapp.mapper;

import net.annakat.restapp.dto.UserDto;
import net.annakat.restapp.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(User user);

    @InheritInverseConfiguration
    User map(UserDto userDto);
}
