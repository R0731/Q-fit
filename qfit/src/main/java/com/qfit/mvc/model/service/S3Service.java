//package com.qfit.mvc.model.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//
//import jakarta.annotation.PostConstruct;
//
//@Service
//public class S3Service {
//
//    private AmazonS3 amazonS3;
//    
//    @Value("${cloud.aws.credentials.access-key}")
//	private String accesskey;
//
//	@Value("${cloud.aws.credentials.secret-key}")
//	private String secretKey;
//
//	@Value("${cloud.aws.region.static}")
//	private String region;
//
//	@Value("${cloud.aws.s3.bucket}")
//	private String bucket;
//
//	// AWS S3 자격 증명 지정
//	@PostConstruct
//	public void setAmazonS3() {
//		AWSCredentials awsCredentials = new BasicAWSCredentials(accesskey, secretKey);
//
//		amazonS3 = AmazonS3ClientBuilder.standard()
//				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//				.withRegion(region)
//				.build();
//	}
//}
