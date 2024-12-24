package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;
    @Autowired
    PetService petService;
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToEntity(customerDTO);
        Customer newCustomer = customerService.saveCustomer(customer);
        return convertEntitytoCustomerDTO(newCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return convertListtoCustomerDTO(customers);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPet(petId);
        Customer customer = customerService.findPet(pet);
        return convertEntitytoCustomerDTO(customer);

    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEntity(employeeDTO);
        Employee newEmployee = employeeService.saveEmployee(employee);
        return convertEntitytoEmployeeDTO(newEmployee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable Long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return convertEntitytoEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable Long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        employeeService.setEmployeeAvailability(employee, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        DayOfWeek dayOfWeek = employeeDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skills = employeeDTO.getSkills();

        List<Employee> availableEmployees = employeeService.getAvailableEmployeesForService(dayOfWeek,skills);
        return convertListtoEmployeeDTO(availableEmployees);
    }

    // Customer DTO conversions
    private Customer convertCustomerDTOToEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private CustomerDTO convertEntitytoCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPets() != null){
            customerDTO.setPetIds(customer.getPets().stream().map(Pet :: getId).collect(Collectors.toList()));
        }

        return customerDTO;
    }

    private List<CustomerDTO> convertListtoCustomerDTO(List<Customer> customers){
        List<CustomerDTO> customerDTO = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTO.add(convertEntitytoCustomerDTO(customer));

        }
        return customerDTO;
    }


    // Employee DTOs
    private Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private EmployeeDTO convertEntitytoEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employeeDTO, employeeDTO);
        return employeeDTO;
    }

    private List<EmployeeDTO> convertListtoEmployeeDTO(List<Employee> employees){
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTOS.add(convertEntitytoEmployeeDTO(employee));

        }
        return employeeDTOS;
    }
}
