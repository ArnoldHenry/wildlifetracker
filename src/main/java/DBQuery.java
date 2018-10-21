import org.sql2o.Sql2o;

public class DBQuery {
    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker","arnold","arnold1234");
}
