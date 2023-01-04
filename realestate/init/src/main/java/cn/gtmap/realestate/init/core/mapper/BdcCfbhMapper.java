package cn.gtmap.realestate.init.core.mapper;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/30
 * @description 查封编号
 */
public interface BdcCfbhMapper {

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取最大查封编号
      */
    Integer getMaxCfbh();
}
