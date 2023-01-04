package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlQl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/11
 * @description 不动产受理异议信息
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcSlYyDTO.class)
@ApiModel(value = "BdcSlYyDTO", description = "不动产受理异议信息")
public class BdcSlYyDTO implements BdcSlQl {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "异议事项")
    private String yysx;

    @ApiModelProperty(value = "注销异议原因")
    private String zxyyyy;

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

    public String getYysx() {
        return yysx;
    }

    public void setYysx(String yysx) {
        this.yysx = yysx;
    }


    public String getZxyyyy() {
        return zxyyyy;
    }

    public void setZxyyyy(String zxyyyy) {
        this.zxyyyy = zxyyyy;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }
}
