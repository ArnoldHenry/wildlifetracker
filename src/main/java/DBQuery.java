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

    public String getRanger() {
        return ranger;
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

    public void setRanger(String ranger) {
        this.ranger = ranger;
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

    public void savesightings(){
        try (Connection connection = DBConnection.sql2o.open()){
            String sql = "INSERT INTO sightings(location,age,ranger)VALUES(:location,:age,:ranger)";
            connection.createQuery(sql)
                    .addParameter("location",this.location)
                    .addParameter("age",this.age)
                    .addParameter("ranger",this.ranger)
                    .executeUpdate();

        }
    }
    //save ranger details
    public void saveranger(DBQuery ranger) {
        try (Connection connection = DBConnection.sql2owild.open()) {
            String newdata = "INSERT INTO customer(fname,sname,lname,mobile,gender,email,customerid,stylistid)" +
                    "VALUES(:fname,:sname,:lname,:mobile,:gender,:email,:customerid,:stylistid)";
            connection.createQuery(newdata)
                    .addParameter("rangerid", ranger.getRanger())
                    .addParameter("fname", ranger.getFname())
                    .addParameter("sname", ranger.getSname())
                    .addParameter("gender", ranger.getGender())
                    .executeUpdate();
        }
    }

    public static List<DataProperties> allranger() {
        String sql = "SELECT location,age,ranger FROM sightings";
        try(Connection con = DBConnection.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(DataProperties.class);
        }
    }

    @Override
    public boolean equals(Object otherTask) {
        if (!(otherTask instanceof DataProperties)) {
            return false;
        } else {
            DataProperties newTask = (DataProperties) otherTask;
            return  this.getLocation().equals(newTask.getLocation())&&
                    this.getAge().equals(newTask.getAge())&&
                    this.getRanger().equals(newTask.getRanger());
        }
    }
}
