package com.example.metapor.Domain.event.entity.dao;

import com.example.metapor.Domain.event.entity.Event;
import com.example.metapor.Domain.user.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e where e.doctor = ?1")
    List<Event> findByDoctor(Doctor doctor);

    @Query("select e from Event e where e.doctor = ?1 and e.isAccepted = true")
    List<Event> findByDoctorAndIsAccepted(Doctor doctor);

    @Query("select e from Event e where e.doctor = ?1 and e.isAccepted is null")
    List<Event> findByDoctorAndPending(Doctor doctor);

}