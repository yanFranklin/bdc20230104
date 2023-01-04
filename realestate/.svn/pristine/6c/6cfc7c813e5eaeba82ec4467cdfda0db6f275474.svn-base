package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-11
 * @description 外网申请 请求创建项目 后的响应
 */
public class WwsqCjBdcXmResponseDTO {

    /**
     * 规则验证返回的结果
     */
    private List<Map<String, Object>> gzyzList;

    /**
     * 生成的不动产项目列表
     */
    private List<BdcXmDO> bdcXmDOList;

    /**
     * 非登记流程返回的受理信息
     */
    private BdcSlxxInitDTO bdcSlxxInitDTO;

    /*
     * 组合流程附件材料信息
     * */
    private List<WwsqZhlcSjclDTO> wwsqZhlcSjclDTOList;

    private Boolean sfzbbj;

    public Boolean getSfzbbj() {
        return sfzbbj;
    }

    public void setSfzbbj(Boolean sfzbbj) {
        this.sfzbbj = sfzbbj;
    }

    public List<Map<String, Object>> getGzyzList() {
        return gzyzList;
    }

    public void setGzyzList(List<Map<String, Object>> gzyzList) {
        this.gzyzList = gzyzList;
    }

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }

    public BdcSlxxInitDTO getBdcSlxxInitDTO() {
        return bdcSlxxInitDTO;
    }

    public void setBdcSlxxInitDTO(BdcSlxxInitDTO bdcSlxxInitDTO) {
        this.bdcSlxxInitDTO = bdcSlxxInitDTO;
    }

    public List<WwsqZhlcSjclDTO> getWwsqZhlcSjclDTOList() {
        return wwsqZhlcSjclDTOList;
    }

    public void setWwsqZhlcSjclDTOList(List<WwsqZhlcSjclDTO> wwsqZhlcSjclDTOList) {
        this.wwsqZhlcSjclDTOList = wwsqZhlcSjclDTOList;
    }

    @Override
    public String toString() {
        return "WwsqCjBdcXmResponseDTO{" +
                "gzyzList=" + gzyzList +
                ", bdcXmDOList=" + bdcXmDOList +
                ", bdcSlxxInitDTO=" + bdcSlxxInitDTO +
                ", wwsqZhlcSjclDTOList=" + wwsqZhlcSjclDTOList +
                ", sfzbbj=" + sfzbbj +
                '}';
    }
}
