package com.github.mangelt.fileloader.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.github.mangelt.fileloader.util.AppConstant;
import com.github.mangelt.fileloader.util.PropertyConstant;

@ExtendWith(MockitoExtension.class)
class AwsConfigTest {
	
	@BeforeAll
	static void setUp() {
		System.setProperty(PropertyConstant.APP_CONFIG_AWS_ACCESS_KEY, AppConstant.APP_EMPTY_STRING);
		System.setProperty(PropertyConstant.APP_CONFIG_AWS_SECRET_KEY, AppConstant.APP_EMPTY_STRING);
	}
	
	@Test
	void getS3Client() {
		final PropertyConfig pro = new PropertyConfig();
		final AwsConfig aws = new AwsConfig(
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_ACCESS_KEY), 
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_SECRET_KEY), 
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_REGION));
		final AWSCredentials credentials = aws.awsCredencials();
		final AmazonS3 s3Client = aws.s3Client(credentials);
		assertThat(s3Client).isNotNull();
	}
	
}
