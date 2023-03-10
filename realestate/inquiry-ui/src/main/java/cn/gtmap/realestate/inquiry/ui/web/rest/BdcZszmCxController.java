package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.inquiry.ZipKinDO;
import cn.gtmap.realestate.common.core.dto.BdcQlrxxDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.register.BdcLsgxXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZszmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcSzgzlTjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZsTjQO;
import cn.gtmap.realestate.common.core.qo.register.BdcLsgxQO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyhZtFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcGdxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZsTjVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZsmxTjVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.common.util.office.ExcelUtil;
import cn.gtmap.realestate.inquiry.ui.core.qo.BdcXtOrgQO;
import cn.gtmap.realestate.inquiry.ui.util.ExportUtils;
import cn.gtmap.realestate.inquiry.ui.util.PageUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import cn.gtmap.realestate.inquiry.ui.web.rest.config.ZtreeController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/05/16
 * @description ????????????????????????????????????
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcZszmCxController extends BaseController {
    /**
     * ????????????
     */
    @Value("${html.version:}")
    private String version;

    @Value("${dycx.valid.qlrzjh:true}")
    private Boolean validateQlrzjh;

    /**
     * ??????????????????
     */
    @Autowired
    private BdcZszmCxFeignService bdcZszmCxFeignService;
    /**
     * ????????????????????????
     */
    @Autowired
    private BdcZfxxCxFeignService bdcZfxxCxFeignService;
    /**
     * ?????????????????????
     */
    @Autowired
    private BdcBdcdyFeignService bdcBdcdyFeignService;
    /**
     * ????????????
     */
    @Autowired
    private BdcZdController bdcZdController;
    /**
     * Redis????????????
     */
    @Autowired
    private BdcRedisFeignService bdcRedisFeignService;

    /**
     * zipkin??????????????????
     */
    @Autowired
    private ZipKinFeignService zipKinFeignService;
    /**
     * ????????????
     */
    @Autowired
    private BdcLogFeignService bdcLogFeignService;
    /**
     * ????????????
     */
    @Autowired
    private UserManagerUtils userManagerUtils;
    /**
     * ??????????????????
     */
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    /**
     * ??????????????????
     */
    @Autowired
    BdcGdxxFeignService bdcGdxxFeignService;

    @Autowired
    BdcdyFeignService bdcdyFeignService;

    /**
     * ???????????????????????????
     */
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * ???????????????????????????????????????
     */
    @Value("${zhcx.qllx.sxpz:}")
    private String qllxSxpz;

    /**
     * ????????????????????????(????????????)?????????
     * ?????????: name1:value1,value2;name2:value1;
     */
    @Value("${zhcx.qjss.sxpz:???????????????:340100;?????????:340124;}")
    private String qjssSxpz;

    /**
     * ????????????????????????????????????
     */
    @Value("#{${zhcx.qllxzd:{'qllx_zjd':'?????????'}}}")
    private Map<String,String> qllxzd;

    @Value("${cxtj.sfglbm:false}")
    private boolean sfglbm;

    @Autowired
    ZtreeController ztreeController;

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable  ????????????
     * @param bdcZszmQO ????????????
     * @return {Page} ??????????????????????????????
     * @description ???????????????????????????????????????
     */
    @GetMapping("/zszm")
    public Object listBdcZszm(@LayuiPageable Pageable pageable, BdcZszmQO bdcZszmQO, HttpServletRequest request) {
        if(StringUtils.isBlank(bdcZszmQO.getIpaddress())){
            bdcZszmQO.setIpaddress(IPUtils.getRequestClientRealIP(request));
        }

        //?????????????????????htba??????
        if(ArrayUtils.isNotEmpty(bdcZszmQO.getQllx())){
            List<Integer> qllx = Arrays.asList(bdcZszmQO.getQllx());
            if(qllx.contains(-1)){
                bdcZszmQO.setCxhtba(true);
            }
            //?????????????????????
            if(Objects.nonNull(bdcZszmQO.getQszt2()) && (!bdcZszmQO.getQszt2().equals(1))){
                bdcZszmQO.setCxhtba(false);
            }
            if(Objects.nonNull(bdcZszmQO.getQszt()) && (!bdcZszmQO.getQszt().equals(1))){
                bdcZszmQO.setCxhtba(false);
            }
            //
            if(ArrayUtils.isNotEmpty(bdcZszmQO.getQszt3())){
                List<Integer> qszt = Arrays.asList(bdcZszmQO.getQszt3());
                if (!qszt.contains(1)){
                    bdcZszmQO.setCxhtba(false);
                }
            }
        }
        bdcZszmQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("zhcx","",bdcZszmQO.getModuleCode()));
        Page<BdcZszmDTO> bdcZszmDTOPage = bdcZszmCxFeignService.listBdcZszm(pageable, JSON.toJSONString(bdcZszmQO));
        return super.addLayUiCode(bdcZszmDTOPage);
    }

    /**
     * @param request
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????????????????
     * @date : 2022/6/16 17:42
     */
    @GetMapping("/zszm/mkjk")
    public Object querySfzxxByMkjk(HttpServletRequest request) {
        HashMap<String, String> paramMap = new HashMap<>(2);
        paramMap.put("sysIp", IPPortUtils.getClientIp(request));
        paramMap.put("serviceId", UUIDGenerator.generate16());
        LOGGER.warn("?????????????????????????????????????????????????????????{}", paramMap);
        return exchangeInterfaceFeignService.requestInterface("mk_sfzxx_rh", paramMap);
    }

    /**
     * @return java.lang.Object
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @description ?????????????????????????????????????????????
     */
    @GetMapping("/getQllxSxpz")
    public Object getQllxSxpz(@RequestParam(value = "authority",required = false) String authority) {
        if (StringUtils.isNotBlank(qllxSxpz)) {
            String[] qllxSxpzs = qllxSxpz.split(";");
            List<String> qllxSxpzList = Arrays.asList(qllxSxpzs);
            //????????????????????????
            UserDto user = userManagerUtils.getCurrentUser();
            boolean isAdmin = false;
            if(user != null && StringUtils.isNotBlank(user.getId())){
                isAdmin = userManagerUtils.isAdminByUserId(user.getId());
            }
            if (StringUtils.isNotBlank(authority) && !isAdmin){
                //????????????????????????????????????????????????
                StringBuilder qllxStr = new StringBuilder();
                Map<String,String> authorityMap = JSON.parseObject(authority,Map.class);
                for (Map.Entry<String,String> entry : authorityMap.entrySet()){
                    if (entry.getKey().startsWith("qllx_") && "available".equals(entry.getValue())
                            && StringUtils.isNotBlank(qllxzd.get(entry.getKey()))){
                        String str = qllxSxpzList.stream().filter(o -> o.contains(qllxzd.get(entry.getKey()))).findFirst().orElse("");
                        if (StringUtils.isNotBlank(str)){
                            str = str.contains("checked")?str:str+" checked";
                            qllxStr.append(str).append(";");
                        }
                    }
                }
                if (StringUtils.isNotBlank(qllxStr)){
                    String qllx = qllxStr.substring(0,qllxStr.lastIndexOf(";"));
                    if (StringUtils.isNotBlank(qllx)) {
                        return new HashSet<>(Arrays.asList(qllx.split(";")));
                    }
                }
            }
            return qllxSxpzList;
        }
        return null;
    }

    /**
     * @param bdcLsgxQO ????????????QO
     * @return {BdcQl} ??????????????????
     * @description ?????????????????????????????????
     */
    @GetMapping("/bdcdy/getlsgx")
    public Object getXmly(@LayuiPageable Pageable pageable, BdcLsgxQO bdcLsgxQO) {
        Page<BdcLsgxXmDTO> bdcXmDOPage = bdcBdcdyFeignService.listBdcdyLsgx(pageable, JSON.toJSONString(bdcLsgxQO));
        return super.addLayUiCode(bdcXmDOPage);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  ??????????????????
     */
    @GetMapping("/zszm/userinfo")
    public UserDto getCurrentUserInfo(){
        return userManagerUtils.getCurrentUser();
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  xmidList ??????ID????????????
     * @return {List} ??????????????????????????????
     * @description  ??????XMID????????????????????????????????????
     */
    @PostMapping("/zszm/fjxx")
    public List<BdcZhcxDTO> listBdcZszm(@RequestBody List<String> xmidList) {
        if(CollectionUtils.isEmpty(xmidList)){
            return Collections.emptyList();
        }
        return bdcZszmCxFeignService.listZhcxFjxx(xmidList);
    }

    /**
     * @param zsid ??????ID
     * @return {List} ????????????????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ???????????????????????????????????????????????????
     */
    @GetMapping("/zszm/{zsid}/bdcdyh")
    public List<String> listBdcZszmBdcdyh(@PathVariable("zsid") String zsid) {
        if (StringUtils.isBlank(zsid)) {
            throw new NullPointerException("????????????????????????????????????????????????????????????????????????");
        }

        return bdcZszmCxFeignService.listBdcZszmBdcdyh(zsid);
    }

    /**
     * @param pageable  ????????????
     * @param bdcZszmQO ????????????
     * @return {Page} ??????????????????????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ????????????ID???????????????????????????
     */
    @GetMapping("/zszm/xmxx")
    public Object listBdcZszmXmxx(@LayuiPageable Pageable pageable, BdcZszmQO bdcZszmQO) {
        if (null == bdcZszmQO || StringUtils.isBlank(bdcZszmQO.getZsid())) {
            throw new NullPointerException("??????????????????????????????ID??????????????????????????????????????????");
        }

        Page<BdcXmDO> bdcXmDOPage = bdcZszmCxFeignService.listBdcZszmXmxx(pageable, JSON.toJSONString(bdcZszmQO));
        return super.addLayUiCode(bdcXmDOPage);
    }

    /**
     * @param bdcdyh ??????????????????
     * @return {String} XML??????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ?????????????????????????????????
     */
    @GetMapping("/zfxx/{bdcdyh}/fcda")
    public BdcFcdaDTO getBdcZfxxFcda(@PathVariable("bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new NullPointerException("?????????????????????????????????????????????????????????");
        }
        return bdcZfxxCxFeignService.getBdcFcdaDTO(bdcdyh,"");
    }

    /**
     * @param bdcdyhList ??????????????????
     * @return {Boolean} ?????????  0 ; ?????? 1
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ????????????????????????????????????????????????
     */
    @PostMapping("/zfxx/fcda")
    public Integer getBdcZfxxFcda(@RequestBody List<String> bdcdyhList) {
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            throw new NullPointerException("?????????????????????????????????????????????????????????");
        }

        for (String bdcdyh : bdcdyhList) {
            BdcFcdaDTO bdcFcdaDTO = bdcZfxxCxFeignService.getBdcFcdaDTO(bdcdyh,"");
            if (null == bdcFcdaDTO || null == bdcFcdaDTO.getFdcq() || CollectionUtils.isEmpty(bdcFcdaDTO.getZfxx())) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * @param bdcZsTjQO
     * @return {List<BdcZsTjVO>}
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ??????????????????
     */
    @PostMapping("/zszm/count")
    public Object zszmCount(@RequestBody BdcZsTjQO bdcZsTjQO) {
        if(sfglbm && StringUtils.isBlank(bdcZsTjQO.getDjbmdm())){
            List<OrganizationDto> organizationDtos = ztreeController.queryGlOrgs(new BdcXtOrgQO());
            if (CollectionUtils.isNotEmpty(organizationDtos)){
                bdcZsTjQO.setDjbmdm(organizationDtos.stream().map(OrganizationDto::getCode).collect(Collectors.joining(",")));
            }
        }
        String zstjParamJson = JSON.toJSONString(bdcZsTjQO);
        List<BdcZsTjVO> bdcZsTjVOS = bdcZszmCxFeignService.listBdcZsTj(zstjParamJson);
        if (CollectionUtils.isNotEmpty(bdcZsTjVOS)) {
            Map<String, String> param = bdcZdController.queryAllBmDmAndMc();
            bdcZsTjVOS.forEach(bdcZsTjVO -> {
                bdcZsTjVO.setDjjg(StringUtils.isNotBlank(param.get(bdcZsTjVO.getDjbmdm()))?param.get(bdcZsTjVO.getDjbmdm()):bdcZsTjVO.getDjbmdm());
            });
        }
        return bdcZsTjVOS;
    }

    /**
     * @param bdcZsTjQO
     * @return {List<BdcZsTjVO>}
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description ??????????????????????????????
     */
    @GetMapping("/zszm/detail")
    public Object zszmDetail(@LayuiPageable Pageable pageable, BdcZsTjQO bdcZsTjQO) {
        String zstjParamJson = JSON.toJSONString(bdcZsTjQO);
        List<BdcZsmxTjVO> bdcZsTjVOS = bdcZszmCxFeignService.listBdcZsmxTj(pageable, zstjParamJson);
        if (CollectionUtils.isNotEmpty(bdcZsTjVOS)) {
            Map<String, String> param = bdcZdController.queryAllBmDmAndMc();
            bdcZsTjVOS.forEach(bdcZsTjVO -> {
                bdcZsTjVO.setDjjg(StringUtils.isNotBlank(param.get(bdcZsTjVO.getDjbmdm()))?param.get(bdcZsTjVO.getDjbmdm()):bdcZsTjVO.getDjbmdm());
            });
        }
        return super.addLayUiCode(PageUtils.listToPage(bdcZsTjVOS, pageable));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZsTjQO ????????????
     * @description ??????????????????????????????????????????????????????
     */
    @PostMapping("/zszm/print/count")
    public Object countZszmPrint(@RequestBody BdcZsTjQO bdcZsTjQO) {
        return bdcLogFeignService.countZszmPrint(bdcZsTjQO);
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param xmid
     * @return
     */
    @GetMapping("/zszm/tellTdFromBdc")
    public Object tellTdFromBdc(@RequestParam("xmid") String xmid) {
        Map config = bdcBdcdyFeignService.queryXmly(xmid);
        config.put("version", version);
        return config;
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcGzYzQO] ????????????????????????
     * @return: List<Map < String , Object>> ???????????? ??????????????????  ccx 2019-10-04
     * @description
     */
    @PostMapping("/zhcx/dyzm/gzyz/{zhbs}")
    public List<Map<String, Object>> checkBdcdyhStatus(@RequestBody BdcGzYzQO bdcGzYzQO, @PathVariable("zhbs") String zhbs) {
        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? by zhuyong 20191128
        if(StringUtils.isBlank(zhbs)){
            throw new AppException("??????????????????????????????????????????");
        }
        bdcGzYzQO.setZhbs(zhbs);
        return this.bdcZszmCxFeignService.checkBdcdyhGz(bdcGzYzQO);
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param gzlslid
     * @return num
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/zfxx/getFdcqOrDyaqCount")
    public Integer getFdcqCount(@RequestParam(value = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new NullPointerException("???????????????????????????????????????");
        }
        Integer fdcqNum = bdcZszmCxFeignService.getFdcqCount(gzlslid);
        Integer dyaNum = bdcZszmCxFeignService.getDyaqCount(gzlslid);
        if (fdcqNum > 1) {
            return 1;
        }
        if (dyaNum >= 1) {
            return 2;
        }
        return 0;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/zszm/zszmCount")
    public Object getZszmCount(@LayuiPageable Pageable pageable, BdcZsTjQO bdcZsTjQO) {
        Page<BdcZsTjDTO> bdcZsTjDTOPage = bdcZszmCxFeignService.getZszmCount(pageable, JSON.toJSONString(bdcZsTjQO));
        return super.addLayUiCode(bdcZsTjDTOPage);

    }

    /**
     * ??????????????????????????????????????? ??????Excel
     *
     * @param bdcZsTjQO
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @GetMapping("/zszm/zszmCount/excel")
    public Object getZszmCountExcel(BdcZsTjQO bdcZsTjQO) {
        return bdcZszmCxFeignService.getZszmCountExcel(JSON.toJSONString(bdcZsTjQO));
    }

    @GetMapping("/status/color")
    @ResponseBody
    public Object queryColorPz(){
        String xtPzColor = bdcRedisFeignService.getStringValue("xtPzColor");
        return JSONObject.parseObject(xtPzColor);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/zszm/zszmCountList")
    public Object getZszmCountList(BdcZsTjQO bdcZsTjQO) {
        List<BdcZsTjDTO> bdcZsTjDTOList = bdcZszmCxFeignService.getZszmCountList(JSON.toJSONString(bdcZsTjQO));
        return  super.addLayUiCode(bdcZsTjDTOList);
    }

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param pageable  ????????????
     * @param bdcZszmQO ????????????
     * @return {Page} ??????????????????????????????
     * @description ??????????????????
     * <p> 1??????????????????????????????????????????????????????????????????????????????????????????????????????
     * ??????????????????????????????????????????????????????????????????????????????????????????
     */
    @GetMapping("/zszm/dycx")
    public Object listBdcZszmForDycx(@LayuiPageable Pageable pageable, BdcZszmQO bdcZszmQO) {
        if(validateQlrzjh){
            if(bdcZszmQO.getQlrmc().length == 0){
                throw new AppException("????????????????????????????????????????????????");
            }
            List<String> qlrmc = Arrays.asList(bdcZszmQO.getQlrmc());
            if(bdcZszmCxFeignService.getQlrZjhNullCount(qlrmc) > 0){
                return super.addLayUiErrorCode("????????????????????????????????????????????????");
            }
        }
        Page<BdcZszmDTO> bdcZszmDTOPage = bdcZszmCxFeignService.listBdcZszm(pageable, JSON.toJSONString(bdcZszmQO));
        return super.addLayUiCode(bdcZszmDTOPage);
    }

    /**
     * @author <a href="mailto:wangyinghao@gtmap.cn">wyh</a>
     * @params []
     * @return java.lang.Object
     * @description ??????????????????????????????(????????????)?????????????????????
     */
    @GetMapping("/getQjssSxpz")
    public Object getgetQjssSxpz(){
        List<String> qjssSxpzsList = new ArrayList<>();
        if(StringUtils.isNotBlank(qjssSxpz)){
            String[] qjssSxpzsSplit = qjssSxpz.split(";");
            if(qjssSxpzsSplit.length > 0) {
                //????????????????????????
                List<String> qjgldmList =Container.getBean(BdcConfigUtils.class).qjgldmFilter("zhcx");
                for (String qjssSxpzs : qjssSxpzsSplit) {
                    String[] qjssSxpz =qjssSxpzs.split(CommonConstantUtils.ZF_YW_MH);
                    //????????????
                    if(CollectionUtils.isEmpty(qjgldmList) ||(qjssSxpz.length >1 &&qjgldmList.contains(qjssSxpz[1]))) {
                        qjssSxpzsList.add(StringUtils.trim(qjssSxpzs));
                    }
                }
            }
        }
        return qjssSxpzsList;
    }

    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @param
    * @return 
    * @description ???????????????????????????excel
    **/
    @ResponseBody
    @RequestMapping("/zszm/getSzgzlExcel")
    public String getSzgzlExcel(String paramJson , String exportCol ,HttpServletResponse response) throws IOException, WriteException {
        String filename = "?????????????????????" + System.currentTimeMillis() + ".xls";
        paramJson = URLDecoder.decode(paramJson, "utf-8");
        BdcSzgzlTjQO bdcSzgzlTjQO = JSONObject.parseObject(paramJson, BdcSzgzlTjQO.class);
        exportCol = URLDecoder.decode(exportCol, "utf-8");
        LinkedHashMap exportColMap = JSONObject.parseObject(exportCol, LinkedHashMap.class);
        List<BdcSzgzlTjDTO> bdcSzgzlTjDTOList = bdcZszmCxFeignService.getSzgzl(bdcSzgzlTjQO);
        ExportUtils.exportExcel(filename, exportColMap ,JSON.parseArray(JSON.toJSONString(bdcSzgzlTjDTOList), Map.class),response);
        return "????????????";
    }

    /**
     * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
     * @param
     * @return
     * @description ??????Excel????????????
     **/
    @ResponseBody
    @PostMapping("/zszm/importExcelSearch")
    public List importExcelSearch(MultipartHttpServletRequest httpServletRequest) throws IOException, WriteException {
        if (httpServletRequest == null){
            return null;
        }
        InputStream fileIn = null;
        List<BdcQlrxxDTO> bdcQlrxxList = new ArrayList<>();
        try{
            Iterator<String> iterator = httpServletRequest.getFileNames();
            // ????????????????????????
            while (iterator.hasNext()){
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // ????????????????????????????????????Excel????????????Workbook??????
                Workbook workbook = Workbook.getWorkbook(fileIn);
                // ???????????????
                BdcQlrxxDTO dtcxCx = new BdcQlrxxDTO();
                bdcQlrxxList = exportToObject(BdcQlrxxDTO.class,0,workbook);
                // ???????????????????????????
                bdcQlrxxList.remove(0);
            }

        }catch (IOException | BiffException | IllegalAccessException | InstantiationException | ParseException ex){
            LOGGER.error(ex.getMessage(),ex);
            throw new AppException("????????????");
        }finally{
            if (fileIn != null){
                try{
                    fileIn.close();
                }catch (IOException ex){
                    LOGGER.error(ex.getMessage(),ex);
                }
            }
        }

        return bdcQlrxxList;
    }

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description ?????????Excel?????????????????????????????????
    */
    @ResponseBody
    @GetMapping("/zszm/excelSearchResult")
    public Object excelSearchResult(@LayuiPageable Pageable pageable,@RequestParam("data") String data){
        List<BdcQlrxxDTO> bdcQlrxxDTOList = JSON.parseArray(data,BdcQlrxxDTO.class);
        BdcZszmQO bdcZszmQO = new BdcZszmQO();
        StringBuilder qlrArr = new StringBuilder();
        StringBuilder qlrzjhArr = new StringBuilder();
        for (BdcQlrxxDTO dto : bdcQlrxxDTOList){
            qlrArr.append(dto.getQlrmc()).append(",");
            qlrzjhArr.append(dto.getZjh()).append(",");
        }
        String[] qlrmc = qlrArr.substring(0,qlrArr.lastIndexOf(",")).split(",");
        String[] qlrzjh = qlrzjhArr.substring(0,qlrzjhArr.lastIndexOf(",")).split(",");
        bdcZszmQO.setQlrmc(qlrmc);
        bdcZszmQO.setQlrzjh(qlrzjh);
        bdcZszmQO.setQjgldm("341523");
        if (bdcZszmQO.getQlrmc().length==0 && bdcZszmQO.getQlrzjh().length ==0 ){
            return super.addLayUiErrorCode("?????????????????????");
        }
        Page<BdcZszmDTO> bdcZszmDTOPage = bdcZszmCxFeignService.listBdcZszm(pageable,JSON.toJSONString(bdcZszmQO));
        return super.addLayUiCode(bdcZszmDTOPage);
    }

    /**
     * ??????sheet??????????????????Object
     *
     * @param dataClass   ????????????
     * @param sheetNumber ????????????
     * @param workbook    ????????????
     * @return List ????????????
     */
    private static List exportToObject(Class dataClass,Integer sheetNumber,Workbook workbook) throws InstantiationException, IllegalAccessException, ParseException{
        Sheet dataSheet = workbook.getSheet(sheetNumber);
        Field[] fieldExportDataList = dataClass.getDeclaredFields();
        List dataList = new ArrayList();
        // ??????Excel
        for (int i = 1; i < dataSheet.getRows(); i++){
            Object dataObject = dataClass.newInstance();
            for (int j = 0; j < fieldExportDataList.length; j++){
                fieldExportDataList[j].setAccessible(true);
                String contentData = dataSheet.getCell(j,i).getContents();
                // ?????????????????????
                if (StringUtils.equals(fieldExportDataList[j].getType().toString(),"class java.lang.String")){
                    fieldExportDataList[j].set(dataObject,contentData);
                }else if ((StringUtils.equals(fieldExportDataList[j].getType().toString(),"int")
                        || StringUtils.equals(fieldExportDataList[j].getType().toString(),"class java.lang.Integer"))
                        && StringUtils.isNotBlank(contentData)){
                    fieldExportDataList[j].set(dataObject,Integer.valueOf(contentData));
                }else if (StringUtils.equals(fieldExportDataList[j].getType().toString(),"class java.util.Date")
                        && StringUtils.isNotBlank(contentData)){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.ROOT);
                    Date dateData = simpleDateFormat.parse(contentData);
                    fieldExportDataList[j].set(dataObject,dateData);
                }
            }
            dataList.add(dataObject);
        }
        return dataList;
    }

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description ?????????????????????????????????
    */
    @GetMapping("/export")
    public CommonResponse export(ZipKinDTO zipKinDTO) throws IOException, WriteException {
        CommonResponse<ZipkinExportDTO> response1 = new CommonResponse();
        Integer num = zipKinDTO.getNum();
        String startDate = "";
        String endDate = "";
        if (zipKinDTO.getStartDate() != null){
            startDate = DateUtils.formateTime(zipKinDTO.getStartDate(),DateUtils.DATE_FORMAT_2);
        }
        if (zipKinDTO.getEndDate() != null){
            endDate = DateUtils.formateTime(zipKinDTO.getEndDate(),DateUtils.DATE_FORMAT_2);
        }
        ZipkinExportDTO dto = zipKinFeignService.getExportExcel(num,startDate,endDate);
        response1.setData(dto);
        return response1;
    }
    /**
     * ????????????????????????????????????
     * @param bdcZszmQOList ??????QO????????????????????????bdcdyh???qjgldm??????
     * @return ????????????
     */
    @ResponseBody
    @PostMapping("/zszm/bdcdyh/xzzt")
    public List<BdcdyhZtResponseDTO> queryBdcdyhXzzt(@RequestBody List<BdcZszmQO> bdcZszmQOList){
        if(CollectionUtils.isEmpty(bdcZszmQOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "???????????????????????????????????????");
        }
        List<String> bdcdyhList = new ArrayList<>(bdcZszmQOList.size());
        // ??????????????????????????????????????????????????????
        Map<String,List<String>> qjgldmBdcdyhMap =new HashMap<>();
        for(BdcZszmQO bdcZszmQO : bdcZszmQOList){
            if(StringUtils.isNotBlank(bdcZszmQO.getQjgldm())) {
                if (qjgldmBdcdyhMap.containsKey(bdcZszmQO.getQjgldm())) {
                    List<String> dyhList = qjgldmBdcdyhMap.get(bdcZszmQO.getQjgldm());
                    dyhList.add(bdcZszmQO.getBdcdyh());
                    qjgldmBdcdyhMap.put(bdcZszmQO.getQjgldm(),dyhList);
                }else{
                    List<String> dyhList =new ArrayList<>();
                    dyhList.add(bdcZszmQO.getBdcdyh());
                    qjgldmBdcdyhMap.put(bdcZszmQO.getQjgldm(),dyhList);

                }
            }else{
                bdcdyhList.add(bdcZszmQO.getBdcdyh());
            }
        }
        if (CollectionUtils.isEmpty(bdcdyhList) && MapUtils.isEmpty(qjgldmBdcdyhMap)) {
            return new ArrayList();
        }

        // ???????????????????????????????????????????????????qjgldmBdcdyhMap???
        if(MapUtils.isNotEmpty(qjgldmBdcdyhMap) && CollectionUtils.isNotEmpty(bdcdyhList)){
            qjgldmBdcdyhMap.put("", bdcdyhList);
        }

        /// 2???????????????????????????
        List<BdcdyhZtResponseDTO> bdcdyhZtDTOList =new ArrayList<>();
        if(MapUtils.isNotEmpty(qjgldmBdcdyhMap)){
            for (Map.Entry<String, List<String>> entry : qjgldmBdcdyhMap.entrySet()) {
                bdcdyhZtDTOList.addAll(bdcdyZtFeignService.commonListBdcdyhZtPlcx(entry.getValue(),entry.getKey()));
            }
        }else {
            bdcdyhZtDTOList = bdcdyZtFeignService.commonListBdcdyhZtPlcx(bdcdyhList, "");
        }
        return bdcdyhZtDTOList;
    }

    /**
     * ?????????????????????????????????????????????
     */
    @GetMapping("/zszm/yzlpb")
    public Boolean verifyBuildingByBdcdyh(@RequestParam("bdcdyh") String bdcdyh){
        FwHsDO fwHsDO = bdcdyFeignService.queryHsByBdcdyh(bdcdyh,"","");
        if (fwHsDO != null){
            if (StringUtils.isBlank(fwHsDO.getFwDcbIndex())){
                return true;
            }else {
                return false;
            }
        }
        return true;
    }
}
