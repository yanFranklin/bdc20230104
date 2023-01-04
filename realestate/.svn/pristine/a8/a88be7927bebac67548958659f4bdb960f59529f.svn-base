package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.com.infosec.netsign.agent.NetSignAgent;
import cn.com.infosec.netsign.agent.NetSignResult;
import cn.com.infosec.netsign.agent.exception.NetSignAgentException;
import cn.com.infosec.netsign.agent.exception.ServerProcessException;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.fs.FsczfpxxRquestDTO;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.dto.fs.FsCommonRequest;
import cn.gtmap.realestate.exchange.core.dto.yancheng.fs.EInvoice;
import cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data.*;
import cn.gtmap.realestate.exchange.core.dto.yancheng.fs.head.Header;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcFsService;
import cn.gtmap.realestate.exchange.util.Base64Util;
import cn.gtmap.realestate.exchange.util.Md5Util;
import cn.gtmap.realestate.exchange.util.XmlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.gtmap.realestate.common.util.DateUtils.sdf_ymdhms;

@Service
public class BdcFsServiceImpl implements BdcFsService {

    private static final Logger logger = LoggerFactory.getLogger(BdcFsServiceImpl.class);

    @Value("${exchange.feishui.interface.agencyCode:bdcdj}")
    private String agencyCode;
    @Value("${exchange.feishui.interface.appId:bdcdj}")
    private String appId;
    @Value("${exchange.feishui.interface.encryption:0}")
    private String encryption;
    @Value("${exchange.feishui.interface.regionCode:}")
    private String regionCode;
    @Value("${exchange.feishui.interface.version:1.0.1}")
    private String version;
    @Value("${exchange.feishui.interface.appkey:exchange}")
    private String appkey;
    @Value("${exchange.feishui.interface.test.flag:true}")
    private boolean testFlag;
    @Value("${data.version:standard}")
    private String dataVersion;
//    @Value("${exchange.feishui.interface.author:盐城市不动产登记中心}")
//    private String author;
    @Value("${exchange.feishui.interface.acc.assign.type:1}")
    private String accAssignType;
    @Value("${exchange.feishui.interface.rec.name:}")
    private String recName;
    @Value("${exchange.feishui.interface.rec.no:}")
    private String recNo;
    @Value("${exchange.feishui.interface.rec.bank:}")
    private String recBank;
//    @Value("${exchange.feishui.interface.invoicingParty.rec.name:}")
//    private String invoicingPartyRecName;
//    @Value("${exchange.feishui.interface.invoicingParty.rec.acct:}")
//    private String invoicingPartyRecAcct;
//    @Value("${exchange.feishui.interface.invoicingParty.rec.opbk:}")
//    private String invoicingPartyRecOpBk;
    @Value("${exchange.feishui.interface.issue.bill.batch.code:32010121}")
    private String billBatchCode4Issue;
    @Value("${exchange.feishui.interface.confirm.bill.batch.code:32990121}")
    private String billBatchCode4Confirm;

    private static String DATA_VERSION;

    //利用@PostConstruct将application中配置的值赋给本地的变量
    @PostConstruct
    public void getServelPort(){
        DATA_VERSION = this.dataVersion;
        if (DATA_VERSION.equals("yancheng")){
            try {
                NetSignAgent.initialize() ;
            } catch (NetSignAgentException e) {
                logger.error("初始化签章系统失败:",e);
            }
        }
    }

    /**
     * 第三方商品编码配置
     */
    @Value("#{${exchange.feishui.interface.itemcode:{'登记费': '10304321102'}}}")
    private Map<String, String> itemCodeMap;
    /**
     * 第三方商品编码配置
     */
    @Value("#{${exchange.feishui.interface.place.code:{'mawei': '1'}}}")
    private Map<String, String> placeCodeMap;

    private static final String FORMAT_JSON = "json";
    private static final String FORMAT_XML = "xml";

    private static final String YC_FS_ITEM_INFO_REDIS_KEY = "YC_FS_ITEM_INFO_REDIS_KEY";

