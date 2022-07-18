import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BruteforceDecoder extends CaesarConverter implements BruteForcable, Hackable {

    @Override
    public String decode(Path inputFile, Path outputFile, List<Character> alphabet) {
        try {
            String fileToString = Files.readString(inputFile, StandardCharsets.UTF_8);
            List<Integer> matches = new ArrayList<>();
            char[] encodedChars = fileToString.toLowerCase().toCharArray();
            char[] decodedChars = new char[encodedChars.length];

            for (int bruteKey = 0; bruteKey < alphabet.size(); bruteKey++) {
                for (int i = 0; i < encodedChars.length; i++) {
                    int charIndex = alphabet.indexOf(encodedChars[i]);
                    if (charIndex < 0) {
                        continue;
                    }
                    if (charIndex - bruteKey < 0) {
                        decodedChars[i] = alphabet.get(charIndex - bruteKey + alphabet.size());
                        continue;
                    }
                    decodedChars[i] = alphabet.get(charIndex - bruteKey);
                }
                String tempEncodedString = new String(decodedChars);
                int summOfMatches = countRegexMatches(REGEXPS, tempEncodedString);
                matches.add(summOfMatches);
            }

            byte bestKey = (byte) matches.indexOf(Collections.max(matches));
            System.out.println("выбран ключ " + bestKey);

            CaesarDecoder decoder = new CaesarDecoder();
            return decoder.decode(bestKey, inputFile, outputFile, alphabet);
        } catch (IOException e) {
            return e.toString();
        }
    }
}
