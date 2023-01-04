package cn.gtmap.realestate.exchange.core.dto.yctb;

import cn.gtmap.realestate.common.util.ParamUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class YctbOldBusinessRequest implements Serializable {

    private static final long serialVersionUID = 4398964537488200737L;

    private String htbh; //string N 合同编号（暂不使用）
    private String qxdm; //string Y 区县代码
    private String cqly; //string Y 产权来源 见字典产权来源
    private String djlxmc; //string Y 登记类型名称
    private String bdcqzh; //string N 不动产权证号 （bdcqzh\bdcdjzmh\bdcdyh 三 者其中一个为必要）
    private String bdcdjzmh; //string N 不动产证明号 （bdcqzh\bdcdjzmh\bdcdyh 三 者其中一个为必要）
    private String bdcdyh; //string N 不动产单元号 （bdcqzh\bdcdjzmh\bdcdyh 三 者其中一个为必要）
    private String fwid; //string N 房屋 ID（土地及房屋业务必 填）
    private String zdid; //string N 宗地 ID（土地业务必填）
    private String djlx; //string Y 登记类型 见字典登记类型
    private String bdclx; //string Y 不动产类型 见字典不动产类型

    public void checkParam(){
        ParamUtil.nonNull(this.cqly, "产权来源不能为空");
        ParamUtil.nonNull(this.qxdm,"区县代码不能为空");
        ParamUtil.nonNull(this.djlx,"登记类型不能为空");
        ParamUtil.nonNull(this.djlxmc,"登记类型名称不能为空");
        ParamUtil.nonNull(this.bdclx,"不动产类型代码不能为空");
        if (StringUtils.isBlank(this.bdcqzh) && StringUtils.isBlank(this.bdcdjzmh) && StringUtils.isBlank(this.bdcdyh)){
            throw new RuntimeException("bdcqzh\\bdcdjzmh\\bdcdyh 三者其中一个为必填");
        }
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getCqly() {
        return cqly;
    }

    public void setCqly(String cqly) {
        this.cqly = cqly;
    }

    public String getDjlxmc() {
        return djlxmc;
    }

    public void setDjlxmc(String djlxmc) {
        this.djlxmc = djlxmc;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcdjzmh() {
        return bdcdjzmh;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getFwid() {
        return fwid;
    }

    public void setFwid(String fwid) {
        this.fwid = fwid;
    }

    public String getZdid() {
        return zdid;
    }

    public void setZdid(String zdid) {
        this.zdid = zdid;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }
}
