package cn.gtmap.realestate.exchange.service.impl;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkCsDO;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkXffdzgxDO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.*;
import cn.gtmap.realestate.common.core.enums.OpenApiFieldType;
import cn.gtmap.realestate.common.core.enums.OpenApiParamConstansEnum;
import cn.gtmap.realestate.common.core.enums.OpenApiReleaseStatus;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.interfaces.CustomConverter;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.core.convert.BdcOpenApiConvert;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcDwJkCsMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcDwJkMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcDwJkXffdzgxMapper;
import cn.gtmap.realestate.exchange.service.BdcOpenApiManagementService;
import cn.gtmap.realestate.exchange.service.impl.inf.build.BuildLogServiceImpl;
import cn.gtmap.realestate.exchange.util.OpenApiUrlUtils;
import cn.gtmap.realestate.exchange.util.TreeNode;
import cn.gtmap.realestate.exchange.util.constants.ConfigLocations;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mortbay.util.MultiMap;
import org.mortbay.util.UrlEncoded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/24 15:28
 */
@Service
public class BbcOpenApiManagementServiceImpl implements BdcOpenApiManagementService {

    private static final Logger logger = LoggerFactory.getLogger(BbcOpenApiManagementServiceImpl.class);

    private static final String SQL_SCRIPT_SEPARATOR = ";";

    private static final String BASE_URL = "/realestate-exchange/rest/v1.0/apiManagement/simple/";

    private final String BASE_PACKAGE = "cn.gtmap.realestate.exchange.web.rest";

    private final String RESOURCE_PATTERN = "/**/*.class";

    private static final String XML_BEAN_INTERFACE_URL_PRE_FIX = "/realestate-exchange/rest/v1.0/interface/";

    @Value("${api.management.token.url:http://192.168.2.99:8002/account/oauth/token}")
    private String tokenUrl;

    @Value("${api.management.token.grant.type:client_credentials}")
    private String tokenGrantType;

    @Value("${api.management.token.client.secret:accountUIClientSecret}")
    private String tokenClientSecret;

    @Value("${api.management.token.client.id:accountUIClientID}")
    private String tokenClientId;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BdcDwJkMapper bdcDwJkMapper;

    @Autowired
    private BdcDwJkCsMapper bdcDwJkCsMapper;

    @Autowired
    private BdcDwJkXffdzgxMapper bdcDwJkXffdzgxMapper;

    @Autowired
    private Repository repository;

    @Autowired
    private BuildLogServiceImpl buildLogService;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BdcOpenApiConvert bdcOpenApiConvert;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;


    /**
     * 保存接口信息
     *
     * @param bdcOpenApiDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    @Transactional(transactionManager = "serverTransactionManager", rollbackFor = Exception.class)
    public String saveApi(BdcOpenApiDTO bdcOpenApiDTO) {

        /**
         * 生成apiId和url
         */
        String apiId = UUIDGenerator.generate();
        if (StringUtils.isNotBlank(bdcOpenApiDTO.getId())) {
            apiId = bdcOpenApiDTO.getId();
        }

        String url = generateUrl(bdcOpenApiDTO.getUrl(), bdcOpenApiDTO.getParamList());

        /**
         * 校验
         */
        checkParam(bdcOpenApiDTO);
        checkUrlUniqueness(apiId, url);
        checkNameUniqueness(apiId, bdcOpenApiDTO.getName());

        /**
         * 保存接口基本信息
         */
        BdcDwJkDO bdcDwJkDO = new BdcDwJkDO();
        bdcDwJkDO.convertDTO(bdcOpenApiDTO);
        bdcDwJkDO.setJkid(apiId);
        bdcDwJkDO.setJkdz(url);
        bdcDwJkMapper.insertApiInfo(bdcDwJkDO);

        /**
         * 保存接口参数
         */
        saveApiParam(apiId, bdcOpenApiDTO.getParamList());

        return apiId;
    }

    /**
     * 分页查询接口信息
     *
     * @param pageable
     * @param bdcOpenApiQOStr
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    public Page<BdcOpenApiDetailInfoDTO> listBdcOpenApi(Pageable pageable, String bdcOpenApiQOStr) {
        Map<String, Object> paramMap = PageUtils.pageableSort(pageable, JSONObject.parseObject(bdcOpenApiQOStr, HashMap.class));

        Page<BdcDwJkDO> result = repository.selectPaging("listPageApiInfoByPage", paramMap, pageable);
        Page<BdcOpenApiDetailInfoDTO> dtoPage = result.map(new CustomConverter<BdcDwJkDO, BdcOpenApiDetailInfoDTO>() {
                @Override
                public BdcOpenApiDetailInfoDTO doConvert(BdcDwJkDO bdcDwJkDO) {
                    BdcOpenApiDetailInfoDTO bdcOpenApiDetailInfoDTO = searchOpenApiByApiId(bdcDwJkDO.getJkid());
                    return bdcOpenApiDetailInfoDTO;
                }
            }
        );
        return dtoPage;
    }

    /**
     * 根据ikid查询接口详细信息
     *
     * @param apiId
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    @GetMapping("/realestate-exchange/rest/v1.0/searchById")
    public BdcOpenApiDetailInfoDTO searchOpenApiByApiId(String apiId) {
        BdcDwJkDO bdcDwJkDO = bdcDwJkMapper.searchApiInfoById(apiId);

        List<BdcDwJkCsDO> bdcDwJkCsDOList = bdcDwJkCsMapper.searchApiParamByApiId(apiId);

        BdcOpenApiDetailInfoDTO bdcOpenApiDetailInfoDTO = new BdcOpenApiDetailInfoDTO();

        if (Objects.isNull(bdcDwJkDO) && CollectionUtils.isEmpty(bdcDwJkCsDOList)) {
            return bdcOpenApiDetailInfoDTO;
        }
        if (Objects.isNull(bdcDwJkDO)) {
            bdcOpenApiDetailInfoDTO.convertDOWithOutBdcDwJkDO(bdcDwJkCsDOList);
        } else {
            bdcOpenApiDetailInfoDTO.convertDO(bdcDwJkDO, bdcDwJkCsDOList);
        }

        List<BdcDwJkCsDO> restParamList = bdcDwJkCsDOList.stream().filter(x -> x.getCslx().equals(2)).collect(Collectors.toList());
        List<BdcDwJkCsDO> queryParamList = bdcDwJkCsDOList.stream().filter(x -> x.getCslx().equals(1)).collect(Collectors.toList());
        List<BdcDwJkCsDO> requestBodyParamList = bdcDwJkCsDOList.stream().filter(x -> x.getCslx().equals(0)).collect(Collectors.toList());
        List<BdcDwJkCsDO> tempList = new ArrayList<>();
        initRequestParamList(requestBodyParamList, tempList);
        requestBodyParamList.addAll(tempList);
        List<BdcDwJkCsDO> returnTypeParamList = bdcDwJkCsDOList.stream().filter(x -> x.getCslx().equals(3)).collect(Collectors.toList());
        bdcOpenApiDetailInfoDTO.setRestParamJson(paramListConvertJson(restParamList));
        bdcOpenApiDetailInfoDTO.setQueryParamJson(paramListConvertJson(queryParamList));
        bdcOpenApiDetailInfoDTO.setRequestBodyParamJson(paramListConvertJson(requestBodyParamList));
        bdcOpenApiDetailInfoDTO.setReturnTypeParamJson(paramListConvertJson(returnTypeParamList));
        List<BdcOpenApiParamDTO> tempReturnParamList = bdcOpenApiDetailInfoDTO.getReturnTypeParamList();
//        logger.info(JSON.toJSONString(bdcOpenApiDetailInfoDTO));
        if (CollectionUtils.isNotEmpty(tempReturnParamList)) {
            Map<String, BdcOpenApiParamDTO> collect = tempReturnParamList.stream().collect(Collectors.toMap(BdcOpenApiParamDTO::getFieldName, bdcOpenApiParamDTO -> bdcOpenApiParamDTO));
            returnTypeParamList.forEach(bdcDwJkCsDO -> {
                BdcOpenApiParamDTO tempBdcOpenApiParamDTO = collect.get(bdcDwJkCsDO.getCsm());
                if (StringUtils.isNotBlank(bdcDwJkCsDO.getJkcsext())) {
                    JSONObject jsonObject = JSON.parseObject(bdcDwJkCsDO.getJkcsext());
                    if (jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey()).equals(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getValue())) {
                        tempBdcOpenApiParamDTO.setIsListFlag("on");
                    } else {
                        tempBdcOpenApiParamDTO.setIsListFlag("off");
                    }
                    if (jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_GET_RESULT_WITH_ALONE_SQL.getKey()).equals(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_GET_RESULT_WITH_ALONE_SQL.getValue())) {
                        tempBdcOpenApiParamDTO.setSql(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_SQL.getKey()));
                        if (jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_OBJECT_YES.getKey()).equals(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_OBJECT_YES.getValue()) && StringUtils.isNotBlank(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()))) {
                            tempBdcOpenApiParamDTO.setChildParamId(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()));
                        }
                    }
                }
            });
            bdcOpenApiDetailInfoDTO.setReturnTypeParamList(collect.values().stream().collect(Collectors.toList()));
        }
        bdcOpenApiDetailInfoDTO.setTokenUrl(tokenUrl);
        bdcOpenApiDetailInfoDTO.setTokenGrantType(tokenGrantType);
        bdcOpenApiDetailInfoDTO.setTokenClientId(tokenClientId);
        bdcOpenApiDetailInfoDTO.setTokenClientSecret(tokenClientSecret);
        return bdcOpenApiDetailInfoDTO;
    }

    private void initRequestParamList(List<BdcDwJkCsDO> requestBodyParamList, List<BdcDwJkCsDO> tempList) {
        requestBodyParamList.forEach(bdcDwJkCsDO -> {
            if (StringUtils.isNotBlank(bdcDwJkCsDO.getJkcsext())) {
                JSONObject jsonObject = JSON.parseObject(bdcDwJkCsDO.getJkcsext());
                String jkid = jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey());
                List<BdcDwJkCsDO> bdcDwJkCsDOS = bdcDwJkCsMapper.searchApiParamByApiId(jkid);
                initRequestParamList(bdcDwJkCsDOS, tempList);
                tempList.addAll(bdcDwJkCsDOS);
            }
        });
    }

    @Override
    public Map<String, String> getOpenApiDocXmlData(String jkid) {

        Map<String, String> map = new ConcurrentHashMap<>(16);

        BdcDwJkDO bdcDwJkDO = bdcDwJkMapper.searchApiInfoById(jkid);
        if (Objects.isNull(bdcDwJkDO)) {
            return map;
        }

        List<BdcDwJkCsDO> bdcDwJkCsDOList = bdcDwJkCsMapper.searchApiParamByApiId(jkid);

        List<BdcDwJkCsDO> requestBodyParamList = initBdcApiParamList(bdcDwJkCsDOList.stream().filter(x -> x.getCslx().equals(0)).sorted(Comparator.comparing(BdcDwJkCsDO::getCscj)).collect(Collectors.toList()));

        List<BdcDwJkCsDO> returnTypeParamList = initBdcApiParamList(bdcDwJkCsDOList.stream().filter(x -> x.getCslx().equals(3)).sorted(Comparator.comparing(BdcDwJkCsDO::getCscj)).collect(Collectors.toList()));

        List<BdcDwJkCsDO> requestParamList = initBdcApiParamList(bdcDwJkCsDOList.stream().filter(x -> !x.getCslx().equals(3)).sorted(Comparator.comparing(BdcDwJkCsDO::getCscj)).collect(Collectors.toList()));

        // 1、创建document对象
        Document document = DocumentHelper.createDocument();
        // 2、创建根节点rss
        Element fetchdatas = document.addElement("fetchdatas");

        // 4、生成子节点及子节点内容
        Element datas = fetchdatas.addElement("datas");

        Element jkmc = datas.addElement("data");
        jkmc.addAttribute("name", "jkmc");
        jkmc.addAttribute("type", "String");
        jkmc.setText(StringUtils.isBlank(bdcDwJkDO.getJkmc()) ? "" : bdcDwJkDO.getJkmc());

        Element jkms = datas.addElement("data");
        jkms.addAttribute("name", "jkms");
        jkms.addAttribute("type", "String");
        jkms.setText(StringUtils.isBlank(bdcDwJkDO.getJkms()) ? "" : bdcDwJkDO.getJkms());

        Element qqsl = datas.addElement("data");
        qqsl.addAttribute("name", "qqsl");
        qqsl.addAttribute("type", "String");
        qqsl.setText(paramListConvertJson(requestBodyParamList));

        Element fhsl = datas.addElement("data");
        fhsl.addAttribute("name", "fhsl");
        fhsl.addAttribute("type", "String");
        fhsl.setText(paramListConvertJson(returnTypeParamList));

        Element jkqqfs = datas.addElement("data");
        jkqqfs.addAttribute("name", "jkqqfs");
        jkqqfs.addAttribute("type", "String");
        jkqqfs.setText(StringUtils.isBlank(bdcDwJkDO.getJkqqfs()) ? "" : bdcDwJkDO.getJkqqfs());

        Element jkdz = datas.addElement("data");
        jkdz.addAttribute("name", "jkdz");
        jkdz.addAttribute("type", "String");
        String requestUrl = StringUtils.isBlank(bdcDwJkDO.getJkdz()) ? "" : bdcDwJkDO.getJkdz();
        if (requestUrl.contains("?")) {
            requestUrl = requestUrl + "&apiId=" + jkid;
        } else {
            requestUrl = requestUrl + "?apiId=" + jkid;
        }
        requestUrl = requestUrl + "&access_token=";
        jkdz.setText(requestUrl);

        Element paramDetail = fetchdatas.addElement("detail");
        paramDetail.addAttribute("ID", "param");
        addElement(paramDetail, requestParamList);

        Element returnDetail = fetchdatas.addElement("detail");
        returnDetail.addAttribute("ID", "return");
        addElement(returnDetail, returnTypeParamList);

        // 5、设置生成xml的格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置编码格式
        format.setEncoding("UTF-8");
        StringWriter stringWriter = new StringWriter();
        String xmlData = "";
        try {
            XMLWriter writer = new XMLWriter(stringWriter, format);
            // 设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(document);
            xmlData = stringWriter.toString();
            writer.close();
            logger.info("------xmlData:{}", xmlData);
            map.put("xmlData", xmlData);
            return map;
        } catch (Exception e) {
            return map;
        }
    }

    @NotNull
    private List<BdcDwJkCsDO> initBdcApiParamList(List<BdcDwJkCsDO> requestBodyParamList) {
        List<BdcDwJkCsDO> tempList = new ArrayList<>();
        initRequestParamList(requestBodyParamList, tempList);
        requestBodyParamList.addAll(tempList);
        return requestBodyParamList;
    }

    private static final String SUCCESS = "success";
    private static final String WITHOUT_REQUIRED_PARAM = "withoutRequiredParam";
    private static final String FAIL_PARAM_TYPE = "failParamType";

    @Override
    public Object getSimpleOpenApiResult(BdcOpenApiTestDTO bdcOpenApiTestDTO) {
        try {
            if (StringUtils.isEmpty(bdcOpenApiTestDTO.getApiId())) {
                throw new AppException("apiId参数缺失!");
            }

            BdcDwJkDO param = new BdcDwJkDO();
            param.setJkid(bdcOpenApiTestDTO.getApiId());
            List<BdcDwJkDO> bdcDwJkDOS = bdcDwJkMapper.searchApiInfo(param);

            if (CollectionUtils.isEmpty(bdcDwJkDOS)) {
                throw new AppException("未查询到相关接口信息!");
            }

            if (bdcDwJkDOS.size() > 1) {
                throw new AppException("根据条件查询到多个接口信息，请核实接口信息!");
            }

            BdcDwJkDO bdcDwJkDO = bdcDwJkDOS.get(0);

            //权限校验，发布状态的接口做权限校验
            LogBO logBO = null;
            if (OpenApiReleaseStatus.PUBLISHED.getReleaseStatus().intValue() == bdcDwJkDO.getFbzt()) {
                String clientId = "";
                String consumer = "";
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                BdcDwJkXffdzgxDO clientIdBdcDwJkXffDo = null;
                BdcDwJkXffdzgxDO consumerBdcDwJkXffDo = null;
                if (authentication != null) {
                    JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(authentication));
                    if (jsonObject.getJSONObject("oAuth2Request") != null && org.apache.commons.lang3.StringUtils.isNoneBlank(jsonObject.getJSONObject("oAuth2Request").getString("clientId"))) {
                        logger.info("===>consumerInfo:{}", jsonObject.toJSONString());
                        clientId = jsonObject.getJSONObject("oAuth2Request").getString("clientId");
                        consumer = authentication.getName();
                        clientIdBdcDwJkXffDo = bdcDwJkXffdzgxMapper.searchDzGxByQxbs(jsonObject.getJSONObject("oAuth2Request").getString("clientId"));
                        if (StringUtils.isNotBlank(authentication.getName())) {
                            consumerBdcDwJkXffDo = bdcDwJkXffdzgxMapper.searchDzGxByQxbs(authentication.getName());
                        }
                    }
                } else {
                    throw new AppException("权限异常，请配置接口消费方对照或使用相应消费方的账号调用!");
                }
//                clientIdBdcDwJkXffDo = bdcDwJkXffdzgxMapper.searchDzGxByQxbs("ceshitest1");
//                consumerBdcDwJkXffDo = bdcDwJkXffdzgxMapper.searchDzGxByQxbs("jlf");
//                logger.info("===>principal:{}", JSON.toJSONString(bdcDwJkXffdzgxDO));
                //权限过滤处理
                if (StringUtils.isNotBlank(bdcDwJkDO.getJkxff())) {
                    List<String> jkxxfList = Arrays.asList(bdcDwJkDO.getJkxff().split(","));
                    if ((!Objects.isNull(clientIdBdcDwJkXffDo) && jkxxfList.contains(clientIdBdcDwJkXffDo.getXff())) || (!Objects.isNull(consumerBdcDwJkXffDo) && jkxxfList.contains(consumerBdcDwJkXffDo.getXff()))) {
                        if (!Objects.isNull(clientIdBdcDwJkXffDo)){
                            logBO = initLogBO(clientIdBdcDwJkXffDo.getXff(), consumer, "BDC", bdcDwJkDO.getJkmc(), bdcDwJkDO.getJkdz());
                        }else {
                            logBO = initLogBO(consumerBdcDwJkXffDo.getXff(), consumer, "BDC", bdcDwJkDO.getJkmc(), bdcDwJkDO.getJkdz());
                        }
                    } else {
                        throw new AppException("权限异常，请配置接口消费方对照或使用相应消费方的账号调用!");
                    }
                } else {
                    logBO = initLogBO(clientId, consumer, "BDC", bdcDwJkDO.getJkmc(), bdcDwJkDO.getJkdz());
                }
            }

            // 当前接口未发布
            if (Objects.nonNull(bdcOpenApiTestDTO.getFlag()) && bdcOpenApiTestDTO.getFlag() == 1) {
                if (Objects.isNull(bdcDwJkDO.getFbzt()) || bdcDwJkDO.getFbzt() == 0) {
                    throw new AppException("未查询到相关接口信息!");
                }
            }

            /**
             * 处理url中的rest参数
             */
            String originalRequestUrl = bdcDwJkDO.getJkdz();
            String realRequestUrl = bdcOpenApiTestDTO.getUrl();
            Map<String, String> restParamMap = handleRestParam(originalRequestUrl, realRequestUrl, true);
            String paramJsonStr = bdcOpenApiTestDTO.getParamJson();
            JSONObject paramJson = new JSONObject();
            if (StringUtils.isNotBlank(paramJsonStr)) {
                paramJson = JSON.parseObject(paramJsonStr);
            }
            for (Map.Entry<String, String> entry : restParamMap.entrySet()) {
                paramJson.put(entry.getKey(), entry.getValue());
            }

            /**
             * 处理url中的query参数
             */
            Map<String, String> queryParamMap = handleRestParam(originalRequestUrl, realRequestUrl, false);
            for (Map.Entry<String, String> entry : queryParamMap.entrySet()) {
                paramJson.put(entry.getKey(), entry.getValue());
            }
            //返回是否包装响应头
            Map<String, BdcOpenApiResponseHeadDTO> responseHeadMap = new HashMap<>(4);
            Map<String, BdcOpenApiResponseDetailDTO> responseDetailMap = new HashMap<>(4);
            if (StringUtils.isNotBlank(bdcDwJkDO.getResponseHead())) {
                List<BdcOpenApiResponseHeadDTO> bdcOpenApiResponseHeadDTOS = JSON.parseArray(bdcDwJkDO.getResponseHead(), BdcOpenApiResponseHeadDTO.class);
                responseHeadMap = bdcOpenApiResponseHeadDTOS.stream().collect(Collectors.toMap(BdcOpenApiResponseHeadDTO::getHeadParamCode, bdcOpenApiResponseHeadDTO -> bdcOpenApiResponseHeadDTO));
            }
            if (StringUtils.isNotBlank(bdcDwJkDO.getResponseDetail())){
                List<BdcOpenApiResponseDetailDTO> bdcOpenApiResponseDetailDTOS = JSON.parseArray(bdcDwJkDO.getResponseDetail(), BdcOpenApiResponseDetailDTO.class);
                responseDetailMap = bdcOpenApiResponseDetailDTOS.stream().collect(Collectors.toMap(BdcOpenApiResponseDetailDTO::getRespDetailCode, bdcOpenApiResponseDetailDTO -> bdcOpenApiResponseDetailDTO));
            }
            if (responseHeadMap.size() > 0) {
                if (MapUtils.isEmpty(paramJson)) {
                    JSONObject response = new JSONObject();
                    JSONObject head = new JSONObject();
                    head.put(responseHeadMap.get(WITHOUT_REQUIRED_PARAM).getHeadParamName(), responseHeadMap.get(WITHOUT_REQUIRED_PARAM).getHeadParamMessage());
                    response.put("head", head);
                    return response;
                }
            } else if(responseDetailMap.size() > 0){
                if (MapUtils.isEmpty(paramJson)) {
                    JSONObject response = new JSONObject();
                    response.put(responseDetailMap.get(WITHOUT_REQUIRED_PARAM).getRespCode(), responseDetailMap.get(WITHOUT_REQUIRED_PARAM).getRespCodeValue());
                    response.put(responseDetailMap.get(WITHOUT_REQUIRED_PARAM).getRespMsg(), responseDetailMap.get(WITHOUT_REQUIRED_PARAM).getRespMsgValue());
                    return response;
                }
            } else {
                if (MapUtils.isEmpty(paramJson)) {
                    throw new AppException("参数缺失!");
                }
            }

            if (StringUtils.isEmpty(bdcDwJkDO.getSjkjb())) {
                throw new AppException("该接口缺少数据库脚本!");
            }

            List<BdcDwJkCsDO> bdcDwJkCsDOList = bdcDwJkCsMapper.searchApiParamByApiId(bdcDwJkDO.getJkid());

            if (CollectionUtils.isEmpty(bdcDwJkCsDOList)) {
                throw new AppException("该接口未配置参数!");
            }

            List<BdcDwJkCsDO> requestParamList = bdcDwJkCsDOList.stream().filter(x -> !x.getCslx().equals(3)).collect(Collectors.toList());
            String standardRequestParamJson = paramListConvertJson(requestParamList);
            logger.info("===>接口标准入参:{}", standardRequestParamJson);
            String paramStr = toJSONString(paramJson, SerializerFeature.WriteMapNullValue);
            Map<String, Object> paramMap = new HashMap<>(16);
