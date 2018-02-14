package artsensys.dbcontroller.neo4jcontroller;

import org.neo4j.driver.v1.*;

import java.util.List;


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

    public List<Record> execute(final String command) {
        try (Session session = driver.session()) {
            List<Record> results = session.writeTransaction(tx -> {
                StatementResult result = null;
                try {
                    result = tx.run(command);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                assert result != null;
                return result.list();
            });
            return results;
        }

    }
}
