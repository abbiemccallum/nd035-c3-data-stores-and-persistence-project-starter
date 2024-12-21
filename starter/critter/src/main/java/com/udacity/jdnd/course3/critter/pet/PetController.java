package com.udacity.jdnd.course3.critter.pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToEntity(petDTO);
        Pet newPet = petService.savePet(pet, petDTO.getOwnerId());
        return convertEntitytoPetDTO(newPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        return convertEntitytoPetDTO(pet);    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return convertListtoPetDTO(pets);    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pet = petService.findPetsbyCustomer(ownerId);
        return convertListtoPetDTO(pet);
    }


    // Pet DTO conversions
    private Pet convertPetDTOToEntity(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    private PetDTO convertEntitytoPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }

    private List<PetDTO> convertListtoPetDTO(List<Pet> pets){
        List<PetDTO> petDTOS = new ArrayList<>();
        for (Pet pet : pets) {
            petDTOS.add(convertEntitytoPetDTO(pet));

        }
        return petDTOS;
    }
}
