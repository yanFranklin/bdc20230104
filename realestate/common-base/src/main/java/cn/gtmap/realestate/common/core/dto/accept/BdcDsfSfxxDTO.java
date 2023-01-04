package cn.gtmap.realestate.common.core.dto.accept;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/12/4
 * @description 第三方收费信息
 */
public class BdcDsfSfxxDTO {

    // 收费信息集合
    private List<BdcSfxxDTO> bdcSfxxDTOS;

    //工作流实例ID
    private String gzlslid;

    //受理编号
    private String slbh;

    //交款方式
    private String jkfs;

    //开票用户
    private String kpyh;

    //是否推送财政
    private boolean tsdjfxx;

    // 外网受理编号
    private String wwslbh;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getJkfs() {
        return jkfs;
    }

    public void setJkfs(String jkfs) {
        this.jkfs = jkfs;
    }

    public List<BdcSfxxDTO> getBdcSfxxDTOS() {
        return bdcSfxxDTOS;
    }

    public void setBdcSfxxDTOS(List<BdcSfxxDTO> bdcSfxxDTOS) {
        this.bdcSfxxDTOS = bdcSfxxDTOS;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public boolean isTsdjfxx() {
        return tsdjfxx;
    }

    public void setTsdjfxx(boolean tsdjfxx) {
        this.tsdjfxx = tsdjfxx;
    }

    public String getKpyh() {
        return kpyh;
    }

    public void setKpyh(String kpyh) {
        this.kpyh = kpyh;
    }

    public String getWwslbh() {
        return wwslbh;
    }

    public void setWwslbh(String wwslbh) {
        this.wwslbh = wwslbh;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcDsfSfxxDTO{");
        sb.append("bdcSfxxDTOS=").append(bdcSfxxDTOS);
        sb.append(", gzlslid='").append(gzlslid).append('\'');
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", jkfs='").append(jkfs).append('\'');
        sb.append(", kpyh='").append(kpyh).append('\'');
        sb.append(", tsdjfxx=").append(tsdjfxx);
        sb.append(", wwslbh='").append(wwslbh).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
