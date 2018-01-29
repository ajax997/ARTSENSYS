package artsensys.dbcontroller.mongocontroller;

import javafx.util.Pair;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by nguyennghi on 12/10/1712:10 AM.
 */

public class JSONBuilder {

   private Document doc = null;
   private ObjectId objectId;
   public JSONBuilder()
    {
        doc = new Document();
        objectId = new ObjectId();
        doc.append("_id", objectId.toString());
    }

    public JSONBuilder(Document doc)
    {
        this.doc = doc;
    }

    public void initialize(String entity)
    {
        doc.append("Object_Entity", entity);
    }

    public String getObjectID()
    {
        return  objectId.toString();
    }

    public void addElement(Pair<String, Object> element)
    {
        doc.append(element.getKey(), element.getValue());
    }

    public void addNestedDocument(String parent, HashMap<String, Object> elements)
    {
        if(parent.equals("root"))
        {
            for(Map.Entry<String, Object> e : elements.entrySet())
            {
                doc.append(e.getKey(), e.getValue());
            }
        }
        else
        {

        }
    }

    public Document toDocument()
    {
        return doc;
    }

    public String toJson()
    {
        return doc.toJson();
    }
    
}
