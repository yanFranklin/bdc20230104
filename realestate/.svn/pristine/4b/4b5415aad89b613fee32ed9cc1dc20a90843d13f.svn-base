package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.exchange.core.domain.ViewJyQlrDO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ycfw.YcfwCxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ychsbyysfwbm.response.YchsByYsfwbmResponseDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by zdq on 2021/03/24.
 * 需要单独查询常州视图的方法
 */
public interface ViewMapper {

    /**
     * zdq 查询ViewJyQlr by Cqzh
     *
     * @return
     */
    List<ViewJyQlrDO> getViewJyQlrByCqzh(Map map);

    /**
     * zdq 查询ViewJyQlr by zl
     *
     * @return
     */
    List<ViewJyQlrDO> getViewJyQlrByZl(Map map);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [queryParam]
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.ychsbyysfwbm.response.YchsByYsfwbmResponseDTO>
     * @description 查询DvYg
     */
    YchsByYsfwbmResponseDTO queryViewDvYgDO(YcfwCxRequestDTO queryParam);
}
