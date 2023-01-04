package cn.gtmap.realestate.etl.core.qo.ggxypt.qy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/31
 * @description 区市企业名房产抵押信息QO
 */
@ApiModel(value = "区市企业名房产抵押信息QO")
public class GgxyptQyDyQO {


    @ApiModelProperty(value = "抵押人名称")
    private String DYRMC;

    @ApiModelProperty(value = "抵押人证件号(统一社会信用代码)")
    private String DYRZJH;

    @ApiModelProperty(value = "不动产登记证号")
    private String BDCDJZH;

    @ApiModelProperty(value = "地区码。设区市提供时默认市本市的行政区划码（4位")
    private String AREACODE;

    @ApiModelProperty(value = "传递token")
    private String Bearer;

    public String getDYRMC() {
        return DYRMC;
    }

    public void setDYRMC(String DYRMC) {
        this.DYRMC = DYRMC;
    }

    public String getDYRZJH() {
        return DYRZJH;
    }

    public void setDYRZJH(String DYRZJH) {
        this.DYRZJH = DYRZJH;
    }

    public String getBDCDJZH() {
        return BDCDJZH;
    }

    public void setBDCDJZH(String BDCDJZH) {
        this.BDCDJZH = BDCDJZH;
    }

    public String getAREACODE() {
        return AREACODE;
    }

    public void setAREACODE(String AREACODE) {
        this.AREACODE = AREACODE;
    }

    public String getBearer() {
        return Bearer;
    }

    public void setBearer(String bearer) {
        Bearer = bearer;
    }

    @Override
    public String toString() {
        return "GgxyptQyDyQO{" +
                "DYRMC='" + DYRMC + '\'' +
                ", DYRZJH='" + DYRZJH + '\'' +
                ", BDCDJZH='" + BDCDJZH + '\'' +
                ", AREACODE='" + AREACODE + '\'' +
                ", Bearer='" + Bearer + '\'' +
                '}';
    }
}
