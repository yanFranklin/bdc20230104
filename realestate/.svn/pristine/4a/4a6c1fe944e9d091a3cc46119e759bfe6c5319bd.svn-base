package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.OpenApiClientReqPropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import com.alibaba.fastjson.JSONObject;
import com.open.common.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-06
 * @description 综合查询 请求方式
 */
@Service(value = "openApiClient")
public class OpenApiClientRequestServiceImpl extends InterfaceRequestService<OpenApiClientReqPropBO> {

    /**
     * 合肥婚姻合并接口，默认抛出异常不再继续查询，配置为false可忽略异常，继续后续查询
     */
    @Value("${hyxxcx.throw.exception:true}")
    private String hyxxcxThrowException;

    @Value("${data.version:}")
    private String dataVersion;

    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        OpenApiClientReqPropBO prop = super.getRequestPropFromBuilder(builder);
        if(requestBody != null && StringUtils.isNoneBlank(prop.getUrl(),prop.getAppKey(),
                prop.getUserName(),prop.getSkprivateKeyPath(),prop.getRequestType())){
            // 参数MAP
            String content = JSONObject.toJSONString(requestBody);
            String timeStamp = CommonUtil.getCurrStrDate();
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", prop.getContentType());
            Map<String, Object> contentMap = new HashMap<>();
            contentMap.put("appKey", prop.getAppKey());
            contentMap.put("messageId", cn.gtmap.realestate.exchange.util.CommonUtil.openApiUUID());
            contentMap.put("userName", prop.getUserName());
            contentMap.put("status", prop.getStatus());
            contentMap.put("content", content);
            Exception logE = null;
            String response = null;
            // 公证书测试数据
//            response = getJsonFromFile();

            // 家庭成员测试数据
//            response = "{\"IsSuccess\": \"true\",\"TotalCount\": \"金晓艳\",\"ExceptionMessage\": \"金晓艳\",\"Message\": \"金晓艳\",\"DataType\": \"金晓艳\",\"ErrorCode\": \"金晓艳\",\"Result\": \" 340111197307267565 \",\"DataList\": [{\"xm\": \"父亲\",\"zj\": \"152827199011110909\",\"gx\": \"父亲\"}, {\"xm\": \"母亲\",\"zj\": \"152827199011110909\",\"gx\": \"母亲\"}, {\"xm\": \"子女\",\"zj\": \"152827199011110909\",\"gx\": \"子女\"}, {\"xm\": \"兄弟姐妹\",\"zj\": \"152827199011110909\",\"gx\": \"兄弟姐妹\"}]}";

            // 死亡标志 JSON
//            response = "{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":2,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20190910084147WakkyaWkbsWQR8gzLZS】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[{\"swzxrq\":\"\",\"xm\":\"龚静\",\"gmsfhm\":\"342124198106120223\"},{\"swzxrq\":\"\",\"xm\":\"龚静\",\"gmsfhm\":\"342124198106120223\"}],\"ErrorCode\":0}";

            // 融合查询
//            response = "{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":1,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【201909100841476UHnsMEfZgKRNUc4elB】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[{\"fddbrxm\":\"陈东\",\"zs\":\"合肥市东流路100号政务中心三区A座\",\"shzzmc\":\"合肥市人民政府信访局\",\"tyshxydm\":\"11340100746752129D\"}],\"ErrorCode\":0}";

            // 融资担保小额贷款公司查询
//            response = "{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":1,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20190910084147SEeTX446VmHB5EEQyPg】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[{\"gssx\":\"国有\",\"gsmc\":\"合肥市兴泰融资担保集团有限公司\",\"clrq\":\"2009年2月\",\"jydz\":\"安徽省合肥市蜀山区祁门路1688号\",\"bz\":\"政策性融资担保公司\",\"xkzh\":\"皖340103003\",\"zczb\":\"258683\",\"zcd\":\"蜀山区\",\"frdb\":\"陈锐\",\"tyshxydm\":\"91340100728497523W \"}],\"ErrorCode\":0}";

            // 第一顺位继承人查询
//            response = "{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":2,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20190910084147DkPuTYAn9NNQRF1DGD4】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[{\"gx\":\"子女\",\"mc\":\"廖梓宸\",\"sfz\":\"340104201404063543\"},{\"gx\":\"配偶\",\"mc\":\"廖凯\",\"sfz\":\"340603198310071012\"}],\"ErrorCode\":0}";
            //市场监管总局_企业基本信息查询查询
//            response = "[{\"apprdate\": 1574647496000,\"dom\": \"合肥市高新开发区望江西路666号\",\"entname\": \"科大讯飞股份有限公司\",\"enttypeCn\": \"其他股份有限公司(上市)\",\"estdate\": 946483200000,\"name\": \"刘庆峰\",\"opfrom\": 946483200000,\"opscope\": \"增值电信业务，专业技术人员培训，计算机软、硬件开发、生产和销售及技术服务，系统工程、信息服务，电子产品、计算机通讯设备研发、生产、销售，进出口业务，安全技术防范工程，房屋租赁，物业管理服务，设计、制作、代理、发布广告，移动通信设备的研发、销售，图书、电子出版物销售。（以上依法须经批准的项目经相关部门批准后方可开展经营活动）。\",\"opto\": null,\"regcap\": 219857.506700,\"regcapcurCn\": null,\"regno\": \"340000000016368\",\"regorgCn\": \"安徽省市场监督管理局\",\"regstateCn\": \"存续（在营、开业、在册）\",\"sExtNodenum\": \"340000\",\"uniscid\": \"91340000711771143J\"}]";
            //市场监管总局_企业基本信息查询查询
//            response = "{\"ent_idx\": {\"entname\": \"xxxxxx公司\",\"uniscid\": \"Gxxxxxx67\"},\"regstateCn\": \"吊销,未注销\",\"entchk_checkres_key\": \"0x\",\"diff_list\": [{\"chk_key\": \"regno\",\"in_val\": \"666xxxxxx\",\"out_val\": \"652xxxxxx\"},{\"chk_key\": \"enttypeCn\",\"in_val\": \"xxxx公司\",\"out_val\": \"xx有限责任公司\"}],\"_map_\": \"true\"}";
            //殡葬服务信息
//            response = "{\"result\": {\"total\": \"1\",\"data\": [  {    \"RN\": \"1\",    \"NAME\": \"李XXX\",    \"ID_CARD\": \"XXXX\",    \"SEX\": \"1\",    \"DEATH_TIME\": \"2014-05-31 00:00:00\",    \"ADDRESS\": \"XXX村\",    \"CREMATION_TIME\": \"2014-05-31 00:00:00\"  }],\"fields\": [  \"RN\",  \"NAME\",  \"ID_CARD\",  \"SEX\",  \"DEATH_TIME\",  \"ADDRESS\",  \"CREMATION_TIME\"]},\"status\": \"success\",\"code\": \"0\"}";
//            response = "{\n\"IsSuccess\": true,\n\"Result\": 1,\n\"TotalCount\": 2,\n\"DataType\": \"json\",\n\"Message\": \"Message:901-委办局/企业【9811000048659837】访问服务【201906211705467q2clWHwgHXWgJjAIaC】成功，已有效查询到数据！\",\n\"ErrorFieldList\": null,\n\"ErrorTableList\": null,\n\"Data\": null,\n\"ExceptionMessage\": \"\",\n\"ErrorServiceId\": null,\n\"DataList\": [\n  {\n    \"regno\": \"340100000912077\",\n    \"uniscid\": \"9134010009286856XM\",\n    \"dmms\": \"正常\",\n    \"mobtel\": \"18900512286\",\n    \"dom\": \"合肥市蜀山区金寨路155号黄金广场5幢C1106室\",\n    \"cerno\": \"340123198803266211\",\n    \"lerep\": \"丁飞飞\",\n    \"entname\": \"合肥飞凌服饰有限公司\",\n    \"opscope\": \"服装、皮具、打印设备、计算机软硬件、电子产品、安防设备、五金交电、通讯器材、塑料制品、针纺织品、二手车、汽车及汽车配件、润滑油、机械设备、汽车用品、保健用品、成人用品、玩具、农副产品、家居用品、鞋帽、箱包、办公用品及耗材、饰品、化妆品、图书、鲜花礼品、建材、工艺品、日用百货、家具、预包装食品兼散装食品、数码产品及配件、影音电器、电脑及配件、电脑耗材及外设、母婴用品、家用电器及配件批发及零售（含网上销售）；品牌管理及咨询；展览展示服务；会议会展服务；网站建设及技术开发、技术转让、技术咨询；商务信息咨询；电子商务平台建设；自营和代理各类商品及技术进出口业务（国家限定企业经营或禁止进出口的商品和技术除外）。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\n    \"opto\": \"2044-02-25 00:00:00.000000000\",\n    \"tel\": \"\",\n    \"opfrom\": \"2014-02-26 00:00:00.000000000\"\n  },\n  {\n    \"regno\": \"340100000912077\",\n    \"uniscid\": \"9134010009286856XM\",\n    \"dmms\": \"正常\",\n    \"mobtel\": \"\",\n    \"dom\": \"合肥市蜀山区金寨路155号黄金广场5幢C1106室\",\n    \"cerno\": \"340121198910111634\",\n    \"lerep\": \"丁飞飞\",\n    \"entname\": \"合肥飞凌服饰有限公司\",\n    \"opscope\": \"服装、皮具、打印设备、计算机软硬件、电子产品、安防设备、五金交电、通讯器材、塑料制品、针纺织品、二手车、汽车及汽车配件、润滑油、机械设备、汽车用品、保健用品、成人用品、玩具、农副产品、家居用品、鞋帽、箱包、办公用品及耗材、饰品、化妆品、图书、鲜花礼品、建材、工艺品、日用百货、家具、预包装食品兼散装食品、数码产品及配件、影音电器、电脑及配件、电脑耗材及外设、母婴用品、家用电器及配件批发及零售（含网上销售）；品牌管理及咨询；展览展示服务；会议会展服务；网站建设及技术开发、技术转让、技术咨询；商务信息咨询；电子商务平台建设；自营和代理各类商品及技术进出口业务（国家限定企业经营或禁止进出口的商品和技术除外）。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\n    \"opto\": \"2044-02-25 00:00:00.000000000\",\n    \"tel\": \"9243751\",\n    \"opfrom\": \"2014-02-26 00:00:00.000000000\"\n  }\n],\n\"ErrorCode\": 0\n" +
//                    "}";

//            response = "{\n" +
//                    "  \"api_method\": \"wjwswyxzmcx\",\n" +
//                    "  \"xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><MDEML templateVersion=\\\"1.0\\\">\\n  <requestinfo>\\n    <requestDate>2019-12-17 09:22:52</requestDate>\\n    <requestOrgCode>000000000</requestOrgCode>\\n    <requestOrgName>国家卫计委</requestOrgName>\\n  </requestinfo>\\n  <requestinfo reference=\\\"../requestinfo\\\"/>\\n  <body>\\n    <data>\\n      <GMSFZH>340104290113053</GMSFZH>\\n      <XM>朱志刚</XM>\\n    </data>\\n  </body>\\n</MDEML>\"\n" +
//                    "}";
            // 省级结婚
            //response = "{\"resultCode\":\"00\",\"resultData\":[{\"POXM\":\"贾倩茹\",\"DJRQ\":\"20200609\",\"POGMSFHM\":\"340104197711291520\"}],\"resultInfo\":\"请求成功\",\"resultTotal\":0}";
            //省级离婚
            //response = "{\"resultCode\":\"00\",\"resultData\":[{\"POXM\":\"贾倩茹\",\"DJRQ\":\"20160923\",\"POGMSFHM\":\"340104197711291520\"}],\"resultInfo\":\"请求成功\",\"resultTotal\":0}";
//            response = "{\"StateCode\":0,\"SpouseName\":\"\",\"Date\":\"\",\"SpouseCardId\":\"\",\"Success\":true,\"ErrorMsg\":\"\"}";

            //小微企业
//            response ="{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":1,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20190624152000jlG5fKCS7w3nRS3a9Uk】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[{\"lgl_reg_no\":\"340100000160899\",\"industryco\":\"8090\",\"fundam\":\"200.0\",\"enttra\":\"数源\",\"enttype\":\"1130\",\"conam\":\"200.0\",\"small_id\":\"5b5ccc6a558b4ac59c901710586eb2a6\",\"valid_flag\":\"1 \",\"type\":\"12\",\"estdate\":\"2003-12-24 00:00:00.000000000\",\"regorgname\":\"合肥市工商行政管理局\",\"src_update_time\":\"2018-07-01 16:17:25.000000000\",\"regorg\":\"340100\",\"gldm_update_time\":\"2019-03-14 00:00:00.000000000\",\"data_source_id\":\"102803\",\"moveindate\":\"2018-07-01 23:02:10.000000000\",\"lgl_id\":\"7946118CB5D7A4D4E15A4176E58C03B9\",\"lgl_name\":\"安徽数源测绘技术有限公司\",\"lgl_credit_no\":\"9134010075683227X3\",\"moveoutdate\":null,\"gldm_create_time\":\"2019-03-14 00:00:00.000000000\",\"moveintype\":\"2 \",\"regcap\":\"200.0\",\"enttypename\":\"有限责任公司(自然人投资或控股)\",\"lgl_org_no\":\"\",\"moveouttype\":\"  \",\"industryphy\":\"O\"}],\"ErrorCode\":0}";
//
//            response = "{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":0,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20190709170942qH8QqbV88ia0Pg6RMWu】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[],\"ErrorCode\":0}";
//           合肥——常驻人口查询
//            response = "\n" +
//                    "{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":2,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20190627140223IE9XSPJPf5iRkB2mlSs】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[{\"xx\":\"3\",\"hh\":\"018002785\",\"xxjb\":\"\",\"csrq\":\"19880828\",\"nbsfzid\":null,\"xzjd\":\"340111017\",\"gmsfhm\":\"622225198808281848\",\"zjxy\":\"\",\"zym\":\"\",\"qtssxq\":\"\",\"swrq\":\"\",\"qtzz\":\"\",\"jgxxdz\":\"\",\"hssxqlbz\":\"\",\"ryid\":null,\"yxqxqsrq\":\"20171023\",\"swzxlb\":\"\",\"hgjdqlbz\":\"\",\"zjlb\":\"\",\"cxbz\":\"0\",\"qfjg\":\"合肥市公安局包河分局\",\"yhzgx\":\"02\",\"hhnbid\":\"3401000001013846799\",\"jlbz\":\"1\",\"hhid\":null,\"mz\":\"01\",\"csdssxq\":\"620724\",\"mlpnbid\":null,\"fwcs\":\"\",\"hyzk\":\"20\",\"hslbz\":\"\",\"swzxdzxsj\":\"\",\"gxsjc\":\"20180608195653\",\"gxsj\":\"20180608201318\",\"zrq\":\"\",\"mlpid\":null,\"zy\":\"职员\",\"whcd\":\"31\",\"rylb\":\"1\",\"qysj\":\"20180608195652\",\"zylb\":\"\",\"hssxqql\":\"\",\"cjsj\":\"20180608201318\",\"jggjdq\":\"\",\"jssj\":\"99999999999999\",\"mlxz\":\"蓝山花园14幢602室\",\"csdgjdq\":\"\",\"sg\":\"165\",\"jgssxq\":\"620724\",\"hxzlbz\":\"\",\"hsql\":\"20170217\",\"hyql\":\"\",\"csdxz\":\"\",\"pcs\":\"340111018\",\"hylbz\":\"\",\"pxh\":\"\",\"swzxrq\":\"\",\"hgjdqql\":\"\",\"mlph\":\"2858号\",\"byzk\":\"\",\"zpid\":\"3401000001015781510\",\"cssj\":\"\",\"xb\":\"2\",\"xxqysj\":\"20180608195652\",\"yxqxjzrq\":\"20371023\",\"ssxq\":\"340111\",\"scxl\":\"475721012\",\"rynbid\":\"3401000001023123957\",\"hxzql\":\"城关镇县府东街01号\",\"ryzt\":\"0\",\"xm\":\"许丽霞\",\"hlx\":\"\",\"jlx\":\"340100000955\",\"hb\":\"6\",\"jcwh\":\"340111018001\",\"objectid\":\"AC30B329A44E379BE053A7FD1FAC06C2\"},{\"xx\":\"3\",\"hh\":\"017061321\",\"xxjb\":\"\",\"csrq\":\"19880828\",\"nbsfzid\":null,\"xzjd\":\"340111006\",\"gmsfhm\":\"622225198808281848\",\"zjxy\":\"\",\"zym\":\"\",\"qtssxq\":\"\",\"swrq\":\"\",\"qtzz\":\"\",\"jgxxdz\":\"\",\"hssxqlbz\":\"\",\"ryid\":null,\"yxqxqsrq\":\"20371023\",\"swzxlb\":\"\",\"hgjdqlbz\":\"\",\"zjlb\":\"\",\"cxbz\":\"0\",\"qfjg\":\"合肥市公安局包河分局\",\"yhzgx\":\"02\",\"hhnbid\":\"3401000001013462772\",\"jlbz\":\"2\",\"hhid\":null,\"mz\":\"01\",\"csdssxq\":\"620724\",\"mlpnbid\":null,\"fwcs\":\"\",\"hyzk\":\"20\",\"hslbz\":\"\",\"swzxdzxsj\":\"\",\"gxsjc\":\"20180608195653\",\"gxsj\":\"20180608201318\",\"zrq\":\"\",\"mlpid\":null,\"zy\":\"职员\",\"whcd\":\"31\",\"rylb\":\"1\",\"qysj\":\"20170217091542\",\"zylb\":\"\",\"hssxqql\":\"\",\"cjsj\":\"20170217091504\",\"jggjdq\":\"\",\"jssj\":\"20180608195652\",\"mlxz\":\"蓝山花园14幢602室\",\"csdgjdq\":\"\",\"sg\":\"165\",\"jgssxq\":\"620724\",\"hxzlbz\":\"\",\"hsql\":\"20170217\",\"hyql\":\"\",\"csdxz\":\"\",\"pcs\":\"340111017\",\"hylbz\":\"\",\"pxh\":\"\",\"swzxrq\":\"\",\"hgjdqql\":\"\",\"mlph\":\"2858号\",\"byzk\":\"\",\"zpid\":\"3401000001015781510\",\"cssj\":\"\",\"xb\":\"2\",\"xxqysj\":\"20170217091542\",\"yxqxjzrq\":\"20171023\",\"ssxq\":\"340111\",\"scxl\":\"475708940\",\"rynbid\":\"3401000001021717842\",\"hxzql\":\"城关镇县府东街01号\",\"ryzt\":\"0\",\"xm\":\"许丽霞\",\"hlx\":\"\",\"jlx\":\"340100000955\",\"hb\":\"6\",\"jcwh\":\"340111017046\",\"objectid\":\"AC30B3143D1B379BE053A7FD1FAC06C2\"}],\"ErrorCode\":0}\n";
//           台湾居民信息查询
//            response = "{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":5,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20190822104735YAfiEKPhjJZjTxpUFl4】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[{\"gjdq\":\"TWN\",\"twzjhm\":\"132032641\",\"csrq\":\"19650419\",\"bslyy\":\"\",\"jwrysf\":\"20\",\"dbrxm\":\"\",\"sfyx\":\"\",\"twjzddm\":\"83\",\"ztzydm\":\"96\",\"sspcs\":\"340191440000\",\"dwzzjgdm\":\"#34010001609\",\"byzd2\":\"\",\"ywm\":\"KANG\",\"sqsscqzyxqz\":\"20091106\",\"jjts\":null,\"sqssczjyxqz\":\"20110820\",\"byzd3\":\"\",\"yyqzrq\":\"20090707\",\"sljguo\":\"1\",\"jzdpcsmc\":\"\",\"ywlx\":\"1A04\",\"kzbsdywjdbs\":\"10000000000000000000000000000000000000000000000000\",\"bslyj\":\"\",\"jzdxxdz\":\"高新区合欢路10号晶达光电有限公司\",\"sldw\":\"340100\",\"ywx\":\"OUYANG\",\"skbs\":\"0\",\"md5m\":\"\",\"xzqh\":\"340191\",\"sldwsc\":\"\",\"sqsjly\":\"01\",\"rjsy\":\"02\",\"sqssczjbbh\":\"03\",\"dbdwbh\":\"\",\"sjrxm\":\"\",\"gxsj\":\"20170719030700\",\"rjka\":\"F340241\",\"ryxh\":\"01\",\"rjrq\":\"20090413\",\"sjryb\":\"\",\"djsy\":\"\",\"yyqzdw\":\"340100\",\"gdbz\":\"3\",\"zpbh\":\"000000000307806\",\"zwxm\":\"欧阳刚\",\"sjrlxdh\":\"\",\"cjsj\":\"20140612172445\",\"sjrdz\":\"\",\"sfwdwdm\":\"1\",\"sfsb\":\"1\",\"bz\":\"\",\"zjzl\":\"16\",\"ywbh\":\"093401010043129\",\"xmdbignm\":\"BCDAB6A7ADE8\",\"sqssczjhm\":\"01597025\",\"twjzxz\":\"新竹市\",\"sfjj\":\"0\",\"dbrlxdh\":\"\",\"ftzwxm\":\"歐陽剛\",\"xb\":\"1\",\"slrq\":\"20090701\",\"jwrylb\":\"13\",\"byzd\":\"\",\"twsfzh\":\"J120114040\",\"sqsy\":\"02\",\"bzlbdm\":\"81\",\"xlh\":\"13949953\",\"jnlxdh\":\"15155906958\",\"sldwxzqh\":\"340100\",\"rybh\":\"TWN00000083401010051673\",\"tkzdbs\":\"0\",\"objectid\":\"5DEF4143E99B7CA5E053A8FD1FAC3415\"},{\"gjdq\":\"TWN\",\"twzjhm\":\"\",\"csrq\":\"19650419\",\"bslyy\":\"\",\"jwrysf\":\"20\",\"dbrxm\":\"\",\"sfyx\":\"\",\"twjzddm\":\"83\",\"ztzydm\":\"96\",\"sspcs\":\"340191440000\",\"dwzzjgdm\":\"000000009\",\"byzd2\":\"\",\"ywm\":\"KANG\",\"sqsscqzyxqz\":\"20100630\",\"jjts\":null,\"sqssczjyxqz\":\"20110820\",\"byzd3\":\"\",\"yyqzrq\":\"20100608\",\"sljguo\":\"1\",\"jzdpcsmc\":\"\",\"ywlx\":\"1A04\",\"kzbsdywjdbs\":\"10000000000000000000000000000000000000000000000000\",\"bslyj\":\"\",\"jzdxxdz\":\"合欢路8号\",\"sldw\":\"340100\",\"ywx\":\"OUYANG\",\"skbs\":\"0\",\"md5m\":\"\",\"xzqh\":\"340191440000\",\"sldwsc\":\"\",\"sqsjly\":\"01\",\"rjsy\":\"02\",\"sqssczjbbh\":\"03\",\"dbdwbh\":\"\",\"sjrxm\":\"\",\"gxsj\":\"20170719030700\",\"rjka\":\"Z030551\",\"ryxh\":\"01\",\"rjrq\":\"20100512\",\"sjryb\":\"\",\"djsy\":\"\",\"yyqzdw\":\"340100\",\"gdbz\":\"3\",\"zpbh\":\"000000000307806\",\"zwxm\":\"欧阳刚\",\"sjrlxdh\":\"\",\"cjsj\":\"20140612172445\",\"sjrdz\":\"\",\"sfwdwdm\":\"0\",\"sfsb\":\"\",\"bz\":\"\",\"zjzl\":\"16\",\"ywbh\":\"103401010034452\",\"xmdbignm\":\"BCDAB6A7ADE8\",\"sqssczjhm\":\"01597025\",\"twjzxz\":\"新竹市\",\"sfjj\":\"0\",\"dbrlxdh\":\"\",\"ftzwxm\":\"歐陽剛\",\"xb\":\"1\",\"slrq\":\"20100602\",\"jwrylb\":\"13\",\"byzd\":\"\",\"twsfzh\":\"J120114040\",\"sqsy\":\"14\",\"bzlbdm\":\"81\",\"xlh\":\"13953344\",\"jnlxdh\":\"15155906958\",\"sldwxzqh\":\"340100\",\"rybh\":\"TWN00000083401010051673\",\"tkzdbs\":\"0\",\"objectid\":\"5DEF4143F3C57CA5E053A8FD1FAC3415\"},{\"gjdq\":\"TWN\",\"twzjhm\":\"132032641\",\"csrq\":\"19650419\",\"bslyy\":\"\",\"jwrysf\":\"20\",\"dbrxm\":\"\",\"sfyx\":\"\",\"twjzddm\":\"83\",\"ztzydm\":\"96\",\"sspcs\":\"340191440000\",\"dwzzjgdm\":\"771149874\",\"byzd2\":\"\",\"ywm\":\"KANG\",\"sqsscqzyxqz\":\"20090102\",\"jjts\":null,\"sqssczjyxqz\":\"20110820\",\"byzd3\":\"\",\"yyqzrq\":\"20081113\",\"sljguo\":\"1\",\"jzdpcsmc\":\"\",\"ywlx\":\"1A04\",\"kzbsdywjdbs\":\"10000000000000000000000000000000000000000000000000\",\"bslyj\":\"\",\"jzdxxdz\":\"合欢路8号晶达光电有限公司\",\"sldw\":\"340100\",\"ywx\":\"OUYANG\",\"skbs\":\"0\",\"md5m\":\"\",\"xzqh\":\"340191\",\"sldwsc\":\"\",\"sqsjly\":\"01\",\"rjsy\":\"02\",\"sqssczjbbh\":\"03\",\"dbdwbh\":\"\",\"sjrxm\":\"\",\"gxsj\":\"20170719030700\",\"rjka\":\"F340241\",\"ryxh\":\"01\",\"rjrq\":\"20081008\",\"sjryb\":\"\",\"djsy\":\"\",\"yyqzdw\":\"340100\",\"gdbz\":\"3\",\"zpbh\":\"000000000307806\",\"zwxm\":\"欧阳刚\",\"sjrlxdh\":\"\",\"cjsj\":\"20140612172445\",\"sjrdz\":\"\",\"sfwdwdm\":\"1\",\"sfsb\":\"1\",\"bz\":\"\",\"zjzl\":\"16\",\"ywbh\":\"083401010051673\",\"xmdbignm\":\"BCDAB6A7ADE8\",\"sqssczjhm\":\"01597025\",\"twjzxz\":\"新竹市\",\"sfjj\":\"0\",\"dbrlxdh\":\"\",\"ftzwxm\":\"歐陽剛\",\"xb\":\"1\",\"slrq\":\"20081107\",\"jwrylb\":\"13\",\"byzd\":\"\",\"twsfzh\":\"J120114040\",\"sqsy\":\"02\",\"bzlbdm\":\"81\",\"xlh\":\"13950370\",\"jnlxdh\":\"5355070\",\"sldwxzqh\":\"340100\",\"rybh\":\"TWN00000083401010051673\",\"tkzdbs\":\"0\",\"objectid\":\"5DEF4143F5137CA5E053A8FD1FAC3415\"},{\"gjdq\":\"TWN\",\"twzjhm\":\"\",\"csrq\":\"19650419\",\"bslyy\":\"\",\"jwrysf\":\"\",\"dbrxm\":\"\",\"sfyx\":\"\",\"twjzddm\":\"83\",\"ztzydm\":\"96\",\"sspcs\":\"340398440000\",\"dwzzjgdm\":\"000000011\",\"byzd2\":\"\",\"ywm\":\"KANG\",\"sqsscqzyxqz\":\"\",\"jjts\":null,\"sqssczjyxqz\":\"20170918\",\"byzd3\":\"\",\"yyqzrq\":\"20170228\",\"sljguo\":\"1\",\"jzdpcsmc\":\"\",\"ywlx\":\"1A03\",\"kzbsdywjdbs\":\"10000000000000000000000000000000000000000000000000\",\"bslyj\":\"\",\"jzdxxdz\":\"蚌埠市环湖西路瀚林华府3区1栋1单元1703\",\"sldw\":\"340300\",\"ywx\":\"OUYANG\",\"skbs\":\"0\",\"md5m\":\"\",\"xzqh\":\"\",\"sldwsc\":\"\",\"sqsjly\":\"\",\"rjsy\":\"06\",\"sqssczjbbh\":\"04\",\"dbdwbh\":\"\",\"sjrxm\":\"欧阳刚\",\"gxsj\":\"20170719030700\",\"rjka\":\"\",\"ryxh\":\"01\",\"rjrq\":\"\",\"sjryb\":\"233000\",\"djsy\":\"\",\"yyqzdw\":\"340000\",\"gdbz\":\"3\",\"zpbh\":\"000000008512489\",\"zwxm\":\"欧阳刚\",\"sjrlxdh\":\"18655275810\",\"cjsj\":\"20170215190038\",\"sjrdz\":\"蚌埠市环湖西路瀚林华府3区1栋1单元1703\",\"sfwdwdm\":\"0\",\"sfsb\":\"1\",\"bz\":\"\",\"zjzl\":\"\",\"ywbh\":\"173403010002302\",\"xmdbignm\":\"BCDAB6A7ADE8\",\"sqssczjhm\":\"01597025\",\"twjzxz\":\"台湾新竹市经国路1段315巷12弄18号\",\"sfjj\":\"0\",\"dbrlxdh\":\"\",\"ftzwxm\":\"歐陽剛\",\"xb\":\"1\",\"slrq\":\"20170215\",\"jwrylb\":\"\",\"byzd\":\"\",\"twsfzh\":\"J120114040\",\"sqsy\":\"12\",\"bzlbdm\":\"31\",\"xlh\":\"13961643\",\"jnlxdh\":\"\",\"sldwxzqh\":\"340300\",\"rybh\":\"TWN00000083401010051673\",\"tkzdbs\":\"1\",\"objectid\":\"5DEF4144114B7CA5E053A8FD1FAC3415\"},{\"gjdq\":\"TWN\",\"twzjhm\":\"\",\"csrq\":\"19650419\",\"bslyy\":\"\",\"jwrysf\":\"86\",\"dbrxm\":\"\",\"sfyx\":\"\",\"twjzddm\":\"83\",\"ztzydm\":\"96\",\"sspcs\":\"340303420000\",\"dwzzjgdm\":\"000000009\",\"byzd2\":\"\",\"ywm\":\"KANG\",\"sqsscqzyxqz\":\"20140703\",\"jjts\":null,\"sqssczjyxqz\":\"20170918\",\"byzd3\":\"\",\"yyqzrq\":\"20140901\",\"sljguo\":\"1\",\"jzdpcsmc\":\"\",\"ywlx\":\"1A04\",\"kzbsdywjdbs\":\"10000000000000000000000000000000000000000000000000\",\"bslyj\":\"\",\"jzdxxdz\":\"蚌埠市奋勇街中平街1号楼2单元8号\",\"sldw\":\"340300\",\"ywx\":\"OUYANG\",\"skbs\":\"0\",\"md5m\":\"\",\"xzqh\":\"340303\",\"sldwsc\":\"\",\"sqsjly\":\"\",\"rjsy\":\"03\",\"sqssczjbbh\":\"04\",\"dbdwbh\":\"\",\"sjrxm\":\"\",\"gxsj\":\"20170719030700\",\"rjka\":\"F320081\",\"ryxh\":\"01\",\"rjrq\":\"20140425\",\"sjryb\":\"\",\"djsy\":\"\",\"yyqzdw\":\"340300\",\"gdbz\":\"3\",\"zpbh\":\"000000003874586\",\"zwxm\":\"欧阳刚\",\"sjrlxdh\":\"\",\"cjsj\":\"20140905145724\",\"sjrdz\":\"\",\"sfwdwdm\":\"0\",\"sfsb\":\"\",\"bz\":\"\",\"zjzl\":\"16\",\"ywbh\":\"143403010026943\",\"xmdbignm\":\"BCDAB6A7ADE8\",\"sqssczjhm\":\"01597025\",\"twjzxz\":\"新竹市经国路1段315巷12弄18号\",\"sfjj\":\"0\",\"dbrlxdh\":\"\",\"ftzwxm\":\"歐陽剛\",\"xb\":\"1\",\"slrq\":\"20140826\",\"jwrylb\":\"90\",\"byzd\":\"\",\"twsfzh\":\"J120114040\",\"sqsy\":\"14\",\"bzlbdm\":\"81\",\"xlh\":\"13956463\",\"jnlxdh\":\"18655275810\",\"sldwxzqh\":\"340300\",\"rybh\":\"TWN00000083401010051673\",\"tkzdbs\":\"0\",\"objectid\":\"5DEF414424427CA5E053A8FD1FAC3415\"}],\"ErrorCode\":0}";
//           建设工程竣工验收信息查询_税收风管
//            response = "{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":1,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20191018160120IXQAA1ZmdUdYk4Qassp】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[{\"proj_factcode\":\"1311.35\",\"proj_factarea\":\"8118.68\",\"proj_jgba_idea\":\"通过\",\"proj_jgba_orgname\":\"合肥高新区管委会建设发展局\",\"proj_structsyscode\":\"剪力墙\",\"cons_name\":\"安徽新华新茂置业有限公司\",\"proj_bdate\":\"2017-05-02 00:00:00.000000000\",\"proj_factsize\":\"\",\"proj_jgba_personname\":\"合肥高新区管委会建设发展局\",\"proj_jgba_address\":\"高新区\",\"proj_jgba_date\":\"2020-09-03 00:00:00.000000000\",\"proj_jgba_code\":\"3401361703010102-DW063(JG)\",\"proj_name\":\"金茂湾（KI2-2地块）G-1#楼\",\"proj_code\":\"340136201703010102\",\"proj_censor_code\":\"3401361703010102-TX-020\",\"proj_edate\":\"2020-08-21 00:00:00.000000000\"}],\"ErrorCode\":0}";
//           死亡信息
//            response = "{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":1,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20190910084147WakkyaWkbsWQR8gzLZS】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"TableName\":\"\",\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[{\"xm\":\"徐明俊\",\"swrq\":\"20110729\",\"gmsfhm\":\"340122198305016176\"}],\"ErrorCode\":0}";
            //结婚信息
            /*response = "{\n" +
                    "    \"IsSuccess\":true,\n" +
                    "    \"Result\":1,\n" +
                    "    \"TotalCount\":1,\n" +
                    "    \"DataType\":\"json\",\n" +
                    "    \"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20210910140115hJiKnqiJfvhGdjzkGXm】成功，已有效查询到数据！\",\n" +
                    "    \"ErrorFieldList\":null,\n" +
                    "    \"ErrorTableList\":null,\n" +
                    "    \"TableName\":\"\",\n" +
                    "    \"Data\":null,\n" +
                    "    \"ExceptionMessage\":\"\",\n" +
                    "    \"ErrorServiceId\":null,\n" +
                    "    \"DataList\":[\n" +
                    "        {\n" +
                    "            \"poxm\":\"巩志强\",\n" +
                    "            \"posfzh\":\"341227199102254417\",\n" +
                    "            \"ycyj\":null,\n" +
                    "            \"djrq\":\"2017-08-04 00:00:00.000000000\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"ErrorCode\":0\n" +
                    "}";*/
            //离婚数据
           /* response = "{\n" +
                    "    \"IsSuccess\":true,\n" +
                    "    \"Result\":1,\n" +
                    "    \"TotalCount\":1,\n" +
                    "    \"DataType\":\"json\",\n" +
                    "    \"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【20210910141400O3Q3WG3Ovb53Ec2j8ve】成功，已有效查询到数据！\",\n" +
                    "    \"ErrorFieldList\":null,\n" +
                    "    \"ErrorTableList\":null,\n" +
                    "    \"TableName\":\"\",\n" +
                    "    \"Data\":null,\n" +
                    "    \"ExceptionMessage\":\"\",\n" +
                    "    \"ErrorServiceId\":null,\n" +
                    "    \"DataList\":[\n" +
                    "        {\n" +
                    "            \"poxm\":\"贾倩茹\",\n" +
                    "            \"posfzh\":\"340104197711291520\",\n" +
                    "            \"djrq\":\"2016-09-23 00:00:00.000000000\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"ErrorCode\":0\n" +
                    "}";*/
            try {
                response = sendCommonHttpRequst(prop.getUrl(), headers,
                        prop.getRequestType(), prop.getSkprivateKeyPath(), timeStamp, contentMap);
                LOGGER.info("请求结果：", response);
                super.setResponseBody(response, builder);
            } catch (Exception e) {
                logE = e;
                //判断是合肥婚姻合并接口，根据hyxxcxThrowException=falze可不抛异常
                Map<String, Object> requestInfoMap = builder.getRequestInfo();
                String flag = "";
                if (requestInfoMap != null) {
                    flag = (String)requestInfoMap.get("hyjkFlag");
                }
                if(StringUtils.equals(dataVersion, CommonConstantUtils.SYSTEM_VERSION_HF) && StringUtils.isNotBlank(flag)
                        && StringUtils.equals(flag, "STHYCX") && !Boolean.parseBoolean(hyxxcxThrowException)){
                    LOGGER.error("省厅婚姻信息查询请求异常！");
                    LOGGER.error("请求地址：{},请求参数：{}",prop.getUrl(), contentMap, e);
                    LOGGER.error("忽略异常，hyxxcxThrowException：{}", Boolean.parseBoolean(hyxxcxThrowException));
                }else{
                    LOGGER.error("httpPost 请求异常：url:{},reqMap:{}", prop.getUrl(), contentMap, e);
                    throw new AppException("httpPost 请求异常");
                }

            } finally {
                // 记录 请求日志
                AuditEventBO auditEventBO = new AuditEventBO(prop,builder);
                auditEventBO.setRequest(JSONObject.toJSONString(contentMap));
                auditEventBO.setResponse(response);
                auditEventBO.setException(logE);
                super.saveAuditLog(auditEventBO);
            }
        }
    }

    /**
     * 处理外部接口返回体转化为exchange内部的返回体,由子类实现
     *
     * @param response
     * @return
     */
    @Override
    public String dealWithResponse(String response) {return response;}

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.String
     * @description  公正书测试数据
     */
    private static String getJsonFromFile() {
        String filePath = "E:\\gzsxx.json";
        File file = new File(filePath);
        StringBuilder strbuffer = new StringBuilder("");
        if(file.exists()){
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fis, "GB2312");
                BufferedReader in  = new BufferedReader(inputStreamReader);

                String str;
                while ((str = in.readLine()) != null) {
                    strbuffer.append(str);  //new String(str,"UTF-8")
                }
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return strbuffer.toString();
    }

    public static String sendCommonHttpRequst(String url, Map<String, String> headers, String method, String signPath, String timeStamp, Map<String, Object> contentMap) {
        String result = "";
        String requestParameters = "";
        String param = "";
        Map<String, Object> httpBodyMap = new HashMap();
        String valueContent = "";
        String orginStr = "";
        orginStr = SystemParameterNames.getTimeStamp() + "=" + timeStamp;
        httpBodyMap.putAll(contentMap);
        Iterator i$ = httpBodyMap.entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry<String, Object> etr = (Map.Entry)i$.next();
            if (etr.getValue() != null) {
                valueContent = etr.getValue().toString();
                if (method.equalsIgnoreCase("get")) {
                    try {
                        param = param + "&" + (String)etr.getKey() + "=" + URLEncoder.encode(valueContent, "utf-8");
                    } catch (UnsupportedEncodingException var17) {
                        var17.printStackTrace();
                    }
                } else {
                    param = param + "&" + (String)etr.getKey() + "=" + valueContent;
                }
            }
        }
        orginStr = orginStr + param;
        String sign;
        if (method.equalsIgnoreCase("POST")) {
            sign = toSign(orginStr, signPath);
            try {
                sign = URLEncoder.encode(sign, "utf-8");
            } catch (UnsupportedEncodingException var16) {
                var16.printStackTrace();
            }

            requestParameters = orginStr + "&sign=" + sign;
        } else {
//            LOGGER.info("signPath: {}" ,signPath);
            sign = toSign(orginStr, signPath);
//            LOGGER.info("sign: {}" ,sign);
            try {
                requestParameters = orginStr + "&sign=" + URLEncoder.encode(sign, "utf-8");
            } catch (UnsupportedEncodingException var15) {
                var15.printStackTrace();
            }
        }

        if (method.equalsIgnoreCase("POST")) {
            result = sendPostReq(url, headers, requestParameters);
        } else {
            result = sendGetReq(url, headers, requestParameters);
        }
        return result;
    }

    protected static String sendPostReq(String url, Map<String, String> headers, String requestParameters) {
        String result = "";
//        System.out.println("sendPostReq requestParameters:" + requestParameters);
        result = OpenApiClient.doPost(headers, url, requestParameters);
        return result;
    }

    protected static String sendGetReq(String url, Map<String, String> headers, String requestParameters) {
        String result = "";
//        System.out.println("sendGetReq requestParameters:" + requestParameters);
        result = OpenApiClient.doGet(headers, url, requestParameters);
        return result;
    }

    public static String toSign(String orginStr, String skprivateKeyFile) {
        String content = orginStr;
        String sign_useSitechKey = null;
        String enQryStr = null;
        PrivateKey skPrivateKey = fileToPrivateKey(skprivateKeyFile);
//        LOGGER.info("skPrivateKey: {}" ,JSONObject.toJSONString(skPrivateKey));
        try {
            content = StringUtil.sortOrginReqStr(content);
//            LOGGER.info("content: {}" ,content);
            enQryStr = URLEncoder.encode(content, "UTF-8");
//            LOGGER.info("enQryStr: {}" ,enQryStr);
            String md5Str = MD5.ToMD5(enQryStr);
//            LOGGER.info("md5Str: {}" ,md5Str);
            sign_useSitechKey = RSAUtils.sign(md5Str.getBytes(), skPrivateKey);
//            LOGGER.info("sign_useSitechKey: {}" ,sign_useSitechKey);
        } catch (Exception var7) {
            LOGGER.error(var7.getMessage());
        }

        return sign_useSitechKey;
    }

    public static PrivateKey fileToPrivateKey(String privateKeyFile) {
        File key = new File(privateKeyFile);

        try {
            FileInputStream is = new FileInputStream(key);
            FileChannel channel = is.getChannel();
            byte[] buffer = new byte[is.available()];
            channel.read(ByteBuffer.wrap(buffer));
            byte[] keyBytes = Base64Utils.decode(new String(buffer));
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            return privateK;
        } catch (Throwable var9) {
            var9.printStackTrace();
            return null;
        }
    }
}
