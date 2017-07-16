package com.work.findrools.service;

import com.work.findrools.rules.Driver;
import com.work.findrools.rules.Policy;

/**
 * Service Interface for managing Person.
 */
public interface DroolsService {
	
	public String calculateRisk(Driver d, Policy p);
	
	public String calculateRiskForNewDrivers(Driver d, Policy p);
	public String calculateRiskForNewDriversDecisionTable(Driver d, Policy p);

}
