package cn.gtmap.realestate.common.core.support.request;

import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.config.BdcPdfDyQO;
import cn.gtmap.realestate.common.core.service.feign.config.BdcDysjPzFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.RestRpcUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/4/2
 * @description 打印配置公共方法
 */

@RestController
@RequestMapping(value = "/rest/v1.0/dypz/common")
public class DypzController {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DypzController.class);

    @Autowired
    private BdcDysjPzFeignService bdcDysjPzFeignService;
    @Autowired
    private PdfController pdfController;
    @Autowired
    private RestRpcUtils restRpcUtils;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * @param dylxList 打印类型数组
     * @return Map 各个打印类型对应的配置信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取各个打印类型的配置信息
     */
    @PostMapping(value = "/pzxx")
    public Map getBdcDysjPz (@RequestBody List<String> dylxList){
        if (CollectionUtils.isEmpty(dylxList)){
            throw  new MissingArgumentException("查询打印配置信息，但是当前参数 dylxList 为空。请检查！");
        }
        return bdcDysjPzFeignService.queryBdcDysjPzByDylx(dylxList);
    }

    /**
     * @param bdcPdfDyQO pdf打印参数
     * @return String redis保存的key值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将pdf打印所需的参数保存到redis中，并返回redis的key值
     */
    @PostMapping("/pdf/param/redis")
    public String sendXmlToRedis(@RequestBody BdcPdfDyQO bdcPdfDyQO) {
        LOGGER.info("打印数据参数信息：{}", JSON.toJSONString(bdcPdfDyQO));
        return redisUtils.addStringValue(UUIDGenerator.generate16(), JSONObject.toJSONString(bdcPdfDyQO), 600);
    }

    /**
     * @param redisKey redis的key值
     * @return pdf流信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据事先保存在redis中的pdf参数，生成pdf信息
     */
    @GetMapping("/pdf/{redisKey}")
    public void getPdfViewData(HttpServletResponse response,
                               @PathVariable(value = "redisKey") String redisKey,
                               @RequestParam(name = "localFile", required = false)String localFile) {
        // 获取 redis 的参数
        String paramJson = redisUtils.getStringValue(redisKey);

        BdcPdfDyQO bdcPdfDyQO = JSONObject.parseObject(paramJson, BdcPdfDyQO.class);
        if (null == bdcPdfDyQO) {
            throw new AppException("PDF打印预览失败，原因：未获取到参数信息！");
        }

        // 获取打印XML数据
        String xmlData = this.getPrintDataXml(bdcPdfDyQO);
        if (StringUtils.isBlank(xmlData)) {
            throw new MissingArgumentException("证明单导出pdf中止，原因：未获取到数据信息！");
        }

        // 调用PDF打印服务
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(bdcPdfDyQO.getPdfpath());
        /// 文件名称临时用打印类型代替下
        pdfExportDTO.setFileName(bdcPdfDyQO.getFileName());
        pdfExportDTO.setXmlData(xmlData);
        pdfExportDTO.setLocalFile(localFile);
        pdfController.exportPdf(response, pdfExportDTO);
    }

    /**
     * @param bdcPdfDyQO pdf打印参数
     * @return String xml结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取打印的xml信息
     */
    @GetMapping("/xml")
    public String getPrintDataXml(BdcPdfDyQO bdcPdfDyQO) {
        try {
            String result = restRpcUtils.getRpcRequest(bdcPdfDyQO.getAppName(), bdcPdfDyQO.getDataUrl(), null);
            return result;
        } catch (Exception e) {
            LOGGER.error("获取打印的xml信息,执行GET方式请求RPC调用发生异常：{}", e.getMessage());
            throw new AppException("获取打印的xml信息,执行GET方式请求RPC调用发生异常：" + e.getMessage());
        }
    }
}
