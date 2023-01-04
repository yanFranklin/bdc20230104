package cn.gtmap.realestate.exchange.core.convert;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzCfxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzDyxxResponseData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface ZsyzConvert {

    @Mappings({
            @Mapping(target = "bdcdybh", source = "bdcdywybh"),
            @Mapping(target = "dykssj", source = "zwlxqssj",dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "dyjssj", source = "zwlxjssj",dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "djsj", source = "djsj",dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "dyje", source = "bdbzzqse"),
            @Mapping(target = "zgzqe", source = "zgzqe")
    })
    ZsyzDyxxResponseData getZsyzDyxxResponseDataByBdcDyaqDO(BdcDyaqDO bdcDyaqDO);

    @Mappings({
            @Mapping(target = "bdcdybh", source = "bdcdywybh"),
            @Mapping(target = "djsj", source = "djsj",dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "cfjssj", source = "cfjssj",dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "cfkssj", source = "cfqssj",dateFormat = "yyyy-MM-dd HH:mm:ss")
    })
    ZsyzCfxxResponseData getZsyzCfxxResponseDataByBdcCfDO(BdcCfDO bdcCfDO);

    default List<ZsyzDyxxResponseData> getZsyzDyxxResponseDataListByBdcDyaqDOList(List<BdcDyaqDO> bdcDyaqDOList, BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        if ( bdcDyaqDOList == null ) {
            return null;
        }

        List<ZsyzDyxxResponseData> list = new ArrayList<ZsyzDyxxResponseData>( bdcDyaqDOList.size() );
        for ( BdcDyaqDO bdcDyaqDO : bdcDyaqDOList ) {
            ZsyzDyxxResponseData temp = getZsyzDyxxResponseDataByBdcDyaqDO(bdcDyaqDO);
            if (temp.getQszt() !=null && CommonConstantUtils.QSZT_VALID.equals(bdcDyaqDO.getQszt())){
                if (bdcZdDsfzdgxDO!=null && StringUtils.isNotBlank(bdcZdDsfzdgxDO.getDsfzdz())){
                    temp.setQszt(bdcZdDsfzdgxDO.getDsfzdz());
                }else {
                    temp.setQszt("现势");
                }
                list.add( temp );
            }
        }

        return list;
    }

    default List<ZsyzCfxxResponseData> getZsyzCfxxResponseDataListByBdcCfDOList(List<BdcCfDO> bdcCfDOList, BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        if ( bdcCfDOList == null ) {
            return null;
        }

        List<ZsyzCfxxResponseData> list = new ArrayList<ZsyzCfxxResponseData>( bdcCfDOList.size() );
        for ( BdcCfDO bdcCfDO : bdcCfDOList ) {
            ZsyzCfxxResponseData temp = getZsyzCfxxResponseDataByBdcCfDO(bdcCfDO);
            if (temp.getQszt() !=null && CommonConstantUtils.QSZT_VALID.equals(bdcCfDO.getQszt())){
                if (bdcZdDsfzdgxDO!=null && StringUtils.isNotBlank(bdcZdDsfzdgxDO.getDsfzdz())){
                    temp.setQszt(bdcZdDsfzdgxDO.getDsfzdz());
                }else {
                    temp.setQszt("现势");
                }
                list.add( temp );
            }
        }

        return list;
    }

}
