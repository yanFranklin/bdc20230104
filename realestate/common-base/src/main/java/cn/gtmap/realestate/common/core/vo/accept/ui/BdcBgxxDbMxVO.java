package cn.gtmap.realestate.common.core.vo.accept.ui;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/10/24.
 * @description 不动产业务变更信息对比明细VO
 */
@ApiModel(value = "BdcBgxxDbVO", description = "不动产业务变更信息对比明细VO")
public class BdcBgxxDbMxVO {
    @ApiModelProperty(value = "表名称")
    private String tableName;

    @ApiModelProperty(value = "类名称")
    private String className;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "原不动产项目信息")
    private BdcXmDO ybdcXm;

    @ApiModelProperty(value = "原不动产权利信息")
    private BdcQl ybdcQl;

    @ApiModelProperty(value = "当前不动产项目信息")
    private BdcXmDO bdcXm;

    @ApiModelProperty(value = "当前不动产权利信息")
    private BdcQl bdcQl;

    @ApiModelProperty(value = "当前不动产权利信息")
    private List<BdcBgxxQlrVO> bdcBgxxQlrVOList;

    public BdcXmDO getYbdcXm() {
        return ybdcXm;
    }

    public void setYbdcXm(BdcXmDO ybdcXm) {
        this.ybdcXm = ybdcXm;
    }

    public BdcQl getYbdcQl() {
        return ybdcQl;
    }

    public void setYbdcQl(BdcQl ybdcQl) {
        this.ybdcQl = ybdcQl;
    }

    public BdcXmDO getBdcXm() {
        return bdcXm;
    }

    public void setBdcXm(BdcXmDO bdcXm) {
        this.bdcXm = bdcXm;
    }

    public BdcQl getBdcQl() {
        return bdcQl;
    }

    public void setBdcQl(BdcQl bdcQl) {
        this.bdcQl = bdcQl;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public List<BdcBgxxQlrVO> getBdcBgxxQlrVOList() {
        return bdcBgxxQlrVOList;
    }

    public void setBdcBgxxQlrVOList(List<BdcBgxxQlrVO> bdcBgxxQlrVOList) {
        this.bdcBgxxQlrVOList = bdcBgxxQlrVOList;
    }

    public BdcBgxxDbMxVO(){}

    public BdcBgxxDbMxVO(String bdcdyh, Integer qllx){
        this.bdcdyh = bdcdyh;
        this.qllx = qllx;
    }
}
