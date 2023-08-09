package com.diptopaul.blog.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.diptopaul.blog.entities.Comment;
import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.payloads.CommentDto;
import com.diptopaul.blog.payloads.UserDto;

//configuration for getting or autowiring the modelMapper bean object

@Configuration
public class ModelMapperConfig {
	
  @Bean
  public ModelMapper modelMapper() {
	ModelMapper modelMapper = new ModelMapper();
	//this setting will make sure that the modelMapper is able to map and thus convert the nested objects as well;
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
	
	//added this for user update issue
	modelMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE);
    modelMapper.getConfiguration().setFieldMatchingEnabled(true);
    modelMapper.getConfiguration().setSkipNullEnabled(true);
    //modelMapper.setNullValueMappingStrategy(MatchingStrategies.);
	
    return modelMapper;
  }
}