    private static final String CRETE_TAX_NOTICE_METHOD_ID = "paybook.e.issue.do";
    private static final String CONFIRM_PAY_STATUS_METHOD_ID = "paybook.confirm.get";
    private static final String GET_ETICKET_NUM_METHOD_ID = "stock.billno.get";
    private static final String GET_INVOICE_BY_PAYCODE_METHOD_ID = "invoice.bypaycode.get";
    private static final String DOWNLOAD_INVOICE_METHOD_ID = "invoice.jiangsu.get";
    private static final String ISSUE_INVOICE_METHOD_ID = "invoice.e.issue.withsign.do";
    private static final String GET_ITEMS_METHOD_ID = "basic.items.get";
    private static final String FS_COMMON_INTERFACE = "fsCommonInterface";
    private static final String PAYBOOK_INVALIDATE_DO_METHOD_ID = "paybook.e.invalidate.do";


    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;
    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;
    @Autowired
    private RedisUtils redisUtils;

    private Map<String,String> initItems(String placeCode){
        if (StringUtils.isNotBlank(placeCode)) {
            return null;
        }
        try {
            FsCommonRequest fsCommonRequest = initRequestParam(placeCode, FORMAT_JSON, GET_ITEMS_METHOD_ID);
            logger.info("initItems 接口调用最终入参:{}",JSON.toJSONString(fsCommonRequest));
            Object response = exchangeBeanRequestService.request(FS_COMMON_INTERFACE, fsCommonRequest);
//            Object response = new JSONObject();
            CommonResponse<Object> objectCommonResponse = dealResponse(response);
            if (objectCommonResponse.isSuccess()) {
                JSONObject jsonObject = JSON.parseObject((String) objectCommonResponse.getData());
                JSONArray items = jsonObject.getJSONArray("items");
                if (CollectionUtils.isNotEmpty(items)){
                    Map<String,String> itemMap = new HashMap<>(items.size());
                    for (int i = 0; i < items.size(); i++) {
                        itemMap.put(items.getJSONObject(i).getJSONObject("item").getString("item_name"),items.getJSONObject(i).getJSONObject("item").getString("item_code"));
                    }
                    redisUtils.addStringValue(YC_FS_ITEM_INFO_REDIS_KEY, JSONObject.toJSONString(itemMap));
                    return itemMap;
                }
            }

        } catch (Exception e) {
            logger.info("initItems接口异常:", e);
        }
        return null;
    }

    @Override
    public CommonResponse createTaxNotice(FsczfpxxRquestDTO requestParam) {
        logger.info("createTaxNotice入参:{}",JSON.toJSONString(requestParam));
        if (requestParam == null) {
            return CommonResponse.fail("参数为空！");
        }
        //转换入参
        JSONObject requestJson = new JSONObject();
        dozerBeanMapper.map(requestParam, requestJson, "jfsxx2FsCreateTaxNoticeRequest");
        initPlaceCode(requestJson);
        if (CollectionUtils.isNotEmpty(requestParam.getItem())) {
            JSONArray itemList = new JSONArray(requestParam.getItem().size());
            requestParam.getItem().forEach(jfsxxRequestJkxmDTO -> {
                if ("1".equals(jfsxxRequestJkxmDTO.getUnits())){
                    jfsxxRequestJkxmDTO.setUnits("元");
                }else if ("2".equals(jfsxxRequestJkxmDTO.getUnits())){
                    jfsxxRequestJkxmDTO.setUnits("元");
                    jfsxxRequestJkxmDTO.setAmount(jfsxxRequestJkxmDTO.getAmount() * 10000);
                    jfsxxRequestJkxmDTO.setTotalAmount(jfsxxRequestJkxmDTO.getTotalAmount() * 10000);
                }
                JSONObject itemJson = new JSONObject();
                dozerBeanMapper.map(jfsxxRequestJkxmDTO, itemJson, "jfDetail");
                logger.info("开始转换code");
                if (MapUtils.isNotEmpty(itemCodeMap) && itemCodeMap.containsKey(jfsxxRequestJkxmDTO.getName())){
                    itemJson.put("item_code",itemCodeMap.get(jfsxxRequestJkxmDTO.getName()));
                }
                if (itemJson.containsKey("name")){
                    itemJson.remove("name");
                }
//                if (redisUtils.isExistKey(YC_FS_ITEM_INFO_REDIS_KEY)){
//                    logger.info("开始转换code");
//                    String stringValue = redisUtils.getStringValue(YC_FS_ITEM_INFO_REDIS_KEY);
//                    JSONObject jsonObject = JSON.parseObject(stringValue, JSONObject.class);
//                    itemJson.put("item_code",jsonObject.getString(jfsxxRequestJkxmDTO.getName()));
//                }else {
//                    logger.info("开始转换code");
//                    Map<String, String> itemMap = initItems(requestJson.getString("place_code"));
//                    if (itemMap != null && itemMap.containsKey(jfsxxRequestJkxmDTO.getName())){
//                        itemJson.put("item_code",itemMap.get(jfsxxRequestJkxmDTO.getName()));
//                    }
//                }
                itemList.add(itemJson);
            });
            requestJson.put("item_details", itemList);
        }
        if (requestJson.containsKey("bill_code")) {
            requestJson.put("bill_batch_code", requestJson.getString("bill_code") + (getYear()));
        }
        if (StringUtils.isNotBlank(requestParam.getRemark())){
            requestJson.put("memo",requestParam.getRemark());
        }
        if (StringUtils.isNotBlank(requestParam.getCreater())){
            requestJson.put("author",requestParam.getCreater());
        }
        //配置的缴费银行添加
        if (StringUtils.isNotBlank(accAssignType)){
            requestJson.put("acc_assign_type",accAssignType);
        }
        if (StringUtils.isNotBlank(recName)){
            requestJson.put("rec_name",recName);
        }
        if (StringUtils.isNotBlank(recNo)){
            requestJson.put("rec_no",recNo);
        }
        if (StringUtils.isNotBlank(recBank)){
            requestJson.put("rec_bank",recBank);
        }
        if (testFlag){
            requestJson.put("bill_code","09");
            requestJson.put("bill_batch_code",billBatchCode4Confirm);
            requestJson.put("place_code","001");
        }
        requestJson.put("bill_batch_code",billBatchCode4Confirm);
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        requestJson.put("date",date.format(new Date()));
        JSONObject commonJSON = new JSONObject();
        commonJSON.put("message",requestJson);
        try {
            FsCommonRequest fsCommonRequest = initRequestParam(commonJSON.toJSONString(), FORMAT_JSON, CRETE_TAX_NOTICE_METHOD_ID);
            logger.info("createTaxNotice接口调用最终入参:{}",JSON.toJSONString(fsCommonRequest));
            Object response = exchangeBeanRequestService.request(FS_COMMON_INTERFACE, fsCommonRequest);
//            Object response = new JSONObject();
            return dealResponse(response);
        } catch (Exception e) {
            logger.info("creteTaxNotice接口异常:", e);
            return CommonResponse.fail("creteTaxNotice接口异常:" + e.getMessage());
        }
    }

