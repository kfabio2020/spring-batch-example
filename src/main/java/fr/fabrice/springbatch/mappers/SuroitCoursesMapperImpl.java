package fr.fabrice.springbatch.mappers;

import fr.fabrice.springbatch.dto.SuroitCoursesDTO;
import fr.fabrice.springbatch.entities.SuroitCourses;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SuroitCoursesMapperImpl implements SuroitCoursesMapper{

    private ModelMapper modelMapper;

    public SuroitCoursesMapperImpl() {
        modelMapper = new ModelMapper();
    }

    @Override
    public SuroitCourses from(SuroitCoursesDTO suroitCoursesDTO) {
        return modelMapper.map(suroitCoursesDTO, SuroitCourses.class);
    }

    @Override
    public SuroitCoursesDTO from(SuroitCourses suroitCourses) {
        return modelMapper.map(suroitCourses, SuroitCoursesDTO.class);
    }
}
