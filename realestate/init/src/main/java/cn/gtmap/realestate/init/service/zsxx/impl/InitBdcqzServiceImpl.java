package cn.gtmap.realestate.init.service.zsxx.impl;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.zsxx.InitBdcZslxAbstractService;
import cn.gtmap.realestate.init.service.zsxx.InitBdcqzAbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
@Service
public class InitBdcqzServiceImpl extends InitBdcqzAbstractService {
    @Override
    public String getVal() {
        return CommonConstantUtils.SF_S_DM.toString();
    }

    @Override
    public List<BdcZsDO> initZsxx(InitServiceQO initServiceQO) throws Exception {
        InitServiceDTO initServiceDTO = new InitServiceDTO();
        //要生成证书，没有配置证书类型的，通过权利判断生成证书还是证明
        if (initServiceQO.getBdcCshFwkgSl().getZszl() == null && initServiceQO.getBdcCshFwkgSl().getQllx() != null) {
            if (ArrayUtils.contains(CommonConstantUtils.BDC_ZM_QLLX, initServiceQO.getBdcCshFwkgSl().getQllx())) {
                initServiceQO.getBdcCshFwkgSl().setZszl(CommonConstantUtils.ZSLX_ZM);
            } else if (ArrayUtils.contains(CommonConstantUtils.BDC_ZS_QLLX, initServiceQO.getBdcCshFwkgSl().getQllx())) {
                initServiceQO.getBdcCshFwkgSl().setZszl(CommonConstantUtils.ZSLX_ZS);
            }
        }
        List<InitService> list = initBeanFactory.getTrafficMode(initServiceQO.getBdcCshFwkgSl(), InitBdcZslxAbstractService.class);
        if (CollectionUtils.isNotEmpty(list)) {
            for (InitService service : list) {
                service.init(initServiceQO, initServiceDTO);
            }
        }
        return initServiceDTO.getBdcZsList();
    }
}
