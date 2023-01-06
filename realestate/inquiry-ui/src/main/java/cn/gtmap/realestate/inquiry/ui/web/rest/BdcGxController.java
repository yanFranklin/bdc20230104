package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQlrGroupDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.cbirc.financialquery.CbircFinancialQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.cbirc.financialquery.CbircFinancialQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.baseInfo.PoliceQueryBaseInfoRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.idcheck.PoliceCheckIdRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.scopsr.organquery.ScopsrOrganQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.scopsr.organquery.ScopsrOrganQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionapply.SupremeCourtDecisionApplyRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionresponse.SupremeCourtDecisionResponseRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionresponse.SupremeCourtDecisionResponseResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment.*;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.bzfwhhxx.request.BzfwhhxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.csyxzm.request.CsyxzmRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.dmxxcx.request.DmxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.dqjg.request.QDqjgRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.gasfhc.request.GaSfhcRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxxhygr.request.HyxxhygrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxxhysf.request.HyxxhysfRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.jjhfr.request.JjhfrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.mbfqydjzs.request.MbfqydjzsrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.qyxx.request.QyxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shttfrdjzs.request.ShttfrdjzsdjzsrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shzztyxx.request.ShzztyxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.swyxzm.request.SwyxzmRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.sydjxxcx.request.SydjxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.sydjxxjy.request.SydjxxjyRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.wjwswyxzm.request.WjwswyxzmRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.wjwswyxzmjy.request.WjwswyxzmjyRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.shiji.BdcShijiZzxzDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcGxjkPdfDTO;
import cn.gtmap.realestate.common.core.enums.FileContentTypeEnum;
import cn.gtmap.realestate.common.core.enums.GxJkEnum;
import cn.gtmap.realestate.common.core.enums.ShijgxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.config.BdcTsywPzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.GxCxywcsQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcTsywPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcZdSsjgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcGxjkFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.groovy.XmlUtils;
import cn.gtmap.realestate.common.util.office.OfficeUtil;
import cn.gtmap.realestate.inquiry.ui.config.SjgxjkmlConfig;
import cn.gtmap.realestate.inquiry.ui.core.annotation.GxInterfaceLog;
import cn.gtmap.realestate.inquiry.ui.core.dto.gx.ggxt.AttachData;
import cn.gtmap.realestate.inquiry.ui.core.dto.gx.ggxt.AttachList;
import cn.gtmap.realestate.inquiry.ui.core.dto.gx.ggxt.GgFjxx;
import cn.gtmap.realestate.inquiry.ui.core.dto.gx.ythsp.Files;
import cn.gtmap.realestate.inquiry.ui.core.dto.gx.ythsp.YthxxResponse;
import cn.gtmap.realestate.inquiry.ui.util.IpUtils;
import cn.gtmap.realestate.inquiry.ui.util.ZipUtil;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import org.apache.avalon.framework.parameters.ParameterException;
import org.apache.axis.utils.XMLUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:zhongjinpe@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/6 09:40
 * @description 南通大市共享接口
 */
@RestController
@RequestMapping(value = "/rest/v1.0/gx")
public class BdcGxController extends BaseController {

    @Autowired
    private NaturalResourcesFeignService naturalResourcesFeignService;

    @Autowired
    private ProvincialPublicSecurityDepartmentFeignService provincialPublicSecurityDepartmentFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    ExchangeInterfaceRestService exchangeInterfaceRestService;

    @Autowired
    ProvincialDataSharingFeignService provincialDataSharingFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcCfxxFeignService bdcCfxxFeignService;

    @Autowired
    SjgxjkmlConfig sjgxjkmlConfig;

    @Autowired
    private PdfController pdfController;

    @Autowired
    private BdcGxjkFeignService bdcGxjkFeignService;

    @Autowired
    BdcTsywPzFeignService bdcTsywPzFeignService;

    @Autowired
    private BdcShijiFeignService bdcShijiFeignService;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BdcZdSsjgxFeignService bdcZdSsjgxFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    StorageClientMatcher storageClient;

    @Value("${html.version:}")
    private String version;

    private static final String MLPZ_PREFIX = "gxjkml.";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 地名信息行政区代码
     */
    @Value("${sjgx.dmxx.xzqdm:}")
    private String dmxzqdm;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 地名信息地名名称
     */
    @Value("${sjgx.dmxx.dmmc:}")
    private String dmmc;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  打印模板地址
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * 工改平台附件上传目录对应关系
     */
    @Value("#{${ggpt.fj.mldz:{'附件名称': '附件要放置的目录名称'}}}")
    private Map<String, String> ggptFileDir;

    /**
     * 工改平台附件上传目录对应关系
     */
    @Value("${gx.url.dldz:}")
    private String dldz;

    @Value("${gx.url.ydgh:}")
    private String ydghurl;

    @Value("${gx.url.tdcr:}")
    private String tdcrurl;


