package com.work.findrools.config;

import java.io.IOException;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {


    @Bean
    public KieContainer kieContainer() throws IOException {
    	KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
    	return kc;
    }

}