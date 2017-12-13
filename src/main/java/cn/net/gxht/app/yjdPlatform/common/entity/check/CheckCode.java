package cn.net.gxht.app.yjdPlatform.common.entity.check;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/25.
 */
@Component
public class CheckCode {
    /**
     * 检查验证码的类
     */
    public static void checkCode(String code, HttpSession session){
        /**
         * 从session里面取出code
         * 比较两个code是否相等
         */
        if("".equals(code)){
            throw new RuntimeException("请输入验证码");
        }
        String sessionCode=(String)session.getAttribute("code");
        if(sessionCode==null){
            throw new RuntimeException("没有获取验证码或验证码已过期");
        }
        if(!code.equals(sessionCode)){
            throw new RuntimeException("验证码输入错误");
        }
    }
//    public void checkCode(Map maps){
//        if("10001".equals(maps.get("code"))){
//            throw new RuntimeException("错误的请求appkey");
//        }
//        if("11010".equals(maps.get("code"))){
//            throw new RuntimeException("商家接口调用异常，请稍后再试");
//        }
//        if("11030".equals(maps.get("code"))){
//            throw new RuntimeException("商家接口返回格式有误");
//        }
//        if("10003".equals(maps.get("code"))){
//            throw new RuntimeException("不存在相应的数据信息");
//        }
//        if("10010".equals(maps.get("code"))){
//            throw new RuntimeException("接口需要付费，请充值");
//        }
//        if("10030".equals(maps.get("code"))){
//            throw new RuntimeException("调用万象网关失败， 请与万象联系");
//        }
//        if("10040".equals(maps.get("code"))){
//            throw new RuntimeException("超过每天限量，请明天继续");
//        }
//        if("10050".equals(maps.get("code"))){
//            throw new RuntimeException("用户已被禁用");
//        }
//        if("10060".equals(maps.get("code"))){
//            throw new RuntimeException("提供方设置调用权限，请联系提供方");
//        }
//        if("10070".equals(maps.get("code"))){
//            throw new RuntimeException("该数据只允许企业用户调用");
//        }
//        if("10090".equals(maps.get("code"))){
//            throw new RuntimeException("文件大小超限，请上传小于1M的文件");
//        }
//        if("10020".equals(maps.get("code"))){
//            throw new RuntimeException("万象系统繁忙，请稍后再试");
//        }
//    };
}
