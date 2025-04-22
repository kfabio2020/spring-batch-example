package fr.fabrice.springbatch.config;


import fr.fabrice.springbatch.dto.SuroitCoursesDTO;
import fr.fabrice.springbatch.entities.SuroitCourses;
import fr.fabrice.springbatch.mappers.SuroitCoursesMapper;
import fr.fabrice.springbatch.repository.SuroitCoursesRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    private final SuroitCoursesRepository suroitCoursesRepository;
    private final SuroitCoursesMapper suroitCoursesMapper;
    private final String fileName = "suroit-courses.csv";

    public SpringBatchConfig(SuroitCoursesRepository suroitCoursesRepository, SuroitCoursesMapper suroitCoursesMapper) {
        this.suroitCoursesRepository = suroitCoursesRepository;
        this.suroitCoursesMapper = suroitCoursesMapper;
    }

    @Bean
    public Job importerSuroitCoursesJob(JobRepository jobRepository, Step step){
        return new JobBuilder("importerSuroitCoursesJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

    //Avec Mutlithread
    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("importerSuroitCoursesStep", jobRepository)
                .<SuroitCoursesDTO, SuroitCourses>chunk(10000,transactionManager)
                .reader(itemReader())
                .processor(processor())
                .writer(itemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }


    // Sans le multiThread
    /*@Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("importerSuroitCoursesStep", jobRepository)
                .<SuroitCoursesDTO, SuroitCourses>chunk(5000,transactionManager)
                .reader(itemReader())
                .processor(processor())
                .writer(itemWriter())
                .build();
    }*/

    // Poblème de out of memory car CsvToBeanBuilder charge tout le fichier en seule fois dans une List.
    /*@Bean
    public ItemReader<SuroitCoursesDTO> itemReader() throws Exception {
        List<SuroitCoursesDTO> suroitCoursesList = new CsvToBeanBuilder<SuroitCoursesDTO>(new FileReader("src/main/resources/"+fileName))
                .withType(SuroitCoursesDTO.class)
                .withSeparator(';')
                .build()
                .parse();
        return new ListItemReader<>(suroitCoursesList);
    }*/

    // Marche mais n'utulise pas CsvToBeanBuilder
    /*@Bean
    public FlatFileItemReader<SuroitCoursesDTO> itemReader() {
        return new FlatFileItemReaderBuilder<SuroitCours

                .resource(new ClassPathResource(fileName))
                .delimited()
                .delimiter(";")
                .names("codeStifLigne", "antenne", "idtm","idmiss","course","nommiss","sens","numseq","hrpass","idptar","zdep")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(SuroitCoursesDTO.class);
                }})
                .linesToSkip(1)
                .build();
    }*/

    @Bean
    public ItemReader<SuroitCoursesDTO> itemReader() {
        return new StreamingCsvItemReader<>("src/main/resources/"+fileName, SuroitCoursesDTO.class);
    }



    @Bean
    public ItemProcessor<SuroitCoursesDTO,SuroitCourses> processor(){
        return dto -> {
            // On vérifie si le DTO est vide (tous les champs null ou vides)
            if (dto == null || isDtoEmpty(dto)) {
                return null; // Skipped by Spring Batch
            }
            return suroitCoursesMapper.from(dto);
        };
    }

    private boolean isDtoEmpty(SuroitCoursesDTO dto) {
        return dto.getCodeStifLigne() == null || dto.getCodeStifLigne().isBlank();
        // Ajoute d'autres vérifications si nécessaire
    }

    @Bean
    public ItemWriter<SuroitCourses> itemWriter() {
        return chunk -> suroitCoursesRepository.saveAll(chunk.getItems());
    }
}
