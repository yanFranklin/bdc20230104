package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcNydjyqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcNydjyqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/6.
 * @description
 */
@Service
public class InitYxmToBdcNydjyqServiceImpl extends InitBdcNydjyqAbstractService {
    @Autowired
    BdcXmService bdcXmService;

    @Override
    public String getVal() {
        return Constants.QLSJLY_YXM;
    }

    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        BdcQl bdcQl = initFromYxm(initServiceQO, BdcNydjyqDO.class);
        getYxmQlrToCbf(bdcQl,initServiceQO);
        return bdcQl;
    }
    /**
    * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
    * @Date 2020/9/2
    * @param bdcQl
    * @param initServiceQO
    * @return cn.gtmap.realestate.common.core.domain.BdcQl
    * @description 当前权利承包方取原项目权利人
    */
    private BdcQl getYxmQlrToCbf(BdcQl bdcQl,InitServiceQO initServiceQO){
        BdcNydjyqDO nydjyqDO =  (BdcNydjyqDO)bdcQl;
        if(StringUtils.isBlank(nydjyqDO.getCbf()) &&StringUtils.isNotBlank(initServiceQO.getYxmid())){
            BdcXmDO yxm;
            InitServiceDTO initServiceDTO = initServiceQO.getServiceDataMap().get(initServiceQO.getYxmid());
            if (initServiceDTO != null) {
                yxm = initServiceDTO.getBdcXm();
            } else {
                yxm = bdcXmService.queryBdcXmByPrimaryKey(initServiceQO.getYxmid());
            }
            if(yxm != null && StringUtils.isNotBlank(yxm.getQlr())){
                nydjyqDO.setCbf(yxm.getQlr());
            }
        }
        return bdcQl;
    }
}
