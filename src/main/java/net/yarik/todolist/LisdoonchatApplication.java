package net.yarik.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class LisdoonchatApplication {

	public static void main(String[] args) {

		File uploadedImagesDir = new File("./uploaded_images");
		if (!uploadedImagesDir.exists()){
			uploadedImagesDir.mkdirs();
		}

		SpringApplication.run(LisdoonchatApplication.class, args);
	}

}
