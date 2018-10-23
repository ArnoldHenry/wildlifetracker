import spark.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.get;
import static spark.Spark.post;

public class wildlifetracker {
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        setPort(port);

        String layout = "templates/layout.vtl";
        staticFileLocation ("/public");
        DBQuery dbQuery = new DBQuery();

        get("/rangerform",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("template","/templates/rangerform.vtl");
            return new ModelAndView(model,layout);
        },new VelocityTemplateEngine());

        get("/rangerview",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("template","/templates/rangerform.vtl");
            model.put("stylists",DBQuery.allranger());
            return new ModelAndView(model,layout);
        },new VelocityTemplateEngine());

        post("/",((request, response) ->{
            Map<String,Object> model = new HashMap<String,Object>();

            String rangerid = request.queryParams("rangerid");
            dbQuery.setRanger(rangerid);
            String fname = request.queryParams("fname");
            dbQuery.setFname(fname);
            String sname = request.queryParams("sname");
            dbQuery.setName(sname);
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
            response.redirect("/backhome");

            return new  ModelAndView(model,layout);
        },new VelocityTemplateEngine());

        post("/customer",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();

            String fname = request.queryParams("fname");
            hairdp.setFname(fname);
            String sname = request.queryParams("sname");
            hairdp.setSname(sname);
            String lname = request.queryParams("lname");
            hairdp.setLname(lname);
            Integer mobile = Integer.parseInt(request.queryParams("mobile"));
            hairdp.setMobile(mobile);
            String gender = request.queryParams("gender");
            hairdp.setGender(gender);
            String email = request.queryParams("email");
            hairdp.setEmail(email);
            String customerid = request.queryParams("clientid");
            hairdp.setCustomerid(customerid);
            String stylistid = request.queryParams("stylistid");
            hairdp.setStylistid(stylistid);

            hb.savecustomer(hairdp);
            response.redirect("/backhome");

            return new  ModelAndView(model,layout);
        },new VelocityTemplateEngine());

        get("/viewstylist",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            try{
                model.put("stylists",HairSalonDB.all());
                model.put("template","/templates/records.vtl");
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());

        get("/viewcustomers",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            try{
                model.put("customers",HairSalonDB.allcustomers());
                model.put("template","/templates/customers.vtl");
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());

        //go to update page

        get("/update",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("template","/templates/updatefile.vtl");
            model.put("stylists",HairSalonDB.allstylist());
            try{
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());

        //update stylist
        post("/updatestylist",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("stylists",HairSalonDB.allstylist());
            String stylistid = request.queryParams("stylistid");
            hairdp.setStylistid(stylistid);
            Integer mobile = Integer.parseInt (request.queryParams("mobile"));
            hairdp.setMobile(mobile);
            String email = request.queryParams("email");
            hairdp.setEmail(email);
            String password = request.queryParams("password");
            byte[] pass = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            hairdp.setPassword(Arrays.toString(pass));
            String df = HairSalonDB.select(hairdp);
            try{
                if (df.equals(stylistid)) {
                    hb.updatestylist(hairdp);
                }else{
                    model.put("template","/templates/notexist.vtl");
                }

                response.redirect("/update");
            }catch(Exception e){
                model.put("template","/templates/notexist.vtl");
                System.out.println(e.getMessage());
            }
            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());

        //update customer
        post("/updatecustomer",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("stylists",HairSalonDB.allstylist());
            Integer mobile = Integer.parseInt (request.queryParams("mobile"));
            hairdp.setMobile(mobile);
            String email = request.queryParams("email");
            hairdp.setEmail(email);
            String clientid = request.queryParams("clientid");
            hairdp.setCustomerid(clientid);
            String stylistid = request.queryParams("stylistid");
            hairdp.setStylistid(stylistid);
            String df = HairSalonDB.selectcustomer(hairdp);
            try{
                if (df.equals(clientid)) {
                    hb.updateclient(hairdp);
                }else{
                    model.put("template","/templates/notexist.vtl");
                }


                response.redirect("/update");
            }catch(Exception e){
                System.out.println(e.getMessage());
                model.put("template","/templates/notexist.vtl");
            }
            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());

//view stylist customers

        get("/stylistcustomers",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();

            try{
//                model.put("customers",HairSalonDB.stylistcustomers());
                model.put("template","/templates/stylistpage.vtl");
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());


        post("/delcustomer",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            try{
                String customerid = request.queryParams("delcustomer");
                hairdp.setCustomerid(customerid);
                String df = HairSalonDB.selectcustomer(hairdp);
                if ( df != null) {
                    hb.delcustomer(hairdp);
                    response.redirect("/backhome");
                } else{
                    model.put("template","/templates/delnot.vtl");
                }

            }catch(Exception e){
                System.out.println(e.getMessage());
            }


            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());



        post("/delstylist",(request,response)->{
            Map<String,Object> model = new HashMap<String,Object>();
            try{
                String stylistid = request.queryParams("delstylist");
                hairdp.setStylistid(stylistid);
                String df = HairSalonDB.select(hairdp);
                if ( df != null) {
                    hb.delstylist(hairdp);
                    response.redirect("/backhome");
                } else{
                    model.put("template","/templates/delnot.vtl");
                    System.out.println(HairSalonDB.select(hairdp));
                }

            }catch(Exception e){
                System.out.println(e.getMessage());
            }


            return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());
    }
}
