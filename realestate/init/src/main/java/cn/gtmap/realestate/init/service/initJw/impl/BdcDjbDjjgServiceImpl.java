package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.realestate.common.core.domain.BdcBdcdjbDO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 登记簿 登记机构名称对照
 * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
 * @version 1.0  2022/10/26.
 * @description
 */
@Service
public class BdcDjbDjjgServiceImpl extends InitBdcJwService {
    private static Logger logger = LoggerFactory.getLogger(BdcDjbDjjgServiceImpl.class);

    /**
     * 登记簿名称对照
     */
    @Value("${djbdz:false}")
    private Boolean djbdz;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Autowired
    EntityMapper entityMapper;

    /**
     * 初始化入库数据之后的服务
     *
     * @param initResultDTO 初始化后的数据
     * @throws Exception
     */
    @Override
    public void initJw(InitResultDTO initResultDTO,List<InitServiceQO> listQO) throws Exception {
        if(djbdz){
            String qxdm = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getCurrentUserName());
            if(StringUtils.isNotBlank(qxdm)){
                // 初始化登记机构名称字典获取
                List<Map> zdMapList = bdcZdFeignService.queryBdcZd("cshdjbmc");
                String djjg = "";
                if (CollectionUtils.isNotEmpty(zdMapList)) {
                    for (Map map : zdMapList) {
                        if (StringUtils.isNotBlank(MapUtils.getString(map, "MC")) && qxdm.equals(MapUtils.getString(map, "DM"))) {
                            djjg = MapUtils.getString(map, "MC");
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(initResultDTO.getBdcdjbList())){
                    for (BdcBdcdjbDO bdcBdcdjbDO : initResultDTO.getBdcdjbList()) {
                        bdcBdcdjbDO.setDjjg(djjg);
                        entityMapper.saveOrUpdate(bdcBdcdjbDO,bdcBdcdjbDO.getZdzhh());
                    }
                }
            }
        }
    }
}
