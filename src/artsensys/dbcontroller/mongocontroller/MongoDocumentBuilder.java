package artsensys.dbcontroller.mongocontroller;

import org.bson.Document;

/**
 * Created by nguyennghi on 1/28/181:55 PM.
 */
public class MongoDocumentBuilder {
    Document document;

    MongoDocumentBuilder(String objectEntity, String json)
    {
        document = Document.parse(json).append("objectEntity", objectEntity);
    }
    public String toJson(){
        return document.toJson();
    }
    public MongoDocumentBuilder addAttribute(String key, String value)
    {
        document.put(key,value);
        return this;
    }
    public Document toDocument()
    {
        return document;
    }
}
