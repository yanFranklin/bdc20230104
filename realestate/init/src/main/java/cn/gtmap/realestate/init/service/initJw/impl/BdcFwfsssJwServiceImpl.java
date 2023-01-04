package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.realestate.common.core.domain.BdcFwfsssDO;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 房屋附属设施结尾处理服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-01-18 14:13
 **/
@Service
public class BdcFwfsssJwServiceImpl extends InitBdcJwService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFwfsssJwServiceImpl.class);

    @Autowired
    BdcdyFeignService bdcdyFeignService;
    @Autowired
    protected DozerBeanMapper initDozerMapper;
    @Autowired
    EntityMapper entityMapper;

    /**
     * 初始化入库数据之后的服务
     *
     * @param initResultDTO 初始化后的数据
     * @param listQO
     * @throws Exception
     */
    @Override
    public void initJw(InitResultDTO initResultDTO, List<InitServiceQO> listQO) throws Exception {
        //非首次业务，先查询数据库是否存在附属设施，不存在查权籍子户室信息
        //非首次判断逻辑-- 是否存在yxmid ，首次yxmid 为空，非首次会存在上一手的xmid
        if (CollectionUtils.isNotEmpty(listQO)) {
            String yxmid = listQO.get(0).getYxmid();
            if (StringUtils.isNotBlank(yxmid) && CollectionUtils.isEmpty(initResultDTO.getBdcFwfsssDOList())) {
                List<BdcFwfsssDO> bdcFwfsssDOList = new ArrayList<>(1);
                for (InitServiceQO initServiceQO : listQO) {
                    //查上一手有没有附属设施
                    Example example = new Example(BdcFwfsssDO.class);
                    example.createCriteria().andEqualTo("xmid", initServiceQO.getYxmid());
                    bdcFwfsssDOList = entityMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(bdcFwfsssDOList)) {
                        LOGGER.info("当前单元号{}项目id{}查询上一手xmid={}附属设施有数据带入附属设施", initServiceQO.getBdcdyh(), initServiceQO.getXmid(), initServiceQO.getYxmid());
                        for (BdcFwfsssDO bdcFwfsssDO : bdcFwfsssDOList) {
                            bdcFwfsssDO.setFwfsssid(UUIDGenerator.generate16());
                            bdcFwfsssDO.setXmid(initServiceQO.getXmid());
                        }
                    } else {
                        bdcFwfsssDOList = new ArrayList<>(1);
                        if (StringUtils.isNotBlank(initServiceQO.getBdcdyh())) {
                            LOGGER.info("当前单元号{},上一手无附属设施，重新查询权籍数据，bdcdyfwlx={}", initServiceQO.getBdcdyh(), initServiceQO.getBdcXm().getBdcdyfwlx());
                            BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryBdcdy(initServiceQO.getBdcdyh(), String.valueOf(initServiceQO.getBdcXm().getBdcdyfwlx()), initServiceQO.getQjgldm());
                            if (Objects.nonNull(bdcdyResponseDTO) && CollectionUtils.isNotEmpty(bdcdyResponseDTO.getFwZhsList())) {
                                for (FwZhsDO fwZhs : bdcdyResponseDTO.getFwZhsList()) {
                                    BdcFwfsssDO bdcFwfsss = new BdcFwfsssDO();
                                    initDozerMapper.map(initServiceQO.getBdcXm(), bdcFwfsss);
                                    // 通过户室信息赋值
                                    initDozerMapper.map(initServiceQO.getBdcdyResponseDTO(), bdcFwfsss);
                                    // 通过子户室信息赋值
                                    initDozerMapper.map(fwZhs, bdcFwfsss);
                                    bdcFwfsssDOList.add(bdcFwfsss);
                                }
                            }
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(bdcFwfsssDOList)) {
                    entityMapper.insertBatchSelective(bdcFwfsssDOList);
                }

            }
        }

    }

    @Override
    public List<String> getVersion() {
        return Collections.singletonList(CommonConstantUtils.SYSTEM_VERSION_CZ);
    }
}
