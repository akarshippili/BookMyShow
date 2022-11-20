package com.ticketapp.userservice.dao.repo;

import com.ticketapp.userservice.dao.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> { }
