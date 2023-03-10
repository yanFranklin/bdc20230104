package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.accept.ui.utils.TzmUtils;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyClfHtxxDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.ydjxt.DvHlwResponseDto;
import cn.gtmap.realestate.common.core.dto.init.BdcCfxxPageResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlPageResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcBahtQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlBdcdyhQO;
import cn.gtmap.realestate.common.core.qo.accept.LjzQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.qo.register.BdcCqQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZjjgFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcYdslPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcRedisFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlXztzPzRestService;
import cn.gtmap.realestate.common.core.service.rest.building.FwLjzRestService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.common.util.office.ExcelUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/12
 * @description ?????????????????????
 */
@Controller
@Validated
@RequestMapping("/bdcdyh")
public class SelectBdcdyhController extends BaseController {

    @Autowired
    private AcceptBdcdyFeignService acceptBdcdyFeignService;

    @Autowired
    private AcceptLsBdcdyFeignService acceptLsBdcdyFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private FwLjzRestService fwLjzRestService;
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    private BdcSlXztzPzRestService bdcSlXztzPzRestService;
    @Autowired
    private BdcSlFeignService bdcSlFeignService;
    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcPpFeignService bdcPpFeignService;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    /**
     * Redis????????????
     */
    @Autowired
    private BdcRedisFeignService bdcRedisFeignService;
    @Autowired
    BdcGzyzFeignService bdcGzyzFeignService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcdyFeignService bdcdyFeignService;
    @Autowired
    ZdJsydLhxxFeignService zdJsydLhxxFeignService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    BdcSdFeignService bdcSdFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcZjjgFeignService bdcZjjgFeignService;
    @Autowired
    BdcYdslPzFeignService bdcYdslPzFeignService;

    @Value("${bdcdyzt.qbxs:false}")
    private boolean bdcdyztQbxs;
    @Value("#{'${tdppfw.gzldyid:}'.split(',')}")
    private List<String> tdppfw;
    @Value("${ysgg.gzldyid:}")
    private String ysggdyid;
    @Value("${xztz.sfcxgl:true}")
    private boolean sfcxgl;

    /*
     * ??????????????????
     * */
    @Value("${xztz.sfxzcx:false}")
    private boolean sfxzcx;

    @Value("${czsd.gzldyid:}")
    private String czsdGzldyid;

    //????????????????????????????????????????????????
    @Value("${sfcxyg:false}")
    private boolean sfcxyg;

    /*
     * ?????????????????????????????????????????????
     * */
    @Value("#{'${sfcjzy.gzldyid:}'.split(',')}")
    private List<String> sfcjzyGzldyid;

    /**
     * ?????????????????????????????????????????????ID????????????????????????????????????
     */
    @Value("#{'${xztz.filterZjjg.gzldyids:}'.split(',')}")
    private List<String> zjjgGzldyids;

    @Value("#{'${query.tempqszt.gzldyids:}'.split(',')}")
    private List<String> queryTempQsztDyidList;

    /**
     * @param processDefKey ???????????????ID
     * @return ?????????????????????????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????ID???????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("")
    public Object queryBdcSlXztzPzDOByGzldyid(String processDefKey) {
        if (StringUtils.isBlank(processDefKey)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        return bdcSlXztzPzRestService.queryBdcSlXztzPzDTOByGzldyid(processDefKey);
    }

    /**
     * @param gzlslid ???????????????ID
     * @return ?????????????????????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????ID???????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/queryParams")
    public Object queryParams(String gzlslid) {
        Map map = new HashMap();
        //?????????????????????ID??????
        ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
        if (StringUtils.isBlank(processInstanceData.getProcessDefinitionKey())) {
            throw new AppException("????????????????????????,??????????????????");
        }
        map.put("processDefKey", processInstanceData.getProcessDefinitionKey());

        //??????????????????ID???????????????UUID??????
        String jbxxid = "";
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (bdcSlJbxxDO != null) {
            jbxxid = bdcSlJbxxDO.getJbxxid();
        } else {
            jbxxid = UUIDGenerator.generate16();

        }
        map.put("jbxxid", jbxxid);
        return map;
    }

    /**
     * @param loadpage ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @GetMapping("/listBdcdyhByPageJson")
    public Object listBdcdyhByPageJson(@LayuiPageable Pageable pageable, BdcSlBdcdyhQO bdcSlBdcdyhQO, Boolean loadpage) {
        //???????????????????????????????????????????????????????????????????????????????????????(9?????????)
        if (sfxzcx) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            if (userDto != null) {
                String xzqh = userManagerUtils.getRegionCodeByUserName(userDto.getUsername());
                if (StringUtils.isNotBlank(xzqh) && StringUtils.length(xzqh) == 9) {
                    bdcSlBdcdyhQO.setXjxzqh(xzqh);
                }
            }
        }
        bdcSlBdcdyhQO.setQlr(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getQlr()));
        bdcSlBdcdyhQO.setBdcdyh(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getBdcdyh()));
        bdcSlBdcdyhQO.setZl(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getZl()));
        if (StringUtils.isNotBlank(bdcSlBdcdyhQO.getBdclx()) && !TzmUtils.getCxByBdclx(bdcSlBdcdyhQO)) {
            //??????????????????????????????????????????
            return addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));

        }
        if (bdcSlBdcdyhQO.getDyhcxlx() != null) {
            if (StringUtils.isNotBlank(bdcSlBdcdyhQO.getZdtzm())) {
                TzmUtils.getQlxzAndZdtzm(bdcSlBdcdyhQO);
            }
        }

        return acceptBdcdyFeignService.listBdcdyhByPageOrList(pageable, JSON.toJSONString(bdcSlBdcdyhQO), loadpage);
    }

    /**
     * @param loadpage    ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????(??????)
     */
    @ResponseBody
    @GetMapping("/listXmByPageJson")
    public Object listXmByPageJson(@LayuiPageable Pageable pageable,
                                   BdcQlQO bdcQlQO,
                                   Boolean loadpage) {
        bdcQlQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("selectbdcdyh",bdcQlQO.getGzldyid(),""));
        bdcQlQO.setYwrmc(StringUtils.deleteWhitespace(bdcQlQO.getYwrmc()));
        bdcQlQO.setQlrmc(StringUtils.deleteWhitespace(bdcQlQO.getQlrmc()));
        bdcQlQO.setZl(StringUtils.deleteWhitespace(bdcQlQO.getZl()));
        bdcQlQO.setBdcdyh(StringUtils.deleteWhitespace(bdcQlQO.getBdcdyh()));
        bdcQlQO.setBdcqzh(StringUtils.deleteWhitespace(bdcQlQO.getBdcqzh()));
        bdcQlQO.setDyazmh(StringUtils.deleteWhitespace(bdcQlQO.getDyazmh()));
        if(Objects.isNull(bdcQlQO.getSftjmrqszt()) || CommonConstantUtils.SF_S_DM.equals(bdcQlQO.getSftjmrqszt())){
            bdcQlQO.setQszt(CommonConstantUtils.QSZT_VALID);
        }
        if (CollectionUtils.isNotEmpty(queryTempQsztDyidList) && queryTempQsztDyidList.contains(bdcQlQO.getGzldyid())) {
            //?????????????????????????????????????????????????????????????????????
            bdcQlQO.setQszt(CommonConstantUtils.QSZT_TEMPORY);
        }
        bdcQlQO.setSlbh(StringUtils.deleteWhitespace(bdcQlQO.getSlbh()));
        if (StringUtils.isNotBlank(bdcQlQO.getGzldyid()) && StringUtils.equals(bdcQlQO.getGzldyid(), ysggdyid)) {
            bdcQlQO.setFbczdt(true);
        }

        String xzqh = "";
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (userDto != null) {
            xzqh = userManagerUtils.getRegionCodeByUserName(userDto.getUsername());
        }

