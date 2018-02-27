package artsensys.dbcontroller;

import artsensys.dbcontroller.mongocontroller.MongoInteractive;
import artsensys.dbcontroller.neo4jcontroller.Neo4JInteraction;
import artsensys.dbcontroller.neo4jcontroller.Neo4jObjectHelper;
import artsensys.dbcontroller.neo4jcontroller.Neo4jQueryConnectionType;
import artsensys.dbcontroller.neo4jcontroller.QueryBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.Record;

import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nguyennghi on 12/11/17 12:43 PM.
 */
public class ObjectController implements AutoCloseable {
    private static MongoInteractive mongoInteractive =
            new MongoInteractive("artsensys_core_kb", "objectEntities");
    private static Neo4JInteraction neo4JInteraction = new
            Neo4JInteraction("bolt://localhost:7687", "neo4j", "123456");
    // FileWriter fileWriter = new FileWriter(new File("done.txt"));
    // FileWriter fileWriter2 = new FileWriter(new File("nodef1.txt"));



    public ObjectController() throws IOException {
    }

    public static Neo4JInteraction getNeo4jInstance()
    {
        return neo4JInteraction;
    }
    public static MongoInteractive getMongoInstance()
    {
        return mongoInteractive;
    }
    public void start(int lines) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File("listfile.txt")));
        Scanner scanner = new Scanner(inputStreamReader);
        int i = 0;
        while (scanner.hasNextLine()) {
            i++;

            if (i % 100 == 0) {
                System.out.println(i + "/" + (int) (double) lines + " - " + String.valueOf((i / (double) lines) * 100).substring(0, 5) + "%" + " - at " + i);
            }
            String pathFile = scanner.nextLine().trim();
            // fileWriter.append(pathFile).append("\r\n");

            File file = new File(pathFile);
            String fileName = file.getName();

            if (fileName.startsWith("_") && fileName.endsWith(".json")) {
                String entity = fileName.substring(1, fileName.length() - 5);
                wordWithDash(pathFile, entity, new JSONObject(readContent(file)));

            } else {
                if (fileName.endsWith(".json")) {
                    String entity = fileName.substring(0, fileName.length() - 5);
                    wordWithoutDash(pathFile, entity, new JSONObject(readContent(file)));
                }
            }
        }
    }


    public void test() {
        String query = "match(n) return n._id, labels(n) limit 100";
        List<Record> res = neo4JInteraction.execute(query);
        Record record = res.get(0);
        JSONObject object = new JSONObject(record.asMap());
        System.out.println(object.toString());

    }

    private void wordWithDash(String path, String name, JSONObject jsonObject) throws JSONException, IOException {
        // System.out.println("[DASH]: " + name + "; Processing -->");

        ArrayList<String> stringKeys = new ArrayList<>();

        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            stringKeys.add(keys.next().toString());
        }
        // System.out.println("keys: " + stringKeys.size());


        JSONObject root = new JSONObject();
        root.put("objectEntity", name);
        root.put("length", name.length());
        root.put("language", "english");
        if (name.contains(" ") || name.contains("-"))
            root.put("word", false);
        else
            root.put("word", true);

        signalNode(jsonObject, stringKeys, root);
        if (stringKeys.contains("frequency")) {
            Object o = jsonObject.get("frequency");
            if (o instanceof JSONObject) {
                JSONObject object = (JSONObject) o;
                root.put("frequency", object.get("zipf"));
            } else
                root.put("frequency", jsonObject.get("frequency"));
        }

        if (!stringKeys.contains("definitions")) {
            String singleNodeQuery = "CREATE (n " + ObjectController.standardize(root.toString()) + ")";
            neo4JInteraction.execute(singleNodeQuery);
            return;
        }

        QueryBuilder queryBuilder = new QueryBuilder(name);
        queryBuilder.setRootNode(ObjectController.standardize(root.toString()));
        // System.out.println(root.toString());

        //neo4JInteraction.execute("create (n:ObjectEntity) set n ="+ ObjectController.standardize(root.toString()));

        if (stringKeys.contains("definitions")) {
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
        if (stringKeys.contains("syllables")) {
            JSONObject object = jsonObject.getJSONObject("syllables");
            JSONArray array = object.getJSONArray("list");
            root.put("syllables", array);
            root.put("syllablesCount", object.get("count"));
        }
    }

    private void wordWithoutDash(String path, String name, JSONObject jsonObject) throws IOException {
        //System.out.println("[WITHOUT_DASH]: " + name+ "; Processing -->");
        ArrayList<String> stringKeys = new ArrayList<>();

        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            stringKeys.add(keys.next().toString());
        }


        // System.out.println("keys: " + stringKeys.size());
        JSONObject root = new JSONObject();
        root.put("objectEntity", name);
        root.put("length", name.length());
        root.put("language", "english");
        if (name.contains(" ") || name.contains("-"))
            root.put("word", false);
        else
            root.put("word", true);


        signalNode(jsonObject, stringKeys, root);
        if (stringKeys.contains("frequency")) {
            root.put("frequency", jsonObject.get("frequency"));
        }


        if (!stringKeys.contains("results")) {
            String singleNodeQuery = "CREATE (n " + ObjectController.standardize(root.toString()) + ")";
            neo4JInteraction.execute(singleNodeQuery);
            return;
        }
        // neo4JInteraction.execute("create (n:ObjectEntity) set n ="+ ObjectController.standardize(root.toString()));
        QueryBuilder queryBuilder = new QueryBuilder(name);
        queryBuilder.setRootNode(ObjectController.standardize(root.toString()));

        if (stringKeys.contains("results")) {
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

    public void createNode(JSONObject o, String name, QueryBuilder queryBuilder) {
        //Mongo
        Document document = Document.parse(o.toString());
        document.put("objectEntity", name);
        mongoInteractive.addNewDocument(document);
        String partOfSpeech = o.get("partOfSpeech").toString();
        String id = document.get("_id").toString();


        //NEO4J
        String query;
        if (partOfSpeech != null && !partOfSpeech.equals("null")) {
            JSONObject object = new JSONObject();
            object.put("objectEntity", name).put("_id", id);
            //  query = "CREATE (N:"+partOfSpeech.toUpperCase()+")" + "SET N = "+
            //        ObjectController.standardize(object.toString());
            queryBuilder.addElement(partOfSpeech.toUpperCase(), ObjectController.standardize(object.toString()));
            //neo4JInteraction.execute(query);
        } else {
            JSONObject object = new JSONObject();
            object.put("objectEntity", name).put("_id", id);

            queryBuilder.addElement("NOT_KNOW_POS", ObjectController.standardize(object.toString()));

        }
//

//        String queries = queryBuilder.toString();
//        System.out.println(queries);
//       // neo4JInteraction.execute(queries);
    }


    private String readContent(File file) {

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

    public static String standardize(String str) {
        char[] newStr = new char[str.length()];
        int current = 0;
        for (int i = 0; i < str.length(); i++) {
            newStr[i] = str.charAt(i);

            if (str.charAt(i) == '\"' && str.charAt(i + 1) != ':') {
                current = i;
            }
            if (str.charAt(i) == ':') {
                newStr[i - 1] = ' ';
                newStr[current] = ' ';
                current = i + 1;
            }
        }
        return String.valueOf(newStr);
    }

    @Override
    public void close() throws Exception {


    }

    public void deleteDuplicateDocument() throws IOException {
        FileWriter file = new FileWriter("properties.txt");
        MongoCursor<Document> cursor = mongoInteractive.find(new BasicDBObject());
        ArrayList<String> mongo = new ArrayList<>();
        ArrayList<String> neo4j = new ArrayList<>();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            ObjectId id = (ObjectId) document.get("_id");
            //file.append(id.toString()+"\r\n");
            mongo.add(id.toString());
        }

        String query = "match (n) where not n:ObjectEntity return n._id";
        List<Record> list = neo4JInteraction.execute(query);
        for (Record re : list) {
            String id = re.get(0).toString().replace('\"', ' ').trim();
            neo4j.add(id);
        }
        mongo.removeAll(neo4j);
        for (String dup : mongo) {
            file.append(dup + "\r\n");
        }

        file.close();

//        FileInputStream fis = new FileInputStream("duplicate.txt");
//        Scanner scanner = new Scanner(fis);
//        while (scanner.hasNextLine())
//        {
//            String line = scanner.nextLine();
//            BasicDBObject query = new BasicDBObject();
//            query.append("_id",new ObjectId(line));
//            if(mongoInteractive.deleteDocument(query).getDeletedCount()==1)
//            {
//                System.out.println("Successful delete document with id: " + line);
//            }
//        }
    }

    public void linkingWords() throws IOException {
        FileWriter file = new FileWriter("not_found_related_map2.txt");
        MongoCursor<Document> cursor = mongoInteractive.find(new BasicDBObject());
        ArrayList<String> props = new ArrayList<>();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            ObjectId docID = (ObjectId) document.get("_id");
            String id = docID.toString();
            JSONObject jsonObject = new JSONObject(document.toJson());
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();
//                if(!props.contains(key))
//                {
//                    props.add(key);
//
                switch (key) {
                    case "synonyms":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_SYNONYM);
                        break;
                    case "typeOf":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_TYPE_OF);
                        break;
                    case "derivation":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_DERIVATION);
                        break;
                    case "similarTo":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_SIMILAR_TO);
                        break;
                    case "hasTypes":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_HAS_TYPE);
                        break;
                    case "antonyms":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_ANTONYM);
                        break;
                    case "memberOf":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_MEMBER_OF);
                        break;
                    case "instanceOf":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_INSTANCE_OF);
                        break;
                    case "partOf":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_PART_OF);
                        break;
                    case "pertainsTo":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_PERTAIN_TO);
                        break;
                    case "inCategory":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_IN_CATEGORY);
                        break;
                    case "hasMembers":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_HAS_MEMBER);
                        break;
                    case "hasParts":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_HAS_PART);
                        break;
                    case "verbGroup":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_VERB_GROUP);
                        break;
                    case "hasInstances":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_HAS_INSTANCE);
                        break;
                    case "attribute":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_ATTRIBUTE);
                        break;
                    case "substanceOf":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_SUBSTANCE_OF);
                        break;
                    case "usageOf":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_USAGE_OF);
                        break;
                    case "cause":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_CAUSE);
                        break;
                    case "hasSubstances":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_HAS_SUBSTANCE);
                        break;
                    case "entails":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_ENTAIL);
                        break;
                    case "inRegion":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_IN_REGION);
                        break;
                    case "hasCategories":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_HAS_CATEGORY);
                        break;
                    case "also":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_ALSO);
                        break;
                    case "regionOf":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_REGION_OF);
                        break;
                    case "hasUsages":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_HAS_USAGE);
                        break;
                    case "participle":
                        helper(file, jsonObject, key, id, Neo4jQueryConnectionType.LANG_RELATED_PARTICIPLE);
                        break;
