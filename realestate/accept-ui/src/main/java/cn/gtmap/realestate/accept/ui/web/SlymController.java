package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshFwkgDataDTO;
import cn.gtmap.realestate.common.core.dto.accept.LpbFjDTO;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmZhxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQzxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcYxmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcSynchFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/12/17,1.0
 * @description 受理页面
 */
@Controller
@RequestMapping("rest/v1.0/slym")
public class SlymController extends BaseController {

    private static final String SLYMZH_URL = "/view/slym/slymzh.html";
    private static final String SLYMPL_URL = "/view/slym/slympl.html";
    private static final String SLYMPLZH_URL = "/view/slym/slymplzh.html";
    private static final String SLYMQJFJLIST_URL = "/realestate-accept-ui/view/query/slymqjfjList.html";
    private static final String SFCJ_URL = "/view/slym/sfcj.html";

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSynchFeignService bdcSynchFeignService;
    @Autowired
    DjxxFeignService djxxFeignService;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    private LogMessageClient logMessageClient;
    @Autowired
    BdcInitFeignService bdcInitFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcdyFeignService bdcdyFeignService;
    @Autowired
    FwLjzFeginService fwLjzFeginService;
    @Autowired
    ProcessTaskClient processTaskClient;

    @Autowired
    BdcGzyzFeignService bdcGzyzFeignService;

    @Autowired
    BdcDjbxxFeignService bdcDjbxxFeignService;

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    /**
     * 档案附件地址
     */
    @Value("${url.archiveFjUrl:}")
    private String archiveFjUrl;
    /**
     * CAS认证地址
     */
    @Value("${yzt.casUrl:}")
    private String casUrl;
    /**
     * 一张图出让合同链接地址
     */
    @Value("${yzt.yzthtUrl:}")
    private String yzthtUrl;

    /**
     * 需要验证身份证是否为18位的工作流定义id集合
     */
    @Value("#{'${sfzyz.gzldyid:}'.split(',')}")
    protected List<String> sfzyzdyidList;

    @Value("${cxdj.gzldyid:}")
    private String cxdjGzldyid;

    @Value("#{'${sfcj.gzldyid:}'.split(',')}")
    private List<String> sfcjGzldyidList;

    /**
     * 批量流程一本证的工作流定义id
     */
    @Value("#{'${pllcybz.gzldyid:}'.split(',')}")
    private List<String> pllcybzGzldyidList;

    /**
     * 受理页面获取商品房交易信息时，合同编号默认值
     */
    @Value("${slym.spf.htbh:}")
    private String slymSpfHtbh;

    /**
     * 受理页面获取存量房交易信息时，合同编号默认值
     */
    @Value("${slym.clf.htbh:}")
    private String slymClfHtbh;

    /**
     * 获取公证处材料webservice接口url
     */
    @Value("${webservice.requestUrl.gzcl:}")
    private String gzclRequestUrl;

    /**
     * 从第三方获取获取公证处材料保存到附件材料的文件夹名称
     */
    @Value("${sjcl.gzclWjjmc: 公证处材料}")
    private String gzclWjjmc;

    /**
     * 从第三方获取获取公证处材料保存到附件材料的文件名称
     */
    @Value("${sjcl.gzclWjmc: 公证书}")
    private String gzclWjmc;

