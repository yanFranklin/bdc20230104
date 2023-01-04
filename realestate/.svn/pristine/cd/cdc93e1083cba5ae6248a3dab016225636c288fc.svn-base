package cn.gtmap.realestate.exchange.core.dto.fssr;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.alibaba.fastjson.annotation.JSONField;

import cn.gtmap.realestate.exchange.core.dto.fssr.JfblDataDetailDTO;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0  2022/5/24 9:59
 * @description 缴费办理
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JfblDataDetailsDTO {

    @JSONField(name = "NONTAXCODE")
    private List<JfblDataDetailDTO> DETAIL;


    public List<JfblDataDetailDTO> getDETAIL() {
        return DETAIL;
    }

    public void setDETAIL(List<JfblDataDetailDTO> DETAIL) {
        this.DETAIL = DETAIL;
    }

    @Override
    public String toString() {
        return "JfblDataDetailsDTO{" +
                "DETAIL=" + DETAIL +
                '}';
    }
}
