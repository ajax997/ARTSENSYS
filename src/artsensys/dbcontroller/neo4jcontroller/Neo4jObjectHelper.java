package artsensys.dbcontroller.neo4jcontroller;

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
}
