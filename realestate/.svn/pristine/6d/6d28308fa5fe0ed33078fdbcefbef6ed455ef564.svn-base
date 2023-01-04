package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.HNydDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydJtcyDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description 农用地调查表
 */
public interface NydService {
    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydJtcyDo>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询农用地家庭成员
     */
    List<NydJtcyDO> listNydJtcyByBdcdyh(String bdcdyh);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询农用地宗地
     */
    NydDjdcbDO queryNydDjdcbByBdcdyh(String bdcdyh);

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.NydDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH 查询NYD
     */
    NydDjdcbDO queryNydDjdcbByDjh(String djh);

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.NydDjdcbResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询农用地信息
     */
    Page<Map> listNydByPage(Pageable pageable, Map map);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.HNydDjdcbDO
     * @description 查询备份表
     */
    HNydDjdcbDO queryHNydDjdcbByDjh(String djh);
}