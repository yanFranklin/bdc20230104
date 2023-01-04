package cn.gtmap.realestate.building.core.bo;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-02
 * @description 楼盘表查询配置 户室实体
 */
public class FwhsBO {

    /**
     * 基础字段
     */
    private InfoBO info;

    /**
     * 状态
     */
    private String status;

    /**
     * 实体内的link
     */
    private List<LinkBO> links;

    public InfoBO getInfo() {
        return info;
    }

    public void setInfo(InfoBO info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LinkBO> getLinks() {
        return links;
    }

    public void setLinks(List<LinkBO> links) {
        this.links = links;
    }
}
