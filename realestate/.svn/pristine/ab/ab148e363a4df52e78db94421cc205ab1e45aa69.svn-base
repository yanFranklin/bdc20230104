package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcHzdytjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYbbdytjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcHzdytjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcHzdytjFeignService;
import cn.gtmap.realestate.inquiry.ui.util.ExportExcelUtils;
import cn.gtmap.realestate.inquiry.ui.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/07/10
 * @description 综合监管
 */
@RestController
@RequestMapping(value = "/hzdytj")
public class BdcHzdytjController extends BaseController {

    @Autowired
    private BdcHzdytjFeignService bdcHzdytjFeignService;
    @Value("${print.path:}")
    private String filePath;


    /**
     * 汇总抵押统计
     * @param bdcHzdytjQO
     * @return
     */
    @GetMapping(value = "/hz/list")
    public Object listHzdytj(@LayuiPageable Pageable pageable, BdcHzdytjQO bdcHzdytjQO) {
        List list = bdcHzdytjFeignService.listBdcHzdytj(JSON.toJSONString(bdcHzdytjQO));
        return super.addLayUiCode(PageUtils.listToPage(list,pageable));
    }

    /**
     * 汇总抵押统计
     * @param bdcHzdytjQO
     * @return
     */
    @GetMapping(value = "/hz/bb/list")
    public Object listHzdytjBb(@LayuiPageable Pageable pageable, BdcHzdytjQO bdcHzdytjQO) {
        return bdcHzdytjFeignService.listBdcHzdytjBb(JSON.toJSONString(bdcHzdytjQO));
        //return CollectionUtils.isNotEmpty(list)?list.get(0):new ArrayList<>();
        //return super.addLayUiCode(PageUtils.listToPage(list,pageable));
    }

    /**
     * 汇总抵押统计
     * @param bdcHzdytjQO
     * @return
     */
    @GetMapping(value = "/ybb/list")
    public Object listYbbdytj(@LayuiPageable Pageable pageable, BdcHzdytjQO bdcHzdytjQO) {
        List list = bdcHzdytjFeignService.listBdcYbbdytj(JSON.toJSONString(bdcHzdytjQO));
        return super.addLayUiCode(PageUtils.listToPage(list,pageable));
    }

    /**
     * 汇总抵押统计
     * @param bdcHzdytjQO
     * @return
     */
    @GetMapping(value = "/ybb/bb/list")
    public Object listYbbdytjBb(@LayuiPageable Pageable pageable, BdcHzdytjQO bdcHzdytjQO) {
        List list = bdcHzdytjFeignService.listBdcYbbdytj(JSON.toJSONString(bdcHzdytjQO));
        return super.addLayUiCode(PageUtils.listToPage(list,pageable));
    }

    /**
     * 汇总抵押统计
     * @param bdcHzdytjQO
     * @return
     */
    @GetMapping(value = "/export")
    public void export(HttpServletResponse response, @RequestParam(name = "hz", required = false) String hzJson,
                         @RequestParam(name = "ybb", required = false) String ybbJson) {
        List listhz = JSONArray.parseArray(hzJson);
        List listybb = JSONArray.parseArray(ybbJson);
        BdcHzdytjDTO bdcHzdytjDTO = new BdcHzdytjDTO();
        BdcYbbdytjDTO bdcYbbdytjDTO = new BdcYbbdytjDTO();
        // 汇总统计
        if(listhz.size() > 0){
            bdcHzdytjDTO = JSON.parseObject(JSON.toJSONString(listhz.get(0)), BdcHzdytjDTO.class);
        }
        // 月报表统计
        if(listybb.size() > 0){
            bdcYbbdytjDTO = JSON.parseObject(JSON.toJSONString(listybb.get(0)), BdcYbbdytjDTO.class);
        }
        ExportExcelUtils.exportHzdytj(filePath,bdcHzdytjDTO,bdcYbbdytjDTO);

        //浏览器下载
        String fileName = null;
        String pdfOpfile = filePath+ "model.xlsx";
        File file = new File(pdfOpfile);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            XSSFWorkbook workbook  = new XSSFWorkbook(new FileInputStream(file));
            fileName = URLEncoder.encode("汇总抵押统计.xlsx", "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            outputStream.flush();
            workbook.write(outputStream);
        } catch (Exception e) {
            LOGGER.error("输出流处理异常！");
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                LOGGER.error("流关闭异常异常！");
            }
        }

    }



}
