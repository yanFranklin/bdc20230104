package cn.gtmap.realestate.certificate.core.model.domain;


import java.sql.Timestamp;

/**
 * 目录信息汇交日志表
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/4/30
 * @description
 */
public class BdcDzzzMlxxHjlogDO {
    /**
     * 目录ID
     */
    private String mlid;
    /**
     * 上条目录ID
     */
    private String stmlid;
    /**
     * 行政区代码
     */
    private String xzqdm;
    /**
     * 上传时间戳
     */
    private Timestamp scsj;
    /**
     * 客户端数据总条数
     */
    private Integer khdzts;
    /**
     * 服务端数据总条数
     */
    private Integer fwdzts;
    /**
     * 新增差值
     */
    private Integer xzcz;
    /**
     * 归档状态(0:未归档 1:已归档)
     */
    private Integer gdzt;

    public String getMlid() {
        return mlid;
    }

    public void setMlid(String mlid) {
        this.mlid = mlid;
    }

    public String getStmlid() {
        return stmlid;
    }

    public void setStmlid(String stmlid) {
        this.stmlid = stmlid;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public Timestamp getScsj() {
        return scsj;
    }

    public void setScsj(Timestamp scsj) {
        this.scsj = scsj;
    }

    public Integer getKhdzts() {
        return khdzts;
    }

    public void setKhdzts(Integer khdzts) {
        this.khdzts = khdzts;
    }

    public Integer getFwdzts() {
        return fwdzts;
    }

    public void setFwdzts(Integer fwdzts) {
        this.fwdzts = fwdzts;
    }

    public Integer getXzcz() {
        return xzcz;
    }

    public void setXzcz(Integer xzcz) {
        this.xzcz = xzcz;
    }

    public Integer getGdzt() {
        return gdzt;
    }

    public void setGdzt(Integer gdzt) {
        this.gdzt = gdzt;
    }
}
