package cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.qsdjxx.response;

import java.util.List;

/**
 * @description 权属登记信息
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date 2022/12/22 11:10
 */
public class BdcQsdjxxResponseDTO {

    /**
     * 人员信息
     */
    private List<BdcQlrResponseDTO> transEntPros;

    /**
     * 房源信息
     */
    private List<BdcFyxxResponseDTO> transHousePros;


    public List<BdcQlrResponseDTO> getTransEntPros() {
        return transEntPros;
    }

    public void setTransEntPros(List<BdcQlrResponseDTO> transEntPros) {
        this.transEntPros = transEntPros;
    }

    public List<BdcFyxxResponseDTO> getTransHousePros() {
        return transHousePros;
    }

    public void setTransHousePros(List<BdcFyxxResponseDTO> transHousePros) {
        this.transHousePros = transHousePros;
    }

    @Override
    public String toString() {
        return "BdcQsdjxxResponseDTO{" +
                "transEntPros=" + transEntPros +
                ", transHousePros=" + transHousePros +
                '}';
    }
}
