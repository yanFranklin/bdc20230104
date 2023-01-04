package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcDyaqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 从原项目加载数据到抵押权
 */
@Service
public class InitYxmToBdcDyaqServiceImpl extends InitBdcDyaqAbstractService {
    /**
     * 抵押权是否需要根据抵押方式区分保存抵押金额
     */
    @Value("${qlxx.dyaq.qfdyje:false}")
    private Boolean qfdyje;

    @Override
    public String getVal() {
        return Constants.QLSJLY_YXM;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcDyaqDO bdcDyaqDO=initFromYxm(initServiceQO,BdcDyaqDO.class);
        return  dealDyaq(initServiceQO,bdcDyaqDO);
    }

    /**
     * 抵押权后置数据处理：抵押权根据抵押方式区分保存抵押金额
     * @param initServiceDTO
     */
    @Override
    public void qlxxPostProcess(InitServiceDTO initServiceDTO) {
        BdcQl bdcQl = initServiceDTO.getBdcQl();
        if(bdcQl instanceof BdcDyaqDO) {
            BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
            if(qfdyje && null != bdcDyaqDO.getDyfs()) {
                if(CommonConstantUtils.DYFS_ZGEDY.equals(bdcDyaqDO.getDyfs())) {
                    // 默认保存到被担保主债权数额，当抵押方式为2最高额抵押时候才处理保存到最高债权额
                    bdcDyaqDO.setZgzqe(bdcDyaqDO.getBdbzzqse());
                    bdcDyaqDO.setBdbzzqse(null);
                }
            }
        }
    }
}
