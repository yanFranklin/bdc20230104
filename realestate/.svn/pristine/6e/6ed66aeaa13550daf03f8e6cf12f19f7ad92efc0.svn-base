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
 * @description 查询子系统：证书证明查询
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcZszmCxController extends BaseController {
    /**
     * 版本信息
     */
    @Value("${html.version:}")
    private String version;

    @Value("${dycx.valid.qlrzjh:true}")
    private Boolean validateQlrzjh;

    /**
     * 综合查询服务
     */
    @Autowired
    private BdcZszmCxFeignService bdcZszmCxFeignService;
    /**
     * 住房信息查询服务
     */
    @Autowired
    private BdcZfxxCxFeignService bdcZfxxCxFeignService;
    /**
     * 不动产单元服务
     */
    @Autowired
    private BdcBdcdyFeignService bdcBdcdyFeignService;
    /**
     * 字典服务
     */
    @Autowired
    private BdcZdController bdcZdController;
    /**
     * Redis调用服务
     */
    @Autowired
    private BdcRedisFeignService bdcRedisFeignService;

    /**
     * zipkin链路信息服务
     */
    @Autowired
    private ZipKinFeignService zipKinFeignService;
    /**
     * 日志服务
     */
    @Autowired
    private BdcLogFeignService bdcLogFeignService;
    /**
     * 用户信息
     */
    @Autowired
    private UserManagerUtils userManagerUtils;
    /**
     * 项目信息服务
     */
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    /**
     * 归档信息服务
     */
    @Autowired
    BdcGdxxFeignService bdcGdxxFeignService;

    @Autowired
    BdcdyFeignService bdcdyFeignService;

    /**
     * 不动产单元状态查询
     */
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 综合查询权利类型筛选配置项
     */
    @Value("${zhcx.qllx.sxpz:}")
    private String qllxSxpz;

    /**
     * 综合查询权籍筛选(行政区划)配置项
     * 格式为: name1:value1,value2;name2:value1;
     */
    @Value("${zhcx.qjss.sxpz:合肥市本级:340100;庐江县:340124;}")
    private String qjssSxpz;

    /**
     * 权限系统中元素编码的对照
     */
    @Value("#{${zhcx.qllxzd:{'qllx_zjd':'宅基地'}}}")
    private Map<String,String> qllxzd;

    @Value("${cxtj.sfglbm:false}")
    private boolean sfglbm;

    @Autowired
    ZtreeController ztreeController;

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable  分页对象
     * @param bdcZszmQO 查询条件
     * @return {Page} 证书证明查询分页数据
     * @description （南通、合肥公共）综合查询
     */
    @GetMapping("/zszm")
    public Object listBdcZszm(@LayuiPageable Pageable pageable, BdcZszmQO bdcZszmQO, HttpServletRequest request) {
        if(StringUtils.isBlank(bdcZszmQO.getIpaddress())){
            bdcZszmQO.setIpaddress(IPUtils.getRequestClientRealIP(request));
        }

        //判断是否要查询htba数据
        if(ArrayUtils.isNotEmpty(bdcZszmQO.getQllx())){
            List<Integer> qllx = Arrays.asList(bdcZszmQO.getQllx());
            if(qllx.contains(-1)){
                bdcZszmQO.setCxhtba(true);
            }
            //只查有效的数据
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
     * @description 调用摩科接口读取身份证信息
     * @date : 2022/6/16 17:42
     */
    @GetMapping("/zszm/mkjk")
    public Object querySfzxxByMkjk(HttpServletRequest request) {
        HashMap<String, String> paramMap = new HashMap<>(2);
        paramMap.put("sysIp", IPPortUtils.getClientIp(request));
        paramMap.put("serviceId", UUIDGenerator.generate16());
        LOGGER.warn("综合查询页面点击身份证读取调用接口入参{}", paramMap);
        return exchangeInterfaceFeignService.requestInterface("mk_sfzxx_rh", paramMap);
    }

    /**
     * @return java.lang.Object
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @description 综合查询页面权利类型筛选项加载
     */
    @GetMapping("/getQllxSxpz")
    public Object getQllxSxpz(@RequestParam(value = "authority",required = false) String authority) {
        if (StringUtils.isNotBlank(qllxSxpz)) {
            String[] qllxSxpzs = qllxSxpz.split(";");
            List<String> qllxSxpzList = Arrays.asList(qllxSxpzs);
            //判断是否为管理员
            UserDto user = userManagerUtils.getCurrentUser();
            boolean isAdmin = false;
            if(user != null && StringUtils.isNotBlank(user.getId())){
                isAdmin = userManagerUtils.isAdminByUserId(user.getId());
            }
            if (StringUtils.isNotBlank(authority) && !isAdmin){
                //根据配置以及权限显示哪些权利类型
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
     * @param bdcLsgxQO 历史关系QO
     * @return {BdcQl} 权利信息实体
     * @description 查询项目关联的权利信息
     */
    @GetMapping("/bdcdy/getlsgx")
    public Object getXmly(@LayuiPageable Pageable pageable, BdcLsgxQO bdcLsgxQO) {
        Page<BdcLsgxXmDTO> bdcXmDOPage = bdcBdcdyFeignService.listBdcdyLsgx(pageable, JSON.toJSONString(bdcLsgxQO));
        return super.addLayUiCode(bdcXmDOPage);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  获取当前用户
     */
    @GetMapping("/zszm/userinfo")
    public UserDto getCurrentUserInfo(){
        return userManagerUtils.getCurrentUser();
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  xmidList 项目ID集合参数
     * @return {List} 综合查询附加展示信息
     * @description  根据XMID查询综合查询附加显示信息
     */
    @PostMapping("/zszm/fjxx")
    public List<BdcZhcxDTO> listBdcZszm(@RequestBody List<String> xmidList) {
        if(CollectionUtils.isEmpty(xmidList)){
            return Collections.emptyList();
        }
        return bdcZszmCxFeignService.listZhcxFjxx(xmidList);
    }

    /**
     * @param zsid 证书ID
     * @return {List} 不动产单元号集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取证书证明关联的不动产单元号集合
     */
    @GetMapping("/zszm/{zsid}/bdcdyh")
    public List<String> listBdcZszmBdcdyh(@PathVariable("zsid") String zsid) {
        if (StringUtils.isBlank(zsid)) {
            throw new NullPointerException("查询子系统：证书证明查看登记历史关系未选中记录！");
        }

        return bdcZszmCxFeignService.listBdcZszmBdcdyh(zsid);
    }

    /**
     * @param pageable  分页对象
     * @param bdcZszmQO 查询条件
     * @return {Page} 证书证明查询分页数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据证书ID获取关联的项目信息
     */
    @GetMapping("/zszm/xmxx")
    public Object listBdcZszmXmxx(@LayuiPageable Pageable pageable, BdcZszmQO bdcZszmQO) {
        if (null == bdcZszmQO || StringUtils.isBlank(bdcZszmQO.getZsid())) {
            throw new NullPointerException("查询子系统：根据证书ID获取关联的项目信息入参为空！");
        }

        Page<BdcXmDO> bdcXmDOPage = bdcZszmCxFeignService.listBdcZszmXmxx(pageable, JSON.toJSONString(bdcZszmQO));
        return super.addLayUiCode(bdcXmDOPage);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取打印房产档案的数据
     */
    @GetMapping("/zfxx/{bdcdyh}/fcda")
    public BdcFcdaDTO getBdcZfxxFcda(@PathVariable("bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new NullPointerException("查询子系统：获取房产档案信息参数为空！");
        }
        return bdcZfxxCxFeignService.getBdcFcdaDTO(bdcdyh,"");
    }

    /**
     * @param bdcdyhList 不动产单元号
     * @return {Boolean} 不存在  0 ; 存在 1
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断要打印的房产档案信息是否存在
     */
    @PostMapping("/zfxx/fcda")
    public Integer getBdcZfxxFcda(@RequestBody List<String> bdcdyhList) {
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            throw new NullPointerException("查询子系统：获取房产档案信息参数为空！");
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
     * @description 证书证明统计
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
     * @description 盐城证书打印明细统计
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
     * @param bdcZsTjQO 统计参数
     * @description 统计打印的证书、证明、证明单数量信息
     */
    @PostMapping("/zszm/print/count")
    public Object countZszmPrint(@RequestBody BdcZsTjQO bdcZsTjQO) {
        return bdcLogFeignService.countZszmPrint(bdcZsTjQO);
    }

    /**
     * 查询项目来源以及档案调用的其他参数
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
     * @param: [bdcGzYzQO] 规则验证查询参数
     * @return: List<Map < String , Object>> 验证结果 增加查看项目  ccx 2019-10-04
     * @description
     */
    @PostMapping("/zhcx/dyzm/gzyz/{zhbs}")
    public List<Map<String, Object>> checkBdcdyhStatus(@RequestBody BdcGzYzQO bdcGzYzQO, @PathVariable("zhbs") String zhbs) {
        // 修改组合标识，旧的只验证正在办理其它登记，现在用于多个证明，动态传参调用不同规则 by zhuyong 20191128
        if(StringUtils.isBlank(zhbs)){
            throw new AppException("验证异常：未指定组合规则标识");
        }
        bdcGzYzQO.setZhbs(zhbs);
        return this.bdcZszmCxFeignService.checkBdcdyhGz(bdcGzYzQO);
    }

    /**
     * 以此区别是一证多房还是在建工程抵押
     *
     * @param gzlslid
     * @return num
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/zfxx/getFdcqOrDyaqCount")
    public Integer getFdcqCount(@RequestParam(value = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new NullPointerException("查询子系统：查询参数为空！");
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
     * 根据部门，时间统计证书数量
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
     * 根据部门，时间统计证书数量 导出Excel
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
     * 根据部门，时间统计证书数量
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
     * @param pageable  分页对象
     * @param bdcZszmQO 查询条件
     * @return {Page} 证书证明查询分页数据
     * @description 打印查询功能
     * <p> 1、通过权利人名称查询证件号为空的项目数据，存在数据时直接返回提示信息
     * 不存在数据时，返回权利人名称与权利人证件号精确查询的数据结果
     */
    @GetMapping("/zszm/dycx")
    public Object listBdcZszmForDycx(@LayuiPageable Pageable pageable, BdcZszmQO bdcZszmQO) {
        if(validateQlrzjh){
            if(bdcZszmQO.getQlrmc().length == 0){
                throw new AppException("未获取到查询参数权利人名称信息。");
            }
            List<String> qlrmc = Arrays.asList(bdcZszmQO.getQlrmc());
            if(bdcZszmCxFeignService.getQlrZjhNullCount(qlrmc) > 0){
                return super.addLayUiErrorCode("存在权利人信息缺失情况，请核实。");
            }
        }
        Page<BdcZszmDTO> bdcZszmDTOPage = bdcZszmCxFeignService.listBdcZszm(pageable, JSON.toJSONString(bdcZszmQO));
        return super.addLayUiCode(bdcZszmDTOPage);
    }

    /**
     * @author <a href="mailto:wangyinghao@gtmap.cn">wyh</a>
     * @params []
     * @return java.lang.Object
     * @description 综合查询页面权籍所属(行政区划)筛选配置项加载
     */
    @GetMapping("/getQjssSxpz")
    public Object getgetQjssSxpz(){
        List<String> qjssSxpzsList = new ArrayList<>();
        if(StringUtils.isNotBlank(qjssSxpz)){
            String[] qjssSxpzsSplit = qjssSxpz.split(";");
            if(qjssSxpzsSplit.length > 0) {
                //权籍管理代码过滤
                List<String> qjgldmList =Container.getBean(BdcConfigUtils.class).qjgldmFilter("zhcx");
                for (String qjssSxpzs : qjssSxpzsSplit) {
                    String[] qjssSxpz =qjssSxpzs.split(CommonConstantUtils.ZF_YW_MH);
                    //过滤匹配
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
    * @description 缮证工作量统计导出excel
    **/
    @ResponseBody
    @RequestMapping("/zszm/getSzgzlExcel")
    public String getSzgzlExcel(String paramJson , String exportCol ,HttpServletResponse response) throws IOException, WriteException {
        String filename = "缮证工作量统计" + System.currentTimeMillis() + ".xls";
        paramJson = URLDecoder.decode(paramJson, "utf-8");
        BdcSzgzlTjQO bdcSzgzlTjQO = JSONObject.parseObject(paramJson, BdcSzgzlTjQO.class);
        exportCol = URLDecoder.decode(exportCol, "utf-8");
        LinkedHashMap exportColMap = JSONObject.parseObject(exportCol, LinkedHashMap.class);
        List<BdcSzgzlTjDTO> bdcSzgzlTjDTOList = bdcZszmCxFeignService.getSzgzl(bdcSzgzlTjQO);
        ExportUtils.exportExcel(filename, exportColMap ,JSON.parseArray(JSON.toJSONString(bdcSzgzlTjDTOList), Map.class),response);
        return "导出成功";
    }

    /**
     * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
     * @param
     * @return
     * @description 舒城Excel导入查询
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
            // 遍历所有上传文件
            while (iterator.hasNext()){
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                // 根据指定的文件输入流导入Excel从而产生Workbook对象
                Workbook workbook = Workbook.getWorkbook(fileIn);
                // 创建实体类
                BdcQlrxxDTO dtcxCx = new BdcQlrxxDTO();
                bdcQlrxxList = exportToObject(BdcQlrxxDTO.class,0,workbook);
                // 去除模板中的第一行
                bdcQlrxxList.remove(0);
            }

        }catch (IOException | BiffException | IllegalAccessException | InstantiationException | ParseException ex){
            LOGGER.error(ex.getMessage(),ex);
            throw new AppException("导入失败");
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
    * @description 转换从Excel中解析的数据并进行查询
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
            return super.addLayUiErrorCode("请勿传入空数据");
        }
        Page<BdcZszmDTO> bdcZszmDTOPage = bdcZszmCxFeignService.listBdcZszm(pageable,JSON.toJSONString(bdcZszmQO));
        return super.addLayUiCode(bdcZszmDTOPage);
    }

    /**
     * 读取sheet页并写入对应Object
     *
     * @param dataClass   导出类名
     * @param sheetNumber 导出页号
     * @param workbook    导出文件
     * @return List 数据列表
     */
    private static List exportToObject(Class dataClass,Integer sheetNumber,Workbook workbook) throws InstantiationException, IllegalAccessException, ParseException{
        Sheet dataSheet = workbook.getSheet(sheetNumber);
        Field[] fieldExportDataList = dataClass.getDeclaredFields();
        List dataList = new ArrayList();
        // 遍历Excel
        for (int i = 1; i < dataSheet.getRows(); i++){
            Object dataObject = dataClass.newInstance();
            for (int j = 0; j < fieldExportDataList.length; j++){
                fieldExportDataList[j].setAccessible(true);
                String contentData = dataSheet.getCell(j,i).getContents();
                // 判断属性的类型
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
    * @description 获取当天最耗时服务数据
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
     * 查询不动产单元号限制状态
     * @param bdcZszmQOList 查询QO对象集合，主要传bdcdyh和qjgldm字段
     * @return 限制状态
     */
    @ResponseBody
    @PostMapping("/zszm/bdcdyh/xzzt")
    public List<BdcdyhZtResponseDTO> queryBdcdyhXzzt(@RequestBody List<BdcZszmQO> bdcZszmQOList){
        if(CollectionUtils.isEmpty(bdcZszmQOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产单元号信息。");
        }
        List<String> bdcdyhList = new ArrayList<>(bdcZszmQOList.size());
        // 根据权籍管理代码对不动产单元集合分组
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

        // 将没有权籍管理代码的单元号，加入到qjgldmBdcdyhMap中
        if(MapUtils.isNotEmpty(qjgldmBdcdyhMap) && CollectionUtils.isNotEmpty(bdcdyhList)){
            qjgldmBdcdyhMap.put("", bdcdyhList);
        }

        /// 2、调用权籍获取状态
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
     * 根据不动产单元号验证楼盘表数据
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
