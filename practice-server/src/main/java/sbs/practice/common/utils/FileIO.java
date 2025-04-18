package sbs.practice.common.utils;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.constant.FileConstant;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.constant.MinIOConstant;
import sbs.practice.common.constant.StudentOrTeacherConstant;
import sbs.practice.common.exception.FileStorageException;
import sbs.practice.config.MinIOInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 文件上传和下载工具类
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FileIO {

    private final MinioClient minioClient;
    private final MinIOInfo minIOInfo;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file, Integer identity) {
        log.info("uploadFile");

        try {
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();

            // 查看文件类型是否合法
            String extname = originalFilename.substring(originalFilename.lastIndexOf("."));// 获取文件扩展名

            // 随机名+文件扩展名
            String newFileName = UUID.randomUUID().toString() + extname;
            // 将文件存储在服务器的磁盘目录
            String filePath = (identity.equals(StudentOrTeacherConstant.TEACHER) ? FileConstant.TEACHER_FILE_PATH : FileConstant.UPLOAD_PATH) + newFileName;

            file.transferTo(new File(filePath));
            log.info("文件上传路径：{}", filePath);
            // 返回文件名
            return newFileName;
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
            throw new FileStorageException(MessageConstant.UPLOAD_FAILED);
        }
    }


    public static String uploadImage(MultipartFile file) {
        log.info("uploadImage");
        try {
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();
            log.info("originalFilename:{}", originalFilename);
            String newFileName;
            try {
                // 查看文件类型是否合法
                String extname = originalFilename.substring(originalFilename.lastIndexOf("."));// 获取文件扩展名
                // 随机名+文件扩展名
                newFileName = UUID.randomUUID().toString() + extname;
            } catch (StringIndexOutOfBoundsException e) {
                newFileName = UUID.randomUUID().toString() + ".jpeg";
            }

            // 将文件存储在服务器的磁盘目录
            String filePath = FileConstant.IMAGE_UPLOAD_PATH + newFileName;
            file.transferTo(new File(filePath));
            // 返回文件名
            return newFileName;
        } catch (IOException e) {
            log.error("图片上传失败：{}", e);
            throw new FileStorageException(MessageConstant.UPLOAD_FAILED);
        }
    }

    /**
     * 将文件压缩成zip文件，并保存到本地
     *
     * @param files
     * @param zipFilePath
     * @throws IOException
     */
    public static void compressFiles(List<File> files, Path zipFilePath) throws IOException {
        // 这是一个用于写入 ZIP 文件的输出流。它允许你添加多个文件到同一个 ZIP 文件中。
        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(Files.newOutputStream(zipFilePath))) {
            for (File file : files) {
                // 创建 ZIP 条目：为当前文件创建一个新的 ZipArchiveEntry 对象。ZipArchiveEntry 表示 ZIP 文件中的一个条目（即一个文件）。这里使用文件对象和文件名来初始化条目。
                ZipArchiveEntry entry = new ZipArchiveEntry(file, file.getName());
                // 将刚刚创建的 ZipArchiveEntry 放入 ZipArchiveOutputStream 中，表示即将开始写入该文件的内容。
                zos.putArchiveEntry(entry);
                // FileInputStream：用于从当前文件中读取数据。
                try (FileInputStream fis = new FileInputStream(file)) {
                    // 复制流：使用 IOUtils.copy 方法将 FileInputStream 中的数据高效地复制到 ZipArchiveOutputStream 中。这实际上是将文件内容写入 ZIP 文件。
                    IOUtils.copy(fis, zos);
                }
                // 关闭 ZIP 条目：告诉 ZipArchiveOutputStream 当前文件的写入已经完成，可以准备写入下一个文件。
                zos.closeArchiveEntry();
            }
        }
    }

    /**
     * 将一组文件压缩成 ZIP 文件，并上传到 MinIO 的指定 bucket 中。
     *
     * @param files       要压缩的文件列表
     * @param zipFileName 在 MinIO 中存储的 ZIP 文件名
     * @throws IOException 如果发生 I/O 错误
     */
    public void zipAndUploadToMinIO(List<File> files, String zipFileName) throws Exception {
        // 使用 ByteArrayOutputStream 将 ZIP 文件写入到内存中
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipArchiveOutputStream zos = new ZipArchiveOutputStream(baos)) {

            for (File file : files) {
                // 创建 ZIP 条目
                ZipArchiveEntry entry = new ZipArchiveEntry(file, file.getName());
                zos.putArchiveEntry(entry);

                // 将文件内容复制到 ZIP 输出流
                try (FileInputStream fis = new FileInputStream(file)) {
                    IOUtils.copy(fis, zos);
                }
                zos.closeArchiveEntry();
            }

            // 完成 ZIP 文件的写入
            zos.finish();

            // 将 ZIP 文件内容作为字节数组上传到 MinIO
            byte[] zipData = baos.toByteArray();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(zipData);

            // 上传到 MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minIOInfo.getTeacherBucket())
                            .object(zipFileName)
                            .stream(inputStream, zipData.length, -1)
                            .contentType(MinIOConstant.ZIP_CONTENT_TYPE)
                            .build()
            );
        }
    }


    public String getSignFilesFromMinIO(String bucketName, String fileName) throws Exception {
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .method(Method.GET)
                        .expiry(10, TimeUnit.MINUTES)
                        .build()
        );
        return url;
    }
}
