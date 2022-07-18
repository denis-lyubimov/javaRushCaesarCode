import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CaesarCoder extends CaesarConverter implements Codable {


    public CaesarCoder() {
    }

//    public CaesarCoder(byte key, Path inputFile, Path outputFile, List<Character> alphabet) {
//        super(inputFile, outputFile);
//        this.key = key;
//    }

    @Override
    public String encode(byte key, Path inputFile, Path outputFile, List<Character> alphabet) {
        try {
            String fileToString = Files.readString(inputFile, StandardCharsets.UTF_8);
            char[] rawChars = fileToString.toLowerCase().toCharArray();
            for (int i = 0; i < rawChars.length; i++) {
                int charIndex = alphabet.indexOf(rawChars[i]);
                if (charIndex < 0) {
                    continue;
                }
                if (charIndex + key >= alphabet.size()) {
                    rawChars[i] = alphabet.get(charIndex + key - alphabet.size());
                    continue;
                }
                rawChars[i] = alphabet.get(charIndex + key);
            }
            fileToString = new String(rawChars);
            return Files.write(outputFile, fileToString.getBytes(StandardCharsets.UTF_8)).toAbsolutePath().toString();
        } catch (IOException e) {
            return e.toString();
        }
    }


}
