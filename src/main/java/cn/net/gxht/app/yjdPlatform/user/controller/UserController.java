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
    @RequestMapping("/getCode")
    @ResponseBody
    public JsonResult getCode(String phone, HttpSession session){
        Map<String,Object> map=userService.getCode(phone,session);
        return new JsonResult(map);
    }
    @RequestMapping("/insertUser")
    @ResponseBody
    public JsonResult insertUser(String name,String password, String phone, String code, HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.insertAppUser(name,password, phone, code, session,request);
        return new JsonResult();
    }
    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(String account,String password,HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.debug("account:"+account+","+"password:"+password);
        String token=userService.login(account,password,session);
        return new JsonResult(token);
    }
    @RequestMapping("/doApplication")
    @ResponseBody
    public JsonResult doApplication(Integer id,String token) throws UnsupportedEncodingException {
        Map map=userService.userApplication(token,id);
        return new JsonResult(map);
    }
    @RequestMapping("/checkCode")
    @ResponseBody
    public JsonResult checkCode(String code,HttpSession session){
        CheckCode.checkCode(code,session);
        return new JsonResult();
    }
    @RequestMapping("/updatePassword")
    @ResponseBody
    public JsonResult updatePassword(String phone,String password,HttpSession session,String code) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.upadatePassword(phone,password,session,code);
        return new JsonResult();
    }
    @RequestMapping("/userAuth")
    @ResponseBody
    public JsonResult userAuth(String authName, String token, String authCardNo, MultipartFile topPicture,MultipartFile backPicture,MultipartFile holdPicture, HttpServletRequest request) throws IOException {
        logger.debug("用户认证:"+authName+","+token+","+authCardNo+","+topPicture+","+backPicture+","+holdPicture);
        userService.userAuth(authName,token,authCardNo,topPicture,backPicture,holdPicture,request);
        return new JsonResult();
    }
    @RequestMapping("/updateUserImg")
    @ResponseBody
    public JsonResult doUpdateJsonResult(String token,MultipartFile userPictureFile,HttpServletRequest request) throws UnsupportedEncodingException {
        String newImgPath =  userService.updateUserImg(token,request,userPictureFile);
        return new JsonResult(newImgPath);
    }
    @RequestMapping("/findUserAuthInfo")
    @ResponseBody
    public JsonResult doFindUserAuthInfo(String token){
        Map<String,Object> [] map=userService.findUserAuthInfo(token);
        return new JsonResult(map);
    }
    @RequestMapping("/userFeedBack")
    @ResponseBody
    public JsonResult doInsertUserFeedBack(FeedBack feedBack, String token) throws UnsupportedEncodingException {
        feedBack.setContent(new String(feedBack.getContent().getBytes("ISO8859-1"),"UTF-8"));
        userService.insertUserFeedBack(feedBack,token);
        System.out.println(feedBack);
        return new JsonResult();
    }
    @RequestMapping("/updatePasswordAfterLogin")
    @ResponseBody
    public JsonResult updatePasswordAfterLogin(String oldPassword,String token,String newPassword){
        userService.updatePasswordAfterLogin(oldPassword,newPassword,token);
        return new JsonResult();
    }
    @RequestMapping("/findUserApplicationInfo")
    @ResponseBody
    public JsonResult findUserApplicationInfo(String token, Page page){
        Map<String,Object> map= userService.findUserApplicationInfo(token,page);
        return new JsonResult(map);
    }
    @RequestMapping("/findApplicationInfo")
    @ResponseBody
    public JsonResult findApplicationInfo(String token, Page page){
        Map<String,Object> map= userService.findApplicationInfo(token,page);
        return new JsonResult(map);
    }
    @RequestMapping("/doFindUserInfo")
    @ResponseBody
    public JsonResult doFindUserInfo(String token){
        AppUser appUser=userService.findAppUserInfo(token);
        return new JsonResult(appUser);
    }
    //根据userId和token获取用户信息
    @RequestMapping("/doFindByQRcodeInfo")
    @ResponseBody
    public JsonResult doFindByQRcodeInfo(Integer merchantId,String token){
        Map<String,Object> map= userService.doFindQRcodeInfo(merchantId, token);
        return new JsonResult(map);
    }
}
