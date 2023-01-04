package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: realestate
 * @description: 司法裁决初始化后生成单元锁定的数据
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-08-19 08:35
 **/
@Service
public class BdcSfcjDysdServiceImpl extends InitBdcJwService {
    @Value("#{'${sfcj.dysd.gzldyid:}'.split(',')}")
    private List<String> sfcjGzldyid;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    BdcXmService bdcXmService;

    /**
     * 初始化入库数据之后的服务
     *
     * @param initResultDTO 初始化后的数据
     * @param listQO
     * @throws Exception
     */
    @Override
    public void initJw(InitResultDTO initResultDTO, List<InitServiceQO> listQO) throws Exception {
        if (CollectionUtils.isNotEmpty(sfcjGzldyid) && sfcjGzldyid.contains(initResultDTO.getBdcXmList().get(0).getGzldyid())) {
            List<BdcDysdDO> bdcDysdDOList = new ArrayList<>(initResultDTO.getBdcXmList().size());
            UserDto userDto = userManagerUtils.getCurrentUser();
            for (BdcXmDO bdcXmDO : initResultDTO.getBdcXmList()) {
                BdcDysdDO bdcDysdDO = new BdcDysdDO();
                bdcDysdDO.setDysdid(UUIDGenerator.generate16());
                bdcDysdDO.setBdcdyh(bdcXmDO.getBdcdyh());
                bdcDysdDO.setBdcdywybh(bdcXmDO.getBdcdywybh());
                bdcDysdDO.setBdclx(bdcXmDO.getBdclx());
                //初始化默认锁定状态为空
//                bdcDysdDO.setSdzt(CommonConstantUtils.SF_F_DM);
                //裁决锁定 sdlx=9
                bdcDysdDO.setSdlx(9);
                if (userDto != null) {
                    bdcDysdDO.setSdr(userDto.getAlias());
                    bdcDysdDO.setSdrid(userDto.getId());
                }
                bdcDysdDO.setXmid(bdcXmDO.getXmid());
                bdcDysdDO.setGzlslid(bdcXmDO.getGzlslid());
                //如果是限制权力办理司法裁决，找到最初的产权数据
//                List<BdcXmDO> bdcXmDOList  = bdcXmFeignService.listPrevCqXm(bdcXmDO.getXmid(),true);
                List<BdcXmDO> bdcXmDOList = bdcXmService.listPrevCqXm(bdcXmDO.getXmid(), new ArrayList<>(), true);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    bdcDysdDO.setCqxmid(bdcXmDOList.get(0).getXmid());
                }
                bdcDysdDOList.add(bdcDysdDO);
            }
            if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
                entityMapper.insertBatchSelective(bdcDysdDOList);
            }
        }
    }

    /**
     * 获取服务版本
     *
     * @return
     */
    @Override
    public List<String> getVersion() {
        return Collections.singletonList(CommonConstantUtils.SYSTEM_VERSION_CZ);
    }
}
