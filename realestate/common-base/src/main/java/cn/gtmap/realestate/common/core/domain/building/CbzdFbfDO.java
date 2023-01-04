package cn.gtmap.realestate.common.core.domain.building;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/11
 * @description发包发基本信息
 */
@Table(name = "CBZD_FBF")
public class CbzdFbfDO {
    /**
     * 发包方调查表主键
     */
    @Id
    private String cbzdFbfIndex;
    /**
     * 宗地调查表主键
     */
    private String zddcbIndex;
    /**
     * 发包方名称
     */
    private String fbfmc;
    /**
     * 负责人姓名
     */
    private String fbffzrxm;
    /**
     * 负责人地址
     */
    private String fbffzrdz;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 邮政编码
     */
    private String yzbm;
    /**
     * 证件种类
     */
    private String fzrzjlx;
    /**
     * 证件号码
     */
    private String fzrzjhm;
    /**
     * 更新日期
     */
    private Date gxrq;
    /**
     * 备注
     */
    private String bz;
    /**
     * 发包方编码
     */
    private String fbfbm;


    public String getZddcbIndex() {
        return zddcbIndex;
    }

    public void setZddcbIndex(String zddcbIndex) {
        this.zddcbIndex = zddcbIndex;
    }

    public String getFbfmc() {
        return fbfmc;
    }

    public void setFbfmc(String fbfmc) {
        this.fbfmc = fbfmc;
    }

    public String getFbffzrxm() {
        return fbffzrxm;
    }

    public void setFbffzrxm(String fbffzrxm) {
        this.fbffzrxm = fbffzrxm;
    }

    public String getFbffzrdz() {
        return fbffzrdz;
    }

    public void setFbffzrdz(String fbffzrdz) {
        this.fbffzrdz = fbffzrdz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    public String getFzrzjlx() {
        return fzrzjlx;
    }

    public void setFzrzjlx(String fzrzjlx) {
        this.fzrzjlx = fzrzjlx;
    }

    public String getFzrzjhm() {
        return fzrzjhm;
    }

    public void setFzrzjhm(String fzrzjhm) {
        this.fzrzjhm = fzrzjhm;
    }

    public Date getGxrq() {
        return gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCbzdFbfIndex() {
        return cbzdFbfIndex;
    }

    public void setCbzdFbfIndex(String cbzdFbfIndex) {
        this.cbzdFbfIndex = cbzdFbfIndex;
    }

    public String getFbfbm() {
        return fbfbm;
    }

    public void setFbfbm(String fbfbm) {
        this.fbfbm = fbfbm;
    }

    @Override
    public String toString() {
        return "CbzdFbfDO{" +
                "cbzdFbfIndex='" + cbzdFbfIndex + '\'' +
                ", zddcbIndex='" + zddcbIndex + '\'' +
                ", fbfmc='" + fbfmc + '\'' +
                ", fbffzrxm='" + fbffzrxm + '\'' +
                ", fbffzrdz='" + fbffzrdz + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", yzbm='" + yzbm + '\'' +
                ", fzrzjlx='" + fzrzjlx + '\'' +
                ", fzrzjhm='" + fzrzjhm + '\'' +
                ", gxrq=" + gxrq +
                ", bz='" + bz + '\'' +
                ", fbfbm='" + fbfbm + '\'' +
                '}';
    }
}

