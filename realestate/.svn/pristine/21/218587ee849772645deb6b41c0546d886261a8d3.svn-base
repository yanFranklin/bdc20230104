package cn.gtmap.realestate.init.service.xmxx.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.xmxx.InitBdcXmLsgxAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目历史关系初始化服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/8.
 * @description
 */
@Service
public class InitBdcXmLsgxServiceImpl extends InitBdcXmLsgxAbstractService{


    @Override
    public String getVal() {
        return Constants.DEFAULT;
    }


    @Override
//    @AuditLog(event = "初始化不动产项目关系信息",response = true,names = {"initServiceQO"})
    public List<BdcXmLsgxDO> initBdcXmLsgx(InitServiceQO initServiceQO) {
        //初始化参数转换模块封装好结构
        List<BdcXmLsgxDO> list=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(initServiceQO.getBdcXmLsgxList())){
           for(BdcXmLsgxDO bdcXmLsgxDO:initServiceQO.getBdcXmLsgxList()){
               if(StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())){
                   if(bdcXmLsgxDO.getWlxm()==null){
                       bdcXmLsgxDO.setWlxm(CommonConstantUtils.SF_F_DM);
                   }
                   bdcXmLsgxDO.setGxid(UUIDGenerator.generate16());
                   list.add(bdcXmLsgxDO);
               }
           }
        }
        return list;
    }
}
