package cn.gtmap.realestate.init.service.tshz.standard.zs;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.enums.BdcZslxEnum;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcZsTsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 发证记录备注  流程方式
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcFzjlBzLcServiceImpl")
public class InitBdcFzjlBzLcServiceImpl implements InitBdcZsTsService {

    @Autowired
    private InitServiceQoService initServiceQoService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    private InitBeanFactory initBeanFactory;
    /**
     * 需要生成原证备注的工作流定义ID
     */
    @Value("#{'${nttscl.xmbz.yzhnum:}'.split(',')}")
    private List<String> yzhBzGzldyidList;

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
                StringBuffer bz = new StringBuffer();

                String gzlslid = bdcXmDO.getGzlslid();
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(yzhBzGzldyidList) && (null != bdcSlJbxxDO) && yzhBzGzldyidList.contains(bdcSlJbxxDO.getGzldyid())) {
                    // 生成流程原产权证数数量信息
                    String yzhBz = bdcXmService.generateXmBzYzhNum(gzlslid);
                    if (StringUtils.isNotBlank(yzhBz)) {
                        bz.append(yzhBz).append("\n");
                    }
                }
                int zssl = 0;
                int zmdsl = 0;
                int zmsl = 0;
                for (BdcZsDO bdcZsDO : initZsDTO.getBdcZsList()) {
                    if(initBeanFactory.getVersion().equals("bengbu") || initBeanFactory.getVersion().equals("standard") || initBeanFactory.getVersion().equals(CommonConstantUtils.SYSTEM_VERSION_HA)){
                        // 证书
                        if (CommonConstantUtils.ZSLX_ZS.equals(bdcZsDO.getZslx())) {
                            zssl++;
                        }
                        // 证明书或证明单
                        if (CommonConstantUtils.ZSLX_ZMD.equals(bdcZsDO.getZslx())) {
                            zmdsl++;
                        }
                    }else{
                        // 证书
                        if (CommonConstantUtils.ZSLX_ZS.equals(bdcZsDO.getZslx()) || CommonConstantUtils.ZSLX_ZMD.equals(bdcZsDO.getZslx())) {
                            zssl++;
                        }
                    }
                    // 证明
                    if (CommonConstantUtils.ZSLX_ZM.equals(bdcZsDO.getZslx())) {
                        zmsl++;
                    }
                }
                if (zssl > 0) {
                    bz = bz.append(CommonConstantUtils.ZSLX_ZS_CN)
                            .append(CommonConstantUtils.ZF_ZW_MH)
                            .append(zssl)
                            .append("本。");
                }
                if (zmdsl > 0) {
                    if (zssl > 0) {
                        bz.append(CommonConstantUtils.ZF_HH_CHAR);
                    }
                    bz = bz.append(BdcZslxEnum.getZhlx(CommonConstantUtils.ZSLX_ZMD, initBeanFactory.getVersion()))
                            .append(CommonConstantUtils.ZF_ZW_MH)
                            .append(zmdsl)
                            .append("本。");

                }
                if (zmsl > 0) {
                    if (zmdsl > 0 || (zssl > 0)) {
                        bz.append(CommonConstantUtils.ZF_HH_CHAR);
                    }
                    bz = bz.append(CommonConstantUtils.ZSLX_ZM_CN)
                            .append(CommonConstantUtils.ZF_ZW_MH)
                            .append(zmsl)
                            .append("本。");
                }
                bdcXmService.updateBatchXmBz(bz.toString(), null, bdcXmDO.getGzlslid(), null);
            }
        }
        return initZsDTO;
    }
}
