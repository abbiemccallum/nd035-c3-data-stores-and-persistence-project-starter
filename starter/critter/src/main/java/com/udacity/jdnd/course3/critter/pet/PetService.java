package com.udacity.jdnd.course3.critter.pet;


import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    public List<Pet> getPets(List<Long> id){
        return petRepository.findAllById(id);
    }

    public Pet getPet(long id){
        return petRepository.findById(id).get();
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public Pet savePet(Pet pet, long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        pet.setCustomer(customer);
        return petRepository.save(pet);
    }

    public List<Pet> findPetsbyCustomer(long id){
        return petRepository.findAllByCustomerId(id);
    }


}
