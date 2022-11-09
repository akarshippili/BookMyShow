package com.movieTicketService.locationservice.dao.repo;

import com.movieTicketService.locationservice.dao.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
