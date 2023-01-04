package cn.gtmap.realestate.init.service.tshz.standard;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 续封登记的附记中存入上一首的查封文号
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcXfFjServiceImpl")
public class InitBdcXfFjServiceImpl implements InitBdcTsHzService {

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
        if(!initServiceQO.isSfzqlpbsj() && initServiceDTO!=null){
            //续封做处理
            if(initServiceDTO.getBdcQl() instanceof BdcCfDO){
                BdcCfDO bdcCfDO= (BdcCfDO) initServiceDTO.getBdcQl();
                //根据查封类型判断续封登记
                if(CommonConstantUtils.CFLX_XF.equals(bdcCfDO.getCflx()) && StringUtils.isNotBlank(initServiceQO.getYxmid())){
                    BdcQl yql=initServiceQoService.queryYql(initServiceQO.getYxmid(),initServiceQO);
                    if(yql instanceof BdcCfDO){
                        BdcCfDO ycf= (BdcCfDO) yql;
                        if(StringUtils.isNotBlank(ycf.getCfwh())){
                            String str="针对"+ycf.getCfwh()+"文号进行续封";
                            if(StringUtils.isBlank(bdcCfDO.getFj())){
                                bdcCfDO.setFj(str);
                            }else{
                                bdcCfDO.setFj(str+"\n"+bdcCfDO.getFj());
                            }
                        }
                    }
                }
            }
        }
        return initServiceDTO;
    }
}
