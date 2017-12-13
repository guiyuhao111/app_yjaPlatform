package cn.net.gxht.app.yjdPlatform.merchant.service.impl;

import cn.net.gxht.app.yjdPlatform.common.dao.CommonDao;
import cn.net.gxht.app.yjdPlatform.common.entity.PageUtil;
import cn.net.gxht.app.yjdPlatform.common.entity.Page;
import cn.net.gxht.app.yjdPlatform.file.entity.FileUtil;
import cn.net.gxht.app.yjdPlatform.file.entity.ImgUtil;
import cn.net.gxht.app.yjdPlatform.merchant.dao.MerchantDao;
import cn.net.gxht.app.yjdPlatform.merchant.entity.ApplyInfo;
import cn.net.gxht.app.yjdPlatform.merchant.entity.MerchantBelong;
import cn.net.gxht.app.yjdPlatform.merchant.service.MerchantService;
import cn.net.gxht.app.yjdPlatform.pictures.dao.PicturesDao;
import cn.net.gxht.app.yjdPlatform.pictures.entity.MerchantPicture;
import cn.net.gxht.app.yjdPlatform.pictures.entity.QRcodeUtil;
import cn.net.gxht.app.yjdPlatform.user.service.UserService;
import com.alibaba.druid.sql.PagerUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.net.gxht.app.yjdPlatform.file.entity.FileUtil.uploadFileUtil;

/**
 * Created by Administrator on 2017/9/11.
 */
