package com.movieTicketService.userservice.dao.repo;

import com.movieTicketService.userservice.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
