package cn.net.gxht.app.yjdPlatform.user.entity.md5;

import java.util.*;

/**
 * Created by Administrator on 2017/9/25.
 */
public class SecurityUtil {
    public static String authentication(Map<String, Object> srcData) {
        //排序，根据keyde 字典序排序
        if (null == srcData) {
            throw new RuntimeException("传入参数为空");
        }
        List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(srcData.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            //升序排序
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuffer srcSb = new StringBuffer();
        for (Map.Entry<String, Object> srcAtom : list) {
            srcSb.append(String.valueOf(srcAtom.getValue()));
        }
        System.out.println("身份验证加密前字符串：" + srcSb.toString());
        //计算token
        String token = MD5.md5(srcSb.toString());
//      System.out.println(cToken);//for test
        System.out.println(token);
        return token;
    }
}
