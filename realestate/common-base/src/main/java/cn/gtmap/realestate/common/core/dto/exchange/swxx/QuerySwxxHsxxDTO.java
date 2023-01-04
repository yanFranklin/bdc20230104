package cn.gtmap.realestate.common.core.dto.exchange.swxx;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxMxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSwspFjDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-11
 * @description 核税信息
 */
public class QuerySwxxHsxxDTO {

    // 核税信息
    private BdcSlHsxxDO bdcSlHsxxDO;

    // 明细列表
    private List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 税务税票附件
     */
    private List<BdcSwspFjDTO> swspFjDTOS;

    //税票附件文件夹名称
    private String swspfjWjjmc;

    //备注
    private String bz;

    @ApiModelProperty(value = "申报提醒函附件地址")
    private String sbtxhfj;

    public BdcSlHsxxDO getBdcSlHsxxDO() {
        return bdcSlHsxxDO;
    }

    public void setBdcSlHsxxDO(BdcSlHsxxDO bdcSlHsxxDO) {
        this.bdcSlHsxxDO = bdcSlHsxxDO;
    }

    public List<BdcSlHsxxMxDO> getBdcSlHsxxMxDOList() {
        return bdcSlHsxxMxDOList;
    }

    public void setBdcSlHsxxMxDOList(List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList) {
        this.bdcSlHsxxMxDOList = bdcSlHsxxMxDOList;
    }

    public List<BdcSwspFjDTO> getSwspFjDTOS() {
        return swspFjDTOS;
    }

    public void setSwspFjDTOS(List<BdcSwspFjDTO> swspFjDTOS) {
        this.swspFjDTOS = swspFjDTOS;
    }

    public String getSwspfjWjjmc() {
        return swspfjWjjmc;
    }

    public void setSwspfjWjjmc(String swspfjWjjmc) {
        this.swspfjWjjmc = swspfjWjjmc;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSbtxhfj() {
        return sbtxhfj;
    }

    public void setSbtxhfj(String sbtxhfj) {
        this.sbtxhfj = sbtxhfj;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuerySwxxHsxxDTO{");
        sb.append("bdcSlHsxxDO=").append(bdcSlHsxxDO);
        sb.append(", bdcSlHsxxMxDOList=").append(bdcSlHsxxMxDOList);
        sb.append(", swspFjDTOS=").append(swspFjDTOS);
        sb.append(", swspfjWjjmc='").append(swspfjWjjmc).append('\'');
        sb.append(", bz='").append(bz).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
