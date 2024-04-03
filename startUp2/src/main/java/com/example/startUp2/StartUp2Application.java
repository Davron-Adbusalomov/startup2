package com.example.startUp2;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.print.attribute.standard.Media;

@SpringBootApplication
public class StartUp2Application {

	public static void main(String[] args) {
		SpringApplication.run(StartUp2Application.class, args);
		System.out.println("Hello Team");

		// Retrieve the connection string for use with the application.
		String connectStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");
		if (connectStr != null && connectStr.startsWith("<") && connectStr.endsWith(">")) {
			connectStr = connectStr.substring(1, connectStr.length() - 1);
		}

        // Create a BlobServiceClient object using a connection string
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
				.connectionString(connectStr)
				.buildClient();

		// Create a unique name for the container
		String containerName = "startup2" + java.util.UUID.randomUUID();

        // Create the container and return a container client object
		BlobContainerClient blobContainerClient = blobServiceClient.createBlobContainer(containerName);
	}

}

