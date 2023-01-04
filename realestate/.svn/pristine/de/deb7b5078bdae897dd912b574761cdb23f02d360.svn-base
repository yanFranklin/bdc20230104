package cn.gtmap.realestate.common.core.bo.accessnewlog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2018/12/20
 * @description 日志接入表
 */
@XmlRootElement(name = "AccessLogs")
public class AccessNewLogs {
    private List<AccessNewLog> accessNewLogList;

    public AccessNewLogs() {
    }

    @XmlElement(
            name = "AccessLog"
    )
    public List<AccessNewLog> getAccessNewLogList() {
        return this.accessNewLogList;
    }

    public void setAccessNewLogList(List<AccessNewLog> accessNewLogList) {
        this.accessNewLogList = accessNewLogList;
    }
}