    /**
     * @param requestJson
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 获取缴费状态
     */
    @Override
    public CommonResponse confirmPayStatus(Map requestJson) {
        if (requestJson == null) {
            return CommonResponse.fail("参数为空！");
        }
        try {
            JSONObject commonJSON = new JSONObject();
            commonJSON.put("message",requestJson);
            FsCommonRequest fsCommonRequest = initRequestParam(commonJSON.toJSONString(), FORMAT_JSON, CONFIRM_PAY_STATUS_METHOD_ID);
            logger.info("confirmPayStatus 接口调用最终入参:{}",JSON.toJSONString(fsCommonRequest));
            Object response = exchangeBeanRequestService.request(FS_COMMON_INTERFACE, fsCommonRequest);
            CommonResponse<Object> objectCommonResponse = dealResponse(response);
            if (objectCommonResponse.isSuccess()) {
                //转换入参
           /* String response = "{\n" +
                    "    \"confirm_date\": \"2021-12-02\",\n" +
                    "    \"payment_channel\": \"支付宝\",\n" +
                    "    \"pay_proxy_bank\": \"中国建设银行 \",\n" +
                    "    \"pay_proxy_bank_code\": \"0105\",\n" +
                    "    \"total_amt\": 80,\n" +
                    "    \"confirm_time\": \"10:21:30\",\n" +
                    "    \"confirm_code\": \"\",\n" +
                    "    \"operator\": \"G000001\",\n" +
                    "    \"bank_out_let\": \"00000\",\n" +
                    "    \"rec_acct_bank\": \"0301\",\n" +
                    "    \"rec_acct_op_bank\": \"交通银行股份有限公司盐城分行营业部\",\n" +
                    "    \"rec_acc_id_code\": \"\",\n" +
                    "    \"rec_acct_type\": \"1\",\n" +
                    "    \"rec_acct\": \"329009001018010006746\",\n" +
                    "    \"receive\": \"0\",\n" +
                    "    \"app_src\": \"1\",\n" +
                    "    \"payment_channel_code\": \"51\"\n" +
                    "}";*/
                BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                JSONObject resp = JSON.parseObject((String) objectCommonResponse.getData());
                String date = resp.getString("confirm_date") + " " + resp.getString("confirm_time");
                logger.info("非税返回的时间：{}", date);
                bdcSlSfxxDO.setJkrq(DateUtils.formatDate(date, sdf_ymdhms));
                bdcSlSfxxDO.setSfsj(DateUtils.formatDate(date, sdf_ymdhms));
                logger.info("受理更新的时间：{}", bdcSlSfxxDO.toString());
                dozerBeanMapper.map(resp, bdcSlSfxxDO, "responseJson2BdcSlSfxxDO");
                return CommonResponse.ok(bdcSlSfxxDO);
            } else {
                return objectCommonResponse;
            }
        } catch (Exception e) {
            logger.info("confirmPayStatus接口异常:", e);
            return CommonResponse.fail("confirmPayStatus接口异常:" + e.getMessage());
        }
    }

