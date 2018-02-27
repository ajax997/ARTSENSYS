package artsensys.dbcontroller.neo4jcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Neo4jQueryBuilder {
    public static String aLinkToNewB(Neo4jLabel nLabels, String nProps,
                              Neo4jLabel vLabels, String vProps,
                              Neo4jQueryConnectionType connectionType)
    {

        String base = "match (n:label1 {prop1}) create (v:ObjectEntity:label2 {prop2}) create (n)-[:conn]->(v)";
       base= base.
               replaceAll("\\bconn\\b", connectionType.name()).
               replaceAll("\\blabel1\\b", nLabels.name()).
               replaceAll("\\blabel2\\b", vLabels.name()).
               replaceAll("\\bprop1\\b", nProps).
               replaceAll("\\bprop2\\b", vProps);
        return base;
    }
    public static String generateProperty(Neo4jProperty property, String value) {
        return property.name() + ": \"" + value + "\"";
    }


}
