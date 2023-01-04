package cn.gtmap.realestate.init.service.qlr.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
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
 * @version 1.0, 2019/5/16
 * @description 初始化原项目中同权利的义务人数据
 */
@Service
public class InitYtqlToBdcYwrServiceImpl extends InitBdcYwrAbstractService {

    @Override
    public String getVal() {
        return Constants.QLRSJLY_YTQL_QLR.toString();
    }


    @Override
    public List<BdcQlrDO> initYwr(InitServiceQO initServiceQO){
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
                //排序降序
                listLsgx.sort((o1, o2) -> Integer.compare(o2.getWlxm()==null? 0 : o2.getWlxm(),o1.getWlxm()==null? 0 : o1.getWlxm()));
                for(BdcXmLsgxDO bdcXmLsgxDO:listLsgx){
                    if(StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())){
                        BdcXmDO yxm=bdcXmService.queryBdcXmByPrimaryKey(bdcXmLsgxDO.getYxmid());
                        //读取同权利项目的数据
                        if(yxm!=null && initServiceQO.getBdcXm()!=null &&
                                initServiceQO.getBdcXm().getQllx()!=null && initServiceQO.getBdcXm().getQllx().equals(yxm.getQllx())){
                            //获取上一手项目义务人，继承为当前项目的义务人
                            bdcQlrDOList = bdcQlrService.inheritYxmBdcYwr(yxm.getXmid(),initServiceQO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                            break;
                        }
                    }
                }
            }
        }
        return bdcQlrDOList;
    }


}
