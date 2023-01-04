package cn.gtmap.realestate.inquiry.ui.web.rest.bengbu;

import cn.gtmap.realestate.common.core.dto.etl.EtlBbHtbaMsfDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlBbHtbaZrfDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlClfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2020/3/6 09:55
 * @description 通过exchange查询相关信息进行展示台账
 * 1.个人身份查询
 * 2.营业执照查询
 */
@RestController
@RequestMapping("/rest/v1.0/queryByExchange")
public class QueryByExchange {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryByExchange.class);

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;


    /**
     * 根据证件号查询个人身份证信息，exchange调用的是科大讯飞的接口
     *
     * @param zjh 证件号
     * @return idCard 身份证信息
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @ResponseBody
    @GetMapping("/bengbu/queryIdinfo")
    public Object queryIDcard(@RequestParam(value = "zjh", required = false) String zjh) {
        if (StringUtils.isBlank(zjh)) {
            throw new MissingArgumentException("缺失证件号参数！");
        }
        String beanName = "bb_cxsfzxx";
        Map<String, String> param = new HashMap<>(16);
        param.put("zjh", StringUtils.deleteWhitespace(zjh));
        param.put("beanName", beanName);
        Object idCard = exchangeInterfaceFeignService.requestInterface(beanName, param);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(idCard));
        LOGGER.info("jsonObject:{}", jsonObject);
        if (!jsonObject.isEmpty()) {
            String grzjhm = jsonObject.getString("GRZJHM").toString();
            jsonObject.put("GRZJHM", grzjhm);
            if (StringUtils.isNotBlank(jsonObject.getString("ZP"))) {
                jsonObject.getString("ZP").replaceAll("\\\\n", "");
            }
        }
        LOGGER.info("去除换行符之后：" + jsonObject);
        return jsonObject;
    }

    /**
     * 根据证件号查询营业执照信息，exchange调用的是科大讯飞的接口
     *
     * @param zjh 营业执照号
     * @return yyzzInfo 营业执照信息
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @ResponseBody
    @GetMapping("/bengbu/queryYyzzinfo")
    public Object queryYyzzinfo(@RequestParam(value = "zjh", required = false) String zjh) {
        if (StringUtils.isBlank(zjh)) {
            throw new MissingArgumentException("缺失查询参数！");
        }
        String beanName = "bb_cxyyzzxx";
        Map<String, String> param = new HashMap<>(16);
        param.put("zjh", StringUtils.deleteWhitespace(zjh));
        param.put("beanName", beanName);
        Object yyzzInfo = exchangeInterfaceFeignService.requestInterface(beanName, param);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(yyzzInfo));
        if (!jsonObject.isEmpty()) {
            String shtyxydm = jsonObject.getString("SHTYXYDM").toString();
            jsonObject.put("SHTYXYDM", shtyxydm);
            LOGGER.info("证件号toString：" + jsonObject);
        }
        return jsonObject;
    }


    /**
     * @param qlrzjh   权利人证件号
     * @param beanName 接口定义标识符
     * @return 接口返回值（Object）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 婚姻、户籍信息查询
     */
    @ResponseBody
    @GetMapping("/bengbu/hygaxx")
    public Object getHygaxx(@RequestParam(value = "qlrzjh", required = true) String qlrzjh,
                            @RequestParam(value = "beanName", required = true) String beanName) {
        if (org.apache.commons.lang3.StringUtils.isAnyBlank(qlrzjh, beanName)) {
            throw new AppException("查询参数缺失！");
        }
        Map param = new HashMap(2);
        param.put("qlrzjh", qlrzjh);
        return exchangeInterfaceFeignService.requestInterface(beanName, param);
    }


    /**
     * @param htbh   合同编号
     * @param beanName 接口定义标识符
     * @return 接口返回值（Object）
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 查询
     */
    @ResponseBody
    @GetMapping("/bengbu/fcjyhtxx")
    public Object getFcjyHtxx(@RequestParam(value = "htbh", required = true) String htbh,
                            @RequestParam(value = "beanName", required = true) String beanName) {
        if (org.apache.commons.lang3.StringUtils.isAnyBlank(htbh, beanName)) {
            throw new AppException("查询参数缺失！");
        }
        Map param = new HashMap(2);
        param.put("contractNo", htbh);
        LOGGER.info("获取住建网签合同信息接口，beanName:{}，请求参数参数:{}", beanName, JSONObject.toJSONString(param));
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, param);
        LOGGER.info("获取住建网签合同信息接口，响应信息:{}", JSONObject.toJSONString(response));
        if (Objects.nonNull(response)) {
            if ("fcjySpfBaxx_bbhtxx".equals(beanName)) {
                EtlSpfHtbaResponseDTo etlSpfHtbaResponseDTo = JSONObject.parseObject(JSONObject.toJSONString(response), EtlSpfHtbaResponseDTo.class);
                List<EtlBbHtbaMsfDTO> msfDTOList = etlSpfHtbaResponseDTo.getMsfDTOList();
                if (CollectionUtils.isNotEmpty(msfDTOList)) {
                    String msr = msfDTOList.stream().map(msfDTO -> msfDTO.getMsr()).collect(Collectors.joining("/"));
                    String msrzjhm = msfDTOList.stream().map(msfDTO -> msfDTO.getMsrzjhm()).collect(Collectors.joining("/"));
                    etlSpfHtbaResponseDTo.setMsr(msr);
                    etlSpfHtbaResponseDTo.setMsrzjhm(msrzjhm);
                }
                return etlSpfHtbaResponseDTo;
            } else if ("fcjyClfHtxx_bbhtxx".equals(beanName)) {
                EtlClfHtbaResponseDTo etlClfHtbaResponseDTo = JSONObject.parseObject(JSONObject.toJSONString(response), EtlClfHtbaResponseDTo.class);
                List<EtlBbHtbaMsfDTO> msfDTOList = etlClfHtbaResponseDTo.getMsfDTOList();
                List<EtlBbHtbaZrfDTO> zrfDTOList = etlClfHtbaResponseDTo.getZrfDTOList();
                if (CollectionUtils.isNotEmpty(msfDTOList)) {
                    String srfmc = msfDTOList.stream().map(msfDTO -> msfDTO.getMsr()).collect(Collectors.joining("/"));
                    String srfzjhm = msfDTOList.stream().map(msfDTO -> msfDTO.getMsrzjhm()).collect(Collectors.joining("/"));
                    String msrzjlb = msfDTOList.stream().map(msfDTO -> msfDTO.getMsrzjlb()).collect(Collectors.joining("/"));
                    String srflxdh = msfDTOList.stream().map(msfDTO -> msfDTO.getMsrlxdh()).collect(Collectors.joining("/"));
                    etlClfHtbaResponseDTo.setSrfmc(srfmc);
                    etlClfHtbaResponseDTo.setSrfzjhm(srfzjhm);
                    etlClfHtbaResponseDTo.setMsrzjlb(msrzjlb);
                    etlClfHtbaResponseDTo.setSrflxdh(srflxdh);
                }
                if (CollectionUtils.isNotEmpty(zrfDTOList)) {
                    String zrfmc = zrfDTOList.stream().map(zrfDTO -> zrfDTO.getZrfmc()).collect(Collectors.joining("/"));
                    String zrfzjhm = zrfDTOList.stream().map(zrfDTO -> zrfDTO.getZrfzjhm()).collect(Collectors.joining("/"));
                    String zrflxdh = zrfDTOList.stream().map(zrfDTO -> zrfDTO.getZrflxdh()).collect(Collectors.joining("/"));
                    etlClfHtbaResponseDTo.setZrfmc(zrfmc);
                    etlClfHtbaResponseDTo.setZrfzjhm(zrfzjhm);
                    etlClfHtbaResponseDTo.setZrflxdh(zrflxdh);
                }
                return etlClfHtbaResponseDTo;
            }
        }
        return null;
    }

    /**
     * @param cszh     出生证号
     * @param beanName 接口定义标识符
     * @return 接口返回值（Object）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 婚姻、户籍信息查询
     */
    @ResponseBody
    @GetMapping("/bengbu/csxx")
    public Object getCsxx(@RequestParam(value = "cszh", required = true) String cszh,
                          @RequestParam(value = "xsemqxm", required = true) String xsemqxm,
                          @RequestParam(value = "xsefqxm", required = true) String xsefqxm,
                          @RequestParam(value = "beanName", required = true) String beanName) {
        if (org.apache.commons.lang3.StringUtils.isAnyBlank(cszh, xsefqxm, xsemqxm, beanName)) {
            throw new AppException("查询参数缺失！");
        }
        Map param = new HashMap(2);
        param.put("cszh", cszh);
        param.put("xsemqxm", xsemqxm);
        param.put("xsefqxm", xsefqxm);
        Object cszm = exchangeInterfaceFeignService.requestInterface(beanName, param);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(cszm));
        LOGGER.info("出生证明返回：{}：", jsonObject);
        return JSONObject.toJSON(jsonObject);
