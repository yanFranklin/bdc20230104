package cn.gtmap.realestate.etl.core.qo.ggxypt.qy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/31
 * @description 区市企业名下不动产登记情况QO
 */
@ApiModel(description = "区市企业名下不动产登记情况QO")
public class GgxyptQyBdccqQO {

    @ApiModelProperty(value = "权利人名称")
    private String QYMC;

    @ApiModelProperty(value = "权利人证件号(统一社会信用代码)")
    private String QLRZJH;

    @ApiModelProperty(value = "不动产权证书号")
    private String BDCQZSH;

    @ApiModelProperty(value = "地区码。设区市提供时默认市本市的行政区划码（4位")
    private String AREACODE;

    @ApiModelProperty(value = "传递token")
    private String Bearer;


    public String getQYMC() {
        return QYMC;
    }

    public void setQYMC(String QYMC) {
        this.QYMC = QYMC;
    }

    public String getQLRZJH() {
        return QLRZJH;
    }

    public void setQLRZJH(String QLRZJH) {
        this.QLRZJH = QLRZJH;
    }

    public String getBDCQZSH() {
        return BDCQZSH;
    }

    public void setBDCQZSH(String BDCQZSH) {
        this.BDCQZSH = BDCQZSH;
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
        return "GgxyptQyBdccqQO{" +
                "QYMC='" + QYMC + '\'' +
                ", QLRZJH='" + QLRZJH + '\'' +
                ", BDCQZSH='" + BDCQZSH + '\'' +
                ", AREACODE='" + AREACODE + '\'' +
                ", Bearer='" + Bearer + '\'' +
                '}';
    }
}
