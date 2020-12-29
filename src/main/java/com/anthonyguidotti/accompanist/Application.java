package com.anthonyguidotti.accompanist;

import com.anthonyguidotti.accompanist.note.NoteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		for (String arg : args) {
			if ("generateNotes".equalsIgnoreCase(arg)) {
				ctx.getBean(NoteService.class).generateNoteMaps();
			}
		}
	}

}
