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
            dbQuery.setRanger(rangerid);
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
            dbQuery.setRanger(rangerid);
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
                String delranger = request.queryParams("delranger");
                dbQuery.setRanger(delranger);
                String df = DBQuery.selectranger(dbQuery);
                if ( df != null) {
                    dbQuery.delranger(dbQuery);
                    response.redirect("/backhome");
                } else{
                    model.put("templates","/templates/delranger.vtl");
                }

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());

//        get("/update",(request,response)->{
//            Map<String,Object> model = new HashMap<String,Object>();
//            model.put("templates","/templates/updatefile.vtl");
//            model.put("stylists",HairSalonDB.allstylist());
//            try{
//            }catch(Exception e){
//                System.out.println(e.getMessage());
//            }
//            return new ModelAndView(model,layout);
//        }, new VelocityTemplateEngine());
//
//        //update stylist
//        post("/updateranger",(request,response)->{
//            Map<String,Object> model = new HashMap<String,Object>();
//            model.put("stylists",HairSalonDB.allstylist());
//            String stylistid = request.queryParams("stylistid");
//            hairdp.setStylistid(stylistid);
//            Integer mobile = Integer.parseInt (request.queryParams("mobile"));
//            hairdp.setMobile(mobile);
//            String email = request.queryParams("email");
//            hairdp.setEmail(email);
//            String password = request.queryParams("password");
//            byte[] pass = digest.digest(password.getBytes(StandardCharsets.UTF_8));
//            hairdp.setPassword(Arrays.toString(pass));
//            String df = HairSalonDB.select(hairdp);
//            try{
//                if (df.equals(stylistid)) {
//                    hb.updatestylist(hairdp);
//                }else{
//                    model.put("templates","/templates/notexist.vtl");
//                }
//
//                response.redirect("/update");
//            }catch(Exception e){
//                model.put("templates","/templates/notexist.vtl");
//                System.out.println(e.getMessage());
//            }
//            return new ModelAndView(model,layout);
//        }, new VelocityTemplateEngine());
//
//        //update customer
//        post("/updateanimal",(request,response)->{
//            Map<String,Object> model = new HashMap<String,Object>();
//            model.put("stylists",HairSalonDB.allstylist());
//            Integer mobile = Integer.parseInt (request.queryParams("mobile"));
//            hairdp.setMobile(mobile);
//            String email = request.queryParams("email");
//            hairdp.setEmail(email);
//            String clientid = request.queryParams("clientid");
//            hairdp.setCustomerid(clientid);
//            String stylistid = request.queryParams("stylistid");
//            hairdp.setStylistid(stylistid);
//            String df = HairSalonDB.selectcustomer(hairdp);
//            try{
//                if (df.equals(clientid)) {
//                    hb.updateclient(hairdp);
//                }else{
//                    model.put("templates","/templates/notexist.vtl");
//                }
//
//
//                response.redirect("/update");
//            }catch(Exception e){
//                System.out.println(e.getMessage());
//                model.put("templates","/templates/notexist.vtl");
//            }
//            return new ModelAndView(model,layout);
//        }, new VelocityTemplateEngine());
//
////view stylist customers
//        post("/delranger",(request,response)->{
//            Map<String,Object> model = new HashMap<String,Object>();
//            try{
//                String customerid = request.queryParams("delcustomer");
//                hairdp.setCustomerid(customerid);
//                String df = HairSalonDB.selectcustomer(hairdp);
//                if ( df != null) {
//                    hb.delcustomer(hairdp);
//                    response.redirect("/backhome");
//                } else{
//                    model.put("templates","/templates/delnot.vtl");
//                }
//
//            }catch(Exception e){
//                System.out.println(e.getMessage());
//            }
//
//
//            return new ModelAndView(model,layout);
//        }, new VelocityTemplateEngine());
//
//
//

    }
}
