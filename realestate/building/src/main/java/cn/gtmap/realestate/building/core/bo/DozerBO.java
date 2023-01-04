package cn.gtmap.realestate.building.core.bo;


import org.dozer.classmap.MappingFileData;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-06
 * @description xml Dozer对照实体
 */
public class DozerBO {

    /**
     * 是否 删除后插入
     */
    private boolean deleteAndInsert;

    /**
     * 是否 更新
     */
    private boolean update;

    /**
     * 是否 直接新增
     */
    private boolean insert;

    /**
     * 验证方法
     */
    private Method checkMethod;

    /**
     * 查询方法
     */
    private Method queryMethod;

    /**
     * mapping 列表
     */
    private List<MappingFileData> mappingList;

    public boolean isDeleteAndInsert() {
        return deleteAndInsert;
    }

    public void setDeleteAndInsert(boolean deleteAndInsert) {
        this.deleteAndInsert = deleteAndInsert;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public Method getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(Method checkMethod) {
        this.checkMethod = checkMethod;
    }

    public Method getQueryMethod() {
        return queryMethod;
    }

    public void setQueryMethod(Method queryMethod) {
        this.queryMethod = queryMethod;
    }

    public List<MappingFileData> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<MappingFileData> mappingList) {
        this.mappingList = mappingList;
    }
}
