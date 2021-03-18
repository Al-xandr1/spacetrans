package com.company.spacetrans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
public class SpacetransApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpacetransApplication.class, args);
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix="main.datasource")
	DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	//todo  HIGH подключить PostgreSQL
	//todo  HIGH стоит убрать создание зависимых сущностей без созданных родительских. Понять как
	//todo  LOW исключить version из поиска в UI. Понять как

	//todo  BUG Shift+f1 – пробрасывает на документацию, а там 404
}
