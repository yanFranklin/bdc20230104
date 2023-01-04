package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.natural.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.natural.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.domain.natural.DtcxDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyDtcxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyDtcxQO;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyDtcxFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.natural.ui.service.ZrzyDtcxDataFilterService;
import cn.gtmap.realestate.natural.ui.util.ExportExcelUtils;
import cn.gtmap.realestate.natural.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/07/16
 * @description 自定义查询
 */
@RestController
@RequestMapping(value = "/dtcx")
public class ZrzyDtcxController extends BaseController {

    @Autowired
    private ZrzyDtcxFeignService zrzyDtcxFeignService;

    /**
     * 获取配置台账分页数据
     *
     * @param dtcxQO
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO>
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping("/list/page")
    public Page<ZrzyDtcxDTO> listDtcxPage(ZrzyDtcxQO dtcxQO, @LayuiPageable Pageable pageable) {
        dtcxQO.setSfjkcx(0);
        return zrzyDtcxFeignService.listDtcxPage(dtcxQO, pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
    }

    /**
     * 获取配置台账
     *
     * @return java.lang.Object
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping(value = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object xxgkcxConfig() {
        JSONObject result = new JSONObject();

        JSONArray gridArray = JSONArray.parseArray("[" +
                "            {type: 'checkbox', fixed: 'left'}," +
                "            {title: '序号',width: 60, templet: '#xuhao'}," +
                "            {field: 'cxid', title: 'id',hide: true} ," +
                "            {field: 'cxmc', title: '查询名称', width: 150, align:'center'}, " +
                "            {field: 'cxdh', title: '查询代号', width: 100, align:'center'}, " +
                "            {field: 'cjr', title: '创建人', width: 90, align:'center'}, " +
                "            {field: 'cjsj', title: '创建时间', width: 180, align:'center'}, " +
                "            {field: 'url', title: '页面地址',  align:'center'}, " +
                "            {field: 'bgr', title: '变更人', width: 90, align:'center'}, " +
                "            {fixed: 'right', title: '操作', width: 200, align:'center',templet:'#rowTools'}, " +

                "]");
        result.put("grid", gridArray);
        JSONArray toolbarArray = JSONArray.parseArray("[" +
                "{type:'button', layEvent:'add', text:'新增', , class:'bdc-major-btn', renderer:'configAddOrChange()'}" +
                "{type:'button', layEvent:'import', text:'导入', , class:'bdc-table-second-btn', renderer:'importExcel()'}" +
                "{type:'button', layEvent:'fz', text:'复制', , class:'bdc-table-second-btn', renderer:'fuZhi()'}" +
                "]");
        result.put("toolbar", toolbarArray);

        // 行内操作按钮
        JSONArray toolArray = JSONArray.parseArray("[" +
                "{layEvent:'info',class:'bdc-major-btn', text:'进入', renderer: 'seeInfo'}," +
                "{layEvent:'change',class:'bdc-secondary-btn', text:'修改', renderer: 'changeOne'}," +
                "{layEvent:'export',class:'bdc-secondary-btn', text:'导出', renderer: 'exportToExcel'}," +
                "{layEvent:'del',class:'layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn', text:'删除', renderer: 'delOne'}]");
        result.put("tool", toolArray);

        return result;
    }

    /**
     * 检查查询条件配置内容
     *
     * @param cxsql
     * @param cxtj
     * @return java.lang.Object
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping(value = "/check/config/cxtj")
    public Object checkCxtj(@RequestParam(value = "cxsql") String cxsql,
                            @RequestParam(value = "cxtj") String cxtj) {
        cxsql = cxsql.replace("\n", " ");
        cxsql = cxsql.trim();
        return zrzyDtcxFeignService.checkCxtj(cxsql, cxtj);
    }

    /**
     * 检查查询结果
     *
     * @param cxsql 查询sql
     * @param cxjg  查询结果json字符串
     * @return java.lang.Object
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping(value = "/check/config/cxjg")
    public Object checkCxjg(@RequestParam("cxsql") String cxsql,
                            @RequestParam("cxjg") String cxjg) {
        cxsql = cxsql.replace("\n", " ");
        cxsql = cxsql.trim();
        return zrzyDtcxFeignService.checkCxjg(cxsql, cxjg);
    }

    /**
     * @param dtcxDO
     * @param cxtj    查询条件配置内容字符串
     * @param cxjg    查询结果配置内容字符串
     * @param mhcx    是否为模糊查询
     * @param fuzhiCx 是否为复制查询
     * @return void
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping(value = "/save/all")
    public void saveAll(ZrzyDtcxDTO dtcxDO,
                        @RequestParam("cxtj") String cxtj,
                        @RequestParam("cxjg") String cxjg,
                        @RequestParam("mhcx") Integer mhcx,
                        @RequestParam("fuzhiCx") boolean fuzhiCx) {
        dtcxDO.setCanmhcx(mhcx);
        dtcxDO.setCxtjDOList(JSONObject.parseArray(cxtj, DtcxCxtjDO.class));
        dtcxDO.setCxjgDOList(JSONObject.parseArray(cxjg, DtcxCxjgDO.class));
        zrzyDtcxFeignService.saveAll(dtcxDO, fuzhiCx);

    }

    /**
     * 获取查询配置，用于前台渲染页面
     *
     * @param cxdh
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping(value = "/get/{cxdh}")
    public ZrzyDtcxDTO listCxConfig(@PathVariable("cxdh") String cxdh) {

        if (StringUtils.isBlank(cxdh)) {
            throw new MissingArgumentException("查询服务代号");
        }

        return zrzyDtcxFeignService.getCxxxByCxdh(cxdh);
    }

    /**
     * 执行具体的查询
     *
     * @param pageable
     * @param data
     * @return org.springframework.data.domain.Page
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping("list/result")
    public Page listResultPage(@LayuiPageable Pageable pageable, String data) {
        if (StringUtils.isBlank(data)) {
            throw new MissingArgumentException("查询条件");
        }
        return zrzyDtcxFeignService.listResult(data, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
    }

    /**
     * 删除对应的cxid的查询配置
     *
     * @param cxid
     * @return void
     * @date 2019/07/16
     * @author hanyi
     */
    @DeleteMapping("del/{cxid}")
    public void delCxConfig(@PathVariable("cxid") String cxid) {
        if (StringUtils.isBlank(cxid)) {
            throw new MissingArgumentException("ID");
        }
        zrzyDtcxFeignService.delCxConfig(cxid);
    }

