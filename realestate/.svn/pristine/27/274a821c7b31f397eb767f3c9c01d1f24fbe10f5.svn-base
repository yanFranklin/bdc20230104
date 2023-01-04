package cn.gtmap.realestate.common.core.domain.rules;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0  2018-11-10
 * @description 不动产业务规则
 */
@Table(name = "bdc_gz_ywgz")
@ApiModel(value = "BdcGzYwgzDO", description = "不动产业务规则")
public class BdcGzYwgzDO implements Serializable {

    private static final long serialVersionUID = 5303211925955263848L;
    @Id
    @ApiModelProperty(value = "业务规则ID")
    private String ywgzid;

    @ApiModelProperty(value = "规则名称")
    private String gzmc;

    @ApiModelProperty(value = "数据来源类型")
    private String sjlylx;

    @ApiModelProperty(value = "规则条件")
    private String gztj;

    @ApiModelProperty(value = "数据SQL")
    private String sjsql;

    @ApiModelProperty(value = "数据服务")
    private String sjfw;

    @ApiModelProperty(value = "提示信息")
    private String tsxx;

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

    public String getSjfw() {
        return sjfw;
    }

    public void setSjfw(String sjfw) {
        this.sjfw = sjfw;
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
        return "BdcGzYwgzDO{" +
                "ywgzid='" + ywgzid + '\'' +
                ", gzmc='" + gzmc + '\'' +
                ", sjlylx='" + sjlylx + '\'' +
                ", gztj='" + gztj + '\'' +
                ", sjsql='" + sjsql + '\'' +
                ", sjfw='" + sjfw + '\'' +
                ", tsxx='" + tsxx + '\'' +
                ", gzsm='" + gzsm + '\'' +
                '}';
    }
}
