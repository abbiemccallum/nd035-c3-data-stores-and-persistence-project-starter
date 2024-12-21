package com.udacity.jdnd.course3.critter.schedule;


import com.udacity.jdnd.course3.critter.pet.Pet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    public List<Schedule> findScheduleByPet(Pet pet);


}
