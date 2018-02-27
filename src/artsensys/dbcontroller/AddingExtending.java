package artsensys.dbcontroller;

import artsensys.dbcontroller.neo4jcontroller.*;
import org.neo4j.driver.v1.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nguyennghi on 2/15/185:54 PM.
 */
public class AddingExtending {
    Neo4JInteraction neo4JInteraction = new
            Neo4JInteraction("bolt://localhost:7687", "neo4j", "123456");

    public void collect() throws IOException {

        FileInputStream fileInputStream = new FileInputStream(new File("finalnotfound.txt"));
        Scanner scanner = new Scanner(fileInputStream);
        FileWriter edOring = new FileWriter(new File("erOrest.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.endsWith("er") || line.endsWith("est")) {
                System.out.println(line);
                edOring.append(line + "\r\n");
            }
        }
        edOring.close();
        fileInputStream.close();
        //neo4JInteraction.close();
    }

    public void getPluralNotConnected() {
//        String query = "match (n:PLURAL) where not (n)--() return n.objectEntity";
//        List<Record> list = neo4JInteraction.execute(query);
//        for (Record record : list) {
//
//            String plural = record.get(0).toString().replace(\"\"\", \" \").trim();
//            System.out.println(plural);
//            if(plural.endsWith("a"))
//            {
//                String base = plural.substring(0, plural.length()-1)+"on";
//                System.out.println(base);
//                if(Neo4jObjectHelper.checkEntityAvailable(base))
//                {
//                    String queryConnect = "match (n:ObjectEntity {objectEntity : \""+base+"\"}) match (v:PLURAL {objectEntity : \""+plural+"\"}) create (n)-[:LANG_GRAMMAR_PLURAL]->(v)";
//                    neo4JInteraction.execute(queryConnect);
//                    //System.out.print(queryConnect);
//                }
//            }

  //      }
    }

