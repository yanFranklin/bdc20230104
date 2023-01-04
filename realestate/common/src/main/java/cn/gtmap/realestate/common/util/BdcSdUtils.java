package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/12/8 22:07
 * @description 不动产锁定方法
 */
public class BdcSdUtils {

    /**
     * 生成锁定原因
     * @param bdcZssdS  不动产证书锁定信息
     * @param bdcDysdS  不动产单元锁定信息
     * @param sdlxZdMap 锁定类型字典Map
     * @return
     */
    public static String sdyyWithList(List<BdcZssdDO> bdcZssdS, List<BdcDysdDO> bdcDysdS, List<Map> sdlxZdMap){
        StringBuilder sdyy = new StringBuilder();
        if (CollectionUtils.isNotEmpty(bdcDysdS)){
            sdyy.append("单元");
            if(Objects.nonNull(bdcDysdS.get(0).getSdlx())){
                String dySdlxName = StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcDysdS.get(0).getSdlx()), sdlxZdMap);
                sdyy.append(StringUtil.isNotBlank(dySdlxName)? dySdlxName : "").append("：");
            }else{
                sdyy.append("裁决锁定：");
            }
            bdcDysdS.forEach(bdcDysdDO -> sdyy.append(StringUtil.isNotBlank(bdcDysdDO.getSdyy())?bdcDysdDO.getSdyy():"").append(" "));
        }
        if(StringUtil.isNotBlank(sdyy.toString())){
            sdyy.append("</br>");
        }
        if (CollectionUtils.isNotEmpty(bdcZssdS)){
            sdyy.append("证书");
            if(Objects.nonNull(bdcZssdS.get(0).getSdlx())){
                String zsSdlxName = StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcZssdS.get(0).getSdlx()), sdlxZdMap);
                sdyy.append(StringUtil.isNotBlank(zsSdlxName)? zsSdlxName : "").append("：");
            }else{
                sdyy.append("冻结锁定：");
            }
            bdcZssdS.forEach(bdcZssdDO -> sdyy.append(StringUtil.isNotBlank(bdcZssdDO.getSdyy())?bdcZssdDO.getSdyy():"").append(" "));
        }
        return sdyy.toString();
    }
}
