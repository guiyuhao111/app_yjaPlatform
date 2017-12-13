package cn.net.gxht.app.yjdPlatform.common.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public interface CommonDao {
    Map<String,Object> checkToken(String token);
    List<Map<String,Object>> findAllCity(String firstChar);
    List<Map<String ,Object>> findHotCity();
    Integer [] findClientIdByCityCode(Integer adcode);
    String findClientPhoneById(Integer id);
    List<Map<String,Object>> findCards(@Param(value = "id") Integer id);
    List<Map<String,Object>> findLoads(@Param(value = "id") Integer id);
    List<Map<String,Object>> findHotLoans(@Param(value = "id") Integer id);
    List<Map<String,Object>> GetMessageData();
    String [] GetMessageDefault();
    Integer updateLoansById(@Param(value = "id") Integer id, @Param(value="title") String title, @Param(value = "img") String img, @Param(value = "content") String content, @Param(value = "url") String url);
    Integer updateCardsById(@Param(value = "id") Integer id, @Param(value="title") String title, @Param(value = "img") String img, @Param(value = "content") String content, @Param(value = "url") String url);
    Integer updateHotLoansById(@Param(value = "id") Integer id, @Param(value="title") String title, @Param(value = "img") String img, @Param(value = "content") String content, @Param(value = "url") String url);

    Integer deleteCardById(@Param(value="id") Integer id);
    Integer deleteLoadsById(@Param(value="id") Integer id);
    Integer deleteHotLoansById(@Param(value="id") Integer id);

    Integer insertApplyCard(@Param(value="title")String title, @Param(value="content")String content,@Param(value="url")String url,@Param(value="img")String img);
    Integer insertApplyLoans(@Param(value="title")String title, @Param(value="content")String content,@Param(value="url")String url,@Param(value="img")String img);
    Integer insertHotLoans(@Param(value="title")String title, @Param(value="content")String content,@Param(value="url")String url,@Param(value="img")String img);
}