        // ?????????????????? ?????????????????????  ??????bdcdyh??????6???
        if (StringUtils.equals(czsdGzldyid, bdcQlQO.getGzldyid()) && StringUtils.isNotBlank(xzqh) && StringUtils.length(xzqh) == 6) {
            bdcQlQO.setQjxzqh(xzqh);
        }
        // ?????????????????????????????????????????????????????????????????????????????????????????????(9?????????)
        if (sfxzcx && StringUtils.isNotBlank(xzqh) && StringUtils.length(xzqh) == 9) {
            bdcQlQO.setXjxzqh(xzqh);
        }
        // ???????????????????????????????????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(zjjgGzldyids) && zjjgGzldyids.contains(bdcQlQO.getGzldyid())) {
            bdcQlQO.setSfzjjg(CommonConstantUtils.SF_S_DM);
        }

        //?????????????????????????????????????????????????????????
        if (CollectionUtils.isNotEmpty(sfcjzyGzldyid) && sfcjzyGzldyid.contains(bdcQlQO.getGzldyid())) {
            bdcQlQO.setSfcjzy(CommonConstantUtils.SF_S_DM);
        }
        //??????????????????????????????????????????????????????????????????????????????????????? ?????????????????????????????????????????????
        if (StringUtils.isNotBlank(bdcQlQO.getDyazmh())) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setBdcqzh(bdcQlQO.getDyazmh());
            List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                List<BdcXmDO> allBdcXmList = new ArrayList<>(CollectionUtils.size(bdcZsDOList));
                for (BdcZsDO bdcZsDO : bdcZsDOList) {
                    List<BdcXmDO> bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(bdcZsDO.getZsid());
                    allBdcXmList.addAll(bdcXmDOList);
                }
                if (CollectionUtils.isNotEmpty(allBdcXmList)) {
                    List<String> bdcdyhList = allBdcXmList.stream().filter(bdcXmDO -> StringUtils.isNotBlank(bdcXmDO.getBdcdyh())).map(BdcXmDO::getBdcdyh).collect(Collectors.toList());
                    bdcQlQO.setBdcdyhList(bdcdyhList);
                }
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
        LOGGER.info("??????????????????????????????????????????{},loadpage{}", simpleDateFormat.format(new Date()), loadpage);
        if (loadpage != null && loadpage) {
            String bdcQlQoStr = JSON.toJSONString(bdcQlQO);
            Page<BdcQlPageResponseDTO> pageResponseDTOS = bdcXmFeignService.bdcQlPageByPageJson(pageable, bdcQlQoStr);
            if (CollectionUtils.isNotEmpty(pageResponseDTOS.getContent())) {
                List<Map> qllxZdMap = bdcZdFeignService.queryBdcZd("qllx");
                List<String> fwdyhList = new ArrayList<>();
                for (BdcQlPageResponseDTO bdcQlPageResponseDTO : pageResponseDTOS.getContent()) {
                    bdcQlPageResponseDTO.setQllxMc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcQlPageResponseDTO.getQllx()), qllxZdMap));
                    if (StringUtils.equals(CommonConstantUtils.FWBDCLX.toString(), bdcQlPageResponseDTO.getBdclx()) && StringUtils.isNotBlank(bdcQlPageResponseDTO.getBdcdyh()) && StringUtils.isNotBlank(bdcQlPageResponseDTO.getQllx()) && ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, Integer.parseInt(bdcQlPageResponseDTO.getQllx()))) {
                        fwdyhList.add(bdcQlPageResponseDTO.getBdcdyh());
                    }
                }
                //?????????????????????
                if (Boolean.TRUE.equals(bdcQlQO.getGltdz()) && CollectionUtils.isNotEmpty(fwdyhList)) {
                    glTdzxx(fwdyhList, pageResponseDTOS);
                }
                //??????????????????
                if (CollectionUtils.isNotEmpty(pageResponseDTOS.getContent()) && StringUtils.isNotBlank(bdcQlQO.getSfsdzt())) {
                    glSdzt(pageResponseDTOS);
                }
                //???????????????????????????
                if (CollectionUtils.isNotEmpty(pageResponseDTOS.getContent()) && bdcQlQO.getHbcxjg()) {
                    glSczmd(pageResponseDTOS);
                }
            }
            LOGGER.info("??????????????????????????????{}", simpleDateFormat.format(new Date()));
            return addLayUiCode(pageResponseDTOS);
        } else {
            return bdcXmFeignService.bdcQlList(bdcQlQO);
        }
    }


    @GetMapping("/listXmByPageJson/download/{lx}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "????????????", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "??????", dataType = "string", paramType = "query")})
    public void queryDataDownload(@LayuiPageable Pageable pageable,
                                  @PathVariable("lx") String lx,
                                  @RequestParam("param") String param,
                                  HttpServletResponse response
    ) {
        if (Objects.isNull(lx)) {
            throw new MissingArgumentException("????????????");
        }
        Object data = null;
        //????????????
        Map<String, String> exportColumn = new LinkedHashMap<>();
        //???????????????
        Map<String, Consumer<Map<String, String>>> exportColumnFunction = new HashMap<>();
        switch (lx) {
            case "cd":
                data = listXmByPageJson(pageable, JSON.parseObject(param,BdcQlQO.class),true);
                exportColumn.put("???????????????????????????","bdcqzh");
                exportColumn.put("??????","zl");
                exportColumn.put("?????????","qlrmc");
                //zdzhyt
                exportColumn.put("????????????","dzwyt");
                //dzwmj
                exportColumn.put("????????????","dzwmj");
                exportColumn.put("??????????????????","tdsyqmj");
                exportColumn.put("????????????","qszt");
                exportColumn.put("????????????","zt");
                break;
        }
        if (Objects.isNull(data)) {
            throw new MissingArgumentException("????????????");
        }
        //??????????????????
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        List<Map> jddm = zdMap.get("jddm");
        List<Map> qsztZd = zdMap.get("qszt");
        List<Map> ghytZd = zdMap.get("fwyt");
        exportColumnFunction.put("zt", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("bdcdyh")
                            && stringStringMap.containsKey("qjgldm")
                    ) {
                        Object o = queryBdcdyZt(stringStringMap.get("bdcdyh").toString(),
                                stringStringMap.get("qjgldm").toString()
                        );
                        if(Objects.nonNull(o)){
                            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(o));
                            if (jsonObject.containsKey("yg") && jsonObject.getBoolean("yg")) {
                                stringStringMap.put("zt","?????????");
                            }else if (jsonObject.containsKey("ycf") && jsonObject.getBoolean("ycf")) {
                                stringStringMap.put("zt","?????????");
                            }else if (jsonObject.containsKey("ydya") && jsonObject.getBoolean("ydya")) {
                                stringStringMap.put("zt","?????????");
                            }else if (jsonObject.containsKey("cf") && jsonObject.getBoolean("cf")) {
                                stringStringMap.put("zt","??????");
                            }else if (jsonObject.containsKey("dya") && jsonObject.getBoolean("dya")) {
                                stringStringMap.put("zt","??????");
                            }else if (jsonObject.containsKey("yy") && jsonObject.getBoolean("yy")) {
                                stringStringMap.put("zt","??????");
                            }else if (jsonObject.containsKey("sd") && jsonObject.getBoolean("sd")) {
                                stringStringMap.put("zt","??????");
                            }else if (jsonObject.containsKey("dyi") && jsonObject.getBoolean("dyi")) {
                                stringStringMap.put("zt","??????");
                            }else if (jsonObject.containsKey("zjgcdy") && jsonObject.getBoolean("zjgcdy")) {
                                stringStringMap.put("zt","??????????????????");
                            }else if (jsonObject.containsKey("jzq") && jsonObject.getBoolean("jzq")) {
                                stringStringMap.put("zt","??????");
                            }else if (jsonObject.containsKey("fwcq") && jsonObject.getBoolean("fwcq")) {
                                stringStringMap.put("zt","????????????");
                            }else if (jsonObject.containsKey("ks") && jsonObject.getBoolean("ks")) {
                                stringStringMap.put("zt","??????");
                            }else if (jsonObject.containsKey("ys") && jsonObject.getBoolean("ys")) {
                                stringStringMap.put("zt","??????");
                            }else if (jsonObject.containsKey("xjspfks") && jsonObject.getBoolean("xjspfks")) {
                                stringStringMap.put("zt","?????????????????????");
                            }else if (jsonObject.containsKey("xjspfys") && jsonObject.getBoolean("xjspfys")) {
                                stringStringMap.put("zt","?????????????????????");
                            }else if (jsonObject.containsKey("clfks") && jsonObject.getBoolean("clfks")) {
                                stringStringMap.put("zt","???????????????");
                            }else if (jsonObject.containsKey("clfys") && jsonObject.getBoolean("clfys")) {
                                stringStringMap.put("zt","???????????????");
                            } else {
                                stringStringMap.put("zt","??????");
                            }
                        }else {
                            stringStringMap.put("zt","??????");
                        }
                    } else {
                        stringStringMap.put("zt","??????");
                    }
                } catch (Exception e) {
                    stringStringMap.put("zt","??????");
                    e.printStackTrace();
                }

                if(!stringStringMap.containsKey("zt") || StringUtils.isEmpty(stringStringMap.get("zt"))){
                    stringStringMap.put("zt","??????");
                }
            }
        });

        exportColumnFunction.put("ssxz", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("ssxz") && Objects.nonNull(jddm)) {
                        for (Map map : jddm) {
                            if(map.containsKey("DM") && map.get("DM").equals("yyfzx")){
                                stringStringMap.put("ssxz", String.valueOf(map.get("MC")));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        exportColumnFunction.put("qszt", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("qszt") && Objects.nonNull(qsztZd)) {
                        Integer qszt = Integer.parseInt(stringStringMap.get("qszt"));
                        for (Map map : qsztZd) {
                            if(map.containsKey("DM") && map.get("DM").equals(qszt)){
                                stringStringMap.put("qszt", String.valueOf(map.get("MC")));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        exportColumnFunction.put("zdzhyt", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("dzwyt") && Objects.nonNull(ghytZd)) {
                        Integer zdzhyt = Integer.parseInt(stringStringMap.get("dzwyt"));
                        for (Map map : ghytZd) {
                            if(map.containsKey("DM") && map.get("DM").equals(zdzhyt)){
                                stringStringMap.put("dzwyt", String.valueOf(map.get("MC")));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        exportColumnFunction.put("djsj", new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {
                try {
                    if (stringStringMap.containsKey("djsj")
                    ) {
                        stringStringMap.put("djsj",
                                DateUtil.beginOfDay(new Date(Long.parseLong(stringStringMap.get("djsj")))).toString()
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            Map map = JSONObject.parseObject(JSONObject.toJSONString(data), Map.class);
            if(map.containsKey("totalElements")){
                if ((Integer) map.get("totalElements") > 2000) {
                    throw new MissingArgumentException("???????????????");
                }
            }
            List<Map<String, String>> content = JSON.parseObject(JSON.toJSONString(map.get("content")), new TypeReference<List<Map<String, String>>>() {
            });
            for (Map<String, String> stringStringMap : content) {
                if(stringStringMap.containsKey("xmid") && stringStringMap.containsKey("qllx")){
                    List<String> fdcq = Arrays.asList("4", "6", "8");
                    if(fdcq.contains(stringStringMap.get("qllx"))) {
                        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(stringStringMap.get("xmid"));
                        if (bdcQl instanceof BdcFdcqDO) {
                            if(Objects.nonNull(((BdcFdcqDO) bdcQl).getTdsyqmj())) {
                                stringStringMap.put("tdsyqmj", ((BdcFdcqDO) bdcQl).getTdsyqmj().toString());
                            }
                        }
                    }
                }
            }
            ExcelUtil.exportExcelWithConsumer("??????"+ new SimpleDateFormat("yyyyMMddHH").format(new Date()) +"??????.xls",
                    exportColumn,
                    content,
                    response,
                    exportColumnFunction);
        } catch (Exception e) {
            throw new AppException("??????Excel??????");
        }
    }


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????(???????????????)
     */
    @ResponseBody
    @PostMapping("/listQlList")
    public Object listQlList(@RequestBody BdcQlQO bdcQlQO) {
        bdcQlQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("selectbdcdyh",bdcQlQO.getGzldyid(),""));
        bdcQlQO.setQlrmc(StringUtils.deleteWhitespace(bdcQlQO.getQlrmc()));
        bdcQlQO.setZl(StringUtils.deleteWhitespace(bdcQlQO.getZl()));
        bdcQlQO.setBdcdyh(StringUtils.deleteWhitespace(bdcQlQO.getBdcdyh()));
        bdcQlQO.setBdcqzh(StringUtils.deleteWhitespace(bdcQlQO.getBdcqzh()));
        bdcQlQO.setQszt(CommonConstantUtils.QSZT_VALID);
        bdcQlQO.setSlbh(StringUtils.deleteWhitespace(bdcQlQO.getSlbh()));

        return bdcXmFeignService.bdcQlList(bdcQlQO);

    }

    /**
     * @param loadpage ????????????
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description ?????????????????????
     */
    @ResponseBody
    @GetMapping("/listCfByPageJson")
    public Object listCfByPageJson(@LayuiPageable Pageable pageable, BdcCfQO bdcCfQO, Boolean loadpage) {
        bdcCfQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("selectbdcdyh",bdcCfQO.getGzldyid(),""));
        bdcCfQO.setQlrmc(StringUtils.deleteWhitespace(bdcCfQO.getQlrmc()));
        bdcCfQO.setYwrmc(StringUtils.deleteWhitespace(bdcCfQO.getYwrmc()));
        bdcCfQO.setZl(StringUtils.deleteWhitespace(bdcCfQO.getZl()));
        bdcCfQO.setCfwh(StringUtils.deleteWhitespace(bdcCfQO.getCfwh()));
        bdcCfQO.setCfjg(StringUtils.deleteWhitespace(bdcCfQO.getCfjg()));
        bdcCfQO.setSlbh(StringUtils.deleteWhitespace(bdcCfQO.getSlbh()));
        bdcCfQO.setYcqzh(StringUtils.deleteWhitespace(bdcCfQO.getYcqzh()));
        if (loadpage) {
            String bdcCfQoStr = JSON.toJSONString(bdcCfQO);
            Page<BdcCfxxPageResponseDTO> bdcCfxxPageResponseDTOS = bdcXmFeignService.bdcCfxxPageByPageJson(pageable, bdcCfQoStr);
            if(StringUtils.isNotBlank(bdcCfQO.getSfsdzt())) {
                for (BdcCfxxPageResponseDTO bdcCfxxPageResponseDTO : bdcCfxxPageResponseDTOS) {
                    String sdzt = genSdzt(bdcCfxxPageResponseDTO.getXmid(), bdcCfxxPageResponseDTO.getBdcdyh());
                    bdcCfxxPageResponseDTO.setSdzt(sdzt);
                }
            }
            return addLayUiCode(bdcCfxxPageResponseDTOS);
        } else {
            return bdcXmFeignService.bdcCfxxList(bdcCfQO);
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????(?????????)
     */
    @ResponseBody
    @PostMapping("/listCfList")
    public Object listCfList(@RequestBody BdcCfQO bdcCfQO) {
        bdcCfQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("selectbdcdyh",bdcCfQO.getGzldyid(),""));
        bdcCfQO.setQlrmc(StringUtils.deleteWhitespace(bdcCfQO.getQlrmc()));
        bdcCfQO.setYwrmc(StringUtils.deleteWhitespace(bdcCfQO.getYwrmc()));
        bdcCfQO.setZl(StringUtils.deleteWhitespace(bdcCfQO.getZl()));
        bdcCfQO.setCfwh(StringUtils.deleteWhitespace(bdcCfQO.getCfwh()));
        bdcCfQO.setCfjg(StringUtils.deleteWhitespace(bdcCfQO.getCfjg()));
        bdcCfQO.setSlbh(StringUtils.deleteWhitespace(bdcCfQO.getSlbh()));
        bdcCfQO.setYcqzh(StringUtils.deleteWhitespace(bdcCfQO.getYcqzh()));
        return bdcXmFeignService.bdcCfxxList(bdcCfQO);
    }

    /**
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description ???????????????????????????
     */
    @ResponseBody
    @GetMapping("/listLjzByPageJson")

    public Object listLjzByPageJson(@LayuiPageable Pageable pageable, LjzQO ljzQO) {
        ljzQO.setBdcdyh(StringUtils.deleteWhitespace(ljzQO.getBdcdyh()));
        ljzQO.setZrzh(StringUtils.deleteWhitespace(ljzQO.getZrzh()));
        ljzQO.setLszd(StringUtils.deleteWhitespace(ljzQO.getLszd()));
        ljzQO.setFwmc(StringUtils.deleteWhitespace(ljzQO.getFwmc()));
        ljzQO.setLjzh(StringUtils.deleteWhitespace(ljzQO.getLjzh()));
        ljzQO.setZldz(StringUtils.deleteWhitespace(ljzQO.getZldz()));
        ljzQO.setDh(StringUtils.deleteWhitespace(ljzQO.getDh()));
        Page<Map> results = fwLjzRestService.listLjzByPageJson(pageable, JSON.toJSONString(ljzQO));
        if (StringUtils.isNotBlank(ljzQO.getSfsdzt())) {
            for (Map result : results) {
                String sdzt = genSdzt((String)result.get("xmid"), (String)result.get("bdcdyh"));
                if(StringUtils.isNotBlank(sdzt)){
                    result.put("sdzt",sdzt);
                }
            }
        }
        return PageUtils.addLayUiCodeWithQjgldm(results,ljzQO.getQjgldm());
    }

    /**
     * @param bdcZssdQO ????????????????????????
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description ??????????????????
     */
    @ResponseBody
    @GetMapping("/listZsSdByPageJson")
    public Object listSdByPageJson(@LayuiPageable Pageable pageable, BdcZssdQO bdcZssdQO, Boolean loadpage) {
        bdcZssdQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("selectbdcdyh",bdcZssdQO.getGzldyid(),""));
        if(loadpage){
            String bdcZssdQOStr = JSON.toJSONString(bdcZssdQO);
            return addLayUiCode(bdcXmFeignService.bdcZssdPageByPageJson(pageable, bdcZssdQOStr));
        }else{
            return bdcXmFeignService.bdcZssdList(bdcZssdQO);
        }
    }

    /**
     * @param bdcDysdQO ????????????????????????
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description ??????????????????
     */
    @ResponseBody
    @GetMapping("/listDySdByPageJson")
    public Object listDySdByPageJson(@LayuiPageable Pageable pageable, BdcDysdQO bdcDysdQO, Boolean loadpage) {
        bdcDysdQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("selectbdcdyh",bdcDysdQO.getGzldyid(),""));
        if(loadpage){
            String bdcdysdOStr = JSON.toJSONString(bdcDysdQO);
            return addLayUiCode(bdcXmFeignService.bdcDysdPageByPageJson(pageable, bdcdysdOStr));
        }else{
            return bdcXmFeignService.bdcDysdList(bdcDysdQO);
        }
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/queryBdcdyZt")
    public Object queryBdcdyZt(String bdcdyh,String qjgldm) {
        BdcdyhZtResponseDTO bdcdyhZtResponseDTO = new BdcdyhZtResponseDTO();
        if (StringUtils.isNotBlank(bdcdyh)) {
            bdcdyhZtResponseDTO = bdcdyZtFeignService.commonQueryBdcdyhZtBybdcdyh(StringUtils.deleteWhitespace(bdcdyh),qjgldm);
        } else {
            return null;
        }
        return bdcdyhZtResponseDTO;
    }

    /**
     * @param bdcdyh ??????????????????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  ??????bdcdyh??????????????????
     */
    @ResponseBody
    @GetMapping("/fwhs")
    public Object queryFwhsByBdcdyh(String bdcdyh,String qjgldm) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            return fwHsFeignService.queryFwhsByBdcdyh(StringUtils.deleteWhitespace(bdcdyh),qjgldm);
        } else {
            return null;
        }
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????
     */
    @ResponseBody
    @GetMapping("/queryBdcqzZt")
    public Object queryBdcqzZt(String bdcdyh, String qllx,String zsxmid,String qjgldm) {
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.isNotBlank(qllx)) {
            if (!bdcdyztQbxs) {
                if (StringUtils.equals(CommonConstantUtils.QLLX_DYAQ_DM.toString(),qllx) || StringUtils.equals(qllx, "97") || StringUtils.equals(qllx, "98")) {
                    //????????????????????????????????????????????????????????????????????????????????????????????????
                    return null;
                } else if (StringUtils.equals(qllx, "96")) {
                    BdcdyhZtResponseDTO bdcdyhZtResponseDTO = bdcdyZtFeignService.commonQueryBdcdyhZtBybdcdyh(StringUtils.deleteWhitespace(bdcdyh),qjgldm);
                    if (bdcdyhZtResponseDTO != null) {
                        BdcdyhZtResponseDTO returnZt = new BdcdyhZtResponseDTO();
                        returnZt.setYcf(bdcdyhZtResponseDTO.getYcf());
                        returnZt.setYdya(bdcdyhZtResponseDTO.getYdya());
                        if (sfcxyg) {
                            returnZt.setYg(bdcdyhZtResponseDTO.getYg());
                        }
                        return returnZt;
                    } else {
                        return null;
                    }
                } else if(CommonConstantUtils.QLLX_TDCBNYDSYQ.equals(Integer.parseInt(qllx))){
                    //??????????????????????????????+???????????????
                    return  bdcdyZtFeignService.queryZsBdcdyZtByCqzsxmid(zsxmid);
                }
                else {
                    return bdcdyZtFeignService.commonQueryBdcdyhZtBybdcdyh(StringUtils.deleteWhitespace(bdcdyh),qjgldm);
                }
            } else {
                if(CommonConstantUtils.QLLX_TDCBNYDSYQ.equals(Integer.parseInt(qllx))){
                    //??????????????????????????????+???????????????
                    return  bdcdyZtFeignService.queryZsBdcdyZtByCqzsxmid(zsxmid);
                }else {
                    return bdcdyZtFeignService.commonQueryBdcdyhZtBybdcdyh(StringUtils.deleteWhitespace(bdcdyh),qjgldm);
                }
            }
        } else {
            return null;
        }
    }

    /**
     * ?????????????????????
     * @param fwDcbIndex
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/queryLjzZt")
    public Object queryLjzZt(String fwDcbIndex, String bdcdyh) {
        if (StringUtils.isNotBlank(fwDcbIndex) && StringUtils.isNotBlank(bdcdyh)) {
            String lszd = bdcdyh.substring(0,19);
            Map<String, Object> ljzZtMap = new HashMap<>(1);
            ljzZtMap.put("lhdycs", this.zdJsydLhxxFeignService.queryLhdyqlZtByFwDcbIndexAndDjh(fwDcbIndex, lszd) > 0 ? true : false);
            return ljzZtMap;
        }
        return null;
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/queryqllsgx")
    public Object queryqllsgx(@LayuiPageable Pageable pageable, BdcQlQO bdcQlQO, Boolean isShowCount) {
        bdcQlQO.setQlrmc(StringUtils.deleteWhitespace(bdcQlQO.getQlrmc()));
        bdcQlQO.setZl(StringUtils.deleteWhitespace(bdcQlQO.getZl()));
        bdcQlQO.setBdcdyh(StringUtils.deleteWhitespace(bdcQlQO.getBdcdyh()));
        bdcQlQO.setBdcqzh(StringUtils.deleteWhitespace(bdcQlQO.getBdcqzh()));
        String bdcQlQoStr = JSON.toJSONString(bdcQlQO);
        return addLayUiCode(bdcXmFeignService.bdcQlPageByPageJson(pageable, bdcQlQoStr));
    }

    /**
     * @param lx ???????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/queryBdclxByBdcdyh")
    public Map queryBdclxByBdcdyh(String bdcdyh, String lx, String gzldyid) {
        Map map = Maps.newHashMap();
        String bdclx = "";
        //??????????????????????????????????????????????????????????????????????????????????????????????????????
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.length(bdcdyh) == CommonConstantUtils.BDCDYH_LENGTH) {
            bdclx = BdcdyhToolUtils.queryBdclxByBdcdyh(bdcdyh, lx);
            if (StringUtils.contains(bdclx, "/")) {
                if (StringUtils.isBlank(gzldyid)) {
                    throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
                }
                Integer djxlpzBdclx = bdcSlFeignService.queryBdcdjxlPzBdclx(gzldyid, StringUtils.deleteWhitespace(bdcdyh));
                if (djxlpzBdclx != null) {
                    bdclx = djxlpzBdclx.toString();
                } else {
                    bdclx = bdclx.split("/")[0];
                }
            }
        }

        map.put("bdclx", (StringUtils.isNotBlank(bdclx) ? Integer.parseInt(bdclx) : bdclx));
        return map;
    }

    /**
     * @param json ??????id
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????????????????
     */
    @ResponseBody
    @PostMapping("/pplzzt")
    public Map getPplzzt(@RequestBody String json, String xztzlx) {
        Map map = Maps.newHashMap();
        JSONArray jsonArray = JSONObject.parseArray(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            String bdcdyh = obj.getString("bdcdyh");
            //???????????????????????????????????????
            boolean isttbdcdyh = CommonConstantUtils.BDCLX_TZM_W.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh));
            if (StringUtils.isNotBlank(obj.getString("xmid"))
                    && StringUtils.isNotBlank(obj.getString("bdcdyh"))) {
                // ????????????
                List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(obj.getString("xmid"));
                if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                    if (bdcFctdPpgxDOList.size() > 1) {
                        map.put("mullz", obj.getString("xmid"));
                    }
                    BdcFctdPpgxDO bdcFctdPpgxDO = bdcFctdPpgxDOList.get(0);
                    //??????????????????
                    if (null == bdcFctdPpgxDO) {
                        map.put("sfpp", "false");
                    } else {
                        map.put("sfpp", "true");
                    }
                } else {
                    map.put("sfpp", "false");
                }
                // ??????
                List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList = bdcPpFeignService.queryBdcXnbdcdyhGxByXmid(obj.getString("xmid"));
                if (CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)) {
                    if (bdcXnbdcdyhGxDOList.size() > 1) {
                        //????????????????????????
                        map.put("mullz", obj.getString("xmid"));
                    }
                    BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO = bdcXnbdcdyhGxDOList.get(0);
                    //?????????????????????????????????????????????
                    if (bdcXnbdcdyhGxDO != null) {
                        map.put("sflz", "true");
                    } else {
                        map.put("sflz", "false");
                    }
                    //??????????????????????????????
                    if (!BdcdyhToolUtils.checkXnbdcdyh(obj.getString("bdcdyh"))) {
                        //??????????????????????????????
                        if (StringUtils.isNotBlank(xztzlx) && "2".equals(xztzlx)) {
                            String bdcqzh = "";
                            if (StringUtils.isNotBlank(obj.getString("bdcqzh"))) {
                                bdcqzh = obj.getString("bdcqzh");
                            } else {
                                bdcqzh = obj.getString("ybdcqz");
                            }
                            if (StringUtils.isNotBlank(bdcqzh) && bdcqzh.contains(CommonConstantUtils.BDC_BDCQZH_BS) || StringUtils.isBlank(bdcqzh)) {
                                //?????????????????????????????????????????????????????????
                                map.put("sflz", null);
                                map.put("sfpp", null);
                                //????????????????????????????????????????????????????????????
                                map.put("sfcxgl", sfcxgl);
                            }
                        }
                    }
                } else {
                    //?????????????????????????????????????????????
                    if (!BdcdyhToolUtils.checkXnbdcdyh(obj.getString("bdcdyh"))) {
                        //??????????????????????????????
                        if (StringUtils.isNotBlank(xztzlx) && StringUtils.equals(xztzlx, "2")) {
                            String bdcqzh = "";
                            if (StringUtils.isNotBlank(obj.getString("bdcqzh"))) {
                                bdcqzh = obj.getString("bdcqzh");
                            } else {
                                bdcqzh = obj.getString("ybdcqz");
                            }
                            if (StringUtils.isNotBlank(bdcqzh) && bdcqzh.contains(CommonConstantUtils.BDC_BDCQZH_BS) || StringUtils.isBlank(bdcqzh)) {
                                //?????????????????????????????????????????????????????????
                                map.put("sflz", null);
                                map.put("sfpp", null);
                                //????????????????????????????????????????????????????????????
                                map.put("sfcxgl", sfcxgl);
                            } else {
                                //????????????????????????
                                map.put("sflz", "true");
                            }
                        } else {
                            //????????????????????????
                            map.put("sflz", "true");
                        }
                    } else {
                        map.put("sflz", "false");
                    }
                }
            }
            if (isttbdcdyh) {
                map.put("sfpp", "true");
            }
            //?????????????????????????????????????????????????????????????????? ?????????????????????????????????
            if ((map.get("sflz") != null && map.get("sflz") == "false")
                    || map.get("sfpp") != null && map.get("sfpp") == "false"
                    || map.get("mullz") != null) {
                break;
            }
        }
        return map;
    }

    /**
     * @param xmid ??????id
     * @return ????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @GetMapping("/showppbtn")
    public Map showPpBtn(String xmid) {
        Map<String, Boolean> map = new HashMap<>();
        if (StringUtils.isNotBlank(xmid)) {
            List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(xmid);
            List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList = bdcPpFeignService.queryBdcXnbdcdyhGxByXmid(xmid);
            BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO = null;
            BdcFctdPpgxDO bdcFctdPpgxDO = null;
            if (CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)) {
                bdcXnbdcdyhGxDO = bdcXnbdcdyhGxDOList.get(0);
            }
            if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                bdcFctdPpgxDO = bdcFctdPpgxDOList.get(0);
            }
            //???????????????????????????????????????????????????????????????????????????
            if (bdcFctdPpgxDO != null || bdcXnbdcdyhGxDO != null) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(xmid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList) && bdcXmDOList.get(0).getQszt() != 0) {
                    map.put("showBtn", true);
                } else {
                    map.put("showBtn", false);
                }
            } else {
                map.put("showBtn", false);
            }
        } else {
            return null;
        }
        return map;
    }


    /**
     * @param bdcdyh ??????id
     * @return ????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????bdcdyh???ajzt???????????????????????????
     */
    @ResponseBody
    @GetMapping("/ajzt")
    public Integer queryAjzt(String bdcdyh, String xmid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        bdcXmQO.setAjzt(CommonConstantUtils.AJZT_ZB_DM);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList) && StringUtils.isNotBlank(xmid)) {
            /**
             * @param bdcdyh
             * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
             * @description ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
             * @date : 2020/9/18 10:49
             */
            //??????xmid??????????????????????????????????????????
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setYxmid(xmid);
            bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                bdcXmQO = new BdcXmQO(bdcXmLsgxDOList.get(0).getXmid());
                bdcXmQO.setAjzt(CommonConstantUtils.AJZT_ZB_DM);
                bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            }
        }
        return bdcXmDOList.size();
    }

    @GetMapping("/status/color")
    @ResponseBody
    public Object queryColorPz() {
        String xtPzColor = bdcRedisFeignService.getStringValue("xtPzColor");
        return JSONObject.parseObject(xtPzColor);
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: gzldyid ???????????????id
     * @return: boolean ???????????? ??????true ?????????false
     * @description ????????????????????????????????????????????????????????????
     */
    @GetMapping("/tdppfw/{gzldyid}")
    @ResponseBody
    public boolean queryAllowedGzldyid(@PathVariable(value = "gzldyid") String gzldyid) {
        if (StringUtils.isBlank(gzldyid)) {
            throw new NullPointerException("??????????????????????????????id???");
        }
        return CollectionUtils.isNotEmpty(tdppfw) && tdppfw.contains(gzldyid);
    }

    @GetMapping("/fwhs/page")
    @ResponseBody
    public Object listfwhsByPage(@LayuiPageable Pageable pageable, String fwbm, BdcSlBdcdyhQO bdcSlBdcdyhQO) {
        //?????????????????????????????????????????????
        Object object;
        if (StringUtils.isNotBlank(fwbm)) {
            Map<String, String> queryMap = new HashMap<>();
            queryMap.put("fwbm", fwbm);
            Page<FwHsDO> fwHsDOPage = fwHsFeignService.listFwHsByPageJson(pageable, JSON.toJSONString(queryMap));
            //??????????????????????????????????????????????????????????????????
            if (fwHsDOPage != null && CollectionUtils.isNotEmpty(fwHsDOPage.getContent())) {
                List<Map> hsMapList = new ArrayList<>(CollectionUtils.size(fwHsDOPage.getContent()));
                for (FwHsDO fwHsDO : fwHsDOPage.getContent()) {
                    Map hsMap = JSONObject.parseObject(JSON.toJSONString(fwHsDO), Map.class);
                    if (MapUtils.isNotEmpty(hsMap)) {
                        hsMap.put("lx", "HS");
                    }
                    hsMapList.add(hsMap);
                }
                object = addLayUiCode(new PageImpl(hsMapList));
            } else {
                //?????????????????????
                Page<FwYchsDO> fwYcHsPage = fwYcHsFeignService.listYchsByPage(pageable, JSON.toJSONString(queryMap));
                //?????????????????????????????????
                if (fwYcHsPage != null && CollectionUtils.isNotEmpty(fwYcHsPage.getContent())) {
                    List<Map> ychsMapList = new ArrayList<>(CollectionUtils.size(fwYcHsPage.getContent()));
                    for (FwYchsDO fwYchsDO : fwYcHsPage.getContent()) {
                        Map ychsMap = JSONObject.parseObject(JSON.toJSONString(fwYchsDO), Map.class);
                        if (MapUtils.isNotEmpty(ychsMap)) {
                            ychsMap.put("lx", "YCHS");
                        }
                        ychsMapList.add(ychsMap);
                    }
                    object = addLayUiCode(new PageImpl(ychsMapList));
                } else {
                    object = this.listBdcdyhByPageJson(pageable, bdcSlBdcdyhQO, true);
                }
            }
        } else {
            object = this.listBdcdyhByPageJson(pageable, bdcSlBdcdyhQO, true);
        }
        return object;
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param xmids         ??????ID??????
     * @param processDefKey ???????????????ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/plQueryXzQl")
    public boolean plQueryXzQl(@RequestBody List<String> xmids, @RequestParam(value = "processDefKey") String processDefKey) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(xmids), "??????????????????ID??????");
        Preconditions.checkArgument(StringUtils.isNotBlank(processDefKey), "????????????????????????ID?????????");
        for (String xmid : xmids) {
            if (this.bdcGzyzFeignService.checkWltdzXzQl(xmid, processDefKey)) {
                return true;
            }
        }
        return false;
    }

    @ResponseBody
    @GetMapping("/ghyt")
    public Object queryGhyt(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new AppException("????????????????????????xmid");
        }
        String ghyt = "";
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        if (Objects.nonNull(bdcQl)) {
            List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmDOList = bdcQllxFeignService.listFdcqXm(bdcQl.getQlid());
            if (CollectionUtils.isNotEmpty(bdcFdcqFdcqxmDOList)) {
                List<Map> ghytZdMap = bdcZdFeignService.queryBdcZd("fwyt");
                ghyt = StringToolUtils.convertBeanPropertiesValueOfZd(bdcFdcqFdcqxmDOList, "ghyt", ghytZdMap,"");
            }
        }
        if (StringUtils.isNotBlank(ghyt)) {
            List<String> ghytList = Arrays.asList(StringUtils.split(ghyt, CommonConstantUtils.ZF_YW_DH));
            ghytList = ghytList.stream().distinct().collect(Collectors.toList());
            ghyt = StringUtils.join(ghytList, CommonConstantUtils.ZF_YW_DH);
        }
        return ghyt;
    }

    /**
     * @param pageable ????????????
     * @param bdcQlQO ????????????
     * @param loadpage ??????????????????
     * @param queryType ???????????? 0:3.0????????? 1???????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ?????????????????????
     */
    @ResponseBody
    @GetMapping("/listYxmByPageJson")
    @LogNote(value = "???????????????", action = LogConstans.LOG_TYPE_XZTZ, custom = LogConstans.LOG_TYPE_XZTZ)
    public Object listYxmByPageJson(@LayuiPageable Pageable pageable, BdcQlQO bdcQlQO, Boolean loadpage,String queryType) {
        String htbh = bdcQlQO.getHtbh();
        String gzldyid = bdcQlQO.getGzldyid();
        String cqzh = bdcQlQO.getBdcqzh();
        Object ydjxxObj = null;
        Map queryYdjMap = new HashMap();
        List<Map> list = new ArrayList<>();
        List listDto =new ArrayList();
        //??????3.0????????????
        List<BdcQlPageResponseDTO> bdcQlPageResponseDTOList =null;
        // ????????????????????? ??? ???????????????????????? ???????????????????????????
        if(StringUtils.isBlank(cqzh) && StringUtils.isNotBlank(htbh)){
            Date getHtxxStartTime = new Date();
            // ???????????????????????? ??????????????????
            Object response = this.getHtxxByHtbh(htbh, gzldyid);
            long getHtxxTime = System.currentTimeMillis() - getHtxxStartTime.getTime();
            LOGGER.info("??????????????????????????????????????????{}", getHtxxTime);
            FcjyClfHtxxDTO fcjyClfHtxxDTO = JSON.parseObject(JSONObject.toJSONString(response),FcjyClfHtxxDTO.class);
            if(!StringUtils.equals(fcjyClfHtxxDTO.getCode(),CommonConstantUtils.SUCCESS_CODE)){
                throw new AppException("????????????????????????????????????????????????" + fcjyClfHtxxDTO.getMsg());
            }
            if(null != fcjyClfHtxxDTO.getBdcSlXmDO() && null != fcjyClfHtxxDTO.getBdcSlJyxx()){
                String zl = fcjyClfHtxxDTO.getBdcSlXmDO().getZl();
                if(StringUtils.isBlank(zl)){
                    throw new AppException("???????????????????????????????????????????????????????????????");
                }

                // ????????????gzldyid
                String pzGzldyid = getSpfGzldyid();
                String fwbhlx ="";
                if(fcjyClfHtxxDTO.getQtAttrMap() != null &&fcjyClfHtxxDTO.getQtAttrMap().get("fwbhlx") != null) {
                    fwbhlx = String.valueOf(fcjyClfHtxxDTO.getQtAttrMap().get("fwbhlx"));
                }
                //??????????????????,????????????fwbhlx???1???,????????????????????????????????????????????????
                if(pzGzldyid.indexOf(bdcQlQO.getGzldyid()) > -1 &&StringUtils.equals("1",fwbhlx)){
                    Date startTime = new Date();
                    //????????????????????????????????????
                    zl =acceptBdcdyFeignService.queryZlBySgbh(zl);
                    long time = System.currentTimeMillis() - startTime.getTime();
                    LOGGER.info("????????????????????????????????????????????????????????????{}", time);
                }
                if(StringUtils.isNotBlank(zl)) {
            //??????????????????????????????
                    bdcQlQO.setZl(zl);
                    bdcQlQO.setZlmh("1");
            bdcQlQO.setQszt(CommonConstantUtils.QSZT_VALID);
                    bdcQlQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("selectbdcdyh",bdcQlQO.getGzldyid(),""));
            bdcQlPageResponseDTOList =bdcXmFeignService.bdcQlList(bdcQlQO);
                }
            }else{
                throw new AppException("?????????????????????????????????????????????");
            }
            // ?????????????????? ??? ??????????????????????????? ??????????????????????????????
        }else if(StringUtils.isNotBlank(cqzh) && StringUtils.isBlank(htbh)){
            queryYdjMap.put("bdcqzh",cqzh);
            LOGGER.info("?????????????????????????????????????????????{}",queryYdjMap);
            ydjxxObj = exchangeInterfaceFeignService.requestInterface("getCzYdjxtXx", queryYdjMap);
            if(ydjxxObj ==null){
                throw new AppException("????????????????????????????????????????????????null");
            }
        }else{
            throw new AppException("?????????????????????");
        }

        if(null != ydjxxObj){
            listDto  = (List)ydjxxObj;
        }
        //??????3.0????????????
        if(CollectionUtils.isNotEmpty(bdcQlPageResponseDTOList)){
            bdcQlPageResponseDTOList.forEach(bdcQlPageResponseDTO -> bdcQlPageResponseDTO.setHtbh(htbh));
            if(Boolean.TRUE.equals(loadpage)) {
                Page<BdcQlPageResponseDTO> page = PageUtils.listToPage(bdcQlPageResponseDTOList, pageable);
                if(CollectionUtils.isNotEmpty(page.getContent()) && StringUtils.isNotBlank(bdcQlQO.getSfsdzt())) {
                    for (BdcQlPageResponseDTO bdcQlPageResponseDTO : page.getContent()) {
                        if (StringUtils.isNotBlank(bdcQlPageResponseDTO.getBdcdyh()) || StringUtils.isNotBlank(bdcQlPageResponseDTO.getXmid())) {
                            bdcQlPageResponseDTO.setSdzt(genSdzt(bdcQlPageResponseDTO.getXmid(), bdcQlPageResponseDTO.getBdcdyh()));
                        }
                    }
                }
                return addLayUiCode(page);
            }else{
                return bdcQlPageResponseDTOList;
            }
        }
        //????????????????????????
        if(CollectionUtils.isNotEmpty(listDto)){
            LOGGER.info("????????????????????????????????????????????????{}",JSONObject.toJSONString(listDto));
            for(Object obj : listDto){
                DvHlwResponseDto dvHlwResponseDto = JSONObject.parseObject(JSON.toJSONString(obj), DvHlwResponseDto.class);
                String jsonStr = JSONObject.toJSONString(dvHlwResponseDto, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
                Map map = JSONObject.parseObject(jsonStr, HashMap.class);
                if(map.containsKey("qlrs")){
                    List<Map> js  = (List<Map>) map.get("qlrs");
                    if(CollectionUtils.isNotEmpty(js)){
                        map.put("qlrmc",js.get(0).get("qlrmc"));
                    }
                }
                map.put("htbh",htbh);
                map.put("zl",dvHlwResponseDto.getFczl());
                list.add(map);
            }
            if(Boolean.TRUE.equals(loadpage)){
                Page<Map> page = PageUtils.listToPage(list, pageable);
                if(CollectionUtils.isNotEmpty(page.getContent()) && StringUtils.isNotBlank(bdcQlQO.getSfsdzt())) {
                    for (Map map : page.getContent()) {
                        if (map.containsKey("xmid") || map.containsKey("bdcdyh")) {
                            map.put("sdzt", genSdzt((String) map.get("xmid"), (String) map.get("bdcdyh")));
                        }
                    }
                }
                return super.addLayUiCode(page);
            }else{
                return list;
            }
        }else{
            return super.addLayUiCode(PageUtils.listToPage(listDto, pageable));
        }
    }

    /**
     * @param htbh ????????????
     * @param gzldyid ???????????????id
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ??????????????????
     */
    @ResponseBody
    @PostMapping("/htxxByHtbh/{htbh}/{gzldyid}")
    public Object getHtxxByHtbh(@PathVariable(value="htbh") String htbh, @PathVariable(value="gzldyid") String gzldyid) {
        Map map = new HashMap();
        map.put("contractNo",htbh);
        LOGGER.info("???????????????????????????????????????{}",map);
        Object response;
        String pzGzldyid = getSpfGzldyid();
        if(StringUtils.isBlank(gzldyid)){
            throw new AppException("??????????????????????????????id??????");
        }
        if(StringUtils.isBlank(pzGzldyid)){
            throw new AppException("?????????????????????????????????id??????");
        }
        if(pzGzldyid.indexOf(gzldyid) > -1 ){
            response = exchangeInterfaceFeignService.requestInterface("fcjySpfBaxx", map);
        }else{
            response = exchangeInterfaceFeignService.requestInterface("fcjyClfHtxx", map);
        }
        //?????????????????????????????????????????????
        bdcZjjgFeignService.sfzjjgYthpt(gzldyid,htbh);
        if(null != response){
            LOGGER.info("??????????????????????????????????????????{}",JSONObject.toJSONString(response));
            return response;
        }else{
            throw new AppException("?????????????????????????????????????????????");
        }
    }

    /**
     * ?????????????????????????????????????????????????????????????????????
     * @param yywxxList
     */
    @ResponseBody
    @PostMapping("/insertYxmxx")
    public Object insertYxmxx(@RequestBody List<DvHlwResponseDto> yywxxList) {
        if(CollectionUtils.isNotEmpty(yywxxList)){
            Object response = exchangeInterfaceFeignService.requestInterface("initYxmxx", yywxxList);
            if(null != response){
                LOGGER.info("???????????????????????????????????????{}",JSONObject.toJSONString(response));
                Map map = (Map)response;
                if(map.containsKey("yxmid")){
                    return map.get("yxmid");
                }else{
                    throw new AppException("??????????????????????????????yxmid???");
                }
            }
        }else{
            throw new AppException("??????????????????????????????");
        }
        return "";
    }



    /**
     * @param fwDcbIndex
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????
     * @date : 2020/9/30 10:31
     */
    @ResponseBody
    @GetMapping("/sfdz")
    public Boolean sfdz(String fwDcbIndex,String qjgldm) {
        boolean sfdz = false;
        if(StringUtils.isBlank(fwDcbIndex)) {
            throw  new AppException("???????????????????????????????????????");
        }
        FwLjzDO fwLjzDO =fwLjzRestService.queryLjzByFwDcbIndex(fwDcbIndex,qjgldm);
        if(fwLjzDO != null &&StringUtils.equals(CommonConstantUtils.BDCDYFWLX_DUZH.toString(),fwLjzDO.getBdcdyfwlx())){
            sfdz = true;
        }
        return sfdz;
    }

    /**
     * ????????? ?????????????????????????????????id
     * @return SpfGzldyid
     */
    public String getSpfGzldyid() {
        String spfGzldyid = bdcSlJbxxFeignService.spfGzldyid();
        LOGGER.info("???????????????????????????gzldyid???{}",spfGzldyid);
        return spfGzldyid;
    }

    /**
     * ?????? ????????????????????? ??????bdcqzh
     * @param bdcBahtQO
     * @return
     */
    @ResponseBody
    @PostMapping("/queryBaht/param")
    public Object queryBahtParam(@RequestBody BdcBahtQO bdcBahtQO) {
        String spfGzldyid = bdcSlJbxxFeignService.spfGzldyid();
        // ????????????????????????bdcqzh+zslx+htbh???
        Map cxParam = new HashMap();
        if(StringUtils.isNotBlank(bdcBahtQO.getGzldyid())){
            Object response = null;
            // ?????????????????????????????????????????????????????????????????????
            if(StringUtils.isNotBlank(bdcBahtQO.getCxlx())){
                if(CommonConstantUtils.BAHTCXLX_SPF.equals(bdcBahtQO.getCxlx())){
                    LOGGER.info("???????????????????????????????????????beanId:rd_spfhtxx,?????????{}",JSONObject.toJSONString(bdcBahtQO));
                    response = exchangeInterfaceFeignService.requestInterface("rd_spfhtxx",bdcBahtQO);
                    cxParam.put("zslx",CommonConstantUtils.ZSLX_ZMD);
                }else{
                    LOGGER.info("???????????????????????????????????????beanId:rd_clfhtxx,?????????{}",JSONObject.toJSONString(bdcBahtQO));
                    response = exchangeInterfaceFeignService.requestInterface("rd_clfhtxx",bdcBahtQO);
                }
            }else{
                if(spfGzldyid.indexOf(bdcBahtQO.getGzldyid()) > -1){
                    LOGGER.info("???????????????????????????????????????beanId:rd_spfhtxx,?????????{}",JSONObject.toJSONString(bdcBahtQO));
                    response = exchangeInterfaceFeignService.requestInterface("rd_spfhtxx",bdcBahtQO);
                    cxParam.put("zslx",CommonConstantUtils.ZSLX_ZMD);
                }else{
                    LOGGER.info("???????????????????????????????????????beanId:rd_clfhtxx,?????????{}",JSONObject.toJSONString(bdcBahtQO));
                    response = exchangeInterfaceFeignService.requestInterface("rd_clfhtxx",bdcBahtQO);
                }
            }

            if(null != response){
                LOGGER.info("???????????????????????????????????????????????????{}",JSONObject.toJSONString(response));
                String bdcqzh = "";
                String htbh = "";
                JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(response));
                JSONArray datas = data.getJSONArray("data");
                if(CollectionUtils.isNotEmpty(datas)){
                    for (int i = 0; i < datas.size(); i++) {
                        JSONObject curJsonObj = (JSONObject) datas.get(i);
                        bdcqzh += String.valueOf(curJsonObj.get("djzmdh") == null?curJsonObj.get("bdcqzh"):curJsonObj.get("djzmdh"))+",";
                        htbh += String.valueOf(curJsonObj.get("htbh")) + ",";
                    }
                }else{
                    return cxParam;
                }
                if(bdcqzh.length() > 0){
                    bdcqzh = bdcqzh.substring(0,bdcqzh.length()-1);
                    htbh = htbh.substring(0, htbh.length() - 1);
                }

                cxParam.put("bdcqzh", bdcqzh);
                cxParam.put("htbh", htbh);

            }
        }
        return cxParam;
    }

    @ResponseBody
    @GetMapping("/xmxx")
    public Object listXmxxByPageJson(@LayuiPageable Pageable pageable, String xmids) {
        if (StringUtils.isBlank(xmids)) {
            return addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));
        }
        BdcQlQO bdcQlQO = new BdcQlQO();
        bdcQlQO.setXmid(Arrays.asList(xmids.split(CommonConstantUtils.ZF_YW_DH)));
        Page<BdcQlPageResponseDTO> pageResponseDTOS = bdcXmFeignService.bdcQlPageByPageJson(pageable, JSONObject.toJSONString(bdcQlQO));
        return addLayUiCode(pageResponseDTOS);
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private void glTdzxx(List<String> fwdyhList,Page<BdcQlPageResponseDTO> pageResponseDTOS){
        BdcCqQO bdcCqQO = new BdcCqQO();
        //???????????????,???????????????????????????????????????
        bdcCqQO.setBdcdyhList(fwdyhList);
        bdcCqQO.setQszt(CommonConstantUtils.QSZT_VALID);
        List<Map> jsydsyqXmxxList = bdcBdcdyFeignService.listBdcJsydsyqXmxx(bdcCqQO);
        if(CollectionUtils.isNotEmpty(jsydsyqXmxxList)){
            Map<String,Map<String,Object>> dyhTdzMap =new HashMap<>();
            for(Map<String,Object> map:jsydsyqXmxxList){
                if(map.get("BDCDYH") !=null) {
                    dyhTdzMap.put(MapUtils.getString(map,"BDCDYH"), map);
                }
            }
            for(BdcQlPageResponseDTO bdcQlPageResponseDTO:pageResponseDTOS.getContent()){
                if(dyhTdzMap.containsKey(bdcQlPageResponseDTO.getBdcdyh())){
                    Map<String,Object> tdzMap =dyhTdzMap.get(bdcQlPageResponseDTO.getBdcdyh());
                    bdcQlPageResponseDTO.setTdzh(MapUtils.getString(tdzMap,"BDCQZH"));
                    bdcQlPageResponseDTO.setTdzzdmj(MapUtils.getString(tdzMap,"ZDZHMJ"));
                    bdcQlPageResponseDTO.setTdzfttdmj(MapUtils.getString(tdzMap,"FTTDMJ"));
                    bdcQlPageResponseDTO.setTdzdytdmj(MapUtils.getString(tdzMap,"DYTDMJ"));
                    bdcQlPageResponseDTO.setTdzsyqx(MapUtils.getString(tdzMap,"SYQX"));
                    bdcQlPageResponseDTO.setTdztdsyqssj(MapUtils.getString(tdzMap,"SYQQSSJ"));
                    bdcQlPageResponseDTO.setTdztdsyjssj(MapUtils.getString(tdzMap,"SYQJSSJ"));
                    bdcQlPageResponseDTO.setTdztdsyqssj2(MapUtils.getString(tdzMap,"SYQQSSJ2"));
                    bdcQlPageResponseDTO.setTdztdsyjssj2(MapUtils.getString(tdzMap,"SYQJSSJ2"));
                    bdcQlPageResponseDTO.setTdztdsyqssj3(MapUtils.getString(tdzMap,"SYQQSSJ3"));
                    bdcQlPageResponseDTO.setTdztdsyjssj3(MapUtils.getString(tdzMap,"SYQJSSJ3"));
                }
            }
        }

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private void glSdzt(Page<BdcQlPageResponseDTO> pageResponseDTOS){
        if(CollectionUtils.isNotEmpty(pageResponseDTOS.getContent())){
            for(BdcQlPageResponseDTO bdcQlPageResponseDTO:pageResponseDTOS.getContent()){

                List<BdcZssdDO> bdcZssdDOS = new ArrayList<>();
                List<BdcDysdDO> bdcDysdDOS = new ArrayList<>();
                if(StringUtils.isNotBlank(bdcQlPageResponseDTO.getXmid())){
                    bdcZssdDOS = bdcSdFeignService.queryBdczsSdByXmid(bdcQlPageResponseDTO.getXmid());
                }
                if(StringUtils.isNotBlank(bdcQlPageResponseDTO.getBdcdyh())){
                    BdcDysdDO bdcDysdDO = new BdcDysdDO();
                    bdcDysdDO.setBdcdyh(bdcQlPageResponseDTO.getBdcdyh());
                    bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_SD);
                    bdcDysdDOS = bdcSdFeignService.queryBdcdySd(bdcDysdDO);
                }

                //????????????????????????????????????
                if (CollectionUtils.isNotEmpty(bdcZssdDOS) || CollectionUtils.isNotEmpty(bdcDysdDOS)) {
                    List<Map> sdlxZdMap = bdcZdFeignService.queryBdcZd("sdlx");
                    bdcQlPageResponseDTO.setSdzt(BdcSdUtils.sdyyWithList(bdcZssdDOS, bdcDysdDOS, sdlxZdMap));
                }
            }
        }
    }

    private void glSczmd(Page<BdcQlPageResponseDTO> pageResponseDTOS) {
        if (CollectionUtils.isNotEmpty(pageResponseDTOS.getContent())) {
            for (BdcQlPageResponseDTO bdcQlPageResponseDTO : pageResponseDTOS.getContent()) {
                List<BdcXmDO> xscqXmList = bdcXmFeignService.listBdcXmXscq(Collections.singletonList(bdcQlPageResponseDTO.getBdcdyh()));
                if (CollectionUtils.isNotEmpty(xscqXmList)) {
                    bdcQlPageResponseDTO.setSczmdh(xscqXmList.get(0).getBdcqzh());
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private String genSdzt(String xmid, String bdcdyh) {
        List<BdcZssdDO> bdcZssdDOS = new ArrayList<>();
        List<BdcDysdDO> bdcDysdDOS = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            bdcZssdDOS = bdcSdFeignService.queryBdczsSdByXmid(xmid);
        }
        if(StringUtils.isNotBlank(bdcdyh)){
            BdcDysdDO bdcDysdDO = new BdcDysdDO();
            bdcDysdDO.setBdcdyh(bdcdyh);
            bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_SD);
            bdcDysdDOS = bdcSdFeignService.queryBdcdySd(bdcDysdDO);
        }

        //????????????????????????????????????
        if(CollectionUtils.isNotEmpty(bdcZssdDOS) || CollectionUtils.isNotEmpty(bdcDysdDOS)){
            List<Map> sdlxZdMap = bdcZdFeignService.queryBdcZd("sdlx");
            return BdcSdUtils.sdyyWithList(bdcZssdDOS, bdcDysdDOS, sdlxZdMap);
        }
        return "";
    }

    /**
     * ???????????????????????????????????????????????????????????????
     * @param
     * @return ?????????????????????id
     */
    @ResponseBody
    @GetMapping(value = "/ydslcxtj")
    public String getYdslcxtj(String cxym){
        if(org.apache.commons.lang.StringUtils.isBlank(cxym)){
            throw new MissingArgumentException("???????????????????????????????????????????????????????????????,??????????????????cxym");
        }
        return bdcYdslPzFeignService.listcxtj(cxym);
    }


    /**
     * @param loadpage ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @GetMapping("/ls/listBdcdyhByPageJson")
    public Object listLsBdcdyhByPageJson(@LayuiPageable Pageable pageable,
                                         BdcSlBdcdyhQO bdcSlBdcdyhQO,
                                         Boolean loadpage) {
        //???????????????????????????????????????????????????????????????????????????????????????(9?????????)
        if (sfxzcx) {
            UserDto userDto = userManagerUtils.getCurrentUser();
            if (userDto != null) {
                String xzqh = userManagerUtils.getRegionCodeByUserName(userDto.getUsername());
                if (StringUtils.isNotBlank(xzqh) && StringUtils.length(xzqh) == 9) {
                    bdcSlBdcdyhQO.setXjxzqh(xzqh);
                }
            }
        }
        bdcSlBdcdyhQO.setBdcdyh(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getBdcdyh()));
        bdcSlBdcdyhQO.setZl(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getZl()));
        if (StringUtils.isNotBlank(bdcSlBdcdyhQO.getBdclx()) && !TzmUtils.getCxByBdclx(bdcSlBdcdyhQO)) {
            //??????????????????????????????????????????
            return addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));

        }
        if (bdcSlBdcdyhQO.getDyhcxlx() != null) {
            if (StringUtils.isNotBlank(bdcSlBdcdyhQO.getZdtzm())) {
                TzmUtils.getQlxzAndZdtzm(bdcSlBdcdyhQO);
            }
        }

        return acceptLsBdcdyFeignService.listLsBdcdyhByPageOrList(pageable, JSON.toJSONString(bdcSlBdcdyhQO), loadpage);
    }
}
