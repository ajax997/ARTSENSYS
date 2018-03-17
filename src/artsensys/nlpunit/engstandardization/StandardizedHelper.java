package artsensys.nlpunit.engstandardization;

import artsensys.dbcontroller.neo4jcontroller.Neo4jObjectHelper;
import artsensys.dbcontroller.neo4jcontroller.PartOfSpeech;

import java.util.ArrayList;

/**
 * Created by nguyennghi on 2/25/18 8:39 PM.
 */
public class StandardizedHelper {
    public static boolean endSentenceSign(String word) {
        return word.endsWith(".") || word.equals("?") || word.equals("!");
    }

    private static String engSentenceSigns = "!\"#$%&'()-=^~|@`[{;+:*]}_/?.>,<";

    public static String alignWord(String word) {

        if (engSentenceSigns.contains(word.substring(0, 1))) {
            word = word.substring(0, 1) + " " + word.substring(1, word.length());
        }
        if (engSentenceSigns.contains(word.substring(word.length() - 1, word.length()))) {
            word = word.substring(0, word.length() - 1) + " " + word.substring(word.length() - 1, word.length());
        }
        return word;
    }
    public static boolean isPunctuation(String entity)
    {
        if(entity.length() > 1)
            return false;
        else
        {

            return engSentenceSigns.contains(entity);
        }
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

    public static ArrayList<String> concat(String[]... arrs)
    {
        ArrayList<String> res = new ArrayList<>();
        for(String[] arr : arrs)
        {
            for(String literal : arr)
            {
                res.add(literal);
            }
        }
        return res;
    }

    public static PartOfSpeech determinatePOSBasedOnRules(ArrayList<ENG_Word> listWord, int position) {

        ENG_Word literal = listWord.get(position);
        ArrayList<PartOfSpeech> partOfSpeeches = Neo4jObjectHelper.getAllPartOfSpeech(literal.getWord());
        //NOUN
        String[] determiner = {"a", "an", "the", "this", "that", "these", "those"};
        String[] possessive_adjectives = {"my", "your", "his", "her", "its", "our", "your", "their"};
        String[] quantifiers = { "much", "many",  "most", "some", "any", "enough"};

        String[] quantifiers_multi = {"a few", "a little","a lot of"};


        String[] tobe = {"am", "is", "are", "was", "were", "be", "being", "been"};

        //after determiner (determiner, possessive_adjectives, quantifiers) is a noun;
        ArrayList<String> merged = concat(determiner, possessive_adjectives, quantifiers);
        if(position-1 >= 0 && merged.contains(listWord.get(position-1).getWord()) &&  partOfSpeeches.contains(PartOfSpeech.NOUN))
            return PartOfSpeech.NOUN;

        //after preposition is a noun.
        if(position-1 >= 0 && listWord.get(position-1).getPartOfSpeech() == PartOfSpeech.PREPOSITION && partOfSpeeches.contains(PartOfSpeech.NOUN))
            return PartOfSpeech.NOUN;
        //TODO


        //ADJECTIVE
        String[] sensingVerbs = {"look", "feel", "seem", "smell", "taste", "find", "sound"};
        String[] srb = {"stay", "remain", "become"};
        //??one more TODO another! http://www.tuhocanhvan.com/vi-tri-cua-cac-tu-loai-trong-tieng-anh/

        //after a sensing verb is an adjective
        if(position-1 >= 0 && concat(sensingVerbs).contains(listWord.get(position-1).getWord()) && partOfSpeeches.contains(PartOfSpeech.ADJECTIVE))
            return PartOfSpeech.ADJECTIVE;

        // adjective next to tobe
        if(position-1>=0 && concat(tobe).contains(listWord.get(position-1).getWord()) &&  partOfSpeeches.contains(PartOfSpeech.ADJECTIVE))
            return PartOfSpeech.ADJECTIVE;

        //after stay, remain, become is a adjective
        if(position-1 >= 0 && concat(srb).contains(listWord.get(position-1).getWord())&&  partOfSpeeches.contains(PartOfSpeech.ADJECTIVE))
            return PartOfSpeech.ADJECTIVE;


        //ADVERB
        //TODO

        //VERB
        String[] aoF = {"always", "usually", "normally","generally", "often","frequently", "sometimes",
                "occasionally", "hardly ever","rarely","seldom", "never"};

        //after adverbs of frequently is a verb (not tobe)
        if(position-1>=0&&concat(aoF).contains(listWord.get(position-1).getWord())&&partOfSpeeches.contains(PartOfSpeech.VERB))
            return PartOfSpeech.VERB;

        //before adverbs of frequently is a tobe verb.
        if(position+1<listWord.size() && concat(aoF).contains(listWord.get(position+1).getWord())&& concat(tobe).contains(literal.getWord()))
            return PartOfSpeech.VERB;

        return null;

    }
}
