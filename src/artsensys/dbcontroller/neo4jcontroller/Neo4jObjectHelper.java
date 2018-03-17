package artsensys.dbcontroller.neo4jcontroller;

import org.json.JSONArray;
import org.neo4j.driver.v1.Record;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nguyennghi on 2/13/1810:31 PM.
 */
public class Neo4jObjectHelper {
    private static Neo4JInteraction neo4JInteraction;

    static {
        neo4JInteraction = new
                Neo4JInteraction("bolt://localhost:7687", "neo4j", "123456");
    }

    public static boolean checkEntityAvailable(String entity) {
        String query = "match (n:ObjectEntity {objectEntity : \"" + entity + "\"}) return n";
        return neo4JInteraction.execute(query).size() > 0;
    }
    public static boolean checkEntityAvailableWithAdjective(String entity) {
        String query = "match (n:ObjectEntity {objectEntity : \"" + entity + "\"}) where (n)-[:LANG_POLY_MEANING]->(:ADJECTIVE) return n";
        return neo4JInteraction.execute(query).size() > 0;
    }

    public static ArrayList<PartOfSpeech> getLabels(String entity)
    {
        String query = "match (n:ObjectEntity { objectEntity : \"" + entity + "\"}) return labels(n)";
        List<Record> results = neo4JInteraction.execute(query);
        ArrayList<PartOfSpeech> returnVals = new ArrayList<>();
        try {

//            System.err.println(results.get(0).get(0).toString().replace('\"', ' ').trim());
            JSONArray array = new JSONArray(results.get(0).get(0).toString());
            for (Object anArray : array) {

                returnVals.add(PartOfSpeech.valueOf(anArray.toString().replace('\"', ' ').trim()));
            }
        }
        catch (Exception e)
        {
           // e.printStackTrace();
            return new ArrayList<>();
        }

        return returnVals;
    }

    public static ArrayList<PartOfSpeech> getAllPartOfSpeech(String entity) {
        String query;
        ArrayList<PartOfSpeech> returnVals = new ArrayList<>();
        ArrayList<PartOfSpeech> labels = getLabels(entity);

        query = "match (n:ObjectEntity {objectEntity: \"" + entity + "\" })-[:LANG_POLY_MEANING]->(v) with labels(v) as result return result";

        if (labels.contains(PartOfSpeech.PLURAL)) {
            query = "match (n:PLURAL {objectEntity:\""+entity+"\"})<-[:LANG_GRAMMAR_PLURAL]-(v)-[:LANG_POLY_MEANING]->(x) with labels(x) as result return result";
        }

        if (labels.contains(PartOfSpeech.COMPARATIVE_ADJECTIVE)) {
            returnVals.add(PartOfSpeech.COMPARATIVE_ADJECTIVE);
            return returnVals;
        }

        if (labels.contains(PartOfSpeech.SUPERLATIVE_ADJECTIVE)) {
            returnVals.add(PartOfSpeech.SUPERLATIVE_ADJECTIVE);
            return returnVals;
        }
        if (labels.contains(PartOfSpeech.PAST_PARTICIPLE_VERB)) {
            returnVals.add(PartOfSpeech.PAST_PARTICIPLE_VERB);
            return returnVals;
        }

        if(labels.contains(PartOfSpeech.PAST_SIMPLE_VERB))
        {
            returnVals.add(PartOfSpeech.PAST_SIMPLE_VERB);
            return returnVals;
        }
        if (labels.contains(PartOfSpeech.GERUND))
        {
            returnVals.add(PartOfSpeech.GERUND);
            return returnVals;
        }

        if (labels.contains(PartOfSpeech.REGULAR_VERB))
        {
            returnVals.add(PartOfSpeech.REGULAR_VERB);
            return returnVals;
        }


        List<Record> results = neo4JInteraction.execute(query);

        try {
            for (Record record : results) {
               // String json = record.get("result").toString();
                // System.out.println(json);
                JSONArray array = new JSONArray(record.get("result").toString());
                for (Object anArray : array) {
                    //    System.out.println(anArray.toString());
                    String val = anArray.toString().replace('\"', ' ').trim();
                    if (!returnVals.contains(PartOfSpeech.valueOf(val)))
                        returnVals.add(PartOfSpeech.valueOf(val));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        returnVals.remove(PartOfSpeech.ObjectEntity);
        return returnVals;

    }

    public static void close()
    {
        try {
            neo4JInteraction.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
