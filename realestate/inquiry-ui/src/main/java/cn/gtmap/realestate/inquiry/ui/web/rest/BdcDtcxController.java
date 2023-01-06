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
 * @description 自定义查询
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
     * 文件管理器默认权限
     */
    private static final String defaultAuthority = "{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanAddFolder\":0,\"CanDeleteFolder\":0,\"CanRenameFolder\":0,\"CanAddFile\":1,\"CanDeleteFile\":0,\"CanRenameFile\":1,\"CanDelete\":0,\"CanRename\":1,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":1,\"CanTakePhoto\":1,\"CanScan\":1,\"CanEdit\":-1}";

    /**
     * 获取配置台账分页数据
     *
     * @param dtcxQO
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO>
     * @date 2019/07/16
     * @author hanyi
     *
     * 批量查询修改为post请求，避免get请求头过大问题
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @PostMapping("/list/page")
    public Page<BdcDtcxDTO> listDtcxPage(BdcDtcxQO dtcxQO,@LayuiPageable Pageable pageable){
        dtcxQO.setSfjkcx(0);
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcDtcxDTO> bdcDtcxDTOS =  bdcDtcxFeignService.listDtcxPage(dtcxQO,pageable.getPageNumber(),pageable.getPageSize(), sort);
        //CXSQL加密
        if(bdcDtcxDTOS.hasContent()){
            for (BdcDtcxDTO bdcDtcxDTO : bdcDtcxDTOS.getContent()) {
                bdcDtcxDTO.setCxsql(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcDtcxDTO.getCxsql().getBytes())));
            }
        }
        return bdcDtcxDTOS;
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
                "            {type: 'checkbox', fixed: 'left'}," +
                "            {title: '序号',width: 60, type:'numbers'}," +
                "            {field: 'cxid', title: 'id',hide: true} ," +
                "            {field: 'cxmc', title: '查询名称', width: 150, align:'center'}, " +
                "            {field: 'cxdh', title: '查询代号', width: 100, align:'center'}, " +
                "            {field: 'cjr', title: '创建人', width: 90, align:'center'}, " +
                "            {field: 'cjsj', title: '创建时间', width: 180, align:'center'}, " +
                "            {field: 'bgr', title: '变更人', width: 90, align:'center'}, " +
                "            {field: 'bgsj', title: '变更时间', width: 180, align:'center'}, " +
                "            {field: 'url', title: '页面地址',  align:'center'}, " +
                "            {fixed: 'right', title: '操作', width: 200, align:'center',templet:'#rowTools'}, " +

                "]");
        result.put("grid",gridArray);
        JSONArray toolbarArray = JSONArray.parseArray("[" +
                "{type:'button', layEvent:'add', text:'新增', , class:'bdc-major-btn', renderer:'configAddOrChange()'}" +
                "{type:'button', layEvent:'import', text:'导入', , class:'bdc-table-second-btn', renderer:'importExcel()'}" +
                "{type:'button', layEvent:'fz', text:'复制', , class:'bdc-table-second-btn', renderer:'fuZhi()'}" +
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
    public Object checkCxtj(@RequestParam(value = "cxsql") String cxsql,
                            @RequestParam(value = "cxtj") String cxtj){
        //获得sql解密
        if(StringUtils.isNotBlank(cxsql)){
            cxsql = StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(cxsql)));
        }
        cxsql = cxsql.replace("\n"," ");
        cxsql = cxsql.trim();
        return bdcDtcxFeignService.checkCxtj(cxsql,cxtj);
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
                            @RequestParam("cxjg") String cxjg){
        //获得sql解密
        if(StringUtils.isNotBlank(cxsql)){
            cxsql = StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(cxsql)));
        }
        cxsql = cxsql.replace("\n"," ");
        cxsql = cxsql.trim();
        return bdcDtcxFeignService.checkCxjg(cxsql,cxjg);
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
    public void saveAll(BdcDtcxDTO dtcxDO,
                        @RequestParam("cxtj") String cxtj,
                        @RequestParam("cxjg") String cxjg,
                        @RequestParam("mhcx") Integer mhcx,
                        @RequestParam("fuzhiCx") boolean fuzhiCx){
        dtcxDO.setCanmhcx(mhcx);
        dtcxDO.setCxtjDOList(JSONObject.parseArray(cxtj,DtcxCxtjDO.class));
        dtcxDO.setCxjgDOList(JSONObject.parseArray(cxjg,DtcxCxjgDO.class));
        //获得的sql进行解密
        if(StringUtils.isNotBlank(dtcxDO.getCxsql())){
            dtcxDO.setCxsql(StringToolUtils.replaceBracket(new String(Base64Utils.decodeBase64StrToByte(dtcxDO.getCxsql()))));
        }
        bdcDtcxFeignService.saveAll(dtcxDO,fuzhiCx);

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
        BdcDtcxDTO bdcDtcxDTO = bdcDtcxFeignService.getCxxxByCxdh(cxdh);
        //数据中SQL加密
        if(StringUtils.isNotBlank(bdcDtcxDTO.getCxsql())){
            bdcDtcxDTO.setCxsql(StringToolUtils.replaceBracket(Base64Utils.encodeByteToBase64Str(bdcDtcxDTO.getCxsql().getBytes())));
        }
        return bdcDtcxDTO;
    }

    /**
     * 执行具体的查询
     *
     * @param dtcxRequestDTO 自定义查询条件
     * @return org.springframework.data.domain.Page
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping("list/result")
    public Page listResultPage(@RequestBody DtcxRequestDTO dtcxRequestDTO){
        if (StringUtils.isBlank(dtcxRequestDTO.getData())){
            throw new MissingArgumentException("查询条件");
        }
        Sort sort = "UNSORTED".equals(String.valueOf(dtcxRequestDTO.getSort())) ? null : dtcxRequestDTO.getSort();
        return bdcDtcxFeignService.listResult(dtcxRequestDTO.getData(), dtcxRequestDTO.getPage() - 1, dtcxRequestDTO.getSize(),
                sort);
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
     * 设置工作表自动列宽
     *
     * @param ws   表格sheet
     * @param cxdh 查询代号
     */
    private void setColumnAutoSize(WritableSheet ws,String cxdh){
        try{
            // 获取本列的最宽单元格的宽度
            for (int i = 0; i < ws.getColumns(); i++){
                int colWith = 0;
                for (int j = 0; j < ws.getRows(); j++){
                    String content = ws.getCell(i,j).getContents();
                    int cellWith = content.length();
                    if (colWith < cellWith){
                        colWith = cellWith;
                    }
                }
                // 设置单元格的宽度
                ws.setColumnView(i,colWith + 5);
            }
        }catch (Exception e){
            LOGGER.error("自定义查询导出Excel设置列宽错误:{}",cxdh);
        }
    }

    /**
     * 如果配置了导出宽度设置工作表列宽
     *
     * @param ws   表格sheet
     * @param cxdh 查询代号
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
            LOGGER.error("自定义查询导出Excel自定义设置列宽错误:{}",cxdh);
        }
    }

    /**
     * 导入配置信息到excel文件中
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
     *  上传自定义查询的js到指定目录
     *
     * @param file js文件
     * @return StorageDto
     * @date 2021/11/25
     * @author wutao2
     */
    @PostMapping("config/upload/js")
    public StorageDto configUploadJs(@RequestBody MultipartFile file){
        StorageDto storageDto = null;
        try {
            String userName = userManagerUtils.getCurrentUserName();
            // 上传的文件名称
            String fileName = file.getOriginalFilename();
            // 上传的文件夹名称
            String folderName = StringUtils.split(fileName, '.')[1];

            byte[] fileBytes = file.getBytes();
            // 避免附件重复，上传前先判断，如果已存在，则删除已有的信息
            List<StorageDto> storageDtoList = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID_ZDYJS, CommonConstantUtils.SPACEID_ZDYJS_MODEL, null, fileName, null, 6);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                storageDto = storageDtoList.get(0);
                List<String> listId = new ArrayList();
                for (StorageDto storageDtoTemp : storageDtoList) {
                    listId.add(storageDtoTemp.getId());
                }
                if (CollectionUtils.isNotEmpty(listId)) {
                    boolean result = storageClient.deleteStorages(listId);
                    LOGGER.warn("文件删除结果：{}。删除信息为：{}", result, JSONObject.toJSONString(storageDtoList));
                }
            }
            // 创建新的文件夹，已存在，则不再创建
            storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID_ZDYJS, CommonConstantUtils.SPACEID_ZDYJS_MODEL, folderName, null);
            if (storageDto != null) {
                MultipartDto multipartDto = this.getUploadParamDto(userName, storageDto, fileBytes, fileName);
                storageDto = storageClient.multipartUpload(multipartDto);
                LOGGER.warn("上传信息：{}", JSONObject.toJSONString(storageDto));
            }
        } catch (IOException e) {
            LOGGER.error("文件解析异常！{}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("文件操作异常{}", e.getMessage());
        }
        return storageDto;
    }

    /**
     * @param currentUserName 当前用户名
     * @param fileByte        文件字节
     * @param fileName        文件名称
     * @return MultipartDto 大云上传参数
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 组织大云上传参数
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
        return this.exportToExcelWithZdyFilter(response, dataString, data, null);
    }

    @PostMapping("export/excel/filter")
    public String exportToExcelWithZdyFilter(HttpServletResponse response, String dataString, String data, BdcDtcxDataFilterService bdcDtcxDataFilterService){
        List<Map> listResultData = JSONObject.parseArray(data,Map.class);
        Map<String,Object> dataMap = JSONObject.parseObject(dataString,Map.class);

        // 查询结果数据
        if (CollectionUtils.isEmpty(listResultData)){
            listResultData = bdcDtcxFeignService.listResultData(dataString);
            if(null != bdcDtcxDataFilterService){
                bdcDtcxDataFilterService.filterData(listResultData);
            }
        }
        // 查询结果
        List<DtcxCxjgDO> cxjgListAll = bdcDtcxFeignService.getCxjgList(MapUtils.getString(dataMap,"cxid"));
        // 筛选需要导出Excel的结果列
        List<DtcxCxjgDO> cxjgList = new ArrayList<>();
        for (DtcxCxjgDO dtcxCxjgDO : cxjgListAll){
            if ((!StringUtils.equals(dtcxCxjgDO.getJgtype(),"button")) && (!StringUtils.equals(dtcxCxjgDO.getMrxs(),"0"))){
                cxjgList.add(dtcxCxjgDO);
            }
        }
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

    /**
     * 查询附件文件夹是否存在
     *
     * @param id 主键id
     * @param cxdh 查询代号
     * @return String 存储id
     */
    @GetMapping(value = "/queryFjgl")
    public String queryWjzxid(@RequestParam("id")String id,@RequestParam("cxdh")String cxdh,@RequestParam("fjwjjmc")String fjwjjmc){
        String  storageid = "";
        // 获取自定义附件文件夹名称，默认为"附件文件夹"
        String wjjmc = new String(Base64Utils.decodeBase64StrToByte(fjwjjmc));
        if (StringUtils.isEmpty(fjwjjmc) || StringUtils.isBlank(fjwjjmc)){
            wjjmc = "附件文件夹";
        }
        // 查询文件夹是否存在
        boolean wjzxidExist = storageClient.checkExist("",cxdh+id,"",wjjmc,"",0);
        LOGGER.info("文件夹是否存在: {}", wjzxidExist);
        if (wjzxidExist) {
            List<StorageDto> storageDtoList = storageClient.listAllRootStorages("",cxdh+id,"",wjjmc,null,0,null,"");
            storageid = storageDtoList.get(0).getId();
            LOGGER.info("存在附件文件夹: spaceid={}", storageDtoList.get(0).getSpaceId());
        }
        return storageid;
    }

    /**
     * 新建附件文件夹
     *
     * @param id 主键id
     * @param cxdh 查询代号
     * @return String 存储id
     */
    @GetMapping(value = "/createFjgl")
    public String createWjzxid(@RequestParam("id")String id, @RequestParam("cxdh")String cxdh, @RequestParam("fjwjjmc")String fjwjjmc){
        // 获取owner
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userid = userDto == null ? "" : userDto.getId();
        // 获取自定义附件文件夹名称，默认为"附件文件夹"
        String wjjmc = new String(Base64Utils.decodeBase64StrToByte(fjwjjmc));
        if (StringUtils.isEmpty(fjwjjmc) || StringUtils.isBlank(fjwjjmc)){
            wjjmc = "附件文件夹";
        }

        // 创建新文件夹
        StorageDto storageDto = storageClient.createRootFolder("clientId",cxdh+id,wjjmc,userid);
        LOGGER.info("新建附件文件夹: spaceid{}", storageDto.getSpaceId());
        return storageDto.getId();
    }

    /**
     * @param processInsId 工作流实例ID
     * @param clmc         材料名称
     * @param sjclxmid     收件材料项目ID
     * @return 文件上传参数
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 组织文件上传参数
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
