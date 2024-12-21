package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToEntity(scheduleDTO);
        Schedule newSchedule = scheduleService.saveSchedule(schedule);
        return convertEntitytoScheduleDTO(newSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return convertListtoScheduleDTO(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        List <Schedule> schedule = scheduleService.findSchedule(pet);
        return convertListtoScheduleDTO(schedule);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        throw new UnsupportedOperationException();
    }

    // Schedule DTO conversions
    private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    private ScheduleDTO convertEntitytoScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        return scheduleDTO;
    }

    private List<ScheduleDTO> convertListtoScheduleDTO(List<Schedule> schedules){
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convertEntitytoScheduleDTO(schedule));

        }
        return scheduleDTOS;
    }
}
