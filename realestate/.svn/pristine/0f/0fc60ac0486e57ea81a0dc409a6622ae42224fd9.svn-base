package cn.gtmap.realestate.exchange.core.convert;

import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkCsDO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiParamDTO;
import cn.gtmap.realestate.common.core.enums.OpenApiParamConstansEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口管理相关实体转换类
 */
@Mapper(componentModel = "spring")
public interface BdcOpenApiConvert {

    @Mappings({
            @Mapping(source = "csid", target = "id"),
            @Mapping(source = "csfid", target = "parentId"),
            @Mapping(source = "csm", target = "fieldName"),
            @Mapping(source = "cszdlx", target = "fieldType"),
            @Mapping(source = "cssm", target = "fieldRemark"),
            @Mapping(source = "cslx", target = "paramType"),
            @Mapping(source = "csmrz", target = "defaultValue"),
            @Mapping(source = "cscj", target = "level"),
            @Mapping(source = "xgr", target = "updatedBy"),
            @Mapping(source = "cjr", target = "createdBy"),
            @Mapping(source = "sfbt", target = "required")
    })
    BdcOpenApiParamDTO getBdcOpenApiParamDTOByBdcDwJkCsDO(BdcDwJkCsDO bdcDwJkCsDO);

    //{"isObject":"1","isAloneSql":"1","isList":"1","jkid":"2215c6c61b494d009680bf1933b6344d","sql":"select zjh,qlrmc from bdc_qlr where xmid = #{xmid}"}
    default List<BdcOpenApiParamDTO> getBdcOpenApiParamDTOListByBdcDwJkCsDOList(List<BdcDwJkCsDO> bdcDwJkCsDOList) {
        if ( bdcDwJkCsDOList == null ) {
            return null;
        }

        List<BdcOpenApiParamDTO> list = new ArrayList<>( bdcDwJkCsDOList.size() );
        for ( BdcDwJkCsDO bdcDwJkCsDO : bdcDwJkCsDOList ) {
            BdcOpenApiParamDTO bdcOpenApiParamDTO = getBdcOpenApiParamDTOByBdcDwJkCsDO(bdcDwJkCsDO);
            if (StringUtils.isNotBlank(bdcDwJkCsDO.getJkcsext())){
                JSONObject jsonObject = JSON.parseObject(bdcDwJkCsDO.getJkcsext());
                if (StringUtils.isNotBlank(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()))){
                    bdcOpenApiParamDTO.setChildParamId(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()));
                }
                if (StringUtils.isNotBlank(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_SQL.getKey()))){
                    bdcOpenApiParamDTO.setSql(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_SQL.getKey()));
                }
                if (StringUtils.isNotBlank(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey())) && OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getValue().equals(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey()))){
                    bdcOpenApiParamDTO.setIsListFlag("on");
                }
                if (StringUtils.isNotBlank(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_NO.getKey())) && OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_NO.getValue().equals(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey()))){
                    bdcOpenApiParamDTO.setIsListFlag("off");
                }
            }
            list.add( bdcOpenApiParamDTO );
        }

        return list;
    }

}
