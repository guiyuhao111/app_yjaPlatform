package merchant;

import cn.net.gxht.app.yjdPlatform.merchant.dao.MerchantDao;
import cn.net.gxht.app.yjdPlatform.merchant.entity.ApplyInfo;
import common.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/11.
 */
    public class MerchantTest extends BaseTest{
 private MerchantDao merchantDao;
    @Before
    public void testBefor(){
        merchantDao=ctx.getBean("merchantDao",MerchantDao.class);
    }
    @Test
    public void testFindMerchat(){
        ApplyInfo applyInfo=new ApplyInfo();
        applyInfo.setCitycode(156330200);
        applyInfo.setUserId(1);
        applyInfo.setTypeId(1);
        merchantDao.insertIntoApplyInfo(applyInfo);
        System.out.println(applyInfo.getApplyId());
    }
    @Test
    public void testGetTime(){
//        Date date=merchantDao.testGetTime(15);
//        System.out.println(date);
    }
    @Test
    public void testFindApplyInfo(){
        List<Map<String,Object>> resultList=merchantDao.findUserApplyInfo(1);
    }
}
