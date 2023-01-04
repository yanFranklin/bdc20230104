package cn.gtmap.realestate.init.service.tshz.standard;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首次登记土地使用权人
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/8/2 14:58
 */
@Service("bdcScdjTdsyqrServiceImpl")
public class InitBdcScdjTdsyqrServiceImpl implements InitBdcTsHzService {


    @Value("#{'${init.scdjtdsyqr}'.split(',')}")
    private List<String> scdjtdsyqr;

    /**
     * 特殊后置处理
     * 房屋所有权首次登记（证明单）土地使用权人字段全部默认生成全体业主
     *
     * @param initServiceQO
     * @param initServiceDTO *@return InitServiceDTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if (initServiceDTO != null && CollectionUtils.isNotEmpty(scdjtdsyqr)) {
            //登记小类在此配置内的
            if (!initServiceQO.isSfzqlpbsj() && initServiceDTO.getBdcQl() instanceof BdcFdcqDO && initServiceDTO.getBdcXm() != null && scdjtdsyqr.contains(initServiceDTO.getBdcXm().getDjxl())) {
                ((BdcFdcqDO) initServiceDTO.getBdcQl()).setTdsyqr(Constants.TDSYQR_QTYZ);
            }
        }
        return initServiceDTO;
    }
}
