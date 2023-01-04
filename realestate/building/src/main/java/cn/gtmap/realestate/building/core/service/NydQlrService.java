package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.HNydQlrDO;
import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-14
 * @description 农用地权利人服务
 */
public interface NydQlrService {

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询农用地权利人信息
     */
    List<NydQlrDO> listNydQlrByBdcdyh(String bdcdyh);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @description 根据DJH查询林权相关权利人（包括林地使用权人、林木使用权人、林木所有权人）
     */
    List<NydQlrDO> listLqQlrByDjh(String djh);
    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询农用地权利人信息
     */
    List<NydQlrDO> listNydQlrByDjh(String djh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @description  用外键查询农用地权利人信息
     */
    List<NydQlrDO> listNydQlrByDjdcbIndex(String djdcbIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.HNydQlrDO>
     * @description 查询备份农用地权利人
     */
    List<HNydQlrDO> listHNydQlrByDjh(String djh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.HNydQlrDO>
     * @description 外键查询权利人
     */
    List<HNydQlrDO> listHNydQlrByDcbIndex(String dcbIndex);
}
