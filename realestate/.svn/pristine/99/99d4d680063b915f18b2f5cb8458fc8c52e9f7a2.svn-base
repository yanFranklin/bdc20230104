package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AccessStatsDto;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDsfRzDO;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdSsjGxDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.shiji.BdcShijiZzxzDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.ShijgxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.config.BdcZdSsjGxQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcZdSsjgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.excel.ExcelController;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.core.dto.yancheng.BdcShijiStatisticsDTO;
import cn.gtmap.realestate.exchange.core.vo.BdcShijiStatisticsVO;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcShijiService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;

import static cn.hutool.core.collection.CollUtil.newArrayList;

@Service
public class BdcShijiServiceImpl implements BdcShijiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcShijiService.class);

    private final static String ES_QUERY_TYPE_EQUAL = "equal";

    private final static String ES_QUERY_TYPE_IN = "list_equal";

    private final static String ES_STATISTIC_KEYWORD_CZR = "czr";

    private static final String INTERFACE_NAME_KEY = "interfaceName";

    @Autowired
    private LogMessageClient logMessageClient;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private BdcZdSsjgxFeignService bdcZdSsjgxFeignService;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private EntityMapper entityMapper;

//    @Autowired
//    private HttpPostRequestForShijServiceImpl httpPostRequestForShijService;

    /**
     * 事项的编码和名称的对应关系
     */
    @Value("#{${shijgx.zzxz.codeNameMap:{'default': 'default'}}}")
    private Map<String, String> codeNameMap;
    /**
     * 事项的编码对应的工作流定义id
     */
    @Value("#{${shijgx.zzxz.codeGzldyidMap:{'default': 'default'}}}")
    private Map<String, String> codeGzldyidMap;
    /**
     * 证照名称定义
     */
    @Value("#{${yancheng.shiji.zzxz.file.name:{'jmhkbxz': '户口簿电子证照','yyzzxz':'营业执照电子证照','hydjzzxz':'结婚证电子证照','sfzzzxz': '身份证电子证照'}}}")
    private Map<String, String> fileNameMap;


    @Override
    public Object listQueryLog(BdcShijiStatisticsVO request, int pageNumber, int pageSize) {
        List<QueryLogCondition> conditions = new ArrayList<>();
        initShijiStatistics(request, conditions);
        LOGGER.info("查询es数据开始");
        Page<AuditLogDto> logPageResult = logMessageClient.listLogs(pageNumber, pageSize,
                Constants.EXCHANGE_ES_SHIJI_GX_RZLX, null, null, request.getStartTime() != null ? request.getStartTime().getTime() : null, request.getEndTime() != null ? request.getEndTime().getTime() : null, conditions);
//        LOGGER.info("查询es数据结束:{}", JSON.toJSONString(logPageResult));
        LOGGER.info("查询es数据结束");
        List<BdcShijiStatisticsDTO> list = null;
        if (logPageResult.getContent() != null && CollectionUtils.isNotEmpty(logPageResult.getContent())) {
            list = new ArrayList<>(logPageResult.getContent().size());
            for (AuditLogDto auditLogDto : logPageResult.getContent()) {
                List<DataValue> dataValues = auditLogDto.getBinaryAnnotations();
                BdcShijiStatisticsDTO bdcShijiStatisticsDTO = new BdcShijiStatisticsDTO();
                //查询接口名称、查询人、查询人所属部门、查询时间
                bdcShijiStatisticsDTO.setQueryTime(auditLogDto.getTimestamp_millis());
                for (DataValue dataValue : dataValues) {
                    if ("jkmc".equals(dataValue.getKey())) {
                        bdcShijiStatisticsDTO.setInterfaceName(dataValue.getValue());
                    } else if ("czr".equals(dataValue.getKey())) {
                        bdcShijiStatisticsDTO.setCreater(dataValue.getValue());
                    } else if ("gxbmbz".equals(dataValue.getKey())) {
                        bdcShijiStatisticsDTO.setDepartment(dataValue.getValue());
                    }
                }
                list.add(bdcShijiStatisticsDTO);
            }
        } else {
            list = new ArrayList<>();
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize, null);
        if (CollectionUtils.isNotEmpty(list)) {
            Page page = PageUtils.listToPageWithTotal(list, pageable, new Long(logPageResult.getTotalElements()).intValue());
            return addLayUiCode(page);
        } else {
            Page page = PageUtils.listToPageWithTotal(newArrayList(), pageable, 0);
            return addLayUiCode(page);
        }
    }

    @Override
    public Object exportStaticLog(BdcShijiStatisticsVO request, HttpServletResponse response) {
        List<QueryLogCondition> conditions = new ArrayList<>();
        QueryLogCondition condition = new QueryLogCondition();
        initQueryParam(condition, "event", Constants.EXCHANGE_ES_SHIJI_GX_RZLX, ES_QUERY_TYPE_EQUAL);
        conditions.add(condition);

        ExcelExportDTO excelExportDTO = new ExcelExportDTO();
        Set<String> czrSet = new HashSet<>();
        List<JSONObject> result = new ArrayList<>(ShijgxEnum.values().length);
        JSONObject czrCount = new JSONObject();
        Map<String, Integer> interfaceCount = new HashMap<>(ShijgxEnum.values().length);

        //市级接口
        List<BdcZdSsjGxDO> bdcZdSsjGxDOS = bdcZdSsjgxFeignService.listSsjgx(new BdcZdSsjGxQO());
        for (BdcZdSsjGxDO bdcZdSsjGxDO : bdcZdSsjGxDOS) {
            initInterfaceQueryCount(request, conditions, czrSet, result, czrCount, interfaceCount, bdcZdSsjGxDO.getJkmc());
        }
//        for (ShijgxEnum value : ShijgxEnum.values()) {
//            String interfaceName = value.getInterfaceName();
//            initInterfaceQueryCount(request, conditions, czrSet, result, czrCount, interfaceCount, interfaceName);
//        }
//        //省级接口
//        for (GxEnum value : GxEnum.values()) {
//            String interfaceName = "省级-" + value.getInterfaceName();
//            initInterfaceQueryCount(request, conditions, czrSet, result, czrCount, interfaceCount, interfaceName);
//        }
        String title = czrSet.toString().replace(" ", "");
        for (JSONObject rowResult : result) {
            czrSet.forEach(czr -> {
                if (!rowResult.containsKey(czr)) {
                    rowResult.put(czr, 0);
                }
            });
            rowResult.put("interfaceQueryCount", interfaceCount.get(rowResult.getString(INTERFACE_NAME_KEY)));
        }
        czrCount.put(INTERFACE_NAME_KEY, "合计");
        result.add(czrCount);
        excelExportDTO.setData(JSON.toJSONString(result));
        excelExportDTO.setFileName("市级接口统计表格");
        excelExportDTO.setSheetName("市级接口统计表格");
        excelExportDTO.setCellTitle("接口名," + title.substring(1, title.length() - 1) + ",合计");
        excelExportDTO.setCellKey(INTERFACE_NAME_KEY + "," + title.substring(1, title.length() - 1) + ",interfaceQueryCount");
        excelExportDTO.setCellWidth("60,20,20,20,20,20,20,20,20,20,20");
//        excelExportDTO.setAddBorder(true);
        try (HSSFWorkbook workbook = new HSSFWorkbook();
             OutputStream outputStream = response.getOutputStream()) {
            // 创建单个工作表
            excelController.createSheet(workbook, excelExportDTO);
            //浏览器下载
            String fileName = URLEncoder.encode(excelExportDTO.getFileName() + ".xls", "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            outputStream.flush();
            workbook.write(outputStream);
        } catch (IOException e) {
            LOGGER.error("系统导出Excel报错：{}", e.getMessage());
        }

        return null;
    }

    @Override
    public CommonResponse dealWithZzxzResponse(BdcShijiZzxzDTO zzxzRequest) {
        Example example = new Example(BdcXmDO.class);
        example.createCriteria().andEqualTo("gzlslid", zzxzRequest.getGzlslid());
        List<BdcXmDO> bdcXmList = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(bdcXmList)) {
            throw new AppException("获取bdcxm信息失败:" + zzxzRequest.getGzlslid());
        }
        zzxzRequest.setGzldyid(bdcXmList.get(0).getGzldyid());
        // 判空
        zzxzRequest.checkParam();
        String gzldyid = bdcXmList.get(0).getGzldyid();
        String serviceItemCode = "";
        String serviceItemName = "";
        if (codeGzldyidMap != null) {
            for (Map.Entry<String, String> map : codeGzldyidMap.entrySet()) {
                if (map.getValue().contains(gzldyid)){
                    serviceItemCode = map.getKey();
                }
            }
        }
        if (codeNameMap!=null){
            serviceItemName = codeNameMap.get(serviceItemCode);
        }
        JSONObject request = new JSONObject(4);
        request.put("bjry", zzxzRequest.getBjry());
        request.put("bjrzjh", zzxzRequest.getBjrzjh());
        request.put("zjh", zzxzRequest.getZjh());
        request.put("serviceItemCode", serviceItemCode);
        request.put("serviceItemName", serviceItemName);
        LOGGER.info("开始调用证照下载接口,接口的beanName：{},接口的参数：{}", zzxzRequest.getCertificateType(), request);
        Object response = exchangeBeanRequestService.request(zzxzRequest.getCertificateType(), request);
//        Object response = new JSONObject();
       /* if ("shijjk_jmhkbxz".equals(zzxzRequest.getCertificateType())) {
            response = "{\n" +
                    "    \"code\":200,\n" +
                    "    \"data\":{\n" +
                    "        \"data\":{\n" +
                    "            \"issueDept\":\"江苏省盐城市公安局城南新区分局\",\n" +
                    "            \"certificateNumber\":\"320000202101FRTUGC\",\n" +
                    "            \"fileUrl\":\"http://2.142.133.97:8090/el-web/storage/downloadOfd/320000202101FRTUGC.pdf/B797024DD32F2064F1F9A21580942FEC661003E8972F40EE743C8FA1398F0D55\",\n" +
                    "            \"issueDate\":\"2021-04-21 00:00:00\",\n" +
                    "            \"fileFormat\":\"pdf\",\n" +
                    "            \"certificateType\":\"居民户口簿\"\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"msg\":\"操作成功\",\n" +
                    "    \"time\":\"2021-11-05 10:51:52\"\n" +
                    "}";
            response = "{\n" +
                    "    \"code\": 200,\n" +
                    "    \"data\": {\n" +
                    "        \"data\": \"$get{node1.response.body.data.dataList.[0]}\"\n" +
                    "    },\n" +
                    "    \"msg\": \"操作成功\",\n" +
                    "    \"time\": \"2021-11-08 09:22:55\"\n" +
                    "}\n";
        }
        if ("shijjk_hydjzzxz".equals(zzxzRequest.getCertificateType())) {
            response = "{\n" +
                    "    \"code\":200,\n" +
                    "    \"data\":{\n" +
                    "        \"data\":{\n" +
                    "            \"total\":1,\n" +
                    "            \"dataList\":[\n" +
                    "                {\n" +
                    "                    \"issueDept\":\"……婚姻登记处\",\n" +
                    "                    \"certificateNumber\":\"3……Q\",\n" +
                    "                    \"fileUrl\":\"http://2.142.133.97:8090/el-web/storage/downloadOfd/……\",\n" +
                    "                    \"issueDate\":\"2016-11-01 00:00:00\",\n" +
                    "                    \"fileFormat\":\"pdf\",\n" +
                    "                    \"certificateType\":\"中华人民共和国结婚证\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"msg\":\"操作成功\",\n" +
                    "    \"time\":\"2021-11-08 09:10:00\"\n" +
                    "}";
            response = "{\n" +
                    "    \"code\":200,\n" +
                    "    \"data\":{\n" +
                    "        \"data\":\"$get{node1.response.body.data}\"\n" +
                    "    },\n" +
                    "    \"msg\":\"操作成功\",\n" +
                    "    \"time\":\"2021-11-08 09:14:43\"\n" +
                    "}\n";
        }
        if ("shijjk_yyzzxz".equals(zzxzRequest.getCertificateType())) {
            response = "{\n" +
                    "    \"code\": 200,\n" +
                    "    \"data\": {\n" +
                    "        \"head\": {\n" +
                    "            \"message\": \"接口调用成功\",\n" +
                    "            \"status\": 0\n" +
                    "        },\n" +
                    "        \"data\": {\n" +
                    "            \"total\": 2,\n" +
                    "            \"dataList\": [\n" +
                    "                {\n" +
                    "                    \"issueDept\": \"……理局\",\n" +
                    "                    \"certificateNumber\": \"320……CT\",\n" +
                    "                    \"fileUrl\": \"http://2.142.133.97:8090/el-web/storage/downloadOfd/……75\",\n" +
                    "                    \"issueDate\": \"2021-03-04 00:00:00\",\n" +
                    "                    \"fileFormat\": \"pdf\",\n" +
                    "                    \"certificateType\": \"营业执照\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"issueDept\": \"……管理局\",\n" +
                    "                    \"certificateNumber\": \"32……79E\",\n" +
                    "                    \"fileUrl\": \"http://2.142.133.97:8090/el-web/storage……7DC\",\n" +
                    "                    \"issueDate\": \"2019-04-04 00:00:00\",\n" +
                    "                    \"fileFormat\": \"pdf\",\n" +
                    "                    \"certificateType\": \"营业执照\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"msg\": \"操作成功\",\n" +
                    "    \"time\": \"2021-11-11 09:22:29\"\n" +
                    "}\n";
            response = "{\n" +
                    "    \"code\": 200,\n" +
                    "    \"data\": {\n" +
                    "        \"head\": {\n" +
                    "            \"message\": \"未找到匹配的证照信息\",\n" +
                    "            \"status\": 10\n" +
                    "        },\n" +
                    "        \"data\": \"$get{node1.response.body.data}\"\n" +
                    "    },\n" +
                    "    \"msg\": \"操作成功\",\n" +
                    "    \"time\": \"2021-11-11 09:24:00\"\n" +
                    "}\n";
        }
        if ("shijjk_sfz_zzxz".equals(zzxzRequest.getCertificateType())) {
            response = "{\n" +
                    "    \"code\":200,\n" +
                    "    \"data\":{\n" +
                    "        \"head\":{\n" +
                    "            \"message\":\"接口调用成功\",\n" +
                    "            \"status\":0\n" +
                    "        },\n" +
                    "        \"data\":{\n" +
                    "            \"total\":1,\n" +
                    "            \"dataList\":[\n" +
                    "                {\n" +
                    "                    \"issueDept\":\"……公安局\",\n" +
                    "                    \"certificateNumber\":\"3……NN\",\n" +
                    "                    \"fileUrl\":\"http://2.142.133.97:8090/el-web/storage/downloadOfd/……\",\n" +
                    "                    \"issueDate\":\"2014-08-05 00:00:00\",\n" +
                    "                    \"fileFormat\":\"pdf\",\n" +
                    "                    \"certificateType\":\"中华人民共和国居民身份证\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"msg\":\"操作成功\",\n" +
                    "    \"time\":\"2021-11-04 11:16:53\"\n" +
                    "}";
            response = "{\n" +
                    "    \"code\":200,\n" +
                    "    \"data\":{\n" +
                    "        \"result\":\"未找到匹配的证照信息\"\n" +
                    "    },\n" +
                    "    \"msg\":\"操作成功\",\n" +
                    "    \"time\":\"2021-02-08 14:49:14\"\n" +
                    "}";
        }*/
        if (response == null) {
            throw new AppException("下载证照接口未返回:" + zzxzRequest.getCertificateType());
        }
//        JSONObject zzxzResponse = JSONObject.parseObject(response.toString());
        JSONObject zzxzResponse = JSONObject.parseObject(JSON.toJSONString(response));
        LOGGER.info("证照接口返回：{}", zzxzResponse.toJSONString());
        //上传大云
        if (zzxzResponse.containsKey("code") && ("0".equals(zzxzResponse.getString("code")) || "200".equals(zzxzResponse.getString("code")))) {
            if (zzxzResponse.containsKey("data")) {
                JSONObject responData = zzxzResponse.getJSONObject("data");
                if (null != responData) {
                    //三个接口返回格式不一样，分开处理
                    if ("shijjk_hydjzzxz".equals(zzxzRequest.getCertificateType())) {
                        if (!isJson(responData.getString("data"))) {
                            return CommonResponse.fail("查询成功但是无证照信息");
                        }
                        JSONObject result = responData.getJSONObject("data");
                        JSONArray hyData = result.getJSONArray("dataList");
                        for (int i = 0; i < hyData.size(); i++) {
                            JSONObject data = hyData.getJSONObject(i);
                            if (StringUtils.isNotBlank(data.getString("fileUrl"))) {
                                StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, zzxzRequest.getGzlslid(), fileNameMap.containsKey(zzxzRequest.getCertificateType()) ? fileNameMap.get(zzxzRequest.getCertificateType()) : data.getString("certificateType") + "电子证照", null);
                                if (storageDto != null) {
                                    // 添加受理收件材料信息
                                    if (StringUtils.isNotBlank(storageDto.getId())) {
                                        this.addSjclxx(zzxzRequest.getGzlslid(), storageDto.getName(), storageDto.getId());
                                    }
                                    //上传附件
                                    downAndUploadFjFromUrl(data.getString("fileUrl"), zzxzRequest.getZjh() + data.getString("certificateType") + "." + data.getString("fileFormat"), storageDto.getId());
                                    return CommonResponse.ok(hyData);

                                }
                            }
                        }

                    }
                    if ("shijjk_jzgcjgysbazzxz".equals(zzxzRequest.getCertificateType())) {
                        if (!isJson(responData.getString("result"))) {
                            return CommonResponse.fail("查询成功但是无证照信息");
                        }
                        JSONObject result = responData.getJSONObject("result");
                        JSONArray hyData = result.getJSONArray("dataList");
                        for (int i = 0; i < hyData.size(); i++) {
                            JSONObject data = hyData.getJSONObject(i);
                            if (StringUtils.isNotBlank(data.getString("fileUrl"))) {
                                StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, zzxzRequest.getGzlslid(), fileNameMap.containsKey(zzxzRequest.getCertificateType()) ? fileNameMap.get(zzxzRequest.getCertificateType()) : data.getString("certificateType") + "电子证照", null);
                                if (storageDto != null) {
                                    // 添加受理收件材料信息
                                    if (StringUtils.isNotBlank(storageDto.getId())) {
                                        this.addSjclxx(zzxzRequest.getGzlslid(), storageDto.getName(), storageDto.getId());
                                    }
                                    //上传附件
                                    downAndUploadFjFromUrl(data.getString("fileUrl"), zzxzRequest.getZjh() + data.getString("certificateType") + "." + data.getString("fileFormat"), storageDto.getId());
                                    return CommonResponse.ok(hyData);

                                }
                            }
                        }

                    }
                    if ("shijjk_sfz_zzxz".equals(zzxzRequest.getCertificateType())) {
                        LOGGER.info("身份证证照接口返回开始解析：{}", responData.toJSONString());

                        if (!isJson(responData.getString("data"))) {
                            return CommonResponse.fail("查询成功但是无证照信息");
                        }
                        JSONObject result = responData.getJSONObject("data");
                        if (request.containsKey("result")) {
                            LOGGER.info("身份证证照接口返回result：{}", request.toJSONString());
                            return CommonResponse.fail("查询成功但是无证照信息");
                        }
                        JSONArray hyData = result.getJSONArray("dataList");
                        for (int i = 0; i < hyData.size(); i++) {
                            JSONObject data = hyData.getJSONObject(i);
                            if (StringUtils.isNotBlank(data.getString("fileUrl"))) {
                                StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, zzxzRequest.getGzlslid(), fileNameMap.containsKey(zzxzRequest.getCertificateType()) ? fileNameMap.get(zzxzRequest.getCertificateType()) : data.getString("certificateType") + "电子证照", null);
                                if (storageDto != null) {
                                    // 添加受理收件材料信息
                                    if (StringUtils.isNotBlank(storageDto.getId())) {
                                        this.addSjclxx(zzxzRequest.getGzlslid(), storageDto.getName(), storageDto.getId());
                                    }
                                    //上传附件
                                    downAndUploadFjFromUrl(data.getString("fileUrl"), zzxzRequest.getZjh() + data.getString("certificateType") + "." + data.getString("fileFormat"), storageDto.getId());
                                    return CommonResponse.ok(hyData);

                                }
                            }
                        }

                    }
                    if ("shijjk_yyzzxz".equals(zzxzRequest.getCertificateType())) {
                        LOGGER.info("营业执照证照接口返回：{}", responData.toJSONString());
                        if (!isJson(responData.getString("data"))) {
                            return CommonResponse.fail("查询成功但是无证照信息");
                        }
                        JSONObject result1 = responData.getJSONObject("data");
                        if (result1.containsKey("dataList")) {
                            JSONArray yyData = result1.getJSONArray("dataList");
                            for (int i = 0; i < yyData.size(); i++) {
                                JSONObject data = yyData.getJSONObject(i);
                                if (StringUtils.isNotBlank(data.getString("fileUrl"))) {
                                    StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, zzxzRequest.getGzlslid(), fileNameMap.containsKey(zzxzRequest.getCertificateType()) ? fileNameMap.get(zzxzRequest.getCertificateType()) : data.getString("certificateType") + "电子证照", null);
                                    if (storageDto != null) {
                                        // 添加受理收件材料信息
                                        if (StringUtils.isNotBlank(storageDto.getId())) {
                                            this.addSjclxx(zzxzRequest.getGzlslid(), storageDto.getName(), storageDto.getId());
                                        }
                                        //上传附件
                                        downAndUploadFjFromUrl(data.getString("fileUrl"), zzxzRequest.getZjh() + data.getString("certificateType") + "." + data.getString("fileFormat"), storageDto.getId());
                                        return CommonResponse.ok(yyData);
                                    }
                                }
                            }
                        } else {
                            return CommonResponse.fail("查询成功但是无证照信息");
                        }

                    }
                    if ("shijjk_jmhkbxz".equals(zzxzRequest.getCertificateType())) {
                        if (!isJson(responData.getString("data"))) {
                            return CommonResponse.fail("查询成功但是无证照信息");
                        }
                        JSONObject xxsj = responData.getJSONObject("data");
                        if (null != xxsj) {
                            if (StringUtils.isNotBlank(xxsj.getString("fileUrl"))) {
                                StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, zzxzRequest.getGzlslid(), fileNameMap.containsKey(zzxzRequest.getCertificateType()) ? fileNameMap.get(zzxzRequest.getCertificateType()) : xxsj.getString("certificateType") + "电子证照", null);
                                if (storageDto != null) {
                                    // 添加受理收件材料信息
                                    if (StringUtils.isNotBlank(storageDto.getId())) {
                                        this.addSjclxx(zzxzRequest.getGzlslid(), storageDto.getName(), storageDto.getId());
                                    }
                                    //上传附件
                                    downAndUploadFjFromUrl(xxsj.getString("fileUrl"), zzxzRequest.getZjh() + xxsj.getString("certificateType") + "." + xxsj.getString("fileFormat"), storageDto.getId());
                                }
                                return CommonResponse.ok(xxsj);
                            }
                        } else {
                            return CommonResponse.fail("查询成功但是无证照信息");
                        }
                    }

                } else {
                    return CommonResponse.fail("查询成功但是无证照信息");

                }
            } else {
                return CommonResponse.fail("查询成功但是无证照信息");
            }
        } else {
            return CommonResponse.fail("查询失败:" + JSON.toJSONString(zzxzResponse));
        }
        return CommonResponse.fail("查询失败未进入:" + JSON.toJSONString(zzxzResponse));
    }

    private void initInterfaceQueryCount(BdcShijiStatisticsVO request, List<QueryLogCondition> conditions, Set<String> czrSet, List<JSONObject> result, JSONObject czrCount, Map<String, Integer> interfaceCount, String interfaceName) {
        if (conditions.size() > 1) {
            conditions.remove(1);
        }
        QueryLogCondition jkmcCondition = new QueryLogCondition();
        initQueryParam(jkmcCondition, "jkmc", interfaceName, ES_QUERY_TYPE_EQUAL);
        conditions.add(1, jkmcCondition);
        AccessStatsDto czrStatisticInfo = logMessageClient.statisticLog(ES_STATISTIC_KEYWORD_CZR, Constants.EXCHANGE_ES_SHIJI_GX_RZLX, null, request.getStartTime() != null ? request.getStartTime().getTime() : null, request.getEndTime() != null ? request.getEndTime().getTime() : null, conditions);
        Map<String, Integer> czrQueryCount = czrStatisticInfo.getDetails();
        JSONObject interfaceQueryTimesInfo = new JSONObject(czrQueryCount.size() + 1);
        interfaceQueryTimesInfo.put(INTERFACE_NAME_KEY, interfaceName);
        if (MapUtils.isNotEmpty(czrQueryCount)) {
            for (String czr : czrQueryCount.keySet()) {
                czrSet.add(czr);
                interfaceQueryTimesInfo.put(czr, czrQueryCount.get(czr));
                if (czrCount.containsKey(czr)) {
                    czrCount.put(czr, czrQueryCount.get(czr) + czrCount.getInteger(czr));
                } else {
                    czrCount.put(czr, czrQueryCount.get(czr));
                }
            }
        }
        if (interfaceCount.containsKey(interfaceName)) {
            interfaceCount.put(interfaceName, czrStatisticInfo.getTotal() + interfaceCount.get(interfaceName));
        } else {
            interfaceCount.put(interfaceName, czrStatisticInfo.getTotal());
        }
        result.add(interfaceQueryTimesInfo);
    }


    private void initQueryParam(QueryLogCondition condition, String key, String value, String type) {
        condition.setType(type);
        condition.setKey(key);
        condition.setValue(value);
    }

    private void initShijiStatistics(BdcShijiStatisticsVO request, List<QueryLogCondition> conditions) {

        if (StringUtils.isNotBlank(request.getCreater())) {
            QueryLogCondition condition = new QueryLogCondition();
            initQueryParam(condition, "czr", request.getCreater(), ES_QUERY_TYPE_EQUAL);
            conditions.add(condition);
        }
        if (StringUtils.isNotBlank(request.getDepartment())) {
            QueryLogCondition condition = new QueryLogCondition();
            initQueryParam(condition, "gxbmbz", request.getDepartment(), ES_QUERY_TYPE_EQUAL);
            conditions.add(condition);
        }
        if (StringUtils.isNotBlank(request.getInterfaceName())) {
            QueryLogCondition condition = new QueryLogCondition();
            if (request.getInterfaceName().contains(",")) {
                initQueryParam(condition, "jkmc", request.getInterfaceName(), ES_QUERY_TYPE_IN);
            } else {
                initQueryParam(condition, "jkmc", request.getInterfaceName(), ES_QUERY_TYPE_EQUAL);
            }
            conditions.add(condition);
        }
//        QueryLogCondition common = new QueryLogCondition();
//        initQueryParam(common, "xyf", "ZWGX", ES_QUERY_TYPE_EQUAL);
//        conditions.add(common);
        QueryLogCondition event = new QueryLogCondition();
        initQueryParam(event, "event", Constants.EXCHANGE_ES_SHIJI_GX_RZLX, ES_QUERY_TYPE_EQUAL);
        conditions.add(event);
    }

    /**
     * 处理分页结构数据 添加code字段
     *
     * @param page
     * @return
     */
    private static Object addLayUiCode(Page page) {
        Map map = null;
        if (page != null) {
            map = JSONObject.parseObject(JSONObject.toJSONString(page), Map.class);
            if (map != null) {
                map.put("msg", "成功");
            }
            if (map == null) {
                map = new HashMap(1);
                map.put("msg", "无数据");
            }
            map.put("code", 0);
        }
        return map;
    }

    /**
     * 添加受理材料信息
     *
     * @param gzlslid  工作流实例ID
     * @param foldName 文件夹名称
     * @param wjzxId   文件中心ID
     */
    @Override
    public void addSjclxx(String gzlslid, String foldName, String wjzxId) {
        // 获取受理收件材料
        List<BdcSlSjclDO> sjclList = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, foldName);
        BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
        int xh = 0;
        // 如果收件材料中已经有了目录,不再新增目录，受理页面的收件单只保留一个目录
        if (CollectionUtils.isNotEmpty(sjclList)) {
            bdcSlSjclDO = sjclList.get(0);
        } else {
            // 没有目录，新增目录，初始化附件材料信息
            List<BdcSlSjclDO> sjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
            xh = sjclDOList.size();
            bdcSlSjclDO.setGzlslid(gzlslid);
            bdcSlSjclDO.setClmc(foldName);
            bdcSlSjclDO.setWjzxid(wjzxId);
            bdcSlSjclDO.setFs(1);
            bdcSlSjclDO.setYs(1);
            bdcSlSjclDO.setMrfs(1);
            bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
            bdcSlSjclDO.setXh(++xh);
        }


        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            bdcSlSjclDO.setDjxl(bdcXmDTOList.get(0).getDjxl());
        }
        LOGGER.info("保存不动产受理收件材料信息{}", JSONObject.toJSONString(bdcSlSjclDO));
        if (StringUtils.isNotBlank(bdcSlSjclDO.getSjclid())) {
            this.bdcSlSjclFeignService.updateBdcSlSjcl(bdcSlSjclDO);
        } else {
            this.bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
        }
    }

    /**
     * @param fjmc  附件名称
     * @param fjurl 附件URL
     * @param wjjid 文件夹ID
     * @description 根据附件URL地址下载并上传附件
     */
    @Override
    public void downAndUploadFjFromUrl(String fjurl, String fjmc, String wjjid) {
        InputStream is;
        try {
            is = httpClientService.doGetReturnStream(fjurl);
            if (is != null) {
                byte[] dataBytes = Base64Utils.getByteByIn(is);
                if (dataBytes != null) {
                    MultipartDto multipartDto = getUploadParamDto(wjjid, dataBytes, fjmc);
                    StorageDto dto = storageClient.multipartUpload(multipartDto);
                    LOGGER.info("附件上传成功 fjmc:{},storageid:{}", fjmc, dto.getId());
                }
            }
        } catch (Exception e) {
            LOGGER.error("下载附件 {} 获取流异常", fjmc, e);
        }
    }

    /**
     * @param fjmc  附件名称
     * @param fjurl 附件URL
     * @param wjjid 文件夹ID
     * @description 根据附件URL地址下载并上传附件
     */
    @Override
    public void downAndUploadFjFromUrlThrowException(String fjurl, String fjmc, String wjjid) {
        InputStream is;
        try {
            is = httpClientService.doGetReturnStream(fjurl);
            if (is != null) {
                byte[] dataBytes = Base64Utils.getByteByIn(is);
                if (dataBytes != null) {
                    MultipartDto multipartDto = getUploadParamDto(wjjid, dataBytes, fjmc);
                    StorageDto dto = storageClient.multipartUpload(multipartDto);
                    LOGGER.info("附件上传成功 fjmc:{},storageid:{}", fjmc, dto.getId());
                }
            }
        } catch (Exception e) {
            LOGGER.error("下载附件 {} 获取流异常", fjmc, e);
            throw new AppException("下载附件异常");
        }
    }


    /**
     * @param wjjid 文件夹ID
     * @return
     * @description 组织上传附件对象
     */
    private static MultipartDto getUploadParamDto(String wjjid, byte[] dataBytes, String fileName) {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(wjjid);
        multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        if (dataBytes != null) {
            multipartDto.setData(dataBytes);
            multipartDto.setContentType("application/octet-stream");
            multipartDto.setSize(dataBytes.length);
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setName(fileName);
        }
        return multipartDto;
    }

    public static boolean isJson(String content) {
        if (StringUtils.isEmpty(content)) {
            return false;
        }
        boolean isJsonObject = true;
        boolean isJsonArray = true;
        try {
            JSONObject.parseObject(content);
        } catch (Exception e) {
            isJsonObject = false;
        }
        try {
            JSONObject.parseArray(content);
        } catch (Exception e) {
            isJsonArray = false;
        }
        //不是json格式
        if (!isJsonObject && !isJsonArray) {
            return false;
        }
        return true;
    }

    /**
     * 通过市级接口ES日志至数据库中
     */
    @Override
    public void syncShijiEsLogToDB(String kssj) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.formatDate(kssj));

        //逐日打印日期
        LocalDate startDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DATE));
        LocalDate endDate = LocalDate.now();

        //日志查询条件
        List<QueryLogCondition> conditions = new ArrayList<>();
        QueryLogCondition event = new QueryLogCondition();
        initQueryParam(event, "event", Constants.EXCHANGE_ES_SHIJI_GX_RZLX, ES_QUERY_TYPE_EQUAL);
        conditions.add(event);

        while(startDate.isBefore(endDate)){
            // 以10天为一周期，获取es数据
            Date startTime = DateUtils.formatDate(startDate.toString() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            startDate = startDate.plusDays(10);
            Date endTime = DateUtils.formatDate(startDate.toString() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

            Page<AuditLogDto> logPageResult = logMessageClient.listLogs(0, 10,
                    Constants.EXCHANGE_ES_SHIJI_GX_RZLX, null, null,
                    startTime.getTime(), endTime.getTime(), conditions);
            if(logPageResult.getContent().size() > 0){
                this.convertAndSaveDsfRz(logPageResult.getContent());
                for(int i=1; i<=logPageResult.getTotalPages(); i++ ){
                    Page<AuditLogDto> pageList = logMessageClient.listLogs(i, 10,
                            Constants.EXCHANGE_ES_SHIJI_GX_RZLX, null, null, startTime.getTime(), endTime.getTime(), conditions);
                    this.convertAndSaveDsfRz(pageList.getContent());
                }
            }
        }
    }

    /**
     * 将 AuditLogDto 对象转换成 BdcDsfRzDO 对象后并保存
     */
    private void convertAndSaveDsfRz(List<AuditLogDto> auditLogDtoList){
        if(CollectionUtils.isEmpty(auditLogDtoList)){
            return;
        }
        List<BdcDsfRzDO> bdcDsfRzDOList = new ArrayList<>(auditLogDtoList.size());
        for(AuditLogDto auditLogDto: auditLogDtoList){
            if (CollectionUtils.isNotEmpty(auditLogDto.getBinaryAnnotations())) {
                JSONObject jsonObject = new JSONObject();
                for (DataValue dataValue : auditLogDto.getBinaryAnnotations()) {
                    String key = dataValue.getKey().toLowerCase();
                    String value = dataValue.getValue();
                    if(StringUtils.isBlank(key) || StringUtils.isBlank(value)){
                        continue;
                    }
                    jsonObject.put(key, value);
                }
                if(!jsonObject.isEmpty()){
                    BdcDsfRzDO bdcDsfRzDO = JSON.parseObject(jsonObject.toJSONString(), BdcDsfRzDO.class);
                    if(StringUtils.isBlank(bdcDsfRzDO.getRzid())){
                        bdcDsfRzDO.setRzid(UUIDGenerator.generate16());
                    }
                    bdcDsfRzDOList.add(bdcDsfRzDO);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(bdcDsfRzDOList)){
            entityMapper.batchSaveSelective(bdcDsfRzDOList);
        }
    }


}
