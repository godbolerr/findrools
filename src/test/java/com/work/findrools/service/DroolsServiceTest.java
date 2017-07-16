package com.work.findrools.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.work.findrools.FindroolsApp;
import com.work.findrools.rules.Driver;
import com.work.findrools.rules.Policy;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FindroolsApp.class)
public class DroolsServiceTest {
	
	@Autowired
	DroolsService dService;

	@Test
	public void testCalculateRisk() {
		Driver driver = new Driver();
		Policy policy = new Policy();
		
		String message  = dService.calculateRisk(driver, policy);
		
		System.out.println(message);
		
		
	}
	
	@Test
	public void testCalculateRiskForNewDriver() {
		Driver driver = new Driver();
		driver.setAge(22);
		Policy policy = new Policy();
		
		String message  = dService.calculateRiskForNewDrivers(driver, policy);
		
		System.out.println(message);
		
		
	}	
	
	@Test
	public void testCalculateRiskForNewDriverDecisionTable() {
		Driver driver = new Driver();
		Policy policy = new Policy();
		
		String message  = dService.calculateRiskForNewDriversDecisionTable(driver, policy);
		
		System.out.println(message);
		
		
	}		

}
