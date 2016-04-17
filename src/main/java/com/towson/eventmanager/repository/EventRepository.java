package com.towson.eventmanager.repository;


import com.towson.eventmanager.domain.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Event entity.
 */
public interface EventRepository extends MongoRepository<Event,String> {
	Page<Event> findByVenue(String venue, Pageable pageable);
}
