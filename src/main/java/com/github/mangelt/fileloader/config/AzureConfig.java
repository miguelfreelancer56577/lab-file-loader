package com.github.mangelt.fileloader.config;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.microsoft.azure.storage.CloudStorageAccount;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AzureConfig {

	protected String connectionString;
	protected String containerName;
	
	public AzureConfig(String connectionString, String containerName) {
		this.connectionString = connectionString;
		this.containerName = containerName;
	}
	
	public BlobServiceClient storageClient()
	{
		log.debug("Setting up azure storage client.");
		return new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
	}
	
	public BlobContainerClient  blobContainer(BlobServiceClient storageClient)
	{
		log.debug("Setting up azure blob client.");
		return storageClient.getBlobContainerClient(containerName);
	}
	
	public CloudStorageAccount  cloudStorageAccount() throws InvalidKeyException, URISyntaxException {
		log.debug("Setting up azure blob client.");
		return CloudStorageAccount.parse(connectionString);
	}
	
}
