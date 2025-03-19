package sbs.practice.common.constant;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class FileConstant {


    public static String UPLOAD_PATH;
    public static String IMAGE_UPLOAD_PATH;
    public static String ZIP_FILE_NAME;
    public static String TEACHER_FILE_PATH;
    public static String ZIP_FILE_EXTENSION;

    @Value("${file.teacher.filePath}")
    public void setTeacherFilePath(String teacherFilePath) {
        TEACHER_FILE_PATH = teacherFilePath;
    }

    @Value("${file.uploadPath}")
    public void setUploadPath(String uploadPath) {
        UPLOAD_PATH = uploadPath;
    }

    @Value("${file.imageUploadPath}")
    public void setImageUploadPath(String imageUploadPath) {
        IMAGE_UPLOAD_PATH = imageUploadPath;
    }

    @Value("${file.zipFileName}")
    public void setZipFileName(String zipFileName) {
        ZIP_FILE_NAME = zipFileName;
    }

    @Value("${file.zipFileExtension}")
    public void setZipFileExtension(String zipFileExtension) {
        ZIP_FILE_EXTENSION = zipFileExtension;
    }
}
