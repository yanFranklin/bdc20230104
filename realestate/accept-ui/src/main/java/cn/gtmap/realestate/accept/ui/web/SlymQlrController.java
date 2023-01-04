package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.LogMsgDTO;
import cn.gtmap.realestate.accept.ui.config.QyxxInterfaceConfig;
import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCsjpzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQlrAndDlrDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQlrGroupDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcYwrGroupDTO;
import cn.gtmap.realestate.common.core.dto.accept.SfmLiscenseInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.identitycheck.*;
import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckParamDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.qyxx.request.QyxxCxywcsRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.qyxx.request.QyxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shzztyxx.request.ShzztyxxCxywcsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shzztyxx.request.ShzztyxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrIdsDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlrXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcQlrInterfaceQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcQueryFrmcQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcYzQyxxInterfaceQO;
import cn.gtmap.realestate.common.core.qo.accept.CsjZzxxQO;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NaturalResourcesFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ProvincialDataSharingFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/17
 * @description 受理页面申请人相关控制层
 */
@Controller
@RequestMapping("/slym/qlr")
@Validated
public class SlymQlrController extends BaseController {

    private static final String MSG_HXFAIL = "回写信息失败";
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    private BdcRyzdFeignService bdcRyzdFeignService;
    @Autowired
    private BdcXtJgFeignService bdcXtJgFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlQlrFeignService bdcSlQlrFeignService;
    @Autowired
    BdcInitFeignService bdcInitFeignService;
    @Autowired
    NaturalResourcesFeignService naturalResourcesFeignService;
    @Autowired
    ProvincialDataSharingFeignService provincialDataSharingFeignService;
    @Autowired
    BdcLzrFeignService bdcLzrFeignService;
    @Autowired
    QyxxInterfaceConfig qyxxInterfaceConfig;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    BdcSlLzrFeignService bdcSlLzrFeignService;
    @Autowired
    BdcDlrFeignService bdcDlrFeignService;
    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    BdcSlCsjPzFeignService bdcSlCsjPzFeignService;
    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    @Autowired
    LogMessageClient logMessageClient;

    @Value("${default.cdghDjxl:}")
    private String cdghDjxl;

    /**
     * 特殊处理的要设置为不是持证人的权利人
     */
    @Value("#{'${qlr.bcz:}'.split(',')}")
    private List<String> bcz;

    /**
     * 单个权利人时需要默认为共同共有的 登记小类
     */
    @Value("${default.gtgyDjxl:}")
    private String gtgyDjxl;


    @Value("${html.version:}")
    private String version;

    /**
     * 同步生成土地使用权人配置的工作流实例ID,多个采用“,”分隔
     */
    @Value("${tbqlrtofdcq.gzldyid:}")
    private String tdsyqrGzldyids;

    /**
     * 自动保存机构下的领证人开关
     */
    @Value("${tbjglzr:false}")
    private boolean tbjglzr;

    @Value("${sftbdsqlr: false}")
    private boolean sftbdsqlr;

    /**
     * 预抵押登记小类
     */
    @Value("#{'${accept-ui.ydydjxl:}'.split(',')}")
    private List<String> ydydjxl;


    @Value("${slym.syncQlrToLzrFlag:false}")
    private boolean syncQlrToLzrFlag;

    /**
     * @param qlrid 权利人ID
     * @return 权利人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据权利人ID 获取权利人（用于权利人详细）
     */
    @ResponseBody
    @GetMapping("")
    public Object queryQlr(String qlrid) {
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        if (StringUtils.isNotBlank(qlrid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            setSxh(bdcQlrDOList);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                bdcQlrDO = bdcQlrDOList.get(0);
            }
        }
        return bdcQlrDO;
    }

    /**
     * @param qlrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人id查询权利人信息
     * @date : 2022/3/21 15:02
     */
    @ResponseBody
    @GetMapping("/qlrdlr")
    public Object queryQlrAndDlr(String qlrid, String dlrlb) {
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        BdcDlrDO bdcDlrDO = new BdcDlrDO();
        List<BdcDlrDO> bdcDlrDOList = Lists.newArrayList();
        BdcQlrAndDlrDTO bdcQlrAndDlrDTO = new BdcQlrAndDlrDTO();
        if (StringUtils.isNotBlank(qlrid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            setSxh(bdcQlrDOList);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                bdcQlrDO = bdcQlrDOList.get(0);
            }
            bdcQlrAndDlrDTO.setBdcQlrDO(bdcQlrDO);
            //查询代理人信息
            BdcDlrQO bdcDlrQO = new BdcDlrQO();
            bdcDlrQO.setQlrid(qlrid);
            if (StringUtils.isNotBlank(dlrlb)) {
                bdcDlrQO.setDlrlb(dlrlb);
            }
            bdcDlrDOList = bdcDlrFeignService.listBdcDlr(bdcDlrQO);
            if (CollectionUtils.isEmpty(bdcDlrDOList)) {
                bdcDlrDOList = new ArrayList<>(1);
                bdcDlrDOList.add(bdcDlrDO);
            }
            bdcQlrAndDlrDTO.setBdcDlrDOList(bdcDlrDOList);
        }
        return bdcQlrAndDlrDTO;
    }

