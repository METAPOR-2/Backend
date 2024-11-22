package com.example.metapor.Domain.user.entity.dao;

import com.example.metapor.Domain.user.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("""
            select d from Doctor d
            where d.hospitalName like concat('%', ?1, '%') or d.user.name like concat('%', ?1, '%')
            order by d.rating DESC""")
    List<Doctor> findByQuery(String query);

    @Query("select d from Doctor d order by d.rating DESC")
    List<Doctor> findByOrderByRatingDesc();

    @Query("""
            select d from Doctor d
            where (d.hospitalName like concat('%', ?1, '%') or d.user.name like concat('%', ?1, '%')) and d.patientNumberLowerLimit <= ?2 and d.patientNumberUpperLimit >= ?2 and d.startAbleDate <= ?3 and d.endAbleDate >= ?3
            order by d.rating DESC""")
    List<Doctor> findByQueryNumberDate(String query, Integer patientNumber, LocalDate ableDate);
}
