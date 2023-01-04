package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcCfService;
import cn.gtmap.realestate.init.core.service.BdcCfbhService;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/30
 * @description 查封编号处理
 */
@Service
public class BdcCfbhInitServiceImpl extends InitBdcJwService {

    @Autowired
    BdcCfbhService bdcCfbhService;

    @Autowired
    BdcCfService bdcCfService;

    @Override
    public void initJw(InitResultDTO initResultDTO, List<InitServiceQO> listQO) throws Exception {
        if(initResultDTO!=null && CollectionUtils.isNotEmpty(initResultDTO.getBdcQlList()) &&CollectionUtils.isNotEmpty(initResultDTO.getBdcXmList())){
            String gzlslid =initResultDTO.getBdcXmList().get(0).getGzlslid();
            if(StringUtils.isNotBlank(gzlslid)) {
                String cfbh = StringUtils.EMPTY;
                for (BdcQl bdcQl : initResultDTO.getBdcQlList()) {
                    if (bdcQl instanceof BdcCfDO) {
                        //生成查封编号
                        cfbh = bdcCfbhService.generateCfbh(bdcQl.getQlid());
                        break;
                    }
                }
                //批量更新当前业务所有查封的查封编号
                if(StringUtils.isNotBlank(cfbh)){
                    bdcCfService.updateCfbhPl(gzlslid,cfbh);
                }
            }
        }

    }

    /**
     * 获取服务版本
     *
     * @return
     */
    @Override
    public List<String> getVersion() {
        List<String> versionList =new ArrayList<>();
        versionList.add(CommonConstantUtils.SYSTEM_VERSION_CZ);
        return versionList;
    }
}
