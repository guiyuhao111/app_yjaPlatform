package cn.net.gxht.app.yjdPlatform.common.entity.commonInterceptor;

import cn.net.gxht.app.yjdPlatform.common.dao.CommonDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */

/**
 * 这个类的作用是检查session里面是否有token的记录
 * 没有token则从请求对象取出token,然后从数据库中查询这个token的相关信息再将这个token放入session，
 * 以及从数据库查询的信息放入token
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {
    /**
     * 注入查询token的Dao
     */
    @Resource
    CommonDao commonDao;
    Logger logger = LogManager.getLogger(CommonInterceptor.class);

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = (String) httpServletRequest.getParameter("token");
        HttpSession session = httpServletRequest.getSession();
        String sessionToken = (String) session.getAttribute("token");
        logger.debug("用户请求信息:客户端传入的token:" + token + " 服务端保存的token:" + sessionToken);
        System.out.println(token + "," + sessionToken);
        if (sessionToken == null) {
            Map<String, Object> tokenQueryInfoMap = commonDao.checkToken(token);
            if (tokenQueryInfoMap != null) {
                sessionToken = (String) tokenQueryInfoMap.get("token");
                session.setAttribute("token", sessionToken);
                Map<String, Object> tokenMap = new HashMap<String, Object>();
                tokenMap.put("tokenMap", (String) tokenQueryInfoMap.get("phone"));
            }
        }
        return true;
    }
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
