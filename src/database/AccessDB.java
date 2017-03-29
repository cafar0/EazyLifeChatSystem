package database;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public abstract class AccessDB {
    protected String databaseName;
    protected String objectCollection;
    //don't forget to start db
    //net start mongoDB
    protected MongoClient mongoClient;
    protected MongoDatabase mongoDatabase;
    private static AccessDB instance;
    protected MongoCollection<Document> collection;


    public AccessDB() {
        //MongoClientOptions.Builder builder = MongoClientOptions.builder();
        //builder.socketKeepAlive(true);
    }

    protected void openDB() {
        mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase(databaseName);
        collection = mongoDatabase.getCollection(objectCollection);
    }

    protected void closeDB() {
        mongoClient.close();
    }

    protected void addData() {
        openDB();
        mongoDatabase.getCollection("objects").insertOne(
                new Document("location",
                        new Document()
                                .append("latitude", 45.7549842)
                                .append("longitude", 21.2320606))
                        .append("title", "BMW X1")
                        .append("description", "CategoryOff-road Vehicle / Pickup Truck\n" +
                                "Vehicle Number 67697WO\n" +
                                "AvailabilityNow\n" +
                                "Mileage15,929km\n" +
                                "Cubic Capacity1,995 ccm\n" +
                                "Power105 kW (143 PS)\n" +
                                "FuelDiesel\n" +
                                "Fuel Consumption \n" +
                                "≈ 5.2 l/100km (combined)\n" +
                                "≈ 6.1 l/100km (urban)\n" +
                                "≈ 4.7 l/100km (extra-urban)")
                        .append("location_id", "1852"));
        closeDB();
    }

    protected void printTable() {
        openDB();
        FindIterable<Document> iterable = collection.find();
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });
        closeDB();
    }

    protected void printTable2() {
        openDB();
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
        closeDB();
    }

    protected void findAllByQuerry() {
        openDB();
        // now use a range query to get a larger subset
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        };
        System.out.println("here?");
        collection.find(and(gt("location.latitude", 40), lt("location.latitude", 50))).forEach(printBlock);

        //collection.find(and(gt("i", 50), lte("i", 100))).forEach(printBlock);
        //myDoc = collection.find(exists("i")).sort(descending("i")).first();
        closeDB();
    }


    protected boolean updateObject() {
        openDB();
        Document document = collection.find(gt("da", 4)).first();
        System.out.println(document);
        UpdateResult updateResult = collection.updateOne(eq("location_id", "!4124"), new Document("$set", new Document("location.latitude", 46.8)));
        collection.updateOne(eq("location_id", "!4124"), new Document("$set", new Document("location.longitude", 21.75)));
        Document document2 = collection.find(gt("da", 4)).first();
        System.out.println(document2);
        System.out.println("is modified count available? " + updateResult.isModifiedCountAvailable() + " count: " + updateResult.getModifiedCount());
        closeDB();
        return updateResult.getModifiedCount() > 0;
    }


    protected void getCollection() {
        openDB();
        if (mongoDatabase == null) {
            System.out.println("Could not connect to the database");
            closeDB();
            return;
        }

        mongoDatabase.getCollection("objects").insertOne(new Document("da", "5"));
        closeDB();
    }

}

