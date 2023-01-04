package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/11/30
 * @description 公用头和msg
 */
@XmlRootElement(name = "CFX")
@XmlAccessorType(XmlAccessType.PROPERTY)

public class FsczRequest {


    private FsczHead head;


    private MSGModel msg;

    @XmlElement(name = "HEAD")
    public FsczHead getHead() {
        return head;
    }

    public void setHead(FsczHead head) {
        this.head = head;
    }

    @XmlElement(name = "MSG")
    public MSGModel getMsg() {
        return msg;
    }

    public void setMsg(MSGModel msg) {
        this.msg = msg;
    }
}
