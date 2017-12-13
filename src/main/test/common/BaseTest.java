package common;

import cn.net.gxht.app.yjdPlatform.common.dao.CommonDao;
import cn.net.gxht.app.yjdPlatform.common.entity.request.GetTest;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.MyMD5Util;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.SecurityUtil;
import cn.net.gxht.app.yjdPlatform.user.entity.weather.Weather;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by 10604 on 2017/8/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-mvc.xml","classpath*:spring-mybatis.xml"})
public class BaseTest {
    private CommonDao commonDao;
    @Resource
    protected ApplicationContext ctx;
    @Before
    public void beforeTest(){
        commonDao=ctx.getBean("commonDao",CommonDao.class);
    }
    @Test
    public void testToken(){
        Map<String,Object> map=new HashedMap();
        map.put("phone","13780088569");
        map.put("password","1060455912");
        String token=SecurityUtil.authentication(map);
        System.out.println(token);
    }
    @Test
    public void testCheckToken(){Map<String,Object> tokenMap=new HashedMap();
        Map<String,Object> map = commonDao.checkToken("c2d90a70471099e4ea3cfc1e22b63589");
        System.out.println(map);
    }
    @Test
    public void testBase64(){
        String str="guiyuhao";
        byte [] s= Base64.decodeBase64(str);
        str=new String(s);
        /**
         * 将此字符串存入数据库
         */
        s=str.getBytes();
        System.out.println(str);
        str=Base64.encodeBase64String(s);
        System.out.println(str);
    }

    /**
     * Base64和MD5的组合加密的实现
     */
    @Test
    public void testBase64AndMD5() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pwd="zwz161818,";
        //实现策略1.先用MD5对用户密码进行加密(将此值存入数据库)，加密完将密文进行Base64再加密将结果作为token
        String pwdMD5=MyMD5Util.getEncryptedPwd(pwd);
        /**
         * 将MD5加密后的结果存入数据库
         */
        byte [] base64Byte = Base64.decodeBase64(pwdMD5);
        String token = MyMD5Util.byteToHexString(base64Byte);
        System.out.println(token);
        /**
         * 将token传入数据库和session中
         */
        /**
         * 假设用户登录输入pwd
         */
        String realPwd="zwz161818,";
        System.out.println(pwd+","+realPwd);
        System.out.println(MyMD5Util.validPassword(pwd,pwdMD5));
    }
    @Test
    public void testSHA1(){
        String message="gyh1060455912";
        String shaHexMessage=DigestUtils.shaHex(message);
        System.out.println(shaHexMessage);
        byte [] shaMessage=DigestUtils.sha(message);
        for (byte b:shaMessage) {
            System.out.print(b+" ");
        }
        System.out.println(DigestUtils.sha256Hex(message));
        System.out.println(DigestUtils.sha384Hex(message));
    }
    @Test
    public  void testGetWeatherInfo() throws IOException, NoSuchAlgorithmException {
        String longitude="121.465910";
        String latitude="29.892820";
        Map<String,Object> map=new HashedMap();
        Map<String,Object> resultMap=new HashedMap();
        map.put("from", Weather.form);
        map.put("lng",longitude);
        map.put("lat",latitude);
        map.put("showapi_appid",Weather.showapi_appid);
        map.put("showapi_sign",Weather.showapi_sign);
        map.put("needMoreDay",Weather.needMoreDay);
        map.put("needIndex",Weather.needMorIndex);
        map.put("needHourData",Weather.needHourData);
        map.put("need3HourForcast",Weather.need3HourForcast);
        map.put("needAlarm",Weather.needAlarm);
        StringBuilder url=new StringBuilder("http://route.showapi.com/9-5");
        JSONObject returnJsonObject = GetTest.sendGetReturnJsonString(map,url);
        Gson gson=new Gson();
        //gson
        System.out.println(returnJsonObject);
    }
    @Test
    public void testJsonToObject(){
    }
    @Test
    public void testSubString(){
        String path="https://www.gxht.net.cn/app_yjdPlatform/userPhotos/userId1/photo.jpg";
        path=path.substring(path.lastIndexOf("/")+1);
        System.out.println(path);
    }
    @Test
    public void getMessageData(){
        List<Map<String,Object>> messageList = commonDao.GetMessageData();
        System.out.println(messageList);
        String [] messageArray=new String[messageList.size()];
        int index=0;
        for (Map<String,Object> map:messageList){
            String title=(String) map.get("title");
            String phone=(String) map.get("phone");
            phone=getPhone(phone);
            messageArray[index]=phone+"申请"+title+"成功";
            index++;
        }
        System.out.println(ArrayUtils.toString(messageArray));
    }
    public String getPhone(String phone){
        phone=phone.substring(0,3)+"****"+phone.substring(phone.length()-4);
        return phone;
    }
}
