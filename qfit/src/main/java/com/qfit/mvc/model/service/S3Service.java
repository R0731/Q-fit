package com.qfit.mvc.model.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.InputStream;

import jakarta.annotation.PostConstruct;

@Service
public class S3Service {

    private AmazonS3 amazonS3;
    
    @Value("${cloud.aws.credentials.access-key}")
	private String accesskey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	// AWS S3 자격 증명 생성 및 클라이언트 초기화
	@PostConstruct
	public void setAmazonS3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accesskey, secretKey);
		amazonS3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.withRegion(region)
				.build();
	}

	// 파일 url 반환	
	public String getFileUrl(String fileName) {
	    return amazonS3.getUrl(bucket, fileName).toString();
	}

	// 파일 삭제	
	public void deleteFile(String fileName) {
	    amazonS3.deleteObject(bucket, fileName);
	}
	
	/**
	 * 파일 업로드 메서드
	 * @param  fileName S3 버킷에 저장될 파일 이름
	 * @param  inputStream 파일의 내용을 스트림 형태로 제공하여 업로드
	 * @param  contentLength 파일의 크기
	 * @param  contentType 파일의 MIME 타입(콘텐츠 유형)
	 */
	public void uploadFile(String fileName, InputStream inputStream, long contentLength, String contentType) {
	    ObjectMetadata metadata = new ObjectMetadata();
	    metadata.setContentLength(contentLength);
	    metadata.setContentType(contentType);

	    amazonS3.putObject(bucket, fileName, inputStream, metadata);
	}

	/**
	 * S3에서 파일을 InputStream으로 가져오는 메서드
	 * @param  fileName S3 버킷에서 가져올 파일의 이름
	 * @return S3 객체의 콘텐츠를 InputStream으로 반환
	 */
	public InputStream getFileAsStream(String fileName) {
	    return amazonS3.getObject(bucket, fileName).getObjectContent();
	}



}