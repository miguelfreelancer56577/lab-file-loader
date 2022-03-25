package com.github.mangelt.fileloader.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AwsConfig {
	
	final String accesskey;
	final String secretkey;
	final String region;
	
	public AwsConfig(String accesskey, String secretkey, String region) {
		this.accesskey = accesskey;
		this.secretkey = secretkey;
		this.region = region;
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
