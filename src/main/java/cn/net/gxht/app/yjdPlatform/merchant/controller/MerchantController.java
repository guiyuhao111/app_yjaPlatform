package cn.net.gxht.app.yjdPlatform.merchant.controller;

import cn.net.gxht.app.yjdPlatform.common.entity.jsonResult.JsonResult;
import cn.net.gxht.app.yjdPlatform.common.entity.Page;
import cn.net.gxht.app.yjdPlatform.merchant.entity.ApplyInfo;
import cn.net.gxht.app.yjdPlatform.merchant.entity.MerchantBelong;
import cn.net.gxht.app.yjdPlatform.merchant.service.MerchantService;
import cn.net.gxht.app.yjdPlatform.pictures.entity.MerchantPicture;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/11.
 */
@Controller
public class MerchantController {
    @Resource
    MerchantService merchantService;
    private Logger logger = LogManager.getLogger(MerchantController.class);

    /**
     * 寻找所有的商家
     *
     * @param hengpaiId 用户点击的具体的哪一个入驻类型的id
     * @param adcode    根据城市代码来寻找商家
     * @param page      分页查询
     * @param session   用于存储hengpaiId,模糊查询时就不用再从前端获取hengpaiId
     * @return
     */
    @RequestMapping("/findMerchants")
    @ResponseBody
    public JsonResult findMerchants(Integer hengpaiId, Integer adcode, Page page, HttpSession session) {
        Map<String, Object> map = merchantService.findMerchantService(hengpaiId, adcode, page, session);
        return new JsonResult(map);
    }

    /**
     * 申请入驻管理
     * 这个代码应该是我设计有问题,所有的参数应该都可以放到applyInfo对象里面的
     *
     * @param merchantPicture     入驻的图片的信息
     * @param applyInfo           入驻的信息(其实可以将merchantPicture放在applyInfo里面)
     * @param merchantBelong      商家入驻哪种类型(其实也可以放在applyInfo里面作为一个属性)
     * @param businessLicenseFile 商家入驻营业执照(其实也可以放在applyInfo里面作为一个属性)
     * @param merchantFile        商家入驻的图片(其实也可以放在applyInfo里面作为一个属性)
     * @param request             获取当前根目录
     * @param token               用户信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/merchantEnter")
    @ResponseBody
    public JsonResult merchantEnter(MerchantPicture merchantPicture, ApplyInfo applyInfo, MerchantBelong merchantBelong, MultipartFile businessLicenseFile, MultipartFile merchantFile, HttpServletRequest request, String token) throws Exception {
        String realPath = request.getServletContext().getRealPath("/");
        logger.debug("用户入驻申请:" + merchantPicture + "," + applyInfo + "," + merchantBelong + "," + businessLicenseFile + "," + merchantFile + "," + token);
        merchantService.MerchantEnter(merchantPicture, applyInfo, merchantBelong, businessLicenseFile, merchantFile, realPath, token);
        return new JsonResult();
    }
    //寻找用户申请记录

    /**
     * 获取用户入驻记录
     *
     * @param token 获取用户信息
     * @param page  分页(需要优化)
     * @return
     */
    @RequestMapping("/findUserEnterInfo")
    @ResponseBody
    public JsonResult findUserEnterInfo(String token, Page page) {
        Map<String, Object> map = merchantService.findApplyInfo(token, page);
        return new JsonResult(map);
    }

    /**
     * 根据入驻的id寻找商家入驻的信息是后台接口
     *
     * @param applyInfoId
     * @return
     */
    @RequestMapping("/findApplyInfoByApplyInfoId")
    @ResponseBody
    JsonResult findApplyInfoByApplyInfoId(Integer applyInfoId) {
        return new JsonResult(merchantService.findApplyInfoByApplyInfoId(applyInfoId));
    }

    ;
}
