package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/12/3
 * @description 不动产受理查询参数记录
 */
@Table(name = "BDC_SL_CXCS")
public class BdcSlCxcsDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String cxid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "查询人")
    private String cxr;

    @ApiModelProperty(value = "操作时间")
    private Date czsj;

    @ApiModelProperty(value = "查询参数")
    private String cxcs;

    public String getCxid() {
        return cxid;
    }

    public void setCxid(String cxid) {
        this.cxid = cxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getCxr() {
        return cxr;
    }

    public void setCxr(String cxr) {
        this.cxr = cxr;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getCxcs() {
        return cxcs;
    }

    public void setCxcs(String cxcs) {
        this.cxcs = cxcs;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcSlCxcsDO.class.getSimpleName() + "[", "]")
                .add("cxid='" + cxid + "'")
                .add("gzlslid='" + gzlslid + "'")
                .add("cxr='" + cxr + "'")
                .add("czsj=" + czsj)
                .add("cxcs='" + cxcs + "'")
                .toString();
    }
}
