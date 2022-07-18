import java.nio.file.Path;
import java.util.List;

public interface BruteForcable {
    String decode(Path inputFile, Path outputFile, List<Character> alphabet);
}
