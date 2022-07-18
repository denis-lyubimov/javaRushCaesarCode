import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CaesarDecoder extends CaesarConverter implements Decodable {
    @Override
    public String decode(byte key, Path inputFile, Path outputFile, List<Character> alphabet) {
        try {
            String fileToString = Files.readString(inputFile, StandardCharsets.UTF_8);
            char[] encodedChars = fileToString.toLowerCase().toCharArray();
            for (int i = 0; i < encodedChars.length; i++) {
                int charIndex = alphabet.indexOf(encodedChars[i]);
                if (charIndex < 0) {
                    continue;
                }
                if (charIndex - key < 0) {
                    encodedChars[i] = alphabet.get(charIndex - key + alphabet.size());
                    continue;
                }
                encodedChars[i] = alphabet.get(charIndex - key);
            }
            fileToString = new String(encodedChars);
            return Files.write(outputFile, fileToString.getBytes(StandardCharsets.UTF_8)).toAbsolutePath().toString();
        } catch (IOException e) {
            return e.toString();
        }
    }
}
