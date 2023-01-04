package cn.gtmap.realestate.init.service.tshz.hefei;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.springframework.stereotype.Service;

/**
 * 交易价格清空
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcJyjgServiceImpl_hefei")
public class InitBdcJyjgServiceImpl implements InitBdcTsHzService {
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
        //产权的交易价格清空
        if(!initServiceQO.isSfzqlpbsj() &&  initServiceDTO!=null && initServiceDTO.getBdcQl() instanceof BdcFdcqDO){
            ((BdcFdcqDO) initServiceDTO.getBdcQl()).setJyjg(null);
            //获取受理传递的交易金额
            if(initServiceQO.getDsfSlxxDTO()!=null && initServiceQO.getDsfSlxxDTO().getJyje()!=null){
                ((BdcFdcqDO) initServiceDTO.getBdcQl()).setJyjg(initServiceQO.getDsfSlxxDTO().getJyje());
            }
        }
        return initServiceDTO;
    }
}
