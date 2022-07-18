import java.nio.file.Path;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Runner {
    private static final List<Character> ALPHABET = Arrays.asList('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' ');
    private static final String RESULT_MESSAGE = "Результат находится в файле %s\n";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 5) {

            System.out.println("Возможные действия:\n" +
                    "1. Шифровка текста\n" +
                    "2. Расшифровка текста с помощью ключа\n" +
                    "3. Расшифровка текста с помощью bruteforce\n" +
                    "4. Расшифровка с помощью статистического анализа текста\n" +
                    "5. Выход");
            System.out.print("выберите номер дальнейшего действия: ");

            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        System.out.println("выбран 1й вариант");
                        System.out.println("для шифрования тербуется");
                        InputAsker asker = new InputAsker();
                        System.out.println("ввести ключ шифрования");
                        byte key = asker.askKey(ALPHABET.size());
                        System.out.println("ввести файл, который надо зашифровать");
                        Path inputFile = asker.askInputFile();
                        System.out.println("ввксти файл, где будут хранится зашифрованные данные");
                        Path outputFile = asker.askOutputFile();

                        CaesarCoder encoder = new CaesarCoder();
                        encoder.encode(key, inputFile, outputFile, ALPHABET);

                        System.out.printf(RESULT_MESSAGE, outputFile.toString());
                    }
                    case 2 -> {
                        System.out.println("выбран 2й вариант");
                        System.out.println("для расшиврофки требуется");
                        InputAsker asker = new InputAsker();
                        System.out.println("ввести ключ шифрования");
                        byte key = asker.askKey(ALPHABET.size());
                        System.out.println("ввести файл, который надо рашифровать");
                        Path inputFile = asker.askInputFile();
                        System.out.println("ввести файл, куда будет выведен результат расшифровки");
                        Path outputFile = asker.askOutputFile();

                        CaesarDecoder decoder = new CaesarDecoder();
                        decoder.decode(key, inputFile, outputFile, ALPHABET);

                        System.out.printf(RESULT_MESSAGE, outputFile.toString());
                    }
                    case 3 -> {
                        System.out.println("выбран 3й вариант");
                        System.out.println("для расшиврофки требуется:");
                        InputAsker asker = new InputAsker();
                        System.out.println("ввести файл, который надо рашифровать");
                        Path inputFile = asker.askInputFile();
                        System.out.println("ввести файл, куда будет выведен результат расшифровки");
                        Path outputFile = asker.askOutputFile();


                        BruteforceDecoder decoder = new BruteforceDecoder();
                        decoder.decode(inputFile, outputFile, ALPHABET);

                        System.out.printf(RESULT_MESSAGE, outputFile.toString());
                    }
                    case 4 -> System.out.println("not implementeted yet");
//                            {
//                        System.out.println("выбран 4й вариант");
//                        System.out.println("для расшиврофки требуется");
//                        InputAsker asker = new InputAsker();
//                        System.out.println("ввести файл, с примером текста для сбора статистической информации");
//                        Path textExampleFile = asker.askInputFile();
//                        System.out.println("ввести файл, который надо рашифровать");
//                        Path inputFile = asker.askInputFile();
//                        System.out.println("ввести файл, куда будет выведен результат расшифровки");
//                        Path outputFile = asker.askOutputFile();
//
//
//                        StatisticalDecoder decoder = new StatisticalDecoder();
//                        decoder.decode(textExampleFile, inputFile, outputFile);
//
//                        System.out.printf(RESULT_MESSAGE, outputFile.toString());
//                    }
                    case 5 -> {
                        System.out.println("выбран 5й вариант");
                        System.out.println("выходим из программы");
                        System.exit(0);
                    }
                    default -> System.out.println("такого варианта нет, возможные варианты от 1го до 5ти");
                }
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("такого варианта нет, возможные варианты от 1го до 5ти");
            }
        }
    }
}