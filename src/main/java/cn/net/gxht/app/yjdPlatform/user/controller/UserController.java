package cn.net.gxht.app.yjdPlatform.user.controller;

import cn.net.gxht.app.yjdPlatform.common.entity.Page;
import cn.net.gxht.app.yjdPlatform.common.entity.check.CheckCode;
import cn.net.gxht.app.yjdPlatform.common.entity.jsonResult.JsonResult;
import cn.net.gxht.app.yjdPlatform.user.entity.AppUser;
import cn.net.gxht.app.yjdPlatform.user.entity.FeedBack;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.SecurityUtil;
import cn.net.gxht.app.yjdPlatform.user.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
@Controller
public class UserController {
    @Resource
    private UserService userService;
    Logger logger = LogManager.getLogger(UserController.class);

    /**
     * 获取验证码
     *
     * @param phone
     * @param session
     * @return
     */
    @RequestMapping("/getCode")
    @ResponseBody
    public JsonResult getCode(String phone, HttpSession session) {
        Map<String, Object> map = userService.getCode(phone, session);
        return new JsonResult(map);
    }

    /**
     * 插入用户
     *
     * @param name     用户名
     * @param password 密码
     * @param phone    用户手机号
     * @param code     验证码
     * @param session
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/insertUser")
    @ResponseBody
    public JsonResult insertUser(String name, String password, String phone, String code, HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.insertAppUser(name, password, phone, code, session, request);
        return new JsonResult();
    }

    /**
     * 用户登录
     *
     * @param account  账号(手机号或账号名)
     * @param password 密码
     * @param session  保存token(先从token取对象，先从session中取token，如果session中没有token则从数据库中取)
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(String account, String password, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.debug("account:" + account + "," + "password:" + password);
        String token = userService.login(account, password, session);
        return new JsonResult(token);
    }

    /**
     * 用户申请贷款
     *
     * @param id    申请贷款的id
     * @param token 检查用户是否登录
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/doApplication")
    @ResponseBody
    public JsonResult doApplication(Integer id, String token) throws UnsupportedEncodingException {
        Map map = userService.userApplication(token, id);
        return new JsonResult(map);
    }

    /**
     * 检查验证码是否正确
     *
     * @param code
     * @param session
     * @return
     */
    @RequestMapping("/checkCode")
    @ResponseBody
    public JsonResult checkCode(String code, HttpSession session) {
        CheckCode.checkCode(code, session);
        return new JsonResult();
    }

    /**
     * 用户更新密码(被废弃的更新密码的方式,已经废弃了一种)
     *
     * @param phone    用户手机号
     * @param password 用户新密码
     * @param session
     * @param code
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public JsonResult updatePassword(String phone, String password, HttpSession session, String code) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.upadatePassword(phone, password, session, code);
        return new JsonResult();
    }

    /**
     * @param authName    认证姓名
     * @param token       检查用户登录
     * @param authCardNo  用户身份证认证
     * @param topPicture  身份证正面
     * @param backPicture 身份证反面
     * @param holdPicture 手持身份证
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/userAuth")
    @ResponseBody
    public JsonResult userAuth(String authName, String token, String authCardNo, MultipartFile topPicture, MultipartFile backPicture, MultipartFile holdPicture, HttpServletRequest request) throws IOException {
        logger.debug("用户认证:" + authName + "," + token + "," + authCardNo + "," + topPicture + "," + backPicture + "," + holdPicture);
        userService.userAuth(authName, token, authCardNo, topPicture, backPicture, holdPicture, request);
        return new JsonResult();
    }

    /**
     * 更新用户图片
     *
     * @param token           检查用户登录
     * @param userPictureFile 用户图片
     * @param request         获取当前跟目录
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/updateUserImg")
    @ResponseBody
    public JsonResult doUpdateJsonResult(String token, MultipartFile userPictureFile, HttpServletRequest request) throws UnsupportedEncodingException {
        String newImgPath = userService.updateUserImg(token, request, userPictureFile);
        return new JsonResult(newImgPath);
    }

    /**
     * 根据用的token寻找用户的认证信息
     *
     * @param token
     * @return
     */
    @RequestMapping("/findUserAuthInfo")
    @ResponseBody
    public JsonResult doFindUserAuthInfo(String token) {
        Map<String, Object>[] map = userService.findUserAuthInfo(token);
        return new JsonResult(map);
    }

    /**
     * 用户反馈
     *
     * @param feedBack 反馈内容
     * @param token    用登录信息
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/userFeedBack")
    @ResponseBody
    public JsonResult doInsertUserFeedBack(FeedBack feedBack, String token) throws UnsupportedEncodingException {
        feedBack.setContent(new String(feedBack.getContent().getBytes("ISO8859-1"), "UTF-8"));
        userService.insertUserFeedBack(feedBack, token);
        System.out.println(feedBack);
        return new JsonResult();
    }

    /**
     * 更新用户密码(有两种更新密码的方式，已经废弃了一种)
     *
     * @param oldPassword
     * @param token
     * @param newPassword
     * @return
     */
    @RequestMapping("/updatePasswordAfterLogin")
    @ResponseBody
    public JsonResult updatePasswordAfterLogin(String oldPassword, String token, String newPassword) {
        userService.updatePasswordAfterLogin(oldPassword, newPassword, token);
        return new JsonResult();
    }

    /**
     * 寻找用户申请的信息
     * 查询的是用户申请贷款的信息
     *
     * @param token
     * @param page
     * @return
     */
    @RequestMapping("/findUserApplicationInfo")
    @ResponseBody
    public JsonResult findUserApplicationInfo(String token, Page page) {
        Map<String, Object> map = userService.findUserApplicationInfo(token, page);
        return new JsonResult(map);
    }

    /**
     * 寻找用户申请信息
     * 查询的是他人申请我入驻的商家的信息
     *
     * @param token
     * @param page
     * @return
     */
    @RequestMapping("/findApplicationInfo")
    @ResponseBody
    public JsonResult findApplicationInfo(String token, Page page) {
        Map<String, Object> map = userService.findApplicationInfo(token, page);
        return new JsonResult(map);
    }

    /**
     * 寻找用户信息(前端无理取闹的要求，写了两个)
     *
     * @param token
     * @return
     */
    @RequestMapping("/doFindUserInfo")
    @ResponseBody
    public JsonResult doFindUserInfo(String token) {
        AppUser appUser = userService.findAppUserInfo(token);
        return new JsonResult(appUser);
    }

    /**
     * @param merchantId
     * @param token
     * @return
     */
    //根据userId和token获取用户信息
    @RequestMapping("/doFindByQRcodeInfo")
    @ResponseBody
    public JsonResult doFindByQRcodeInfo(Integer merchantId, String token) {
        Map<String, Object> map = userService.doFindQRcodeInfo(merchantId, token);
        return new JsonResult(map);
    }
}
