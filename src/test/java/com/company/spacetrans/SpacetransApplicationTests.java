package com.company.spacetrans;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;

@SpringBootTest
class SpacetransApplicationTests {

	@Test
	void contextLoads() {
		DecimalFormat decimalFormat = new DecimalFormat("###.00%");
		System.out.println("MY TEST: " + decimalFormat.format(.5));
	}

}
