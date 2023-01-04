package cn.gtmap.realestate.exchange.core.dto.yancheng;

import java.io.Serializable;
import java.util.Date;

/**
 * 市级接口查询统计返回参数
 */
public class BdcShijiStatisticsDTO implements Serializable {

    private static final long serialVersionUID = -4124907699775778901L;

    /**
     * 查询接口名称、查询人、查询人所属部门、查询时间、以及本次查询结果
     */
    private String interfaceName;

    private String creater;

    private String department;

    private Date queryTime;

    private String response;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
