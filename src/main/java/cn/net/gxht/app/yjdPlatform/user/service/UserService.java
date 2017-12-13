package cn.net.gxht.app.yjdPlatform.user.service;

import cn.net.gxht.app.yjdPlatform.common.entity.Page;
import cn.net.gxht.app.yjdPlatform.user.entity.AppUser;
import cn.net.gxht.app.yjdPlatform.user.entity.FeedBack;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public interface UserService {
    void insertAppUser(String name,String password, String phone,String code,HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    Map<String,Object> getCode(String phone, HttpSession session);
    String login(String name, String password,HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    Map<String,Object> userApplication(String token,Integer pictureId);
    void upadatePassword(String phone,String password,HttpSession session,String code)throws UnsupportedEncodingException, NoSuchAlgorithmException ;
    void userAuth(String authName, String token, String authCardNo,MultipartFile topPicture,MultipartFile backPicture,MultipartFile holdPicture, HttpServletRequest request) throws IOException;
    String updateUserImg(String token,HttpServletRequest request,MultipartFile multipartFile) throws UnsupportedEncodingException;
    Map<String,Object> [] findUserAuthInfo(String token);
    void insertUserFeedBack(FeedBack feedBack, String token);
    void updatePasswordAfterLogin(String oldPassword,String newPasswsord,String token);
    Map<String,Object> findUserApplicationInfo(String token, Page page);
    AppUser findAppUserInfo(String token);
    Map<String,Object> doFindQRcodeInfo(Integer id,String token);
    Map<String, Object> findApplicationInfo(String token,Page page);
    Integer findUserIdBytoken(String token);
}
