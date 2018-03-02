package artsensys.nlpunit.engstandardization;

import java.util.ArrayList;

/**
 * Created by nguyennghi on 2/26/18 10:36 PM.
 */
public class ENG_SentenceStandardized{
    ArrayList<ENG_Word> listWord;

    public ENG_SentenceStandardized() {
        this.listWord = new ArrayList<>();
    }

    public void addWord(ENG_Word word)
    {
        listWord.add(word);
    }

    public ArrayList<ENG_Word> getListWord() {
        return listWord;
    }


}
