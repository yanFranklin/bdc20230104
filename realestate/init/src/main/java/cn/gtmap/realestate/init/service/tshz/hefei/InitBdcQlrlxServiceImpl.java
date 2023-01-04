package cn.gtmap.realestate.init.service.tshz.hefei;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * 权利人类型自动赋值
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcQlrlxServiceImpl_hefei")
public class InitBdcQlrlxServiceImpl implements InitBdcTsHzService {

    /**
     * 权利人类型自动赋值
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
                //空的时候做处理
                if(bdcQlrDO.getQlrlx()==null){
                    Integer qlrlx=CommonConstantUtils.QLRLX_QT;
                    //身份证
                    if(CommonConstantUtils.ZJZL_SFZ.equals(bdcQlrDO.getZjzl())){
                        qlrlx=CommonConstantUtils.QLRLX_GR;
                    }else if(bdcQlrDO.getZjzl()!=null){
                        qlrlx=CommonConstantUtils.QLRLX_QY;
                    }
                    bdcQlrDO.setQlrlx(qlrlx);
                }
            }
        }
        return initServiceDTO;
    }
}
