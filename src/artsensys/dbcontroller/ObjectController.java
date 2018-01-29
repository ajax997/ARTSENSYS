package artsensys.dbcontroller;

import artsensys.dbcontroller.mongocontroller.MongoInteractive;
import artsensys.dbcontroller.neo4jcontroller.Neo4JInteraction;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.print.Doc;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by nguyennghi on 12/11/1712:43 PM.
 */
public class ObjectController {
    MongoInteractive mongoInteractive = new MongoInteractive("artsensys_core_kb", "objectEntities");
    Neo4JInteraction neo4JInteraction = new Neo4JInteraction("bolt://localhost:7687", "neo4j", "123456" );



    public void start() throws Exception
    {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File("listfile.txt")));
        Scanner scanner = new Scanner(inputStreamReader);
        while (scanner.hasNextLine()) {
            String pathFile = scanner.nextLine().trim();
            File file = new File(pathFile);
            String fileName = file.getName();

            if (fileName.startsWith("_") && fileName.endsWith(".json")) {
                String entity = fileName.substring(1, fileName.length() - 5);
                wordWithDash(entity, new JSONObject(readContent(file)));

            } else {
                if (fileName.endsWith(".json")) {
                    String entity = fileName.substring(0, fileName.length() - 5);
                    wordWithoutDash(entity, new JSONObject(readContent(file)));
                }
            }

        }
        neo4JInteraction.close();

    }

    private void wordWithDash(String name, JSONObject jsonObject) throws JSONException {
        System.out.println("[DASH]: " + name + "; Processing -->");

        ArrayList<String> stringKeys = new ArrayList<>();

        Iterator keys = jsonObject.keys();
        while (keys.hasNext())
        {
            stringKeys.add(keys.next().toString());
        }
        System.out.println("keys: " + stringKeys.size());

        JSONObject root = new JSONObject();
        root.put("objectEntity", name);
        root.put("length", name.length());
        root.put("language", "english");
        if(name.contains(" ") || name.contains("-"))
            root.put("word",false);
        else
            root.put("word", true);

        if(stringKeys.contains("syllables"))
        {
            JSONObject object = jsonObject.getJSONObject("syllables");
            JSONArray array = object.getJSONArray("list");
            root.put("syllables", array);
        }
        if(stringKeys.contains("frequency"))
        {
            Object o = jsonObject.get("frequency");
            if(o instanceof JSONObject) {
                JSONObject object = (JSONObject) o;
                root.put("frequency", object.get("zipf"));
            }
            else
                root.put("frequency", jsonObject.get("frequency"));
        }

        System.out.println(root.toString());

        neo4JInteraction.execute("create (n:ObjectEntity) set n ="+ ObjectController.standardize(root.toString()));

        if(stringKeys.contains("definitions"))
        {
            JSONArray array = jsonObject.getJSONArray("definitions");

            for (Object o : array) {
                if (o instanceof JSONObject) {
                    createNode((JSONObject) o, name);
                }
            }
        }


    }

    private void wordWithoutDash(String name, JSONObject jsonObject)
    {
        System.out.println("[WITHOUT_DASH]: " + name+ "; Processing -->");
        ArrayList<String> stringKeys = new ArrayList<>();

        Iterator keys = jsonObject.keys();
        while (keys.hasNext())
        {
            stringKeys.add(keys.next().toString());
        }

        System.out.println("keys: " + stringKeys.size());
        JSONObject root = new JSONObject();
        root.put("objectEntity", name);
        root.put("length", name.length());
        root.put("language", "english");
        if(name.contains(" ") || name.contains("-"))
            root.put("word",false);
        else
            root.put("word", true);

        if(stringKeys.contains("syllables"))
        {
            JSONObject object = jsonObject.getJSONObject("syllables");
            JSONArray array = object.getJSONArray("list");
            root.put("syllables", array);
        }
        if(stringKeys.contains("frequency"))
        {
            root.put("frequency",jsonObject.get("frequency"));
        }

        neo4JInteraction.execute("create (n:ObjectEntity) set n ="+ ObjectController.standardize(root.toString()));

        if(stringKeys.contains("results"))
        {
            JSONArray array = jsonObject.getJSONArray("results");

            for (Object o : array) {
                if (o instanceof JSONObject) {

                    createNode((JSONObject) o, name);

                }
            }
        }

        System.out.println(root.toString());

        neo4JInteraction.execute("create (n:ObjectEntity) set n ="+ ObjectController.standardize(root.toString()));


    }

    public void createNode(JSONObject o, String name)
    {
        //Mongo
        Document document = Document.parse(o.toString());
        document.put("objectEntity", name);
        mongoInteractive.addNewDocument(document);
        String partOfSpeech = ((JSONObject) o).get("partOfSpeech").toString();
        String id = document.get("_id").toString();


        //NEO4J
        String query;
        if(partOfSpeech!=null && !partOfSpeech.equals("null")) {
            JSONObject object = new JSONObject();
            object.put("objectEntity", name).put("_id", id);
            query = "CREATE (N:"+partOfSpeech.toUpperCase()+")" + "SET N = "+
                    ObjectController.standardize(object.toString());
            neo4JInteraction.execute(query);
        }
        else
        {
            JSONObject object = new JSONObject();
            object.put("objectEntity", name).put("_id", id);
            query = "CREATE (N:NOT_KNOW_POS)" + "SET N = "+
                    ObjectController.standardize(object.toString());
            neo4JInteraction.execute(query);
        }
        String connectQuery = "match (a:ObjectEntity { objectEntity:" +"\""+name+"\"}),"+"(b{ objectEntity: "+"\""+name+"\"})" +
                " where labels(a)[0]<>labels(b)[0] create (a) -[:LANG_POLY_MEANING]->(b)";
        System.out.println(connectQuery);
        neo4JInteraction.execute(connectQuery);

        String removeDuplicate = "match (s)-[r]->(e)\n" +
                "with s,e,type(r) as typ, tail(collect(r)) as coll \n" +
                "foreach(x in coll | delete x)";
        neo4JInteraction.execute(removeDuplicate);
    }


    private  String readContent(File file) {
        String res = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            res = new String(data, "UTF-8");
        } catch (Exception ignored) {
        }
        return res;

    }
    public static String standardize(String str)
    {
        char[] newStr = new char[str.length()];
        int current = 0;
        for (int i = 0; i<str.length(); i++)
        {
            newStr[i] = str.charAt(i);

            if(str.charAt(i) == '\"'&&str.charAt(i+1)!=':')
            {
                current = i;
            }
            if(str.charAt(i) == ':')
            {
                newStr[i-1] = ' ';
                newStr[current] = ' ';
                current = i+1;
            }
        }
        return String.valueOf(newStr);
    }

}
