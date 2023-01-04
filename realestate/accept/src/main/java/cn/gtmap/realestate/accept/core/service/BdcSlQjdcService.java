package cn.gtmap.realestate.accept.core.service;


import cn.gtmap.realestate.common.core.domain.accept.BdcSlQjdcsqDO;

import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 权籍调查Service服务
 * @date : 2021/8/5 17:26
 */
public interface BdcSlQjdcService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权籍调查数据
     * @date : 2021/8/5 13:40
     */
    List<BdcSlQjdcsqDO> listSlQjdc(String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除权籍调查数据
     * @date : 2021/8/5 13:41
     */
    int deleteSlQjdc(String gzlslid);

    /**
     * @param bdcSlQjdcsqDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增权籍调查数据
     * @date : 2021/8/5 13:41
     */
    BdcSlQjdcsqDO saveSlQjdc(BdcSlQjdcsqDO bdcSlQjdcsqDO);
}
