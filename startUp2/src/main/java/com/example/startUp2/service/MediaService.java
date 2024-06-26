package com.example.startUp2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import java.io.IOException;

@Service
public class MediaService {
    String connectionString = "DefaultEndpointsProtocol=https;AccountName=startuptwo;AccountKey=2IrSoCHZrpjqFOTa4l7LNQe8QWv/7Ho5miRdPJzOuVm+EBQAaDdGk+ZjnsLsUaxVu0i4SXE371cP+AStQTzcJw==;EndpointSuffix=core.windows.net";
    private final BlobServiceClient blobServiceClient=new BlobServiceClientBuilder()
            .connectionString(connectionString)
            .buildClient();;

    public String uploadMedia(MultipartFile file) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("startup2");

        String mediaName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        try {
            BlobClient blobClient = containerClient.getBlobClient(mediaName);
            blobClient.upload(file.getInputStream(), file.getSize(), true);

            return blobClient.getBlobUrl();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
