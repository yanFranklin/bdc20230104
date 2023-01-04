package cn.gtmap.realestate.building.service.impl.bdcdyhzt;

import cn.gtmap.realestate.building.service.bdcdyhzt.BdcdyhZtAbstractService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.annotations.SaveBdcdyhZt;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhlsztDO;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-27
 * @description 备份表
 */
public class HFwBdcdyhZtServiceImpl<T> extends BdcdyhZtAbstractService<T> {

    /**
     * @param object
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存状态
     */
    @Override
    public void insertZt(Object object) {
        SSjBdcdyhxsztDO sSjBdcdyhxsztDO = structureXszt(object);
        if(sSjBdcdyhxsztDO != null && StringUtils.isNotBlank(sSjBdcdyhxsztDO.getBdcdyh())){
            SSjBdcdyhlsztDO sSjBdcdyhlsztDO = new SSjBdcdyhlsztDO();
            BeanUtils.copyProperties(sSjBdcdyhxsztDO,sSjBdcdyhlsztDO);
            sSjBdcdyhlsztDO.setZtid(UUIDGenerator.generate());
            bdcdyZtService.insertLsztDO(sSjBdcdyhlsztDO);
        }
    }

    /**
     * @param obj
     * @param oldXszt
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除状态
     */
    @Override
    public void deleteZt(Object obj, SSjBdcdyhxsztDO oldXszt) {
        if(oldXszt != null && StringUtils.isNotBlank(oldXszt.getBdcdyh())){
            bdcdyZtService.deleteBdcdyhLszt(oldXszt.getBdcdyh());
        }
    }

    /**
     * @param record
     * @param oldXszt
     * @param newXszt
     * @param updateNull
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新状态
     */
    @Override
    public void updateZt(Object record, SSjBdcdyhxsztDO oldXszt, SSjBdcdyhxsztDO newXszt, boolean updateNull) {
        return;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param object
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @description  构造 XSZT
     */
    @Override
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
        // 查询 已有现势状态数据
        if(StringUtils.isNotBlank(sSjBdcdyhxsztDO.getBdcdyh())){
            sSjBdcdyhxsztDO = bdcdyZtService.getBdcdyztDO(sSjBdcdyhxsztDO.getBdcdyh());
        }
        return sSjBdcdyhxsztDO;
    }
}
