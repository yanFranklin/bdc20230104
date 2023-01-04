package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;
import cn.gtmap.realestate.common.core.domain.BdcFwfsssDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcFdcqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 从楼盘表加载数据到房地产权
 */
@Service
public class InitLpbToBdcFdcqServiceImpl extends InitBdcFdcqAbstractService {
    private static Logger logger = LoggerFactory.getLogger(InitLpbToBdcFdcqServiceImpl.class);

    @Override
    public String getVal() {
        return Constants.QLSJLY_LPB;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        return initFwFromLpb(initServiceQO, BdcFdcqDO.class);
    }

    @Override
    public List<BdcFdcqFdcqxmDO> initFdcqXm(InitServiceQO initServiceQO, String qlid, InitServiceDTO initServiceDTO) {
        List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmList = new ArrayList<>();
        BigDecimal zmj = new BigDecimal("0.00");
        if (CollectionUtils.isNotEmpty(initServiceQO.getBdcdyResponseDTO().getLjzList())) {
            logger.info("逻辑幢信息：{}", JSON.toJSONString(initServiceQO.getBdcdyResponseDTO().getLjzList()));
            for (FwLjzDO fwLjz : initServiceQO.getBdcdyResponseDTO().getLjzList()) {
                BdcFdcqFdcqxmDO bdcFdcqFdcqxm = new BdcFdcqFdcqxmDO();
                initDozerMapper.map(initServiceQO.getBdcdyResponseDTO(), bdcFdcqFdcqxm);
                initDozerMapper.map(fwLjz, bdcFdcqFdcqxm);
                if (fwLjz.getScjzmj() != null) {
                    zmj = zmj.add(new BigDecimal(fwLjz.getScjzmj()));
                }
                bdcFdcqFdcqxm.setFzid(UUIDGenerator.generate16());
                bdcFdcqFdcqxm.setQlid(qlid);
                bdcFdcqFdcqxmList.add(bdcFdcqFdcqxm);
            }
        }

        // bug 45514 当pzjzmj为空时，再取scjzmj之和
        if (initServiceDTO.getBdcXm() != null) {
            Double dzwmj = initServiceDTO.getBdcXm().getDzwmj();
            if (Objects.isNull(dzwmj) || "0.0".equalsIgnoreCase(dzwmj.toString())) {
                initServiceDTO.getBdcXm().setDzwmj(zmj.doubleValue());
            }
        }
        if (initServiceDTO.getBdcQl() instanceof BdcFdcqDO) {
            Double jzmj = ((BdcFdcqDO) initServiceDTO.getBdcQl()).getJzmj();
            if (Objects.isNull(jzmj) || "0.0".equalsIgnoreCase(jzmj.toString())) {
                ((BdcFdcqDO) initServiceDTO.getBdcQl()).setJzmj(zmj.doubleValue());
            }
        }

        return bdcFdcqFdcqxmList;
    }

    @Override
    public List<BdcFwfsssDO> initFdcqFsss(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        List<BdcFwfsssDO> bdcFwfsssList = new ArrayList<>();
        if (initServiceQO.getBdcdyResponseDTO() != null
                && CollectionUtils.isNotEmpty(initServiceQO.getBdcdyResponseDTO().getFwZhsList())) {
            for (FwZhsDO fwZhs : initServiceQO.getBdcdyResponseDTO().getFwZhsList()) {
                BdcFwfsssDO bdcFwfsss = initFromLpbXm(initServiceQO, BdcFwfsssDO.class, null);
                // 通过户室信息赋值
                initDozerMapper.map(initServiceQO.getBdcdyResponseDTO(), bdcFwfsss);
                // 通过子户室信息赋值
                initDozerMapper.map(fwZhs, bdcFwfsss);
                bdcFwfsssList.add(bdcFwfsss);
            }
        }

        return bdcFwfsssList;
    }
}
