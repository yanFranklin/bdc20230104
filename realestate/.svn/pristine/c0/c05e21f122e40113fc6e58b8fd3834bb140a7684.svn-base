package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFwfsssDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.core.service.BdcFwFsssService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 附属设施服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/17.
 * @description
 */
@Service
public class BdcFwFsssServiceImpl implements BdcFwFsssService {

    @Autowired
    EntityMapper entityMapper;

    /**
     * 通过项目ID获取房地产附属设施信息
     * @param xmid 项目id
     * @return List<BdcFwfsssDOss>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcFwfsssDO> queryFwFsssListByXmid(String xmid) {
        if(StringUtils.isBlank(xmid)){
            return  Collections.emptyList();
        }
        Example example = new Example(BdcFwfsssDO.class);
        example.createCriteria().andEqualTo("xmid", xmid);
        return entityMapper.selectByExample(example);
    }
}
