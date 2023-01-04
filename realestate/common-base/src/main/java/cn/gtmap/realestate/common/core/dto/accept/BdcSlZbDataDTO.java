package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 打印子表数据传输DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-11-16 10:38
 **/
public class BdcSlZbDataDTO {

    private String dyxl;

    private Map configParam;

    List<BdcDysjZbPzDO> bdcDysjZbPzDOList;

    public Map getConfigParam() {
        return configParam;
    }

    public void setConfigParam(Map configParam) {
        this.configParam = configParam;
    }

    public List<BdcDysjZbPzDO> getBdcDysjZbPzDOList() {
        return bdcDysjZbPzDOList;
    }

    public void setBdcDysjZbPzDOList(List<BdcDysjZbPzDO> bdcDysjZbPzDOList) {
        this.bdcDysjZbPzDOList = bdcDysjZbPzDOList;
    }

    public String getDyxl() {
        return dyxl;
    }

    public void setDyxl(String dyxl) {
        this.dyxl = dyxl;
    }

    @Override
    public String toString() {
        return "BdcSlZbDataDTO{" +
                "dyxl='" + dyxl + '\'' +
                ", configParam=" + configParam +
                ", bdcDysjZbPzDOList=" + bdcDysjZbPzDOList +
                '}';
    }
}
