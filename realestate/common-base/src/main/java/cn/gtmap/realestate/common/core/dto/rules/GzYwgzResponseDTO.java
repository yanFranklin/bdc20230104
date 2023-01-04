package cn.gtmap.realestate.common.core.dto.rules;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0  2018/12/13
 * @description 业务规则DTO
 */
@ApiModel(value = "GzYwgzResponseDTO", description = "业务规则DTO")
public class GzYwgzResponseDTO implements Serializable {
    private static final long serialVersionUID = -3724692637024016174L;
    /**
     * 业务规则ID
     */
    @ApiModelProperty(value = "业务规则ID")
    private String ywgzid;
    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称")
    private String gzmc;
    /**
     * 数据来源类型
     */
    @ApiModelProperty(value = "数据来源类型")
    private String sjlylx;
    /**
     * 规则条件
     */
    @ApiModelProperty(value = "规则条件")
    private String gztj;
    /**
     * 数据SQL
     */
    @ApiModelProperty(value = "数据SQL")
    private String sjsql;
    /**
     * 数据服务
     */
    @ApiModelProperty(value = "数据服务")
    private String fjfw;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String tsxx;

    /**
     * 规则说明
     */
    @ApiModelProperty(value = "规则说明")
    private String gzsm;

    public String getYwgzid() {
        return ywgzid;
    }

    public void setYwgzid(String ywgzid) {
        this.ywgzid = ywgzid;
    }

    public String getGzmc() {
        return gzmc;
    }

    public void setGzmc(String gzmc) {
        this.gzmc = gzmc;
    }

    public String getSjlylx() {
        return sjlylx;
    }

    public void setSjlylx(String sjlylx) {
        this.sjlylx = sjlylx;
    }

    public String getGztj() {
        return gztj;
    }

    public void setGztj(String gztj) {
        this.gztj = gztj;
    }

    public String getSjsql() {
        return sjsql;
    }

    public void setSjsql(String sjsql) {
        this.sjsql = sjsql;
    }

    public String getFjfw() {
        return fjfw;
    }

    public void setFjfw(String fjfw) {
        this.fjfw = fjfw;
    }

    public String getTsxx() {
        return tsxx;
    }

    public void setTsxx(String tsxx) {
        this.tsxx = tsxx;
    }

    public String getGzsm() {
        return gzsm;
    }

    public void setGzsm(String gzsm) {
        this.gzsm = gzsm;
    }

    @Override
    public String toString() {
        return "GzYwgzResponseDTO{" +
                "ywgzid='" + ywgzid + '\'' +
                ", gzmc='" + gzmc + '\'' +
                ", sjlylx='" + sjlylx + '\'' +
                ", gztj='" + gztj + '\'' +
                ", sjsql='" + sjsql + '\'' +
                ", fjfw='" + fjfw + '\'' +
                ", tsxx='" + tsxx + '\'' +
                ", gzsm='" + gzsm + '\'' +
                '}';
    }
}