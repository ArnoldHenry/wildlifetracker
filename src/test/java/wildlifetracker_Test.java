import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class wildlifetracker_Test {
@Test
    public void is_insertingsighting_true(){
    DataProperties dataProperties = new DataProperties("Nairobi",70,"Arnold");
    dataProperties.savesightings();
    assertEquals(DataProperties.all().get(0), dataProperties);
}

    @After
    public void tearDown(){
        try(Connection connection = DBConnection.sql2o.open()){
            String sql = "DELETE FROM sightings *";
            connection.createQuery(sql).executeUpdate();
        }
    }




}
