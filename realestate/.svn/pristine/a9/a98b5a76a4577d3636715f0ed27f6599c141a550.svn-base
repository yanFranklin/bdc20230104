package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-23
 * @description 从 DB 中读取 base64码
 */
@Service
public class ReadHstFromDbBase64ServiceImpl extends ReadHstAbstractServiceImpl{

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadHstFromDbBase64ServiceImpl.class);

    /**
     * @param fwHsIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据fwHsIndex 读取base 64位码
     */
    @Override
    public String readBase64ByFwHsIndex(String fwHsIndex) {
        if(StringUtils.isNotBlank(fwHsIndex)){
            FwHstDO fwHstDO = super.queryFwHstByFwHsIndex(fwHsIndex);
            if(fwHstDO != null){
                LOGGER.warn("根据户室主键查询到户室图数据{}", fwHsIndex);
                return BuildingUtils.blobToStr(fwHstDO.getHst());
            }else{
                LOGGER.error("根据户室主键查询不到户室图数据,fwHsIndex:{}",fwHsIndex);
            }
        }
        return null;
    }

    /**
     * @param fwHstIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FwHstIndex 读取 base 64码
     */
    @Override
    public String readBase64ByFwHstIndex(String fwHstIndex) {
        if(StringUtil.isNotBlank(fwHstIndex)){
            FwHstDO fwHstDO = super.queryFwHstByFwHstIndex(fwHstIndex);
            if(fwHstDO != null){
                return BuildingUtils.blobToStr(fwHstDO.getHst());
            }
        }
        return null;
    }

}
