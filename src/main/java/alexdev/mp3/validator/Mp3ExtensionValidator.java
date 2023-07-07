package alexdev.mp3.validator;

import org.springframework.stereotype.Component;

@Component
public class Mp3ExtensionValidator implements Validator<String> {
    @Override
    public boolean validate(String fileName) {
        // Expresión regular para verificar la extensión del archivo
        String extensionPattern = "^.+\\.(?i)(mp3)$";
        // Verificar si el nombre del archivo coincide con el patrón de extensión
        return fileName != null && fileName.matches(extensionPattern);
    }
}