@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {
    @Resource
    private MerchantDao merchantDao;
    @Resource
    private PicturesDao picturesDao;
    @Resource
    private CommonDao commonDao;
    @Resource
    private UserService userService;
    Logger logger = LogManager.getLogger(MerchantService.class);

    public Map<String, Object> findMerchantService(Integer belongId, Integer adcode, Page page, HttpSession session) {
        System.out.printf("citycode" + adcode);
        session.setAttribute("citycode", adcode);
        session.setAttribute("BelongId", belongId);
        /**
         * 1.通过citycode寻找到所有clientId
         * 2.通过clientId寻找到所有的merchant
         */
        System.out.println("belongId = [" + belongId + "], adcode = [" + adcode + "], page = [" + page + "], session = [" + session + "]");
        List<Map<String, Object>> list = merchantDao.findMerchantBybelongIdAndCityCode(adcode, belongId);

        Map<String, Object> map = PageUtil.byPage(list, page);
        return map;
    }
    @Override
    public void MerchantEnter(MerchantPicture merchantPicture, ApplyInfo applyInfo, MerchantBelong merchantBelong, MultipartFile businessLicenseFile, MultipartFile merchantFile, String realPath, String token) throws Exception {
        Map<String, Object> tokenMap = commonDao.checkToken(token);
        if (tokenMap == null) {
            throw new RuntimeException("请先登录");
        }
        String title = merchantPicture.getTitle();
        if (title == null) {
            throw new RuntimeException("请填写标题");
        }
        if (title.length() > 8) {
            throw new RuntimeException("抱歉标题太长");
        }
        if (merchantPicture.getContent() == null) {
            throw new RuntimeException("内容介绍不得为空");
        }
        Integer userId = (Integer) tokenMap.get("userId");
        String url = "https://www.gxht.net.cn/app_yjdPlatform/";
        applyInfo.setUserId(userId);
        //假如用户是企业入驻就需要上传企业营业执照
        /**
         * 营业执照压缩文件访问的路径
         */
        String zipBLFPath = "";
        if (applyInfo.getTypeId() == 2) {
            applyInfo.setBusinessLicense(url + "userBusinessLicenseFile/userId" + userId + "/" + businessLicenseFile.getOriginalFilename());
            String blfRealPath = realPath + "userBusinessLicenseFile" + File.separator + "userId" + userId + File.separator;
            //
            zipBLFPath = url + "userBusinessLicenseFile/userId" + userId + "/zip" + businessLicenseFile.getOriginalFilename();
            String blfOfileName = businessLicenseFile.getOriginalFilename();
            String formFile = blfRealPath + blfOfileName;
            String toFile = blfRealPath + "zip" + blfOfileName;
            FileUtil.uploadFileUtil(blfRealPath, businessLicenseFile);
            ImgUtil.resizePng(formFile, toFile, ImgUtil.OUTPUTWIDTH, ImgUtil.OUTPUTHEIGHT, ImgUtil.PROPORTION);
        }
        applyInfo.setZipBusinessLicense(zipBLFPath);
        Integer state = merchantDao.insertIntoApplyInfo(applyInfo);
        if (state <= 0) {
            throw new RuntimeException("插入用户数据失败1");
        }
        Integer applyInfoId = applyInfo.getApplyId();
        merchantPicture.setApplyInfoId(applyInfoId);
        String oFileName = merchantFile.getOriginalFilename();
        merchantPicture.setImg(url + "userApplyPhotos/userId" + userId + "/" + oFileName);
        String mpRrealPath = realPath + "userApplyPhotos" + File.separator + "userId" + userId + File.separator;
        uploadFileUtil(mpRrealPath, merchantFile);
        state = picturesDao.insertMerchantPicture(merchantPicture);
        if (state <= 0) {
            throw new RuntimeException("插入用户数据失败2");
        }
        Integer merchantId = merchantPicture.getMerchantPictureId();
        merchantBelong.setMerchantId(merchantId);
        state = merchantDao.MerchantEnter(merchantBelong);
        if (state <= 0) {
            throw new RuntimeException("插入用户数据失败3");
        }
        /**
         * 生成二维码，将二维码的访问路径保存至数据库
         */
        String qrPathIndataBase = url + "userApplyPhotos/userId" + userId + "/" + "zip" + applyInfo.getApplyId() + ".png";
        String qrPath = realPath + "userApplyPhotos" + File.separator + "userId" + userId + File.separator + "zip" + applyInfo.getApplyId() + ".png";
        String logoPath = mpRrealPath + oFileName;
        createQRcode(merchantBelong.getBelongId(), qrPath, merchantBelong.getMerchantId(), logoPath);
        /**
         * 将qrPathIndataBase保存至数据库
         */
        Integer updateQrImgState = merchantDao.updataQrPathByApplyId(qrPathIndataBase, applyInfoId);
        if (updateQrImgState <= 0) {
            throw new RuntimeException("保存用户二维码失败");
        }
    }

    public void createQRcode(Integer belongId, String QRpath, Integer merchantId, String logoPath) throws Exception {
        String contents = "https://www.gxht.net.cn/app_yjdPlatform/returnSharePage.do?" + "belongId=" + belongId + "&id=" + merchantId+"&state=1";
        QRcodeUtil.makeQRcode(contents, QRcodeUtil.QRCODE_SIZE, logoPath, QRcodeUtil.LOGO_SIZE, QRcodeUtil.QRCODE_IMG_FORMAT, QRpath);
    }

    public Map<String, Object> findApplyInfo(String token, Page page) {
        Integer userId = userService.findUserIdBytoken(token);
        List<Map<String, Object>> mapList = merchantDao.findUserApplyInfo(userId);
        System.out.println(mapList);
        for (Map<String, Object> map : mapList) {
            Date date = (Date) map.get("createDate");
            System.out.println(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("createDate", simpleDateFormat.format(date));
        }
        page.setPageSize(3);
        Map<String, Object> map = PageUtil.byPage(mapList, page);
        return map;
    }

    public Map<String, Object>[] findApplyInfoByApplyInfoId(Integer applyInfoId) {
        Map<String, Object> map = merchantDao.findApplyInfoByApplyInfoId(applyInfoId);
        if (map == null) {
            throw new RuntimeException("没有查到相关信息");
        }
        Date date = (Date) map.get("createDate");
        System.out.println(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("createDate", simpleDateFormat.format(date));
        Map<String, Object>[] maps = new HashMap[1];
        maps[0] = map;
        return maps;
    }

}
