package alexdev.mp3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DeleteMusicServiceImpl implements DeleteMusicService{

    @Value("${music.directory}")
    private String MUSIC_DIRECTORY;

    @Override
    public void deleteMusic(String fileName) {
        // Crea un objeto File que representa el archivo de música a eliminar
        File musicFile = new File(MUSIC_DIRECTORY + "/" + fileName);
        // Verifica si el archivo existe
        if (!musicFile.exists()) {
            throw new RuntimeException("File does not exists");
        }
        // Intenta eliminar el archivo
        boolean result = musicFile.delete();
        // Verifica si la operación de eliminación fue exitosa
        if(!result) {
            throw new RuntimeException("Error deleting file");
        }
    }
}
