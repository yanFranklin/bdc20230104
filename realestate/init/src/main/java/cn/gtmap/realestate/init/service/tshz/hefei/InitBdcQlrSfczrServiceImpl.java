package cn.gtmap.realestate.init.service.tshz.hefei;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 权利人是否持证人自动赋值
 *
 * @author wangyinghao
 */
@Service("bdcQlrSfczrServiceImpl_hefei")
@Slf4j
public class InitBdcQlrSfczrServiceImpl implements InitBdcTsHzService {
    /**
     * 特殊处理的要设置为不是持证人的权利人
     */
    @Value("#{'${init.tshz.qlr.bcz:}'.split(',')}")
    private List<String> bcz;

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
        if(Objects.isNull(initServiceQO) || CollectionUtils.isEmpty(initServiceDTO.getBdcQlrList()) || Objects.isNull(initServiceDTO.getBdcCshFwkgSlDO())){
            return initServiceDTO;
        }
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
            //如果是查封或者注销类的则不是持证人
            boolean sfbsczr = (CommonConstantUtils.QLLX_CF.equals(bdcCshFwkgSlDO.getQllx())
            ) || (
                    CommonConstantUtils.SF_F_DM.equals(bdcCshFwkgSlDO.getSfscql())
                            && CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfzxyql())
            );

            for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                //如果是注销或者查封或者指定的特殊的工作流则设置为否
                log.info("Init设置qlr:{},是否领证人{},{}", bdcQlrDO.getQlrid(), sfbsczr, gzldyid);
                if (sfbsczr || (CollectionUtils.isNotEmpty(bcz) && bcz.contains(gzldyid))) {
                    bdcQlrDO.setSfczr(CommonConstantUtils.SF_F_DM);
                } else if (Objects.isNull(bdcQlrDO.getSfczr())) {
                    bdcQlrDO.setSfczr(CommonConstantUtils.SF_S_DM);
                }
            }
        }


        //义务人处理
        List<BdcQlrDO> bdcywrDOS = initServiceDTO.getBdcQlrList()
                .stream()
                .filter(bdcQlrDO -> bdcQlrDO.getQlrlb() != null
                        && StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcQlrDO.getQlrlb()))
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(bdcywrDOS) && Objects.nonNull(bdcCshFwkgSlDO)) {
            for (BdcQlrDO bdcQlrDO : bdcywrDOS) {
                //空的时候做处理
                log.info("Init设置ywr:{},是否领证人", bdcQlrDO.getQlrid());
                bdcQlrDO.setSfczr(CommonConstantUtils.SF_F_DM);
            }
        }
        return initServiceDTO;
    }
}
