package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.define.v1.WorkDayClient;
import cn.gtmap.gtc.workflow.domain.define.Work;
import cn.gtmap.gtc.workflow.domain.define.WorkDay;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.realestate.accept.core.dto.ClfhtPersonDTO;
import cn.gtmap.realestate.accept.core.mapper.BdcSlConfigMapper;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.*;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.dao.BdcConfigMapper;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yzt.WorkDayVO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcQzxxFeginService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/12/12,1.0
 * @description ????????????
 */
@Repository
public class BdcSlPrintServiceImpl extends BaseController implements BdcSlPrintService {

    private final Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z]+\\}");
    private final String dzqmName = "????????????";
    private final String dzqmFjmc = "e????????????.png";
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcDysjPzService bdcDysjPzService;
    @Autowired
    BdcDypzFeignService bdcDypzFeignService;
    @Autowired
    BdcSjclService bdcSjclService;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlCountService bdcSlCountService;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcDjxlPzService bdcDjxlPzService;
    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    private BdcSdqghFeignService bdcSdqghFeignService;
    @Autowired
    BdcGenerateQlrService bdcGenerateQlrService;
    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;
    @Autowired
    BdcSlSqrService bdcSlSqrService;
    @Autowired
    private BdcSlConfigMapper bdcSlConfigMapper;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;
    /**
     * Redis??????
     */
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    BdcQzxxFeginService bdcQzxxFeginService;
    @Autowired
    private UserManagerClient userManagerClient;
    @Autowired
    BdcZfxxCxFeignService bdcZfxxCxFeignService;
    @Autowired
    BdcSlCdBlxxService bdcSlCdBlxxService;
    @Autowired
    BdcLzrFeignService bdcLzrFeignService;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcSlFpxxService bdcSlFpxxService;
    @Autowired
    BdcSlCdxxService bdcSlCdxxService;
    @Autowired
    BdcSfxxService bdcSfxxService;
    @Autowired
    BdcLcTsjfGxService bdcLcTsjfGxService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BeansResolveUtils beansResolveUtils;
    @Autowired
    WorkDayClient workDayClient;
    @Autowired
    BdcSlSjclService bdcSlSjclService;
    @Autowired
    BdcDlrFeignService bdcDlrFeignService;
    @Autowired
    BdcSlSfssDdxxService bdcSlSfssDdxxService;

    @Value("${url.acceptUrl:}")
    protected String acceptUrl;
    @Value("${accept.hidemodel:}")
    protected String hidemodel;
    @Value("#{'${sjddy.getnowYwr:}'.split(',')}")
    private List<String> ywrDjxlList;
    @Value("#{'${sjddy.balcDyid:}'.split(',')}")
    private List<String> balcDyidList;
    @Value("#{'${sjddy.oneSqsDyid:}'.split(',')}")
    private List<String> oneSqsDyidList;
    @Value("#{'${sjddy.getallQlr:}'.split(',')}")
    private List<String> getAllQlrDyids;
    @Value("${sfddy.ewmurl:}")
    private String sfdewm;

    @Value("#{'${print.jycxewm.dylx:}'.split(',')}")
    private List<String> jycxEwmDylxList;

    @Value("#{'${print.jycxewmmx.dylx:}'.split(',')}")
    private List<String> jycxEwmMxDylxList;
    /**
     * ?????????????????????
     */
    @Value("#{'${ydydjxl:}'.split(',')}")
    private List<String> ydydjxl;

    @Value("${sjddy.bjjd.url:}")
    private String bjjdUrl;

    @Value("#{'${dzqm.gzldyid:}'.split(',')}")
    private List<String> dzqmDyidList;

    /**
     * ??????????????????
     */
    @Value("${url.sign.image:}")
    private String signImageUrl;

    //???????????????????????????????????????
    @Value("#{'${print.sjd.dylx:}'.split(',')}")
    private List<String> sjdDylxList;
    @Value("#{'${print.sqs.dylx:}'.split(',')}")
    private List<String> sqsDylxList;
    @Value("#{'${dysjy.djk.dylx:}'.split(',')}")
    private List<String> djkDylxList;
    @Value("#{'${dysjy.slk.dylx:}'.split(',')}")
    private List<String> slkDylxList;

    @Value("#{'${print.zhlc.one.dylx:}'.split(',')}")
    private List<String> zhlcOneDylxList;

    /**
     * ?????????????????????????????????????????????
     */
    @Value("${sjddy.sfdgewm:true}")
    private boolean sfdgewm;

    /**
     * ??? sjddy.sfdgewm ?????????????????????????????????????????????????????????
     * ?????????????????????????????????????????????????????????
     * ?????????????????????????????????????????????????????????
     */
    @Value("#{'${sjddy.dydgewmlx:}'.split(',')}")
    private List<String> dydgewmlx;

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????????????????????????????????????????
     * @date : 2021/8/24 14:14
     */
    @Value("#{'${plslkdy.dylx:}'.split(',')}")
    private List<String> pldyDylxList;

    @Value("#{'${pllc.dydf.dylx:}'.split(',')}")
    private List<String> pllcDfDylxList;

    @Value("#{'${plzh.dydf.dylx:}'.split(',')}")
    private List<String> plzhDfDylxList;

    //???????????????????????????????????????????????????
    @Value("#{'${noplslkdylx.yzdf.dylx:}'.split(',')}")
    private List<String> yzdfDylxList;

    @Value("#{'${nocxsfxgxx.dylx:}'.split(',')}")
    private List<String> noqtxxDylxList;

    private static final String PROJECT_PATH = "/rest/v1.0/print/";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BdcSlPrintServiceImpl.class);

    /**
     * @param bdcSlPrintDTO ??????????????????
     * @return String  ????????????xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????????????????????????????
     */
    @Override
    public String getFr3Url(BdcSlPrintDTO bdcSlPrintDTO) {
        String printXmlUrl = "";
        //?????????????????????????????????????????????-???????????????-??????????????????-??????????????????
        if (ArrayUtils.contains(Constants.SL_SQS_DYLX, bdcSlPrintDTO.getDylx()) || ArrayUtils.contains(Constants.SL_XWBL_DYLX, bdcSlPrintDTO.getDylx()) || ArrayUtils.contains(Constants.SL_JDS_DYLX, bdcSlPrintDTO.getDylx())
                || ArrayUtils.contains(Constants.DJ_OTHER_DYLX, bdcSlPrintDTO.getDylx()) || sqsDylxList.contains(bdcSlPrintDTO.getDylx())) {
            printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getZxlc() + "/xml/nt";
        }
        //????????????????????????????????????--???????????????
        if (ArrayUtils.contains(Constants.SL_ALL_DYLX, bdcSlPrintDTO.getDylx()) || ArrayUtils.contains(CommonConstantUtils.DYLX_JK, bdcSlPrintDTO.getDylx()) || slkDylxList.contains(bdcSlPrintDTO.getDylx())) {
            printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getQlrlb() + "/" + bdcSlPrintDTO.getXmid() + "/xml/datas/nt";
        }
        if (ArrayUtils.contains(Constants.SL_ZFSF_DYLX, bdcSlPrintDTO.getDylx())) {
            printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getSfxxid() + "/sfxx/xml";
        }
        String fr3Url = "";
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getUrl())) {
            fr3Url = fr3Url + "v2|designMode=false|frURL=" + bdcSlPrintDTO.getUrl() + "|dataURL=" + printXmlUrl + "|updateUrl=http://oa.gtis.com.cn:80/platform/pluging/update.ini|hiddeMode=" + hidemodel;
        }
        return fr3Url;
    }

    /**
     * @param bdcSlPrintDTO ????????????
     * @return String  ????????????xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????????????????????????????
     */
    @Override
    public String getFr3UrlByLclx(BdcSlPrintDTO bdcSlPrintDTO) {
        String printXmlUrl = "";
        if (ArrayUtils.contains(CommonConstantUtils.Sl_DJ_DYLX, bdcSlPrintDTO.getDylx())) {
            printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getZxlc() + "/xml" + "?sjclids=" + StringUtils.join(bdcSlPrintDTO.getSjclids(), ",");
        }
        if (ArrayUtils.contains(CommonConstantUtils.Sl_ALL_DYLX, bdcSlPrintDTO.getDylx())) {
            Integer lclx = bdcXmFeignService.makeSureBdcXmLx(bdcSlPrintDTO.getGzlslid());
            if (CommonConstantUtils.LCLX_PL.equals(lclx) || CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
                bdcSlPrintDTO.setDylx(bdcSlPrintDTO.getDylx() + "pl");
            }
            printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getQlrlb() + "/" + bdcSlPrintDTO.getXmid() + "/xml/datas" + "?sjclids=" + StringUtils.join(bdcSlPrintDTO.getSjclids(), ",");
        }
        if (ArrayUtils.contains(CommonConstantUtils.SL_ZD_DYLX, bdcSlPrintDTO.getDylx())) {
            printXmlUrl = acceptUrl + PROJECT_PATH + "zd/" + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getQlrlb() + "/" + bdcSlPrintDTO.getXmid() + "/xml/datas" + "?sjclids=" + StringUtils.join(bdcSlPrintDTO.getSjclids(), ",");
        }
        String fr3Url = "";
        if (StringUtils.isNotBlank(bdcSlPrintDTO.getUrl())) {
            fr3Url = fr3Url + "v2|designMode=false|frURL=" + bdcSlPrintDTO.getUrl() + "|dataURL=" + printXmlUrl + "|updateUrl=http://oa.gtis.com.cn:80/platform/pluging/update.ini|hiddeMode=" + hidemodel;
        }
        return fr3Url;
    }

    /**
     * @param processInsId ???????????????id
     * @return String  ????????????xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????xml???????????????????????????xml?????????---????????????????????????????????????????????????????????????????????????xml?????????
     */
    @Override
    public String packPrintXml(String processInsId, String dylx, String zxlc, String sjclids, String userName) {
        String xml = "";
        if (StringUtils.isNotBlank(processInsId) && StringUtils.isNotBlank(dylx)) {
            Map<String, List<Map>> paramMap = Maps.newHashMap();
            List<Map> maps = new ArrayList<>();
            generatePrintParam(processInsId, dylx, zxlc, sjclids, userName, maps);
            paramMap.put(dylx, maps);
            xml = bdcPrintFeignService.print(paramMap);
        }
        LOGGER.info("????????????{},??????????????????xml{}", dylx, xml);
        return xml;
    }

    /**
     * @param
     * @param processInsId
     * @param dylx
     * @param zxlc
     * @param sjclids
     * @param userName
     * @param maps
     * @return void
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/7/6 19:51
     * @description ????????????????????????
     **/
    private void generatePrintParam(String processInsId, String dylx, String zxlc, String sjclids, String userName, List<Map> maps) {
        Map<String, String> map;
        //???????????????????????????????????????????????????????????????
        //?????????????????????????????????????????????????????????????????????
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(processInsId);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            switch (xmlx) {
                //????????????
                case 1:
                    //????????????
                case 2:
                    for (BdcXmDO bdcXmDO : bdcXmDOList) {
                        map = generateDjMap(bdcXmDO, sjclids, zxlc);
                        map.put("userName", userName);
                        if (StringUtils.isNotBlank(map.get("jycx"))) {
                            map.put("jycx", map.get("jycx") + "?dylx=" + dylx);
                        }
                        maps.add(map);
                        //????????????????????????
                        if (CollectionUtils.isNotEmpty(zhlcOneDylxList) && zhlcOneDylxList.contains(dylx)) {
                            break;
                        }
                    }
                    break;
                case 3:
                    //????????????????????????????????????
                    if (CollectionUtils.isNotEmpty(pllcDfDylxList) && pllcDfDylxList.contains(dylx)) {
                        for (BdcXmDO bdcXmDO : bdcXmDOList) {
                            map = generateDjMap(bdcXmDO, sjclids, zxlc);
                            map.put("userName", userName);
                            maps.add(map);
                        }
                    } else {
                        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                        map = generateDjMap(bdcXmDO, sjclids, zxlc);
                        map.put("userName", userName);
                        maps.add(map);
                    }
                    break;
                case 4:
                    if (CollectionUtils.isNotEmpty(plzhDfDylxList) && plzhDfDylxList.contains(dylx)) {
                        for (BdcXmDO bdcXmDO : bdcXmDOList) {
                            map = generateDjMap(bdcXmDO, sjclids, zxlc);
                            map.put("userName", userName);
                            maps.add(map);
                        }
                        break;
                    }
                    Set<BdcXmDO> newBdcXm = null;
                    List<BdcCshFwkgSlDO> cshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(processInsId);
                    if (CollectionUtils.isNotEmpty(cshFwkgSlDOList)) {
                        newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl));
                        //??????????????????
                        Map<Object, List<BdcCshFwkgSlDO>> groupFwkgSlMap = new HashMap<>();
                        //???????????????????????????????????????????????????????????????
                        List<BdcCshFwkgSlDO> nullZsxhBdcCshFwkgSlDOS = cshFwkgSlDOList
                                .stream().filter(xm -> xm.getZsxh() == null ||
                                        !Objects.equals(CommonConstantUtils.QLLX_DYAQ_DM, xm.getQllx()))
                                .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(nullZsxhBdcCshFwkgSlDOS)) {
                            //??????????????????????????????
                            Map<String, List<BdcCshFwkgSlDO>> djxlXmMap = nullZsxhBdcCshFwkgSlDOS
                                    .stream()
                                    .collect(Collectors.groupingBy(BdcCshFwkgSlDO::getDjxl));
                            if (MapUtils.isNotEmpty(djxlXmMap)) {
                                groupFwkgSlMap.putAll(djxlXmMap);
                            }
                            cshFwkgSlDOList.removeAll(nullZsxhBdcCshFwkgSlDOS);
                        }
                        //?????????????????????????????????????????????
                        if (CollectionUtils.isNotEmpty(cshFwkgSlDOList)) {
                            Map<Integer, List<BdcCshFwkgSlDO>> zsxhXmMap = cshFwkgSlDOList
                                    .stream()
                                    .collect(Collectors.groupingBy(BdcCshFwkgSlDO::getZsxh));
                            if (MapUtils.isNotEmpty(zsxhXmMap)) {
                                groupFwkgSlMap.putAll(zsxhXmMap);
                            }
                        }
                        //???????????????????????????
                        if (MapUtils.isNotEmpty(groupFwkgSlMap)) {
                            List<String> xmids = new ArrayList<>();
                            for (Map.Entry<Object, List<BdcCshFwkgSlDO>> objectListEntry : groupFwkgSlMap.entrySet()) {
                                xmids.add(objectListEntry.getValue().get(0).getId());
                            }
                            List<BdcXmDO> printXmList = bdcXmDOList.stream().filter(bdcxm -> xmids.contains(bdcxm.getXmid())).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(printXmList)) {
                                newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getXmid));
                                newBdcXm.addAll(printXmList);
                            } else {
                                newBdcXm.addAll(bdcXmDOList);
                            }
                        } else {
                            newBdcXm.addAll(bdcXmDOList);
                        }
                    } else {
                        newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl));
                        newBdcXm.addAll(bdcXmDOList);
                    }
                    for (BdcXmDO bdcXm : newBdcXm) {
                        map = generateDjMap(bdcXm, sjclids, zxlc);
                        map.put("userName", userName);
                        maps.add(map);
                        //????????????????????????
                        if (CollectionUtils.isNotEmpty(zhlcOneDylxList) && zhlcOneDylxList.contains(dylx)) {
                            break;
                        }
                    }
                    break;
                default:
                    break;
            }
            LOGGER.info("????????????:{},????????????:{},??????{}", dylx, bdcXmDOList.get(0).getSlbh(), maps);
        }
    }

    @Override
    public String packPrintXmlPl(String gzlslids, String dylx, String zxlc, String sjclids, String userName) {
        String xml = "";
        Map<String, List<Map>> paramMap = Maps.newHashMap();
        List<Map> maps = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslids)) {
            String[] gzlslidArr = gzlslids.split(",");
            if (gzlslidArr.length > 0) {
                for (String gzlslid : gzlslidArr) {
                    if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(dylx)) {
                        generatePrintParam(gzlslid, dylx, zxlc, sjclids, userName, maps);
                    }
                }
            }
        }
        paramMap.put(dylx, maps);
        xml = bdcPrintFeignService.print(paramMap);
        return xml;
    }

    /**
     * @param processInsId ???????????????id
     * @param dylx
     * @param zxlc
     * @param sjclids
     * @param userName
     * @return String  ????????????xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????xml???????????????????????????xml?????????---???????????????????????????????????????
     * ?????????????????????????????????????????????
     */
    @Override
    public String packPrintXmlToXmid(String processInsId, String dylx, String zxlc, String sjclids, String userName, String xmid) {
        String xml = "";
        if (StringUtils.isNotBlank(processInsId) && StringUtils.isNotBlank(dylx)) {
            Map<String, List<Map>> paramMap = Maps.newHashMap();
            Map<String, String> map;
            List<Map> maps = new ArrayList<>();
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            if (StringUtils.isNotBlank(xmid)) {
                bdcXmQO.setXmid(xmid);
            }
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                map = generateDjMap(bdcXmDOList.get(0), sjclids, zxlc);
                if (StringUtils.equals(CommonConstantUtils.DYLX_CLFMMHT, dylx)) {
                    map = getJyxx(map, bdcXmDOList.get(0));
                }
                if (StringUtils.isNotBlank(MapUtils.getString(map, "jycx"))) {
                    map.put("jycx", map.get("jycx") + "?dylx=" + dylx);
                }
                map.put("userName", userName);
                maps.add(map);
            }
            LOGGER.info("????????????:{},????????????:{}", dylx, bdcXmDOList.get(0).getSlbh());
            paramMap.put(dylx, maps);
            xml = bdcPrintFeignService.print(paramMap);
        }
        LOGGER.info("????????????{},??????????????????xml{}", dylx, xml);
        return xml;
    }

    /**
     * @param gzlslid ???????????????id
     * @param dylx    ????????????
     * @param zxlc    ????????????
     * @return String  ????????????xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????????????????xml??????(???????????????????????????_??????)
     */
    @Override
    public String packPrintXmlToNt(String gzlslid, String dylx, String zxlc) {
        String xml = "";
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(dylx)) {
            Map<String, List<Map>> paramMap = Maps.newHashMap();
            Map<String, String> map;
            List<BdcXmDO> bdcXmDOList = new ArrayList<>();
            Integer lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
            if (StringUtils.isNotBlank(gzlslid)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            }
            List<Map> maps = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                //xmid??????????????????????????????????????????????????????
                bdcXmDOList.sort(Comparator.comparing(BdcXmDO::getXmid));
                if (ArrayUtils.contains(Constants.SL_SQS_DYLX, dylx)) {
                    //?????????????????????????????????---??????bdc_djxl_pz??????????????????
                    BdcSlDysjDTO bdcSlDysjDTO = generateDylxAndSl(bdcXmDOList.get(0), dylx, lclx);
                    if (bdcSlDysjDTO.getDysl() == 1) {
                        LOGGER.info("????????????:{},????????????:{}", bdcSlDysjDTO.getDylx(), bdcXmDOList.get(0).getSlbh());
                        map = generateDjMap(bdcXmDOList.get(0), "", zxlc);
                        maps.add(map);
                    } else {
                        if (CollectionUtils.isNotEmpty(bdcSlDysjDTO.getBdcCshFwkgSlDOList())) {
                            LOGGER.info("????????????:{},????????????:{}", bdcSlDysjDTO.getDylx(), bdcXmDOList.get(0).getSlbh());
                            List<BdcXmDO> printXmList = new ArrayList<>(bdcXmDOList.size());
                            for (BdcCshFwkgSlDO bdcCshFwkgSlDO : bdcSlDysjDTO.getBdcCshFwkgSlDOList()) {
                                BdcXmDO printXm = bdcXmDOList.stream().filter(bdcXmDO -> StringUtils.equals(bdcCshFwkgSlDO.getId
                                        (), bdcXmDO.getXmid())).collect(Collectors.toList()).get(0);
                                printXmList.add(printXm);
                            }
                            for (BdcXmDO bdcXmDO : printXmList) {
                                map = generateDjMap(bdcXmDO, "", zxlc);
                                maps.add(map);
                            }
                        }
                    }
                    paramMap.put(bdcSlDysjDTO.getDylx(), maps);
                } else {
                    //???????????????????????????????????????????????????
                    if (CommonConstantUtils.LCLX_PL.equals(lclx) || CommonConstantUtils.LCLX_PT.equals(lclx) || ArrayUtils.contains(Constants.SL_XMBL_DYLX_HM, dylx)) {
                        map = generateDjMap(bdcXmDOList.get(0), "", zxlc);
                        maps.add(map);
                    } else {
                        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        //?????????????????????????????????????????????????????????????????????
                        Set<BdcXmDO> newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl));
                        newBdcXm.addAll(bdcXmDOList);
                        for (BdcXmDO bdcXmDO : newBdcXm) {
                            map = generateDjMap(bdcXmDO, "", zxlc);
                            maps.add(map);
                        }
                    }
                    paramMap.put(dylx, maps);
                    LOGGER.info("????????????:{},????????????:{}", dylx, bdcXmDOList.get(0).getSlbh());
                }
            }
            xml = bdcPrintFeignService.print(paramMap);
        }
        LOGGER.info("????????????{},??????????????????xml{}", dylx, xml);
        return xml;
    }

    /**
     * @param gzlslid ???????????????id
     * @param dylx    ????????????
     * @return String  ????????????????????????????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????xml(???????????????????????????????????????, ????????????????????????????????????_??????)
     * ????????????????????????????????????????????????????????????xmid??????????????????
     */
    @Override
    public String generatePrintData(String gzlslid, String dylx, String qlrlb, String xmid, String sjclids) {
        // ????????????????????????????????????????????????xmid,????????????????????? added by cyc 2019-12-30
        if (CommonConstantUtils.YCSDQDYLX.equals(dylx)) {
            List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
            BdcXmDO bdcXmDO = new BdcXmDO();
            bdcXmDO.setGzlslid(gzlslid);
            //??????????????????????????????????????????xml
            BdcDysjDTO bdcDysjDTO = generateXml(bdcXmDO, dylx, qlrlb, true, sjclids);
            bdcDysjDTOList.add(bdcDysjDTO);
            return bdcPrintFeignService.printDatas(bdcDysjDTOList);
        }
        String xml = "";
        //?????????????????????????????????????????????????????????????????????
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        if (ArrayUtils.contains(CommonConstantUtils.SFD_ALL_DYLX, dylx) && StringUtils.isNotBlank(xmid)) {
            bdcXmQO.setXmid(xmid);
        }
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
            generateDjDysjList(dylx, qlrlb, sjclids, bdcDysjDTOList, gzlslid, xmlx, bdcXmList);
            LOGGER.info("????????????:{},????????????:{}", dylx, bdcXmList.get(0).getSlbh());
            xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
        } else {
            List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
            //???????????????????????????????????????
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (bdcSlJbxxDO != null) {
                BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
                bdcSlXmQO.setJbxxid(bdcSlJbxxDO.getJbxxid());
                //???????????????????????? ???????????????xmid
                if (StringUtils.isNotBlank(xmid) && !pldyDylxList.contains(dylx)) {
                    bdcSlXmQO.setXmid(xmid);
                }
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    List<Map> maps = new ArrayList<>();
                    //??????????????????????????????????????????????????????
                    if (CollectionUtils.isNotEmpty(pldyDylxList) && pldyDylxList.contains(dylx)) {
                        List<BdcSlXmZsDTO> bdcSlXmZsDTOList = new ArrayList<>(CollectionUtils.size(bdcSlXmDOList));
                        if (CollectionUtils.isNotEmpty(yzdfDylxList) && yzdfDylxList.contains(dylx)) {
                            //???????????????????????????????????????????????????????????????????????????????????????????????????
                            //????????????????????????????????????????????????????????????????????????id
                            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), "", CommonConstantUtils.SF_F_DM);
                                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                                    BdcSlXmZsDTO bdcSlXmZsDTO = new BdcSlXmZsDTO();
                                    bdcSlXmZsDTO.setBdcSlXmDO(bdcSlXmDO);
                                    List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(bdcSlXmLsgxDOList.get(0).getYxmid());
                                    if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                                        bdcSlXmZsDTO.setZsid(bdcZsDOList.get(0).getZsid());
                                    }
                                    bdcSlXmZsDTOList.add(bdcSlXmZsDTO);
                                }
                            }
                            bdcSlXmZsDTOList = bdcSlXmZsDTOList.stream().filter(bdcSlXmZsDTO -> StringUtils.isNotBlank(bdcSlXmZsDTO.getZsid())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcSlXmZsDTO::getZsid))), ArrayList::new));
                            LOGGER.error("????????????????????????{},????????????{}", dylx, bdcSlXmZsDTOList.size());
                        }
                        if (CollectionUtils.isNotEmpty(bdcSlXmZsDTOList)) {
                            for (BdcSlXmZsDTO bdcSlXmZsDTO : bdcSlXmZsDTOList) {
                                BdcDysjDTO bdcDysjDTO = generateSlXml(dylx, bdcSlXmZsDTO.getBdcSlXmDO(), bdcSlJbxxDO, sjclids);
                                bdcDysjDTOList.add(bdcDysjDTO);
                                if (MapUtils.isNotEmpty(bdcDysjDTO.getParamMap())) {
                                    maps.add(bdcDysjDTO.getParamMap());
                                }
                            }
                        } else {
                            for (BdcSlXmDO bdcSlXm : bdcSlXmDOList) {
                                BdcDysjDTO bdcDysjDTO = generateSlXml(dylx, bdcSlXm, bdcSlJbxxDO, sjclids);
                                bdcDysjDTOList.add(bdcDysjDTO);
                                if (MapUtils.isNotEmpty(bdcDysjDTO.getParamMap())) {
                                    maps.add(bdcDysjDTO.getParamMap());
                                }
                            }
                        }
                    } else {
                        BdcDysjDTO bdcDysjDTO = generateSlXml(dylx, bdcSlXmDOList.get(0), bdcSlJbxxDO, sjclids);
                        bdcDysjDTOList.add(bdcDysjDTO);
                        if (MapUtils.isNotEmpty(bdcDysjDTO.getParamMap())) {
                            maps.add(bdcDysjDTO.getParamMap());
                        }
                    }
                    LOGGER.info("????????????:{},????????????:{}", dylx, bdcSlJbxxDO.getSlbh());
                    if (CollectionUtils.isNotEmpty(maps)) {
                        //???????????????????????????????????????????????????????????????????????????sql ????????????????????????
                        Map<String, List<Map>> paramMap = Maps.newHashMap();
                        paramMap.put(dylx, maps);
                        xml = bdcPrintFeignService.print(paramMap);
                    } else {
                        xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
                    }
                }
            }
        }
        LOGGER.info("????????????{},??????????????????xml{}", dylx, xml);
        return xml;
    }

    @Override
    public String generatePrintPlData(String gzlslids, String dylx, String qlrlb, String xmid, String sjclids) {
        String xml = "";
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
        if (StringUtils.isNotBlank(gzlslids)) {
            String[] gzlslidArr = gzlslids.split(",");
            if (gzlslidArr.length > 0) {
                for (String gzlslid : gzlslidArr) {
                    //?????????????????????????????????????????????????????????????????????
                    int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setGzlslid(gzlslid);
                    if (ArrayUtils.contains(CommonConstantUtils.SFD_ALL_DYLX, dylx) && StringUtils.isNotBlank(xmid)) {
                        bdcXmQO.setXmid(xmid);
                    }
                    List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    // ??????????????????ids
                    BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
                    bdcSlSjclQO.setGzlslid(gzlslid);
                    List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(bdcSlSjclQO);
                    if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                        sjclids = bdcSlSjclDOList.stream().map(String::valueOf).collect(Collectors.joining(CommonConstantUtils.ZF_YW_DH));
                    }
                    if (CollectionUtils.isNotEmpty(bdcXmList)) {
                        generateDjDysjList(dylx, qlrlb, sjclids, bdcDysjDTOList, gzlslid, xmlx, bdcXmList);
                    }
                }
            }
        }
        xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
        LOGGER.info("????????????{},??????????????????xml{}", dylx, xml);
        return xml;
    }

    /**
     * @param
     * @param dylx
     * @param qlrlb
     * @param sjclids
     * @param bdcDysjDTOList
     * @param gzlslid
     * @param xmlx
     * @param bdcXmList
     * @return void
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/7/6 19:34
     * @description ??????????????????????????????
     **/
    private void generateDjDysjList(String dylx, String qlrlb, String sjclids, List<BdcDysjDTO> bdcDysjDTOList, String gzlslid, int xmlx, List<BdcXmDO> bdcXmList) {
        //??????xmid???????????????????????????????????????
        bdcXmList.sort(Comparator.comparing(BdcXmDO::getXmid));
        BdcDysjDTO bdcDysjDTO;
        //???????????????????????????????????????
        switch (xmlx) {
            case 1:
                //????????????
                for (BdcXmDO bdcXmDO : bdcXmList) {
                    bdcDysjDTO = generateXml(bdcXmDO, dylx, qlrlb, false, sjclids);
                    bdcDysjDTOList.add(bdcDysjDTO);
                }
                break;
            case 2:
                if (CollectionUtils.isNotEmpty(balcDyidList)) {
                    //?????????????????????????????????
                    ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
                    if (processInstanceData != null && balcDyidList.contains(processInstanceData.getProcessDefinitionKey())) {
                        //????????????????????????????????????????????????????????????????????????
                        BdcXmDO bdcXmDO = bdcXmList.get(bdcXmList.size() - 1);
                        bdcDysjDTO = generateXml(bdcXmDO, dylx, qlrlb, false, sjclids);
                        bdcDysjDTOList.add(bdcDysjDTO);
                    } else {
                        //??????????????????????????????????????????
                        for (BdcXmDO bdcXmDO : bdcXmList) {
                            bdcDysjDTO = generateXml(bdcXmDO, dylx, qlrlb, false, sjclids);
                            if (bdcDysjDTO != null) {
                                bdcDysjDTOList.add(bdcDysjDTO);
                            }
                            //????????????????????????????????????,???????????????????????????
                            if (StringUtils.equals(CommonConstantUtils.DYLX_ZHSJD, dylx) || (CollectionUtils.isNotEmpty(zhlcOneDylxList) && zhlcOneDylxList.contains(dylx))) {
                                break;
                            }
                        }
                    }
                } else {
                    //??????????????????????????????????????????
                    for (BdcXmDO bdcXmDO : bdcXmList) {
                        bdcDysjDTO = generateXml(bdcXmDO, dylx, qlrlb, false, sjclids);
                        if (bdcDysjDTO != null) {
                            bdcDysjDTOList.add(bdcDysjDTO);
                        }
                        if (CollectionUtils.isNotEmpty(zhlcOneDylxList) && zhlcOneDylxList.contains(dylx)) {
                            break;
                        }
                    }
                }
                break;
            case 3:
                //???????????? sfpllc=true
                // ?????????????????????????????????????????????
                if (CommonConstantUtils.YCSSSQDDWDYLX.equals(dylx)
                        || CommonConstantUtils.YCSSSQDGRDYLX.equals(dylx)) {
                    for (BdcXmDO bdcXmDO : bdcXmList) {
                        bdcDysjDTO = generateXml(bdcXmDO, dylx, qlrlb, true, sjclids);
                        bdcDysjDTOList.add(bdcDysjDTO);
                    }
                } else {
                    BdcXmDO bdcXmDO = bdcXmList.get(0);
                    bdcDysjDTO = generateXml(bdcXmDO, dylx, qlrlb, true, sjclids);
                    bdcDysjDTOList.add(bdcDysjDTO);
                }

                break;
            case 4:
                //?????????????????????????????????????????????????????????????????????
                Set<BdcXmDO> newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl));
                newBdcXm.addAll(bdcXmList);
                for (BdcXmDO bdcXm : newBdcXm) {
                    bdcDysjDTO = generateXml(bdcXm, dylx, qlrlb, false, sjclids);
                    if (bdcDysjDTO != null) {
                        bdcDysjDTOList.add(bdcDysjDTO);
                    }
                    //????????????????????????????????????,???????????????????????????
                    if (StringUtils.equals(CommonConstantUtils.DYLX_ZHSJDPL, dylx) || (CollectionUtils.isNotEmpty(zhlcOneDylxList) && zhlcOneDylxList.contains(dylx))) {
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * @param gzlslid ???????????????id
     * @param dylx
     * @param qlrlb
     * @param xmid
     * @return String  ????????????xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????xml????????????????????????????????????,?????????????????????
     */
    @Override
    public String generatePrintDataToNt(String gzlslid, String dylx, String qlrlb, String xmid, String userName) {
        String xml = "";
        if (ArrayUtils.contains(CommonConstantUtils.DYLX_JK, dylx)) {
            //????????????????????????
            return generateJkData(dylx, xmid, userName);

        }
        //?????????????????????????????????????????????????????????????????????
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        if (ArrayUtils.contains(CommonConstantUtils.SFD_ALL_DYLX, dylx) && StringUtils.isNotBlank(xmid)) {
            bdcXmQO.setXmid(xmid);
        }
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            BdcDysjDTO bdcDysjDTO;
            List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
            //?????????????????????????????????????????????
            if (ArrayUtils.contains(Constants.SL_SJD_DYLX, dylx)) {
                //??????xmid??????
                bdcXmList.sort(Comparator.comparing(BdcXmDO::getXmid));
                BdcXmDO bdcXmDO = bdcXmList.get(0);
                bdcDysjDTO = generateXml(bdcXmDO, dylx, qlrlb, false, "");
                if (bdcDysjDTO != null) {
                    bdcDysjDTOList.add(bdcDysjDTO);
                }
            } else {
                //??????????????????????????????????????????????????????
                //??????xmid???????????????????????????????????????
                bdcXmList.sort(Comparator.comparing(BdcXmDO::getXmid));
                //???????????????????????????????????????
                switch (xmlx) {
                    case 1:
                        //????????????
                        BdcXmDO bdcXmDO = bdcXmList.get(0);
                        bdcDysjDTO = generateXml(bdcXmDO, dylx, qlrlb, false, "");
                        bdcDysjDTOList.add(bdcDysjDTO);
                        break;
                    case 2:
                        //??????????????????????????????????????????
                        for (BdcXmDO bdcXm : bdcXmList) {
                            bdcDysjDTO = generateXml(bdcXm, dylx, qlrlb, false, "");
                            if (bdcDysjDTO != null) {
                                bdcDysjDTOList.add(bdcDysjDTO);
                            }
                        }
                        break;
                    case 3:
                        //???????????? sfpllc=true
                        BdcXmDO bdcXm = bdcXmList.get(0);
                        bdcDysjDTO = generateXml(bdcXm, dylx, qlrlb, true, "");
                        bdcDysjDTOList.add(bdcDysjDTO);
                        break;
                    case 4:
                        //?????????????????????????????????????????????????????????????????????
                        Set<BdcXmDO> newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl));
                        newBdcXm.addAll(bdcXmList);
                        for (BdcXmDO bdcxm : newBdcXm) {
                            bdcDysjDTO = generateXml(bdcxm, dylx, qlrlb, false, "");
                            if (bdcDysjDTO != null) {
                                bdcDysjDTOList.add(bdcDysjDTO);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            LOGGER.info("????????????:{},????????????:{}", dylx, bdcXmList.get(0).getSlbh());
            xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
        } else {
            List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
            //?????????????????????????????????????????????????????????????????????
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (bdcSlJbxxDO != null) {
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    BdcDysjDTO bdcDysjDTO = generateSlXml(dylx, bdcSlXmDOList.get(0), bdcSlJbxxDO, "");
                    if (bdcDysjDTO != null) {
                        bdcDysjDTOList.add(bdcDysjDTO);
                    }
                    LOGGER.info("????????????:{},????????????:{}", dylx, bdcSlJbxxDO.getSlbh());
                    xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
                    if (StringUtils.isNotBlank(xml)) {
                        xml = replaceZdCodeToMc(xml);
                    }
                }
            }
        }
        LOGGER.info("????????????{},??????????????????xml{}", dylx, xml);
        return xml;
    }

    @Override
    public String generatePrintDataWithZdyData(Map configParam, String dylx) {
        configParam = Optional.ofNullable(configParam).orElse(new HashMap(8));
        if (configParam.containsKey("yjdh")) {
            generateYjSfdData(configParam);
        }
        return this.generateXmlData(configParam, dylx);
    }

    // ???????????????????????????
    private void generateYjSfdData(Map configParam) {
        // ???????????????????????????????????????????????????????????????????????????
        String mainPath = acceptUrl + PROJECT_PATH;
        String yjdh = String.valueOf(configParam.get("yjdh"));
        if (StringUtils.isNotBlank(yjdh)) {
            configParam.put("yjsfdewm", this.getYkqewm(yjdh, yjdh, mainPath, CommonConstantUtils.SF_S_DM));
        }
    }

    // ??????XML??????
    private String generateXmlData(Map configParam, String dylx) {
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx(dylx);
        List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDypzFeignService.listBdcDysjZbPz(bdcDysjZbPzDO);
        Map datas = bdcDysjPzService.queryPrintDatasList(configParam, "bdcSlConfigMapper", bdcDysjPzDOList);
        Multimap detail = bdcDysjPzService.queryPrintDetailList(configParam, "bdcSlConfigMapper", bdcDysjZbPzDOList);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDylx(dylx);
        bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        bdcDysjDTO.setDysj(JSONObject.toJSONString(datas));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(detail));
        bdcDysjDTOList.add(bdcDysjDTO);
        String xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
        LOGGER.info("????????????{},??????????????????xml{}", dylx, xml);
        return xml;
    }

    @Override
    public String generatePrintDataWithSfxxid(String gzlslid, String dylx, String sfxxid) throws UnsupportedEncodingException {
        Map configParam = Maps.newHashMap();
        String mainPath = acceptUrl + PROJECT_PATH;
        //??????????????????sfxxid?????????"1"????????????fegin???????????????????????????????????????
        if (StringUtils.equals("1", sfxxid)){
            sfxxid = null;
        }
        if (ArrayUtils.contains(CommonConstantUtils.SL_JFXX_DYLX, dylx) && StringUtils.isNotBlank(sfxxid)) {
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
            if (StringUtils.isNotBlank(bdcSlSfxxDO.getJfsewmurl())) {
                String ewm = mainPath + sfxxid + "/ewm?ewmurl=" + URLEncoder.encode(bdcSlSfxxDO.getJfsewmurl(), "UTF-8");
                LOGGER.info("?????????????????????:{}",ewm);
                configParam.put("ewmUrl", ewm);
            }else {
                configParam.put("ewmUrl", "");
            }
            configParam.put("sfxxid", CommonUtil.getOrElse(sfxxid, ""));
            return this.generateXmlData(configParam, dylx);
        } else {
            Map<String, String> map = new HashMap<>();
            List<BdcSlSfxxDO> list = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(list)) {
                BdcSlSfxxDO bdcSlSfxxDO = list.get(0);
                if (StringUtils.isNotBlank(bdcSlSfxxDO.getHyzfurl())) {
                    map.put("hyzfUrl", bdcSlSfxxDO.getHyzfurl());
                }
            }
            configParam.put("gzlslid", CommonUtil.getOrElse(gzlslid, ""));
            if (StringUtils.isNotBlank(map.get("hyzfUrl"))) {
                String hyzf = map.get("hyzfUrl");
                String hyzfUrl = mainPath + gzlslid + "/ewm?ewmurl=" + URLEncoder.encode(hyzf, "UTF-8");
                LOGGER.info("???????????????????????????:{}",hyzfUrl);
                configParam.put("hyzfUrl", hyzfUrl);
            }else {
                configParam.put("hyzfUrl", "");
            }
            return this.generateXmlData(configParam, dylx);
        }
    }

    private BdcDysjDTO generateXml(BdcXmDO bdcXmDO, String dylx, String qlrlb, Boolean sfpllc, String sjclids) {
        if (CommonConstantUtils.YCSDQDYLX.equals(dylx)) {
            return generateYcsdqData(bdcXmDO, dylx);
        }
        String mainPath = acceptUrl + PROJECT_PATH;
        //??????????????????
        List<Map> fwytMapList = bdcZdFeignService.queryBdcZd("fwyt");
        Map configParam = Maps.newHashMap();
        //???????????????????????????????????????
        generateZdData(bdcXmDO, configParam);
        //????????????????????????
        Date slsj = bdcXmDO.getSlsj();
        Date lzrq = null;
        try {
            List<Work> works = workDayClient.getWorks();
            if (CollectionUtils.isNotEmpty(works)) {
                List<WorkDay> workDays = workDayClient.getWorkDays(works.get(0).getId(), "", "");
                WorkDayVO workDayVO = DateUtilForWorkDay.getCloudWorkDays(workDays);
                lzrq = DateUtilForWorkDay.getNextWorkDay(slsj, bdcXmDO.convertIntegerCnqx(), workDayVO.getHolidayList(), workDayVO.getWorkList());
            }
            if (lzrq != null) {
                configParam.put("lzrq", DateUtils.formateYmdZw(lzrq));
            } else {
                configParam.put("lzrq", DateUtils.formateYmdZw(slsj));
            }
            LOGGER.info("gzlslid:{}????????????:{}", DateUtils.formateYmdZw(lzrq),bdcXmDO.getGzlslid());
        } catch (Exception e) {
            LOGGER.error("????????????????????????:{}", e.getMessage());
        }
        configParam.put("zl", StringUtils.isNotBlank(bdcXmDO.getZl()) ? bdcXmDO.getZl() : "");
        configParam.put("sjclids", StringUtils.isNotBlank(sjclids) ? sjclids : "");
        configParam.put("gzlslid", StringUtils.isNotBlank(bdcXmDO.getGzlslid()) ? bdcXmDO.getGzlslid() : "");
        //?????????URL????????????
        configParam.put("xmid", bdcXmDO.getXmid());
        configParam.put("slbh", bdcXmDO.getSlbh());
        configParam.put("djjg", CommonUtil.getOrElse(bdcXmDO.getDjjg(), ""));
        configParam.put("slsj", bdcXmDO.getSlsj());
        configParam.put("slr", StringUtils.isNotBlank(bdcXmDO.getSlr()) ? bdcXmDO.getSlr() : "");
        configParam.put("cnqx", null == bdcXmDO.getCnqx() ? "" : bdcXmDO.getCnqx());
        configParam.put("lcmc", StringUtils.isNotBlank(bdcXmDO.getGzldymc()) ? bdcXmDO.getGzldymc() : "");
        configParam.put("bdcdyh", bdcXmDO.getBdcdyh());
        configParam.put("djyy", StringUtils.isNotBlank(bdcXmDO.getDjyy()) ? bdcXmDO.getDjyy() : "");
        configParam.put("bz", null == bdcXmDO.getBz() ? "" : bdcXmDO.getBz());
        configParam.put("yxtcqzh", bdcXmDO.getYxtcqzh() == null ? "" : bdcXmDO.getYxtcqzh());
        configParam.put("dzwmj", null == bdcXmDO.getDzwmj() ? "" : bdcXmDO.getDzwmj());
        String dzwyt = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getDzwyt(), fwytMapList);
        configParam.put("dzwyt", null == dzwyt ? "" : dzwyt);
        configParam.put("qllx", null == bdcXmDO.getQllx() ? "" : bdcXmDO.getQllx());
        configParam.put("djlx", null == bdcXmDO.getDjlx() ? "" : bdcXmDO.getDjlx());
        configParam.put("qlrlb", CommonUtil.getOrElse(qlrlb, ""));
        configParam.put("bdcqzh", CommonUtil.getOrElse(bdcXmDO.getBdcqzh(), ""));
        configParam.put("djxl", CommonUtil.getOrElse(bdcXmDO.getDjxl(), ""));
        // ???????????????
        this.getLzrxx(configParam, bdcXmDO.getXmid());
