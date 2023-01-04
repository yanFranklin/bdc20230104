package cn.gtmap.realestate.accept.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dto.etl.BdcCzRlsbDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0, 2021/01/16
 * @description 常州人脸识别
 */
@RestController
@RequestMapping("/rest/v1.0/rlsb")
@Api(tags = "人脸识别")
public class BdcRlsbController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcRlsbController.class);

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    /**
     * 根据工作流实例id获取xmid和ywlx
     */
    @GetMapping(value = "/changzhou/get/rlsb/ywlx")
    public BdcCzRlsbDTO changzhouRlsbYwlx(@RequestParam(name="gzlslid")String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)){
            LOGGER.info("开始查询人脸识别业务类型入参:{}",gzlslid);
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOS = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOS)){
                LOGGER.info("查询的项目相关信息");
                int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDOS);
                if (CommonConstantUtils.LCLX_ZH.equals(lclx) && bdcXmDOS.stream().anyMatch(bdcXmDO -> CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx()))) {
                    LOGGER.info("当前流程为存在抵押流程的组合流程");
                    // 组合流程区别处理 带抵押的组合流程只获取转移项目的信息
                    Optional<BdcXmDO> any = bdcXmDOS.stream().filter(bdcXmDO -> !CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())).findAny();
                    if (any.isPresent()){
                        LOGGER.info("当前组合流程存在转移");
                        BdcXmDO bdcXmDO = any.get();
                        return getBdcCzRlsbDTO(bdcXmDO);
                    }
                }else {
                    LOGGER.info("当前流程为普通流程");
                    BdcXmDO bdcXmDO = bdcXmDOS.get(0);
                    return getBdcCzRlsbDTO(bdcXmDO);
                }
            }
        }
        return null;
    }

    private BdcCzRlsbDTO getBdcCzRlsbDTO(BdcXmDO bdcXmDO){
        LOGGER.info("根据流程定义id获取对应的人脸识别的业务类型:{}",bdcXmDO.getGzldyid());
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setDsfxtbs("czrlsb");
        bdcZdDsfzdgxDO.setZdbs("BDC_CZ_RLSB_YWLX");
        List<BdcZdDsfzdgxDO> zdList = bdcZdFeignService.queryDsfZdxBybs(bdcZdDsfzdgxDO);
        if (CollectionUtils.isNotEmpty(zdList)){
            Optional<BdcZdDsfzdgxDO> any = zdList.stream().filter(zdDsfzdgxDo -> bdcXmDO.getGzldyid().equals(zdDsfzdgxDo.getBdczdz())).findAny();
            if (any.isPresent()){
                LOGGER.info("根据流程定义id获取对应的人脸识别的业务类型结束");
                return BdcCzRlsbDTO.BdcCzRlsbDTOBuilder.aBdcCzRlsbDTO().withXmid(bdcXmDO.getXmid()).withYwlx(any.get().getDsfzdz()).build();
            }
        }
        return null;
    }

}
