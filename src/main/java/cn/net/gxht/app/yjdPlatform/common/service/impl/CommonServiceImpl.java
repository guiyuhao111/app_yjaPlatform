package cn.net.gxht.app.yjdPlatform.common.service.impl;

import cn.net.gxht.app.yjdPlatform.common.dao.CommonDao;
import cn.net.gxht.app.yjdPlatform.common.entity.Page;
import cn.net.gxht.app.yjdPlatform.common.entity.PageUtil;
import cn.net.gxht.app.yjdPlatform.common.service.CommonService;
import cn.net.gxht.app.yjdPlatform.file.entity.FileUtil;
import cn.net.gxht.app.yjdPlatform.pictures.dao.PicturesDao;
import cn.net.gxht.app.yjdPlatform.pictures.entity.Picture;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.net.gxht.app.yjdPlatform.common.entity.PageUtil.byPage;

/**
 * Created by Administrator on 2017/9/18.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CommonServiceImpl implements CommonService{
    @Resource
    private CommonDao commonDao;
    @Resource
    private PicturesDao pictureDao;
    public String checkUserLogin(String token, HttpSession session){
        return null;
    };
    public Map<String, Object> findCity() {
        String [] fistChars=new String[]{"A","B","C","D","E","F","G","H","Y","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        Map<String,Object> resultMap=new HashMap<String,Object>();
        for(String s:fistChars){
            resultMap.put(s,commonDao.findAllCity(s));
        }
        List<Map<String,Object>> hots=commonDao.findHotCity();
        resultMap.put("hotCity",hots);
        System.out.println(resultMap);
        return resultMap;
    }

    public Map<String,Object> findCards(Page page,Integer id) {
        List<Map<String,Object>> cardList=commonDao.findCards(id);
        page.setPageSize(20);
        Map<String,Object> cardInfoMap= PageUtil.byPage(cardList,page);
        return cardInfoMap;
    }

    public Map<String, Object> findLoads(Page page,Integer id) {
        List<Map<String,Object>> loadList=commonDao.findLoads(id);
        Map<String,Object> loadInfoMap= PageUtil.byPage(loadList,page);
        return loadInfoMap;

    }

    public Map<String, Object> findHotLoans(Page page,Integer id) {
        List<Map<String,Object>> hotLoansList=commonDao.findHotLoans(id);
        page.setPageSize(5);
        Map<String,Object> hotLoansMap= PageUtil.byPage(hotLoansList,page);
        return hotLoansMap;
    }

    public String [] GetMessageData() {
        List<Map<String,Object>> messageList = commonDao.GetMessageData();
        System.out.println(messageList);
        String [] messageArray=new String[messageList.size()];
        int index=0;
        for (Map<String,Object> map:messageList){
            String title=(String) map.get("title");
            String phone=(String) map.get("phone");
            phone=getPhone(phone);
            messageArray[index]=phone+"申请"+title+"成功";
            index++;
        }
        System.out.println(ArrayUtils.toString(messageArray));
        return messageArray;
    }

    public String[] GetMessageDefault() {
       return commonDao.GetMessageDefault();
    }

    public void updatePageInfo(String classType, Picture picture, MultipartFile multipartFile, HttpServletRequest request, String url) throws UnsupportedEncodingException {
        System.out.println("classType = [" + classType + "], picture = [" + picture + "], multipartFile = [" + multipartFile + "], request = [" + request + "], url = [" + url + "]");
        String title=picture.getTitle();
        String content=picture.getContent();
        String img=picture.getPath();
        Integer id=picture.getId();
        String realPath=request.getServletContext().getRealPath("/");
        if(multipartFile!=null){
            //删除文件
            img=img.replace("https://www.gxht.net.cn/app_yjdPlatform/",realPath).replace("\\","/");
            System.out.println("path:"+img);
            File file=new File(img);
            if(file.exists()){
                file.delete();
            }else {
                throw new RuntimeException("系统异常");
            }
            if("applyCardEdit".equals(classType)){
                realPath+="card"+File.separator;
                img="https://www.gxht.net.cn/app_yjdPlatform/card/"+multipartFile.getOriginalFilename();
            }else if("hotLoansEdit".equals(classType)){
                realPath+="hotLoans"+File.separator;
                img="https://www.gxht.net.cn/app_yjdPlatform/hotLoans/"+multipartFile.getOriginalFilename();
            }else if ("loansEdit".equals(classType)){
                realPath+="loans"+File.separator;
                img="https://www.gxht.net.cn/app_yjdPlatform/loans/"+multipartFile.getOriginalFilename();
            }else if ("SudokuEdit".equals(classType)){
                realPath+="henpai"+File.separator;
                img="https://www.gxht.net.cn/app_yjdPlatform/henpai/"+multipartFile.getOriginalFilename();
            }
            System.out.printf(realPath+"realPath");
            FileUtil.uploadFileUtil(realPath,multipartFile);
        }
        System.out.println("realPath"+realPath);
        if("applyCardEdit".equals(classType)){
            commonDao.updateCardsById(id,title,img,content,url);
        }else if("hotLoansEdit".equals(classType)){
            commonDao.updateHotLoansById(id,title,img,content,url);
        }else if ("loansEdit".equals(classType)){
            commonDao.updateLoansById(id,title,img,content,url);
        }else if ("SudokuEdit".equals(classType)){
            picture.setPath(img);
            pictureDao.updatePictureById(picture);
        }
    }

    public void deletePageInfoById(String classType, Integer id,String path,HttpServletRequest request) {
        //先根据url删除文
        //2.删除数据库记录
        int i=1;
        System.out.println("classType = [" + classType + "], id = [" + id + "], path = [" + path + "], request = [" + request + "]");
        String realPath=request.getServletContext().getRealPath("/");
        path=path.replace("https://www.gxht.net.cn/app_yjdPlatform/",realPath).replace("\\","/");
        System.out.println("path:"+path);
        File file=new File(path);
        if(file.exists()){
            file.delete();
        }else {
            throw new RuntimeException("系统异常");
        }
        if("applyCardEdit".equals(classType)){
            i=commonDao.deleteCardById(id);
        }else if("hotLoansEdit".equals(classType)){
            i=commonDao.deleteHotLoansById(id);
        }else if ("loansEdit".equals(classType)){
            i=commonDao.deleteLoadsById(id);
        }else if ("SudokuEdit".equals(classType)){
            i=pictureDao.deletePictureById(id);
        }else if("carouselEdit".equals(classType)){
            i=pictureDao.deletePictureById(id);
        }
        if(i<=0){
            throw new RuntimeException("删除数据库记录失败");
        }
    }

    public void addPageObjectListObject(MultipartFile multipartFile, Picture picture, String url, HttpServletRequest request, String imgType) throws UnsupportedEncodingException {
        //取得realPath
        //先将数据保存至数据库
        //将文件保存
        String realPath=request.getServletContext().getRealPath("/");
        String path="";
        String title=picture.getTitle();
        String content=picture.getContent();
        Integer insertResultState=null;
        System.out.println("multipartFile = [" + multipartFile + "], picture = [" + picture + "], url = [" + url + "], request = [" + request + "], imgType = [" + imgType + "]");
        if("applyCard".equals(imgType)){
            realPath+="card"+File.separator;
            path="https://www.gxht.net.cn/app_yjdPlatform/card/"+multipartFile.getOriginalFilename();
            insertResultState = commonDao.insertApplyCard(title,content,url,path);
        }else if("loans".equals(imgType)){
            realPath+="loans"+File.separator;
            path="https://www.gxht.net.cn/app_yjdPlatform/loans/"+multipartFile.getOriginalFilename();
            insertResultState = commonDao.insertApplyLoans(title,content,url,path);
        }else if ("hotLoans".equals(imgType)){
            realPath+="hotLoans"+File.separator;
            path="https://www.gxht.net.cn/app_yjdPlatform/hotLoans/"+multipartFile.getOriginalFilename();
            insertResultState=commonDao.insertHotLoans(title,content,url,path);
        }else if ("henpai".equals(imgType)){
            realPath+="henpai"+File.separator;
            path="https://www.gxht.net.cn/app_yjdPlatform/henpai/"+multipartFile.getOriginalFilename();
            insertResultState = pictureDao.insertPicture("横排",title,null,path,null);
        }else{
            realPath+="lunbo"+File.separator;
            path="https://www.gxht.net.cn/app_yjdPlatform/lunbo/"+multipartFile.getOriginalFilename();
            insertResultState = pictureDao.insertPicture("轮播",null,null,path,null);
        }
        if(insertResultState<=0){
            throw new RuntimeException("系统异常 插入数据错误");
        }
        FileUtil.uploadFileUtil(realPath,multipartFile);
    }

    public String getPhone(String phone){
        phone=phone.substring(0,3)+"****"+phone.substring(phone.length()-4);
        return phone;
    }
}
