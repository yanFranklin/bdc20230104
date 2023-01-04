package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.register.core.mapper.BdcFdcqFdcqXmMapper;
import cn.gtmap.realestate.register.core.qo.BdcQlQO;
import cn.gtmap.realestate.register.core.service.BdcFdcqFdcqXmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/11
 * @description 房地产权（项目内多幢）项目信息查询服务实现
 */
@Service
public class BdcFdcqFdcqXmServiceImpl implements BdcFdcqFdcqXmService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcFdcqFdcqXmMapper bdcFdcqFdcqXmMapper;

    /**
     * @param qlid 房地产权的权利ID
     * @return List<BdcFdcqFdcqxmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前权利的房地产权项目信息
     */
    @Override
    public List<BdcFdcqFdcqxmDO> listFdcqxm(String qlid) {
        if (StringUtils.isNotBlank(qlid)) {
            Example example = new Example(BdcFdcqFdcqxmDO.class);
            example.setOrderByClause("BDCDYWYBH ASC");
            example.createCriteria().andEqualTo("qlid", qlid);
            return entityMapper.selectByExampleNotNull(example);
        }
        return new ArrayList();
    }

    /**
     * @param bdcQlQO 权利查询对象
     * @return Integer 总数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目内多幢，项目信息总数
     */
    @Override
    public Integer countFdcqFdcqXm(BdcQlQO bdcQlQO) {
        if (null != bdcQlQO) {
            return bdcFdcqFdcqXmMapper.countFdcqFdcqXm(bdcQlQO);
        }
        return 0;
    }
}
