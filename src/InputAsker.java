import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputAsker {

    public byte askKey(int alphabetSize) {
        boolean validatorResult = false;
        byte key = 0;
        while (!validatorResult) {
            System.out.print("введите ключ: ");
            Scanner scanner = new Scanner(System.in);
            try {
                key = scanner.nextByte();
                InputValidator validator = new InputValidator(alphabetSize);
                validatorResult = validator.validateKey(key);
            } catch (InputMismatchException e){
                System.out.println("введен неверный ключ, ошибка "+ e);
                System.out.printf("ключ должен быть больше нуля и не больше %d(количество символов в алфавите)\n",alphabetSize);
                continue;
            }
        }
        return key;
    }

    public Path askInputFile() {
        boolean validatorResult = false;
        Path inputFile = Path.of("");
        while (!validatorResult) {
            System.out.print("введите файл: ");
            Scanner scanner = new Scanner(System.in);
            String inputFileString = scanner.next();
            inputFile = Path.of(inputFileString).toAbsolutePath();
            InputValidator validator = new InputValidator();
            validatorResult = validator.validateInputFile(inputFile);
        }
        return inputFile;
    }

    public Path askOutputFile() {
        boolean validatorResult = false;
        Path outputFile = Path.of("");
        while (!validatorResult) {
            System.out.print("введите файл: ");
            Scanner scanner = new Scanner(System.in);
            String outputFileString = scanner.next();
            outputFile = Path.of(outputFileString).toAbsolutePath();
            InputValidator validator = new InputValidator();
            validatorResult = validator.validateOutputFile(outputFile);
        }
        return outputFile;
    }

}