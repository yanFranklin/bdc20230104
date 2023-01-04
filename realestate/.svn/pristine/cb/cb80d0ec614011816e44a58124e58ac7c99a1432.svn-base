package cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.jyqd;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;

import java.util.Objects;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/17
 * @description 推送住建买受人信息
 */
@ApiModel(value = "ZjClfFwxxDTO", description = "推送住建房屋信息")
public class ZjClfFwxxDTO {

    /**
     * FH : 101
     * FWYT : 住宅
     * FWMJ : 113.22
     */
    @JSONField(name="FH")
    private String FH;
    @JSONField(name="FWYT")
    private String FWYT;
    @JSONField(name="FWMJ")
    private String FWMJ;
    @JSONField(name="FWJE")
    private String FWJE;

    public String getFH() {
        return FH;
    }

    @JSONField(name="FH")
    public void setFH(String FH) {
        this.FH = FH;
    }

    public String getFWYT() {
        return FWYT;
    }

    @JSONField(name="FWYT")
    public void setFWYT(String FWYT) {
        this.FWYT = FWYT;
    }

    public String getFWMJ() {
        return FWMJ;
    }

    @JSONField(name="FWMJ")
    public void setFWMJ(String FWMJ) {
        this.FWMJ = FWMJ;
    }

    public String getFWJE() {
        return FWJE;
    }

    @JSONField(name="FWJE")
    public void setFWJE(String FWJE) {
        this.FWJE = FWJE;
    }

    /**
     * 将万元转换为元
     */
    public void convertWyToy(Double fwje){
        if(Objects.nonNull(fwje) && fwje.compareTo(0.0)!= 0){
            fwje = fwje * 10000;
            this.FWJE = fwje.toString();
        }
    }
}
