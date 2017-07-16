package com.work.findrools.service;

import com.work.findrools.service.dto.ProfSettingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProfSetting.
 */
public interface ProfSettingService {

    /**
     * Save a profSetting.
     *
     * @param profSettingDTO the entity to save
     * @return the persisted entity
     */
    ProfSettingDTO save(ProfSettingDTO profSettingDTO);

    /**
     *  Get all the profSettings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProfSettingDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" profSetting.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProfSettingDTO findOne(Long id);

    /**
     *  Delete the "id" profSetting.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
