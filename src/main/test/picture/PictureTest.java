package picture;

import cn.net.gxht.app.yjdPlatform.common.dao.CommonDao;
import cn.net.gxht.app.yjdPlatform.pictures.dao.PicturesDao;
import cn.net.gxht.app.yjdPlatform.pictures.entity.BufferedImageLuminanceSource;
import cn.net.gxht.app.yjdPlatform.pictures.entity.MatrixToImageWriter;
import cn.net.gxht.app.yjdPlatform.pictures.entity.MerchantPicture;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import common.BaseTest;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10604 on 2017/8/28.
 */
public class PictureTest extends BaseTest {
    PicturesDao dao;
    CommonDao commonDao;

    @Before
    public void BeforTest() {
        dao = ctx.getBean("picturesDao", PicturesDao.class);
        commonDao = ctx.getBean("commonDao", CommonDao.class);
    }
    @Test
    public void findPicturesTest() {
        List<Map<String, Object>> list = dao.findPictures(null);
        System.out.println(list);
    }

    @Test
    public void findPictureTest() {
        List<Map<String, Object>> list = dao.findRegistPicture();
        System.out.println(list);
    }
    @Test
    public void findByBelongIdTest(){
        String path=dao.findPictureByBelongId(4);
        System.out.println(path);
    }
    @Test
    public void testFindTitleByPictureId(){
        String title = dao.findTileByPictureId(7);
        System.out.println(title);
    }
    @Test
    public void testFindPageTwoImg(){
        List<Map<String,Object>> list=dao.findPageTwoImg();
        System.out.println(list);
    }
    @Test
    public void testFindMerchantByIdAndPlace(){
//        List<Map<String,Object>> list=dao.findMerchantByIdAndCityCode(156330200);
//        System.out.println(list);
    }
    @Test
    public void testFindByTitle(){
        List<Map<String,Object>> list=dao.fuzzyQuery("国信",4,156330200);
        System.out.println(list);
    }
    @Test
    public void findPictureId(){
        Map<String,Object> map=dao.findPicture(42,null);
        System.out.println(map);
        Integer price=(Integer)map.get("price");
        System.out.println(price);
    }
    @Test
    public void TestFindContactByPictureId(){
        String contact = dao.findContactByPictureId(4);
        System.out.println(contact);
    }
    @Test
    public void testFindPhone(){
        Integer integer= dao.findByPhone("13780088569");
        System.out.printf(integer+"");
    }
    @Test
    public void findClientByPhone(){
        List<Map<String,Object>> list=dao.findClientByPhone("13655880858");
        System.out.println(list);
        Integer clientId=0;
        if(list==null){
            throw new RuntimeException("此功能只对入驻的商家提供服务");
        }
        if(list!=null){
            clientId = (Integer) list.get(0).get("clientId");
        }
        System.out.println(clientId);
        Integer times=dao.findClientQueryTimes(clientId);
        System.out.println(times);
        if(times<=0){
            throw new RuntimeException("您查询次数已达上限,请及时联系客服");
        }
        times--;
        Integer integer=dao.updateClientQueryTimes(clientId,times);
        if(integer<=0){
            throw new RuntimeException("系统异常，更新商家查询次数失败");
        }
    }

    @Test
    public void findLunBoPicture(){
        String [] strings=dao.findLunboPictures();
        System.out.println(strings);
    }
    @Test
    public void test(){
        try {
            String content = "https://www.gxht.net.cn/yjdPlatform/id=1";
            String path = "C:/Users/Administrator";
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
            File file1 = new File(path,"gxht002.jpg");
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test2(){
        try {
            MultiFormatReader formatReader = new MultiFormatReader();
            String filePath = "C:/Users/Administrator/餐巾纸.jpg";
            File file = new File(filePath);
            BufferedImage image = ImageIO.read(file);;
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer  binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            Result result = formatReader.decode(binaryBitmap,hints);

            System.out.println("result = "+ result.toString());
            System.out.println("resultFormat = "+ result.getBarcodeFormat());
            System.out.println("resultText = "+ result.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public  void testInsertMerchantPicture(){
        MerchantPicture merchantPicture=new MerchantPicture();
        merchantPicture.setApplyInfoId(2);
        merchantPicture.setContent("测试Content");
        merchantPicture.setImg("test.img");
        merchantPicture.setTitle("侧试title");
        Integer i=dao.insertMerchantPicture(merchantPicture);
        System.out.println(merchantPicture.getMerchantPictureId());
    }
}