package artsensys.nlpunit.engstandardization;

import artsensys.dbcontroller.neo4jcontroller.PartOfSpeech;

/**
 * Created by nguyennghi on 3/1/18 8:32 PM.
 */
public class ENG_Word {
    private String word;
    private PartOfSpeech partOfSpeech;

    ENG_Word(String word, PartOfSpeech partOfSpeech)
    {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
    }
    ENG_Word(String word)
    {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public String toString() {
        return word;
    }
}
