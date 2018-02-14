package artsensys.dbcontroller;

import artsensys.dbcontroller.mongocontroller.MongoInteractive;
import artsensys.dbcontroller.neo4jcontroller.Neo4JInteraction;
import artsensys.dbcontroller.neo4jcontroller.QueryBuilder;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nguyennghi on 12/11/1712:43 PM.
 */
public class ObjectController implements AutoCloseable{
    private MongoInteractive mongoInteractive =
            new MongoInteractive("artsensys_core_kb", "objectEntities");
    private Neo4JInteraction neo4JInteraction = new
            Neo4JInteraction("bolt://localhost:7687", "neo4j", "123456" );
   // FileWriter fileWriter = new FileWriter(new File("done.txt"));
   // FileWriter fileWriter2 = new FileWriter(new File("nodef1.txt"));

    public ObjectController() throws IOException {

    }
    public void start(int lines) throws Exception
    {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File("listfile.txt")));
        Scanner scanner = new Scanner(inputStreamReader);
        int i = 0;
        while (scanner.hasNextLine()) {
            i++;

                if (i % 100 == 0) {
                    System.out.println(i + "/" + (int) (double) lines + " - " + String.valueOf((i / (double) lines)*100).substring(0,5) + "%" + " - at " + i);
                }
                String pathFile = scanner.nextLine().trim();
               // fileWriter.append(pathFile).append("\r\n");

                File file = new File(pathFile);
                String fileName = file.getName();

                if (fileName.startsWith("_") && fileName.endsWith(".json")) {
                    String entity = fileName.substring(1, fileName.length() - 5);
                    wordWithDash(pathFile,entity, new JSONObject(readContent(file)));

                } else {
                    if (fileName.endsWith(".json")) {
                        String entity = fileName.substring(0, fileName.length() - 5);
                        wordWithoutDash(pathFile,entity, new JSONObject(readContent(file)));
                    }
                }
        }
    }

    public void test()
    {
        String query = "match(n) return n._id, labels(n) limit 100";
       List<Record> res =  neo4JInteraction.execute(query);
           Record record = res.get(0);
           JSONObject object = new JSONObject(record.asMap());
           System.out.println(object.toString());

    }

    private void wordWithDash(String path,String name, JSONObject jsonObject) throws JSONException, IOException {
       // System.out.println("[DASH]: " + name + "; Processing -->");

        ArrayList<String> stringKeys = new ArrayList<>();

        Iterator keys = jsonObject.keys();
        while (keys.hasNext())
        {
            stringKeys.add(keys.next().toString());
        }
       // System.out.println("keys: " + stringKeys.size());


        JSONObject root = new JSONObject();
        root.put("objectEntity", name);
        root.put("length", name.length());
        root.put("language", "english");
        if(name.contains(" ") || name.contains("-"))
            root.put("word",false);
        else
            root.put("word", true);

        signalNode(jsonObject, stringKeys, root);
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

        if(!stringKeys.contains("definitions"))
        {
            String singleNodeQuery = "CREATE (n "+ObjectController.standardize(root.toString())+")";
            neo4JInteraction.execute(singleNodeQuery);
            return;
        }

        QueryBuilder queryBuilder = new QueryBuilder(name);
        queryBuilder.setRootNode(ObjectController.standardize(root.toString()));
       // System.out.println(root.toString());

        //neo4JInteraction.execute("create (n:ObjectEntity) set n ="+ ObjectController.standardize(root.toString()));

        if(stringKeys.contains("definitions"))
        {
            JSONArray array = jsonObject.getJSONArray("definitions");

            for (Object o : array) {
                if (o instanceof JSONObject) {
                    createNode((JSONObject) o, name, queryBuilder);
                }
            }
        }

        neo4JInteraction.execute(queryBuilder.toString());


    }

    private void signalNode(JSONObject jsonObject, ArrayList<String> stringKeys, JSONObject root) {
        if(stringKeys.contains("syllables"))
        {
            JSONObject object = jsonObject.getJSONObject("syllables");
            JSONArray array = object.getJSONArray("list");
            root.put("syllables", array);
            root.put("syllablesCount", object.get("count"));
        }
    }

    private void wordWithoutDash(String path,String name, JSONObject jsonObject) throws IOException {
        //System.out.println("[WITHOUT_DASH]: " + name+ "; Processing -->");
        ArrayList<String> stringKeys = new ArrayList<>();

        Iterator keys = jsonObject.keys();
        while (keys.hasNext())
        {
            stringKeys.add(keys.next().toString());
        }



       // System.out.println("keys: " + stringKeys.size());
        JSONObject root = new JSONObject();
        root.put("objectEntity", name);
        root.put("length", name.length());
        root.put("language", "english");
        if(name.contains(" ") || name.contains("-"))
            root.put("word",false);
        else
            root.put("word", true);


        signalNode(jsonObject, stringKeys, root);
        if(stringKeys.contains("frequency"))
        {
            root.put("frequency",jsonObject.get("frequency"));
        }


        if(!stringKeys.contains("results"))
        {
            String singleNodeQuery = "CREATE (n "+ObjectController.standardize(root.toString())+")";
            neo4JInteraction.execute(singleNodeQuery);
            return;
        }
       // neo4JInteraction.execute("create (n:ObjectEntity) set n ="+ ObjectController.standardize(root.toString()));
        QueryBuilder queryBuilder = new QueryBuilder(name);
        queryBuilder.setRootNode(ObjectController.standardize(root.toString()));

        if(stringKeys.contains("results"))
        {
            JSONArray array = jsonObject.getJSONArray("results");

            for (Object o : array) {
                if (o instanceof JSONObject) {

                    createNode((JSONObject) o, name, queryBuilder);

                }
            }
        }

        //System.out.println(root.toString());
        neo4JInteraction.execute(queryBuilder.toString());


    }

    public void createNode(JSONObject o, String name, QueryBuilder queryBuilder)
    {
        //Mongo
        Document document = Document.parse(o.toString());
        document.put("objectEntity", name);
        mongoInteractive.addNewDocument(document);
        String partOfSpeech = o.get("partOfSpeech").toString();
        String id = document.get("_id").toString();


        //NEO4J
        String query;
        if(partOfSpeech!=null && !partOfSpeech.equals("null")) {
            JSONObject object = new JSONObject();
            object.put("objectEntity", name).put("_id", id);
          //  query = "CREATE (N:"+partOfSpeech.toUpperCase()+")" + "SET N = "+
            //        ObjectController.standardize(object.toString());
            queryBuilder.addElement(partOfSpeech.toUpperCase(), ObjectController.standardize(object.toString()));
            //neo4JInteraction.execute(query);
        }
        else
        {
            JSONObject object = new JSONObject();
            object.put("objectEntity", name).put("_id", id);

            queryBuilder.addElement("NOT_KNOW_POS", ObjectController.standardize(object.toString()));

        }
//

//        String queries = queryBuilder.toString();
//        System.out.println(queries);
//       // neo4JInteraction.execute(queries);
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
            ignored.printStackTrace();
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

    @Override
    public void close() throws Exception {


    }
}
