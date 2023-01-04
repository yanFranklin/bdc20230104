package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXtZsfhDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXtZsfhDTO;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/5
 * @description 废号SQL操作Mapper接口
 */
public interface BdcXtFhMapper {

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsfhDTO 废号查询实体
     * @return {BdcXtZsfhDO} 废号信息
     * @description 获取指定区域当前年度可用废号
     */
    List<BdcXtZsfhDO> queryAvailableBdcXtFh(BdcXtZsfhDTO bdcXtZsfhDTO);

}
