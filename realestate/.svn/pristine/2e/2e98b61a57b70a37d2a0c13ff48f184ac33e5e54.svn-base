package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlQl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/5
 * @description 不动产受理地役权
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcSlDyiqDTO.class)
@ApiModel(value = "BdcSlDyiqDTO", description = "不动产受理地役权信息")
public class BdcSlDyiqDTO implements BdcSlQl {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "地役权内容")
    private String dyqnr;

    @ApiModelProperty(value = "权利起始时间", example = "2018-10-01 12:18:48")
    private Date qlqssj;

    @ApiModelProperty(value = "权利结束时间", example = "2018-10-01 12:18:48")
    private Date qljssj;

    @ApiModelProperty(value = "需役地不动产单元号")
    private String xydbdcdyh;

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

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getDyqnr() {
        return dyqnr;
    }

    public void setDyqnr(String dyqnr) {
        this.dyqnr = dyqnr;
    }

    public Date getQlqssj() {
        return qlqssj;
    }

    public void setQlqssj(Date qlqssj) {
        this.qlqssj = qlqssj;
    }

    public Date getQljssj() {
        return qljssj;
    }

    public void setQljssj(Date qljssj) {
        this.qljssj = qljssj;
    }

    public String getXydbdcdyh() {
        return xydbdcdyh;
    }

    public void setXydbdcdyh(String xydbdcdyh) {
        this.xydbdcdyh = xydbdcdyh;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    @Override
    public String toString() {
        return "BdcSlDyiqDTO{" +
                "id='" + id + '\'' +
                ", xmid='" + xmid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", dyqnr='" + dyqnr + '\'' +
                ", qlqssj=" + qlqssj +
                ", qljssj=" + qljssj +
                ", xydbdcdyh='" + xydbdcdyh + '\'' +
                ", fj='" + fj + '\'' +
                '}';
    }
}
