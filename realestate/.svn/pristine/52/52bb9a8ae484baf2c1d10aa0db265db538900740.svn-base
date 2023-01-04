package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.service.impl.NydDjxxServiceImpl;
import cn.gtmap.realestate.building.service.impl.QszdDjxxServiceImpl;
import cn.gtmap.realestate.building.service.impl.ZdDjxxServiceImpl;
import cn.gtmap.realestate.building.service.impl.ZhDjxxServiceImpl;
import cn.gtmap.realestate.common.core.domain.building.*;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-10
 * @description 不动产单元信息同步枚举
 */
public enum BdcdyxxEnum {

    /**
     * 宗地 地籍调查表
     */
    ZDDJDCBDO(ZdDjdcbDO.class,
            Constants.DOZER_UPDATE_TYPE_DEFAULT,
            BuildingUtils.getMethod(ZdDjxxServiceImpl.class, "queryDjdcbByBdcdyhOrDjh", String.class),
            BuildingUtils.getMethod(ZdDjxxServiceImpl.class, "checkNeedDjdcb", String.class)),

    /**
     * 农用地 地籍调查表
     */
    NYDDJDCBDO(NydDjdcbDO.class,
            Constants.DOZER_UPDATE_TYPE_DEFAULT,
            BuildingUtils.getMethod(NydDjxxServiceImpl.class, "queryDjdcbByBdcdyhOrDjh", String.class),
            BuildingUtils.getMethod(NydDjxxServiceImpl.class, "checkNeedDjdcb", String.class)),

    /**
     * 权属宗地 地籍调查表
     */
    QSZDDJDCBDO(QszdDjdcbDO.class,
            Constants.DOZER_UPDATE_TYPE_DEFAULT,
            BuildingUtils.getMethod(QszdDjxxServiceImpl.class, "queryDjdcbByBdcdyhOrDjh", String.class),
            BuildingUtils.getMethod(QszdDjxxServiceImpl.class, "checkNeedDjdcb", String.class)),

    /**
     * 宗海 地籍调查表
     */
    ZHDJDCB(ZhDjdcbDO.class,
            Constants.DOZER_UPDATE_TYPE_DEFAULT,
            BuildingUtils.getMethod(ZhDjxxServiceImpl.class, "queryDjdcbByBdcdyhOrDjh", String.class),
            BuildingUtils.getMethod(ZhDjxxServiceImpl.class, "checkNeedDjdcb", String.class)),

    /**
     * 宗地 权利人
     */
    ZDQLRDO(ZdQlrDO.class,
            Constants.DOZER_UPDATE_TYPE_DELANDINSERT,
            BuildingUtils.getMethod(ZdQlrService.class, "listZdQlrByBdcdyh", String.class),
            BuildingUtils.getMethod(ZdDjxxServiceImpl.class, "checkNeedDjQlr", String.class),
            BuildingUtils.getMethod(ZdDjxxServiceImpl.class, "setDjQlrFkVal", String.class, ZdQlrDO.class)),

    /**
     * 农用地 权利人
     */
    NYDQLRDO(NydQlrDO.class,
            Constants.DOZER_UPDATE_TYPE_DELANDINSERT,
            BuildingUtils.getMethod(NydQlrService.class, "listNydQlrByBdcdyh", String.class),
            BuildingUtils.getMethod(NydDjxxServiceImpl.class, "checkNeedDjQlr", String.class),
            BuildingUtils.getMethod(NydDjxxServiceImpl.class, "setDjQlrFkVal", String.class, NydQlrDO.class)),

    /**
     * 权属宗地 权利人
     */
    QSZDQLRDO(QszdQlrDO.class,
            Constants.DOZER_UPDATE_TYPE_DELANDINSERT,
            BuildingUtils.getMethod(QszdQlrService.class,"listQszdQlrByBdcdyh",String.class),
            BuildingUtils.getMethod(QszdDjxxServiceImpl.class,"checkNeedDjQlr",String.class),
            BuildingUtils.getMethod(QszdDjxxServiceImpl.class,"setDjQlrFkVal",String.class,QszdQlrDO.class)),
    /**
     * 宗海 权利人
     */
    ZHQLRDO(ZhQlrDO.class,
            Constants.DOZER_UPDATE_TYPE_DELANDINSERT,
            BuildingUtils.getMethod(ZhQlrService.class,"listZhQlrByBdcdyh",String.class),
            BuildingUtils.getMethod(ZhDjxxServiceImpl.class,"checkNeedDjQlr",String.class),
            BuildingUtils.getMethod(ZhDjxxServiceImpl.class,"setDjQlrFkVal",String.class,ZhQlrDO.class)),

