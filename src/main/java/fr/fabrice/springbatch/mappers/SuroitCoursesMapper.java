package fr.fabrice.springbatch.mappers;

import fr.fabrice.springbatch.dto.SuroitCoursesDTO;
import fr.fabrice.springbatch.entities.SuroitCourses;

public interface SuroitCoursesMapper {
    SuroitCourses from(SuroitCoursesDTO suroitCoursesDTO);
    SuroitCoursesDTO from(SuroitCourses suroitCourses);
}
