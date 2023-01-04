package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/5
 * @description 不动产系统预留证号设置
 */
@Table(name = "BDC_XT_YLZH")
public class BdcXtYlzhDO extends BdcZhDO{
    @Id
    @ApiModelProperty(value = "预留证号ID")
    private String ylzhid;

    @ApiModelProperty(value = "证书id")
    private String zsid;

    @ApiModelProperty(value = "创建人")
    private String cjr;

    @ApiModelProperty(value = "报废原因")
    private String bfyy;

    @ApiModelProperty(value = "登记部门代码")
    private String djbmdm;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", example = "2018-10-01 12:18:48")
    private Date cjsj;


    public String getYlzhid() {
        return ylzhid;
    }

    public void setYlzhid(String ylzhid) {
        this.ylzhid = ylzhid;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getBfyy() {
        return bfyy;
    }

    public void setBfyy(String bfyy) {
        this.bfyy = bfyy;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getDjbmdm() {
        return djbmdm;
    }

    public void setDjbmdm(String djbmdm) {
        this.djbmdm = djbmdm;
    }

    @Override
    public String toString() {
        return "BdcXtYlzhDO{" +
                "ylzhid='" + ylzhid + '\'' +
                ", zsid='" + zsid + '\'' +
                ", cjr='" + cjr + '\'' +
                ", bfyy='" + bfyy + '\'' +
                ", cjsj=" + cjsj +
                ", djbmdm='" + djbmdm + '\'' +
                '}';
    }
}
