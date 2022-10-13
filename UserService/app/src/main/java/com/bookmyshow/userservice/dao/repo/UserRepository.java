package com.bookmyshow.userservice.dao.repo;

import com.bookmyshow.userservice.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
