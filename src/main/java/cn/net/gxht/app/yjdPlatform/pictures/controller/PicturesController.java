package cn.net.gxht.app.yjdPlatform.pictures.controller;

import cn.net.gxht.app.yjdPlatform.common.entity.jsonResult.JsonResult;
import cn.net.gxht.app.yjdPlatform.pictures.entity.MatrixToImageWriter;
import cn.net.gxht.app.yjdPlatform.pictures.service.PicturesService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
@Controller
public class PicturesController {
    @Resource
    private PicturesService picturesService;

    /**
     * 被废弃的接口不做解释,可以删除
     *
     * @param id
     * @return
     */
//    @RequestMapping("/findSonPicture")
//    @ResponseBody
//    public JsonResult doFindSonPicture(int id) {
//        String path = picturesService.findPictureByBelongId(id);
//        return new JsonResult(path);
//    }

    /**
     * 寻找轮播图图片url
     *
     * @return
     */
    @RequestMapping("/findLunbo")
    @ResponseBody
    public JsonResult doFindLunboPictures() {
        String[] pictures = picturesService.findGundongPictures();
        return new JsonResult(pictures);
    }

    /**
     * 寻早九宫格url
     *
     * @param id
     * @return
     */
    @RequestMapping("/findHengpai")
    @ResponseBody
    public JsonResult doFindHengpaiPictures(Integer id) {
        Map<String, Object> map = picturesService.findHengpaiPictures(id);
        return new JsonResult(map);
    }

    /**
     * 被抛弃的接口不做解释
     *
     * @return
     */
//    @RequestMapping("/findShupai")
//    @ResponseBody
//    public JsonResult doFindShupaiPictures() {
//        Map<String, Object> map = picturesService.findShupaiPictures();
//        return new JsonResult(map);
//    }

//    /**
//     * 被废弃的接口不做解释
//     *
//     * @return
//     */
//    @RequestMapping("/findRegistPicture")
//    @ResponseBody
//    public JsonResult doFindRegistPicture() {
//        return new JsonResult(picturesService.findRegistPicture());
//    }

    /**
     * 寻找所有商家
     *
     * @param request
     * @return
     */
    @RequestMapping("/findMerchant")
    @ResponseBody
    public JsonResult fuzzyQuery(HttpServletRequest request) {
        String title = request.getParameter("title");
        HttpSession session = request.getSession();
        try {
            title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("系统异常：数据转换异常");
        }
        List<Map<String, Object>> list = picturesService.fuzzyQuery(title, session);
        return new JsonResult(list);
    }

    /**
     * 写的demo可以删除
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getQRCode")
    @ResponseBody
    public String getQRCode(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getSession().getServletContext().getRealPath("/");
        System.out.println("url:" + url);
        try {
            String content = "www.gxht.net.cn";
            String path = url;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
            File file1 = new File(path, "gxhtLTD.jpg");
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 返回商家入驻时所有选择的入驻类型
     *
     * @return
     */
    @RequestMapping("/findSelectOption")
    @ResponseBody
    public JsonResult findSelectOption() {
        List<Map<String, Object>> list = picturesService.findSelectOption();
        return new JsonResult(list);
    }

    ;

    /**
     * 根据图片id寻找图片(可以和寻找所有图片的接口写在一起)
     *
     * @param id
     * @return
     */
    @RequestMapping("/findPictureById")
    @ResponseBody
    public JsonResult findPictureById(Integer id) {
        return new JsonResult(picturesService.findPictrue(id));
    }

    /**
     * 根据图片的类型选择图片
     *
     * @param type
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/findPictureByType")
    @ResponseBody
    public JsonResult findPictureByType(String type) throws UnsupportedEncodingException {
        type = new String(type.getBytes("ISO8859-1"), "UTF-8");
        return new JsonResult(picturesService.findPictureByType(type));
    }
}
