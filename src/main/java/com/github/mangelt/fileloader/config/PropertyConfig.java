package com.github.mangelt.fileloader.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;

import com.github.mangelt.fileloader.util.AppConstant;
import com.github.mangelt.fileloader.util.PropertyConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyConfig {
	
	final Properties prop = new Properties();
	
	final Consumer<String> loadPropertiesFile = fileName->{
		final File external = new File(fileName);
		try(InputStream input = external.exists()?new FileInputStream(external): this.getClass().getClassLoader().getResourceAsStream(fileName)){
            prop.load(input);
        } catch (Exception ex) {
            log.error("There was an error to load your properties file.", ex);
        }
	};
	
	public PropertyConfig() {
		Optional
			.ofNullable(System.getProperty(PropertyConstant.APP_CONFIG_PROPERTY_FILE_NAME))
			.ifPresentOrElse(loadPropertiesFile, ()->loadPropertiesFile.accept(AppConstant.APP_PROPERTIES_NAME));
	}

	public String getProperty(String key) {
		return Optional
		.ofNullable(System.getProperty(key))
		.orElseGet(()->prop.getProperty(key, AppConstant.APP_EMPTY_STRING));
	}
	
}
