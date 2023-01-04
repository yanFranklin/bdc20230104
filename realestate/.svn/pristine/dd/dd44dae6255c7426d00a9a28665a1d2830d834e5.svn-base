package cn.gtmap.realestate.init.service.qlr.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlr.InitBdcYwrAbstractService;
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
public class InitHhToBdcYwrServiceImpl extends InitBdcYwrAbstractService {

    @Override
    public String getVal() {
        return Constants.QLRSJLY_HH_QLR.toString();
    }

    /**
     * 初始化义务人接口
     * @param initServiceQO 初始化所需数据结构
     * @return List<BdcQlrDO> 返回所有义务人信息
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description
     */
    @Override
    public List<BdcQlrDO> initYwr(InitServiceQO initServiceQO) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if(initServiceQO!=null && StringUtils.isNotBlank(initServiceQO.getXmid()) && CollectionUtils.isNotEmpty(initServiceQO.getYxmids())){
            for(String yxmid:initServiceQO.getYxmids()){
                //不等就读取
                if(!StringUtils.equals(initServiceQO.getYxmid(),yxmid)){
                    //获取上一手项目义务人，继承为当前项目的义务人
                    bdcQlrDOList = bdcQlrService.inheritYxmBdcYwr(yxmid,initServiceQO.getXmid(),CommonConstantUtils.QLRLB_YWR);
                    break;
                }
            }
        }
        return bdcQlrDOList;
    }
}
