package test;

import artsensys.dbcontroller.neo4jcontroller.Extending;
import artsensys.dbcontroller.neo4jcontroller.Neo4JInteraction;
import artsensys.dbcontroller.neo4jcontroller.Neo4jObjectHelper;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.types.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.WeakHashMap;
import java.util.logging.LogManager;

/**
 * Created by nguyennghi on 12/10/17 1:05 PM.
 */
public class Main {
    //static Neo4JInteraction neo4JInteraction;
    public static void main(String... args) throws Exception {
        String str = "In the mid-1980s, Swiss physician René Flammer discovered an antigen within the mushroom that stimulates an autoimmune reaction causing the body's immune cells to consider its own red blood cells as foreign and attack them. Despite this, it was not until 1990 that guidebooks firmly warned against eating P. involutus, and one Italian guidebook recommended it as edible in 1998.[60] The relatively rare immunohemolytic syndrome occurs following the repeated ingestion of Paxillus mushrooms.[61] Most commonly it arises when the person has ingested the mushroom for a long period of time, sometimes for many years, and has shown mild gastrointestinal symptoms on previous occasions.[27] The Paxillus syndrome is better classed as a hypersensitivity reaction than a toxicological reaction as it is caused not by a genuinely poisonous substance but by the antigen in the mushroom. The antigen is still of unknown structure but it stimulates the formation of IgG antibodies in the blood serum. In the course of subsequent meals, antigen-antibody complexes are formed; these complexes attach to the surface of blood cells and eventually lead to their breakdown.[27]\n" +
                "\n";
        str = str.replace('.', ' ').replace(',',' ').replace('\"', ' ');
        StringTokenizer tokenizer = new StringTokenizer(str, " ");
        int i = 0, count = 0;
        while (tokenizer.hasMoreTokens())
        {
            i++;
            String word = tokenizer.nextToken().toLowerCase();
           boolean chk =  Neo4jObjectHelper.checkEntityAvailable(word);
           System.out.println(word + ">> "+chk);
           if(chk) count++;
        }
        System.out.print((float) count/i*100);

    }
//        LogManager.getLogManager().reset();
////        FileInputStream fis = new FileInputStream(new File("listfile.txt"));
////        Scanner scanner = new Scanner(fis);
////        int i = 0;
////        while (scanner.hasNextLine())
////        {
////            i++;
////            scanner.nextLine();
////        }
////
////        ObjectController controller = new ObjectController();
////
////       controller.start(i);
//
//        Extending extending = new Extending();
//        extending.pluralVerbAdding();

//        Neo4JInteraction neo4JInteraction = new Neo4JInteraction("bolt://localhost:11002", "neo4j", "123456" );
//        List<Record> res = neo4JInteraction.execute("match (n {objectEntity: \"eee\"}) create (n)-[r:LANG_GRAMMAR_PLURAL]->(b:PLURAL{objectEntity:\"dscdcd\"}) return r");
//        System.out.println(res.size());


//         neo4JInteraction = new
//                Neo4JInteraction("bolt://localhost:7687", "neo4j", "123456");
//        FileInputStream fileInputStream = new FileInputStream(new File("not_complete.txt"));
//
//        Scanner scanner = new Scanner(fileInputStream);
//
//        while (scanner.hasNextLine()) {

//
//            String v1 = scanner.next().toLowerCase();
//            String v2 = scanner.next().toLowerCase();
//            String v3 = scanner.next().toLowerCase();
//
//            System.out.println(v1 + " " + v2 + " " + v3);
//
//            if (v1.equals(v2) && v1.equals(v3) && v2.equals(v3)) {
//                fileWriter.append(v1 + " " + v2 + " " + v3 + "\r\n");
//            } else {
//                StringTokenizer st1 = new StringTokenizer(v1, "/");
//                while (st1.hasMoreTokens()) {
//                    String verb1 = st1.nextToken();
//                    boolean chk1 = Neo4jObjectHelper.checkEntityAvailable(verb1);
//
////                    StringTokenizer st2 = new StringTokenizer(v2, "/");
////                    while (st2.hasMoreTokens()) {
////                        String verb2 = st2.nextToken();
////                        boolean chk2 = Neo4jObjectHelper.checkEntityAvailable(verb2);
////                        if (!chk1 || !chk2) {
////                            fileWriter.append(verb1 + "[" + chk1 + "]" + "!" + verb2 + "[" + chk2 + "]\r\n");
////                        } else {
////                            String query = "match(a:ObjectEntity{objectEntity :\"" + verb1 + "\"}), (b:ObjectEntity{objectEntity :\"" + verb2 + "\"}) set b:PAST_SIMPLE_VERB create (a)-[:LANG_GRAMMAR_PAST_SIMPLE_VERB]->(b)";
////                            neo4JInteraction.execute(query);
////                        }
////                    }
//                    StringTokenizer st3 = new StringTokenizer(v3, "/");
//                    while (st3.hasMoreTokens()) {
//                        String verb3 = st3.nextToken();
//                        boolean chk3 = Neo4jObjectHelper.checkEntityAvailable(verb3);
//                        if (!chk1 || !chk3) {
//                            fileWriter.append(verb1 + "[" + chk1 + "]" + "*" + verb3 + "[" + chk3 + "]\r\n");
//                        } else {
//                            String query = "match(a:ObjectEntity{objectEntity :\"" + verb1 + "\"}), (b:ObjectEntity{objectEntity :\"" + verb3 + "\"}) set b:PAST_PARTICIPLE_VERB create (a)-[:LANG_GRAMMAR_PAST_PARTICIPLE_VERB]->(b)";
//                            neo4JInteraction.execute(query);
//                        }
//                    }
//                }
//            }
//            String line = scanner.nextLine();
//            if (line.contains("!")) {
//
//               process(line,"!", true);
//            } else if (line.contains("*")) {
//                //V3
//                process(line,"*", false);
//            } else if (line.contains(" ")) {
//                StringTokenizer tokenizer = new StringTokenizer(line, " ");
//                String v = tokenizer.nextToken();
//                String query = "match (n:ObjectEntity{ objectEntity: \""+v+"\"}) set n:PAST_SIMPLE_VERB:PAST_PARTICIPLE_VERB create (n)-[:LANG_GRAMMAR_PAST_SIMPLE_VERB]->(n) create (n)-[:LANG_GRAMMAR_PAST_PARTICIPLE_VERB]->(n)";
//                neo4JInteraction.execute(query);
//            }
//        }
//
//        fileInputStream.close();
//        neo4JInteraction.close();
//    }

//    static void process(String line, String split, boolean v2) {
//        String pv1 = "", pv2 = "";
//        if (v2) {
//            pv1 = "PAST_SIMPLE_VERB";
//            pv2 = "LANG_GRAMMAR_PAST_SIMPLE_VERB";
//        } else {
//            pv1 = "PAST_PARTICIPLE_VERB";
//            pv2 = "LANG_GRAMMAR_PAST_PARTICIPLE_VERB";
//        }
//
//        String s1 = line.substring(0, line.indexOf(split));
//        boolean s1available = s1.contains("true");
//
//        s1 = s1.replaceAll("\\btrue\\b", "").
//                replaceAll("\\bfalse\\b", "").
//                replace('[', ' ').replace(']', ' ').trim();
//
//        String s2 = line.substring(line.indexOf(split) + 1, line.length());
//        boolean s2available = s2.contains("true");
//        s2 = s2.replaceAll("\\btrue\\b", "").
//                replaceAll("\\bfalse\\b", "").
//                replace('[', ' ').replace(']', ' ').trim();
//
//        if (s1available && !s2available) {
//            String query = "match(a:ObjectEntity{objectEntity :\"" + s1 + "\"}) create (b:ObjectEntity:"+pv1+" {objectEntity:\"" + s2 + "\"}) create (a)-[:"+pv2+"]->(b)";
//            neo4JInteraction.execute(query);
//        }
//        if (!s1available && s2available) {
//            String query = "match(a:ObjectEntity{objectEntity :\"" + s2 + "\"}) create (b:ObjectEntity:"+pv1+" {objectEntity:\"" + s1 + "\"}) create (b)-[:"+pv2+"]->(a)";
//            neo4JInteraction.execute(query);
//        }
//        if (!s1available && !s2available) {
//            String query = "create (a:ObjectEntity:"+pv1+" {objectEntity:\"" + s1 + "\"}) create (b:ObjectEntity:"+pv1+" {objectEntity:\"" + s2 + "\"}) create (a)-[:"+pv2+"]->(b)";
//            neo4JInteraction.execute(query);
//            System.out.println(query);
//        }
//
//    }
}
