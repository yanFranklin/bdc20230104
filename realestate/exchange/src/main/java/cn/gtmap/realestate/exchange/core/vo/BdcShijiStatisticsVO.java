package cn.gtmap.realestate.exchange.core.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-08-02
 * @description 第三方日志统计  市级接口查询统计入参 VO
 */
public class BdcShijiStatisticsVO implements Serializable {

    private static final long serialVersionUID = 4010125368528589864L;

    /**
     * 查询的起始时间和终止时间、查询部门（展示所有部门）、查询人（和部门联动）
     */
    private Date startTime;

    private Date endTime;

    private String department;

    private String creater;

    private String interfaceName;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public static final class BdcShijiStatisticsVOBuilder {
        private Date startTime;
        private Date endTime;
        private String department;
        private String creater;
        private String interfaceName;

        private BdcShijiStatisticsVOBuilder() {
        }

        public static BdcShijiStatisticsVOBuilder aBdcShijiStatisticsVO() {
            return new BdcShijiStatisticsVOBuilder();
        }

        public BdcShijiStatisticsVOBuilder withStartTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public BdcShijiStatisticsVOBuilder withEndTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public BdcShijiStatisticsVOBuilder withDepartment(String department) {
            this.department = department;
            return this;
        }

        public BdcShijiStatisticsVOBuilder withCreater(String creater) {
            this.creater = creater;
            return this;
        }

        public BdcShijiStatisticsVOBuilder withInterfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }

        public BdcShijiStatisticsVO build() {
            BdcShijiStatisticsVO bdcShijiStatisticsVO = new BdcShijiStatisticsVO();
            bdcShijiStatisticsVO.setStartTime(startTime);
            bdcShijiStatisticsVO.setEndTime(endTime);
            bdcShijiStatisticsVO.setDepartment(department);
            bdcShijiStatisticsVO.setCreater(creater);
            bdcShijiStatisticsVO.setInterfaceName(interfaceName);
            return bdcShijiStatisticsVO;
        }
    }
}
