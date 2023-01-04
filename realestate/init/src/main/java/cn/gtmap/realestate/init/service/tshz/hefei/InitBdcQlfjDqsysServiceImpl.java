package cn.gtmap.realestate.init.service.tshz.hefei;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 权利附记读取上一首数据
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcQlfjDqsysServiceImpl_hefei")
public class InitBdcQlfjDqsysServiceImpl implements InitBdcTsHzService {
    @Autowired
    private InitServiceQoService initServiceQoService;

    /**
     * 特殊后置处理
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(!initServiceQO.isSfzqlpbsj() &&  initServiceDTO!=null && initServiceDTO.getBdcQl()!=null && StringUtils.isNotBlank(initServiceQO.getYxmid())){
            //查看上一首权利
            BdcQl ybdcQl=initServiceQoService.queryYql(initServiceQO.getYxmid(),initServiceQO);
            //原权利的附记不为空
            if(ybdcQl !=null && StringUtils.isNotBlank(ybdcQl.getFj())){
                StringBuilder stringBuilder=new StringBuilder();
                if(StringUtils.isNotBlank(initServiceDTO.getBdcQl().getFj())){
                    stringBuilder.append(initServiceDTO.getBdcQl().getFj()).append("\n");
                }
                stringBuilder.append(ybdcQl.getFj());
                initServiceDTO.getBdcQl().setFj(stringBuilder.toString());
            }
        }
        return initServiceDTO;
    }
}
