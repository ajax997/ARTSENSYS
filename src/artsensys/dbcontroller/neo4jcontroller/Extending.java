package artsensys.dbcontroller.neo4jcontroller;

import artsensys.dbcontroller.mongocontroller.MongoInteractive;
import org.neo4j.driver.v1.Record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nguyennghi on 2/5/189:20 PM.
 */
public class Extending {

    //private MongoInteractive mongoInteractive = new MongoInteractive("artsensys_core_kb", "objectEntities");
   private Neo4JInteraction neo4JInteraction = new Neo4JInteraction("bolt://localhost:7687", "neo4j", "123456" );
    public Extending()
    {

    }

    public void pluralVerbAdding() throws Exception {

        FileInputStream fis = new FileInputStream(new File("notmatch.txt"));
        Scanner scanner = new Scanner(fis);


        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();

            String s1 = (str.substring(0, str.indexOf('*')));
            String s2 = (str.substring(str.indexOf('*')+1, str.length()));

            String query = "MATCH (n:ObjectEntity {objectEntity: \"" + s2 + "\"}) CREATE (n)-[r:LANG_GRAMMAR_PLURAL]->(v:PLURAL {objectEntity: " + "\"" + s1 + "\"}) return r";
            if(neo4JInteraction.execute(query).size()==0)
            {
                System.out.println("Field at "   + s1);
            }


        }

    }
}
