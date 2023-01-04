package cn.gtmap.realestate.init.service.tshz.standard;

import cn.gtmap.realestate.common.core.domain.BdcDsQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @program: realestate
 * @description: 抵押权初始化借款人信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-16 10:23
 **/
@Service("bdcDyaqJkrServiceImpl")
public class InitBdcDyaqJkrServiceImpl implements InitBdcTsHzService {

    private static Logger logger = LoggerFactory.getLogger(InitBdcDyaqJkrServiceImpl.class);

    /**
     * 特殊后置处理
     *
     * @param initServiceQO
     * @param initServiceDTO *@return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //抵押人作为借款人，存储到第三权利人
        if (CollectionUtils.isNotEmpty(initServiceQO.getBdcYwrList()) && CommonConstantUtils.QLLX_DYAQ_DM.equals(initServiceDTO.getBdcXm().getQllx())) {
            return dealJkr(initServiceQO, initServiceDTO);
        }
        //预抵押也需要带入
        // 预告业务同步处理
        BdcQl bdcQl = initServiceDTO.getBdcQl();
        if (bdcQl instanceof BdcYgDO) {
            BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
            if (bdcYgDO.getYgdjzl() != null && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())) {
                return dealJkr(initServiceQO, initServiceDTO);
            }
        }
        return initServiceDTO;
    }

    private InitServiceDTO dealJkr(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) {
        for (BdcQlrDO bdcywr : initServiceQO.getBdcYwrList()) {
            BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
            BeanUtils.copyProperties(bdcywr, bdcDsQlrDO);
            //第三权利人类别，借款人
            bdcDsQlrDO.setQlrid(UUIDGenerator.generate16());
            bdcDsQlrDO.setQlrlb("5");
            initServiceDTO.getBdcDsQlrDOList().add(bdcDsQlrDO);
        }
        return initServiceDTO;
    }
}
