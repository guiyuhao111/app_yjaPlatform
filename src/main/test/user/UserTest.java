package user;

import cn.net.gxht.app.yjdPlatform.user.dao.UserDao;
import cn.net.gxht.app.yjdPlatform.user.entity.AppUser;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.MD5;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.MyMD5Util;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.SecurityUtil;
import common.BaseTest;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public class UserTest extends BaseTest {
    private UserDao userDao;

    @Before
    public void testBefore() {
        userDao = ctx.getBean("userDao", UserDao.class);
    }

    @Test
    public void testInsertUser() {
        Integer i = userDao.insertAppUser("测试","13141592654", "13780088569");
        System.out.println(i);
    }

    @Test
    public void testLogger() {
        Logger logger = LogManager.getLogger(UserTest.class);
        logger.entry();
        logger.error("这是logger记录的错误信息");
        logger.info("我是info信息");
        logger.debug("我是debug信息");
        logger.warn("我是警告信息");
        logger.fatal("我是fatal信息");
        logger.exit();
    }

    @Test
    public void testFindHasSameName() {
        Integer integer = userDao.findHasSameName("桂预豪");
        System.out.println(integer);
    }

    @Test
    public void testLogin() {
        String name = "桂预豪";
        String password = "13141592654";
        if ("".equals(name)) {
            throw new RuntimeException("账号或手机号不得为空");
        }
        if (name.length() > 30) {
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
        if (name.length() < 10) {
            user = userDao.findUserByName(name);
        }
        /**
         * 假设用户输入的是手机号
         */
        AppUser user1 = userDao.findUserByPhone(name);
        /**
         * 用户根据账号查找到了对应的账户
         */
        System.out.println(user + "," + user1);
        if (user != null && user1 == null) {
            if (!password.equals(user.getPassword())) {
                throw new RuntimeException("密码错误");
            }
        } else if (user1 != null && user == null) {
            if (!password.equals(user1.getPassword())) {
                throw new RuntimeException("密码错误");
            }
        } else if (user == null && user1 == null) {
            throw new RuntimeException("没有此用户");
        } else if (user != null && user1 != null) {
            throw new RuntimeException("请更换登录方式(手机/账号登录)");
        }
    }
    @Test
    public void main() {
        String name="144324284324520";
        Boolean flag=NumberUtils.isDigits(name);
        System.out.printf(flag.toString());
    }
    @Test
    public void testInsertToken(){
        Map<String,Object> tokenMap=new HashedMap();
        String phone="13780088569";
        String password="123456";
        tokenMap.put("phone",phone);
        tokenMap.put("password",password);
        tokenMap.put("time",new Date());
        String token= SecurityUtil.authentication(tokenMap);
//        Integer integer=userDao.insertToken(token,phone);
//        System.out.println(integer);
    }
    @Test
    public void testCommons_Collections(){
        //1.ArrayUtils toString方法用于输出数组的内容
        String []  array={"1","2","a","4","c","d","3"};
        System.out.println(ArrayUtils.toString(array));
        //2.contains方法用于检查数组是否包含某元素
        System.out.println(ArrayUtils.contains(array,"2"));
        //3.反转数组
        ArrayUtils.reverse(array);
        System.out.println(ArrayUtils.toString(array));
    }
    @Test
    public void testUpdatePassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String newPassword="gyh123";
        newPassword=MyMD5Util.getEncryptedPwd(newPassword);
        System.out.println(newPassword);
        System.out.println(MyMD5Util.validPassword("gyh123",newPassword));
        Integer i=userDao.updatePassword(newPassword,1);
        System.out.println(i);
    }
    @Test
    public void testMyMD5(){
        String md5=MD5.encrypt("MD5","GYH123");
        System.out.println(md5);
    }
    @Test
    public void testUpdateUserAuthStatus(){
        String userName="桂预豪";
        String authCardNo="421127199501062812";
        Integer id=1;
        Integer state=userDao.updateUserAuthInfo(userName,id,authCardNo);
        System.out.println(state);
    }
    @Test
    public void testFindUserByUserId(){
        AppUser appUser=userDao.findUserByUserId(1);
        System.out.println(appUser);
    }
    @Test
    public void testFindUserApplyMerchantPhoneAndTitle(){
        Map<String,Object> map=userDao.findUserApplyMerchantPhoneAndTitle(97);
        System.out.println(map);
    }
    @Test
    public void test11(){
        Map<String,Object> tokenMap=new HashedMap();
        String account="123456";
        tokenMap.put("userName",account);
        tokenMap.put("date",new Date().getTime());
        String token = SecurityUtil.authentication(tokenMap);
        System.out.println(token);
    }
    @Test
    public void testFindUserAuthInfo(){
        AppUser appUser=userDao.findUserAuthInfo(1);
        System.out.println(appUser);
    }
    @Test
    public void testFindUserName(){
        String name= "aadadwqdwad";
        if(name.matches(".*\\?.*")){
            System.out.println(name);
        }
    }
}
