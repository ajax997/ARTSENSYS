package artsensys.nlpunit.engstandardization;

/**
 * Created by nguyennghi on 2/25/18 8:39 PM.
 */
public class StandardizerHelper {
    public static boolean endSentenceSign(String word)
    {
        return word.endsWith(".")||word.endsWith("?")||word.endsWith("!");
    }
}