    /**
     * @param processInsId 工作流实例ID
     * @return 页面地址
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 受理页面统一入口
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardSlymHtml(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "processDefKey", required = false) String processDefKey) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(processInsId);
        LOGGER.info("{}根据gzlslid获取项目类型：{}", processInsId, xmlx);
        //1.如果当前流程是撤销登记，判断上一手
        if (StringUtils.isNotBlank(processDefKey) && StringUtils.equals(processDefKey, cxdjGzldyid)) {
            //当前流程查询上一手判断是否是司法裁决，司法裁决
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                BdcYxmQO bdcYxmQO = new BdcYxmQO();
                bdcYxmQO.setGzlslid(processInsId);
                bdcYxmQO.setWlxm(CommonConstantUtils.SF_S_DM);
                List<BdcXmDO> wlxmList = bdcXmFeignService.listYxmByYxmQO(bdcYxmQO);
                if (CollectionUtils.isNotEmpty(wlxmList)) {
                    //判断所有外联项目看是否有司法裁决数据
                    for (BdcXmDO wlxm : wlxmList) {
                        if (CollectionUtils.isNotEmpty(sfcjGzldyidList) && sfcjGzldyidList.contains(wlxm.getGzldyid())) {
                            return SFCJ_URL;
                        }
                    }
                }
            }
        }
        if (CommonConstantUtils.LCLX_PL.equals(xmlx)) {
            //批量流程一本证，权利类型不同的话，跳转受理批量组合页面
            if(CollectionUtils.isNotEmpty(pllcybzGzldyidList)){
                List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
                if(CollectionUtils.isNotEmpty(bdcXmDTOList) && bdcXmDTOList.get(0) != null && pllcybzGzldyidList.contains(bdcXmDTOList.get(0).getGzldyid())){
                    Set<Integer> qllxSet = new HashSet<>();
                    for(BdcXmDTO bdcxm : bdcXmDTOList) {
                        if(bdcxm.getQllx()!= null){
                            qllxSet.add(bdcxm.getQllx());
                        }
                    }
                    if(qllxSet.size() > 1){
                        return SLYMPLZH_URL;
                    }
                }
            }
            //批量流程
            return SLYMPL_URL;
        } else if (CommonConstantUtils.LCLX_PLZH.equals(xmlx)) {
            //批量组合流程
            return SLYMPLZH_URL;
        } else {
            //简单流程和单个组合流程
            return SLYMZH_URL;
        }
    }

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 同步权籍数据
     */
    @ResponseBody
    @PatchMapping("/synchLpbDataToLc")
    public void synchLpbDataToLc(@RequestParam(name = "processInsId") String processInsId) throws Exception {
        bdcSynchFeignService.synchLpbDataToLc(processInsId);
    }

