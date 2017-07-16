package com.work.findrools.service.mapper;

import com.work.findrools.domain.*;
import com.work.findrools.service.dto.RiskProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RiskProfile and its DTO RiskProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RiskProfileMapper extends EntityMapper <RiskProfileDTO, RiskProfile> {
    
    
    default RiskProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        RiskProfile riskProfile = new RiskProfile();
        riskProfile.setId(id);
        return riskProfile;
    }
}
