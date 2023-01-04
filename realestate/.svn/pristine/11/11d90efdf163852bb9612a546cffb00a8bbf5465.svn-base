package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3DO;
import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcFdcq3AbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/16
 * @description 从原项目加载数据到共有部分
 */
@Service
public class InitYxmToBdcFdcq3ServiceImpl extends InitBdcFdcq3AbstractService {
    @Override
    public String getVal() {
        return Constants.QLSJLY_YXM;
    }

    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        return initFromYxm(initServiceQO,BdcFdcq3DO.class);
    }

    @Override
    public List<BdcFdcq3GyxxDO> initFdcq3Gyxx(InitServiceQO initServiceQO, String qlid) {
        List<BdcFdcq3GyxxDO> bdcFdcq3GyxxList = new ArrayList<>();
        // 若权利id不为空时
        if (StringUtils.isNotBlank(qlid) && StringUtils.isNotBlank(initServiceQO.getYxmid()) && initServiceQO.getBdcXm() != null) {
            // 查询权利id
            Example example = new Example(BdcFdcq3GyxxDO.class);
            example.createCriteria().andEqualTo("xmid", initServiceQO.getYxmid());
            bdcFdcq3GyxxList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcFdcq3GyxxList)) {
                for (int i = 0; i < bdcFdcq3GyxxList.size(); i++) {
                    bdcFdcq3GyxxList.get(i).setQlid(qlid);
                    initDozerMapper.map(initServiceQO.getBdcXm(), bdcFdcq3GyxxList.get(i));
                }
            }
        }
        return bdcFdcq3GyxxList;
    }
}
