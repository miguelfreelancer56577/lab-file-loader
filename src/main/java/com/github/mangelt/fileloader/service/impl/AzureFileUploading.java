package com.github.mangelt.fileloader.service.impl;

import java.io.File;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.github.mangelt.fileloader.service.FileUploading;

public class AzureFileUploading implements FileUploading{

	AmazonS3 client;
	
	@Override
	public void upload(List<File> items) {
		
	}

}
