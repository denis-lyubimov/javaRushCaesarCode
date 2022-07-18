import java.nio.file.Path;
import java.util.List;

public interface Decodable {

    String decode(byte key, Path inputFile, Path outputFile, List<Character> alphabet);

}