//        return exchangeInterfaceFeignService.requestInterface(beanName, param);
    }

    /**
     * @param cxrzjhm     出生证号
     * @param beanName 接口定义标识符
     * @return 接口返回值（Object）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 婚姻、户籍信息查询
     */
    @ResponseBody
    @GetMapping("/bengbu/swxx")
    public Object getSwxx(@RequestParam(value = "xm", required = true) String xm,
                          @RequestParam(value = "cxrzjhm", required = true) String cxrzjhm,
                          @RequestParam(value = "beanName", required = true) String beanName) {
        if (org.apache.commons.lang3.StringUtils.isAnyBlank(xm, cxrzjhm, beanName)) {
            throw new AppException("查询参数缺失！");
        }
        Map param = new HashMap(2);
        param.put("xm", xm);
        param.put("cxrzjhm", cxrzjhm);
        Object cszm = exchangeInterfaceFeignService.requestInterface(beanName, param);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(cszm));
        LOGGER.info("死亡信息返回：{}：", jsonObject);
        return JSONObject.toJSON(jsonObject);
//        return exchangeInterfaceFeignService.requestInterface(beanName, param);
    }

    /**
     * @param wjh      文件号
     * @param beanName 接口定义标识符
     * @return 接口返回值（Object）
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 土地资产划拨
     */
    @ResponseBody
    @GetMapping("/bengbu/tdzchb")
    public Object getTdzchb(@RequestParam(value = "wjh", required = true) String wjh,
                            @RequestParam(value = "beanName", required = true) String beanName) {
        if (org.apache.commons.lang3.StringUtils.isAnyBlank(wjh, beanName)) {
            throw new AppException("查询参数缺失！");
        }
        Map param = new HashMap(2);
        param.put("wjh", wjh);
        Object tdzchbxx = exchangeInterfaceFeignService.requestInterface(beanName, param);
        JSONArray jsonObject = JSONArray.parseArray(JSONObject.toJSONString(tdzchbxx));
        /*String tdzchbxx = "[ {\n" +
                "\t\t\t\t\t\"WJH\" : \"财行资[2021]569号\",\n" +
                "\t\t\t\t\t\"FJMC\" : \"蚌财行资〔2021〕569号：市委统战部划转房产复函_1.jpg\",\n" +
                "\t\t\t\t\t\"ZCMC\" : \"房屋\",\n" +
                "\t\t\t\t\t\"HRF\" : \"市行管中心\",\n" +
                "\t\t\t\t\t\"HCF\" : \"市委统战部\",\n" +
                "\t\t\t\t\t\"BZ\" : null,\n" +
                "\t\t\t\t\t\"WJM\" : \"关于市委统战部划转房产的复函\",\n" +
                "\t\t\t\t\t\"SCWZ\" : \"南山路63号\"\n" +
                "\t\t\t\t}, {\n" +
                "\t\t\t\t\t\"WJH\" : \"财行资[2021]569号\",\n" +
                "\t\t\t\t\t\"FJMC\" : \"蚌财行资〔2021〕569号：市委统战部划转房产复函_2.jpg\",\n" +
                "\t\t\t\t\t\"ZCMC\" : \"房屋\",\n" +
                "\t\t\t\t\t\"HRF\" : \"市行管中心\",\n" +
                "\t\t\t\t\t\"HCF\" : \"市委统战部\",\n" +
                "\t\t\t\t\t\"BZ\" : null,\n" +
                "\t\t\t\t\t\"WJM\" : \"关于市委统战部划转房产的复函\",\n" +
                "\t\t\t\t\t\"SCWZ\" : \"南山路63号\"\n" +
                "\t\t\t\t} ]";*/
//        JSONArray jsonObject = JSONArray.parseArray(tdzchbxx);

        LOGGER.info("土地资产划拨信息返回：{}：", jsonObject);
        return JSONObject.toJSON(jsonObject);
    }

    /**
     * 根据对账日期获取税费统缴对账单信息
     * @param dzrq 对账日期
     * @return 对账单信息和对账单文件
     */
    @ResponseBody
    @GetMapping("/sftjdzd")
    public Object querySftjDzdxx(@RequestParam(value = "dzrq", required = true) String dzrq) {
        if(StringUtils.isBlank(dzrq)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到对账日期参数。");
        }
        Map param = new HashMap(2);
        param.put("rcnclDt", dzrq);
        return exchangeInterfaceFeignService.requestInterface("zr_dzwjxz", param);
    }

}
