package com.towson.eventmanager.repository;

import com.towson.eventmanager.domain.Event;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Event entity.
 */
public interface EventRepository extends MongoRepository<Event,String> {

}
