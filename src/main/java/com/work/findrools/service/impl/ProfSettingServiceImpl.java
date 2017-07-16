package com.work.findrools.service.impl;

import com.work.findrools.service.ProfSettingService;
import com.work.findrools.domain.ProfSetting;
import com.work.findrools.repository.ProfSettingRepository;
import com.work.findrools.service.dto.ProfSettingDTO;
import com.work.findrools.service.mapper.ProfSettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ProfSetting.
 */
@Service
@Transactional
public class ProfSettingServiceImpl implements ProfSettingService{

    private final Logger log = LoggerFactory.getLogger(ProfSettingServiceImpl.class);

    private final ProfSettingRepository profSettingRepository;

    private final ProfSettingMapper profSettingMapper;

    public ProfSettingServiceImpl(ProfSettingRepository profSettingRepository, ProfSettingMapper profSettingMapper) {
        this.profSettingRepository = profSettingRepository;
        this.profSettingMapper = profSettingMapper;
    }

    /**
     * Save a profSetting.
     *
     * @param profSettingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProfSettingDTO save(ProfSettingDTO profSettingDTO) {
        log.debug("Request to save ProfSetting : {}", profSettingDTO);
        ProfSetting profSetting = profSettingMapper.toEntity(profSettingDTO);
        profSetting = profSettingRepository.save(profSetting);
        return profSettingMapper.toDto(profSetting);
    }

    /**
     *  Get all the profSettings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProfSettings");
        return profSettingRepository.findAll(pageable)
            .map(profSettingMapper::toDto);
    }

    /**
     *  Get one profSetting by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProfSettingDTO findOne(Long id) {
        log.debug("Request to get ProfSetting : {}", id);
        ProfSetting profSetting = profSettingRepository.findOne(id);
        return profSettingMapper.toDto(profSetting);
    }

    /**
     *  Delete the  profSetting by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfSetting : {}", id);
        profSettingRepository.delete(id);
    }
}
