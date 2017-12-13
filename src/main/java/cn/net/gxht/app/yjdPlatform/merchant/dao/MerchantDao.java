package cn.net.gxht.app.yjdPlatform.merchant.dao;

import cn.net.gxht.app.yjdPlatform.merchant.entity.ApplyInfo;
import cn.net.gxht.app.yjdPlatform.merchant.entity.MerchantBelong;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/11.
 */
public interface MerchantDao {
    List<Map<String,Object>> findByBelongIdAndMerchantId(@Param(value = "belongId") Integer hengpaiId, @Param(value = "merchantId") Integer merchantId);
    List<Map<String,Object>> findMerchantBybelongIdAndCityCode(@Param(value = "citycode")Integer citycode,@Param(value = "belongId") Integer belongId);

    Integer insertIntoApplyInfo(ApplyInfo applyInfo);
    Integer MerchantEnter(MerchantBelong merchantBelong);
    Date testGetTime(Integer id);

    List<Map<String,Object>> findUserApplyInfo(Integer userId);
    Integer updataQrPathByApplyId(@Param(value = "qrPath") String qrPath,@Param(value="applyId") Integer applyId);
    Map<String, Object> findApplyInfoByApplyInfoId(Integer applyInfoId);
}
