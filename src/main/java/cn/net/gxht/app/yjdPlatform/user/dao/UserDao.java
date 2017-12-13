package cn.net.gxht.app.yjdPlatform.user.dao;

import cn.net.gxht.app.yjdPlatform.user.entity.AppUser;
import cn.net.gxht.app.yjdPlatform.user.entity.FeedBack;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.core.net.ssl.SslConfiguration;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public interface UserDao {
    Integer insertAppUser(@Param(value = "name") String name,@Param(value="password") String password, @Param(value = "phone") String phone);
    Integer findHasSameName(String name);
    AppUser findUserByName(String name);
    AppUser findUserByPhone(String phone);
    Integer insertToken(@Param(value="tokenValue") String tokenValue, @Param(value="phone") String phone, @Param(value = "userId")Integer userId);
    AppUser findUserById(Integer id);
    Integer userApplication(@Param(value="userId") Integer userId,@Param(value="pictureId") Integer pictureId);
    Integer updatePassword(@Param(value = "password") String password,@Param(value = "id") Integer id);
    AppUser findPhoneWhetherRegister(String phone);
    Integer updateUserAuthInfo(@Param(value = "authName") String authName,@Param(value = "userId") Integer id,@Param(value = "authCardNo") String authCardNo);
    AppUser findUserByUserId(Integer id);
    Map<String,Object> findUserApplyMerchantPhoneAndTitle(Integer id);
    Integer updateUserPhoto(@Param(value="userId") Integer userId,@Param(value = "newUserImgPath") String newUserImgPath);
    AppUser findUserAuthInfo(Integer userId);
    Integer insertUserFeedBack(FeedBack feedBck);
    List<Map<String,Object>> findUserApplicationInfo(Integer userId);
    List<Map<String,Object>> findUserAuthImgLastTime(Integer id);
    Integer deleteAuthImgLastTime(Integer id);
   List<Map<String,Object>> findUserApplicationInfoByHiself(Integer id);
    Map<String,Object> findUserPhoneAndNameByToken(String token);
}
