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
 * @description 当日登簿集合属性
 */
@XmlType(
        name = "registerList",
        propOrder = {"totalNum", "register"}
)
@XmlRootElement(
        name = "RegisterList"
)
public class RegisterList {

    private Integer totalNum;

    private List<Register> register;


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
            name = "Register",
            nillable = true,
            required = true
    )
    public List<Register> getRegister() {
        return register;
    }

    public void setRegister(List<Register> register) {
        this.register = register;
    }
}
