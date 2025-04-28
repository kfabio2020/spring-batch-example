package fr.fabrice.springbatch.config;

import fr.fabrice.springbatch.dto.SuroitCoursesDTO;
import fr.fabrice.springbatch.entities.SuroitCourses;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SuroitCoursesProcessorConfig {

    private ModelMapper modelMapper;

    public SuroitCoursesProcessorConfig() {
        modelMapper = new ModelMapper();
    }

    @Bean
    public ItemProcessor<SuroitCoursesDTO, SuroitCourses> validProcessor() {
        return dto -> {
            return modelMapper.map(dto, SuroitCourses.class);
        };
    }

    @Bean
    public ItemProcessor<SuroitCoursesDTO, SuroitCourses> invalidProcessor() {
        return dto -> {
            if (dto == null || isDtoEmpty(dto)) {
                return null;
            }
            return null;
        };
    }

    @Bean
    public ClassifierCompositeItemProcessor<SuroitCoursesDTO, SuroitCourses> compositeProcessor() {
        ClassifierCompositeItemProcessor<SuroitCoursesDTO, SuroitCourses> processor = new ClassifierCompositeItemProcessor<>();
        processor.setClassifier(dto -> {
            if (dto.getCodeStifLigne() != null) {
                return validProcessor();
            } else {
                return invalidProcessor();
            }
        });
        return processor;
    }

    @Bean
    public ItemProcessor<SuroitCoursesDTO, SuroitCourses> processor(){
        return dto -> {
            if (dto == null || isDtoEmpty(dto)) {
                return null;
            }
            return modelMapper.map(dto, SuroitCourses.class);
        };
    }

    private boolean isDtoEmpty(SuroitCoursesDTO dto) {
        return dto.getCodeStifLigne() == null || dto.getCodeStifLigne().isBlank();
    }
}
