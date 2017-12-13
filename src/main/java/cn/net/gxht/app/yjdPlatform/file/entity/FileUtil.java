package cn.net.gxht.app.yjdPlatform.file.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/10/16.
 */
public class FileUtil {
    private Logger logger= LogManager.getLogger(FileUtil.class);
    public static void uploadFileUtil(String realPath, MultipartFile mFile) throws UnsupportedEncodingException {
        String oFileName = mFile.getOriginalFilename();
        System.out.println(oFileName);
        String newFileName = realPath +oFileName;
        System.out.println("newFileName:" + newFileName);
        File file = new File(newFileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            try {
                parentFile.mkdir();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        try {
            System.out.println(mFile + "," + file);
            byte[] fileBytes = mFile.getBytes();
            mFile.transferTo(file);
        } catch (Exception e) {
            throw new RuntimeException("图片上傳失敗");
        }
        System.out.println("oFileName=" + oFileName);
    };
}
