package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.enums.BdcSdlxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.core.service.BdcZsService;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import org.springframework.beans.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/26.
 * @description 常州冻结业务走流程 初始化时生成证书锁定信息
 */
@Service
@ConfigurationProperties(prefix = "djxl")
public class BdcZssdInitServiceImpl extends InitBdcJwService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZssdInitServiceImpl.class);
    //证书锁定登记小类
    private List<String> zssd;

    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    @Lazy
    private BdcXmService bdcXmService;

    @Autowired
    private BdcSdFeignService bdcSdFeignService;

    @Override
    public void initJw(InitResultDTO initResultDTO, List<InitServiceQO> listQO) throws Exception {
        // 1、判断当前是否办理证书锁定业务
        if(CollectionUtils.isEmpty(zssd) ||  !zssd.contains(listQO.get(0).getBdcXm().getDjxl())){
            return;
        }
        List<BdcZssdDO> bdcZssdDOList = new ArrayList<>(10);
        List<BdcDysdDO> bdcDysdDOList = new ArrayList<>(10);
        // 2、根据listQO 中yxmid 是否有值，判定为产权还是单元号创建，有yxmid为产权创建，否则为单元号创建
        for(BdcXmDO bdcXmDO : initResultDTO.getBdcXmList()){
            if(Objects.nonNull(bdcXmDO) && StringUtils.isNotBlank(bdcXmDO.getXmid()) && StringUtils.isNotBlank(bdcXmDO.getGzlslid())){
                String yxmid = bdcXmService.queryYxmid(bdcXmDO.getXmid());
                if(StringUtils.isNotBlank(yxmid)){
                    // 2.1 生成证书锁定数据
                    this.addBdcZssd(bdcZssdDOList, yxmid, bdcXmDO);
                }else{
                    // 2.2 生成单元锁定数据
                    this.addBdcDysd(bdcDysdDOList, yxmid, bdcXmDO);
                }
            }
        }

        if(CollectionUtils.isNotEmpty(bdcZssdDOList)){
            bdcSdFeignService.sdBdczsNoSdyy(bdcZssdDOList);
        }

        if(CollectionUtils.isNotEmpty(bdcDysdDOList)){
            bdcSdFeignService.scSdBdcdyxx(bdcDysdDOList);
        }
    }

    /**
     * 生成证书锁定数据
     */
    private void addBdcZssd(List<BdcZssdDO> bdcZssdDOList, String yxmid, BdcXmDO bdcXmDO){
        List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(yxmid);
        if(CollectionUtils.isEmpty(bdcZsDOList)){
            LOGGER.error("生成冻结数据，证书信息异常，xmid为：{},yxmid为：{}", bdcXmDO.getXmid(), yxmid);
            throw new AppException("生成冻结数据，证书信息异常!");
        }
        for(BdcZsDO bdcZsDO : bdcZsDOList){
            if(Objects.nonNull(bdcZsDO) && StringUtils.isNotBlank(bdcZsDO.getZsid())){
                // 查询当前证书是否已经放入过锁定集合里，一证多房只生成一条zssd数据
                List<BdcZssdDO> zssdTemp = bdcZssdDOList.stream().filter(zs->StringUtils.equals(zs.getZsid(),bdcZsDO.getZsid())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(zssdTemp)){
                    BdcZssdDO bdcZssdDO = new BdcZssdDO();
                    bdcZssdDO.setBdclx(bdcXmDO.getBdclx());
                    bdcZssdDO.setGzlslid(bdcXmDO.getGzlslid());
                    bdcZssdDO.setZsid(bdcZsDO.getZsid());
                    bdcZssdDO.setCqzh(bdcZsDO.getBdcqzh());
                    bdcZssdDO.setXmid(bdcXmDO.getXmid());
                    bdcZssdDO.setSdlx(BdcSdlxEnum.ZSSD.getCode());
                    bdcZssdDOList.add(bdcZssdDO);
                }
            }
        }
    }

    /**
     * 添加单元锁定信息
     */
    private void addBdcDysd(List<BdcDysdDO> bdcDysdDOList, String yxmid, BdcXmDO bdcXmDO){
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        BeanUtils.copyProperties(bdcXmDO, bdcDysdDO);
        bdcDysdDO.setSdlx(BdcSdlxEnum.ZSSD.getCode());
        bdcDysdDO.setCqxmid(yxmid);
        bdcDysdDOList.add(bdcDysdDO);
    }

    public List<String> getZssd() {
        return zssd;
    }

    public void setZssd(List<String> zssd) {
        this.zssd = zssd;
    }
}
