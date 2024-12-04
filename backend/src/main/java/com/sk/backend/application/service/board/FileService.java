package com.sk.backend.application.service.board;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * packageName    : com.sk.backend.application.service.board
 * fileName       : FileService
 * author         : moongi
 * date           : 12/4/24
 * description    :
 */
public interface FileService {
    String storeFile(MultipartFile file) throws IOException;
}
