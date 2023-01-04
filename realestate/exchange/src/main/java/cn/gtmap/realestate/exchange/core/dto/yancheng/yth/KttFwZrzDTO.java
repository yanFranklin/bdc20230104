package cn.gtmap.realestate.exchange.core.dto.yancheng.yth;

import cn.gtmap.realestate.common.core.domain.exchange.KttFwLjzDO;
import cn.gtmap.realestate.common.core.domain.exchange.KttFwZrzDO;
import cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttFwZrzOldDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/10
 * @description 权利人
 */
public class KttFwZrzDTO extends KttFwZrzOldDO {

    @ApiModelProperty(value = "建筑物名称")
    private String jzwmc;

    @ApiModelProperty(value = "状态")
    private String zt;

    @Override
    public String getJzwmc() {
        return jzwmc;
    }

    @Override
    public void setJzwmc(String jzwmc) {
        this.jzwmc = jzwmc;
    }

    @Override
    public String getZt() {
        return zt;
    }

    @Override
    public void setZt(String zt) {
        this.zt = zt;
    }
}
