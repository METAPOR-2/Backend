package com.example.metapor.Domain.event.entity.dao;

import com.example.metapor.Domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}