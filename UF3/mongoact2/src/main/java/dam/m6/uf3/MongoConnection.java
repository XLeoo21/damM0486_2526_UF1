package dam.m6.uf3;

import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class MongoConnection {

    static String uri = "mongodb+srv://llaguna:llaguna5617%3F@inscastellet.f4jjgcr.mongodb.net/?appName=inscastellet";

    public static void main(String[] args) {

        try (MongoClient client = MongoClients.create(uri)) {
            MongoDatabase db = client.getDatabase("Tasques");
            MongoCollection<Document> collection = db.getCollection("Entrades");

            // CREATE
            Document doc = new Document("nom", "CRUD")
                    .append("cognom1", "Java")
                    .append("cognom2", "Mongo")
                    .append("dataEntrada", new java.util.Date())
                    .append("completa", false)
                    .append("observacions", "Test CRUD");
            collection.insertOne(doc);

            // READ
            collection.find().forEach(d -> System.out.println(d.toJson()));

            // UPDATE
            UpdateResult u = collection.updateOne(eq("nom", "CRUD"),
                    new Document("$set", new Document("completa", true)));
            System.out.println("Updated: " + u.getModifiedCount());

            // DELETE
            DeleteResult del = collection.deleteOne(eq("nom", "CRUD"));
            System.out.println("Deleted: " + del.getDeletedCount());
        }
    }
}