    /**
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 获取电子票号
     */
    @Override
    public CommonResponse getEticketNum(Map map) {
        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put("userCode",map.get("userCode"));
            initPlaceCode(requestJson);
            requestJson.put("bill_batch_code", "2" + getYear());
            if (testFlag){
                requestJson.put("bill_batch_code",billBatchCode4Issue);
                requestJson.put("place_code","001");
            }
            requestJson.put("bill_batch_code",billBatchCode4Issue);
            JSONObject commonJSON = new JSONObject();
            commonJSON.put("message",requestJson);
            FsCommonRequest fsCommonRequest = initRequestParam(commonJSON.toJSONString(), FORMAT_JSON, GET_ETICKET_NUM_METHOD_ID);
            logger.info("getEticketNum 接口调用最终入参:{}",JSON.toJSONString(fsCommonRequest));
            Object response = exchangeBeanRequestService.request(FS_COMMON_INTERFACE, fsCommonRequest);
//            Object response = new JSONObject();
            return dealResponse(response);
        } catch (Exception e) {
            logger.info("getEticketNum接口异常:", e);
            return CommonResponse.fail("getEticketNum接口异常:" + e.getMessage());
        }
    }

    /**
     * @param requestJson
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 作废电子缴费书
     */
    @Override
    public CommonResponse invalidate(Map requestJson) {
        if (requestJson == null) {
            return CommonResponse.fail("参数为空！");
        }
        try {
            JSONObject commonJSON = new JSONObject();
            commonJSON.put("message",requestJson);
            FsCommonRequest fsCommonRequest = initRequestParam(commonJSON.toJSONString(), FORMAT_JSON, PAYBOOK_INVALIDATE_DO_METHOD_ID);
            logger.info("invalidate 接口调用最终入参:{}",JSON.toJSONString(fsCommonRequest));
            Object response = exchangeBeanRequestService.request(FS_COMMON_INTERFACE, fsCommonRequest);
//            Object response = new JSONObject();
            return dealResponse(response);
        } catch (Exception e) {
            logger.info("invalidate接口异常:", e);
            return CommonResponse.fail("invalidate接口异常:" + e.getMessage());
        }
    }