    @Value("${dsjj.gzajjbxx.mlmc:}")
    private String gzajmlmc;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  2.1 公安部-身份核查
     */
    @PostMapping("/naturalResources/gasfhc")
    @GxInterfaceLog(interfaceCode = "shengjjk_gasfhc")
    public Object dmxxCx(@RequestBody GaSfhcRequestDTO info){
        if(info != null &&StringUtils.isNotBlank(info.getGzlslid()) &&StringUtils.isBlank(info.getGzldyid())){
            List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(info.getGzlslid());
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                BeanUtils.copyProperties(bdcXmDTOList.get(0),info);
            }
        }
        Object response =exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_gasfhc", info);
        return response;

    }

    /**
     * 2.2 民政部-地名信息查询接口说明
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:45 2020/11/12
     */
    @PostMapping("/naturalResources/dmxxcx")
    @GxInterfaceLog(interfaceCode = "shengjjk_dmxx")
    public Object dmxxCx(@RequestBody DmxxcxRequestDTO info) {
        Object response;
        List dmxxList = null;

        if (null != info && CollectionUtils.isNotEmpty(info.getCxywcs())) {
            if(StringUtils.isNotBlank(version) &&CommonUtil.indexOfStrs(CommonConstantUtils.SYSTEM_VERSION_AH, version)){
                response = exchangeInterfaceFeignService.requestInterface("ahst_dmxxcx", info.getCxywcs().get(0));
                if (response != null) {
                    Map<String, Object> data = (Map<String, Object>) response;
                    dmxxList = (List) data.get("rows");
                }
            }else{
                response = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_dmxxcx", info);
                if (response != null) {
                    JSONObject content = JSONObject.parseObject(JSONObject.toJSONString(response));
                    if (content.getJSONObject("data") != null) {
                        dmxxList = content.getJSONObject("data").getJSONArray("rows");
                    }
                }
            }
        }
        if(null != info &&info.isLoadpage()) {
            Pageable pageable = new PageRequest(1, 10, null);
            if (CollectionUtils.isNotEmpty(dmxxList)) {
                Page page = PageUtils.listToPageWithTotal(dmxxList, pageable, dmxxList.size());
                return addLayUiCode(page);
            }
            Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
            return addLayUiCode(page);
        }else{
            return dmxxList;
        }
    }

    /**
     * 2.3民政部-婚姻登记信息查询服务接口
     *
     * @param info
     * @return String
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @PostMapping("/naturalResources/civil/marriageQuery")
    @GxInterfaceLog(interfaceCode = "shengjjk_hydjxx")
    public Object marriageQuery(@RequestBody CivilMarriageQueryRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        if (info != null && CollectionUtils.isNotEmpty(info.getParamDTOList())) {
            if (StringUtils.isNotBlank(version)) {
                if (StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_BB)) {
                    Object response = exchangeInterfaceFeignService.requestInterface("ahst_hydjxxcx", info.getParamDTOList().get(0));
                    if (response != null) {
                        Map<String, Object> data = (Map<String, Object>) response;
                        List hyxxList = (List) data.get("rows");
                        if (CollectionUtils.isNotEmpty(hyxxList)) {
                            if(info.isLoadpage()) {
                                Page page = PageUtils.listToPageWithTotal(hyxxList, pageable, hyxxList.size());
                                return addLayUiCode(page);
                            }else{
                                return hyxxList;
                            }
                        }
                    }
                }else if (StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_HF)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("slbh", info.getSlbh());
                    jsonObject.put("zjh", info.getParamDTOList().get(0).getCertNum());
                    jsonObject.put("qlrmc", info.getParamDTOList().get(0).getName());
                    Object response = exchangeInterfaceRestService.requestInterface("bjjk_hydjxx", jsonObject);
                    List dataList = (List)response;
                    if(info.isLoadpage()) {
                        Page page = PageUtils.listToPageWithTotal(dataList, pageable, dataList.size());
                        return addLayUiCode(page);
                    }else{
                        return dataList;
                    }
                }else{
                    CivilMarriageQueryResponseDTO civilMarriageQueryResponseDTO = naturalResourcesFeignService.marriageQuery(info);
                    if (civilMarriageQueryResponseDTO != null && CollectionUtils.isNotEmpty(civilMarriageQueryResponseDTO.getDataDTOList())) {
                        if(info.isLoadpage()) {
                            Page page = PageUtils.listToPageWithTotal(civilMarriageQueryResponseDTO.getDataDTOList(), pageable, civilMarriageQueryResponseDTO.getCount());
                            return addLayUiCode(page);
                        }else{
                            return civilMarriageQueryResponseDTO.getDataDTOList();
                        }
                    }
                }
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
            return addLayUiCode(page);
        }else{
            return Collections.emptyList();
        }
    }

    /**
     * 2.4 民政部-社会组织统一社会信用代码信息查询服务接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:11 2020/11/12
     */
    @PostMapping("/naturalResources/shzztyxxcx")
    @GxInterfaceLog(interfaceCode = "shengjjk_shxydmxx")
    public Object shzztyxxCx(@RequestBody ShzztyxxRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        Object response;
        List shzztyxxList = null;
        if (StringUtils.isNotBlank(version) && null != info && CollectionUtils.isNotEmpty(info.getCxywcs())) {
            if (StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_BB)) {
                response = exchangeInterfaceFeignService.requestInterface("ahst_shzzxxcx", info.getCxywcs().get(0));
                if (response != null) {
                    Map<String, Object> data = (Map<String, Object>) response;
                    shzztyxxList = (List) data.get("rows");
                }
            }else if (StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_HF)){
                //构造bjjk_shzz所需参数
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("slbh", "111");
                jsonObject.put("zjh", info.getCxywcs().get(0).getTyshxydm());
                jsonObject.put("qlrmc", info.getCxywcs().get(0).getName());
                jsonObject.put("type", info.getCxywcs().get(0).getSearch_type());
                Object result = exchangeInterfaceRestService.requestInterface("bjjk_shzz", jsonObject);
                shzztyxxList = (List)result;
            }else{
                response = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_shzztyxxcx", info);
                if (response != null) {
                    JSONObject content = JSONObject.parseObject(JSONObject.toJSONString(response));
                    if (content.getJSONObject("data") != null) {
                        shzztyxxList = content.getJSONObject("data").getJSONArray("rows");
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(shzztyxxList)) {
                if(info.isLoadpage()) {
                    Page page = PageUtils.listToPageWithTotal(shzztyxxList, pageable, shzztyxxList.size());
                    return addLayUiCode(page);
                }else{
                    return shzztyxxList;
                }
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
            return addLayUiCode(page);
        }else{
            return shzztyxxList;
        }
    }

    /**
     * 2.5银保监会-金融许可证查询接口
     *
     * @param info
     * @return Object
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @PostMapping("/naturalResources/cbirc/financialQuery")
    @GxInterfaceLog(interfaceCode = "shengjjk_jrxkzcx")
    public Object financialQuery(@RequestBody CbircFinancialQueryRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        if (StringUtils.isNotBlank(version) && info != null && CollectionUtils.isNotEmpty(info.getParamDTOS())) {
            if (StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_BB)) {
                Object response = exchangeInterfaceFeignService.requestInterface("ahst_jrxkzcx", info.getParamDTOS().get(0));
                if (response != null) {
                    List jrxkzxxList = new ArrayList();
                    jrxkzxxList.add(response);
                    if(info.isLoadpage()) {
                        Page page = PageUtils.listToPageWithTotal(jrxkzxxList, pageable, jrxkzxxList.size());
                        return addLayUiCode(page);
                    }else{
                        return jrxkzxxList;
                    }
                }
            }else if (StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_HF)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("slbh", info.getSlbh());
                jsonObject.put("type", info.getParamDTOS().get(0).getTypeId());
                jsonObject.put("zjh", info.getParamDTOS().get(0).getCertCode());
                Object result = exchangeInterfaceRestService.requestInterface("bjjk_jrxkz", jsonObject);
                if(result != null){
                    List resultList = new ArrayList();
                    resultList.add(result);
                    if(info.isLoadpage()) {
                        Page page = PageUtils.listToPageWithTotal(resultList, pageable, resultList.size());
                        return addLayUiCode(page);
                    }else{
                        return resultList;
                    }
                }
            }else{
                CbircFinancialQueryResponseDTO cbircFinancialQueryResponseDTO = naturalResourcesFeignService.financialQuery(info);
                if (cbircFinancialQueryResponseDTO != null) {
                    List dataList = new ArrayList<>();
                    dataList.add(cbircFinancialQueryResponseDTO);
                    if(info.isLoadpage()) {
                        Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
                        return addLayUiCode(page);
                    }else{
                        return dataList;
                    }
                }
            }

        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
            return addLayUiCode(page);
        }else{
            return Collections.emptyList();
        }
    }

    /**
     * 2.6中编办-事业单位登记信息（含机关、群团信息）查询接口
     *
     * @param info
     * @return Object
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @PostMapping("/naturalResources/scopsr/organQuery")
    @GxInterfaceLog(interfaceCode = "shengjjk_sydwdjxx")
    public Object organQuery(@RequestBody ScopsrOrganQueryRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        if (StringUtils.isNotBlank(version) && info != null && CollectionUtils.isNotEmpty(info.getParamDTOList())) {

            if (StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_BB)) {
                Object response = exchangeInterfaceFeignService.requestInterface("ahst_sydwxxcx", info.getParamDTOList().get(0));
                if (response != null) {
                    Map<String, Object> data = (Map<String, Object>) response;
                    List sydwxxList = (List) data.get("rows");
                    if (CollectionUtils.isNotEmpty(sydwxxList)) {
                        if(info.isLoadpage()) {
                            Page page = PageUtils.listToPageWithTotal(sydwxxList, pageable, sydwxxList.size());
                            return addLayUiCode(page);
                        }else{
                            return sydwxxList;
                        }
                    }
                }
            }else if (StringUtils.equals(version, CommonConstantUtils.SYSTEM_VERSION_HF)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("slbh", info.getSlbh());
                jsonObject.put("zjh", info.getParamDTOList().get(0).getTyshxydm());
                jsonObject.put("qlrmc", info.getParamDTOList().get(0).getName());
                Object result = exchangeInterfaceRestService.requestInterface("bjjk_zbb", jsonObject);
                if(info.isLoadpage()) {
                    Page page = PageUtils.listToPageWithTotal((List) result, pageable, 1);
                    return addLayUiCode(page);
                }else{
                    return result;
                }
            }else{
                ScopsrOrganQueryResponseDTO scopsrOrganQueryResponseDTO = naturalResourcesFeignService.organQuery(info);
                if (scopsrOrganQueryResponseDTO != null && CollectionUtils.isNotEmpty(scopsrOrganQueryResponseDTO.getDataDTOS())) {
                    if(info.isLoadpage()) {
                        Page page = PageUtils.listToPageWithTotal(scopsrOrganQueryResponseDTO.getDataDTOS(), pageable, scopsrOrganQueryResponseDTO.getDataDTOS().size());
                        return addLayUiCode(page);
                    }else{
                        return scopsrOrganQueryResponseDTO.getDataDTOS();
                    }
                }
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
            return addLayUiCode(page);
        }else{
            return  Collections.emptyList();
        }
    }

    /**
     * 2.7最高法-司法判决信息查询申请接口
     *
     * @return Object
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @PostMapping("/naturalResources/supremecourt/decisionApply")
    @GxInterfaceLog(interfaceCode = "shengjjk_sfpjxx")
    public Object decisionApply(@RequestBody SupremeCourtDecisionApplyRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        if(StringUtils.isNotBlank(version)){
            if(CommonUtil.indexOfStrs(CommonConstantUtils.SYSTEM_VERSION_AH, version)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("slbh", info.getSlbh());
                List cxnr = new ArrayList();
                info.getParamDTOList().stream().forEach(paramDTO ->{
                    JSONObject qlrxx = new JSONObject();
                    qlrxx.put("cxbm",paramDTO.getCxbm());
                    qlrxx.put("cxbmbh", paramDTO.getCxbmbh());
                    qlrxx.put("cxr", paramDTO.getCxr());
                    qlrxx.put("cxrbh", paramDTO.getCxrbh());
                    qlrxx.put("ajbh", paramDTO.getAjbh());
                    cxnr.add(qlrxx);
                });
                JSONObject data = new JSONObject();
                data.put("data", cxnr);
                jsonObject.put("datas", data);
                Object response = exchangeInterfaceRestService.requestInterface("bjjk_sfpjcx", jsonObject);
                return response;//要在确认下cxqqdh的格式
            }else{
                SupremeCourtDecisionResponseResponseDTO supremeCourtDecisionResponseResponseDTO = naturalResourcesFeignService.decisionApply(info);
                if (supremeCourtDecisionResponseResponseDTO != null) {
                    List dataList = new ArrayList<>();
                    dataList.add(supremeCourtDecisionResponseResponseDTO);
                    if (info.isLoadpage()) {
                        Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
                        return addLayUiCode(page);
                    } else {
                        return dataList;
                    }
                }
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
            return addLayUiCode(page);
        }else{
            return Collections.emptyList();
        }
    }

    /**
     * 2.8最高法-司法判决信息结果反馈接口
     *
     * @param info
     * @return Object
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @PostMapping("/naturalResources/supremecourt/decisionResponse")
    @GxInterfaceLog(interfaceCode = "shengjjk_sfpjxxfk")
    public Object decisionResponse(@RequestBody SupremeCourtDecisionResponseRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        if(StringUtils.isNotBlank(version)) {
            if(CommonUtil.indexOfStrs(CommonConstantUtils.SYSTEM_VERSION_AH, version)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("slbh", info.getSlbh());
                jsonObject.put("cxqqdh", info.getCxqqdhList());//确认下格式
                Object response = exchangeInterfaceRestService.requestInterface("bjjk_sfpjcxfk", jsonObject);
                return response;
            }else{
                SupremeCourtDecisionResponseResponseDTO supremeCourtDecisionResponseResponseDTO = naturalResourcesFeignService.decisionResponse(info);
                if (supremeCourtDecisionResponseResponseDTO != null) {
                    List dataList = new ArrayList<>();
                    dataList.add(supremeCourtDecisionResponseResponseDTO);
                    if(info.isLoadpage()) {
                        Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
                        return addLayUiCode(page);
                    }else{
                        return dataList;
                    }
                }
            }
        }

        if(info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
            return addLayUiCode(page);
        }else{
            return Collections.emptyList();
        }
    }

    /**
     * 3.1 卫健委-出生医学证明查询接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/csyxzm")
    @GxInterfaceLog(interfaceCode = "shengjjk_csyxzm")
    public Object csyxzmCx(@RequestBody CsyxzmRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List data = new ArrayList<>();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_csyxzm", info);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---卫健委-出生医学证明查询接口:{}",jsonObject);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        JSONObject headJsonObject = jsonObject.getJSONObject("head");

        if(!"0".equals(headJsonObject.getString("status"))){
            throw new AppException(headJsonObject.getString("msg"));
        }
        if(dataJsonObject.get("cxjg") instanceof  String){
            String cxjg = dataJsonObject.getString("cxjg");
            data.add(cxjg);
        }else{
            JSONArray cxjgJsonArray = dataJsonObject.getJSONArray("cxjg");
            if(CollectionUtils.isNotEmpty(cxjgJsonArray)) {
                for (int i = 0; i < cxjgJsonArray.size(); i++) {
                    data.add(JSON.parse(cxjgJsonArray.getString(i)));
                }
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        }else{
            return data;
        }
    }

    /**
     * 3.2 卫健委-死亡医学证明查询接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/swyxzm")
    @GxInterfaceLog(interfaceCode = "shengjjk_swyxzm")
    public Object swyxzmCx(@RequestBody SwyxzmRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List data = new ArrayList<>();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_swyxzm", info);

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---卫健委-死亡医学证明查询接口:{}",jsonObject);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        JSONObject headJsonObject = jsonObject.getJSONObject("head");

        if(!"0".equals(headJsonObject.getString("status"))){
            throw new AppException("死亡医学证明接口返回信息:"+headJsonObject.getString("msg"));
        }

        if(dataJsonObject.get("cxjg") instanceof  String){
            String cxjg = dataJsonObject.getString("cxjg");
            data.add(cxjg);
        }else{
            JSONArray cxjgJsonArray = dataJsonObject.getJSONArray("cxjg");
            if(CollectionUtils.isNotEmpty(cxjgJsonArray)) {
                for (int i = 0; i < cxjgJsonArray.size(); i++) {
                    data.add(JSON.parse(cxjgJsonArray.getString(i)));
                }
            }
        }

        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        }else{
            return data;
        }
    }


    /**
     * 3.3 民政部-基金会法人登记证书查询接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/jjhfrdjzs")
    @GxInterfaceLog(interfaceCode = "shengjjk_jjhfrdjzs")
    public Object jjhfrdjzsCx(@RequestBody JjhfrRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List<Object> data = new ArrayList<>();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_jjhfrdjzs", info);
        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_jjhfrdjzs响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---民政部-基金会法人登记证书查询接口返回:{}",jsonObject);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        JSONObject headJsonObject = jsonObject.getJSONObject("head");

        if(!"0".equals(headJsonObject.getString("status"))){
            throw new AppException(headJsonObject.getString("msg"));
        }

        JSONObject cxjgJsonObject = dataJsonObject.getJSONObject("cxjg");
        JSONArray dataJsonArray = cxjgJsonObject.getJSONArray("data");
        if(CollectionUtils.isNotEmpty(dataJsonArray)) {
            for (int i = 0; i < dataJsonArray.size(); i++) {
                data.add(JSON.parse(dataJsonArray.getString(i)));
            }
        }

        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        }else{
            return data;
        }
    }


    /**
     * 3.4 民政部-民办非企业单位登记证书查询接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/mbfqydjzs")
    @GxInterfaceLog(interfaceCode = "shengjjk_mbfqydwdjzs")
    public Object mbfqydjzsCx(@RequestBody MbfqydjzsrRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List data = new ArrayList();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_mbfqydjzs", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_mbfqydjzs响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---民政部-民办非企业单位登记证书查询接口:{}",jsonObject);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        JSONObject headJsonObject = jsonObject.getJSONObject("head");

        if(!"0".equals(headJsonObject.getString("status"))){
            throw new AppException(headJsonObject.getString("msg"));
        }

        JSONObject cxjgJsonObject = dataJsonObject.getJSONObject("cxjg");
        JSONArray dataJsonArray = cxjgJsonObject.getJSONArray("data");
        if(CollectionUtils.isNotEmpty(dataJsonArray)) {
            for (int i = 0; i < dataJsonArray.size(); i++) {
                data.add(JSON.parse(dataJsonArray.getString(i)));
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        }else{
            return data;
        }
    }


    /**
     * 3.5 民政部-社会团体法人登记证书查询接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/shttfrdjzs")
    @GxInterfaceLog(interfaceCode = "shengjjk_shttfrdjzs")
    public Object shttfrdjzsCx(@RequestBody ShttfrdjzsdjzsrRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List data = new ArrayList<>();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_shttfrdjzs", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_shttfrdjzs响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---民政部-社会团体法人登记证书查询接口:{}",jsonObject);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        JSONObject headJsonObject = jsonObject.getJSONObject("head");

        if(!"0".equals(headJsonObject.getString("status"))){
            throw new AppException(headJsonObject.getString("msg"));
        }

        JSONObject cxjgJsonObject = dataJsonObject.getJSONObject("cxjg");
        JSONArray dataJsonArray = cxjgJsonObject.getJSONArray("data");
        if(CollectionUtils.isNotEmpty(dataJsonArray)) {
            for (int i = 0; i < dataJsonArray.size(); i++) {
                data.add(JSON.parse(dataJsonArray.getString(i)));
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        }else{
            return data;
        }
    }

    /**
     * 3.6 民政部-婚姻登记信息核验（个人）接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/hyxxhygr")
    @GxInterfaceLog(interfaceCode = "shengjjk_hyxxhygr")
    public Object csyxzmCx(@RequestBody HyxxhygrRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List data = new ArrayList<>();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_hyxxhygr", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_hyxxhygr响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---民政部-婚姻登记信息核验（个人）接口:{}",jsonObject);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        JSONObject headJsonObject = jsonObject.getJSONObject("head");

        if(!"0".equals(headJsonObject.getString("status"))){
            throw new AppException(headJsonObject.getString("msg"));
        }

        JSONObject cxjgJsonObject = dataJsonObject.getJSONObject("cxjg");
        Object result = cxjgJsonObject.get("result");
        data.add(result);
        if(info !=null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        }else{
            return data;
        }
    }

    /**
     * 3.7 民政部-婚姻登记信息核验（双方）接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/hyxxhysf")
    @GxInterfaceLog(interfaceCode = "shengjjk_hyxxhysf")
    public Object hyxxhysfCx(@RequestBody HyxxhysfRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List data = new ArrayList();
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_hyxxhysf", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_hyxxhysf响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---民政部-婚姻登记信息核验（双方）接口:{}",jsonObject);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        JSONObject headJsonObject = jsonObject.getJSONObject("head");

        if(!"0".equals(headJsonObject.getString("status"))){
            throw new AppException(headJsonObject.getString("msg"));
        }

        JSONObject cxjgJsonObject = dataJsonObject.getJSONObject("cxjg");
        Object result = cxjgJsonObject.get("result");
        data.add(result);

        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        }else{
            return data;
        }
    }

    /**
     * 3.8 市场监管总局-企业基本信息查询接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/qyjbxx")
    @GxInterfaceLog(interfaceCode = "shengjjk_qyjbxx")
    public Object qyxxCx(@RequestBody QyxxRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List dataList = new ArrayList<>();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_qyxx", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_qyxx响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---企业基本信息接口:{}",jsonObject);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        JSONObject headJsonObject = jsonObject.getJSONObject("head");

        if(!"0".equals(headJsonObject.getString("status"))){
            throw new AppException(headJsonObject.getString("msg"));
        }


        JSONArray cxjgJsonArray = dataJsonObject.getJSONArray("cxjg");
        if(CollectionUtils.isNotEmpty(cxjgJsonArray)) {
            for (int i = 0; i < cxjgJsonArray.size(); i++) {
                dataList.add(JSON.parse(cxjgJsonArray.getString(i)));
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
            return addLayUiCode(page);
        }else{
            return dataList;
        }

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  3.9市场监管总局-企业基本信息验证接口
     */
    @PostMapping("/naturalResources/qyjbxxyz")
    @GxInterfaceLog(interfaceCode = "shengjjk_qyjbxxyz")
    public Object qyxxYz(@RequestBody ZwSamrEnterpriseCheckRequestDTO info) {
        List dataList = new ArrayList<>();
        ZwSamrEnterpriseCheckResponseDTO zwSamrEnterpriseCheckResponseDTO =provincialDataSharingFeignService.enterpriseCheck(info);
        if(Objects.isNull(zwSamrEnterpriseCheckResponseDTO)){
            throw new AppException("企业基本信息验证响应结果为空!");
        }

        LOGGER.info("---企业基本信息接口:{}",zwSamrEnterpriseCheckResponseDTO);
        if(zwSamrEnterpriseCheckResponseDTO != null){
            dataList.add(zwSamrEnterpriseCheckResponseDTO);
            return dataList;
        }
        return Collections.emptyList();

    }

    /**
     * 3.10 中编办-党群机关信息查询接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/dqjg")
    @GxInterfaceLog(interfaceCode = "shengjjk_dqjg")
    public Object dqjgCx(@RequestBody QDqjgRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List dataList = new ArrayList<>();

        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_dqjg", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_dqjg响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---中编办-党群机关信息查询接口:{}",jsonObject);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        JSONObject headJsonObject = jsonObject.getJSONObject("head");

        if(!"0".equals(headJsonObject.getString("status"))){
            throw new AppException(headJsonObject.getString("msg"));
        }

        JSONObject cxjgJsonObject = dataJsonObject.getJSONObject("cxjg");
        JSONArray dataJsonArray = cxjgJsonObject.getJSONArray("datalist");
        if(CollectionUtils.isNotEmpty(dataJsonArray)) {
            for (int i = 0; i < dataJsonArray.size(); i++) {
                dataList.add(JSON.parse(dataJsonArray.getString(i)));
            }
        }

        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
            return addLayUiCode(page);
        }else{
            return dataList;
        }
    }

    /**
     * 3.11 中编办-事业单位信息查询接口
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/sydw")
    @GxInterfaceLog(interfaceCode = "shengjjk_sydwcx")
    public Object sydwCx(@RequestBody QDqjgRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List dataList = new ArrayList<>();
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_sydw", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_sydw响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---中编办-事业单位信息查询接口:{}",jsonObject);

        if(!"查询成功".equals(jsonObject.getString("msg"))){
            throw new AppException("省级接口返回信息:"+jsonObject.getString("msg"));
        }

        JSONArray dataJsonArray = jsonObject.getJSONArray("data");
        if(CollectionUtils.isNotEmpty(dataJsonArray)) {
            for (int i = 0; i < dataJsonArray.size(); i++) {
                dataList.add(JSON.parse(dataJsonArray.getString(i)));
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
            return addLayUiCode(page);
        }else{
            return dataList;
        }
    }

    /**
     * 3.12 公安部-人口库基准信息查询接口
     *
     * @param
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @Date 14:45 2021/08/05
     */
    @PostMapping("/naturalResources/baseinfo")
    public Object baseInfoCx(@RequestBody List<PoliceQueryBaseInfoRequestDTO> info) {
        List baseInfoList = null;
        if (null != info && CollectionUtils.isNotEmpty(info)) {
            CommonResponse<JSONArray> baseInfo = naturalResourcesFeignService.baseInfo(info);
//            String ss = "{\"success\":true,\"data\":[{\"csd_gjdqm\":\"156\",\"csd_ssxdqm\":\"320803\",\"csrq\":\"1985-06-18\",\"gmsfzh\":\"320198506185815\",\"gmsfzh_ppddm\":\"1\",\"mzdm\":\"01\",\"swbs\":\"0\",\"xbdm\":\"1\",\"xm\":\"朱军\",\"xm_ppddm\":\"1\"}]}";
//            CommonResponse<JSONArray> baseInfo = JSON.parseObject(ss,CommonResponse.class);
            if (baseInfo != null && baseInfo.isSuccess()) {
                baseInfoList = baseInfo.getData();
            }
        }
        Pageable pageable = new PageRequest(1, 10, null);
        if (CollectionUtils.isNotEmpty(baseInfoList)) {
            Page page = PageUtils.listToPageWithTotal(baseInfoList, pageable, baseInfoList.size());
            return addLayUiCode(page);
        }
        Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
        return addLayUiCode(page);
    }

    /**
     * 3.13 民政部-地名信息查询接口说明
     *
     * @param
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @Date 14:45 2021/08/05
     */
    @PostMapping("/naturalResources/id/check")
    public Object idCheck(@RequestBody List<PoliceCheckIdRequestDTO> info) {
        List baseInfoList = null;
        if (null != info && CollectionUtils.isNotEmpty(info)) {
            CommonResponse<JSONArray> baseInfo = naturalResourcesFeignService.idCheck(info);
//            String ss = "{\"success\":true,\"data\":[{\"gmsfzh\":\"32098506185815\",\"gmsfzh_ppddm\":\"1\",\"swbs\":\"0\",\"xm\":\"朱军\",\"xm_ppddm\":\"1\"}]}";
//            CommonResponse<JSONArray> baseInfo = JSON.parseObject(ss,CommonResponse.class);
            if (baseInfo != null && baseInfo.isSuccess()) {
                baseInfoList = baseInfo.getData();
            }
        }
        Pageable pageable = new PageRequest(1, 10, null);
        if (CollectionUtils.isNotEmpty(baseInfoList)) {
            Page page = PageUtils.listToPageWithTotal(baseInfoList, pageable, baseInfoList.size());
            return addLayUiCode(page);
        }
        Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
        return addLayUiCode(page);
    }

    /**
     * 3.14 民政部-收养登记信息
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">wyh</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/sydjxxcx")
    @GxInterfaceLog(interfaceCode = "shengjjk_sydjxxcx")
    public Object sydjxxcx(@RequestBody SydjxxcxRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List dataList = new ArrayList<>();
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_sydjxxcx", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_sydjxxcx响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---民政部-收养登记信息:{}",jsonObject);

        if(!"查询成功".equals(jsonObject.getJSONObject("head").getString("msg"))){
            throw new AppException("省级接口返回信息:"+jsonObject.getJSONObject("head").getString("msg"));
        }

        JSONArray dataJsonArray = jsonObject.getJSONObject("data").getJSONArray("cxjg");
        if(CollectionUtils.isNotEmpty(dataJsonArray)) {
            for (int i = 0; i < dataJsonArray.size(); i++) {
                dataList.add(JSON.parse(dataJsonArray.getString(i)));
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
            return addLayUiCode(page);
        }else{
            return dataList;
        }
    }

    /**
     * 3.15 民政部-收养登记信息校验
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/sydjxxjy")
    @GxInterfaceLog(interfaceCode = "shengjjk_sydjxxjy")
    public Object sydjxxjy(@RequestBody SydjxxjyRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List dataList = new ArrayList<>();
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_sydjxxjy", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_sydjxxjy响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---民政部-收养登记信息校验:{}",jsonObject);

        if(!"查询成功".equals(jsonObject.getJSONObject("head").getString("msg"))){
            throw new AppException("省级接口返回信息:"+jsonObject.getJSONObject("head").getString("msg"));
        }

        JSONObject dataJSONObject = jsonObject.getJSONObject("data");
        if(Objects.nonNull(dataJSONObject)) {
            dataList.add(dataJSONObject);
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
            return addLayUiCode(page);
        }else{
            return dataList;
        }
    }

    /**
     * 3.16 民政部-殡葬服务火化信息
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/bzfwhhxx")
    @GxInterfaceLog(interfaceCode = "shengjjk_bzfwhhxx")
    public Object bzfwhhxx(@RequestBody BzfwhhxxRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List dataList = new ArrayList<>();
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_bzfwhhxx", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_bzfwhhxx响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---民政部-殡葬服务火化信息:{}",jsonObject);

        if(!"查询成功".equals(jsonObject.getJSONObject("head").getString("msg"))){
            throw new AppException("省级接口返回信息:"+jsonObject.getJSONObject("head").getString("msg"));
        }

        JSONArray dataJsonArray = jsonObject.getJSONObject("data").getJSONArray("cxjg");
        if(CollectionUtils.isNotEmpty(dataJsonArray)) {
            for (int i = 0; i < dataJsonArray.size(); i++) {
                dataList.add(JSON.parse(dataJsonArray.getString(i)));
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
            return addLayUiCode(page);
        }else{
            return dataList;
        }
    }


    /**
     * 3.17 省卫健委-死亡医学证明
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/wjwswyxzm")
    @GxInterfaceLog(interfaceCode = "shengjjk_wjwswyxzm")
    public Object wjwswyxzm(@RequestBody WjwswyxzmRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List dataList = new ArrayList<>();
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_wjwswyxzm", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_wjwswyxzm响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---省卫健委-死亡医学证明:{}",jsonObject);

        if(!"查询成功".equals(jsonObject.getJSONObject("head").getString("msg"))){
            throw new AppException("省级接口返回信息:"+jsonObject.getJSONObject("head").getString("msg"));
        }

        JSONArray dataJsonArray = jsonObject.getJSONObject("data").getJSONArray("cxjg");
        if(CollectionUtils.isNotEmpty(dataJsonArray)) {
            for (int i = 0; i < dataJsonArray.size(); i++) {
                dataList.add(JSON.parse(dataJsonArray.getString(i)));
            }
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
            return addLayUiCode(page);
        }else{
            return dataList;
        }
    }

    /**
     * 3.18 省卫健委-死亡医学证明校验
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:27 2020/11/12
     */
    @PostMapping("/naturalResources/wjwswyxzmjy")
    @GxInterfaceLog(interfaceCode = "shengjjk_wjwswyxzmjy")
    public Object sydwCx(@RequestBody WjwswyxzmjyRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List dataList = new ArrayList<>();
        Object object = exchangeInterfaceFeignService.sjptRequestInterface("xgbmcx_wjwswyxzmjy", info);

        if(Objects.isNull(object)){
            throw new AppException("beanid:xgbmcx_wjwswyxzmjy响应结果为空!");
        }

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        LOGGER.info("---省卫健委-死亡医学证明校验:{}",jsonObject);

        if(!"查询成功".equals(jsonObject.getJSONObject("head").getString("msg"))){
            throw new AppException("省级接口返回信息:"+jsonObject.getJSONObject("head").getString("msg"));
        }

        JSONObject dataJSONObject = jsonObject.getJSONObject("data");
        if(Objects.nonNull(dataJSONObject)) {
            dataList.add(dataJSONObject);
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(dataList, pageable, 1);
            return addLayUiCode(page);
        }else{
            return dataList;
        }
    }

    /**
     * 4.1省公安厅-公民基本信息在线比对接口
     *
     * @param info
     * @returnƒ
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @PostMapping("/provincialPublicSecurityDepartment/policeRealName")
    @GxInterfaceLog(interfaceCode = "shengjjk_gmjbxxzxbd")
    public Object policeRealName(@RequestBody PoliceRealNameRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List<PoliceRealNameResponseDataDTO> data = new ArrayList<>();

        PoliceRealNameResponseDTO policeRealNameResponseDTO = provincialPublicSecurityDepartmentFeignService.policeRealName(info);
        if(Objects.nonNull(policeRealNameResponseDTO) && CollectionUtils.isNotEmpty(policeRealNameResponseDTO.getRealNameResponseDataDTOList())){
            data = policeRealNameResponseDTO.getRealNameResponseDataDTOList();
        }
        if (info != null && info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        } else {
            return data;
        }
    }

    @PostMapping("/rxdb")
    @GxInterfaceLog(interfaceCode = "shengjijk_gmjbxxrxbd")
    public Object gmjbxxRxdb(@RequestBody RxbdParamDTO rxbdParamDTO) {
        RxbdRequestDTO rxbdRequestDTO = new RxbdRequestDTO();
        List<RxbdParamDTO> rxbdParamDTOList = new ArrayList<>(1);
        rxbdParamDTOList.add(rxbdParamDTO);
        rxbdRequestDTO.setRxbdParamDTOList(rxbdParamDTOList);
        LOGGER.warn("人像对比接口入参{}", JSON.toJSONString(rxbdRequestDTO));
        RxbdResponseDTO rxbdResponseDTO = provincialPublicSecurityDepartmentFeignService.gmrxdb(rxbdRequestDTO);
        LOGGER.warn("人像比对接口返回值{}", JSON.toJSONString(rxbdResponseDTO));
        rxbdResponseDTO.setMc(rxbdParamDTO.getXm());
        rxbdResponseDTO.setZjh(rxbdParamDTO.getGmsfhm());
        return rxbdResponseDTO;
    }

    /**
     * 4.3省公安厅-居民户成员信息在线查询接口
     *
     * @param info
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @PostMapping("/provincialPublicSecurityDepartment/policeHouseholdMembers")
    @GxInterfaceLog(interfaceCode = "shengjjk_jmcyxx")
    public Object policeHouseholdMembers(@RequestBody PoliceHouseholdMembersRequestDTO info) {
        Pageable pageable = new PageRequest(1, 10, null);
        List<PoliceHouseholdMembersResponseDataDTO> data = new ArrayList<>();

        PoliceHouseholdMembersResponseDTO policeHouseholdMembersResponseDTO = provincialPublicSecurityDepartmentFeignService.policeHouseholdMembers(info);
        if (Objects.nonNull(policeHouseholdMembersResponseDTO) && CollectionUtils.isNotEmpty(policeHouseholdMembersResponseDTO.getResponseDataDTOList())) {
            data = policeHouseholdMembersResponseDTO.getResponseDataDTOList();
        }
        if(info != null &&info.isLoadpage()) {
            Page page = PageUtils.listToPageWithTotal(data, pageable, 1);
            return addLayUiCode(page);
        }else{
            return data;
        }
    }

    /**
     * @param gxCxywcsQO 权利人查询参数
     * @return 查询业务参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取查询业务参数--权利人
     */
    @PostMapping("/cxywcs/qlr")
    public Object getCxywcsQlrxx(@RequestBody GxCxywcsQO gxCxywcsQO){
        if(StringUtils.isBlank(gxCxywcsQO.getGzlslid())){
            throw new AppException("工作流实例ID为空");
        }
        List<BdcQlrDO> bdcQlrDOList =new ArrayList<>();
        List<BdcQlrGroupDTO> bdcQlrGroupDTOList= bdcQlrFeignService.groupQlrYwrByZjh(gxCxywcsQO.getGzlslid(),"","","");
        if(CollectionUtils.isNotEmpty(bdcQlrGroupDTOList)){
            for(BdcQlrGroupDTO bdcQlrGroupDTO:bdcQlrGroupDTOList){
                String qlrlx ="";
                if(bdcQlrGroupDTO.getBdcQlrDO().getQlrlx() != null){
                    qlrlx =bdcQlrGroupDTO.getBdcQlrDO().getQlrlx().toString();
                }
                if ((StringUtils.isBlank(gxCxywcsQO.getQlrlx()) && StringUtils.isBlank(gxCxywcsQO.getExcludeQlrlx())) || (StringUtils.isNotBlank(gxCxywcsQO.getQlrlx()) && gxCxywcsQO.getQlrlx().contains(qlrlx))
                        || (StringUtils.isNotBlank(gxCxywcsQO.getExcludeQlrlx()) && !StringUtils.equals(gxCxywcsQO.getExcludeQlrlx(), qlrlx))) {
                    bdcQlrDOList.add(bdcQlrGroupDTO.getBdcQlrDO());
                }
            }
        }
        return bdcQlrDOList;
    }

    /**
     * @param gxCxywcsQO 权利人查询参数
     * @return 查询业务参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取查询业务参数--项目信息
     */
    @PostMapping("/cxywcs/xmxx")
    public Object getCxywcsXmxx(@RequestBody GxCxywcsQO gxCxywcsQO){
        if(StringUtils.isBlank(gxCxywcsQO.getGzlslid())){
            throw new AppException("工作流实例ID为空");
        }
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gxCxywcsQO.getGzlslid());
        return bdcXmDTOList;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取查询业务参数-地名
     */
    @PostMapping("/cxywcs/dmxx")
    public Object getCxywcs(){
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("dmxzqdm",dmxzqdm);
        jsonObject.put("dmmc",dmmc);
        return jsonObject;

    }

    /**
     * @param gxCxywcsQO 权利人查询参数
     * @return 查询业务参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取查询业务参数--查封信息
     */
    @PostMapping("/cxywcs/cfxx")
    public Object getCxywcsCfxx(@RequestBody GxCxywcsQO gxCxywcsQO){
        if(StringUtils.isBlank(gxCxywcsQO.getGzlslid())){
            throw new AppException("工作流实例ID为空");
        }
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gxCxywcsQO.getGzlslid());
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            List<String> bdcdyhList = bdcXmDTOList.stream().map(BdcXmDTO::getBdcdyh).distinct().collect(Collectors.toList());
            return  bdcCfxxFeignService.listBdcCfByBdcdyhs(bdcdyhList);
        }
        return Collections.emptyList();
    }

    /**
     * 工改平台-工程规划许可证、工程规划核实证、竣工验证备案表等材料查询
     * @param request
     * @return
     */
    @PostMapping("/ggxt/gcxxcx")
    @GxInterfaceLog(interfaceCode = "gg_fjxx")
    public Object gcxxcx(@RequestBody JSONObject request) {
        Object response;
        Object result = exchangeInterfaceRestService.requestInterface("gg_fjxx", request);
        LOGGER.info("---工改平台信息查询:{}", JSON.toJSONString(result));
        if(Objects.nonNull(result)){
            GgFjxx ggFjxx = JSON.parseObject(JSON.toJSONString(result), GgFjxx.class);
            if (ggFjxx.isSuccess()) {
                for (AttachData datum : ggFjxx.getData()) {
                    datum.setAttachFiles(datum.getAttachList()
                            .stream()
                            .map(AttachList::getFileName)
                            .collect(Collectors.joining(",")));
                }
            }
            return ggFjxx;
        }else {
            return null;
        }
    }


    /**
     * 工改平台-工程规划许可证、工程规划核实证、竣工验证备案表等材料下载
     *
     * @param param
     * @return
     */
    @PostMapping("/ggxt/gcxxsc")
