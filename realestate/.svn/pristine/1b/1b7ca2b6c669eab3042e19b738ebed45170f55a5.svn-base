package cn.gtmap.realestate.init.service.qlr.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
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
 * @description 初始化外联项目义务人到权利人
 */
@Service
public class InitWlxmYwrToBdcYwrServiceImpl extends InitBdcYwrAbstractService {

    @Override
    public String getVal() {
        return Constants.QLRSJLY_WLXM_YWR.toString();
    }


    @Override
    public List<BdcQlrDO> initYwr(InitServiceQO initServiceQO) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        List<BdcXmLsgxDO> listLsgx=null;
        if (initServiceQO != null) {
            //获取历史关系
            if(CollectionUtils.isNotEmpty(initServiceQO.getBdcXmLsgxList())){
                listLsgx=initServiceQO.getBdcXmLsgxList();
            }else{
                listLsgx=bdcXmLsgxService.queryBdcXmLsgxByXmid(initServiceQO.getXmid(),null);
            }
            if(CollectionUtils.isNotEmpty(listLsgx)){
                for(BdcXmLsgxDO bdcXmLsgxDO:listLsgx){
                    //外联项目
                    if(StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid()) && CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getWlxm())){
                        //获取上一手项目义务人，继承为当前项目的义务人
                        bdcQlrDOList = bdcQlrService.inheritYxmBdcYwr(bdcXmLsgxDO.getYxmid(),initServiceQO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                        break;
                    }
                }
            }
        }
        return bdcQlrDOList;
    }
}
