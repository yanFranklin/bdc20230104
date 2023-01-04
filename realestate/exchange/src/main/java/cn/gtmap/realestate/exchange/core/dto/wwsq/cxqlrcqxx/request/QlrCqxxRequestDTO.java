package cn.gtmap.realestate.exchange.core.dto.wwsq.cxqlrcqxx.request;

import java.util.List;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0, 2022/7/14 8:54
 * @description 权利人产权信息入参
 */
public class QlrCqxxRequestDTO {

    /**
     * 权利人集合
     */
    private List<String> qlrmcList;

    /**
     * 权利人证件号集合
     */
    private List<String> qlrzjhList;

    /**
     * 区县代码集合
     */
    private List<String> qxdmList;

    /**
     * 权利类型集合
     */
    private List<Integer> qllxList;

    public List<String> getQlrmcList() {
        return qlrmcList;
    }

    public void setQlrmcList(List<String> qlrmcList) {
        this.qlrmcList = qlrmcList;
    }

    public List<String> getQlrzjhList() {
        return qlrzjhList;
    }

    public void setQlrzjhList(List<String> qlrzjhList) {
        this.qlrzjhList = qlrzjhList;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    public List<Integer> getQllxList() {
        return qllxList;
    }

    public void setQllxList(List<Integer> qllxList) {
        this.qllxList = qllxList;
    }

    @Override
    public String toString() {
        return "QlrCqxxRequestDTO{" +
                "qlrmcList='" + qlrmcList + '\'' +
                ", qlrzjhList='" + qlrzjhList + '\'' +
                ", qxdmList=" + qxdmList +
                ", qllxList=" + qllxList +
                '}';
    }
}
