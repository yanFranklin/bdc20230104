package cn.gtmap.realestate.init.service.tshz.hefei;

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
 * 解封文件默认赋值
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcJfwjServiceImpl_hefei")
public class InitBdcJfwjServiceImpl implements InitBdcTsHzService {

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
                if(bdcQl instanceof BdcCfDO){
                    ((BdcCfDO) bdcQl).setJfwj("协助执行通知书");
                    entityMapper.updateByPrimaryKeySelective(bdcQl);
                }
            }
        }
        return initServiceDTO;
    }
}
