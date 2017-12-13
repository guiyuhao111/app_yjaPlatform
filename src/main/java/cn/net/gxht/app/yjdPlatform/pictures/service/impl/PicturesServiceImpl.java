package cn.net.gxht.app.yjdPlatform.pictures.service.impl;

import cn.net.gxht.app.yjdPlatform.pictures.dao.PicturesDao;
import cn.net.gxht.app.yjdPlatform.pictures.service.PicturesService;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 10604 on 2017/8/28.
 * 程序彪红的原因是因为用了代码检查插件
 */
@Service
@Transactional
public class PicturesServiceImpl implements PicturesService {
    @Resource
    private PicturesDao picturesDao;

    @Override
    public String findPictureByBelongId(Integer id) {
        return picturesDao.findPictureByBelongId(id);
    }

    @Override
    public Map<String, Object> findPageTwoImg() {
        List<Map<String, Object>> list = picturesDao.findPageTwoImg();
        List<Map<String, Object>> junData = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> headImg = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            String type = (String) map.get("type");
            if ("jiuData".equals(type)) {
                map.remove("type");
                junData.add(map);
            }
            if ("headImg".equals(type)) {
                map.remove("type");
                headImg.add(map);
            }
        }
        Map<String, Object> map = new HashedMap();
        map.put("jiuData", junData);
        map.put("headImg", headImg);
        return map;
    }

    @Override
    public String[] findGundongPictures() {
        String[] lunboPictures = picturesDao.findLunboPictures();
        return lunboPictures;
    }

    @Override
    public Map<String, Object> findHengpaiPictures(Integer id) {
        List<Map<String, Object>> list = picturesDao.findPictures(id);
        System.out.println(list);
        List<Map<String, Object>> henpai = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            String type = (String) map.get("type");
            if ("横排".equals(type)) {
                map.remove("type");
                henpai.add(map);
            }
        }
        Map<String, Object> map = new HashedMap();
        map.put("henpai", henpai);
        return map;
    }

    @Override
    public Map<String, Object> findShupaiPictures() {
        List<Map<String, Object>> list = picturesDao.findPictures(null);
        List<Map<String, Object>> shupai = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            String type = (String) map.get("type");
            if ("竖排".equals(type)) {
                map.remove("type");
                shupai.add(map);
            }
        }
        Map<String, Object> map = new HashedMap();
        map.put("shupai", shupai);
        return map;
    }

    @Override
    public List<Map<String, Object>> findRegistPicture() {
        return picturesDao.findRegistPicture();
    }

    @Override
    public List<Map<String, Object>> fuzzyQuery(String title, HttpSession session) {
        Integer id = (Integer) session.getAttribute("BelongId");
        Integer citycode = (Integer) session.getAttribute("citycode");
        return picturesDao.fuzzyQuery(title, id, citycode);
    }

    @Override
    public List<Map<String, Object>> findSelectOption() {
        List<Map<String, Object>> list = picturesDao.findSelectOption();
        return list;
    }

    @Override
    public Map<String, Object> findPictrue(Integer id) {
        return picturesDao.findPicture(id, null);
    }

    @Override
    public List<Map<String, Object>> findPictureByType(String type) {
        List<Map<String, Object>> pictureList = picturesDao.findPicture(type, null);
        return pictureList;
    }
}
