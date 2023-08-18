package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	//get all employees list
	@GetMapping("/employees")
	public List<Employee> getAllEmployeep(){
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employee")
	Employee newEmployee (@RequestBody Employee newEmployee) {
		return employeeRepository.save(newEmployee);
		
	}
	
	@GetMapping("/employee/{id}")
	Employee getEmployeeById(@PathVariable Long id) {
		return employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));
		
	}
	
	@PutMapping("/employee/{id}")
	Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
	    return employeeRepository.findById(id).map(employee -> {
	        employee.setFirstName(newEmployee.getFirstName());
	        employee.setLastName(newEmployee.getLastName());
	        employee.setEmailId(newEmployee.getEmailId());
	        return employeeRepository.save(employee);
	    }).orElseThrow(() -> new ResourceNotFoundException(id));	
	}
	
	@DeleteMapping("employee/{id}")
	String deleteEmployee(@PathVariable Long id) {
		if(!employeeRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		employeeRepository.deleteById(id);
		return "employee with id " + id + " has been deleted successfully.";
	}

	
}
