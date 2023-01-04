package cn.gtmap.realestate.init.service.tshz.standard;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 身份证证件号处理为大写
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcZjhclServiceImpl")
public class InitBdcZjhclServiceImpl implements InitBdcTsHzService {

    /**
     * 身份证证件号处理为大写
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //权利人数据
        if(!initServiceQO.isSfzqlpbsj() && initServiceDTO!=null && CollectionUtils.isNotEmpty(initServiceDTO.getBdcQlrList())){
            for(BdcQlrDO bdcQlrDO:initServiceDTO.getBdcQlrList()){
                if(StringUtils.isNotBlank(bdcQlrDO.getZjh())){
                    //转为大写
                    bdcQlrDO.setZjh(bdcQlrDO.getZjh().toUpperCase());
                }
                if(StringUtils.isNotBlank(bdcQlrDO.getDlrzjh())){
                    //转为大写
                    bdcQlrDO.setDlrzjh(bdcQlrDO.getDlrzjh().toUpperCase());
                }
                if(StringUtils.isNotBlank(bdcQlrDO.getFrzjh())){
                    //转为大写
                    bdcQlrDO.setFrzjh(bdcQlrDO.getFrzjh().toUpperCase());
                }
                if(StringUtils.isNotBlank(bdcQlrDO.getLzrzjh())){
                    //转为大写
                    bdcQlrDO.setLzrzjh(bdcQlrDO.getLzrzjh().toUpperCase());
                }
            }
        }
        return initServiceDTO;
    }
}
