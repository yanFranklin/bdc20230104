package cn.gtmap.realestate.etl.convert;

import cn.gtmap.realestate.etl.core.domian.BdcZsYzhLsDO;
import cn.gtmap.realestate.etl.core.dto.BdcZsYzhLsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BdcZsYzhConverter {

    @Mappings({
            @Mapping(target = "zh", source = "bdcqzh"),
    })
    BdcZsYzhLsDO getBdcZsYzhLsDOByBdcZsYzhLsDTO(BdcZsYzhLsDTO bdcZsYzhLsDTO);

}
