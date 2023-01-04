package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wyh
 * @version 1.0, 2019/7/8
 * @description  不动产居住权信息查询结果DTO定义
 */
public class BdcJzqxxDTO {
    @ApiModelProperty(value = "居住范围")
    private String jzfw;

    @ApiModelProperty(value = "居住权人信息")
    List<BdcJzqrxxDTO> jzqrxx;

    @ApiModelProperty(value = "居住合同编号")
    private String jzhtbh;

    @ApiModelProperty(value = "居住权开始期限")
    private String jzqksqx;

    @ApiModelProperty(value = "居住权结束期限")
    private String jzqjsqx;

    @ApiModelProperty(value = "不动产证明号")
    private String bdczmh;

    @ApiModelProperty(value = "登记事件")
    private String djsj;

    @ApiModelProperty(value = "附记")
    private String fj;

    public String getJzfw() {
        return jzfw;
    }

    public void setJzfw(String jzfw) {
        this.jzfw = jzfw;
    }

    public List<BdcJzqrxxDTO> getJzqrxx() {
        return jzqrxx;
    }

    public void setJzqrxx(List<BdcJzqrxxDTO> jzqrxx) {
        this.jzqrxx = jzqrxx;
    }

    public String getJzhtbh() {
        return jzhtbh;
    }

    public void setJzhtbh(String jzhtbh) {
        this.jzhtbh = jzhtbh;
    }

    public String getJzqksqx() {
        return jzqksqx;
    }

    public void setJzqksqx(String jzqksqx) {
        this.jzqksqx = jzqksqx;
    }

    public String getJzqjsqx() {
        return jzqjsqx;
    }

    public void setJzqjsqx(String jzqjsqx) {
        this.jzqjsqx = jzqjsqx;
    }

    public String getBdczmh() {
        return bdczmh;
    }

    public void setBdczmh(String bdczmh) {
        this.bdczmh = bdczmh;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }
}
