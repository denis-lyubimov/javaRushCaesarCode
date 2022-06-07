
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CoderEncoder {
    private static final List<Character> ALPHABET = Arrays.asList('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' ');
    private static final int ALPHABETSIZE = ALPHABET.size();
    private static final String[] REGEXPS = new String[]{
            " и ", " и, ", "[а-яё], ", " или ", " или, ", " в ", "  если ", "  если, ", " не ", " на ", " я ",
            " я, ", " быть ", " быть, ", " он ", " он, ", " c ", " c, ", " что ", " что, ", " a, ", " а ", " по ",
            " это ", " она ", " этот ", " к ", " но ", " но, ", " они ", " мы ", " как ", " из ", " у ", " который ",
            " то ", " за ", " свой ", " весь ", " год ", " от ", " так ", " для ", " ты ", "из-за", " же ", " все ",
            " тот ", " вы ", " такой ", " его ", " тест ", " сам ", " когда ", " уже ", " время ", " нет ", "привет", "мир",
            " еще ", " чего-нибудь "
    };


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
                    case 1:
                        System.out.println("шифруем файл");
                        System.out.println(encode(key(), inputFile(choice), outputFile()));
                        break;
                    case 2:
                        System.out.println(decodeByKey(key(),inputFile(choice),outputFile()));
                        break;
                    case 3:
                        System.out.println(decodeByBruteForce(inputFile(choice),outputFile()));
                        break;
                    case 4:
                        System.out.println("это еще не готово, выбери другое");
                        break;
                    case 5:
                        System.out.println("выходим из программы");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("такого варианта нет, возможные варианты от 1го до 5ти");
                        break;
                }
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("такого варианта нет, возможные варианты от 1го до 5ти");

            }
        }

    }

    public static int key() {
        Scanner scanner = new Scanner(System.in);
        int key ;
        System.out.printf("ключ может иметь значение от 1го до %d\n",ALPHABETSIZE - 1);
        while (true) {
            System.out.print("введите ключ шифрования: ");
            try {
                key = scanner.nextInt();
                if (key < 1 | key > (ALPHABETSIZE - 1) ) {
                    System.out.printf("ключ может иметь значение от 1го до %2$d, Вы ввели %1$d\n", key, ALPHABETSIZE - 1);
                    continue;
                }
                return key;
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.printf("ключ может иметь значение от 1го до %d\n",ALPHABETSIZE - 1);
            }
        }
    }

    public static Path inputFile(int action) {

        Path inputFilePath ;
        Scanner scanner = new Scanner(System.in);
        String message = action == 1? "введите файл, который надо зашифровать: " : "введите файл, который надо расшифровать: ";
        while (true) {
            try {
                System.out.print(message);
                String stringPath = scanner.nextLine();
                if (stringPath.isEmpty()) {
                    System.out.println("введите не пустую строку");
                    continue;
                }
                inputFilePath = Path.of(stringPath).toAbsolutePath();
                if (!Files.isReadable(inputFilePath)) {
                    System.out.printf("невозможно прочитать %s, проверьте верность имени файла и права на этот файл для текущего пользователя\n", inputFilePath);
                    continue;
                }
                if (Files.size(inputFilePath) == 0) {
                    System.out.printf("файл %s пустой, нужен файл с текстовым содержимым\n", inputFilePath);
                    continue;
                }
                return inputFilePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static Path outputFile() {
        Path outputFilePath ;
        Scanner scanner = new Scanner(System.in);
        System.out.print("введите файл, куда будет записан результат: ");
        while (true) {
            try {
                String stringPath = scanner.nextLine();
                if (stringPath.isEmpty()) {
                    System.out.println("введите не пустую строку");
                    continue;
                }
                outputFilePath = Path.of(stringPath).toAbsolutePath();
                if (!Files.exists(outputFilePath)) {
                    System.out.printf("файла %s не существует, создаем\n", outputFilePath);
                    Files.createFile(outputFilePath);
                }
                if (!Files.isWritable(outputFilePath)) {
                    System.out.println("нет рпав на запись в файл, измените права на запись");
                    System.exit(-3);
                }
                return outputFilePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static int countRegexMatches(String[] regexArray, String string) {
        int summOfMatches = 0;
        for (int i = 0; i < regexArray.length; i++) {
            summOfMatches = summOfMatches + ((string.length() - string.replace(regexArray[i], "").length()) / regexArray[i].length());
        }
        return summOfMatches;
    }

    public static String encode(int key, Path intpuFile, Path outputFile) {
        try {
            String fileToString = Files.readString(intpuFile, StandardCharsets.UTF_8);
            char[] rawChars = fileToString.toLowerCase().toCharArray();
            for (int i = 0; i < rawChars.length; i++) {
                int charIndex = ALPHABET.indexOf(rawChars[i]);
                if (charIndex < 0) {
                    continue;
                }
                if (charIndex + key >= ALPHABETSIZE) {
                    rawChars[i] = ALPHABET.get(charIndex + key - ALPHABETSIZE);
                    continue;
                }
                rawChars[i] = ALPHABET.get(charIndex + key);
            }
            fileToString = new String(rawChars);
            return "Записано в файл " + Files.write(outputFile, fileToString.getBytes(StandardCharsets.UTF_8)).toAbsolutePath();
        } catch (IOException e) {
            return e.toString();
        }
    }

    public static String decodeByKey(int key, Path intpuFile, Path outputFile) {
        try {
            String fileToString = Files.readString(intpuFile, StandardCharsets.UTF_8);
            char[] encodedChars = fileToString.toLowerCase().toCharArray();
            for (int i = 0; i < encodedChars.length; i++) {
                int charIndex = ALPHABET.indexOf(encodedChars[i]);
                if (charIndex < 0) {
                    continue;
                }
                if (charIndex - key < 0) {
                    encodedChars[i] = ALPHABET.get(charIndex - key + ALPHABETSIZE);
                    continue;
                }
                encodedChars[i] = ALPHABET.get(charIndex - key);
            }
            fileToString = new String(encodedChars);
            return Files.write(outputFile, fileToString.getBytes(StandardCharsets.UTF_8)).toAbsolutePath().toString();
        } catch (IOException e) {
            return e.toString();
        }
    }

    public static String decodeByBruteForce(Path intpuFile, Path outputFile) {
        try {
            String fileToString = Files.readString(intpuFile, StandardCharsets.UTF_8);
            List<Integer> matches = new ArrayList<>();
            char[] encodedChars = fileToString.toLowerCase().toCharArray();
            char[] decodedChars = new char[encodedChars.length];

            for (int bruteKey = 0; bruteKey < ALPHABETSIZE; bruteKey++) {
                for (int i = 0; i < encodedChars.length; i++) {
                    int charIndex = ALPHABET.indexOf(encodedChars[i]);
                    if (charIndex < 0) {
                        continue;
                    }
                    if (charIndex - bruteKey < 0) {
                        decodedChars[i] = ALPHABET.get(charIndex - bruteKey + ALPHABETSIZE);
                        continue;
                    }
                    decodedChars[i] = ALPHABET.get(charIndex - bruteKey);
                }
                String tempEncodedString = new String(decodedChars);
                int summOfMatches = countRegexMatches(REGEXPS, tempEncodedString);
                matches.add(summOfMatches);
            }

            int bestKey = matches.indexOf(Collections.max(matches));
            return "результат буртфорса пишем по ключу = " + bestKey + " в файл " + decodeByKey(bestKey, intpuFile, outputFile);
        } catch (IOException e) {
            return e.toString();
        }
    }
}
