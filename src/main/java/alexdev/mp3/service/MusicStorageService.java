package alexdev.mp3.service;

import org.springframework.web.multipart.MultipartFile;


public interface MusicStorageService {
    void saveMusic(MultipartFile file);
}
