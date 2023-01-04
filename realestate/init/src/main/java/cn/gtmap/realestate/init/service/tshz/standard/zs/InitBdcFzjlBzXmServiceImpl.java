package cn.gtmap.realestate.init.service.tshz.standard.zs;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmZsGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.enums.BdcZslxEnum;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcZsTsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 发证记录备注  项目方式
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcFzjlBzXmServiceImpl")
public class InitBdcFzjlBzXmServiceImpl implements InitBdcZsTsService {

    @Autowired
    private InitServiceQoService initServiceQoService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private InitBeanFactory initBeanFactory;


    /**
     * 证书后置处理接口
     *
     * @param initZsDTO 证书集合
     * @param zsyl      是否预览
     * @return InitResultDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitResultDTO tshz(InitResultDTO initZsDTO, boolean zsyl, InitServiceQO initServiceQO) throws Exception {
        if (!zsyl && initZsDTO != null && CollectionUtils.isNotEmpty(initZsDTO.getBdcXmZsGxList())
                && CollectionUtils.isNotEmpty(initZsDTO.getBdcZsList())) {
            boolean hasBz = false;
            //获取一个项目取gzlslid
            BdcXmDO bdcXmDO = initServiceQoService.queryYbdcxm(initZsDTO.getBdcXmZsGxList().get(0).getXmid(), initServiceQO);
            //查看是否已经生成备注
            if (StringUtils.isNotBlank(bdcXmDO.getBz()) && bdcXmDO.getBz().indexOf("本。") > -1) {
                hasBz = true;
            }
            if (!hasBz && bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
                int xmlx = bdcXmService.bdcXmLx(bdcXmDO.getGzlslid());
                if (CommonConstantUtils.LCLX_PL.equals(xmlx)) {
                    String bz = generateFzjlBz(initZsDTO.getBdcZsList().get(0).getZslx(), initZsDTO.getBdcZsList().size());
                    bdcXmService.updateBatchXmBz(bz, null, bdcXmDO.getGzlslid(), null);
                } else {
                    //存储证书类型
                    Map<String, Integer> zsidZslxMap = new HashMap<>();
                    //项目证书关系分组
                    Map<String, List<BdcXmZsGxDO>> map = initZsDTO.getBdcXmZsGxList().stream().collect(Collectors.groupingBy(BdcXmZsGxDO::getXmid));
                    for (BdcZsDO bdcZsDO : initZsDTO.getBdcZsList()) {
                        zsidZslxMap.put(bdcZsDO.getZsid(), bdcZsDO.getZslx());
                    }
                    if (MapUtils.isNotEmpty(map)) {
                        for (List<BdcXmZsGxDO> list : map.values()) {
                            if (CollectionUtils.isNotEmpty(list)) {
                                String bz = generateFzjlBz(zsidZslxMap.get(list.get(0).getZsid()), list.size());
                                bdcXmService.updateBatchXmBz(bz, null, null, list.get(0).getXmid());
                            }
                        }
                    }
                }
            }
        }
        return initZsDTO;
    }

    /**
     * 处理备注
     *
     * @param zslx
     * @param size
     * @return
     */
    private String generateFzjlBz(Integer zslx, int size) {
        StringBuffer bz = new StringBuffer();
        if(initBeanFactory.getVersion().equals("bengbu") || initBeanFactory.getVersion().equals("standard") || initBeanFactory.getVersion().equals(CommonConstantUtils.SYSTEM_VERSION_HA)){
            if (CommonConstantUtils.ZSLX_ZS.equals(zslx)) {
                bz = bz.append(CommonConstantUtils.ZSLX_ZS_CN)
                        .append(CommonConstantUtils.ZF_ZW_MH)
                        .append(size)
                        .append("本。");
            }
            // 证明单或证明书（蚌埠）
            if (CommonConstantUtils.ZSLX_ZMD.equals(zslx)) {
                bz = bz.append(BdcZslxEnum.getZhlx(CommonConstantUtils.ZSLX_ZMD, initBeanFactory.getVersion()))
                        .append(CommonConstantUtils.ZF_ZW_MH)
                        .append(size)
                        .append("本。");
            }
        }else{
            if (CommonConstantUtils.ZSLX_ZS.equals(zslx) || CommonConstantUtils.ZSLX_ZMD.equals(zslx)) {
                bz = bz.append(CommonConstantUtils.ZSLX_ZS_CN)
                        .append(CommonConstantUtils.ZF_ZW_MH)
                        .append(size)
                        .append("本。");
            }
        }

        // 证明
        if (CommonConstantUtils.ZSLX_ZM.equals(zslx)) {
            bz = bz.append(CommonConstantUtils.ZSLX_ZM_CN)
                    .append(CommonConstantUtils.ZF_ZW_MH)
                    .append(size)
                    .append("本。");
        }
        return bz.toString();
    }
}
