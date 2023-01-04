package cn.gtmap.realestate.init.service.qlr.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlr.InitBdcQlrAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/5/6
 * @description 初始化查封相关流程的权利人数据
 */
@Service
public class InitZdqlrToBdcQlrServiceImpl extends InitBdcQlrAbstractService {


    @Override
    public String getVal() {
        return Constants.QLRSJLY_ZDQLR.toString();
    }


    @Override
    public List<BdcQlrDO> initQlr(InitServiceQO initServiceQO) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (initServiceQO != null && CollectionUtils.isNotEmpty(initServiceQO.getDjxxResponseDTO().getQlrList())) {
            List<Object> list=new ArrayList<>();
            list.addAll(initServiceQO.getDjxxResponseDTO().getQlrList());
            //获取权籍权利人信息
            bdcQlrDOList = bdcQlrService.inheritLpbQlr(list, initServiceQO.getXmid(), initServiceQO.getBdcdyh(), CommonConstantUtils.QLRLB_QLR);
        }
        return bdcQlrDOList;
    }


}
