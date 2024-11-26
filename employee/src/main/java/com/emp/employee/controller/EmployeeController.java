package com.emp.employee.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.employee.dto.TaxDetailDto;
import com.emp.employee.model.Employee;
import com.emp.employee.service.EmployeeService;
import com.emp.employee.service.TaxCalculationService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private TaxCalculationService taxCalculationService;

	@PostMapping("/employee")
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
		Employee savedEmployee = employeeService.saveEmployee(employee);
		return ResponseEntity.ok(savedEmployee);
	}

	@GetMapping("/employee/tax")
	public ResponseEntity<List<TaxDetailDto>> calculateTaxForAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		List<TaxDetailDto> taxDetails = employees.stream().map(taxCalculationService::calculateTax).toList();
		return ResponseEntity.ok(taxDetails);
	}

	@GetMapping("/employee/tax/{employeeId}")
	public ResponseEntity<TaxDetailDto> calculateTaxForEmployee(@PathVariable String employeeId) {
		Employee employee = employeeService.getEmployeeById(employeeId);
		if (employee == null) {
			return ResponseEntity.notFound().build();
		}
		TaxDetailDto taxDetail = taxCalculationService.calculateTax(employee);
		return ResponseEntity.ok(taxDetail);
	}
}
