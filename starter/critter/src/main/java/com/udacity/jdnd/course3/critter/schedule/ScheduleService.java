package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }
    public List<Schedule> findScheduleForPet(Pet pet) {
        return scheduleRepository.findSchedulesByPets(pet);
    }

    public List<Schedule> findScheduleForEmployee(Employee employee) {
        return scheduleRepository.findSchedulesByEmployees(employee);
    }

    public List<Schedule> findScheduleForCustomer(Customer customer) {
        List<Pet> pets = customer.getPets();
        List<Schedule> schedules = new ArrayList<>();

        for (Pet pet : pets) {
            schedules.addAll(scheduleRepository.findSchedulesByPets(pet));
        }
        return schedules;
    }
}
