package cn.net.gxht.app.yjdPlatform.merchant.service;

import cn.net.gxht.app.yjdPlatform.common.entity.Page;
import cn.net.gxht.app.yjdPlatform.merchant.entity.ApplyInfo;
import cn.net.gxht.app.yjdPlatform.merchant.entity.MerchantBelong;
import cn.net.gxht.app.yjdPlatform.pictures.entity.MerchantPicture;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface MerchantService {
    Map<String,Object> findMerchantService(Integer BelongId, Integer adcode, Page page, HttpSession session);
    void MerchantEnter(MerchantPicture merchantPicture, ApplyInfo applyInfo, MerchantBelong merchantBelong, MultipartFile businessLicenseFile,MultipartFile merchantFile,String realPath,String token) throws Exception;
    Map<String,Object> findApplyInfo(String token,Page page);
    Map<String, Object> [] findApplyInfoByApplyInfoId(Integer applyInfoId);

}
