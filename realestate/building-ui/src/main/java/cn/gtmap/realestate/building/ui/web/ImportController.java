package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.util.Constants;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.building.ImportLpbRequestDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.building.LpbFeignService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.*;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2018/12/11
 * @description
 */
@Controller
@RequestMapping("import")
public class ImportController extends BaseController {

    @Autowired
    private LpbFeignService lpbFeignService;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportController.class);


    /**
     * 楼盘表导入模板下载地址
     */
    private static final String TEMP_PATH = "excel/lpbImportTemp.xls";


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param response
     * @return java.lang.String
     * @description 模板下载
     */
    @RequestMapping("/downtemp")
    public void downTemp(HttpServletResponse response)  {
        ClassPathResource res = new ClassPathResource(TEMP_PATH);
        if(res != null){
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            // 设置文件头
            response.setHeader("Content-disposition", "attachment; filename=lpbImportTemp.xls");
            // 设置输出类型
            response.setContentType("application/msexcel");

            try {
                bis = new BufferedInputStream(res.getInputStream());
                bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (FileNotFoundException e) {
                LOGGER.error("下载模板异常",e);
            } catch (IOException e) {
                LOGGER.error("下载模板异常",e);
            } finally {
                if(bis != null){
                    try {
                        bis.close();
                    } catch (IOException e) {
                        LOGGER.error("下载模板异常",e);
                    }
                }
                if(bos != null){
                    try {
                        bos.close();
                    } catch (IOException e) {
                        LOGGER.error("下载模板异常",e);
                    }
                }
            }
        }
    }


    /**
     * 导入楼盘表页面展现
     *
     * @param model
     * @param fwDcbIndex
     * @return
     */
    @RequestMapping(value = "/lpb")
    public String lpb(Model model, @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,String qjgldm) {
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        model.addAttribute("qjgldm", qjgldm);
        return "fwhs/importLpb";
    }

    /**
     * 导入预测楼盘表页面展现
     *
     * @param model
     * @param fwDcbIndex
     * @return
     */
    @RequestMapping(value = "/yclpb")
    public String yclpb(Model model, @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,String qjgldm) {
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        model.addAttribute("qjgldm", qjgldm);
        return "fwhs/importYcLpb";
    }

    /**
     * 导入楼盘表页面展现
     *
     * @param model
     * @param fwDcbIndex
     * @return
     */
    @RequestMapping(value = "/scmj")
    public String scmj(Model model, @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,String qjgldm) {
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        model.addAttribute("qjgldm", qjgldm);
        return "fwhs/importScmj";
    }

    /**
     * 导入楼盘表方法
     *
     * @param file                楼盘表excel
     * @param importLpbRequestDTO 逻辑幢主键，是否覆盖已有户室
     */
    @ResponseBody
    @RequestMapping(value = "/importlpb")
    public void importlpb(@NotNull(message = "导入文件不能为空") @RequestParam("file") MultipartFile file
            , ImportLpbRequestDTO importLpbRequestDTO) {
        InputStream fileIn = null;
        try {
            fileIn = file.getInputStream();
            Workbook workbook = Workbook.getWorkbook(fileIn);
            if (workbook != null) {
                importLpbRequestDTO.setLpbList(getEntityListByExcel(workbook));
                lpbFeignService.importLpbInfo(importLpbRequestDTO);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new AppException("导入失败");
        } finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException e) {
                    LOGGER.error("", e);
                }
            }
        }
    }

    /**
     * 导入楼盘表方法
     *
     * @param file                楼盘表excel
     * @param importLpbRequestDTO 逻辑幢主键，是否覆盖已有户室
     */
    @ResponseBody
    @RequestMapping(value = "/importyclpb")
    public void importYclpb(@NotNull(message = "导入文件不能为空") @RequestParam("file") MultipartFile file
            , ImportLpbRequestDTO importLpbRequestDTO) {
        InputStream fileIn = null;
        try {
            fileIn = file.getInputStream();
            Workbook workbook = Workbook.getWorkbook(fileIn);
            if (workbook != null) {
                importLpbRequestDTO.setLpbList(getEntityListByExcel(workbook));
                lpbFeignService.importYcLpbInfo(importLpbRequestDTO);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new AppException("导入失败");
        } finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException e) {
                    LOGGER.error("", e);
                }
            }
        }
    }

    /**
     * 导入实测面积方法
     *
     * @param file                楼盘表excel
     * @param importLpbRequestDTO 逻辑幢主键，是否覆盖已有户室
     */
    @ResponseBody
    @RequestMapping(value = "/importScmj")
    public void importScmj(@NotNull(message = "导入文件不能为空") @RequestParam("file") MultipartFile file
            , ImportLpbRequestDTO importLpbRequestDTO) {
        InputStream fileIn = null;
        try {
            fileIn = file.getInputStream();
            Workbook workbook = Workbook.getWorkbook(fileIn);
            if (workbook != null) {
                importLpbRequestDTO.setLpbList(getScListByExcel(workbook));
                lpbFeignService.importScmj(importLpbRequestDTO);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException e) {
                    LOGGER.error("", e);
                }
            }
        }
    }

    private static List<String> getTitlesByExcel(Workbook excelWorkbook) {
        //默认只有当前一个sheet
        Sheet dataSheet = excelWorkbook.getSheet(0);
        Cell[] titles = dataSheet.getRow(0);
        List<String> titleList = new ArrayList<>();
        if (titles != null && titles.length > 0) {
            for (Cell cell : titles) {
                if (cell == null) {
                    titleList.add("");
                } else {
                    titleList.add(cell.getContents());
                }
            }
        }
        return titleList;
    }

    private static List<Map<String, Object>> getEntityListByExcel(Workbook excelWorkbook) {
        //默认只有当前一个sheet
        Sheet dataSheet = excelWorkbook.getSheet(0);
        List<String> titleList = getTitlesByExcel(excelWorkbook);
        List hsList = new ArrayList<>();
        for (int i = 1; i < dataSheet.getRows(); i++) {
            Map<String, Object> map = new HashMap();
            for (int j = 0; j < titleList.size(); j++) {
                map.put(titleList.get(j), dataSheet.getCell(j, i).getContents());
            }
            if (MapUtils.isNotEmpty(map) && !checkIsEmptyRow(map)) {
                hsList.add(map);
            }
        }
        return hsList;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param map
     * @return boolean
     * @description 验证行数据是否为空
     */
    private static boolean checkIsEmptyRow(Map<String, Object> map){
        boolean empty = true;
        Iterator<Map.Entry<String,Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Object> entry = iterator.next();
            if(entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())){
                empty = false;
                break;
            }
        }
        return empty;
    }

    private static List<Map<String, Object>> getScListByExcel(Workbook excelWorkbook) {
        //默认只有当前一个sheet
        Sheet dataSheet = excelWorkbook.getSheet(0);
        List<String> titleList = getTitlesByExcel(excelWorkbook);
        List hsList = new ArrayList<>();
        for (int i = 1; i < dataSheet.getRows(); i++) {
            Map<String, Object> map = new HashMap();
            for (int j = 0; j < titleList.size(); j++) {
                if (StringUtils.contains(titleList.get(j), Constants.SC) || StringUtils.equals("物理层数", titleList.get(j)) || StringUtils.equals("房间号", titleList.get(j)) || StringUtils.equals("单元号", titleList.get(j))) {
                    map.put(titleList.get(j), dataSheet.getCell(j, i).getContents());
                }
            }
            if (MapUtils.isNotEmpty(map)) {
                hsList.add(map);
            }
        }
        return hsList;
    }
}
