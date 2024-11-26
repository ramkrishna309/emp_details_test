package com.emp.employee.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.emp.employee.dto.TaxDetailDto;
import com.emp.employee.model.Employee;

@Service
public class TaxCalculationService {

	public TaxDetailDto calculateTax(Employee employee) {
		double monthlySalary = employee.getMonthlySalary();
		LocalDate doj = employee.getDateOfJoining();
		int monthsWorked = calculateMonthsWorked(doj);

		double yearlySalary = monthlySalary * monthsWorked;

		double taxAmount = 0.0;
		double cessAmount = 0.0;

		// Calculate tax based on slabs
		if (yearlySalary <= 250000) {
			taxAmount = 0;
		} else if (yearlySalary <= 500000) {
			taxAmount = (yearlySalary - 250000) * 0.05;
		} else if (yearlySalary <= 1000000) {
			taxAmount = (250000) * 0.05 + (yearlySalary - 500000) * 0.10;
		} else {
			taxAmount = (250000) * 0.05 + (500000) * 0.10 + (yearlySalary - 1000000) * 0.20;
		}

		if (yearlySalary > 2500000) {
			cessAmount = (yearlySalary - 2500000) * 0.02;
		}

		TaxDetailDto taxDetail = new TaxDetailDto();
		taxDetail.setEmployeeId(employee.getEmployeeId());
		taxDetail.setFirstName(employee.getFirstName());
		taxDetail.setLastName(employee.getLastName());
		taxDetail.setYearlySalary(yearlySalary);
		taxDetail.setTaxAmount(taxAmount);
		taxDetail.setCessAmount(cessAmount);

		return taxDetail;
	}

	private int calculateMonthsWorked(LocalDate doj) {
		LocalDate currentDate = LocalDate.now();
		int monthsWorked = currentDate.getMonthValue() - doj.getMonthValue();
		if (currentDate.getDayOfMonth() < doj.getDayOfMonth()) {
			monthsWorked -= 1;
		}
		return monthsWorked > 0 ? monthsWorked : 1;
	}
}
