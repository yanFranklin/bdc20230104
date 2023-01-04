package cn.gtmap.realestate.common.core.dto.exchange.access;

import java.util.List;

/**
 * @program: bdcdj3.0
 * @description: 上报销账前后端交互实体信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-09-29 10:07
 **/
public class SbxzDTO {

    private List<String> xmidList;

    private List<String> idList;

    private String lwyy;

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getLwyy() {
        return lwyy;
    }

    public void setLwyy(String lwyy) {
        this.lwyy = lwyy;
    }
}
