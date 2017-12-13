package cn.net.gxht.app.yjdPlatform.user.service.impl;

import cn.net.gxht.app.yjdPlatform.common.dao.CommonDao;
import cn.net.gxht.app.yjdPlatform.common.entity.*;
import cn.net.gxht.app.yjdPlatform.common.entity.check.CheckCode;
import cn.net.gxht.app.yjdPlatform.common.entity.constant.Constants;
import cn.net.gxht.app.yjdPlatform.common.entity.constant.HttpHeader;
import cn.net.gxht.app.yjdPlatform.common.entity.constant.HttpSchema;
import cn.net.gxht.app.yjdPlatform.common.entity.enums.Method;
import cn.net.gxht.app.yjdPlatform.file.dao.FileDao;
import cn.net.gxht.app.yjdPlatform.file.entity.FileUtil;
import cn.net.gxht.app.yjdPlatform.file.entity.ImgUtil;
import cn.net.gxht.app.yjdPlatform.merchant.service.MerchantService;
import cn.net.gxht.app.yjdPlatform.pictures.dao.PicturesDao;
import cn.net.gxht.app.yjdPlatform.user.dao.UserDao;
import cn.net.gxht.app.yjdPlatform.user.entity.AppUser;
import cn.net.gxht.app.yjdPlatform.user.entity.AuthImg;
import cn.net.gxht.app.yjdPlatform.user.entity.FeedBack;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.MD5;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.MyMD5Util;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.SecurityUtil;
import cn.net.gxht.app.yjdPlatform.user.service.UserService;
import com.alibaba.fastjson.JSON;

