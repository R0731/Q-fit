package com.qfit.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.qfit.mvc.model.dto.user.User;
import com.qfit.mvc.model.service.S3Service;
import com.qfit.mvc.model.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    @Operation(summary = "파일 업로드", description = "AWS S3에 파일을 업로드하고 URL을 반환합니다.")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // 고유 이름 생성
        s3Service.uploadFile(fileName, file.getInputStream(), file.getSize(), file.getContentType());
        return s3Service.getFileUrl(fileName); // 업로드된 파일의 URL 반환
    }

    @DeleteMapping("/delete")
    @Operation(summary = "파일 삭제", description = "AWS S3에서 파일을 삭제합니다.")
    public String deleteFile(@RequestParam("fileName") String fileName) {
        s3Service.deleteFile(fileName);
        return "File deleted: " + fileName;
    }
    
    @GetMapping("/load")
    @Operation(summary = "파일 로드", description = "AWS S3에서 특정 파일을 로드하여 반환합니다.")
    public ResponseEntity<byte[]> loadFile(@RequestParam("fileName") String fileName) throws IOException {
        try (InputStream inputStream = s3Service.getFileAsStream(fileName)) {
            byte[] fileBytes = inputStream.readAllBytes(); // 스트림에서 바이트 배열로 읽기
            
            // Content-Type 자동 추정 (이미지 파일로 가정)
            String contentType = fileName.endsWith(".png") ? "image/png" :
                                 fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ? "image/jpeg" :
                                 "application/octet-stream";

            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .body(fileBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 파일이 없을 경우 404 반환
        }
    }

}
