package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/16
 * @description 不动产登记项目与建设用地量化信息关系表
 */
@Table(name = "BDC_XM_JSYDLHXX_GX")
@ApiModel(value = "BdcXmJsydlhxxGxDO",description = "不动产登记项目与建设用地量化信息关系表")
public class BdcXmJsydlhxxGxDO {

    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;

    @ApiModelProperty(value = "建设用地自然幢信息主键")
    private String jsydzrzxxid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getJsydzrzxxid() {
        return jsydzrzxxid;
    }

    public void setJsydzrzxxid(String jsydzrzxxid) {
        this.jsydzrzxxid = jsydzrzxxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcXmJsydlhxxGxDO{");
        sb.append("gxid='").append(gxid).append('\'');
        sb.append(", jsydzrzxxid='").append(jsydzrzxxid).append('\'');
        sb.append(", gzlslid='").append(gzlslid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
