package com.udacity.jdnd.course3.critter.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeesByDaysAvailableAndSkills(DayOfWeek daysAvailable, Set<EmployeeSkill> skills);
}
