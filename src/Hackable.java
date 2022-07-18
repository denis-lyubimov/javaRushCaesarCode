public interface Hackable {
    String[] REGEXPS = new String[]{
            " и ", " и, ", "[а-яё], ", " или ", " или, ", " в ", "  если ", "  если, ", " не ", " на ", " я ",
            " я, ", " быть ", " быть, ", " он ", " он, ", " c ", " c, ", " что ", " что, ", " a, ", " а ", " по ",
            " это ", " она ", " этот ", " к ", " но ", " но, ", " они ", " мы ", " как ", " из ", " у ", " который ",
            " то ", " за ", " свой ", " весь ", " год ", " от ", " так ", " для ", " ты ", "из-за", " же ", " все ",
            " тот ", " вы ", " такой ", " его ", " тест ", " сам ", " когда ", " уже ", " время ", " нет ", "привет", "мир",
            " еще ", " чего-нибудь "
    };
    public default int countRegexMatches(String[] regexArray, String string){
        int summOfMatches = 0;
        for (int i = 0; i < regexArray.length; i++) {
            summOfMatches = summOfMatches + ((string.length() - string.replace(regexArray[i], "").length()) / regexArray[i].length());
        }
        return summOfMatches;
    }
}
