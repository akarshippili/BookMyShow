package com.movieTicketService.userservice.dao.repo;

import com.movieTicketService.userservice.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> { }
