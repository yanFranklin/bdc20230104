package cn.gtmap.realestate.init.service.qlr.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlr.InitBdcYwrAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/5
 * @description
 */
@Service
public class InitYqlrToBdcYwrServiceImpl extends InitBdcYwrAbstractService {


    @Override
    public String getVal() {
        return Constants.QLRSJLY_YQLR.toString();
    }


    @Override
    public List<BdcQlrDO> initYwr(InitServiceQO initServiceQO){
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if(initServiceQO!=null && StringUtils.isNotBlank(initServiceQO.getYxmid())
                && StringUtils.isNotBlank(initServiceQO.getXmid())){
            InitServiceDTO initServiceDTO = initServiceQO.getServiceDataMap().get(initServiceQO.getYxmid());
            if (initServiceDTO != null && CollectionUtils.isNotEmpty(initServiceDTO.getBdcQlrList())) {
                // 通过组合业务中生成的原项目权利人信息为义务人信息赋值
                bdcQlrDOList = bdcQlrService.inheritYxmBdcQlr(initServiceDTO.getBdcQlrList(),initServiceQO.getXmid(),CommonConstantUtils.QLRLB_YWR);
            } else {
                //获取上一手项目权利人，继承为当前项目的义务人
                bdcQlrDOList = bdcQlrService.inheritYxmBdcQlr(initServiceQO.getYxmid(),initServiceQO.getXmid(),CommonConstantUtils.QLRLB_YWR);
            }
        }
        return bdcQlrDOList;
    }


}
