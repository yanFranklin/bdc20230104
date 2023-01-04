package cn.gtmap.realestate.exchange.core.convert;

import cn.gtmap.realestate.common.core.domain.exchange.BdcDaCxLog;
import cn.gtmap.realestate.common.core.dto.exchange.BdcDaCxLogDTO;
import org.mapstruct.Mapper;

/**
 * 档案查询相关实体转换类
 */
@Mapper(componentModel = "spring")
public interface BdcDacxConvert {

    BdcDaCxLog getBdcDaCxLogByBdcDaCxLogDTO(BdcDaCxLogDTO bdcDaCxLogDTO);

}
