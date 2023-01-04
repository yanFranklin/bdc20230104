package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.register.core.qo.BdcQlQO;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/12
 * @description 房地产权（项目内多幢）项目信息
 */
public interface BdcFdcqFdcqXmMapper {
    /**
     * @param bdcQlQO 权利查询对象
     * @return Integer 总数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目内多幢，项目信息总数
     */
    Integer countFdcqFdcqXm(BdcQlQO bdcQlQO);
}
