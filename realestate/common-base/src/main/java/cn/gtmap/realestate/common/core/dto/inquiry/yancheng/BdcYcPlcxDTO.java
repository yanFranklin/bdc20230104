package cn.gtmap.realestate.common.core.dto.inquiry.yancheng;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcPlcxDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/1/11 9:17
 * @description
 */
@Api(value = "BdcYcPlcxDTO", description = "盐城不动产批量查询分页查询DTO")
public class BdcYcPlcxDTO extends BdcPlcxDTO {
    @ApiModelProperty("序号")
    private Integer xh;

    @ApiModelProperty("查询结果")
    private String cxjg;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;

    @ApiModelProperty("备注")
    private String bz;

    @ApiModelProperty("注销登记时间")
    private String zxdjsj;

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getCxjg() {
        return cxjg;
    }

    public void setCxjg(String cxjg) {
        this.cxjg = cxjg;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getZxdjsj() {
        return zxdjsj;
    }

    public void setZxdjsj(String zxdjsj) {
        this.zxdjsj = zxdjsj;
    }
}
