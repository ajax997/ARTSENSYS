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


            System.err.println(results.get(0).get(0).toString().replace('\"', ' ').trim());
            JSONArray array = new JSONArray(results.get(0).get(0).toString());
            for (Object anArray : array) {

                returnVals.add(PartOfSpeech.valueOf(anArray.toString().replace('\"', ' ').trim()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

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
