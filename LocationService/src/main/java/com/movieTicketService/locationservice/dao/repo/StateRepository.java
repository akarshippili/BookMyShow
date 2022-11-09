package com.movieTicketService.locationservice.dao.repo;

import com.movieTicketService.locationservice.dao.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
