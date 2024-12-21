package com.udacity.jdnd.course3.critter.pet;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PetRepository extends JpaRepository<Pet, Long>{
    List <Pet> findAllByCustomerId(long id);


}
