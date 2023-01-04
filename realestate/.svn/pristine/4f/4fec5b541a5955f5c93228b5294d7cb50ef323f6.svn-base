package cn.gtmap.realestate.init.service.qlr.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlr.InitBdcQlrAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/5/6
 * @description 俩个项目互换权利人
 */
@Service
public class InitHhToBdcQlrServiceImpl extends InitBdcQlrAbstractService {

    @Override
    public String getVal() {
        return Constants.QLRSJLY_HH_QLR.toString();
    }

    @Override
    public List<BdcQlrDO> initQlr(InitServiceQO initServiceQO) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if(initServiceQO!=null && StringUtils.isNotBlank(initServiceQO.getXmid()) && CollectionUtils.isNotEmpty(initServiceQO.getYxmids())){
            for(String yxmid:initServiceQO.getYxmids()){
                //不等就读取
                if(!StringUtils.equals(initServiceQO.getYxmid(),yxmid)){
                    //获取上一手项目权利人，继承为当前项目的权利人
                    bdcQlrDOList = bdcQlrService.inheritYxmBdcQlr(yxmid,initServiceQO.getXmid(),CommonConstantUtils.QLRLB_QLR);
                    break;
                }
            }
        }
        return bdcQlrDOList;
    }


}
