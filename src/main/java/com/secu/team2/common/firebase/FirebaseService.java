package com.secu.team2.common.firebase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FirebaseService {

    @Value("${app.firebase-bucket}")
    private String firebaseBucket;

    public String uploadFile(MultipartFile file, String originalFilename) throws IOException, FirebaseAuthException {
        String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + extName;

        return uploadFileToFirebaseStorage(file, fileName);
    }

    public void deleteFile(String filePath) {
        deleteFileFromFirebaseStorage(filePath);
    }

    private String uploadFileToFirebaseStorage(MultipartFile file, String fileName)
            throws IOException, FirebaseAuthException {
        try {
            Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
            InputStream content = new ByteArrayInputStream(file.getBytes());
            Blob blob = bucket.create(fileName, content, file.getContentType());
            String mediaLink = blob.getMediaLink();
            log.info("파일 업로드 성공. Media link: {}", mediaLink);
            return mediaLink;
        } catch (Exception e) {
            log.error("파일 업로드 실패 : {}", e.getMessage(), e);
            throw e;
        }
    }

    private void deleteFileFromFirebaseStorage(String filePath) {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        BlobId blobId = BlobId.of(bucket.getName(), extractFileName(filePath));
        boolean deleted = bucket.getStorage().delete(blobId);
        if (deleted) {
            log.info("파일 삭제됨. Path: {}", filePath);
        } else {
            log.warn("파일 삭제 실패. Path: {}", filePath);
        }
    }

    private String extractFileName(String filePath) {
        int lastSlashIndex = filePath.lastIndexOf("/");
        if (lastSlashIndex != -1) {
            return filePath.substring(lastSlashIndex + 1);
        }
        return filePath;
    }
}
