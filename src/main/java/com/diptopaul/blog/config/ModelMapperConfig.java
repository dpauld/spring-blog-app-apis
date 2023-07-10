package com.diptopaul.blog.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//configuration for getting or autowiring the modelMapper bean object

@Configuration
public class ModelMapperConfig {
	
  @Bean
  public ModelMapper modelMapper() {
	ModelMapper modelMapper = new ModelMapper();
	//this setting will make sure that the modelMapper is able to map and thus convert the nested objects as well;
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    return modelMapper;
  }
}
