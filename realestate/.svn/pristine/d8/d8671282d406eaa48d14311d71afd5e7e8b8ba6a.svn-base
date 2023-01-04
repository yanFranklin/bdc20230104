package cn.gtmap.realestate.building.core.bo;

import cn.gtmap.realestate.common.core.dto.building.BdcdyxxRequestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-05
 * @description
 */
public class BdcxxConvertBO {

    /**
     * 请求参数实体
     */
    private BdcdyxxRequestDTO bdcdyxxRequestDTO;

    /**
     * 请求参数
     */
    private Map<String,List> requestDOMap;


    /**
     * 转换后的 需要插入实体List
     */
    private List<Object> insertList = new ArrayList<>();

    /**
     * 转换后的 需要更新实体List
     */
    private List<Object> updateList = new ArrayList<>();

    /**
     * 转换后需要删除的实体List
     */
    private List<Object> deleteObjectList = new ArrayList<>();

    public List<Object> getDeleteObjectList() {
        return deleteObjectList;
    }

    public void setDeleteObjectList(List<Object> deleteObjectList) {
        this.deleteObjectList = deleteObjectList;
    }

    public List<Object> getInsertList() {
        return insertList;
    }

    public void setInsertList(List<Object> insertList) {
        this.insertList = insertList;
    }

    public List<Object> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<Object> updateList) {
        this.updateList = updateList;
    }

    public Map<String, List> getRequestDOMap() {
        return requestDOMap;
    }

    public void setRequestDOMap(Map<String, List> requestDOMap) {
        this.requestDOMap = requestDOMap;
    }

    public BdcdyxxRequestDTO getBdcdyxxRequestDTO() {
        return bdcdyxxRequestDTO;
    }

    public void setBdcdyxxRequestDTO(BdcdyxxRequestDTO bdcdyxxRequestDTO) {
        this.bdcdyxxRequestDTO = bdcdyxxRequestDTO;
    }
}
