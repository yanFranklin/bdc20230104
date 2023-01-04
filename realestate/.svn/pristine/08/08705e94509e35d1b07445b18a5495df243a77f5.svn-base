package cn.gtmap.realestate.init.service.qlr.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlr.InitBdcQlrAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/5
 * @description 初始化权利人信息服务实现
 */
@Service
public class InitLpbToBdcQlrServiceImpl extends InitBdcQlrAbstractService {
    @Override
    public String getVal() {
        return Constants.QLRSJLY_LPB.toString();
    }


    @Override
    public List<BdcQlrDO> initQlr(InitServiceQO initServiceQO) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (initServiceQO != null) {
            List<Object> list=bdcQlrService.queryDjQlr(initServiceQO,CommonConstantUtils.QLRLB_QLR);
            //获取权籍权利人信息
            bdcQlrDOList = bdcQlrService.inheritLpbQlr(list, initServiceQO.getXmid(), initServiceQO.getBdcdyh(), CommonConstantUtils.QLRLB_QLR);
        }
        return bdcQlrDOList;
    }


}
