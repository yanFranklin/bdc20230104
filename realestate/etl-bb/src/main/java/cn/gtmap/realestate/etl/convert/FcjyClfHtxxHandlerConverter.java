package cn.gtmap.realestate.etl.convert;

import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaResponseDTo;
import cn.gtmap.realestate.etl.core.domian.SpfHtbaDo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FcjyClfHtxxHandlerConverter {

    EtlSpfHtbaResponseDTo getEtlSpfHtbaResponseDToBySpfHtbaDo(SpfHtbaDo spfHtbaDo);

    List<EtlSpfHtbaResponseDTo> getEtlSpfHtbaResponseDToListBySpfHtbaDo(List<SpfHtbaDo> spfHtbaDoList);

}
