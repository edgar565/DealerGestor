/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.storage;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;

@Service
public class S3FileStorageService implements FileStorageService {
//
//    private final AmazonS3 s3;
//    private final String bucketName;
//
//    public S3FileStorageService(AmazonS3 s3,
//                                @Value("${aws.s3.bucket}") String bucketName) {
//        this.s3 = s3;
//        this.bucketName = bucketName;
//    }
//
//    @Override
//    public String storeFile(MultipartFile file, String targetFolder) {
//        String key = targetFolder + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        try {
//            s3.putObject(new PutObjectRequest(bucketName, key, file.getInputStream(), null));
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to upload file to S3", e);
//        }
//        return s3.getUrl(bucketName, key).toString();
//    }
}
