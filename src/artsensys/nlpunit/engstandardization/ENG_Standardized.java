package artsensys.nlpunit.engstandardization;

import artsensys.dbcontroller.ObjectController;
import artsensys.dbcontroller.mongocontroller.MongoInteractive;
import artsensys.dbcontroller.neo4jcontroller.Neo4JInteraction;
import artsensys.dbcontroller.neo4jcontroller.Neo4jObjectHelper;
import artsensys.dbcontroller.neo4jcontroller.PartOfSpeech;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by nguyennghi on 2/25/18 6:30 PM.
 */
public class ENG_Standardized {

    private String datetime;
    private FormalLevel formalLevel;
    private String situation;
    private MongoInteractive mongoInteractive = ObjectController.getMongoInstance();
    private Neo4JInteraction neo4JInteraction = ObjectController.getNeo4jInstance();

    ENG_Standardized(String datetime, FormalLevel formalLevel, String situation)
    {
        this.datetime = datetime;
        this.formalLevel = formalLevel;
        this.situation = situation;
    }

    private ArrayList<ENG_Sentence> listSentences = new ArrayList<>();
    private ArrayList<String> listWords = new ArrayList<>();

    private ENG_Sentence sentence;
    public String parse(String input)
    {

        StringTokenizer tokenizer  = new StringTokenizer(input.replace("\n", " "), " ");
        StringBuilder stringBuilder = new StringBuilder();
        while (tokenizer.hasMoreTokens())
        {
            String token = tokenizer.nextToken();
            stringBuilder.append(StandardizedHelper.alignWord(token)+" ");
        }

        StringTokenizer tokenizerPost  = new StringTokenizer(stringBuilder.toString(), " ");
        while (tokenizerPost.hasMoreTokens())
            listWords.add(tokenizerPost.nextToken());


        boolean newSentence = true;
        for(int i = 0; i< listWords.size(); i++)
        {
            String word = listWords.get(i);
            if(newSentence)
            {
                sentence = new ENG_Sentence();
                listSentences.add(sentence);
                sentence.addLiteral(word);
                newSentence = false;

                if(StandardizedHelper.endSentenceSign(word)
                        && i<listWords.size()-1
                        && Character.isUpperCase(listWords.get(i+1).substring(0,1).toCharArray()[0])) {
                    newSentence = true;
                }
            }
            else {
                sentence.addLiteral(word);
                if(StandardizedHelper.endSentenceSign(word)
                        && i<listWords.size()-1
                        && Character.isUpperCase(listWords.get(i+1).substring(0,1).toCharArray()[0])) {
                    newSentence = true;
                }
            }
        }


        System.out.printf("there are %d sentences.\n", listSentences.size());
        for (ENG_Sentence s: listSentences) {
           standardizeSingleSentence(s);
        }
        return "";
    }

    private ENG_SentenceStandardized standardizeSingleSentence(ENG_Sentence sentence)
    {
        ENG_SentenceStandardized sentenceStandardized = new ENG_SentenceStandardized();
        for(String word: sentence.listWords)
        {
            if(StandardizedHelper.isPunctuation(word))
            {sentenceStandardized.addWord(new ENG_Word(word, PartOfSpeech.PUNCTUATION));}
            else
            sentenceStandardized.addWord(new ENG_Word(word));
        }

        sentenceStandardized.startStandardized();
        System.out.println(sentenceStandardized);

        //TODO
        return new ENG_SentenceStandardized();
    }


}
