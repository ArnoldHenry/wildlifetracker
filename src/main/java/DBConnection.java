//import org.sql2o.Sql2o;
//
//public class DBConnection {
//    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test","arnold","arnold1234");
//    public static Sql2o sql2owild = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker","arnold","arnold1234");
//}

import org.sql2o.*;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnection {
    private static URI dbUri;
    public static Sql2o sql2o;

    static Logger logger = LoggerFactory.getLogger(DBConnection.class);

    static {

        try {
            if (System.getenv("DATABASE_URL") == null) {
                dbUri = new URI("postgres://localhost:5432/wildlife_tracker");
            } else {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            }

            int port = dbUri.getPort();
            String host = dbUri.getHost();
            String path = dbUri.getPath();
            String username = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[0];
            String password = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[1];

            sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + port + path, username, password);
        } catch (URISyntaxException e) {
            logger.error("Unable to connect to database.");
        }
    }
}