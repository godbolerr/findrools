package com.work.findrools.repository;

import com.work.findrools.domain.RiskProfile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RiskProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskProfileRepository extends JpaRepository<RiskProfile,Long> {
    
}
