package controllers;

import db.DBHelper;
import models.Department;
import models.Employee;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class ManagersController {

    public ManagersController(){
        this.setupEndPoints();
    }

    private void setupEndPoints() {
        get("/managers", (req, res) -> {
            List<Manager> managers = DBHelper.getAll(Manager.class);
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/index.vtl");
            model.put("managers", managers);
            return new ModelAndView(model, "templates/layout.vtl");
        },
        new VelocityTemplateEngine());

        get("/managers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("template", "templates/managers/create.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        },
        new VelocityTemplateEngine());

        post("/managers", (req,res) ->{
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = req.queryParams("firstName");
            // this (above) refers to name="firstName" in managers/create.vtl
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            double budget = Double.parseDouble(req.queryParams("budget"));
            Manager manager = new Manager(firstName, lastName, salary, department, budget);
            DBHelper.save(manager);
            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());
    }
}
