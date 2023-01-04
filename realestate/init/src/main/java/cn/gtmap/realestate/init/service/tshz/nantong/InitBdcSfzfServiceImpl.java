package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @program: realestate
 * @description: 初始化设置是否主房
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-14 14:00
 **/
@Service("bdcSfzfServiceImpl")
public class InitBdcSfzfServiceImpl implements InitBdcTsHzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitBdcSfzfServiceImpl.class);

    @Autowired
    BdcSlFeignService bdcSlFeignService;

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
        if (Objects.nonNull(initServiceDTO.getBdcCshFwkgSlDO()) && Objects.isNull(initServiceDTO.getBdcCshFwkgSlDO().getSfzf())) {
            //如果是否主房值为空，根据accept配置的用途判断是否主房
            if (Objects.nonNull(initServiceDTO.getBdcXm().getDzwyt())) {
                boolean sfzf = bdcSlFeignService.checkSfzf(String.valueOf(initServiceDTO.getBdcXm().getDzwyt()));
                if (sfzf) {
                    initServiceDTO.getBdcCshFwkgSlDO().setSfzf(CommonConstantUtils.SF_S_DM);
                } else {
                    initServiceDTO.getBdcCshFwkgSlDO().setSfzf(CommonConstantUtils.SF_F_DM);
                }
            } else {
                LOGGER.warn("当前项目{}定着物用途为空，默认为主房", initServiceDTO.getBdcXm().getXmid());
                initServiceDTO.getBdcCshFwkgSlDO().setSfzf(CommonConstantUtils.SF_S_DM);
            }
        }
        return initServiceDTO;
    }
}
