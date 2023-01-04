package cn.gtmap.realestate.exchange.core.dto.accessLog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0, 2018/12/20
 * @description 日志接入表
 */
@XmlRootElement(name = "AccessLogs")
public class AccessLogs {
    private List<AccessLog> accessLogList;

    public AccessLogs() {
    }

    @XmlElement(
            name = "AccessLog"
    )
    public List<AccessLog> getAccessLogList() {
        return this.accessLogList;
    }

    public void setAccessLogList(List<AccessLog> accessLogList) {
        this.accessLogList = accessLogList;
    }
}