//    @GxInterfaceLog(interfaceCode = "gg_fjxx")
    public void gzajsCx(@RequestBody JSONObject param) {

        if (Objects.isNull(param)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        String gzlslid = param.getString("gzlslid");
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        Object result = exchangeInterfaceRestService.requestInterface("gg_fjxx", param);
        if (null != result) {
            GgFjxx ggFjxx = JSON.parseObject(JSON.toJSONString(result), GgFjxx.class);
            LOGGER.info("---工改平台信息下载查询:{}，{}", JSON.toJSONString(result),dldz);
            if (ggFjxx.isSuccess()) {
                //先获取所有的文件,然后再将文件全都下载,再一次性上传附件
                List<AttachList> allFiles = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(ggFjxx.getData())){
                    for (AttachData datum : ggFjxx.getData()) {
                        if(CollectionUtils.isNotEmpty(datum.getAttachList())){
                            allFiles.addAll(datum.getAttachList());
                        }
                    }
                }
                LOGGER.info("---工改平台信息下载所有文件:{}", JSON.toJSONString(allFiles));
                if(CollectionUtils.isNotEmpty(allFiles)){
                    //将文件内容都下载下来
                    for (AttachList allFile : allFiles) {
                        LOGGER.info("---工改平台信息下载原文件:{}", JSON.toJSONString(allFile));
                        //查看是否要替换请求地址为转发地址
//                        if(StringUtils.isNotBlank(dldz)) {
//                            String urlStr = IpUtils.getIps(allFile.getUrl());
//                            LOGGER.info("替换共享查询地址，原地址{},ip{}", allFile.getUrl(), urlStr);
//                            if (StringUtils.isNotBlank(urlStr)) {
//                                String replaceUrl = allFile.getUrl().replace(urlStr, dldz);
//                                LOGGER.info("替换共享查询地址，原地址{},ip{},替换后的地址{}",
//                                        allFile.getUrl(),
//                                        urlStr,
//                                        replaceUrl);
//                                allFile.setUrl(replaceUrl);
//                            }
//                        }
                        if(!allFile.getUrl().startsWith("http://")){
                            allFile.setUrl("http://" + allFile.getUrl());
                        }
                        //获取文件类型
                        byte[] fileByte = Base64Utils.getFile(allFile.getUrl());
                        allFile.setContent(fileByte);
//                        String[] split = allFile.getFileName().split(".");
                        allFile.setDirName(allFile.getFileName().substring(0,allFile.getFileName().lastIndexOf(".")));
                        allFile.setFileType(allFile.getFileName().substring(allFile.getFileName().lastIndexOf(".") + 1));
                    }
                    LOGGER.info("---工改平台信息查询,需要上传的文件:{}", JSON.toJSONString(allFiles));
                    //文件上传
                    for (AttachList allFile : allFiles) {
                        Map<String, StorageDto> stringStorageDtoMap = new HashMap<>(16);
                        //创建当前文件所在的目录,如果没有指定目录
                        if(ggptFileDir.containsKey(allFile.getDirName())){
                            allFile.setDirName(ggptFileDir.get(allFile.getDirName()));
                        }
                        LOGGER.info("---工改平台信息查询,文件上传:{}", JSON.toJSONString(allFile));
                        //检测目录是否已存在
                        StorageDto dir = null;
                        List<StorageDto> existFiles = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID,
                                gzlslid, "", allFile.getDirName(), null, null);
                        if(CollectionUtils.isNotEmpty(existFiles)){
                            dir = existFiles.get(0);
                        }else {
                            dir = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, allFile.getDirName(), null);
                        }
                        //文件上传
                        MultipartDto multipartDto = new MultipartDto();
                        multipartDto.setNodeId(dir.getId());
                        multipartDto.setClientId(StringUtils.isBlank(dir.getClientId())?CommonConstantUtils.WJZX_CLIENTID:dir.getClientId());
                        String contentType =  FileContentTypeEnum.getMcByDm(allFile.getFileType());
                        multipartDto.setData(allFile.getContent());
                        multipartDto.setOwner(userManagerUtils.getCurrentUserName());
                        multipartDto.setContentType(contentType);
                        multipartDto.setSize(allFile.getContent().length);
                        multipartDto.setOriginalFilename(allFile.getFileName());
                        multipartDto.setName(allFile.getFileName());
                        multipartDto.setSpaceCode(gzlslid);
                        StorageDto storageDto = storageClient.multipartUpload(multipartDto);
                        allFile.setStorageId(storageDto.getId());
                    }
                    LOGGER.info("---工改平台信息查询,文件上传完毕:{}", JSON.toJSONString(allFiles));
                }
            }
        }
    }

    /**
     * 不动产登记系统从一体化审批系统获取自然资源内部信息
     * @param request
     * @return
     */
    @PostMapping("/yth/xxcx")
