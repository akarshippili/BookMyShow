package com.ticketapp.userservice.dao.repo;

import com.ticketapp.userservice.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> { }
