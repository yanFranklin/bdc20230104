package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;
import cn.gtmap.realestate.common.core.domain.BdcFwfsssDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcFdcqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 从原项目加载数据到房地产权
 */
@Service
public class InitYxmToBdcFdcqServiceImpl extends InitBdcFdcqAbstractService {

    @Override
    public String getVal() {
        return Constants.QLSJLY_YXM;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcFdcqDO bdcFdcqDO=initFromYxm(initServiceQO,BdcFdcqDO.class);
        //读取宗地数据的需求
        DjxxResponseDTO djxxResponseDTO=initServiceQO.getDjxxResponseDTO();
        if (djxxResponseDTO.getDjDcbResponseDTO() != null) {
            initDozerMapper.map(djxxResponseDTO.getDjDcbResponseDTO(), bdcFdcqDO, "djxx_yxm");
        }
        // 通过不动产单元信息为权利数据赋值
        initDozerMapper.map(initServiceQO.getBdcdyDTO(), bdcFdcqDO,"bdcdy_yxm");
        return bdcFdcqDO;
    }

    @Override
    public List<BdcFdcqFdcqxmDO> initFdcqXm(InitServiceQO initServiceQO, String qlid, InitServiceDTO initServiceDTO) {
        BdcQl yBdcQl = null;
        String yQlid;
        // 原项目id不为空时 查询原权利信息
        if (StringUtils.isNotBlank(initServiceQO.getYxmid())) {
            yBdcQl = bdcQllxService.queryQllxDO(initServiceQO.getYxmid());
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
        if (StringUtils.isNotBlank(initServiceQO.getYxmid())) {
            // 查询权利id
            Example example = new Example(BdcFwfsssDO.class);
            example.createCriteria().andEqualTo("xmid", initServiceQO.getYxmid());
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
