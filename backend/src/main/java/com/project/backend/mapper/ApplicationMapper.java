package com.project.backend.mapper;

import com.project.backend.dto.ApplicationDto;
import com.project.backend.model.Application;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Named("mapFromApplicationDtoToApplication")
    Application mapFromApplicationDtoToApplication(ApplicationDto applicationDto);

    @Named("mapFromApplicationToApplicationDto")
    ApplicationDto mapFromApplicationToApplicationDto(Application application);

    @IterableMapping(qualifiedByName = "mapFromApplicationToApplicationDto")
    List<ApplicationDto> mapFromApplicationsToApplicationDto(List<Application> applications);
    
}

