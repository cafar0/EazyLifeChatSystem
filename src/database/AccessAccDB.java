package database;

import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Antoniu on 17-Feb-17.
 */
public class AccessAccDB extends AccessDB {
    final String username, password, sessionID;

    public AccessAccDB() {
        //MongoClientOptions.Builder builder = MongoClientOptions.builder();
        //builder.socketKeepAlive(true);
        databaseName = "EasyLifeAcc";
        objectCollection = "accounts";
        username = "username";
        password = "password";
        sessionID = "sessionID";
    }

    public boolean addAccount(String user, String pw) {
        boolean success;
        openDB();
        Document account = collection.find(eq(username, user)).first();
        if (account == null) {
            mongoDatabase.getCollection(objectCollection).insertOne(
                    new Document(username, user)
                            .append(password, pw));
            success = true;
        } else {
            System.out.println("account already existing");
            success = false;
        }
        closeDB();
        return success;
    }

    public boolean authenticatePassword(String user, String password) {
        boolean success;
        openDB();
        Document document = collection.find(eq(username, user)).first();
        if(document != null) {
            String passDb = document.getString(this.password);
            String saltPass = password;

            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                System.out.println("No such algorithm!!");
            }

            byte[] pwHash = {0};
            if (digest != null) {
                digest.update(saltPass.getBytes());
                pwHash = digest.digest();
            }

            System.out.println(Arrays.toString(pwHash));
            System.out.println(passDb);

            if (Arrays.toString(pwHash).equals(document.getString(this.password))) {
                success = true;
            } else {
                success = false;
            }
        } else {
            System.out.println("Acc not existent");
            success = false;
        }
        closeDB();
        return success;
    }

    public boolean authenticateSession(String user, String sessionID) {
        boolean success;
        openDB();
        Document document = collection.find(eq(username, user)).first();
        System.out.println(document);
        System.out.println("session is: " + document.getString(this.sessionID));
        if (sessionID.equals(document.getString(this.sessionID))) {
            success = true;
        } else {
            success = false;
        }
        closeDB();
        return success;

    }

    public boolean setSessionID(String user, String sessionID) {
        boolean success;
        openDB();
        Document document = collection.find(eq(username, user)).first();
        if (document != null) {
            UpdateResult updateResult1 = collection.updateOne(eq(username, user), new Document("$set", new Document(this.sessionID, sessionID)));

            if (updateResult1.getModifiedCount() > 0) {
                success = true;
            } else {
                success = false;
            }
        } else {
            System.out.println("something is wrong. Could not find user");
            success = false;
        }
        closeDB();
        return success;
    }

    public static void main(String args[]) {
        AccessAccDB accDB = new AccessAccDB();
        accDB.printTable();
        byte[] bytes = new byte[128];
        new Random().nextBytes(bytes);
        //System.out.println("1 " + accDB.addAccount("Testing", "password"));
        //System.out.println("2 " + accDB.setSessionID("Testing", "nu", new Date().getTime() + 30 * 1000));
        //System.out.println("3 " + accDB.authenticatePassword("TestAcc", "password"));
        //System.out.println("4 " + accDB.authenticateSession("Testing", "nu"));
        accDB.printTable();
    }
}
