package com.github.mangelt.fileloader.service.impl;

import java.io.File;
import java.util.List;

import com.github.mangelt.fileloader.service.FileProcessing;
import com.github.mangelt.fileloader.service.FileReading;
import com.github.mangelt.fileloader.service.FileUploading;

public class SynchronousFileProcessing implements FileProcessing{

	final FileUploading fileUploading;
	final FileReading fileReading;
	
	public SynchronousFileProcessing(FileUploading fileUploading, FileReading fileReading) {
		this.fileUploading = fileUploading;
		this.fileReading = fileReading;
	}
	
	@Override
	public void process() {
		final List<File> items = fileReading.read();
		fileUploading.upload(items);
	}
	
}
