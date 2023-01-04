package cn.gtmap.realestate.etl.core.model;

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
public class XscqModel {

    @XmlElement(name="data")
    private List<XscqDTO> xscqDTOList;

    public List<XscqDTO> getXscqDTOList() {
        return xscqDTOList;
    }

    public void setXscqDTOList(List<XscqDTO> xscqDTOList) {
        this.xscqDTOList = xscqDTOList;
    }

    @Override
    public String toString() {
        return "XscqModel{" +
                "xscqDTOList=" + xscqDTOList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof XscqModel)) {
            return false;
        }
        XscqModel xscqModel = (XscqModel) o;
        return Objects.equals(getXscqDTOList(), xscqModel.getXscqDTOList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getXscqDTOList());
    }
}
