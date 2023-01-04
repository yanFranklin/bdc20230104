package cn.gtmap.realestate.etl.core.model;

import cn.gtmap.realestate.common.core.dto.etl.BzjdDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description
 */
@XmlRootElement(name = "root")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class BzjdModel {

    @XmlElement(name="data")
    private List<BzjdDTO> bzjdDTOList;

    public List<BzjdDTO> getBzjdDTOList() {
        return bzjdDTOList;
    }

    public void setBzjdDTOList(List<BzjdDTO> bzjdDTOList) {
        this.bzjdDTOList = bzjdDTOList;
    }

    @Override
    public String toString() {
        return "BzjdModel{" +
                "bzjdDTOList=" + bzjdDTOList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BzjdModel)) {
            return false;
        }
        BzjdModel bzjdModel = (BzjdModel) o;
        return Objects.equals(getBzjdDTOList(), bzjdModel.getBzjdDTOList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBzjdDTOList());
    }
}
