package cn.gtmap.realestate.init.service.tshz.bengbu;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * @author <a href=""mailto:liaoxiang@gtmap.cn>liaoxiang</a>
 * @version 1.0, 2020/10/6
 * @description 定着物面积, 抵押房产面积、预告建筑面积优先读取实测建筑面积,没有值读取预测建筑面积
 */
@Service("bdcDzwmjServiceImpl_bengbu")
public class InitBdcDzwmjServiceImpl implements InitBdcTsHzService {

    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(!initServiceQO.isSfzqlpbsj() && initServiceDTO!=null && initServiceDTO.getBdcXm()!=null &&initServiceDTO.getBdcCshFwkgSlDO() != null){
            BdcCshFwkgSlDO bdcCshFwkgSlDO=initServiceDTO.getBdcCshFwkgSlDO();
            if(StringUtils.isNotBlank(bdcCshFwkgSlDO.getQlsjly()) &&bdcCshFwkgSlDO.getQlsjly().endsWith(CommonConstantUtils.QLSJLY_LPB)) {
                Double fcmj = null;
                //权籍单元信息
                BdcdyResponseDTO bdcdyResponseDTO = initServiceQO.getBdcdyResponseDTO();
                if (bdcdyResponseDTO != null) {
                    fcmj = bdcdyResponseDTO.getScjzmj() != null ? bdcdyResponseDTO.getScjzmj() : bdcdyResponseDTO.getYcjzmj();
                }
                if (fcmj != null) {
                    initServiceDTO.getBdcXm().setDzwmj(fcmj);
                }
                if (initServiceDTO.getBdcQl() instanceof BdcFdcqDO) {
                    ((BdcFdcqDO) initServiceDTO.getBdcQl()).setJzmj(fcmj);

                } else if (initServiceDTO.getBdcQl() instanceof BdcDyaqDO) {
                    ((BdcDyaqDO) initServiceDTO.getBdcQl()).setFwdymj(fcmj);
                }
                // 蚌埠无论实测楼盘还是预测楼盘，导入权籍库中都入库至fw_hs表，建筑面积的取值逻辑改为先读取实测面积，若实测面积没有则读取预测面积。
                if (initServiceDTO.getBdcQl() instanceof BdcYgDO) {
                    ((BdcYgDO) initServiceDTO.getBdcQl()).setJzmj(fcmj);
                }
            }
        }
        return initServiceDTO;
    }

}
