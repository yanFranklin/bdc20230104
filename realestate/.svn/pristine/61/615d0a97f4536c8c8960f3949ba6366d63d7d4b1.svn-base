package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 独撞房屋的特殊处理
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/29.
 * @description
 */
@Service("bdcDzfwServiceImpl_nantong")
public class InitBdcDzfwServiceImpl implements InitBdcTsHzService {

    @Autowired
    private FwHsFeignService fwHsFeignService;

    /**
     * 独撞房屋的特殊处理
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(initServiceDTO!=null && initServiceDTO.getBdcXm()!=null){
            //属于房地产权的做处理  土地时间为空并且土地分摊面积为空的处理
            if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO &&
                    CommonConstantUtils.BDCDYFWLX_DUZH.equals(((BdcFdcqDO) initServiceDTO.getBdcQl()).getBdcdyfwlx())){
                //判定是否为以权籍为主
                if(initServiceQO.getBdcCshFwkgSl()!=null && StringUtils.isNotBlank(initServiceQO.getBdcCshFwkgSl().getQlsjly())
                        && StringUtils.endsWith(initServiceQO.getBdcCshFwkgSl().getQlsjly(), Constants.QLSJLY_LPB)){
                    //部分对照的重新取数据
                    if(initServiceQO.isSfdzbflpbsj()){
                        initServiceQO.setBdcdyResponseDTO(null);
                        initServiceQO.setDjxxResponseDTO(null);
                    }
                    //获取房屋调查表index
                    if(initServiceQO.getBdcdyResponseDTO()!=null && StringUtils.isNotBlank(initServiceQO.getBdcdyResponseDTO().getFwDcbIndex())){
                        List<FwHsDO> list= fwHsFeignService.listFwhsByFwDcbIndex(initServiceQO.getBdcdyResponseDTO().getFwDcbIndex(),initServiceQO.getQjgldm());
                        if(CollectionUtils.isNotEmpty(list)){
                            FwHsDO fwHsDO=list.get(0);
                            if(fwHsDO!=null){
                                if(StringUtils.isNotBlank(fwHsDO.getGhyt()) && NumberUtils.isNumber(fwHsDO.getGhyt())){
                                    ((BdcFdcqDO) initServiceDTO.getBdcQl()).setGhyt(NumberUtils.toInt(fwHsDO.getGhyt()));
                                    initServiceDTO.getBdcXm().setDzwyt(NumberUtils.toInt(fwHsDO.getGhyt()));
                                }
                                if(fwHsDO.getScjzmj()!=null){
                                    ((BdcFdcqDO) initServiceDTO.getBdcQl()).setJzmj(fwHsDO.getScjzmj());
                                    initServiceDTO.getBdcXm().setDzwmj(fwHsDO.getScjzmj());
                                }
                            }
                        }
                    }

                }
            }
        }
        return initServiceDTO;
    }
}
