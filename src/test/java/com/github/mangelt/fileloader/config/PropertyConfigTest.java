package com.github.mangelt.fileloader.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.mangelt.fileloader.util.PropertyConstant;

@ExtendWith(MockitoExtension.class)
class PropertyConfigTest {
	
	static String FAKE_PROPERTIES_FILE_NAME = "fake.properties";
	
	@BeforeAll
	static void setUp() {
		System.setProperty(PropertyConstant.APP_CONFIG_PROPERTY_FILE_NAME, FAKE_PROPERTIES_FILE_NAME);
	} 

	@Test
	void validateEmptyProperty() {
		PropertyConfig propertyConfig = new PropertyConfig();
		assertThat(propertyConfig.getProperty(PropertyConstant.APP_CONFIG_AWS_REGION)).isBlank();
	}
	
}
