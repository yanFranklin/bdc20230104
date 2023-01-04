package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcDyaqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 从原预抵项目加载数据到抵押权
 */
@Service
public class InitYydxmToBdcDyaqServiceImpl extends InitBdcDyaqAbstractService {
    @Autowired
    private BdcXmService bdcXmService;

    @Override
    public String getVal() {
        return Constants.QLSJLY_YZX_YYD;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcDyaqDO bdcDyaqDO=null;
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
                    if(StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())){
                        BdcXmDO yxm=bdcXmService.queryBdcXmByPrimaryKey(bdcXmLsgxDO.getYxmid());
                        //读取预告项目的数据
                        if(yxm!=null && CommonConstantUtils.QLLX_YG_DM.equals(yxm.getQllx())){
                            bdcDyaqDO=initFromYxm(initServiceQO,BdcDyaqDO.class,bdcXmLsgxDO.getYxmid());
                            break;
                        }
                    }
                }
            }
        }
        return  dealDyaq(initServiceQO,bdcDyaqDO);
    }
}