//    @GxInterfaceLog(interfaceCode = "yth_fjxx")
    public Object ythxxcx(@RequestBody JSONObject request) {
        Object result = exchangeInterfaceRestService.requestInterface("yth_ywsjxx", request);
        LOGGER.info("---一体化审批系统信息查询:{}", JSON.toJSONString(result));
        if(Objects.nonNull(result)){
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(result));
           if(Objects.nonNull(jsonObject.get("data"))){
                return jsonObject.get("data");
           }else {
               return null;
           }
        }else {
            return null;
        }
    }


    /**
     * 不动产登记系统从一体化审批系统获取自然资源内部信息 文件下载--将文件上传到流程附件中
     *
     * @param param
     * @return
     */
    @PostMapping("/yth/fjxz")
//    @GxInterfaceLog(interfaceCode = "yth_fjxx")
    public void ythFjXz(@RequestBody JSONObject param) {

        if (Objects.isNull(param)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        String gzlslid = param.getString("gzlslid");
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        Object result = exchangeInterfaceRestService.requestInterface("yth_ywsjxx", param);
        if (null != result) {
            YthxxResponse ggFjxx = JSON.parseObject(JSON.toJSONString(result), YthxxResponse.class);
            LOGGER.info("---一体化审批系统信息查询:{}", JSON.toJSONString(result));
            //文档中没有明确说明返回值,也没有返回值示例,直接判断data
            if (Objects.nonNull(ggFjxx.getData()) && CollectionUtils.isNotEmpty(ggFjxx.getData().getFiles())) {
                List<Files> files = ggFjxx.getData().getFiles();
                //将文件内容都下载下来
                for (Files file : files) {
                    LOGGER.info("---一体化审批系统,需要上传的文件:{}", JSON.toJSONString(file));
                    //获取文件类型
//                    if( StringUtils.isNotBlank(dldz)) {
//                        String urlStr = IpUtils.getIps(file.getFileNetPath());
//                        LOGGER.info("替换共享查询地址，原地址{},ip{}", file.getFileNetPath(), urlStr);
//                        if (StringUtils.isNotBlank(urlStr)) {
//                            String replaceUrl = file.getFileNetPath().replace(urlStr, dldz);
//                            LOGGER.info("替换共享查询地址，原地址{},ip{},替换后的地址{}",
//                                    file.getFileNetPath(),
//                                    urlStr,
//                                    replaceUrl);
//                            file.setFileNetPath(replaceUrl);
//                        }
//                    }
                    if(!file.getFileNetPath().startsWith("http://")){
                        file.setFileNetPath("http://" + file.getFileNetPath());
                    }
                    file.setFileNetPath(file.getFileNetPath().replace("&&","&"));
                    file.setFileNetPath(file.getFileNetPath().replace(file.getFileName(), URLEncoder.encode(file.getFileName())));
                    byte[] fileByte = Base64Utils.getFile(file.getFileNetPath());
                    //检测目录是否已存在
                    StorageDto dir = null;
                    List<StorageDto> existFiles = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID,
                            gzlslid, "", file.getDirectoryName(), null, null);
                    if(CollectionUtils.isNotEmpty(existFiles)){
                        dir = existFiles.get(0);
                    }else {
                        dir = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, file.getDirectoryName(), null);
                    }
                    //文件上传
                    MultipartDto multipartDto = new MultipartDto();
                    multipartDto.setNodeId(dir.getId());
                    multipartDto.setClientId(StringUtils.isBlank(dir.getClientId())?CommonConstantUtils.WJZX_CLIENTID:dir.getClientId());
                    String contentType =  FileContentTypeEnum.getMcByDm(file.getFileName().substring(file.getFileName().lastIndexOf(".") + 1));
                    multipartDto.setData(fileByte);
                    multipartDto.setOwner(userManagerUtils.getCurrentUserName());
                    multipartDto.setContentType(contentType);
                    multipartDto.setSize(fileByte.length);
                    multipartDto.setOriginalFilename(file.getFileName());
                    multipartDto.setName(file.getFileName());
                    multipartDto.setSpaceCode(gzlslid);
                    StorageDto storageDto = storageClient.multipartUpload(multipartDto);
                    LOGGER.info("---一体化审批系统,文件上传完毕:{}", JSON.toJSONString(storageDto));
                }
            }else {
                throw new MissingArgumentException("获取附件失败！");
            }
        }
    }


    /**
     * 不动产登记系统从一体化审批系统获取自然资源内部信息 文件预览--将文件压缩后输出到浏览器
     *
     * @param param
     * @return
     */
    @GetMapping("/yth/fjxzzip")
