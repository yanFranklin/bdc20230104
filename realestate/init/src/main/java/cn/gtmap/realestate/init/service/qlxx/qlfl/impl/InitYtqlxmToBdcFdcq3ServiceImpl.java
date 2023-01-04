package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;


import cn.gtmap.realestate.common.core.domain.BdcFdcq3DO;
import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcFdcq3AbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/8
 * @description 从原同权利到共有部分
 */
@Service
public class InitYtqlxmToBdcFdcq3ServiceImpl extends InitBdcFdcq3AbstractService {

    @Autowired
    private InitServiceQoService initServiceQoService;


    @Override
    public String getVal() {
        return CommonConstantUtils.QLSJLY_YTQL;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        return initFromYxm(initServiceQO, BdcFdcq3DO.class,initServiceQoService.getYtqlXmid(initServiceQO));
    }

    @Override
    public List<BdcFdcq3GyxxDO> initFdcq3Gyxx(InitServiceQO initServiceQO, String qlid) {
        List<BdcFdcq3GyxxDO> bdcFdcq3GyxxList = new ArrayList<>();
        // 若权利id不为空时
        String ytqlxmid =initServiceQoService.getYtqlXmid(initServiceQO);
        if (StringUtils.isNotBlank(qlid) && StringUtils.isNotBlank(ytqlxmid) && initServiceQO.getBdcXm() != null) {
            // 查询权利id
            Example example = new Example(BdcFdcq3GyxxDO.class);
            example.createCriteria().andEqualTo("xmid", ytqlxmid);
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
