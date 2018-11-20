package com.unsaidtalks.auth.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unsaidtalks.auth.service.UserMasterService;
import com.unsaidtalks.auth.service.dto.UserMasterDTO;
import com.unsaidtalks.auth.web.rest.errors.BadRequestAlertException;
import com.unsaidtalks.auth.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing UserMaster.
 */
@RestController
@RequestMapping("/api")
public class UserMasterResource {

    private final Logger log = LoggerFactory.getLogger(UserMasterResource.class);

    private static final String ENTITY_NAME = "authServiceUserMaster";

    private final UserMasterService userMasterService;

    public UserMasterResource(UserMasterService userMasterService) {
        this.userMasterService = userMasterService;
    }

    /**
     * POST  /user-masters : Create a new userMaster.
     *
     * @param userMasterDTO the userMasterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userMasterDTO, or with status 400 (Bad Request) if the userMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-masters")
    @Timed
    public ResponseEntity<UserMasterDTO> createUserMaster(@Valid @RequestBody UserMasterDTO userMasterDTO) throws URISyntaxException {
        log.debug("REST request to save UserMaster : {}", userMasterDTO);
        if (userMasterDTO.getId() == null) {
            throw new BadRequestAlertException("A new userMaster must have an ID", ENTITY_NAME, "idexists");
        }
        UserMasterDTO result = userMasterService.save(userMasterDTO);
        return ResponseEntity.created(new URI("/api/user-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-masters : Updates an existing userMaster.
     *
     * @param userMasterDTO the userMasterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userMasterDTO,
     * or with status 400 (Bad Request) if the userMasterDTO is not valid,
     * or with status 500 (Internal Server Error) if the userMasterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-masters")
    @Timed
    public ResponseEntity<UserMasterDTO> updateUserMaster(@Valid @RequestBody UserMasterDTO userMasterDTO) throws URISyntaxException {
        log.debug("REST request to update UserMaster : {}", userMasterDTO);
        if (userMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserMasterDTO result = userMasterService.save(userMasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-masters : get all the userMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userMasters in body
     */
    @GetMapping("/user-masters")
    @Timed
    public List<UserMasterDTO> getAllUserMasters() {
    	System.out.println("REST request to get all UserMasters");
        log.debug("REST request to get all UserMasters");
        return userMasterService.findAll();
    }

    /**
     * GET  /user-masters/:id : get the "id" userMaster.
     *
     * @param id the id of the userMasterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userMasterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-masters/{id}")
    @Timed
    public ResponseEntity<UserMasterDTO> getUserMaster(@PathVariable String id) {
        log.debug("REST request to get UserMaster : {}", id);
        Optional<UserMasterDTO> userMasterDTO = userMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userMasterDTO);
    }

    /**
     * DELETE  /user-masters/:id : delete the "id" userMaster.
     *
     * @param id the id of the userMasterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserMaster(@PathVariable String id) {
        log.debug("REST request to delete UserMaster : {}", id);
        userMasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
