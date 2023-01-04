package cn.gtmap.realestate.register.core.service;

import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;
import cn.gtmap.realestate.register.core.qo.BdcQlQO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/11
 * @description 房地产权（项目内多幢）项目信息查询服务接口
 */
public interface BdcFdcqFdcqXmService {
    /**
     * @param qlid 房地产权的权利ID
     * @return List<BdcFdcqFdcqxmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前权利的房地产权项目信息
     */
    List<BdcFdcqFdcqxmDO> listFdcqxm(String qlid);

    /**
     * @param bdcQlQO 权利查询对象
     * @return Integer 总数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目内多幢，项目信息总数
     */
    Integer countFdcqFdcqXm(BdcQlQO bdcQlQO);
}
