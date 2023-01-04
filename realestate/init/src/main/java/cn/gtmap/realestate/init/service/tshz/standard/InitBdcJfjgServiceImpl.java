package cn.gtmap.realestate.init.service.tshz.standard;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
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
 * 解封机关读取查封机关
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcJfjgServiceImpl")
public class InitBdcJfjgServiceImpl implements InitBdcTsHzService {

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private InitServiceQoService initServiceQoService;

    /**
     * 特殊后置处理
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(!initServiceQO.isSfzqlpbsj() && initServiceDTO!=null && initServiceDTO.getBdcCshFwkgSlDO()!=null){
            BdcCshFwkgSlDO bdcCshFwkgSlDO=initServiceDTO.getBdcCshFwkgSlDO();
            //查封类的并且不生成权利的为注销流程，做解封文件处理
            if(CommonConstantUtils.QLLX_CF.equals(bdcCshFwkgSlDO.getQllx()) && StringUtils.isNotBlank(initServiceQO.getYxmid()) && CommonConstantUtils.SF_F_DM.equals(bdcCshFwkgSlDO.getSfscql())){
                BdcQl bdcQl=initServiceQoService.queryYql(initServiceQO.getYxmid(),initServiceQO);
                //查封机关不为空的并且解封机关是空的  将查封机关赋值到解封机关里
                if(bdcQl instanceof BdcCfDO && StringUtils.isNotBlank(((BdcCfDO) bdcQl).getCfjg()) && StringUtils.isBlank(((BdcCfDO) bdcQl).getJfjg())){
                    ((BdcCfDO) bdcQl).setJfjg(((BdcCfDO) bdcQl).getCfjg());
                    entityMapper.updateByPrimaryKeySelective(bdcQl);
                }
            }
        }
        return initServiceDTO;
    }
}
