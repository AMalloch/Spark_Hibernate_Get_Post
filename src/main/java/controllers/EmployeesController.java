package controllers;

import db.DBHelper;
import db.Seeds;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class EmployeesController {
    public static void main(String[] args) {
        staticFileLocation("public");
        ManagersController managersController = new ManagersController();
        Seeds.seedData();

        get("/employees", (req, res) -> {
            List<Employee> employees = DBHelper.getAll(Employee.class);
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/employees/index.vtl");
            model.put("employees", employees);
            return new ModelAndView(model, "templates/layout.vtl");
        },  new VelocityTemplateEngine());
    }
}
