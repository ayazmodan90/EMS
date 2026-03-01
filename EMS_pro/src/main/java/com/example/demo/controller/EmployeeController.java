package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // Show Records
    @GetMapping("/showRecords")
    public String showRecords(Model model) {
        model.addAttribute("listEmployees", service.getAllEmployees());
        return "employee/showRecords";
    }

    // Add Page
    @GetMapping("/addEmployee")
    public String addForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/addEmployee";
    }

    // Save
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee) {
        service.saveEmployee(employee);
        return "redirect:/showRecords";
    }

    // Edit
    @GetMapping("/showFormForUpdate/{id}")
    public String updateForm(@PathVariable Long id, Model model) {

        Employee employee = service.getEmployeeById(id);
        model.addAttribute("employee", employee);

        return "employee/addEmployee";
    }

    // Delete
    @GetMapping("/deleteEmployee/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteEmployee(id);
        return "redirect:/showRecords";
    }
    
}