    /**
     * 检查查询代号是否已使用
     *
     * @param cxdh 查询代号
     * @return int 1表示未使用，0表示已使用
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping("check/cxdh")
    public int checkCxdhExist(@RequestParam("cxdh") String cxdh) {
        ZrzyDtcxDTO zrzyDtcxDTO = zrzyDtcxFeignService.getCxxxByCxdh(cxdh);
        if (zrzyDtcxDTO == null) {
            return 1;
        }
        return 0;
    }

    /**
     * 导出配置信息到excel文件中
     *
     * @param response 网页请求
     * @param cxdh     查询代号
     * @return java.lang.String
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping("config/export/excel")
    public String configExportToExcel(HttpServletResponse response, String cxdh) {
        // 获取查询信息
        ZrzyDtcxDTO zrzyDtcxDTO = zrzyDtcxFeignService.getCxxxByCxdh(cxdh);
        DtcxDO dtcxCx = new DtcxDO();
        BeanUtils.copyProperties(zrzyDtcxDTO, dtcxCx);

        // 当存在查询结果时
        if (zrzyDtcxDTO != null) {
            // 生成excel
            WritableWorkbook workbook = null;
            try {
                // 取得输出流
                OutputStream outputStream = response.getOutputStream();
                response.reset();
                // 设置文件头
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateString = simpleDateFormat.format(System.currentTimeMillis());
                response.setHeader("Content-disposition",
                        "attachment; filename=" + new String((dtcxCx.getCxmc() + dateString + ".xls").getBytes("GB2312"), "ISO8859-1"));
                // 设置输出类型
                response.setContentType("application/msexcel");

                // 创建excel
                workbook = Workbook.createWorkbook(outputStream);

                // 标题样式
                WritableCellFormat titleFormat = ExportExcelUtils.titleFormat();
                // 正文样式
                WritableCellFormat contentFormat = ExportExcelUtils.contentFormat();

                // 动态查询表
                // 创建sheet
                WritableSheet sheet = workbook.createSheet("DtcxCx", 0);

                // 写入内容
                Field[] fieldDtcxCxList = dtcxCx.getClass().getDeclaredFields();
                for (int i = 0; i < fieldDtcxCxList.length; i++) {
                    sheet.addCell(new Label(i, 0, fieldDtcxCxList[i].getName(), titleFormat));
                    fieldDtcxCxList[i].setAccessible(true);
                    if (fieldDtcxCxList[i].get(dtcxCx) != null) {
                        sheet.addCell(new Label(i, 1, fieldDtcxCxList[i].get(dtcxCx).toString(), contentFormat));
                    }
                }

                // 创建sheet页
                sheet = workbook.createSheet(DtcxCxtjDO.class.getSimpleName(), 1);
                ExportExcelUtils.writeSheetByClass(zrzyDtcxDTO.getCxtjDOList(), sheet, titleFormat, contentFormat);
                sheet = workbook.createSheet(DtcxCxjgDO.class.getSimpleName(), 2);
                ExportExcelUtils.writeSheetByClass(zrzyDtcxDTO.getCxjgDOList(), sheet, titleFormat, contentFormat);

                workbook.write();
            } catch (IOException | WriteException | IllegalAccessException ex) {
                LOGGER.error(ex.getMessage(), ex);
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException | WriteException ex) {
                        LOGGER.error(ex.getMessage(), ex);
                    }
                }
            }
        }
        return "成功";
    }

    /**
     * 设置工作表自动列宽
     *
     * @param ws   表格sheet
     * @param cxdh 查询代号
     */
    private void setColumnAutoSize(WritableSheet ws, String cxdh) {
        try {
            // 获取本列的最宽单元格的宽度
            for (int i = 0; i < ws.getColumns(); i++) {
                int colWith = 0;
                for (int j = 0; j < ws.getRows(); j++) {
                    String content = ws.getCell(i, j).getContents();
                    int cellWith = content.length();
                    if (colWith < cellWith) {
                        colWith = cellWith;
                    }
                }
                // 设置单元格的宽度
                ws.setColumnView(i, colWith + 5);
            }
        } catch (Exception e) {
            LOGGER.error("自定义查询导出Excel设置列宽错误:{}", cxdh);
        }
    }

