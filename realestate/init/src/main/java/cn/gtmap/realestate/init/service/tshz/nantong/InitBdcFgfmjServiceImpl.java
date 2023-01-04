package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 房改房面积处理
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/29.
 * @description
 */
@Service("bdcFgfmjServiceImpl_nantong")
public class InitBdcFgfmjServiceImpl implements InitBdcTsHzService {

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
        //属于房改房登记小类的做处理
        if(initServiceDTO!=null && initServiceDTO.getBdcXm()!=null && Arrays.asList("2000410").contains(initServiceDTO.getBdcXm().getDjxl())){
            if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO){
                //赋值实测建筑面积
                Double scjzmj=initServiceQO.getBdcdyResponseDTO().getScjzmj();
                initServiceDTO.getBdcXm().setDzwmj(scjzmj);
                ((BdcFdcqDO) initServiceDTO.getBdcQl()).setJzmj(scjzmj);
            }
        }
        return initServiceDTO;
    }
}
