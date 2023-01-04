package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 南通交易价格清空
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcJyjgServiceImpl_nantong")
public class InitBdcJyjgServiceImpl implements InitBdcTsHzService {

    @Value("#{'${nttscl.jyjg:}'.split(',')}")
    private List<String> jyjg;

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
            if(jyjg.contains(initServiceDTO.getBdcXm().getGzldyid())){
                ((BdcFdcqDO) initServiceDTO.getBdcQl()).setJyjg(null);
            }
        }
        return initServiceDTO;
    }
}
