package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToEntity(scheduleDTO);

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();
        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();
        if(employeeIds!=null) {
            for (Long id : employeeIds) {
                employeeList.add(employeeService.getEmployee(id));
            }
        }
        if(petIds != null){
            for(Long id : petIds){
                petList.add(petService.getPet(id));
            }
        }
        schedule.setEmployees(employeeList);
        schedule.setPets(petList);
        schedule.setActivities(scheduleDTO.getActivities());
        scheduleService.saveSchedule(schedule);
        scheduleDTO.setId(schedule.getId());
        return scheduleDTO;
    }

    @GetMapping
    //Map Ids of Pets and Employees
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();

        List<ScheduleDTO> newScheduleList = new ArrayList<>();

        ScheduleDTO newSchedule;
        for (Schedule schedule : schedules) {
            newSchedule = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, newSchedule);
            newSchedule.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            List<Long> pets = new ArrayList<>();
            for (Pet p : schedule.getPets()) {
                pets.add(p.getId());
            }
            newSchedule.setPetIds(pets);
            newScheduleList.add(newSchedule);
        }
            return newScheduleList;
    }


        @GetMapping("/pet/{petId}")
        public List<ScheduleDTO> getScheduleForPet ( @PathVariable long petId){
            Pet pet = petService.getPet(petId);
            List<Schedule> scheduleForEntity = scheduleService.findScheduleForPet(pet);
            return getScheduleDTOS(scheduleForEntity);
        }


        @GetMapping("/employee/{employeeId}")
        public List<ScheduleDTO> getScheduleForEmployee ( @PathVariable long employeeId){
            Employee employee = employeeService.getEmployee(employeeId);
            List<Schedule> scheduleForEntity = scheduleService.findScheduleForEmployee(employee);
            return getScheduleDTOS(scheduleForEntity);
        }


        @GetMapping("/customer/{customerId}")
        public List<ScheduleDTO> getScheduleForCustomer ( @PathVariable long customerId){
            Customer customer = customerService.getCustomer(customerId);
            List<Schedule> scheduleForEntity = scheduleService.findScheduleForCustomer(customer);
            return getScheduleDTOS(scheduleForEntity);
        }

    private static List<ScheduleDTO> getScheduleDTOS(List<Schedule> scheduleForEntity) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        ScheduleDTO newSchedule;

        for(Schedule schedule : scheduleForEntity){
            newSchedule = new ScheduleDTO();
            newSchedule.setId(schedule.getId());
            newSchedule.setActivities(schedule.getActivities());
            newSchedule.setDate(schedule.getDate());

            newSchedule.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            List<Long> pets = new ArrayList<>();
            for(Pet p : schedule.getPets()){
                pets.add(p.getId());
            }
            newSchedule.setPetIds(pets);
            scheduleDTOList.add(newSchedule);
        }
        return scheduleDTOList;
    }

    // Schedule DTO conversions
        private Schedule convertScheduleDTOToEntity (ScheduleDTO scheduleDTO){
            Schedule schedule = new Schedule();
            BeanUtils.copyProperties(scheduleDTO, schedule);
            return schedule;
        }

        private ScheduleDTO convertEntitytoScheduleDTO (Schedule schedule){
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDTO);
            return scheduleDTO;
        }

        private List<ScheduleDTO> convertListtoScheduleDTO (List < Schedule > schedules) {
            List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
            for (Schedule schedule : schedules) {
                scheduleDTOS.add(convertEntitytoScheduleDTO(schedule));

            }
            return scheduleDTOS;
        }
    }
