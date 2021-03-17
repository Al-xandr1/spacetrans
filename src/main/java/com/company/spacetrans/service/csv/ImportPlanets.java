package com.company.spacetrans.service.csv;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
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
public class ImportPlanets {

    private static final char SEPARATOR = ';';
    private static final Logger LOG = LoggerFactory.getLogger(ImportPlanets.class);

    public List<PlanetCSV> parse(byte[] csvBytes) {
        try (InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(csvBytes), StandardCharsets.UTF_8)) {
            ColumnPositionMappingStrategy<PlanetCSV> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(PlanetCSV.class);

            CsvToBean<PlanetCSV> parser = new CsvToBeanBuilder<PlanetCSV>(reader)
                    .withType(PlanetCSV.class)
                    .withMappingStrategy(strategy)
                    .withSeparator(SEPARATOR)
                    .build();

            LOG.info("Import succeeded");
            return parser.parse();

        } catch (IOException e) {
            LOG.error("Import failed", e);
        }

        return null;
    }
}
