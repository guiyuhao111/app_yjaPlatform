package cn.net.gxht.app.yjdPlatform.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */
public class AppUser {
    private Integer id;
    private String name;
    private String password;
    private String phone;
    private String img;
    private String authCardNo;
    private String authFailedResult;
    private List<AuthImg> authImgs;
    /**
     * 商家查询的次数
     */
    private Integer times;
    /**
     * 商家注册的城市代码
     */
    private String citycode;
    /**
     * 商家注册名，公司为公司名，个人可以自定义
     */
    private String authName;
    /**
     * 商家认证结果
     */
    private String authStatus;
    /**
     * 商家认证
     */
    private Integer authState;
    /**
     * 商家认证的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date authTime;
    /**
     * 商家认证的额外的信息
     */
    private String authInfo;
    /**
     * 商家的排序
     */
    private Integer order;

    public String getAuthFailedResult() {
        return authFailedResult;
    }

    public void setAuthFailedResult(String authFailedResult) {
        this.authFailedResult = authFailedResult;
    }

    public String getAuthCardNo() {
        return authCardNo;
    }

    public void setAuthCardNo(String authCardNo) {
        this.authCardNo = authCardNo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<AuthImg> getAuthImgs() {
        return authImgs;
    }

    public void setAuthImgs(List<AuthImg> authImgs) {
        this.authImgs = authImgs;
    }

    public Integer getAuthState() {
        return authState;
    }

    public void setAuthState(Integer authState) {
        this.authState = authState;
    }


}
