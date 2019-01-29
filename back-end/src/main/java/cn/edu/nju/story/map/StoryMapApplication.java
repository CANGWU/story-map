package cn.edu.nju.story.map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class StoryMapApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoryMapApplication.class, args);
	}

}

