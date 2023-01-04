package cn.gtmap.realestate.init.service.tshz.huaian;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @return 淮安流程创建时将是否过户信息为是存入数据库
* @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
* @description
*/
@Service("bdcSdqghServiceImpl")
public class InitBdcSdqghServiceImpl implements InitBdcTsHzService {
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcSdqghFeignService bdcSdqghFeignService;

    /**配置水电气台账流程**/
    @Value("${saveSdq.pzsdqlc:}")
    private String pzsdqlc;

    /**
     * 淮安流程创建时将是否过户信息为是存入数据库
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //判断配置了水电气的流程是否包含当前流程
        if (StringUtils.isNotBlank(pzsdqlc) && StringToolUtils.existItemEquals(initServiceDTO.getBdcXm().getGzldyid(),pzsdqlc.split(","))){
            BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
            bdcSdqywQO.setGzlslid(initServiceDTO.getBdcXm().getGzlslid());
            List<BdcSdqghDO> sdqxx = bdcSdqghFeignService.querySdqywOrder(bdcSdqywQO);
            if (CollectionUtils.isEmpty(sdqxx)) {
                BdcSdqghDO bdcSdqghDO = new BdcSdqghDO();
                //水过户
                bdcSdqghDO.setId(UUIDGenerator.generate16());
                bdcSdqghDO.setYwlx(BdcSdqEnum.S.key());
                bdcSdqghDO.setSfbl(CommonConstantUtils.SF_S_DM);
                bdcSdqghDO.setGzlslid(initServiceDTO.getBdcXm().getGzlslid());
                bdcSdqghFeignService.insertSdqghDO(bdcSdqghDO);
                //电过户
                bdcSdqghDO.setId(UUIDGenerator.generate16());
                bdcSdqghDO.setYwlx(BdcSdqEnum.D.key());
                bdcSdqghFeignService.insertSdqghDO(bdcSdqghDO);
                //气过户
                bdcSdqghDO.setId(UUIDGenerator.generate16());
                bdcSdqghDO.setYwlx(BdcSdqEnum.Q.key());
                bdcSdqghFeignService.insertSdqghDO(bdcSdqghDO);

            }else {
                //存在则进行更新
                for (BdcSdqghDO bdcSdqghDO : sdqxx){
                    bdcSdqghDO.setSfbl(CommonConstantUtils.SF_S_DM);
                    entityMapper.updateByPrimaryKeySelective(bdcSdqghDO);
                }
            }

        }
        return initServiceDTO;
    }
}
