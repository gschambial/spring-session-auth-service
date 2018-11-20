package com.unsaidtalks.auth.service;

import java.util.List;
import java.util.Optional;

import com.unsaidtalks.auth.service.dto.UserMasterDTO;

/**
 * Service Interface for managing UserMaster.
 */
public interface UserMasterService {

    /**
     * Save a userMaster.
     *
     * @param userMasterDTO the entity to save
     * @return the persisted entity
     */
    UserMasterDTO save(UserMasterDTO userMasterDTO);

    /**
     * Get all the userMasters.
     *
     * @return the list of entities
     */
    List<UserMasterDTO> findAll();


    /**
     * Get the "id" userMaster.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserMasterDTO> findOne(String id);

    /**
     * Delete the "id" userMaster.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
