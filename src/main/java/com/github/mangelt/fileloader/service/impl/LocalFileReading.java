package com.github.mangelt.fileloader.service.impl;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.mangelt.fileloader.service.FileReading;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalFileReading implements FileReading {
	
	final String dirPath;
	
	public LocalFileReading(String dirPath) {
		this.dirPath = dirPath;
	}

	@Override
	public List<File> read() {
		try(var newDirectoryStream = Files.newDirectoryStream(Paths.get(dirPath))) {
			return convertToFile(newDirectoryStream);
		}catch (Exception e) {
			log.error("There was an error to read files from {} with exception {}", dirPath, e);
			return Collections.emptyList();
		}
	}
	
	List<File> convertToFile(DirectoryStream<Path> dirStream){
		final List<File> elements = new ArrayList<>();
		dirStream.forEach(item->{
			try {
				elements.add(item.toFile());
			} catch (Exception e) {
				log.error("There was an error to read file {} with exception {}", item.toString(), e);
			}
		});
		return elements;
	}

}
