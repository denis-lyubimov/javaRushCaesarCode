import java.nio.file.Path;
import java.util.List;

public interface Codable {

    String encode(byte key, Path inputFile, Path outputFile, List<Character> alphabet);
}
