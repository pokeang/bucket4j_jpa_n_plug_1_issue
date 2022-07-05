package com.exercise.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@RunWith(SpringRunner.class)
// @SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		System.out.print("hello");
	}
	
	@Test
	void testBasicBucket4J () {
		Refill refill = Refill.intervally(10, Duration.ofSeconds(1));
		Bandwidth limit = Bandwidth.classic(10, refill);
		Bucket bucket = Bucket4j.builder()
		    .addLimit(limit)
		    .build();

		for (int i = 1; i <= 10; i++) {
		    assertTrue(bucket.tryConsume(1));
		}
		assertFalse(bucket.tryConsume(1));
	}

}
