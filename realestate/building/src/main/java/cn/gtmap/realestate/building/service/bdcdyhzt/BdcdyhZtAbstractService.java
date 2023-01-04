package cn.gtmap.realestate.building.service.bdcdyhzt;

import cn.gtmap.realestate.building.core.service.BdcdyZtService;
import cn.gtmap.realestate.building.core.service.FwFcqlrService;
import cn.gtmap.realestate.building.util.BdcdyhZtUtils;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.annotations.SaveBdcdyhZt;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-26
 * @description 更新BDCDYH状态
 */
public abstract class BdcdyhZtAbstractService<T> {

    @Autowired
    protected BdcdyZtService bdcdyZtService;

    @Autowired
    protected EntityMapper entityMapper;

    @Autowired
    private FwFcqlrService fwFcqlrService;

    public BdcdyhZtAbstractService(){
        try{
            Class entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            BdcdyhZtUtils.setImplBean(entityClass.getName(),this);
        }catch (Exception e){

        }
    }

    public BdcdyhZtAbstractService getBean(String entityClassName){
        return BdcdyhZtUtils.IMPL_BEAN.get(entityClassName);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param object
     * @return void
     * @description 保存状态
     */
    public void insertZt(Object object) {
        SSjBdcdyhxsztDO sSjBdcdyhxsztDO = structureXszt(object);
        if(sSjBdcdyhxsztDO != null && StringUtils.isNotBlank(sSjBdcdyhxsztDO.getBdcdyh())){
            bdcdyZtService.insertXsztDO(sSjBdcdyhxsztDO);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param obj
     * @param oldXszt
     * @return void
     * @description 删除状态
     */
    public void deleteZt(Object obj,SSjBdcdyhxsztDO oldXszt){
        if(oldXszt != null && StringUtils.isNotBlank(oldXszt.getBdcdyh())){
            bdcdyZtService.deleteBdcdyhXszt(oldXszt.getBdcdyh());
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param record
     * @param oldXszt
     * @param newXszt
     * @return void
     * @description 更新状态
     */
    public void updateZt(Object record,SSjBdcdyhxsztDO oldXszt,SSjBdcdyhxsztDO newXszt,boolean updateNull){

        String oldBdcdyh = oldXszt != null ? oldXszt.getBdcdyh() : "";
        String newBdcdyh = newXszt != null ? newXszt.getBdcdyh() : "";

        // 1. 原有  新没有  删除BDCDYHXSZT
        if(StringUtils.isNotBlank(oldBdcdyh)
                && StringUtils.isBlank(newBdcdyh)){
            bdcdyZtService.deleteBdcdyhXszt(oldBdcdyh);
        }

        // 2. 原没有 新有  新增BDCDYHXSZT
        if(StringUtils.isBlank(oldBdcdyh)
                && StringUtils.isNotBlank(newBdcdyh)){
            bdcdyZtService.insertXsztDO(newXszt);
        }

        // 3. 原有 新有
        if(StringUtils.isNotBlank(oldBdcdyh)
                && StringUtils.isNotBlank(newBdcdyh)){
            // 3.1 BDCDYH 一致  更新
            if(StringUtils.equals(oldBdcdyh,newBdcdyh)){
                bdcdyZtService.updateXsztDO(newXszt,updateNull);
            }else{
                // 3.2 BDCDYH 不一致  删除后新增
                bdcdyZtService.deleteBdcdyhXszt(oldBdcdyh);
                bdcdyZtService.insertXsztDO(newXszt);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param object
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @description 获取更新前 XSZT
     */
    public SSjBdcdyhxsztDO getOldXszt(Object object){
        SSjBdcdyhxsztDO yXszt = null;
        if(CheckEntityUtils.checkPk(object)){
            Object pk = BuildingUtils.getObjectPk(object);
            Object yEntity = entityMapper.selectByPrimaryKey(object.getClass(),pk);
            SSjBdcdyhxsztDO sjBdcdyhxsztDO = structureXszt(yEntity);
            if(sjBdcdyhxsztDO != null && StringUtils.isNotBlank(sjBdcdyhxsztDO.getBdcdyh())){
                yXszt = bdcdyZtService.querySsjBdcdyhxsztDOByBdcdyh(sjBdcdyhxsztDO.getBdcdyh());
            }
        }
        return yXszt;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param object
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @description  构造 XSZT
     */
    public SSjBdcdyhxsztDO structureXszt(Object object){
        SSjBdcdyhxsztDO sSjBdcdyhxsztDO = new SSjBdcdyhxsztDO();
        List<Field> fieldList = AnnotationsUtils.getAnnotationField(object, SaveBdcdyhZt.class);
        for (Field entityField : fieldList) {
            // 获取 field值
            SaveBdcdyhZt anno = entityField.getAnnotation(SaveBdcdyhZt.class);
            String ztColumnName = anno.value();
            if(StringUtils.isBlank(ztColumnName)){
                ztColumnName = entityField.getName();
            }
            Object fieldVal = BuildingUtils.getVal(object,entityField.getName());
            BuildingUtils.setVal(sSjBdcdyhxsztDO,ztColumnName,fieldVal);
        }

        Object pk = BuildingUtils.getObjectPk(object);
        if(pk != null){
            // 查询QLR  TODO 目前写死 要考虑优化
            String qlrmc = fwFcqlrService.wmConcatQlrByFwIndex(pk.toString());
            if(StringUtils.isNotBlank(qlrmc)){
                sSjBdcdyhxsztDO.setQlr(qlrmc);
            }
        }
        return sSjBdcdyhxsztDO;
    }

}
