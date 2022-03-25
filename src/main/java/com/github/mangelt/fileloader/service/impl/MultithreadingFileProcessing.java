package com.github.mangelt.fileloader.service.impl;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.mangelt.fileloader.service.FileProcessing;
import com.github.mangelt.fileloader.service.FileReading;
import com.github.mangelt.fileloader.service.FileUploading;

import lombok.extern.slf4j.Slf4j;

//Reference https://stackoverflow.com/questions/7939257/wait-until-all-threads-finish-their-work-in-java
//https://ducmanhphan.github.io/2020-03-20-Waiting-threads-to-finish-completely-in-Java/
@Slf4j
public class MultithreadingFileProcessing implements FileProcessing{

	final FileUploading fileUploading;
	final FileReading fileReading;
	
	public MultithreadingFileProcessing(FileUploading fileUploading, FileReading fileReading) {
		this.fileUploading = fileUploading;
		this.fileReading = fileReading;
	}
	
	@Override
	public void process() {
		final List<File> items = fileReading.read();
		final List<Thread> lstElements = items
			.stream()
			.map(file->new Thread(()->fileUploading.upload(Collections.singletonList(file))))
			.collect(Collectors.toList());
		
		lstElements.forEach(Thread::start);
		lstElements.forEach(thread->{
			try {
				log.info("Joining thread: {}", thread.getName());
				thread.join();
				log.info("Joined thread: {}", thread.getName());
			} catch (InterruptedException e) {
				thread.interrupt();
				e.printStackTrace();
			}
		});
	}
	
}
