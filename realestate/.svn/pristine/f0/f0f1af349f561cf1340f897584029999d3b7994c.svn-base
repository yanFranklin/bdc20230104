package cn.gtmap.realestate.common.core.bo.accessnewlog;

import cn.gtmap.realestate.common.util.jaxb.JaxbIntegerAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/4/27
 * @description 当日上报详单集合属性
 */
@XmlType(
        name = "accessList",
        propOrder = {"totalNum", "access"}
)
@XmlRootElement(
        name = "AccessList"
)
public class AccessList {

    private Integer totalNum;

    private List<Access> access;


    @XmlAttribute(
            name = "totalNum"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    @XmlElement(
            name = "Access",
            nillable = true,
            required = true
    )

    public List<Access> getAccess() {
        return access;
    }

    public void setAccess(List<Access> access) {
        this.access = access;
    }
}
