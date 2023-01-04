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
 * @description 查询不动产单元
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
     * Redis调用服务
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
     * 是否乡镇查询
     * */
    @Value("${xztz.sfxzcx:false}")
    private boolean sfxzcx;

    @Value("${czsd.gzldyid:}")
    private String czsdGzldyid;

    //限制权力预告是否展现，默认不查询
    @Value("${sfcxyg:false}")
    private boolean sfcxyg;

    /*
     * 司法裁决转移关联查询锁定表数据
     * */
    @Value("#{'${sfcjzy.gzldyid:}'.split(',')}")
    private List<String> sfcjzyGzldyid;

    /**
     * 产权证选择台账，根据工作流定义ID过滤展示资金监管撤销数据
     */
    @Value("#{'${xztz.filterZjjg.gzldyids:}'.split(',')}")
    private List<String> zjjgGzldyids;

    @Value("#{'${query.tempqszt.gzldyids:}'.split(',')}")
    private List<String> queryTempQsztDyidList;

    /**
     * @param processDefKey 工作流定义ID
     * @return 不动产受理选择台账配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产受理选择台账配置
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
     * @param gzlslid 工作流实例ID
     * @return 选择不动产单元所需参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID转换选择不动产单元所需参数
     */
    @ResponseBody
    @GetMapping("/queryParams")
    public Object queryParams(String gzlslid) {
        Map map = new HashMap();
        //获取工作流定义ID参数
        ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
        if (StringUtils.isBlank(processInstanceData.getProcessDefinitionKey())) {
            throw new AppException("工作流实例未找到,请联系管理员");
        }
        map.put("processDefKey", processInstanceData.getProcessDefinitionKey());

        //获取基本信息ID参数，无值UUID生成
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
     * @param loadpage 是否分页
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 实测户室分页查询
     */
    @ResponseBody
    @GetMapping("/listBdcdyhByPageJson")
    public Object listBdcdyhByPageJson(@LayuiPageable Pageable pageable, BdcSlBdcdyhQO bdcSlBdcdyhQO, Boolean loadpage) {
        //如果是下级乡镇查询根据当前用户的行政区划代码判断单元号查询(9位代码)
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
            //查询条件矛盾，直接返回空数据
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
     * @param loadpage    是否分页
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 查询不动产项目(证书)
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
            //需要查询临时权属状态的流程（常州收费用途修正）
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

        // 常州三调查询 要区分行政区划  匹配bdcdyh的钱6位
        if (StringUtils.equals(czsdGzldyid, bdcQlQO.getGzldyid()) && StringUtils.isNotBlank(xzqh) && StringUtils.length(xzqh) == 6) {
            bdcQlQO.setQjxzqh(xzqh);
        }
        // 南通如果是下级乡镇查询根据当前用户的行政区划代码判断单元号查询(9位代码)
        if (sfxzcx && StringUtils.isNotBlank(xzqh) && StringUtils.length(xzqh) == 9) {
            bdcQlQO.setXjxzqh(xzqh);
        }
        // 常州资金监管流程查询时，过滤查询数据为资金监管数据
        if (CollectionUtils.isNotEmpty(zjjgGzldyids) && zjjgGzldyids.contains(bdcQlQO.getGzldyid())) {
            bdcQlQO.setSfzjjg(CommonConstantUtils.SF_S_DM);
        }

        //司法裁决转移登记关联锁定表查询锁定数据
        if (CollectionUtils.isNotEmpty(sfcjzyGzldyid) && sfcjzyGzldyid.contains(bdcQlQO.getGzldyid())) {
            bdcQlQO.setSfcjzy(CommonConstantUtils.SF_S_DM);
        }
        //如果抵押登记证明号不为空，先用抵押登记证明号查所有相关的物 的单元号，再用单元号查现势产权
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
        LOGGER.info("开始查询产权证数据，当前时间{},loadpage{}", simpleDateFormat.format(new Date()), loadpage);
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
                //关联土地证信息
                if (Boolean.TRUE.equals(bdcQlQO.getGltdz()) && CollectionUtils.isNotEmpty(fwdyhList)) {
                    glTdzxx(fwdyhList, pageResponseDTOS);
                }
                //关联锁定状态
                if (CollectionUtils.isNotEmpty(pageResponseDTOS.getContent()) && StringUtils.isNotBlank(bdcQlQO.getSfsdzt())) {
                    glSdzt(pageResponseDTOS);
                }
                //预告关联首次证明单
                if (CollectionUtils.isNotEmpty(pageResponseDTOS.getContent()) && bdcQlQO.getHbcxjg()) {
                    glSczmd(pageResponseDTOS);
                }
            }
            LOGGER.info("查询产权证结束时间为{}", simpleDateFormat.format(new Date()));
            return addLayUiCode(pageResponseDTOS);
        } else {
            return bdcXmFeignService.bdcQlList(bdcQlQO);
        }
    }


    @GetMapping("/listXmByPageJson/download/{lx}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "数据下载", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public void queryDataDownload(@LayuiPageable Pageable pageable,
                                  @PathVariable("lx") String lx,
                                  @RequestParam("param") String param,
                                  HttpServletResponse response
    ) {
        if (Objects.isNull(lx)) {
            throw new MissingArgumentException("参数错误");
        }
        Object data = null;
        //导出字段
        Map<String, String> exportColumn = new LinkedHashMap<>();
        //导出字典项
        Map<String, Consumer<Map<String, String>>> exportColumnFunction = new HashMap<>();
        switch (lx) {
            case "cd":
                data = listXmByPageJson(pageable, JSON.parseObject(param,BdcQlQO.class),true);
                exportColumn.put("不动产权证（明）号","bdcqzh");
                exportColumn.put("坐落","zl");
                exportColumn.put("权利人","qlrmc");
                //zdzhyt
                exportColumn.put("规划用途","dzwyt");
                //dzwmj
                exportColumn.put("建筑面积","dzwmj");
                exportColumn.put("土地权利面积","tdsyqmj");
                exportColumn.put("权属状态","qszt");
                exportColumn.put("限制状态","zt");
                break;
        }
        if (Objects.isNull(data)) {
            throw new MissingArgumentException("参数错误");
        }
        //流程状态处理
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
                                stringStringMap.put("zt","已预告");
                            }else if (jsonObject.containsKey("ycf") && jsonObject.getBoolean("ycf")) {
                                stringStringMap.put("zt","预查封");
                            }else if (jsonObject.containsKey("ydya") && jsonObject.getBoolean("ydya")) {
                                stringStringMap.put("zt","预抵押");
                            }else if (jsonObject.containsKey("cf") && jsonObject.getBoolean("cf")) {
                                stringStringMap.put("zt","查封");
                            }else if (jsonObject.containsKey("dya") && jsonObject.getBoolean("dya")) {
                                stringStringMap.put("zt","抵押");
                            }else if (jsonObject.containsKey("yy") && jsonObject.getBoolean("yy")) {
                                stringStringMap.put("zt","异议");
                            }else if (jsonObject.containsKey("sd") && jsonObject.getBoolean("sd")) {
                                stringStringMap.put("zt","锁定");
                            }else if (jsonObject.containsKey("dyi") && jsonObject.getBoolean("dyi")) {
                                stringStringMap.put("zt","地役");
                            }else if (jsonObject.containsKey("zjgcdy") && jsonObject.getBoolean("zjgcdy")) {
                                stringStringMap.put("zt","在建工程抵押");
                            }else if (jsonObject.containsKey("jzq") && jsonObject.getBoolean("jzq")) {
                                stringStringMap.put("zt","居住");
                            }else if (jsonObject.containsKey("fwcq") && jsonObject.getBoolean("fwcq")) {
                                stringStringMap.put("zt","房屋拆迁");
                            }else if (jsonObject.containsKey("ks") && jsonObject.getBoolean("ks")) {
                                stringStringMap.put("zt","可售");
                            }else if (jsonObject.containsKey("ys") && jsonObject.getBoolean("ys")) {
                                stringStringMap.put("zt","已售");
                            }else if (jsonObject.containsKey("xjspfks") && jsonObject.getBoolean("xjspfks")) {
                                stringStringMap.put("zt","新建商品房可售");
                            }else if (jsonObject.containsKey("xjspfys") && jsonObject.getBoolean("xjspfys")) {
                                stringStringMap.put("zt","新建商品房已售");
                            }else if (jsonObject.containsKey("clfks") && jsonObject.getBoolean("clfks")) {
                                stringStringMap.put("zt","存量房可售");
                            }else if (jsonObject.containsKey("clfys") && jsonObject.getBoolean("clfys")) {
                                stringStringMap.put("zt","存量房已售");
                            } else {
                                stringStringMap.put("zt","正常");
                            }
                        }else {
                            stringStringMap.put("zt","正常");
                        }
                    } else {
                        stringStringMap.put("zt","正常");
                    }
                } catch (Exception e) {
                    stringStringMap.put("zt","正常");
                    e.printStackTrace();
                }

                if(!stringStringMap.containsKey("zt") || StringUtils.isEmpty(stringStringMap.get("zt"))){
                    stringStringMap.put("zt","正常");
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
                    throw new MissingArgumentException("数据量过大");
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
            ExcelUtil.exportExcelWithConsumer("查档"+ new SimpleDateFormat("yyyyMMddHH").format(new Date()) +"导出.xls",
                    exportColumn,
                    content,
                    response,
                    exportColumnFunction);
        } catch (Exception e) {
            throw new AppException("导出Excel失败");
        }
    }


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询不动产项目(证书不分页)
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
     * @param loadpage 是否分页
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 查询不动产查封
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
     * @description 查询不动产查封(不分页)
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
     * @description 分页查询逻辑幢信息
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
     * @param bdcZssdQO 证书锁定查询对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 查询证书锁定
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
     * @param bdcDysdQO 单元锁定查询对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 查询单元锁定
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
     * @description 查询不动产单元现势状态
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
     * @param bdcdyh 不动产单元号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据bdcdyh查询房屋户室
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
     * @description 查询产权证现势状态
     */
    @ResponseBody
    @GetMapping("/queryBdcqzZt")
    public Object queryBdcqzZt(String bdcdyh, String qllx,String zsxmid,String qjgldm) {
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.isNotBlank(qllx)) {
            if (!bdcdyztQbxs) {
                if (StringUtils.equals(CommonConstantUtils.QLLX_DYAQ_DM.toString(),qllx) || StringUtils.equals(qllx, "97") || StringUtils.equals(qllx, "98")) {
                    //查封，抵押，异议不查询状态，预告只需要查询是否存在预查封和预抵押
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
                    //土地承包权需要根据人+物查询状态
                    return  bdcdyZtFeignService.queryZsBdcdyZtByCqzsxmid(zsxmid);
                }
                else {
                    return bdcdyZtFeignService.commonQueryBdcdyhZtBybdcdyh(StringUtils.deleteWhitespace(bdcdyh),qjgldm);
                }
            } else {
                if(CommonConstantUtils.QLLX_TDCBNYDSYQ.equals(Integer.parseInt(qllx))){
                    //土地承包权需要根据人+物查询状态
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
     * 查询逻辑幢状态
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
     * @description 查询不动产单元权利历史关系
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
     * @param lx 权籍表类型
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据不动产单元号查询不动产类型
     */
    @ResponseBody
    @GetMapping("/queryBdclxByBdcdyh")
    public Map queryBdclxByBdcdyh(String bdcdyh, String lx, String gzldyid) {
        Map map = Maps.newHashMap();
        String bdclx = "";
        //先根据不动产不动产单元获取不动产类型，无法唯一判断的根据流程配置获取
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
     * @param json 项目id
     * @return 配置结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取匹配页面条件配置
     */
    @ResponseBody
    @PostMapping("/pplzzt")
    public Map getPplzzt(@RequestBody String json, String xztzlx) {
        Map map = Maps.newHashMap();
        JSONArray jsonArray = JSONObject.parseArray(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            String bdcdyh = obj.getString("bdcdyh");
            //是否是纯土地的不动产单元号
            boolean isttbdcdyh = CommonConstantUtils.BDCLX_TZM_W.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh));
            if (StringUtils.isNotBlank(obj.getString("xmid"))
                    && StringUtils.isNotBlank(obj.getString("bdcdyh"))) {
                // 匹配关系
                List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(obj.getString("xmid"));
                if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                    if (bdcFctdPpgxDOList.size() > 1) {
                        map.put("mullz", obj.getString("xmid"));
                    }
                    BdcFctdPpgxDO bdcFctdPpgxDO = bdcFctdPpgxDOList.get(0);
                    //设置匹配状态
                    if (null == bdcFctdPpgxDO) {
                        map.put("sfpp", "false");
                    } else {
                        map.put("sfpp", "true");
                    }
                } else {
                    map.put("sfpp", "false");
                }
                // 落宗
                List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList = bdcPpFeignService.queryBdcXnbdcdyhGxByXmid(obj.getString("xmid"));
                if (CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)) {
                    if (bdcXnbdcdyhGxDOList.size() > 1) {
                        //存在多条匹配关系
                        map.put("mullz", obj.getString("xmid"));
                    }
                    BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO = bdcXnbdcdyhGxDOList.get(0);
                    //已匹配落宗的单元号，显示已落宗
                    if (bdcXnbdcdyhGxDO != null) {
                        map.put("sflz", "true");
                    } else {
                        map.put("sflz", "false");
                    }
                    //非虚拟单元号的产权证
                    if (!BdcdyhToolUtils.checkXnbdcdyh(obj.getString("bdcdyh"))) {
                        //查封台账没有产权证号
                        if (StringUtils.isNotBlank(xztzlx) && "2".equals(xztzlx)) {
                            String bdcqzh = "";
                            if (StringUtils.isNotBlank(obj.getString("bdcqzh"))) {
                                bdcqzh = obj.getString("bdcqzh");
                            } else {
                                bdcqzh = obj.getString("ybdcqz");
                            }
                            if (StringUtils.isNotBlank(bdcqzh) && bdcqzh.contains(CommonConstantUtils.BDC_BDCQZH_BS) || StringUtils.isBlank(bdcqzh)) {
                                //不动产数据不显示是否落宗匹配和查看按钮
                                map.put("sflz", null);
                                map.put("sfpp", null);
                                //不动产数据可以重新关联，显示重新关联按钮
                                map.put("sfcxgl", sfcxgl);
                            }
                        }
                    }
                } else {
                    //未查询到落宗数据但是单元号正常
                    if (!BdcdyhToolUtils.checkXnbdcdyh(obj.getString("bdcdyh"))) {
                        //查封台账没有产权证号
                        if (StringUtils.isNotBlank(xztzlx) && StringUtils.equals(xztzlx, "2")) {
                            String bdcqzh = "";
                            if (StringUtils.isNotBlank(obj.getString("bdcqzh"))) {
                                bdcqzh = obj.getString("bdcqzh");
                            } else {
                                bdcqzh = obj.getString("ybdcqz");
                            }
                            if (StringUtils.isNotBlank(bdcqzh) && bdcqzh.contains(CommonConstantUtils.BDC_BDCQZH_BS) || StringUtils.isBlank(bdcqzh)) {
                                //不动产数据不显示是否落宗匹配和查看按钮
                                map.put("sflz", null);
                                map.put("sfpp", null);
                                //不动产数据可以重新关联，显示重新关联按钮
                                map.put("sfcxgl", sfcxgl);
                            } else {
                                //存量数据显示落宗
                                map.put("sflz", "true");
                            }
                        } else {
                            //存量数据显示落宗
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
            //多条数据的话只要有一条存在未未匹配或者未落宗 或者多条匹配关系就返回
            if ((map.get("sflz") != null && map.get("sflz") == "false")
                    || map.get("sfpp") != null && map.get("sfpp") == "false"
                    || map.get("mullz") != null) {
                break;
            }
        }
        return map;
    }

    /**
     * @param xmid 项目id
     * @return 匹配结果
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 是否展现匹配按钮
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
            //只要有一个获取到数据就说明曾经匹配过，展现查看按钮
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
     * @param bdcdyh 项目id
     * @return 匹配结果
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据bdcdyh和ajzt判断是否正在办理的
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
             * @description 增加判断逻辑，如果存在被关联为外联证书，如果外联证书的主数据正在办理，那么该条数据也显示正在办理
             * @date : 2020/9/18 10:49
             */
            //根据xmid找历史关系且是外联证书的数据
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
     * @param: gzldyid 工作流定义id
     * @return: boolean 是否匹配 匹配true 不匹配false
     * @description 根据配置文件进行对比，若存在配置则匹配。
     */
    @GetMapping("/tdppfw/{gzldyid}")
    @ResponseBody
    public boolean queryAllowedGzldyid(@PathVariable(value = "gzldyid") String gzldyid) {
        if (StringUtils.isBlank(gzldyid)) {
            throw new NullPointerException("未获取到工作流程定义id。");
        }
        return CollectionUtils.isNotEmpty(tdppfw) && tdppfw.contains(gzldyid);
    }

    @GetMapping("/fwhs/page")
    @ResponseBody
    public Object listfwhsByPage(@LayuiPageable Pageable pageable, String fwbm, BdcSlBdcdyhQO bdcSlBdcdyhQO) {
        //房屋编码为空查询权籍所有单元号
        Object object;
        if (StringUtils.isNotBlank(fwbm)) {
            Map<String, String> queryMap = new HashMap<>();
            queryMap.put("fwbm", fwbm);
            Page<FwHsDO> fwHsDOPage = fwHsFeignService.listFwHsByPageJson(pageable, JSON.toJSONString(queryMap));
            //根据房屋编码未能找到数据则默认查询所有单元号
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
                //查预测户室数据
                Page<FwYchsDO> fwYcHsPage = fwYcHsFeignService.listYchsByPage(pageable, JSON.toJSONString(queryMap));
                //没有预测户室数据查全部
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
     * 用于校验外联土地证是否存在限制状态
     *
     * @param xmids         项目ID集合
     * @param processDefKey 工作流定义ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/plQueryXzQl")
    public boolean plQueryXzQl(@RequestBody List<String> xmids, @RequestParam(value = "processDefKey") String processDefKey) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(xmids), "未获取到项目ID信息");
        Preconditions.checkArgument(StringUtils.isNotBlank(processDefKey), "未获取到流程定义ID信息。");
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
            throw new AppException("查询权力信息缺失xmid");
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
     * @param pageable 分页对象
     * @param bdcQlQO 查询对象
     * @param loadpage 是否查询全部
     * @param queryType 查询方式 0:3.0登记库 1：原登记库
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询原项目信息
     */
    @ResponseBody
    @GetMapping("/listYxmByPageJson")
    @LogNote(value = "选择合同号", action = LogConstans.LOG_TYPE_XZTZ, custom = LogConstans.LOG_TYPE_XZTZ)
    public Object listYxmByPageJson(@LayuiPageable Pageable pageable, BdcQlQO bdcQlQO, Boolean loadpage,String queryType) {
        String htbh = bdcQlQO.getHtbh();
        String gzldyid = bdcQlQO.getGzldyid();
        String cqzh = bdcQlQO.getBdcqzh();
        Object ydjxxObj = null;
        Map queryYdjMap = new HashMap();
        List<Map> list = new ArrayList<>();
        List listDto =new ArrayList();
        //登记3.0产权数据
        List<BdcQlPageResponseDTO> bdcQlPageResponseDTOList =null;
        // 当没有产权证号 且 有合同编号的时候 是合同编号创建页面
        if(StringUtils.isBlank(cqzh) && StringUtils.isNotBlank(htbh)){
            Date getHtxxStartTime = new Date();
            // 调取合同信息接口 获得合同信息
            Object response = this.getHtxxByHtbh(htbh, gzldyid);
            long getHtxxTime = System.currentTimeMillis() - getHtxxStartTime.getTime();
            LOGGER.info("调取合同信息接口，所耗时间：{}", getHtxxTime);
            FcjyClfHtxxDTO fcjyClfHtxxDTO = JSON.parseObject(JSONObject.toJSONString(response),FcjyClfHtxxDTO.class);
            if(!StringUtils.equals(fcjyClfHtxxDTO.getCode(),CommonConstantUtils.SUCCESS_CODE)){
                throw new AppException("调用合同信息接口返回失败，信息：" + fcjyClfHtxxDTO.getMsg());
            }
            if(null != fcjyClfHtxxDTO.getBdcSlXmDO() && null != fcjyClfHtxxDTO.getBdcSlJyxx()){
                String zl = fcjyClfHtxxDTO.getBdcSlXmDO().getZl();
                if(StringUtils.isBlank(zl)){
                    throw new AppException("交易信息接口返回的受理项目信息中坐落为空！");
                }

                // 商品房的gzldyid
                String pzGzldyid = getSpfGzldyid();
                String fwbhlx ="";
                if(fcjyClfHtxxDTO.getQtAttrMap() != null &&fcjyClfHtxxDTO.getQtAttrMap().get("fwbhlx") != null) {
                    fwbhlx = String.valueOf(fcjyClfHtxxDTO.getQtAttrMap().get("fwbhlx"));
                }
                //获取产权坐落,商品房且fwbhlx为1时,坐落需要通过权籍视图获取对应坐落
                if(pzGzldyid.indexOf(bdcQlQO.getGzldyid()) > -1 &&StringUtils.equals("1",fwbhlx)){
                    Date startTime = new Date();
                    //此处交易坐落即为施工编号
                    zl =acceptBdcdyFeignService.queryZlBySgbh(zl);
                    long time = System.currentTimeMillis() - startTime.getTime();
                    LOGGER.info("需要通过权籍视图获取对应坐落，所耗时间：{}", time);
                }
                if(StringUtils.isNotBlank(zl)) {
            //根据坐落查询产权信息
                    bdcQlQO.setZl(zl);
                    bdcQlQO.setZlmh("1");
            bdcQlQO.setQszt(CommonConstantUtils.QSZT_VALID);
                    bdcQlQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("selectbdcdyh",bdcQlQO.getGzldyid(),""));
            bdcQlPageResponseDTOList =bdcXmFeignService.bdcQlList(bdcQlQO);
                }
            }else{
                throw new AppException("通过合同编号未查询到相应的产权");
            }
            // 当有产权证号 且 没有合同编号的时候 是产权证台账创建页面
        }else if(StringUtils.isNotBlank(cqzh) && StringUtils.isBlank(htbh)){
            queryYdjMap.put("bdcqzh",cqzh);
            LOGGER.info("调取常州查询原登记系统的入参：{}",queryYdjMap);
            ydjxxObj = exchangeInterfaceFeignService.requestInterface("getCzYdjxtXx", queryYdjMap);
            if(ydjxxObj ==null){
                throw new AppException("调取常州查询原登记系统的返回值为null");
            }
        }else{
            throw new AppException("缺失查询条件！");
        }

        if(null != ydjxxObj){
            listDto  = (List)ydjxxObj;
        }
        //登记3.0产权数据
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
        //老登记库产权数据
        if(CollectionUtils.isNotEmpty(listDto)){
            LOGGER.info("调取常州查询原登记系统的返回值：{}",JSONObject.toJSONString(listDto));
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
     * @param htbh 合同编号
     * @param gzldyid 工作流定义id
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取交易信息
     */
    @ResponseBody
    @PostMapping("/htxxByHtbh/{htbh}/{gzldyid}")
    public Object getHtxxByHtbh(@PathVariable(value="htbh") String htbh, @PathVariable(value="gzldyid") String gzldyid) {
        Map map = new HashMap();
        map.put("contractNo",htbh);
        LOGGER.info("调取常州合同信息接口入参：{}",map);
        Object response;
        String pzGzldyid = getSpfGzldyid();
        if(StringUtils.isBlank(gzldyid)){
            throw new AppException("传入流程的工作流定义id为空");
        }
        if(StringUtils.isBlank(pzGzldyid)){
            throw new AppException("配置的商品房工作流定义id为空");
        }
        if(pzGzldyid.indexOf(gzldyid) > -1 ){
            response = exchangeInterfaceFeignService.requestInterface("fcjySpfBaxx", map);
        }else{
            response = exchangeInterfaceFeignService.requestInterface("fcjyClfHtxx", map);
        }
        //尝试去查询合同号的资金监管信息
        bdcZjjgFeignService.sfzjjgYthpt(gzldyid,htbh);
        if(null != response){
            LOGGER.info("调取常州合同信息接口返回值：{}",JSONObject.toJSONString(response));
            return response;
        }else{
            throw new AppException("调取常州合同信息接口返回值为空");
        }
    }

    /**
     * 常州特殊情况，在创建的时候需要先插入原项目信息
     * @param yywxxList
     */
    @ResponseBody
    @PostMapping("/insertYxmxx")
    public Object insertYxmxx(@RequestBody List<DvHlwResponseDto> yywxxList) {
        if(CollectionUtils.isNotEmpty(yywxxList)){
            Object response = exchangeInterfaceFeignService.requestInterface("initYxmxx", yywxxList);
            if(null != response){
                LOGGER.info("插入原项目信息返回信息为：{}",JSONObject.toJSONString(response));
                Map map = (Map)response;
                if(map.containsKey("yxmid")){
                    return map.get("yxmid");
                }else{
                    throw new AppException("插入原项目信息未返回yxmid值");
                }
            }
        }else{
            throw new AppException("插入原项目信息值为空");
        }
        return "";
    }



    /**
     * @param fwDcbIndex
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否为独幢
     * @date : 2020/9/30 10:31
     */
    @ResponseBody
    @GetMapping("/sfdz")
    public Boolean sfdz(String fwDcbIndex,String qjgldm) {
        boolean sfdz = false;
        if(StringUtils.isBlank(fwDcbIndex)) {
            throw  new AppException("判断是否独幢缺失逻辑幢主键");
        }
        FwLjzDO fwLjzDO =fwLjzRestService.queryLjzByFwDcbIndex(fwDcbIndex,qjgldm);
        if(fwLjzDO != null &&StringUtils.equals(CommonConstantUtils.BDCDYFWLX_DUZH.toString(),fwLjzDO.getBdcdyfwlx())){
            sfdz = true;
        }
        return sfdz;
    }

    /**
     * 配置项 查询商品房的工作流定义id
     * @return SpfGzldyid
     */
    public String getSpfGzldyid() {
        String spfGzldyid = bdcSlJbxxFeignService.spfGzldyid();
        LOGGER.info("读取配置的商品房的gzldyid：{}",spfGzldyid);
        return spfGzldyid;
    }

    /**
     * 南通 合同号创建台账 查询bdcqzh
     * @param bdcBahtQO
     * @return
     */
    @ResponseBody
    @PostMapping("/queryBaht/param")
    public Object queryBahtParam(@RequestBody BdcBahtQO bdcBahtQO) {
        String spfGzldyid = bdcSlJbxxFeignService.spfGzldyid();
        // 查询产权证参数（bdcqzh+zslx+htbh）
        Map cxParam = new HashMap();
        if(StringUtils.isNotBlank(bdcBahtQO.getGzldyid())){
            Object response = null;
            // 前台传了备案合同查询类型，按照查询类型调用接口
            if(StringUtils.isNotBlank(bdcBahtQO.getCxlx())){
                if(CommonConstantUtils.BAHTCXLX_SPF.equals(bdcBahtQO.getCxlx())){
                    LOGGER.info("南通合同选择台账，调用接口beanId:rd_spfhtxx,参数：{}",JSONObject.toJSONString(bdcBahtQO));
                    response = exchangeInterfaceFeignService.requestInterface("rd_spfhtxx",bdcBahtQO);
                    cxParam.put("zslx",CommonConstantUtils.ZSLX_ZMD);
                }else{
                    LOGGER.info("南通合同选择台账，调用接口beanId:rd_clfhtxx,参数：{}",JSONObject.toJSONString(bdcBahtQO));
                    response = exchangeInterfaceFeignService.requestInterface("rd_clfhtxx",bdcBahtQO);
                }
            }else{
                if(spfGzldyid.indexOf(bdcBahtQO.getGzldyid()) > -1){
                    LOGGER.info("南通合同选择台账，调用接口beanId:rd_spfhtxx,参数：{}",JSONObject.toJSONString(bdcBahtQO));
                    response = exchangeInterfaceFeignService.requestInterface("rd_spfhtxx",bdcBahtQO);
                    cxParam.put("zslx",CommonConstantUtils.ZSLX_ZMD);
                }else{
                    LOGGER.info("南通合同选择台账，调用接口beanId:rd_clfhtxx,参数：{}",JSONObject.toJSONString(bdcBahtQO));
                    response = exchangeInterfaceFeignService.requestInterface("rd_clfhtxx",bdcBahtQO);
                }
            }

            if(null != response){
                LOGGER.info("南通合同选择台账，调用接口返回值：{}",JSONObject.toJSONString(response));
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
        //匹配情况下,房屋单元号与土地单元号相同
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

                //存在单元锁定或者证书锁定
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

        //存在单元锁定或者证书锁定
        if(CollectionUtils.isNotEmpty(bdcZssdDOS) || CollectionUtils.isNotEmpty(bdcDysdDOS)){
            List<Map> sdlxZdMap = bdcZdFeignService.queryBdcZd("sdlx");
            return BdcSdUtils.sdyyWithList(bdcZssdDOS, bdcDysdDOS, sdlxZdMap);
        }
        return "";
    }

    /**
     * 查询是否是异地受理角色需要展示部分查询条件
     * @param
     * @return 查询条件的页面id
     */
    @ResponseBody
    @GetMapping(value = "/ydslcxtj")
    public String getYdslcxtj(String cxym){
        if(org.apache.commons.lang.StringUtils.isBlank(cxym)){
            throw new MissingArgumentException("查询是否是异地受理角色需要展示部分查询条件,缺失请求参数cxym");
        }
        return bdcYdslPzFeignService.listcxtj(cxym);
    }


    /**
     * @param loadpage 是否分页
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 历史户室分页查询
     */
    @ResponseBody
    @GetMapping("/ls/listBdcdyhByPageJson")
    public Object listLsBdcdyhByPageJson(@LayuiPageable Pageable pageable,
                                         BdcSlBdcdyhQO bdcSlBdcdyhQO,
                                         Boolean loadpage) {
        //如果是下级乡镇查询根据当前用户的行政区划代码判断单元号查询(9位代码)
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
            //查询条件矛盾，直接返回空数据
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
