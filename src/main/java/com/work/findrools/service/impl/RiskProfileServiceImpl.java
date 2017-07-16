package com.work.findrools.service.impl;

import com.work.findrools.service.RiskProfileService;
import com.work.findrools.domain.RiskProfile;
import com.work.findrools.repository.RiskProfileRepository;
import com.work.findrools.service.dto.RiskProfileDTO;
import com.work.findrools.service.mapper.RiskProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RiskProfile.
 */
@Service
@Transactional
public class RiskProfileServiceImpl implements RiskProfileService{

    private final Logger log = LoggerFactory.getLogger(RiskProfileServiceImpl.class);

    private final RiskProfileRepository riskProfileRepository;

    private final RiskProfileMapper riskProfileMapper;

    public RiskProfileServiceImpl(RiskProfileRepository riskProfileRepository, RiskProfileMapper riskProfileMapper) {
        this.riskProfileRepository = riskProfileRepository;
        this.riskProfileMapper = riskProfileMapper;
    }

    /**
     * Save a riskProfile.
     *
     * @param riskProfileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RiskProfileDTO save(RiskProfileDTO riskProfileDTO) {
        log.debug("Request to save RiskProfile : {}", riskProfileDTO);
        RiskProfile riskProfile = riskProfileMapper.toEntity(riskProfileDTO);
        riskProfile = riskProfileRepository.save(riskProfile);
        return riskProfileMapper.toDto(riskProfile);
    }

    /**
     *  Get all the riskProfiles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RiskProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RiskProfiles");
        return riskProfileRepository.findAll(pageable)
            .map(riskProfileMapper::toDto);
    }

    /**
     *  Get one riskProfile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RiskProfileDTO findOne(Long id) {
        log.debug("Request to get RiskProfile : {}", id);
        RiskProfile riskProfile = riskProfileRepository.findOne(id);
        return riskProfileMapper.toDto(riskProfile);
    }

    /**
     *  Delete the  riskProfile by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RiskProfile : {}", id);
        riskProfileRepository.delete(id);
    }
}
