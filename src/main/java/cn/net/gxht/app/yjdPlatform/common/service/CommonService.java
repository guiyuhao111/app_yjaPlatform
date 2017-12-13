package cn.net.gxht.app.yjdPlatform.common.service;

import cn.net.gxht.app.yjdPlatform.common.entity.Page;
import cn.net.gxht.app.yjdPlatform.pictures.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public interface CommonService {
    String checkUserLogin(String token, HttpSession session);
    Map<String,Object> findCity();
    Map<String,Object> findCards(Page page,Integer id);
    Map<String,Object> findLoads(Page page,Integer id);
    Map<String,Object> findHotLoans(Page page,Integer id);
    String [] GetMessageData();
    String [] GetMessageDefault();
    void updatePageInfo(String classType, Picture picture, MultipartFile multipartFile, HttpServletRequest request,String url) throws UnsupportedEncodingException;
    void deletePageInfoById(String classType, Integer id,String path,HttpServletRequest request);
    void addPageObjectListObject(MultipartFile multipartFile,Picture picture,String url,HttpServletRequest request,String imgType) throws UnsupportedEncodingException;
}
