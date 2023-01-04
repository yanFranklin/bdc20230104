package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化入库数据之后的服务  查封状态回写
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/5.
 * @description
 */
@Service
public class BdcCfHxQjztServiceImpl extends InitBdcJwService {
    private static Logger logger = LoggerFactory.getLogger(BdcCfHxQjztServiceImpl.class);

    /**
     * 查封流程查封状态立即生效，配置false则到登簿环节再生效
     */
    @Value("${init.cfztsx:true}")
    private Boolean cfztsx;


    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;

    /**
     * 初始化入库数据之后的服务
     *
     * @param initResultDTO 初始化后的数据
     * @throws Exception
     */
    @Override
    public void initJw(InitResultDTO initResultDTO,List<InitServiceQO> listQO) throws Exception {
        if(!cfztsx) {
            logger.warn("当前系统设置查封流程创建不立即生效，无需同步不动产单元查封状态到权籍");
            return;
        }


        if(initResultDTO!=null && CollectionUtils.isNotEmpty(initResultDTO.getBdcQlList())){
            List<String> bdcdyList=new ArrayList<>();
            for(BdcQl bdcQl:initResultDTO.getBdcQlList()){
                if(bdcQl instanceof BdcCfDO){
                    BdcCfDO bdcCfDO= (BdcCfDO) bdcQl;
                    if(StringUtils.isNotBlank(bdcCfDO.getBdcdyh()) && !bdcdyList.contains(bdcCfDO.getBdcdyh())){
                        bdcdyList.add(bdcCfDO.getBdcdyh());
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(bdcdyList)){
                bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyList,CollectionUtils.isNotEmpty(initResultDTO.getBdcXmFbList())?initResultDTO.getBdcXmFbList().get(0).getQjgldm():"");
            }
        }
    }
}
