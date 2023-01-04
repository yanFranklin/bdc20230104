package cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxxTycx.request;

import java.util.List;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0, 2022/10/21 14:54
 * @description 家庭不动产信息通用查询请求DTO
 */
public class JtBdcxxTycxRequestDTO {

    /**
     * 权利人名称
     */
    private List<String> qlrmcList;

    /**
     * 权利人证件种类
     */
    private List<String> qlzjzlList;

    /**
     * 权利人证件号
     */
    private List<String> qlrzjhList;

    /**
     * 权利类型
     */
    private List<Integer> qllxList;

    /**
     * 区县代码
     */
    private List<String> qxdmList;

    public List<String> getQlrmcList() {
        return qlrmcList;
    }

    public void setQlrmcList(List<String> qlrmcList) {
        this.qlrmcList = qlrmcList;
    }

    public List<String> getQlzjzlList() {
        return qlzjzlList;
    }

    public void setQlzjzlList(List<String> qlzjzlList) {
        this.qlzjzlList = qlzjzlList;
    }

    public List<String> getQlrzjhList() {
        return qlrzjhList;
    }

    public void setQlrzjhList(List<String> qlrzjhList) {
        this.qlrzjhList = qlrzjhList;
    }

    public List<Integer> getQllxList() {
        return qllxList;
    }

    public void setQllxList(List<Integer> qllxList) {
        this.qllxList = qllxList;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    @Override
    public String toString() {
        return "JtBdcxxTycxRequestDTO{" +
                "qlrmcList=" + qlrmcList +
                ", qlzjzlList=" + qlzjzlList +
                ", qlrzjhList=" + qlrzjhList +
                ", qllxList=" + qllxList +
                ", qxdmList=" + qxdmList +
                '}';
    }
}
