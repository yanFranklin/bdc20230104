package cn.gtmap.realestate.etl.core.model;

import cn.gtmap.realestate.common.core.dto.etl.CfxxDTO;
import cn.gtmap.realestate.common.core.dto.etl.DyxxDTO;
import cn.gtmap.realestate.common.core.dto.etl.XscqDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description 蚌埠交易查询webservice
 */
@XmlRootElement(name = "root")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class DoTaskResponseModel {

    @XmlElement(name="data")
    private List<CfxxDTO> cfxxDTOList;

    @XmlElement(name="data")
    private List<DyxxDTO> dyxxDTOList;

    public List<DyxxDTO> getDyxxDTOList() {
        return dyxxDTOList;
    }

    public void setDyxxDTOList(List<DyxxDTO> dyxxDTOList) {
        this.dyxxDTOList = dyxxDTOList;
    }

    public List<CfxxDTO> getCfxxDTOList() {
        return cfxxDTOList;
    }

    public void setCfxxDTOList(List<CfxxDTO> cfxxDTOList) {
        this.cfxxDTOList = cfxxDTOList;
    }
}
