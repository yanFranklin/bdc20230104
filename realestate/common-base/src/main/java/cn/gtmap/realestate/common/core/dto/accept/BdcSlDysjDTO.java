package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 打印数据传参
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-09-02 13:58
 **/
@ApiModel(value = "BdcSlDysjDTO", description = "受理打印数据传参")
public class BdcSlDysjDTO implements Serializable {
    private static final long serialVersionUID = 5267908959558781666L;

    @ApiModelProperty(value = "初始化房屋开关信息")
    private List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList;
    @ApiModelProperty(value = "打印数量")
    private Integer dysl;
    @ApiModelProperty(value = "打印类型")
    private String dylx;

    public List<BdcCshFwkgSlDO> getBdcCshFwkgSlDOList() {
        return bdcCshFwkgSlDOList;
    }

    public void setBdcCshFwkgSlDOList(List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList) {
        this.bdcCshFwkgSlDOList = bdcCshFwkgSlDOList;
    }

    public Integer getDysl() {
        return dysl;
    }

    public void setDysl(Integer dysl) {
        this.dysl = dysl;
    }

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    @Override
    public String toString() {
        return "BdcSlDysjDTO{" +
                "bdcCshFwkgSlDOList=" + bdcCshFwkgSlDOList +
                ", dysl=" + dysl +
                ", dylx='" + dylx + '\'' +
                '}';
    }
}
