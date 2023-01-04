package cn.gtmap.realestate.exchange.core.mapper.server;

import java.util.List;
import java.util.Map;

/**
 * 蚌埠 交易共享表查询
 *
 * @author shaoliyao
 */
public interface BdcJyGxMapper {

    /**
     * sly
     * 根据参数bdcdyh查询bdc_jy_gx表
     *
     * @param bdcdyh
     * @return
     */
    List<Map> queryBdcJyGxList(String bdcdyh);


}
