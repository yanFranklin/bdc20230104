package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPrintDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 打印方法
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-31 13:53
 **/
@RestController()
@RequestMapping("/realestate-etl/rest/v1.0/print")
public class PrintRestController {
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    /**
     * 默认打印地址
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;
    @Autowired
    private PdfController pdfController;

    /**
     * @param dylx    打印类型
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 页面打印商品房网签信息
     * @date : 2020/12/31 15:27
     */
    @GetMapping("/spf/{dylx}/{gzlslid}/xml")
    String printDjbcx(@PathVariable(name = "dylx") String dylx, @PathVariable(name = "gzlslid") String gzlslid) {
        if (StringUtils.isAnyBlank(dylx, gzlslid)) {
            throw new AppException("打印商品房网签信息缺失打印类型和工作流实例id");
        }
        Map<String, List<Map>> params = new HashMap<>(1);
        List<Map> mapList = new ArrayList<>(1);
        Map<String, String> printMap = new HashMap(1);
        printMap.put("gzlslid", gzlslid);
        mapList.add(printMap);
        params.put(dylx, mapList);
        return bdcPrintFeignService.print(params);
    }

    @GetMapping("/htbaxx/{dylx}/{baid}/xml")
    String printHtbaxx(@PathVariable(name = "dylx") String dylx, @PathVariable(name = "baid") String baid){
        HashMap<String, List<Map>>  params = new HashMap<>(1);
        buildPrintParams(params,dylx,baid);
        return bdcPrintFeignService.print(params);
    }

    @ResponseBody
    @GetMapping("/htbaxx/pdf")
    public void printPdf(HttpServletResponse response, @RequestParam(name = "dylx", required = false) String dylx, @RequestParam(name = "baid", required = false) String baid,
                         @RequestParam(name = "slbh", required = false) String slbh) {
        String xml = "";
        HashMap<String, List<Map>>  params = new HashMap<>(1);
        buildPrintParams(params,dylx,baid);
        xml = bdcPrintFeignService.print(params);
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath  + dylx +CommonConstantUtils.WJHZ_DOCX);
        pdfExportDTO.setFileName(dylx + slbh);
        pdfExportDTO.setXmlData(xml);
        pdfController.exportPdf(response, pdfExportDTO);
    }

    private void buildPrintParams(HashMap<String, List<Map>>  params, String dylx, String baid){
        if(StringUtils.isAnyBlank(dylx,baid)){
            throw new AppException("打印合同备案信息缺失打印类型和合同备案id");
        }
        List<Map> mapList = new ArrayList<>(1);
        Map<String, String> printMap = new HashMap(1);
        printMap.put("baid",baid);
        mapList.add(printMap);
        params.put(dylx, mapList);
    }

}
