package alexdev.mp3.service;

import alexdev.mp3.validator.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class MusicStorageServiceImpl implements MusicStorageService {
    @Value("${music.directory}")
    private String musicDirectory;

    private Validator<String> mp3ExtensionValidator;

    public MusicStorageServiceImpl(Validator<String> mp3ExtensionValidator) {
        this.mp3ExtensionValidator = mp3ExtensionValidator;
    }

    @Override
    public void saveMusic(MultipartFile file) {
        // Verifica que el archivo no sea nulo
        Objects.requireNonNull(file);
        // Obtiene el nombre original del archivo
        String fileName = file.getOriginalFilename();
        // Verificar la extensión del archivo
        if (!mp3ExtensionValidator.validate(fileName)) {
            throw new IllegalArgumentException("Invalid file extension.");
        }
        // Crea la ruta de destino para guardar el archivo
        Path destination = Path.of(musicDirectory, fileName);
        try (InputStream inputStream = file.getInputStream()) {
            // Copia los datos del archivo de entrada al destino especificado
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
