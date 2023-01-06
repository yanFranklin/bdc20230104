package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.config.BdcDysjPzDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjxxgx.*;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhxx.HhxxRequestBody;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhxx.HhxxRequestCondition;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhxx.HhxxResponseBody;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhxx.HhxxResponseData;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcJgysbaRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcJgysbaResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcJgysbaxqResponseDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.config.BdcDysjPzFeignService;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.XuanchengHhxxFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.groovy.XmlUtils;
import cn.gtmap.realestate.inquiry.ui.core.qo.BdcFyxxQO;
import cn.gtmap.realestate.inquiry.ui.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 宣称市级查询接口
 *
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/11/03
 */
@RestController
@RequestMapping("/rest/v1.0/sjxxgx")
public class BdcSjxxgxController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSjxxgxController.class);

    /**
     * 法院查询接口成功状态标识
     */
    public static final String SERVICE_FY_SUCCESS = "00";

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcDysjPzFeignService bdcDysjPzFeignService;
    @Autowired
    private PdfController pdfController;
    @Autowired
    private BdcUploadFileUtils bdcUploadFileUtils;

    /**
     * ===== 宣城法院查询接口查看详情保存附件名称配置=====
     */
    @Value("${sjcx.lsdcl.fjwjjmc:律师调查令}")
    private String lsdclFjwjjmc;

    @Value("${sjcx.lsdcl.fjmc:律师调查令核验}")
    private String lsdclFjmc;

    @Value("${sjcx.lsws.fjwjjmc:立案文书}")
    private String lawsFjwjjmc;

    @Value("${sjcx.laws.fjmc:立案文书信息}")
    private String lawsFjmc;

    @Autowired
    XuanchengHhxxFeignService hhxxFeignService;

    /**
     * 户籍人口信息查询
     *
     * @param pageable             分页对象
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping("/hjrkxxcx")
    public Object hjrkxxcx(@LayuiPageable Pageable pageable,@RequestParam("XM") String XM,@RequestParam("GMSFHM") String GMSFHM) {
        if (StringUtils.isBlank(XM) || StringUtils.isBlank(GMSFHM)) {
            throw new MissingArgumentException("姓名和证件号不能为空！");
        }
        Map<String,String> param = new HashMap<String,String>();
        param.put("XM",XM);
        param.put("GMSFHM",GMSFHM);
        Map paramMap = initRequest(XmlUtils.setXmlData(CommonConstantUtils.HJRKCX_REQUEST_CONDITION,param),CommonConstantUtils.HJRKCX_REQUEST_REQUIREDITEMS,CommonConstantUtils.REQUEST_CLIENTINFO);
        LOGGER.info("户籍人口信息查询请求参数：{}",paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("hjrkxxcx", paramMap);
        LOGGER.info("接口返回参数：{}",response);
        List result = new ArrayList<>();
        if(response != null){
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultXml = responseJson.getJSONObject("response").getJSONObject("value").getString("value");
            LOGGER.info("resultXml：{}",resultXml);
            String resultCode = XmlUtils.getXmlElementValue(resultXml,"resultCode");
            if(resultCode.equals("00")){
                result = readXMLToList(resultXml,HjrkxxcxResponseDTO.class);
            }
            LOGGER.info("返回页面的result：{}",result);
        }
        return addLayUiCode(PageUtils.listToPage(result,pageable));
    }

    /**
     * 户籍信息查询
     *
     * @param pageable             分页对象
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping("/hjxxcx")
    public Object hjxxcx(@LayuiPageable Pageable pageable,@RequestParam("zjh") String zjh) {
        if (StringUtils.isBlank(zjh)) {
            throw new MissingArgumentException("证件号不能为空！");
        }
        Map<String,String> param = new HashMap<String,String>();
        param.put("ZJH",zjh);
        Map paramMap = initRequest(XmlUtils.setXmlData(CommonConstantUtils.HJCX_REQUEST_CONDITION,param),CommonConstantUtils.HJCX_REQUEST_REQUIREDITEMS,CommonConstantUtils.REQUEST_CLIENTINFO);
        LOGGER.info("户籍信息查询请求参数：{}",paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("jtcycx", paramMap);
        LOGGER.info("接口返回参数：{}",response);
        List result = new ArrayList<>();
        if(response != null){
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultXml = responseJson.getJSONObject("response").getJSONObject("value").getString("value");
            LOGGER.info("resultXml：{}",resultXml);
            String resultCode = XmlUtils.getXmlElementValue(resultXml,"resultCode");
            if(resultCode.equals("00")){
                result = readXMLToList(resultXml,HjxxcxResponseDTO.class);
            }
            LOGGER.info("返回页面的result：{}",result);
        }
        return addLayUiCode(PageUtils.listToPage(result,pageable));
    }

    /**
     * 企业信息查询
     *
     * @param pageable             分页对象
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping("/qyxxcx")
    public Object qyxxcx(@LayuiPageable Pageable pageable,@RequestParam("QYMC") String QYMC,@RequestParam("SHTYXYDM") String SHTYXYDM) {
        if (StringUtils.isBlank(QYMC) || StringUtils.isBlank(SHTYXYDM)) {
            throw new MissingArgumentException("企业名称和统一社会性质代码不能为空！");
        }
        Map<String,String> param = new HashMap<String,String>();
        param.put("QYMC",QYMC);
        param.put("SHTYXYDM",SHTYXYDM);
        Map paramMap = initRequest(XmlUtils.setXmlData(CommonConstantUtils.QYXXCX_REQUEST_CONDITION,param),CommonConstantUtils.QYXXCX_REQUEST_REQUIREDITEMS,CommonConstantUtils.REQUEST_CLIENTINFO);
        LOGGER.info("企业信息查询请求参数：{}",paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("qyxxcx", paramMap);
        LOGGER.info("接口返回参数：：{}",response);
        List result = new ArrayList<>();
        if(response != null){
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultXml = responseJson.getJSONObject("response").getJSONObject("value").getString("value");
            LOGGER.info("resultXml：{}",resultXml);
            String resultCode = XmlUtils.getXmlElementValue(resultXml,"resultCode");
            if(resultCode.equals("00")){
                result = readXMLToList(resultXml,QyxxcxResponseDTO.class);
            }
            LOGGER.info("返回页面的result：{}",result);
        }
        return addLayUiCode(PageUtils.listToPage(result,pageable));
    }

    /**
     * 出生证明查询
     *
     * @param pageable             分页对象
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping("/cszmcx")
    public Object cszmcx(@LayuiPageable Pageable pageable,@RequestParam("CSYXZMBH") String CSYXZMBH,@RequestParam("FQXM") String FQXM,@RequestParam("MQXM") String MQXM) {
        if (StringUtils.isBlank(CSYXZMBH) || StringUtils.isBlank(FQXM) || StringUtils.isBlank(MQXM)) {
            throw new MissingArgumentException("出生证明编号、父亲姓名和母亲姓名不能为空！");
        }
        Map<String,String> param = new HashMap<String,String>();
        param.put("CSYXZMBH",CSYXZMBH);
        param.put("FQXM",FQXM);
        param.put("MQXM",MQXM);
        Map paramMap = initRequest(XmlUtils.setXmlData(CommonConstantUtils.CSZMCX_REQUEST_CONDITION,param),CommonConstantUtils.CSZMCX_REQUEST_REQUIREDITEMS,CommonConstantUtils.REQUEST_CLIENTINFO);
        LOGGER.info("出生证明查询请求参数：{}",paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("cszmcx", paramMap);
        LOGGER.info("接口返回参数：：{}",response);
        List result = new ArrayList<>();
        if(response != null){
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultXml = responseJson.getJSONObject("response").getJSONObject("value").getString("value");
            LOGGER.info("resultXml：{}",resultXml);
            String resultCode = XmlUtils.getXmlElementValue(resultXml,"resultCode");
            if(resultCode.equals("00")){
                result = readXMLToList(resultXml,CszmcxResponseDTO.class);
            }
            LOGGER.info("返回页面的result：{}",result);
        }
        return addLayUiCode(PageUtils.listToPage(result,pageable));
    }

    /**
     * 单位法人查询
     *
     * @param pageable             分页对象
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping("/dwfrcx")
    public Object dwfrcx(@LayuiPageable Pageable pageable,@RequestParam("MC") String MC,@RequestParam("MC") String TYSHXYDM) {
        if (StringUtils.isBlank(MC) || StringUtils.isBlank(TYSHXYDM)) {
            throw new MissingArgumentException("名称和社会信用代码不能为空！");
        }
        Map<String,String> param = new HashMap<String,String>();
        param.put("MC",MC);
        param.put("TYSHXYDM",TYSHXYDM);
        Map paramMap = initRequest(XmlUtils.setXmlData(CommonConstantUtils.DWFRCX_REQUEST_CONDITION,param),CommonConstantUtils.DWFRCX_REQUEST_REQUIREDITEMS,CommonConstantUtils.REQUEST_CLIENTINFO);
        LOGGER.info("单位法人查询请求参数：{}",paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("dwfrcx", paramMap);
        LOGGER.info("接口返回参数：：{}",response);
        List result = new ArrayList<>();
        if(response != null){
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultXml = responseJson.getJSONObject("response").getJSONObject("value").getString("value");
            LOGGER.info("resultXml：{}",resultXml);
            String resultCode = XmlUtils.getXmlElementValue(resultXml,"resultCode");
            if(resultCode.equals("00")){
                result = readXMLToList(resultXml,DwfrcxResponseDTO.class);
            }
            LOGGER.info("返回页面的result：{}",result);
        }
        return addLayUiCode(PageUtils.listToPage(result,pageable));
    }

    /**
     * 死亡证明查询
     *
     * @param pageable             分页对象
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping("/swzmcx")
    public Object swzmcx(@LayuiPageable Pageable pageable,@RequestParam("XM") String XM,@RequestParam("ZJHM") String ZJHM) {
        if (StringUtils.isBlank(XM) || StringUtils.isBlank(ZJHM)) {
            throw new MissingArgumentException("姓名和证件号不能为空！");
        }
        Map<String,String> param = new HashMap<String,String>();
        param.put("XM",XM);
        param.put("ZJHM",ZJHM);
        Map paramMap = initRequest(XmlUtils.setXmlData(CommonConstantUtils.SWZMCX_REQUEST_CONDITION,param),CommonConstantUtils.SWZMCX_REQUEST_REQUIREDITEMS,CommonConstantUtils.REQUEST_CLIENTINFO);
        LOGGER.info("死亡证明查询请求参数：{}",paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("swzmcx", paramMap);
        LOGGER.info("接口返回参数：：{}",response);
        List result = new ArrayList<>();
        if(response != null){
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultXml = responseJson.getJSONObject("response").getJSONObject("value").getString("value");
            LOGGER.info("resultXml：{}",resultXml);
            String resultCode = XmlUtils.getXmlElementValue(resultXml,"resultCode");
            if(resultCode.equals("00")){
                result = readXMLToList(resultXml,SwzmcxResponseDTO.class);
            }
            LOGGER.info("返回页面的result：{}",result);
        }
        return addLayUiCode(PageUtils.listToPage(result,pageable));
    }

    /**
     * 婚姻登记查询
     *
     * @param pageable             分页对象
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping("/hydjcx")
    public Object hydjcx(@LayuiPageable Pageable pageable,@RequestParam("XM") String XM,@RequestParam("GMSFHM") String GMSFHM) {
        if (StringUtils.isBlank(XM) || StringUtils.isBlank(GMSFHM)) {
            throw new MissingArgumentException("姓名和证件号不能为空！");
        }
        Map<String,String> param = new HashMap<String,String>();
        param.put("XM",XM);
        param.put("GMSFHM",GMSFHM);
        Map paramMap = initRequest(XmlUtils.setXmlData(CommonConstantUtils.HYDJCX_REQUEST_CONDITION,param),CommonConstantUtils.HYDJCX_REQUEST_REQUIREDITEMS,CommonConstantUtils.REQUEST_CLIENTINFO);
        LOGGER.info("婚姻登记查询请求参数：{}",paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("hydjcx", paramMap);
        LOGGER.info("接口返回参数：：{}",response);
        List result = new ArrayList<>();
        if(response != null){
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultXml = responseJson.getJSONObject("response").getJSONObject("value").getString("value");
            LOGGER.info("resultXml：{}",resultXml);
            String resultCode = XmlUtils.getXmlElementValue(resultXml,"resultCode");
            if(resultCode.equals("00")){
                result = readXMLToList(resultXml,HydjcxResponseDTO.class);
            }
            LOGGER.info("返回页面的result：{}",result);
        }
        return addLayUiCode(PageUtils.listToPage(result,pageable));
    }

    /**
     * 竣工验收备案查询
     *
     * @param pageable 分页对象
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping("/jgysbacx")
    public Object jgysbacx(@LayuiPageable Pageable pageable, BdcJgysbaRequestDTO bdcJgysbaRequestDTO) {
        if ( null == bdcJgysbaRequestDTO || (StringUtils.isBlank(bdcJgysbaRequestDTO.getBuildUnit()) && StringUtils.isBlank(bdcJgysbaRequestDTO.getProjName()))) {
            throw new MissingArgumentException("缺失必要查询条件！");
        }
        LOGGER.info("竣工验收备案查询：{}",bdcJgysbaRequestDTO.toString());
        Object response = exchangeInterfaceFeignService.requestInterface("jgysbacx", bdcJgysbaRequestDTO);
        LOGGER.info("接口返回参数：：{}",response);
        List result = new ArrayList<>();
        if (response != null) {
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultCode = responseJson.getString("success");
            if(resultCode.equals("true")){
                JSONArray content = responseJson.getJSONArray("content");
                List<BdcJgysbaResponseDTO> bdcJgysbaResponseDTOs = JSONArray.parseArray(content.toJSONString(), BdcJgysbaResponseDTO.class);
                result.addAll(bdcJgysbaResponseDTOs);
            }
            LOGGER.info("返回页面的result：{}",result);
        }
        return addLayUiCode(PageUtils.listToPage(result,pageable));
    }

    /**
     * 竣工验收备案详情查询
     *
     * @param certCode 备案编号
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping("/jgysbaxqcx")
    public Object jgysbaxqcx(@RequestParam("certCode") String certCode) {
        if (StringUtils.isBlank(certCode)) {
            throw new MissingArgumentException("缺失必要查询条件！");
        }
        LOGGER.info("竣工验收备案查询,备案编号：{}",certCode);
        Map<String,String> param = new HashMap<String,String>(1);
        param.put("certCode",certCode);
        Object response = exchangeInterfaceFeignService.requestInterface("jgysbaxqcx", param);
        LOGGER.info("接口返回参数：：{}",response);
        if (response != null) {
            JSONObject responseJson = new JSONObject((Map<String, Object>) response);
            String resultCode = responseJson.getString("success");
            if(resultCode.equals("true")){
                JSONObject content = responseJson.getJSONObject("content");
                BdcJgysbaxqResponseDTO bdcJgysbaxqResponseDTO = JSONObject.parseObject(content.toJSONString(), BdcJgysbaxqResponseDTO.class);
                return bdcJgysbaxqResponseDTO;
            }
        }
        return null;
    }

    public Map initRequest(String condition,String requiredItems,String clientInfo) {
        Map paramMap = new HashMap();
        List<Map> data = new ArrayList<>();
        Map conditionParam = new HashMap();
        conditionParam.put("name","condition");
        conditionParam.put("type","GsbString");
        conditionParam.put("value",condition);
        data.add(conditionParam);
        Map requiredItemsParam = new HashMap();
        requiredItemsParam.put("name","requiredItems");
        requiredItemsParam.put("type","GsbString");
        requiredItemsParam.put("value",requiredItems);
        data.add(requiredItemsParam);
        Map clientInfoParam = new HashMap();
        clientInfoParam.put("name","clientInfo");
        clientInfoParam.put("type","GsbString");
        clientInfoParam.put("value",clientInfo);
        data.add(clientInfoParam);
        paramMap.put("data", JSONArray.parseArray(JSON.toJSONString(data)));
        paramMap.put("method", "");
        paramMap.put("serviceCode", "");
        paramMap.put("version", "");
        paramMap.put("authCode", "");
        paramMap.put("senderID", "");
        return paramMap;
    }


    public static List<Object> readXMLToList( String s, Class<?> clazz) {

        List<Object> list = new ArrayList();//创建list集合
        try {
            //1.创建一个SAXBuilder的对象
            SAXReader reader = new SAXReader();
            // 指定编码格式为UTF-8,可根据具体情况修改
            org.dom4j.Document doc = reader.read(new InputSource(new ByteArrayInputStream(s.getBytes("UTF-8"))));//dom4j读取
            // 4.通过document对象获取xml文件的根节点
            org.dom4j.Element root = doc.getRootElement();//获得根节点
            // 5. 获取根节点下的子节点items,即要解析XML多节点的父节点，可根据具体情况修改
            org.dom4j.Element body1 = root.element("body");
            org.dom4j.Element body = body1.element("resultList");
            // 6. 获取根节点下的二级节点
            org.dom4j.Element foo;
            for (Iterator i = body.elementIterator("result"); i.hasNext();) {//遍历t.getClass().getSimpleName()节点
                foo = (org.dom4j.Element) i.next();//下一个二级节点
                //实例化Object对象
                Object obj = clazz.newInstance();
                Field[] properties = obj.getClass().getDeclaredFields();//获得实例的属性
                //实例的get方法
                Method getmeth;
                //实例的set方法
                Method setmeth;
                //遍历实体类的属性
                for (int j = 0; j < properties.length; j++) {
                    //实例的set方法
                    if (properties[j].getName().equals("serialVersionUID")) {
                        continue;
                    } else {
                        setmeth = obj.getClass().getMethod(
                                "set"
                                        + properties[j].getName().substring(0, 1).toUpperCase()
                                        + properties[j].getName().substring(1), properties[j].getType());
                        Object setStr = foo.elementText(properties[j].getName());

                        if (foo.elementText(properties[j].getName()) != null&&!"".equals(foo.elementText(properties[j].getName()))) {
                            if (properties[j].getType() == java.util.Date.class) {
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date sj = df.parse(foo.elementText(properties[j].getName()));
                                setStr = sj;
                            } else if (properties[j].getType() == java.lang.Integer.class) {
                                setStr = Integer.parseInt(foo.elementText(properties[j].getName()));
                            } else if (properties[j].getType() == java.lang.Character.class) {
                                setStr = foo.elementText(properties[j].getName()).charAt(0);
                            }else if (properties[j].getType() == java.lang.Double.class) {
                                setStr = Double.parseDouble(foo.elementText(properties[j].getName()));
                            }
                        } else {
                            setStr = null;
                        }
                        //将对应节点的值存入
                        setmeth.invoke(obj, setStr);
                    }
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 宣城市级法院查询接口
     * @param pageable 分页参数
     * @param bdcLsdclQO 查询参数
     * @param jkmc 接口名称（xclsdcl 律师调查令  laws 立案文书）
     * @return {Object} 接口返回分页信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping("/fyxx/{jkmc}")
    public Object fycx(@LayuiPageable Pageable pageable, BdcFyxxQO bdcLsdclQO, @PathVariable("jkmc") String jkmc) {
        if(!CheckParameter.checkAnyParameter(bdcLsdclQO) || StringUtils.isBlank(jkmc)) {
            throw new MissingArgumentException("缺失查询参数");
        }

        // 律师调查令查询结果字段
        String lsdclcxjg = "lsxm,lsxb,lszyjg,lszyzh,lsdclbh,dcsx,dclyxjzrq,fymc";
        // 立案文书查询结果字段
        String lawscxjg = "ah,slfy,ay,dsr,cbbm,cbr,sjy,larq,sar,labd,sycx,ajly";

        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject param = new JSONObject();
            param.put("condition", bdcLsdclQO);
            param.put("requiredItems", "xclsdcl".equals(jkmc)? lsdclcxjg : lawscxjg);
            jsonObject.put("requestBody", URLEncoder.encode(JSON.toJSONString(param), "UTF-8"));
            LOGGER.info("宣城查询法院查询接口{}信息入参：{}", jkmc, JSON.toJSONString(jsonObject));

            Object response = exchangeInterfaceFeignService.requestInterface(jkmc, jsonObject);
            if (null == response) {
                LOGGER.info("宣城查询全市法院接口{}信息无返回值，查询参数：{}", jkmc, JSON.toJSONString(bdcLsdclQO));
                return addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));
            }
            LOGGER.info("宣城查询全市法院接口{}信息返回值：{}，查询参数：{}", jkmc, response, JSON.toJSONString(bdcLsdclQO));

            JSONObject responseJSON = JSONObject.parseObject(JSON.toJSONString(response));
            String resultCode = responseJSON.getString("resultCode");
            if(SERVICE_FY_SUCCESS.equals(resultCode)) {
                // 请求成功
                UserDto userDto = userManagerUtils.getCurrentUser();
                String useralias = null == userDto ? "" : userDto.getAlias();

                List<Map> resultData = JSON.parseArray(JSON.toJSONString(responseJSON.get("resultData")), Map.class);
                if(CollectionUtils.isNotEmpty(resultData)) {
                    for(Map data : resultData) {
                        if(MapUtils.isNotEmpty(data)) {
                            data.put("hyr", useralias);
                            data.put("hysj", DateFormatUtils.format(new Date(), DateUtils.sdf_ymdhms));
                        }
                    }
                }
                return addLayUiCode(new PageImpl(resultData, pageable, resultData.size()));
            } else {
                throw new AppException(responseJSON.getString("resultInfo"));
            }
        } catch (Exception e) {
            LOGGER.error("宣城查询全市法院接口{}信息查询异常，查询参数：{}", jkmc, JSON.toJSONString(bdcLsdclQO), e);
            throw new AppException("查询异常");
        }
    }

    /**
     * 宣城市级法院查询接口详情内容生成PDF保存到当前项目
     * @param jkmc 接口名称（lsdclcxhy 律师调查令  lawshy 立案文书）
     * @param processInsId 工作流实例ID
     * @param data 接口详情数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/fyxx/pdf/{jkmc}/{processInsId}")
    public void generatePdf(@PathVariable("jkmc")String jkmc, @PathVariable("processInsId")String processInsId, @RequestBody Map<String, String> data) {
        if(StringUtils.isAnyBlank(jkmc, processInsId) || MapUtils.isEmpty(data)) {
            throw new MissingArgumentException("宣城查询全市法院接口详情生成PDF缺失参数");
        }

        BdcDysjPzDTO bdcDysjPzDO = bdcDysjPzFeignService.getPzxx(jkmc);
        if(null == bdcDysjPzDO || StringUtils.isBlank(bdcDysjPzDO.getDyzd())) {
            throw new AppException("未配置打印数据源" + jkmc);
        }

        String xmlData = bdcDysjPzDO.getDyzd();
        for(Map.Entry<String, String> entry : data.entrySet()) {
            xmlData = xmlData.replace("$" + StringUtils.upperCase(entry.getKey()), entry.getValue());
        }

        // 生成PDF
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(bdcDysjPzDO.getPdfpath());
        pdfExportDTO.setFileName("lsdclcxhy".equals(jkmc)? "律师调查令核验" : "立案文书");
        pdfExportDTO.setXmlData(xmlData);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);

        // 保存到项目附件
        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
        bdcPdfDTO.setGzlslid(processInsId);
        bdcPdfDTO.setFoldName("lsdclcxhy".equals(jkmc)? lsdclFjwjjmc : lawsFjwjjmc);
        bdcPdfDTO.setPdffjmc("lsdclcxhy".equals(jkmc)? lsdclFjmc : lawsFjmc);
        bdcPdfDTO.setFileSuffix(CommonConstantUtils.WJHZ_PDF);
        bdcPdfDTO.setPdfFilePath(pdfFilePath);
        try {
            bdcUploadFileUtils.uploadPdfByFilePath(bdcPdfDTO);
        } catch (IOException e) {
            LOGGER.error("宣城查询全市法院接口详情生成PDF上传文件失败", e);
            throw new AppException("宣城查询全市法院接口详情生成PDF上传文件失败");
        }
    }

    /**
     * 宣城-火化信息查询
     *
     * @param pageable 分页对象
     * @param xm 逝者姓名
     * @param zjh 逝者证件号
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @GetMapping("/hhxxcx")
    public Object hhxxcx(@LayuiPageable Pageable pageable, @RequestParam("xm") String xm, @RequestParam("zjh") String zjh) {
        LOGGER.info("宣城火化信息查询台账, 请求参数: 逝者姓名{}, 逝者证件号{}", xm, zjh);
        if (StringUtils.isBlank(xm)) {
            LOGGER.info("逝者姓名为空");
//            throw new MissingArgumentException("逝者姓名不能为空！");
        }
        if (StringUtils.isBlank(zjh)) {
            LOGGER.info("逝者证件号为空");
//            throw new MissingArgumentException("逝者证件号不能为空！");
        }

        HhxxRequestBody hhxxRequestBody = new HhxxRequestBody();
        HhxxRequestCondition condition = new HhxxRequestCondition();
        hhxxRequestBody.setCondition(condition);
        hhxxRequestBody.setRequiredItems("SZXM,SZZJH,SZXB,SZHJDZ,SWRQ,HHKSSJ");

        condition.setSZXM(xm);
        condition.setSZZJH(zjh);

        String reqJsonStr = JSON.toJSONString(hhxxRequestBody);
        LOGGER.info("宣城火化信息查询, 请求参数: {}", reqJsonStr);
        HhxxResponseBody hhxxResponseBody = new HhxxResponseBody();

        try {
            String res = hhxxFeignService.hhxx(reqJsonStr);
            res = StringEscapeUtils.unescapeJava(res);

            if (res.endsWith("\"")) {
                res = res.substring(0,res.length()-1);
            }
            if (res.startsWith("\"")) {
                res = res.substring(1);
            }

            hhxxResponseBody = JSONObject.parseObject(res, HhxxResponseBody.class);

            LOGGER.info("宣城火化信息查询, 响应结果: {}", JSON.toJSONString(hhxxResponseBody));
        } catch (Exception e) {
            LOGGER.warn("宣城火化信息查询失败, exception: {}", e.getMessage());
            throw e;
        }

//        String test1 = "{\"resultCode\":\"00\",\"resultData\":[{\"SWRQ\":\"2020-09-03\",\"SZXB\":\"男\",\"SZHJDZ\":\"袁家庄组\",\"HHKSSJ\":\"2020-09-07 07:44:00\",\"SZZJH\":\"342524195602023236\",\"SZXM\":\"汤小春\"}],\"resultInfo\":\"请求成功\",\"resultTotal\":0}";
//        hhxxResponseBody = JSONObject.parseObject(test1, HhxxResponseBody.class);

        String resultCode = hhxxResponseBody.getResultCode();
        List<HhxxResponseData> responseDataList = new ArrayList<>();
        if (StringUtils.isNotBlank(resultCode)) {
            if ("00".equals(resultCode)) {
                LOGGER.info("宣城火化信息查询,请求成功 resultCode: {}", resultCode);
                responseDataList = hhxxResponseBody.getResultData();
            } else {
                LOGGER.info("宣城火化信息查询, resultCode: {}, resultInfo: {}", resultCode, hhxxResponseBody.getResultInfo());
                throw new AppException("查询火化信息缺失失败, 原因: "+JSONObject.toJSONString(hhxxResponseBody.getResultInfo()));
            }
        } else {
            LOGGER.info("宣城火化信息查询失败");
            throw new AppException("查询火化信息缺失失败了");
        }

        LOGGER.info("返回页面的result：{}",JSONObject.toJSONString(responseDataList));
        return addLayUiCode(PageUtils.listToPage(responseDataList,pageable));
    }

}
