package com.ticketapp.locationservice.dao.repo;

import com.ticketapp.locationservice.dao.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
