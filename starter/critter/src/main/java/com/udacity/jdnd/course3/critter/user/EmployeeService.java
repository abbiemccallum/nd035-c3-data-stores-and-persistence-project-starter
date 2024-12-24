package com.udacity.jdnd.course3.critter.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
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

    public void setEmployeeAvailability(Long employeeId, Set<DayOfWeek> daysAvailable){
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setDaysAvailable(daysAvailable);
    }

    public List<Employee> getAvailableEmployeesForService(DayOfWeek daysAvailable, Set<EmployeeSkill> employeeSkillSet ) {
        List<Employee> employees = employeeRepository.findEmployeesByDaysAvailableAndSkills(daysAvailable, employeeSkillSet);
        List<Employee> availableEmployee = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSkills().containsAll(employeeSkillSet)) {
                availableEmployee.add(employee);
            }
        }
        return availableEmployee;
    }

    }
