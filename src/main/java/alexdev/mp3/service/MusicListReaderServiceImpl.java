package alexdev.mp3.service;

import alexdev.mp3.validator.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class MusicListReaderServiceImpl implements MusicListReaderService{

    @Value("${music.directory}")
    private String musicDirectory;

    private final Validator<String> mp3ExtensionValidator;

    public MusicListReaderServiceImpl(Validator<String> mp3ExtensionValidator) {
        this.mp3ExtensionValidator = mp3ExtensionValidator;
    }

    @Override
    public List<String> getMusicList() {
        // Crea un objeto File que representa la carpeta de música
        File folder = new File(musicDirectory);
        // Obtiene la lista de archivos dentro de la carpeta
        File[] files = folder.listFiles();
        // Verifica si la lista de archivos es nula (carpeta no encontrada)
        Objects.requireNonNull(files);
        // Utiliza Java Streams para realizar el procesamiento de la lista de archivos
        return Arrays.stream(files)
                .filter(File::isFile) // Filtra solo los archivos (no directorios)
                .map(File::getName) // Mapea cada archivo a su nombre
                .filter(mp3ExtensionValidator::validate) // Filtra solo los archivos con extensión .mp3 o .MP3
                .toList(); // Recolecta los nombres de los archivos en una lista
    }
}
