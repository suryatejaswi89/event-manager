package com.towson.eventmanager.repository;

import com.towson.eventmanager.domain.Hall;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Hall entity.
 */
public interface HallRepository extends MongoRepository<Hall,String> {

}
