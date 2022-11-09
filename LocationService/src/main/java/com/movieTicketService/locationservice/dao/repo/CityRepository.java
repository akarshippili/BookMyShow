package com.movieTicketService.locationservice.dao.repo;

import com.movieTicketService.locationservice.dao.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
