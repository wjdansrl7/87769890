package com.sk.backend.application.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * packageName    : com.sk.backend.application.controller
 * fileName       : FileController
 * author         : moongi
 * date           : 12/4/24
 * description    :
 */
@RestController
public class FileController {
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename) {
        try {
            // 파일 저장 경로 설정 (서버의 실제 파일 경로)
            Path filePath = Paths.get("./file/upload/").resolve(filename).normalize();

            // 파일 Resource 가져오기
            Resource resource = new UrlResource(filePath.toUri());

            // 파일이 존재하지 않으면 예외 처리
            if (!resource.exists()) {
                throw new RuntimeException("파일을 찾을 수 없습니다: " + filename);
            }

            // 파일 다운로드를 위한 헤더 설정
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("파일 다운로드 실패: " + filename, e);
        }
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename) {
        try {
            Path filePath = Paths.get("./file/upload/").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("이미지를 찾을 수 없습니다: " + filename);
            }

            return ResponseEntity.ok().body(resource);

        } catch (Exception e) {
            throw new RuntimeException("이미지 로드 실패: " + filename, e);
        }
    }
}
