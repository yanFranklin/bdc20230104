package cn.gtmap.realestate.init.service.tshz.yancheng;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCfjfDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlQl;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 保存解封信息
 * <p>
 * jwfh,jfjg,jfyy,jfwj
 * </p>
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 下午4:00 2020/12/30
 */
@Service("InitBdcJfxxServiceImpl")
public class InitBdcJfxxServiceImpl implements InitBdcTsHzService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private InitServiceQoService initServiceQoService;

    /**
     * 特殊后置处理
     *
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if (!initServiceQO.isSfzqlpbsj() && initServiceDTO != null && initServiceDTO.getBdcCshFwkgSlDO() != null) {
            BdcCshFwkgSlDO bdcCshFwkgSlDO = initServiceDTO.getBdcCshFwkgSlDO();
            // 判断是否是解封流程
            if (CommonConstantUtils.QLLX_CF.equals(bdcCshFwkgSlDO.getQllx()) && StringUtils.isNotBlank(initServiceQO.getYxmid())
                    && CommonConstantUtils.SF_F_DM.equals(bdcCshFwkgSlDO.getSfscql())) {
                BdcQl bdcQl = initServiceQoService.queryYql(initServiceQO.getYxmid(), initServiceQO);
                BdcSlQl bdcSlQl = initServiceQO.getBdcSlQl();
                // 保存解封信息
                if (bdcQl instanceof BdcCfDO && bdcSlQl instanceof BdcSlCfjfDO) {
                    BdcSlCfjfDO bdcSlCfjfDO = (BdcSlCfjfDO) bdcSlQl;
                    ((BdcCfDO) bdcQl).setJfwj(bdcSlCfjfDO.getJfwj());
                    ((BdcCfDO) bdcQl).setJfwh(bdcSlCfjfDO.getJfwh());
                    ((BdcCfDO) bdcQl).setJfjg(bdcSlCfjfDO.getJfjg());
                    ((BdcCfDO) bdcQl).setJfyy(bdcSlCfjfDO.getJfyy());
                    entityMapper.updateByPrimaryKeySelective(bdcQl);
                }
            }
        }
        return initServiceDTO;
    }
}
