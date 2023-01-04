package cn.gtmap.realestate.common.core.dto.accept;

/**
 * @program: realestate
 * @description: 查询权利人限购信息DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-04-20 14:55
 **/
public class XgxxDTO {

    private String isCanBuy;//是否可以购买（是、否）

    private String mc;

    private String zjh;

    private String bdcdyh;

    public String getIsCanBuy() {
        return isCanBuy;
    }

    public void setIsCanBuy(String isCanBuy) {
        this.isCanBuy = isCanBuy;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }


    @Override
    public String toString() {
        return "XgxxDTO{" +
                "isCanBuy='" + isCanBuy + '\'' +
                ", mc='" + mc + '\'' +
                ", zjh='" + zjh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                '}';
    }
}
