package com.bookmyshow.userservice.dao.repo;

import com.bookmyshow.userservice.dao.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> { }