//    @GxInterfaceLog(interfaceCode = "yth_fjxx")
    public void ythFjylZip(@RequestParam("number") String number,
                           @RequestParam(value = "type",required = false) String type,
                           HttpServletResponse response) {

        if (Objects.isNull(number)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        JSONObject param = new JSONObject();
        param.put("number",number);
        param.put("type",type);
        Object result = exchangeInterfaceRestService.requestInterface("yth_ywsjxx", param);
        if (null != result) {
            YthxxResponse ggFjxx = JSON.parseObject(JSON.toJSONString(result), YthxxResponse.class);
            LOGGER.info("---一体化审批系统信息查询:{}", JSON.toJSONString(result));
            //文档中没有明确说明返回值,也没有返回值示例,直接判断data
            if (Objects.nonNull(ggFjxx.getData()) && CollectionUtils.isNotEmpty(ggFjxx.getData().getFiles())) {
                List<Files> files = ggFjxx.getData().getFiles();
                //将文件内容都下载下来
                String filepath = printPath + param.getString("number");
                //下载zip文件
                try (OutputStream outputStream = response.getOutputStream()) {
                    //创建目录
                    File dir = new File(filepath);
                    dir.mkdirs();
                    for (Files file : files) {
                        LOGGER.info("---一体化审批系统,需要上传的文件:{}", JSON.toJSONString(file));
                        //获取文件类型
                        if(!file.getFileNetPath().startsWith("http://")){
                            file.setFileNetPath("http://" + file.getFileNetPath());
                        }
                        file.setFileNetPath(file.getFileNetPath().replace("&&","&"));
                        file.setFileNetPath(file.getFileNetPath().replace(file.getFileName(), URLEncoder.encode(file.getFileName())));
                        byte[] fileByte = Base64Utils.getFile(file.getFileNetPath());
                        String fileName = filepath + "/" + file.getFileName();
                        //将文件放在本地
                        try {
                            File localfile = new File(fileName);
                            if(Objects.isNull(localfile)) {
                                localfile.createNewFile();
                            }
                            //写入文件内容
                            FileOutputStream fos = new FileOutputStream(localfile);
                            fos.write(fileByte);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    String zip = ZipUtils.zip(filepath, filepath + ".zip");
                    File zipfile = new File(zip);
                    FileInputStream inputStream = new FileInputStream(zipfile);
                    String fileName = URLDecoder.decode(param.getString("number") + ".zip", "utf-8");
                    // 浏览器下载
                    String downloadFile = URLEncoder.encode(fileName, "utf-8");
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
                    outputStream.write(IOUtils.readFully(inputStream, Math.toIntExact(zipfile.length())));
                    outputStream.flush();
                    inputStream.close();
                    outputStream.close();
                    ZipUtils.deleteDir(dir);
                    zipfile.delete();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                throw new MissingArgumentException("获取附件失败！");
            }
        }
    }

    /**
     * 不动产登记系统从一体化审批系统获取自然资源内部信息 文件预览--将文件输出到浏览器
     *
     * @param param
     * @return
     */
    @GetMapping("/yth/fjyl")
    public void ythFjyl(@RequestParam("filename") String filename, @RequestParam("filepath") String filepath,HttpServletResponse response) {
        if (StringUtils.isBlank(filename) || StringUtils.isBlank(filepath)) {
            throw new MissingArgumentException("参数不可为空！");
        }

        if(!filepath.startsWith("http://")){
            filepath = "http://" + filepath;
        }
        byte[] fileByte = Base64Utils.getFile(filepath);
        if(Objects.isNull(fileByte)){
            throw new MissingArgumentException("文件下载失败！");
        }
        try (OutputStream outputStream = response.getOutputStream()) {
            String fileName = URLDecoder.decode(filename, "utf-8");
            // 浏览器下载
            String downloadFile = URLEncoder.encode(fileName, "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
            outputStream.write(fileByte);
            outputStream.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据工作流实例ID获取目录
     */
    @PostMapping("/ml/{gzlslid}")
    public Object getGxjkml(@PathVariable("gzlslid") String gzlslid) {
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            return sjgxjkmlConfig.getJkml(bdcXmDTOList.get(0).getGzldyid());
        }
        //登记库无数据查受理库
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (Objects.nonNull(bdcSlJbxxDO)) {
            return sjgxjkmlConfig.getJkml(bdcSlJbxxDO.getGzldyid());
        }
        return Collections.emptyList();
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [gzlslid]
     * @return java.lang.Object
     * @description 根据工作流实例ID查询配置的目录
     */
    @PostMapping("/shij/ml/{gzlslid}")
    public Object getShijGxjkml(@PathVariable("gzlslid") String gzlslid){
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            BdcTsywPzQO bdcTsywPzQO = new BdcTsywPzQO();
            bdcTsywPzQO.setPzmc(MLPZ_PREFIX + bdcXmDTOList.get(0).getGzldyid());
            List<BdcTsywPzDO> bdcTsywPzDOList = bdcTsywPzFeignService.listBdcTsywPz(bdcTsywPzQO);
            if(CollectionUtils.isNotEmpty(bdcTsywPzDOList) && StringUtils.isNotBlank(bdcTsywPzDOList.get(0).getPzz())) {
                return bdcZdSsjgxFeignService.getSsjgxByFmldms(bdcTsywPzDOList.get(0).getPzz());
            }
        }
        return Collections.emptyList();
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  保存共享接口数据到redis
     */
    @PostMapping("/redis")
    public String generateFzjlPdf(@RequestBody List<Map> dataList) {
        return bdcGxjkFeignService.saveGxjkDataToRedis(dataList);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  生成PDF文件,并保存到当前流程附件
     */
    @PostMapping("/pdf")
    public void generateFzjlPdf(@RequestBody BdcGxjkPdfDTO bdcGxjkPdfDTO) {
        if (StringUtils.isAnyBlank(bdcGxjkPdfDTO.getGzlslid(), bdcGxjkPdfDTO.getDylx())) {
            throw new MissingArgumentException("省级共享接口验证结果缺少参数");
        }
        // 获取PDF打印数据
        String xmlData = bdcGxjkFeignService.getPrintXmlOfGxjk(bdcGxjkPdfDTO);
        LOGGER.info("省级共享接口验证结果生成PDF文件，PDF打印数据：{}", xmlData);

        // 生成PDF文件（PDF的生成需要UI应用处理）
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + bdcGxjkPdfDTO.getDylx() + CommonConstantUtils.WJHZ_DOCX);
        pdfExportDTO.setFileName(bdcGxjkPdfDTO.getDylx() + bdcGxjkPdfDTO.getGzlslid());
        pdfExportDTO.setXmlData(xmlData);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
        LOGGER.info("省级共享接口验证结果生成PDF文件，文件路径：{}", pdfFilePath);

        // 上传文件到storage
        bdcGxjkPdfDTO.setPdfFilePath(pdfFilePath);
        //添加水印
        OfficeUtil.addWaterMarkToPdfFileWithFontSzie(pdfFilePath,  bdcGxjkPdfDTO.getSynr(), printPath + "/", 15);
        bdcGxjkFeignService.saveGxjkPdfFile(bdcGxjkPdfDTO);
    }

    /**
     * 共享查询通用打印功能
     * <p>页面传递打印类型和工作流实例ID 参数， redisKey为存在redis缓存中的子表打印数据</p>
     * @param response httpResponse
     * @param dylx  打印类型
     * @param redisKey 缓存Key
     * @param gzlslid 工作流实例ID
     */
    @GetMapping("/pdf/print/{dylx}/{redisKey}")
    public void getPdfViewData(HttpServletResponse response, @PathVariable(value = "dylx") String dylx,
                               @PathVariable(value = "redisKey") String redisKey, @RequestParam(value = "gzlslid") String gzlslid) {

        if(StringUtils.isAnyBlank(dylx, redisKey)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到打印类型、缓存参数信息");
        }
        BdcGxjkPdfDTO bdcGxjkPdfDTO = new BdcGxjkPdfDTO();
        bdcGxjkPdfDTO.setDylx(dylx);
        bdcGxjkPdfDTO.setKey(redisKey);
        // 存在有些打印类型，无需工作流实例ID参数，但后续打印使用工作流实例ID做为生成pdf文件的名称，以及打印功能所用，所以生成一个id
        if(StringUtils.isBlank(gzlslid)){
            gzlslid = UUIDGenerator.generate16();
        }
        bdcGxjkPdfDTO.setGzlslid(gzlslid);

        // 获取PDF打印数据
        String xmlData = bdcGxjkFeignService.getPrintXmlOfGxjk(bdcGxjkPdfDTO);
        LOGGER.info("省级共享接口验证结果生成PDF文件，PDF打印数据：{}", xmlData);
        // 生成PDF文件（PDF的生成需要UI应用处理）
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + bdcGxjkPdfDTO.getDylx() + CommonConstantUtils.WJHZ_DOCX);
        pdfExportDTO.setFileName(bdcGxjkPdfDTO.getDylx() + bdcGxjkPdfDTO.getGzlslid());
        pdfExportDTO.setXmlData(xmlData);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
        pdfController.exportPdf(response, pdfExportDTO);
        LOGGER.info("省级共享接口验证结果生成PDF文件，文件路径：{}", pdfFilePath);
    }

    /**
      * @param cxywlb 查询业务类别
      * @param param 查询参数
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  市级共享接口
      */
    @PostMapping("/shijgxCx/{cxywlb}")
    public Object shijgxCx(@PathVariable("cxywlb") String cxywlb,@RequestBody JSONObject param) {
        if(StringUtils.isBlank(cxywlb) ||param ==null){
            throw new AppException("市级共享查询缺失参数查询业务类别或查询参数");
        }
        String beanName =ShijgxEnum.getBeanName(cxywlb);
        if(StringUtils.isBlank(beanName)){
            throw new AppException("查询业务类别错误"+cxywlb);
        }
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, param);
        try {
            LOGGER.info("市级共享接口保存日志开始");
            UserDto userDto = userManagerUtils.getUserDto();
            String department = userManagerUtils.getOrganizationByUserName(userDto.getUsername());
            bdcShijiFeignService.insertShijiInterfaceLog(beanName,StringUtils.isNotBlank(userDto.getAlias())?userDto.getAlias():userDto.getUsername(),department,param.toJSONString(),JSON.toJSONString(response),beanName);
            LOGGER.info("市级共享接口保存日志结束");
        }catch (Exception e){
            LOGGER.error("市级共享接口保存日志异常:", e);
        }
        return response;
    }

    /**
     * @param cxywlb 查询业务类别
     * @param request 查询参数
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description  市级共享证照下载接口
     */
    @PostMapping("/shijgxCx/zzxz/{cxywlb}")
    public Object shijgxZzxz(@PathVariable("cxywlb") String cxywlb,@RequestBody BdcShijiZzxzDTO request) {
        if(StringUtils.isBlank(cxywlb) || request == null){
            throw new AppException("市级共享证照下载查询缺失参数查询业务类别或查询参数");
        }
        String beanName =ShijgxEnum.getBeanName(cxywlb);
        if(StringUtils.isBlank(beanName)){
            throw new AppException("查询业务类别错误"+cxywlb);
        }
        UserDto userDto = userManagerUtils.getUserDto();
        if(StringUtils.isBlank(request.getBjry())){
            request.setBjry(userDto.getAlias());
            request.setBjrzjh(userDto.getIdCard());
        }
        JSONArray data = new JSONArray();
        if (StringUtils.isNotBlank(request.getZjh())){
            Object responseObject = exchangeInterfaceFeignService.requestInterface(beanName, request);
            LOGGER.info("接口返回{}",responseObject.toString());
            CommonResponse response = JSON.parseObject(JSON.toJSONString(responseObject),CommonResponse.class);
//            CommonResponse<JSONArray> response = JSON.parseObject(JSON.toJSONString(responseObject), new TypeReference<CommonResponse<JSONArray>>() {});
            try {
                LOGGER.info("市级共享接口保存日志开始{}",response.toString());
                String department = userManagerUtils.getOrganizationByUserName(userDto.getUsername());
                bdcShijiFeignService.insertShijiInterfaceLog(request.getCertificateType(),StringUtils.isNotBlank(userDto.getAlias())?userDto.getAlias():userDto.getUsername(),department,JSON.toJSONString(request),JSON.toJSONString(response),request.getCertificateType());
                LOGGER.info("市级共享接口保存日志结束");
            }catch (Exception e){
                LOGGER.info("市级共享接口保存日志异常:", e);
            }
            return response;
        }
        return CommonResponse.fail("查询失败！");
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 共享接口查询接口
      */
    @PostMapping("/gxjkCx/{cxywlb}")
    public Object gxjkCx(@PathVariable("cxywlb") String cxywlb,@RequestBody JSONObject param) {
        if(StringUtils.isBlank(cxywlb) ||param ==null){
            throw new AppException("共享查询缺失参数查询业务类别或查询参数");
        }
        String beanName =GxJkEnum.getBeanName(cxywlb);
        if(StringUtils.isBlank(beanName)){
            throw new AppException("查询业务类别错误"+cxywlb);
        }
        Object response= exchangeInterfaceFeignService.requestInterface(beanName, param);
        return response;

    }


    /**
     *
     * 2.7 司法部公正文书服务接口
     * @return String
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @PostMapping("/bwgxjk/sfgzws")
    @GxInterfaceLog(interfaceCode = "bwgxjk_sfgzws")
    public Object sfgzws(@RequestParam("dsrxm") String dsrxm,
                         @RequestParam("dsrzjhm") String dsrzjhm,
                         @RequestParam("gzsbh") String gzsbh){
        Pageable pageable = new PageRequest(1, 10, null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dsrxm", dsrxm);
        jsonObject.put("dsrzjhm", dsrzjhm);
        jsonObject.put("gzsbh", gzsbh);
        try {
            Object response = exchangeInterfaceRestService.requestInterface("bwgxjk_sfgzws", jsonObject);
            JSONObject jsonResponse = JSON.parseObject(JSON.toJSONString(response));
            LOGGER.info("司法部公正文书服务接口返回{}",JSON.toJSONString(response));

            String code = jsonResponse.getString("code");
            String dataString = jsonResponse.getString("data");
            if(Objects.nonNull(code) && code.equals(CommonConstantUtils.RESPONSE_SUCCESS)
                    && StringUtils.isNotEmpty(dataString)){
                JSONObject dataObject = JSON.parseObject(dataString);
                JSONObject data = JSON.parseObject(dataObject.getString("data"));
                Page page = PageUtils.listToPageWithTotal(Arrays.asList(data), pageable, 1);
                return addLayUiCode(page);
            }else {
                Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
                return addLayUiCode(page);
            }
        }catch (Exception e){
            e.printStackTrace();
            Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
            return addLayUiCode(page);
        }
    }



    /**
     *
     * 2.8卫健委_出生医学证明
     * @return String
     * @author wangyinghao
     */
    @PostMapping("/bwgxjk/wjwcsyxzm")
    @GxInterfaceLog(interfaceCode = "bwgxjk_wjwcsyxzm")
    public Object wjwcsyxzm(@RequestParam("birthCode") String birthCode,
                         @RequestParam("momName") String momName,
                         @RequestParam("momIdCode") String momIdCode){
        Pageable pageable = new PageRequest(1, 10, null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("birthCode", birthCode);
        jsonObject.put("momName", momName);
        jsonObject.put("momIdCode", momIdCode);
        try {
            Object response = exchangeInterfaceRestService.requestInterface("bwgxjk_wjwcsyxzm", jsonObject);
            JSONObject jsonResponse = JSON.parseObject(JSON.toJSONString(response));
            LOGGER.info("卫健委_出生医学证明返回{}",JSON.toJSONString(response));

            String code = jsonResponse.getString("code");
            String firstDataString = jsonResponse.getString("data");
            if(Objects.nonNull(code) && code.equals(CommonConstantUtils.RESPONSE_SUCCESS)
                    && StringUtils.isNotEmpty(firstDataString)){
                JSONObject dataObject = JSON.parseObject(firstDataString);
                String secondDataString =dataObject.getString("data");
                //返回的是xml，转成实体
                JSONObject data = new JSONObject();
                if(StringUtils.isNotBlank(secondDataString)) {
                    try {
                        JSONObject jsonFromXml = JsonXmlUtil.getJsonFromXml(secondDataString);
                        //获取实体中的数据
                        JSONObject body = jsonFromXml.getJSONObject("MDEML").getJSONObject("body");
                        if(Objects.nonNull(body)){
                            data = body.getJSONObject("data");
                        }
                    } catch (Exception e) {
                       LOGGER.info("获取卫建委-出生医学证明返回失败{}",secondDataString);
                    }
                }
                Page page = PageUtils.listToPageWithTotal(Arrays.asList(data), pageable, 1);
                return addLayUiCode(page);
            }else {
                Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
                return addLayUiCode(page);
            }
        }catch (Exception e){
            e.printStackTrace();
            Page page = PageUtils.listToPageWithTotal(Lists.newArrayList(), pageable, 1);
            return addLayUiCode(page);
        }
    }


    /**
     * 建设工程规划许可
     * @param request
     * @return
     */
    @PostMapping("/ydgh/xxcx")
    public Object ydghCx(@RequestBody JSONObject request) {
        String cxlx = request.getString("cxlx");
        String jkdz = "queryYdghList";
        if(cxlx.equals("gcgh")){
            jkdz = "queryGcghList";
        }
        if(cxlx.equals("ydhs")){
            jkdz = "queryYdhsList";
        }
        JSONObject param = new JSONObject();
        param.put("bdcdyh",request.getJSONObject("params").get("bdcdyh"));
        param.put("xmmc",request.getJSONObject("params").get("xmmc"));
        JSONObject cxParam = new JSONObject();
        cxParam.put("params",param);
        Object data = exchangeInterfaceRestService.requestInterface(jkdz, cxParam);
        LOGGER.info("---建设工程规划许可信息查询:{}", JSON.toJSONString(data));
        if(Objects.nonNull(data)){
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(data));
            if(Objects.nonNull(jsonObject.get("result"))){
                JSONObject result = jsonObject.getJSONObject("result");
                if(result.containsKey("data")){
                    JSONArray gcghxxList = result.getJSONArray("data");
                    //处理返回值中的url地址
                    if(StringUtils.isNotBlank(ydghurl)){
                        for (int i = 0; i < gcghxxList.size(); i++) {
                            JSONObject dataItem = gcghxxList.getJSONObject(i);
                            if(dataItem.containsKey("fj")){
                                JSONArray fj = dataItem.getJSONArray("fj");
                                for (int i1 = 0; i1 < fj.size(); i1++) {
                                    JSONObject fjitem = fj.getJSONObject(i1);
                                    if(fjitem.containsKey("path")){
                                        fjitem.put("path",ydghurl + fjitem.getString("path"));
                                    }
                                }
                            }
                        }
                    }
                    return gcghxxList;
                }
            }
        }
        return null;
    }


    /**
     * 建设工程规划许可--将文件压缩后输出到浏览器
     *
     * @param param
     * @return
     */
    @GetMapping("/ydgh/fjxzzip")
    public void ydghCxZip(@RequestParam("bdcdyh") String bdcdyh,
                           @RequestParam(value = "xmmc",required = false) String xmmc,
                           @RequestParam(value = "cxlx",required = false) String cxlx,
                           HttpServletResponse response) {

        if (Objects.isNull(bdcdyh)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        JSONObject param = new JSONObject();
        param.put("bdcdyh",bdcdyh);
        param.put("xmmc",xmmc);
        JSONObject cxParam = new JSONObject();
        cxParam.put("params",param);
        String jkdz = "queryYdghList";
        if(cxlx.equals("gcgh")){
            jkdz = "queryGcghList";
        }
        if(cxlx.equals("ydhs")){
            jkdz = "queryYdhsList";
        }

        Object result = exchangeInterfaceRestService.requestInterface(jkdz, cxParam);
        if (null != result) {
            JSONObject gcghxx = JSON.parseObject(JSON.toJSONString(result));
            List<JSONObject> fjitemList = new ArrayList<>();
            LOGGER.info("---建设工程规划许可信息查询:{}", JSON.toJSONString(gcghxx));
            if(Objects.nonNull(gcghxx.get("result"))){
                JSONObject gcghxxResult = gcghxx.getJSONObject("result");
                if(gcghxxResult.containsKey("data")){
                    JSONArray gcghxxList = gcghxxResult.getJSONArray("data");
                    //处理返回值中的url地址
                    if(StringUtils.isNotBlank(ydghurl)){
                        for (int i = 0; i < gcghxxList.size(); i++) {
                            JSONObject dataItem = gcghxxList.getJSONObject(i);
                            if(dataItem.containsKey("fj")){
                                JSONArray fj = dataItem.getJSONArray("fj");
                                for (int i1 = 0; i1 < fj.size(); i1++) {
                                    JSONObject fjitem = fj.getJSONObject(i1);
                                    if(fjitem.containsKey("path")){
                                        fjitem.put("path",ydghurl + fjitem.getString("path"));
                                        fjitemList.add(fjitem);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(fjitemList)) {
                //将文件内容都下载下来
                String filepath = printPath + bdcdyh;
                //下载zip文件
                try (OutputStream outputStream = response.getOutputStream()) {
                    //创建目录
                    File dir = new File(filepath);
                    dir.mkdirs();
                    for (JSONObject jsonObject : fjitemList) {
                        LOGGER.info("---建设工程规划许可信息查询,需要上传的文件:{}", JSON.toJSONString(jsonObject));
                        //获取文件类型
                        byte[] fileByte = Base64Utils.getFile(jsonObject.getString("path"));
                        String fileName = filepath + "/" + jsonObject.getString("name");
                        //将文件放在本地
                        try {
                            File localfile = new File(fileName);
                            if(Objects.isNull(localfile)) {
                                localfile.createNewFile();
                            }
                            //写入文件内容
                            FileOutputStream fos = new FileOutputStream(localfile);
                            fos.write(fileByte);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    String zip = ZipUtils.zip(filepath, filepath + ".zip");
                    File zipfile = new File(zip);
                    FileInputStream inputStream = new FileInputStream(zipfile);
                    String fileName = URLDecoder.decode(bdcdyh + ".zip", "utf-8");
                    // 浏览器下载
                    String downloadFile = URLEncoder.encode(fileName, "utf-8");
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
                    outputStream.write(IOUtils.readFully(inputStream, Math.toIntExact(zipfile.length())));
                    outputStream.flush();
                    inputStream.close();
                    outputStream.close();
                    ZipUtils.deleteDir(dir);
                    zipfile.delete();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                throw new MissingArgumentException("获取附件失败！");
            }
        }
    }



    /**
     * 土地出让
     * @param request
     * @return
     */
    @PostMapping("/tdcr/xxcx")
    public Object tdcrCx(@RequestBody JSONObject request) {
        String cxlx = request.getString("cxlx");
        String jkdz = "queryTdcrList";
        if(cxlx.equals("tdhb")){
            jkdz = "queryTdhbList";
        }
        JSONObject param = new JSONObject();
        param.put("bdcdyh",request.getJSONObject("params").get("bdcdyh"));
        param.put("xmmc",request.getJSONObject("params").get("xmmc"));
        JSONObject cxParam = new JSONObject();
        cxParam.put("params",param);
        Object data = exchangeInterfaceRestService.requestInterface(jkdz, cxParam);
        LOGGER.info("---土地出让信息查询:{}", JSON.toJSONString(data));
        if(Objects.nonNull(data)){
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(data));
            if(Objects.nonNull(jsonObject.get("result"))){
                JSONObject result = jsonObject.getJSONObject("result");
                if(result.containsKey("data")){
                    JSONArray gcghxxList = result.getJSONArray("data");
                    //处理返回值中的url地址
                    if(StringUtils.isNotBlank(tdcrurl)){
                        for (int i = 0; i < gcghxxList.size(); i++) {
                            JSONObject dataItem = gcghxxList.getJSONObject(i);
                            if(dataItem.containsKey("fj")){
                                JSONArray fj = dataItem.getJSONArray("fj");
                                for (int i1 = 0; i1 < fj.size(); i1++) {
                                    JSONObject fjitem = fj.getJSONObject(i1);
                                    if(fjitem.containsKey("path")){
                                        fjitem.put("path",tdcrurl + fjitem.getString("path"));
                                    }
                                }
                            }
                        }
                    }
                    return gcghxxList;
                }
            }
        }
        return null;
    }


    /**
     * 土地出让--将文件压缩后输出到浏览器
     *
     * @param param
     * @return
     */
    @GetMapping("/tdcr/fjxzzip")
    public void tdcrCxZip(@RequestParam("bdcdyh") String bdcdyh,
                          @RequestParam(value = "xmmc",required = false) String xmmc,
                          @RequestParam(value = "cxlx",required = false) String cxlx,
                          HttpServletResponse response) {

        if (Objects.isNull(bdcdyh)) {
            throw new MissingArgumentException("参数不可为空！");
        }
        JSONObject param = new JSONObject();
        param.put("bdcdyh",bdcdyh);
        param.put("xmmc",xmmc);
        JSONObject cxParam = new JSONObject();
        cxParam.put("params",param);
        String jkdz = "queryTdcrList";
        if(cxlx.equals("tdhb")){
            jkdz = "queryTdhbList";
        }
        Object result = exchangeInterfaceRestService.requestInterface(jkdz, cxParam);
        if (null != result) {
            JSONObject gcghxx = JSON.parseObject(JSON.toJSONString(result));
            List<JSONObject> fjitemList = new ArrayList<>();
            LOGGER.info("---土地出让信息查询:{}", JSON.toJSONString(gcghxx));
            if(Objects.nonNull(gcghxx.get("result"))){
                JSONObject gcghxxResult = gcghxx.getJSONObject("result");
                if(gcghxxResult.containsKey("data")){
                    JSONArray gcghxxList = gcghxxResult.getJSONArray("data");
                    //处理返回值中的url地址
                    if(StringUtils.isNotBlank(tdcrurl)){
                        for (int i = 0; i < gcghxxList.size(); i++) {
                            JSONObject dataItem = gcghxxList.getJSONObject(i);
                            if(dataItem.containsKey("fj")){
                                JSONArray fj = dataItem.getJSONArray("fj");
                                for (int i1 = 0; i1 < fj.size(); i1++) {
                                    JSONObject fjitem = fj.getJSONObject(i1);
                                    if(fjitem.containsKey("path")){
                                        fjitem.put("path",tdcrurl + fjitem.getString("path"));
                                        fjitemList.add(fjitem);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(fjitemList)) {
                //将文件内容都下载下来
                String filepath = printPath + bdcdyh;
                //下载zip文件
                try (OutputStream outputStream = response.getOutputStream()) {
                    //创建目录
                    File dir = new File(filepath);
                    dir.mkdirs();
                    for (JSONObject jsonObject : fjitemList) {
                        LOGGER.info("---土地出让信息查询,需要上传的文件:{}", JSON.toJSONString(jsonObject));
                        //获取文件类型
                        byte[] fileByte = Base64Utils.getFile(jsonObject.getString("path"));
                        String fileName = filepath + "/" + jsonObject.getString("name");
                        //将文件放在本地
                        try {
                            File localfile = new File(fileName);
                            if(Objects.isNull(localfile)) {
                                localfile.createNewFile();
                            }
                            //写入文件内容
                            FileOutputStream fos = new FileOutputStream(localfile);
                            fos.write(fileByte);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    String zip = ZipUtils.zip(filepath, filepath + ".zip");
                    File zipfile = new File(zip);
                    FileInputStream inputStream = new FileInputStream(zipfile);
                    String fileName = URLDecoder.decode(bdcdyh + ".zip", "utf-8");
                    // 浏览器下载
                    String downloadFile = URLEncoder.encode(fileName, "utf-8");
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
                    outputStream.write(IOUtils.readFully(inputStream, Math.toIntExact(zipfile.length())));
                    outputStream.flush();
                    inputStream.close();
                    outputStream.close();
                    ZipUtils.deleteDir(dir);
                    zipfile.delete();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                throw new MissingArgumentException("获取附件失败！");
            }
        }
    }


    /**
     * 公证案件基本信息
     *
     * @param request
     * @return
     */
    @PostMapping("/dsjgzs/xxcx")
    public Object dsjgzsCx(@RequestBody JSONObject request) {
        JSONObject cxParam = new JSONObject();
        cxParam.put("baseParams", request.getString("baseParams"));
        LOGGER.info("---公证案件基本信息查询参数:{}", JSON.toJSONString(cxParam));
        Object data = exchangeInterfaceRestService.requestInterface("dsjj_gzajjbxx", cxParam);
        LOGGER.info("---公证案件基本信息查询:{}", JSON.toJSONString(data));
        if (Objects.nonNull(data)) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(data));
            if (Objects.nonNull(jsonObject.get("retCode"))
                    && jsonObject.getString("retCode").equals("0")
                    && Objects.nonNull(jsonObject.get("object"))) {
                return jsonObject.getJSONObject("object");
            }
        }
        return null;
    }


    /**
     * 公证案件基本信息附件下载
     * @param xm
     * @param sfzhm
     * @param gzsbh
     * @param jgbm
     * @param response
     * @throws Exception
     */
    @GetMapping("/ydgh/fjxz")
    public void dsjgzsDownload(@RequestParam(value = "xm",required = true) String xm,
                               @RequestParam(value = "sfzhm", required = true) String sfzhm,
                               @RequestParam(value = "gzsbh", required = true) String gzsbh,
                               @RequestParam(value = "jgbm", required = true) String jgbm,
                               HttpServletResponse response) throws Exception {
        JSONObject cxParam = new JSONObject();
        cxParam.put("baseParams", "<xm>" + xm + "</xm>" +
                "<sfzhm>" + sfzhm + "</sfzhm>" +
                "<gzsbh>" + gzsbh + "</gzsbh>" +
                "<jgbm>" + jgbm + "</jgbm>");
        LOGGER.info("---公证案件基本信息查询参数:{}", JSON.toJSONString(cxParam));
        Object data = exchangeInterfaceRestService.requestInterface("dsjj_gzajfjxx", cxParam);
        LOGGER.info("---公证案件基本信息查询:{}", JSON.toJSONString(data));
        if (Objects.nonNull(data)) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(data));
            if (Objects.nonNull(jsonObject.get("retCode"))
                    && jsonObject.getString("retCode").equals("0")
                    && Objects.nonNull(jsonObject.get("object"))) {
                String content = jsonObject.getString("object");
                byte[] decode = Base64.getDecoder().decode(content);
                try (OutputStream outputStream = response.getOutputStream()) {
                    // 浏览器下载
                    String downloadFile = URLEncoder.encode(gzsbh + ".pdf", "utf-8");
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
                    outputStream.write(decode);
                    outputStream.flush();
                    outputStream.close();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new ParameterException("查询附件失败");
            }
        }
    }

    /**
     * 公证案件基本信息附件上传到流程
     * @param xm
     * @param sfzhm
     * @param gzsbh
     * @param jgbm
     * @param gzlslid
     * @throws Exception
     */
    @GetMapping("/ydgh/fjsc")
    public void dsjgzsUpload(@RequestParam(value = "xm" ,required = true) String xm,
                             @RequestParam(value = "sfzhm", required = true) String sfzhm,
                             @RequestParam(value = "gzsbh", required = true) String gzsbh,
                             @RequestParam(value = "jgbm", required = true) String jgbm,
                             @RequestParam(value = "gzlslid", required = true) String gzlslid) throws Exception {

        JSONObject cxParam = new JSONObject();
        cxParam.put("baseParams", "<xm>" + xm + "</xm>" +
                "<sfzhm>" + sfzhm + "</sfzhm>" +
                "<gzsbh>" + gzsbh + "</gzsbh>" +
                "<jgbm>" + jgbm + "</jgbm>");
        LOGGER.info("---公证案件基本信息查询参数:{}", JSON.toJSONString(cxParam));
        Object data = exchangeInterfaceRestService.requestInterface("dsjj_gzajfjxx", cxParam);
        LOGGER.info("---公证案件基本信息查询:{}", JSON.toJSONString(data));
        if (Objects.nonNull(data)) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(data));
            if (Objects.nonNull(jsonObject.get("retCode"))
                    && jsonObject.getString("retCode").equals("0")
                    && Objects.nonNull(jsonObject.get("object"))) {
                String content = jsonObject.getString("object");
                byte[] decode = Base64.getDecoder().decode(content);
                String mlmc = StringUtils.isNotBlank(gzajmlmc) ? gzajmlmc : "公证案件";
                //检测目录是否已存在
                StorageDto dir = null;
                List<StorageDto> existFiles = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID,
                        gzlslid, "", mlmc, null, null);
                if (CollectionUtils.isNotEmpty(existFiles)) {
                    dir = existFiles.get(0);
                } else {
                    dir = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, mlmc, null);
                }
                //文件上传
                MultipartDto multipartDto = new MultipartDto();
                multipartDto.setNodeId(dir.getId());
                multipartDto.setClientId(StringUtils.isBlank(dir.getClientId()) ? CommonConstantUtils.WJZX_CLIENTID : dir.getClientId());
                String contentType = "application/pdf";
                multipartDto.setData(decode);
//                multipartDto.setOwner(userManagerUtils.getCurrentUserName());
                multipartDto.setContentType(contentType);
                multipartDto.setSize(decode.length);
                multipartDto.setOriginalFilename(gzsbh + ".pdf");
                multipartDto.setName(gzsbh + ".pdf");
                multipartDto.setSpaceCode(gzlslid);
                StorageDto storageDto = storageClient.multipartUpload(multipartDto);
                LOGGER.info("---一体化审批系统,文件上传完毕:{}", JSON.toJSONString(storageDto));
            } else {
                throw new ParameterException("查询附件失败");
            }
        }
    }



    /**
     * 省级常住人口查询
     *
     * @param request
     * @return
     */
    @PostMapping("/sheng/czrkcx")
    public Object czrkCx(@RequestBody JSONObject request) {
        LOGGER.info("---省级常住人口查询:{}", JSON.toJSONString(request));
        String gzlslid = request.getString("gzlslid");
        String name = request.getString("name");
        try {
            Object data = exchangeInterfaceRestService.requestInterface("xc_czrkcx", request);
            LOGGER.info("---省级常住人口查询返回:{}", JSON.toJSONString(data));
            if (Objects.nonNull(data)) {
                JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(data));
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    jsonObject.put("CXR", userManagerUtils.getCurrentUserName());
                    jsonObject.put("CXSJ", DateUtils.getCurrentTimeStr());
                    //将结果中返回的图片上传到大云
                    if (jsonObject.containsKey("XP")) {
                        try {
                            BASE64Decoder decoder = new BASE64Decoder();
                            byte[] content = decoder.decodeBuffer(jsonObject.getString("XP"));
                            String mlmc = "信息共享查询";
                            //检测目录是否已存在
                            StorageDto dir = null;
                            List<StorageDto> existFiles = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID,
                                    gzlslid, "", mlmc, null, null);
                            if (CollectionUtils.isNotEmpty(existFiles)) {
                                dir = existFiles.get(0);
                            } else {
                                dir = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, mlmc, null);
                            }
                            List<StorageDto> storageDtoList = storageClient.listStoragesByName("clientId", gzlslid, null, name + "_人口信息查询相片.gif", 1, null);
                            if(CollectionUtils.isNotEmpty(storageDtoList)){
                                storageClient.deleteStorages(storageDtoList
                                        .stream().map(StorageDto::getId)
                                        .collect(Collectors.toList()));
                            }

                            //文件上传
                            MultipartDto multipartDto = new MultipartDto();
                            multipartDto.setNodeId(dir.getId());
                            multipartDto.setClientId(StringUtils.isBlank(dir.getClientId()) ? CommonConstantUtils.WJZX_CLIENTID : dir.getClientId());
                            String contentType = "image/gif;charset=utf-8";
                            multipartDto.setData(content);
                            multipartDto.setContentType(contentType);
                            multipartDto.setSize(content.length);
                            multipartDto.setOriginalFilename(name + "_人口信息查询相片.gif");
                            multipartDto.setName(name + "_人口信息查询.gif");
                            multipartDto.setSpaceCode(gzlslid);
                            StorageDto storageDto = storageClient.multipartUpload(multipartDto);
                            jsonObject.put("XPURL", storageDto.getDownUrl());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                return jsonArray;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 查询个体工商户
     *
     * @param request
     * @return
     */
    @PostMapping("/sheng/gtgshcx")
    public Object gtgshcx(@RequestBody JSONObject request) {
        LOGGER.info("---查询个体工商户:{}", JSON.toJSONString(request));
        String gzlslid = request.getString("gzlslid");
        String name = request.getString("name");
        try {
            Object data = exchangeInterfaceRestService.requestInterface("xc_gtgshcx", request);
            LOGGER.info("---查询个体工商户:{}", JSON.toJSONString(data));
            if (Objects.nonNull(data)) {
                return JSON.parseObject(JSON.toJSONString(data)).getJSONArray("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 验证个体工商户
     *
     * @param request
     * @return
     */
    @PostMapping("/sheng/gtgshyz")
    public Object gtgshyz(@RequestBody JSONObject request) {
        LOGGER.info("---验证个体工商户:{}", JSON.toJSONString(request));
        String gzlslid = request.getString("gzlslid");
        String name = request.getString("name");
        try {
            Object data = exchangeInterfaceRestService.requestInterface("xc_gtgshyz", request);
            LOGGER.info("---验证个体工商户:{}", JSON.toJSONString(data));
            if (Objects.nonNull(data)) {
                return JSON.parseObject(JSON.toJSONString(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * sdk查询企业基本信息
     *
     * @param request
     * @return
     */
    @PostMapping("/sheng/qyxxcx")
    public Object qyxxcx(@RequestBody JSONObject request) {
        LOGGER.info("---sdk查询企业基本信息:{}", JSON.toJSONString(request));
        String gzlslid = request.getString("gzlslid");
        String name = request.getString("name");
        try {
            Object data = exchangeInterfaceRestService.requestInterface("xc_qyxxcx", request);
            LOGGER.info("---sdk查询企业基本信息:{}", JSON.toJSONString(data));
            if (Objects.nonNull(data)) {
                return JSON.parseObject(JSON.toJSONString(data)).getJSONArray("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sdk验证企业基本信息
     *
     * @param request
     * @return
     */
    @PostMapping("/sheng/qyxxyz")
    public Object qyxxyz(@RequestBody JSONObject request) {
        LOGGER.info("---sdk验证企业基本信息:{}", JSON.toJSONString(request));
        String gzlslid = request.getString("gzlslid");
        String name = request.getString("name");
        try {
            Object data = exchangeInterfaceRestService.requestInterface("xc_qyxxyz", request);
            LOGGER.info("---sdk验证企业基本信息:{}", JSON.toJSONString(data));
            if (Objects.nonNull(data)) {
                return JSON.parseObject(JSON.toJSONString(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
