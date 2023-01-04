package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.SSjZdtDO;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-23
 * @description 从数据库直接读取BASE64码
 */
@Service
@Validated
public class ReadZdtFromDbBase64ServiceImpl extends ReadZdtAbstractServiceImpl {

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH 读取base 64位码
     */
    @Override
    public String readBase64ByDjh(@NotBlank(message = "地籍号不能为空") String djh) {
        SSjZdtDO sSjZdtDO = super.queryZdtByDjh(djh);
        if(sSjZdtDO != null){
            return BuildingUtils.blobToStr(sSjZdtDO.getZdtImg());
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 读取base 64位码
     */
    @Override
    public String readBase64ByBdcdyh(String bdcdyh,String qjgldm) {
        String djh = bdcdyh.substring(0,19);
        SSjZdtDO sSjZdtDO = super.queryZdtByDjh(djh);
        if(sSjZdtDO != null){
            return BuildingUtils.blobToStr(sSjZdtDO.getZdtImg());
        }
        return null;
    }

}
