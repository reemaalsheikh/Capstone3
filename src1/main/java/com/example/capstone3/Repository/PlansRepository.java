package com.example.capstone3.Repository;

import com.example.capstone3.Model.Plans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlansRepository extends JpaRepository<Plans, Integer> {
    Plans findPlansById(Integer planId);

    Plans findPlansByPlants(String plantName);

    @Query("SELECT p FROM Plans p JOIN p.consultant c WHERE c.specialization = :specialization")
    List<Plans> findPlansByConsultantSpecialization(String specialization);
}
