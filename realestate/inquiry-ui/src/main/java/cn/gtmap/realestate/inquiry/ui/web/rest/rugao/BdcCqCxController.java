package cn.gtmap.realestate.inquiry.ui.web.rest.rugao;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.inquiry.FccxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.inquiry.rugao.BdcDyCfCqCxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.rugao.BdcCqCxFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.gtmap.realestate.common.util.PageUtils.addLayUiCode;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/01/19
 * @description
 */
@RestController
@RequestMapping(value = "/rest/v1.0/cqcx")
public class BdcCqCxController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCqCxController.class);

    @Autowired
    private BdcCqCxFeignService bdcCqCxFeignService;

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcDyCfCqcxQO
     * @return
     */
    @GetMapping("/dycfcq/page/search")
    @ResponseStatus(code = HttpStatus.OK)
    public Object listDycfByPage(@LayuiPageable Pageable pageable, BdcDyCfCqCxQO bdcDyCfCqcxQO) {
        if(StringUtils.isEmpty(bdcDyCfCqcxQO.getQlrmc()) && StringUtils.isEmpty(bdcDyCfCqcxQO.getQlrzjh())){
            throw new AppException("请输入必要查询条件！");
        }
        return addLayUiCode(bdcCqCxFeignService.dycfcqCx(pageable, JSON.toJSONString(bdcDyCfCqcxQO)));
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param baseQO
     * @return
     */
    @PostMapping("/dycfcq/search")
    @ResponseStatus(code = HttpStatus.OK)
    public Object listCqxxByBdchyhList(@RequestBody BaseQO baseQO) {
        return bdcCqCxFeignService.listCqxxByBdchyhList(baseQO);
    }


    @PostMapping("/import/excel")
    @ResponseStatus(code = HttpStatus.OK)
    public Object configImportToExcel(MultipartHttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return null;
        }
        InputStream fileIn = null;
        List dataList = new ArrayList();
        try {
            Iterator<String> iterator = httpServletRequest.getFileNames();
            // 遍历所有上传文件
            while (iterator.hasNext()) {
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // 根据指定的文件输入流导入Excel从而产生Workbook对象
                Workbook workbook = Workbook.getWorkbook(fileIn);
                Sheet dataSheet = workbook.getSheet(0);
                Field[] fieldExportDataList = FccxDO.class.getDeclaredFields();

                // 遍历Excel
                for (int i = 1; i < dataSheet.getRows(); i++) {
                    Object dataObject = FccxDO.class.newInstance();
                    for (int j = 0; j < fieldExportDataList.length; j++) {
                        fieldExportDataList[j].setAccessible(true);
                        String contentData = dataSheet.getCell(j, i).getContents();
                        // 判断属性的类型
                        if (org.apache.commons.lang.StringUtils.equals(fieldExportDataList[j].getType().toString(), "class java.lang.String")) {
                            fieldExportDataList[j].set(dataObject, contentData);
                        } else if (org.apache.commons.lang.StringUtils.equals(fieldExportDataList[j].getType().toString(), "int")
                                || org.apache.commons.lang.StringUtils.equals(fieldExportDataList[j].getType().toString(), "Integer")) {
                            fieldExportDataList[j].set(dataObject, Integer.valueOf(contentData));
                        } else if (org.apache.commons.lang.StringUtils.equals(fieldExportDataList[j].getType().toString(), "class java.util.Date")) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ROOT);
                            Date dateData = simpleDateFormat.parse(contentData);
                            fieldExportDataList[j].set(dataObject, dateData);
                        }
                    }
                    dataList.add(dataObject);
                }
            }
            return dataList;
        } catch (IOException | BiffException | IllegalAccessException | InstantiationException | ParseException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new AppException("导入失败");
        } finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
        }
    }
}
