package alexdev.mp3;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Mp3Application {

	private static final Logger log = LoggerFactory.getLogger(Mp3Application.class);

	@Value("${music.directory}")
	private String musicDirectory;

	public static void main(String[] args) {
		SpringApplication.run(Mp3Application.class, args);
	}

	@PostConstruct
	public void init() {
		File musicFolder = new File(musicDirectory);
		if (!musicFolder.exists()) {
			boolean created = musicFolder.mkdirs();
			if (created) {
				log.info("Carpeta 'music' creada en " + musicDirectory);
			} else {
				log.error("No se pudo crear la carpeta 'music' en " + musicDirectory);
			}
		}
	}

}
