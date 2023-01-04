package cn.gtmap.realestate.exchange.core.dto.hefei.jfpt.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description
 */
public class JfptTsDjfxxResponseData {

    private String mpayCode;

    private List<JfptTsDjfxxResponsePrePayRecord> prePayRecordList;

    public String getMpayCode() {
        return mpayCode;
    }

    public void setMpayCode(String mpayCode) {
        this.mpayCode = mpayCode;
    }

    public List<JfptTsDjfxxResponsePrePayRecord> getPrePayRecordList() {
        return prePayRecordList;
    }

    public void setPrePayRecordList(List<JfptTsDjfxxResponsePrePayRecord> prePayRecordList) {
        this.prePayRecordList = prePayRecordList;
    }
}
