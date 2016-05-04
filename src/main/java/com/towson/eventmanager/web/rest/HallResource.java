package com.towson.eventmanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.towson.eventmanager.domain.Hall;
import com.towson.eventmanager.repository.HallRepository;
import com.towson.eventmanager.security.AuthoritiesConstants;
import com.towson.eventmanager.web.rest.util.HeaderUtil;
import com.towson.eventmanager.web.rest.util.PaginationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Hall.
 */
@RestController
@RequestMapping("/api")
public class HallResource {

    private final Logger log = LoggerFactory.getLogger(HallResource.class);
        
    @Inject
    private HallRepository hallRepository;
    
    /**
     * POST  /halls -> Create a new hall.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/halls",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Hall> createHall(@RequestBody Hall hall) throws URISyntaxException {
        if (hall.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hall", "idexists", "A new hall cannot already have an ID")).body(null);
        }
        Hall result = hallRepository.save(hall);
        return ResponseEntity.created(new URI("/api/halls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hall", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /halls -> Updates an existing hall.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/halls",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Hall> updateHall(@RequestBody Hall hall) throws URISyntaxException {
        log.debug("REST request to update Hall : {}", hall);
        if (hall.getId() == null) {
            return createHall(hall);
        }
        Hall result = hallRepository.save(hall);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hall", hall.getId().toString()))
            .body(result);
    }

    /**
     * GET  /halls -> get all the halls.
     */
    @RequestMapping(value = "/halls",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Hall>> getAllHalls(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Halls");
        Page<Hall> page = hallRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/halls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /halls/:id -> get the "id" hall.
     */
    @RequestMapping(value = "/halls/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Hall> getHall(@PathVariable String id) {
        log.debug("REST request to get Hall : {}", id);
        Hall hall = hallRepository.findOne(id);
        return Optional.ofNullable(hall)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /halls/:id -> delete the "id" hall.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/halls/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHall(@PathVariable String id) {
        log.debug("REST request to delete Hall : {}", id);
        hallRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hall", id.toString())).build();
    }
}