    /**
     * @param json 原对象和目标对象
     * @return 整合后的目标对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 数据同步对照
     */
    @ResponseBody
    @PostMapping("/ywxxDozer")
    public Object ywxxDozer(@RequestBody String json, @RequestParam(name = "processInsId") String processInsId) throws Exception {
        //判断对象是否为上下手关系且第2个对象权利数据来源为上一手
        if (checkSfDozer(processInsId)) {
            List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByProcessInsId(processInsId);
            if (CollectionUtils.isNotEmpty(bdcQlList) && bdcQlList.size() == 2 && StringUtils.isNotBlank(json)) {
                List<Object> list = JSONObject.parseArray(json);
                return bdcInitFeignService.ywxxDozerMap(list, bdcQlList.get(0).getClass().getName(), bdcQlList.get(1).getClass().getName());

            }
        }
        return null;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 权籍附件
     */
    @ResponseBody
    @GetMapping("lpbFjUrl")
    public String forwardlpbFjUrl(@RequestParam(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm",required = false) String qjgldm) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("不动产单元号不能为空！");
        }
        if (StringUtils.isBlank(archiveFjUrl)) {
            throw new AppException("未配置档案附件地址，请联系管理员！");
        }
        String slbh = "";
        String url = "";
        LpbFjDTO lpbFjDTO = getLpbFjDTOByBdcdyh(bdcdyh,qjgldm);
        slbh = lpbFjDTO.getSlbh();
        if (StringUtils.isBlank(slbh)) {
            throw new AppException("未找到对应的权籍数据！");
        }
        url = archiveFjUrl + slbh;
        return url;

    }

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    @ResponseBody
    @GetMapping("lpbFjUrlByLc")
    public Object lpbFjUrlByLc(@RequestParam(name = "processInsId") String processInsId) {
        String url = "";
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        Map<String,BdcXmDTO> ljzMap =groupLjzMapByGzlslid(processInsId);
        if (MapUtils.isNotEmpty(ljzMap)) {
            if (ljzMap.size() == 1) {
                for (Map.Entry<String, BdcXmDTO> entry : ljzMap.entrySet()) {
                    BdcXmDTO bdcXmDTO = entry.getValue();
                    if (bdcXmDTO != null) {
                        url = forwardlpbFjUrl(bdcXmDTO.getBdcdyh(),bdcXmDTO.getQjgldm());
                    }
                }
            } else {
                return SLYMQJFJLIST_URL + "?gzlslid=" + processInsId;
            }

        } else {
            throw new AppException("未找到对应的权籍数据！");
        }
        return url;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查看权籍附件列表分页
     */
    @ResponseBody
    @GetMapping(value = "/listQjfjByPageJson")
    public Object listQjfjByPageJson(@LayuiPageable Pageable pageable, String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失参数gzlslid");
        }
        Map<String,BdcXmDTO> ljzMap =groupLjzMapByGzlslid(gzlslid);
        List<LpbFjDTO> lpbFjDTOList = new ArrayList<>();
        if (MapUtils.isNotEmpty(ljzMap)) {
            for (Map.Entry<String, BdcXmDTO> entry : ljzMap.entrySet()) {
                BdcXmDTO bdcXmDTO = entry.getValue();
                if (bdcXmDTO != null) {
                    LpbFjDTO lpbFjDTO = getLpbFjDTOByBdcdyh(bdcXmDTO.getBdcdyh(),bdcXmDTO.getQjgldm());
                    if (lpbFjDTO != null) {
                        lpbFjDTOList.add(lpbFjDTO);
                    }
                }
            }
        }
        //封装返回分页信息
        return addLayUiCode(new PageImpl(lpbFjDTOList, pageable, lpbFjDTOList.size()));
    }

    /**
     * @param json
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 记录受理页面字段修改日志
     */
    @ResponseBody
    @PostMapping("/addXgLog")
    public void addXgLog(@RequestBody String json, String gzlslid) throws Exception {
        Map<String, Object> data = initData(gzlslid, json);
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), Constants.SJXG, data);
        zipkinAuditEventRepository.add(auditEvent);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 返回日志信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询修改日志
     */
    @ResponseBody
    @GetMapping("/queryXgLog")
    public Object queryXgLog(String gzlslid) {
        List<QueryLogCondition> conditions = Lists.newArrayList();
        QueryLogCondition queryLogCondition = new QueryLogCondition();
        queryLogCondition.setType(CommonConstantUtils.TYPE_EQUAL);
        queryLogCondition.setKey(Constants.SJXG_PARAMCH);
        queryLogCondition.setValue(gzlslid);
        conditions.add(queryLogCondition);
        Object obj = null;
        Page<AuditLogDto> auditLogDtoPage = logMessageClient.listLogs(0, 1, Constants.SJXG, userManagerUtils.getCurrentUserName(), null, null, null, conditions);
        if (auditLogDtoPage.hasContent()) {
            //解析数据
            for (AuditLogDto auditLogDto : auditLogDtoPage) {
                //默认只存在一条
                List<DataValue> dataValueList = auditLogDto.getBinaryAnnotations();
                for (DataValue dataValue : dataValueList) {
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), "change")) {
                        obj = dataValue;
                        break;
                    }
                }
            }
        }
        return obj;
    }

    /**
     * @param json    数据修改内容
     * @param gzlslid 工作流
     * @return {Map} 返回日志保存需要的对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化日志data数据
     */
    public static Map<String, Object> initData(String gzlslid, String json) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.SJXG_PARAMCH, gzlslid);
        data.put(Constants.SJXG_CHANGE, json);
        data.put(CommonConstantUtils.VIEW_TYPE_NAME, "受理页面修改信息");
        data.put("eventName", Constants.SJXG);
        return data;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 判断组合项目是否需要对照
     */
    private Boolean checkSfDozer(String gzlslid) {
        Boolean sfdz = false;
        //判断对象是否为上下手关系且第2个对象权利数据来源为上一手
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList) && bdcXmDTOList.size() == 2) {
            bdcXmDTOList.sort(Comparator.comparing(BdcXmDTO::getXmid));
            BdcXmDTO bdcXmDTO = bdcXmDTOList.get(0);
            List<BdcXmZhxxDTO> bdcXmZhxxDTOList = bdcXmFeignService.queryBdcXmZhxx(bdcXmDTO.getXmid());
            if (CollectionUtils.isNotEmpty(bdcXmZhxxDTOList)) {
                for (BdcXmZhxxDTO bdcXmZhxxDTO : bdcXmZhxxDTOList) {
                    if (!StringUtils.equals(bdcXmZhxxDTO.getXmid(), bdcXmDTO.getXmid()) && StringUtils.equals(bdcXmZhxxDTO.getXmid(), bdcXmDTOList.get(1).getXmid()) && bdcXmDTO.getQllx().equals(bdcXmZhxxDTO.getQllx())) {
                        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmZhxxDTO.getXmid());
                        //预告和预抵押之间不进行对照
                        if (bdcCshFwkgSlDO != null && StringUtils.equals(CommonConstantUtils.QLSJLY_YXM, bdcCshFwkgSlDO.getQlsjly()) &&!CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDTO.getQllx())) {
                            sfdz = true;
                        }
                    }
                }
            }
        }
        return sfdz;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return 权籍附件对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元号组织权籍附件对象
     */
    private LpbFjDTO getLpbFjDTOByBdcdyh(String bdcdyh,String qjgldm) {
        LpbFjDTO lpbFjDTO = new LpbFjDTO();
        String slbh = "";
        if (StringUtils.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh), CommonConstantUtils.BDCLX_TZM_F)) {
            BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryBdcdy(bdcdyh, "",qjgldm);
            if (bdcdyResponseDTO != null && StringUtils.isNotBlank(bdcdyResponseDTO.getFwDcbIndex())) {
                FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(bdcdyResponseDTO.getFwDcbIndex(),qjgldm);
                if (fwLjzDO != null) {
                    slbh = fwLjzDO.getSlbh();
                    lpbFjDTO.setBdcdyh(fwLjzDO.getBdcdyh());
                    lpbFjDTO.setFwmc(fwLjzDO.getFwmc());
                    lpbFjDTO.setLszd(fwLjzDO.getLszd());
                    lpbFjDTO.setZl(fwLjzDO.getZldz());
                }
            }
        } else {
            String tdBdcdyh = StringUtils.substring(bdcdyh, 0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
            DjDcbResponseDTO djDcbResponseDTO = djxxFeignService.queryDjDcbByBdcdyh(tdBdcdyh,qjgldm);
            if (djDcbResponseDTO != null) {
                if (djDcbResponseDTO instanceof ZdDjdcbResponseDTO) {
                    slbh = ((ZdDjdcbResponseDTO) djDcbResponseDTO).getSlbh();
                    lpbFjDTO.setBdcdyh(((ZdDjdcbResponseDTO) djDcbResponseDTO).getBdcdyh());
                } else if (djDcbResponseDTO instanceof NydDjdcbResponseDTO) {
                    slbh = ((NydDjdcbResponseDTO) djDcbResponseDTO).getSlbh();
                    lpbFjDTO.setBdcdyh(((NydDjdcbResponseDTO) djDcbResponseDTO).getBdcdyh());
                } else if (djDcbResponseDTO instanceof QszdDjdcbResponseDTO) {
                    slbh = ((QszdDjdcbResponseDTO) djDcbResponseDTO).getSlbh();
                    lpbFjDTO.setBdcdyh(((QszdDjdcbResponseDTO) djDcbResponseDTO).getBdcdyh());
                }
            }
        }
        lpbFjDTO.setSlbh(matchSlbh(slbh,qjgldm));
        lpbFjDTO.setArchiveFjUrl(archiveFjUrl);
        return lpbFjDTO;
    }

    /**
     * 匹配受理编号，当受理编号以CH开头，通过fw_ljz和s_sj_bgsh关联查询出受理编号
     */
    private String matchSlbh(String slbh,String qjgldm) {
        if (StringUtils.isBlank(slbh)) {
            return slbh;
        }
        if (slbh.startsWith("CH")) {
            String matchSlbh = fwLjzFeginService.fwljzLinkBgsh(slbh,qjgldm);
            if (StringUtils.isBlank(matchSlbh)) {
                matchSlbh = slbh;
            }
            return matchSlbh;
        }
        return slbh;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元号获取权籍房屋信息
     */
    @ResponseBody
    @GetMapping("/fwbdcdy")
    public BdcdyResponseDTO queryFwBdcdyByBdcdyh(String bdcdyh,String bdcdyfwlx,String qjgldm) {
        return bdcdyFeignService.queryBdcdy(bdcdyh, bdcdyfwlx,qjgldm);
    }


    /**
     * @param taskId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/12/10 14:19
     */
    @ResponseBody
    @GetMapping("/jdmc")
    public Object queryJdmc(String taskId) {
        if (StringUtils.isBlank(taskId)) {
            throw new AppException("缺失taskid");
        }
        return processTaskClient.getTaskById(taskId);
    }

    /**
     * 获取南通一张图合同页面地址
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param  htbh 合同编号
     * @return url  南通一张图合同页面地址
     */
    @ResponseBody
    @GetMapping("/getYzthtUrl")
    public String getYztCrHtUrl(@RequestParam String htbh) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(htbh)){
            throw new MissingArgumentException("未获取到合同信息。");
        }
        StringBuilder yztCrHtUrl = new StringBuilder();
        yztCrHtUrl.append(casUrl).append(URLEncoder.encode(yzthtUrl +htbh,"UTF-8"));
        return yztCrHtUrl.toString();
    }


    /**
     * 更新初始化开关数据中的 证书类型与是否发证
     * @param gzlslid 工作流实例ID
     * @param bdcSlCshFwkgDataDTOList  不动产初始化开关数据集合
     */
    @ResponseBody
    @PutMapping("/updateCshFwkg/{gzlslid}")
    public void modidyCshFwkgData(@PathVariable(name = "gzlslid") String gzlslid,
                                  @RequestBody List<BdcSlCshFwkgDataDTO> bdcSlCshFwkgDataDTOList) {
        if(StringUtils.isBlank(gzlslid) ||CollectionUtils.isEmpty(bdcSlCshFwkgDataDTOList)){
            throw new MissingArgumentException("缺失参数工作流实例ID或发证信息。");
        }

        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);

        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            throw new NullPointerException("未获取到不动产项目信息。");
        }

        // 将获取到不动产单元集合数据转换成Map<bdcdyh, xmid>结构，便于后续查找项目id
        Map<String,String> bdcXmMap = bdcXmDTOList.stream().collect(Collectors.toMap(BdcXmDTO::getBdcdyh, BdcXmDTO::getXmid));

        for(BdcSlCshFwkgDataDTO fwkgDataDTO: bdcSlCshFwkgDataDTOList){
            String bdcdyh = fwkgDataDTO.getBdcdyh();
            if(bdcXmMap.containsKey(bdcdyh)){
                // 获取数据库中的初始化服务开关数据，并做更新
                BdcCshFwkgSlDO bdcCshFwkgSlDO = this.bdcXmFeignService.queryFwkgsl(bdcXmMap.get(bdcdyh));
                if(null != bdcCshFwkgSlDO){
                    bdcCshFwkgSlDO.setSfsczs(fwkgDataDTO.getSfsczs());
                    bdcCshFwkgSlDO.setZszl(fwkgDataDTO.getZszl());
                    this.bdcXmFeignService.updateCshFwkgSl(bdcCshFwkgSlDO);
                }
            }
        }
    }

    /**
     * 获取配置的用于匹配落宗状态的bdcqzh标识
     * @return
     */
    @ResponseBody
    @GetMapping("/pplzztBdcqzhBs")
    public Object pplzztBdcqzhBs() {
        return bdcGzyzFeignService.pplzztBdcqzhBs();
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 对流程中的单元号进行逻辑幢分组
      */
    private Map<String, BdcXmDTO> groupLjzMapByGzlslid(String gzlslid){
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        //根据不动产单元号前24位判断是否为同一逻辑幢
        Map<String, BdcXmDTO> ljzMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                if (StringUtils.isNotBlank(bdcXmDTO.getBdcdyh()) && StringUtils.length(bdcXmDTO.getBdcdyh()) == CommonConstantUtils.BDCDYH_LENGTH) {
                    if (!ljzMap.containsKey(StringUtils.substring(bdcXmDTO.getBdcdyh(), 0, 24))) {
                        ljzMap.put(StringUtils.substring(bdcXmDTO.getBdcdyh(), 0, 24), bdcXmDTO);
                    }
                }
            }
        }
        return ljzMap;

    }

    /**
     * @return 返回身份证非18位验证工作流定义id
     * @description 查询身份证检查配置
     */
    @ResponseBody
    @GetMapping("/querySfzyzGzldyid")
    public List<String>  querySfzyzGzldyid( ) {
        return sfzyzdyidList;
    }

    /**
     * @return 海域等级
     * @description 根据不动产单元号查询海域等级
     */
    @ResponseBody
    @GetMapping("/queryDb")
    public Object queryDbByBdcdyh(String bdcdyh) {
        BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZhjbxx(bdcdyh);
        if(Objects.nonNull(bdcBdcdjbZhjbxxDO)){
            return bdcBdcdjbZhjbxxDO.getDb();
        }
        return null;
    }

    /**
     * @return 用海方式
     * @description 根据不动产单元号查询用海方式
     */
    @ResponseBody
    @GetMapping("/queryYhfs")
    public Object  queryYhfsByBdcdyh(String bdcdyh) {
        List<BdcBdcdjbZhjbxxYhzkDO> bdcBdcdjbZhjbxxYhzkDOS = bdcDjbxxFeignService.listBdcBdcdjbZhjbxxYhzk(bdcdyh);
        if(CollectionUtils.isNotEmpty(bdcBdcdjbZhjbxxYhzkDOS)){
            return bdcBdcdjbZhjbxxYhzkDOS.get(0).getYhfs();
        }
        return null;
    }

    /**
     * @return 用海方式
     * @description 根据不动产单元号查询用海方式
     */
    @ResponseBody
    @PostMapping("/updateYhfs")
    public Object  queryYhfsByBdcdyh(@RequestParam("bdcdyh")  String bdcdyh,@RequestParam("yhfs")  String yhfs) {
        if(StringUtils.isBlank(bdcdyh) || StringUtils.isBlank(yhfs)){
            throw new MissingArgumentException("缺失参数不动产单元号或用海方式。");
        }
        List<BdcBdcdjbZhjbxxYhzkDO> bdcBdcdjbZhjbxxYhzkDOS = bdcDjbxxFeignService.listBdcBdcdjbZhjbxxYhzk(bdcdyh);
        if(CollectionUtils.isNotEmpty(bdcBdcdjbZhjbxxYhzkDOS)){
            BdcBdcdjbZhjbxxYhzkDO bdcBdcdjbZhjbxxYhzkDO = bdcBdcdjbZhjbxxYhzkDOS.get(0);
            bdcBdcdjbZhjbxxYhzkDO.setYhfs(Integer.parseInt(yhfs));
            return bdcDjbxxFeignService.updateBdcBdcdjbZhjbxxYhzk(bdcBdcdjbZhjbxxYhzkDO);
        }
        return null;
    }

    /**
     * @return 用海方式
     * @description 根据不动产单元号查询用海方式
     */
    @ResponseBody
    @PostMapping("/updateDb")
    public Object  updateDb(@RequestParam("bdcdyh") String bdcdyh,@RequestParam("hydb") String hydb) {
        if(StringUtils.isBlank(bdcdyh) || StringUtils.isBlank(hydb)){
            throw new MissingArgumentException("缺失参数不动产单元号或海域等级。");
        }
        BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZhjbxx(bdcdyh);
        if(Objects.nonNull(bdcBdcdjbZhjbxxDO)){
            bdcBdcdjbZhjbxxDO.setDb(Integer.parseInt(hydb));
            return bdcDjbxxFeignService.updateBdcBdcdjbZdjbxx(bdcBdcdjbZhjbxxDO);
        }
        return null;
    }

    /**
     * 获取商品房、存量房默认的合同编号配置值
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return map
     */
    @ResponseBody
    @GetMapping("/htbh/pz")
    public Object getSlymHtbhValue() {
        Map<String, String> map = new HashMap<>(2);
        map.put("spfHtbh", Optional.ofNullable(slymSpfHtbh).orElse(""));
        map.put("clfHtbh", Optional.ofNullable(slymClfHtbh).orElse(""));
        return map;
    }

    /**
     * @param bdcQzxxDTO 签字信息DTO
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 记录受理人员操作权利人义务人签字
     */
    @PostMapping("/qlrywrQz")
    @LogNote(value = "记录受理人员操作权利人义务人签字", action = "OTHER", custom = LogConstans.LOG_TYPE_QLRYWRQZ)
    public void getSlymQlrywrQz(BdcQzxxDTO bdcQzxxDTO) {
        if (bdcQzxxDTO != null){
            LOGGER.info("记录受理人员操作权利人义务人签字:{}",bdcQzxxDTO);
        }
    }

    /**
     * @param xm 姓名
     * @param sfzhm 身份证号码
     * @param gzsbh 公证书编号
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 从第三方获取获取公证处材料保存到附件材料
     * @date : 2022/12/15
     * @return object
     * @exception IOException
     */
    @ResponseBody
    @PostMapping("/gzccl")
    public Object downloadFile(@RequestParam("xm") String xm, @RequestParam("sfzhm") String sfzhm, @RequestParam("gzsbh") String gzsbh, @RequestParam("gzlslid") String gzlslid) throws IOException {
        if (StringUtils.isAnyBlank(xm, sfzhm, gzsbh, gzlslid)) {
            throw new MissingArgumentException("缺失获取公证处材料参数");
        }

        String licenseXml = Constants.GZCL_LICENSE_XML;
        String dataXml = MessageFormat.format(Constants.GZCL_DATA_XML, xm, sfzhm, gzsbh);
        String param = MessageFormat.format(Constants.GZCL_PARAM_XML, licenseXml, dataXml);
        LOGGER.info("获取公证处材料请求webservice接口参数:{}",param);

        // 请求公证处材料webservice接口
        String soapXml = WebServiceUtils.HttpURLConnection(gzclRequestUrl, param);
        if (StringUtils.isNotBlank(soapXml)) {
            soapXml = soapXml.replace("&lt;", "<").replace("&gt;", ">")
                    .replace("&#13;", "\n");
            LOGGER.info("获取公证处材料请求webservice接口返回的soapXml:{}",soapXml);

            if (soapXml.contains("<file>") && soapXml.contains("</file>")) {
                String file = soapXml.split("<file>")[1].split("</file>")[0];
                String pdfBase64Str = "data:application/pdf;base64," + Base64Utils.encodeByteToBase64Str(Base64Utils.decodeBase64StrToByte(file));
                return bdcUploadFileUtils.uploadBase64File(pdfBase64Str, gzlslid, gzclWjjmc, gzclWjmc,".pdf");
            }
        }
        return null;
    }
}
