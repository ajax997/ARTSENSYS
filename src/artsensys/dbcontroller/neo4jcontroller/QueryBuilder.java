package artsensys.dbcontroller.neo4jcontroller;

import artsensys.dbcontroller.ObjectController;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nguyennghi on 1/31/18 9:40 PM.
 */
public class QueryBuilder {
    private String query;
    ArrayList<String> characters = new ArrayList<>();
    int cur = 0;
    String name;
    public QueryBuilder(String name)
    {
        this.name = name;
        query = "CREATE ("+createName(cur)+":ObjectEntity "  ;
        characters.add(createName(cur));
        cur++;
    }
    public void setRootNode(String elements)
    {
        query += elements + ") ";
    }

    public void addElement(String pos,String id) {
        query += "create (" + createName(cur) + ":" + pos + id + ") ";
        characters.add(createName(cur));
        cur++;
    }

    @Override
    public String toString() {
        for(int i = 1; i<characters.size(); i++)
        {
            query+= "create ("+characters.get(0)+")-[:LANG_POLY_MEANING]->("+characters.get(i)+") ";
        }
        return query;
    }
    private String createName(int i)
    {
        return "A"+String.valueOf(i);
    }
}
