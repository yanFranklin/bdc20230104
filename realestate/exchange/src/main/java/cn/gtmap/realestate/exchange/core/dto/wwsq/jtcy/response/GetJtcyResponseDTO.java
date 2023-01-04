package cn.gtmap.realestate.exchange.core.dto.wwsq.jtcy.response;
/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-07-05
 * @description 获取家庭成员信息DTO
 */
public class GetJtcyResponseDTO {
    /**
     * 姓名
     */
    private String  xm;
    /**
     * 证件号码
     */
    private String gmsfhm;
    /**
     * 户籍地址
     */
    private String mlxz;
    /**
     * 籍贯
     */
    private String jgssxq;
    /**
     * 与户主关系
     */
    private String yhzgx;

    /**
     * 关系
     */
    private String gx;
    /**
     * 身份证件号码
     */
    private String zj;

    public String getGx() {
        return gx;
    }

    public void setGx(String gx) {
        this.gx = gx;
    }

    public String getZj() {
        return zj;
    }

    public void setZj(String zj) {
        this.zj = zj;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getGmsfhm() {
        return gmsfhm;
    }

    public void setGmsfhm(String gmsfhm) {
        this.gmsfhm = gmsfhm;
    }

    public String getMlxz() {
        return mlxz;
    }

    public void setMlxz(String mlxz) {
        this.mlxz = mlxz;
    }

    public String getJgssxq() {
        return jgssxq;
    }

    public void setJgssxq(String jgssxq) {
        this.jgssxq = jgssxq;
    }

    public String getYhzgx() {
        return yhzgx;
    }

    public void setYhzgx(String yhzgx) {
        this.yhzgx = yhzgx;
    }
}
