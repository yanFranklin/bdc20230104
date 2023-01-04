package cn.gtmap.realestate.init.service.qlr.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlr.InitBdcYwrAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/5/16
 * @description
 */
@Service
public class InitZdqlrToBdcYwrServiceImpl extends InitBdcYwrAbstractService {

    @Override
    public String getVal() {
        return Constants.QLRSJLY_ZDQLR.toString();
    }


    @Override
    public List<BdcQlrDO> initYwr(InitServiceQO initServiceQO){
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (initServiceQO != null && CollectionUtils.isNotEmpty(initServiceQO.getDjxxResponseDTO().getQlrList())) {
            List<Object> list=new ArrayList<>();
            list.addAll(initServiceQO.getDjxxResponseDTO().getQlrList());
            //获取权籍权利人信息
            bdcQlrDOList = bdcQlrService.inheritLpbQlr(list, initServiceQO.getXmid(), initServiceQO.getBdcdyh(), CommonConstantUtils.QLRLB_YWR);
        }
        return bdcQlrDOList;
    }


}
