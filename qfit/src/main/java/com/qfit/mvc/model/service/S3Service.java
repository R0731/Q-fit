package com.qfit.mvc.model.service;

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

	// AWS S3 자격 증명 지정
	@PostConstruct
	public void setAmazonS3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accesskey, secretKey);

		amazonS3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.withRegion(region)
				.build();
	}

	// 파일 url 가져오기	
	public String getFileUrl(String fileName) {
	    return amazonS3.getUrl(bucket, fileName).toString();
	}

	// 파일 삭제	
	public void deleteFile(String fileName) {
	    amazonS3.deleteObject(bucket, fileName);
	}
	
	// 파일 추가
	public void uploadFile(String fileName, InputStream inputStream, long contentLength, String contentType) {
	    ObjectMetadata metadata = new ObjectMetadata();
	    metadata.setContentLength(contentLength);
	    metadata.setContentType(contentType);

	    amazonS3.putObject(bucket, fileName, inputStream, metadata);
	}

	// S3Service.java
	public InputStream getFileAsStream(String fileName) {
	    return amazonS3.getObject(bucket, fileName).getObjectContent();
	}



}
