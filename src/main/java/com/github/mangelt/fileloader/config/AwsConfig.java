package com.github.mangelt.fileloader.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.github.mangelt.fileloader.util.PropertyConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AwsConfig {
	
	final String accesskey;
	final String secretkey;
	final String region;
	
	public AwsConfig() {
		PropertyConfig pro = new PropertyConfig();
		accesskey = pro.getProperty(PropertyConstant.APP_CONFIG_AWS_ACCESS_KEY);
		secretkey = pro.getProperty(PropertyConstant.APP_CONFIG_AWS_SECRET_KEY);
		region = pro.getProperty(PropertyConstant.APP_CONFIG_AWS_REGION);
	}
	
	public AWSCredentials awsCredencials() {
		log.debug("Setting up aws credencials.");
		return new BasicAWSCredentials(accesskey, secretkey);
	}
	
	public AmazonS3 s3Client(AWSCredentials credentials) {
		log.debug("Setting up aws client.");
		return AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.valueOf(region))
				  .build();
	}
	
}
