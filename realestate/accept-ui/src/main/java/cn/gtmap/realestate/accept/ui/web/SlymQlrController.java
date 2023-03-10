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
 * @description ????????????????????????????????????
 */
@Controller
@RequestMapping("/slym/qlr")
@Validated
public class SlymQlrController extends BaseController {

    private static final String MSG_HXFAIL = "??????????????????";
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
     * ??????????????????????????????????????????????????????
     */
    @Value("#{'${qlr.bcz:}'.split(',')}")
    private List<String> bcz;

    /**
     * ???????????????????????????????????????????????? ????????????
     */
    @Value("${default.gtgyDjxl:}")
    private String gtgyDjxl;


    @Value("${html.version:}")
    private String version;

    /**
     * ??????????????????????????????????????????????????????ID,???????????????,?????????
     */
    @Value("${tbqlrtofdcq.gzldyid:}")
    private String tdsyqrGzldyids;

    /**
     * ???????????????????????????????????????
     */
    @Value("${tbjglzr:false}")
    private boolean tbjglzr;

    @Value("${sftbdsqlr: false}")
    private boolean sftbdsqlr;

    /**
     * ?????????????????????
     */
    @Value("#{'${accept-ui.ydydjxl:}'.split(',')}")
    private List<String> ydydjxl;


    @Value("${slym.syncQlrToLzrFlag:false}")
    private boolean syncQlrToLzrFlag;

