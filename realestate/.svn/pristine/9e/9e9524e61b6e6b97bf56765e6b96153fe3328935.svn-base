package cn.gtmap.realestate.common.core.vo.accept.ui;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/10/21.
 * @description 变更信息对比VO对象
 */
@ApiModel(value = "BdcBgxxDbVO", description = "变更信息对比信息实体")
public class BdcBgxxDbVO {

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "不动产项目信息")
    private BdcXmDO bdcXm;

    @ApiModelProperty(value = "不动产权利信息")
    private BdcQl bdcQl;

    @ApiModelProperty(value = "不动产权利人信息集合")
    private List<BdcQlrDO> bdcQlrList;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
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

    public List<BdcQlrDO> getBdcQlrList() {
        return bdcQlrList;
    }

    public void setBdcQlrList(List<BdcQlrDO> bdcQlrList) {
        this.bdcQlrList = bdcQlrList;
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

    public BdcBgxxDbVO(){}

    public BdcBgxxDbVO(String xmid, Integer qllx, String bdcdyh){
        this.xmid = xmid;
        this.qllx = qllx;
        this.bdcdyh = bdcdyh;
    }

    public BdcBgxxDbVO withJbxx(String xmid, Integer qllx, String bdcdyh){
        this.xmid = xmid;
        this.qllx = qllx;
        this.bdcdyh = bdcdyh;
        return this;
    }

    public BdcBgxxDbVO withBdcYwxx(BdcXmDO bdcXmDO, BdcQl bdcQl, List<BdcQlrDO> bdcQlrList){
        this.bdcXm = bdcXmDO;
        this.bdcQl = bdcQl;
        this.bdcQlrList = bdcQlrList;
        return this;
    }

}
