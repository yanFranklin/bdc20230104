package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;

import java.io.Serializable;

/**
 * @program: realestate
 * @description: 电子证照简要信息DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-31 10:58
 **/
public class DzzzJyclxxDTO implements Serializable {
    private static final long serialVersionUID = 5713862139433002800L;

    private String typeCode;

    private String status;

    private String isMaterial;

    private String areaCode;

    private BdcQlrDO bdcQlrDO;

    public BdcQlrDO getBdcQlrDO() {
        return bdcQlrDO;
    }

    public void setBdcQlrDO(BdcQlrDO bdcQlrDO) {
        this.bdcQlrDO = bdcQlrDO;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsMaterial() {
        return isMaterial;
    }

    public void setIsMaterial(String isMaterial) {
        this.isMaterial = isMaterial;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public String toString() {
        return "DzzzJyclxxDTO{" +
                "typeCode='" + typeCode + '\'' +
                ", status='" + status + '\'' +
                ", isMaterial='" + isMaterial + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", bdcQlrDO=" + bdcQlrDO +
                '}';
    }
}
