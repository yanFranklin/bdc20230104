package cn.gtmap.realestate.exchange.core.dto.shucheng.fgj;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.BdcZdGyfsDO;
import cn.gtmap.realestate.common.core.domain.BdcZdZjzlDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/4/6
 * @description 舒城县房管局-一手房登记权利人数据DTO
 */
@ApiModel("舒城县房管局-一手房登记权利人数据DTO")
public class ShuchengFgjQlrxxDTO {
    @ApiModelProperty("产权证号")
    private String cqzh;

    @Zd(tableClass = BdcZdGyfsDO.class)
    @ApiModelProperty(value = "共有方式")
    private String gyfs;

    @ApiModelProperty(value = "权利比例")
    private String qlbl;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @Zd(tableClass = BdcZdZjzlDO.class)
    @ApiModelProperty(value = "证件种类")
    private String zjzl;

    @ApiModelProperty(value = "证件号")
    private String zjh;

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }
}
