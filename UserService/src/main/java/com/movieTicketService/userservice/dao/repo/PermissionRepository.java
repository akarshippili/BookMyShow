package com.movieTicketService.userservice.dao.repo;

import com.movieTicketService.userservice.dao.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> { }
