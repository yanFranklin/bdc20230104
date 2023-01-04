package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.springframework.stereotype.Service;

/**
 * 房地产权表 tdsyqmj 通过 fttdmj + dytdmj 计算取得
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/29.
 * @description
 */
@Service("InitBdcTdsyqmjServiceImpl_nantong")
public class InitBdcTdsyqmjServiceImpl implements InitBdcTsHzService {

    /**
     * 房地产权表 tdsyqmj 通过 fttdmj + dytdmj 计算取得
     *
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if (initServiceDTO != null && initServiceDTO.getBdcQl() instanceof BdcFdcqDO) {
            BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) initServiceDTO.getBdcQl();
            Double tdsyqmj = 0.0;
            if (bdcFdcqDO.getDytdmj() != null) {
                tdsyqmj += bdcFdcqDO.getDytdmj();
            }
            if (bdcFdcqDO.getFttdmj() != null) {
                tdsyqmj += bdcFdcqDO.getFttdmj();
            }
            bdcFdcqDO.setTdsyqmj(tdsyqmj);
        }
        return initServiceDTO;
    }
}
