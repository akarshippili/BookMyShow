package com.ticketapp.userservice.dao.repo;

import com.ticketapp.userservice.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
