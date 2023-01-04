package cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * 查询印章信息
 * @author wangyinghao
 */
public class GmxaDzzzSearchSealReq {

    /**
     * 组织
     */
    @ApiModelProperty("组织")
    String deptId;

    /**
     * 印章编码
     */
    @ApiModelProperty("印章编码")
    String sealCode;

    /**
     * 印章名称
     */
    @ApiModelProperty("印章名称")
    String sealName;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getSealCode() {
        return sealCode;
    }

    public void setSealCode(String sealCode) {
        this.sealCode = sealCode;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }
}
