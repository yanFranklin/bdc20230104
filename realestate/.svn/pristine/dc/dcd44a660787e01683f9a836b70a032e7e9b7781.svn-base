package cn.gtmap.realestate.portal.ui.core.dto;

import cn.gtmap.realestate.common.core.dto.portal.BdcProcessDefData;

import java.util.List;

/**
 * 新建任务信息
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/27.
 * @description
 */
public class BdcCategoryProcessDto {
    /*
    *  业务分类Id
    * */
    private String id;

    /**
     * 业务分类
     */
    private String name;

    /*
     *  业务分类描述
     * */
    private String description;

    /*
    *  流程列表
    * */
    private List<BdcProcessDefData> processList;

    /**
     * 子流程
     */
    List<BdcCategoryProcessDto> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BdcProcessDefData> getProcessList() {
        return processList;
    }

    public void setProcessList(List<BdcProcessDefData> processList) {
        this.processList = processList;
    }

    public List<BdcCategoryProcessDto> getChildren() {
        return children;
    }

    public void setChildren(List<BdcCategoryProcessDto> children) {
        this.children = children;
    }
}
