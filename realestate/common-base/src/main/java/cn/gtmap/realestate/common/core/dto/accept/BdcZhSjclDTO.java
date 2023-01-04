package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 组合流程收件材料DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-03-29 08:54
 **/
public class BdcZhSjclDTO {

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "登记小类名称")
    private String djxlmc;

    @ApiModelProperty(value = "权利人名称")
    private String qlr;

    @ApiModelProperty(value = "收件材料信息")
    private List<BdcSlSjclDO> bdcSlSjclDOList;

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getDjxlmc() {
        return djxlmc;
    }

    public void setDjxlmc(String djxlmc) {
        this.djxlmc = djxlmc;
    }

    public List<BdcSlSjclDO> getBdcSlSjclDOList() {
        return bdcSlSjclDOList;
    }

    public void setBdcSlSjclDOList(List<BdcSlSjclDO> bdcSlSjclDOList) {
        this.bdcSlSjclDOList = bdcSlSjclDOList;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    @Override
    public String toString() {
        return "BdcZhSjclDTO{" +
                "djxl='" + djxl + '\'' +
                ", djxlmc='" + djxlmc + '\'' +
                ", qlr='" + qlr + '\'' +
                ", bdcSlSjclDOList=" + bdcSlSjclDOList +
                '}';
    }
}
