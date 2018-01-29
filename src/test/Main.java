package test;

import artsensys.dbcontroller.ObjectController;
import artsensys.dbcontroller.mongocontroller.JSONBuilder;
import artsensys.dbcontroller.mongocontroller.MongoInteractive;
import artsensys.dbcontroller.neo4jcontroller.Neo4JInteraction;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.BSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import javax.print.Doc;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.LogManager;

/**
 * Created by nguyennghi on 12/10/17 1:05 PM.
 */
public class Main {
    public static void main(String... args) throws Exception {
        LogManager.getLogManager().reset();
        ObjectController controller = new ObjectController();
        controller.start();


    }





}
