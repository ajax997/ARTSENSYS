package artsensys.nlpunit.engstandardization;

/**
 * Created by nguyennghi on 2/25/18 8:39 PM.
 */
public class StandardizedHelper {
    public static boolean endSentenceSign(String word) {
        return word.endsWith(".") || word.equals("?") || word.equals("!");
    }

    static String engSentenceSigns = "!\"#$%&'()-=^~|@`[{;+:*]}_/?.>,<";

    public static String alignWord(String word) {

        if (engSentenceSigns.contains(word.substring(0, 1))) {
            word = word.substring(0, 1) + " " + word.substring(1, word.length());
        }
        if (engSentenceSigns.contains(word.substring(word.length() - 1, word.length()))) {
            word = word.substring(0, word.length() - 1) + " " + word.substring(word.length() - 1, word.length());
        }
        return word;
    }

    public static boolean wordUpperCase(String literal)
    {
        for(Character c:literal.toCharArray())
        {
            if(Character.isUpperCase(c))
                return true;
        }
        return false;
    }
}
