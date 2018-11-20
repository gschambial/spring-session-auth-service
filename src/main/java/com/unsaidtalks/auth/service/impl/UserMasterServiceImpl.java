package com.unsaidtalks.auth.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unsaidtalks.auth.domain.UserMaster;
import com.unsaidtalks.auth.repository.UserMasterRepository;
import com.unsaidtalks.auth.service.UserMasterService;
import com.unsaidtalks.auth.service.dto.UserMasterDTO;
import com.unsaidtalks.auth.service.mapper.UserMasterMapper;

/**
 * Service Implementation for managing UserMaster.
 */
@Service
@Transactional
public class UserMasterServiceImpl implements UserMasterService {

    private final Logger log = LoggerFactory.getLogger(UserMasterServiceImpl.class);

    private final UserMasterRepository userMasterRepository;

    private final UserMasterMapper userMasterMapper;

    public UserMasterServiceImpl(UserMasterRepository userMasterRepository, UserMasterMapper userMasterMapper) {
        this.userMasterRepository = userMasterRepository;
        this.userMasterMapper = userMasterMapper;
    }

    /**
     * Save a userMaster.
     *
     * @param userMasterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserMasterDTO save(UserMasterDTO userMasterDTO) {
        log.debug("Request to save UserMaster : {}", userMasterDTO);

        UserMaster userMaster = userMasterMapper.toEntity(userMasterDTO);
        userMaster = userMasterRepository.save(userMaster);
        return userMasterMapper.toDto(userMaster);
    }

    /**
     * Get all the userMasters.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserMasterDTO> findAll() {
        log.debug("Request to get all UserMasters");
        return userMasterRepository.findAll().stream()
            .map(userMasterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userMaster by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserMasterDTO> findOne(String id) {
        log.debug("Request to get UserMaster : {}", id);
        return userMasterRepository.findById(id)
            .map(userMasterMapper::toDto);
    }

    /**
     * Delete the userMaster by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete UserMaster : {}", id);
        userMasterRepository.deleteById(id);
    }
}