//match (n:NOUN {_id : "5a74387622f0cd3bcae42a11"}) match (v:ObjectEntity {objectEntity:"kitchenette"}) create (n)-[:LANG_RELATED_HAS_PART]->(v)
                }
            }
        }
        for (String prop : props) {
            file.append(prop + "\r\n");
        }
        System.out.print("DONE");
        System.out.print(i);
        //file.close();
        // done.close();
    }

    //FileWriter done = new FileWriter("relatedDone.txt");
    int i = 0;

    private void helper(FileWriter fileWriter, JSONObject jsonObject, String relatedType, String id, Neo4jQueryConnectionType connectionType) throws IOException {

        if (id.compareTo("5a74448c22f0cd3bcae5d465") < 0) {
            return;
        }
        i++;
        System.out.println(i + "/" + 10000);
        JSONArray jsonArray = jsonObject.getJSONArray(relatedType);
        String label = jsonObject.get("partOfSpeech").toString();
        label = label.equals("null") ? "NOT_KNOW_POS" : label.toUpperCase();
        int c = 0;
//            boolean chk = Neo4jObjectHelper.checkEntityAvailable(destination);
//            if(!chk)
//            {
//                //fileWriter.append(id).append("*").append(connectionType.name()).append("*").append(destination).append("\r\n");
//            }
//            else {
        String query = " match (n:" + label + " {_id : \"" + id + "\"}) ";
        ArrayList<String> arrCreate = new ArrayList<>();
        for (Object node : jsonArray) {
            String current = "v"+String.valueOf(c);
            String destination = node.toString();
           query+= " match ("+current+":ObjectEntity {objectEntity:\"" + destination + "\"}) ";
           arrCreate.add(" create (n)-[:" + connectionType.name() + "]->("+current+") ");

            c++;
            //done.append(query).append("\r\n");

            // }
            //System.out.println(from + " - " + id + " - " + connectionType.name() + " - " + node.toString());

            //TODO
        }
        for (String str:arrCreate) {
            query+=str;
        }
        System.out.println(query);
      // neo4JInteraction.execute(query);
    }
}
