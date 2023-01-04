package cn.gtmap.realestate.common.core.domain.exchange;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: bdcdj3.0
 * @description: xsd校验对照关系表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-11-23 15:16
 **/
@Table(name = "BDC_JR_XSDJY")
public class BdcJrXsdjyDO {

    @Id
    private String id;

    @ApiModelProperty("xsd字段")
    private String xsdzd;

    @ApiModelProperty("数据库字段")
    private String sjkzd;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXsdzd() {
        return xsdzd;
    }

    public void setXsdzd(String xsdzd) {
        this.xsdzd = xsdzd;
    }

    public String getSjkzd() {
        return sjkzd;
    }

    public void setSjkzd(String sjkzd) {
        this.sjkzd = sjkzd;
    }

    @Override
    public String toString() {
        return "BdcJrXsdjyDO{" +
                "id='" + id + '\'' +
                ", xsdzd='" + xsdzd + '\'' +
                ", sjkzd='" + sjkzd + '\'' +
                '}';
    }
}
