package artsensys.dbcontroller.mongocontroller;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.util.Pair;
import org.bson.Document;

/**
 * Created by nguyennghi on 12/9/1711:26 PM.
 */
public class MongoInteractive  implements MongoInteractiveInterface{
    MongoClient mongo;
    MongoDatabase db;
    MongoCollection collection;
    public MongoInteractive()
    {
        mongo = new MongoClient("localhost", 27017);
    }
    public MongoInteractive(String url, int port)
    {
        mongo = new MongoClient(url, port);
    }
    public  MongoInteractive(String url, int port, String dbName)
    {
        mongo = new MongoClient(url, port);
        db = mongo.getDatabase(dbName);
    }

    public MongoInteractive(String dbName, String collName)
    {
        mongo = new MongoClient("localhost", 27017);
        db = mongo.getDatabase(dbName);
        collection = db.getCollection(collName);
    }

   public MongoInteractive(String url, int port, String dbName, String collName)
    {
        mongo = new MongoClient(url, port);
        db = mongo.getDatabase(dbName);
        collection = db.getCollection(collName);
    }

    public void addNewDocument(Document document)
    {
        collection.insertOne(document);
    }
    public void addNewDocument(DBObject dbObject)
    {
        collection.insertOne(dbObject);
    }


    public MongoCursor find(BasicDBObject basicDBObject)
    {

       return collection.find(basicDBObject).iterator();
    }

    public void update(BasicDBObject query, BasicDBObject update)
    {
        collection.updateOne(query, update);
    }




    public void createNewDataBase(String dbName)
    {
         mongo.getDatabase(dbName);
    }

    public void setDbToInteraction(String dbName)
    {
        db = mongo.getDatabase(dbName);
    }

    public void setCollectionToInteractive(String collName)
    {
        collection = db.getCollection(collName);
    }
    public void addCollection(String collectionName)
    {
        db.createCollection(collectionName);
    }
    public void deleteCollection(String collectionName)
    {
        db.getCollection(collectionName).drop();
    }

}
