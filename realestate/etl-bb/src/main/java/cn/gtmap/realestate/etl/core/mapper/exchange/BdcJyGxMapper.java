package cn.gtmap.realestate.etl.core.mapper.exchange;

import cn.gtmap.realestate.common.core.domain.etl.BdcJyGxDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/24 17:20
 */
public interface BdcJyGxMapper {

    List<BdcJyGxDO> listBdcJyGx(@Param("bdcdyh") String bdcdyh);
}
