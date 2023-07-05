package alexdev.mp3.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    // Maneja cualquier excepción o RuntimeException que se produzca
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity
                .internalServerError() // Retorna un código de respuesta 500 (Internal Server Error)
                .build();
    }
}

