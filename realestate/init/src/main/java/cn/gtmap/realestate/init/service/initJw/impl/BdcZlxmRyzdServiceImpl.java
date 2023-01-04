package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 增量初始化更新冗余字段
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-08 10:34
 **/
@Service
public class BdcZlxmRyzdServiceImpl extends InitBdcJwService {

    @Autowired
    BdcRyzdFeignService bdcRyzdFeignService;

    /**
     * 初始化入库数据之后的服务
     *
     * @param initResultDTO 初始化后的数据
     * @param listQO
     * @throws Exception
     */
    @Override
    public void initJw(InitResultDTO initResultDTO, List<InitServiceQO> listQO) throws Exception {
        //增量初始化更新冗余字段
        if (CollectionUtils.isNotEmpty(listQO) && Objects.nonNull(listQO.get(0).getBdcCshFwkgSl()) && CommonConstantUtils.SF_S_DM.equals(listQO.get(0).getBdcCshFwkgSl().getSfzlcsh())) {
            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(initResultDTO.getBdcXmList().get(0).getGzlslid());
        }
    }
}