    /**
     * @param qlrid ?????????ID
     * @return ?????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ???????????????ID ??????????????????????????????????????????
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
     * @description ???????????????id?????????????????????
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
            //?????????????????????
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
     * @param processInsId ???????????????ID
     * @return ???????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????ID????????????????????????????????????????????????????????????
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
                // ??????????????????????????????gzlslid???xmid
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
     * @param xmid  ??????ID
     * @param qlrlb ???????????????
     * @return ???????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ????????????ID????????????????????????????????????????????????????????????????????????
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
     * @param xmid  ??????ID
     * @author <a href="mailto:sunchao@gtmap.cn">zhangxinyu</a>
     * @description ????????????ID???????????????????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/list/yjxx/sjr")
    public Object listBdcYjxxSjr(String xmid) {
        //?????????
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb("1");
            bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            setSxh(bdcQlrDOList);
        }

        //?????????
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
     * ???????????????????????????????????????????????????????????????????????????????????????????????????/"?????????
     * @param json ????????????????????????JSON
     * @param processInsId ???????????????ID
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
     * ?????????????????????
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
                        // ????????????????????????????????????????????????  ???????????????????????? ?????????
                        if((jsonObject.containsKey("qlrmc") &&!StringUtils.equals(bdcQlrDOOld.getQlrmc(),bdcQlrDO.getQlrmc()))
                                || (jsonObject.containsKey("zjh") &&!StringUtils.equals(bdcQlrDOOld.getZjh(),bdcQlrDO.getZjh()))){
                            jsonObject.put("qlrly",CommonConstantUtils.QLRLY_SD);
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("???????????????????????????",e);
        }
    }



    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  ????????????????????????????????????
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
     * ?????????????????????????????????
     * @param processInsId ???????????????ID
     * @param type ????????????
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
     * @param json ???????????????????????????Json
     * @return ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ???????????????????????????????????????
     */
    @ResponseBody
    @PatchMapping(value = "/list/jdlc")
    public Integer updateBdcQlr(@RequestBody String json, String processInsId,String xmid,String type) throws Exception{
        BdcYwxxDTO bdcYwxxDTOBefore =new BdcYwxxDTO();
        //????????????????????????????????????
        if(StringUtils.equals(CommonConstantUtils.XXBL,type) &&StringUtils.isNotBlank(xmid)) {
            // ?????????????????????????????????
            bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(xmid);
        }
        //???????????????
        Integer count = 0;
        for (Object obj : JSON.parseArray(json)) {
            dealQlrly(JSONObject.parseObject(JSONObject.toJSONString(obj)));
            count += bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(obj), BdcQlrDO.class.getName());
        }
        if (StringUtils.isNotBlank(processInsId)) {
            //???????????????????????????
            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
            bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
            //?????????????????????
            try {
                bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
            } catch (Exception e) {
                LOGGER.error(MSG_HXFAIL, e);
            }
        }
        //????????????????????????????????????
        if(StringUtils.equals(CommonConstantUtils.XXBL,type) &&StringUtils.isNotBlank(xmid)) {
            // ???????????????????????????
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
     * @param json ???????????????????????????json
     * @return ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????????????????????????????
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
                        // ?????????????????? ??????????????? ???????????????????????????zjh???mc ??? ????????????????????? ????????????????????????????????????????????????????????????????????? ??????
                        dealQlrly(object);
                        count += bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(object), BdcQlrDO.class.getName());
                    }

                    // ?????????????????????????????????
                    this.syncYcslSqrxx(jsonObjectList, processInsId, Constants.FUN_UPDATE);
                }
            }
            if (StringUtils.isNotBlank(processInsId)) {
                //???????????????????????????
                bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
                //??????????????????
                bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
                //?????????????????????
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
     * @param json ???????????????????????????json
     * @return ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????????????????????????????
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
     * @param json ???????????????????????????json
     * @return List<BdcQlrDO> ????????????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????(????????????????????????)
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
     * @param bdcQlrDO ?????????
     * @return ?????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ???????????????(???????????????, ??????????????????)
     */
    @ResponseBody
    @PostMapping("/jdlc")
    public Object insertBdcQlr(@RequestBody BdcQlrDO bdcQlrDO, @RequestParam("processInsId") String processInsId,String xmid,String type) throws Exception{
        //????????????????????????????????????
        BdcYwxxDTO bdcYwxxDTOBefore =new BdcYwxxDTO();
        if(StringUtils.equals(CommonConstantUtils.XXBL,type) &&StringUtils.isNotBlank(xmid)) {
            // ?????????????????????????????????
            bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(xmid);
        }
        //???????????????
        if (bdcQlrDO != null) {
            bdcQlrDO.setQlrid(UUIDGenerator.generate16());
            //????????????????????????
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
            //?????????????????????
            try {
                bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
            } catch (Exception e) {
                LOGGER.error(MSG_HXFAIL, e);
            }
        }
        //????????????????????????????????????
        if(StringUtils.equals(CommonConstantUtils.XXBL,type) &&StringUtils.isNotBlank(xmid)) {
            // ???????????????????????????
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
     * @param json ?????????
     * @return ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ???????????????(???????????????, ??????????????????)
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
                //?????????????????????
                try {
                    bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
                } catch (Exception e) {
                    LOGGER.error(MSG_HXFAIL, e);
                }
                this.notifySyncTdsyqr(JSON.toJSONString(jsonObjectList), processInsId);
            }

            // ?????????????????????????????????
            this.syncYcslSqrxx(jsonObjectList, processInsId, Constants.FUN_INSERT);
        }

        return returnQlr;
    }

    /**
     * @param bdcQlrDO ?????????
     * @return ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ???????????????(???????????????, ??????????????????)
     */
    @ResponseBody
    @PostMapping("/pllc")
    public Object insertPlBdcQlr(@RequestBody BdcQlrDO bdcQlrDO, @RequestParam("processInsId") String processInsId) {
        if (bdcQlrDO != null) {
            //????????????????????????????????????,??????????????????????????????
            bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), processInsId, bdcQlrDO.getQlrlb(),"",new ArrayList<>());
            if(bdcQlrDO.getQlrly() ==null){
                bdcQlrDO.setQlrly(CommonConstantUtils.QLRLY_SD);
            }
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.insertBatchBdcQlr(bdcQlrDO, processInsId,"");
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                if (StringUtils.isNotBlank(processInsId)) {
                    //?????????????????????????????????????????????
                    updateQlrxxPl(processInsId,"",bdcQlrDOList.get(0).getQlrlb());
                    // ??????????????????????????????
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
     * @description ???????????????(???????????????, ????????????????????????)
     */
    @ResponseBody
    @PostMapping("/plzh")
    public Object insertPlzhBdcQlr(@RequestBody String json, @RequestParam("processInsId") String processInsId,@RequestParam("djxl") String djxl) {
        return bdcSlQlrFeignService.insertPlzhBdcQlr(json, processInsId, djxl,syncTdsyqr(processInsId));

    }


    /**
     * @param qlrid ?????????ID
     * @param xmid  ??????ID
     * @param qlrlb ???????????????
     * @param sxh   ?????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ???????????????(??????????????????)
     */
    @ResponseBody
    @DeleteMapping("/jdlc")
    public void deleteBdcQlr(@RequestParam("qlrid") String qlrid, @RequestParam("xmid") String xmid, @RequestParam("qlrlb") String qlrlb, @RequestParam("sxh") Integer sxh, @RequestParam("processInsId") String processInsId) {
        bdcQlrFeignService.deleteBdcQlr(qlrid);
        changeSxhForDel(xmid, qlrlb, sxh, "");
        if (StringUtils.isNotBlank(processInsId)) {
            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
            bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
            //?????????????????????
            try {
                bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
            } catch (Exception e) {
                LOGGER.error(MSG_HXFAIL, e);
            }
            // ?????? ?????????????????????
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
     * @param qlrid ?????????ID
     * @param xmid  ??????ID
     * @param qlrlb ???????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ???????????????(??????????????????)
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
            LOGGER.error("??????????????????????????????????????????????????????",e);
        }
        List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(obj, Constants.FUN_DELETE);
        if (CollectionUtils.isNotEmpty(jsonObjectList)) {
            // ?????????????????????????????????
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
                //?????????????????????
                try {
                    bdcYwsjHxFeignService.saveBdcYwsj(processInsId);
                } catch (Exception e) {
                    LOGGER.error(MSG_HXFAIL, e);
                }
                // ??????????????????????????????
                this.notifySyncTdsyqr(JSON.toJSONString(jsonObjectList), processInsId);
            }
        }
    }

    /**
     * @param qlrid        ?????????ID
     * @param processInsId ???????????????ID
     * @param sxh          ?????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ???????????????(??????????????????)
     */
    @ResponseBody
    @DeleteMapping("/pllc")
    public void deletePlBdcQlr(@RequestParam("qlrid") String qlrid, @RequestParam("processInsId") String processInsId, @RequestParam("sxh") Integer sxh) {
        if (StringUtils.isNotBlank(qlrid)) {
            try {
                deleteJgLzr(qlrid);
            } catch (Exception e) {
                LOGGER.error("??????????????????????????????????????????????????????",e);
            }

            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
                //?????????????????????
                bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), processInsId, bdcQlrDO.getQlrlb(),"",new ArrayList<>());
                changeSxhForDel(bdcQlrDO.getXmid(), bdcQlrDO.getQlrlb(), sxh, processInsId);
                //?????????????????????????????????????????????
                updateQlrxxPl(processInsId,"","");
                // ?????? ?????????????????????????????????????????????????????????????????????
                if(StringUtils.isNotBlank(version) && StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_YC)){
                    deleteLzrPl(bdcQlrDO, processInsId, "");
                }
                // ??????????????????????????????
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
            LOGGER.error("?????????????????????id?????????????????????????????????????????????????????????id???{},???????????????{}", processInsId,djxl);
            throw new AppException("?????????????????????id???????????????????????????????????????");
        }
        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.getBdcLzrDOByXm(bdcXmDOList);
        if(CollectionUtils.isNotEmpty(bdcLzrDOList)){
            BdcLzrDO bdcLzrDO = bdcLzrDOList.get(0);
            // ????????????????????????????????????????????????????????????????????????????????????
            if(StringUtils.equals(bdcDeleteQlrDO.getQlrmc(),bdcLzrDO.getLzrmc()) && StringUtils.equals(bdcDeleteQlrDO.getZjh(), bdcLzrDO.getLzrzjh())){
                bdcLzrFeignService.batchDeleteBdcLzr(bdcLzrDOList);
            }
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????(????????????????????????)
     */
    @ResponseBody
    @DeleteMapping("/plzh")
    public void deletePlzhBdcQlr(@RequestParam("qlrid") String qlrid, @RequestParam("processInsId") String processInsId, @RequestParam("sxh") Integer sxh,@RequestParam("djxl") String djxl) {
        if (StringUtils.isNotBlank(qlrid)) {
            try {
                deleteJgLzr(qlrid);
            } catch (Exception e) {
                LOGGER.error("??????????????????????????????????????????????????????",e);
            }
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                BdcQlrDO bdcDeleteQlrDO = bdcQlrDOList.get(0);
                //????????????????????????????????????????????????????????????????????????
                JSONObject obj =JSONObject.parseObject(JSONObject.toJSONString(bdcDeleteQlrDO));
                List<JSONObject> jsonObjectList = bdcQlrFeignService.listZhBdcQlr(obj, Constants.FUN_DELETE);
                for (JSONObject object : jsonObjectList) {
                    BdcQlrDO bdcQlrDO = JSONObject.toJavaObject(object, BdcQlrDO.class);
                    djxl =getDjxlByXmid(bdcQlrDO.getXmid());
                    if(StringUtils.isNotBlank(djxl)) {
                        //??????????????????????????????id??????????????????
                        if(StringUtils.isBlank(bdcQlrDO.getQlrmc()) || StringUtils.isBlank(processInsId)){
                            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
                        }
                        List<String> xmidList =new ArrayList<>();
                        //???????????????????????????,???????????????????????????????????????????????????
                        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcQlrDO.getXmid());
                        if (bdcCshFwkgSlDO.getZsxh() != null &&CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcCshFwkgSlDO.getQllx())) {
                            xmidList = bdcXmFeignService.queryZsxmList(bdcQlrDO.getXmid());
                        }
                        //????????????
                        bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), processInsId, bdcQlrDO.getQlrlb(), djxl,xmidList);
                        if(StringUtils.isNotBlank(processInsId)) {
                            //?????????????????????????????????????????????
                            updateQlrxxPl(processInsId, djxl,bdcQlrDO.getQlrlb());
                            // ??????????????????????????????
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
     * @param qlrid        ?????????ID
     * @param czlx         ????????????
     * @param processInsId ???????????????ID
     * @return ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ??????????????????????????????????????????????????????????????????????????????
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
     * @param xmid  ??????id
     * @param qlrlb ???????????????
     * @param sxh   ?????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ????????????????????????
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
     * @param bdcQlrDOList ???????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ????????????????????????
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
     * @return bdc_xt_jg??????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/bdcxtjg")
    public Object queryBdcXtJg(Integer jglb) {
        return bdcXtJgFeignService.listBdcXtJg(jglb);
    }

    /**
     * @param jgmc ????????????
     * @return bdc_xt_jg??????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????????????????
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
     * @param gzlslid ???????????????ID
     * @return ?????????????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????????????????????????????????????????
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
     * @param gzlslid ???????????????ID
     * @return ?????????????????????
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description ????????????????????????????????????????????????????????????
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
     * @param json ?????????id??????
     * @return ?????????????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????id??????????????????????????????
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
                //?????????????????????
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    bdcQlrFeignService.listPlBdcQlr(JSON.parseObject(JSON.toJSONString(qlrDOList.get(0))),
                            Constants.FUN_DELETE);
                }
                //????????????
                if (CollectionUtils.isNotEmpty(qlridList)) {
                    bdcQlrFeignService.deleteBatchQlr(qlridList);
                }
                changeSxhForDel(xmid, CommonConstantUtils.QLRLB_YWR, sxh, gzlslid);
                //?????????????????????????????????
                List<BdcQlrXmDTO> bdcQlrXmDTOList = new ArrayList<>();
                BdcQlrXmDTO bdcQlrXmDTO = new BdcQlrXmDTO();
                bdcQlrXmDTO.setQlrlb(CommonConstantUtils.QLRLB_YWR_DM);
                bdcQlrXmDTO.setXmidList(xmids);
                bdcQlrXmDTOList.add(bdcQlrXmDTO);
                bdcRyzdFeignService.updateRyzdQlrXm(bdcQlrXmDTOList);
                //?????????????????????
                try {
                    bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
                } catch (Exception e) {
                    LOGGER.error(MSG_HXFAIL, e);
                }
            }
        }
    }

    /**
     * @param json ????????????
     * @return ??????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????
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
            //?????????????????????,??????????????????????????????????????????????????????
            bdcQlrFeignService.listPlBdcQlr(JSON.parseObject(JSON.toJSONString(bdcYwrGroupDTO.getBdcQlrDO())),
                    Constants.FUN_UPDATE);
            bdcQlrIdsDTO.setBdcQlrDO(bdcYwrGroupDTO.getBdcQlrDO());
            bdcQlrIdsDTO.setQlridlist(qlrids);
            bdcQlrIdsDTO.setXmidlist(xmids);
            bdcQlrDOList = bdcQlrFeignService.updateBatchQlr(bdcQlrIdsDTO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(xmid)) {
                    //??????????????????
                    List<BdcQlrXmDTO> bdcQlrXmDTOList = new ArrayList<>();
                    BdcQlrXmDTO bdcQlrXmDTO = new BdcQlrXmDTO();
                    bdcQlrXmDTO.setQlrlb(CommonConstantUtils.QLRLB_YWR_DM);
                    bdcQlrXmDTO.setXmidList(xmids);
                    bdcQlrXmDTOList.add(bdcQlrXmDTO);
                    bdcRyzdFeignService.updateRyzdQlrXm(bdcQlrXmDTOList);
                    //?????????????????????
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
     * @param bdcQlrQO ?????????????????????
     * @return ???????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????
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
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????
     */
    @ResponseBody
    @GetMapping("/getQyxx")
    public Object getQyxx(BdcQlrInterfaceQO bdcQlrInterfaceQO,String beanName) {
        if (StringUtils.isBlank(bdcQlrInterfaceQO.getQlrmc()) ||StringUtils.isBlank(beanName)) {
            throw new MissingArgumentException("??????????????????????????????,???????????????????????????");
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
     * ??????????????????
     *
     * @param obj exchange?????????
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 17:18 2020/9/14
     */
    public Object hfQyjbxxcx(String beanName, Object obj) {
        JSONArray jsonArray = JSONArray.parseArray(obj.toString());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        LOGGER.info("json???{}", jsonObject);
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
        LOGGER.info("???????????????{}", dataMap);

        return dataMap;


    }

    /**
     * @return ????????????
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjaiqiang</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @GetMapping("/getQlrswxx")
    public Object getQlrswxx(BdcQlrInterfaceQO bdcQlrInterfaceQO) {
        if (StringUtils.isBlank(bdcQlrInterfaceQO.getQlrmc()) || StringUtils.isBlank(bdcQlrInterfaceQO.getZjh())) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        String beanName = "hf_acceptSiwangxx";
        //????????????????????????????????????,????????????????????????
        Object swxxResult = exchangeInterfaceFeignService.requestInterface(beanName, bdcQlrInterfaceQO);
        JSONArray swxxArry = JSONObject.parseArray(JSONObject.toJSONString(swxxResult));
        if(CollectionUtils.isEmpty(swxxArry)) {
            return exchangeInterfaceFeignService.requestInterface("hf_acceptBinzangxx",bdcQlrInterfaceQO);
        }
        return swxxResult;
    }

    /**
     * @param cardNo ?????????
     * @return ?????????????????????
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjaiqiang</a>
     * @description ?????????????????????
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
     * @param gzsbh ???????????????
     * @return ?????????
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjaiqiang</a>
     * @description ???????????????
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
     * @param gzlslid ???????????????ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    private void updateQlrxxPl(String gzlslid,String djxl,String qlrlb) {
        //???????????????????????????????????????????????????
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
                //????????????????????????????????????
                if(StringUtils.isBlank(djxl) ||  StringUtils.equals(bdcXmDTO.getDjxl(),djxl)) {
                    xmids.add(bdcXmDTO.getXmid());
                }
            }
            bdcQlrXmDTO.setXmidList(xmids);
            bdcQlrXmDTOList.add(bdcQlrXmDTO);
            bdcRyzdFeignService.updateRyzdQlrXm(bdcQlrXmDTOList);
        }
        //?????????????????????
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
     * @description ?????????????????????????????????????????????????????????????????????????????????????????????
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
                //????????????????????????????????????
                List<BdcQlrDO> bdcQlrList = new ArrayList<>();
                List<BdcQlrDO> bdcYwrList = new ArrayList<>();
                //1????????????????????????
                for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    if (StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                        bdcQlrList.add(bdcQlrDO);
                    } else {
                        bdcYwrList.add(bdcQlrDO);
                    }
                }
                //?????????????????????????????????????????????????????????
                if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                    //?????????????????????????????????????????????????????????
                    /**
                     * @param bdcQlrDOList
                     * @param xmid
                     * @param lclx
                     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
                     * @description   ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????xmid??????
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
     * @description ????????????
     */
    private List<BdcQlrDO> generateUpdateData(List<BdcQlrDO> bdcQlrList, String gzlslid, String xmid, String lclx) throws Exception{
        if(CollectionUtils.isNotEmpty(bdcQlrList)){
            return this.bdcSlQlrFeignService.updateBdcQlrGyfs(bdcQlrList, gzlslid, xmid, lclx);
        }else{
            return bdcQlrList;
        }
    }

    /**
     * @param json ?????????
     * @return ????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ?????????????????????
     */
    @ResponseBody
    @PostMapping(value = "/insertDsqlr")
    public BdcDsQlrDO insertDsQlr(@RequestBody String json, @RequestParam("processInsId") String processInsId) {
        JSONObject obj = JSONObject.parseObject(json);
        BdcDsQlrDO bdcDsQlrDO = JSONObject.toJavaObject(obj, BdcDsQlrDO.class);
        return bdcQlrFeignService.insertBdcDsQlr(bdcDsQlrDO);
    }

    /**
     * @param xmid ??????ID
     * @return ?????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ????????????ID??????????????????????????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/list/dsQlr")
    public List<BdcDsQlrDO> listBdcDsQlr(String xmid) {
        List<BdcDsQlrDO> bdcDsQlrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcDsQlrQO bdcDsQlrQO = new BdcDsQlrQO();
            bdcDsQlrQO.setXmid(xmid);
            bdcDsQlrDOList = bdcQlrFeignService.listBdcDsQlr(bdcDsQlrQO);
            //??????????????????????????????????????????
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
                                //?????????????????????????????????id?????????????????????????????????id??????????????????????????????????????????
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
     * @param bdcDsQlrDOList ???????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ??????????????????????????????
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
     * @param qlrid ?????????ID
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ???????????????
     */
    @ResponseBody
    @DeleteMapping("/deleteDsQlr")
    public void deleteBdcDsQlr(@RequestParam("qlrid") String qlrid, @RequestParam("xmid") String xmid, @RequestParam
            ("qlrlb") String qlrlb, @RequestParam("sxh") Integer sxh, @RequestParam("processInsId") String processInsId) {
        if(StringUtils.isAnyBlank(qlrid, processInsId)){
            throw new AppException(ErrorCode.MISSING_ARG, "??????????????????????????????ID??????????????????ID");
        }
        //??????????????????????????????
        BdcDsQlrQO bdcDsQlrQO =  new BdcDsQlrQO();
        bdcDsQlrQO.setQlrid(qlrid);
        List<BdcDsQlrDO> bdcDsQlrDOList = this.bdcQlrFeignService.listBdcDsQlr(bdcDsQlrQO);
        if(CollectionUtils.isNotEmpty(bdcDsQlrDOList)){
            // ????????????????????????????????????????????????????????? ?????????????????????
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
     * @description ????????????????????????????????????
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
     * @param json ???????????????????????????Json
     * @return ????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ???????????????????????????????????????
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
     * @description ????????????????????????
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
     * @description ????????????????????????????????????
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
     * @description ????????????ID??????????????????
     */
    private String getDjxlByXmid(String xmid){
        //????????????????????????????????????????????????????????????
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
     * @description ????????????????????????????????????????????????
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
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????
     */
    @ResponseBody
    @GetMapping("/yzQyxx")
    public Object yzQyxx(BdcYzQyxxInterfaceQO bdcYzQyxxInterfaceQO) {
        LOGGER.info("?????????????????????????????????{}",bdcYzQyxxInterfaceQO);
        Object obj = exchangeInterfaceFeignService.requestInterface("hf_qyjbxxyz", bdcYzQyxxInterfaceQO);
        if(null != obj){
            LOGGER.info("????????????????????????????????????{}",JSONObject.toJSONString(obj));
            return obj;
        }else{
            throw new AppException("????????????????????????????????????null");
        }
    }


    /**
     * @param qlrmc ???????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????
     * @date : 2020/10/30 15:30
     */
    @ResponseBody
    @GetMapping("/shzzyz")
    public Object shzzyz(String qlrmc, String qlrzjh) {
        if(StringUtils.isBlank(qlrmc) || StringUtils.isBlank(qlrzjh)) {
            throw new AppException("????????????????????????????????????????????????");
        }
        ShzztyxxRequestDTO shgxCxywcsRequestDTO = new ShzztyxxRequestDTO();
        ShzztyxxCxywcsDTO shzztyxxCxywcsDTO = new ShzztyxxCxywcsDTO();
        List<ShzztyxxCxywcsDTO> shzztyxxCxywcsDTOList = new ArrayList<>(1);
        shzztyxxCxywcsDTO.setName(qlrmc);
        shzztyxxCxywcsDTO.setTyshxydm(qlrzjh);
        shzztyxxCxywcsDTO.setSearch_type(CommonConstantUtils.SHZZYZ_TYPE);
        shzztyxxCxywcsDTOList.add(shzztyxxCxywcsDTO);
        shgxCxywcsRequestDTO.setCxywcs(shzztyxxCxywcsDTOList);
        LOGGER.info("???????????????????????????????????????{}",JSONObject.toJSONString(shgxCxywcsRequestDTO));
        Object response = exchangeInterfaceFeignService.requestInterface("xgbmcx_shzztyxxcx", shgxCxywcsRequestDTO);
        LOGGER.info("??????????????????????????????????????????{}",JSONObject.toJSONString(response));
        int cou = 0;
        if(Objects.nonNull(response)){
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(response));
            // ?????????????????????
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
     * @param qlrmc ???????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????
     * @date : 2020/10/30 15:30
     */
    @ResponseBody
    @GetMapping("/gayz")
    public Object gayz(String qlrmc, String qlrzjh, String gzlslid){
        if(StringUtils.isAnyBlank(qlrmc,qlrzjh,gzlslid)) {
            throw new AppException("??????????????????????????????????????????");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("??????????????????????????????????????????");
        }
        PoliceIdentityCheckRequestDTO gabSfhsRequestDTO = new PoliceIdentityCheckRequestDTO();
        List<PoliceIdentityCheckParamDTO> gaSfhsParamDTOList = new ArrayList<>(1);
        PoliceIdentityCheckParamDTO gaSfhsParamDTO = new PoliceIdentityCheckParamDTO();
        //????????????
        gaSfhsParamDTO.setBusinesstypecode(bdcXmDOList.get(0).getGzldyid());
        gaSfhsParamDTO.setBusinesstypename(bdcXmDOList.get(0).getGzldymc());
        //???????????????
        PoliceIdentityCheckConditionDTO gaSfhsConditionDTO = new PoliceIdentityCheckConditionDTO();
        List<PoliceIdentityCheckConditionDTO> policeIdentityCheckConditionDTOList = new ArrayList<>(1);
        gaSfhsConditionDTO.setGmsfhm(qlrzjh);
        gaSfhsConditionDTO.setXm(qlrmc);
        policeIdentityCheckConditionDTOList.add(gaSfhsConditionDTO);
        gaSfhsParamDTO.setConditionDTOS(policeIdentityCheckConditionDTOList);
        gaSfhsParamDTOList.add(gaSfhsParamDTO);
        //???????????????
        gabSfhsRequestDTO.setAppname(CommonConstantUtils.ACCEPT_DM);
        gabSfhsRequestDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
        gabSfhsRequestDTO.setParamDTOList(gaSfhsParamDTOList);
        LOGGER.info("??????????????????????????????{}",JSONObject.toJSONString(gabSfhsRequestDTO));
        PoliceIdentityCheckResponseDTO policeIdentityCheckResponseDTO = naturalResourcesFeignService.identityCheck(gabSfhsRequestDTO);
        LOGGER.info("????????????????????????????????????{}",JSONObject.toJSONString(policeIdentityCheckResponseDTO));
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
     * @param qlrmc ???????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????
     * @date : 2020/10/30 15:30
     */
    @ResponseBody
    @GetMapping("/qyyz")
    public Object qyyz(String qlrmc, String qlrzjh, String gzlslid){
        if(StringUtils.isAnyBlank(qlrmc,qlrzjh,gzlslid)) {
            throw new AppException("??????????????????????????????????????????");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("??????????????????????????????????????????");
        }
        ZwSamrEnterpriseCheckRequestDTO requestDTO = new ZwSamrEnterpriseCheckRequestDTO();
        //???????????????
        ZwSamrEnterpriseCheckParamDTO paramDTO  = new ZwSamrEnterpriseCheckParamDTO();
        List<ZwSamrEnterpriseCheckParamDTO> paramDTOList = new ArrayList<>(1);
        paramDTO.setEntname(qlrmc);
        paramDTO.setUniscid(qlrzjh);
        paramDTOList.add(paramDTO);
        requestDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
        requestDTO.setParamDTOList(paramDTOList);
        LOGGER.info("????????????????????????{}",JSONObject.toJSONString(requestDTO));
        ZwSamrEnterpriseCheckResponseDTO responseDTO = provincialDataSharingFeignService.enterpriseCheck(requestDTO);
        LOGGER.info("???????????????????????????{}",JSONObject.toJSONString(responseDTO));
        if(Objects.nonNull(responseDTO)) {
            return StringUtils.isNotBlank(responseDTO.getEntchk_checkres_key()) && StringUtils.equals(CommonConstantUtils.QYYZ_RESULT_CODE,responseDTO.getEntchk_checkres_key());
        }
        return false;
    }

    /**
     * ????????????????????????????????????(??????)
     *
     * @param gzlslid ???????????????ID
     * @return ????????????
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/sfhy")
    public Object sfhy(@RequestParam(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("???????????????????????????ID");
        }
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listAllBdcQlr(gzlslid, null, null);
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            throw new AppException("???????????????????????????");
        }
        bdcQlrDOList = bdcQlrDOList.stream().filter(t -> CommonConstantUtils.ZJZL_SFZ.equals(t.getZjzl())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            throw new AppException("??????????????????????????????????????????????????????");
        }
        Map<String, Object> map = new HashMap(2);
        //  ????????????????????????
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        String slbh = "";
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            slbh = bdcXmDTOList.get(0).getSlbh();
        }
        map.put("slbh", slbh);

        // ?????????????????????
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
     * ????????????????????????????????????????????????????????????lzr??????????????????lzr
     *
     * @param bdcQlrDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @ResponseBody
    @PostMapping("/saveJglzr/{xmid}")
    public void saveJglzr(@PathVariable(name = "xmid") String xmid, @RequestBody(required = true) BdcQlrDO bdcQlrDO) throws Exception {
        if (tbjglzr && null != bdcQlrDO && StringUtils.isNotBlank(xmid)) {
            // ??????????????????????????????
            String qlrmc = bdcQlrDO.getQlrmc();
            if (StringUtils.isNotBlank(qlrmc)) {
                BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(qlrmc, null);
                if (null != bdcXtJgDO && StringUtils.isNotBlank(bdcXtJgDO.getJgid())) {
                    // ??????????????? ????????????????????????????????????
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
                        //??????????????????
                        JSONObject jsonObject =new JSONObject();
                        jsonObject.put("lzfs",CommonConstantUtils.LZFS_CK);
                        jsonObject.put("xmid",xmid);
                        bdcEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(jsonObject),BdcXmFbDO.class.getName());
                        if(StringUtils.isBlank(gzlslid)){
                            throw new AppException("??????xmid????????????gzlslid,xmid???"+xmid);
                        }
                        for(BdcJgLzrGxDO bdcJgLzrGxDO : list) {
                            BdcLzrDO bdcLzrDO = new BdcLzrDO();
                            BeanUtils.copyProperties(bdcJgLzrGxDO, bdcLzrDO);
                            bdcLzrDO.setXmid(xmid);

                            listLzr.add(bdcLzrDO);
                            // ???????????? ?????????lzr?????????????????????
                            bdcLzrFeignService.deleteBdcLzrsByLzrxx(bdcLzrDO.getLzrmc(), bdcLzrDO.getLzrzjh(), gzlslid, "");
                        }
                        // ????????????????????? ?????????????????????lzr
                        //????????????
                        bdcSlLzrFeignService.updatePlBdcLzr(JSON.toJSONString(listLzr), gzlslid, "");
                    }
                }
            }
        }
    }

    /**
     * ??????lzr?????????????????????lzr
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
                throw new AppException("??????xmid????????????gzlslid,xmid???"+xmid);
            }

            // ????????????????????????????????????
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
            throw new AppException("??????????????????????????????????????????");
        }
        bdcQlrFeignService.deleteDsQlrsByQlrxx(dsqlrmc, dsqlrzjh, processInsId, qlrlb, "", new ArrayList<>(0));
    }

    /**
     * ?????????????????????????????????????????????
     * @param jgmc  ????????????
     * @param xmid  ??????ID
     * @param processInsId  ???????????????ID
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

        // ?????????????????????
        this.removeOriginalSqr(processInsId, xmid, djxl);
        // ?????????????????????????????????????????????
        BdcQlrDO bdcQlrDO = this.getBdcQlrDOByCfjg(jgmc);
        // ?????????????????????
        bdcQlrFeignService.insertBatchBdcQlr(bdcQlrDO, processInsId, djxl);
        // ?????????????????????????????????
        bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
    }

    /**
     * ?????????????????????????????????????????????
     * @param jgmc  ????????????
     * @param xmid  ??????ID
     * @param processInsId  ???????????????ID
     * @param djxl ????????????
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

        // ?????????????????????
        this.removeOriginalSqr(processInsId, xmid, djxl);
        // ?????????????????????????????????????????????
        BdcQlrDO bdcQlrDO = this.getBdcQlrDOByCfjg(jgmc);
        // ?????????????????????
        bdcQlrFeignService.insertBatchBdcQlr(bdcQlrDO, processInsId, djxl);
        // ?????????????????????????????????
        bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
    }

    /**
     * ???????????????????????????????????????
     * @param jgmc  ????????????
     * @return ????????????????????????
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
     * ????????????????????????????????????????????????????????????????????????????????????
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
     * @param json ???????????????????????????json
     * @return ????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????????????????????????????
     */
    @ResponseBody
    @PostMapping(value = "/checkSyncQlrLzr")
    public boolean checkSyncQlrLzr(@RequestBody String json, @RequestParam("processInsId") String processInsId) {
        if(!syncQlrToLzrFlag){
            return false;
        }

        if(StringUtils.isNotBlank(json)){
            LOGGER.info("????????????????????????????????????????????????????????????{}",json);
            JSONArray jsonArray = JSONObject.parseArray(json);
            if (CollectionUtils.isNotEmpty(jsonArray)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    BdcQlrDO bdcQlrDOTemp = JSONObject.toJavaObject(obj, BdcQlrDO.class);
                    String qlrid = bdcQlrDOTemp.getQlrid();
                    BdcLzrQO lzrQO = new BdcLzrQO();
                    lzrQO.setQlrid(qlrid);
                    // ???????????????id?????????lzr ???????????????
                    List<BdcLzrDO> listLzrTtemp  = bdcLzrFeignService.listBdcLzr(lzrQO);
                    if(CollectionUtils.isNotEmpty(listLzrTtemp)){
                        // ?????????????????????????????? ????????????
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
     * @param qlrsJson ???????????????????????????json
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ???????????????????????????
     */
    @ResponseBody
    @PatchMapping(value = "/syncQlrLzr")
    public void syncQlrLzr(@RequestBody String qlrsJson, @RequestParam("processInsId") String processInsId) throws Exception{

        if(StringUtils.isNotBlank(qlrsJson)){
            LOGGER.info("????????????????????????????????????{}",qlrsJson);
            JSONArray jsonArray = JSONObject.parseArray(qlrsJson);
            if (CollectionUtils.isNotEmpty(jsonArray)) {
                List<BdcLzrDO> listLzr = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    BdcQlrDO bdcQlrDOTemp = JSONObject.toJavaObject(obj, BdcQlrDO.class);
                    String qlrid = bdcQlrDOTemp.getQlrid();
                    BdcLzrQO lzrQO = new BdcLzrQO();
                    lzrQO.setQlrid(qlrid);
                    // ???????????????id?????????lzr ???????????????
                    List<BdcLzrDO> listLzrTtemp  = bdcLzrFeignService.listBdcLzr(lzrQO);
                    if(CollectionUtils.isNotEmpty(listLzrTtemp)){
                        // ????????????lzr????????? ??????????????????????????????lzr
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
     * @param bdcXtJgDO ??????????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ??????????????????
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
     * ???????????????????????????????????????????????????
     * @param bdcQueryFrmcQO
     * @return ????????????
     */
    @ResponseBody
    @PostMapping("/queryFrmcByQlrmcAndZjh")
    public Object queryFrmcByQlrmcAndZjh(@RequestBody BdcQueryFrmcQO bdcQueryFrmcQO) {
        if(null == bdcQueryFrmcQO){
            throw new AppException("queryFrmcByQlrmcAndZjh???????????????null");
        }
        // ???????????????????????????fddbr
        // ????????????qlrlx ?????????????????????????????????????????????
        if(bdcQueryFrmcQO.getQlrlx() == null || !qyxxInterfaceConfig.getBeanid().containsKey(bdcQueryFrmcQO.getQlrlx()+"")){
            return "";
        }

        // ???????????????????????? ???????????????
        if(bdcQueryFrmcQO.getQlrlb() == null || qyxxInterfaceConfig.getQlrlb().indexOf(bdcQueryFrmcQO.getQlrlb()+"") == -1){
            return "";
        }

        // ???????????????
        if(StringUtils.isNotBlank(bdcQueryFrmcQO.getJgmc())){
            Object obj = this.queryBdcXtJgxx(bdcQueryFrmcQO.getJgmc(),null);
            if(null != obj){
                BdcXtJgDO bdcXtJgDO = (BdcXtJgDO)obj;
                if(StringUtils.isNotBlank(bdcXtJgDO.getJgid())){
                    return bdcXtJgDO.getFrmc();
                }
            }
        }else{
            throw new AppException("queryFrmcByQlrmcAndZjh?????????????????????jgmc");
        }
        if(StringUtils.isBlank(bdcQueryFrmcQO.getJgzjbh())){
            throw new AppException("??????????????????????????????????????????");
        }
        // ???????????????????????????????????????????????????????????????????????????????????????????????????
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
            // ?????????????????????????????????????????????????????? ????????????????????????????????????
            this.saveXtjg(bdcXtJgDO);
            return frmc;
        }
        return "";

    }

    /**
     * ????????????????????????????????????
     * @param qlrmc
     * @param zjh
     * @param beanId
     * @return
     */
    public String getQyxxYc(String qlrmc,String zjh,String beanId) {
        if (StringUtils.isBlank(qlrmc) || StringUtils.isBlank(zjh)) {
            throw new MissingArgumentException("?????????????????????????????????????????????");
        }
        if (StringUtils.isBlank(beanId)) {
            throw new MissingArgumentException("???????????????beanid");
        }
        QyxxRequestDTO qyxxRequestDTO = new QyxxRequestDTO();
        List<QyxxCxywcsRequestDTO> cxywcs = new ArrayList<>();
        QyxxCxywcsRequestDTO qyxxCxywcsRequestDTO = new QyxxCxywcsRequestDTO();
        qyxxCxywcsRequestDTO.setEntname(qlrmc);
        qyxxCxywcsRequestDTO.setUniscid(zjh);
        cxywcs.add(qyxxCxywcsRequestDTO);
        qyxxRequestDTO.setCxywcs(cxywcs);

        LOGGER.info("????????????{}?????????{}",beanId,JSONObject.toJSONString(qyxxRequestDTO));
        Object object = exchangeInterfaceFeignService.sjptRequestInterface(beanId, qyxxRequestDTO);

        if(Objects.isNull(object)){
            throw new AppException("beanid:"+beanId+"??????????????????!");
        }
        LOGGER.info("????????????????????????????????????????????????{}",JSONObject.toJSONString(object));

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));

        LOGGER.info("---????????????????????????:{}",jsonObject);
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
            throw new AppException("????????????????????????");
        }

        return "";
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????????????????
     * @date : 2021/4/21 14:13
     */
    @ResponseBody
    @GetMapping("/xgxx")
    public Object listXgxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("???????????????????????????????????????id");
        }
        return bdcSlJyxxFeignService.listXgxx(gzlslid);
    }


    /**
     * @param yyzzm ???????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????????????????????????????????????????????????????
     * @date : 2021/5/11 10:47
     */

    @ResponseBody
    @GetMapping("/importYyzz")
    public void importYyzz(@NotBlank(message = "???????????????????????????") String yyzzm, @NotBlank(message = "???????????????id????????????") String gzlslid, String djxl) throws IOException {
        bdcSlQlrFeignService.importYyzz(yyzzm, gzlslid, djxl);
    }

    /**
     * @param qllx ????????????
     * @param qlrlb ???????????????
     * @return ???????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????????????????
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
      * @description ???????????????????????????
      */
    @ResponseBody
    @PostMapping("/hxywsj")
    public void hxYwsj(String gzlslid) {
        //?????????????????????
        try {
            bdcYwsjHxFeignService.saveBdcYwsj(gzlslid);
        } catch (Exception e) {
            LOGGER.error(MSG_HXFAIL, e);
        }

    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????id????????????????????????????????????id??????????????????????????????
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
     * ?????????????????????
     *
     * @param xmid ??????ID
     * @param qllx ????????????
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

        //???????????????????????????????????????????????????
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
     * @description ?????????????????????????????????
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
     * @description ??????????????????
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
     * ??????????????????????????????
     * @param csjZzxxQO
     * @return
     */
    @PostMapping("/sfm/cx")
    @ResponseBody
    public List<SfmLiscenseInfoDTO> scanSfmCx(@RequestBody CsjZzxxQO csjZzxxQO) {
        return bdcSlQlrFeignService.querCsjZzxxBySfmCx(csjZzxxQO);
    }

    /**
     * ???????????????????????????????????????
     * @param csjZzxxQO
     * @return
     */
    @PostMapping("/sfm/addqlr")
    @ResponseBody
    public void scanSfmAdd(@RequestBody CsjZzxxQO csjZzxxQO) {
        bdcSlQlrFeignService.querCsjZzxxBySfmAdd(csjZzxxQO);
    }

    /**
     * ???????????????????????????????????????
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
     * @description ???????????????????????????????????????
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
                LOGGER.warn("??????????????????????????????????????????????????????{}", paramMap);
                return exchangeInterfaceFeignService.requestInterface("mk_sfzxx_rh", paramMap);
            }
        }
        return null;
    }

    /**
     * ???????????????????????????
     * @param gzlslid ???????????????ID
     * @param xmid  ??????ID
     * @param zjh ?????????
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
                bdcCzrzDO.setCzyy("?????????????????????????????????" + zjh);
                bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
            }
        }
    }
}


