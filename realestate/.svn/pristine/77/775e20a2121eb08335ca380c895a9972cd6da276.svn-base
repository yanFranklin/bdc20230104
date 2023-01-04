package cn.gtmap.realestate.exchange.core.dto.yctb;

import cn.gtmap.realestate.common.util.ParamUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

public class YctbZxcdRequest implements Serializable {

    private static final long serialVersionUID = -6962587201852070720L;

    private String cxlx; //string Y 1:权利人查询；2：其他人查询；
    private String cxbh; //string Y 查询编号
    //private List<YctbZxcdSqrRequest> sqrList; //object[] N 查询申请人
    private String cxmd; //string Y 查询目的；1：买卖；2：租赁；3：抵押不动产意向；4：拟就不动产提起诉讼或者仲裁；5：法律法规规定的其他利害关系情形
    private String zl; //string N 不动产坐落
    private String bdcdyh; //string N 不动产单元号
    private String bdcqzs; //string N 不动产权证书
    private String qxdm; //string Y 区县代码
    /**
     * qxdm配置项，查询使用
     */
    private String qxdmConfig;

    private String name; //string Y 姓名
    private String zjh; //string Y 证件号

    public void checkParam() {
        ParamUtil.nonNull(this.cxlx, "查询类型不能为空");
        ParamUtil.nonNull(this.cxbh, "查询编号不能为空");
        ParamUtil.nonNull(this.cxmd, " 查询目的不能为空");
        ParamUtil.nonNull(this.qxdm, "区县代码不能为空");
        if ("2".equals(this.cxlx) && StringUtils.isBlank(this.zl) && StringUtils.isBlank(this.bdcdyh) && StringUtils.isBlank(this.bdcqzs)) {
            throw new RuntimeException("cxlx为2时zl,bdcdyh,bdcqzs三个不能都为空");
        }
        if ("1".equals(this.cxlx) && (StringUtils.isBlank(this.zjh) || StringUtils.isBlank(this.name))) {
            throw new RuntimeException("cxlx为1时name和zjh不能为空");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getCxlx() {
        return cxlx;
    }

    public void setCxlx(String cxlx) {
        this.cxlx = cxlx;
    }

    public String getCxbh() {
        return cxbh;
    }

    public void setCxbh(String cxbh) {
        this.cxbh = cxbh;
    }


    public String getCxmd() {
        return cxmd;
    }

    public void setCxmd(String cxmd) {
        this.cxmd = cxmd;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzs() {
        return bdcqzs;
    }

    public void setBdcqzs(String bdcqzs) {
        this.bdcqzs = bdcqzs;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getQxdmConfig() {
        return qxdmConfig;
    }

    public void setQxdmConfig(String qxdmConfig) {
        this.qxdmConfig = qxdmConfig;
    }
}
