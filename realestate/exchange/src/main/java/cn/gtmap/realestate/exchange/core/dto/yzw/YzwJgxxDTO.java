package cn.gtmap.realestate.exchange.core.dto.yzw;

import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCaseacceptDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCaseprocessDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.TBmCaseresultDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/2
 * @description 一张网结果信息
 */
@Api(value = "YzwJgxxDTO", description = "一张网结果信息")
public class YzwJgxxDTO {

    @ApiModelProperty("一张网编号")
    private String yzwbh;

    @ApiModelProperty("过程信息")
    private TBmCaseprocessDO tBmCaseprocessDO;

    @ApiModelProperty("结果信息")
    private TBmCaseresultDO tBmCaseresultDO;

    @ApiModelProperty("受理信息")
    private TBmCaseacceptDO tBmCaseacceptDO;

    public String getYzwbh() {
        return yzwbh;
    }

    public void setYzwbh(String yzwbh) {
        this.yzwbh = yzwbh;
    }

    public TBmCaseprocessDO gettBmCaseprocessDO() {
        return tBmCaseprocessDO;
    }

    public void settBmCaseprocessDO(TBmCaseprocessDO tBmCaseprocessDO) {
        this.tBmCaseprocessDO = tBmCaseprocessDO;
    }

    public TBmCaseresultDO gettBmCaseresultDO() {
        return tBmCaseresultDO;
    }

    public void settBmCaseresultDO(TBmCaseresultDO tBmCaseresultDO) {
        this.tBmCaseresultDO = tBmCaseresultDO;
    }

    public TBmCaseacceptDO gettBmCaseacceptDO() {
        return tBmCaseacceptDO;
    }

    public void settBmCaseacceptDO(TBmCaseacceptDO tBmCaseacceptDO) {
        this.tBmCaseacceptDO = tBmCaseacceptDO;
    }
}
