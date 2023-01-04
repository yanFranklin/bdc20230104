package cn.gtmap.realestate.init.service.tshz.haimen;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcTdcbnydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcTdsyqDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.impl.InitDataDealServiceImpl;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/11.
 * @description
 */
@Service("bdcZdSylxServiceImpl_nantong")
public class InitBdcZdSylxServiceImpl implements InitBdcTsHzService{
    private static final Logger LOGGER = LoggerFactory.getLogger(InitBdcZdSylxServiceImpl.class);
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        // 项目附表中宗地所有类型为空时 走赋默认值逻辑
        if(initServiceDTO.getBdcXmFb() != null && initServiceDTO.getBdcXmFb().getZdsylx() == null){
            Integer zdsylx = CommonConstantUtils.ZDSYLX_GY;
            if(initServiceDTO.getBdcQl() != null){
                if(initServiceDTO.getBdcQl() instanceof BdcJsydsyqDO || initServiceDTO.getBdcQl() instanceof BdcTdsyqDO
                        || initServiceDTO.getBdcQl() instanceof BdcTdcbnydsyqDO){
                    // 土地
                    zdsylx = CommonConstantUtils.ZDSYLX_DY;
                }else if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO){
                    // 房地产权
                    if(CommonConstantUtils.FWLX_DUOZH.equals(((BdcFdcqDO)initServiceDTO.getBdcQl()).getBdcdyfwlx())){
                        zdsylx = CommonConstantUtils.ZDSYLX_DY;
                    }
                }
            }
            initServiceDTO.getBdcXmFb().setZdsylx(zdsylx);
        }
        return initServiceDTO;
    }
}
