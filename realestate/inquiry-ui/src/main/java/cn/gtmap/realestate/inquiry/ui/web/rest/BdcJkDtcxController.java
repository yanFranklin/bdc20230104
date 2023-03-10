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
 * @description ???????????????
 */
@RestController
@RequestMapping(value = "/rest/v1.0/jk/dtcx")
public class BdcJkDtcxController extends BaseController{

    @Autowired
    private BdcDtcxFeignService bdcDtcxFeignService;
    @Autowired
    private BdcJkDtcxFeignService bdcJkDtcxFeignService;

    /**
     * ??????????????????????????????
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
     * ??????????????????
     *
     * @return java.lang.Object
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping(value = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object xxgkcxConfig(){
        JSONObject result = new JSONObject();

        JSONArray gridArray = JSONArray.parseArray("[" +
                "            {title: '??????',width: 60, templet: '#xuhao'}" +
                "            {field: 'cxid', title: 'id',hide: true} ," +
                "            {field: 'cxmc', title: '????????????', width: 150, align:'center'}, " +
                "            {field: 'cxdh', title: '????????????', width: 100, align:'center'}, " +
                "            {field: 'cjr', title: '?????????', width: 90, align:'center'}, " +
                "            {field: 'cjsj', title: '????????????', width: 180, align:'center'}, " +
                "            {field: 'bgr', title: '?????????', width: 90, align:'center'}, " +
                "            {field: 'url', title: '????????????',  align:'center'}, " +
                "            {fixed: 'right', title: '??????', width: 200, align:'center',templet:'#rowTools'}, " +

                "]");
        result.put("grid",gridArray);
        JSONArray toolbarArray = JSONArray.parseArray("[" +
                "{type:'button', layEvent:'add', text:'??????', , class:'bdc-major-btn', renderer:'configAddOrChange()'}" +
                "{type:'button', layEvent:'import', text:'??????', , class:'bdc-major-btn', renderer:'importExcel()'}" +
                "]");
        result.put("toolbar",toolbarArray);

        // ??????????????????
        JSONArray toolArray = JSONArray.parseArray("[" +
                "{layEvent:'info',class:'bdc-major-btn', text:'??????', renderer: 'seeInfo'}," +
                "{layEvent:'change',class:'bdc-secondary-btn', text:'??????', renderer: 'changeOne'}," +
                "{layEvent:'export',class:'bdc-secondary-btn', text:'??????', renderer: 'exportToExcel'}," +
                "{layEvent:'del',class:'layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn', text:'??????', renderer: 'delOne'}]");
        result.put("tool",toolArray);

        return result;
    }

    /**
     * ??????????????????????????????
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
     * ??????????????????json????????????
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

        // ?????????????????????????????????sql??????
        for (DtcxCxtjDO cxtj : cxtjList){
            cxjson = cxjson.replace("#{" + cxtj.getTjzdid() + "}","\"\"");
        }

        // ??????????????????
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
     * @param cxsql  ??????sql??????
     * @param cxtj   ?????????????????????????????????
     * @param cxjg   ?????????????????????????????????
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
     * ?????????????????????????????????????????????
     *
     * @param cxdh
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping(value = "/get/{cxdh}")
    public BdcDtcxDTO listCxConfig(@PathVariable("cxdh") String cxdh){

        if (StringUtils.isBlank(cxdh)){
            throw new MissingArgumentException("??????????????????");
        }

        return bdcDtcxFeignService.getCxxxByCxdh(cxdh);
    }

    /**
     * ?????????????????????
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
            throw new MissingArgumentException("????????????");
        }
        if (StringUtils.isBlank(jkid)){
            throw new MissingArgumentException("????????????");
        }
        if (StringUtils.isBlank(fhzkey)){
            throw new MissingArgumentException("?????????????????????key");
        }
        if (StringUtils.isBlank(jk)){
            throw new MissingArgumentException("????????????");
        }
        return bdcJkDtcxFeignService.listResult(data,jk,jkid,fhzkey,pageable.getPageNumber(),pageable.getPageSize());
    }

    /**
     * ???????????????cxid???????????????
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
     * ?????????????????????????????????
     *
     * @param cxdh ????????????
     * @return int 1??????????????????0???????????????
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
     * ?????????????????????excel?????????
     *
     * @param response ????????????
     * @param cxdh     ????????????
     * @return java.lang.String
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping("config/export/excel")
    public String configExportToExcel(HttpServletResponse response,String cxdh){
        // ??????????????????
        BdcDtcxDTO bdcDtcxDTO = bdcDtcxFeignService.getCxxxByCxdh(cxdh);
        DtcxDO dtcxCx = new DtcxDO();
        BeanUtils.copyProperties(bdcDtcxDTO,dtcxCx);

        // ????????????????????????
        if (bdcDtcxDTO != null){
            // ??????excel
            WritableWorkbook workbook = null;
            try{
                // ???????????????
                OutputStream outputStream = response.getOutputStream();
                response.reset();
                // ???????????????
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateString = simpleDateFormat.format(System.currentTimeMillis());
                response.setHeader("Content-disposition",
                        "attachment; filename=" + new String((dtcxCx.getCxmc() + dateString + ".xls").getBytes("GB2312"),"ISO8859-1"));
                // ??????????????????
                response.setContentType("application/msexcel");

                // ??????excel
                workbook = Workbook.createWorkbook(outputStream);

                // ????????????
                WritableCellFormat titleFormat = ExportExcelUtils.titleFormat();
                // ????????????
                WritableCellFormat contentFormat = ExportExcelUtils.contentFormat();

                // ???????????????
                // ??????sheet
                WritableSheet sheet = workbook.createSheet("DtcxCx",0);

                // ????????????
                Field[] fieldDtcxCxList = dtcxCx.getClass().getDeclaredFields();
                for (int i = 0; i < fieldDtcxCxList.length; i++){
                    sheet.addCell(new Label(i,0,fieldDtcxCxList[i].getName(),titleFormat));
                    fieldDtcxCxList[i].setAccessible(true);
                    if (fieldDtcxCxList[i].get(dtcxCx) != null){
                        sheet.addCell(new Label(i,1,fieldDtcxCxList[i].get(dtcxCx).toString(),contentFormat));
                    }
                }

                // ??????sheet???
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
        return "??????";
    }

    /**
     * ?????????????????????excel?????????
     *
     * @param httpServletRequest ????????????
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
            // ????????????????????????
            while (iterator.hasNext()){
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // ????????????????????????????????????Excel????????????Workbook??????
                Workbook workbook = Workbook.getWorkbook(fileIn);

                // ???????????????
                DtcxDO dtcxCx = new DtcxDO();

                List<DtcxDO> dtcxCxList = exportToObject(DtcxDO.class,0,workbook);
                if (!dtcxCxList.isEmpty()){
                    dtcxCx = dtcxCxList.get(0);
                }
                // ??????id
                String cxid = dtcxCx.getCxid();
                // ??????Excel???????????????????????????
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
            throw new AppException("????????????");
        }finally{
            if (fileIn != null){
                try{
                    fileIn.close();
                }catch (IOException ex){
                    LOGGER.error(ex.getMessage(),ex);
                }
            }
        }

        return "??????";
    }

    /**
     * ??????excel??????????????????
     *
     * @param response   ????????????
     * @param dataString ????????????
     * @param data       ????????????
     * @return java.lang.String
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping("export/excel")
    public String exportToExcel(HttpServletResponse response,String dataString,String data){
        List<Map> listResultData = JSONObject.parseArray(data,Map.class);
        Map<String,Object> dataMap = JSONObject.parseObject(dataString,Map.class);

        // ??????????????????
        if (CollectionUtils.isEmpty(listResultData)){
            listResultData = bdcDtcxFeignService.listResultData(dataString);
        }
        // ????????????
        List<DtcxCxjgDO> cxjgList = bdcDtcxFeignService.getCxjgList(MapUtils.getString(dataMap,"cxid"));
        Map<String,List<Map>> zdMap = new HashMap<>();
        StringBuilder zdStringBuilder = new StringBuilder();
        // ??????????????????
        BdcDtcxDTO bdcDtcxDTO = bdcDtcxFeignService.getCxxxByCxdh(MapUtils.getString(dataMap,"cxdh"));

        // ????????????????????????
        if (CollectionUtils.isNotEmpty(cxjgList) && bdcDtcxDTO != null){
            // ??????????????????
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
            // ??????excel
            WritableWorkbook workbook = null;
            try{
                // ???????????????
                OutputStream outputStream = response.getOutputStream();
                response.reset();
                // ???????????????
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateString = simpleDateFormat.format(System.currentTimeMillis());
                response.setHeader("Content-disposition",
                        "attachment; filename=" + new String((bdcDtcxDTO.getCxmc() + dateString + ".xls").getBytes("GB2312"),"ISO8859-1"));
                // ??????????????????
                response.setContentType("application/msexcel");
                // ??????excel
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
        return "??????";
    }

    /**
     * ??????sheet???
     *
     * @param sheet    sheet???
     * @param cxjgList ??????????????????
     * @param dataList ????????????
     * @param zdMap    ?????????
     * @throws WriteException
     */
    private static void writeSheet(WritableSheet sheet,List<DtcxCxjgDO> cxjgList,List<Map> dataList,Map<String,List<Map>> zdMap) throws WriteException{
        // ????????????
        WritableCellFormat titleFormat = ExportExcelUtils.titleFormat();
        // ????????????
        WritableCellFormat contentFormat = ExportExcelUtils.contentFormat();

        if (CollectionUtils.isNotEmpty(cxjgList) && sheet != null){
            for (int i = 0; i < cxjgList.size(); i++){
                String title = cxjgList.get(i).getJgzdname();
                sheet.addCell(new Label(i,0,title,titleFormat));
            }

            // ??????????????????
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
     * ???????????????????????????????????????
     *
     * @param cxjg  ????????????
     * @param map   ????????????
     * @param zdMap ?????????
     * @return ??????????????????
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
     * ??????sheet??????????????????Object
     *
     * @param dataClass   ????????????
     * @param sheetNumber ????????????
     * @param workbook    ????????????
     * @return List ????????????
     */
    private static List exportToObject(Class dataClass,Integer sheetNumber,Workbook workbook) throws InstantiationException, IllegalAccessException, ParseException{
        Sheet dataSheet = workbook.getSheet(sheetNumber);
        Field[] fieldExportDataList = dataClass.getDeclaredFields();
        List dataList = new ArrayList();
        // ??????Excel
        for (int i = 1; i < dataSheet.getRows(); i++){
            Object dataObject = dataClass.newInstance();
            for (int j = 0; j < fieldExportDataList.length; j++){
                fieldExportDataList[j].setAccessible(true);
                String contentData = dataSheet.getCell(j,i).getContents();
                // ?????????????????????
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
