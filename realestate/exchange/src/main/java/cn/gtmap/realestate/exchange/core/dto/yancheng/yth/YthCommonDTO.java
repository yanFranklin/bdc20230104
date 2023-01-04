package cn.gtmap.realestate.exchange.core.dto.yancheng.yth;

import java.io.Serializable;

/**
 * 盐城一体化相关接口通用DTO类
 */
public class YthCommonDTO implements Serializable {

    private static final long serialVersionUID = -8459887364015876577L;

    /**
     * 一体化业务编号（对应 spxtywh 审批系统业务号）
     */
    private String ythywbh;
    /**
     * 实际业务名称
     */
    private String sjywmc;
    /**
     * 业务编码
     */
    private String recType;
    /**
     * 业务名称
     */
    private String recName;
    /**
     * 登记类型
     */
    private String regType;
    /**
     * 权利类型
     */
    private String rightType;

    /**
     * 业务流水号 暂时没用
     */
    private String recFlowId;

    /**
     * 是否全部电子签章
     */
    private String sfkyzddb;

    public String getYthywbh() {
        return ythywbh;
    }

    public void setYthywbh(String ythywbh) {
        this.ythywbh = ythywbh;
    }

    public String getSjywmc() {
        return sjywmc;
    }

    public void setSjywmc(String sjywmc) {
        this.sjywmc = sjywmc;
    }

    public String getRecType() {
        return recType;
    }

    public void setRecType(String recType) {
        this.recType = recType;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    public String getRecFlowId() {
        return recFlowId;
    }

    public void setRecFlowId(String recFlowId) {
        this.recFlowId = recFlowId;
    }

    public String getSfkyzddb() {
        return sfkyzddb;
    }

    public void setSfkyzddb(String sfkyzddb) {
        this.sfkyzddb = sfkyzddb;
    }
}
