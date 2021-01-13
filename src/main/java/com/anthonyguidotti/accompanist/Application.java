package com.anthonyguidotti.accompanist;

import com.anthonyguidotti.accompanist.tuning.TuningService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		for (String arg : args) {
			if ("generateNotes".equalsIgnoreCase(arg)) {
				ctx.getBean(TuningService.class).generateTunings();
			}
		}
	}

}
