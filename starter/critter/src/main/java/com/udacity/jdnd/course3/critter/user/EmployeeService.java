package com.udacity.jdnd.course3.critter.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Long id){
        return employeeRepository.findById(id).get();
    }

    public void setEmployeeAvailability(Employee employee, Set<DayOfWeek> daysAvailable){
        employee.setDaysAvailable(daysAvailable);
    }
    }
