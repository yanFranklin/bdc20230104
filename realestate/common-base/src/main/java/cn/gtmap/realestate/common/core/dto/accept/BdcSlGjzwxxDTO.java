package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlQl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/5
 * @description 不动产受理构（建）筑物信息
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcSlGjzwxxDTO.class)
@ApiModel(value = "BdcSlGjzwxxDTO", description = "不动产受理构（建）筑物信息")
public class BdcSlGjzwxxDTO implements BdcSlQl {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "构(建)筑物面积")
    private Double gjzwmj;

    @ApiModelProperty(value = "构（建）筑物类型")
    private Integer gjzwlx;

    @ApiModelProperty(value = "构（建）筑物规划用途")
    private String gjzwghyt;

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

    public Double getGjzwmj() {
        return gjzwmj;
    }

    public void setGjzwmj(Double gjzwmj) {
        this.gjzwmj = gjzwmj;
    }

    public Integer getGjzwlx() {
        return gjzwlx;
    }

    public void setGjzwlx(Integer gjzwlx) {
        this.gjzwlx = gjzwlx;
    }

    public String getGjzwghyt() {
        return gjzwghyt;
    }

    public void setGjzwghyt(String gjzwghyt) {
        this.gjzwghyt = gjzwghyt;
    }

    @Override
    public String toString() {
        return "BdcSlGjzwxxDTO{" +
                "id='" + id + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gjzwmj=" + gjzwmj +
                ", gjzwlx=" + gjzwlx +
                ", gjzwghyt='" + gjzwghyt + '\'' +
                '}';
    }
}
