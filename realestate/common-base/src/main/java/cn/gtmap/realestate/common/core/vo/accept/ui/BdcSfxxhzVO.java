package cn.gtmap.realestate.common.core.vo.accept.ui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @return 收费信息汇总VO
 */
public class BdcSfxxhzVO {
    /**
     * 缴费时间
     */
    private List<String> jfsj;

    /**
     * 要展示的登记部门名称
     */
    private List<String> orgName;

    /**
     * 要展示的登记部门代码
     */
    private List<String> orgCode;

    /**
     * 收费汇总数据
     */
    private List<LinkedHashMap<String, Object>> data;


    public List<String> getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(List<String> orgCode) {
        this.orgCode = orgCode;
    }

    public List<String> getJfsj() {
        return jfsj;
    }

    public void setJfsj(List<String> jfsj) {
        this.jfsj = jfsj;
    }

    public List<String> getOrgName() {
        return orgName;
    }

    public void setOrgName(List<String> orgName) {
        this.orgName = orgName;
    }

    public List<LinkedHashMap<String, Object>> getData() {
        return data;
    }

    public void setData(List<LinkedHashMap<String, Object>> data) {
        this.data = data;
    }
}
