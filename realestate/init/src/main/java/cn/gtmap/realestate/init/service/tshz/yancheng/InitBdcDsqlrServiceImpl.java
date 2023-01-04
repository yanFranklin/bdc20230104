package cn.gtmap.realestate.init.service.tshz.yancheng;

import cn.gtmap.realestate.common.core.domain.BdcDsQlrDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 盐城处理债务人信息
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 下午4:00 2020/12/30
 */
@Service("bdcDsqlrServiceImpl")
public class InitBdcDsqlrServiceImpl implements InitBdcTsHzService {

    /**
     * 特殊后置处理
     * 盐城债务人信息保存
     *
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin1</a>
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        List<BdcDsQlrDO> bdcDsQlrDOList = initServiceQO.getBdcDsQlrDOList();
        if (CollectionUtils.isNotEmpty(bdcDsQlrDOList)) {
            for(BdcDsQlrDO bdcDsQlrDO:bdcDsQlrDOList){
                if(StringUtils.isBlank(bdcDsQlrDO.getXmid()) &&initServiceDTO.getBdcXm() != null){
                    bdcDsQlrDO.setXmid(initServiceDTO.getBdcXm().getXmid());
                }

            }
            initServiceDTO.setBdcDsQlrDOList(bdcDsQlrDOList);
        }
        return initServiceDTO;
    }
}
