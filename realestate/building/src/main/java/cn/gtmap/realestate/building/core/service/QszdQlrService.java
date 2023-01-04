package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.HQszdQlrDO;
import cn.gtmap.realestate.common.core.domain.building.QszdQlrDO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-12
 * @description
 */
public interface QszdQlrService {

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.QszdQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询权属宗地权利人信息
     */
    List<QszdQlrDO> listQszdQlrByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.QszdQlrDO>
     * @description 根据地籍号查询权属宗地权利人信息
     */
    List<QszdQlrDO> listQszdQlrByDjh(String djh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qszdDjdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.QszdQlrDO>
     * @description 根据DJDCBINDEX 查询 权属宗地权利人
     */
    List<QszdQlrDO> listQszdQlrByQszdDjdcbIndex(String qszdDjdcbIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qszdDjdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.HQszdQlrDO>
     * @description  根据DJDCBINDEX 查询 备份权属宗地权利人
     */
    List<HQszdQlrDO> listHQszdQlrByQszdDjdcbIndex(String qszdDjdcbIndex);
}
