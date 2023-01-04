package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDtcxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDtcxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcJkDtcxFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.ui.util.ExportExcelUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Boolean;
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
@RequestMapping(value = "/rest/v1.0/jk/dtcx")
public class BdcJkDtcxController extends BaseController{

    @Autowired
    private BdcDtcxFeignService bdcDtcxFeignService;
    @Autowired
    private BdcJkDtcxFeignService bdcJkDtcxFeignService;

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
    public Page<BdcDtcxDTO> listDtcxPage(BdcDtcxQO dtcxQO,@LayuiPageable Pageable pageable){
        dtcxQO.setSfjkcx(1);
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        return bdcDtcxFeignService.listDtcxPage(dtcxQO,pageable.getPageNumber(),pageable.getPageSize(), sort);
    }

    /**
     * 获取配置台账
     *
     * @return java.lang.Object
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping(value = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object xxgkcxConfig(){
        JSONObject result = new JSONObject();

        JSONArray gridArray = JSONArray.parseArray("[" +
                "            {title: '序号',width: 60, templet: '#xuhao'}" +
                "            {field: 'cxid', title: 'id',hide: true} ," +
                "            {field: 'cxmc', title: '查询名称', width: 150, align:'center'}, " +
                "            {field: 'cxdh', title: '查询代号', width: 100, align:'center'}, " +
                "            {field: 'cjr', title: '创建人', width: 90, align:'center'}, " +
                "            {field: 'cjsj', title: '创建时间', width: 180, align:'center'}, " +
                "            {field: 'bgr', title: '变更人', width: 90, align:'center'}, " +
                "            {field: 'url', title: '页面地址',  align:'center'}, " +
                "            {fixed: 'right', title: '操作', width: 200, align:'center',templet:'#rowTools'}, " +

                "]");
        result.put("grid",gridArray);
        JSONArray toolbarArray = JSONArray.parseArray("[" +
                "{type:'button', layEvent:'add', text:'新增', , class:'bdc-major-btn', renderer:'configAddOrChange()'}" +
                "{type:'button', layEvent:'import', text:'导入', , class:'bdc-major-btn', renderer:'importExcel()'}" +
                "]");
        result.put("toolbar",toolbarArray);

        // 行内操作按钮
        JSONArray toolArray = JSONArray.parseArray("[" +
                "{layEvent:'info',class:'bdc-major-btn', text:'进入', renderer: 'seeInfo'}," +
                "{layEvent:'change',class:'bdc-secondary-btn', text:'修改', renderer: 'changeOne'}," +
                "{layEvent:'export',class:'bdc-secondary-btn', text:'导出', renderer: 'exportToExcel'}," +
                "{layEvent:'del',class:'layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn', text:'删除', renderer: 'delOne'}]");
        result.put("tool",toolArray);

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
    public Object checkCxtj(@RequestParam(value = "cxjson") String cxjson,
                            @RequestParam(value = "cxtj") String cxtj){
        cxjson = cxjson.replace("\n"," ");
        cxjson = cxjson.trim();
        return bdcJkDtcxFeignService.checkCxtj(cxjson,cxtj);
    }

    /**
     * 检查查询条件json配置内容
     *
     * @param cxsql
     * @param cxtj
     * @return java.lang.Object
     * @date 2020/03/04
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @PostMapping(value = "/check/config/cxjson")
    public Boolean checkCxjson(@RequestParam(value = "cxjson") String cxjson,
                               @RequestParam(value = "cxtj") String cxtjjson){
        cxjson = cxjson.replace("\n"," ");
        cxjson = cxjson.trim();
        List<DtcxCxtjDO> cxtjList = JSONObject.parseArray(cxtjjson,DtcxCxtjDO.class);

        if (StringUtils.isBlank(cxjson) || CollectionUtils.isEmpty(cxtjList)){
            return false;
        }

        // 用字段对应方式替换后的sql执行
        for (DtcxCxtjDO cxtj : cxtjList){
            cxjson = cxjson.replace("#{" + cxtj.getTjzdid() + "}","\"\"");
        }

        // 组织分页参数
        cxjson = cxjson.replace("#{pagesize}","\"\"");
        cxjson = cxjson.replace("#{pagenumber}","\"\"");

        try{
            JSONObject.parseObject(cxjson);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    /**
     * @param dtcxDO
     * @param cxsql  查询sql配置
     * @param cxtj   查询条件配置内容字符串
     * @param cxjg   查询结果配置内容字符串
     * @return void
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping(value = "/save/all")
    public void saveAll(BdcDtcxDTO dtcxDO,
                        @RequestParam("cxtj") String cxtj,
                        @RequestParam("cxjg") String cxjg){
        dtcxDO.setCxtjDOList(JSONObject.parseArray(cxtj,DtcxCxtjDO.class));
        dtcxDO.setCxjgDOList(JSONObject.parseArray(cxjg,DtcxCxjgDO.class));
        bdcDtcxFeignService.saveAll(dtcxDO,false);
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
    public BdcDtcxDTO listCxConfig(@PathVariable("cxdh") String cxdh){

        if (StringUtils.isBlank(cxdh)){
            throw new MissingArgumentException("查询服务代号");
        }

        return bdcDtcxFeignService.getCxxxByCxdh(cxdh);
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
    public Object listResultPage(@LayuiPageable Pageable pageable,String data,String jkid,String fhzkey,String jk){
        if (StringUtils.isBlank(data)){
            throw new MissingArgumentException("查询条件");
        }
        if (StringUtils.isBlank(jkid)){
            throw new MissingArgumentException("接口方法");
        }
        if (StringUtils.isBlank(fhzkey)){
            throw new MissingArgumentException("结果内容返回值key");
        }
        if (StringUtils.isBlank(jk)){
            throw new MissingArgumentException("调用接口");
        }
        return bdcJkDtcxFeignService.listResult(data,jk,jkid,fhzkey,pageable.getPageNumber(),pageable.getPageSize());
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
    public void delCxConfig(@PathVariable("cxid") String cxid){
        if (StringUtils.isBlank(cxid)){
            throw new MissingArgumentException("ID");
        }
        bdcDtcxFeignService.delCxConfig(cxid);
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
    public int checkCxdhExist(@RequestParam("cxdh") String cxdh){
        BdcDtcxDTO bdcDtcxDTO = bdcDtcxFeignService.getCxxxByCxdh(cxdh);
        if (bdcDtcxDTO == null){
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
    public String configExportToExcel(HttpServletResponse response,String cxdh){
        // 获取查询信息
        BdcDtcxDTO bdcDtcxDTO = bdcDtcxFeignService.getCxxxByCxdh(cxdh);
        DtcxDO dtcxCx = new DtcxDO();
        BeanUtils.copyProperties(bdcDtcxDTO,dtcxCx);

        // 当存在查询结果时
        if (bdcDtcxDTO != null){
            // 生成excel
            WritableWorkbook workbook = null;
            try{
                // 取得输出流
                OutputStream outputStream = response.getOutputStream();
                response.reset();
                // 设置文件头
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateString = simpleDateFormat.format(System.currentTimeMillis());
                response.setHeader("Content-disposition",
                        "attachment; filename=" + new String((dtcxCx.getCxmc() + dateString + ".xls").getBytes("GB2312"),"ISO8859-1"));
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
                WritableSheet sheet = workbook.createSheet("DtcxCx",0);

                // 写入内容
                Field[] fieldDtcxCxList = dtcxCx.getClass().getDeclaredFields();
                for (int i = 0; i < fieldDtcxCxList.length; i++){
                    sheet.addCell(new Label(i,0,fieldDtcxCxList[i].getName(),titleFormat));
                    fieldDtcxCxList[i].setAccessible(true);
                    if (fieldDtcxCxList[i].get(dtcxCx) != null){
                        sheet.addCell(new Label(i,1,fieldDtcxCxList[i].get(dtcxCx).toString(),contentFormat));
                    }
                }

                // 创建sheet页
                sheet = workbook.createSheet(DtcxCxtjDO.class.getSimpleName(),1);
                ExportExcelUtils.writeSheetByClass(bdcDtcxDTO.getCxtjDOList(),sheet,titleFormat,contentFormat);
                sheet = workbook.createSheet(DtcxCxjgDO.class.getSimpleName(),2);
                ExportExcelUtils.writeSheetByClass(bdcDtcxDTO.getCxjgDOList(),sheet,titleFormat,contentFormat);

                workbook.write();
            }catch (IOException | WriteException | IllegalAccessException ex){
                LOGGER.error(ex.getMessage(),ex);
            }finally{
                if (workbook != null){
                    try{
                        workbook.close();
                    }catch (IOException | WriteException ex){
                        LOGGER.error(ex.getMessage(),ex);
                    }
                }
            }
        }
        return "成功";
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
    public String configImportToExcel(MultipartHttpServletRequest httpServletRequest){
        if (httpServletRequest == null){
            return null;
        }
        InputStream fileIn = null;
        try{
            Iterator<String> iterator = httpServletRequest.getFileNames();
            // 遍历所有上传文件
            while (iterator.hasNext()){
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // 根据指定的文件输入流导入Excel从而产生Workbook对象
                Workbook workbook = Workbook.getWorkbook(fileIn);

                // 创建实体类
                DtcxDO dtcxCx = new DtcxDO();

                List<DtcxDO> dtcxCxList = exportToObject(DtcxDO.class,0,workbook);
                if (!dtcxCxList.isEmpty()){
                    dtcxCx = dtcxCxList.get(0);
                }
                // 查询id
                String cxid = dtcxCx.getCxid();
                // 获取Excel文档中的第二个表单
                List<DtcxCxtjDO> cxtjList = exportToObject(DtcxCxtjDO.class,1,workbook);
                for (DtcxCxtjDO dtcxCxtj : cxtjList){
                    dtcxCxtj.setTjid(UUIDGenerator.generate());
                    dtcxCxtj.setCxid(cxid);
                }
                List<DtcxCxjgDO> cxjgList = exportToObject(DtcxCxjgDO.class,2,workbook);
                for (DtcxCxjgDO dtcxCxjg : cxjgList){
                    dtcxCxjg.setJgid(UUIDGenerator.generate());
                    dtcxCxjg.setCxid(cxid);
                }
                BdcDtcxDTO bdcDtcxDTO = new BdcDtcxDTO();
                BeanUtils.copyProperties(dtcxCx,bdcDtcxDTO);
                bdcDtcxDTO.setCxtjDOList(cxtjList);
                bdcDtcxDTO.setCxjgDOList(cxjgList);

                bdcDtcxFeignService.saveConfig(bdcDtcxDTO);
            }
        }catch (IOException | BiffException | IllegalAccessException | InstantiationException | ParseException ex){
            LOGGER.error(ex.getMessage(),ex);
            throw new AppException("导入失败");
        }finally{
            if (fileIn != null){
                try{
                    fileIn.close();
                }catch (IOException ex){
                    LOGGER.error(ex.getMessage(),ex);
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
    public String exportToExcel(HttpServletResponse response,String dataString,String data){
        List<Map> listResultData = JSONObject.parseArray(data,Map.class);
        Map<String,Object> dataMap = JSONObject.parseObject(dataString,Map.class);

        // 查询结果数据
        if (CollectionUtils.isEmpty(listResultData)){
            listResultData = bdcDtcxFeignService.listResultData(dataString);
        }
        // 查询结果
        List<DtcxCxjgDO> cxjgList = bdcDtcxFeignService.getCxjgList(MapUtils.getString(dataMap,"cxid"));
        Map<String,List<Map>> zdMap = new HashMap<>();
        StringBuilder zdStringBuilder = new StringBuilder();
        // 获取查询名称
        BdcDtcxDTO bdcDtcxDTO = bdcDtcxFeignService.getCxxxByCxdh(MapUtils.getString(dataMap,"cxdh"));

        // 当存在查询结果时
        if (CollectionUtils.isNotEmpty(cxjgList) && bdcDtcxDTO != null){
            // 获取字典信息
            for (DtcxCxjgDO cxjg : cxjgList){
                if (StringUtils.isNotEmpty(cxjg.getZdid())){
                    zdStringBuilder.append(cxjg.getZdid()).append(",");
                }
            }
            if (zdStringBuilder.length() > 0){
                zdStringBuilder.substring(0,zdStringBuilder.length() - 1);
                String[] arr = zdStringBuilder.toString().split(",");
                zdMap = getZdList(arr);
            }
            // 生成excel
            WritableWorkbook workbook = null;
            try{
                // 取得输出流
                OutputStream outputStream = response.getOutputStream();
                response.reset();
                // 设置文件头
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateString = simpleDateFormat.format(System.currentTimeMillis());
                response.setHeader("Content-disposition",
                        "attachment; filename=" + new String((bdcDtcxDTO.getCxmc() + dateString + ".xls").getBytes("GB2312"),"ISO8859-1"));
                // 设置输出类型
                response.setContentType("application/msexcel");
                // 创建excel
                workbook = Workbook.createWorkbook(outputStream);
                WritableSheet sheet = workbook.createSheet(bdcDtcxDTO.getCxmc(),0);

                writeSheet(sheet,cxjgList,listResultData,zdMap);
                workbook.write();
            }catch (IOException | WriteException ex){
                LOGGER.error(ex.getMessage(),ex);
            }finally{
                if (workbook != null){
                    try{
                        workbook.close();
                    }catch (IOException | WriteException ex){
                        LOGGER.error(ex.getMessage(),ex);
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
    private static void writeSheet(WritableSheet sheet,List<DtcxCxjgDO> cxjgList,List<Map> dataList,Map<String,List<Map>> zdMap) throws WriteException{
        // 标题样式
        WritableCellFormat titleFormat = ExportExcelUtils.titleFormat();
        // 正文样式
        WritableCellFormat contentFormat = ExportExcelUtils.contentFormat();

        if (CollectionUtils.isNotEmpty(cxjgList) && sheet != null){
            for (int i = 0; i < cxjgList.size(); i++){
                String title = cxjgList.get(i).getJgzdname();
                sheet.addCell(new Label(i,0,title,titleFormat));
            }

            // 写入列表数据
            int i = 1;
            for (Map<String,Object> map : dataList){
                int j = 0;
                for (DtcxCxjgDO cxjg : cxjgList){
                    String content = getValue(cxjg,map,zdMap);

                    sheet.addCell(new Label(j,i,content,contentFormat));
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
    private static String getValue(DtcxCxjgDO cxjg,Map<String,Object> map,Map<String,List<Map>> zdMap){
        String result = "";
        if (map.get(cxjg.getJgzdid().toUpperCase()) != null){
            if (StringUtils.isBlank(cxjg.getZdid())){
                result = map.get(cxjg.getJgzdid().toUpperCase()).toString();
            }else{
                for (Map zdData : zdMap.get(cxjg.getZdid())){
                    if (StringUtils.equals(map.get(cxjg.getJgzdid().toUpperCase()).toString(),zdData.get("DM").toString())){
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
    private static List exportToObject(Class dataClass,Integer sheetNumber,Workbook workbook) throws InstantiationException, IllegalAccessException, ParseException{
        Sheet dataSheet = workbook.getSheet(sheetNumber);
        Field[] fieldExportDataList = dataClass.getDeclaredFields();
        List dataList = new ArrayList();
        // 遍历Excel
        for (int i = 1; i < dataSheet.getRows(); i++){
            Object dataObject = dataClass.newInstance();
            for (int j = 0; j < fieldExportDataList.length; j++){
                fieldExportDataList[j].setAccessible(true);
                String contentData = dataSheet.getCell(j,i).getContents();
                // 判断属性的类型
                if (StringUtils.equals(fieldExportDataList[j].getType().toString(),"class java.lang.String")){
                    fieldExportDataList[j].set(dataObject,contentData);
                }else if ((StringUtils.equals(fieldExportDataList[j].getType().toString(),"int")
                        || StringUtils.equals(fieldExportDataList[j].getType().toString(),"class java.lang.Integer"))
                        && StringUtils.isNotBlank(contentData)){
                    fieldExportDataList[j].set(dataObject,Integer.valueOf(contentData));
                }else if (StringUtils.equals(fieldExportDataList[j].getType().toString(),"class java.util.Date")
                        && StringUtils.isNotBlank(contentData)){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.ROOT);
                    Date dateData = simpleDateFormat.parse(contentData);
                    fieldExportDataList[j].set(dataObject,dateData);
                }
            }
            dataList.add(dataObject);
        }
        return dataList;
    }
}
