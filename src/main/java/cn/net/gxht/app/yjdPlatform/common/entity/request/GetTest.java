package cn.net.gxht.app.yjdPlatform.common.entity.request;

/**
 * Created by Administrator on 2017/9/15.
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * 用于测试与第三方交接时发送Get请求返回json字符串
 */
public class GetTest {
    public static JSONObject sendGetReturnJsonString(Map<String,Object> map, StringBuilder url) throws IOException {
        if(map!=null){
            url.append("?");
            int times=0;
            for(String key:map.keySet()){
                if(times!=0){
                    url.append("&");
                }
                url.append(key+"="+map.get(key));
                times++;
            }
        }
        System.out.println("map = [" + map + "], url = [" + url + "]");
        System.out.printf(url.toString());
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url.toString());
        httpGet.setHeader("Content-Type","text/javascript");
        String responseString="";
        HttpResponse response=httpClient.execute(httpGet);
        HttpEntity entity=response.getEntity();
        if(entity!=null){
            responseString=EntityUtils.toString(entity,"utf-8");
        }
        return JSONObject.parseObject(responseString);
    }
}
