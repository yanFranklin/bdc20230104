package cn.gtmap.realestate.common.core.dto.exchange.swxx;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0  2022-05-06
 * @description 契税税票查询参数
 */
public class QueryQswsxxDTO {

    // 工作流实例id
    private String gzlslid;

    // 合同编号
    private String htbh;

    // 电子税票号
    private String dzsphm;

    // 是否存量房 是传2，否传1
    private String zlfclfbz;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getDzsphm() {
        return dzsphm;
    }

    public void setDzsphm(String dzsphm) {
        this.dzsphm = dzsphm;
    }

    public String getZlfclfbz() {
        return zlfclfbz;
    }

    public void setZlfclfbz(String zlfclfbz) {
        this.zlfclfbz = zlfclfbz;
    }

    @Override
    public String toString() {
        return "QueryQswsxxDTO{" +
                "gzlslid='" + gzlslid + '\'' +
                ", htbh='" + htbh + '\'' +
                ", dzsphm='" + dzsphm + '\'' +
                ", zlfclfbz='" + zlfclfbz + '\'' +
                '}';
    }

}
