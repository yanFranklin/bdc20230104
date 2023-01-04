package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 宗地宗海用途特殊处理
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2020/02/21.
 * @description
 */
@Service("bdcZdzhytServiceImpl_nantong")
public class InitBdcZdzhytServiceImpl implements InitBdcTsHzService {
    @Autowired
    private InitServiceQoService initServiceQoService;

    /**
     * 宗地宗海用途特殊处理
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(!initServiceQO.isSfzqlpbsj() &&  initServiceDTO!=null && initServiceDTO.getBdcCshFwkgSlDO()!=null){
            BdcCshFwkgSlDO bdcCshFwkgSlDO=initServiceDTO.getBdcCshFwkgSlDO();
            //权利数据来源是2,1的做宗地用途处理
            if(StringUtils.equals(bdcCshFwkgSlDO.getQlsjly(),"2,1")){
                if(StringUtils.isNotBlank(initServiceQO.getYxmid())){
                    BdcXmDO ybdcXm=initServiceQoService.queryYbdcxm(initServiceQO.getYxmid(),initServiceQO);
                    if(ybdcXm!=null && StringUtils.isNotBlank(ybdcXm.getZdzhyt())){
                        BdcXmDO bdcXmDO=initServiceDTO.getBdcXm();
                        bdcXmDO.setZdzhyt(ybdcXm.getZdzhyt());
                        bdcXmDO.setZdzhyt2(ybdcXm.getZdzhyt2());
                        bdcXmDO.setZdzhyt3(ybdcXm.getZdzhyt3());
                    }
                }
            }
        }
        return initServiceDTO;
    }
}
