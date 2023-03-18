package com.petition.petition.config

import lombok.Value
import org.gradle.internal.impldep.com.amazonaws.auth.AWSCredentials
import org.gradle.internal.impldep.com.amazonaws.auth.AWSStaticCredentialsProvider
import org.gradle.internal.impldep.com.amazonaws.auth.BasicAWSCredentials
import org.gradle.internal.impldep.com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
class StorageConfiguration {
    @Value("${cloud.aws.credentials.access-key}")
    private val accessKey: String? = null

    @Value("${cloud.aws.credentials.secretKey}")
    private val secretKey: String? = null

    @Value("${cloud.aws.region.static}")
    private val region: String? = null

    @Bean
    @Primary
    fun generateS3client(): AmazonS3 {
        val credentials: org.gradle.internal.impldep.com.amazonaws.auth.AWSCredentials =
            org.gradle.internal.impldep.com.amazonaws.auth.BasicAWSCredentials(accessKey, secretKey)
        return org.gradle.internal.impldep.com.amazonaws.services.s3.AmazonS3ClientBuilder.standard()
            .withCredentials(org.gradle.internal.impldep.com.amazonaws.auth.AWSStaticCredentialsProvider(credentials))
            .withRegion(region)
            .build()
    }
}