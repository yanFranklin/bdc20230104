package cn.gtmap.realestate.init.service.tshz.hefei;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcQlrtzService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 权利人特征自动赋值
 *
 * @author wangyinghao
 */
@Service("bdcQlrQlrtzServiceImpl_hefei")
@Slf4j
public class InitBdcQlrQlrtzServiceImpl implements InitBdcTsHzService {
    @Autowired
    BdcQlrtzService bdcQlrtzService;

    /**
     * 权利人是否持证人自动赋值
     *
     * @param initServiceQO
     * @param initServiceDTO
     * @return
     * @throws Exception
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //权利人数据
        BdcCshFwkgSlDO bdcCshFwkgSlDO = initServiceDTO.getBdcCshFwkgSlDO();
        List<BdcQlrDO> bdcQlrDOS = initServiceDTO.getBdcQlrList()
                .stream()
                .filter(bdcQlrDO -> bdcQlrDO.getQlrlb() != null
                        && StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcQlrDO.getQlrlb()))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(bdcQlrDOS)
                && Objects.nonNull(bdcCshFwkgSlDO)) {
            String gzldyid = initServiceQO.getBdcXm().getGzldyid();

            for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                //空的时候做处理
                if (Objects.isNull(bdcQlrDO.getQlrtz())) {
                    Integer qlrtz = bdcQlrtzService.getQlrtzMrz(initServiceQO.getBdcCshFwkgSl().getQllx(), CommonConstantUtils.QLRLB_QLR);
                    log.info("Init设置Qlr:{},{},权利人特征{}", bdcQlrDO.getQlrid(), initServiceQO.getBdcCshFwkgSl().getQllx(), qlrtz);
                    if (Objects.nonNull(qlrtz)) {
                        bdcQlrDO.setQlrtz(qlrtz);
                    }
                }
            }
        }
        return initServiceDTO;
    }
}
