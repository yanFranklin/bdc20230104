package cn.gtmap.realestate.accept.service.impl.initslxx;

import cn.gtmap.realestate.accept.core.service.BdcSlJyxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlSqrService;
import cn.gtmap.realestate.accept.core.service.BdcSlZjjgService;
import cn.gtmap.realestate.accept.service.BdcAddGwcSjclCommonService;
import cn.gtmap.realestate.accept.service.InitBdcSlxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlZjjgDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxInitDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlZjjgQO;
import cn.gtmap.realestate.common.core.qo.accept.InitSlxxQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCshXtPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/8/18
 * @description 资金监管初始化受理信息
 */
@Service
public class InitBdcSlxxZjjgServiceImpl implements InitBdcSlxxService {

    @Autowired
    BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    BdcSlSqrService bdcSlSqrService;
    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    BdcAddGwcSjclCommonService bdcAddGwcSjclCommonService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlZjjgService bdcSlZjjgService;

    @Override
    public BdcSlxxInitDTO initBdcSlxx(InitSlxxQO initSlxxQO) {
        BdcSlxxInitDTO bdcSlxxInitDTO = new BdcSlxxInitDTO();
        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        BdcSlXmDO bdcSlXmDO = bdcAddGwcSjclCommonService.changeYwxxDtoIntoBdcSlXm(initSlxxQO.getBdcSlYwxxDTO(),initSlxxQO.getCzrid(),initSlxxQO.getJbxxid());
        bdcSlXmDOList.add(bdcSlXmDO);

        // 生成资金监管受理项目历史关系
        if(StringUtils.isNotBlank(initSlxxQO.getYxmid())){
            BdcSlZjjgQO bdcSlZjjgQO = new BdcSlZjjgQO();
            bdcSlZjjgQO.setZt(CommonConstantUtils.ZJJG_ZT_WCX);
            bdcSlZjjgQO.setCqxmid(initSlxxQO.getYxmid());
            List<BdcSlZjjgDO> bdcSlZjjgDOList = bdcSlZjjgService.listBdcSlZjjg(bdcSlZjjgQO);
            if(CollectionUtils.isNotEmpty(bdcSlZjjgDOList)){
                BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), bdcSlZjjgDOList.get(0).getXmid(), "", "", "");
                bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
            }
        }

        bdcSlxxInitDTO.setBdcSlXmDOList(bdcSlXmDOList);
        bdcSlxxInitDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);
        return bdcSlxxInitDTO;
    }

}
