package cn.gtmap.realestate.register.core.dto;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 登记簿页面参数传输类
 */
public class BdcDjbDTO {
    /**宗地宗海号*/
    @ApiModelProperty(value = "宗地宗海号")
    private String zdzhh;
    /**登记机构*/
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    /**省区市*/
    @ApiModelProperty(value = "省区县")
    private String sqs;
    /**市区*/
    @ApiModelProperty(value = "市区")
    private String sq;
    /**县市区*/
    @ApiModelProperty(value = "县市区")
    private String xsq;
    /**街道*/
    @ApiModelProperty(value = "街道")
    private String jd;
    /**街坊*/
    @ApiModelProperty(value = "街坊")
    private String jf;
    /**组*/
    @ApiModelProperty(value = "组")
    private String zu;

    @ApiModelProperty(value = "分页对象")
    private Pageable pageable;

    public String getZdzhh() {
        return zdzhh;
    }

    public void setZdzhh(String zdzhh) {
        this.zdzhh = zdzhh;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getSqs() {
        return sqs;
    }

    public void setSqs(String sqs) {
        this.sqs = sqs;
    }

    public String getSq() {
        return sq;
    }

    public void setSq(String sq) {
        this.sq = sq;
    }

    public String getXsq() {
        return xsq;
    }

    public void setXsq(String xsq) {
        this.xsq = xsq;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getJf() {
        return jf;
    }

    public void setJf(String jf) {
        this.jf = jf;
    }

    public String getZu() {
        return zu;
    }

    public void setZu(String zu) {
        this.zu = zu;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    @Override
    public String toString() {
        return "BdcDjbDTO{" +
                "zdzhh='" + zdzhh + '\'' +
                ", djjg='" + djjg + '\'' +
                ", sqs='" + sqs + '\'' +
                ", sq='" + sq + '\'' +
                ", xsq='" + xsq + '\'' +
                ", jd='" + jd + '\'' +
                ", jf='" + jf + '\'' +
                ", zu='" + zu + '\'' +
                ", pageable=" + pageable +
                '}';
    }
}
