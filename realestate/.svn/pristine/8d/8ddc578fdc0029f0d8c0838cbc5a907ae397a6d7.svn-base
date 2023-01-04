package cn.gtmap.realestate.init.service.tshz.lianyungang;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/12/12/18:03
 * @Description: 处理在建建筑抵押
 */
@Service("bdcZzjzdyZlServiceImpl_lianyungang")
public class InitBdcZzjzdyZlServiceImpl implements InitBdcTsHzService {

    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if (initServiceDTO != null && initServiceDTO.getBdcQl() instanceof BdcDyaqDO) {
            BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) initServiceDTO.getBdcQl();
            BdcXmDO bdcXmDO = initServiceDTO.getBdcXm();
            // 处理在建建筑物坐落
            if (bdcDyaqDO != null && CommonConstantUtils.DYBDCLX_ZJJZW.equals(bdcDyaqDO.getDybdclx())){
                if(StringUtils.isBlank(bdcDyaqDO.getZjjzwzl()) && StringUtils.isNotBlank(bdcXmDO.getZl())){
                    bdcDyaqDO.setZjjzwzl(bdcXmDO.getZl());
                }
            }
        }
        return initServiceDTO;
    }
}
