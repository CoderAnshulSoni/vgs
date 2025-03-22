package com.vgs.vocation_guidance.repository;

import com.vgs.vocation_guidance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
