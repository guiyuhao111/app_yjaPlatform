package cn.net.gxht.app.yjdPlatform.file.service.impl;

import cn.net.gxht.app.yjdPlatform.file.dao.FileDao;
import cn.net.gxht.app.yjdPlatform.file.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2017/10/13.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FileServiceImpl implements FileService {
    @Resource
    private FileDao fileDao;
    private Logger logger= LogManager.getLogger(FileServiceImpl.class);

    public void uploadFile(String realPath, MultipartFile [] mFiles,Integer clientId) {
    }

//    public void uploadFileUtil(String realPath, MultipartFile mFile,Integer clientId)
}
