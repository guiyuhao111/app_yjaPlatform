package cn.net.gxht.app.yjdPlatform.common.entity;

import cn.net.gxht.app.yjdPlatform.common.entity.constant.Constants;
import cn.net.gxht.app.yjdPlatform.common.entity.constant.HttpHeader;
import cn.net.gxht.app.yjdPlatform.common.entity.constant.HttpSchema;
import cn.net.gxht.app.yjdPlatform.common.entity.enums.Method;
import org.apache.commons.collections.map.HashedMap;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/9/5.
 */
public class SendMessage {
    private final static List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList<String>();
    //APP KEY
    private final static String APP_KEY = "24603192";
    // APP密钥
    //916ee64c0f3fe063fbe0ab28cc21f948
    private final static String APP_SECRET = "e8a4f79d4a6955783b8815434795b638";
    //API域名
    private final static String HOST = "sms.market.alicloudapi.com";
    private final static String appcode = "0ed210ef6f544e559cda2912ae1210de";
    private final static String path = "/singleSendSms";
    public static Map sendMessage(String paramString, String signName, String phone, String templateCode) {
        System.out.println(paramString);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        headers.put("Authorization", "APPCODE " + appcode);
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("Authorization");
        Request request = new Request(Method.GET, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ParamString", paramString);
        querys.put("RecNum",phone);
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
        Map<String, Object> map1 = new HashedMap();
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
                        message = "获取验证码的次数收到限制";
                    }else if("The specified templateCode is wrongly formed.".equals(message)){
                        message ="指定的模板有问题";
                    }
                    throw new RuntimeException(message);
                }
                System.out.print(map1);
            }
        }
        return map1;
    }
}
