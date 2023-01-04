package cn.gtmap.realestate.init.service.tshz.hefei;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 划拨的清空房地产权的土地使用结束时间
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcHbQkjssjServiceImpl_hefei")
public class InitBdcHbQkjssjServiceImpl implements InitBdcTsHzService {

    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private EntityMapper entityMapper;

    /**
     * 划拨的清空房地产权的土地使用结束时间
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //产权的并且是划拨的
        if(!initServiceQO.isSfzqlpbsj() && initServiceDTO!=null && initServiceDTO.getBdcXm()!=null &&
                StringUtils.equals("101",initServiceDTO.getBdcXm().getQlxz()) && initServiceDTO.getBdcQl() instanceof BdcFdcqDO){
            //清空产权的土地结束时间
            ((BdcFdcqDO) initServiceDTO.getBdcQl()).setTdsyjssj(null);
        }
        return initServiceDTO;
    }
}
