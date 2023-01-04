package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.KttFwZrzDO;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/5/12
 * @description KTT_FW_ZRZ自然幢信息 一致性字段
 */
public class KttFwZrzBdc extends KttFwZrzDO {

    @ApiModelProperty("地方房屋结构名称")
    private String dffwjgmc;

    @XmlAttribute(name = "DFFWJGMC")
    public String getDffwjgmc() {
        return dffwjgmc;
    }

    public void setDffwjgmc(String dffwjgmc) {
        this.dffwjgmc = dffwjgmc;
    }
}