    /**
     * 导出配置信息到excel文件中
     *
     * @param httpServletRequest 网页请求
     * @return java.lang.String
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping("config/import/excel")
    public String configImportToExcel(MultipartHttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return null;
        }
        InputStream fileIn = null;
        try {
            Iterator<String> iterator = httpServletRequest.getFileNames();
            // 遍历所有上传文件
            while (iterator.hasNext()) {
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // 根据指定的文件输入流导入Excel从而产生Workbook对象
                Workbook workbook = Workbook.getWorkbook(fileIn);

                // 创建实体类
                DtcxDO dtcxCx = new DtcxDO();

                List<DtcxDO> dtcxCxList = exportToObject(DtcxDO.class, 0, workbook);
                if (!dtcxCxList.isEmpty()) {
                    dtcxCx = dtcxCxList.get(0);
                }
                // 查询id
                String cxid = dtcxCx.getCxid();
                // 获取Excel文档中的第二个表单
                List<DtcxCxtjDO> cxtjList = exportToObject(DtcxCxtjDO.class, 1, workbook);
                for (DtcxCxtjDO dtcxCxtj : cxtjList) {
                    dtcxCxtj.setTjid(UUIDGenerator.generate());
                    dtcxCxtj.setCxid(cxid);
                }
                List<DtcxCxjgDO> cxjgList = exportToObject(DtcxCxjgDO.class, 2, workbook);
                for (DtcxCxjgDO dtcxCxjg : cxjgList) {
                    dtcxCxjg.setJgid(UUIDGenerator.generate());
                    dtcxCxjg.setCxid(cxid);
                }
                ZrzyDtcxDTO zrzyDtcxDTO = new ZrzyDtcxDTO();
                BeanUtils.copyProperties(dtcxCx, zrzyDtcxDTO);
                zrzyDtcxDTO.setCxtjDOList(cxtjList);
                zrzyDtcxDTO.setCxjgDOList(cxjgList);

                zrzyDtcxFeignService.saveConfig(zrzyDtcxDTO);
            }
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

        return "成功";
    }

    /**
     * 导出excel功能，待完善
     *
     * @param response   网页请求
     * @param dataString 查询信息
     * @param data       选中数据
     * @return java.lang.String
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping("export/excel")
    public String exportToExcel(HttpServletResponse response, String dataString, String data) {
        return this.exportToExcelWithZdyFilter(response, dataString, data, null);
    }

    @PostMapping("export/excel/filter")
    public String exportToExcelWithZdyFilter(HttpServletResponse response, String dataString, String data, ZrzyDtcxDataFilterService bdcDtcxDataFilterService) {
        List<Map> listResultData = JSONObject.parseArray(data, Map.class);
        Map<String, Object> dataMap = JSONObject.parseObject(dataString, Map.class);

        // 查询结果数据
        if (CollectionUtils.isEmpty(listResultData)) {
            listResultData = zrzyDtcxFeignService.listResultData(dataString);
            if (null != bdcDtcxDataFilterService) {
                bdcDtcxDataFilterService.filterData(listResultData);
            }
        }
        // 查询结果
        List<DtcxCxjgDO> cxjgListAll = zrzyDtcxFeignService.getCxjgList(MapUtils.getString(dataMap, "cxid"));
        // 筛选需要导出Excel的结果列
        List<DtcxCxjgDO> cxjgList = new ArrayList<>();
        for (DtcxCxjgDO dtcxCxjgDO : cxjgListAll) {
            if ((!StringUtils.equals(dtcxCxjgDO.getJgtype(), "button")) && (!StringUtils.equals(dtcxCxjgDO.getMrxs(), "0"))) {
                cxjgList.add(dtcxCxjgDO);
            }
        }
        Map<String, List<Map>> zdMap = new HashMap<>();
        StringBuilder zdStringBuilder = new StringBuilder();
        // 获取查询名称
        ZrzyDtcxDTO zrzyDtcxDTO = zrzyDtcxFeignService.getCxxxByCxdh(MapUtils.getString(dataMap, "cxdh"));

        // 当存在查询结果时
        if (CollectionUtils.isNotEmpty(cxjgList) && zrzyDtcxDTO != null) {
            // 获取字典信息
            for (DtcxCxjgDO cxjg : cxjgList) {
                if (StringUtils.isNotEmpty(cxjg.getZdid())) {
                    zdStringBuilder.append(cxjg.getZdid()).append(",");
                }
            }
            if (zdStringBuilder.length() > 0) {
                zdStringBuilder.substring(0, zdStringBuilder.length() - 1);
                String[] arr = zdStringBuilder.toString().split(",");
                zdMap = getZdList(arr);
            }
            // 生成excel
            WritableWorkbook workbook = null;
            try {
                // 取得输出流
                OutputStream outputStream = response.getOutputStream();
                response.reset();
                // 设置文件头
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateString = simpleDateFormat.format(System.currentTimeMillis());
                response.setHeader("Content-disposition",
                        "attachment; filename=" + new String((zrzyDtcxDTO.getCxmc() + dateString + ".xls").getBytes("GB2312"), "ISO8859-1"));
                // 设置输出类型
                response.setContentType("application/msexcel");
                // 创建excel
                workbook = Workbook.createWorkbook(outputStream);
                WritableSheet sheet = workbook.createSheet(zrzyDtcxDTO.getCxmc(), 0);

                writeSheet(sheet, cxjgList, listResultData, zdMap);

                this.setColumnAutoSize(sheet, zrzyDtcxDTO.getCxdh());

                workbook.write();
            } catch (IOException | WriteException ex) {
                LOGGER.error(ex.getMessage(), ex);
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException | WriteException ex) {
                        LOGGER.error(ex.getMessage(), ex);
                    }
                }
            }
        }
        return "成功";
    }

    /**
     * 写入sheet页
     *
     * @param sheet    sheet页
     * @param cxjgList 查询结果列表
     * @param dataList 数据列表
     * @param zdMap    字典表
     * @throws WriteException
     */
    private static void writeSheet(WritableSheet sheet, List<DtcxCxjgDO> cxjgList, List<Map> dataList, Map<String, List<Map>> zdMap) throws WriteException {
        // 标题样式
        WritableCellFormat titleFormat = ExportExcelUtils.titleFormat();
        // 正文样式
        WritableCellFormat contentFormat = ExportExcelUtils.contentFormat();

        if (CollectionUtils.isNotEmpty(cxjgList) && sheet != null) {
            for (int i = 0; i < cxjgList.size(); i++) {
                String title = cxjgList.get(i).getJgzdname();
                sheet.addCell(new Label(i, 0, title, titleFormat));
            }

            // 写入列表数据
            int i = 1;
            for (Map<String, Object> map : dataList) {
                int j = 0;
                for (DtcxCxjgDO cxjg : cxjgList) {
                    String content = getValue(cxjg, map, zdMap);

                    sheet.addCell(new Label(j, i, content, contentFormat));
                    j++;
                }
                i++;
            }
        }
    }

