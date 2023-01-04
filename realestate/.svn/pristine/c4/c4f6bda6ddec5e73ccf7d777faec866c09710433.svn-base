package cn.gtmap.realestate.common.core.vo.inquiry.count;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @description 根据项目统计人员工作量信息，返回结果
 */
public class BdcRyGzltjXmxxVO {

    @ApiModelProperty(value = "人员名称")
    private String rymc;
    @ApiModelProperty(value = "人员id")
    private String ryid;
    @ApiModelProperty(value = "角色名称")
    private String jsmc;
    @ApiModelProperty(value = "受理收件量")
    private Integer sjl;
    @ApiModelProperty(value = "复审量")
    private Integer fsl;
    @ApiModelProperty(value = "缮证量")
    private Integer szl;
    @ApiModelProperty(value = "发证量")
    private Integer fzl;
    @ApiModelProperty(value = "打印有房无房证明量")
    private Integer yfwfzmsl;
    @ApiModelProperty(value = "打印权属证明量")
    private Integer qszmsl;
    @ApiModelProperty(value = "打印登记簿量")
    private Integer djbsl;
    @ApiModelProperty(value = "综合查询量")
    private Integer zhcxsl;
    @ApiModelProperty(value = "总数")
    private Integer zs;

    public String getRymc() {
        return rymc;
    }

    public void setRymc(String rymc) {
        this.rymc = rymc;
    }

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getJsmc() {
        return jsmc;
    }

    public void setJsmc(String jsmc) {
        this.jsmc = jsmc;
    }

    public Integer getSjl() {
        return sjl;
    }

    public void setSjl(Integer sjl) {
        this.sjl = sjl;
    }

    public Integer getFsl() {
        return fsl;
    }

    public void setFsl(Integer fsl) {
        this.fsl = fsl;
    }

    public Integer getSzl() {
        return szl;
    }

    public void setSzl(Integer szl) {
        this.szl = szl;
    }

    public Integer getFzl() {
        return fzl;
    }

    public void setFzl(Integer fzl) {
        this.fzl = fzl;
    }

    public Integer getYfwfzmsl() {
        return yfwfzmsl;
    }

    public void setYfwfzmsl(Integer yfwfzmsl) {
        this.yfwfzmsl = yfwfzmsl;
    }

    public Integer getQszmsl() {
        return qszmsl;
    }

    public void setQszmsl(Integer qszmsl) {
        this.qszmsl = qszmsl;
    }

    public Integer getDjbsl() {
        return djbsl;
    }

    public void setDjbsl(Integer djbsl) {
        this.djbsl = djbsl;
    }

    public Integer getZhcxsl() {return zhcxsl;}

    public void setZhcxsl(Integer zhcxsl) {this.zhcxsl = zhcxsl;}

    public Integer getZs() {
        zs = Optional.ofNullable(this.sjl).orElse(0) +
                Optional.ofNullable(this.fsl).orElse(0) +
                Optional.ofNullable(this.szl).orElse(0) +
                Optional.ofNullable(this.fzl).orElse(0) +
                Optional.ofNullable(this.yfwfzmsl).orElse(0) +
                Optional.ofNullable(this.qszmsl).orElse(0) +
                Optional.ofNullable(this.zhcxsl).orElse(0) +
                Optional.ofNullable(this.djbsl).orElse(0);
        return zs;
    }

    public void setZs(Integer zs) {
        this.zs = zs;
    }

    public BdcRyGzltjXmxxVO() {
    }

    public BdcRyGzltjXmxxVO(String rymc, String ryid, String jsmc) {
        this.rymc = rymc;
        this.ryid = ryid;
        this.jsmc = jsmc;
    }
}
