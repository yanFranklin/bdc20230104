package cn.gtmap.realestate.exchange.core.dto.hefei.dyswjcrcx.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-04
 * @description 第一顺位继承人查询响应结构
 */
public class DyswjcrcxResponseDTO {

    // 与死亡权利人的关系
    private String gx;

    // 身份证号码
    private String sfz;

    // 姓名
    private String mc;

    public String getGx() {
        return gx;
    }

    public void setGx(String gx) {
        this.gx = gx;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
}
