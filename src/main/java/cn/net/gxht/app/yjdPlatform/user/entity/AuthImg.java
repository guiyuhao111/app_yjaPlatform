package cn.net.gxht.app.yjdPlatform.user.entity;

/**
 * Created by Administrator on 2017/10/17.
 */
public class AuthImg {
    private int imgId;
    private String imgPath;
    private String imgType;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "AuthImg{" +
                "imgId=" + imgId +
                ", imgPath='" + imgPath + '\'' +
                ", imgType='" + imgType + '\'' +
                '}';
    }
}
