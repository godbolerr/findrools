package com.work.findrools.service;

import com.work.findrools.service.dto.RiskProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RiskProfile.
 */
public interface RiskProfileService {

    /**
     * Save a riskProfile.
     *
     * @param riskProfileDTO the entity to save
     * @return the persisted entity
     */
    RiskProfileDTO save(RiskProfileDTO riskProfileDTO);

    /**
     *  Get all the riskProfiles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RiskProfileDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" riskProfile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RiskProfileDTO findOne(Long id);

    /**
     *  Delete the "id" riskProfile.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
