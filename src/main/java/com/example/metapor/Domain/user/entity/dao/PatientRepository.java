package com.example.metapor.Domain.user.entity.dao;

import com.example.metapor.Domain.user.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
