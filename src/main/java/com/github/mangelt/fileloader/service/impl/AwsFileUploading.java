package com.github.mangelt.fileloader.service.impl;

import java.io.File;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.github.mangelt.fileloader.service.FileUploading;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AwsFileUploading implements FileUploading{

	String bucketName;
	
	AmazonS3 client;
	
	public AwsFileUploading(String bucketName, AmazonS3 client) {
		this.bucketName = bucketName;
		this.client = client;
	}
	
	@Override
	public void upload(List<File> items) {
		items.stream().forEach(file->{
			try {
				log.info("Thead name: {}", Thread.currentThread().getName());
				log.info("Uploading object {}", file.getName());
				client.putObject(bucketName, file.getName(), file);
				log.info("Uploaded object {}", file.getName());
			} catch (Exception e) {
				log.error("There was an error to upload the file {} with exception {}", file.getAbsolutePath(), e);
			}
		});
	}

}
