package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/10/31
 * @description 不动产权利人
 */
@Table(
        name = "BDC_QLR"
)
@ApiModel(value = "BdcQlrDO",description = "不动产权利人")
public class BdcQlrDO {
    @Id
    @ApiModelProperty(value = "权利人id")
    private String qlrid;
    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;
    @Zd(tableClass = BdcZdZjzlDO.class)
    @ApiModelProperty(value = "证件种类")
    private Integer zjzl;
    @ApiModelProperty(value = "证件号")
    private String zjh;
    @ApiModelProperty(value = "通讯地址")
    private String txdz;
    @ApiModelProperty(value = "邮编")
    private String yb;
    @ApiModelProperty(value = "性别")
    private Integer xb;
    @ApiModelProperty(value = "法人名称")
    private String frmc;
    @ApiModelProperty(value = "法人电话")
    private String frdh;
    @Zd(tableClass = BdcZdZjzlDO.class)
    @ApiModelProperty(value = "法人证件种类")
    private String frzjzl;
    @ApiModelProperty(value = "法人证件号")
    private String frzjh;
    @ApiModelProperty(value = "代理人名称")
    private String dlrmc;
    @ApiModelProperty(value = "代理人电话")
    private String dlrdh;
    @Zd(tableClass = BdcZdZjzlDO.class)
    @ApiModelProperty(value = "代理人证件种类")
    private String dlrzjzl;
    @ApiModelProperty(value = "代理人证件号")
    private String dlrzjh;
    @ApiModelProperty(value = "代理机构")
    private String dljg;
    @Zd(tableClass = BdcZdQlrlxDO.class)
    @ApiModelProperty(value = "权利人类型")
    private Integer qlrlx;
    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;
    @ApiModelProperty(value = "权利比例")
    private String qlbl;
    @Zd(tableClass = BdcZdGyfsDO.class)
    @ApiModelProperty(value = "共有方式")
    private Integer gyfs;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "电话")
    private String dh;
    @ApiModelProperty(value = "所属行业")
    private String sshy;
    @ApiModelProperty(value = "备注")
    private String bz;
    @Zd(tableClass = BdcZdSfDO.class)
    @ApiModelProperty(value = "是否持证人")
    private Integer sfczr;
    @ApiModelProperty(value = "顺序号")
    private Integer sxh;
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    @ApiModelProperty(value = "不动产权证书id")
    private String zsid;
    @ApiModelProperty(value = "权利人分摊面积")
    private Double qlrftmj;
    @ApiModelProperty(value = "领证人")
    private String lzr;
    @ApiModelProperty(value = "领证人电话")
    private String lzrdh;
    @Zd(tableClass = BdcZdZjzlDO.class)
    @ApiModelProperty(value = "领证人证件种类")
    private Integer lzrzjzl;
    @ApiModelProperty(value = "领证人证件号")
    private String lzrzjh;
    @ApiModelProperty(value = "存储第三方系统从其他系统查询出来的权利人状态信息(中文)")
    private String qlrwbzt;
    @ApiModelProperty(value = "出生日期")
    private String csrq;
    @ApiModelProperty(value = "名族")
    private String mz;
    @ApiModelProperty(value = "权利人证件签发机关")
    private String qlrzjqfjg;
    @ApiModelProperty(value = "有效期限")
    private String yxqx;

    @ApiModelProperty(value = "权利人来源")
    private Integer qlrly;

    @ApiModelProperty(value = "交易份额")
    private Double jyfe;
    @ApiModelProperty(value = "继承份额")
    private Double jcfe;
    @ApiModelProperty(value = "赠予份额")
    private Double zyfe;
    @ApiModelProperty(value = "分析份额")
    private Double fxfe;
    @ApiModelProperty(value = "权利人特征")
    private Integer qlrtz;
    @ApiModelProperty(value = "是否集体经济组织成员")
    private String sfjtjjzzcy;
    @ApiModelProperty(value = "与户主关系")
    private String yhzgx;

    public String getLzr() {
        return lzr;
    }

    public void setLzr(String lzr) {
        this.lzr = lzr;
    }

    public String getLzrdh() {
        return lzrdh;
    }

    public void setLzrdh(String lzrdh) {
        this.lzrdh = lzrdh;
    }

    public Integer getLzrzjzl() {
        return lzrzjzl;
    }

    public Integer getQlrly() {
        return qlrly;
    }

    public void setQlrly(Integer qlrly) {
        this.qlrly = qlrly;
    }

    @Override
    public String toString() {
        return "BdcQlrDO{" +
                "qlrid='" + qlrid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", zjzl=" + zjzl +
                ", zjh='" + zjh + '\'' +
                ", txdz='" + txdz + '\'' +
                ", yb='" + yb + '\'' +
                ", xb=" + xb +
                ", frmc='" + frmc + '\'' +
                ", frdh='" + frdh + '\'' +
                ", frzjzl='" + frzjzl + '\'' +
                ", frzjh='" + frzjh + '\'' +
                ", dlrmc='" + dlrmc + '\'' +
                ", dlrdh='" + dlrdh + '\'' +
                ", dlrzjzl='" + dlrzjzl + '\'' +
                ", dlrzjh='" + dlrzjh + '\'' +
                ", dljg='" + dljg + '\'' +
                ", qlrlx=" + qlrlx +
                ", qlrlb='" + qlrlb + '\'' +
                ", qlbl='" + qlbl + '\'' +
                ", gyfs=" + gyfs +
                ", gyqk='" + gyqk + '\'' +
                ", dh='" + dh + '\'' +
                ", sshy='" + sshy + '\'' +
                ", bz='" + bz + '\'' +
                ", sfczr=" + sfczr +
                ", sxh=" + sxh +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", zsid='" + zsid + '\'' +
                ", qlrftmj=" + qlrftmj +
                ", lzr='" + lzr + '\'' +
                ", lzrdh='" + lzrdh + '\'' +
                ", lzrzjzl=" + lzrzjzl +
                ", lzrzjh='" + lzrzjh + '\'' +
                ", qlrwbzt='" + qlrwbzt + '\'' +
                ", csrq='" + csrq + '\'' +
                ", mz='" + mz + '\'' +
                ", qlrzjqfjg='" + qlrzjqfjg + '\'' +
                ", yxqx='" + yxqx + '\'' +
                ", qlrly=" + qlrly +
                ", jyfe=" + jyfe +
                ", jcfe=" + jcfe +
                ", zyfe=" + zyfe +
                ", fxfe=" + fxfe +
                ", qlrtz=" + qlrtz +
                '}';
    }

    public void setLzrzjzl(Integer lzrzjzl) {
        this.lzrzjzl = lzrzjzl;
    }

    public String getLzrzjh() {
        return lzrzjh;
    }

    public void setLzrzjh(String lzrzjh) {
        this.lzrzjh = lzrzjh;
    }

    public Double getQlrftmj() {
        return qlrftmj;
    }

    public void setQlrftmj(Double qlrftmj) {
        this.qlrftmj = qlrftmj;
    }

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public Integer getZjzl() {
        return zjzl;
    }

    public void setZjzl(Integer zjzl) {
        this.zjzl = zjzl;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getTxdz() {
        return txdz;
    }

    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    public Integer getXb() {
        return xb;
    }

    public void setXb(Integer xb) {
        this.xb = xb;
    }

    public String getFrmc() {
        return frmc;
    }

    public void setFrmc(String frmc) {
        this.frmc = frmc;
    }

    public String getFrdh() {
        return frdh;
    }

    public void setFrdh(String frdh) {
        this.frdh = frdh;
    }

    public String getDlrmc() {
        return dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public String getDlrdh() {
        return dlrdh;
    }

    public void setDlrdh(String dlrdh) {
        this.dlrdh = dlrdh;
    }

    public String getDljg() {
        return dljg;
    }

    public void setDljg(String dljg) {
        this.dljg = dljg;
    }

    public Integer getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(Integer qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public Integer getGyfs() {
        return gyfs;
    }

    public void setGyfs(Integer gyfs) {
        this.gyfs = gyfs;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getSshy() {
        return sshy;
    }

    public void setSshy(String sshy) {
        this.sshy = sshy;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getSfczr() {
        return sfczr;
    }

    public void setSfczr(Integer sfczr) {
        this.sfczr = sfczr;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getFrzjzl() {
        return frzjzl;
    }

    public void setFrzjzl(String frzjzl) {
        this.frzjzl = frzjzl;
    }

    public String getFrzjh() {
        return frzjh;
    }

    public void setFrzjh(String frzjh) {
        this.frzjh = frzjh;
    }

    public String getDlrzjzl() {
        return dlrzjzl;
    }

    public void setDlrzjzl(String dlrzjzl) {
        this.dlrzjzl = dlrzjzl;
    }

    public String getDlrzjh() {
        return dlrzjh;
    }

    public void setDlrzjh(String dlrzjh) {
        this.dlrzjh = dlrzjh;
    }

    public String getQlrwbzt() {
        return qlrwbzt;
    }

    public void setQlrwbzt(String qlrwbzt) {
        this.qlrwbzt = qlrwbzt;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getQlrzjqfjg() {
        return qlrzjqfjg;
    }

    public void setQlrzjqfjg(String qlrzjqfjg) {
        this.qlrzjqfjg = qlrzjqfjg;
    }

    public String getYxqx() {
        return yxqx;
    }

    public void setYxqx(String yxqx) {
        this.yxqx = yxqx;
    }

    public Double getJyfe() {
        return jyfe;
    }

    public void setJyfe(Double jyfe) {
        this.jyfe = jyfe;
    }

    public Double getJcfe() {
        return jcfe;
    }

    public void setJcfe(Double jcfe) {
        this.jcfe = jcfe;
    }

    public Double getZyfe() {
        return zyfe;
    }

    public void setZyfe(Double zyfe) {
        this.zyfe = zyfe;
    }

    public Double getFxfe() {
        return fxfe;
    }

    public void setFxfe(Double fxfe) {
        this.fxfe = fxfe;
    }

    public Integer getQlrtz() {
        return qlrtz;
    }

    public void setQlrtz(Integer qlrtz) {
        this.qlrtz = qlrtz;
    }

    public String getSfjtjjzzcy() {
        return sfjtjjzzcy;
    }

    public void setSfjtjjzzcy(String sfjtjjzzcy) {
        this.sfjtjjzzcy = sfjtjjzzcy;
    }

    public String getYhzgx() {
        return yhzgx;
    }

    public void setYhzgx(String yhzgx) {
        this.yhzgx = yhzgx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof BdcQlrDO)){
            return false;
        }
        BdcQlrDO bdcQlrDO = (BdcQlrDO) o;
        return Objects.equals(qlrid, bdcQlrDO.qlrid) && Objects.equals(xmid, bdcQlrDO.xmid) && Objects.equals(qlrmc, bdcQlrDO.qlrmc) && Objects.equals(zjzl, bdcQlrDO.zjzl) && Objects.equals(zjh, bdcQlrDO.zjh) && Objects.equals(txdz, bdcQlrDO.txdz) && Objects.equals(yb, bdcQlrDO.yb) && Objects.equals(xb, bdcQlrDO.xb) && Objects.equals(frmc, bdcQlrDO.frmc) && Objects.equals(frdh, bdcQlrDO.frdh) && Objects.equals(frzjzl, bdcQlrDO.frzjzl) && Objects.equals(frzjh, bdcQlrDO.frzjh) && Objects.equals(dlrmc, bdcQlrDO.dlrmc) && Objects.equals(dlrdh, bdcQlrDO.dlrdh) && Objects.equals(dlrzjzl, bdcQlrDO.dlrzjzl) && Objects.equals(dlrzjh, bdcQlrDO.dlrzjh) && Objects.equals(dljg, bdcQlrDO.dljg) && Objects.equals(qlrlx, bdcQlrDO.qlrlx) && Objects.equals(qlrlb, bdcQlrDO.qlrlb) && Objects.equals(qlbl, bdcQlrDO.qlbl) && Objects.equals(gyfs, bdcQlrDO.gyfs) && Objects.equals(gyqk, bdcQlrDO.gyqk) && Objects.equals(dh, bdcQlrDO.dh) && Objects.equals(sshy, bdcQlrDO.sshy) && Objects.equals(bz, bdcQlrDO.bz) && Objects.equals(sfczr, bdcQlrDO.sfczr) && Objects.equals(sxh, bdcQlrDO.sxh) && Objects.equals(bdcqzh, bdcQlrDO.bdcqzh) && Objects.equals(zsid, bdcQlrDO.zsid) && Objects.equals(qlrftmj, bdcQlrDO.qlrftmj) && Objects.equals(lzr, bdcQlrDO.lzr) && Objects.equals(lzrdh, bdcQlrDO.lzrdh) && Objects.equals(lzrzjzl, bdcQlrDO.lzrzjzl) && Objects.equals(lzrzjh, bdcQlrDO.lzrzjh) && Objects.equals(qlrwbzt, bdcQlrDO.qlrwbzt) && Objects.equals(csrq, bdcQlrDO.csrq) && Objects.equals(mz, bdcQlrDO.mz) && Objects.equals(qlrzjqfjg, bdcQlrDO.qlrzjqfjg) && Objects.equals(yxqx, bdcQlrDO.yxqx) && Objects.equals(qlrly, bdcQlrDO.qlrly) && Objects.equals(jyfe, bdcQlrDO.jyfe) && Objects.equals(jcfe, bdcQlrDO.jcfe) && Objects.equals(zyfe, bdcQlrDO.zyfe) && Objects.equals(fxfe, bdcQlrDO.fxfe) && Objects.equals(qlrtz, bdcQlrDO.qlrtz) && Objects.equals(sfjtjjzzcy, bdcQlrDO.sfjtjjzzcy) && Objects.equals(yhzgx, bdcQlrDO.yhzgx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qlrid, xmid, qlrmc, zjzl, zjh, txdz, yb, xb, frmc, frdh, frzjzl, frzjh, dlrmc, dlrdh, dlrzjzl, dlrzjh, dljg, qlrlx, qlrlb, qlbl, gyfs, gyqk, dh, sshy, bz, sfczr, sxh, bdcqzh, zsid, qlrftmj, lzr, lzrdh, lzrzjzl, lzrzjh, qlrwbzt, csrq, mz, qlrzjqfjg, yxqx, qlrly, jyfe, jcfe, zyfe, fxfe, qlrtz, sfjtjjzzcy, yhzgx);
    }

    public static final class BdcQlrDOBuilder {
        private String qlrid;
        private String xmid;
        private String qlrmc;
        private Integer zjzl;
        private String zjh;
        private String txdz;
        private String yb;
        private Integer xb;
        private String frmc;
        private String frdh;
        private String frzjzl;
        private String frzjh;
        private String dlrmc;
        private String dlrdh;
        private String dlrzjzl;
        private String dlrzjh;
        private String dljg;
        private Integer qlrlx;
        private String qlrlb;
        private String qlbl;
        private Integer gyfs;
        private String gyqk;
        private String dh;
        private String sshy;
        private String bz;
        private Integer sfczr;
        private Integer sxh;
        private String bdcqzh;
        private String zsid;
        private Double qlrftmj;
        private String lzr;
        private String lzrdh;
        private Integer lzrzjzl;
        private String lzrzjh;
        private String qlrwbzt;
        private String csrq;
        private String mz;
        private String qlrzjqfjg;
        private String yxqx;

        private BdcQlrDOBuilder() {
        }

        public static BdcQlrDOBuilder aBdcQlrDO() {
            return new BdcQlrDOBuilder();
        }

        public BdcQlrDOBuilder withQlrid(String qlrid) {
            this.qlrid = qlrid;
            return this;
        }

        public BdcQlrDOBuilder withXmid(String xmid) {
            this.xmid = xmid;
            return this;
        }

        public BdcQlrDOBuilder withQlrmc(String qlrmc) {
            this.qlrmc = qlrmc;
            return this;
        }

        public BdcQlrDOBuilder withZjzl(Integer zjzl) {
            this.zjzl = zjzl;
            return this;
        }

        public BdcQlrDOBuilder withZjh(String zjh) {
            this.zjh = zjh;
            return this;
        }

        public BdcQlrDOBuilder withTxdz(String txdz) {
            this.txdz = txdz;
            return this;
        }

        public BdcQlrDOBuilder withYb(String yb) {
            this.yb = yb;
            return this;
        }

        public BdcQlrDOBuilder withXb(Integer xb) {
            this.xb = xb;
            return this;
        }

        public BdcQlrDOBuilder withFrmc(String frmc) {
            this.frmc = frmc;
            return this;
        }

        public BdcQlrDOBuilder withFrdh(String frdh) {
            this.frdh = frdh;
            return this;
        }

        public BdcQlrDOBuilder withFrzjzl(String frzjzl) {
            this.frzjzl = frzjzl;
            return this;
        }

        public BdcQlrDOBuilder withFrzjh(String frzjh) {
            this.frzjh = frzjh;
            return this;
        }

        public BdcQlrDOBuilder withDlrmc(String dlrmc) {
            this.dlrmc = dlrmc;
            return this;
        }

        public BdcQlrDOBuilder withDlrdh(String dlrdh) {
            this.dlrdh = dlrdh;
            return this;
        }

        public BdcQlrDOBuilder withDlrzjzl(String dlrzjzl) {
            this.dlrzjzl = dlrzjzl;
            return this;
        }

        public BdcQlrDOBuilder withDlrzjh(String dlrzjh) {
            this.dlrzjh = dlrzjh;
            return this;
        }

        public BdcQlrDOBuilder withDljg(String dljg) {
            this.dljg = dljg;
            return this;
        }

        public BdcQlrDOBuilder withQlrlx(Integer qlrlx) {
            this.qlrlx = qlrlx;
            return this;
        }

        public BdcQlrDOBuilder withQlrlb(String qlrlb) {
            this.qlrlb = qlrlb;
            return this;
        }

        public BdcQlrDOBuilder withQlbl(String qlbl) {
            this.qlbl = qlbl;
            return this;
        }

        public BdcQlrDOBuilder withGyfs(Integer gyfs) {
            this.gyfs = gyfs;
            return this;
        }

        public BdcQlrDOBuilder withGyqk(String gyqk) {
            this.gyqk = gyqk;
            return this;
        }

        public BdcQlrDOBuilder withDh(String dh) {
            this.dh = dh;
            return this;
        }

        public BdcQlrDOBuilder withSshy(String sshy) {
            this.sshy = sshy;
            return this;
        }

        public BdcQlrDOBuilder withBz(String bz) {
            this.bz = bz;
            return this;
        }

        public BdcQlrDOBuilder withSfczr(Integer sfczr) {
            this.sfczr = sfczr;
            return this;
        }

        public BdcQlrDOBuilder withSxh(Integer sxh) {
            this.sxh = sxh;
            return this;
        }

        public BdcQlrDOBuilder withBdcqzh(String bdcqzh) {
            this.bdcqzh = bdcqzh;
            return this;
        }

        public BdcQlrDOBuilder withZsid(String zsid) {
            this.zsid = zsid;
            return this;
        }

        public BdcQlrDOBuilder withQlrftmj(Double qlrftmj) {
            this.qlrftmj = qlrftmj;
            return this;
        }

        public BdcQlrDOBuilder withLzr(String lzr) {
            this.lzr = lzr;
            return this;
        }

        public BdcQlrDOBuilder withLzrdh(String lzrdh) {
            this.lzrdh = lzrdh;
            return this;
        }

        public BdcQlrDOBuilder withLzrzjzl(Integer lzrzjzl) {
            this.lzrzjzl = lzrzjzl;
            return this;
        }

        public BdcQlrDOBuilder withLzrzjh(String lzrzjh) {
            this.lzrzjh = lzrzjh;
            return this;
        }

        public BdcQlrDOBuilder withQlrwbzt(String qlrwbzt) {
            this.qlrwbzt = qlrwbzt;
            return this;
        }

        public BdcQlrDOBuilder withCsrq(String csrq) {
            this.csrq = csrq;
            return this;
        }

        public BdcQlrDOBuilder withMz(String mz) {
            this.mz = mz;
            return this;
        }

        public BdcQlrDOBuilder withQlrzjqfjg(String qlrzjqfjg) {
            this.qlrzjqfjg = qlrzjqfjg;
            return this;
        }

        public BdcQlrDOBuilder withYxqx(String yxqx) {
            this.yxqx = yxqx;
            return this;
        }



        public BdcQlrDO build() {
            BdcQlrDO bdcQlrDO = new BdcQlrDO();
            bdcQlrDO.setQlrid(qlrid);
            bdcQlrDO.setXmid(xmid);
            bdcQlrDO.setQlrmc(qlrmc);
            bdcQlrDO.setZjzl(zjzl);
            bdcQlrDO.setZjh(zjh);
            bdcQlrDO.setTxdz(txdz);
            bdcQlrDO.setYb(yb);
            bdcQlrDO.setXb(xb);
            bdcQlrDO.setFrmc(frmc);
            bdcQlrDO.setFrdh(frdh);
            bdcQlrDO.setFrzjzl(frzjzl);
            bdcQlrDO.setFrzjh(frzjh);
            bdcQlrDO.setDlrmc(dlrmc);
            bdcQlrDO.setDlrdh(dlrdh);
            bdcQlrDO.setDlrzjzl(dlrzjzl);
            bdcQlrDO.setDlrzjh(dlrzjh);
            bdcQlrDO.setDljg(dljg);
            bdcQlrDO.setQlrlx(qlrlx);
            bdcQlrDO.setQlrlb(qlrlb);
            bdcQlrDO.setQlbl(qlbl);
            bdcQlrDO.setGyfs(gyfs);
            bdcQlrDO.setGyqk(gyqk);
            bdcQlrDO.setDh(dh);
            bdcQlrDO.setSshy(sshy);
            bdcQlrDO.setBz(bz);
            bdcQlrDO.setSfczr(sfczr);
            bdcQlrDO.setSxh(sxh);
            bdcQlrDO.setBdcqzh(bdcqzh);
            bdcQlrDO.setZsid(zsid);
            bdcQlrDO.setQlrftmj(qlrftmj);
            bdcQlrDO.setLzr(lzr);
            bdcQlrDO.setLzrdh(lzrdh);
            bdcQlrDO.setLzrzjzl(lzrzjzl);
            bdcQlrDO.setLzrzjh(lzrzjh);
            bdcQlrDO.setQlrwbzt(qlrwbzt);
            bdcQlrDO.setCsrq(csrq);
            bdcQlrDO.setMz(mz);
            bdcQlrDO.setQlrzjqfjg(qlrzjqfjg);
            bdcQlrDO.setYxqx(yxqx);
            return bdcQlrDO;
        }
    }
}
