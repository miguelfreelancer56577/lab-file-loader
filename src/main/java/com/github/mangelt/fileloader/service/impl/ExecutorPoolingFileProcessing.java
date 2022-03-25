package com.github.mangelt.fileloader.service.impl;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.github.mangelt.fileloader.service.FileProcessing;
import com.github.mangelt.fileloader.service.FileReading;
import com.github.mangelt.fileloader.service.FileUploading;

import lombok.extern.slf4j.Slf4j;

//Reference https://stackoverflow.com/questions/7939257/wait-until-all-threads-finish-their-work-in-java
//https://ducmanhphan.github.io/2020-03-20-Waiting-threads-to-finish-completely-in-Java/
@Slf4j
public class ExecutorPoolingFileProcessing implements FileProcessing{

	final FileUploading fileUploading;
	final FileReading fileReading;
	
	public ExecutorPoolingFileProcessing(FileUploading fileUploading, FileReading fileReading) {
		this.fileUploading = fileUploading;
		this.fileReading = fileReading;
	}
	
	@Override
	public void process() {
		final ExecutorService executorService = Executors.newCachedThreadPool();
		final List<File> items = fileReading.read();
		items
			.stream()
			.forEach(file->
				executorService.execute(()->fileUploading.upload(Collections.singletonList(file)))
			);
		executorService.shutdown();
		try {
			executorService.awaitTermination(2, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			log.error("There was an error to await threads.", e);
			Thread.currentThread().interrupt();
		}
	}
	
}
