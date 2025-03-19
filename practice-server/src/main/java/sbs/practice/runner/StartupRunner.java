package sbs.practice.runner;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sbs.practice.config.MinIOInfo;

import javax.annotation.Resource;

@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

    @Resource
    private MinioClient minIOClient;

    @Resource
    private MinIOInfo minIOInfo;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(minIOClient);
        Boolean exists = minIOClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(minIOInfo.getTeacherBucket())
                        .build());
        if (!exists) {
            minIOClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(minIOInfo.getTeacherBucket())
                            .build());
        }
        log.info("teacher bucket exists: {}", exists);
    }
}