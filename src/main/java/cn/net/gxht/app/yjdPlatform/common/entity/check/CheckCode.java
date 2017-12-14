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
}