import com.sun.xml.internal.ws.api.server.SDDocument;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/9/18.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    private final static List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList<String>();
    //APP KEY
    private final static String APP_KEY = "24603192";
    // APP密钥
    private final static String APP_SECRET = "e8a4f79d4a6955783b8815434795b638";
    //API域名
    private final static String HOST = "sms.market.alicloudapi.com";
    //log4j
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Resource
    private UserDao userDao;
    @Resource
    private CommonDao commonDao;
    @Resource
    private PicturesDao picturesDao;
    @Resource
    private FileDao fileDao;
    @Resource
    private MerchantService merchantService;
    public Map<String, Object> getCode(String phone, HttpSession session) {
        logger.entry();
        Map<String, Object> map1 = new HashedMap();
        //请求path
        String path = "/singleSendSms";
        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        String appcode = "0ed210ef6f544e559cda2912ae1210de";
        headers.put("Authorization", "APPCODE " + appcode);
        //headers.put("b-header2", "header2Value");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("Authorization");
        //CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");
        Request request = new Request(Method.GET, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);

        //请求的query
        // {“no”:”123456”}

        Map<String, String> querys = new HashMap<String, String>();
        String code = (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10);
        String paramString = "{\"code\":\"" + code + "\"}";
        querys.put("ParamString", paramString);
        querys.put("RecNum", phone);
        querys.put("SignName", "姚江平台");
        querys.put("TemplateCode", "SMS_90245076");
        request.setQuerys(querys);

        //调用服务端
        Response response = null;
        try {
            response = Client.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("执行请求异常");
        }
        String status = JSON.toJSONString(response);
        System.out.println(status);
        Map maps = (Map) JSON.parse(status);
        System.out.println("这个是用JSON类来解析JSON字符串!!!");
        Integer statusCode = (Integer) maps.get("statusCode");
        for (Object map : maps.entrySet()) {
            if ("body".equals((String) ((Map.Entry) map).getKey())) {
                String s = (String) ((Map.Entry) map).getValue();
                Map map2 = (Map) JSON.parse(s);
                String message = (String) map2.get("message");
                Boolean state = (Boolean) map2.get("success");
                map1.put("statusCode", statusCode);
                map1.put("message", message);
                map1.put("state", state);
                if (state == false) {
                    if ("The specified recNum is wrongly formed.".equals(message)) {
                        message = "手机号格式错误";
                    } else if ("The specified dayu status is wrongly formed.".equals(message)) {
                        message = "运营商账号未开通";
                    } else if ("Frequency limit reaches.".equals(message)) {
                        message = "获取验证码的次数收到限制";
                    }
                    throw new RuntimeException(message);
                }
                System.out.print(map1);
            }
        }
        if (map1 == null) {
            throw new RuntimeException("系统异常");
        }
        session.setAttribute("code", code);
        System.out.printf("code:" + code);
        logger.debug("用户:" + phone + "验证码发送成功,验证码为" + code);
        logger.exit();
        return map1;
    }

    public void insertAppUser(String name, String password, String phone, String code, HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String method=request.getMethod();
        logger.debug("请求方式:"+method);
        logger.debug("用户正在注册:"+name+","+password+","+phone+","+code);
        String newName=name;
        CheckCode.checkCode(code, session);
        try{
            name = new String(name.getBytes("ISO8859-1"), "UTF-8");
            if (name.matches(".*\\?.*")){
                name=newName;
            }
        }catch (Exception e){
            logger.debug(e);
            throw new RuntimeException("name"+name+"自负编码有问题");
        }

        Boolean passwordStyle = NumberUtils.isDigits(password);
        if (passwordStyle == true) {
            throw new RuntimeException("密码不得是纯数字");
        }
        if (phone.length() != 11) {
            throw new RuntimeException("手机号格式错误");
        }
        /**
         * 此处要判断验证码是否相等
         */
        if ("".equals(password)) {
            throw new RuntimeException("密码不得为空");
        }
        if (password.length() > 30) {
            throw new RuntimeException("密码不得超过三十个字符");
        }
        AppUser user1 = userDao.findUserByPhone(phone);
        if (user1 != null) {
            throw new RuntimeException("手机号已被注册");
        }

        /**
         * 对用户密码进行MD5加密
         */
        String pwdInDB = MD5.encrypt("MD5",password);
        //注册时可以看用户是否已注册
        Integer i = userDao.insertAppUser(name, pwdInDB, phone);
        if (i <= 0) {
            throw new RuntimeException("插入数据异常");
        }
        logger.debug("插入数据成功" + phone + "," + password);
    }

    public String login(String account, String password, HttpSession sesssion) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String newAccount=account;
        account = new String(account.getBytes("ISO8859-1"), "UTF-8");
        if (account.matches(".*\\?.*")){
            account=newAccount;
        }
        logger.debug("用户正在登录中,账号:"+account+"密码:"+password);
        Map<String, Object> tokenMap = new HashedMap();
        if ("".equals(account)) {
            throw new RuntimeException("账号或手机号不得为空");
        }
        if (account.length() > 30) {
            throw new RuntimeException("账号或手机号太长,不得超过30个汉字");
        }
        if ("".equals(password)) {
            throw new RuntimeException("密码不得为空");
        }
        if (password.length() > 30) {
            throw new RuntimeException("密码不得超过三十个字符");
        }
        /**
         * 假设用户输入的是账号
         */
        AppUser user = null;
        Integer userId = null;
        if (account.length() < 10) {
            user = userDao.findUserByName(account);
        }
        /**
         * 假设用户输入的是手机号
         */
        AppUser user1 = userDao.findUserByPhone(account);
        /**
         * 用户根据账号查找到了对应的账户
         */
        password= MD5.encrypt("MD5",password);
        System.out.println(user + "," + user1);
        if (user != null && user1 == null) {
            if (!user.getPassword().equals(password)) {
                throw new RuntimeException("密码错误");
            } else {
                userId = user.getId();
            }
        } else if (user1 != null && user == null) {
            if (!user1.getPassword().equals(password)) {
                throw new RuntimeException("密码错误");
            } else {
                userId = user1.getId();
            }
        } else if (user == null && user1 == null) {
            throw new RuntimeException("没有此用户");
        } else if (user != null && user1 != null) {
            throw new RuntimeException("请更换登录方式(手机/账号登录)");
        }
        /**
         * 此处需要将一些重要数据存储在session当中
         */
        tokenMap.put("userName",account);
        tokenMap.put("date",new Date().getTime());
        String token = SecurityUtil.authentication(tokenMap);
        sesssion.setAttribute("token", token);
        /**
         * 将用户token传入数据库
         */
        Integer i = userDao.insertToken(token, account, userId);
        System.out.println(i);
        if (i <= 0) {
            throw new RuntimeException("系统异常 token");
        }
        /**
         * 记日志
         */
        logger.debug(account + "登录成功,token" + token);
        return token;
    }

    public AppUser findAppUserInfo(String token){
        Integer userId = findUserIdBytoken(token);
        AppUser appUser=userDao.findUserByUserId(userId);
        return appUser;
    }

    public Map<String, Object> doFindQRcodeInfo(Integer id, String token) {
        Map<String,Object> pictureInfoMap= picturesDao.findPicture(id,null);
        Map<String,Object> userInfoMap=userDao.findUserPhoneAndNameByToken(token);
        pictureInfoMap.putAll(userInfoMap);
        return pictureInfoMap;
    }

    public Map<String, Object> userApplication(String token, Integer pictureId) {
        if ("".equals(token) || token == null) {
            throw new RuntimeException("抱歉，只有登录过后的商家才有查询权限");
        }
        Map<String, Object> tokenMap = commonDao.checkToken(token);
        System.out.println(tokenMap);
        if (tokenMap == null) {
            throw new RuntimeException("系统原因,请重新登录");
        }
        Integer userId = (Integer) tokenMap.get("userId");
        System.out.println();
        /**
         * 通过userId查询到对应的用户
         */
        AppUser user = userDao.findUserById(userId);
        System.out.println(user);
        String name = user.getName();
        String phone = user.getPhone();
        logger.debug("姓名:" + name + "手机" + phone + "code");
        Map<String,Object> applyInfoMap=userDao.findUserApplyMerchantPhoneAndTitle(pictureId);
        System.out.println(applyInfoMap);
        String hostPhone=(String) applyInfoMap.get("phone");
        String loans=(String)applyInfoMap.get("title");
        Map<String, Object> map1 = new HashedMap();

        String paramString = "{\"name\":\"" + name + "\",\"loans\":\"" + loans + "\",\"phone\":\"" + phone + "\"}";
        String path = "/singleSendSms";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        String appcode = "0ed210ef6f544e559cda2912ae1210de";
        headers.put("Authorization", "APPCODE " + appcode);
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("Authorization");
        Request request = new Request(Method.GET, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
        System.out.println(paramString);
        String signName = "姚江平台";
        String templateCode = "SMS_93330013";
        System.out.println(paramString);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ParamString", paramString);
        querys.put("RecNum", hostPhone);
        querys.put("SignName", signName);
        querys.put("TemplateCode", templateCode);
        request.setQuerys(querys);
        Response response = null;
        try {
            response = Client.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("执行请求异常");
        }
        String status = JSON.toJSONString(response);
        System.out.println(status);
        Map maps = (Map) JSON.parse(status);
        System.out.println("这个是用JSON类来解析JSON字符串!!!");
        Integer statusCode = (Integer) maps.get("statusCode");
        for (Object map : maps.entrySet()) {
            if ("body".equals((String) ((Map.Entry) map).getKey())) {
                String s = (String) ((Map.Entry) map).getValue();
                Map map2 = (Map) JSON.parse(s);
                String message = (String) map2.get("message");
                Boolean resultState = (Boolean) map2.get("success");
                map1.put("statusCode", statusCode);
                map1.put("message", message);
                map1.put("state", resultState);
                if (resultState == false) {
                    if ("The specified recNum is wrongly formed.".equals(message)) {
                        message = "手机号格式错误";
                    } else if ("The specified dayu status is wrongly formed.".equals(message)) {
                        message = "运营商账号未开通";
                    } else if ("Frequency limit reaches.".equals(message)) {
                        message = "获取验证码的次数已达限制,请稍候重试或更换手机号码";
                    }
                    throw new RuntimeException(message);
                }
                System.out.print(map1);
            }
        }
        /**
         * 用户申请完将申请记录保留到数据库
         */
        Integer i= userDao.userApplication(userId,pictureId);
        if(i<=0){
            throw new RuntimeException("userApplication失败");
        }
        logger.debug(name + phone + "短信发送成功");
        return map1;
    }

    public void upadatePassword(String phone,String newPassword,HttpSession session,String code) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        AppUser user= userDao.findPhoneWhetherRegister(phone);
        if(user==null){
            throw new RuntimeException("该手机号没有注册过");
        }
        System.out.println(user);
        CheckCode.checkCode(code,session);
        newPassword=MD5.encrypt("MD5",newPassword);
        System.out.println(newPassword);
        Integer i=userDao.updatePassword(newPassword,user.getId());
        System.out.println(i);
        if(i<=0){
            throw new RuntimeException("系统异常，更新密码失败");
        }
    }

    public void userAuth(String authName, String token, String authCardNo, MultipartFile topPicture,MultipartFile backPicture,MultipartFile holdPicture, HttpServletRequest request) throws IOException {
        /**
         * 现将用户信息存入到数据库
         */
        Integer userId=findUserIdBytoken(token);
        //根据用户id查询用户之前的认证记录删除用户之前上传的图片，并且删除相应的数据库记录
        List<Map<String,Object>> authImgsLastTimeList= userDao.findUserAuthImgLastTime(userId);
        Integer index=1;
        for(Map<String,Object> map:authImgsLastTimeList){
            String path=(String) map.get("path");
            String zipPath=(String) map.get("zipImgPath");
            String temp="";
            if(index==1) {
                temp="Top";
            }else if(index==2){
                temp="Back";
            }else if(index==3){
                temp="Hold";
            }
                path=request.getServletContext().getRealPath("/")+"userAuthInfo"+File.separator+"userId"+temp+userId+File.separator+path;
                zipPath=zipPath.substring(zipPath.lastIndexOf("/")+1);
                zipPath=request.getServletContext().getRealPath("/")+"userAuthInfo"+File.separator+"userId"+temp+userId+File.separator+zipPath;
            deleteUserAuthImg(path);
            deleteUserAuthImg(zipPath);
            index++;
        }
        if(authImgsLastTimeList.size()>0&&userDao.deleteAuthImgLastTime(userId)<=0){
            throw new RuntimeException("删除用户图片是失败");
        }
        Integer state= userDao.updateUserAuthInfo(authName,userId,authCardNo);
        if(state<=0) {
            throw new RuntimeException("更新用户信息失败");
        }
        String realPath=request.getServletContext().getRealPath("/")+"userAuthInfo"+File.separator+"userIdTop"+userId+File.separator;
        String zipImgPath="https://www.gxht.net.cn/app_yjdPlatform/userAuthInfo/userIdTop"+userId+"/";
        insertUserAuthImg(topPicture,realPath,1,userId,zipImgPath);
        realPath=request.getServletContext().getRealPath("/")+"userAuthInfo"+File.separator+"userIdBack"+userId+File.separator;
        zipImgPath="https://www.gxht.net.cn/app_yjdPlatform/userAuthInfo/userIdBack"+userId+"/";
        insertUserAuthImg(backPicture,realPath,2,userId,zipImgPath);
        zipImgPath="https://www.gxht.net.cn/app_yjdPlatform/userAuthInfo/userIdHold"+userId+"/";
        realPath=request.getServletContext().getRealPath("/")+"userAuthInfo"+File.separator+"userIdHold"+userId+File.separator;
        insertUserAuthImg(holdPicture,realPath,3,userId,zipImgPath);
    }

    /**
     * 本来可以在用户登陆时将用户id存入到session但这里并不打算这样做
     * @param token
     * @return
     */
    public Integer findUserIdBytoken(String token){
        if ("".equals(token) || token == null) {
            throw new RuntimeException("请先登录");
        }
        Map<String, Object> tokenMap = commonDao.checkToken(token);
        System.out.println(tokenMap);
        if (tokenMap == null) {
            throw new RuntimeException("系统原因,请重新登录");
        }
        Integer userId = (Integer) tokenMap.get("userId");
        return userId;
    }
    public void insertUserAuthImg(MultipartFile mFile,String realPath,Integer typeId,Integer userId,String zipImgPath) throws IOException {
            String oFileName = mFile.getOriginalFilename();
            logger.debug(oFileName+"开始文件上传...");
            System.out.println(oFileName);
            String newFileName = realPath +oFileName;
            System.out.println("newFileName:" + newFileName);
            File file = new File(newFileName);
            File parentFile = file.getParentFile();
            while (!parentFile.exists()) {
                try {
                    parentFile.mkdir();
                    parentFile = parentFile.getParentFile();
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
            try {
                System.out.println(mFile + "," + file);
                byte[] fileBytes = mFile.getBytes();
                mFile.transferTo(file);
            } catch (Exception e) {
                logger.error("文件上传失败");
                e.printStackTrace();
                throw new RuntimeException("图片上傳失敗");
            }
            System.out.println("oFileName=" + oFileName);
        String fromPath=newFileName;
        String toPath=realPath+"zip"+oFileName;
        ImgUtil.resizePng(fromPath,toPath,ImgUtil.OUTPUTWIDTH,ImgUtil.OUTPUTHEIGHT,ImgUtil.PROPORTION);
        zipImgPath+="zip"+oFileName;
        fileDao.insertUserAuthImgInfo(userId,oFileName,typeId,zipImgPath);
        logger.debug("文件上传成功");
    };

    public String updateUserImg(String token,HttpServletRequest request,MultipartFile userImgFile) throws UnsupportedEncodingException {
        Integer userId= findUserIdBytoken(token);
        AppUser appUser=userDao.findUserById(userId);
        String img= appUser.getImg();
        String realPath= request.getServletContext().getRealPath("/")+"userPhotos"+File.separator+"userId"+userId+File.separator;
        //先删除之前的老文件
        if(img!=null){
            img=img.substring(img.lastIndexOf("/")+1);
            String oldImgFileName=realPath+img;
            File userOldImgFile=new File(oldImgFileName);
            if(userOldImgFile.exists()){
                userOldImgFile.delete();
            }
        }
        //上传今天的新文件
        FileUtil.uploadFileUtil(realPath,userImgFile);
        //将今天的新文件文件路径存入数据库
        String newImgFilePath="https://www.gxht.net.cn/app_yjdPlatform/userPhotos/userId"+userId+"/"+userImgFile.getOriginalFilename();
        userDao.updateUserPhoto(userId,newImgFilePath);
        return newImgFilePath;
    }

    public Map<String, Object> [] findUserAuthInfo(String token) {
        Integer userId= findUserIdBytoken(token);
        System.out.println(token);
        System.out.println(userId);
        AppUser appUser=userDao.findUserAuthInfo(userId);
        Map<String,Object> userAuthInfoMap=new HashedMap();
        System.out.println(appUser);
        if(appUser.getAuthState()==0){
            userAuthInfoMap.put("authState",0);
            userAuthInfoMap.put("authMassage","审核中");
        }else if (appUser.getAuthState()==1){
            userAuthInfoMap.put("authState",1);
            userAuthInfoMap.put("authMessage","审核通过");
            userAuthInfoMap.put("authName",appUser.getAuthName());
            userAuthInfoMap.put("authCardNo",appUser.getAuthCardNo());
            List<AuthImg> imgs = appUser.getAuthImgs();
            int i=1;
            for(AuthImg img:imgs){
                if(i==1){
                    userAuthInfoMap.put("topImg",img.getImgPath());
                }else if(i==2){
                    userAuthInfoMap.put("backImg",img.getImgPath());
                }else if(i==3){
                    userAuthInfoMap.put("holdImg",img.getImgPath());
                }
                i++;
            }
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userAuthInfoMap.put("authTime",simpleDateFormat.format(appUser.getAuthTime()));
        }else if(appUser.getAuthState()==2){
            userAuthInfoMap.put("authState",2);
            userAuthInfoMap.put("authMessage","审核失败");
            userAuthInfoMap.put("authFailedResult",appUser.getAuthFailedResult());
        }else if (appUser.getAuthState()==3){
            userAuthInfoMap.put("authState",3);
            userAuthInfoMap.put("authMessage","未认证");
        }
        Map<String,Object> [] map=new HashedMap[1];
        map[0]=userAuthInfoMap;
        return map;
    }

    public void insertUserFeedBack(FeedBack feedBack,String token){
        Integer userId=findUserIdBytoken(token);
        feedBack.setUserId(userId);
        Integer state=userDao.insertUserFeedBack(feedBack);
        if(state<1){
            throw new RuntimeException("插入用户反馈记录失败");
        }
    }

    public void updatePasswordAfterLogin(String oldPassword,String newPasswsord, String token) {
        Integer userId=findUserIdBytoken(token);
        AppUser appUser=userDao.findUserById(userId);
        if(oldPassword==null){
            throw new RuntimeException("...");
        }
        oldPassword=MD5.encrypt("MD5",oldPassword);
        if (!oldPassword.equals(appUser.getPassword())){
            throw new RuntimeException("原密码不正确");
        }
        boolean flag = NumberUtils.isDigits(newPasswsord);
        if("".equals(newPasswsord)||newPasswsord==null){
            throw new RuntimeException("新密码不得为空");
        }
        if(flag){
            throw new RuntimeException("密码不得是纯数字");
        }
        if(newPasswsord.length()<6){
            throw new RuntimeException("密码不得小于6位数");
        }
        System.out.println("newPasswsord = [" + newPasswsord + "], token = [" + token + "]");
        newPasswsord=MD5.encrypt("MD5",newPasswsord);
        Integer state = userDao.updatePassword(newPasswsord,userId);
        if(state<1){
            throw new RuntimeException("修改密码是失败");
        }
    }

    public Map<String, Object> findUserApplicationInfo(String token,Page page) {
        Integer userId=findUserIdBytoken(token);
        //List<Map<String,Object>> list=userDao.findUserApplicationInfo(userId);
        List<Map<String,Object>> applicationListByMyselfList=userDao.findUserApplicationInfoByHiself(userId);
        //list.addAll(list);
        page.setPageSize(4);
        for (Map<String,Object> map:applicationListByMyselfList){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime= String.valueOf(map.get("createTime"));
            System.out.println(createTime);
            if(createTime.length()>=20){
                createTime=createTime.substring(0,createTime.lastIndexOf("."));
            }
            System.out.println(createTime);
            map.put("createTime",createTime);
        }
        Map<String,Object> map= PageUtil.byPage(applicationListByMyselfList,page);
        return map;
    }

    public Map<String, Object> findApplicationInfo(String token,Page page) {
        Integer userId=findUserIdBytoken(token);
        List<Map<String,Object>> list=userDao.findUserApplicationInfo(userId);
        page.setPageSize(2);
        for (Map<String,Object> map:list){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime= String.valueOf(map.get("createTime"));
            System.out.println(createTime);
            if(createTime.length()>=20){
                createTime=createTime.substring(0,createTime.lastIndexOf("."));
            }
            System.out.println(createTime);
            map.put("createTime",createTime);
        }
        Map<String,Object> map= PageUtil.byPage(list,page);
        return map;
    }
    void deleteUserAuthImg(String img){
        File file=new File(img);
        if(file.exists()){
            file.delete();
        }
    };
}
