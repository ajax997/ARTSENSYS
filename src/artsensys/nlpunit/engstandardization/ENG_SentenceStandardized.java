package artsensys.nlpunit.engstandardization;

import artsensys.dbcontroller.neo4jcontroller.Neo4jObjectHelper;
import artsensys.dbcontroller.neo4jcontroller.PartOfSpeech;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by nguyennghi on 2/26/18 10:36 PM.
 */
public class ENG_SentenceStandardized {
    private ArrayList<ENG_Word> listWord;
    private boolean hasBeenStandardized = false;

    ENG_SentenceStandardized() {
        this.listWord = new ArrayList<>();
    }

    public void addWord(ENG_Word word) {
        listWord.add(word);
    }

    public ArrayList<ENG_Word> getListWord() {
        return listWord;
    }

    public void startStandardized() {
        if (hasBeenStandardized) {
            System.err.println("this sentence already standardized!");
        } else {

            //Step 1: assign the POS for word has 1 POS.
            for(ENG_Word word : listWord)
            {
                if (!word.isAssignedPOS()) {
                    ArrayList<PartOfSpeech> partOfSpeeches = Neo4jObjectHelper.getAllPartOfSpeech(word.getWord());
                    if (partOfSpeeches.size() == 1) {
                     //   System.out.println(word.getWord() + " - " + partOfSpeeches.get(0));
                        word.setPartOfSpeech(partOfSpeeches.get(0));
                    }
                }
                else {
                 //   System.out.println(word.getWord() + " - " + PartOfSpeech.PUNCTUATION);
                }


            }
            //Step 2: assign POS to verbs has more than 1 POS based on the position of them in the sentence.
            for(int i = 0; i< listWord.size(); i++)
            {
                ENG_Word word = listWord.get(i);
                if (!word.isAssignedPOS())
                    word.setPartOfSpeech(StandardizedHelper.determinatePOSBasedOnRules(listWord, i));
            }



            //TODO
            hasBeenStandardized = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(ENG_Word literal: listWord)
        {
            if(!literal.isAssignedPOS())
                builder.append(literal.getWord()).append(" ");
            else {
                if (literal.isAssignedPOS() && literal.getPartOfSpeech() == PartOfSpeech.PUNCTUATION)
                    builder.append("[PUNCTUATION]").append(" ");
                else {
                    builder.append('[').append(literal.getPartOfSpeech()).append(']').append(literal.getWord()).append(" ");
                }
            }
        }


        return builder.toString();
    }
}
