package fr.fabrice.springbatch.config;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.InitializingBean;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class StreamingCsvItemReader<T> implements ItemReader<T>, InitializingBean, ItemStream {

    private final String filePath;
    private final Class<T> type;
    private Iterator<T> iterator;
    private CSVReader csvReader;
    private InputStreamReader fileReader;

    public StreamingCsvItemReader(String filePath, Class<T> type) {
        this.filePath = filePath;
        this.type = type;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        // Pas besoin ici dans ce cas précis
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // Pas besoin ici non plus
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            if (csvReader != null) csvReader.close();
            if (fileReader != null) fileReader.close();
        } catch (Exception ignored) {}
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        fileReader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);

        CSVReader csvReader = new CSVReaderBuilder(fileReader)
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build();
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                .withType(type)
                .build();
        this.iterator = csvToBean.iterator();
    }

    @Override
    public T read() {
        if (iterator != null && iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}
