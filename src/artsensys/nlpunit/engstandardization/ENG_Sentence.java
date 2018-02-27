package artsensys.nlpunit.engstandardization;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by nguyennghi on 2/26/18 4:39 PM.
 */
public class ENG_Sentence {
    ArrayList<String> listWords;

    public ENG_Sentence(ArrayList<String> listWords)
    {
        this.listWords = listWords;
    }
    ENG_Sentence()
    {
        listWords = new ArrayList<>();
    }

    public void addLiteral (String word)
    {
        listWords.add(word);
    }

    public ArrayList<String> toArrayList() {
        return listWords;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(String w: listWords)
        {
            builder.append(w +" ");
        }
        return builder.toString();
    }
}
