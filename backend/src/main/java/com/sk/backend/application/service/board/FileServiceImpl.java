package com.sk.backend.application.service.board;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * packageName    : com.sk.backend.application.service
 * fileName       : FileServiceImpl
 * author         : moongi
 * date           : 12/4/24
 * description    :
 */
@Service
public class FileServiceImpl implements FileService{
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        // 고유 파일 이름 생성 (UUID 사용)
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // 저장 경로 지정
        Path targetLocation = Paths.get(uploadDir).resolve(fileName);

        // 파일 저장
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return targetLocation.toString(); // 저장된 경로 반환
    }
}
