import org.sql2o.Connection;

import java.util.List;

public class DBQuery extends WildLifeAbstract {
    public void setFname(String fname) {
        this.fname = fname;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
    public String getGender() {
        return gender;
    }
    public void setAnimalname(String animalname) {
        this.animalname = animalname;
    }
    public String getAnimalname() {
        return animalname;
    }
    public String getFname() {
        return fname;
    }

    public String getSname() {
        return sname;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getRangerid() {
        return rangerid;
    }

    public String getHealth() {
        return health;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRangerid(String rangerid) {
        this.rangerid = rangerid;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void savesightings(DBQuery sighting){
        try (Connection connection = DBConnection.sql2owild.open()){
            String sql = "INSERT INTO sightings(rangerid,animalname,location,age,health)VALUES(:rangerid,:animalname,:location,:age,:health)";
            connection.createQuery(sql)
                    .addParameter("rangerid",sighting.getRangerid())
                    .addParameter("animalname",sighting.getAnimalname())
                    .addParameter("location",sighting.getLocation())
                    .addParameter("age",sighting.getAge())
                    .addParameter("health",sighting.getHealth())
                    .executeUpdate();

        }
    }
    //save ranger details
    public void saveranger(DBQuery ranger) {
        try (Connection connection = DBConnection.sql2owild.open()) {
            String newdata = "INSERT INTO ranger(rangerid,fname,sname,gender)" +
                    "VALUES(:rangerid,:fname,:sname,:gender)";
            connection.createQuery(newdata)
                    .addParameter("rangerid", ranger.getRangerid())
                    .addParameter("fname", ranger.getFname())
                    .addParameter("sname", ranger.getSname())
                    .addParameter("gender", ranger.getGender())
                    .executeUpdate();
        }
    }

    public static List<DBQuery> allranger() {
        String sql = "SELECT * FROM sightings";
        try(Connection con = DBConnection.sql2owild.open()) {
            return con.createQuery(sql).executeAndFetch(DBQuery.class);
        }
    }
    public static List<DBQuery> rangers() {
        String sql = "SELECT * FROM ranger";
        try(Connection con = DBConnection.sql2owild.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(DBQuery.class);
        }
    }
    public static String selectranger(DBQuery idcheck) {
        try(Connection con = DBConnection.sql2owild.open()) {
            String sql = "SELECT rangerid FROM ranger WHERE rangerid=:rangerid";
            String idranger = con.createQuery(sql)
                    .addParameter("rangerid",idcheck.getRangerid())
                    .executeScalar(String.class);
            return idranger;
        }
    }
    public void delranger(DBQuery rangerid){
        try(Connection connection = DBConnection.sql2owild.open()){
            String sql = "DELETE FROM ranger WHERE rangerid=:rangerid";
            connection.createQuery(sql)
                    .addParameter("rangerid",rangerid.getRangerid())
                    .executeUpdate();

        }
    }

    @Override
    public boolean equals(Object otherTask) {
        if (!(otherTask instanceof DataProperties)) {
            return false;
        } else {
            DBQuery newTask = (DBQuery) otherTask;
            return  this.getLocation().equals(newTask.getLocation())&&
                    this.getAge().equals(newTask.getAge())&&
                    this.getFname().equals(newTask.getFname())&&
                    this.getSname().equals(newTask.getSname())&&
                    this.getRangerid().equals(newTask.getRangerid());
        }
    }
}