//            jsonConvertMap(JSON.parseObject(standardRequestParamJson), paramMap);
            jsonConvertMap(JSON.parseObject(paramStr), paramMap);
            logger.info("===>请求入参:{}", JSON.toJSONString(paramMap));

            //校验参数是否必填
            Object checkRequestParamReult = checkRequestParam(responseDetailMap,responseHeadMap, requestParamList, standardRequestParamJson, paramMap);
            if (checkRequestParamReult != null) {
                return checkRequestParamReult;
            }
            List<BdcDwJkCsDO> returnTypeParamList = bdcDwJkCsDOList.stream().filter(x -> x.getCslx().equals(3)).collect(Collectors.toList());
            String standardReturnTypeJson = paramListConvertJson(returnTypeParamList);
            logger.info("===>接口标准出参:{}", standardReturnTypeJson);
            JSONArray resultJsonArray = null;
            JSONObject resultJSONObject = null;
            if (bdcDwJkDO.getDataIsList() != null && bdcDwJkDO.getDataIsList() == 0){
                resultJSONObject = initOneResultBySql(bdcDwJkDO.getSjkjb(), bdcDwJkCsDOList, paramMap);
                logger.info("===>响应结果:{}", resultJSONObject);
                //发布状态的接口调用时保存日志信息
                if (OpenApiReleaseStatus.PUBLISHED.getReleaseStatus().intValue() == bdcDwJkDO.getFbzt()) {
                    saveLog(logBO, paramMap, resultJSONObject);
                }
            }else {
                resultJsonArray = initResultBySql(bdcDwJkDO.getSjkjb(), bdcDwJkCsDOList, paramMap);
                logger.info("===>响应结果:{}", resultJsonArray);
                //发布状态的接口调用时保存日志信息
                if (OpenApiReleaseStatus.PUBLISHED.getReleaseStatus().intValue() == bdcDwJkDO.getFbzt()) {
                    saveLog(logBO, paramMap, resultJsonArray);
                }
            }
            JSONObject response = new JSONObject();
            //默认值
            if (StringUtils.isNotBlank(bdcDwJkDO.getResponseBodyDefaultValue())) {
                List<BdcOpenApiDefaultParamDTO> responseBodydefaultValueJsonArray = JSON.parseArray(bdcDwJkDO.getResponseBodyDefaultValue(), BdcOpenApiDefaultParamDTO.class);
                responseBodydefaultValueJsonArray.forEach(bdcOpenApiParamDTO -> {
                    response.put(bdcOpenApiParamDTO.getFieldName(), bdcOpenApiParamDTO.getDefaultValue());
                });
            }
            //返回响应头封装
            if (responseHeadMap.size() > 0) {
                JSONObject head = new JSONObject();
                head.put(responseHeadMap.get(SUCCESS).getHeadParamName(), responseHeadMap.get(SUCCESS).getHeadParamMessage());
                response.put("data", resultJsonArray != null? resultJsonArray : resultJSONObject);
                response.put("head", head);
            } else if (responseDetailMap.size() > 0){
                response.put(responseDetailMap.get(SUCCESS).getRespCode(), responseDetailMap.get(SUCCESS).getRespCodeValue());
                response.put(responseDetailMap.get(SUCCESS).getRespMsg(), responseDetailMap.get(SUCCESS).getRespMsgValue());
                response.put("data", resultJsonArray != null? resultJsonArray : resultJSONObject);
                return response;
            } else {
                response.put("data", resultJsonArray != null? resultJsonArray : resultJSONObject);
                response.put("success",true);
            }
            return response;
        } catch (Exception e) {
            logger.info("测试简单接口异常:", e);
            return CommonResponse.fail(e.getMessage());
        }
    }

    @Nullable
    private Object checkRequestParam(Map<String, BdcOpenApiResponseDetailDTO> responseDetailMap,Map<String, BdcOpenApiResponseHeadDTO> responseHeadMap, List<BdcDwJkCsDO> requestParamList, String standardRequestParamJson, Map<String, Object> paramMap) {
        JSONObject paramJsonObject = JSON.parseObject(standardRequestParamJson);
        Map<String, BdcDwJkCsDO> collect = requestParamList.stream().collect(Collectors.toMap(BdcDwJkCsDO::getCsm, bdcDwJkCsDO -> bdcDwJkCsDO));
        boolean checkParamflag = true;
        boolean requiredParamFlag = true;
        for (String paramKey : collect.keySet()) {
            if (collect.get(paramKey).getSfbt() == 0) {
                if (collect.get(paramKey).getCszdlx().equals(OpenApiFieldType.LIST.getClassName())) {
                    if (StringUtils.isNotBlank(collect.get(paramKey).getJkcsext())) {
                        String csid = JSON.parseObject(collect.get(paramKey).getJkcsext()).getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey());
                        List<BdcDwJkCsDO> childParams = bdcDwJkCsMapper.searchApiParamByApiId(csid);
                        Optional<BdcDwJkCsDO> any = childParams.stream().filter(childParam ->
                                paramMap.containsKey(childParam.getCsm())
                        ).findAny();
                        if (!any.isPresent()) {
                            checkParamflag = false;
                            break;
                        }
                    }
                } else {
                    if (!paramMap.containsKey(paramKey) || paramMap.get(paramKey) == null) {
                        checkParamflag = false;
                        break;
                    }
                }
            }
            if (OpenApiFieldType.STRING.getClassName().equals(collect.get(paramKey).getCszdlx())) {
                if (!(paramJsonObject.get(paramKey) instanceof String)) {
                    requiredParamFlag = false;
                    break;
                }
            } else if (OpenApiFieldType.INTEGER.getClassName().equals(collect.get(paramKey).getCszdlx())) {
                if (!(paramJsonObject.get(paramKey) instanceof Integer)) {
                    requiredParamFlag = false;
                    break;
                }
            } else if (OpenApiFieldType.BOOLEAN.getClassName().equals(collect.get(paramKey).getCszdlx())) {
                if (!(paramJsonObject.get(paramKey) instanceof Boolean)) {
                    requiredParamFlag = false;
                    break;
                }
            } else if (OpenApiFieldType.DOUBLE.getClassName().equals(collect.get(paramKey).getCszdlx())) {
                if (!(paramJsonObject.get(paramKey) instanceof BigDecimal)) {
                    requiredParamFlag = false;
                    break;
                }
            } else if (OpenApiFieldType.LIST.getClassName().equals(collect.get(paramKey).getCszdlx())) {
                if (!(paramJsonObject.get(paramKey) instanceof List)) {
                    requiredParamFlag = false;
                    break;
                }
            } else if (OpenApiFieldType.DATE.getClassName().equals(collect.get(paramKey).getCszdlx())) {
                try {
                    paramJsonObject.getDate(paramKey);
                } catch (Exception e) {
                    requiredParamFlag = false;
                    break;
                }
            } else if (OpenApiFieldType.LIST.getClassName().equals(collect.get(paramKey).getCszdlx())) {
                try {
                    if (paramJsonObject.getString(paramKey).contains("[") && paramJsonObject.getString(paramKey).contains("]")) {
                        paramJsonObject.getJSONArray(paramKey);
                    } else {
                        requiredParamFlag = false;
                        break;
                    }
                } catch (Exception e) {
                    requiredParamFlag = false;
                    break;
                }
            }


        }
        if (!checkParamflag) {
            if (responseHeadMap.size() > 0) {
                JSONObject response = new JSONObject();
                JSONObject head = new JSONObject();
                head.put(responseHeadMap.get(WITHOUT_REQUIRED_PARAM).getHeadParamName(), responseHeadMap.get(WITHOUT_REQUIRED_PARAM).getHeadParamMessage());
                response.put("head", head);
                return response;
            } else if (responseDetailMap.size() > 0){
                JSONObject response = new JSONObject();
                response.put(responseDetailMap.get(WITHOUT_REQUIRED_PARAM).getRespCode(), responseDetailMap.get(WITHOUT_REQUIRED_PARAM).getRespCodeValue());
                response.put(responseDetailMap.get(WITHOUT_REQUIRED_PARAM).getRespMsg(), responseDetailMap.get(WITHOUT_REQUIRED_PARAM).getRespMsgValue());
                return response;
            } else {
                throw new AppException("参数缺失!");
            }
        }
        if (!requiredParamFlag && responseHeadMap.size() > 0) {
            JSONObject response = new JSONObject();
            JSONObject head = new JSONObject();
            head.put(responseHeadMap.get(FAIL_PARAM_TYPE).getHeadParamName(), responseHeadMap.get(FAIL_PARAM_TYPE).getHeadParamMessage());
            response.put("head", head);
            return response;
        } else if (!requiredParamFlag && responseDetailMap.size() > 0){
            JSONObject response = new JSONObject();
            response.put(responseDetailMap.get(FAIL_PARAM_TYPE).getRespCode(), responseDetailMap.get(FAIL_PARAM_TYPE).getRespCodeValue());
            response.put(responseDetailMap.get(FAIL_PARAM_TYPE).getRespMsg(), responseDetailMap.get(FAIL_PARAM_TYPE).getRespMsgValue());
            return response;
        } else if(!requiredParamFlag){
            throw new AppException("参数类型有误!");
        }
        return null;
    }

    /**
     * 组装返回参数
     *
     * @param sql
     * @param bdcDwJkCsDOList
     * @param paramMap
     * @return
     */
    private JSONArray initResultBySql(String sql, List<BdcDwJkCsDO> bdcDwJkCsDOList, Map<String, Object> paramMap) {
        //主sql执行
        String primarySql = compileSql(sql, paramMap, false, false);

        List<Map<String, Object>> primaryResutlt = new ArrayList<>();
        try {
            primaryResutlt = resultWithOneSql(primarySql);
        } catch (Exception e) {
            logger.info("转换结果报错:{}", primaryResutlt);
            throw new RuntimeException(e);
        }

        JSONArray resultJsonArray = new JSONArray();
        Map<String, BdcDwJkCsDO> bdcDwJkCsDOMap = bdcDwJkCsDOList.stream().filter(bdcDwJkCsDO -> bdcDwJkCsDO.getCslx() == 3).collect(Collectors.toMap(BdcDwJkCsDO::getCsm, bdcDwJkCsDO -> bdcDwJkCsDO));
        primaryResutlt.forEach(map -> {
            JSONObject jsonObject = initResultParam(paramMap, bdcDwJkCsDOMap, map);
            if (jsonObject != null){
                resultJsonArray.add(jsonObject);
            }
        });
        return resultJsonArray;
    }

    /**
     * 组装返回参数
     *
     * @param sql
     * @param bdcDwJkCsDOList
     * @param paramMap
     * @return
     */
    private JSONObject initOneResultBySql(String sql, List<BdcDwJkCsDO> bdcDwJkCsDOList, Map<String, Object> paramMap) {
        //主sql执行
        String primarySql = compileSql(sql, paramMap, false, false);

        List<Map<String, Object>> primaryResutlt = new ArrayList<>();
        try {
            primaryResutlt = resultWithOneSql(primarySql);
        } catch (Exception e) {
            logger.info("转换结果报错:{}", primaryResutlt);
            throw new RuntimeException(e);
        }
        Map<String, BdcDwJkCsDO> bdcDwJkCsDOMap = bdcDwJkCsDOList.stream().filter(bdcDwJkCsDO -> bdcDwJkCsDO.getCslx() == 3).collect(Collectors.toMap(BdcDwJkCsDO::getCsm, bdcDwJkCsDO -> bdcDwJkCsDO));
        if (CollectionUtils.isEmpty(primaryResutlt)){
            return null;
        }
       return initResultParam(paramMap, bdcDwJkCsDOMap, primaryResutlt.get(0));
    }

    private JSONObject initResultParam(Map<String, Object> paramMap, Map<String, BdcDwJkCsDO> bdcDwJkCsDOMap, Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        bdcDwJkCsDOMap.keySet().forEach(key -> {
            if (!map.containsKey(key.toLowerCase())) {
                //未匹配到相应的key时的处理
                if (StringUtils.isNotBlank(bdcDwJkCsDOMap.get(key).getJkcsext())) {
                    //当参数单独配置sql时处理
                    JSONObject interfaceParamExtInfo = JSON.parseObject(bdcDwJkCsDOMap.get(key).getJkcsext());
                    if (OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_GET_RESULT_WITH_ALONE_SQL.getValue().equals(interfaceParamExtInfo.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_GET_RESULT_WITH_ALONE_SQL.getKey()))) {
                        //单独配置sql处理
                        try {
                            if (OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_OBJECT_NO.getValue().equals(interfaceParamExtInfo.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_OBJECT_NO.getKey()))) {
                                //非复杂参数处理
                                //参数处理
                                Map<String, Object> tempParamMap = new HashMap<>();
                                tempParamMap.putAll(map);
                                paramMap.keySet().forEach(tempKey -> {
                                    if (!tempParamMap.containsKey(tempKey)) {
                                        tempParamMap.put(tempKey, paramMap.get(tempKey));
                                    }
                                });
                                List<Map<String, Object>> maps = resultWithOneSql(compileSql(interfaceParamExtInfo.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_SQL.getKey()), tempParamMap, false, false));
                                if (OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getValue().equals(interfaceParamExtInfo.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey()))) {
                                    ArrayList<Object> collect = maps.stream().collect(ArrayList::new,
                                            (list, item) -> {
                                                if (item.containsKey(key)) {
                                                    list.add(item.get(key));
                                                }
                                            },
                                            ArrayList::addAll);
                                    jsonObject.put(key, collect);
                                } else {
                                    if (CollectionUtils.isNotEmpty(maps) && maps.get(0).containsKey(key)) {
//                                            jsonObject.put(key, maps.get(0).get(key));
                                        replaceZdDm2Zdmc(jsonObject, key, maps.get(0).get(key), bdcDwJkCsDOMap.get(key));
                                    }
                                }
                            } else if (OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_OBJECT_YES.getValue().equals(interfaceParamExtInfo.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_OBJECT_YES.getKey()))) {
                                //复杂参数处理
                                Map<String, Object> tempParamMap = new HashMap<>();
                                tempParamMap.putAll(map);
                                paramMap.keySet().forEach(tempKey -> {
                                    if (!tempParamMap.containsKey(tempKey)) {
                                        tempParamMap.put(tempKey, paramMap.get(tempKey));
                                    }
                                });
                                List<BdcDwJkCsDO> tempBdcDwJkCsDOList = bdcDwJkCsMapper.searchApiParamByApiId(interfaceParamExtInfo.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()));
                                if (CollectionUtils.isNotEmpty(tempBdcDwJkCsDOList)) {
                                    if (OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getValue().equals(interfaceParamExtInfo.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey()))) {
                                        jsonObject.put(key, initResultBySql(interfaceParamExtInfo.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_SQL.getKey()), tempBdcDwJkCsDOList, tempParamMap));
                                    } else {
                                        jsonObject.put(key, initResultBySql(interfaceParamExtInfo.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_SQL.getKey()), tempBdcDwJkCsDOList, tempParamMap).size() > 0 ? initResultBySql(interfaceParamExtInfo.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_SQL.getKey()), tempBdcDwJkCsDOList, tempParamMap).get(0) : null);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        //复杂参数处理
                    } else {
                        //普通参数处理
                        replaceZdDm2Zdmc(jsonObject, key, map.get(key), bdcDwJkCsDOMap.get(key));
                    }
                } else if (StringUtils.isNotBlank(bdcDwJkCsDOMap.get(key).getCspj())) {
                    //拼接字符串处理
                    dealWithSplictParam(bdcDwJkCsDOMap, map, jsonObject, key);
                }
            } else {
                //普通参数处理
                if (StringUtils.isNotBlank(bdcDwJkCsDOMap.get(key).getCspj())) {
                    //拼接字符串处理
                    dealWithSplictParam(bdcDwJkCsDOMap, map, jsonObject, key);
                } else {
                    replaceZdDm2Zdmc(jsonObject, key, map.get(key.toLowerCase()), bdcDwJkCsDOMap.get(key));
                }
//                    jsonObject.put(key, map.get(key.toLowerCase()));
            }
        });
        return jsonObject;
    }

    private void dealWithSplictParam(Map<String, BdcDwJkCsDO> bdcDwJkCsDOMap, Map<String, Object> map, JSONObject jsonObject, String key) {
        if (StringUtils.isNotBlank(bdcDwJkCsDOMap.get(key).getCszdmc()) && bdcDwJkCsDOMap.get(key).getCszdmc().contains(",")) {
            String[] strings = bdcDwJkCsDOMap.get(key).getCszdmc().split(";;");
            for (String string : strings) {
                String[] split = string.split(",");
                List<Map> maps = bdcZdFeignService.queryBdcZd(split[1]);
                if (CollectionUtils.isNotEmpty(maps) && split.length > 2 && map.containsKey(split[0].toLowerCase()) && map.get(split[0].toLowerCase()) != null) {
                    for (int i = 0; i < maps.size(); i++) {
                        if (split[2].equals("dmzmc")) {
                            if (org.apache.commons.lang3.StringUtils.equals(map.get(split[0].toLowerCase()).toString(), MapUtils.getString(maps.get(i), "DM"))) {
                                map.put(split[0].toLowerCase(), MapUtils.getString(maps.get(i), "MC"));
                                break;
                            }
                        }
                        if (split[1].equals("mczdm")) {
                            if (org.apache.commons.lang3.StringUtils.equals(map.get(split[0].toLowerCase()).toString(), MapUtils.getString(maps.get(i), "MC"))) {
                                map.put(split[0].toLowerCase(), MapUtils.getString(maps.get(i), "DM"));
                                break;
                            }
                        }
                    }
                }
            }
        }
        String cspj = bdcDwJkCsDOMap.get(key).getCspj();
        String cspjResult = compileSql(cspj, map, false, true);
        //普通参数处理
        jsonObject.put(key, cspjResult);
    }

    private void replaceZdDm2Zdmc(JSONObject taget, String key, Object value, BdcDwJkCsDO bdcDwJkCsDO) {
        if (StringUtils.isNotBlank(bdcDwJkCsDO.getCszdmc()) && bdcDwJkCsDO.getCszdmc().contains(",")) {
            String[] strings = bdcDwJkCsDO.getCszdmc().split(",");
            List<Map> maps = bdcZdFeignService.queryBdcZd(strings[0]);
            if (CollectionUtils.isNotEmpty(maps) && strings.length >= 2) {
                for (Map map : maps) {
                    if (strings[1].equals("dmzmc")) {
                        if (org.apache.commons.lang3.StringUtils.equals(value.toString(), MapUtils.getString(map, "DM"))) {
                            taget.put(key, map.get("MC"));
                            return;
                        }
                    }
                    if (strings[1].equals("mczdm")) {
                        if (org.apache.commons.lang3.StringUtils.equals(value.toString(), MapUtils.getString(map, "MC"))) {
                            taget.put(key, map.get("DM"));
                            return;
                        }
                    }
                }
            }
        }
        taget.put(key, value);
    }

    @Override
    @Transactional(transactionManager = "serverTransactionManager", rollbackFor = Exception.class)
    public void updateApi(BdcOpenApiDTO bdcOpenApiDTO) {

        if (Objects.nonNull(bdcOpenApiDTO.getType()) && (bdcOpenApiDTO.getType() == 1 || bdcOpenApiDTO.getType() == 2)) {
            BdcDwJkDO bdcDwJkDO = new BdcDwJkDO();
            bdcDwJkDO.convertDTO(bdcOpenApiDTO);
            bdcDwJkDO.setJkdz(bdcOpenApiDTO.getUrl());
            bdcDwJkDO.setUpdatetime(new Date());
            logger.info("修改api参数:{}", bdcDwJkDO);
            bdcDwJkMapper.updateApiInfo(bdcDwJkDO);
            return;
        }

        /**
         * 获取apiId、生成url
         */
        String apiId = bdcOpenApiDTO.getId();
        String url = generateUrl(bdcOpenApiDTO.getUrl(), bdcOpenApiDTO.getParamList());

        /**
         * 校验
         */
        checkParam(bdcOpenApiDTO);
        checkUrlUniqueness(apiId, url);
        checkNameUniqueness(apiId, bdcOpenApiDTO.getName());

        /**
         * 修改接口基本信息
         */
        BdcDwJkDO bdcDwJkDO = new BdcDwJkDO();
        bdcDwJkDO.convertDTO(bdcOpenApiDTO);
        bdcDwJkDO.setJkdz(url);
        bdcDwJkDO.setUpdatetime(new Date());
        logger.info("修改api参数:{}", bdcDwJkDO);
        bdcDwJkMapper.updateApiInfo(bdcDwJkDO);

        /**
         * 修改参数信息
         */
        bdcDwJkCsMapper.deleteApiParam(bdcOpenApiDTO.getId());
        List<BdcOpenApiParamDTO> collect = bdcOpenApiDTO.getParamList().stream().filter(bdcOpenApiParamDTO -> StringUtils.isNotBlank(bdcOpenApiParamDTO.getChildParamId()) && CollectionUtils.isEmpty(bdcOpenApiParamDTO.getChildParamInfo())).collect(Collectors.toList());
        dealChildParam(collect, bdcOpenApiDTO.getParamList());
        List<BdcOpenApiParamDTO> collect1 = bdcOpenApiDTO.getParamList().stream().filter(bdcOpenApiParamDTO -> StringUtils.isNotBlank(bdcOpenApiParamDTO.getChildParamId()) && CollectionUtils.isNotEmpty(bdcOpenApiParamDTO.getChildParamInfo())).collect(Collectors.toList());
        dealChildParamWithOutInfo(collect1, bdcOpenApiDTO.getParamList());
        saveApiParam(apiId, bdcOpenApiDTO.getParamList());
    }

    private void dealChildParamWithOutInfo(List<BdcOpenApiParamDTO> collect, List<BdcOpenApiParamDTO> source) {
        if (CollectionUtils.isNotEmpty(collect)) {
            collect.forEach(bdcOpenApiParamDTO -> {
                List<BdcDwJkCsDO> bdcDwJkCsDOS = bdcDwJkCsMapper.searchApiParamByApiId(bdcOpenApiParamDTO.getChildParamId());
                source.forEach(bdcOpenApiParamDTO1 -> {
                    if (bdcOpenApiParamDTO1.getId() != null && bdcOpenApiParamDTO.getId() != null && bdcOpenApiParamDTO1.getId().equals(bdcOpenApiParamDTO.getId())) {
                        List<BdcOpenApiParamDTO> jkCsDOList = bdcOpenApiConvert.getBdcOpenApiParamDTOListByBdcDwJkCsDOList(bdcDwJkCsDOS);
                        List<BdcOpenApiParamDTO> collect1 = jkCsDOList.stream().filter(bdcOpenApiParamDTO2 -> StringUtils.isNotBlank(bdcOpenApiParamDTO2.getChildParamId()) && CollectionUtils.isNotEmpty(bdcOpenApiParamDTO2.getChildParamInfo())).collect(Collectors.toList());
                        dealChildParamWithOutInfo(collect1, jkCsDOList);
                    }
                });
                bdcDwJkCsMapper.deleteApiParam(bdcOpenApiParamDTO.getChildParamId());
            });
        }
    }

    private void dealChildParam(List<BdcOpenApiParamDTO> collect, List<BdcOpenApiParamDTO> source) {
        if (CollectionUtils.isNotEmpty(collect)) {
            collect.forEach(bdcOpenApiParamDTO -> {
                List<BdcDwJkCsDO> bdcDwJkCsDOS = bdcDwJkCsMapper.searchApiParamByApiId(bdcOpenApiParamDTO.getChildParamId());
                source.forEach(bdcOpenApiParamDTO1 -> {
                    if (bdcOpenApiParamDTO1.getId() != null && bdcOpenApiParamDTO.getId() != null && bdcOpenApiParamDTO1.getId().equals(bdcOpenApiParamDTO.getId())) {
                        List<BdcOpenApiParamDTO> jkCsDOList = bdcOpenApiConvert.getBdcOpenApiParamDTOListByBdcDwJkCsDOList(bdcDwJkCsDOS);
                        List<BdcOpenApiParamDTO> collect1 = jkCsDOList.stream().filter(bdcOpenApiParamDTO2 -> StringUtils.isNotBlank(bdcOpenApiParamDTO2.getChildParamId()) && CollectionUtils.isEmpty(bdcOpenApiParamDTO2.getChildParamInfo())).collect(Collectors.toList());
                        dealChildParam(collect1, jkCsDOList);
                        bdcOpenApiParamDTO1.setChildParamInfo(jkCsDOList);
                    }
                });
                bdcDwJkCsMapper.deleteApiParam(bdcOpenApiParamDTO.getChildParamId());
            });
        }
    }

    @Override
    public void updateReleaseStatus(BdcOpenApiDTO bdcOpenApiDTO) {
        bdcDwJkMapper.updateReleaseStatus(bdcOpenApiDTO);
    }

    @Override
    @Transactional(transactionManager = "serverTransactionManager", rollbackFor = Exception.class)
    public void deleteApi(BaseQO baseQO) {
        if (CollectionUtils.isEmpty(baseQO.getIds())) {
            return;
        }
        bdcDwJkMapper.batchDeleteApi(baseQO.getIds());
        List<String> extParamJkidList = new ArrayList<>(4);
        baseQO.getIds().forEach(id -> {
            int stopCount = 0;
            initDeleteParamIds(id, extParamJkidList, stopCount);
        });
        bdcDwJkCsMapper.batchDeleteApiParam(baseQO.getIds());
        if (CollectionUtils.isNotEmpty(extParamJkidList)) {
            bdcDwJkCsMapper.batchDeleteApiParam(extParamJkidList);
        }
    }

    private void initDeleteParamIds(String jkid, List<String> extParamJkidList, int stopCount) {
        if (stopCount++ <= 20) {
            List<BdcDwJkCsDO> bdcDwJkCsDOS = bdcDwJkCsMapper.searchApiParamByApiId(jkid);
            if (CollectionUtils.isNotEmpty(bdcDwJkCsDOS)) {
                int finalStopCount = stopCount;
                bdcDwJkCsDOS.forEach(bdcDwJkCsDO -> {
                    if (StringUtils.isNotBlank(bdcDwJkCsDO.getJkcsext()) && StringUtils.isNotBlank(JSON.parseObject(bdcDwJkCsDO.getJkcsext()).getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()))) {
                        extParamJkidList.add(JSON.parseObject(bdcDwJkCsDO.getJkcsext()).getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()));
                        initDeleteParamIds(JSON.parseObject(bdcDwJkCsDO.getJkcsext()).getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()), extParamJkidList, finalStopCount);
                    }
                });
            }
        }

    }

    @Override
    public String saveContrastRelation(BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO) {
        String id = UUIDGenerator.generate();
        BdcDwJkXffdzgxDO bdcDwJkXffdzgxDO = new BdcDwJkXffdzgxDO();
        bdcDwJkXffdzgxDO.setId(id);
        bdcDwJkXffdzgxDO.setXff(bdcOpenApiConsumerContrastRelationDTO.getConsumer());
        bdcDwJkXffdzgxDO.setQxbs(bdcOpenApiConsumerContrastRelationDTO.getPrincipal());
        try {
            bdcDwJkXffdzgxMapper.insertDzGx(bdcDwJkXffdzgxDO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            String message = e.getMessage();
            if (StringUtils.contains(message, "违反唯一约束条件")) {
                throw new AppException("当前应用id/用户名【" + bdcOpenApiConsumerContrastRelationDTO.getPrincipal() + "】已存在对照关系!");
            }
            throw new AppException(e.getMessage());
        }

        return id;
    }

    @Override
    public Page<BdcOpenApiConsumerContrastRelationDTO> listContrastRelation(Pageable pageable, String bdcOpenApiConsumerContrastRelationDTOStr) {
        Map<String, Object> paramMap = PageUtils.pageableSort(pageable, JSONObject.parseObject(bdcOpenApiConsumerContrastRelationDTOStr, HashMap.class));

        Page<BdcDwJkXffdzgxDO> result = repository.selectPaging("listPageContrastRelationByPage", paramMap, pageable);
        Page<BdcOpenApiConsumerContrastRelationDTO> dtoPage = result.map(new CustomConverter<BdcDwJkXffdzgxDO, BdcOpenApiConsumerContrastRelationDTO>() {
            @Override
            public BdcOpenApiConsumerContrastRelationDTO doConvert(BdcDwJkXffdzgxDO bdcDwJkXffdzgxDO) {
                BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO = new BdcOpenApiConsumerContrastRelationDTO();
                bdcOpenApiConsumerContrastRelationDTO.setId(bdcDwJkXffdzgxDO.getId());
                bdcOpenApiConsumerContrastRelationDTO.setConsumer(bdcDwJkXffdzgxDO.getXff());
                bdcOpenApiConsumerContrastRelationDTO.setPrincipal(bdcDwJkXffdzgxDO.getQxbs());
                return bdcOpenApiConsumerContrastRelationDTO;
            }
        });
        return dtoPage;
    }

    @Override
    public void updateContrastRelation(BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO) {
        BdcDwJkXffdzgxDO bdcDwJkXffdzgxDO = new BdcDwJkXffdzgxDO();
        bdcDwJkXffdzgxDO.setId(bdcOpenApiConsumerContrastRelationDTO.getId());
        bdcDwJkXffdzgxDO.setXff(bdcOpenApiConsumerContrastRelationDTO.getConsumer());
        bdcDwJkXffdzgxDO.setQxbs(bdcOpenApiConsumerContrastRelationDTO.getPrincipal());
        try {
            bdcDwJkXffdzgxMapper.updateDzGx(bdcDwJkXffdzgxDO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            String message = e.getMessage();
            if (StringUtils.contains(message, "违反唯一约束条件")) {
                throw new AppException("当前应用id/用户名【" + bdcOpenApiConsumerContrastRelationDTO.getPrincipal() + "】已存在对照关系!");
            }
            throw new AppException(e.getMessage());
        }
    }

    @Override
    public void deleteContrastRelation(BaseQO baseQO) {
        if (CollectionUtils.isNotEmpty(baseQO.getIds())) {
            bdcDwJkXffdzgxMapper.deleteDzGx(baseQO.getIds());
        }

    }

    /**
     * 保存接口参数
     *
     * @param apiId
     * @param paramDTOList
     */
    private void saveApiParam(String apiId, List<BdcOpenApiParamDTO> paramDTOList) {
        TreeNode<BdcOpenApiParamDTO> root = new TreeNode<BdcOpenApiParamDTO>(new BdcOpenApiParamDTO());
        List<BdcOpenApiParamDTO> firstList = paramDTOList.stream().filter(x -> StringUtils.isEmpty(x.getParentFieldName())).collect(Collectors.toList());
        firstList.forEach(bdcOpenApiParamDTO -> {
            bdcOpenApiParamDTO.setId(UUIDGenerator.generate());
            TreeNode<BdcOpenApiParamDTO> treeNode = root.addChildren(bdcOpenApiParamDTO);
            buildTree(treeNode, bdcOpenApiParamDTO, paramDTOList);
        });
        List<BdcDwJkCsDO> bdcDwJkCsDOList = new ArrayList<>();
        Map<String, List<BdcOpenApiParamDTO>> childBdcDwJkCsMap = new HashMap<>();
        for (TreeNode<BdcOpenApiParamDTO> node : root) {
            int level = node.getLevel();
            BdcOpenApiParamDTO bdcOpenApiParamDTO = node.data;
            if (StringUtils.isNotEmpty(bdcOpenApiParamDTO.getFieldName())) {
                BdcDwJkCsDO bdcDwJkCsDO = new BdcDwJkCsDO();
                bdcDwJkCsDO.convertDTO(bdcOpenApiParamDTO);
                bdcDwJkCsDO.setCscj(String.valueOf(level));
                bdcDwJkCsDO.setJkid(apiId);
                JSONObject jkcsExtJsonobject = new JSONObject();
                if (bdcOpenApiParamDTO.getParamType().equals(3) && StringUtils.isNotBlank(bdcOpenApiParamDTO.getSql())) {
                    jkcsExtJsonobject.put(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_SQL.getKey(), bdcOpenApiParamDTO.getSql());
                    jkcsExtJsonobject.put(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_GET_RESULT_WITH_ALONE_SQL.getKey(), OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_GET_RESULT_WITH_ALONE_SQL.getValue());
                    if (StringUtils.isNotBlank(bdcOpenApiParamDTO.getIsListFlag()) && bdcOpenApiParamDTO.getIsListFlag().equals("on")) {
                        jkcsExtJsonobject.put(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey(), OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getValue());
                    } else {
                        jkcsExtJsonobject.put(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_NO.getKey(), OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_NO.getValue());
                    }
                    if (StringUtils.isNotBlank(bdcOpenApiParamDTO.getChildParamId())) {
                        jkcsExtJsonobject.put(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey(), bdcOpenApiParamDTO.getChildParamId());
                        jkcsExtJsonobject.put(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_OBJECT_YES.getKey(), OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_OBJECT_YES.getValue());
                        childBdcDwJkCsMap.put(bdcOpenApiParamDTO.getChildParamId(), bdcOpenApiParamDTO.getChildParamInfo());
                    } else {
                        jkcsExtJsonobject.put(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_OBJECT_NO.getKey(), OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_OBJECT_NO.getValue());
                    }
                    bdcDwJkCsDO.setJkcsext(jkcsExtJsonobject.toJSONString());
                }
                if (bdcOpenApiParamDTO.getParamType().equals(0) && StringUtils.isNotBlank(bdcOpenApiParamDTO.getChildParamId())) {
                    List<BdcOpenApiParamDTO> requestChildParamInfoList = bdcOpenApiParamDTO.getChildParamInfo();
                    requestChildParamInfoList.forEach(requestChildParamInfo -> {
                        BdcDwJkCsDO requsetBdcDwJkCsDO = new BdcDwJkCsDO();
                        requsetBdcDwJkCsDO.convertDTO(requestChildParamInfo);
                        requsetBdcDwJkCsDO.setCscj(String.valueOf(2));
                        requsetBdcDwJkCsDO.setCsid(UUIDGenerator.generate());
                        requsetBdcDwJkCsDO.setJkid(bdcOpenApiParamDTO.getChildParamId());
                        JSONObject tempJkcsExtJsonobject = new JSONObject();
                        tempJkcsExtJsonobject.put(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey(), bdcOpenApiParamDTO.getChildParamId());
                        bdcDwJkCsDO.setJkcsext(tempJkcsExtJsonobject.toJSONString());
                        bdcDwJkCsDOList.add(requsetBdcDwJkCsDO);
                    });
                }

                bdcDwJkCsDOList.add(bdcDwJkCsDO);
            }
        }
        if (CollectionUtils.isNotEmpty(childBdcDwJkCsMap.keySet())) {
            childBdcDwJkCsMap.keySet().forEach(
                    key -> {
                        saveApiParam(key, childBdcDwJkCsMap.get(key));
                    }
            );
        }
        if (CollectionUtils.isNotEmpty(bdcDwJkCsDOList)) {
            bdcDwJkCsMapper.batchInsertApiParamInfo(bdcDwJkCsDOList);
        }
    }

    /**
     * 生成url
     *
     * @param url
     * @param paramDTOList
     * @return
     */
    private String generateUrl(String url, List<BdcOpenApiParamDTO> paramDTOList) {
        String shortUrl = StringUtils.isEmpty(url) ? OpenApiUrlUtils.generateShortUuid() : url;
        StringBuilder querySb = new StringBuilder();
        StringBuilder restSb = new StringBuilder();
        paramDTOList.forEach(paramDTO -> {
            // query参数
            if (StringUtils.equalsIgnoreCase("1", paramDTO.getParamType().toString())) {
                if (StringUtils.isNotEmpty(querySb.toString())) {
                    querySb.append("&");
                }
                querySb.append(paramDTO.getFieldName());
                querySb.append("=");
            }
            //rest参数
            if (StringUtils.equalsIgnoreCase("2", paramDTO.getParamType().toString())) {
                if (StringUtils.isNotEmpty(restSb.toString())) {
                    restSb.append("/");
                }
                restSb.append("{");
                restSb.append(paramDTO.getFieldName());
                restSb.append("}");
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append(BASE_URL).append(shortUrl);
        if (StringUtils.isNotEmpty(querySb.toString())) {
            sb.append("?").append(querySb.toString());
        }
        if (StringUtils.isNotEmpty(restSb.toString())) {
            sb.append("/").append(restSb.toString());
        }
        return sb.toString();
    }

    /**
     * 记录接口调用日志
     *
     * @param request
     * @param response
     */
    private void saveLog(LogBO logBO, Object request, Object response) {
        try {
            AuditEventBO auditEventBO = new AuditEventBO(logBO);
            auditEventBO.setRequest(toJSONString(request));
            auditEventBO.setResponse(toJSONString(response));
            auditEventBO.setSimpleInterface(true);
            buildLogService.saveAuditLog(auditEventBO, logBO.getBdcdz());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 组织LogBO
     *
     * @param gxbmbs
     * @param requester
     * @param responser
     * @param logEventName
     */
    private LogBO initLogBO(String gxbmbs, String requester, String responser, String logEventName, String url) {
        LogBO logBO = new LogBO();
        logBO.setRequester(requester);
        logBO.setResponser(responser);
        logBO.setDsfFlag(gxbmbs);
        logBO.setLogEventName(logEventName);
        logBO.setLogService("");
        logBO.setBdcdz(url);
        return logBO;
    }

    /**
     * 校验接口地址唯一性
     *
     * @param apiId
     * @param url
     */
    private void checkUrlUniqueness(String apiId, String url) {
        if (StringUtils.isEmpty(url)) {
            throw new AppException("接口地址不能为空!");
        }

        List<BdcDwJkDO> bdcDwJkDOS = bdcDwJkMapper.queryApiByUrl(url);
        if (CollectionUtils.isEmpty(bdcDwJkDOS)) {
            return;

        }
        List<String> apiIds = bdcDwJkDOS.stream().map(BdcDwJkDO::getJkid).distinct().collect(Collectors.toList());
        apiIds = apiIds.stream().filter(x -> !x.contains(apiId)).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(apiIds)) {
            throw new AppException("当前接口地址代码已存在!");
        }
    }

    /**
     * 校验接口名称唯一性
     *
     * @param apiId
     * @param name
     */
    private void checkNameUniqueness(String apiId, String name) {
        if (StringUtils.isEmpty(name)) {
            throw new AppException("接口名称不能为空!");
        }

        List<BdcDwJkDO> bdcDwJkDOS = bdcDwJkMapper.queryApiByName(name);
        if (CollectionUtils.isEmpty(bdcDwJkDOS)) {
            return;

        }
        List<String> apiIds = bdcDwJkDOS.stream().map(BdcDwJkDO::getJkid).distinct().collect(Collectors.toList());
        apiIds = apiIds.stream().filter(x -> !x.contains(apiId)).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(apiIds)) {
            throw new AppException("当前接口名称已存在!");
        }
    }

    /**
     * 校验接口参数和配置sql
     *
     * @param bdcOpenApiDTO
     */
    private void checkParam(BdcOpenApiDTO bdcOpenApiDTO) {
        String sqlStr = bdcOpenApiDTO.getSql();
        if (StringUtils.isBlank(sqlStr)) {
            throw new AppException("sql不能为空!");
        }

        List<BdcOpenApiParamDTO> paramList = bdcOpenApiDTO.getParamList();
        if (CollectionUtils.isEmpty(paramList)) {
            throw new AppException("参数不能为空!");
        }
        paramList.forEach(paramDTO -> {
            if (StringUtils.isEmpty(paramDTO.getFieldName())) {
                throw new AppException("参数字段名不能为空!");
            }
        });

        List<String> sqlList = new ArrayList<>();
        if (sqlStr.contains(SQL_SCRIPT_SEPARATOR)) {
            sqlList = Arrays.asList(sqlStr.split(SQL_SCRIPT_SEPARATOR));
        } else {
            sqlList.add(sqlStr);
        }

        /**
         * 校验请求体和返回参数父字段
         */
//        List<BdcOpenApiParamDTO> requestBodyParamList = paramList.stream().filter(x -> x.getParamType().equals(0)).collect(Collectors.toList());
//        if (CollectionUtils.isNotEmpty(requestBodyParamList)) {
//            List<String> requestBodyFieldList = paramList.stream().filter(x -> x.getParamType().equals(0)).map(BdcOpenApiParamDTO::getFieldName).collect(Collectors.toList());
//            requestBodyParamList.forEach(paramDTO -> {
//                if (StringUtils.isNotEmpty(paramDTO.getParentFieldName())) {
//                    if (CollectionUtils.isEmpty(requestBodyFieldList) || !requestBodyFieldList.contains(paramDTO.getParentFieldName())) {
//                        throw new AppException("请求体参数" + paramDTO.getFieldName() + "对应父字段不存在,请核实!");
//                    }
//                }
//            });
//        }
//        List<BdcOpenApiParamDTO> returnParamList = paramList.stream().filter(x -> x.getParamType().equals(3)).collect(Collectors.toList());
//        if (CollectionUtils.isNotEmpty(returnParamList)) {
//            List<String> returnParamFieldList = paramList.stream().filter(x -> x.getParamType().equals(3)).map(BdcOpenApiParamDTO::getFieldName).collect(Collectors.toList());
//            returnParamList.forEach(paramDTO -> {
//                if (StringUtils.isNotEmpty(paramDTO.getParentFieldName())) {
//                    if (CollectionUtils.isEmpty(returnParamFieldList) || !returnParamFieldList.contains(paramDTO.getParentFieldName())) {
//                        throw new AppException("返回参数" + paramDTO.getFieldName() + "对应父字段不存在,请核实!");
//                    }
//                }
//            });
//        }

        List<String> sqlParamList = new ArrayList<>();
        List<String> returnColumnList = new ArrayList<>();
        /**
         * 检验sql查询参数和请求参数是否一致、查询结果集合返回参数是否一致
         */
        for (String sql : sqlList) {
            logger.info("当前处理的sql======================>" + sql);
            /**
             * 1.sql中的查询参数与请求参数一致性
             */
            if (!StringUtils.containsIgnoreCase(sql, "#")) {
                throw new AppException("【" + sql + "】" + "当前sql查询条件不能为空!");
            }

            String sqlParamRegex = "#\\{(.*?)}";
            Pattern sqlParamPattern = Pattern.compile(sqlParamRegex);
            Matcher sqlParamMatcher = sqlParamPattern.matcher(sql);
            while (sqlParamMatcher.find()) {
                String sqlParam = sqlParamMatcher.group();
                String key = sqlParam.substring(2, sqlParam.length() - 1);
                sqlParamList.add(key);
            }

            /**
             * 2.sql中的查询列与响应参数一致性进行校验
             */
            if (StringUtils.startsWithIgnoreCase(sql, "select *")) {
                throw new AppException("sql中的查询列与响应参数不一致!");
            }
//            int startIndex = StringUtils.indexOfIgnoreCase(sql, "select") + 6;
//            int endIndex = StringUtils.indexOfIgnoreCase(sql, "from");
//            String returnColumn = sql.substring(startIndex, endIndex);
            returnColumnList.add(sql);
//            for (String columnName : returnColumn.split(",")) {
//                if (columnName.contains(".")) {
//                    columnName = columnName.substring(columnName.indexOf(".") + 1);
//                }
//                if (StringUtils.containsIgnoreCase(columnName, "as")) {
//                    columnName = columnName.substring(StringUtils.indexOfIgnoreCase(columnName, "as") + 2);
//                }
//                returnColumnList.add(columnName.trim());
//            }
        }

        /**
         * 参数校验
         */
        List<String> requestParamFieldList = paramList.stream().filter(x -> !x.getParamType().equals(3) && !OpenApiFieldType.LIST.getClassName().equals(x.getFieldType())).map(BdcOpenApiParamDTO::getFieldName).collect(Collectors.toList());
        List<String> paramSubtraction = compareList(requestParamFieldList, sqlParamList);
        if (CollectionUtils.isNotEmpty(paramSubtraction)) {
            String missParam = String.join(",", paramSubtraction);
            throw new AppException("【" + missParam + "】" + "请求参数与sql查询参数不一致!");
        }

        /**
         * 响应校验
         */
        List<String> returnParamFieldList = paramList.stream().filter(x -> x.getParamType().equals(3) && StringUtils.isBlank(x.getChildParamId())).map(BdcOpenApiParamDTO::getFieldName).collect(Collectors.toList());
        List<String> returnSubtraction = compareParamWithSql(returnColumnList, returnParamFieldList);
        if (CollectionUtils.isNotEmpty(returnSubtraction)) {
            String missParam = String.join(",", returnSubtraction);
            throw new AppException("【" + missParam + "】" + "响应列和响应参数不一致!");
        }

        /**
         * sql合法性进行校验
         */
        try {
            Map<String, Object> paramMap = new HashMap<>();
            resultSet(compileSqlList(sqlStr, paramMap));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            String errorMessage = e.getMessage();
            errorMessage = errorMessage.toLowerCase();
            if (StringUtils.containsIgnoreCase(errorMessage, "标识符无效")) {
                errorMessage = StringUtils.substring(errorMessage, errorMessage.indexOf(":") + 1, errorMessage.length());
                throw new AppException("sql字段名与数据库不符," + errorMessage);
            }
            if (StringUtils.containsIgnoreCase(errorMessage, "表或视图不存在")) {
                errorMessage = StringUtils.substring(errorMessage, errorMessage.indexOf(":") + 1, errorMessage.length());
                throw new AppException(errorMessage);
            }
            if (StringUtils.containsIgnoreCase(errorMessage, "命令未正确结束")) {
                errorMessage = StringUtils.substring(errorMessage, errorMessage.indexOf(":") + 1, errorMessage.length());
                throw new AppException("sql语法异常," + errorMessage);
            }
            throw new AppException(errorMessage);
        }
    }

    /**
     * 比较sql与参数列表
     *
     * @param list1
     * @param list2
     * @return
     */
    private List<String> compareParamWithSql(List<String> list1, List<String> list2) {
        List<String> differentParam = new ArrayList<>();
        list1.forEach(sqlParamStr -> {
            list2.forEach(paramKey -> {
                if (!sqlParamStr.contains(paramKey)) {
                    differentParam.add(paramKey);
                }
            });
        });
        return differentParam;
    }

    /**
     * 比较两个集合不同的元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private List<String> compareList(List<String> list1, List<String> list2) {
        if (CollectionUtils.isEmpty(list1)) {
            return null;
        }
        if (CollectionUtils.isEmpty(list2)) {
            return list1;
        }
        List<String> tempList = new ArrayList<>();
        list1.forEach(requestParam -> {
            if (!list2.contains(requestParam)) {
                tempList.add(requestParam);
            }
        });
        return tempList;
    }

    /**
     * json转map,复杂json一律key-value形式
     *
     * @param source
     * @param map
     */
    private void jsonConvertMap(JSONObject source, Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List) {
                JSONArray jsonArray = (JSONArray) value;
                Object object = jsonArray.size() > 0 ? (Object) jsonArray.get(0) : null;
                if (object instanceof JSONObject) {
                    jsonArray.forEach(jsonObject -> {
                        jsonConvertMap(JSON.parseObject(jsonObject.toString()), map);
                    });
                } else {
                    map.put(key, value);
                }
            } else if (value instanceof Map) {
                jsonConvertMap(JSON.parseObject(value.toString()), map);
            } else {
                if (map.containsKey(key)) {
                    if (map.get(key) instanceof List) {
                        ((List) map.get(key)).add(value);
                    } else {
                        List list = new ArrayList();
                        list.add(map.get(key));
                        list.add(value);
                        map.put(key, list);
                    }
                } else {
                    map.put(key, value);
                }
            }
        }
    }

    /**
     * 根据接口标准出参构建返回数据
     *
     * @param parentKey
     * @param template
     * @param result
     * @param dataList
     */
    private void buildJsonResult(String parentKey, JSONObject template, JSONObject result, List<Map<String, Object>> dataList) {
        for (Map.Entry<String, Object> entry : template.entrySet()) {
            JSONObject jsonObject = new JSONObject();

            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                parentKey = entry.getKey();
                template = JSONObject.parseObject(value.toString());
                buildJsonResult(parentKey, template, result, dataList);
            } else if (value instanceof List) {
                JSONArray jsonArrayTemplate = (JSONArray) value;
                JSONObject childrenTemplate = jsonArrayTemplate.size() > 0 ? (JSONObject) jsonArrayTemplate.get(0) : null;
                JSONArray jsonArray = new JSONArray();

                dataList.forEach(data -> {
                    JSONObject childrenJsonObject = new JSONObject();
                    if (Objects.isNull(childrenTemplate)) {
                        jsonArray.add(getValueByKey(key, dataList));
                    } else {
                        for (Map.Entry<String, Object> childEntry : childrenTemplate.entrySet()) {
                            Object newValue = getValueByKey(childEntry.getKey(), dataList);
                            childrenJsonObject.put(childEntry.getKey(), newValue);
                        }
                        jsonArray.add(childrenJsonObject);
                    }
                });
                jsonObject.put(key, jsonArray);
            } else {
                Object newValue = getValueByKey(key, dataList);
                jsonObject.put(key, newValue);
            }

            if (result.containsKey(parentKey)) {
                jsonObject.putAll(result.getJSONObject(parentKey));
            }

            if (StringUtils.isEmpty(parentKey)) {
                result.put(key, jsonObject.get(key));
            } else {
                result.put(parentKey, jsonObject);
            }
        }
    }

    /**
     * 根据key从结果集中获取数据
     *
     * @param key
     * @param resultSet
     * @return
     */
    private Object getValueByKey(String key, List<Map<String, Object>> resultSet) {
        Map<String, Object> map = new HashMap<>(16);
        resultSet.forEach(dataMap -> {
            if (dataMap.containsKey(key)) {
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + "," + dataMap.get(key));
                } else {
                    map.put(key, dataMap.get(key));
                }
            } else {
                map.put(key, null);
            }
        });
        return map.get(key);
    }

    /**
     * 处理rest参数 从请求url中获取值
     *
     * @param originalRequestUrl
     * @param realRequestUrl
     * @return
     */
    private Map<String, String> handleRestParam(String originalRequestUrl, String realRequestUrl, boolean isRestParam) {
        Map<String, String> restParamMap = new HashMap<>(16);
        if (StringUtils.equalsIgnoreCase(originalRequestUrl, realRequestUrl)) {
            return restParamMap;
        }
        if (isRestParam) {
            String[] originalRequestUrlArr = originalRequestUrl.split("/");
            String[] realRequestUrlArr = realRequestUrl.split("/");
            String regex = "\\{(.*?)}";
            Pattern pattern = Pattern.compile(regex);
            for (int i = 0; i < originalRequestUrlArr.length; i++) {
                if (originalRequestUrlArr[i].contains("?")) {
                    continue;
                }
                Matcher matcher = pattern.matcher(originalRequestUrlArr[i]);
                if (matcher.find()) {
                    String restParam = matcher.group();
                    String key = restParam.substring(1, restParam.length() - 1);
                    if (i > realRequestUrlArr.length) {
                        throw new AppException("404,未查询到相关接口,rest参数:" + key + "缺失!");
                    }
                    restParamMap.put(key, realRequestUrlArr[i]);
                }
            }
            return restParamMap;
        } else {
            if (realRequestUrl.contains("?") && originalRequestUrl.contains("?")) {
                MultiMap multiMap = new MultiMap();
                UrlEncoded.decodeTo(realRequestUrl.substring(realRequestUrl.indexOf("?") + 1), multiMap, "UTF-8");
                if (!multiMap.isEmpty()) {
                    multiMap.keySet().forEach(key -> {
                        restParamMap.put((String) key, multiMap.getString(key));
                    });
                }
            }
            return restParamMap;
        }

    }

    private List<String> compileSqlList(String sql, Map<String, Object> dataMap) {
        List<String> sqlList = new ArrayList<>();
        String compileSql = compileSql(sql, dataMap, true, false);
        if (compileSql.contains(SQL_SCRIPT_SEPARATOR)) {
            sqlList = Arrays.asList(compileSql.split(SQL_SCRIPT_SEPARATOR));
        } else {
            sqlList.add(compileSql);
        }
        return sqlList;
    }

    /**
     * 编译sql 将参数添加到sql中
     *
     * @param sql
     * @return
     * @throws Exception
     */
    private String compileSql(String sql, Map<String, Object> dataMap, boolean isCheckSql, boolean isSplitParam) {
//        List<String> sqlList = new ArrayList<>();
        String regex = "#\\{(.*?)}";
        String unrequire = "<unrequire>(.*?)</unrequire>";
        Pattern unrequirePattern = Pattern.compile(unrequire);
        Matcher unrequireMatcher = unrequirePattern.matcher(sql);
        while (unrequireMatcher.find()){
            String unrequireSqlParam = unrequireMatcher.group();
            Pattern keyPattern = Pattern.compile(regex);
            Matcher keyMatcher = keyPattern.matcher(unrequireSqlParam);
            if (keyMatcher.find()){
                String keyParam = keyMatcher.group();
                String key = keyParam.substring(2, keyParam.length() - 1);
                Object object = dataMap.get(key);
                if (Objects.isNull(object) && !isCheckSql) {
                    sql = sql.replace(unrequireSqlParam, "");
                }else if (object instanceof String && StringUtils.isBlank((String) object)){
                    sql = sql.replace(unrequireSqlParam, "");
                }
                else {
                    String target = unrequireSqlParam.replace("<unrequire>","").replace("</unrequire>","");
                    sql = sql.replace(unrequireSqlParam,target);
                }
            }
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String sqlParam = matcher.group();
            String key = sqlParam.substring(2, sqlParam.length() - 1);

            Object object = dataMap.get(key);

            if (Objects.nonNull(object)) {
                StringBuffer sb = new StringBuffer();
                if (object instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) object;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        if (StringUtils.isNotEmpty(sb.toString())) {
                            sb.append(",");
                        }
                        if (isSplitParam) {
                            sb.append(jsonArray.get(i));
                        } else {
                            sb.append("'");
                            sb.append(jsonArray.get(i));
                            sb.append("'");
                        }
                    }
                } else if (object instanceof List) {
                    ((List) object).forEach(o -> {
                        if (StringUtils.isNotEmpty(sb.toString())) {
                            sb.append(",");
                        }
                        if (isSplitParam) {
                            sb.append(o);
                        } else {
                            sb.append("'");
                            sb.append(o);
                            sb.append("'");
                        }
                    });
                } else {
                    if (isSplitParam) {
                        sb.append(object.toString());
                    } else {
                        sb.append("'");
                        sb.append(object.toString());
                        sb.append("'");
                    }
                }

                sql = sql.replace(sqlParam, sb.toString());
            } else if (isCheckSql) {
                sql = sql.replace(sqlParam, "''");
            } else {
                sql = sql.replace(sqlParam, "null");
            }
        }
        return sql;
    }

    /**
     * 执行sql获取结果集
     *
     * @return
     * @throws Exception
     */
    private Map<String, List<Map<String, Object>>> resultForListSql(Map<String, String> sqlMap) throws Exception {
        Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
        List<Map<String, Object>> dataSourceDataList = new ArrayList<>();
        DruidDataSource druidDataSource = applicationContext.getBean(DruidDataSource.class);
        Connection connection = druidDataSource.getConnection();
        for (String sqlKey : sqlMap.keySet()) {
            logger.info("===>sql:{}", sqlMap.get(sqlKey));
            PreparedStatement preparedStatement = connection.prepareStatement(sqlMap.get(sqlKey));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getResultSet();
            // 获取键名
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            // 获取行的数量
            int columnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> dataSourceDataMap = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    //获取键名及值
                    // 获得指定列的数据类型名
                    String columnTypeName = resultSetMetaData.getColumnTypeName(i);
                    // 对应数据类型的类
                    String columnClassName = resultSetMetaData.getColumnClassName(i);
                    String key = resultSetMetaData.getColumnName(i);
                    if (columnTypeName.equalsIgnoreCase("date")) {
                        dataSourceDataMap.put(key.toLowerCase(), resultSet.getDate(i));
                    } else {
                        dataSourceDataMap.put(key.toLowerCase(), resultSet.getObject(i));
                    }
                }
                dataSourceDataList.add(dataSourceDataMap);
            }
            preparedStatement.close();
            resultMap.put(sqlKey, dataSourceDataList);
        }
        logger.info("===>resultSet:{}", JSON.toJSONString(resultMap));
        // 释放资源
        if (Objects.nonNull(connection)) {
            connection.close();
        }
        return resultMap;
    }

    /**
     * 执行sql获取结果集
     *
     * @param sqlList
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> resultSet(List<String> sqlList) throws Exception {
        List<Map<String, Object>> dataSourceDataList = new ArrayList<>();
        DruidDataSource druidDataSource = applicationContext.getBean(DruidDataSource.class);
        Connection connection = druidDataSource.getConnection();
        for (String sql : sqlList) {
            logger.info("===>sql:{}", sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getResultSet();
            // 获取键名
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            // 获取行的数量
            int columnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> dataSourceDataMap = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    //获取键名及值
                    // 获得指定列的数据类型名
                    String columnTypeName = resultSetMetaData.getColumnTypeName(i);
                    // 对应数据类型的类
                    String columnClassName = resultSetMetaData.getColumnClassName(i);
                    String key = resultSetMetaData.getColumnName(i);
                    if (columnTypeName.equalsIgnoreCase("date")) {
                        dataSourceDataMap.put(key.toLowerCase(), resultSet.getDate(i));
                    } else {
                        dataSourceDataMap.put(key.toLowerCase(), resultSet.getObject(i));
                    }
                }
                dataSourceDataList.add(dataSourceDataMap);
            }
            preparedStatement.close();
        }
        logger.info("===>resultSet:{}", JSON.toJSONString(dataSourceDataList));
        // 释放资源
        if (Objects.nonNull(connection)) {
            connection.close();
        }
        return dataSourceDataList;
    }

    /**
     * 执行sql获取结果集
     *
     * @param primarySql
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> resultWithOneSql(String primarySql) throws Exception {
        List<Map<String, Object>> dataSourceDataList = new ArrayList<>();
        DruidDataSource druidDataSource = applicationContext.getBean(DruidDataSource.class);
        Connection connection = druidDataSource.getConnection();
        logger.info("===>sql:{}", primarySql);
        PreparedStatement preparedStatement = connection.prepareStatement(primarySql);
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getResultSet();

        // 获取键名
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        // 获取行的数量
        int columnCount = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> dataSourceDataMap = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                //获取键名及值
                // 获得指定列的数据类型名
                String columnTypeName = resultSetMetaData.getColumnTypeName(i);
                // 对应数据类型的类
                String columnClassName = resultSetMetaData.getColumnClassName(i);
                String key = resultSetMetaData.getColumnName(i);
                if (columnTypeName.equalsIgnoreCase("date")) {
                    dataSourceDataMap.put(key.toLowerCase(), resultSet.getDate(i));
                } else {
                    dataSourceDataMap.put(key.toLowerCase(), resultSet.getObject(i));
                }
            }
            dataSourceDataList.add(dataSourceDataMap);
        }
        preparedStatement.close();
        logger.info("===>resultSet:{}", JSON.toJSONString(dataSourceDataList));
        // 释放资源
        if (Objects.nonNull(connection)) {
            connection.close();
        }
        return dataSourceDataList;
    }

    /**
     * 根据请求参数生成相应的json
     * 1.虚拟一个根节点，将请求参数以此生成树形结构
     * 2.遍历树形结构将其转换为json字符串
     *
     * @param bdcDwJkCsDOS
     * @return
     */
    private String paramListConvertJson(List<BdcDwJkCsDO> bdcDwJkCsDOS) {
        if (CollectionUtils.isEmpty(bdcDwJkCsDOS)) {
            return "";
        }
        BdcDwJkCsDO virtualRoot = new BdcDwJkCsDO();
        virtualRoot.setCsm("root");
        TreeNode<BdcDwJkCsDO> root = new TreeNode<BdcDwJkCsDO>(virtualRoot);
        List<BdcDwJkCsDO> firstList = bdcDwJkCsDOS.stream().filter(x -> StringUtils.isNotBlank(x.getJkcsext())).collect(Collectors.toList());
        if (firstList.size() == 0) {
            firstList = bdcDwJkCsDOS;
        }
        firstList.forEach(bdcDwJkCsPO -> {
            TreeNode<BdcDwJkCsDO> treeNode = root.addChildren(bdcDwJkCsPO);
            buildTreeV2(treeNode, bdcDwJkCsPO, bdcDwJkCsDOS);
        });
        JSONObject jsonObject = new JSONObject();
        buildJson(root, jsonObject);
        return JSON.toJSONString(jsonObject.getJSONObject("root"), SerializerFeature.WriteMapNullValue);
    }

    /**
     * 将参数转换为json字符串
     *
     * @param node
     * @param jsonObject
     */
    private void buildJson(TreeNode<BdcDwJkCsDO> node, JSONObject jsonObject) {
        JSONObject childrenJson = new JSONObject();
        node.children.forEach(bdcDwJkCsDOTreeNode -> {
            if (OpenApiFieldType.STRING.getClassName().equals(bdcDwJkCsDOTreeNode.data.getCszdlx())) {
                String value = "";
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), value);
            } else if (OpenApiFieldType.INTEGER.getClassName().equals(bdcDwJkCsDOTreeNode.data.getCszdlx())) {
                Integer value = 0;
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), value);
            } else if (OpenApiFieldType.BOOLEAN.getClassName().equals(bdcDwJkCsDOTreeNode.data.getCszdlx())) {
                Boolean value = Boolean.FALSE;
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), value);
            } else if (OpenApiFieldType.DOUBLE.getClassName().equals(bdcDwJkCsDOTreeNode.data.getCszdlx())) {
                Double value = (double) 0;
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), value);
            } else if (OpenApiFieldType.FLOAT.getClassName().equals(bdcDwJkCsDOTreeNode.data.getCszdlx())) {
                Float value = (float) 0;
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), value);
            } else if (OpenApiFieldType.LIST.getClassName().equals(bdcDwJkCsDOTreeNode.data.getCszdlx())) {
                List value = Lists.newArrayList();
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), value);
            } else if (OpenApiFieldType.MAP.getClassName().equals(bdcDwJkCsDOTreeNode.data.getCszdlx())) {
                Map value = new HashMap(16);
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), value);
            } else if (OpenApiFieldType.ARRAY.getClassName().equals(bdcDwJkCsDOTreeNode.data.getCszdlx())) {
                List value = Lists.newArrayList();
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), value);
            } else if (OpenApiFieldType.OBJECT.getClassName().equals(bdcDwJkCsDOTreeNode.data.getCszdlx())) {
                Object value = new Object();
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), value);
            } else if (OpenApiFieldType.DATE.getClassName().equals(bdcDwJkCsDOTreeNode.data.getCszdlx())) {
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), null);
            } else {
                Object value = new Object();
                childrenJson.put(bdcDwJkCsDOTreeNode.data.getCsm(), value);
            }

            if (OpenApiFieldType.LIST.getClassName().equals(node.data.getCszdlx())) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(childrenJson);
                jsonObject.put(node.data.getCsm(), jsonArray);
            } else {
                jsonObject.put(node.data.getCsm(), childrenJson);
            }

            buildJson(bdcDwJkCsDOTreeNode, childrenJson);
        });
    }

    private void addElement(Element deatil, List<BdcDwJkCsDO> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(bdcDwJkCsDO -> {
                Element row = deatil.addElement("row");
                row.addAttribute("ID", UUIDGenerator.generate16());

                Element csms = row.addElement("data");
                csms.addAttribute("name", "csms");
                csms.addAttribute("type", "String");
                csms.setText(StringUtils.isBlank(bdcDwJkCsDO.getCssm()) ? "" : bdcDwJkCsDO.getCssm());

                Element csm = row.addElement("data");
                csm.addAttribute("name", "csm");
                csm.addAttribute("type", "String");
                csm.setText(StringUtils.isBlank(bdcDwJkCsDO.getCsm()) ? "" : bdcDwJkCsDO.getCsm());

                Element cszdlx = row.addElement("data");
                cszdlx.addAttribute("name", "cszdlx");
                cszdlx.addAttribute("type", "String");

                if (StringUtils.isNotBlank(bdcDwJkCsDO.getCszdlx())) {
                    String cszdlxStr = bdcDwJkCsDO.getCszdlx();
                    int start = cszdlxStr.lastIndexOf(".");
                    int end = cszdlxStr.length();
                    String className = cszdlxStr.substring(start + 1, end);
                    cszdlx.setText(className);
                } else {
                    cszdlx.setText("");
                }

                Element sfbt = row.addElement("data");
                sfbt.addAttribute("name", "sfbt");
                sfbt.addAttribute("type", "String");
                boolean requiredFlag = null != bdcDwJkCsDO.getSfbt() && 0 == bdcDwJkCsDO.getSfbt();
                sfbt.setText(requiredFlag ? "是" : "否");

                Element csmrz = row.addElement("data");
                csmrz.addAttribute("name", "csmrz");
                csmrz.addAttribute("type", "String");
                csmrz.setText(StringUtils.isBlank(bdcDwJkCsDO.getCsmrz()) ? "" : bdcDwJkCsDO.getCsmrz());
            });
        }
    }

    private void buildTree(TreeNode<BdcOpenApiParamDTO> root, BdcOpenApiParamDTO parentTreeNode, List<BdcOpenApiParamDTO> childrenTreeNodes) {
        List<BdcOpenApiParamDTO> paramDTOList = childrenTreeNodes.stream().filter(x -> StringUtils.equals(parentTreeNode.getFieldName(), x.getParentFieldName())).collect(Collectors.toList());
        for (BdcOpenApiParamDTO bdcOpenApiParamDTO : paramDTOList) {
            bdcOpenApiParamDTO.setId(UUIDGenerator.generate());
            bdcOpenApiParamDTO.setParentId(parentTreeNode.getId());
            TreeNode<BdcOpenApiParamDTO> treeNode1 = root.addChildren(bdcOpenApiParamDTO);
            buildTree(treeNode1, bdcOpenApiParamDTO, childrenTreeNodes);
        }
    }

    private void buildTreeV2(TreeNode<BdcDwJkCsDO> root, BdcDwJkCsDO parentTreeNode, List<BdcDwJkCsDO> childrenTreeNodes) {
        List<BdcDwJkCsDO> bdcDwJkCsPOList = childrenTreeNodes.stream().filter(x -> {
                    if (StringUtils.isBlank(x.getJkcsext()) && StringUtils.isNotBlank(parentTreeNode.getJkcsext())) {
                        JSONObject jsonObject = JSON.parseObject(parentTreeNode.getJkcsext());
                        return jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()).equals(x.getJkid());
                    }
                    return false;
                }
        ).collect(Collectors.toList());
        for (BdcDwJkCsDO bdcDwJkCsPO : bdcDwJkCsPOList) {
            TreeNode<BdcDwJkCsDO> treeNode = root.addChildren(bdcDwJkCsPO);
            buildTreeV2(treeNode, bdcDwJkCsPO, childrenTreeNodes);
        }
    }

    @Override
    @Transactional(transactionManager = "serverTransactionManager", rollbackFor = Exception.class)
    public CommonResponse initApiInfo() {
        try {
            bdcDwJkMapper.deleteApiWithoutUpdate();
            logger.info("start init api");
            List<BdcDwJkDO> bdcDwJkDOS = new ArrayList<>();
            bdcDwJkDOS.addAll(getApiFromXml());
            bdcDwJkDOS.addAll(getApiFromSwagger());
            logger.info("api count:{}", bdcDwJkDOS.size());
//            List<BdcDwJkDO> param = new ArrayList<>();
            bdcDwJkMapper.batchInsertApiInfoMerge(bdcDwJkDOS);
            return CommonResponse.ok();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonResponse.fail("");
        }
    }

    /**
     * 导入接口配置
     *
     * @param bdcOpenApiDTOList
     * @param updateFlag
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @Transactional(transactionManager = "serverTransactionManager", rollbackFor = Exception.class)
    public CommonResponse importInterface(List<BdcOpenApiDTO> bdcOpenApiDTOList, boolean updateFlag) {
        logger.info("导入接口配置开始，是否更新标识:{}", updateFlag);
        Map<String, Object> respMap = new HashMap<>(4);
        if (CollectionUtils.isEmpty(bdcOpenApiDTOList)){
            respMap.put("error", "缺少必填参数");
            return CommonResponse.fail(respMap);
        }
        try {
            Map<String, BdcOpenApiDTO> collect = bdcOpenApiDTOList.stream().collect(Collectors.toMap(BdcOpenApiDTO::getId, bdcOpenApiDTO -> bdcOpenApiDTO));
            Set<String> jkIds = collect.keySet();
            List<String> jkids2Update = bdcDwJkMapper.listSearchApiIds(jkIds);
            Map<String, Object> addErrorJkidMap = new HashMap<>(jkIds.size());
            if (updateFlag) {
                if (CollectionUtils.isNotEmpty(jkids2Update)) {
                    jkids2Update.forEach(jkid -> {
                        updateApi(collect.get(jkid));
                    });
                }
            }
            jkIds.forEach(jkid -> {
                if ((CollectionUtils.isNotEmpty(jkids2Update) && !jkids2Update.contains(jkid)) || CollectionUtils.isEmpty(jkids2Update)) {
                    try {
                        saveApi(collect.get(jkid));
                    } catch (Exception e) {
                        addErrorJkidMap.put(jkid, e.getMessage());
                    }
                }
            });
            if (!updateFlag && CollectionUtils.isNotEmpty(jkids2Update)) {
                respMap.put("updateIds", jkids2Update);
            }
            if (MapUtils.isNotEmpty(addErrorJkidMap)) {
                respMap.put("addErrorIds", addErrorJkidMap);
            }
            return CommonResponse.ok(respMap);
        } catch (Exception e) {
            logger.error("导入接口配置异常:{}", e.getMessage());
            respMap.put("error", e.getMessage());
            return CommonResponse.fail(respMap);
        }
    }


    /**
     * 导出接口配置
     *
     * @param baseQO
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    public List<BdcOpenApiDTO> exportInterface(BaseQO baseQO) {
        if (CollectionUtils.isNotEmpty(baseQO.getIds())) {
            List<BdcOpenApiDTO> bdcOpenApiDTOList = new ArrayList<>(baseQO.getIds().size());
            baseQO.getIds().forEach(id -> {
                BdcOpenApiDTO bdcOpenApiDTO = searchInterfaceInfoForExport(id,false);
                if (bdcOpenApiDTO != null) {
                    bdcOpenApiDTOList.add(bdcOpenApiDTO);
                }
            });
            return bdcOpenApiDTOList;
        }
        return null;
    }

    /**
     * 复制接口配置
     *
     * @param interfaceId
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    public CommonResponse<String> copytInterface(String interfaceId) {
        logger.info("复制接口开始:{}", interfaceId);
        try {
            if (StringUtils.isNotBlank(interfaceId)){
                BdcOpenApiDTO newInterfaceInfo = searchInterfaceInfoForExport(interfaceId,true);
                if (newInterfaceInfo != null){
                    //重新生成id
                    newInterfaceInfo.setId(UUIDGenerator.generate());
                    newInterfaceInfo.setName(newInterfaceInfo.getName() + "-copy");
                    newInterfaceInfo.setUrl("");
                    newInterfaceInfo.setCreatedTime(new Date());
                    newInterfaceInfo.setUpdatedTime(new Date());
                    String jkid = saveApi(newInterfaceInfo);
                    logger.info("复制接口结束");
                    return CommonResponse.ok(jkid);
                }else {
                    return CommonResponse.fail("未查询到复制的原接口！");
                }
            }else {
                return CommonResponse.fail("缺少必填参数！");
            }
        } catch (Exception e) {
            logger.error("复制接口异常:{}", e.getMessage());
            return CommonResponse.fail(e.getMessage());
        }
    }

    private BdcOpenApiDTO searchInterfaceInfoForExport(String apiId,boolean createNewInterfacefalg) {
        if (StringUtils.isBlank(apiId)){
            return null;
        }
        BdcDwJkDO bdcDwJkDO = bdcDwJkMapper.searchApiInfoById(apiId);
        if (bdcDwJkDO == null) {
            return null;
        }
        List<BdcDwJkCsDO> bdcDwJkCsDOList = bdcDwJkCsMapper.searchApiParamByApiId(apiId);
        if (CollectionUtils.isEmpty(bdcDwJkCsDOList)) {
            return null;
        }
        BdcOpenApiDTO bdcOpenApiDTO = new BdcOpenApiDTO();
        bdcOpenApiDTO.convertDO(bdcDwJkDO, bdcDwJkCsDOList);
        if (StringUtils.isNotBlank(bdcOpenApiDTO.getUrl())) {
            String replace = bdcOpenApiDTO.getUrl().replace(BASE_URL, "");
            if (replace.contains("?")) {
                bdcOpenApiDTO.setUrl(replace.substring(0, replace.indexOf("?")));
            } else if (replace.contains("/")) {
                bdcOpenApiDTO.setUrl(replace.substring(0, replace.indexOf("/")));
            } else {
                bdcOpenApiDTO.setUrl(replace);
            }
        }
        //处理多层嵌套参数
        if (createNewInterfacefalg){
            initChildrenParamListInfoWithNewChildrenId(bdcOpenApiDTO.getParamList());
        }else {
            initChildrenParamListInfo(bdcOpenApiDTO.getParamList());
        }
        return bdcOpenApiDTO;
    }

    private void initChildrenParamListInfo(List<BdcOpenApiParamDTO> paramList) {
        if (CollectionUtils.isNotEmpty(paramList)) {
            paramList.forEach(param -> {
                if (StringUtils.isNotBlank(param.getChildParamId())) {
                    List<BdcDwJkCsDO> bdcDwJkCsDOS = bdcDwJkCsMapper.searchApiParamByApiId(param.getChildParamId());
                    if (CollectionUtils.isNotEmpty(bdcDwJkCsDOS)) {
                        List<BdcOpenApiParamDTO> bdcOpenApiParamDTOList = new ArrayList<>(bdcDwJkCsDOS.size());
                        bdcDwJkCsDOS.forEach(bdcDwJkCsDO -> {
                            BdcOpenApiParamDTO bdcOpenApiParamDTO = new BdcOpenApiParamDTO();
                            bdcOpenApiParamDTO.convertDO(bdcDwJkCsDO);
                            bdcOpenApiParamDTOList.add(bdcOpenApiParamDTO);
                        });
                        initChildrenParamListInfo(bdcOpenApiParamDTOList);
                        param.setChildParamInfo(bdcOpenApiParamDTOList);
                    }
                }
            });
        }
    }

    private void initChildrenParamListInfoWithNewChildrenId(List<BdcOpenApiParamDTO> paramList) {
        if (CollectionUtils.isNotEmpty(paramList)) {
            paramList.forEach(param -> {
                if (StringUtils.isNotBlank(param.getChildParamId())) {
                    List<BdcDwJkCsDO> bdcDwJkCsDOS = bdcDwJkCsMapper.searchApiParamByApiId(param.getChildParamId());
                    if (CollectionUtils.isNotEmpty(bdcDwJkCsDOS)) {
                        List<BdcOpenApiParamDTO> bdcOpenApiParamDTOList = new ArrayList<>(bdcDwJkCsDOS.size());
                        bdcDwJkCsDOS.forEach(bdcDwJkCsDO -> {
                            BdcOpenApiParamDTO bdcOpenApiParamDTO = new BdcOpenApiParamDTO();
                            bdcOpenApiParamDTO.convertDO(bdcDwJkCsDO);
                            bdcOpenApiParamDTOList.add(bdcOpenApiParamDTO);
                        });
                        initChildrenParamListInfo(bdcOpenApiParamDTOList);
                        param.setChildParamInfo(bdcOpenApiParamDTOList);
                        param.setChildParamId(UUIDGenerator.generate());
                    }
                }
            });
        }
    }

    private List<BdcDwJkDO> getApiFromXml() {
        List<BdcDwJkDO> bdcDwJkDOS = new ArrayList<>();
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(ConfigLocations.PATHS);
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            ExchangeBean bean = (ExchangeBean) applicationContext.getBean(beanName);
            if (bean != null) {
                BdcDwJkDO bdcDwJkDO = BdcDwJkDO.BdcDwJkDOBuilder.aBdcDwJkDO().withYyid("exchange-app")
                        .withJkid(UUIDGenerator.generate())
                        .withJklx(2)
                        .withCjr("system")
                        .withSfjlrz(0)
                        .withFbzt(OpenApiReleaseStatus.PUBLISHED.getReleaseStatus())
//                        .withJkmc((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("logEventName", "接口名称初始化异常请手动补录"))
//                        .withJkms((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("logEventName", "接口名称初始化异常请手动补录"))
                        .withJkdz(XML_BEAN_INTERFACE_URL_PRE_FIX + bean.getId())
                        .withFbzt(OpenApiReleaseStatus.PUBLISHED.getReleaseStatus())
                        .withJkqqfs(RequestMethod.POST.name())
                        .build();
                if (bean.getServiceInfoBO() != null && MapUtils.isNotEmpty(bean.getServiceInfoBO().getRequestInfo()) && bean.getServiceInfoBO().getRequestInfo().get("logEventName") != null) {
                    bdcDwJkDO.setJkmc((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("logEventName", "接口名称初始化异常请手动补录"));
                    bdcDwJkDO.setJkms((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("logEventName", "接口名称初始化异常请手动补录"));
                    bdcDwJkDO.setJkxff((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("requester", "接口调用方初始化异常请手动补录"));
                } else {
                    bdcDwJkDO.setJkmc(bean.getLogBO() != null && org.apache.commons.lang3.StringUtils.isNoneBlank(bean.getLogBO().getLogEventName()) ? bean.getLogBO().getLogEventName() : "接口名称初始化异常请手动补录");
                    bdcDwJkDO.setJkms(bean.getLogBO() != null && org.apache.commons.lang3.StringUtils.isNoneBlank(bean.getLogBO().getLogEventName()) ? bean.getLogBO().getLogEventName() : "接口名称初始化异常请手动补录");
                    bdcDwJkDO.setJkxff(bean.getLogBO() != null && org.apache.commons.lang3.StringUtils.isNoneBlank(bean.getLogBO().getRequester()) ? bean.getLogBO().getRequester() : "接口调用方初始化异常请手动补录");
                }
                bdcDwJkDOS.add(bdcDwJkDO);
            }

        }
        return bdcDwJkDOS;
    }

    private List<BdcDwJkDO> getApiFromSwagger() {
        List<BdcDwJkDO> bdcDwJkDOS = new ArrayList<>();
        //spring工具类，可以获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(BASE_PACKAGE) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            //MetadataReader 的工厂类
            MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                //用于读取类信息
                MetadataReader reader = readerfactory.getMetadataReader(resource);
                //扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(classname);
                //判断是否有指定主解
                OpenController annotation = clazz.getAnnotation(OpenController.class);
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                if (annotation != null) {
                    //将注解中的类型值作为key，对应的类作为 value
                    String preFixUrl = "";
                    if (requestMapping != null) {
                        String[] urlArray = requestMapping.value();
                        if (urlArray != null && urlArray.length > 0) {
                            preFixUrl = urlArray[0];
                        }
                    }
                    Method[] methods = clazz.getMethods();
//                    Method[] interfaceMethods = null;
//                    if (clazz.getInterfaces() != null && clazz.getInterfaces().length > 0) {
//                        interfaceMethods = clazz.getInterfaces()[0].getMethods();
//                    }
                    for (Method method : methods) {
                        DsfLog dsfLog = method.getAnnotation(DsfLog.class);
                        if (dsfLog != null) {
                            PostMapping postMapping = method.getAnnotation(PostMapping.class);
                            GetMapping getMapping = method.getAnnotation(GetMapping.class);
                            if (clazz.getInterfaces() != null && clazz.getInterfaces().length > 0) {
                                Method tempMethod = clazz.getInterfaces()[0].getMethod(method.getName(), method.getParameterTypes());
                                postMapping = tempMethod.getAnnotation(PostMapping.class);
                                getMapping = tempMethod.getAnnotation(GetMapping.class);
                            }
                            BdcDwJkDO bdcDwJkDO = BdcDwJkDO.BdcDwJkDOBuilder.aBdcDwJkDO().withJkid(UUIDGenerator.generate())
                                    .withCjr("system")
                                    .withFbzt(OpenApiReleaseStatus.PUBLISHED.getReleaseStatus())
                                    .withJklx(2)
                                    .withSfjlrz(0)
                                    .withJkxff(dsfLog.requester())
                                    .withYyid("exchange-app")
                                    .withJkmc(dsfLog.logEventName())
                                    .withJkms(dsfLog.logEventName())
                                    .withFbzt(1)
                                    .withJkqqfs(postMapping != null ? RequestMethod.POST.name() : RequestMethod.GET.name())
                                    .build();
                            if (postMapping != null) {
                                bdcDwJkDO.setJkdz(preFixUrl + (postMapping.value().length > 0 ? postMapping.value()[0] : ""));
                            } else if (getMapping != null) {
                                bdcDwJkDO.setJkdz(preFixUrl + (getMapping.value().length > 0 ? getMapping.value()[0] : ""));
                            } else {
                                bdcDwJkDO.setJkdz("接口地址获取异常请手动补录");
                            }
                            bdcDwJkDOS.add(bdcDwJkDO);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return bdcDwJkDOS;
    }

    /**
     * 检查sql
     *
     * @param bdcOpenApiDTO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    public CommonResponse checkSql(BdcOpenApiDTO bdcOpenApiDTO) {
        checkSqlForChildren(bdcOpenApiDTO);
        return CommonResponse.ok();
    }

    /**
     * 保存接口信息
     *
     * @param bdcDwJkDO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    public CommonResponse addOldApi(BdcDwJkDO bdcDwJkDO) {
        try {
            if (StringUtils.isNotBlank(bdcDwJkDO.getJkdz())){
                if (StringUtils.isBlank(bdcDwJkDO.getJkid())){
                    bdcDwJkDO.setJkid(UUIDGenerator.generate());
                }
                checkUrlUniqueness(bdcDwJkDO.getJkid(),bdcDwJkDO.getJkdz());
                bdcDwJkDO.setUpdatetime(new Date());
                bdcDwJkDO.setCjsj(new Date());
                bdcDwJkDO.setFbzt(1);
                bdcDwJkDO.setJklx(2);
                bdcDwJkMapper.insertApiInfo(bdcDwJkDO);
                return CommonResponse.ok();
            }else {
                return CommonResponse.fail("补录程序接口异常");
            }
        }catch (Exception e){
            logger.info("补录程序接口异常:{}",e.getMessage());
            return CommonResponse.fail("补录程序接口异常:"+e.getMessage());
        }
    }

    /**
     * 校验接口参数和配置sql
     *
     * @param bdcOpenApiDTO
     */
    private void checkSqlForChildren(BdcOpenApiDTO bdcOpenApiDTO) {
        String sqlStr = bdcOpenApiDTO.getSql();
        if (StringUtils.isBlank(sqlStr)) {
            throw new AppException("sql不能为空!");
        }

        List<String> sqlParamList = new ArrayList<>();
        List<String> returnColumnList = new ArrayList<>();
        /**
         * 检验sql查询参数和请求参数是否一致、查询结果集合返回参数是否一致
         */
        logger.info("当前处理的sql======================>" + sqlStr);
        /**
         * 1.sql中的查询参数与请求参数一致性
         */
        if (!StringUtils.containsIgnoreCase(sqlStr, "#")) {
            throw new AppException("【" + sqlStr + "】" + "当前sql查询条件不能为空!");
        }

        String sqlParamRegex = "#\\{(.*?)}";
        Pattern sqlParamPattern = Pattern.compile(sqlParamRegex);
        Matcher sqlParamMatcher = sqlParamPattern.matcher(sqlStr);
        while (sqlParamMatcher.find()) {
            String sqlParam = sqlParamMatcher.group();
            String key = sqlParam.substring(2, sqlParam.length() - 1);
            sqlParamList.add(key);
        }

        /**
         * 2.sql中的查询列与响应参数一致性进行校验
         */
        if (StringUtils.startsWithIgnoreCase(sqlStr, "select *")) {
            throw new AppException("sql中的查询列与响应参数不一致!");
        }
        returnColumnList.add(sqlStr);
        List<BdcOpenApiParamDTO> paramList = bdcOpenApiDTO.getParamList();
        if (CollectionUtils.isNotEmpty(paramList)){
            /**
             * 响应校验
             */
            List<String> returnParamFieldList = paramList.stream().filter(x -> x.getParamType().equals(3) && StringUtils.isBlank(x.getChildParamId())).map(BdcOpenApiParamDTO::getFieldName).collect(Collectors.toList());
            List<String> returnSubtraction = compareParamWithSql(returnColumnList, returnParamFieldList);
            if (CollectionUtils.isNotEmpty(returnSubtraction)) {
                String missParam = String.join(",", returnSubtraction);
                throw new AppException("【" + missParam + "】" + "响应列和响应参数不一致!");
            }
        }

        /**
         * sql合法性进行校验
         */
        try {
            Map<String, Object> paramMap = new HashMap<>();
            resultSet(compileSqlList(sqlStr, paramMap));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            String errorMessage = e.getMessage();
            errorMessage = errorMessage.toLowerCase();
            if (StringUtils.containsIgnoreCase(errorMessage, "标识符无效")) {
                errorMessage = StringUtils.substring(errorMessage, errorMessage.indexOf(":") + 1, errorMessage.length());
                throw new AppException("sql字段名与数据库不符," + errorMessage);
            }
            if (StringUtils.containsIgnoreCase(errorMessage, "表或视图不存在")) {
                errorMessage = StringUtils.substring(errorMessage, errorMessage.indexOf(":") + 1, errorMessage.length());
                throw new AppException(errorMessage);
            }
            if (StringUtils.containsIgnoreCase(errorMessage, "命令未正确结束")) {
                errorMessage = StringUtils.substring(errorMessage, errorMessage.indexOf(":") + 1, errorMessage.length());
                throw new AppException("sql语法异常," + errorMessage);
            }
            throw new AppException(errorMessage);
        }
    }


}