    /**
     * 房屋房产 权利人
     */
    FWFCQLRDO(FwFcqlrDO.class,
            Constants.DOZER_UPDATE_TYPE_DELANDINSERT,
            BuildingUtils.getMethod(BdcdyService.class,"listFwFcQlrByBdcdyh",String.class),
            BuildingUtils.getMethod(FwFcqlrService.class,"checkNeedFwFcQlr",String.class),
            BuildingUtils.getMethod(FwFcqlrService.class,"setFwFcQlrFkVal",String.class,FwFcqlrDO.class)),

    /**
     * 房屋户室
     */
    FWHSDO(FwHsDO.class,
            Constants.DOZER_UPDATE_TYPE_DEFAULT,
            BuildingUtils.getMethod(FwHsService.class,"queryFwhsByBdcdyh",String.class)),

    /**
     * 房屋预测户室
     */
    FWYCHSDO(FwYchsDO.class,
            Constants.DOZER_UPDATE_TYPE_DEFAULT,
            BuildingUtils.getMethod(FwYcHsService.class,"queryFwYcHsByBdcdyh",String.class)),

    /**
     * 房屋逻辑幢
     */
    FWLJZDO(FwLjzDO.class,
            Constants.DOZER_UPDATE_TYPE_DEFAULT,
            BuildingUtils.getMethod(FwLjzService.class,"queryLjzByBdcdyh",String.class)),

    /**
     * 项目信息
     */
    FWXMXXDO(FwXmxxDO.class,
            Constants.DOZER_UPDATE_TYPE_DEFAULT,
            BuildingUtils.getMethod(FwXmxxService.class,"queryXmxxByBdcdyh",String.class));

    /**
     * 实体类
     */
    private Class doClass;

    /**
     * 当前实体的更新方式
     */
    private String updateType;

    /**
     * 根据BDCDYH查询当前实体的反射方法
     */
    private Method queryMethod;

    /**
     * 验证是否需要查询当前实体的反射方法
     */
    private Method checkMethod;

    private Method setFkMethod;

    /**
     * @param doClass     实体类
     * @param updateType  当前实体的更新方式
     * @param queryMethod 根据BDCDYH查询当前实体的反射方法
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    BdcdyxxEnum(Class doClass, String updateType, Method queryMethod) {
        this.doClass = doClass;
        this.updateType = updateType;
        this.queryMethod = queryMethod;
    }

    /**
     * @param doClass     实体类
     * @param updateType  当前实体的更新方式
     * @param queryMethod 根据BDCDYH查询当前实体的反射方法
     * @param checkMethod 验证是否需要查询当前实体的反射方法
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    BdcdyxxEnum(Class doClass, String updateType, Method queryMethod, Method checkMethod) {
        this.doClass = doClass;
        this.updateType = updateType;
        this.queryMethod = queryMethod;
        this.checkMethod = checkMethod;
    }

    BdcdyxxEnum(Class doClass, String updateType, Method queryMethod, Method checkMethod, Method setFkMethod) {
        this.doClass = doClass;
        this.updateType = updateType;
        this.queryMethod = queryMethod;
        this.checkMethod = checkMethod;
        this.setFkMethod = setFkMethod;
    }

    public Class getDoClass() {
        return doClass;
    }

    public void setDoClass(Class doClass) {
        this.doClass = doClass;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public Method getQueryMethod() {
        return queryMethod;
    }

    public void setQueryMethod(Method queryMethod) {
        this.queryMethod = queryMethod;
    }

    public Method getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(Method checkMethod) {
        this.checkMethod = checkMethod;
    }

    public Method getSetFkMethod() {
        return setFkMethod;
    }

    public void setSetFkMethod(Method setFkMethod) {
        this.setFkMethod = setFkMethod;
    }
}
