package cn.gtmap.realestate.init.service.qlr.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlr.InitBdcYwrAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/5/16
 * @description
 */
@Service
public class InitCfToBdcYwrServiceImpl extends InitBdcYwrAbstractService {


    @Override
    public String getVal() {
        return Constants.QLRSJLY_CF.toString();
    }


    @Override
    public List<BdcQlrDO> initYwr(InitServiceQO initServiceQO){
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (initServiceQO != null && StringUtils.isNotBlank(initServiceQO.getYxmid())) {
            BdcXmDO yxm=bdcXmService.queryBdcXmByPrimaryKey(initServiceQO.getYxmid());
            if(yxm!=null){
                //生成证明的权利  读取义务人到义务人  否则读取权利人到义务人
                if(CommonConstantUtils.QLLX_CF.equals(yxm.getQllx())){
                    //获取上一手项目义务人，继承为当前项目的义务人
                    bdcQlrDOList = bdcQlrService.inheritYxmBdcYwr(initServiceQO.getYxmid(),initServiceQO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                }else{
                    //获取上一手项目权利人，继承为当前项目的义务人
                    bdcQlrDOList = bdcQlrService.inheritYxmBdcQlr(initServiceQO.getYxmid(),initServiceQO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                }
            }
        }
        return bdcQlrDOList;
    }


}
