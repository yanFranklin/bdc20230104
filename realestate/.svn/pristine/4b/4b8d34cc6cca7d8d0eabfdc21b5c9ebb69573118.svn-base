package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQszmQO;
import cn.gtmap.realestate.common.core.dto.pub.ResponseEntityDTO;
import cn.gtmap.realestate.common.core.dto.pub.ResponseHeadEntityDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcNtFczmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.service.impl.national.dzzz.*;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 * @date 2020/7/24 15:30
 * @description 南通权属证明信息接口
 */
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0")
@Api(tags = "不动产权属证明信息接口")
@Slf4j
public class BdcQszmRestController {
    @Autowired
    private BdcNtFczmFeignService bdcNtFczmFeignService;

    @Autowired
    BdcSlPrintFeignService bdcSlPrintFeignService;

    @Autowired
    private PdfController pdfController;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcPrintFeignService bdcPrintFeignService;

    // 默认打印地址
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    @Autowired
    DzqzGmxaService dzqzGmxaService;

    @OpenApi(apiDescription = "获取权属证明信息", apiName = "", openLog = false)
    @DsfLog(logEventName = "获取权属证明信息", dsfFlag = "QSZMXX", requester = "QSZMXX", responser = "BDC")
    @PostMapping("/qszm")
    public ResponseEntityDTO queryQszm(@RequestBody BdcQszmQO bdcQszmQO) {
        String time = DateUtils.formateYmdhms(new Date());

        ResponseEntityDTO response = new ResponseEntityDTO();
        response.setHead(new ResponseHeadEntityDTO());
        response.getHead().setResponsetime(time);

        if (null == bdcQszmQO || StringUtils.isAnyBlank(bdcQszmQO.getQlrzjh(), bdcQszmQO.getBdcqzh())) {
            response.getHead().setResponsecode(String.valueOf(HttpStatus.SC_BAD_REQUEST));
            response.getHead().setResponseinfo("参数产权证号、身份证号存在空");
            return response;
        }

        try {
            response.getHead().setResponsecode(String.valueOf(HttpStatus.SC_OK));
            response.getHead().setResponseinfo("成功");

            List<Map<String, Object>> qszmDTOList = bdcNtFczmFeignService.queryQszm(bdcQszmQO);
            if (CollectionUtils.isEmpty(qszmDTOList)) {
                response.getHead().setResponseinfo("未查询到房屋权属数据");
                return response;
            }

            response.setData(qszmDTOList);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.getHead().setResponsecode(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
            response.getHead().setResponseinfo("服务端异常，请联系管理员");
            return response;
        }
    }

    @OpenApi(apiDescription = "获取权属证明信息pdf签章文件", apiName = "", openLog = false)
    @DsfLog(logEventName = "获取权属证明信息pdf签章文件", dsfFlag = "QSZMXX", requester = "QSZMXX", responser = "BDC")
    @PostMapping("/qszm/pdf")
    public ResponseEntityDTO queryQszmPdf(@RequestParam("bdcqzh") String bdcqzh,
                                          @RequestParam(value = "xmid", required = false) String xmid,
                                          @RequestParam(value = "cxh", required = false) String cxh) {
        String time = DateUtils.formateYmdhms(new Date());

        ResponseEntityDTO response = new ResponseEntityDTO();
        response.setHead(new ResponseHeadEntityDTO());
        response.getHead().setResponsetime(time);
        //生成权属证明pdf文件
        BdcXmQO bdcXmQO = new BdcXmQO();
        if(StringUtils.isNotEmpty(xmid)) {
            bdcXmQO.setXmid(xmid);
        }
        bdcXmQO.setBdcqzh(bdcqzh);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        String dylx = "bdcZfxxDa";
        //获取数据源xml
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);
        Map<String, Object> mapTemp = new HashMap<>(1);
        mapTemp.put("bdcdyh", bdcXmDO.getBdcdyh());
        mapTemp.put("qszt", bdcXmDO.getQszt());
        mapTemp.put("gzlslid", bdcXmDO.getGzlslid());
        mapTemp.put("bdcqzh", bdcqzh);
        mapTemp.put("xmid", bdcXmDO.getXmid());
        mapTemp.put("bdclx", bdcXmDO.getBdclx());
        mapTemp.put("jbr", "");
        if (StringUtils.isNotBlank(cxh)) {
            mapTemp.put("cxh", cxh);
        } else {
            mapTemp.put("cxh", "");
        }
        bdcdyhListMap.add(mapTemp);
        paramMap.put(dylx, bdcdyhListMap);

        String xml = bdcPrintFeignService.print(paramMap);
        // 生成PDF文件
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + dylx + CommonConstantUtils.WJHZ_DOCX);
        String filename = bdcXmDO.getXmid() + "权属证明";
        pdfExportDTO.setFileName(filename);
        pdfExportDTO.setXmlData(xml);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);

        //签章
        try {
            String fileContent = dzqzGmxaService.getKeySignDzqzGmxa(filename, pdfFilePath);
            response.getHead().setResponsecode(String.valueOf(HttpStatus.SC_OK));
            response.getHead().setResponseinfo("成功");
            //返response回pdf文件内容
            response.setData(fileContent);
            log.info("权属状态pdf文件签章{}", JSON.toJSONString(response));
        } catch (Exception e) {
            throw new AppException("pdf文件签章失败，错误信息为：" + e.getMessage());
        }
        return response;
    }


}
