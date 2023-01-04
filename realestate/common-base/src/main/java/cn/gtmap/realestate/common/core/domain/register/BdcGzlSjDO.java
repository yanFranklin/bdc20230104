package cn.gtmap.realestate.common.core.domain.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: realestate
 * @description: 工作流事件实体信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-30 14:51
 **/
@Table(name = "BDC_GZLSJ")
@ApiModel(value = "BdcGzlSjDO", description = "工作流事件实体信息")
public class BdcGzlSjDO {


    @Id
    @ApiModelProperty(value = "事件id")
    private String sjid;

    @ApiModelProperty(value = "事件名称")
    private String sjmc;

    @ApiModelProperty(value = "事件标识")
    private String sjbs;

    @ApiModelProperty(value = "节点名称")
    private String jdmc;

    @ApiModelProperty(value = "创建事件")
    private Date cjsj;

    @ApiModelProperty(value = "创建人")
    private String cjr;

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid;
    }

    public String getSjmc() {
        return sjmc;
    }

    public void setSjmc(String sjmc) {
        this.sjmc = sjmc;
    }

    public String getSjbs() {
        return sjbs;
    }

    public void setSjbs(String sjbs) {
        this.sjbs = sjbs;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    @Override
    public String toString() {
        return "BdcGzlSjDO{" +
                "sjid='" + sjid + '\'' +
                ", sjmc='" + sjmc + '\'' +
                ", sjbs='" + sjbs + '\'' +
                ", jdmc='" + jdmc + '\'' +
                ", cjsj=" + cjsj +
                ", cjr='" + cjr + '\'' +
                '}';
    }
}
