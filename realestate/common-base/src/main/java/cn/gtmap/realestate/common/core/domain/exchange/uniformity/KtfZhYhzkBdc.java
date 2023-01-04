package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.KtfZhYhzkDO;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/5/11
 * @description 宗海用海状况一致性
 */
public class KtfZhYhzkBdc extends KtfZhYhzkDO {

    @ApiModelProperty("地方用海方式名称")
    private String dfyhfsmc;

    @XmlAttribute(name = "DFYHFSMC")
    public String getDfyhfsmc() {
        return dfyhfsmc;
    }

    public void setDfyhfsmc(String dfyhfsmc) {
        this.dfyhfsmc = dfyhfsmc;
    }
}
