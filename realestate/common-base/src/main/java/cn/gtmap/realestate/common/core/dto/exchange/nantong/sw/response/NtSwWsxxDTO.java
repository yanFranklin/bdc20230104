package cn.gtmap.realestate.common.core.dto.exchange.nantong.sw.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/11/22
 * @description 获取税务完税信息DTO
 */
@XmlRootElement(name = "TAXBIZML")
@XmlAccessorType(XmlAccessType.FIELD)

public class NtSwWsxxDTO {


    /**
     * TAXBIZML : {"FCJYSKXXLIST":{"FHM":"返回码","FHXX":"返回信息","SJBH":"收件编号","HTBH":"合同编号","JYUUID":"交易编号","FWUUID":"房屋编号"},"QSWSFJXXLIST":{"FJLX":"附件类型","FJID":"附件ID","WJSJ":"文件数据"}}
     */
    @XmlElement(name = "FCJYSKXXLIST")
    private FcjyskxxListbean fcjyskxxList;
    @XmlElement(name = "QSWSFJXXLIST")
    private List<QswsfjxxListbean> qswsfjxxList;


    /**
     * FCJYSKXXLIST : {"FHM":"返回码","FHXX":"返回信息","SJBH":"收件编号","HTBH":"合同编号","JYUUID":"交易编号","FWUUID":"房屋编号"}
     * QSWSFJXXLIST : {"FJLX":"附件类型","FJID":"附件ID","WJSJ":"文件数据"}
     */

    public FcjyskxxListbean getFcjyskxxList() {
        return fcjyskxxList;
    }

    public void setFcjyskxxList(FcjyskxxListbean fcjyskxxList) {
        this.fcjyskxxList = fcjyskxxList;
    }

    public List<QswsfjxxListbean> getQswsfjxxList() {
        return qswsfjxxList;
    }

    public void setQswsfjxxList(List<QswsfjxxListbean> qswsfjxxList) {
        this.qswsfjxxList = qswsfjxxList;
    }

    @Override
    public String toString() {
        return "SwWsxxDTO{" +
                "fcjyskxxList=" + fcjyskxxList +
                ", qswsfjxxList=" + qswsfjxxList +
                '}';
    }
}
