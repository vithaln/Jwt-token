package com.vitu.test.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
	
	@Bean
	public ModelMapper mapper() {
		
		return new ModelMapper();
	}

}
