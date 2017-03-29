package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class DatabaseTemp implements Serializable{
    public static List<DatabaseEntry> db = new ArrayList<>();
    private static DatabaseTemp dt = null;

    private DatabaseTemp() {}

    public static DatabaseTemp instance() {
        if (dt == null) {
            dt = new DatabaseTemp();
        }
        return dt;
    }

    public boolean exisentUser(String user) {
        for (DatabaseEntry dbe : db) {
            if (dbe.getUser().equals(user)) {
                return false;
            }
        }
        return true;
    }

    public void addDE(DatabaseEntry dbe) {
        db.add(dbe);
    }

    public static void stupid() {
        System.out.println("stupid " + db.size());
    }


}
