import spark.ModelAndView;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.debug.DebugScreen.enableDebugScreen;

public class wildlifetracker {
    public static void main(String[] args) {
//        ProcessBuilder process = new ProcessBuilder();
//        Integer port;
//        if (process.environment().get("PORT") != null) {
//            port = Integer.parseInt(process.environment().get("PORT"));
//        } else {
//            port = 4567;
//        }
//
//        setPort(port);

        String layout = "templates/layout.vtl";
        staticFileLocation ("/public");
        enableDebugScreen();
        DBQuery dbQuery = new DBQuery();

        get("/backhome",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("template","/templates/rangerform.vtl");

            return new ModelAndView(model,layout);
        },new VelocityTemplateEngine());

        get("/",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("template","templates/rangerform.vtl");
            model.put("ranger",DBQuery.rangers());
            return new ModelAndView(model,layout);
        },new VelocityTemplateEngine());

        post("/rangerform",((request, response) ->{
            Map<String,Object> model = new HashMap<String,Object>();

            String rangerid = request.queryParams("rangerid");
            dbQuery.setRangerid(rangerid);
            String fname = request.queryParams("fname");
            dbQuery.setFname(fname);
            String sname = request.queryParams("sname");
            dbQuery.setSname(sname);
            String gender = request.queryParams("gender");
            dbQuery.setGender(gender);
            dbQuery.saveranger(dbQuery);
//            response.redirect("/update");
            model.put("template","/templates/success.vtl");
            return new ModelAndView(model,layout);
        }),new VelocityTemplateEngine());


        post("/sightingform",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();

            String rangerid = request.queryParams("rangerid");
            dbQuery.setRangerid(rangerid);
            String animalname = request.queryParams("animalname");
            dbQuery.setAnimalname(animalname);
            String location = request.queryParams("location");
            dbQuery.setLocation(location);
            String age = request.queryParams("age");
            dbQuery.setAge(Integer.parseInt(age));
            String health = request.queryParams("health");
            dbQuery.setHealth(health);
            dbQuery.savesightings(dbQuery);
//            response.redirect("/backhome");
            model.put("template","/templates/success.vtl");
            return new  ModelAndView(model,layout);
        },new VelocityTemplateEngine());

        get("/rangerview",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("template","/templates/rangerform.vtl");
            model.put("stylists",DBQuery.allranger());
            return new ModelAndView(model,layout);
        },new VelocityTemplateEngine());



        get("/viewdata",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("template","/templates/viewdata.vtl");
            model.put("allranger",DBQuery.allranger());
            model.put("ranger",DBQuery.rangers());
            return new  ModelAndView(model,layout);
        },new VelocityTemplateEngine());


        get("/viewrangers",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            try{
                model.put("ranger",DBQuery.rangers());
                model.put("template","/templates/viewdata.vtl");
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());

        post("/delranger",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            try{
                String del = request.queryParams("deleteranger");
                dbQuery.setRangerid(del);
                String df = DBQuery.selectranger(dbQuery);
                   if ( df != null) {
                       System.out.println(DBQuery.selectranger(dbQuery));
                       dbQuery.delranger(dbQuery);
                       response.redirect("/backhome");
                   } else{
                       model.put("template","/templates/delranger.vtl");
                   }
               }catch (Exception e){
                   System.out.println(e.getMessage());
               }
            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());
    }
}