    public void edOrIng() throws IOException {
        ArrayList<String> arr = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File("edOring2.txt"));
        Scanner scanner = new Scanner(fis);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.endsWith("ing")) {
                String base = line.substring(0, line.length() - 4);
                System.out.println(base);
                boolean available = Neo4jObjectHelper.checkEntityAvailable(base);
                if (available) {
                    String query = "match (n:ObjectEntity {objectEntity :\"" + base + "\"}) " +
                            "create (v:ObjectEntity:GERUND {objectEntity: \"" + line + "\"}) " +
                            "create (n)-[:LANG_GRAMMAR_GERUND]->(v)";

                    neo4JInteraction.execute(query);
                } else {
                    arr.add(line);
                }
            }
        }

        FileWriter fileWriter = new FileWriter("edOring2.txt");
        for (String str : arr) {
            fileWriter.append(str + "\r\n");
        }
        fileWriter.close();
        scanner.close();
        fis.close();

    }

    public void bruteForce() {
        String query = "match (n:ObjectEntity) where not (n)--() return n.objectEntity";
        List<Record> res = neo4JInteraction.execute(query);
        int count = 0;
        for (Record record : res) {
            String entity = record.get(0).toString().replace('\"',' ').trim();
            count++;
//            if(entity.endsWith("ed"))
//            {
//                String base = entity.substring(0, entity.length()-1);
//                if(Neo4jObjectHelper.checkEntityAvailable(base))
//                {
//                    String query1 = "match (n:ObjectEntity {objectEntity: \""+base+"\"}) " +
//                            "match (v:ObjectEntity {objectEntity:\""+entity+"\"}) " +
//                            "set v:REGULAR_VERB " +
//                            "create (n)-[:LANG_GRAMMAR_REGULAR_VERB]->(v) ";
//                    System.out.println(query1);
//                    neo4JInteraction.execute(query1);
//                }
//            }
//            if(entity.endsWith("ly"))
//            {
//                String base = entity.substring(0, entity.length()-3)+"y";
//                if(Neo4jObjectHelper.checkEntityAvailable(base))
//                {
//                    String query2 = "match (n:ObjectEntity {objectEntity: \""+base+"\"}) " +
//                            "match (v:ObjectEntity {objectEntity:\""+entity+"\"}) " +
//                            "set v:ADVERB " +
//                            "create (n)-[:LANG_GRAMMAR_ADVERB]->(v) ";
//                    System.out.println(query2);
//                    neo4JInteraction.execute(query2);
//                }
//            }
//            if(entity.endsWith("es"))
//            {
//                String base = entity.substring(0, entity.length()-2);
//                if(Neo4jObjectHelper.checkEntityAvailable(base))
//                {
//                    String query3 = "match (n:ObjectEntity {objectEntity: \""+base+"\"}) " +
//                            "match (v:ObjectEntity {objectEntity:\""+entity+"\"}) " +
//                            "set v:PLURAL " +
//                            "create (n)-[:LANG_GRAMMAR_PLURAL]->(v) ";
//                    System.out.println(query3);
//                    neo4JInteraction.execute(query3);
//                }
//            }
//            if(entity.endsWith("ing"))
//            {
//                String base = entity.substring(0, entity.length()-4)+"ie";
//                if(Neo4jObjectHelper.checkEntityAvailable(base))
//                {
//                    String query4 = "match (n:ObjectEntity {objectEntity: \""+base+"\"}) " +
//                            "match (v:ObjectEntity {objectEntity:\""+entity+"\"}) " +
//                            "set v:GERUND " +
//                            "create (n)-[:LANG_GRAMMAR_GERUND]->(v) ";
//                    System.out.println(query4);
//                    neo4JInteraction.execute(query4);
//                }
//            }
           // System.out.println(entity);
            System.out.println(entity);
        }

        System.out.println(count);

    }

    public void comparation() throws FileNotFoundException {

        ArrayList<String> arr = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File("erOrest.txt"));
        Scanner scanner = new Scanner(fis);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.endsWith("er"))
            {
                String base = line.substring(0, line.length()-2);
                if(Neo4jObjectHelper.checkEntityAvailableWithAdjective(base))
                {
                    String query = Neo4jQueryBuilder.aLinkToNewB(Neo4jLabel.ObjectEntity,
                            Neo4jQueryBuilder.generateProperty(Neo4jProperty.objectEntity, base),
                            Neo4jLabel.COMPARATIVE_ADJECTIVE,
                            Neo4jQueryBuilder.generateProperty(Neo4jProperty.objectEntity, line),
                            Neo4jQueryConnectionType.LANG_GRAMMAR_COMPARATIVE_ADJECTIVE);
                    neo4JInteraction.execute(query);
                    System.out.println(query);
                }
            }
            if(line.endsWith("est"))
            {
                String base = line.substring(0, line.length()-3);
                if(Neo4jObjectHelper.checkEntityAvailableWithAdjective(base))
                {

                    String query = Neo4jQueryBuilder.aLinkToNewB(Neo4jLabel.ObjectEntity,
                            Neo4jQueryBuilder.generateProperty(Neo4jProperty.objectEntity, base),
                            Neo4jLabel.SUPERLATIVE_ADJECTIVE,
                            Neo4jQueryBuilder.generateProperty(Neo4jProperty.objectEntity, line),
                            Neo4jQueryConnectionType.LANG_GRAMMAR_SUPERLATIVE_ADJECTIVE);
                    neo4JInteraction.execute(query);
                    System.out.println(query);
                }
            }
        }
    }

    public void reFind() throws IOException {
        FileInputStream fis = new FileInputStream(new File("finalnotfound.txt"));
        FileWriter fileWriter = new FileWriter("finalnotfound2.txt");
        ArrayList<String> arr = new ArrayList<>();
        Scanner scanner = new Scanner(fis);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(!Neo4jObjectHelper.checkEntityAvailable(line))
            {
                arr.add(line);
                System.out.println(line);
            }
        }

        for(String str : arr)
        {
            fileWriter.append(str+"\r\n");
        }
        fileWriter.close();
        fis.close();
        Neo4jObjectHelper.close();
    }


}
