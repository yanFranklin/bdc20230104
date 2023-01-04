package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2020/05/28
 * @description 不动产受理收费收税订单信息DTO对象
 */
@ApiModel(value = "BdcSlSfssDdxxDTO", description = "不动产受理收费收税订单信息DTO")
public class BdcSlSfssDdxxDTO {

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "月结单号")
    private String yjdh;

    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;

    @ApiModelProperty(value = "税费总额")
    private Double ze;

    @ApiModelProperty(value = "拆分订单信息集合")
    private List<BdcSlSfssDdxxDO> cfddxxList;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public Double getZe() {
        return ze;
    }

    public void setZe(Double ze) {
        this.ze = ze;
    }

    public List<BdcSlSfssDdxxDO> getCfddxxList() {
        return cfddxxList;
    }

    public void setCfddxxList(List<BdcSlSfssDdxxDO> cfddxxList) {
        this.cfddxxList = cfddxxList;
    }

    public String getYjdh() {
        return yjdh;
    }

    public void setYjdh(String yjdh) {
        this.yjdh = yjdh;
    }
}
