package cn.gtmap.realestate.supervise.core.mapper;

import cn.gtmap.realestate.supervise.core.qo.LfYzhQO;
import cn.gtmap.realestate.supervise.core.dto.LfYzhtjDTO;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/26
 * @description 廉防5：证书使用监管Mapper
 */
public interface LfZssyjgMapper {
    /**
     * 废弃印制号统计（统计哪些人废弃了多少印制号）
     * @param lfYzhQO 印制号查询条件
     * @return {List} 统计信息
     */
    List<LfYzhtjDTO> fqyzhtj(LfYzhQO lfYzhQO);
}
