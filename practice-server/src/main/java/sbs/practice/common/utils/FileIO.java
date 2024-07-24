package sbs.practice.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.constant.FileConstant;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.exception.FileStorageException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载工具类
 */
@Slf4j
public class FileIO {

    /**
     * 文件上传
     * @param file
     * @return
     */
    public static String uploadFile(MultipartFile file) {
        log.info("uploadFile");

        try {
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();

            // 查看文件类型是否合法
            String extname = originalFilename.substring(originalFilename.lastIndexOf("."));// 获取文件扩展名

            // 随机名+文件扩展名
            String newFileName = UUID.randomUUID().toString() + extname;
            // 将文件存储在服务器的磁盘目录
            String filePath = FileConstant.UPLOAD_PATH + newFileName;
            file.transferTo(new File(filePath));
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
            log.info("originalFilename:{}",originalFilename);
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
            log.error("文件上传失败：{}", e);
            throw new FileStorageException(MessageConstant.UPLOAD_FAILED);
        }
    }

}
