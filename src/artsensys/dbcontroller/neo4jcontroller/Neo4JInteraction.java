package artsensys.dbcontroller.neo4jcontroller;

import org.neo4j.driver.v1.*;


/**
 * Created by nguyennghi on 1/27/1810:02 PM.
 */
public class Neo4JInteraction implements AutoCloseable {

    private final Driver driver;

    public Neo4JInteraction(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void execute(final String command) {
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(tx -> {
                tx.run(command);
                return "";
            });
            System.out.println(greeting);
        }

    }
}
