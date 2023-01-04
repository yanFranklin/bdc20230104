package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.domain.building.CbzdCbfDO;
import cn.gtmap.realestate.common.core.domain.building.HCbzdCbfDO;
import cn.gtmap.realestate.common.core.domain.building.NydJtcyDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description 承包宗地mapper
 */
public interface CbzdMapper {
    /**
     * 根据不动产单元号查询承包方家庭
     * @auther chenchunxue
     * @param bdcdyh
     * @return
     */
    List<NydJtcyDO> listCbfJtcy(String bdcdyh);

    /**
     * 查询承包方
     * @auther chenchunxue
     * @param param
     * @return
     */
    List<CbzdCbfDO> listCbf(Map<String,Object> param);
    /**
     * 查询承包方备份
     * @auther chenchunxue
     * @param param
     * @return
     */
    List<HCbzdCbfDO> listHCbf(Map<String,Object> param);
}