//        ???????????????
        String ewmUrl = mainPath + bdcXmDO.getSlbh() + "/ewm";
        configParam.put("ewm", ewmUrl);
//        ???????????????
        String sjdQlrQmxx = mainPath + "qmxx?bdlx=2&amp;qzrlb=1&amp;xmid=" + bdcXmDO.getXmid();
        String sjdQlrDlrQmxx = mainPath + "qmxx?bdlx=2&amp;qzrlb=2&amp;xmid=" + bdcXmDO.getXmid();
        configParam.put("sjdqlrqm", CommonUtil.getOrElse(sjdQlrQmxx, ""));
        configParam.put("sjdqlrdlrqm", CommonUtil.getOrElse(sjdQlrDlrQmxx, ""));
        String bjjdUrlEwm = "";
        try {
            if (StringUtils.isNotBlank(bjjdUrl)) {
                bjjdUrlEwm = mainPath + bdcXmDO.getSlbh() + "/ewm?ewmurl=" + URLEncoder.encode(bjjdUrl, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("??????????????????url??????:{}", e.getMessage());
        }
        configParam.put("bjjdewm", bjjdUrlEwm);
        //--??????????????????????????????--??????????????????id??????
        String dzqmUrl = "";
        configParam.put("dzqmUrl", "");
        getDzqmUrl(bdcXmDO.getGzldyid(), bdcXmDO.getGzlslid(), configParam);
        //?????????????????????,????????????,????????????,??????????????????
        generateQlrxx(bdcXmDO, configParam);
        //???????????????????????????????????????
        if (ArrayUtils.contains(CommonConstantUtils.SFD_ALL_DYLX, dylx)) {
            generateSfdData(bdcXmDO, qlrlb, configParam, sfpllc, mainPath);
        }
        //?????????????????????(?????????????????????????????????)----???????????????????????????
        getSfdEwm(dylx, bdcXmDO.getGzlslid(), bdcXmDO.getXmid(), bdcXmDO.getSlbh(), configParam);
        // ?????????????????????????????????(?????????????????????????????????)
        getYczfSfEwm(dylx, bdcXmDO.getGzlslid(), qlrlb, configParam);
        // ???????????????????????????
        getFpxxEwm(dylx, bdcXmDO.getGzlslid(), bdcXmDO.getXmid(), sfpllc, configParam);
        LOGGER.info("????????????:{},????????????:{},?????????????????????{}", dylx, bdcXmDO.getSlbh(), configParam);
        //??????sql???????????????????????????????????????
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx(dylx);
        List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDypzFeignService.listBdcDysjZbPz(bdcDysjZbPzDO);
        Map datas = bdcDysjPzService.queryPrintDatasList(configParam, "bdcSlConfigMapper", bdcDysjPzDOList);
        Multimap detail = bdcDysjPzService.queryPrintDetailList(configParam, "bdcSlConfigMapper", bdcDysjZbPzDOList);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDylx(dylx);
        bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        bdcDysjDTO.setDysj(JSONObject.toJSONString(datas));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(detail));
        return bdcDysjDTO;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????(????????????????????????
     */
    private BdcDysjDTO generateSlXml(String dylx, BdcSlXmDO bdcSlXmDO, BdcSlJbxxDO bdcSlJbxxDO, String sjclids) {
        LOGGER.warn("????????????id{}????????????????????????1.????????????????????????", bdcSlXmDO.getXmid());
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        Map configParam = Maps.newHashMap();
        List<Map> djxlMapList = bdcZdFeignService.queryBdcZd("djxl");
        //??????????????????
        if (CollectionUtils.isNotEmpty(djxlMapList)) {
            if (StringUtils.isNotBlank(bdcSlXmDO.getDjxl())) {
                configParam.put("djxlmc", StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcSlXmDO.getDjxl()), djxlMapList));
            } else {
                configParam.put("djxlmc", "");
            }
        } else {
            configParam.put("djxlmc", "");
        }
        LOGGER.warn("????????????id{}????????????????????????2.??????????????????", bdcSlXmDO.getXmid());
        // ??????????????????
        if (StringUtils.isNotBlank(bdcSlXmDO.getDjxl())) {
            BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO = new BdcDjlxDjxlGxDO();
            bdcDjlxDjxlGxDO.setDjxl(bdcSlXmDO.getDjxl());
            List<BdcDjlxDjxlGxDO> djlxDjxlGxDOS = this.bdcCshXtPzFeignService.listBdcDjlxDjxlGx(bdcDjlxDjxlGxDO);
            if (CollectionUtils.isNotEmpty(djlxDjxlGxDOS)) {
                List<Map> djlxMapList = bdcZdFeignService.queryBdcZd("djlx");
                configParam.put("djlxmc", StringToolUtils.convertBeanPropertyValueOfZd(
                        djlxDjxlGxDOS.get(0).getDjlx(), djlxMapList));
                configParam.put("djlx", djlxDjxlGxDOS.get(0).getDjlx());
            } else {
                configParam.put("djlxmc", "");
                configParam.put("djlx", "");
            }
        } else {
            configParam.put("djlxmc", "");
            configParam.put("djlx", "");
        }
        String mainPath = acceptUrl + PROJECT_PATH;
        //?????????URL????????????
        configParam.put("xmid", bdcSlXmDO.getXmid());
        String ewmUrl = mainPath + bdcSlJbxxDO.getSlbh() + "/ewm";
        configParam.put("ewm", ewmUrl);
        //?????????????????????(?????????????????????????????????)
        if (CollectionUtils.isEmpty(noqtxxDylxList) || !noqtxxDylxList.contains(dylx)) {
            LOGGER.warn("????????????id{}????????????????????????3.???????????????????????????????????????", bdcSlXmDO.getXmid());
            getSfdEwm(dylx, bdcSlJbxxDO.getGzlslid(), bdcSlXmDO.getXmid(), bdcSlJbxxDO.getSlbh(), configParam);
            getDzqmUrl(bdcSlJbxxDO.getGzldyid(), bdcSlJbxxDO.getGzlslid(), configParam);
        }
        configParam.put("gzlslid", bdcSlJbxxDO.getGzlslid());
        configParam.put("sjclids", StringUtils.isNotBlank(sjclids) ? sjclids : "");
        //???????????????????????????????????????????????? yxmid
        String yxmid = "";
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), "", CommonConstantUtils.SF_F_DM);
        if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
            yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
            String czjycxewm = mainPath + "jycx/" + bdcSlXmLsgxDOList.get(0).getYxmid() + "?dylx=" + dylx;
            configParam.put("jycx", czjycxewm);
        } else {
            String czjycxewm = mainPath + "jycx/" + bdcSlXmDO.getXmid() + "?dylx=" + dylx;
            configParam.put("jycx", czjycxewm);
        }
        LOGGER.warn("????????????id{}????????????????????????4.????????????????????????", bdcSlXmDO.getXmid());
        //???????????????????????????
        BdcCdxxQO bdcCdxxQO = new BdcCdxxQO();
        bdcCdxxQO.setXmid(bdcSlXmDO.getXmid());
        String xcxr = "";
        String xcxrzjh = "";
        String cdxxid = "";
        BdcSlCdxxDO bdcSlCdxxDO = bdcSlCdxxService.queryBdcCdxx(bdcCdxxQO);
        if (Objects.nonNull(bdcSlCdxxDO)) {
            xcxr = bdcSlCdxxDO.getXcxr();
            xcxrzjh = bdcSlCdxxDO.getXcxrzjh();
            cdxxid = bdcSlCdxxDO.getCdxxid();
        }
        configParam.put("yxmid", CommonUtil.getOrElse(yxmid, ""));
        configParam.put("xcxr", CommonUtil.getOrElse(xcxr, ""));
        configParam.put("xcxrzjh", CommonUtil.getOrElse(xcxrzjh, ""));
        configParam.put("cdxxid", CommonUtil.getOrElse(cdxxid, ""));
