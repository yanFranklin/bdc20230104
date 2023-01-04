package cn.gtmap.realestate.init.service.lhxx.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmJsydlhxxGxDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmJsydlhxxGxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.lhxx.InitBdcJsydLhxxAbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 建设用地量化
 */
@Service
public class InitBdcJsydLhxxServiceImpl extends InitBdcJsydLhxxAbstractService {

    @Autowired
    BdcXmService bdcXmService;

    @Autowired
    BdcXmJsydlhxxGxService bdcXmJsydlhxxGxService;

    @Override
    public String getVal() {
        return CommonConstantUtils.SF_S_DM.toString();
    }

    @Override
    public List<BdcXmJsydlhxxGxDO> initBdcXmJsydlhxxGxList(InitServiceQO initServiceQO){
        List<BdcXmJsydlhxxGxDO> bdcXmJsydlhxxGxList =new ArrayList<>();

        if(StringUtils.isNotBlank(initServiceQO.getYxmid())){
            BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(initServiceQO.getYxmid());
            if(bdcXmDO != null &&StringUtils.isNotBlank(bdcXmDO.getGzlslid())){
                List<BdcXmJsydlhxxGxDO> bdcXmJsydlhxxGxDOList = this.bdcXmJsydlhxxGxService.listBdcXmJsydlhxxGxByGzlslid(bdcXmDO.getGzlslid());
                if(CollectionUtils.isNotEmpty(bdcXmJsydlhxxGxDOList)){
                    for(BdcXmJsydlhxxGxDO gx : bdcXmJsydlhxxGxDOList){
                        BdcXmJsydlhxxGxDO bdcXmJsydlhxxGxDO =new BdcXmJsydlhxxGxDO();
                        bdcXmJsydlhxxGxDO.setGzlslid(initServiceQO.getBdcXm().getGzlslid());
                        bdcXmJsydlhxxGxDO.setJsydzrzxxid(gx.getJsydzrzxxid());
                        bdcXmJsydlhxxGxDO.setGxid(UUIDGenerator.generate16());
                        bdcXmJsydlhxxGxList.add(bdcXmJsydlhxxGxDO);
                    }
                }
            }
        }
        return bdcXmJsydlhxxGxList;
    }
}
