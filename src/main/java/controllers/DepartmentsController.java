package controllers;

import db.DBHelper;
import models.Department;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class DepartmentsController {

    public DepartmentsController(){
        this.setupEndPoints();
    }

    private void setupEndPoints() {
        get("/departments", (req, res) -> {
            List<Department> departments = DBHelper.getAll(Department.class);
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/index.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
            }, new VelocityTemplateEngine());

        get("/departments/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
            }, new VelocityTemplateEngine());

        get("/departments/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/delete.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/departments", (req,res) ->{
            String title = req.queryParams("title");
            Department department = new Department(title);
            DBHelper.save(department);
            res.redirect("/departments");
            return null;
        }, new VelocityTemplateEngine());


//        delete("/departments/delete/:id", (req,res) ->{
//            Integer id = Integer.parseInt(req.params(":id"));
//            Department department = DBHelper.find(id, Department.class);
//            DBHelper.delete(department);
//            res.redirect("/departments");
//            return null;
//        }, new VelocityTemplateEngine());


//        get("/departments/:id", (req, res) -> {
//            Integer id = Integer.parseInt(req.params(":id"));
//            Department department = DBHelper.find(id, Department.class);
//            Map<String, Object> model = new HashMap<>();
//            model.put("template", "templates/departments/index.vtl");
//            model.put("departments", department);
//            return new ModelAndView(model, "templates/layout.vtl");
//        }, new VelocityTemplateEngine());


//        put("/departments/:id", (req, res) -> {
//            Integer id = Integer.parseInt(req.params(":id"));
//            Department department = DBHelper.find(id, Department.class);
//            DBHelper.save(department);
//            Map<String, Object> model = new HashMap<>();
//            model.put("template", "templates/departments/index.vtl");
//            model.put("departments", department);
//            return new ModelAndView(model, "templates/layout.vtl");
//        }, new VelocityTemplateEngine());

    }
}
