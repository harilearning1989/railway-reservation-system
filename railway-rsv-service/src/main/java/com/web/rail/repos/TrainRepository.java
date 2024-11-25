package com.web.rail.repos;

import com.web.rail.models.TrainDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<TrainDetails, Long> {

    List<TrainDetails> findBySourceAndDestination(String source, String destination);
}
