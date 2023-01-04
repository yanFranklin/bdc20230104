package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.moke.MokeZzdzjPlszDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/1/12
 * @description 合肥摩科自助打证机相关服务
 */
@OpenController(consumer = "合肥摩科自助打证机相关服务")
@RestController
@RequestMapping({"/realestate-exchange/rest/v1.0/hefei/moke", "/realestate-exchange/rest/v1.0/moke"})
@Api(tags = "合肥摩科自助打证机相关服务")
public class HefeiZzdzjController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HefeiZzdzjController.class);

    @Autowired
    BdcXmMapper bdcXmMapper;

    @Autowired
    ZdFeignService zdFeignService;

    @Autowired
    ExchangeBeanRequestService exchangeBeanRequestService;

    @Value("#{'${tsmoke.lzfs:}'.split(',')}")
    private List<Integer> lzfsList;


    /**
     * qrcode1地址配置
     */
    @Value("${mk.qrcode1Url:}")
    private String qrcode1Url;

    /**
     * qrcode取值配置
     */
    @Value("${mk.qrcodepj: false}")
    private boolean qrcodepj;

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="huangjian@gtmap.cn">huangjian</a>
     * @rerutn
     * @description 摩科自助打证机批量缮证接口-工作流事件触发
     */
    @ApiOperation(value = "批量缮证推送接口")
    @ResponseStatus(code = HttpStatus.OK)
    @DsfLog(logEventName = "批量缮证推送接口", dsfFlag = "MK", requester = "BDC", responser = "MK")
    @PostMapping("plsz/processInsId")
    @ResponseBody
    public void mokePlsz(@RequestParam(value = "processInsId") String processInsId) {
        LOGGER.info("批量缮证推送接口gzlslid:{}", processInsId);

        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺少工作流实例id！请检查");
        }
        List<MokeZzdzjPlszDTO> mokeZzdzjPlszDTOList = bdcXmMapper.queryMokeZsxx(processInsId, lzfsList);
        if (CollectionUtils.isNotEmpty(mokeZzdzjPlszDTOList)) {
            for (MokeZzdzjPlszDTO mokeZzdzjPlszDTO : mokeZzdzjPlszDTOList) {
                //对qrcode1赋值操作，固定地址+bdcdyh
                if (StringUtils.isNotBlank(qrcode1Url)) {
                    mokeZzdzjPlszDTO.setQRCode1(qrcode1Url + mokeZzdzjPlszDTO.getBDCDYH());
                }
                //对qrcode赋值操作，qlr+不动产证号
                if (qrcodepj) {
                    mokeZzdzjPlszDTO.setQRCode(mokeZzdzjPlszDTO.getQLR() + "、" + mokeZzdzjPlszDTO.getBDCQZH());
                }
                //查询宗地图
                ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(mokeZzdzjPlszDTO.getBDCDYH(), "");
                if (null != zdtResponseDTO) {
                    mokeZzdzjPlszDTO.setZDT(zdtResponseDTO.getBase64());
                }
            }
            try {
                Object response = exchangeBeanRequestService.request("moke_zzdzj_plts", mokeZzdzjPlszDTOList);
                LOGGER.info("推送摩科自助打证机批量返回结果：{}", response.toString());

            } catch (Exception e) {
                LOGGER.info("推送摩科自助打证机批量报错：{}", e);
            }

        }

    }

}