    /**
     * 获取当前字典字段对应的文字
     *
     * @param cxjg  查询结果
     * @param map   查询数据
     * @param zdMap 字典表
     * @return 字典对应名称
     */
    private static String getValue(DtcxCxjgDO cxjg, Map<String, Object> map, Map<String, List<Map>> zdMap) {
        String result = "";
        if (map.get(cxjg.getJgzdid().toUpperCase()) != null) {
            if (StringUtils.isBlank(cxjg.getZdid())) {
                result = map.get(cxjg.getJgzdid().toUpperCase()).toString();
            } else {
                for (Map zdData : zdMap.get(cxjg.getZdid())) {
                    if (StringUtils.equals(map.get(cxjg.getJgzdid().toUpperCase()).toString(), zdData.get("DM").toString())) {
                        result = zdData.get("MC").toString();
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 读取sheet页并写入对应Object
     *
     * @param dataClass   导出类名
     * @param sheetNumber 导出页号
     * @param workbook    导出文件
     * @return List 数据列表
     */
    private static List exportToObject(Class dataClass, Integer sheetNumber, Workbook workbook) throws InstantiationException, IllegalAccessException, ParseException {
        Sheet dataSheet = workbook.getSheet(sheetNumber);
        Field[] fieldExportDataList = dataClass.getDeclaredFields();
        List dataList = new ArrayList();
        // 遍历Excel
        for (int i = 1; i < dataSheet.getRows(); i++) {
            Object dataObject = dataClass.newInstance();
            for (int j = 0; j < fieldExportDataList.length; j++) {
                fieldExportDataList[j].setAccessible(true);
                String contentData = dataSheet.getCell(j, i).getContents();
                // 判断属性的类型
                if (StringUtils.equals(fieldExportDataList[j].getType().toString(), "class java.lang.String")) {
                    fieldExportDataList[j].set(dataObject, contentData);
                } else if ((StringUtils.equals(fieldExportDataList[j].getType().toString(), "int")
                        || StringUtils.equals(fieldExportDataList[j].getType().toString(), "class java.lang.Integer"))
                        && StringUtils.isNotBlank(contentData)) {
                    fieldExportDataList[j].set(dataObject, Integer.valueOf(contentData));
                } else if (StringUtils.equals(fieldExportDataList[j].getType().toString(), "class java.util.Date")
                        && StringUtils.isNotBlank(contentData)) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ROOT);
                    Date dateData = simpleDateFormat.parse(contentData);
                    fieldExportDataList[j].set(dataObject, dateData);
                }
            }
            dataList.add(dataObject);
        }
        return dataList;
    }
}
