/**
 * 
 */
package com.work.findrools.service.impl;

import java.util.Arrays;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.findrools.rules.Driver;
import com.work.findrools.rules.Policy;
import com.work.findrools.service.DroolsService;

@Service
public class DroolsServiceImpl implements DroolsService {

	@Autowired
	private KieContainer kc;

	/**
	 * 
	 */
	public DroolsServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.work.findrools.service.DroolsService#calculateRisk(com.work.findrools
	 * .rules.Driver, com.work.findrools.rules.Policy)
	 */
	@Override
	public String calculateRisk(Driver driver, Policy policy) {

		KieSession ksession = kc.newKieSession("RuleTemplateDataSession");

		ksession.insert(driver);
		ksession.insert(policy);
		
		ksession.fireAllRules();
		
		return "Executed Successfully " + policy + " for drive " + driver;
	}

	@Override
	public String calculateRiskForNewDrivers(Driver driver, Policy policy) {
		
		KieSession ksession = kc.newKieSession("FindDroolsSession");

		ksession.insert(driver);
		ksession.insert(policy);
		
		ksession.fireAllRules();
		
		return "Executed Successfully " + policy + " for drive " + driver;
	}

	@Override
	public String calculateRiskForNewDriversDecisionTable(Driver driver, Policy policy) {
		KieSession ksession = kc.newKieSession("FindDroolsSessionDecisionTable");

		ksession.insert(driver);
		ksession.insert(policy);
		
		ksession.fireAllRules();
		
		return "Executed Successfully " + policy + " for drive " + driver;
	}

}
