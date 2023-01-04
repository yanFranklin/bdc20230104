package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlQl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/5
 * @description 不动产受理土地所有权信息
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcSlTdsyqDTO.class)
@ApiModel(value = "BdcSlTdsyqDTO", description = "不动产受理土地所有权信息")
public class BdcSlTdsyqDTO implements BdcSlQl {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "农用地面积")
    private Double nydmj;

    @ApiModelProperty(value = "耕地面积")
    private Double gdmj;

    @ApiModelProperty(value = "林地面积")
    private Double ldmj;

    @ApiModelProperty(value = "草地面积")
    private Double cdmj;

    @ApiModelProperty(value = "其他农用地面积")
    private Double qtnydmj;

    @ApiModelProperty(value = "建设用地面积")
    private Double jsydmj;

    @ApiModelProperty(value = "未利用地面积")
    private Double wlydmj;

    @ApiModelProperty(value = "附记")
    private String fj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getXmid() {
        return xmid;
    }

    @Override
    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Double getNydmj() {
        return nydmj;
    }

    public void setNydmj(Double nydmj) {
        this.nydmj = nydmj;
    }

    public Double getGdmj() {
        return gdmj;
    }

    public void setGdmj(Double gdmj) {
        this.gdmj = gdmj;
    }

    public Double getLdmj() {
        return ldmj;
    }

    public void setLdmj(Double ldmj) {
        this.ldmj = ldmj;
    }

    public Double getCdmj() {
        return cdmj;
    }

    public void setCdmj(Double cdmj) {
        this.cdmj = cdmj;
    }

    public Double getQtnydmj() {
        return qtnydmj;
    }

    public void setQtnydmj(Double qtnydmj) {
        this.qtnydmj = qtnydmj;
    }

    public Double getJsydmj() {
        return jsydmj;
    }

    public void setJsydmj(Double jsydmj) {
        this.jsydmj = jsydmj;
    }

    public Double getWlydmj() {
        return wlydmj;
    }

    public void setWlydmj(Double wlydmj) {
        this.wlydmj = wlydmj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    @Override
    public String toString() {
        return "BdcSlTdsyqDTO{" +
                "id='" + id + '\'' +
                ", xmid='" + xmid + '\'' +
                ", nydmj=" + nydmj +
                ", gdmj=" + gdmj +
                ", ldmj=" + ldmj +
                ", cdmj=" + cdmj +
                ", qtnydmj=" + qtnydmj +
                ", jsydmj=" + jsydmj +
                ", wlydmj=" + wlydmj +
                ", fj='" + fj + '\'' +
                '}';
    }
}
