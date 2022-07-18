import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InputValidator {
    private int alphabetSize;
    private static final String FILE_MESSAGE_1 = "не введено имя файла";
    private static final String FILE_MESSAGE_2 = "невозможно прочитать %s, проверьте верность имени файла и права на этот файл для текущего пользователя, не должно быть пробелов в пути до файла и имени файла\n";
    private static final String FILE_MESSAGE_3 = "файл пустой, нужен с текстовыми данными";
    //    private static final String FILE_MESSAGE_4 = "эсепшен";
//    private static final String FILE_MESSAGE_5 = FILE_MESSAGE_1 + ", будет использован файл " + defaultOutputFile;
    private static final String FILE_MESSAGE_6 = "файла %1$s не существует, создаем %2$s\n";
    private static final String FILE_MESSAGE_7 = "нет рпав на запись в файл, измените права на запись";
//    private static final String FILE_MESSAGE_DEFAULT = "что-то пошло не так";

    public InputValidator() {
    }

    InputValidator(int alphabetSize){
        this.alphabetSize = alphabetSize;
    }

    // check key
    public boolean validateKey(byte key){
        if ( key > alphabetSize || key < 1){
            System.out.printf("ключ должен быть больше нуля и меньше %d(количество символов в алфавите)\n",alphabetSize);
            return false;
        }
        return true;
    }

    //  checkInputFile
    public boolean validateInputFile(Path inputFile) {
        try {
            if (inputFile.toString().isEmpty()) {
                System.out.println(FILE_MESSAGE_1);
                return false;
            }
            Path inputFilePath = inputFile.toAbsolutePath();
            if (!Files.isReadable(inputFilePath)) {
                System.out.printf(FILE_MESSAGE_2,inputFile);
                return false;
            }
            if (Files.size(inputFilePath) == 0) {
                System.out.println(FILE_MESSAGE_3);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //    checkOutputFile
    public boolean validateOutputFile(Path outputFile) {
        try {
            if (outputFile.toString().isEmpty()) {
                System.out.println(FILE_MESSAGE_1);
                return false; // use default name of output file in default folder
            }
            Path outputFilePath = outputFile.toAbsolutePath();
            if (!Files.exists(outputFilePath)) {
                System.out.printf(FILE_MESSAGE_6,outputFile,outputFilePath);
                Files.createFile(outputFilePath);
                return validateOutputFile(outputFilePath);
            }
            if (!Files.isWritable(outputFilePath)) {
                System.out.println(FILE_MESSAGE_7);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}