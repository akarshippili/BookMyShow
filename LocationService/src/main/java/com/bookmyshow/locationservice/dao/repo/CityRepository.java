package com.bookmyshow.locationservice.dao.repo;

import com.bookmyshow.locationservice.dao.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
