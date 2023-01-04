package cn.gtmap.realestate.register.core.qo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * *
 *
 * @author <a href="mailto:xuzhou@gtmap.cn>xuzhou</a>"
 * @version 1.0, 2022/5/23
 * @description 项目附表更新证书形态QO
 */
public class BdcXmfbZsxtQO {
    @ApiModelProperty("项目id集合")
    private List<String> xmidList;

    @ApiModelProperty("证书形态")
    private Integer zsxt;

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public Integer getZsxt() {
        return zsxt;
    }

    public void setZsxt(Integer zsxt) {
        this.zsxt = zsxt;
    }

    @Override
    public String toString() {
        return "BdcXmfbZsxtQO{" +
                "xmidList=" + xmidList +
                ", zsxt=" + zsxt +
                '}';
    }
}
