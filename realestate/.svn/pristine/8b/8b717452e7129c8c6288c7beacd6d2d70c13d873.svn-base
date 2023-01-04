package cn.gtmap.realestate.common.core.domain.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 3.0, 2021/11/16
 * @description 常州电子签章状态信息表
 */
@Table(name = "BDC_CZ_DZQZ_ZTXX")
@ApiModel(value = "BdcCzDzqzZtxxDO", description = "常州电子签章状态信息表")
public class BdcCzDzqzZtxxDO {
    @Id
    @ApiModelProperty(value = "证书ID")
    private String zsid;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "推送时间")
    private Date tssj;

    @ApiModelProperty(value = "操作人员")
    private String tsry;

    /**
     * 0 （或空）未推送签章
     * 1 推送失败
     * 2 已推送未下载
     * 3 已推送下载失败
     * 4 已推送已下载
     */
    @ApiModelProperty(value = "推送状态")
    private Integer tszt;

    @ApiModelProperty(value = "日志信息")
    private String rzxx;


    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Date getTssj() {
        return tssj;
    }

    public void setTssj(Date tssj) {
        this.tssj = tssj;
    }

    public String getTsry() {
        return tsry;
    }

    public void setTsry(String tsry) {
        this.tsry = tsry;
    }

    public Integer getTszt() {
        return tszt;
    }

    public void setTszt(Integer tszt) {
        this.tszt = tszt;
    }

    public String getRzxx() {
        return rzxx;
    }

    public void setRzxx(String rzxx) {
        this.rzxx = rzxx;
    }

    @Override
    public String toString() {
        return "BdcCzDzqzZtxxDO{" +
                "zsid='" + zsid + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", tssj=" + tssj +
                ", tsry='" + tsry + '\'' +
                ", tszt=" + tszt +
                ", rzxx='" + rzxx + '\'' +
                '}';
    }
}
