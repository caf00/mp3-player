package alexdev.mp3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class MusicListReaderServiceImpl implements MusicListReaderService{

    @Value("${music.directory}")
    private String MUSIC_DIRECTORY;

    @Override
    public List<String> getMusicList() {
        // Crea un objeto File que representa la carpeta de música
        File folder = new File(MUSIC_DIRECTORY);
        // Obtiene la lista de archivos dentro de la carpeta
        File[] files = folder.listFiles();
        // Verifica si la lista de archivos es nula (carpeta no encontrada)
        Objects.requireNonNull(files);
        // Utiliza Java Streams para realizar el procesamiento de la lista de archivos
        return Arrays.stream(files)
                .filter(File::isFile) // Filtra solo los archivos (no directorios)
                .map(File::getName) // Mapea cada archivo a su nombre
                .filter(name -> name.endsWith(".mp3") || name.endsWith(".MP3")) // Filtra solo los archivos con extensión .mp3 o .MP3
                .toList(); // Recolecta los nombres de los archivos en una lista
    }
}
