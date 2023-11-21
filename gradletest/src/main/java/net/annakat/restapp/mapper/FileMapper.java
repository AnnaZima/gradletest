package net.annakat.restapp.mapper;

import net.annakat.restapp.dto.FileDto;
import net.annakat.restapp.model.FileEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileDto map(FileEntity file);

    @InheritInverseConfiguration
    FileEntity map(FileDto dto);
}
