package cn.gtmap.realestate.exchange.core.dto.yctb;

import cn.gtmap.realestate.common.util.ParamUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class YctbGetHouseInfoRequest implements Serializable {

    private static final long serialVersionUID = 7030417839064002572L;

    private String qxdm; //string Y 区县代码
    private String bdcqzh; //string N 不动产权证号 （bdcqzh\bdcdjzmh\bdcdyh 三者其中一个为必要）
    private String bdcdjzmh; //string N 不动产证明号 （bdcqzh\bdcdjzmh\bdcdyh 三者其中一个为必要）
    private String bdcdyh; // string N 不动产单元号 （bdcqzh\bdcdjzmh\bdcdyh 三者其中一个为必要）
    private String htbh; //string N 合同编号（暂不使用）
    private String qlr; //string Y 权利人姓名
    private String djlx; //string Y 登记类型 见字典登记类型
    private String lcmc; //string Y 流程名称
    private String bdclx; //string Y 不动产类型代码(1 土地 2 房 屋)private String

    public void checkParam(){
        ParamUtil.nonNull(this.qlr, "权利人姓名不能为空");
//        ParamUtil.nonNull(this.qxdm,"区县代码不能为空");
        ParamUtil.nonNull(this.djlx,"登记类型不能为空");
        ParamUtil.nonNull(this.lcmc,"流程名称不能为空");
        ParamUtil.nonNull(this.bdclx,"不动产类型代码不能为空");
        if (StringUtils.isBlank(this.bdcqzh) && StringUtils.isBlank(this.bdcdjzmh) && StringUtils.isBlank(this.bdcdyh)){
            throw new RuntimeException("bdcqzh\\bdcdjzmh\\bdcdyh 三者其中一个为必填");
        }
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
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

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }
}