//        getDzqmUrl(bdcSlJbxxDO.getGzldyid(), bdcSlJbxxDO.getGzlslid(), configParam);
        //????????????
        LOGGER.warn("????????????id{}????????????????????????5.??????????????????", bdcSlXmDO.getXmid());
        String qxdms = Container.getBean(BdcConfigUtils.class).getQxdm(bdcSlJbxxDO.getQxdm());
        LOGGER.info("????????????{}????????????????????????{}??????????????????????????????{}", bdcSlJbxxDO.getSlbh(), bdcSlJbxxDO.getQxdm(), qxdms);
        configParam.put("qxdms", CommonUtil.getOrElse(qxdms, bdcSlJbxxDO.getQxdm()));
        LOGGER.info("????????????:{},????????????:{},????????????????????????{}", dylx, bdcSlJbxxDO.getSlbh(), configParam);
        //??????sql???????????????????????????????????????
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx(dylx);
        List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDypzFeignService.listBdcDysjZbPz(bdcDysjZbPzDO);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        //????????????????????????????????????????????????????????????????????????????????????
        if (StringUtils.isNotBlank(bdcDysjPzDOList.get(0).getDbsource()) && StringUtils.equals("bdcdj", bdcDysjPzDOList.get(0).getDbsource())) {
            bdcDysjDTO.setParamMap(configParam);
        } else {
            Map datas = bdcDysjPzService.queryPrintDatasList(configParam, "bdcSlConfigMapper", bdcDysjPzDOList);
            Multimap detail = bdcDysjPzService.queryPrintDetailList(configParam, "bdcSlConfigMapper", bdcDysjZbPzDOList);
            bdcDysjDTO.setDylx(dylx);
            bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
            bdcDysjDTO.setDysj(JSONObject.toJSONString(datas));
            bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(detail));
        }
        return bdcDysjDTO;

    }

    private BdcSlSfxxQO getSfxx(String gzlslid, String xmid, String qlrlb, String djxl) {
        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        //????????????????????????????????????????????????????????????1??????????????????
        Set<BdcXmDO> bdcxm = new TreeSet<>(Comparator.comparing(BdcXmDO::getBdcdyh));
        bdcxm.addAll(bdcXmDOList);
        Double dzwmj = bdcxm.stream().filter(bdcXm -> bdcXm.getDzwmj() != null).map(t -> new BigDecimal(String.valueOf(t.getDzwmj())))
                .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
        Double zdzhmj = bdcxm.stream().filter(bdcXm -> bdcXm.getZdzhmj() != null).map(t -> new BigDecimal(String.valueOf(t.getZdzhmj())))
                .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
        bdcSlSfxxQO.setDzwmj(dzwmj);
        bdcSlSfxxQO.setZdzhmj(zdzhmj);
        String qlrdlrmc = "";
        String qlrdlrdh = "";
        List<BdcQlrDO> qlrDoList = new ArrayList<>();
        List<BdcQlrDO> ywrList = new ArrayList<>();
        if (StringUtils.equals(qlrlb, CommonConstantUtils.QLRLB_QLR)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(qlrlb);
            qlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            ywrList = bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_YWR, djxl);
        } else if (StringUtils.equals(qlrlb, CommonConstantUtils.QLRLB_YWR)) {
            List<BdcQlrDO> bdcYwrDOList = bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_YWR, djxl);
            qlrDoList = bdcYwrDOList;
        }
        if (CollectionUtils.isNotEmpty(qlrDoList)) {
            if (StringUtils.equals(qlrlb, CommonConstantUtils.QLRLB_QLR)) {
                String qlrmc = StringToolUtils.resolveBeanToAppendStr(qlrDoList, "getQlrmc", ",");
                qlrdlrmc = StringToolUtils.resolveBeanToAppendStr(qlrDoList, "getDlrmc", ",");
                qlrdlrdh = StringToolUtils.resolveBeanToAppendStr(qlrDoList, "getDlrdh", ",");
                bdcSlSfxxQO.setQlrmc(qlrmc != null ? qlrmc : "");
            } else if (StringUtils.equals(qlrlb, CommonConstantUtils.QLRLB_YWR)) {
                BdcQlrDO bdcYwr = bdcGenerateQlrService.getYwrData(qlrDoList);
                qlrdlrmc = bdcYwr.getDlrmc();
                qlrdlrdh = bdcYwr.getDlrdh();
                bdcSlSfxxQO.setQlrmc(StringUtils.isNotBlank(bdcYwr.getQlrmc()) ? bdcYwr.getQlrmc() : "");
            }
        }
        //??????????????????????????????
        if (CollectionUtils.isNotEmpty(ywrList)) {
            BdcQlrDO bdcYwr = bdcGenerateQlrService.getYwrData(ywrList);
            String ywrmc = bdcYwr.getQlrmc();
            bdcSlSfxxQO.setYwrmc(StringUtils.isNotBlank(ywrmc) ? ywrmc : "");
        }
        bdcSlSfxxQO.setDlrmc(qlrdlrmc);
        bdcSlSfxxQO.setDlrlxdh(qlrdlrdh);
        return bdcSlSfxxQO;
    }

    /**
     * @param bdcXmDO,sjclids,zxlc
     * @return xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????????????????????????????????????????????????????????????????
     */
    private Map<String, String> generateDjMap(BdcXmDO bdcXmDO, String sjclids, String zxlc) {
        String mainPath = acceptUrl + PROJECT_PATH;
        Map<String, String> map = new HashMap<>(2);
        List<String> sjclmcList;
        StringBuilder sjclBuilder = new StringBuilder();
        //???????????????????????????????????????????????????gzlslid?????????????????????
        sjclmcList = bdcSjclService.listSjclMc(bdcXmDO.getGzlslid(), sjclids);
        if (CollectionUtils.isNotEmpty(sjclmcList)) {
            for (int i = 0; i < sjclmcList.size(); i++) {
                sjclBuilder = sjclBuilder.append(i + 1).append(".").append(sjclmcList.get(i));
            }
        }
        //??????????????????
        BdcMjDTO bdcMjDTO = bdcSlCountService.countMj(bdcXmDO.getGzlslid());
        String ewmUrl = mainPath + bdcXmDO.getSlbh() + "/ewm";
        map.put("ewm", ewmUrl);
        map.put("xmid", bdcXmDO.getXmid());
        map.put("slbh", bdcXmDO.getSlbh());
        map.put("xmidSql", CommonConstantUtils.ZF_KG_CHAR);
        map.put("gzlslid", bdcXmDO.getGzlslid());
        map.put("sjclmc", sjclBuilder.toString());
        //???????????????????????????????????????
        String sqsQlrQmxx = mainPath + "qmxx?bdlx=1&amp;qzrlb=1&amp;xmid=" + bdcXmDO.getXmid();
        String sqsQlrDlrQmxx = mainPath + "qmxx?bdlx=1&amp;qzrlb=2&amp;xmid=" + bdcXmDO.getXmid();
        String sqsYwrQmxx = mainPath + "qmxx?bdlx=1&amp;qzrlb=3&amp;xmid=" + bdcXmDO.getXmid();
        String sqsYwrDlrQmxx = mainPath + "qmxx?bdlx=1&amp;qzrlb=4&amp;xmid=" + bdcXmDO.getXmid();
        //????????????
        String mmhtQlrQmxx = mainPath + "qmxx?bdlx=4&qzrlb=1&xmid=" + bdcXmDO.getXmid();
        String mmhtQlrDlrQmxx = mainPath + "qmxx?bdlx=4&qzrlb=2&xmid=" + bdcXmDO.getXmid();
        String mmhtYwrQmxx = mainPath + "qmxx?bdlx=4&qzrlb=3&xmid=" + bdcXmDO.getXmid();
        String mmhtYwrDlrQmxx = mainPath + "qmxx?bdlx=4&qzrlb=4&xmid=" + bdcXmDO.getXmid();
        //?????????
        String qrsQlrQmxx = mainPath + "qmxx?bdlx=5&qzrlb=1&xmid=" + bdcXmDO.getXmid();
        String qrsYwrQmxx = mainPath + "qmxx?bdlx=5&qzrlb=3&xmid=" + bdcXmDO.getXmid();
        map.put("sqsqlrqm", sqsQlrQmxx);
        map.put("sqsqlrdlrqm", sqsQlrDlrQmxx);
        map.put("sqsywrqm", sqsYwrQmxx);
        map.put("sqsywrdlrqm", sqsYwrDlrQmxx);
        map.put("mmhtqlrqm", mmhtQlrQmxx);
        map.put("mmhtqlrdlrqm", mmhtQlrDlrQmxx);
        map.put("mmhtywrqm", mmhtYwrQmxx);
        map.put("mmhtywrdlrqm", mmhtYwrDlrQmxx);
        map.put("qrsqlrqm", qrsQlrQmxx);
        map.put("qrsywrqm", qrsYwrQmxx);
        //??????????????????yxmid????????????????????????
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
        bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            String czjycxewm = mainPath + "jycx/" + bdcXmLsgxDOList.get(0).getYxmid();
            map.put("jycx", czjycxewm);
        } else {
            map.put("jycx", "");
        }
        if (StringUtils.equals(CommonConstantUtils.BOOL_TRUE, zxlc) && CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            map.put("yxmid", bdcXmLsgxDOList.get(0).getYxmid());
        } else {
            map.put("yxmid", "");
        }
        if (bdcMjDTO != null) {
            map.put("zdzhmj", bdcMjDTO.getZdzhmj() != null ? bdcMjDTO.getZdzhmj().toString() : "0");
            map.put("dzwmj", bdcMjDTO.getDzwmj() != null ? bdcMjDTO.getDzwmj().toString() : "0");
        } else {
            map.put("zdzhmj", "0");
            map.put("dzwmj", "0");
        }
        //????????????????????????????????????????????????????????????????????????????????????xmid ??????????????????
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listPrevCqXm(bdcXmDO.getXmid(), true);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            map.put("cqxmid", bdcXmDOList.get(0).getXmid());
            List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(bdcXmDOList.get(0).getXmid());
            if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                map.put("cqzsid", bdcZsDOList.get(0).getZsid());
            } else {
                map.put("cqzsid", "");
            }
        } else {
            map.put("cqxmid", bdcXmDO.getXmid());
            map.put("cqzsid", "");
        }
        map.put("bdcdyh", bdcXmDO.getBdcdyh());
        getDzqmUrl(bdcXmDO.getGzldyid(), bdcXmDO.getGzlslid(), map);
        LOGGER.info("????????????????????????{}????????????{}", bdcXmDO.getSlbh(), map);
        return map;
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????---???????????????
     * @date : 2020/2/24 15:11
     */
    private Map<String, String> resolveQlrOrder(String gzlslid) {
        Map<String, String> resultMap = new HashMap<>();
        String qlrAndYwr = "";
        //??????????????????????????????????????????
        String allQlr = "";
        //?????????????????????????????????
        String qlrAll = "";
        //?????????????????????????????????????????????
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        //???????????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //????????????????????????
            bdcXmDOList = bdcXmDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl))), ArrayList::new));
            //??????xmid??????
            bdcXmDOList.sort(Comparator.comparing(BdcXmDO::getXmid));
            String newQlr = "";
            //??????????????????????????????????????????????????????????????????/????????????????????????set?????????????????????????????????????????????
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                //???????????????????????????????????????
                //1.????????????????????????????????????????????????2.??????????????????????????????????????????????????????
                // 3.?????????????????????????????????????????????????????????????????????????????????4.????????????????????????????????????????????????
                // 5.???????????????????????????????????????????????????
                BdcXmDO bdcXmDO = bdcXmDOList.get(bdcXmDOList.size() - 1);
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcXmDO.getXmid());
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    newQlr = StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getQlrmc", ",");
                } else {
                    newQlr = "";
                }
            }
            //????????????1??????????????????????????????????????????
            Set<String> otherQlrSet = new HashSet<>(bdcXmDOList.size());
            //???????????????????????????????????????????????????
            String firstYwr = "";
            //?????????????????????????????????
            List<BdcQlrDO> bdcYwrList = bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_YWR, bdcXmDOList.get(0).getDjxl());
            firstYwr = StringToolUtils.resolveBeanToAppendStr(bdcYwrList, "getQlrmc", ",");
            if (CollectionUtils.size(bdcXmDOList) > 1) {
                String otherQlr = "";
                //bdcxm????????????????????????????????????????????????????????????
                //?????????????????????????????????????????????????????????????????????????????????
                for (int i = 0; i < bdcXmDOList.size() - 1; i++) {
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setXmid(bdcXmDOList.get(i).getXmid());
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    otherQlr = StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getQlrmc", ",");
                    otherQlrSet.add(otherQlr);
                }
            }
            qlrAndYwr = (StringUtils.isNotBlank(newQlr) ? newQlr : "") + (CollectionUtils.isNotEmpty(otherQlrSet) ? ("/" + StringUtils.join(otherQlrSet, "/")) : "") + (StringUtils.isNotBlank(firstYwr) ? ("/" + firstYwr) : "");
            allQlr = (StringUtils.isNotBlank(newQlr) ? newQlr : "") + (CollectionUtils.isNotEmpty(otherQlrSet) ? ("/" + StringUtils.join(otherQlrSet, "/")) : "");
            qlrAll = (CollectionUtils.isNotEmpty(otherQlrSet) ? (StringUtils.join(otherQlrSet, "/")) : "") + (StringUtils.isNotBlank(newQlr) ? ("/" + newQlr) : "");
        }
        resultMap.put("qlrAndYwr", qlrAndYwr);
        resultMap.put("allQlr", allQlr);
        resultMap.put("qlrAll", qlrAll);
        return resultMap;
    }

    /**
     * ????????????????????????????????????????????????
     */
    private void getLzrxx(Map configParam, String xmid) {
        String lzrmc = "", lzrlxdh = "";
        try {
            if (StringUtils.isNotBlank(xmid)) {
                BdcLzrQO queryParam = new BdcLzrQO();
                queryParam.setXmid(xmid);
                List<BdcLzrDO> bdcLzrDOList = this.bdcLzrFeignService.listBdcLzr(queryParam);
                if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
                    lzrmc = bdcLzrDOList.get(0).getLzrmc();
                    lzrlxdh = bdcLzrDOList.get(0).getLzrzjh();
                }
            }
        } catch (Exception e) {
            LOGGER.error("???????????????????????????:{}", e.getMessage());
        }
        configParam.put("lzrmc", StringUtils.isNotBlank(lzrmc) ? lzrmc : StringUtils.EMPTY);
        configParam.put("lzrlxdh", StringUtils.isNotBlank(lzrlxdh) ? lzrlxdh : StringUtils.EMPTY);
    }

    /**
     * @param bdcXmDO,dylx,zxlc
     * @return dylx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????(?????????????????????????????????????????????)
     */
    @Override
    public BdcSlDysjDTO generateDylxAndSl(BdcXmDO bdcXmDO, String dylx, Integer lclx) {
        BdcSlDysjDTO bdcSlDysjDTO = new BdcSlDysjDTO();
        Integer dysl = 1;
        if (ArrayUtils.contains(Constants.SL_SQS_DYLX, dylx) && bdcXmDO != null) {
            ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(bdcXmDO.getGzlslid());
            BdcDjxlPzDO bdcDjxlPz = bdcDjxlPzService.queryBdcDjxlPzByGzldyidAndDjxl(processInstanceData.getProcessDefinitionKey(), bdcXmDO.getDjxl());
            if (bdcDjxlPz != null && StringUtils.isNotBlank(bdcDjxlPz.getSqsdylx())) {
                List<String> sqsDylxList = Arrays.asList(StringUtils.split(bdcDjxlPz.getSqsdylx(), ","));
                //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                if (CollectionUtils.isNotEmpty(sqsDylxList)) {
                    if (CollectionUtils.size(sqsDylxList) == 1) {
                        dylx = StringUtils.isNotBlank(bdcDjxlPz.getSqsdylx()) ? bdcDjxlPz.getSqsdylx() : dylx;
                    } else {
                        for (String sqsDylx : sqsDylxList) {
                            if (StringUtils.indexOf(sqsDylx, dylx) > -1) {
                                dylx = sqsDylx;
                            }
                        }
                    }
                }
                List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(bdcXmDO.getGzlslid());
                if (CollectionUtils.isNotEmpty(bdcCshFwkgSlDOList)) {
                    List<BdcCshFwkgSlDO> printList = new ArrayList<>();
                    //??????????????????????????????????????????
                    List<BdcCshFwkgSlDO> zsCshKgSlList = bdcCshFwkgSlDOList.stream().filter(bdcCshFwkgSlDO -> bdcCshFwkgSlDO.getZszl() != null && CommonConstantUtils.ZSLX_ZS.equals(bdcCshFwkgSlDO.getZszl())).collect(Collectors.toList());
                    List<BdcCshFwkgSlDO> zmCshKgSlList = bdcCshFwkgSlDOList.stream().filter(bdcCshFwkgSlDO -> bdcCshFwkgSlDO.getZszl() != null && CommonConstantUtils.ZSLX_ZM.equals(bdcCshFwkgSlDO.getZszl())).collect(Collectors.toList());
                    List<BdcCshFwkgSlDO> zmdCshKgSlList = bdcCshFwkgSlDOList.stream().filter(bdcCshFwkgSlDO -> bdcCshFwkgSlDO.getZszl() != null && CommonConstantUtils.ZSLX_ZMD.equals(bdcCshFwkgSlDO.getZszl())).collect(Collectors.toList());
                    //??????????????????????????????????????????????????????????????????
                    if (CollectionUtils.isNotEmpty(zsCshKgSlList)) {
                        printList = zsCshKgSlList;
                    } else if (CollectionUtils.isEmpty(zsCshKgSlList)) {
                        //????????????????????????????????????
                        printList = CollectionUtils.isNotEmpty(zmCshKgSlList) ? zmCshKgSlList : (CollectionUtils.isNotEmpty(zmdCshKgSlList) ? zmdCshKgSlList : Collections.emptyList());
                    }
                    if (CollectionUtils.isNotEmpty(printList)) {
                        Set<Integer> zsBdcCshFwkgSet = new HashSet<>();
                        for (int i = 0; i < printList.size(); i++) {
                            //????????????????????????????????????1??????1???????????????1??????????????????
                            zsBdcCshFwkgSet.add(printList.get(i).getZsxh() != null ? printList.get(i).getZsxh() : i);
                        }
                        if (CommonConstantUtils.LCLX_PL.equals(lclx) || CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
                            //??????????????????1????????????????????????????????????
                            dylx = dylx + "pl";
                        }
                        if (zsBdcCshFwkgSet.size() == 1) {
                            dysl = 1;
                        } else {
                            List<BdcCshFwkgSlDO> printCshKgList = printList.stream().filter(bdcCshFwkgSlDO -> bdcCshFwkgSlDO.getZsxh() != null).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(printCshKgList)) {
                                //zsxh?????????????????????????????????????????????????????????????????????????????????????????????
                                Set<BdcCshFwkgSlDO> printSet = new TreeSet<>(Comparator.comparing(BdcCshFwkgSlDO::getZsxh));
                                printSet.addAll(printCshKgList);
                                bdcSlDysjDTO.setBdcCshFwkgSlDOList(new ArrayList<>(printSet));
                                dysl = printSet.size();
                            } else {
                                //????????????????????????????????????????????????????????????????????????????????????
                                bdcSlDysjDTO.setBdcCshFwkgSlDOList(printList);
                                dysl = printList.size();
                            }
                        }
                    } else {
                        dysl = 1;
                    }
                } else {
                    //?????????????????????????????????????????????????????????1
                    dysl = 1;
                }
            }
            //????????????????????????????????????????????????
            if (oneSqsDyidList.contains(processInstanceData.getProcessDefinitionKey())) {
                dysl = 1;
            }
            bdcSlDysjDTO.setDylx(dylx);
            bdcSlDysjDTO.setDysl(dysl);
        }
        return bdcSlDysjDTO;
    }

    /**
     * @param processInsId ????????????id
     * @param dylx         ????????????
     * @param qlrlb        ???????????????
     * @param xmid         ??????id
     * @param sjclids      ????????????id
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: String ??????xml??????
     * @description????????????xml,???????????????code????????????????????????????????????????????????????????????
     */
    @Override
    public String generatePrintDataAndReplaceZd(String processInsId, String dylx, String qlrlb, String xmid, String sjclids) {
        String xmlString = this.generatePrintData(processInsId, dylx, qlrlb, xmid, sjclids);
        if (StringUtils.isNotBlank(xmlString)) {
            xmlString = replaceZdCodeToMc(xmlString);
        }
        return xmlString;
    }

    /**
     * @param configParam ??????????????????
     * @return List<Map> ????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????sql???????????????????????????
     */
    @Override
    public List<Map> executeConfigSql(Map configParam) {
        return bdcSlConfigMapper.executeConfigSql(configParam);
    }

    /**
     * @param bdcSlPrintDTO ????????????????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/4/16 17:06
     */
    @Override
    public BdcNewPrintDTO generatePrintDTO(BdcSlPrintDTO bdcSlPrintDTO) {
        LOGGER.warn("?????????????????????????????????{},??????????????????????????????{}?????????????????????????????????{}", JSON.toJSONString(bdcSlPrintDTO), djkDylxList, slkDylxList);
        //1.?????????????????????????????????????????????????????????????????????
        BdcNewPrintDTO bdcNewPrintDTO = new BdcNewPrintDTO();
        String printXmlUrl = "";
        if (ArrayUtils.contains(CommonConstantUtils.SYSTEM_VERSION_NTDS, bdcSlPrintDTO.getSystemVersion()) || Objects.equals(2, bdcSlPrintDTO.getPrintModel())) {
            //??????????????????xml??????
            //?????????????????????????????????????????????-???????????????-??????????????????-??????????????????
            if (ArrayUtils.contains(Constants.SL_SQS_DYLX, bdcSlPrintDTO.getDylx()) || ArrayUtils.contains(Constants.SL_XWBL_DYLX, bdcSlPrintDTO.getDylx()) || ArrayUtils.contains(Constants.SL_JDS_DYLX, bdcSlPrintDTO.getDylx())
                    || ArrayUtils.contains(Constants.DJ_OTHER_DYLX, bdcSlPrintDTO.getDylx())) {
                printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getZxlc() + "/xml/nt";
            }
            //????????????????????????????????????--???????????????-???????????????
            if (ArrayUtils.contains(Constants.SL_ALL_DYLX, bdcSlPrintDTO.getDylx())) {
                printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getQlrlb() + "/" + bdcSlPrintDTO.getXmid() + "/xml/datas/nt";
            }
        } else {

            //2020-4-20  ???????????????????????????????????????
            /**
             * @param bdcSlPrintDTO
             * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
             * @description //???????????????????????????????????????????????????????????????????????????sql ??????xml
             * @date : 2021/7/26 14:43
             */
            if (ArrayUtils.contains(CommonConstantUtils.Sl_DJ_DYLX, bdcSlPrintDTO.getDylx()) || djkDylxList.contains(bdcSlPrintDTO.getDylx())) {
                printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getZxlc() + "/xml" + "?sjclids=" + StringUtils.join(bdcSlPrintDTO.getSjclids(), ",");
            }
            //?????????????????????????????????????????????
            if (StringUtils.equals(bdcSlPrintDTO.getSystemVersion(), CommonConstantUtils.SYSTEM_VERSION_BB) && ArrayUtils.contains(CommonConstantUtils.Sl_DJ_DYLX_BB, bdcSlPrintDTO.getDylx())) {
                printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getZxlc() + "/xml/bb" + "?sjclids=" + StringUtils.join(bdcSlPrintDTO.getSjclids(), ",") + "&xmid=" + bdcSlPrintDTO.getXmid();
            }
            if (ArrayUtils.contains(CommonConstantUtils.Sl_ALL_DYLX, bdcSlPrintDTO.getDylx()) || slkDylxList.contains(bdcSlPrintDTO.getDylx())) {
                Integer lclx = bdcXmFeignService.makeSureBdcXmLx(bdcSlPrintDTO.getGzlslid());
                if (CommonConstantUtils.LCLX_PL.equals(lclx) || CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
                    bdcSlPrintDTO.setDylx(bdcSlPrintDTO.getDylx() + "pl");
                }
                printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getQlrlb() + "/" + bdcSlPrintDTO.getXmid() + "/xml/datas" + "?sjclids=" + StringUtils.join(bdcSlPrintDTO.getSjclids(), ",");
            }
            if (ArrayUtils.contains(CommonConstantUtils.SL_ZD_DYLX, bdcSlPrintDTO.getDylx())) {
                printXmlUrl = acceptUrl + PROJECT_PATH + "zd/" + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getQlrlb() + "/" + bdcSlPrintDTO.getXmid() + "/xml/datas" + "?sjclids=" + StringUtils.join(bdcSlPrintDTO.getSjclids(), ",");
            }
            if (ArrayUtils.contains(CommonConstantUtils.SL_ZDY_URL_DYLX, bdcSlPrintDTO.getDylx())) {
                printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getZdyXmlUrl();
            }
            //????????????????????????????????????????????????????????????
            if (Objects.equals(3, bdcSlPrintDTO.getPrintModel()) && sjdDylxList.contains(bdcSlPrintDTO.getDylx())) {
                printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getQlrlb() + "/" + bdcSlPrintDTO.getXmid() + "/xml/dypz/sl" + "?sjclids=" + StringUtils.join(bdcSlPrintDTO.getSjclids(), ",");
            }
            // ?????????????????????????????????????????????
            if (Objects.equals(3, bdcSlPrintDTO.getPrintModel()) && sqsDylxList.contains(bdcSlPrintDTO.getDylx())) {
                printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getZxlc() + "/xml/dypz";
            }

            if (Objects.equals(3, bdcSlPrintDTO.getPrintModel()) && ("jfspl".equals(bdcSlPrintDTO.getDylx()) || "dyaqjfspl".equals(bdcSlPrintDTO.getDylx()))) {
                printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslid() + "/" + bdcSlPrintDTO.getDylx() + "/jfs/xml";
            }
        }
        bdcNewPrintDTO.setDataurl(printXmlUrl);
        bdcNewPrintDTO.setAppName(CommonConstantUtils.ACCEPT_DM);
        bdcNewPrintDTO.setHidemodel(hidemodel);
        return bdcNewPrintDTO;
    }

    @Override
    public BdcNewPrintDTO generatePrintPlDTO(BdcSlPrintDTO bdcSlPrintDTO) {
        BdcNewPrintDTO bdcNewPrintDTO = new BdcNewPrintDTO();
        String printXmlUrl = "";
        // ??????
        if (ArrayUtils.contains(CommonConstantUtils.Sl_DJ_DYLX, bdcSlPrintDTO.getDylx()) || djkDylxList.contains(bdcSlPrintDTO.getDylx())) {
            printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslids() + "/" + bdcSlPrintDTO.getDylx() + "/" + bdcSlPrintDTO.getZxlc() + "/xml/pl" + "?sjclids=" + StringUtils.join(bdcSlPrintDTO.getSjclids(), ",");
        }
        // ??????
        if (ArrayUtils.contains(CommonConstantUtils.Sl_ALL_DYLX, bdcSlPrintDTO.getDylx()) || slkDylxList.contains(bdcSlPrintDTO.getDylx())) {
            printXmlUrl = acceptUrl + PROJECT_PATH + bdcSlPrintDTO.getGzlslids() + "/" + bdcSlPrintDTO.getDylx() + "/xml/datas/pl?qlrlb=" + bdcSlPrintDTO.getQlrlb() + "&xmid=" + bdcSlPrintDTO.getXmid() + "&sjclids=" + StringUtils.join(bdcSlPrintDTO.getSjclids(), ",");
        }
        bdcNewPrintDTO.setDataurl(printXmlUrl);
        bdcNewPrintDTO.setAppName(CommonConstantUtils.ACCEPT_DM);
        bdcNewPrintDTO.setHidemodel(hidemodel);
        return bdcNewPrintDTO;
    }

    /**
     * ????????????????????? fwlx@1 ????????????????????????
     */
    private static Pattern FIND_PATTERN = Pattern.compile("[a-zA-Z]+\\@[0-9]+");

    // ?????????????????????????????????dm??????
    private String replaceZdCodeToMc(String xmlStr) {
        //??????????????????????????????
        Map<String, List<Map>> allDjZdMap = bdcZdFeignService.listBdcZd();
        // ??????????????????????????????fwlx@1?????????????????????
        Matcher matcher = FIND_PATTERN.matcher(xmlStr);
        while (matcher.find()) {
            String matchValue = matcher.group();
            String[] matchValueAry = matchValue.split("\\@");
            String zdType = matchValueAry[0];
            String zdKey = matchValueAry[1];
            if (CollectionUtils.isEmpty(allDjZdMap.get(zdType))) {
                continue;
            }
            String zdMc = StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(zdKey), allDjZdMap.get(zdType));
            xmlStr = xmlStr.replace(matchValue, zdMc);
        }

        //????????????????????????????????? fwlx@ ,
        xmlStr = xmlStr.replaceAll("[a-zA-z]+\\@", "");
        return xmlStr;
    }

    //????????????????????????,????????????????????????
    private void getSfdEwm(String dylx, String gzlslid, String xmid, String slbh, Map configParam) {
        String mainPath = acceptUrl + PROJECT_PATH;
        //29300  ?????????????????????????????????jfsewmurl????????????????????????????????????????????????????????????
        if ((ArrayUtils.contains(CommonConstantUtils.SFDEWM_DYLX, dylx) || ArrayUtils.contains(Constants.SL_SJD_DYLX, dylx) || slkDylxList.contains(dylx))
                && StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(xmid)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByXmid(xmid);

            // ????????????????????????????????????????????? 1??????????????????????????????????????????????????????  2???????????????????????????????????????????????????
            boolean sfdydgewm = (ArrayUtils.contains(Constants.SL_SJD_DYLX, dylx) && sfdgewm)
                    || (CollectionUtils.isNotEmpty(dydgewmlx) && dydgewmlx.contains(dylx));
            if (CollectionUtils.isEmpty(bdcSlSfxxDOList) || sfdydgewm) {
                bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            }
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                //??????xmid?????????
                bdcSlSfxxDOList.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
                Double qlrywrsfhj = 0.00;
                List<String> jfrList = new ArrayList<>(bdcSlSfxxDOList.size());
                List<BdcSlSfxxDO> newSfxxList = new ArrayList<>(bdcSlSfxxDOList.size());
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    String jfrxm = bdcSlSfxxDO.getJfrxm();
                    if (!jfrList.contains(jfrxm)) {
                        jfrList.add(jfrxm);
                        newSfxxList.add(bdcSlSfxxDO);
                    }
                    if (bdcSlSfxxDO.getHj() != null) {
                        qlrywrsfhj += bdcSlSfxxDO.getHj();
                    }
                    if (CommonConstantUtils.QLRLB_QLR.equals(bdcSlSfxxDO.getQlrlb())) {
                        if (StringUtils.isNotBlank(bdcSlSfxxDO.getJfsewmurl())) {
                            configParam.put("qlrsfdewm", mainPath + bdcSlSfxxDO.getJfsewmurl() + "/ewm");
                            try {
                                configParam.put("ntqlrsfdewm", mainPath + bdcSlSfxxDO.getSfxxid() + "/ewm?ewmurl=" + URLEncoder.encode(bdcSlSfxxDO.getJfsewmurl(), "utf-8"));
                            } catch (UnsupportedEncodingException e) {
                                LOGGER.error("???????????????????????????????????????:{}", e.getMessage());
                            }
                        }
                        //???????????????????????????
                        try {
                            configParam.put("qlrsfhjdx", ChangeMoneyToCnUtils.convert(ChangeMoneyToCnUtils.moneyFormat(bdcSlSfxxDO.getHj() != null ? bdcSlSfxxDO.getHj().toString() : "0")));
                        } catch (Exception e) {
                            LOGGER.error("????????????,object:{}", bdcSlSfxxDO.getHj());
                        }

                        //????????????????????????????????????????????????????????????????????????????????????
                        configParam.put("sfdewm", this.getYkqewm(bdcSlSfxxDO.getSfxxid(), slbh, mainPath, CommonConstantUtils.SF_F_DM));

                        // ????????????????????????????????????????????????
                        if (StringUtils.isNotBlank(bdcSlSfxxDO.getJfsbm()) && StringUtils.isNotBlank(bdcSlSfxxDO.getJypzh())) {
                            BdcSlSfxxDO bdcSlSfxxDOByJfsbm = new BdcSlSfxxDO();
                            bdcSlSfxxDOByJfsbm.setJfsbm(bdcSlSfxxDO.getJfsbm());
                            bdcSlSfxxDOByJfsbm.setJypzh(bdcSlSfxxDO.getJypzh());
                            List<BdcSlSfxxDO> bdcSlSfxxDOListByJfsbm = bdcSlSfxxService.queryBdcSlSfxx(bdcSlSfxxDOByJfsbm);
                            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOListByJfsbm)) {
                                Double qlrdjfyjhj = NumberUtil.formatDigit(bdcSlSfxxDOListByJfsbm.stream()
                                        .filter(sfxxItem -> Objects.nonNull(sfxxItem.getHj()))
                                        .mapToDouble(BdcSlSfxxDO::getHj).sum(), 2);

                                configParam.put("qlrdjfyjhj", qlrdjfyjhj);
                                try {
                                    configParam.put("qlrdjfyjhjdx", ChangeMoneyToCnUtils.convert(ChangeMoneyToCnUtils.moneyFormat(qlrdjfyjhj != null ? qlrdjfyjhj.toString() : "0")));
                                } catch (Exception e) {
                                    LOGGER.error("????????????,object:{}", qlrdjfyjhj);
                                }
                            }
                        }

                    } else if (CommonConstantUtils.QLRLB_YWR.equals(bdcSlSfxxDO.getQlrlb())) {
                        if (StringUtils.isNotBlank(bdcSlSfxxDO.getJfsewmurl())) {
                            configParam.put("ywrsfdewm", mainPath + bdcSlSfxxDO.getJfsewmurl() + "/ewm");
                            try {
                                configParam.put("ntywrsfdewm", mainPath + bdcSlSfxxDO.getSfxxid() + "/ewm?ewmurl=" + URLEncoder.encode(bdcSlSfxxDO.getJfsewmurl(), "utf-8"));
                            } catch (UnsupportedEncodingException e) {
                                LOGGER.error("???????????????????????????????????????:{}", e.getMessage());
                            }
                        }
                        //???????????????????????????
                        try {
                            configParam.put("ywrsfhjdx", ChangeMoneyToCnUtils.convert(ChangeMoneyToCnUtils.moneyFormat(bdcSlSfxxDO.getHj() != null ? bdcSlSfxxDO.getHj().toString() : "0")));
                        } catch (Exception e) {
                            LOGGER.error("????????????,object:{}", bdcSlSfxxDO.getHj());
                        }


                    }

                    //?????????????????????
                    try {
                        configParam.put("qlrywrsfhjdx", ChangeMoneyToCnUtils.convert(ChangeMoneyToCnUtils.moneyFormat(qlrywrsfhj != null ? qlrywrsfhj.toString() : "0")));
                    } catch (Exception e) {
                        LOGGER.error("????????????,object:{}", qlrywrsfhj);
                    }
                }
                //?????????????????????????????????????????????????????????
                if (CollectionUtils.isNotEmpty(newSfxxList)) {
                    newSfxxList.sort(Comparator.comparing(BdcSlSfxxDO::getXmid));
                    // ??????????????????????????????
                    Map<String, List<BdcSlSfxxDO>> groupQlrlbMap = newSfxxList.stream().filter(t -> StringUtils.isNotBlank(t.getQlrlb()))
                            .collect(Collectors.groupingBy(BdcSlSfxxDO::getQlrlb));
                    List<BdcSlSfxxDO> qlrsfxxList = groupQlrlbMap.get(CommonConstantUtils.QLRLB_QLR);
                    List<BdcSlSfxxDO> ywrsfxxList = groupQlrlbMap.get(CommonConstantUtils.QLRLB_YWR);
                    if (CollectionUtils.isNotEmpty(qlrsfxxList)) {
                        for (int i = 0, j = 1; i < qlrsfxxList.size(); i++, j++) {
                            if (StringUtils.isNotBlank(qlrsfxxList.get(i).getJfsewmurl())) {
                                try {
                                    configParam.put("ntqlrsfdewm" + j, mainPath + qlrsfxxList.get(i).getSfxxid() + "/ewm?ewmurl=" + URLEncoder.encode(qlrsfxxList.get(i).getJfsewmurl(), "utf-8"));
                                    configParam.put("ntqlrjfxm" + j, qlrsfxxList.get(i).getJfrxm());
                                } catch (UnsupportedEncodingException e) {
                                    LOGGER.error("???????????????????????????????????????:{}", e.getMessage());
                                }
                            } else {
                                configParam.put("ntqlrsfdewm" + j, "");
                                configParam.put("ntqlrjfxm" + j, "");
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(ywrsfxxList)) {
                        for (int i = 0, j = 1; i < ywrsfxxList.size(); i++, j++) {
                            if (StringUtils.isNotBlank(ywrsfxxList.get(i).getJfsewmurl())) {
                                try {
                                    configParam.put("ntywrsfdewm" + j, mainPath + ywrsfxxList.get(i).getSfxxid() + "/ewm?ewmurl=" + URLEncoder.encode(ywrsfxxList.get(i).getJfsewmurl(), "utf-8"));
                                    configParam.put("ntywrjfxm" + j, ywrsfxxList.get(i).getJfrxm());
                                } catch (UnsupportedEncodingException e) {
                                    LOGGER.error("???????????????????????????????????????:{}", e.getMessage());
                                }
                            } else {
                                configParam.put("ntywrsfdewm" + j, "");
                                configParam.put("ntywrjfxm" + j, "");
                            }
                        }
                    }

                    // ?????????????????????????????? ??? ????????????????????????
                    this.getCzSfdEwm(configParam, qlrsfxxList, ywrsfxxList);
                }
            }
        }
        // ????????????????????????"" ,????????????????????????????????????????????????
        configParam.put("qlrsfdewm", CommonUtil.getOrElse(configParam.get("qlrsfdewm"), ""));
        configParam.put("ywrsfdewm", CommonUtil.getOrElse(configParam.get("ywrsfdewm"), ""));
        configParam.put("ntqlrsfdewm", CommonUtil.getOrElse(configParam.get("ntqlrsfdewm"), ""));
        configParam.put("ntywrsfdewm", CommonUtil.getOrElse(configParam.get("ntywrsfdewm"), ""));
        configParam.put("sfdewm", CommonUtil.getOrElse(configParam.get("sfdewm"), ""));
        configParam.put("ntywrsfdewm1", CommonUtil.getOrElse(configParam.get("ntywrsfdewm1"), ""));
        configParam.put("ntywrsfdewm2", CommonUtil.getOrElse(configParam.get("ntywrsfdewm2"), ""));
        configParam.put("ntywrsfdewm3", CommonUtil.getOrElse(configParam.get("ntywrsfdewm3"), ""));
        configParam.put("ntywrjfxm1", CommonUtil.getOrElse(configParam.get("ntywrjfxm1"), ""));
        configParam.put("ntywrjfxm2", CommonUtil.getOrElse(configParam.get("ntywrjfxm2"), ""));
        configParam.put("ntywrjfxm3", CommonUtil.getOrElse(configParam.get("ntywrjfxm3"), ""));
        configParam.put("ntqlrsfdewm1", CommonUtil.getOrElse(configParam.get("ntqlrsfdewm1"), ""));
        configParam.put("ntqlrsfdewm2", CommonUtil.getOrElse(configParam.get("ntqlrsfdewm2"), ""));
        configParam.put("ntqlrsfdewm3", CommonUtil.getOrElse(configParam.get("ntqlrsfdewm3"), ""));
        configParam.put("ntqlrjfxm1", CommonUtil.getOrElse(configParam.get("ntqlrjfxm1"), ""));
        configParam.put("ntqlrjfxm2", CommonUtil.getOrElse(configParam.get("ntqlrjfxm2"), ""));
        configParam.put("ntqlrjfxm3", CommonUtil.getOrElse(configParam.get("ntqlrjfxm3"), ""));
        configParam.put("czqlrsfdewm1", CommonUtil.getOrElse(configParam.get("czqlrsfdewm1"), ""));
        configParam.put("czqlrsfdewm2", CommonUtil.getOrElse(configParam.get("czqlrsfdewm2"), ""));
        configParam.put("czywrsfdewm1", CommonUtil.getOrElse(configParam.get("czywrsfdewm1"), ""));
        configParam.put("czywrsfdewm2", CommonUtil.getOrElse(configParam.get("czywrsfdewm2"), ""));
        configParam.put("czqlrtdsyjewm1", CommonUtil.getOrElse(configParam.get("czqlrtdsyjewm1"), ""));
        configParam.put("czqlrtdsyjewm2", CommonUtil.getOrElse(configParam.get("czqlrtdsyjewm2"), ""));
        configParam.put("czywrtdsyjewm1", CommonUtil.getOrElse(configParam.get("czywrtdsyjewm1"), ""));
        configParam.put("czywrtdsyjewm2", CommonUtil.getOrElse(configParam.get("czywrtdsyjewm2"), ""));
    }

    /**
     * ???????????????????????????????????????
     */
    private void getYczfSfEwm(String dylx, String gzlslid, String qlrlb, Map configParam) {
        String mainPath = acceptUrl + PROJECT_PATH;
        if (slkDylxList.contains(dylx) && StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(qlrlb)) {
            // ?????????????????????????????????
            if(StringUtils.equals(CommonConstantUtils.QLRLB_QLR, qlrlb)) {
                BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
                bdcSlSfssDdxxQO.setGzlslid(gzlslid);
                bdcSlSfssDdxxQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
                if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
                    BdcSlSfssDdxxDO bdcSlSfssDdxxDO = bdcSlSfssDdxxDOList.get(0);
                    Double yczfqlrsfhj = bdcSlSfssDdxxDO.getZe();
                    try {
                        // ???????????????????????????????????????
                        configParam.put("yczfqlrsfhjdx", ChangeMoneyToCnUtils.convert(ChangeMoneyToCnUtils.moneyFormat(yczfqlrsfhj != null ? yczfqlrsfhj.toString() : "0")));
                    } catch (Exception e) {
                        LOGGER.error("????????????,object:{}", bdcSlSfssDdxxDO.getZe());
                    }
                    // ????????????????????????????????????
                    String jfurl = bdcSlSfssDdxxDO.getJfurl();
                    if (StringUtils.isNotBlank(jfurl)) {
                        try {
                            configParam.put("yczfqlrsfdewm", mainPath + bdcSlSfssDdxxDO.getId() + "/ewm?ewmurl=" + URLEncoder.encode(jfurl, "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            LOGGER.error("???????????????????????????????????????????????????:{}", e.getMessage());
                        }
                    }
                }
            }
            // ?????????????????????????????????
            if(StringUtils.equals(CommonConstantUtils.QLRLB_YWR, qlrlb)) {
                BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
                bdcSlSfssDdxxQO.setGzlslid(gzlslid);
                bdcSlSfssDdxxQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
                if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
                    BdcSlSfssDdxxDO bdcSlSfssDdxxDO = bdcSlSfssDdxxDOList.get(0);
                    Double yczfywrsfhj = bdcSlSfssDdxxDO.getZe();
                    try {
                        // ???????????????????????????????????????
                        configParam.put("yczfywrsfhjdx", ChangeMoneyToCnUtils.convert(ChangeMoneyToCnUtils.moneyFormat(yczfywrsfhj != null ? yczfywrsfhj.toString() : "0")));
                    } catch (Exception e) {
                        LOGGER.error("????????????,object:{}", bdcSlSfssDdxxDO.getZe());
                    }
                    // ????????????????????????????????????
                    String jfurl = bdcSlSfssDdxxDO.getJfurl();
                    if (StringUtils.isNotBlank(jfurl)) {
                        try {
                            configParam.put("yczfywrsfdewm", mainPath + bdcSlSfssDdxxDO.getId() + "/ewm?ewmurl=" + URLEncoder.encode(jfurl, "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            LOGGER.error("???????????????????????????????????????????????????:{}", e.getMessage());
                        }
                    }
                }
            }
        }
        // ????????????????????????"" ,????????????????????????????????????????????????
        configParam.put("yczfqlrsfhjdx", CommonUtil.getOrElse(configParam.get("yczfqlrsfhjdx"), ""));
        configParam.put("yczfqlrsfdewm", CommonUtil.getOrElse(configParam.get("yczfqlrsfdewm"), ""));
        configParam.put("yczfywrsfhjdx", CommonUtil.getOrElse(configParam.get("yczfywrsfhjdx"), ""));
        configParam.put("yczfywrsfdewm", CommonUtil.getOrElse(configParam.get("yczfywrsfdewm"), ""));
    }
    /**
     * ???????????????????????????????????????
     */
    private void getFpxxEwm(String dylx, String gzlslid, String xmid, boolean sfpllc, Map configParam) {
        if (ArrayUtils.contains(Constants.SL_SJD_DYLX, dylx) && StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(xmid)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList;
            //?????????????????????????????????id???????????????
            if (sfpllc || StringUtils.equals(CommonConstantUtils.DYLX_ZHSJD, dylx)) {
                bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            } else {
                bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByXmid(xmid);
            }
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                Map<String, List<BdcSlSfxxDO>> groupQlrlbMap = bdcSlSfxxDOList.stream().filter(t -> StringUtils.isNotBlank(t.getQlrlb()))
                        .collect(Collectors.groupingBy(BdcSlSfxxDO::getQlrlb));
                List<BdcSlSfxxDO> qlrsfxxList = groupQlrlbMap.get(CommonConstantUtils.QLRLB_QLR);
                List<BdcSlSfxxDO> ywrsfxxList = groupQlrlbMap.get(CommonConstantUtils.QLRLB_YWR);
                // ???????????????fpxx ?????????
                this.addFpxxEwm("qlrfpxxewm", qlrsfxxList, configParam);
                // ???????????????fpxx ?????????
                this.addFpxxEwm("ywrfpxxewm", ywrsfxxList, configParam);
            }
        }

        configParam.put("qlrfpxxewm1", CommonUtil.getOrElse(configParam.get("qlrfpxxewm1"), ""));
        configParam.put("ywrfpxxewm1", CommonUtil.getOrElse(configParam.get("ywrfpxxewm1"), ""));
        configParam.put("qlrfpxxewm2", CommonUtil.getOrElse(configParam.get("qlrfpxxewm2"), ""));
        configParam.put("ywrfpxxewm2", CommonUtil.getOrElse(configParam.get("ywrfpxxewm2"), ""));
        configParam.put("qlrfpxxewm3", CommonUtil.getOrElse(configParam.get("qlrfpxxewm3"), ""));
        configParam.put("ywrfpxxewm3", CommonUtil.getOrElse(configParam.get("ywrfpxxewm3"), ""));
    }

    // ??????fpxx????????????
    private void addFpxxEwm(String key, List<BdcSlSfxxDO> sfxxList, Map configParam) {
        // key : qlrfpxxewm  ywrfpxxewm
        String mainPath = acceptUrl + PROJECT_PATH;
        if (CollectionUtils.isNotEmpty(sfxxList)) {
            List<String> fpxxEwmList = new ArrayList<>(sfxxList.size());
            for (BdcSlSfxxDO bdcSlSfxxDO : sfxxList) {
                List<BdcSlFpxxDO> bdcSlFpxxDOS = this.bdcSlFpxxService.queryBdcSlFpxxBySfxxid(bdcSlSfxxDO.getSfxxid());
                if (CollectionUtils.isNotEmpty(bdcSlFpxxDOS) && StringUtils.isNotBlank(bdcSlFpxxDOS.get(0).getFpurl())) {
                    try {
                        fpxxEwmList.add(mainPath + bdcSlFpxxDOS.get(0).getFpxxid() + "/ewm?ewmurl=" + URLEncoder.encode(bdcSlFpxxDOS.get(0).getFpurl(), "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        LOGGER.error("????????????????????????????????????:{}", e.getMessage());
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(fpxxEwmList)) {
                int i = 1;
                for (String fpxxewm : fpxxEwmList) {
                    configParam.put(key + i++, fpxxewm);
                }
            }
        }
    }

    // ?????????????????????????????????????????????????????????
    public void getCzSfdEwm(Map configParam, List<BdcSlSfxxDO> qlrsfxxList, List<BdcSlSfxxDO> ywrsfxxList) {
        String mainPath = acceptUrl + PROJECT_PATH;
        if (CollectionUtils.isNotEmpty(qlrsfxxList) && StringUtils.isNotBlank(qlrsfxxList.get(0).getSfxxid())) {
            for (int i = 0, j = 1; i < qlrsfxxList.size(); i++, j++) {
                Map<String, String> ewmMap = this.bdcSfxxService.getSfEwmAndTdsyjEwm(qlrsfxxList.get(i).getSfxxid());
                if (MapUtils.isNotEmpty(ewmMap)) {
                    if (ewmMap.containsKey("djewm") && StringUtils.isNotBlank(ewmMap.get("djewm"))) {
                        try {
                            configParam.put("czqlrsfdewm" + j, mainPath + qlrsfxxList.get(i).getSfxxid() + "/ewm?ewmurl=" + URLEncoder.encode(ewmMap.get("djewm"), "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            LOGGER.error("???????????????????????????????????????:{}", e.getMessage());
                        }
                    } else {
                        configParam.put("czqlrsfdewm" + j, "");
                    }
                    if (ewmMap.containsKey("tdsyjewm") && StringUtils.isNotBlank(ewmMap.get("tdsyjewm"))) {
                        try {
                            configParam.put("czqlrtdsyjewm" + j, mainPath + qlrsfxxList.get(i).getSfxxid() + "/ewm?ewmurl=" + URLEncoder.encode(ewmMap.get("tdsyjewm"), "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            LOGGER.error("?????????????????????????????????????????????:{}", e.getMessage());
                        }
                    } else {
                        configParam.put("czqlrtdsyjewm" + j, "");
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(ywrsfxxList) && StringUtils.isNotBlank(ywrsfxxList.get(0).getSfxxid())) {
            for (int i = 0, j = 1; i < ywrsfxxList.size(); i++, j++) {
                Map<String, String> ewmMap = this.bdcSfxxService.getSfEwmAndTdsyjEwm(ywrsfxxList.get(i).getSfxxid());
                if (MapUtils.isNotEmpty(ewmMap)) {
                    if (ewmMap.containsKey("djewm") && StringUtils.isNotBlank(ewmMap.get("djewm"))) {
                        try {
                            configParam.put("czywrsfdewm" + j, mainPath + ywrsfxxList.get(i).getSfxxid() + "/ewm?ewmurl=" + URLEncoder.encode(ewmMap.get("djewm"), "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            LOGGER.error("???????????????????????????????????????:{}", e.getMessage());
                        }
                    } else {
                        configParam.put("czywrsfdewm" + j, "");
                    }
                    if (ewmMap.containsKey("tdsyjewm") && StringUtils.isNotBlank(ewmMap.get("tdsyjewm"))) {
                        try {
                            configParam.put("czywrtdsyjewm" + j, mainPath + ywrsfxxList.get(i).getSfxxid() + "/ewm?ewmurl=" + URLEncoder.encode(ewmMap.get("tdsyjewm"), "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            LOGGER.error("?????????????????????????????????????????????:{}", e.getMessage());
                        }
                    } else {
                        configParam.put("czywrtdsyjewm" + j, "");
                    }
                }
            }
        }

    }


    /**
     * @param bdcXmDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????????????????
     * @date : 2020/2/24 15:51
     */
    private BdcDysjDTO generateYcsdqData(BdcXmDO bdcXmDO, String dylx) {
        // ???????????????xmid?????? ??????gzlslid??????????????????????????????????????????????????????
        if (StringUtils.isEmpty(bdcXmDO.getXmid()) && !StringUtils.isEmpty(bdcXmDO.getGzlslid())) {
            //???????????????????????????????????????????????????????????????  ??????????????????
            Map configParam = Maps.newHashMap();
            configParam.put("gzlslid", StringUtils.isNotBlank(bdcXmDO.getGzlslid()) ? bdcXmDO.getGzlslid() : "");
            List<Map> listMap1 = bdcSdqghFeignService.getSdqSqbYwDyData(bdcXmDO.getGzlslid());
            if (CollectionUtils.isNotEmpty(listMap1)) {
                Map map = listMap1.get(0);
                configParam.put("shui_consno", map.get("SHUI_CONSNO") == null ?
                        "" : map.get("SHUI_CONSNO"));
                configParam.put("shui_yhm", map.get("SHUI_YHM") == null ?
                        "" : map.get("SHUI_YHM"));
                configParam.put("shui_yzl", map.get("SHUI_YZL") == null ?
                        "" : map.get("SHUI_YZL"));
                configParam.put("dian_consno", map.get("DIAN_CONSNO") == null ?
                        "" : map.get("DIAN_CONSNO"));
                configParam.put("dian_yhm", map.get("DIAN_YHM") == null ?
                        "" : map.get("DIAN_YHM"));
                configParam.put("dian_yzl", map.get("DIAN_YZL") == null ?
                        "" : map.get("DIAN_YZL"));
                configParam.put("qi_consno", map.get("QI_CONSNO") == null ?
                        "" : map.get("QI_CONSNO"));
                configParam.put("qi_yhm", map.get("QI_YHM") == null ?
                        "" : map.get("QI_YHM"));
                configParam.put("qi_yzl", map.get("QI_YZL") == null ?
                        "" : map.get("QI_YZL"));
            }
            List<Map> listMapTemp = bdcSlXmLsgxService.sxxBdcXx(bdcXmDO.getGzlslid());
            if (CollectionUtils.isNotEmpty(listMapTemp)) {// ?????????????????????????????????
                Map map = listMapTemp.get(0);
                configParam.put("zl", map.get("ZL") == null ?
                        "" : map.get("ZL"));
                configParam.put("bdcdyh", map.get("BDCDYH") == null ?
                        "" : map.get("BDCDYH"));
                configParam.put("slbh", map.get("SLBH") == null ?
                        "" : map.get("SLBH"));
                configParam.put("qlr", map.get("QLR") == null ?
                        "" : map.get("QLR"));
                configParam.put("qlrzjh", map.get("QLRZJH") == null ?
                        "" : map.get("QLRZJH"));
                configParam.put("yqlr", map.get("YQLR") == null ?
                        "" : map.get("YQLR"));
                configParam.put("yqlrzjh", map.get("YQLRZJH") == null ?
                        "" : map.get("YQLRZJH"));
                configParam.put("ycqzh", map.get("YCQZH") == null ?
                        "" : map.get("YCQZH"));
            } else {// ??????????????????????????????????????????????????????????????????????????????????????????
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(bdcXmDO.getGzlslid());
                if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getJbxxid())) {
                    List<BdcSlXmDO> listSlxm = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
                    if (CollectionUtils.isNotEmpty(listSlxm)) {
                        BdcSlXmDO bdcSlXmDO = listSlxm.get(0);
                        String xmid = bdcSlXmDO.getXmid();
                        configParam.put("zl", bdcSlXmDO.getZl());
                        configParam.put("bdcdyh", bdcSlXmDO.getBdcdyh());
                        configParam.put("slbh", bdcSlJbxxDO.getSlbh());
                        List<BdcSlSqrDO> list = bdcSlSqrService.listBdcSlSqrByXmid(xmid, CommonConstantUtils.QLRLB_QLR);
                        if (CollectionUtils.isNotEmpty(list)) {
                            configParam.put("qlr", list.get(0).getSqrmc());
                            configParam.put("qlrzjh", list.get(0).getZjh());
                        } else {
                            configParam.put("qlr", "");
                            configParam.put("qlrzjh", "");
                        }
                        List<BdcSlXmLsgxDO> listLsgx = bdcSlXmLsgxService.listBdcSlXmLsgxByXmid(xmid);
                        if (CollectionUtils.isNotEmpty(listLsgx) && StringUtils.isNotBlank(listLsgx.get(0).getYxmid())) {
                            BdcXmQO bdcXmQO = new BdcXmQO();
                            bdcXmQO.setXmid(listLsgx.get(0).getYxmid());
                            List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
                            if (CollectionUtils.isNotEmpty(listXm)) {
                                configParam.put("yqlr", listXm.get(0).getQlr());
                                configParam.put("yqlrzjh", listXm.get(0).getQlrzjh());
                                configParam.put("ycqzh", listXm.get(0).getBdcqzh());
                            }
                        } else {
                            configParam.put("yqlr", "");
                            configParam.put("yqlrzjh", "");
                            configParam.put("ycqzh", "");
                        }
                    } else {
                        configParam.put("zl", "");
                        configParam.put("bdcdyh", "");
                        configParam.put("slbh", "");
                        configParam.put("qlr", "");
                        configParam.put("qlrzjh", "");
                        configParam.put("yqlr", "");
                        configParam.put("yqlrzjh", "");
                        configParam.put("ycqzh", "");
                    }
                }
            }
            LOGGER.info("????????????:{}", dylx);
            //??????sql???????????????????????????????????????
            BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
            bdcDysjPzDO.setDylx(dylx);
            List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
            BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
            bdcDysjZbPzDO.setDylx(dylx);
            List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDypzFeignService.listBdcDysjZbPz(bdcDysjZbPzDO);
            Map datas = bdcDysjPzService.queryPrintDatasList(configParam, "bdcSlConfigMapper", bdcDysjPzDOList);
            Multimap detail = bdcDysjPzService.queryPrintDetailList(configParam, "bdcSlConfigMapper", bdcDysjZbPzDOList);
            BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
            bdcDysjDTO.setDylx(dylx);
            bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
            bdcDysjDTO.setDysj(JSONObject.toJSONString(datas));
            bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(detail));
            return bdcDysjDTO;
        } else {
            LOGGER.info("????????????:{}", dylx);
            return null;
        }
    }

    /**
     * @param configParam
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????
     * @date : 2020/2/24 15:51
     */
    private void generateSfdData(BdcXmDO bdcXmDO, String qlrlb, Map configParam, Boolean sfpllc, String mainPath) {
        BdcSlSfxxQO bdcSlSfxxQO = getSfxx(bdcXmDO.getGzlslid(), bdcXmDO.getXmid(), qlrlb, bdcXmDO.getDjxl());
        if (Objects.nonNull(bdcSlSfxxQO)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList;
            //?????????????????????????????????id???????????????
            if (sfpllc) {
                bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(bdcXmDO.getGzlslid());
            } else {
                bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByXmid(bdcXmDO.getXmid());
            }
            //????????????????????????gzlslid????????????????????????????????????????????????
            if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
                bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(bdcXmDO.getGzlslid());
            }
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if (StringUtils.equals(bdcSlSfxxDO.getQlrlb(), qlrlb)) {
                        configParam.put("sfxxid", bdcSlSfxxDO.getSfxxid());
                        //?????????????????????????????????????????????
                        configParam.put("sfdewm", this.getEwmByYkqAndJfse(bdcSlSfxxDO, bdcXmDO.getSlbh(), mainPath));

                        // ?????????????????????????????????????????? -2020/3/5
                        if (StringUtils.isNotBlank(bdcSlSfxxDO.getJfsewmurl())) {
                            configParam.put("jksbewm", mainPath + bdcSlSfxxDO.getJfsewmurl() + "/ewm");
                        }

                        //?????????????????????,?????????????????????????????????????????????
                        try {
                            configParam.put("dxhj", ChangeMoneyToCnUtils.convert(ChangeMoneyToCnUtils.moneyFormat(bdcSlSfxxDO.getHj() != null ? bdcSlSfxxDO.getHj().toString() : "0")));
                        } catch (Exception e) {
                            LOGGER.error("????????????{},object:{}", JSONObject.toJSONString(bdcSlSfxxDO.getHj()), e);
                        }
                        break;
                    }
                }
            }
            // ??????????????????????????????????????????????????????sfxxid???????????????????????????
            configParam.put("sfxxid", CommonUtil.getOrElse(configParam.get("sfxxid"), ""));
            configParam.put("dxhj", CommonUtil.getOrElse(configParam.get("dxhj"), ""));
            configParam.put("sfdewm", CommonUtil.getOrElse(configParam.get("sfdewm"), ""));
            configParam.put("jksbewm", CommonUtil.getOrElse(configParam.get("jksbewm"), ""));

            configParam.put("dzwmj", null == bdcSlSfxxQO.getDzwmj() ? "" : bdcSlSfxxQO.getDzwmj());
            configParam.put("zdzhmj", bdcSlSfxxQO.getZdzhmj());
            configParam.put("dlrmc", CommonUtil.getOrElse(bdcSlSfxxQO.getDlrmc(), ""));
            configParam.put("dlrlxdh", CommonUtil.getOrElse(bdcSlSfxxQO.getDlrlxdh(), ""));
            configParam.put("qlr", CommonUtil.getOrElse(bdcSlSfxxQO.getQlrmc(), ""));
            configParam.put("ywr", CommonUtil.getOrElse(bdcSlSfxxQO.getYwrmc(), ""));
            configParam.put("qlrlb", qlrlb);
        }
    }

    //???????????????????????????????????????????????????
    private String getYkqewm(String sfxxid, String slbh, String mainPath, Integer sfyj) {
        if (StringUtils.isNotBlank(sfdewm)) {
            String token = "";
            if (sfdewm.contains("@token")) {
                //???????????????token
                try {
                    JSONObject map = new JSONObject();
                    Object response = exchangeInterfaceFeignService.requestInterface("ykq_token", map);
                    LOGGER.info("???????????????token:{}", response);
                    if (response != null) {
                        Map responesMap = (Map) response;
                        if (responesMap.containsKey("token") && null != responesMap.get("token")) {
                            token = responesMap.get("token").toString();
                        }

                    }
                } catch (Exception e) {
                    LOGGER.error("???????????????token??????:{}", e);
                }
            }
            String jfType = "", zflx ="";
            if(Objects.equals(sfyj, CommonConstantUtils.SF_F_DM) && StringUtils.isNotBlank(sfxxid)){
                BdcSlSfxxDO bdcSlSfxxDO = this.bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
                if(Objects.nonNull(bdcSlSfxxDO)){
                    // ??????????????????2 ??????????????????
                    if(Objects.nonNull(bdcSlSfxxDO.getHyzfjftj()) && Objects.equals(2, bdcSlSfxxDO.getHyzfjftj())){
                        jfType = "DK";
                    }
                    // ???????????????????????? 1.?????????2.??????????????? ????????????????????????0???????????????,1????????????,2????????????
                    if(Objects.nonNull(bdcSlSfxxDO.getHyzfjflx())){
                        if(Objects.equals(1, bdcSlSfxxDO.getHyzfjflx())){
                            zflx = String.valueOf(CommonConstantUtils.DDXX_DDLX_JNF);
                        }else if(Objects.equals(2, bdcSlSfxxDO.getHyzfjflx())){
                            zflx = String.valueOf(CommonConstantUtils.DDXX_DDLX_SFTJ);
                        }
                    }
                }
            }
            String ewmnr = sfdewm.replace("@nwslbh", slbh).replace("@sfyj", String.valueOf(sfyj)).replace("@token", token)
                    .replace("@jfType", jfType).replace("@zflx", zflx);

            try {
                return mainPath + sfxxid + "/ewm?ewmurl=" + URLEncoder.encode(ewmnr, "utf-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("?????????Url????????????:{}{}", ewmnr, e);
            }
        }
        return "";
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????????????????????????????????????????????????????jfsewmnrurl
     * @date : 2020/1/7 10:13
     */
    private String getEwmByYkqAndJfse(BdcSlSfxxDO bdcSlSfxxDO, String slbh, String mainPath) {
        String ykqewm = this.getYkqewm(bdcSlSfxxDO.getSfxxid(), slbh, mainPath, CommonConstantUtils.SF_F_DM);
        if (StringUtils.isNotBlank(ykqewm)) {
            return ykqewm;
        }
        return Optional.ofNullable(bdcSlSfxxDO.getJfsewmurl())
                .filter(StringUtils::isNotBlank)
                .map(t -> mainPath + t + "/ewm").orElse("");
    }

    /**
     * @param bdcXmDO,confingParam
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     * @date : 2020/2/24 15:52
     */
    private void generateZdData(BdcXmDO bdcXmDO, Map configParam) {
        List<Map> djxlMapList = bdcZdFeignService.queryBdcZd("djxl");
        List<Map> dyfsMapList = bdcZdFeignService.queryBdcZd("dyfs");
        //??????????????????
        if (CollectionUtils.isNotEmpty(djxlMapList)) {
            if (bdcXmDO.getDjxl() != null) {
                configParam.put("djxlmc", StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcXmDO.getDjxl()), djxlMapList));
            } else {
                configParam.put("djxlmc", "");
            }
        } else {
            configParam.put("djxlmc", "");
        }
        if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx()) || bdcXmDO.getQllx() == 96) {
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
            if (bdcQl == null) {
                bdcQl = bdcQllxFeignService.queryBdcYqlxx(bdcXmDO.getXmid());
            }
            if (bdcQl instanceof BdcDyaqDO) {
                BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                if (CollectionUtils.isNotEmpty(dyfsMapList)) {
                    if (bdcDyaqDO.getDyfs() != null) {
                        configParam.put("dyfs", "????????????:" + StringToolUtils.convertBeanPropertyValueOfZd(bdcDyaqDO.getDyfs(), dyfsMapList));
                    } else {
                        configParam.put("dyfs", "????????????:");
                    }
                } else {
                    configParam.put("dyfs", "????????????:" + (bdcDyaqDO.getDyfs() != null ? bdcDyaqDO.getDyfs() : ""));
                }
            } else if (bdcQl instanceof BdcYgDO) {
                BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                //??????????????????????????????????????????
                Boolean sfydydj = false;
                if ((CollectionUtils.isNotEmpty(ydydjxl) && ydydjxl.contains(bdcXmDO.getDjxl())) ||
                        (bdcYgDO.getYgdjzl() != null && (bdcYgDO.getYgdjzl() == 3 || bdcYgDO.getYgdjzl() == 4))) {
                    sfydydj = true;
                }
                if (CollectionUtils.isNotEmpty(dyfsMapList) && sfydydj) {
                    if (bdcYgDO.getDyfs() != null) {
                        configParam.put("dyfs", "????????????:" + StringToolUtils.convertBeanPropertyValueOfZd(bdcYgDO.getDyfs(), dyfsMapList));
                    } else {
                        configParam.put("dyfs", "????????????:");
                    }
                } else {
                    configParam.put("dyfs", "");
                }
            } else {
                configParam.put("dyfs", "");
            }
        } else {
            configParam.put("dyfs", "");
        }
    }

    private void generateQlrxx(BdcXmDO bdcXmDO, Map configParam) {
        String lxdh = "";
        String qlrmc = "";
        String ywrmc = "";
        String qlrzjh = "";
        String ywrzjh = "";
        String ywrlxdh = "";
        String qlrdlr = "";
        //1.?????????????????????????????????
        //?????????
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(bdcXmDO.getXmid());
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        //?????????
        List<BdcQlrDO> bdcYwrDOList = bdcQlrFeignService.listAllBdcQlr(bdcXmDO.getGzlslid(), CommonConstantUtils.QLRLB_YWR, bdcXmDO.getDjxl());
        //????????????????????????????????????????????????
        BdcQlrDO bdcYwr = bdcGenerateQlrService.getYwrData(bdcYwrDOList);
        //???????????????????????????????????????????????????
        if (ywrDjxlList.indexOf(bdcXmDO.getDjxl()) > -1) {
            qlrmc = bdcYwr.getQlrmc();
            lxdh = bdcYwr.getDh();
        } else {
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                qlrmc = StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getQlrmc", ",");
                lxdh = StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getDh", ",");
                qlrzjh = StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getZjh", ",");
                qlrdlr = StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getDlrmc", ",");
            }
            if (CollectionUtils.isNotEmpty(bdcYwrDOList)) {
                ywrmc = bdcYwr.getQlrmc();
                ywrlxdh = bdcYwr.getDh();
                ywrzjh = bdcYwr.getZjh();
            }
        }
        Map<String, String> qlrMap = resolveQlrOrder(bdcXmDO.getGzlslid());
        String qlrAndYwr = qlrMap.get("qlrAndYwr");
        String allQlr = qlrMap.get("allQlr");
        String qlrAll = qlrMap.get("qlrAll");
        configParam.put("qlrywr", StringUtils.isNotBlank(qlrAndYwr) ? qlrAndYwr : "");
        configParam.put("allqlr", StringUtils.isNotBlank(allQlr) ? allQlr : "");
        configParam.put("qlrall", StringUtils.isNotBlank(qlrAll) ? qlrAll : "");
        configParam.put("lxdh", StringUtils.isNotBlank(lxdh) ? lxdh : "");
        configParam.put("qlr", StringUtils.isNotBlank(qlrmc) ? qlrmc : "");
        configParam.put("ywr", StringUtils.isNotBlank(ywrmc) ? ywrmc : "");
        configParam.put("qlrzjh", StringUtils.isNotBlank(qlrzjh) ? qlrzjh : "");
        configParam.put("ywrzjh", StringUtils.isNotBlank(ywrzjh) ? ywrzjh : "");
        configParam.put("ywrlxdh", StringUtils.isNotBlank(ywrlxdh) ? ywrlxdh : "");
        configParam.put("qlrdlr", StringUtils.isNotBlank(qlrdlr) ? qlrdlr : "");
        if (ywrDjxlList.contains(bdcXmDO.getDjxl())) {
            configParam.put("qlrywr", qlrmc);
        }
        //???????????????????????????????????????????????????????????????????????????,??????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(getAllQlrDyids)) {
            ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(bdcXmDO.getGzlslid());
            if (processInstanceData != null && getAllQlrDyids.contains(processInstanceData.getProcessDefinitionKey())) {
                configParam.put("qlr", allQlr);
            }
        }
    }

    private void getDzqmUrl(String gzldyid, String gzlslid, Map configParam) {
        if (CollectionUtils.isNotEmpty(dzqmDyidList) && dzqmDyidList.contains(gzldyid)) {
            //????????????id??????????????????"????????????"??????????????????????????????????????????"e????????????.png"
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(gzlslid, dzqmName);
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                List<StorageDto> storageDtoList = storageClient.listAllSubsetStorages(bdcSlSjclDOList.get(0).getWjzxid(), "", null, null, null, "");
                if (CollectionUtils.isNotEmpty(storageDtoList)) {
                    for (StorageDto storageDto : storageDtoList) {
                        if (StringUtils.equals(dzqmFjmc, storageDto.getName())) {
                            String mainPath = acceptUrl + PROJECT_PATH;
                            String dzqmUrl = mainPath + "dzqm?fjid=" + storageDto.getId();
                            configParam.put("dzqmUrl", dzqmUrl);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Object getQlrPrintDataForBb(String processInsId, String qlrlb, String xmid, String userName) {
        List<Map> qlrList = new ArrayList<>();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(qlrlb);
        List<BdcQlrDO> qlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
        String slbh = "";
        if (CollectionUtils.isNotEmpty(list)) {
            slbh = list.get(0).getSlbh();
        }
        // ??????????????????????????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(qlrDoList)) {
            List<String> comapreList = new ArrayList<>(10);
            for (BdcQlrDO bdcQlrDO : qlrDoList) {
                if (CommonConstantUtils.QLRLX_GR.equals(bdcQlrDO.getQlrlx())) {
                    // ??????????????????????????????????????????????????????????????????????????????
                    Pair<Boolean, List<Map>> dlrxxPair = this.getDlrxxMap(comapreList, bdcQlrDO, slbh, userName);
                    // ??????????????????????????????????????????????????????
                    if (dlrxxPair.getLeft()) {
                        if (CollectionUtils.isNotEmpty(dlrxxPair.getRight())) {
                            qlrList.addAll(dlrxxPair.getRight());
                        }
                        if (CollectionUtils.isEmpty(comapreList)) {
                            Map sqrMap = this.getQlrxx(bdcQlrDO, slbh, userName);
                            if (MapUtils.isNotEmpty(sqrMap)) {
                                qlrList.add(sqrMap);
                            }
                        }
                    } else {
                        Map sqrMap = this.getQlrxx(bdcQlrDO, slbh, userName);
                        if (MapUtils.isNotEmpty(sqrMap)) {
                            qlrList.add(sqrMap);
                        }
                    }
                } else {
                    // ???????????????
                    Map sqrMap = this.getQlrxx(bdcQlrDO, slbh, userName);
                    if (MapUtils.isNotEmpty(sqrMap)) {
                        qlrList.add(sqrMap);
                    }
                    // ???????????????
                    Pair<Boolean, List<Map>> dlrxxPair = this.getDlrxxMap(comapreList, bdcQlrDO, slbh, userName);
                    if (CollectionUtils.isNotEmpty(dlrxxPair.getRight())) {
                        qlrList.addAll(dlrxxPair.getRight());
                    }
                }
            }
        }
        return qlrList;
    }

    // ???????????????????????????
    private Pair<Boolean, List<Map>> getDlrxxMap(List<String> comapreList, BdcQlrDO bdcQlrDO, String slbh, String userName) {
        List<Map> dlrxxMapList = new ArrayList<>();
        Boolean hasdlr = false;
        BdcDlrQO bdcDlrQO = new BdcDlrQO();
        bdcDlrQO.setQlrid(bdcQlrDO.getQlrid());
        List<BdcDlrDO> bdcDlrDOList = bdcDlrFeignService.listBdcDlr(bdcDlrQO);
        if (CollectionUtils.isNotEmpty(bdcDlrDOList)) {
            hasdlr = true;
            for (BdcDlrDO bdcDlrDO : bdcDlrDOList) {
                if (StringUtils.isBlank(bdcDlrDO.getZjh()) || comapreList.contains(bdcDlrDO.getZjh())) {
                    continue;
                }
                comapreList.add(bdcDlrDO.getZjh());
                Map dlrMap = this.getDlrxx(bdcDlrDO, slbh, userName);
                if (MapUtils.isNotEmpty(dlrMap)) {
                    dlrxxMapList.add(dlrMap);
                }
            }
        }
        return Pair.of(hasdlr, dlrxxMapList);
    }

    private Map getDlrxx(BdcDlrDO bdcDlrDO, String slbh, String userName) {
        Map dlrMap = new HashMap();
        dlrMap.put("qlrmckey", "????????????");
        String dlrzjh = bdcDlrDO.getZjh();
        // ???????????????????????????????????????????????????redis????????????base64code
        String dlrid = "DLR" + bdcDlrDO.getDlrid();
        dlrMap.putAll(convertQlrToMap(bdcDlrDO.getDlrmc(), null, null, dlrzjh, dlrid, "", "", "", ""));
        // ??????????????????????????????????????????????????????????????????
        dlrMap.putAll(queryRyxxByZjh(slbh, CommonConstantUtils.QLRLX_GR, dlrzjh, dlrid, userName));
        return dlrMap;
    }

    private Map getQlrxx(BdcQlrDO bdcQlrDO, String slbh, String userName) {
        Map sqrMap = new HashMap();
        // ?????????????????????
        sqrMap.putAll(convertQlrToMap(bdcQlrDO.getQlrmc(), bdcQlrDO.getXb(), bdcQlrDO.getTxdz(), bdcQlrDO.getZjh(), bdcQlrDO.getQlrid(), bdcQlrDO.getCsrq(), bdcQlrDO.getQlrzjqfjg(), bdcQlrDO.getMz(), bdcQlrDO.getYxqx()));
        sqrMap.put("qlrmckey", "?????????");
        // ??????????????????????????????????????????????????????????????????
        sqrMap.putAll(queryRyxxByZjh(slbh, bdcQlrDO.getQlrlx(), bdcQlrDO.getZjh(), bdcQlrDO.getQlrid(), userName));
        return sqrMap;
    }

    // ??????????????????????????????????????????????????????????????????????????????
    private Map queryRyxxByZjh(String slbh, Integer qlrlx, String zjh, String qlrid, String userName) {
        Map map = new HashMap();
        if (CommonConstantUtils.QLRLX_GR.equals(qlrlx)) {
            // ??????????????????
            map = requestInterface(zjh, "bb_cxsfzxx", userName, slbh);
            if (MapUtils.isNotEmpty(map)) {
                map.putAll(splicePersonKey(map, qlrid));
            } else {
                map.put("value4", "??????????????????");
            }
        } else {
            // ??????????????????
            map = requestInterface(zjh, "bb_cxyyzzxx", userName, slbh);
            if (MapUtils.isNotEmpty(map)) {
                map.putAll(spliceCompanyKey(map));
            } else {
                map.put("value4", "??????????????????");
            }
        }
        return map;
    }

    // ???????????????????????????????????????Map??????
    private Map convertQlrToMap(String qlrmc, Integer xb, String txdz, String zjh, String qlrid, String csrq, String qlrzjqfjg, String mz, String yxqx) {
        List<Map> xbZdxMap = bdcZdFeignService.queryBdcZd("xb");
        Map<String, String> map = new HashMap<>(5);
        map.put("qlrmc", qlrmc);
        map.put("qlrxb", getStrData(StringToolUtils.convertBeanPropertyValueOfZd(xb, xbZdxMap)));
        map.put("txdz", getStrData(txdz));
        map.put("zjh", zjh);
        map.put("qlrid", qlrid);
        map.put("csrq", getStrData(csrq));
        map.put("qlrzjqfjg", getStrData(qlrzjqfjg));
        map.put("mz", getStrData(mz));
        map.put("yxqx", getStrData(yxqx));
        return map;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????
     */
    private Map splicePersonKey(Map<String, String> queryDataMap, String qlrid) {
        Map<String, String> map = new HashMap<>();
        map.put("row1", "?????????  " + getStrData(queryDataMap.get("XM")));
        map.put("value1", "?????????  " + getStrData(queryDataMap.get("XB")));
        String sfzh = getStrData(queryDataMap.get("GRZJHM"));
        map.put("row2", "???????????????" + splitCsrq(sfzh));
        map.put("row21", "????????????:");
        map.put("value21", splitCsrq(sfzh));
        map.put("value2", "?????????  " + getStrData(queryDataMap.get("MZ")));
        map.put("row3", "?????????");
        map.put("value3", getStrData(queryDataMap.get("DZ")));
        map.put("row4", "???????????????");
        map.put("value4", sfzh);
        map.put("row5", "???????????????");
        map.put("value5", "");
        map.put("row6", "???????????????");
        map.put("value6", getStrData(queryDataMap.get("YXQ")));
        if (queryDataMap.containsKey("ZP") && StringUtils.isNotBlank(queryDataMap.get("ZP"))) {
            map.put("zp", acceptUrl + PROJECT_PATH + "zp/" + qlrid);
            // ????????????base64code?????????redis????????????????????????zp???url??????????????????????????????
            String zpBase64String = queryDataMap.get("ZP").replaceAll("\r|\n*", "");
            redisUtils.addStringValue(qlrid, zpBase64String, 360);
        }
        return map;
    }

    /**
     * ??????????????????????????????????????????
     */
    private String splitCsrq(String zjh) {
        String csrq = "";
        String csrqTpl = "%s???%s???%s???";
        if (StringUtils.isNotBlank(zjh)) {
            if (zjh.length() == 18) {
                csrq = String.format(csrqTpl, zjh.substring(6, 10), zjh.substring(10, 12), zjh.substring(12, 14));
            }
            if (zjh.length() == 15) {
                csrq = String.format(csrqTpl, "19" + zjh.substring(6, 8), zjh.substring(8, 10), zjh.substring(10, 12));
            }
        }
        return csrq;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????
     */
    private Map spliceCompanyKey(Map<String, String> queryDataMap) {
        Map<String, String> map = new HashMap<>();
        map.put("row1", "???????????????????????????    ");
        map.put("value1", getStrData(queryDataMap.get("SHTYXYDM")));
        map.put("row2", "????????????    ");
        map.put("value2", getStrData(queryDataMap.get("QYZCH")));
        map.put("row3", "?????????");
        map.put("value3", getStrData(queryDataMap.get("QYMC")));
        map.put("row4", "?????????");
        map.put("value4", getStrData(queryDataMap.get("QYLXMC")));
        map.put("row5", "???????????????");
        map.put("value5", getStrData(queryDataMap.get("QYLXXLMC")));
        map.put("row6", "??????????????????");
        map.put("value6", getStrData(queryDataMap.get("FDDBR")));
        map.put("clrqkey", "???????????????");
        map.put("yyqxkey", "???????????????");
        map.put("yyqxbs", "???");
        map.put("jyfwkey", "???????????????");
        map.put("xkjyxmkey", "?????????????????????");
        map.put("jydzkey", "???????????????");
        map.put("jycskey", "????????????");
        return map;
    }

    private static String getStrData(Object object) {
        return String.valueOf(CommonUtil.getOrElse(object, ""));
    }

    private Map<String, String> requestInterface(String zjh, String beanName, String userName, String slbh) {
        Map<String, String> param = new HashMap<>();
        param.put("zjh", zjh);
        param.put("userName", userName);
        param.put("slbh", slbh);
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, param);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("??????????????????({}})???????????????{}", beanName, param);
            LOGGER.info("??????????????????({})????????????????????????{}", beanName, response);
        }
        if (Objects.nonNull(response)) {
            Map responseMap = (Map) response;
            if (responseMap.containsKey("GRZJHM") || responseMap.containsKey("SHTYXYDM")) {
                return (Map) responseMap;
            }
        }
        return new HashMap();
    }

    /**
     * ??????????????????
     *
     * @param processInsId
     * @return
     */
    @Override
    public Object packPrintRxtpDatasForBb(String processInsId) {
        List result = new ArrayList();
        if (StringUtils.isNotBlank(processInsId)) {
            String clmc = "??????";
            List<StorageDto> listFjcl = storageClient.listStoragesByName("clientId", processInsId, null, clmc, 1, 0);
            LOGGER.info("???????????????????????????{}", listFjcl);
            if (CollectionUtils.isNotEmpty(listFjcl)) {
                for (StorageDto storageDto : listFjcl) {
                    List<StorageDto> listFile = storageClient.listAllSubsetStorages(storageDto.getId(), StringUtils.EMPTY, 1, null, 0, null);
                    if (CollectionUtils.isNotEmpty(listFile)) {
                        for (StorageDto storage : listFile) {
                            Map map = new HashMap();
                            map.put("tp", storage.getDownUrl());
                            map.put("id", UUIDGenerator.generate16());
                            result.add(map);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param bdlx
     * @param qzrlb
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     * @date : 2020/7/29 9:27
     */
    @Override
    public void queryQmxx(Integer bdlx, Integer qzrlb, String xmid, HttpServletResponse response) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            BdcQzxxDO bdcQzxx = new BdcQzxxDO();
            bdcQzxx.setQzrlb(qzrlb);
            bdcQzxx.setBdlx(bdlx);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDOList.get(0).getGzlslid());
                bdcQzxx.setGzlslid(bdcXmDOList.get(0).getGzlslid());
                bdcQzxx.setSlbh(bdcXmDOList.get(0).getSlbh());
                //??????????????????xmid??????
                if (lclx != 3) {
                    bdcQzxx.setXmid(xmid);
                }
            } else {
                bdcQzxx.setXmid(xmid);

            }
            List<BdcQzxxDO> bdcQzxxDOList = bdcQzxxFeginService.queryBdcQzxx(bdcQzxx);
            if (CollectionUtils.isNotEmpty(bdcQzxxDOList)) {
                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcQzxxDOList.get(0).getFid());
                if (Objects.nonNull(baseResultDto)) {
                    LOGGER.info("xmid{}???????????????{}??????????????????{}?????????????????????????????????0????????????{}", xmid, bdlx, qzrlb, baseResultDto.getCode());
                    Base64Utils.changeBase64ToImage(baseResultDto.getMsg(), "pjqqm.jpg", "jpg", response);
                }
            }

        }
    }

    /**
     * @param fjid
     * @param response
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????-??????
     * @date : 2022/3/16 11:17
     */
    @Override
    public void queryDzqm(String fjid, HttpServletResponse response) {
        if (StringUtils.isNotBlank(fjid)) {
            BaseResultDto baseResultDto = storageClient.downloadBase64(fjid);
            if (Objects.nonNull(baseResultDto)) {
                Base64Utils.changeBase64ToImage(baseResultDto.getMsg(), "dzqm.jpg", "jpg", response);
            }
        }
    }

    /**
     * @param xmid
     * @param response
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????????????????????????????
     * @date : 2020/7/29 9:27
     */
    @Override
    public void queryJycx(String xmid, String dylx, HttpServletResponse response) throws Exception {
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        String qlrmc = "";
        String zjh = "";
        String type = "1";
        String temp = "0";
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            // ??????xmid??????????????????????????????????????? bdc_sl_sqr ??????
            List<BdcSlSqrDO> bdcSlSqrDOList = this.bdcSlSqrService.listBdcSlSqrByXmid(xmid, null);
            if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                bdcQlrDOList = new ArrayList<>(bdcSlSqrDOList.size());
                for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                    bdcQlrDOList.add(BdcQlrDO.BdcQlrDOBuilder.aBdcQlrDO().withQlrmc(bdcSlSqrDO.getSqrmc()).withZjh(bdcSlSqrDO.getZjh()).build());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            qlrmc = StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getQlrmc", ",");
            zjh = StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getZjh", ",");
            if (StringUtils.isNotBlank(dylx) && StringUtils.isNotBlank(qlrmc) && StringUtils.isNotBlank(zjh)) {
                BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
                List<BdcQlrQO> bdcQlrQOList = new ArrayList<>(bdcQlrDOList.size());
                for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    BdcQlrQO bdcQlr = new BdcQlrQO();
                    bdcQlr.setQlrmc(bdcQlrDO.getQlrmc());
                    bdcQlr.setZjh(bdcQlrDO.getZjh());
                    bdcQlrQOList.add(bdcQlr);
                }
                bdcZfxxQO.setQlrxx(bdcQlrQOList);
                List<BdcZfxxDTO> bdcZfxxDTOList = bdcZfxxCxFeignService.listBdcZfxxDTO(bdcZfxxQO);
                Map<String, String> qrCodeParams = getQRCodeParams(zjh, qlrmc, dylx, bdcZfxxDTOList, xmid);
                type = qrCodeParams.get("type");
                temp = qrCodeParams.get("temp");
            }
            //?????????????????????
            if (StringUtils.isBlank(qlrmc)) {
                qlrmc = UUIDGenerator.generate16();
                LOGGER.warn("??????????????????????????????????????????,????????????????????????{}", qlrmc);
            }
            if (StringUtils.isBlank(zjh)) {
                zjh = UUIDGenerator.generate16();
                LOGGER.warn("?????????????????????????????????????????????,????????????????????????{}", zjh);
            }
        }
        LOGGER.warn("?????????????????????????????????xmid{}???????????????{}?????????{}????????????{},type={},temp={}", xmid, qlrmc, zjh, dylx, type, temp);
        String result = EncodeQRUtil.doEncode(qlrmc, zjh, type, temp);
        response.setContentType("image/jpg;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=test.jpg");
        QRcodeUtils.encoderQRCode(result, null, response);
    }

    public Map<String, String> getJyxx(Map<String, String> map, BdcXmDO bdcXmDO) {
        String xmid = map.get("xmid");
        if (StringUtils.isNotBlank(xmid)) {
            List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                Map<String, List<Map>> zdMap = bdcSlZdFeignService.listBdcSlzd();
                if (StringUtils.isNotBlank(bdcSlJyxxDOList.get(0).getHtbh())) {
                    map.put("htbh", bdcSlJyxxDOList.get(0).getHtbh());
                } else {
                    map.put("htbh", bdcXmDO.getSlbh());
                }
                map.put("fkfs", StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcSlJyxxDOList.get(0).getFkfs() != null ? bdcSlJyxxDOList.get(0).getFkfs() : "1"), zdMap.get("fkfs")));
            } else {
                map.put("htbh", "");
                map.put("fkfs", "");
            }
        }
        return map;
    }

    //??????????????????????????????
    private String generateJkData(String dylx, String xmid, String userName) {
        String xml = "";
        List<Map> jkDataList = new ArrayList<>();
        if (CommonConstantUtils.DYLX_YTHCLFHT.equals(dylx)) {
            //????????????
            Map<String, String> param = new HashMap<>();
            String htbh = "";
            if (StringUtils.isNotBlank(xmid)) {
                List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid(xmid);
                if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                    htbh = bdcSlJyxxDOList.get(0).getHtbh();
                }
            }
            if (StringUtils.isBlank(htbh)) {
                throw new AppException("????????????????????????");
            }
            param.put("htbh", htbh);
            LOGGER.info("??????????????????????????????????????????{}", param);
            Object response = exchangeInterfaceFeignService.requestInterface("ntyth_clfwqxx", param);
            LOGGER.info("????????????????????????????????????????????????{}", response);
            if (response != null) {
                Map<String, Object> jkdata = JSONObject.parseObject(JSON.toJSONString(response), Map.class);
                if (jkdata != null && jkdata.get("mmqy") != null) {
                    jkDataList = JSONObject.parseArray(JSONObject.toJSONString(jkdata.get("mmqy")), Map.class);
                    //??????????????????
                    generateHtjkxx(jkDataList, userName, xmid);

                }
            }

        }
        if (CollectionUtils.isEmpty(jkDataList)) {
            throw new AppException("????????????????????????,??????????????????");
        }
        //??????????????????
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)) {
            throw new AppException("????????????????????????,???????????????");
        }
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
        for (Map<String, Object> jkData : jkDataList) {
            BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
            bdcDysjDTO.setDylx(dylx);
            bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
            Map datas = Maps.newHashMap();
            Multimap<String, List> detail = ArrayListMultimap.create();
            //????????????
            for (Map.Entry<String, Object> entry : jkData.entrySet()) {
                Object entryValue = entry.getValue();
                if (entryValue instanceof List) {
                    detail.put(entry.getKey(), (List) entryValue);
                } else {
                    datas.put(entry.getKey(), entryValue);
                }
            }
            Map sjldatas = bdcDysjPzService.queryPrintDatasList(datas, "bdcSlConfigMapper", bdcDysjPzDOList);
            if (sjldatas != null) {
                datas.putAll(sjldatas);
            }
            bdcDysjDTO.setDysj(JSONObject.toJSONString(datas));
            bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(detail));
            bdcDysjDTOList.add(bdcDysjDTO);
        }
        xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
        return xml;

    }

    /**
     * @param jkDataList ??????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    private void generateHtjkxx(List<Map> jkDataList, String userName, String xmid) {
        if (CollectionUtils.isNotEmpty(jkDataList)) {
            for (Map<String, Object> jkData : jkDataList) {
                Map<String, Object> configParam = Maps.newHashMap();
                //??????????????????
                if (jkData.get("person") != null) {
                    List<ClfhtPersonDTO> personList = JSONObject.parseArray(JSONObject.toJSONString(jkData.get("person")), ClfhtPersonDTO.class);
                    if (CollectionUtils.isNotEmpty(personList)) {
                        Map<String, List<ClfhtPersonDTO>> personMap = personList.stream().collect(Collectors.groupingBy(ClfhtPersonDTO::getPersonType));
                        if (MapUtils.isNotEmpty(personMap)) {
                            for (Map.Entry<String, List<ClfhtPersonDTO>> entry : personMap.entrySet()) {
                                String personType = entry.getKey();
                                //????????????
                                String jdxx = entry.getValue().size() > 1 ? CommonConstantUtils.SUFFIX_PL : "";
                                ClfhtPersonDTO person = entry.getValue().get(0);
                                Field[] fields = ClfhtPersonDTO.class.getDeclaredFields();
                                for (Field field : fields) {
                                    try {
                                        field.setAccessible(true);
                                        if (StringUtils.equals(Constants.YTHCLFHT_TYPE_CMR, personType)) {
                                            configParam.put("jf" + field.getName(), field.get(person) + jdxx);
                                        } else if (StringUtils.equals(Constants.YTHCLFHT_TYPE_MSR, personType)) {
                                            //?????????
                                            configParam.put("yf" + field.getName(), field.get(person) + jdxx);
                                        } else if (StringUtils.equals(Constants.YTHCLFHT_TYPE_CMRDLR, personType)) {
                                            //??????????????????
                                            configParam.put("jfdlr" + field.getName(), field.get(person) + jdxx);
                                        } else if (StringUtils.equals(Constants.YTHCLFHT_TYPE_MSRDLR, personType)) {
                                            //??????????????????
                                            configParam.put("yfdlr" + field.getName(), field.get(person) + jdxx);
                                        }
                                    } catch (Exception e) {
                                        LOGGER.error("??????????????????????????????:{}{}", person.getName(), e);
                                    }
                                }
                            }
                        }

                    }
                }
                //??????????????????
                if (jkData.get("house") != null) {
                    List<Map> fwList = JSONObject.parseArray(JSONObject.toJSONString(jkData.get("house")), Map.class);
                    if (CollectionUtils.isNotEmpty(fwList)) {
                        jkData.put("syqzhAppendStr", StringToolUtils.resolveMapListToAppendStr(fwList, "syqzh", ","));
                        jkData.put("tdhqfsAppendStr", StringToolUtils.resolveMapListToAppendStr(fwList, "tdhqfs", ","));
                        jkData.put("tdsyhAppendStr", StringToolUtils.resolveMapListToAppendStr(fwList, "tdsyh", ","));

                    }
                }
                //??????key??????
                if (jkData.get("fwje") != null) {
                    jkData.put("fwzje", jkData.get("fwje").toString());
                }
                for (Map.Entry<String, Object> entry : jkData.entrySet()) {
                    //??????????????????
                    if (entry.getKey().toUpperCase().endsWith("JE")) {
                        try {
                            configParam.put(entry.getKey() + "dx", ChangeMoneyToCnUtils.convert(ChangeMoneyToCnUtils.moneyFormat(entry.getValue() != null ? entry.getValue().toString() : "0")));
                        } catch (Exception e) {
                            LOGGER.error("????????????{},object:{}", JSONObject.toJSONString(entry.getValue()), e);
                        }
                    }
                }
                //???????????????????????????
                configParam.put("dysj", DateUtils.formateYmdZw(new Date()));
                if (StringUtils.isNotBlank(userName)) {
                    UserDto userDto = userManagerClient.getUserDetailByUsername(userName);
                    if (userDto != null) {
                        configParam.put("dyr", userDto.getAlias());
                    }
                }
                //????????????
                //????????????
                String mainPath = acceptUrl + PROJECT_PATH;
                String mmhtQlrQmxx = mainPath + "qmxx?bdlx=4&qzrlb=1&xmid=" + xmid;
                String mmhtQlrDlrQmxx = mainPath + "qmxx?bdlx=4&qzrlb=2&xmid=" + xmid;
                String mmhtYwrQmxx = mainPath + "qmxx?bdlx=4&qzrlb=3&xmid=" + xmid;
                String mmhtYwrDlrQmxx = mainPath + "qmxx?bdlx=4&qzrlb=4&xmid=" + xmid;
                configParam.put("mmhtqlrqm", mmhtQlrQmxx);
                configParam.put("mmhtqlrdlrqm", mmhtQlrDlrQmxx);
                configParam.put("mmhtywrqm", mmhtYwrQmxx);
                configParam.put("mmhtywrdlrqm", mmhtYwrDlrQmxx);

                jkData.putAll(configParam);
            }
        }

    }

    /**
     * ???????????????????????????
     *
     * @param id
     * @param response
     */
    @Override
    public void fskpImg(String id, HttpServletResponse response) {
        response.setContentType("image/jpg;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=test.jpg");
        BaseResultDto base64 = storageClient.downloadBase64(id);
        String base64Str = base64.getMsg();
        Base64Utils.changeBase64ToImage(base64Str, "test.jpg", "jpg", response);

    }

    /**
     * ???????????????????????????c
     *
     * @param gzlslid
     * @param response
     * @return
     */
    @Override
    public void cdxxSignImg(String gzlslid, HttpServletResponse response) {
        LOGGER.info("??????????????????gzlslid:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
            List<StorageDto> listFjcl = storageClient.listStoragesByName("clientId", gzlslid, null, "??????/??????", 1, 0);
            if (CollectionUtils.isNotEmpty(listFjcl)) {
                StorageDto dto = listFjcl.get(0);
                String id = dto.getId();
                LOGGER.info("??????????????????????????????id:{}", id);
                List<StorageDto> listFiles = storageClient.listAllSubsetStorages(id, null, null, null, null, null);
                if (CollectionUtils.isNotEmpty(listFiles)) {
                    MultipartDto multipartDto = storageClient.download(listFiles.get(0).getId());
                    Base64Utils.changeBase64ToImage(storageClient.downloadBase64(listFiles.get(0).getId()).getMsg(), "test.jpg", "jpg", response);
                }
            }
        }

    }

    @Override
    public String generatePrintDataWithShxxData(Map map, String dylx) {
        map = Optional.ofNullable(map).orElse(new HashMap(8));
        map.put("signImageUrl", this.signImageUrl);
        if (map.containsKey("gzlslid")) {
            List<UserTaskDto> userTaskDataList = workFlowUtils.listShjdxx((String) map.get("gzlslid"));
            if (CollectionUtils.isNotEmpty(userTaskDataList)) {
                int i = 1;
                for (UserTaskDto userTaskDto : userTaskDataList) {
                    map.put("jdmc" + i++, userTaskDto.getName());
                }
            } else {
                for (int i = 1; i < 5; i++) {
                    map.put("jdmc" + i++, "");
                }
            }
        }
        return this.generateXmlData(map, dylx);
    }

    /**
     * @param gzlslid
     * @param dylx
     * @param zxlc
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????????????????xml
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @date : 2021/7/23 10:04
     */
    @Override
    public String generateXmlFromDypz(String gzlslid, String dylx, String zxlc) {
        List<BdcXmDO> bdcXmDOList = new ArrayList<>();
        Map<String, List<Map>> paramMap = Maps.newHashMap();
        Map<String, String> map;
        String xml = "";
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        List<Map> maps = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //xmid??????????????????????????????????????????????????????
            bdcXmDOList.sort(Comparator.comparing(BdcXmDO::getXmid));
            LOGGER.info("????????????:{},????????????:{}", dylx, bdcXmDOList.get(0).getSlbh());
            map = generateDjMap(bdcXmDOList.get(0), "", zxlc);
            maps.add(map);
            paramMap.put(dylx, maps);
        }
        xml = bdcPrintFeignService.print(paramMap);
        LOGGER.info("????????????{},??????????????????xml{}", dylx, xml);
        return xml;
    }

    /**
     * @param gzlslid
     * @param dylx
     * @param qlrlb
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????????????????
     * @date : 2021/7/23 10:27
     */
    @Override
    public String generateSlXmlFromDypz(String gzlslid, String dylx, String qlrlb, String xmid, String sjclids) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        String xml = "";
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            //??????xmid???????????????????????????????????????
            bdcXmList.sort(Comparator.comparing(BdcXmDO::getXmid));
            BdcDysjDTO bdcDysjDTO;
            List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
            bdcDysjDTO = generateXml(bdcXmList.get(0), dylx, qlrlb, false, sjclids);
            bdcDysjDTOList.add(bdcDysjDTO);
            xml = bdcPrintFeignService.printDatas(bdcDysjDTOList);
            bdcDysjDTOList.add(bdcDysjDTO);
        }
        LOGGER.info("????????????:{},????????????:{},??????????????????{}", dylx, bdcXmList.get(0).getSlbh(), xml);
        return xml;
    }

    /**
     * @param gzlslid
     * @param dylx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????????????????
     * @date : 2021/11/8 21:08
     */
    @Override
    public String queryJfsXml(String gzlslid, String dylx) {
        String xml = "";
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(dylx)) {
            //??????gzlslid?????????????????????????????????????????????
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxService.listLcTsjfGx(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                List<Map> maps = new ArrayList<>();
                for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put("gzlslid", bdcLcTsjfGxDO.getGzlslid());
                    paramMap.put("sfxxid", bdcLcTsjfGxDO.getSfxxid());
                    maps.add(paramMap);
                    //?????????????????????????????????????????????????????????
                    if (StringUtils.equals("dyaqjfspl", dylx)) {
                        break;
                    }
                }
                Map<String, List<Map>> paramMap = Maps.newHashMap();
                paramMap.put(dylx, maps);
                xml = bdcPrintFeignService.print(paramMap);
                LOGGER.info("????????????????????????????????????id{},????????????{},?????????xml?????????{}", dylx, gzlslid, xml);
            }
        }
        return xml;
    }

    /**
     * @param bdcSlZbDataDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????sql
     * @date : 2021/11/16 14:02
     */
    @Override
    public List<Map> executSql(BdcSlZbDataDTO bdcSlZbDataDTO) {
        Map configParam = bdcSlZbDataDTO.getConfigParam();
        if (MapUtils.isNotEmpty(configParam) && CollectionUtils.isNotEmpty(bdcSlZbDataDTO.getBdcDysjZbPzDOList())) {
            BdcDysjZbPzDO bdcDysjZbPzDO = bdcSlZbDataDTO.getBdcDysjZbPzDOList().get(0);
            List<String> sqlList = Lists.newArrayList(bdcDysjZbPzDO.getDyzbsjy().split(CommonConstantUtils.ZF_YW_FH));
            List<Map> sqlMapList = Lists.newArrayList();
            int index = 1;
            Map<String, Object> sqlResult = Maps.newHashMap();
            for (String sql : sqlList) {
                if (StringUtils.isBlank(StringUtils.trim(sql))) {
                    continue;
                }
                Matcher matcher = pattern.matcher(sql);
                String matchStr = null;
                String condition = null;
                Object value = null;
                while (matcher.find()) {
                    matchStr = matcher.group();
                    condition = matchStr.substring(2, matchStr.length() - 1);
                    value = configParam.get(condition);
                    if (value == null) {
                        throw new MissingArgumentException(condition);
                    }
                    sql = sql.replaceAll("\\$\\{" + condition + "\\}", value.toString());
                }
                configParam.put("sql", sql);
                List<Map> detailList;
                BdcConfigMapper bdcConfigMapper = beansResolveUtils.getBeanByName("bdcSlConfigMapper");
                detailList = bdcConfigMapper.executeConfigSql(configParam);
                if (CollectionUtils.size(sqlList) > 1) {
                    sqlResult.put("sql" + index, Queues.newArrayDeque(detailList));
                    index++;
                } else {
                    sqlMapList.addAll(detailList);
                }
            }
            while (MapUtils.isNotEmpty(sqlResult)) {
                Iterator<Map.Entry<String, Object>> iterator = sqlResult.entrySet().iterator();
                Map<String, Object> detailMap = Maps.newHashMap();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    Queue queue = (Queue) entry.getValue();
                    Map<String, Object> map = (Map<String, Object>) queue.poll();
                    if (map == null) {
                        iterator.remove();
                        continue;
                    } else {
                        detailMap.putAll(map);
                    }
                }
                if (MapUtils.isNotEmpty(detailMap)) {
                    sqlMapList.add(detailMap);
                }
            }
            return sqlMapList;
        }
        return Collections.emptyList();
    }

    @Override
    public void cdxxscpdfJycxImage(String zjh,String xm, String dylx, HttpServletResponse response) throws Exception {
        zjh = URLDecoder.decode(zjh,"UTF-8");
        xm = URLDecoder.decode(xm,"UTF-8");
        dylx = URLDecoder.decode(dylx,"UTF-8");
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        List<BdcQlrQO> bdcQlrQOList = new ArrayList<>();
        BdcQlrQO bdcQlr = new BdcQlrQO();
        bdcQlr.setQlrmc(xm);
        bdcQlr.setZjh(zjh);
        bdcQlrQOList.add(bdcQlr);
        bdcZfxxQO.setQlrxx(bdcQlrQOList);
        List<BdcZfxxDTO> bdcZfxxDTOList = bdcZfxxCxFeignService.listBdcZfxxDTO(bdcZfxxQO);
        Map<String, String> qrCodeParams = getQRCodeParams(zjh, xm, dylx, bdcZfxxDTOList, null);
        String type = qrCodeParams.get("type");
        String temp = qrCodeParams.get("temp");

        if (StringUtils.isBlank(xm)) {
            xm = UUIDGenerator.generate16();
            LOGGER.warn("??????????????????????????????????????????,????????????????????????{}", xm);
        }
        if (StringUtils.isBlank(zjh)) {
            zjh = UUIDGenerator.generate16();
            LOGGER.warn("?????????????????????????????????????????????,????????????????????????{}", zjh);
        }
        LOGGER.warn("??????????????????????????????PDF???????????????????????????????????????????????????{}?????????{}????????????{},type={},temp={}", xm, zjh, dylx, type, temp);
        String result = EncodeQRUtil.doEncode(xm, zjh, type, temp);
        response.setContentType("image/jpg;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=test.jpg");
        QRcodeUtils.encoderQRCode(result, null, response);

    }

    private Map<String,String> getQRCodeParams(String zjh,String qlrmc,String dylx,List<BdcZfxxDTO> bdcZfxxDTOList,String xmid){
        Map<String,String> params = new HashMap<>(2);
        String type = "1";
        String temp = "0";
        params.put("temp",temp);
        params.put("type",type);
        //??????xmid???????????????????????????????????????????????????
        BdcCdBlxxDO bdcBlxxQO = new BdcCdBlxxDO();
        if(StringUtils.isNotBlank(xmid)){
            bdcBlxxQO.setXmid(xmid);
        }else{
            bdcBlxxQO.setSqrmc(qlrmc);
            bdcBlxxQO.setSqrzjh(zjh);
        }
        BdcCdBlxxDO bdcCdBlxxDO = bdcSlCdBlxxService.queryBdcBlxx(bdcBlxxQO);
        if (StringUtils.equals(CommonConstantUtils.DYLX_QSZM_BDCQZ, dylx) || StringUtils.equals(CommonConstantUtils.DYLX_QSZM_FCTDZ, dylx)|| StringUtils.equals(CommonConstantUtils.DYLX_PDFJK_STFZM, dylx)
                || StringUtils.equals(CommonConstantUtils.DYLX_PDFJK_STFZM_0, dylx)|| StringUtils.equals(CommonConstantUtils.DYLX_STFZM, dylx) || (CollectionUtils.isNotEmpty(jycxEwmDylxList) && jycxEwmDylxList.contains(dylx))) {
            params.put("type",type);
            if (Objects.nonNull(bdcCdBlxxDO) && StringUtils.isNotBlank(bdcCdBlxxDO.getBlxxid())) {
                temp = String.valueOf(CollectionUtils.size(bdcZfxxDTOList) + 1);
            } else {
                temp = String.valueOf(CollectionUtils.size(bdcZfxxDTOList));
            }
            params.put("temp",temp);

        }
        if (StringUtils.equals(CommonConstantUtils.DYLX_CDXX_FCTDZ, dylx) || StringUtils.equals(CommonConstantUtils.DYLX_CDXX_BDCQZ, dylx)||StringUtils.equals(CommonConstantUtils.DYLX_PDFJK_CDXX_BDCQZ, dylx) ||
                StringUtils.equals(CommonConstantUtils.DYLX_PDFJK_CDXX_FCTDZ, dylx)  || (CollectionUtils.isNotEmpty(jycxEwmMxDylxList) && jycxEwmMxDylxList.contains(dylx))) {
            type = "0";
            params.put("type",type);
            String zl = StringToolUtils.resolveBeanToAppendStr(bdcZfxxDTOList, "getZl", CommonConstantUtils.ZF_YW_DH);
            if (Objects.nonNull(bdcCdBlxxDO) && StringUtils.isNotBlank(bdcCdBlxxDO.getBlxxid())) {
                if (StringUtils.isNotBlank(zl)) {
                    temp = zl + CommonConstantUtils.ZF_YW_DH + bdcCdBlxxDO.getFwzl();
                } else {
                    temp = bdcCdBlxxDO.getFwzl();
                }
            } else {
                temp = StringUtils.isNoneBlank(zl) ? zl : StringUtils.EMPTY;
            }
            params.put("temp",temp);

        }
        return params;
    }

    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description ??????????????????????????????xml
     * @date : 2022/12/13 9:54
     */
    @Override
    public Map generateSlMkXml(String gzlslid, String djxl, String zxlc) {
        String xml = "";
        Map result = new HashMap();
        //??????????????????Id??????????????????
        List<BdcXmDO> list = new ArrayList<>();
        //?????????????????????
        String sqsdylx = "";

        BdcXmQO bdcXmQO = new BdcXmQO();
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(djxl)){
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmQO.setDjxl(djxl);
            list = bdcXmFeignService.listBdcXm(bdcXmQO);
        }

        //??????????????????????????????
        if (CollectionUtils.isNotEmpty(list)){
            list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl))), ArrayList::new));
        }

        //?????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(list)){
            BdcXmDO bdcXmDO = list.get(0);
            BdcDjxlPzDO bdcDjxlPzDO = new BdcDjxlPzDO();
            bdcDjxlPzDO = bdcDjxlPzService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDO.getGzldyid(), bdcXmDO.getDjxl());
            if (Objects.nonNull(bdcDjxlPzDO) && StringUtils.isNotBlank(bdcDjxlPzDO.getSqsdylx())) {
                sqsdylx = bdcDjxlPzDO.getSqsdylx();
                result.put("dylx", sqsdylx);
                LOGGER.info("???????????????id,?????????????????????:{},{}", gzlslid, sqsdylx);

                if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(sqsdylx)) {
                    Map<String, List<Map>> paramMap = Maps.newHashMap();
                    List<Map> maps = new ArrayList<>();

                    Map<String, String> map;
                    map = generateDjMap(bdcXmDO, "", zxlc);
                    map.put("userName", userManagerUtils.getCurrentUserName());
                    if (StringUtils.isNotBlank(map.get("jycx"))) {
                        map.put("jycx", map.get("jycx") + "?dylx=" + sqsdylx);
                    }
                    maps.add(map);

                    paramMap.put(sqsdylx, maps);
                    xml = bdcPrintFeignService.print(paramMap);
                    result.put("xml", xml);
                }
            }
        }

        LOGGER.info("???????????????id,??????????????????xml:{},{}",gzlslid, xml);
        return result;
    }
}

