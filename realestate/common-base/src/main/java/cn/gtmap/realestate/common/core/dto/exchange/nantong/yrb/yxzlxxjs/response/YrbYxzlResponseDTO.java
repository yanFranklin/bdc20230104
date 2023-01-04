package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.response;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.YrbFhmResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/8
 * @description 影像文件推送返回DTO
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "TAXBIZML")
public class YrbYxzlResponseDTO {

    private YrbFhmResponse yxzlxxjsList;

    @XmlElement(name = "YXZLXXJSLIST")
    public YrbFhmResponse getYxzlxxjsList() {
        return yxzlxxjsList;
    }

    public void setYxzlxxjsList(YrbFhmResponse yxzlxxjsList) {
        this.yxzlxxjsList = yxzlxxjsList;
    }


    @Override
    public String toString() {
        return "YrbYxzlResponseDTO{" +
                "yxzlxxjsList=" + yxzlxxjsList +
                '}';
    }
}
