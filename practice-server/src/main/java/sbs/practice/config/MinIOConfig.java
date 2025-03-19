package sbs.practice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import io.minio.MinioClient;


@Configuration
public class MinIOConfig {

    @Resource
    private MinIOInfo minIOInfo;

    @Bean
    public MinioClient minioClient() {
        //链式编程，构建MinioClient对象
        return MinioClient.builder()
                .endpoint(minIOInfo.getEndpoint())
                .credentials(minIOInfo.getAccessKey(), minIOInfo.getSecretKey())
                .build();
    }
}
