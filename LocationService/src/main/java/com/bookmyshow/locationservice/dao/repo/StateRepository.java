package com.bookmyshow.locationservice.dao.repo;

import com.bookmyshow.locationservice.dao.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
