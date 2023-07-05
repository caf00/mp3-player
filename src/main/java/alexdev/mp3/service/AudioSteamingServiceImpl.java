package alexdev.mp3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AudioSteamingServiceImpl implements AudioStreamingService{
    @Value("${music.directory}")
    private String musicDirectory;

    @Override
    public byte[] getMusicResource(String fileName) {
        // Obtiene la ruta completa del archivo de música
        Path filePath = Paths.get(musicDirectory, fileName);
        // Arreglo de bytes para almacenar los datos del archivo de música
        byte[] audioBytes = new byte[0];
        try {
            // Lee todos los bytes del archivo de música y los guarda en el arreglo audioBytes
            audioBytes = Files.readAllBytes(filePath);
        } catch (IOException e) {
            // En caso de error al leer el archivo, se lanza una excepción
            throw new RuntimeException(e);
        }
        // Retorna el arreglo de bytes que representa el contenido del archivo de música
        return audioBytes;
    }
}
