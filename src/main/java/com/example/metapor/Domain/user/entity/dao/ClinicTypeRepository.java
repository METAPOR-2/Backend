package com.example.metapor.Domain.user.entity.dao;

import com.example.metapor.Domain.user.entity.ClinicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClinicTypeRepository extends JpaRepository<ClinicType, Long> {
    @Query("select c from ClinicType c where c.title = ?1")
    ClinicType findByTitle(String title);
    @Query("select (count(c) > 0) from ClinicType c where c.title = ?1")
    boolean existsByTitle(String title);
}