    /**
     * @param processInsId 工作流实例ID
     * @return 权利人集合
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID获取权利人集合（用于简单流程、批量流程）
     */
    @ResponseBody
    @GetMapping("/list")
    public Object listQlr(String processInsId, String xmid, String qlrlb) throws Exception {
        if (StringUtils.isBlank(xmid)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                xmid = bdcXmDTOList.get(0).getXmid();
            }
        }
        if (StringUtils.isNotBlank(xmid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            if (StringUtils.isNotBlank(qlrlb)) {
                bdcQlrQO.setQlrlb(qlrlb);
            }
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
                if(!StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ,version)) {
                    bdcQlrDOList = changeGyfs(bdcQlrDOList, xmid, Constants.LCLX_PLLC);
                }
                setSxh(bdcQlrDOList);
                return bdcQlrDOList;
            }else{
                // 当查询不到的时候，用gzlslid查xmid
                List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
                if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                    xmid = bdcXmDTOList.get(0).getXmid();
                    bdcQlrQO.setXmid(xmid);
                    bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    if(!StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ,version)) {
                        bdcQlrDOList = changeGyfs(bdcQlrDOList, xmid, Constants.LCLX_PLLC);
                    }
                    setSxh(bdcQlrDOList);
                    return bdcQlrDOList;
                }
            }

        }
        return null;
    }

    /**
     * @param xmid  项目ID
     * @param qlrlb 权利人类别
     * @return 权利人类别
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID、权利人类别查询权利人集合（用于权利人详细页面）
     */
    @ResponseBody
    @GetMapping("/list/xm")
    public Object listBdcQlr(String xmid, String qlrlb, String lclx) throws Exception{
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(qlrlb);
            bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (StringUtils.isNotBlank(lclx) &&!StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ,version)) {
                bdcQlrDOList = changeGyfs(bdcQlrDOList, xmid, lclx);
            }
            setSxh(bdcQlrDOList);
        }
        return bdcQlrDOList;
    }

    /**
     * @param xmid  项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">zhangxinyu</a>
     * @description 根据项目ID查询收件人信息（用于邮寄信息页面）
     */
    @ResponseBody
    @GetMapping("/list/yjxx/sjr")
    public Object listBdcYjxxSjr(String xmid) {
        //权利人
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb("1");
            bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            setSxh(bdcQlrDOList);
        }

        //领证人
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        bdcLzrQO.setXmid(xmid);
        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
        if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
            for (BdcLzrDO bdcLzrDO : bdcLzrDOList) {
                BdcQlrDO bdcQlrDO = new BdcQlrDO();
                bdcQlrDO.setQlrid(bdcLzrDO.getQlrid());
                bdcQlrDO.setXmid(bdcLzrDO.getXmid());
                bdcQlrDO.setQlrmc(bdcLzrDO.getLzrmc());
                bdcQlrDO.setZjzl(bdcLzrDO.getLzrzjzl());
                bdcQlrDO.setZjh(bdcLzrDO.getLzrzjh());
                bdcQlrDO.setDh(bdcLzrDO.getLzrdh());
                bdcQlrDOList.add(bdcQlrDO);
            }
        }
        List<BdcQlrDO> collect = bdcQlrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getZjh() + "" + o.getQlrmc()))), ArrayList::new));
        return collect;
    }

    /**
     * 新增、修改、删除权利人时，同步修改房地产权中的土地使用权人（使用“/"拼接）
     * @param json 不动产权利人集合JSON
     * @param processInsId 工作流实例ID
     */
    private void notifySyncTdsyqr(String json, String processInsId){
        if(StringUtils.isBlank(tdsyqrGzldyids)){
            return;
        }
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            return;
        }
        String gzldyid = bdcXmDTOList.get(0).getGzldyid();
        if(StringUtils.isNotBlank(gzldyid) &&tdsyqrGzldyids.indexOf(gzldyid)>-1){
            this.bdcSlQlrFeignService.syncTdsyqr(json, processInsId);
        }
    }

    /**
     * 处理权利人来源
     */
    private void dealQlrly(JSONObject jsonObject){
        try{
            if(null != jsonObject){
                BdcQlrDO bdcQlrDO = JSONObject.parseObject(JSONObject.toJSONString(jsonObject),BdcQlrDO.class);
                String qlrid = bdcQlrDO.getQlrid();
                if(StringUtils.isNotBlank(qlrid)){
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setQlrid(qlrid);
                    List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
                        BdcQlrDO bdcQlrDOOld = bdcQlrDOList.get(0);
                        // 权利人名称或权利人证件号不相等时  则更新权利人来源 为手动
                        if((jsonObject.containsKey("qlrmc") &&!StringUtils.equals(bdcQlrDOOld.getQlrmc(),bdcQlrDO.getQlrmc()))
                                || (jsonObject.containsKey("zjh") &&!StringUtils.equals(bdcQlrDOOld.getZjh(),bdcQlrDO.getZjh()))){
                            jsonObject.put("qlrly",CommonConstantUtils.QLRLY_SD);
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("处理权利人来源异常",e);
        }
    }



    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取是否同步土地使用权人
     */
    private boolean syncTdsyqr(String processInsId){
        if(StringUtils.isBlank(tdsyqrGzldyids)){
            return false;
        }
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            return false;
        }
        String gzldyid = bdcXmDTOList.get(0).getGzldyid();
        return (tdsyqrGzldyids.indexOf(gzldyid)>-1);
    }

    /**
     * 同步一窗受理申请人信息
     * @param processInsId 工作流实例ID
     * @param type 操作类型
     */
    private void syncYcslSqrxx(List<JSONObject> objectList, String processInsId, String type){
        if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(systemVersion) &&StringUtils.isNotBlank(processInsId)){
            List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
            if(CollectionUtils.isNotEmpty(bdcXmDTOList) && isSwtbLc(bdcXmDTOList.get(0).getGzldyid())){
                if(CollectionUtils.isNotEmpty(objectList)){
                    List<BdcQlrDO> bdcQlrDOList = new ArrayList<>(objectList.size());
                    for (JSONObject object : objectList) {
                        bdcQlrDOList.add(JSONObject.toJavaObject(object, BdcQlrDO.class));
                    }
                    this.bdcSlSqrFeignService.syncSqrxx(bdcQlrDOList, processInsId, type);
                }
            }
        }
    }

    /**
     * @param json 前台传输权利人集合Json
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 修改权利人（用于简单流程）
     */
    @ResponseBody
    @PatchMapping(value = "/list/jdlc")
    public Integer updateBdcQlr(@RequestBody String json, String processInsId,String xmid,String type) throws Exception{
        BdcYwxxDTO bdcYwxxDTOBefore =new BdcYwxxDTO();
        //信息补录需要记录修改信息
        if(StringUtils.equals(CommonConstantUtils.XXBL,type) &&StringUtils.isNotBlank(xmid)) {
            // 查询出修改前的业务信息
            bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(xmid);
        }
        //更新权利人
        Integer count = 0;
        for (Object obj : JSON.parseArray(json)) {
            dealQlrly(JSONObject.parseObject(JSONObject.toJSONString(obj)));
            count += bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(obj), BdcQlrDO.class.getName());
        }
        if (StringUtils.isNotBlank(processInsId)) {
            //更新权利人冗余字段
            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
            bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
            //回写信息到平台
            try {
                bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
            } catch (Exception e) {
                LOGGER.error(MSG_HXFAIL, e);
            }
        }
        //信息补录需要记录修改信息
        if(StringUtils.equals(CommonConstantUtils.XXBL,type) &&StringUtils.isNotBlank(xmid)) {
            // 查询过修改后的结果
            BdcYwxxDTO bdcYwxxDTOAfter = bdcInitFeignService.queryYwxx(xmid);
            Map<String, String> data = LogCompareUtils.initDataString(xmid, bdcYwxxDTOBefore, bdcYwxxDTOAfter);
            LogMsgDTO logMsgDTO = new LogMsgDTO();
            logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
            logMsgDTO.setTags(data);
            logMsgDTO.setEvent(CommonConstantUtils.XXBL);
            logMessageClient.save(logMsgDTO);
        }
        return count;
    }

    /**
     * @param json 前台传输权利人集合json
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 批量修改权利人（用于组合页面）
     */
    @ResponseBody
    @PatchMapping(value = "/list/zhlc")
    public Integer updateZhBdcQlr(@RequestBody String json, String processInsId) {
        Integer count = 0;
        JSONArray jsonArray = JSONObject.parseArray(json);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(obj, Constants.FUN_UPDATE);
                if (CollectionUtils.isNotEmpty(jsonObjectList)) {
                    for (JSONObject object : jsonObjectList) {
                        // 更新前要处理 权利人来源 判断逻辑为：当前的zjh和mc 和 数据库中的一致 则不更新，不一致（认为改动过）则更新权利人来源 手动
                        dealQlrly(object);
                        count += bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(object), BdcQlrDO.class.getName());
                    }

                    // 同步一窗受理申请人信息
                    this.syncYcslSqrxx(jsonObjectList, processInsId, Constants.FUN_UPDATE);
                }
            }
            if (StringUtils.isNotBlank(processInsId)) {
                //更新权利人冗余字段
                bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
                //更新共有情况
                bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
                //回写信息到平台
                try {
                    bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
                } catch (Exception e) {
                    LOGGER.error(MSG_HXFAIL, e);
                }
                this.notifySyncTdsyqr(json, processInsId);
            }
        }
        return count;
    }

    /**
     * @param json 前台传输权利人集合json
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 批量修改权利人（用于批量页面）
     */
    @ResponseBody
    @PatchMapping(value = "/list/pllc")
    public Integer updatePlBdcQlr(@RequestBody String json, @RequestParam("processInsId") String processInsId, String xmid) throws Exception{
        int count = this.bdcSlQlrFeignService.updatePlBdcQlr(json, processInsId, xmid);
        if(StringUtils.isNotBlank(processInsId)){
            this.notifySyncTdsyqr(json, processInsId);
        }

        return count;

    }

    /**
     * @param json 前台传输权利人集合json
     * @return List<BdcQlrDO> 不动产权利人集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量修改权利人(用于批量组合页面)
     */
    @ResponseBody
    @PatchMapping(value = "/list/plzh")
    public List<BdcQlrDO> updatePlzhBdcQlr(@RequestBody String json, @RequestParam("processInsId") String processInsId, String xmid,String djxl) throws Exception{
        List<BdcQlrDO> bdcQlrDOList = this.bdcSlQlrFeignService.updatePlzhBdcQlr(json, processInsId, xmid, djxl);
        if(StringUtils.isNotBlank(processInsId)){
            this.notifySyncTdsyqr(JSON.toJSONString(bdcQlrDOList), processInsId);
        }
        return bdcQlrDOList;
    }

    /**
     * @param bdcQlrDO 权利人
     * @return 权利人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增权利人(权利人详细, 简单流程新增)
     */
    @ResponseBody
    @PostMapping("/jdlc")
    public Object insertBdcQlr(@RequestBody BdcQlrDO bdcQlrDO, @RequestParam("processInsId") String processInsId,String xmid,String type) throws Exception{
        //信息补录需要记录修改信息
        BdcYwxxDTO bdcYwxxDTOBefore =new BdcYwxxDTO();
        if(StringUtils.equals(CommonConstantUtils.XXBL,type) &&StringUtils.isNotBlank(xmid)) {
            // 查询出修改前的业务信息
            bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(xmid);
        }
        //新增权利人
        if (bdcQlrDO != null) {
            bdcQlrDO.setQlrid(UUIDGenerator.generate16());
            //新增默认手动输入
            if (bdcQlrDO.getQlrly() == null) {
                bdcQlrDO.setQlrly(CommonConstantUtils.QLRLY_SD);
            }
        }
        bdcQlrFeignService.insertBdcQlr(bdcQlrDO);
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        changeGyfs(bdcQlrDOList, xmid, "jdlc");
        if (StringUtils.isNotBlank(processInsId)) {
            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
            bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
            //回写信息到平台
            try {
                bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
            } catch (Exception e) {
                LOGGER.error(MSG_HXFAIL, e);
            }
        }
        //信息补录需要记录修改信息
        if(StringUtils.equals(CommonConstantUtils.XXBL,type) &&StringUtils.isNotBlank(xmid)) {
            // 查询过修改后的结果
            BdcYwxxDTO bdcYwxxDTOAfter = bdcInitFeignService.queryYwxx(xmid);
            Map<String, String> data = LogCompareUtils.initDataString(xmid, bdcYwxxDTOBefore, bdcYwxxDTOAfter);
            if (StringUtils.isNotBlank(RSAEncryptUtils.decrypt(data.get("change").toString()))) {
                LogMsgDTO logMsgDTO = new LogMsgDTO();
                logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
                logMsgDTO.setTags(data);
                logMsgDTO.setEvent(CommonConstantUtils.XXBL);
                logMessageClient.save(logMsgDTO);
            }
        }
        return bdcQlrDO;
    }

    /**
     * @param json 权利人
     * @return 新增数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增权利人(权利人详细, 组合流程新增)
     */
    @ResponseBody
    @PostMapping(value = "/zhlc")
    public BdcQlrDO insertZhBdcQlr(@RequestBody String json, @RequestParam("processInsId") String processInsId) {
        BdcQlrDO returnQlr = null;
        JSONObject obj = JSONObject.parseObject(json);
        List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(obj, Constants.FUN_INSERT);
        if (CollectionUtils.isNotEmpty(jsonObjectList)) {
            for (JSONObject object : jsonObjectList) {
                BdcQlrDO bdcQlrDO = JSONObject.toJavaObject(object, BdcQlrDO.class);
                if(bdcQlrDO != null &&bdcQlrDO.getQlrly() ==null){
                    bdcQlrDO.setQlrly(CommonConstantUtils.QLRLY_SD);
                }
                BdcQlrDO bdcqlr = bdcQlrFeignService.insertBdcQlr(bdcQlrDO);
                if (obj.get("qlrlb") != null && StringUtils.equals(obj.get("qlrlb").toString(), bdcqlr.getQlrlb())) {
                    returnQlr = bdcqlr;
                }
            }
            if (StringUtils.isNotBlank(processInsId)) {
                bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
                bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
                //回写信息到平台
                try {
                    bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
                } catch (Exception e) {
                    LOGGER.error(MSG_HXFAIL, e);
                }
                this.notifySyncTdsyqr(JSON.toJSONString(jsonObjectList), processInsId);
            }

            // 同步一窗受理申请人信息
            this.syncYcslSqrxx(jsonObjectList, processInsId, Constants.FUN_INSERT);
        }

        return returnQlr;
    }

    /**
     * @param bdcQlrDO 权利人
     * @return 新增数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增权利人(权利人详细, 批量流程新增)
     */
    @ResponseBody
    @PostMapping("/pllc")
    public Object insertPlBdcQlr(@RequestBody BdcQlrDO bdcQlrDO, @RequestParam("processInsId") String processInsId) {
        if (bdcQlrDO != null) {
            //新增前先删除相同的权利人,防止有重复权利人数据
            bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), processInsId, bdcQlrDO.getQlrlb(),"",new ArrayList<>());
            if(bdcQlrDO.getQlrly() ==null){
                bdcQlrDO.setQlrly(CommonConstantUtils.QLRLY_SD);
            }
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.insertBatchBdcQlr(bdcQlrDO, processInsId,"");
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                if (StringUtils.isNotBlank(processInsId)) {
                    //权利人保存后更新权利人相关信息
                    updateQlrxxPl(processInsId,"",bdcQlrDOList.get(0).getQlrlb());
                    // 同步更新土地使用权人
                    this.notifySyncTdsyqr(JSON.toJSONString(bdcQlrDOList), processInsId);
                }
                return bdcQlrDOList.get(0);
            }
        }
        return null;
    }

    /**
     * @param json
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 新增权利人(权利人详细, 批量组合流程新增)
     */
    @ResponseBody
    @PostMapping("/plzh")
    public Object insertPlzhBdcQlr(@RequestBody String json, @RequestParam("processInsId") String processInsId,@RequestParam("djxl") String djxl) {
        return bdcSlQlrFeignService.insertPlzhBdcQlr(json, processInsId, djxl,syncTdsyqr(processInsId));

    }


    /**
     * @param qlrid 权利人ID
     * @param xmid  项目ID
     * @param qlrlb 权利人类别
     * @param sxh   顺序号
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 删除权利人(用于简单流程)
     */
    @ResponseBody
    @DeleteMapping("/jdlc")
    public void deleteBdcQlr(@RequestParam("qlrid") String qlrid, @RequestParam("xmid") String xmid, @RequestParam("qlrlb") String qlrlb, @RequestParam("sxh") Integer sxh, @RequestParam("processInsId") String processInsId) {
        bdcQlrFeignService.deleteBdcQlr(qlrid);
        changeSxhForDel(xmid, qlrlb, sxh, "");
        if (StringUtils.isNotBlank(processInsId)) {
            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
            bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
            //回写信息到平台
            try {
                bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
            } catch (Exception e) {
                LOGGER.error(MSG_HXFAIL, e);
            }
            // 盐城 同步删除领证人
            if(StringUtils.isNotBlank(version) && StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_YC)){
                BdcLzrQO bdcLzrQO = new BdcLzrQO();
                bdcLzrQO.setXmid(xmid);
                List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
                if(CollectionUtils.isNotEmpty(bdcLzrDOList)){
                    for(BdcLzrDO bdcLzrDO : bdcLzrDOList){
                        if(StringUtils.equals(bdcLzrDO.getQlrid(), qlrid)){
                            bdcLzrFeignService.deleteBdcLzr(bdcLzrDO.getLzrid());
                        }
                    }
                }
            }
        }
    }

    /**
     * @param qlrid 权利人ID
     * @param xmid  项目ID
     * @param qlrlb 权利人类别
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 删除权利人(用于组合流程)
     */
    @ResponseBody
    @DeleteMapping(value = "/zhlc")
    public void deleteZhBdcQlr(@RequestParam("qlrid") String qlrid, @RequestParam("xmid") String xmid, @RequestParam("qlrlb") String qlrlb, @RequestParam("sxh") Integer sxh, @RequestParam("processInsId") String processInsId) {
        JSONObject obj = new JSONObject();
        obj.put("qlrid", qlrid);
        obj.put("xmid", xmid);
        obj.put("qlrlb", qlrlb);

        try {
            deleteJgLzr(qlrid);
        } catch (Exception e) {
            LOGGER.error("删除机构对应的领证人失败，请手动删除",e);
        }
        List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(obj, Constants.FUN_DELETE);
        if (CollectionUtils.isNotEmpty(jsonObjectList)) {
            // 同步一窗受理申请人信息
            this.syncYcslSqrxx(jsonObjectList, processInsId, Constants.FUN_DELETE);

            for (JSONObject object : jsonObjectList) {
                BdcQlrDO bdcQlrDO = JSONObject.toJavaObject(object, BdcQlrDO.class);
                bdcQlrFeignService.deleteBdcQlr(bdcQlrDO.getQlrid());
                changeSxhForDel(bdcQlrDO.getXmid(), bdcQlrDO.getQlrlb(), sxh, "");
                if(StringUtils.isNotBlank(version) && StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_YC)){
                    if(StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)){
                        BdcLzrQO bdcLzrQO = new BdcLzrQO();
                        bdcLzrQO.setXmid(bdcQlrDO.getXmid());
                        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
                        if(CollectionUtils.isNotEmpty(bdcLzrDOList)){
                            BdcLzrDO bdcLzrDO = bdcLzrDOList.get(0);
                            if(StringUtils.equals(bdcLzrDO.getQlrid(), bdcQlrDO.getQlrid())){
                                bdcLzrFeignService.deleteBdcLzr(bdcLzrDO.getLzrid());
                            }
                        }
                    }
                }
            }

            if (StringUtils.isNotBlank(processInsId)) {
                bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
                bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
                //回写信息到平台
                try {
                    bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
                } catch (Exception e) {
                    LOGGER.error(MSG_HXFAIL, e);
                }
                // 同步更新土地使用权人
                this.notifySyncTdsyqr(JSON.toJSONString(jsonObjectList), processInsId);
            }
        }
    }

    /**
     * @param qlrid        权利人ID
     * @param processInsId 工作流实例ID
     * @param sxh          顺序号
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 删除权利人(用于批量流程)
     */
    @ResponseBody
    @DeleteMapping("/pllc")
    public void deletePlBdcQlr(@RequestParam("qlrid") String qlrid, @RequestParam("processInsId") String processInsId, @RequestParam("sxh") Integer sxh) {
        if (StringUtils.isNotBlank(qlrid)) {
            try {
                deleteJgLzr(qlrid);
            } catch (Exception e) {
                LOGGER.error("删除机构对应的领证人失败，请手动删除",e);
            }

            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
                //更新第三权利人
                bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), processInsId, bdcQlrDO.getQlrlb(),"",new ArrayList<>());
                changeSxhForDel(bdcQlrDO.getXmid(), bdcQlrDO.getQlrlb(), sxh, processInsId);
                //权利人保存后更新权利人相关信息
                updateQlrxxPl(processInsId,"","");
                // 盐城 判断删除的权利人是否为领证人，如果是则批量删除
                if(StringUtils.isNotBlank(version) && StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_YC)){
                    deleteLzrPl(bdcQlrDO, processInsId, "");
                }
                // 同步更新土地使用权人
                this.notifySyncTdsyqr(JSON.toJSONString(bdcQlrDOList), processInsId);
            }
        }
    }

    private void deleteLzrPl(BdcQlrDO bdcDeleteQlrDO, String processInsId, String djxl) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setDjxl(djxl);
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.error("根据工作流实例id和登记小类未查询到项目信息，工作流实例id：{},登记小类：{}", processInsId,djxl);
            throw new AppException("根据工作流实例id和登记小类未查询到项目信息");
        }
        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.getBdcLzrDOByXm(bdcXmDOList);
        if(CollectionUtils.isNotEmpty(bdcLzrDOList)){
            BdcLzrDO bdcLzrDO = bdcLzrDOList.get(0);
            // 如果删除的权利人与领证人名称证件号相同，则批量删除领证人
            if(StringUtils.equals(bdcDeleteQlrDO.getQlrmc(),bdcLzrDO.getLzrmc()) && StringUtils.equals(bdcDeleteQlrDO.getZjh(), bdcLzrDO.getLzrzjh())){
                bdcLzrFeignService.batchDeleteBdcLzr(bdcLzrDOList);
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除权利人(用于批量组合流程)
     */
    @ResponseBody
    @DeleteMapping("/plzh")
    public void deletePlzhBdcQlr(@RequestParam("qlrid") String qlrid, @RequestParam("processInsId") String processInsId, @RequestParam("sxh") Integer sxh,@RequestParam("djxl") String djxl) {
        if (StringUtils.isNotBlank(qlrid)) {
            try {
                deleteJgLzr(qlrid);
            } catch (Exception e) {
                LOGGER.error("删除机构对应的领证人失败，请手动删除",e);
            }
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                BdcQlrDO bdcDeleteQlrDO = bdcQlrDOList.get(0);
                //批量组合需要同步删除其他流程对应的权利人或义务人
                JSONObject obj =JSONObject.parseObject(JSONObject.toJSONString(bdcDeleteQlrDO));
                List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(obj, Constants.FUN_DELETE);
                for (JSONObject object : jsonObjectList) {
                    BdcQlrDO bdcQlrDO = JSONObject.toJavaObject(object, BdcQlrDO.class);
                    djxl =getDjxlByXmid(bdcQlrDO.getXmid());
                    if(StringUtils.isNotBlank(djxl)) {
                        //如果人名、工作流实例id没值则抛异常
                        if(StringUtils.isBlank(bdcQlrDO.getQlrmc()) || StringUtils.isBlank(processInsId)){
                            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
                        }
                        List<String> xmidList =new ArrayList<>();
                        //如果证书序号不为空,获取相同证号序号的所有项目同步更新
                        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcQlrDO.getXmid());
                        if (bdcCshFwkgSlDO.getZsxh() != null &&CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcCshFwkgSlDO.getQllx())) {
                            xmidList = bdcXmFeignService.queryZsxmList(bdcQlrDO.getXmid());
                        }
                        //批量删除
                        bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), processInsId, bdcQlrDO.getQlrlb(), djxl,xmidList);
                        if(StringUtils.isNotBlank(processInsId)) {
                            //权利人保存后更新权利人相关信息
                            updateQlrxxPl(processInsId, djxl,bdcQlrDO.getQlrlb());
                            // 同步更新土地使用权人
                            this.notifySyncTdsyqr(JSON.toJSONString(bdcQlrDO), processInsId);
                        }
                        if(StringUtils.isNotBlank(version) && StringUtils.equals(version, "yancheng")){
                            if(StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)){
                                BdcLzrQO bdcLzrQO = new BdcLzrQO();
                                bdcLzrQO.setXmid(bdcQlrDO.getXmid());
                                List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
                                if(CollectionUtils.isNotEmpty(bdcLzrDOList)){
                                    BdcLzrDO bdcLzrDO = bdcLzrDOList.get(0);
                                    if(StringUtils.equals(bdcLzrDO.getLzrmc(), bdcQlrDO.getQlrmc()) && StringUtils.equals(bdcLzrDO.getLzrzjh(), bdcQlrDO.getZjh())){
                                        deleteLzrPl(bdcQlrDO, processInsId, djxl);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param qlrid        权利人ID
     * @param czlx         操作类型
     * @param processInsId 工作流实例ID
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 修改权利人顺序号（用于简单流程、组合流程、批量流程）
     */
    @ResponseBody
    @GetMapping(value = "/sxh")
    public Integer changeQlrSxh(String qlrid, String czlx, String lclx, String processInsId) throws Exception{
        Integer count = 0;
        List<BdcQlrDO> bdcQlrDOS = new ArrayList<>();
        BdcQlrDO bdcQlrDO = (BdcQlrDO) queryQlr(qlrid);
        if (bdcQlrDO != null) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcQlrDO.getXmid());
            bdcQlrQO.setQlrlb(bdcQlrDO.getQlrlb());
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList) && bdcQlrDOList.size() > 1) {
                for (int i = 0; i < bdcQlrDOList.size(); i++) {
                    BdcQlrDO bdcQlr = bdcQlrDOList.get(i);
                    if (null != bdcQlr && StringUtils.equals(bdcQlr.getQlrid(), qlrid)) {
                        BdcQlrDO changeBdcQlr = null;
                        if (StringUtils.equals(czlx, Constants.SXH_UP) && i != 0) {
                            changeBdcQlr = bdcQlrDOList.get(i - 1);
                        }
                        if (StringUtils.equals(czlx, Constants.SXH_DOWN) && i != (bdcQlrDOList.size() - 1)) {
                            changeBdcQlr = bdcQlrDOList.get(i + 1);
                        }
                        if (changeBdcQlr != null) {
                            if(null != bdcQlrDO.getSxh() && null != changeBdcQlr.getSxh()){
                                Integer sxh1 = bdcQlrDO.getSxh();
                                Integer sxh2 = changeBdcQlr.getSxh();
                                bdcQlrDO.setSxh(sxh2);
                                changeBdcQlr.setSxh(sxh1);
                                bdcQlrDOS.add(bdcQlrDO);
                                bdcQlrDOS.add(changeBdcQlr);
                            }
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
            if (StringUtils.equals(lclx, Constants.LCLX_JDLC)) {
                for (BdcQlrDO bdcQlr : bdcQlrDOS) {
                    count += bdcQlrFeignService.updateBdcQlr(bdcQlr);
                }
            }
            if (StringUtils.equals(lclx, Constants.LCLX_ZHLC)) {
                count = updateZhBdcQlr(JSONObject.toJSONString(bdcQlrDOS), "");
            }
            if (StringUtils.equals(lclx, Constants.LCLX_PLLC)) {
                count = this.bdcSlQlrFeignService.updatePlBdcQlr(JSONObject.toJSONString(bdcQlrDOS),
                        processInsId, bdcQlrDOS.get(0).getXmid());
            }
        }
        return count;
    }

    /**
     * @param xmid  项目id
     * @param qlrlb 权利人类别
     * @param sxh   顺序号
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 删除时修改顺序号
     */
    private void changeSxhForDel(String xmid, String qlrlb, Integer sxh, String processInsId) {
        if (sxh != null) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(qlrlb);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    if (bdcQlrDO.getSxh() > sxh) {
                        bdcQlrDO.setSxh(bdcQlrDO.getSxh() - 1);
                        if (StringUtils.isNotBlank(processInsId)) {
                            bdcQlrFeignService.updateBatchBdcQlr(bdcQlrDO, processInsId,"");
                        } else {
                            bdcQlrFeignService.updateBdcQlr(bdcQlrDO);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param bdcQlrDOList 权利人列表
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 设置权利人顺序号
     */
    private void setSxh(List<BdcQlrDO> bdcQlrDOList) {
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            int qlrsxh = 0;
            int ywrsxh = 0;
            for (int i = 0; i < bdcQlrDOList.size(); i++) {
                int sxh = 0;
                BdcQlrDO bdcQlrDO = bdcQlrDOList.get(i);
                if (StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                    qlrsxh++;
                    sxh = qlrsxh;
                }
                if (StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_YWR)) {
                    ywrsxh++;
                    sxh = ywrsxh;
                }
                if (bdcQlrDO.getSxh() == null || bdcQlrDO.getSxh() == 0) {
                    bdcQlrDO.setSxh(sxh);
                    bdcQlrFeignService.updateBdcQlr(bdcQlrDO);
                }
            }
        }
    }

    /**
     * @return bdc_xt_jg信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取所有的机构名称数据作为下拉框
     */
    @ResponseBody
    @GetMapping("/bdcxtjg")
    public Object queryBdcXtJg(Integer jglb) {
        return bdcXtJgFeignService.listBdcXtJg(jglb);
    }

    /**
     * @param jgmc 机构名称
     * @return bdc_xt_jg信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据机构名称获取对应的数据
     */
    @ResponseBody
    @GetMapping("/bdcxtjg/jgxx")
    public Object queryBdcXtJgxx(String jgmc,String jglb) {
        if (StringUtils.isNotBlank(jgmc)) {
            return bdcXtJgFeignService.queryJgByJgmc(jgmc,jglb);
        } else {
            return null;
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 权利人分组信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人证件号分组所有满足条件的权利人
     */
    @ResponseBody
    @PostMapping("/groupywr")
    public Object listAllQlr(String gzlslid,String djxl,@RequestBody(required = false) String json) {
        if (StringUtils.isNotBlank(gzlslid)) {
            JSONObject obj = JSONObject.parseObject(json);
            String xmidList ="";
            if (obj!=null){
                xmidList = (String)obj.get("xmidList");
            }
            return bdcQlrFeignService.groupQlrYwrByZjh(gzlslid,CommonConstantUtils.QLRLB_YWR,null,xmidList);
        } else {
            return null;
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 权利人分组信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据权利人证件号分组所有满足条件的权利人
     */
    @ResponseBody
    @PostMapping("/groupQlr")
    public Object listAllQlr(String gzlslid, String djxl, String qlrlb,@RequestBody(required = false) String json) {
        if (StringUtils.isNotBlank(gzlslid)) {
            JSONObject obj = JSONObject.parseObject(json);
            String xmidList ="";
            if (obj!=null){
                xmidList = (String)obj.get("xmidList");
            }
            return bdcQlrFeignService.groupQlrYwrByZjh(gzlslid, qlrlb, djxl,xmidList);
        } else {
            return null;
        }
    }

    /**
     * @param json 权利人id集合
     * @return 权利人分组信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人id删除分组的所有权利人
     */
    @ResponseBody
    @DeleteMapping("/deletegroupywr")
    public void deleteAllQlr(@RequestBody String json, String gzlslid, Integer sxh, String xmid) {
        JSONObject obj = JSONObject.parseObject(json);
        if (obj != null) {
            BdcYwrGroupDTO bdcYwrGroupDTO = JSONObject.parseObject(JSON.toJSONString(obj), BdcYwrGroupDTO.class);
            List<String> qlridList = JSONObject.parseArray(bdcYwrGroupDTO.getQlrids(), String.class);
            List<String> xmids = JSONObject.parseArray(bdcYwrGroupDTO.getXmids(), String.class);
            if (CollectionUtils.isNotEmpty(qlridList)) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setQlrid(qlridList.get(0));
                List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                //处理第三权利人
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    bdcQlrFeignService.listPlBdcQlr(JSON.parseObject(JSON.toJSONString(qlrDOList.get(0))),
                            Constants.FUN_DELETE);
                }
                //批量删除
                if (CollectionUtils.isNotEmpty(qlridList)) {
                    bdcQlrFeignService.deleteBatchQlr(qlridList);
                }
                changeSxhForDel(xmid, CommonConstantUtils.QLRLB_YWR, sxh, gzlslid);
                //更新冗余字段和共有情况
                List<BdcQlrXmDTO> bdcQlrXmDTOList = new ArrayList<>();
                BdcQlrXmDTO bdcQlrXmDTO = new BdcQlrXmDTO();
                bdcQlrXmDTO.setQlrlb(CommonConstantUtils.QLRLB_YWR_DM);
                bdcQlrXmDTO.setXmidList(xmids);
                bdcQlrXmDTOList.add(bdcQlrXmDTO);
                bdcRyzdFeignService.updateRyzdQlrXm(bdcQlrXmDTOList);
                //回写信息到平台
                try {
                    bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                } catch (Exception e) {
                    LOGGER.error(MSG_HXFAIL, e);
                }
            }
        }
    }

    /**
     * @param json 数据集合
     * @return 数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新权利人信息
     */
    @ResponseBody
    @PatchMapping("/updategroupywr")
    public List<BdcQlrDO> updateAllQlr(@RequestBody String json, String gzlslid, String xmid) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        JSONObject obj = JSONObject.parseObject(json);
        if (obj != null) {
            BdcYwrGroupDTO bdcYwrGroupDTO = JSONObject.parseObject(JSON.toJSONString(obj), BdcYwrGroupDTO.class);
            BdcQlrIdsDTO bdcQlrIdsDTO = new BdcQlrIdsDTO();
            List<String> qlrids = JSONObject.parseArray(bdcYwrGroupDTO.getQlrids(), String.class);
            List<String> xmids = JSONObject.parseArray(bdcYwrGroupDTO.getXmids(), String.class);
            //要先更新第三方,否则会找不到原始的权利人名称和证件号
            bdcQlrFeignService.listPlBdcQlr(JSON.parseObject(JSON.toJSONString(bdcYwrGroupDTO.getBdcQlrDO())),
                    Constants.FUN_UPDATE);
            bdcQlrIdsDTO.setBdcQlrDO(bdcYwrGroupDTO.getBdcQlrDO());
            bdcQlrIdsDTO.setQlridlist(qlrids);
            bdcQlrIdsDTO.setXmidlist(xmids);
            bdcQlrDOList = bdcQlrFeignService.updateBatchQlr(bdcQlrIdsDTO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(xmid)) {
                    //更新冗余字段
                    List<BdcQlrXmDTO> bdcQlrXmDTOList = new ArrayList<>();
                    BdcQlrXmDTO bdcQlrXmDTO = new BdcQlrXmDTO();
                    bdcQlrXmDTO.setQlrlb(CommonConstantUtils.QLRLB_YWR_DM);
                    bdcQlrXmDTO.setXmidList(xmids);
                    bdcQlrXmDTOList.add(bdcQlrXmDTO);
                    bdcRyzdFeignService.updateRyzdQlrXm(bdcQlrXmDTOList);
                    //回写信息到平台
                    try {
                        bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                    } catch (Exception e) {
                        LOGGER.error(MSG_HXFAIL, e);
                    }
                }
            }
        }
        return bdcQlrDOList;
    }

    /**
     * @param bdcQlrQO 权利人查询对象
     * @return 权利人信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取权利人信息
     */
    @ResponseBody
    @PostMapping("/listBdcQlr")
    public Object listBdcQlr(@RequestBody BdcQlrQO bdcQlrQO) {
        if (!CheckParameter.checkAnyParameter(bdcQlrQO)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        return  bdcQlrFeignService.listBdcQlr(bdcQlrQO);
    }


    /**
     * @return 企业信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取企业信息
     */
    @ResponseBody
    @GetMapping("/getQyxx")
    public Object getQyxx(BdcQlrInterfaceQO bdcQlrInterfaceQO,String beanName) {
        if (StringUtils.isBlank(bdcQlrInterfaceQO.getQlrmc()) ||StringUtils.isBlank(beanName)) {
            throw new MissingArgumentException("缺少参数：权利人名称,第三方系统接口名称");
        }
        if(StringUtils.isNotBlank(bdcQlrInterfaceQO.getXmid())){
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setXmid(bdcQlrInterfaceQO.getXmid());
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                bdcQlrInterfaceQO.setSlbh(bdcXmDOList.get(0).getSlbh());
            }
        }
        return exchangeInterfaceFeignService.requestInterface(beanName, bdcQlrInterfaceQO);

    }


    /**
     * 组织企业信息
     *
     * @param obj exchange返回值
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 17:18 2020/9/14
     */
    public Object hfQyjbxxcx(String beanName, Object obj) {
        JSONArray jsonArray = JSONArray.parseArray(obj.toString());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        LOGGER.info("json为{}", jsonObject);
        String qyzt = jsonObject.getString("regstateCn");
        String frmc = jsonObject.getString("name");
        String qlrmc = jsonObject.getString("entname");
        String txdz = jsonObject.getString("dom");
        String zjh = jsonObject.getString("uniscid");

        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        bdcQlrDO.setFrmc(frmc);
        bdcQlrDO.setQlrmc(qlrmc);
        bdcQlrDO.setTxdz(txdz);
        bdcQlrDO.setZjh(zjh);
        Map dataMap = new HashMap();
        HashMap qyxx = new HashMap();
        List<HashMap> qyxxList = new ArrayList<>();
        qyxx.put("bdcQlrDO", bdcQlrDO);
        qyxx.put("qyzt", qyzt);
        qyxxList.add(qyxx);
        dataMap.put("qyxx", qyxxList);
        LOGGER.info("企业信息为{}", dataMap);

        return dataMap;


    }

    /**
     * @return 死亡信息
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjaiqiang</a>
     * @description 死亡标志信息查询
     */
    @ResponseBody
    @GetMapping("/getQlrswxx")
    public Object getQlrswxx(BdcQlrInterfaceQO bdcQlrInterfaceQO) {
        if (StringUtils.isBlank(bdcQlrInterfaceQO.getQlrmc()) || StringUtils.isBlank(bdcQlrInterfaceQO.getZjh())) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        String beanName = "hf_acceptSiwangxx";
        //如果死亡信息查询结果为空,调用殡葬服务接口
        Object swxxResult = exchangeInterfaceFeignService.requestInterface(beanName, bdcQlrInterfaceQO);
        JSONArray swxxArry = JSONObject.parseArray(JSONObject.toJSONString(swxxResult));
        if(CollectionUtils.isEmpty(swxxArry)) {
            return exchangeInterfaceFeignService.requestInterface("hf_acceptBinzangxx",bdcQlrInterfaceQO);
        }
        return swxxResult;
    }

    /**
     * @param cardNo 证件号
     * @return 第一顺位继承人
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjaiqiang</a>
     * @description 第一顺位继承人
     */
    @ResponseBody
    @GetMapping("/getQlrdyswjcr")
    public Object getQlrswxx(@RequestParam(name = "cardNo") String cardNo,@RequestParam(name = "slbh") String slbh,@RequestParam(name = "xmid") String xmid) {
        if (StringUtils.isBlank(cardNo)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        String beanName = "hf_acceptDyswjcrcx";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("CARD_NO", cardNo);
        paramMap.put("slbh", slbh);
        paramMap.put("xmid", xmid);
        return exchangeInterfaceFeignService.requestInterface(beanName, paramMap);

    }

    /**
     * @param gzsbh 公证书编号
     * @return 公证书
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjaiqiang</a>
     * @description 获取公证书
     */
    @ResponseBody
    @GetMapping("/getGzsxx")
    public Object getGzs(@RequestParam(name = "gzsbh") String gzsbh) {
        if (StringUtils.isBlank(gzsbh)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        String beanName = "hf_acceptGzsxxcx";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("GZSBH", gzsbh);
        return exchangeInterfaceFeignService.requestInterface(beanName, paramMap);

    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量流程更新权利人相关信息（包括权利人冗余字段与共有情况，权利人回写）
     */
    private void updateQlrxxPl(String gzlslid,String djxl,String qlrlb) {
        //组织数据批量更新冗余字段和共有情况
        List<BdcQlrXmDTO> bdcQlrXmDTOList = new ArrayList<>();
        BdcQlrXmDTO bdcQlrXmDTO = new BdcQlrXmDTO();
        if(StringUtils.isNotBlank(qlrlb)){
            bdcQlrXmDTO.setQlrlb(Integer.parseInt(qlrlb));
        }else {
            bdcQlrXmDTO.setQlrlb(CommonConstantUtils.QLRLB_QLR_DM);
        }
        List<String> xmids = new ArrayList<>();
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                //登记小类为空或者两者一致
                if(StringUtils.isBlank(djxl) ||  StringUtils.equals(bdcXmDTO.getDjxl(),djxl)) {
                    xmids.add(bdcXmDTO.getXmid());
                }
            }
            bdcQlrXmDTO.setXmidList(xmids);
            bdcQlrXmDTOList.add(bdcQlrXmDTO);
            bdcRyzdFeignService.updateRyzdQlrXm(bdcQlrXmDTOList);
        }
        //回写信息到平台
        try {
            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
        } catch (Exception e) {
            LOGGER.error(MSG_HXFAIL, e);
        }

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 加载页面时检查所有的共有方式是否正确，不正确默认修改为共同共有
     */
    private List<BdcQlrDO> changeGyfs(List<BdcQlrDO> bdcQlrDOList, String xmid, String lclx) throws Exception{
        if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ,version)) {
            return bdcQlrDOList;
        }
        List<BdcQlrDO> newBdcQlrList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                //各流程对应的更新方法不同
                List<BdcQlrDO> bdcQlrList = new ArrayList<>();
                List<BdcQlrDO> bdcYwrList = new ArrayList<>();
                //1取出所有的权利人
                for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    if (StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                        bdcQlrList.add(bdcQlrDO);
                    } else {
                        bdcYwrList.add(bdcQlrDO);
                    }
                }
                //组合流程不能用批量更新的方法，分开更新
                if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                    //组合流程需要同步更新另一个项目的义务人
                    /**
                     * @param bdcQlrDOList
                     * @param xmid
                     * @param lclx
                     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
                     * @description   组合流程第一权利人更新同步第二手的义务人，但是第二手的权利人不需要同步第一手，根据xmid排序
                     * @date : 2019/12/19 9:23
                     */
                    if (StringUtils.equals(lclx, "zhlc")) {
                        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcXmDOList.get(0).getGzlslid());
                        String xmid2 = "";
                        for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                            if (!StringUtils.equals(bdcXmDTO.getXmid(), xmid)) {
                                xmid2 = bdcXmDTO.getXmid();
                            }
                        }
                        if (StringUtils.isNotBlank(xmid2) && xmid2.compareTo(xmid) > 0) {
                            BdcQlrQO bdcQlrQO = new BdcQlrQO();
                            bdcQlrQO.setXmid(xmid2);
                            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                            List<BdcQlrDO> bdcQlrXm2List = new ArrayList<>();
                            for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                                bdcQlrQO.setQlrmc(bdcQlrDO.getQlrmc());
                                bdcQlrQO.setZjh(bdcQlrDO.getZjh());
                                List<BdcQlrDO> bdcQlrDOList1 = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                                if (CollectionUtils.isNotEmpty(bdcQlrDOList1)) {
                                    bdcQlrXm2List.addAll(bdcQlrDOList1);
                                }
                            }
                            generateUpdateData(bdcQlrXm2List, bdcXmDOList.get(0).getGzlslid(), xmid2, lclx);
                        }
                    }
                    bdcQlrList = generateUpdateData(bdcQlrList, bdcXmDOList.get(0).getGzlslid(), xmid, lclx);
                }
                newBdcQlrList.addAll(bdcQlrList);
                newBdcQlrList.addAll(bdcYwrList);
            }
        }
        return newBdcQlrList;
    }

    /**
     * @param bdcQlrList,gzlslid,xmid,lclx
     * @return bdcQlrDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新操作
     */
    private List<BdcQlrDO> generateUpdateData(List<BdcQlrDO> bdcQlrList, String gzlslid, String xmid, String lclx) throws Exception{
        if(CollectionUtils.isNotEmpty(bdcQlrList)){
            return this.bdcSlQlrFeignService.updateBdcQlrGyfs(bdcQlrList, gzlslid, xmid, lclx);
        }else{
            return bdcQlrList;
        }
    }

    /**
     * @param json 权利人
     * @return 新增数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 新增第三权利人
     */
    @ResponseBody
    @PostMapping(value = "/insertDsqlr")
    public BdcDsQlrDO insertDsQlr(@RequestBody String json, @RequestParam("processInsId") String processInsId) {
        JSONObject obj = JSONObject.parseObject(json);
        BdcDsQlrDO bdcDsQlrDO = JSONObject.toJavaObject(obj, BdcDsQlrDO.class);
        return bdcQlrFeignService.insertBdcDsQlr(bdcDsQlrDO);
    }

    /**
     * @param xmid 项目ID
     * @return 权利人
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据项目ID查询权利人集合（用于权利人详细页面）
     */
    @ResponseBody
    @GetMapping("/list/dsQlr")
    public List<BdcDsQlrDO> listBdcDsQlr(String xmid) {
        List<BdcDsQlrDO> bdcDsQlrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcDsQlrQO bdcDsQlrQO = new BdcDsQlrQO();
            bdcDsQlrQO.setXmid(xmid);
            bdcDsQlrDOList = bdcQlrFeignService.listBdcDsQlr(bdcDsQlrQO);
            //抵押权同步义务人到第三权利人
            sftbdsqlr = false;
            if (sftbdsqlr && CollectionUtils.isEmpty(bdcDsQlrDOList)) {
                BdcXmQO bdcXmQO = new BdcXmQO(xmid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                    if ((CollectionUtils.isNotEmpty(ydydjxl) && ydydjxl.contains(bdcXmDO.getDjxl())) || CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                        List<BdcQlrGroupDTO> bdcYwrList = bdcQlrFeignService.groupQlrYwrByZjh(bdcXmDOList.get(0).getGzlslid(), CommonConstantUtils.QLRLB_YWR, bdcXmDOList.get(0).getDjxl(),"");
                        if (CollectionUtils.isNotEmpty(bdcYwrList)) {
                            bdcDsQlrDOList = new ArrayList<>(1);
                            for (BdcQlrGroupDTO bdcQlrGroupDTO : bdcYwrList) {
                                BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
                                BeanUtils.copyProperties(bdcQlrGroupDTO.getBdcQlrDO(), bdcDsQlrDO);
                                //此处第三权利人的权利人id就是抵押义务人的权利人id，方便页面修改的时候找到数据
                                bdcDsQlrDO.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_ZWR);
                                bdcDsQlrDO.setXmid(xmid);
                                BdcDsQlrDO bdcDsQlr = bdcQlrFeignService.insertBdcDsQlr(bdcDsQlrDO);
                                bdcDsQlrDOList.add(bdcDsQlr);
                            }
                        }
                    }
                }
            }
            setSxhDsQlr(bdcDsQlrDOList);
        }
        return bdcDsQlrDOList;
    }


    /**
     * @param bdcDsQlrDOList 权利人列表
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 设置第三权利人顺序号
     */
    private void setSxhDsQlr(List<BdcDsQlrDO> bdcDsQlrDOList) {
        if (CollectionUtils.isNotEmpty(bdcDsQlrDOList)) {
            int ygrsxh = 0;
            int msrsxh = 0;
            for (int i = 0; i < bdcDsQlrDOList.size(); i++) {
                int sxh = 0;
                BdcDsQlrDO bdcDsQlrDO = bdcDsQlrDOList.get(i);
                if (StringUtils.equals(bdcDsQlrDO.getQlrlb(), Constants.QLRLB_YGR)) {
                    ygrsxh++;
                    sxh = ygrsxh;
                }
                if (StringUtils.equals(bdcDsQlrDO.getQlrlb(), Constants.QLRLB_MSR)) {
                    msrsxh++;
                    sxh = msrsxh;
                }
                if (bdcDsQlrDO.getSxh() == null || bdcDsQlrDO.getSxh() == 0) {
                    bdcDsQlrDO.setSxh(sxh);
                    bdcQlrFeignService.updateBdcDsQlr(bdcDsQlrDO);
                }
            }
        }
    }

    /**
     * @param qlrid 权利人ID
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除权利人
     */
    @ResponseBody
    @DeleteMapping("/deleteDsQlr")
    public void deleteBdcDsQlr(@RequestParam("qlrid") String qlrid, @RequestParam("xmid") String xmid, @RequestParam
            ("qlrlb") String qlrlb, @RequestParam("sxh") Integer sxh, @RequestParam("processInsId") String processInsId) {
        if(StringUtils.isAnyBlank(qlrid, processInsId)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数第三方权利人ID或工作流实例ID");
        }
        //获取第三方权利人信息
        BdcDsQlrQO bdcDsQlrQO =  new BdcDsQlrQO();
        bdcDsQlrQO.setQlrid(qlrid);
        List<BdcDsQlrDO> bdcDsQlrDOList = this.bdcQlrFeignService.listBdcDsQlr(bdcDsQlrQO);
        if(CollectionUtils.isNotEmpty(bdcDsQlrDOList)){
            // 根据第三方权利人姓名、证件号、登记小类 删除第三方权利
            String djxl = "";
            if(StringUtils.isNotBlank(xmid)){
                BdcXmDO bdcXmDO = this.bdcXmFeignService.queryBdcXmByXmid(xmid);
                djxl = bdcXmDO.getDjxl();
            }
            this.bdcQlrFeignService.deleteDsQlrsByQlrxx(bdcDsQlrDOList.get(0).getQlrmc(), bdcDsQlrDOList.get(0).getZjh(),
                    processInsId, null, djxl, new ArrayList<>(0));
            changeSxhForDel(xmid, qlrlb, sxh, "");
        }

    }

    /**
     * @param
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 检查并修改权利人共有方式
     */
    @ResponseBody
    @PostMapping("/checkAndchangeGyfs")
    public void checkAndchangeGyfs(@RequestParam("xmid") String xmid, @RequestParam("processInsId") String processInsId,@RequestParam("lclx") String lclx) throws Exception{
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        generateUpdateData(bdcQlrDOList, processInsId, xmid, lclx);


    }

    /**
     * @param json 前台传输权利人集合Json
     * @return 修改数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 修改权利人（用于简单流程）
     */
    @ResponseBody
    @PatchMapping(value = "/list/updateDsQlr")
    public Integer updateBdcDsQlr(@RequestBody String json, String processInsId) {
        Integer count = 0;
        for (Object obj : JSON.parseArray(json)) {
            JSONObject jsonObject = (JSONObject) obj;
            if(StringUtils.isNotBlank(jsonObject.getString("qlrid"))) {
                count += bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(obj), BdcDsQlrDO.class.getName());
            }else{
                BdcDsQlrDO bdcDsQlrDO = JSONObject.toJavaObject(jsonObject, BdcDsQlrDO.class);
                bdcQlrFeignService.insertBdcDsQlr(bdcDsQlrDO);
                count++;
            }
        }
        return count;
    }

    @ResponseBody
    @PatchMapping(value = "/list/updateDsQlr/dyaq")
    public Integer updateBdcDyaqDsQlr(@RequestBody String json) {
        Integer count = 0;
        JSONObject obj = JSONObject.parseObject(json);
        BdcDsQlrDO bdcDsQlrDO = JSONObject.toJavaObject(obj, BdcDsQlrDO.class);
        count += bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcDsQlrDO), BdcDsQlrDO.class.getName());
        return count;
    }


    /**
     * @return map
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取页面默认配置
     */
    @ResponseBody
    @GetMapping("/getDjxl")
    public Map getDjxl() {
        Map defaultPzMap = new HashMap();
        defaultPzMap.put("cdghDjxl", cdghDjxl);
        return defaultPzMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid
     * @return boolean
     * @description 验证是否为默认的共有方式
     */
    @ResponseBody
    @GetMapping("/checkdefaultgyfs")
    public boolean checkDefaultGyfs(@RequestParam(name = "gyfs")String gyfs,@RequestParam(name = "xmid") String xmid){
        boolean defaultGtgy = checkGyfsDefaultGtgy(xmid);
        if(gyfs.endsWith(",")){
            gyfs = gyfs.substring(0,gyfs.length()-1);
        }
        return defaultGtgy && StringUtils.equals(gyfs, CommonConstantUtils.GYFS_GTGY + "");
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取登记小类
     */
    private String getDjxlByXmid(String xmid){
        //同步其他流程，需要获取对应流程的登记小类
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            return bdcXmDOList.get(0).getDjxl();
        }
        return null;

    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid
     * @return boolean
     * @description 验证是否为共有方式默认为共同共有
     */
    private boolean checkGyfsDefaultGtgy(String xmid){
        if(StringUtils.isNotBlank(gtgyDjxl) && StringUtils.isNotBlank(xmid)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmList)){
                BdcXmDO bdcXmDO = bdcXmList.get(0);
                return StringUtils.contains(gtgyDjxl, bdcXmDO.getDjxl());
            }
        }
        return false;
    }

    /**
     * @return 企业信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取企业信息
     */
    @ResponseBody
    @GetMapping("/yzQyxx")
    public Object yzQyxx(BdcYzQyxxInterfaceQO bdcYzQyxxInterfaceQO) {
        LOGGER.info("验证企业信息接口入参：{}",bdcYzQyxxInterfaceQO);
        Object obj = exchangeInterfaceFeignService.requestInterface("hf_qyjbxxyz", bdcYzQyxxInterfaceQO);
        if(null != obj){
            LOGGER.info("验证企业信息接口返回值：{}",JSONObject.toJSONString(obj));
            return obj;
        }else{
            throw new AppException("验证企业信息接口返回值为null");
        }
    }


    /**
     * @param qlrmc 权利人名称
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 权利人社会组织验证
     * @date : 2020/10/30 15:30
     */
    @ResponseBody
    @GetMapping("/shzzyz")
    public Object shzzyz(String qlrmc, String qlrzjh) {
        if(StringUtils.isBlank(qlrmc) || StringUtils.isBlank(qlrzjh)) {
            throw new AppException("社会组织验证缺失权利人名称证件号");
        }
        ShzztyxxRequestDTO shgxCxywcsRequestDTO = new ShzztyxxRequestDTO();
        ShzztyxxCxywcsDTO shzztyxxCxywcsDTO = new ShzztyxxCxywcsDTO();
        List<ShzztyxxCxywcsDTO> shzztyxxCxywcsDTOList = new ArrayList<>(1);
        shzztyxxCxywcsDTO.setName(qlrmc);
        shzztyxxCxywcsDTO.setTyshxydm(qlrzjh);
        shzztyxxCxywcsDTO.setSearch_type(CommonConstantUtils.SHZZYZ_TYPE);
        shzztyxxCxywcsDTOList.add(shzztyxxCxywcsDTO);
        shgxCxywcsRequestDTO.setCxywcs(shzztyxxCxywcsDTOList);
        LOGGER.info("验证社会组织信息接口入参：{}",JSONObject.toJSONString(shgxCxywcsRequestDTO));
        Object response = exchangeInterfaceFeignService.requestInterface("xgbmcx_shzztyxxcx", shgxCxywcsRequestDTO);
        LOGGER.info("验证社会组织信息接口返回值：{}",JSONObject.toJSONString(response));
        int cou = 0;
        if(Objects.nonNull(response)){
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(response));
            // 获取到查询结果
            JSONObject data = jsonObject.getJSONObject("data");
            Object count = data.get("count");
            if(Objects.nonNull(count)) {
                cou = (Integer) count;
            }
            return cou>0;
        }
        return false;
    }

    /**
     * @param qlrmc 权利人名称
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 权利人公安验证
     * @date : 2020/10/30 15:30
     */
    @ResponseBody
    @GetMapping("/gayz")
    public Object gayz(String qlrmc, String qlrzjh, String gzlslid){
        if(StringUtils.isAnyBlank(qlrmc,qlrzjh,gzlslid)) {
            throw new AppException("权利人公安验证缺少必要的参数");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("公安验证未查询到相关项目信息");
        }
        PoliceIdentityCheckRequestDTO gabSfhsRequestDTO = new PoliceIdentityCheckRequestDTO();
        List<PoliceIdentityCheckParamDTO> gaSfhsParamDTOList = new ArrayList<>(1);
        PoliceIdentityCheckParamDTO gaSfhsParamDTO = new PoliceIdentityCheckParamDTO();
        //流程编码
        gaSfhsParamDTO.setBusinesstypecode(bdcXmDOList.get(0).getGzldyid());
        gaSfhsParamDTO.setBusinesstypename(bdcXmDOList.get(0).getGzldymc());
        //权利人信息
        PoliceIdentityCheckConditionDTO gaSfhsConditionDTO = new PoliceIdentityCheckConditionDTO();
        List<PoliceIdentityCheckConditionDTO> policeIdentityCheckConditionDTOList = new ArrayList<>(1);
        gaSfhsConditionDTO.setGmsfhm(qlrzjh);
        gaSfhsConditionDTO.setXm(qlrmc);
        policeIdentityCheckConditionDTOList.add(gaSfhsConditionDTO);
        gaSfhsParamDTO.setConditionDTOS(policeIdentityCheckConditionDTOList);
        gaSfhsParamDTOList.add(gaSfhsParamDTO);
        //查询入参：
        gabSfhsRequestDTO.setAppname(CommonConstantUtils.ACCEPT_DM);
        gabSfhsRequestDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
        gabSfhsRequestDTO.setParamDTOList(gaSfhsParamDTOList);
        LOGGER.info("公安验证身份核查入参{}",JSONObject.toJSONString(gabSfhsRequestDTO));
        PoliceIdentityCheckResponseDTO policeIdentityCheckResponseDTO = naturalResourcesFeignService.identityCheck(gabSfhsRequestDTO);
        LOGGER.info("公安验证身份核查接口返回{}",JSONObject.toJSONString(policeIdentityCheckResponseDTO));
        if(Objects.nonNull(policeIdentityCheckResponseDTO)) {
            List<PoliceIdentityCheckReturnDTO> returnInfos = policeIdentityCheckResponseDTO.getReturnInfos();
            if(CollectionUtils.isNotEmpty(returnInfos)) {
                PoliceIdentityCheckReturnDTO policeIdentityCheckReturnDTO = returnInfos.get(0);
                return StringUtils.isNotBlank(policeIdentityCheckReturnDTO.getCheckCode()) && StringUtils.equals(CommonConstantUtils.GAYZ_RESULT_CODE, policeIdentityCheckReturnDTO.getCheckCode());
            }
        }
        return false;
    }

    /**
     * @param qlrmc 权利人名称
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 权利人企业验证
     * @date : 2020/10/30 15:30
     */
    @ResponseBody
    @GetMapping("/qyyz")
    public Object qyyz(String qlrmc, String qlrzjh, String gzlslid){
        if(StringUtils.isAnyBlank(qlrmc,qlrzjh,gzlslid)) {
            throw new AppException("权利人公安验证缺少必要的参数");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("企业验证未查询到相关项目信息");
        }
        ZwSamrEnterpriseCheckRequestDTO requestDTO = new ZwSamrEnterpriseCheckRequestDTO();
        //权利人信息
        ZwSamrEnterpriseCheckParamDTO paramDTO  = new ZwSamrEnterpriseCheckParamDTO();
        List<ZwSamrEnterpriseCheckParamDTO> paramDTOList = new ArrayList<>(1);
        paramDTO.setEntname(qlrmc);
        paramDTO.setUniscid(qlrzjh);
        paramDTOList.add(paramDTO);
        requestDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
        requestDTO.setParamDTOList(paramDTOList);
        LOGGER.info("企业验证接口入参{}",JSONObject.toJSONString(requestDTO));
        ZwSamrEnterpriseCheckResponseDTO responseDTO = provincialDataSharingFeignService.enterpriseCheck(requestDTO);
        LOGGER.info("企业验证接口返回值{}",JSONObject.toJSONString(responseDTO));
        if(Objects.nonNull(responseDTO)) {
            return StringUtils.isNotBlank(responseDTO.getEntchk_checkres_key()) && StringUtils.equals(CommonConstantUtils.QYYZ_RESULT_CODE,responseDTO.getEntchk_checkres_key());
        }
        return false;
    }

    /**
     * 权利人义务人身份信息核查(蚌埠)
     *
     * @param gzlslid 工作流实例ID
     * @return 核查结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/sfhy")
    public Object sfhy(@RequestParam(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("未获取到工作流实例ID");
        }
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listAllBdcQlr(gzlslid, null, null);
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            throw new AppException("未获取到权利人信息");
        }
        bdcQlrDOList = bdcQlrDOList.stream().filter(t -> CommonConstantUtils.ZJZL_SFZ.equals(t.getZjzl())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            throw new AppException("未获取到证件类型为身份证的权利人信息");
        }
        Map<String, Object> map = new HashMap(2);
        //  查询受理编号信息
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        String slbh = "";
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            slbh = bdcXmDTOList.get(0).getSlbh();
        }
        map.put("slbh", slbh);

        // 获取权利人信息
        List<Map<String, String>> paramList = new ArrayList<>(bdcQlrDOList.size());
        for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
            Map<String, String> param = new HashMap(2);
            param.put("xm", bdcQlrDO.getQlrmc());
            param.put("zjh", bdcQlrDO.getZjh());
            paramList.add(param);
        }
        map.put("param", paramList);
        return this.exchangeInterfaceFeignService.requestInterface("ahst_sfhc", paramList);
    }


    /**
     * 保存修改权利人如果是机构并且配置了对应的lzr需要自动保存lzr
     *
     * @param bdcQlrDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @ResponseBody
    @PostMapping("/saveJglzr/{xmid}")
    public void saveJglzr(@PathVariable(name = "xmid") String xmid, @RequestBody(required = true) BdcQlrDO bdcQlrDO) throws Exception {
        if (tbjglzr && null != bdcQlrDO && StringUtils.isNotBlank(xmid)) {
            // 检查权利人是否是机构
            String qlrmc = bdcQlrDO.getQlrmc();
            if (StringUtils.isNotBlank(qlrmc)) {
                BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(qlrmc, null);
                if (null != bdcXtJgDO && StringUtils.isNotBlank(bdcXtJgDO.getJgid())) {
                    // 是机构的话 再查询机构下是否有领证人
                    List<BdcJgLzrGxDO> list = bdcXtJgFeignService.queryJgLzrByJgid(bdcXtJgDO.getJgid());
                    if (CollectionUtils.isNotEmpty(list)) {
                        List<BdcLzrDO> listLzr = new ArrayList<>();
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setXmid(xmid);
                        List <BdcXmDO> listxm = bdcXmFeignService.listBdcXm(bdcXmQO);
                        String gzlslid = "";
                        if(CollectionUtils.isNotEmpty(listxm)){
                            gzlslid = listxm.get(0).getGzlslid();
                        }
                        //更新领证方式
                        JSONObject jsonObject =new JSONObject();
                        jsonObject.put("lzfs",CommonConstantUtils.LZFS_CK);
                        jsonObject.put("xmid",xmid);
                        bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(jsonObject),BdcXmFbDO.class.getName());
                        if(StringUtils.isBlank(gzlslid)){
                            throw new AppException("根据xmid未查询到gzlslid,xmid为"+xmid);
                        }
                        for(BdcJgLzrGxDO bdcJgLzrGxDO : list) {
                            BdcLzrDO bdcLzrDO = new BdcLzrDO();
                            BeanUtils.copyProperties(bdcJgLzrGxDO, bdcLzrDO);
                            bdcLzrDO.setXmid(xmid);

                            listLzr.add(bdcLzrDO);
                            // 避免重复 先删除lzr后面再批量插入
                            bdcLzrFeignService.deleteBdcLzrsByLzrxx(bdcLzrDO.getLzrmc(), bdcLzrDO.getLzrzjh(), gzlslid, "");
                        }
                        // 插入之前先删除 同名同证件号的lzr
                        //批量插入
                        bdcSlLzrFeignService.updatePlBdcLzr(JSON.toJSONString(listLzr), gzlslid, "");
                    }
                }
            }
        }
    }

    /**
     * 删除lzr表的机构配置的lzr
     * @param qlrid
     */
    public void deleteJgLzr(String qlrid) throws Exception{
        if(!tbjglzr){
            return;
        }
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrid(qlrid);
        List<BdcQlrDO> listQlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if(CollectionUtils.isNotEmpty(listQlr)){
            String qlrmc = listQlr.get(0).getQlrmc();
            String xmid = listQlr.get(0).getXmid();

            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List <BdcXmDO> listxm = bdcXmFeignService.listBdcXm(bdcXmQO);
            String gzlslid = "";
            if(CollectionUtils.isNotEmpty(listxm)){
                gzlslid = listxm.get(0).getGzlslid();
            }
            if(StringUtils.isBlank(gzlslid)){
                throw new AppException("根据xmid未查询到gzlslid,xmid为"+xmid);
            }

            // 判断当前权利人是否是机构
            BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(qlrmc,null);
            if(null != bdcXtJgDO && StringUtils.isNotBlank(bdcXtJgDO.getJgid())){
                List<BdcJgLzrGxDO> list = bdcXtJgFeignService.queryJgLzrByJgid(bdcXtJgDO.getJgid());
                if (CollectionUtils.isNotEmpty(list)) {
                    for (BdcJgLzrGxDO bdcJgLzrGxDO : list) {
                        bdcLzrFeignService.deleteBdcLzrsByLzrxx(bdcJgLzrGxDO.getLzrmc(), bdcJgLzrGxDO.getLzrzjh(), gzlslid, "");
                    }
                }
            }
        }
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/2/7 10:45
     */
    @ResponseBody
    @DeleteMapping("/deleteDsQlr/dyaq")
    public void deleteBdcDyaDsQlr(@RequestParam("dsqlrmc") String dsqlrmc, @RequestParam("dsqlrzjh") String dsqlrzjh, @RequestParam
            ("qlrlb") String qlrlb, @RequestParam("processInsId") String processInsId) {
        if (StringUtils.isAnyBlank(dsqlrmc, dsqlrzjh)) {
            throw new AppException("删除第三权利人缺少名称证件号");
        }
        bdcQlrFeignService.deleteDsQlrsByQlrxx(dsqlrmc, dsqlrzjh, processInsId, qlrlb, "", new ArrayList<>(0));
    }

    /**
     * 查封流程，同步查封机关为权利人
     * @param jgmc  机构名称
     * @param xmid  项目ID
     * @param processInsId  工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/sync/cfjgSqr")
    public void syncCfjgSqr(String jgmc, String xmid, String processInsId, String djxl) {
        if(StringUtils.isBlank(jgmc)){
            return;
        }
        if(StringUtils.isBlank(xmid)){
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList) ) {
                xmid = bdcXmDTOList.get(0).getXmid();
            }
        }

        // 删除原有权利人
        this.removeOriginalSqr(processInsId, xmid, djxl);
        // 将查封机构信息转换为权利人信息
        BdcQlrDO bdcQlrDO = this.getBdcQlrDOByCfjg(jgmc);
        // 新增权利人信息
        bdcQlrFeignService.insertBatchBdcQlr(bdcQlrDO, processInsId, djxl);
        // 同步权利人信息冗余字段
        bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
    }

    /**
     * 解封流程，同步解封机关为权利人
     * @param jgmc  机构名称
     * @param xmid  项目ID
     * @param processInsId  工作流实例ID
     * @param djxl 登记小类
     * @author <a href="mailto:wangxiao@gtmap.cn">wangxiao</a>
     */
    @ResponseBody
    @GetMapping("/sync/jfjgSqr")
    public void syncJfjgSqr(String jgmc, String xmid, String processInsId, String djxl) {
        if(StringUtils.isBlank(jgmc)){
            return;
        }
        if(StringUtils.isBlank(xmid)){
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList) ) {
                xmid = bdcXmDTOList.get(0).getXmid();
            }
        }

        // 删除原有权利人
        this.removeOriginalSqr(processInsId, xmid, djxl);
        // 将解封机构信息转换为权利人信息
        BdcQlrDO bdcQlrDO = this.getBdcQlrDOByCfjg(jgmc);
        // 新增权利人信息
        bdcQlrFeignService.insertBatchBdcQlr(bdcQlrDO, processInsId, djxl);
        // 同步权利人信息冗余字段
        bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
    }

    /**
     * 根据机构名称获取权利人信息
     * @param jgmc  机构名称
     * @return 不动产权利人信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    private BdcQlrDO getBdcQlrDOByCfjg(String jgmc){
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        bdcQlrDO.setQlrmc(jgmc);
        bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        bdcQlrDO.setQlrlx(CommonConstantUtils.QLRLX_GJJG);
        bdcQlrDO.setGyfs(CommonConstantUtils.GYFS_DDSY);
        BdcXtJgDO bdcXtJgDO = this.bdcXtJgFeignService.queryJgByJgmc(jgmc, "2");
        if(Objects.nonNull(bdcXtJgDO)){
            BeanUtils.copyProperties(bdcXtJgDO, bdcQlrDO);
            bdcQlrDO.setZjzl(bdcXtJgDO.getJgzjzl());
            bdcQlrDO.setZjh(bdcXtJgDO.getJgzjbh());
        }
        return bdcQlrDO;
    }

    /**
     * 同步查封机构为权利人时，先删除权利人信息，再进行同步操作
     */
    public void removeOriginalSqr(String processInsId, String xmid, String djxl){
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            for(BdcQlrDO bdcQlrDO:bdcQlrDOList){
                bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), processInsId, bdcQlrDO.getQlrlb(), djxl, new ArrayList<>());
            }
        }
    }

    /**
     * @param json 前台传输权利人集合json
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 批量修改权利人（用于批量页面）
     */
    @ResponseBody
    @PostMapping(value = "/checkSyncQlrLzr")
    public boolean checkSyncQlrLzr(@RequestBody String json, @RequestParam("processInsId") String processInsId) {
        if(!syncQlrToLzrFlag){
            return false;
        }

        if(StringUtils.isNotBlank(json)){
            LOGGER.info("开始检查是否需要提示同步权利人到领证人：{}",json);
            JSONArray jsonArray = JSONObject.parseArray(json);
            if (CollectionUtils.isNotEmpty(jsonArray)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    BdcQlrDO bdcQlrDOTemp = JSONObject.toJavaObject(obj, BdcQlrDO.class);
                    String qlrid = bdcQlrDOTemp.getQlrid();
                    BdcLzrQO lzrQO = new BdcLzrQO();
                    lzrQO.setQlrid(qlrid);
                    // 通过权利人id能查到lzr 则同步属性
                    List<BdcLzrDO> listLzrTtemp  = bdcLzrFeignService.listBdcLzr(lzrQO);
                    if(CollectionUtils.isNotEmpty(listLzrTtemp)){
                        // 当有任意字段不一样时 需要提示
                        if(!StringUtils.equals(listLzrTtemp.get(0).getLzrmc(),bdcQlrDOTemp.getQlrmc())
                                ||!StringUtils.equals(listLzrTtemp.get(0).getLzrzjh(),bdcQlrDOTemp.getZjh())
                                ||!listLzrTtemp.get(0).getLzrzjzl().equals(bdcQlrDOTemp.getZjzl())
                                ||!StringUtils.equals(listLzrTtemp.get(0).getLzrdh(),bdcQlrDOTemp.getDh())){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param qlrsJson 前台传输权利人集合json
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 同步权利人到领证人
     */
    @ResponseBody
    @PatchMapping(value = "/syncQlrLzr")
    public void syncQlrLzr(@RequestBody String qlrsJson, @RequestParam("processInsId") String processInsId) throws Exception{

        if(StringUtils.isNotBlank(qlrsJson)){
            LOGGER.info("开始同权权利人到领证人：{}",qlrsJson);
            JSONArray jsonArray = JSONObject.parseArray(qlrsJson);
            if (CollectionUtils.isNotEmpty(jsonArray)) {
                List<BdcLzrDO> listLzr = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    BdcQlrDO bdcQlrDOTemp = JSONObject.toJavaObject(obj, BdcQlrDO.class);
                    String qlrid = bdcQlrDOTemp.getQlrid();
                    BdcLzrQO lzrQO = new BdcLzrQO();
                    lzrQO.setQlrid(qlrid);
                    // 通过权利人id能查到lzr 则同步属性
                    List<BdcLzrDO> listLzrTtemp  = bdcLzrFeignService.listBdcLzr(lzrQO);
                    if(CollectionUtils.isNotEmpty(listLzrTtemp)){
                        // 当能查到lzr的时候 批量流程需要批量更新lzr
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setGzlslid(processInsId);
                        List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
                        List<BdcLzrDO> listAllLzr = bdcLzrFeignService.getBdcLzrDOByXm(listXm);
                        for(BdcLzrDO bdcLzrDO : listAllLzr){
                            bdcLzrDO.setLzrmc(bdcQlrDOTemp.getQlrmc());
                            bdcLzrDO.setLzrzjh(bdcQlrDOTemp.getZjh());
                            bdcLzrDO.setLzrzjzl(bdcQlrDOTemp.getZjzl());
                            bdcLzrDO.setLzrdh(bdcQlrDOTemp.getDh());
                        }
                        listLzr.addAll(listAllLzr);
                        if(CollectionUtils.isNotEmpty(listLzr)){
                            break;
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(listLzr)){
                    bdcLzrFeignService.batchUpdateBdcLzr(listLzr);
                }
            }
        }
    }

    /**
     * @param bdcXtJgDO 系统机构实体
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存系统机构
     */
    @ResponseBody
    @PutMapping("/saveXtjg")
    public void saveXtjg(@RequestBody BdcXtJgDO bdcXtJgDO) {
        if(null != bdcXtJgDO && StringUtils.isNotBlank(bdcXtJgDO.getJgmc())){
            BdcXtJgDO sfczBdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcXtJgDO.getJgmc(),null);
            if(sfczBdcXtJgDO == null || StringUtils.isBlank(sfczBdcXtJgDO.getJgid())){
                bdcXtJgFeignService.insertBdcJg(bdcXtJgDO);
            }
        }
    }

    /**
     * 通过权利人名称和证件号查询法人名称
     * @param bdcQueryFrmcQO
     * @return 法人名称
     */
    @ResponseBody
    @PostMapping("/queryFrmcByQlrmcAndZjh")
    public Object queryFrmcByQlrmcAndZjh(@RequestBody BdcQueryFrmcQO bdcQueryFrmcQO) {
        if(null == bdcQueryFrmcQO){
            throw new AppException("queryFrmcByQlrmcAndZjh查询实体为null");
        }
        // 先检查是否需要查询fddbr
        // 没有选择qlrlx 或者选择的权利人类型不在配置中
        if(bdcQueryFrmcQO.getQlrlx() == null || !qyxxInterfaceConfig.getBeanid().containsKey(bdcQueryFrmcQO.getQlrlx()+"")){
            return "";
        }

        // 选择的权利人类别 不在配置中
        if(bdcQueryFrmcQO.getQlrlb() == null || qyxxInterfaceConfig.getQlrlb().indexOf(bdcQueryFrmcQO.getQlrlb()+"") == -1){
            return "";
        }

        // 先查机构表
        if(StringUtils.isNotBlank(bdcQueryFrmcQO.getJgmc())){
            Object obj = this.queryBdcXtJgxx(bdcQueryFrmcQO.getJgmc(),null);
            if(null != obj){
                BdcXtJgDO bdcXtJgDO = (BdcXtJgDO)obj;
                if(StringUtils.isNotBlank(bdcXtJgDO.getJgid())){
                    return bdcXtJgDO.getFrmc();
                }
            }
        }else{
            throw new AppException("queryFrmcByQlrmcAndZjh缺失查询参数：jgmc");
        }
        if(StringUtils.isBlank(bdcQueryFrmcQO.getJgzjbh())){
            throw new AppException("查询共享接口缺失机构证件编号");
        }
        // 当没有查询系统机构表数据的时候，这里调用接口查询企业和事业单位信息
        String frmc = this.getQyxxYc(bdcQueryFrmcQO.getJgmc(),bdcQueryFrmcQO.getJgzjbh(),qyxxInterfaceConfig.getBeanid().get(bdcQueryFrmcQO.getQlrlx()+""));
        if(StringUtils.isNotBlank(frmc)){
            BdcXtJgDO bdcXtJgDO = new BdcXtJgDO();
            BeanUtils.copyProperties(bdcQueryFrmcQO,bdcXtJgDO);
            if(bdcQueryFrmcQO.getQlrlx().equals(CommonConstantUtils.QLRLX_QY)){
                bdcXtJgDO.setJglb(CommonConstantUtils.JGLB_QY);
            }else{
                bdcXtJgDO.setJglb(CommonConstantUtils.JGLB_SYDW);
            }
            bdcXtJgDO.setFrmc(frmc);
            // 能从共享接口查到的企业且不在机构表的 这里插入一条数据到机构表
            this.saveXtjg(bdcXtJgDO);
            return frmc;
        }
        return "";

    }

    /**
     * 调用共享接口查询企业信息
     * @param qlrmc
     * @param zjh
     * @param beanId
     * @return
     */
    public String getQyxxYc(String qlrmc,String zjh,String beanId) {
        if (StringUtils.isBlank(qlrmc) || StringUtils.isBlank(zjh)) {
            throw new MissingArgumentException("缺少参数：权利人名称或者证件号");
        }
        if (StringUtils.isBlank(beanId)) {
            throw new MissingArgumentException("缺少参数：beanid");
        }
        QyxxRequestDTO qyxxRequestDTO = new QyxxRequestDTO();
        List<QyxxCxywcsRequestDTO> cxywcs = new ArrayList<>();
        QyxxCxywcsRequestDTO qyxxCxywcsRequestDTO = new QyxxCxywcsRequestDTO();
        qyxxCxywcsRequestDTO.setEntname(qlrmc);
        qyxxCxywcsRequestDTO.setUniscid(zjh);
        cxywcs.add(qyxxCxywcsRequestDTO);
        qyxxRequestDTO.setCxywcs(cxywcs);

        LOGGER.info("调用接口{}入参：{}",beanId,JSONObject.toJSONString(qyxxRequestDTO));
        Object object = exchangeInterfaceFeignService.sjptRequestInterface(beanId, qyxxRequestDTO);

        if(Objects.isNull(object)){
            throw new AppException("beanid:"+beanId+"响应结果为空!");
        }
        LOGGER.info("调用查询企业或事业单位接口返参：{}",JSONObject.toJSONString(object));

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));

        LOGGER.info("---企业基本信息接口:{}",jsonObject);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        JSONObject headJsonObject = jsonObject.getJSONObject("head");

        if(headJsonObject.containsKey("status") && headJsonObject.containsKey("msg") && !StringUtils.equals("0",headJsonObject.getString("status"))){
            throw new AppException(headJsonObject.getString("msg"));
        }

        if(dataJsonObject.containsKey("cxjg")){
            JSONArray cxjgJsonArray = null;
            if(StringUtils.equals("xgbmcx_qyxx",beanId)){
                cxjgJsonArray = dataJsonObject.getJSONArray("cxjg");
            }else{
                JSONObject cxjgJsonObject = dataJsonObject.getJSONObject("cxjg");
                cxjgJsonArray = cxjgJsonObject.getJSONArray("datalist");
            }
            if(CollectionUtils.isNotEmpty(cxjgJsonArray)) {
                Object obj = JSON.parse(cxjgJsonArray.getString(0));
                if(obj instanceof Map){
                    Map map = (Map)obj;
                    if(map.containsKey("name") && null != map.get("name")){
                        return map.get("name").toString();
                    }else if(map.containsKey("fddbr") && null != map.get("fddbr")){
                        return map.get("fddbr").toString();
                    }
                }
            }

        } else {
            throw new AppException("缺失查询结果数据");
        }

        return "";
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权力人限购信息数据
     * @date : 2021/4/21 14:13
     */
    @ResponseBody
    @GetMapping("/xgxx")
    public Object listXgxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询限购信息缺失工作流实例id");
        }
        return bdcSlJyxxFeignService.listXgxx(gzlslid);
    }


    /**
     * @param yyzzm 营业执照码
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通页面输入营业执照调用接口查询权利人信息和附件信息
     * @date : 2021/5/11 10:47
     */

    @ResponseBody
    @GetMapping("/importYyzz")
    public void importYyzz(@NotBlank(message = "营业执照码不可为空") String yyzzm, @NotBlank(message = "工作流实例id不可为空") String gzlslid, String djxl) throws IOException {
        bdcSlQlrFeignService.importYyzz(yyzzm, gzlslid, djxl);
    }

    /**
     * @param qllx 权利类型
     * @param qlrlb 权利人类别
     * @return 权利人特征
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取权利人特征默认值
     */
    @ResponseBody
    @GetMapping("/qlrtz/mrz")
    public Object getQlrtzMrz(Integer qllx,String qlrlb) {
        if(qllx != null &&StringUtils.isNotBlank(qlrlb)) {
            return bdcQlrFeignService.getQlrtzMrz(qllx, qlrlb);
        }
        return null;
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 回写信息到大云平台
      */
    @ResponseBody
    @PostMapping("/hxywsj")
    public void hxYwsj(String gzlslid) {
        //回写信息到平台
        try {
            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
        } catch (Exception e) {
            LOGGER.error(MSG_HXFAIL, e);
        }

    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人id获取当前项目的所有权利人id，根据名称证件号查找
     * @date : 2022/3/23 11:47
     */
    @ResponseBody
    @GetMapping("/allXmQlrid")
    public Object listAllXmQlr(String gzlslid, String qlrid, String djxl) {
        List<String> qlridList = new ArrayList<>(1);
        if (StringUtils.isNotBlank(qlrid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_QLR, djxl);
                if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                    for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                        if (StringUtils.equals(bdcQlrDO.getQlrmc(), bdcQlrDOList.get(0).getQlrmc()) && StringUtils.equals(bdcQlrDO.getZjh(), bdcQlrDOList.get(0).getZjh())) {
                            qlridList.add(bdcQlrDO.getQlrid());
                        }
                    }
                }
            }
        }
        return qlridList;
    }

    /**
     * 检测是否权利人
     *
     * @param xmid 项目ID
     * @param qllx 权利类型
     * @return
     * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
     */
    @ResponseBody
    @GetMapping("/sfczr")
    public Object checkSfczr(
            @RequestParam("xmid") String xmid,
            @RequestParam("qllx") String qllx) {
        if(StringUtils.isAnyBlank(xmid, qllx)){
            return false;
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOS)){
            return false;
        }

        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        String gzldyid = bdcXmDO.getGzldyid();
        if(CollectionUtils.isNotEmpty(bcz) && bcz.contains(gzldyid)){
            return false;
        }

        //如果是查封或者注销类的则不是持证人
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
        boolean sfbsczr = (CommonConstantUtils.QLLX_CF.equals(Integer.parseInt(qllx))
        ) || (
                CommonConstantUtils.SF_F_DM.equals(bdcCshFwkgSlDO.getSfscql())
                        && CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfzxyql())
        );

        if (sfbsczr) {
            return false;
        }

        return true;
    }


    /**
     * @param gzldyid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取长三角证照类型配置
     * @date : 2022/5/11 8:39
     */
    @GetMapping("/zzlx")
    @ResponseBody
    public Object listCsjpz(String gzldyid) {
        if (StringUtils.isBlank(gzldyid)) {
            return null;
        }
        BdcSlCsjpzDO bdcSlCsjpzQO = new BdcSlCsjpzDO();
        bdcSlCsjpzQO.setGzldyid(gzldyid);
        return bdcSlCsjPzFeignService.listCsjpz(bdcSlCsjpzQO);
    }


    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取证照信息
     * @date : 2022/5/12 13:41
     */
    @PostMapping("/zzxx")
    @ResponseBody
    public String queryCsjZzxx(@RequestBody CsjZzxxQO csjZzxxQO) {
        return bdcSlQlrFeignService.querCsjZzxx(csjZzxxQO);
    }

    @PostMapping("/sfm")
    @ResponseBody
    public String scanSfm(@RequestBody CsjZzxxQO csjZzxxQO) {
        return bdcSlQlrFeignService.querCsjZzxxBySfm(csjZzxxQO);
    }

    /**
     * 不直接处理返回到页面
     * @param csjZzxxQO
     * @return
     */
    @PostMapping("/sfm/cx")
    @ResponseBody
    public List<SfmLiscenseInfoDTO> scanSfmCx(@RequestBody CsjZzxxQO csjZzxxQO) {
        return bdcSlQlrFeignService.querCsjZzxxBySfmCx(csjZzxxQO);
    }

    /**
     * 将苏服码的人加入到权利人中
     * @param csjZzxxQO
     * @return
     */
    @PostMapping("/sfm/addqlr")
    @ResponseBody
    public void scanSfmAdd(@RequestBody CsjZzxxQO csjZzxxQO) {
        bdcSlQlrFeignService.querCsjZzxxBySfmAdd(csjZzxxQO);
    }

    /**
     * 将苏服码的文件加入到流程中
     * @param csjZzxxQO
     * @return
     */
    @PostMapping("/sfm/addfj")
    @ResponseBody
    public void scanSfmAddFj(@RequestBody CsjZzxxQO csjZzxxQO) {
        bdcSlQlrFeignService.querCsjZzxxBySfmAddFj(csjZzxxQO);
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 调用摩科接口读取权利人信息
     * @date : 2022/6/16 14:25
     */
    @GetMapping("/mkjk")
    @ResponseBody
    public Object queryQlrxxByMkjk(HttpServletRequest request, String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                HashMap<String, String> paramMap = new HashMap<>(2);
                paramMap.put("sysIp", IPPortUtils.getClientIp(request));
                paramMap.put("serviceId", bdcXmDTOList.get(0).getSlbh());
                LOGGER.warn("权利人页面点击身份证读取调用接口入参{}", paramMap);
                return exchangeInterfaceFeignService.requestInterface("mk_sfzxx_rh", paramMap);
            }
        }
        return null;
    }

    /**
     * 记录重复权利人日志
     * @param gzlslid 工作流实例ID
     * @param xmid  项目ID
     * @param zjh 证件号
     * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
     */
    @GetMapping("/cf/addLog")
    @ResponseBody
    public void addCfQlrLog(@RequestParam("gzlslid") String gzlslid,
                            @RequestParam("xmid") String xmid,
                            @RequestParam("zjh") String zjh
    ) {
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(zjh)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                BdcXmDO bdcXmDO = bdcXmDOS.get(0);
                BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                BeanUtils.copyProperties(bdcXmDO, bdcCzrzDO);
                bdcCzrzDO.setLcmc(bdcXmDO.getGzldymc());
                bdcCzrzDO.setCzsj(new Date());
                bdcCzrzDO.setSlr(userManagerUtils.getCurrentUserName());
                bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_UPDATE.key());
                bdcCzrzDO.setCzyy("确认添加重复的权利人：" + zjh);
                bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
            }
        }
    }
}


