package cn.net.gxht.app.yjdPlatform.merchant.entity;

/**
 * Created by Administrator on 2017/10/18.
 */
public class MerchantBelong {
    private int id;
    private Integer merchantId;
    private Integer belongId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getBelongId() {
        return belongId;
    }

    public void setBelongId(Integer belongId) {
        this.belongId = belongId;
    }

    @Override
    public String toString() {
        return "MerchantBelong{" +
                "id=" + id +
                ", merchantId=" + merchantId +
                ", belongId=" + belongId +
                '}';
    }
}
