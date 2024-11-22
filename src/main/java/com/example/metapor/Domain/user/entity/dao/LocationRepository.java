package com.example.metapor.Domain.user.entity.dao;

import com.example.metapor.Domain.user.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
