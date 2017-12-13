package cn.net.gxht.app.yjdPlatform.common.controller

import cn.net.gxht.app.yjdPlatform.common.entity.Page
import cn.net.gxht.app.yjdPlatform.common.entity.jsonResult.JsonResult
import cn.net.gxht.app.yjdPlatform.common.entity.request.GetTest
import cn.net.gxht.app.yjdPlatform.common.service.CommonService
import cn.net.gxht.app.yjdPlatform.pictures.entity.Picture
import cn.net.gxht.app.yjdPlatform.user.entity.md5.MD5
import com.alibaba.fastjson.JSONObject
import org.apache.commons.collections.map.HashedMap
import org.omg.CORBA.Object
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.multipart.MultipartFile

import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.*

/**
 * Created by Administrator on 2017/9/18.
 */
@Controller
class CommonController {
    @Resource
    internal var commonService: CommonService? = null

    /**
     * 通过前端传过来的坐标获取当前的位置
     * 虽然这个与第三方接口的对接完全可以交给前端完成,但后端需要获取到用户当前的未知来获取相应的商家
     * 所以将这个对接交给了后端完成，获取城市后将城市的citycode放到session里面，用于用户点击进入商家
     * 页面获取啥商家

     * @param latitude  坐标
     * *
     * @param longitude 坐标
     * *
     * @param session
     * *
     * @return
     * *
     * @throws IOException
     */
    @RequestMapping("/getLocation")
    @ResponseBody
    fun doGetLocationInfo(latitude: String, longitude: String, session: HttpSession): JsonResult {
        val map = HashMap<String, Any>()
        val resultMap = HashedMap()
        map.put("location", latitude + "," + longitude)
        map.put("get_poi", "1")
        map.put("key", "Q7YBZ-UQ5LW-EPHRD-RVBSN-PSALJ-FLFBQ")
        val url = StringBuilder("http://apis.map.qq.com/ws/geocoder/v1/")
        val returnJsonObject = GetTest.sendGetReturnJsonString(map, url)
        System.out.printf(returnJsonObject.toString())
        val result = returnJsonObject["result"] as JSONObject
        val ad_info = result["ad_info"] as JSONObject
        val province = ad_info["province"] as String
        var city = ad_info["city"] as String
        val location = province + city
        session.setAttribute("location", location)
        session.setAttribute("adcode", ad_info.getString("city_code"))
        resultMap.put("location", location)
        resultMap.put("adcode", ad_info.getString("city_code"))
        resultMap.put("city", city)
        return JsonResult(resultMap)
    }

    /**
     * 寻找所有城市,后来废弃了此接口改为返回前端cityList
     * 故这段代码可以去掉

     * @return
     */
    @RequestMapping("/findCities")
    @ResponseBody
    fun doFindCities(): JsonResult {
        val map = commonService!!.findCity()
        return JsonResult(map)
    }

    /**
     * 寻找所有的信用卡(也可以根据id来寻找信用卡,根据id寻找信用卡即是数组的第一个元素)
     * 如果你有时间可以最好是改为limit查询,当然现在mybatis有分页的查询的插件

     * @param page
     * *
     * @param id   信用卡的id
     * *
     * @return
     */
    @RequestMapping("/findCards")
    @ResponseBody
    fun findCards(page: Page, id: Int?): JsonResult {
        val map = commonService!!.findCards(page, id)
        return JsonResult(map)
    }

    /**
     * 寻找所有的贷款
     * 也可以根据id来寻找所用贷款,根据id寻找贷款即是数组的第一个元素

     * @param page 分页的对象
     * *
     * @param id
     * *
     * @return
     */
    @RequestMapping("/findLoads")
    @ResponseBody
    fun findLoads(page: Page, id: Int?): JsonResult {
        val map = commonService!!.findLoads(page, id)
        return JsonResult(map)
    }

    /**
     * 取掉城市中的市,前端无理取闹的要求

     * @param cityName
     * *
     * @return
     */
    //    fun substringCity(cityName: String): String {
//        var cityName = cityName
//        val orgLength = cityName.length
//        if (cityName.endsWith("市")) {
//            cityName = cityName.substring(0, orgLength - 1)
//        }
//        return cityName
//    }

    /**
     * 扫二维码返回分享页面

     * @param session
     * *
     * @param belongId
     * *
     * @param id
     * *
     * @param state
     * *
     * @return
     * *
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/returnSharePage")
    fun reTurnSharePage(session: HttpSession, belongId: String, id: String, state: Int?): String {
        session.setAttribute("belongId", belongId)
        session.setAttribute("id", id)
        session.setAttribute("state", state)
        return "/erweimaShare"
    }

    /**
     * 寻找热门贷款

     * @param page
     * *
     * @param id
     * *
     * @return
     */
    @RequestMapping("/findHotLoans")
    @ResponseBody
    fun findHotLoans(page: Page, id: Int?): JsonResult {
        val hotLoansMap = commonService!!.findHotLoans(page, id)
        return JsonResult(hotLoansMap)
    }

    /**
     * 获得前端喇叭的message

     * @return
     */
    @RequestMapping("/findMessageData")
    @ResponseBody
    fun findMessageData(): JsonResult {
        return JsonResult(commonService!!.GetMessageDefault())
    }

    /**
     * 更新app图片(页面)

     * @param classType     图片的类型
     * *
     * @param picture       图片对象封装了图片的基本信息
     * *
     * @param multipartFile 图片的文件对象
     * *
     * @param request       用于获取当前根目录
     * *
     * @param url           图片的访问路径
     * *
     * @return
     * *
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/appUpdataPageInfo")
    @ResponseBody
    fun updatePageInfo(classType: String, picture: Picture, multipartFile: MultipartFile, request: HttpServletRequest, url: String): JsonResult {
        commonService!!.updatePageInfo(classType, picture, multipartFile, request, url)
        return JsonResult()
    }

    /**
     * @param classType 删除图片的类型
     * *
     * @param id
     * *
     * @param request
     * *
     * @param path      要删除图片的路径
     * *
     * @return
     */
    @RequestMapping("/deletePageInfo")
    @ResponseBody
    fun deletePageInfo(classType: String, id: Int?, request: HttpServletRequest, path: String): JsonResult {
        commonService!!.deletePageInfoById(classType, id, path, request)
        return JsonResult()
    }

    /**
     * @param multipartFile 添加页面(轮播图,九宫格,热门贷款，信用卡)
     * *
     * @param picture       图片对象
     * *
     * @param url           图片访问链接
     * *
     * @param request
     * *
     * @param imgType       图片类型(轮播图,九宫格,热门贷款，信用卡)
     * *
     * @return
     * *
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/addPageListObject")
    @ResponseBody
    fun addPageObjectListObject(multipartFile: MultipartFile, picture: Picture, url: String, request: HttpServletRequest, imgType: String): JsonResult {
        commonService!!.addPageObjectListObject(multipartFile, picture, url, request, imgType)
        return JsonResult();
    }
}
