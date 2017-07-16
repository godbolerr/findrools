package com.work.findrools.service.mapper;

import com.work.findrools.domain.*;
import com.work.findrools.service.dto.ProfSettingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProfSetting and its DTO ProfSettingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfSettingMapper extends EntityMapper <ProfSettingDTO, ProfSetting> {
    
    
    default ProfSetting fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfSetting profSetting = new ProfSetting();
        profSetting.setId(id);
        return profSetting;
    }
}
