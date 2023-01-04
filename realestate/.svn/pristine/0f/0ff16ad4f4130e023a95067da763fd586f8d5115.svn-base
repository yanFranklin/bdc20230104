package cn.gtmap.realestate.init.service.tshz.hefei;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/9/16.
 * @description  项目附表存储是否带入交易信息
 */
@Service("sfdrJyxxServiceImpl_hefei")
public class InitBdcXmFbSfdrJyxxServiceImpl implements InitBdcTsHzService {
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(initServiceQO!=null && StringUtils.isNotBlank(initServiceQO.getXmid())){
            List<BdcSlJyxxDO> jyxxDOS=bdcSlJyxxFeignService.listBdcSlJyxxByXmid(initServiceQO.getXmid());
            if(CollectionUtils.isNotEmpty(jyxxDOS) && StringUtils.isNotBlank(jyxxDOS.get(0).getHtbh())){
                initServiceDTO.getBdcXmFb().setSfdrjyxx(CommonConstantUtils.SF_S_DM);
            }
        }
        return initServiceDTO;
    }
}
