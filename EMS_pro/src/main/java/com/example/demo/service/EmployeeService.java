package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Save
    public void saveEmployee(Employee employee) {
        repository.save(employee);
    }

    // Get All
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // Get By Id  👈 ADD THIS
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // Delete
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}