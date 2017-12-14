package cn.net.gxht.app.yjdPlatform.common.controller;

import cn.net.gxht.app.yjdPlatform.common.entity.Page;
import cn.net.gxht.app.yjdPlatform.common.entity.jsonResult.JsonResult;
import cn.net.gxht.app.yjdPlatform.common.entity.request.GetTest;
import cn.net.gxht.app.yjdPlatform.common.service.CommonService;
import cn.net.gxht.app.yjdPlatform.pictures.entity.Picture;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.MD5;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Administrator on 2017/9/18.
 * 你好
 */
@Controller
class CommonController {
    @Resource
    private CommonService commonService;

    /**
     * 通过前端传过来的坐标获取当前的位置
     * 虽然这个与第三方接口的对接完全可以交给前端完成,但后端需要获取到用户当前的未知来获取相应的商家
     * 所以将这个对接交给了后端完成，获取城市后将城市的citycode放到session里面，用于用户点击进入商家
     * 页面获取啥商家
     *
     * @param latitude  坐标
     *                  *
     * @param longitude 坐标
     *                  *
     * @param session   *
     * @return *
     * @throws IOException
     */
    @RequestMapping("/getLocation")
    @ResponseBody
    public JsonResult doGetLocationInfo(String latitude, String longitude, HttpSession session) throws IOException {
        Map<String, Object> map = new HashedMap();
        Map<String, Object> resultMap = new HashedMap();
        map.put("location", latitude + "," + longitude);
        map.put("get_poi", "1");
        map.put("key", "Q7YBZ-UQ5LW-EPHRD-RVBSN-PSALJ-FLFBQ");
        StringBuilder url = new StringBuilder("http://apis.map.qq.com/ws/geocoder/v1/");
        JSONObject returnJsonObject = GetTest.sendGetReturnJsonString(map, url);
        System.out.printf(returnJsonObject.toString());
        JSONObject result = (JSONObject) returnJsonObject.get("result");
        JSONObject ad_info = (JSONObject) result.get("ad_info");
        String province = (String) ad_info.get("province");
        String city = (String) ad_info.get("city");
        String location = province + city;
        session.setAttribute("location", location);
        session.setAttribute("adcode", ad_info.getString("city_code"));
        resultMap.put("location", location);
        resultMap.put("adcode", ad_info.getString("city_code"));
        city = substringCity(city);
        resultMap.put("city", city);
        return new JsonResult(resultMap);
    }

    /**
     * 寻找所有城市,后来废弃了此接口改为返回前端cityList
     * 故这段代码可以去掉
     *
     * @return
     */
    @RequestMapping("/findCities")
    @ResponseBody
    public JsonResult doFindCities() {
        Map map = commonService.findCity();
        return new JsonResult(map);
    }

    /**
     * 寻找所有的信用卡(也可以根据id来寻找信用卡,根据id寻找信用卡即是数组的第一个元素)
     * 如果你有时间可以最好是改为limit查询,当然现在mybatis有分页的查询的插件
     *
     * @param page *
     * @param id   信用卡的id
     *             *
     * @return
     */
    @RequestMapping("/findCards")
    @ResponseBody
    public JsonResult findCards(Page page, Integer id) {
        Map map = commonService.findCards(page, id);
        return new JsonResult(map);
    }

    /**
     * 寻找所有的贷款
     * 也可以根据id来寻找所用贷款,根据id寻找贷款即是数组的第一个元素
     *
     * @param page 分页的对象
     *             *
     * @param id   *
     * @return
     */
    @RequestMapping("/findLoads")
    @ResponseBody
    public JsonResult findLoads(Page page, Integer id) {
        Map map = commonService.findLoads(page, id);
        return new JsonResult(map);
    }

    /**
     * 去掉城市中的市,前端无理取闹的要求
     *
     * @param cityName *
     * @return
     */
    public String substringCity(String cityName) {
        Integer orgLength = cityName.length();
        if (cityName.endsWith("市")) {
            cityName = cityName.substring(0, orgLength - 1);
        }
        return cityName;
    }

    /**
     * 扫二维码返回分享页面,初学者可能不太了解二维码,二维码其实就是一端文本进行加密，
     * 用户扫二维码后会获取这段文本,假如这段文本是一个连接，通过浏览器扫二维码时会跳
     * 转到这个链接,相当于用户点击这个连接,我在创建二维码时这个链接会带有三个参数belongId id state
     * 经过一些操作用户会在扫app进入app后这三个参数会被带到app前端去,app前
     * 端会根据这三个参数进入对应的商家申请贷款页面(了解)
     *
     * @param session  *
     * @param belongId *
     * @param id       *
     * @param state    *
     * @return *
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/returnSharePage")
    public String reTurnSharePage(HttpSession session, String belongId, String id, Integer state) {
        session.setAttribute("belongId", belongId);
        session.setAttribute("id", id);
        session.setAttribute("state", state);
        return "/erweimaShare";
    }

    /**
     * 寻找热门贷款
     *
     * @param page *
     * @param id   *
     * @return
     */
    @RequestMapping("/findHotLoans")
    @ResponseBody
    public JsonResult findHotLoans(Page page, Integer id) {
        Map hotLoansMap = commonService.findHotLoans(page, id);
        return new JsonResult(hotLoansMap);
    }

    /**
     * 获得前端喇叭的message
     *
     * @return
     */
    @RequestMapping("/findMessageData")
    @ResponseBody
    public JsonResult findMessageData() {
        return new JsonResult(commonService.GetMessageDefault());
    }


    /**
     * 以下三个接口是后台数据接口
     */

    /**
     * 更新app图片(页面)
     *
     * @param classType     图片的类型
     * @param picture       图片对象封装了图片的基本信息
     * @param multipartFile 图片的文件对象
     * @param request       用于获取当前根目录
     * @param url           图片的访问路径
     * @return *
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/appUpdataPageInfo")
    @ResponseBody
    public JsonResult updatePageInfo(String classType, Picture picture, MultipartFile multipartFile, HttpServletRequest request, String url) throws UnsupportedEncodingException {
        commonService.updatePageInfo(classType, picture, multipartFile, request, url);
        return new JsonResult();
    }

    /**
     * @param classType 删除图片的类型
     * @param id        *
     * @param request   *
     * @param path      要删除图片的路径
     * @return
     */
    @RequestMapping("/deletePageInfo")
    @ResponseBody
    public JsonResult deletePageInfo(String classType, Integer id, HttpServletRequest request, String path) {
        commonService.deletePageInfoById(classType, id, path, request);
        return new JsonResult();
    }

    /**
     * @param multipartFile 添加页面(轮播图,九宫格,热门贷款，信用卡)
     * @param picture       图片对象
     * @param url           图片访问链接
     * @param request       *
     * @param imgType       图片类型(轮播图,九宫格,热门贷款，信用卡)
     * @return *
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/addPageListObject")
    @ResponseBody
    public JsonResult addPageObjectListObject(MultipartFile multipartFile, Picture picture, String url, HttpServletRequest request, String imgType) throws UnsupportedEncodingException {
        commonService.addPageObjectListObject(multipartFile, picture, url, request, imgType);
        return new JsonResult();
    }
}
