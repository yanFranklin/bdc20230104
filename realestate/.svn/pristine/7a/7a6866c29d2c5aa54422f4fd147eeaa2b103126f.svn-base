package cn.gtmap.realestate.exchange.core.convert;

import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData;
import cn.gtmap.realestate.exchange.core.dto.changzhou.zzdzj.ChangzhouDzzzResponseData;
import cn.gtmap.realestate.exchange.core.dto.changzhou.zzdzj.ChangzhouDzzzplxzResponseData;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ZzdzjConvert {

    ChangzhouDzzzResponseData getChangzhouDzzzResponseDataByDzzzResponseData(DzzzResponseData dzzzResponseData);

    List<ChangzhouDzzzResponseData> getChangzhouDzzzResponseDataListByDzzzResponseDataList(List<DzzzResponseData> dzzzResponseDataList);

    ChangzhouDzzzplxzResponseData getChangzhouDzzzplResponseDataByDzzzResponseData(DzzzResponseData dzzzResponseData);
}
