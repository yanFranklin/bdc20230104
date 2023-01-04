package cn.gtmap.realestate.init.service.tshz.yancheng;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 盐城处理债务人信息
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 下午4:00 2020/12/30
 */
@Service("bdcCffwServiceImpl")
public class InitBdcCffwServiceImpl implements InitBdcTsHzService {

    /**
     * 特殊后置处理<br>
     * 盐城创建查封业务默认 cffw 为空，此处不修改 dozer 直接特殊处理，dozer 对照影响对比权籍功能
     *
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin1</a>
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        // sply 法院则保存 cffw
        if (initServiceDTO != null && initServiceDTO.getBdcQl() instanceof BdcCfDO &&
                initServiceQO.getBdcXm() != null && !CommonConstantUtils.SPLY_FY.equals(initServiceQO.getBdcXm().getSply())) {
            BdcCfDO bdcCfDO = (BdcCfDO) initServiceDTO.getBdcQl();
            bdcCfDO.setCffw(StringUtils.EMPTY);
        }
        return initServiceDTO;
    }
}
