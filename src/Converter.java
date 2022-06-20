import java.nio.file.LinkPermission;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Converter {
    private static final List<Character> ALPHABET = Arrays.asList('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' ');
    private Path inputFile;
    private Path outputFile;

    public Converter(){}
    public Converter(Path inputFile, Path outputFile ) {
        setInputFile(inputFile);
        setOutputFile(outputFile);
    }

    public Path getInputFile() {
        return inputFile;
    }

    public void setInputFile(Path inputFile) {
        this.inputFile = inputFile;
    }

    public Path getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(Path outputFile) {
        this.outputFile = outputFile;
    }
}
