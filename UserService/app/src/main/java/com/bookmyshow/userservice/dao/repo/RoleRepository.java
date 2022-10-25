package com.bookmyshow.userservice.dao.repo;

import com.bookmyshow.userservice.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> { }
