package fr.fabrice.springbatch.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component
public class FileTypeDecider implements JobExecutionDecider {

    private static final String FILE_NAME = "suroit_courses_oth.csv";

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if (FILE_NAME.endsWith(".csv1")) {
            System.out.println("Fichier CSV détecté.");
            return new FlowExecutionStatus("CSV");
        } else {
            System.out.println("Fichier non CSV détecté.");
            return new FlowExecutionStatus("UNKNOWN");
        }
    }
}
