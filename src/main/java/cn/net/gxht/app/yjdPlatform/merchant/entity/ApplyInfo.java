package cn.net.gxht.app.yjdPlatform.merchant.entity;

import cn.net.gxht.app.yjdPlatform.pictures.entity.MerchantPicture;


/**
 * Created by Administrator on 2017/10/18.
 */

/**
 *
 *  appPicture.title applyType
    FROM (SELECT
    city.cityName
 */
public class ApplyInfo {
    //申请记录的id
    private Integer applyId;
    //申请入驻的城市
    private Integer citycode;
    //申请入驻的用户id
    private Integer userId;
    //申请入驻的信用代码
    private String creditCode;
    //申请入驻的状态
    private Integer status;
    //申请记录的类别
    private Integer typeId;
    //申请入驻的企业营业执照
    private String businessLicense;
    //企业营业执照的压缩文件
    private String zipBusinessLicense;
    //申请入驻的商标以及相关字段的信息
    private String qrPath;
    private MerchantPicture merchantPicture;
    //申请入驻的产品信息
    private MerchantBelong merchantBelong;
    //公司名称
    private String companyName;

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getCitycode() {
        return citycode;
    }

    public void setCitycode(Integer citycode) {
        this.citycode = citycode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public MerchantPicture getMerchantPicture() {
        return merchantPicture;
    }

    public void setMerchantPicture(MerchantPicture merchantPicture) {
        this.merchantPicture = merchantPicture;
    }

    public MerchantBelong getMerchantBelong() {
        return merchantBelong;
    }

    public void setMerchantBelong(MerchantBelong merchantBelong) {
        this.merchantBelong = merchantBelong;
    }

    public String getZipBusinessLicense() {
        return zipBusinessLicense;
    }

    public void setZipBusinessLicense(String zipBusinessLicense) {
        this.zipBusinessLicense = zipBusinessLicense;
    }

    public String getQrPath() {
        return qrPath;
    }

    public void setQrPath(String qrPath) {
        this.qrPath = qrPath;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "ApplyInfo{" +
                "applyId=" + applyId +
                ", citycode=" + citycode +
                ", userId=" + userId +
                ", creditCode='" + creditCode + '\'' +
                ", status=" + status +
                ", typeId=" + typeId +
                ", businessLicense='" + businessLicense + '\'' +
                ", zipBusinessLicense='" + zipBusinessLicense + '\'' +
                ", qrPath='" + qrPath + '\'' +
                ", merchantPicture=" + merchantPicture +
                ", merchantBelong=" + merchantBelong +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
