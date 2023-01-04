package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcFdcqAbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/8
 * @description 从原房地产权项目加载数据到房地产权
 */
@Service
public class InitYtqlxmToBdcFdcqServiceImpl extends InitBdcFdcqAbstractService {

    @Autowired
    private InitServiceQoService initServiceQoService;


    @Override
    public String getVal() {
        return CommonConstantUtils.QLSJLY_YTQL;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcFdcqDO bdcFdcqDO=initFromYxm(initServiceQO,BdcFdcqDO.class,initServiceQoService.getYtqlXmid(initServiceQO));
        return bdcFdcqDO;
    }

    @Override
    public List<BdcFdcqFdcqxmDO> initFdcqXm(InitServiceQO initServiceQO, String qlid, InitServiceDTO initServiceDTO) {
        String ytqlxmid =initServiceQoService.getYtqlXmid(initServiceQO);
        BdcQl yBdcQl = null;
        String yQlid;
        // 原项目id不为空时 查询原权利信息
        if (StringUtils.isNotBlank(ytqlxmid)) {
            yBdcQl = bdcQllxService.queryQllxDO(ytqlxmid);
        }
        if (yBdcQl != null) {
            yQlid = yBdcQl.getQlid();
            // 若原权利为房地产权多幢时
            if (yBdcQl instanceof BdcFdcqDO) {
                Example example = new Example(BdcFdcqFdcqxmDO.class);
                example.createCriteria().andEqualTo("qlid", yQlid);
                List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmList = entityMapper.selectByExample(example);
                if(CollectionUtils.isNotEmpty(bdcFdcqFdcqxmList)){
                    for (int i = 0; i < bdcFdcqFdcqxmList.size(); i++) {
                        bdcFdcqFdcqxmList.get(i).setQlid(qlid);
                        bdcFdcqFdcqxmList.get(i).setFzid(UUIDGenerator.generate16());
                    }
                }
                return bdcFdcqFdcqxmList;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<BdcFwfsssDO> initFdcqFsss(InitServiceQO initServiceQO) {
        List<BdcFwfsssDO> bdcFwfsssList = new ArrayList<>();
        // 若权利id不为空时
        String ytqlxmid =initServiceQoService.getYtqlXmid(initServiceQO);
        if (StringUtils.isNotBlank(ytqlxmid)) {
            // 查询权利id
            Example example = new Example(BdcFwfsssDO.class);
            example.createCriteria().andEqualTo("xmid", ytqlxmid);
            bdcFwfsssList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcFwfsssList)) {
                for (BdcFwfsssDO bdcFwfsssDO: bdcFwfsssList) {
                    bdcFwfsssDO.setFwfsssid(UUIDGenerator.generate16());
                    bdcFwfsssDO.setXmid(initServiceQO.getXmid());
                }
            }
        }
        return bdcFwfsssList;

    }
}
