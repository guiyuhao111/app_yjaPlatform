package cn.net.gxht.app.yjdPlatform.pictures.service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
public interface PicturesService {
    String [] findGundongPictures();
    Map<String,Object> findHengpaiPictures(Integer id);
    Map<String,Object> findShupaiPictures();
    List<Map<String,Object>> findRegistPicture();
    String findPictureByBelongId(Integer id);
    Map<String,Object> findPageTwoImg();
    List<Map<String,Object>> fuzzyQuery(String title, HttpSession session);
    List<Map<String,Object>> findSelectOption();
    Map<String,Object> findPictrue(Integer id);
    List<Map<String,Object>> findPictureByType(String type);
}