//    /**
//     * @param requestJson
//     * @return
//     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
//     * 根据缴款码获取对应电子票信息
//     */
//    @Override
//    public CommonResponse getInvoiceByPaycode(Map requestJson) {
//        if (requestJson == null) {
//            return CommonResponse.fail("参数为空！");
//        }
//        try {
//            JSONObject commonJSON = new JSONObject();
//            commonJSON.put("message",requestJson);
//            FsCommonRequest fsCommonRequest = initRequestParam(commonJSON.toJSONString(), FORMAT_JSON, GET_INVOICE_BY_PAYCODE_METHOD_ID);
//            Object response = exchangeBeanRequestService.request(FS_COMMON_INTERFACE, fsCommonRequest);
//            logger.info("getInvoiceByPaycode 接口调用最终入参:{}",JSON.toJSONString(fsCommonRequest));
////            Object response = new JSONObject();
//            CommonResponse<Object> objectCommonResponse = dealResponse(response);
//            if (objectCommonResponse.isSuccess()) {
//                logger.info("invoice.bypaycode.get返回:"+JSON.toJSONString(response));
//                JSONObject jsonObject = JSON.parseObject((String) objectCommonResponse.getData());
//                JSONArray tickets = jsonObject.getJSONArray("tickets");
//                if (CollectionUtils.isNotEmpty(tickets)) {
//                    List<BdcSlFpxxDO> bdcSlFpxxDOS = new ArrayList<>(tickets.size());
//                    for (int i = 0; i < tickets.size(); i++) {
//                        JSONObject ticket = tickets.getJSONObject(i);
//                        //转换入参
//                        BdcSlFpxxDO bdcSlFpxxDO = new BdcSlFpxxDO();
//                        dozerBeanMapper.map(ticket, bdcSlFpxxDO, "responseJson2BdcSlFpxxDO");
//                        if(StringUtils.isNotBlank(ticket.getString("date"))){
//                            SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
//                            bdcSlFpxxDO.setKprq(data.parse(ticket.getString("date")));
//                        }
//                        bdcSlFpxxDOS.add(bdcSlFpxxDO);
//                    }
//                    return CommonResponse.ok(bdcSlFpxxDOS);
//                }
//                return CommonResponse.fail("返回参数无tickets信息");
//            } else {
//                return objectCommonResponse;
//            }
//        } catch (Exception e) {
//            logger.info("getInvoiceByPaycode接口异常:", e);
//            return CommonResponse.fail("getInvoiceByPaycode接口异常:" + e.getMessage());
//        }
//    }

    /**
     * @param requestJson
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 电子票据下载（根据缴款码获取对应电子票）
     */
    @Override
    public CommonResponse downloadInvoice(Map requestJson) {
        if (requestJson == null) {
            return CommonResponse.fail("参数为空！");
        }
        try {
            JSONObject commonJSON = new JSONObject();
            commonJSON.put("message",requestJson);
            FsCommonRequest fsCommonRequest = initRequestParam(commonJSON.toJSONString(), FORMAT_JSON, DOWNLOAD_INVOICE_METHOD_ID);
            logger.info("downloadInvoice 接口调用最终入参:{}",JSON.toJSONString(fsCommonRequest));
            Object response = exchangeBeanRequestService.request(FS_COMMON_INTERFACE, fsCommonRequest);
//            Object response = new JSONObject();
            return dealResponse(response);
        } catch (Exception e) {
            logger.info("downloadInvoice接口异常:", e);
            return CommonResponse.fail("downloadInvoice接口异常:" + e.getMessage());
        }
    }

    /**
     * @param requestDTO
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 接收已单位签名的开票数据
     */
    @Override
    public CommonResponse issueInvoice(FsczfpxxRquestDTO requestDTO) {
        if (requestDTO == null) {
            return CommonResponse.fail("参数为空！");
        }
        //转换入参
        JSONObject requestJson = new JSONObject();
        dozerBeanMapper.map(requestDTO, requestJson, "jfsxx2FsIssueInvoiceRequest");
        if (StringUtils.isNotBlank(requestDTO.getPayCode())) {
            initPayCodeInfo(requestDTO, requestJson);
        }
//        requestJson.put("author",author);
        requestJson.put("bill_batch_code", "2" + (getYear()));
        initPlaceCode(requestJson);
        if (testFlag){
            requestJson.put("bill_batch_code",billBatchCode4Issue);
            requestJson.put("place_code","001");
        }
        requestJson.put("bill_batch_code",billBatchCode4Issue);
//        requestJson.put("place_code", "result.getBdczdz()");
        //invoice_data数据组装
        try {
            Date currentDate = new Date();
            EInvoice eInvoice = initEInvoiceInfo(requestJson,requestDTO,currentDate);
            String invoiceDataStr = XmlUtils.getXmlStrByObjectWithOutFormat(eInvoice, EInvoice.class);
            invoiceDataStr = dealXml(invoiceDataStr);
            requestJson.put("invoice_data",Base64Util.str2Baes64Str(invoiceDataStr));
            //签章
            //签名原文
            JSONObject signInfo = initSignInfo(currentDate, invoiceDataStr);
            requestJson.put("sign_info",signInfo);
            JSONObject commonJSON = new JSONObject();
            commonJSON.put("message",requestJson);
            FsCommonRequest fsCommonRequest = initRequestParam(commonJSON.toJSONString(), FORMAT_JSON, ISSUE_INVOICE_METHOD_ID);
            logger.info("fsCommonRequest 接口调用最终入参:{}",JSON.toJSONString(fsCommonRequest));
            Object response = exchangeBeanRequestService.request(FS_COMMON_INTERFACE, fsCommonRequest);
//            Object response = new JSONObject();
            return dealResponse(response);
        } catch (Exception e) {
            logger.info("issueInvoice接口异常:", e);
            return CommonResponse.fail("issueInvoice接口异常:" + e.getMessage());
        }
    }

    @NotNull
    private String dealXml(String invoiceDataStr) {
        if (invoiceDataStr.startsWith("<?xml ")) {
            invoiceDataStr = invoiceDataStr.substring(invoiceDataStr.indexOf("?>") + 2);
        }
        if (invoiceDataStr.contains("<EInvoice>")){
            invoiceDataStr = invoiceDataStr.replaceAll("<EInvoice>","");
            invoiceDataStr = invoiceDataStr.replaceAll("</EInvoice>","");
        }
        if (invoiceDataStr.contains(" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")){
            invoiceDataStr = invoiceDataStr.replaceAll(" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"","");
        }
        if (invoiceDataStr.contains("<ItemExt/>")){
            invoiceDataStr = invoiceDataStr.replaceAll("<ItemExt/>","<ItemExt><ItemDetailName/></ItemExt>");
        }
        return invoiceDataStr;
    }

    @NotNull
    private JSONObject initSignInfo(Date currentDate, String invoiceDataStr) throws NetSignAgentException, ServerProcessException {
        logger.info("签章开始:{}",invoiceDataStr);
        byte[] laintext = invoiceDataStr.getBytes() ;
        //签名证书DN，null表示用服务器默认证书进行签名（填证书序列号）
        String subject = null ;
        //摘要算法，null表示用服务器默认的摘要算法（默认就是SM2）
        String digestAlg = null ;
        //是否做TSA签名
        boolean useTSA = false ;
        //签名
        NetSignResult result = NetSignAgent.detachedSignature(laintext , subject , digestAlg , useTSA );
        logger.info("签章结束");
        //获取byte形式的签名结果
//        byte[] signedText = result.getByteArrayResult( NetSignResult.SIGN_TEXT );
        //获取base64编码后的签名结果
        String signedTextB64 = result.getStringResult( NetSignResult.SIGN_TEXT );
        //组织签名信息
        JSONObject signInfo = new JSONObject();
        SimpleDateFormat utcDateTime = new SimpleDateFormat("yyyyMMddHHmmss");
        signInfo.put("value",signedTextB64);
        signInfo.put("format","DETACH");
        signInfo.put("algorithm","SM2");
        signInfo.put("time",utcDateTime.format(currentDate));
        return signInfo;
    }

    private List<Item> getItems(FsczfpxxRquestDTO requestDTO, List<Item> itemList,JSONObject requestJson) {
        if (CollectionUtils.isNotEmpty(requestDTO.getItem())){
            List<Item> finalItemList = new ArrayList<>(requestDTO.getItem().size());;
            requestDTO.getItem().forEach(jfsxxRequestJkxmDTO -> {
                if ("1".equals(jfsxxRequestJkxmDTO.getUnits())){
                    jfsxxRequestJkxmDTO.setUnits("元");
                }else if ("2".equals(jfsxxRequestJkxmDTO.getUnits())){
                    jfsxxRequestJkxmDTO.setUnits("元");
                    jfsxxRequestJkxmDTO.setAmount(jfsxxRequestJkxmDTO.getAmount() * 10000);
                    jfsxxRequestJkxmDTO.setTotalAmount(jfsxxRequestJkxmDTO.getTotalAmount() * 10000);
                }
                Item item = Item.ItemBuilder.anItem()
                        .withItemAmount(jfsxxRequestJkxmDTO.getTotalAmount())
                        .withItemStd(jfsxxRequestJkxmDTO.getAmount())
                        .withItemCode(jfsxxRequestJkxmDTO.getCode())
                        .withItemUnit(jfsxxRequestJkxmDTO.getUnits())
                        .withItemQuantity(jfsxxRequestJkxmDTO.getCount().intValue())
                        .withItemName(jfsxxRequestJkxmDTO.getName())
                        .build();
                if (MapUtils.isNotEmpty(itemCodeMap) && itemCodeMap.containsKey(jfsxxRequestJkxmDTO.getName())){
                    item.setItemCode(itemCodeMap.get(jfsxxRequestJkxmDTO.getName()));
                }
//                if (redisUtils.isExistKey(YC_FS_ITEM_INFO_REDIS_KEY)){
//                    String stringValue = redisUtils.getStringValue(YC_FS_ITEM_INFO_REDIS_KEY);//                    JSONObject jsonObject = JSON.parseObject(stringValue, JSONObject.class);
//                    item.setItemCode(jsonObject.getString(jfsxxRequestJkxmDTO.getName()));
//                }else {
//                    Map<String, String> itemMap = initItems(requestJson.getString("place_code"));
//                    if (itemMap != null && itemMap.containsKey(jfsxxRequestJkxmDTO.getName())){
//                        item.setItemCode(itemMap.get(jfsxxRequestJkxmDTO.getName()));
//                    }
//                }
                finalItemList.add(item);
            });
            itemList = finalItemList;
        }
        return itemList;
    }

    @Value("${exchange.feishui.issue.invoice.tag:CZ-EI-32}")
    private String eInvoiceTag;
    @Value("${exchange.feishui.issue.invoice.name:江苏省非税收入统一票据（电子）}")
    private String eInvoiceName;
    @Value("${exchange.feishui.issue.invoice.specimencode:3201012101}")
    private String eInvoiceSpecimenCode;
    @Value("${exchange.feishui.issue.invoice.supervisor.areacode:320900}")
    private String supervisorAreaCode;
    @Value("${exchange.feishui.issue.invoice.party.code:12320900E875065319}")
    private String partyCode;
    @Value("${exchange.feishui.issue.invoice.party.name:盐城市不动产登记中心}")
    private String partyName;
    @Value("${exchange.feishui.issue.invoice.party.type:1}")
    private String partyType;
    @Value("${exchange.feishui.issue.invoice.supervisorpartyseal.id:edaf9b6fc3064f669df61eda23bcfdb5}")
    private String supervisorPartySealId;
    @Value("${exchange.feishui.issue.invoice.invoicingpartyseal.id:edaf9b6fc3064f669df61eda23bcfdb5}")
    private String invoicingPartySealId;

    private EInvoice initEInvoiceInfo(JSONObject requestJson,FsczfpxxRquestDTO requestDTO,Date currentDate){
        Header header = Header.HeaderBuilder.aHeader().withEInvoiceID(new StringBuilder(requestJson.getString("bill_no")).reverse().toString() + "-" + new StringBuilder(requestJson.getString("bill_batch_code")).reverse().toString())
                .withEInvoiceTag(eInvoiceTag)
                .withVersion("1.1.0").build();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

        List<Item> itemList = null;
        itemList = getItems(requestDTO, itemList,requestJson);
        EInvoiceData eInvoiceData = EInvoiceData.EInvoiceDataBuilder.anEInvoiceData()
                .withMain(Main.MainBuilder.aMain()
                        .withEInvoiceName(eInvoiceName)
                        .withEInvoiceCode(requestJson.getString("bill_batch_code"))
                        .withEInvoiceNumber(requestJson.getString("bill_no"))
                        .withRandomNumber(UUIDGenerator.generate6())
                        .withEInvoiceSpecimenCode(eInvoiceSpecimenCode)
                        .withSupervisorAreaCode(supervisorAreaCode)
                        .withTotalAmount(requestDTO.getIncomeAmount())
                        .withIssueDate(date.format(currentDate))
                        .withIssueTime(time.format(currentDate))
                        .withBizCode(requestDTO.getTradeUniqueCode())
                        .withHandlingPerson(requestDTO.getCreater())
                        .withChecker(requestDTO.getCreater())
                        .withRemark(requestDTO.getRemark())
//                        .withHandlingPerson(author)
//                        .withChecker(author)
                        .withInvoicingParty(InvoicingParty.InvoicingPartyBuilder.anInvoicingParty()
                                .withInvoicingPartyCode(partyCode)
                                .withInvoicingPartyName(partyName)
                                .withRecName(requestDTO.getCreater())
                                .withRecAcct(recNo)
                                .withRecOpBk(recBank)
                                .build())
                        .withPayerParty(PayerParty.PayerPartyBuilder.aPayerParty()
                                .withPayerPartyName(requestDTO.getPayerName())
                                .withPayerPartyCode(requestDTO.getPayerPartyCode())
                                .withPayerPartyType(partyType)
                                .build())
                        .withMainExt(MainExt.MainExtBuilder.aMainExt()
                                .withPayCode(requestDTO.getPayCode())
                                .build())
                        .withInvoicingPartySeal(InvoicingPartySeal.InvoicingPartySealBuilder.anInvoicingPartySeal()
                                .withSealId(invoicingPartySealId)
                                .build())
                        .withSupervisorPartySeal(SupervisorPartySeal.SupervisorPartySealBuilder.aSupervisorPartySeal()
                                .withSealId(supervisorPartySealId)
                                .build())
                        .build())
                .withDetails(Details.DetailsBuilder.aDetails()
                        .withItem(itemList)
                        .build()).build();
        EInvoice eInvoice = EInvoice.EInvoiceBuilder.anEInvoice()
                .withEInvoiceData(eInvoiceData)
                .withHeader(header).build();
        return eInvoice;
    }

    private void initPayCodeInfo(FsczfpxxRquestDTO requestDTO, JSONObject requestJson) {
        JSONObject payCodeInfo = new JSONObject();
        JSONArray payCodeInfos = new JSONArray(1);
        payCodeInfo.put("pay_code", requestDTO.getPayCode());
        payCodeInfo.put("amount", requestDTO.getIncomeAmount());
        payCodeInfos.add(payCodeInfo);
        requestJson.put("pay_code_info", payCodeInfos);
    }

    private void initPlaceCode(JSONObject requestJson) {
//        UserDto userDto = userManagerUtils.getUserDto();
//        logger.info("用户信息:{}",JSON.toJSONString(userDto));
        logger.info("转换后入参:{}",requestJson.toJSONString());
        if (requestJson != null && StringUtils.isNotBlank(requestJson.getString("userCode"))) {
            logger.info("存在userCode");
//            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
//            bdcZdDsfzdgxDO.setZdbs("DSF_ZD_FS_PLACE_CODE");
////            bdcZdDsfzdgxDO.setBdczdz(userDto.getOrgRecordList().get(0).getRegionCode());
//            bdcZdDsfzdgxDO.setBdczdz(requestJson.getString("region_code"));
//            bdcZdDsfzdgxDO.setDsfxtbs("FS");
//            logger.info("查询字典值");
//            BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
//            if (result != null && StringUtils.isNotBlank(result.getDsfzdz())){
//                logger.info("查询字典值:{}",JSON.toJSONString(result));
//                requestJson.put("place_code", result.getDsfzdz());
//            }else {
//                requestJson.put("place_code", "1");
//            }
            //盐城要求  改为通过配置项和用户名获取
            if (MapUtils.isNotEmpty(placeCodeMap) && placeCodeMap.containsKey(requestJson.getString("userCode"))){
                requestJson.put("place_code", placeCodeMap.get(requestJson.getString("userCode")));
            }
            requestJson.remove("userCode");
        }
        else {
            requestJson.put("place_code", "1");
        }
    }

    @NotNull
    private CommonResponse<Object> dealResponse(Object response) {
        if (response == null) {
            return CommonResponse.fail("返回为空！");
        }
//        logger.info("非税接口返回:{}",response);
        logger.info("非税接口返回:{}",JSON.toJSONString(response));
        JSONObject jsonObject = JSON.parseObject(Base64Util.base64Str2Str((String) response));
        if (jsonObject.containsKey("message")) {
            return CommonResponse.ok(jsonObject.getString("message"));
        } else if (jsonObject.containsKey("error_message")) {
            String errorMessage = jsonObject.getString("error_message");
            JSONObject errorMessageJson = JSON.parseObject(errorMessage);
            return CommonResponse.fail(errorMessageJson.getString("error_code"), errorMessageJson.getString("error_msg"));
        } else {
            return CommonResponse.fail("调用失败:" + JSON.toJSONString(response));
        }
    }

    private FsCommonRequest initRequestParam(String requestJson, String format, String method) throws IOException {
        FsCommonRequest request = FsCommonRequest.FsCommonRequestBuilder.aFsCommonRequest().withMessage(Base64Util.str2Baes64Str(requestJson))
                .withAgency_code(agencyCode)
                .withApp_id(appId)
                .withDatetime(new SimpleDateFormat("yyyyMMddHHmmssSSS")
                        .format(new Date()))
                .withEncryption(encryption)
                .withFormat(format)
                .withMessage_id(UUIDGenerator.generate16())
                .withMethod(method)
                .withRegion_code(regionCode)
                .withVersion(version).build();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appkey)
                .append(request.getAgency_code())
                .append(request.getApp_id())
                .append(request.getDatetime())
                .append(request.getEncryption())
                .append(request.getFormat())
                .append(request.getMessage())
                .append(request.getMessage_id())
                .append(request.getMethod())
                .append(request.getRegion_code())
                .append(request.getVersion())
                .append(appkey).toString();
        byte[] bytes = Md5Util.encryptMD5(stringBuilder.toString());
        request.setSecurity(Md5Util.byte2hex(bytes));
        return request;

    }

    private int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

}
