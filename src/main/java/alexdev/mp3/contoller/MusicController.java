package alexdev.mp3.contoller;

import alexdev.mp3.service.AudioStreamingService;
import alexdev.mp3.service.DeleteMusicService;
import alexdev.mp3.service.MusicListReaderService;
import alexdev.mp3.service.MusicStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicController {

    private final MusicListReaderService musicListReaderService;
    private final AudioStreamingService audioStreamingService;
    private final MusicStorageService musicStorageService;
    private final DeleteMusicService deleteMusicService;

    public MusicController(MusicListReaderService musicListReaderService,
                           AudioStreamingService audioStreamingService,
                           MusicStorageService musicStorageService,
                           DeleteMusicService deleteMusicService) {
        this.musicListReaderService = musicListReaderService;
        this.audioStreamingService = audioStreamingService;
        this.musicStorageService = musicStorageService;
        this.deleteMusicService = deleteMusicService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getMusicList() {
        return ResponseEntity.ok(musicListReaderService.getMusicList());
    }

    @GetMapping(value = "/play/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> playMusic(@PathVariable("fileName") String fileName) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(audioStreamingService.getMusicResource(fileName));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        musicStorageService.saveMusic(file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<?> deleteMusic(@PathVariable String fileName) {
        deleteMusicService.deleteMusic(fileName);
        return ResponseEntity.ok().build();
    }

}
