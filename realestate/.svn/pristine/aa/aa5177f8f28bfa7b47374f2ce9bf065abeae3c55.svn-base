package cn.gtmap.realestate.exchange.core.dto.fssr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0  2022/5/24 9:59
 * @description 缴费办理数据
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JfblDataDTO {

    // 主信息节点：对应一般缴款书主信息
    @JSONField(name = "MAIN")
    private JfblDataMainDTO MAIN;

    // -明细信息列表节点：对应一般缴款书明细信息列表
    @JSONField(name = "DETAILS")
    private JfblDataDetailsDTO DETAILS;

    public JfblDataMainDTO getMAIN() {
        return MAIN;
    }

    public void setMAIN(JfblDataMainDTO MAIN) {
        this.MAIN = MAIN;
    }

    public JfblDataDetailsDTO getDETAILS() {
        return DETAILS;
    }

    public void setDETAILS(JfblDataDetailsDTO DETAILS) {
        this.DETAILS = DETAILS;
    }

    @Override
    public String toString() {
        return "JfblDataDTO{" +
                "MAIN=" + MAIN +
                ", DETAILS=" + DETAILS +
                '}';
    }
}
