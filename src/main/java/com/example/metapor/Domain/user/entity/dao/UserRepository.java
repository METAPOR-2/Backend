package com.example.metapor.Domain.user.entity.dao;

import com.example.metapor.Domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    @Override
    boolean existsById(String aLong);
}
