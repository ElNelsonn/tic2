package com.cascardo.backend.minio;

import io.minio.*;
import io.minio.errors.MinioException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @PostConstruct
    public void init() {

        try {
            for (String bucket : minioProperties.getBuckets().values()) {
                createIfNotExists(bucket);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error inicializando buckets", e);
        }
    }

    private void createIfNotExists(String bucket) throws MinioException {

        boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucket)
                        .build()
        );

        if (!exists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucket)
                            .build()
            );
        }
    }


    public String saveEventPhoto(Long eventId, MultipartFile photo) {

        String bucket = minioProperties.getBuckets().get("event-photos");

        String folder = String.valueOf(eventId);

        return uploadFile(photo, bucket, folder);
    }

    public void deleteEventPhoto(String objectName) {
        if (objectName == null || objectName.isBlank()) {
            return;
        }

        String bucket = minioProperties.getBuckets().get("event-photos");

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error borrando archivo en MinIO", e);
        }
    }


    private String uploadFile(MultipartFile file, String bucket, String folder) {

        try (InputStream inputStream = file.getInputStream()) {

            String objectName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            if (folder != null && !folder.isEmpty()) {
                objectName = folder + "/" + objectName;
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1L)
                            .contentType(file.getContentType())
                            .build()
            );

            return objectName;

        } catch (Exception e) {
            throw new RuntimeException("Error subiendo archivo", e);
        }
    }

    public String getPresignedUrl(String bucket, String objectName) {

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Http.Method.GET)
                            .bucket(bucket)
                            .object(objectName)
                            .expiry(60 * 60) // 1 hora
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error generando URL", e);
        }
    }







}
