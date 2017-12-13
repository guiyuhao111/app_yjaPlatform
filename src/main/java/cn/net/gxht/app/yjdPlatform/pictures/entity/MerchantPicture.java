package cn.net.gxht.app.yjdPlatform.pictures.entity;

/**
 * Created by Administrator on 2017/10/18.
 */
public class MerchantPicture {
    //id
    private Integer merchantPictureId;
    //路径
    private String img;
    //类型
    private String type="merchant";
    //标题
    private String title;
    //内容
    private String content;
    //申请信息的id
    private Integer applyInfoId;

    public Integer getMerchantPictureId() {
        return merchantPictureId;
    }

    public void setMerchantPictureId(Integer merchantPictureId) {
        this.merchantPictureId = merchantPictureId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getApplyInfoId() {
        return applyInfoId;
    }

    public void setApplyInfoId(Integer applyInfoId) {
        this.applyInfoId = applyInfoId;
    }

    @Override
    public String toString() {
        return "MerchantPicture{" +
                "merchantPictureId=" + merchantPictureId +
                ", img='" + img + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", applyInfoId=" + applyInfoId +
                '}';
    }
}
