package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2021/5/17
 * @description 受理抵押权dto
 */
@ApiModel(value = "BdcSlDyaqDTO", description = "受理抵押权实体")
public class BdcSlDyaqDTO {

    @ApiModelProperty(value = "抵押权权利信息")
    private BdcSlDyaqDO bdcSlDyaqDO;

    @ApiModelProperty(value = "抵押权人集合")
    private List<BdcSlSqrDO> bdcSlSqrDOList;

    @ApiModelProperty(value = "抵押权人集合")
    private List<BdcQlrDO> bdcQlrDOList;

    @ApiModelProperty(value = "组合贷款时用于区分商业贷款和公积金贷款")
    private String djxl;

    public BdcSlDyaqDO getBdcSlDyaqDO() {
        return bdcSlDyaqDO;
    }

    public void setBdcSlDyaqDO(BdcSlDyaqDO bdcSlDyaqDO) {
        this.bdcSlDyaqDO = bdcSlDyaqDO;
    }

    public List<BdcSlSqrDO> getBdcSlSqrDOList() {
        return bdcSlSqrDOList;
    }

    public void setBdcSlSqrDOList(List<BdcSlSqrDO> bdcSlSqrDOList) {
        this.bdcSlSqrDOList = bdcSlSqrDOList;
    }

    public List<BdcQlrDO> getBdcQlrDOList() {
        return bdcQlrDOList;
    }

    public void setBdcQlrDOList(List<BdcQlrDO> bdcQlrDOList) {
        this.bdcQlrDOList = bdcQlrDOList;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    @Override
    public String toString() {
        return "BdcSlDyaqDTO{" +
                "bdcSlDyaqDO=" + bdcSlDyaqDO +
                ", bdcSlSqrDOList=" + bdcSlSqrDOList +
                ", bdcQlrDOList=" + bdcQlrDOList +
                ", djxl='" + djxl + '\'' +
                '}';
    }


}
