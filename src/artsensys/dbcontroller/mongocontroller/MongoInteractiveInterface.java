package artsensys.dbcontroller.mongocontroller;

/**
 * Created by nguyennghi on 12/9/1711:50 PM.
 */
public interface MongoInteractiveInterface {
   void createNewDataBase(String dbName);
    void setDbToInteraction(String dbName);
    void addCollection(String collectionName);
    void deleteCollection(String collectionName);
}
