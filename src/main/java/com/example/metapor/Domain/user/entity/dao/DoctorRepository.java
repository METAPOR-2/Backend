package com.example.metapor.Domain.user.entity.dao;

import com.example.metapor.Domain.user.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
