package cn.gtmap.realestate.common.core.qo.exchange.openapi;

import cn.gtmap.realestate.common.util.ParamUtil;

import java.util.List;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0
 * @date 2021/08/26 08:41
 */
public class AccessByTimeIntervalQO {

    private String startDate;

    private String endDate;

    private String type;

    private String xmly;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getXmly() {
        return xmly;
    }

    public void setXmly(String xmly) {
        this.xmly = xmly;
    }

    public void checkParam(){
        ParamUtil.nonNull(this.startDate, "开始时间不能为空");
        ParamUtil.nonNull(this.endDate,"结束时间不能为空");
    }
}
