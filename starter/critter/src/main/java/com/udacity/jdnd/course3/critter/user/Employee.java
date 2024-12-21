package com.udacity.jdnd.course3.critter.user;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ElementCollection
    private Set<EmployeeSkill> skills;
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
