package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJbxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSwFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0  2022-12-12
 * @description 税务相关处理
 */
@Service
public class BdcSwServiceImpl {

    protected static Logger LOGGER = LoggerFactory.getLogger(BdcSwServiceImpl.class);

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    BdcSwFeignService bdcSwFeignService;

    private static final String SBTXH = "申报提醒函";

    /**
     * 申报提醒函下载地址映射 IP 和 端口
     */
    @Value("${swjk.sbtxh.ip_port:59.203.19.50:9790}")
    protected String sbtxhIpPort;

    /**
     * @param querySwxxResponseDTO
     * @return Map
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 推送税款报文-德宽版本
     */
    public Map swTssk_dk(QuerySwxxResponseDTO querySwxxResponseDTO) {
        LOGGER.info("推送税款报文-德宽版本，请求报文数据：{}", querySwxxResponseDTO);
        Map map = new HashMap();
        map.put("message_code", "100");
        map.put("message_content", "该笔交易未查询到");
        if (Objects.isNull(querySwxxResponseDTO)) {
            return map;
        }
        if (!StringUtils.equals("已申报待缴款", querySwxxResponseDTO.getResponseMsg())) {
            return map;
        }
        String fwuuid = querySwxxResponseDTO.getFwuuid();
        if (StringUtils.isBlank(fwuuid)) {
            return map;
        }
        // 根据推送税务返回的唯一标识查询基本信息
        BdcSlJbxxQO bdcSlJbxxQO = new BdcSlJbxxQO();
        bdcSlJbxxQO.setFwuuid(fwuuid);
        List<BdcSlJbxxDO> bdcSlJbxxDOList = bdcSlJbxxFeignService.listBdcSlJbxxByJbxxQO(bdcSlJbxxQO);
        if (CollectionUtils.isEmpty(bdcSlJbxxDOList)) {
            return map;
        }
        String gzlslid = bdcSlJbxxDOList.get(0).getGzlslid();
        if (StringUtils.isBlank(gzlslid)) {
            return map;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return map;
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        // 申报提醒函上传
        uploadSbtxh(querySwxxResponseDTO, gzlslid);
        bdcSwFeignService.handleQuerySwxxResponse(querySwxxResponseDTO, bdcXmDO.getXmid(), CommonConstantUtils.SW_GXLX_INSERT_UPDATE);
        map.put("message_code", "200");
        map.put("message_content", "消息接受成功");
        return map;
    }

    /**
     * 根据url下载申报提醒函附件，上传大云
     *
     * @param responseDTO 税务明细返回参数
     * @param gzlslid     工作流实例ID
     */
    private void uploadSbtxh(QuerySwxxResponseDTO responseDTO, String gzlslid) {
        String url = responseDTO.getSbtxhfj();
        // 申报提醒函
        if (StringUtils.isNotEmpty(url)) {
            // 上传申报提醒函
            String fileName = "申报提醒函_" + System.currentTimeMillis() + ".png";
            // 处理url
            String oldIpPort = url.substring(url.indexOf("//") + 2, url.indexOf('/', url.indexOf("//") + 2));
            url = url.replace(oldIpPort, sbtxhIpPort);
            BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", fileName, SBTXH, CommonConstantUtils.WJHZ_PNG);
            bdcPdfDTO.setPdfUrl(url);
            try {
                bdcUploadFileUtils.uploadPdfByUrl(bdcPdfDTO);
            } catch (Exception e) {
                LOGGER.error("申报提醒函上传大云失败，下载url地址:{} ，异常信息：{}", url, e);
            }
        }
    }

}
