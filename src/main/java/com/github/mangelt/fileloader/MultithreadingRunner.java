package com.github.mangelt.fileloader;

import java.time.Duration;
import java.time.Instant;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.github.mangelt.fileloader.config.AwsConfig;
import com.github.mangelt.fileloader.config.PropertyConfig;
import com.github.mangelt.fileloader.service.FileProcessing;
import com.github.mangelt.fileloader.service.FileReading;
import com.github.mangelt.fileloader.service.FileUploading;
import com.github.mangelt.fileloader.service.impl.AwsFileUploading;
import com.github.mangelt.fileloader.service.impl.ExecutorPoolingFileProcessing;
import com.github.mangelt.fileloader.service.impl.LocalFileReading;
import com.github.mangelt.fileloader.service.impl.MultithreadingFileProcessing;
import com.github.mangelt.fileloader.util.PropertyConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultithreadingRunner {

	public static void main(String[] args){
		
		final PropertyConfig pro = new PropertyConfig();
		
		final MultithreadingRunner synchronousRunner = new MultithreadingRunner();
		
		synchronousRunner.executorPoolingProcessUsingAwsProvider(pro);
		
	}
	
	void multithreadingProcessUsingAwsProvider(PropertyConfig pro) {
		
		final Instant start = Instant.now();
		
		log.info("Starting multithreading process loading at {}", start.toString());
		
		final AwsConfig awsConfig = new AwsConfig(
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_ACCESS_KEY), 
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_SECRET_KEY), 
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_REGION));
		
		final AWSCredentials awsCredencials = awsConfig.awsCredencials();
		final AmazonS3 s3Client = awsConfig.s3Client(awsCredencials);
		
		final FileUploading fileUploading = new AwsFileUploading(
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_BUCKET_NAME),
				s3Client); 
		
		final FileReading fileReading = new LocalFileReading(pro.getProperty(PropertyConstant.APP_CONFIG_DIRECTORY_PATH));
		final FileProcessing fileProcessing = new MultithreadingFileProcessing(fileUploading, fileReading);
		
		fileProcessing.process();
		
		Instant finish = Instant.now();   
		
		log.info("Finishing multithreading process loading at  {}", finish.toString());
		
		log.info("Elapsed Time in seconds: {}", Duration.between(start, finish).toSeconds());
		
	}
	
	void executorPoolingProcessUsingAwsProvider(PropertyConfig pro) {
		
		final Instant start = Instant.now();
		
		log.info("Starting executor pooling process loading at {}", start.toString());
		
		final AwsConfig awsConfig = new AwsConfig(
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_ACCESS_KEY), 
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_SECRET_KEY), 
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_REGION));
		
		final AWSCredentials awsCredencials = awsConfig.awsCredencials();
		final AmazonS3 s3Client = awsConfig.s3Client(awsCredencials);
		
		final FileUploading fileUploading = new AwsFileUploading(
				pro.getProperty(PropertyConstant.APP_CONFIG_AWS_BUCKET_NAME),
				s3Client); 
		
		final FileReading fileReading = new LocalFileReading(pro.getProperty(PropertyConstant.APP_CONFIG_DIRECTORY_PATH));
		final FileProcessing fileProcessing = new ExecutorPoolingFileProcessing(fileUploading, fileReading);
		
		fileProcessing.process();
		
		Instant finish = Instant.now();   
		
		log.info("Finishing executor pooling process loading at  {}", finish.toString());
		
		log.info("Elapsed Time in seconds: {}", Duration.between(start, finish).toSeconds());
		
	}

}
