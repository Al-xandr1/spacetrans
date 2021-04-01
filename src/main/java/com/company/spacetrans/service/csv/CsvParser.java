package com.company.spacetrans.service.csv;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CsvParser {

    private static final char SEPARATOR = ';';

    private static final Logger LOG = LoggerFactory.getLogger(CsvParser.class);

    public <T> List<T> parse(byte[] csvBytes, Class<T> csvClass) {
        try (InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(csvBytes), StandardCharsets.UTF_8)) {

            ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(csvClass);

            return new CsvToBeanBuilder<T>(reader)
                    .withType(csvClass)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(SEPARATOR)
                    .build()
                    .parse();

        } catch (IOException e) {
            LOG.error("Unable to read byte array", e);
        }

        return null;
    }
}
