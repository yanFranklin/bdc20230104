package cn.gtmap.realestate.init.service.qlr.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlr.InitBdcYwrAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/5
 * @description
 */
@Service
public class InitLpbToBdcYwrServiceImpl extends InitBdcYwrAbstractService {
    @Override
    public String getVal() {
        return Constants.QLRSJLY_LPB.toString();
    }


    @Override
    public List<BdcQlrDO> initYwr(InitServiceQO initServiceQO){
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if(initServiceQO!=null){
            List<Object> list=bdcQlrService.queryDjQlr(initServiceQO,CommonConstantUtils.QLRLB_YWR);
            //获取权籍义务人息
            bdcQlrDOList = bdcQlrService.inheritLpbQlr(list,initServiceQO.getXmid(),initServiceQO.getBdcdyh(),CommonConstantUtils.QLRLB_YWR);
        }
        return bdcQlrDOList;
    }


}
