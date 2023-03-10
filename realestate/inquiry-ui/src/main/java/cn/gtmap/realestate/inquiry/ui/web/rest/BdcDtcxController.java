package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlWjscDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.DtcxRequestDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDtcxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDtcxFeignService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.ui.core.service.BdcDtcxDataFilterService;
import cn.gtmap.realestate.inquiry.ui.util.Constants;
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
import org.apache.commons.lang.math.NumberUtils;
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
@RequestMapping(value = "/dtcx")
public class BdcDtcxController extends BaseController{

    @Autowired
    private BdcDtcxFeignService bdcDtcxFeignService;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    /**
     * ???????????????????????????
     */
    private static final String defaultAuthority = "{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanAddFolder\":0,\"CanDeleteFolder\":0,\"CanRenameFolder\":0,\"CanAddFile\":1,\"CanDeleteFile\":0,\"CanRenameFile\":1,\"CanDelete\":0,\"CanRename\":1,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":1,\"CanTakePhoto\":1,\"CanScan\":1,\"CanEdit\":-1}";

    /**
     * ??????????????????????????????
     *
     * @param dtcxQO
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO>
     * @date 2019/07/16
     * @author hanyi
     *
     * ?????????????????????post???????????????get?????????????????????
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @PostMapping("/list/page")
    public Page<BdcDtcxDTO> listDtcxPage(BdcDtcxQO dtcxQO,@LayuiPageable Pageable pageable){
        dtcxQO.setSfjkcx(0);
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcDtcxDTO> bdcDtcxDTOS =  bdcDtcxFeignService.listDtcxPage(dtcxQO,pageable.getPageNumber(),pageable.getPageSize(), sort);
        //CXSQL??????
        if(bdcDtcxDTOS.hasContent()){
            for (BdcDtcxDTO bdcDtcxDTO : bdcDtcxDTOS.getContent()) {
                bdcDtcxDTO.setCxsql(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcDtcxDTO.getCxsql().getBytes())));
            }
        }
        return bdcDtcxDTOS;
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
                "            {type: 'checkbox', fixed: 'left'}," +
                "            {title: '??????',width: 60, type:'numbers'}," +
                "            {field: 'cxid', title: 'id',hide: true} ," +
                "            {field: 'cxmc', title: '????????????', width: 150, align:'center'}, " +
                "            {field: 'cxdh', title: '????????????', width: 100, align:'center'}, " +
                "            {field: 'cjr', title: '?????????', width: 90, align:'center'}, " +
                "            {field: 'cjsj', title: '????????????', width: 180, align:'center'}, " +
                "            {field: 'bgr', title: '?????????', width: 90, align:'center'}, " +
                "            {field: 'bgsj', title: '????????????', width: 180, align:'center'}, " +
                "            {field: 'url', title: '????????????',  align:'center'}, " +
                "            {fixed: 'right', title: '??????', width: 200, align:'center',templet:'#rowTools'}, " +

                "]");
        result.put("grid",gridArray);
        JSONArray toolbarArray = JSONArray.parseArray("[" +
                "{type:'button', layEvent:'add', text:'??????', , class:'bdc-major-btn', renderer:'configAddOrChange()'}" +
                "{type:'button', layEvent:'import', text:'??????', , class:'bdc-table-second-btn', renderer:'importExcel()'}" +
                "{type:'button', layEvent:'fz', text:'??????', , class:'bdc-table-second-btn', renderer:'fuZhi()'}" +
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
    public Object checkCxtj(@RequestParam(value = "cxsql") String cxsql,
                            @RequestParam(value = "cxtj") String cxtj){
        //??????sql??????
        if(StringUtils.isNotBlank(cxsql)){
            cxsql = StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(cxsql)));
        }
        cxsql = cxsql.replace("\n"," ");
        cxsql = cxsql.trim();
        return bdcDtcxFeignService.checkCxtj(cxsql,cxtj);
    }

    /**
     * ??????????????????
     *
     * @param cxsql ??????sql
     * @param cxjg  ????????????json?????????
     * @return java.lang.Object
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping(value = "/check/config/cxjg")
    public Object checkCxjg(@RequestParam("cxsql") String cxsql,
                            @RequestParam("cxjg") String cxjg){
        //??????sql??????
        if(StringUtils.isNotBlank(cxsql)){
            cxsql = StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(cxsql)));
        }
        cxsql = cxsql.replace("\n"," ");
        cxsql = cxsql.trim();
        return bdcDtcxFeignService.checkCxjg(cxsql,cxjg);
    }

    /**
     * @param dtcxDO
     * @param cxtj    ?????????????????????????????????
     * @param cxjg    ?????????????????????????????????
     * @param mhcx    ?????????????????????
     * @param fuzhiCx ?????????????????????
     * @return void
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping(value = "/save/all")
    public void saveAll(BdcDtcxDTO dtcxDO,
                        @RequestParam("cxtj") String cxtj,
                        @RequestParam("cxjg") String cxjg,
                        @RequestParam("mhcx") Integer mhcx,
                        @RequestParam("fuzhiCx") boolean fuzhiCx){
        dtcxDO.setCanmhcx(mhcx);
        dtcxDO.setCxtjDOList(JSONObject.parseArray(cxtj,DtcxCxtjDO.class));
        dtcxDO.setCxjgDOList(JSONObject.parseArray(cxjg,DtcxCxjgDO.class));
        //?????????sql????????????
        if(StringUtils.isNotBlank(dtcxDO.getCxsql())){
            dtcxDO.setCxsql(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(dtcxDO.getCxsql()))));
        }
        bdcDtcxFeignService.saveAll(dtcxDO,fuzhiCx);

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
        BdcDtcxDTO bdcDtcxDTO = bdcDtcxFeignService.getCxxxByCxdh(cxdh);
        //?????????SQL??????
        if(StringUtils.isNotBlank(bdcDtcxDTO.getCxsql())){
            bdcDtcxDTO.setCxsql(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcDtcxDTO.getCxsql().getBytes())));
        }
        return bdcDtcxDTO;
    }

    /**
     * ?????????????????????
     *
     * @param dtcxRequestDTO ?????????????????????
     * @return org.springframework.data.domain.Page
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping("list/result")
    public Page listResultPage(@RequestBody DtcxRequestDTO dtcxRequestDTO){
        if (StringUtils.isBlank(dtcxRequestDTO.getData())){
            throw new MissingArgumentException("????????????");
        }
        Sort sort = "UNSORTED".equals(String.valueOf(dtcxRequestDTO.getSort())) ? null : dtcxRequestDTO.getSort();
        return bdcDtcxFeignService.listResult(dtcxRequestDTO.getData(), dtcxRequestDTO.getPage() - 1, dtcxRequestDTO.getSize(),
                sort);
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
     * ???????????????????????????
     *
     * @param ws   ??????sheet
     * @param cxdh ????????????
     */
    private void setColumnAutoSize(WritableSheet ws,String cxdh){
        try{
            // ???????????????????????????????????????
            for (int i = 0; i < ws.getColumns(); i++){
                int colWith = 0;
                for (int j = 0; j < ws.getRows(); j++){
                    String content = ws.getCell(i,j).getContents();
                    int cellWith = content.length();
                    if (colWith < cellWith){
                        colWith = cellWith;
                    }
                }
                // ????????????????????????
                ws.setColumnView(i,colWith + 5);
            }
        }catch (Exception e){
            LOGGER.error("?????????????????????Excel??????????????????:{}",cxdh);
        }
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param ws   ??????sheet
     * @param cxdh ????????????
     */
    private void setColumnSize(WritableSheet ws,String cxdh,List<DtcxCxjgDO> cxjgList){
        try{
            if (CollectionUtils.isNotEmpty(cxjgList) && ws != null){
                for (int i = 0; i < cxjgList.size(); i++){
                    String dclk = cxjgList.get(i).getDclk();
                    if(StringUtils.isNotBlank(dclk) && NumberUtils.isDigits(dclk)){
                        ws.setColumnView(i,Integer.parseInt(dclk));
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("?????????????????????Excel???????????????????????????:{}",cxdh);
        }
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
     *  ????????????????????????js???????????????
     *
     * @param file js??????
     * @return StorageDto
     * @date 2021/11/25
     * @author wutao2
     */
    @PostMapping("config/upload/js")
    public StorageDto configUploadJs(@RequestBody MultipartFile file){
        StorageDto storageDto = null;
        try {
            String userName = userManagerUtils.getCurrentUserName();
            // ?????????????????????
            String fileName = file.getOriginalFilename();
            // ????????????????????????
            String folderName = StringUtils.split(fileName, '.')[1];

            byte[] fileBytes = file.getBytes();
            // ????????????????????????????????????????????????????????????????????????????????????
            List<StorageDto> storageDtoList = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID_ZDYJS, CommonConstantUtils.SPACEID_ZDYJS_MODEL, null, fileName, null, 6);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                storageDto = storageDtoList.get(0);
                List<String> listId = new ArrayList();
                for (StorageDto storageDtoTemp : storageDtoList) {
                    listId.add(storageDtoTemp.getId());
                }
                if (CollectionUtils.isNotEmpty(listId)) {
                    boolean result = storageClient.deleteStorages(listId);
                    LOGGER.warn("?????????????????????{}?????????????????????{}", result, JSONObject.toJSONString(storageDtoList));
                }
            }
            // ???????????????????????????????????????????????????
            storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID_ZDYJS, CommonConstantUtils.SPACEID_ZDYJS_MODEL, folderName, null);
            if (storageDto != null) {
                MultipartDto multipartDto = this.getUploadParamDto(userName, storageDto, fileBytes, fileName);
                storageDto = storageClient.multipartUpload(multipartDto);
                LOGGER.warn("???????????????{}", JSONObject.toJSONString(storageDto));
            }
        } catch (IOException e) {
            LOGGER.error("?????????????????????{}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("??????????????????{}", e.getMessage());
        }
        return storageDto;
    }

    /**
     * @param currentUserName ???????????????
     * @param fileByte        ????????????
     * @param fileName        ????????????
     * @return MultipartDto ??????????????????
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description ????????????????????????
     */
    private MultipartDto getUploadParamDto(String currentUserName, StorageDto storageDto, byte[] fileByte, String fileName) {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(storageDto.getClientId());
        multipartDto.setData(fileByte);
        if (fileByte != null) {
            multipartDto.setOwner(currentUserName);
            multipartDto.setContentType("application/x-javascript");
            multipartDto.setSize(fileByte.length);
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setName(storageDto.getName());
        }
        return multipartDto;
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
        return this.exportToExcelWithZdyFilter(response, dataString, data, null);
    }

    @PostMapping("export/excel/filter")
    public String exportToExcelWithZdyFilter(HttpServletResponse response, String dataString, String data, BdcDtcxDataFilterService bdcDtcxDataFilterService){
        List<Map> listResultData = JSONObject.parseArray(data,Map.class);
        Map<String,Object> dataMap = JSONObject.parseObject(dataString,Map.class);

        // ??????????????????
        if (CollectionUtils.isEmpty(listResultData)){
            listResultData = bdcDtcxFeignService.listResultData(dataString);
            if(null != bdcDtcxDataFilterService){
                bdcDtcxDataFilterService.filterData(listResultData);
            }
        }
        // ????????????
        List<DtcxCxjgDO> cxjgListAll = bdcDtcxFeignService.getCxjgList(MapUtils.getString(dataMap,"cxid"));
        // ??????????????????Excel????????????
        List<DtcxCxjgDO> cxjgList = new ArrayList<>();
        for (DtcxCxjgDO dtcxCxjgDO : cxjgListAll){
            if ((!StringUtils.equals(dtcxCxjgDO.getJgtype(),"button")) && (!StringUtils.equals(dtcxCxjgDO.getMrxs(),"0"))){
                cxjgList.add(dtcxCxjgDO);
            }
        }
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

                this.setColumnAutoSize(sheet,bdcDtcxDTO.getCxdh());

                this.setColumnSize(sheet,bdcDtcxDTO.getCxdh(),cxjgList);

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

    /**
     * ?????????????????????????????????
     *
     * @param id ??????id
     * @param cxdh ????????????
     * @return String ??????id
     */
    @GetMapping(value = "/queryFjgl")
    public String queryWjzxid(@RequestParam("id")String id,@RequestParam("cxdh")String cxdh,@RequestParam("fjwjjmc")String fjwjjmc){
        String  storageid = "";
        // ????????????????????????????????????????????????"???????????????"
        String wjjmc = new String(Base64Utils.decodeBase64StrToByte(fjwjjmc));
        if (StringUtils.isEmpty(fjwjjmc) || StringUtils.isBlank(fjwjjmc)){
            wjjmc = "???????????????";
        }
        // ???????????????????????????
        boolean wjzxidExist = storageClient.checkExist("",cxdh+id,"",wjjmc,"",0);
        LOGGER.info("?????????????????????: {}", wjzxidExist);
        if (wjzxidExist) {
            List<StorageDto> storageDtoList = storageClient.listAllRootStorages("",cxdh+id,"",wjjmc,null,0,null,"");
            storageid = storageDtoList.get(0).getId();
            LOGGER.info("?????????????????????: spaceid={}", storageDtoList.get(0).getSpaceId());
        }
        return storageid;
    }

    /**
     * ?????????????????????
     *
     * @param id ??????id
     * @param cxdh ????????????
     * @return String ??????id
     */
    @GetMapping(value = "/createFjgl")
    public String createWjzxid(@RequestParam("id")String id, @RequestParam("cxdh")String cxdh, @RequestParam("fjwjjmc")String fjwjjmc){
        // ??????owner
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userid = userDto == null ? "" : userDto.getId();
        // ????????????????????????????????????????????????"???????????????"
        String wjjmc = new String(Base64Utils.decodeBase64StrToByte(fjwjjmc));
        if (StringUtils.isEmpty(fjwjjmc) || StringUtils.isBlank(fjwjjmc)){
            wjjmc = "???????????????";
        }

        // ??????????????????
        StorageDto storageDto = storageClient.createRootFolder("clientId",cxdh+id,wjjmc,userid);
        LOGGER.info("?????????????????????: spaceid{}", storageDto.getSpaceId());
        return storageDto.getId();
    }

    /**
     * @param processInsId ???????????????ID
     * @param clmc         ????????????
     * @param sjclxmid     ??????????????????ID
     * @return ??????????????????
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @GetMapping("/bdcSlWjscDTO")
    public Object bdcSlWjscDTO(String processInsId, String sjclxmid, String clmc, String djxl) {
        BdcSlWjscDTO bdcSlWjscDTO = new BdcSlWjscDTO();
        bdcSlWjscDTO.setToken(queryToken());
        bdcSlWjscDTO.setClientId(Constants.WJZX_CLIENTID);
        if (StringUtils.isNotBlank(clmc)) {
            BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
            bdcSlSjclQO.setGzlslid(processInsId);
            bdcSlSjclQO.setClmc(clmc);
            if (StringUtils.isNotBlank(djxl)) {
                bdcSlSjclQO.setDjxl(djxl);
            }
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjcl(bdcSlSjclQO);
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                bdcSlWjscDTO.setNodeId(bdcSlSjclDOList.get(0).getWjzxid());
            }
        }

        bdcSlWjscDTO.setsFrmOption(defaultAuthority);

        return bdcSlWjscDTO;

    }

}
