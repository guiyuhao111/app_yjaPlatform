package cn.net.gxht.app.yjdPlatform.pictures.dao;

import cn.net.gxht.app.yjdPlatform.pictures.entity.MerchantPicture;
import cn.net.gxht.app.yjdPlatform.pictures.entity.Picture;
import com.sun.xml.internal.ws.api.server.SDDocument;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
public interface PicturesDao {
    List<Map<String,Object>> findPictures(@Param(value="id") Integer id);
    List<Map<String,Object>> findRegistPicture();
    String findPictureByBelongId(Integer id);
    String findTileByPictureId(Integer pictureId);
    List<Map<String,Object>> findPageTwoImg();
    //List<Map<String,Object>> findMerchantByIdAndCityCode(Integer citycode);
    List<Map<String,Object>> fuzzyQuery(@Param(value = "title") String title, @Param(value = "belongId") Integer id,@Param(value="citycode")Integer citycode);
    Map<String,Object> findPicture(@Param(value="id") Integer id,@Param(value="type") String type);
    List<Map<String,Object>> findPicture(@Param(value="type") String type,@Param(value="id") Integer id);
    //根据对应的pictureId查找对应的联系人
    String findContactByPictureId(Integer pictureId);
    Integer findByPhone(String phone);
    List<Map<String,Object>> findClientByPhone(String phone);
    Integer findClientQueryTimes(Integer userId);
    Integer updateClientQueryTimes(@Param(value = "userId") Integer clientId, @Param(value = "times") Integer times);



    String [] findLunboPictures();
    List<Map<String,Object>> findMerchantByClientId(Integer userId);
    Integer findClientIdByPictureId(Integer id);
    List<Map<String,Object>> findSelectOption();

    Integer insertMerchantPicture(MerchantPicture merchantPicture);
    Integer updatePictureById(Picture picture);

    Integer deletePictureById(Integer id);
    Integer insertPicture(@Param(value = "type") String type, @Param(value="title") String title, @Param(value="content") String content, @Param(value="img") String img,@Param(value="applyInfoId") Integer applyInfoId);
}
