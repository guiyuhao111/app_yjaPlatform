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
    private Logger logger= LogManager.getLogger(MerchantController.class);
    @RequestMapping("/findMerchants")
    @ResponseBody
    public JsonResult findMerchants(Integer hengpaiId, Integer adcode, Page page, HttpSession session){
        Map<String,Object> map= merchantService.findMerchantService(hengpaiId,adcode,page,session);
        return  new JsonResult(map);
    }
    //申请入驻管理
    @RequestMapping("/merchantEnter")
    @ResponseBody
    public JsonResult merchantEnter(MerchantPicture merchantPicture, ApplyInfo applyInfo, MerchantBelong merchantBelong, MultipartFile businessLicenseFile, MultipartFile merchantFile, HttpServletRequest request,String token) throws Exception {
        String realPath=request.getServletContext().getRealPath("/");
        logger.debug("用户入驻申请:"+merchantPicture+","+applyInfo+","+merchantBelong+","+businessLicenseFile+","+merchantFile+","+token);
        merchantService.MerchantEnter(merchantPicture,applyInfo,merchantBelong,businessLicenseFile,merchantFile,realPath,token);
        return new JsonResult();
    }
    //寻找用户申请记录
    @RequestMapping("/findUserEnterInfo")
    @ResponseBody
    public JsonResult findUserEnterInfo(String token,Page page){
        Map<String,Object> map=merchantService.findApplyInfo(token,page);
        return new JsonResult(map);
    }
    @RequestMapping("/findApplyInfoByApplyInfoId")
    @ResponseBody
    JsonResult findApplyInfoByApplyInfoId(Integer applyInfoId){
        return new JsonResult(merchantService.findApplyInfoByApplyInfoId(applyInfoId));
    };
}
