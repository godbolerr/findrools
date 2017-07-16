package com.work.findrools.repository;

import com.work.findrools.domain.ProfSetting;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProfSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfSettingRepository extends JpaRepository<ProfSetting,Long> {
    
}
