package com.qfit.mvc.controller;

import org.springframework.beans.factory.annotation.Value;
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
public class S3RestController {

	@Value("${cloud.aws.s3.bucket}") // application.properties설정에 정의
    private String bucketName;
	
    private final S3Service s3Service;

    public S3RestController(S3Service s3Service) {
        this.s3Service = s3Service;
    }
    
	/**
	 * 파일 업로드 메서드
	 * @param file 업로드할 파일 (MultipartFile 객체)
	 * @return 업로드된 파일의 URL 문자열 반환
	 */
    @PostMapping("/upload")
    @Operation(summary = "파일 업로드", description = "AWS S3에 파일을 업로드하고 URL을 반환합니다.")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // 고유 이름 생성(현재 시간 + 원본 파일명)
        s3Service.uploadFile(fileName, file.getInputStream(), file.getSize(), file.getContentType());
        return s3Service.getFileUrl(fileName); // 업로드된 파일의 URL 반환
    }

    /**
     * S3에서 파일을 삭제하는 메서드
     * @param fileName S3 버킷에서 삭제할 파일의 이름
     * @return 삭제된 파일의 이름을 포함한 성공 메시지 반환
     */
    @DeleteMapping("/delete")
    @Operation(summary = "파일 삭제", description = "AWS S3에서 파일을 삭제합니다.")
    public String deleteFile(@RequestParam("fileName") String fileName) {
        s3Service.deleteFile(fileName);
        return "File deleted: " + fileName;
    }
    
    /**
     * S3에서 파일을 로드하여 반환하는 메서드
     * @param fileName 로드할 파일의 이름 (S3 URL 또는 키 형식 가능)
     * @return 파일의 바이트 데이터를 HTTP 응답으로 반환
     *         - 성공 시 HTTP 상태 200 (OK) 및 파일 데이터 반환
     *         - 실패 시 HTTP 상태 404 (NOT_FOUND) 반환
     * @throws IOException 파일 읽기 중 오류가 발생할 경우 예외를 발생시킴
     */
    @GetMapping("/load")
    @Operation(summary = "파일 로드", description = "AWS S3에서 특정 파일을 로드하여 반환합니다.")
    public ResponseEntity<byte[]> loadFile(@RequestParam("fileName") String fileName) throws IOException {    	
    	String s3UrlPrefix = "https://" + bucketName + ".s3.us-east-2.amazonaws.com/"; // 제거할 S3 URL 접두사 지정
        String key = fileName.replace(s3UrlPrefix, ""); // 접두사를 제거하고 원하는 형식으로 key 생성 
        try (InputStream inputStream = s3Service.getFileAsStream(key)) {
            byte[] fileBytes = inputStream.readAllBytes(); // InputStream에서 파일 데이터를 바이트 배열로 읽기
            // 파일의 MIME 타입(콘텐츠 유형)을 파일 확장자를 기반으로 추정
            String contentType = fileName.endsWith(".png") ? "image/png" :
                                 fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ? "image/jpeg" :
                                 "application/octet-stream";
            // HTTP 응답 생성            
            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .body(fileBytes);
        } catch (Exception e) {
        	System.out.println("이미지 로드 실패" + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}