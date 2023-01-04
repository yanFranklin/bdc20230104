package cn.gtmap.realestate.common.core.domain.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 工作流事件和接口关系实体信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-30 15:01
 **/
@Table(name = "BDC_GZLSJ_GX")
@ApiModel(value = "BdcGzlsjJkGxDO", description = "工作流事件和接口关系实体信息")
public class BdcGzlsjJkGxDO {

    @Id
    @ApiModelProperty(value = "关系id")
    private String gxid;

    @ApiModelProperty(value = "事件id")
    private String sjid;

    @ApiModelProperty(value = "接口id")
    private String jkid;

    @ApiModelProperty(value = "顺序号")
    private Integer sxh;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid;
    }

    public String getJkid() {
        return jkid;
    }

    public void setJkid(String jkid) {
        this.jkid = jkid;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    @Override
    public String toString() {
        return "BdcGzlsjJkGxDO{" +
                "gxid='" + gxid + '\'' +
                ", sjid='" + sjid + '\'' +
                ", jkid='" + jkid + '\'' +
                ", sxh=" + sxh +
                '}';
    }
}
