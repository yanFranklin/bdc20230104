package cn.gtmap.realestate.common.core.domain.engine;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/4
 * @description  不动产规则数据流参数实体DO定义
 */
@Table(name = "BDC_GZ_SJL_CS")
public class BdcGzSjlCsDO {
    /**
     * 数据流参数ID
     */
    @Id
    @ApiModelProperty(value = "数据流参数ID")
    private String sjlcsid;
    /**
     * 数据流ID
     */
    @ApiModelProperty(value = "数据流ID")
    private String sjlid;
    /**
     * 数据流参数序号
     */
    @ApiModelProperty(value = "数据流参数序号")
    private String sjlcsxh;
    /**
     * 数据流参数种类（1、常量 2、入参 3、动参 4、实体）
     */
    @ApiModelProperty(value = "数据流参数种类")
    private String sjlcszl;
    /**
     * 数据流参数名称
     */
    @ApiModelProperty(value = "数据流参数名称")
    private String sjlcsmc;
    /**
     * 数据流参数值
     */
    @ApiModelProperty(value = "数据流参数值")
    private Object sjlcsz;


    public String getSjlcsid() {
        return sjlcsid;
    }

    public void setSjlcsid(String sjlcsid) {
        this.sjlcsid = sjlcsid;
    }

    public String getSjlid() {
        return sjlid;
    }

    public void setSjlid(String sjlid) {
        this.sjlid = sjlid;
    }

    public String getSjlcsxh() {
        return sjlcsxh;
    }

    public void setSjlcsxh(String sjlcsxh) {
        this.sjlcsxh = sjlcsxh;
    }

    public String getSjlcszl() {
        return sjlcszl;
    }

    public void setSjlcszl(String sjlcszl) {
        this.sjlcszl = sjlcszl;
    }

    public String getSjlcsmc() {
        return sjlcsmc;
    }

    public void setSjlcsmc(String sjlcsmc) {
        this.sjlcsmc = sjlcsmc;
    }

    public Object getSjlcsz() {
        return sjlcsz;
    }

    public void setSjlcsz(Object sjlcsz) {
        this.sjlcsz = sjlcsz;
    }

    @Override
    public String toString() {
        return "BdcGzSjlCsDO{" +
                "sjlcsid='" + sjlcsid + '\'' +
                ", sjlid='" + sjlid + '\'' +
                ", sjlcsxh='" + sjlcsxh + '\'' +
                ", sjlcszl='" + sjlcszl + '\'' +
                ", sjlcsmc='" + sjlcsmc + '\'' +
                ", sjlcsz=" + sjlcsz +
                '}';
    }
}
