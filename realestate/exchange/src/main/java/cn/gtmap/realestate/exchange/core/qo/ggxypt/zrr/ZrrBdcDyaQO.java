package cn.gtmap.realestate.exchange.core.qo.ggxypt.zrr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2022/8/31
 * @description 区市企业名下不动产登记情况QO
 */
@ApiModel(description = "区市自然人不动产抵押信息接口QO")
public class ZrrBdcDyaQO {

    @ApiModelProperty(value = "姓名")
    private String DYRMC;

    @ApiModelProperty(value = "证件类型")
    private String SFZJLX;

    @ApiModelProperty(value = "证件号码")
    private String DYRZJH;

    @ApiModelProperty(value = "证件号码")
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

    public String getSFZJLX() {
        return SFZJLX;
    }

    public void setSFZJLX(String SFZJLX) {
        this.SFZJLX = SFZJLX;
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
        return "ZrrBdcDyaQO{" +
                "DYRMC='" + DYRMC + '\'' +
                ", SFZJLX='" + SFZJLX + '\'' +
                ", DYRZJH='" + DYRZJH + '\'' +
                ", BDCDJZH='" + BDCDJZH + '\'' +
                ", AREACODE='" + AREACODE + '\'' +
                ", Bearer='" + Bearer + '\'' +
                '}';
    }
}
