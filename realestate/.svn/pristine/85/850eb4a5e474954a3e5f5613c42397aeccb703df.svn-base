package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.realestate.accept.ui.config.FcjyxxConfig;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyClfHtxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyHtxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh.HtxxContractDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh.HtxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.getybahtxx.response.YbahtDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.accept.FcjyxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFwxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcQzxxFeginService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.common.util.PageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/20.
 * @description 一窗受理与受理调用第三方接口获取交易、房屋信息服务
 */
@Controller
@RequestMapping("/ycsl/jyxx")
public class YcslJyxxController extends BaseController {

    private static final Integer HTBA_CODE_SUCCESS = 0;

    private static final Integer HTBA_CODE_FAIL = 1;

    private static final Integer HTBA_CODE_NOT_EXIST = 2;


    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Autowired
    BdcSlFwxxFeignService bdcSlFwxxFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcQzxxFeginService bdcQzxxFeginService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    FcjyxxConfig fcjyxxConfig;


    /**
     * @param htbh 合同备案号
     * @param zjh 买受人证件号
     * @param beanName 接口标识符
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 调用接口获取房产交易信息
     */
    @ResponseBody
    @GetMapping("/queryFcjyxx")
    public Object queryFcjyxx(String htbh, String zjh, String beanName) {
        if (StringUtils.isBlank(beanName)) {
            throw new AppException("缺失接口参数beanName");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("htbh", CommonUtil.getOrElse(htbh, ""));
        paramMap.put("msrzjh", CommonUtil.getOrElse(zjh, ""));
        return exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
    }

    /**
     * @param htbh     合同备案号
     * @param zjh      买受人证件号
     * @param beanName 接口标识符
     * @param gzlslid 工作流实例id
     * @param xmid 项目id
     * @param onlyQuery 是否仅仅是查询
     * @return
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 调用接口获取南通商品房备案交易信息,
     */
    @ResponseBody
    @GetMapping("/queryNtSpfBaJyxx")
    public Object queryNtSpfBaJyxx(String htbh, String zjh, String beanName, String gzlslid, String xmid, Boolean onlyQuery) {
        if (StringUtils.isBlank(beanName)) {
            throw new AppException("缺失接口参数beanName");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("htbh", CommonUtil.getOrElse(htbh, ""));
        paramMap.put("xm", "");
        paramMap.put("zjhm", CommonUtil.getOrElse(zjh, ""));
        String foldName = fcjyxxConfig.getSpfWjjmc();
        LOGGER.info("调取南通住建交易信息接口入参beanName：{},paramMap：{}", beanName, paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        if (Objects.isNull(response)) {
            throw new AppException("调取南通住建交易信息接口返回值为空");
        }
        String jsonString = JSONObject.toJSONString(response);
        LOGGER.info("调取南通住建交易信息接口返回值：{}", jsonString);
        List<JSONObject> ntSpfBaJyxxList = JSONObject.parseArray(jsonString, JSONObject.class);
        if (CollectionUtils.isEmpty(ntSpfBaJyxxList)) {
            return Collections.emptyList();
        }
        // 处理合同编号、交易价格并上传附件
        if(Objects.nonNull(onlyQuery) && !onlyQuery){
            bdcSlJyxxFeignService.dealNtSpfBaJyxx(ntSpfBaJyxxList, gzlslid, xmid, foldName);
        }
        return ntSpfBaJyxxList;
    }

    /**
     * @param name     买受人名称
     * @param zjh      买受人证件号
     * @param beanName 接口标识符
     * @param gzlslid 工作流实例id
     * @param xmid 项目id
     * @param onlyQuery 是否仅仅是查询
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 调用接口获取通州房屋(商品房,存量房)备案交易信息
     */
    @ResponseBody
    @GetMapping("/queryTzFwBaJyxx")
    public Object queryTzFwBaJyxx(String name, String zjh, String beanName, String gzlslid, String xmid, Boolean onlyQuery) {
        if (StringUtils.isBlank(beanName)) {
            throw new AppException("缺失接口参数beanName");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", CommonUtil.getOrElse(name, ""));
        paramMap.put("idno", CommonUtil.getOrElse(zjh, ""));
        String foldName ="";
        // 商品房
        if ("getIncrementContactinfoID".equals(beanName)){
            foldName = fcjyxxConfig.getSpfWjjmc();
        }else{
            foldName = fcjyxxConfig.getWjjmc();
        }
        LOGGER.info("调取通州住建交易信息接口入参beanName：{},paramMap：{}", beanName, paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);

        JSONObject jsonResponse = JSON.parseObject(JSONObject.toJSONString(response));
//        JSONObject jsonResponse = JSONObject.parseObject(response);
        List<HtxxDTO> ntSpfBaJyxxList = new ArrayList<>();
        if (Objects.isNull(jsonResponse) || !"0000".equals(jsonResponse.getString("code"))) {
            throw new AppException("调取通州住建交易信息接口返回值失败");
        }
        String jsonString = JSONObject.toJSONString(jsonResponse.get("result"));
        LOGGER.info("调取通州住建交易信息接口返回值：{}", jsonString);
       /* JSONArray resultsArr  = jsonResponse.getJSONArray("result");
        JSONObject result = resultsArr.getJSONObject(0);*/
        HtxxContractDTO htxxContractDTO = JSONObject.parseObject(JSONObject.toJSONString(jsonResponse.get("result")), HtxxContractDTO.class);
        ntSpfBaJyxxList = htxxContractDTO.getContractList();
        if (CollectionUtils.isEmpty(ntSpfBaJyxxList)) {
            return Collections.emptyList();
        }
        // 处理合同编号、交易价格并上传附件
        if (Objects.nonNull(onlyQuery) && !onlyQuery) {
            bdcSlJyxxFeignService.dealTzSpfBaJyxx(ntSpfBaJyxxList, gzlslid, xmid, foldName);
        }
        return ntSpfBaJyxxList;
    }

    /**
     * @param htbh     合同备案号
     * @param zjh      买受人证件号
     * @param beanName 接口标识符
     * @param gzlslid 工作流实例id
     * @param xmid 项目id
     * @param onlyQuery 是否仅仅是查询
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 调用接口获取房产交易信息, 海安根据配置调用接口
     */
    @ResponseBody
    @GetMapping("/queryNtFcjyxx")
    public Object queryNtFcjyxx(String htbh,
                                String zjh,
                                String beanName,
                                String gzlslid,
                                String xmid,
                                Boolean onlyQuery
    ) {
        if (StringUtils.isBlank(beanName)) {
            throw new AppException("缺失接口参数beanName");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("htbh", CommonUtil.getOrElse(htbh, ""));
        paramMap.put("msrzjh", CommonUtil.getOrElse(zjh, ""));
        // 读取配置
        if (fcjyxxConfig.getQxdmBeanNameMap() == null || StringUtils.isBlank(fcjyxxConfig.getWjjmc())){
            throw new AppException("缺失配置参数qxdmBeanNameMap或文件夹名称");
        }
        String qxdm = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getCurrentUserName());
        beanName = fcjyxxConfig.getQxdmBeanNameMap().get(qxdm);
        String foldName = fcjyxxConfig.getWjjmc();
        if (StringUtils.isBlank(beanName)) {
            throw new AppException("调取南通住建交易信息接口未配置接口名称beanName");
        }
        LOGGER.info("调取南通住建交易信息接口入参beanName：{},paramMap：{}", beanName, paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        if (Objects.isNull(response)) {
            throw new AppException("调取南通住建交易信息接口返回值为空");
        }
        String jsonString = JSONObject.toJSONString(response);
        LOGGER.info("调取南通住建交易信息接口返回值：{}", jsonString);
        List<FcjyClfHtxxDTO> fcjyClfHtxxDTOList = JSONObject.parseArray(jsonString, FcjyClfHtxxDTO.class);
        LOGGER.info("调取南通住建交易信息接口返回值：{}", jsonString);
        if (CollectionUtils.isEmpty(fcjyClfHtxxDTOList)) {
            return Collections.emptyList();
        }
        List<FcjyHtxxDTO> list = new ArrayList<>();
        //只查询不做处理
        if(Objects.nonNull(onlyQuery) && onlyQuery){
            LOGGER.info("调取南通住建交易信息接口返回值：{}", JSON.toJSONString(fcjyClfHtxxDTOList));
            for (FcjyClfHtxxDTO fcjyClfHtxxDTO : fcjyClfHtxxDTOList) {
                FcjyHtxxDTO fcjyHtxxDTO = new FcjyHtxxDTO();
                fcjyHtxxDTO.setHtbh(fcjyClfHtxxDTO.getBdcSlJyxx().getHtbh());
                List<BdcQlrDO> qlrDOList = fcjyClfHtxxDTO.getBdcQlr();
                List<String> msrmc = new ArrayList<>();
                List<String> cmrmc = new ArrayList<>();
                if(CollectionUtils.isNotEmpty(qlrDOList)) {
                    for (BdcQlrDO bdcQlrDO : qlrDOList) {
                        // 1-权利人；2-义务人
                        if (CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb())) {
                            msrmc.add(bdcQlrDO.getQlrmc());
                        } else if (CommonConstantUtils.QLRLB_YWR.equals(bdcQlrDO.getQlrlb())) {
                            cmrmc.add(bdcQlrDO.getQlrmc());
                        }
                    }
                }
                fcjyHtxxDTO.setMsrmc(msrmc);
                fcjyHtxxDTO.setCmrmc(cmrmc);
                fcjyHtxxDTO.setZl(fcjyClfHtxxDTO.getBdcSlXmDO().getZl());
                list.add(fcjyHtxxDTO);
            }
            LOGGER.info("调取南通住建交易信息接口返回值：{}", JSON.toJSONString(list));
        }else {
            list = bdcSlJyxxFeignService.dealNtZjjyxx(fcjyClfHtxxDTOList, gzlslid, xmid, foldName);
        }
        return list;
    }

    /**
     * 通过合同编号获取合同pdf并上传到文件服务器
     *
     * @param htbh
     */
    @ResponseBody
    @GetMapping("/fileUpload/{htbh}")
    public void fileUpload(@PathVariable String htbh,@RequestParam(value = "gzlslid") String gzlslid) throws Exception {
        JSONObject json = new JSONObject();
        json.put("code",htbh);
        // 调用exchange 获取合同pdf
        LOGGER.info("开始调用南通获取已备案合同exchange接口,接口的beanName：getYbaHtxx,接口的参数：{}",  json);
        Object response = exchangeInterfaceFeignService.requestInterface("getYbaHtxx",json);
        if (response != null) {
             LOGGER.info("合同编号:{},调取获取已备案合同接口成功,返回结果：{}", htbh, response);
             JSONObject ybahtObject = JSONObject.parseObject(JSONObject.toJSONString(response));
             if (ybahtObject.get("code") != null && "1".equals(ybahtObject.get("code").toString())) {
                 YbahtDTO ybahtDTO  = JSONObject.parseObject(JSONObject.toJSONString(ybahtObject.get("data")), YbahtDTO.class);
                 BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", "", "合同备案附件", CommonConstantUtils.WJHZ_PDF);
                 bdcPdfDTO.setPdfUrl(ybahtDTO.getUrl());
                 bdcUploadFileUtils.uploadPdfByUrl(bdcPdfDTO);
             }
         }
    }

    /**
     * 分页查询房产交易信息
     * @param pageable  分页参数
     * @param fcjyxxQO  房产交易信息查询对象
     * @return 房产交易信息分页数据
     */
    @ResponseBody
    @GetMapping("/listFcjyxxByPage")
    public Object listFcjyxxByPage(@LayuiPageable Pageable pageable, FcjyxxQO fcjyxxQO){
        if (StringUtils.isBlank(fcjyxxQO.getBeanName())) {
            throw new AppException("缺失接口参数beanName");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("htbh", CommonUtil.getOrElse(fcjyxxQO.getHtbh(), ""));
        paramMap.put("msrzjh", "");
        LOGGER.info("请求nt_clfhtxx接口请求参数：{}", paramMap);
        Object object =  exchangeInterfaceFeignService.requestInterface(fcjyxxQO.getBeanName(), paramMap);
        LOGGER.info("请求nt_clfhtxx接口返回接口：{}", JSON.toJSONString(object));
        if(Objects.nonNull(object)){
            Map map = (Map) JSON.parse(JSON.toJSONString(object));
            List<Map> list = (List<Map>) map.get("wqxx");
            return super.addLayUiCode(PageUtils.listToPage(list, pageable));
        }else{
            return super.addLayUiCode(PageUtils.listToPage(new ArrayList(), pageable));
        }
    }

    /**
     * @param param 受理交易信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理交易获取登记信息
     */
    @ResponseBody
    @PostMapping("/dealDjxx")
    public void dealDjxx(@RequestBody JSONObject param, @RequestParam(name = "processInsId", required = false) String processInsId) throws Exception {
        if(param.isEmpty()){
            throw new NullPointerException("未获取到房产交易合同信息！");
        }
        bdcSlJyxxFeignService.dealNtDjxx(param,processInsId);

    }


    /**
     * @description 调用商品房交易接口获取交易信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: htbh 合同编号
     * @param: xm 姓名
     * @param: zjhm 证件号码
     * @param: beanName 接口标识符
     * @return: List<FcjyBaxxDTO> 房产备案信息集合
     */
    @ResponseBody
    @GetMapping("/querySpfJyxx")
    public Object querySpfJyxx(String htbh, String xm, String zjh, String fwbm, String beanName) {
        if (StringUtils.isBlank(beanName)) {
            throw new AppException("缺失接口参数beanName");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("htbh", CommonUtil.getOrElse(htbh, ""));
        paramMap.put("xm", CommonUtil.getOrElse(xm, ""));
        paramMap.put("zjhm", CommonUtil.getOrElse(zjh, ""));
        paramMap.put("fwid", CommonUtil.getOrElse(fwbm, ""));
        return exchangeInterfaceFeignService.requestInterface(beanName, paramMap);

    }

    /**
     * @description 校验当前合同编号是否进行关联备案号
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param:  htbh 合同编号
     * @return: Boolean TRUE 已关联备案信息 FALSE 未关联备案信息
     */
    @ResponseBody
    @GetMapping("/checkHtbhLinked")
    public Object checkHtbhLinked(@RequestParam(value = "htbh") String htbh,@RequestParam(value = "fwyt",required = false) String fwyt){
        if(StringUtils.isBlank(htbh)){
            throw new MissingArgumentException("缺失参数合同编号。");
        }
        return bdcSlJyxxFeignService.checkHtbhLinked(htbh, fwyt);
    }

    /**
     * 验证当前业务是否已导入合同编号
     * @param htbh 合同编号
     * @param gzlslid 工作流实例ID
     * @return 验证结果
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/checkHtbhImport")
    public Object checkHtbhImport(@RequestParam(value = "htbh") String htbh, @RequestParam(value = "gzlslid") String gzlslid){
        if(StringUtils.isAnyBlank(htbh, gzlslid)){
            throw new MissingArgumentException("缺失参数合同编号或项目ID。");
        }
        List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcSlXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到受理项目信息");
        }
        List<String> xmids = bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
        BaseQO baseQO = new BaseQO();
        baseQO.setIds(xmids);
        List<BdcSlJyxxDO> bdcSlJyxxDOList = this.bdcSlJyxxFeignService.listBdcSlJyxxByXmids(baseQO);
        boolean isImported = false;
        for(BdcSlJyxxDO bdcSlJyxxDO : bdcSlJyxxDOList){
            if(htbh.equals(bdcSlJyxxDO.getHtbh())){
                isImported  = true;
                break;
            }
        }
        return isImported;
    }

    /**
     * @description 通过外部接口获取的房产交易备案信息，更新不动产项目的交易合同编号、受理库交易信息与房屋信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: fcjyBaxxDTO 房产交易备案信息
     * @param: processInsId 工作流实例ID
     * @param: xmid 项目ID
     * @return: void 无返回值
     */
    @ResponseBody
    @PostMapping("/dealSlJyxx")
    public void dealJyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO,
                         @RequestParam(value = "processInsId") String processInsId,
                         @RequestParam(value ="xmid") String xmid) {
        if(null == fcjyBaxxDTO){
            throw new AppException("未获取到房产交易合同信息！");
        }
        bdcSlJyxxFeignService.dealSlJyxx(fcjyBaxxDTO, processInsId, xmid);

    }

    /**
     * 处理主房关联后 再次关联附房的逻辑
     * @param fcjyBaxxDTO
     * @param xmid
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/dealFsssBaxx")
    public void dealFsssBaxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO,
                            @RequestParam(value ="xmid") String xmid) {
        if(StringUtils.isBlank(xmid)){
            throw new MissingArgumentException("缺少参数项目ID信息。");
        }

        bdcSlJyxxFeignService.dealFsssBaxx(fcjyBaxxDTO, xmid);

    }

    /**
     * 导入多个交易合同信息
     * @param fcjyBaxxDTO 房产交易信息
     * @param xmid 项目id
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/dealMultiJyxx")
    public void dealMultiJyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO,
                              @RequestParam(value ="xmid") String xmid){
        if(StringUtils.isBlank(xmid)){
            throw new MissingArgumentException("缺少参数项目ID信息。");
        }
        BdcSlJyxxDO bdcSlJyxxDO = fcjyBaxxDTO.getBdcSlJyxx();
        if(null != bdcSlJyxxDO){
            bdcSlJyxxDO.setXmid(xmid);
            this.bdcSlJyxxFeignService.insertBdcSlJyxx(bdcSlJyxxDO);
        }
        BdcSlFwxxDO bdcSlFwxxDO = fcjyBaxxDTO.getBdcSlFwxx();
        if(null != bdcSlFwxxDO){
            bdcSlFwxxDO.setXmid(xmid);
            this.bdcSlFwxxFeignService.insertBdcSlFwxx(bdcSlFwxxDO);
        }
    }

    /**
     * @description 记录接口获取的一手房交易信息，更新不动产受理交易信息与房屋信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [fcjyBaxxDTO, xmid] 房产交易备案信息DTO , 项目ID
     * @return: void 无返回值
     */
    @ResponseBody
    @PostMapping("/dealSpfClfJyxx")
    public void dealSpfClfJyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO,
                            @RequestParam(value ="xmid") String xmid) {
        if(StringUtils.isBlank(xmid)){
            throw new MissingArgumentException("缺少参数项目ID信息。");
        }
        bdcSlJyxxFeignService.dealSpfClfJyxx(fcjyBaxxDTO,xmid,true);
    }

    /**
     * 批量一窗流程，根据xmid导入交易信息、房屋信息
     * @param fcjyBaxxDTO 房产交易备案信息
     * @param xmid 项目ID
     */
    @ResponseBody
    @PostMapping("/dealSpfClfJyxxPl")
    public void dealSpfClfJyxxPl(@RequestBody FcjyBaxxDTO fcjyBaxxDTO,
                               @RequestParam(value ="xmid") String xmid) {
        if(StringUtils.isBlank(xmid)){
            throw new MissingArgumentException("缺少参数项目ID信息。");
        }
        BdcSlJyxxDO bdcSlJyxxDO = fcjyBaxxDTO.getBdcSlJyxx();
        if(null != bdcSlJyxxDO){
            bdcSlJyxxDO.setXmid(xmid);
            List<BdcSlJyxxDO> bdcSlJyxxDOList = this.bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
            if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)){
                bdcSlJyxxDO.setJyxxid(bdcSlJyxxDOList.get(0).getJyxxid());
                this.bdcSlJyxxFeignService.updateXmSlJyxx(xmid, JSON.toJSONString(bdcSlJyxxDO), null);
            }else{
                this.bdcSlJyxxFeignService.insertBdcSlJyxx(bdcSlJyxxDO);
            }
        }
        BdcSlFwxxDO bdcSlFwxxDO = fcjyBaxxDTO.getBdcSlFwxx();
        if(null != bdcSlFwxxDO){
            bdcSlFwxxDO.setXmid(xmid);
            BdcSlXmDO bdcSlXmDO = this.bdcSlXmFeignService.queryBdcSlXmByXmid(xmid);
            // 判断是否主房，不是主房时，将jzmj导入到ckmj中去
            if(Objects.nonNull(bdcSlXmDO) && Objects.equals(CommonConstantUtils.SF_F_DM, bdcSlXmDO.getSfzf())){
                if(Objects.isNull(bdcSlFwxxDO.getCkmj())){
                    bdcSlFwxxDO.setCkmj(bdcSlFwxxDO.getJzmj());
                    bdcSlFwxxDO.setJzmj(null);
                }
            }
            this.bdcSlFwxxFeignService.updateBdcSlFwxxByXmid(bdcSlFwxxDO);
        }
    }

    /**
     * 通过项目ID获取交易信息合同编号
     * @param xmid  项目ID
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 返回合同编号和对应的项目ID
     */
    @GetMapping("/queryHtbh")
    @ResponseBody
    public BdcXmDO getJyxxHtbh(@RequestParam(name="xmid",required = false)String xmid,@RequestParam(name="gzlslid",required = false)String gzlslid){
        Preconditions.checkArgument(StringUtils.isNotBlank(xmid) ||StringUtils.isNotBlank(gzlslid),"未获取到项目ID和工作流实例ID信息。");
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        String htbh = "";
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            for(BdcXmDO bdcXmDO: bdcXmDOList){
                if(StringUtils.isNotBlank(bdcXmDO.getJyhth())){
                    return bdcXmDO;
                }
            }
        }
        return new BdcXmDO();
    }

    /**
     * 通过合同编号查询房产交易信息
     * <p> 常州版接口，通过合同编号和买受人名称获取房产交易信息并返回，
     * @param htbh 合同编号
     * @param fwlx 房屋类型
     * @param xmid 项目ID
     * @param sfck 是否查库
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 房产交易信息
     */
    @PostMapping("/cz/queryFcjyxxByHtbh")
    @ResponseBody
    public Object queryFcjyxxByHtbh(@RequestParam(name="htbh") String htbh,
                                    @RequestParam(name="fwlx",required = false) String fwlx, @RequestParam(name="xmid") String xmid,@RequestParam(name="sfck",required = false) boolean sfck){
       if(StringUtils.isAnyBlank(xmid)){
           throw new MissingArgumentException("缺失参数：项目ID。");
       }
       if(StringUtils.isBlank(htbh)) {
           return null;
       }
       return this.bdcSlJyxxFeignService.queryFcjyHtxxByHtbh(htbh, fwlx, xmid,sfck);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 合同备案推送
     */
    @PostMapping("/ts/htba")
    @ResponseBody
    public Object tsHtba(@RequestParam(name="htbh") String htbh, @RequestParam(name="sczt",required = false) String sczt, @RequestParam(name="reason",required = false) String reason, @RequestParam(name="xmid") String xmid){
        //code：0：备案成功 1：合同有结果,备案失败 2:未查询到合同
        if(StringUtils.isBlank(htbh) ||StringUtils.isBlank(sczt) ||StringUtils.isBlank(xmid)){
            throw new AppException("备案功能：合同编号或审查状态或项目主键不能为空");
        }
        //查询合同信息
        Object response =queryFcjyxx(htbh,"","ntyth_clfwqxx_zh");
        LOGGER.info("请求ntyth_clfwqxx_zh接口结果：{},合同编号：{}", response,htbh);
        if(response != null) {
            List<FcjyBaxxDTO> fcjyClfHtxxDTOList = JSONObject.parseArray(JSON.toJSONString(response), FcjyBaxxDTO.class);
            if (CollectionUtils.isNotEmpty(fcjyClfHtxxDTOList)) {
                //请求合同有结果
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("htbh", CommonUtil.getOrElse(htbh, ""));
                paramMap.put("sczt", CommonUtil.getOrElse(sczt, ""));
                paramMap.put("reason", CommonUtil.getOrElse(reason, ""));
                //获取签字文件
                BdcQzxxDO bdcQzxxDO =new BdcQzxxDO();
                bdcQzxxDO.setXmid(xmid);
                List<BdcQzxxDO> bdcQzxxDOList = bdcQzxxFeginService.queryBdcQzxx(bdcQzxxDO);
                List<Map> fjList =new ArrayList<>();
                if(CollectionUtils.isNotEmpty(bdcQzxxDOList)){
                    for(BdcQzxxDO qzxxDO:bdcQzxxDOList){
                        if(StringUtils.isNotBlank(qzxxDO.getFid()) &&(CommonConstantUtils.QZRLB_QLR.equals(qzxxDO.getQzrlb()) ||CommonConstantUtils.QZRLB_YWR.equals(qzxxDO.getQzrlb()))) {
                            BaseResultDto baseResultDto = storageClient.downloadBase64(qzxxDO.getFid());
                            if(baseResultDto != null) {
                                Map<String, Object> fileMap = new HashMap<>();
                                fileMap.put("fileType",qzxxDO.getQzrlb());
                                fileMap.put("fileBase64", baseResultDto.getMsg());
                                fjList.add(fileMap);
                            }
                        }
                    }
                }
                LOGGER.info("请求ntyth_clfwqsc入参：{},签字信息：{}",paramMap,bdcQzxxDOList);
                paramMap.put("file", fjList);
                Object baResponse =exchangeInterfaceFeignService.requestInterface("ntyth_clfwqsc", paramMap);
                LOGGER.info("请求ntyth_clfwqsc接口结果：{},合同编号：{}", baResponse,htbh);
                if(baResponse != null){
                    Map<String, Object> badata = JSONObject.parseObject(JSON.toJSONString(baResponse), Map.class);
                    if(badata != null &&badata.get("status") != null &&StringUtils.equals("success",badata.get("status").toString())){
                        //备案成功
                        dealSpfClfJyxx(fcjyClfHtxxDTOList.get(0), xmid);
                        return HTBA_CODE_SUCCESS;

                    }else{
                        return HTBA_CODE_FAIL;
                    }
                }
            }else{
                return HTBA_CODE_NOT_EXIST;
            }
        }
        return HTBA_CODE_NOT_EXIST;
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询交易分页信息
      */
    @ResponseBody
    @GetMapping("/page")
    public Object listHtxxByPage(@LayuiPageable Pageable pageable, FcjyxxQO fcjyxxQO){
        if (fcjyxxQO ==null ||StringUtils.isBlank(fcjyxxQO.getBeanName())) {
            throw new AppException("缺失接口标识符beanName");
        }

        Object response = exchangeInterfaceFeignService.requestInterface(fcjyxxQO.getBeanName(),fcjyxxQO);
        LOGGER.info("入参：{}请求:{}接口返回信息：{}",fcjyxxQO,fcjyxxQO.getBeanName(), JSON.toJSONString(response));
        if(Objects.nonNull(response)){
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(response));
            if(jsonObject.get("data") != null){
                return super.addLayUiCode(PageUtils.listToPage(jsonObject.getObject("data",List.class), pageable));
            }
        }
        return super.addLayUiCode(PageUtils.listToPage(new ArrayList(), pageable));
    }

    /**
     * 根据xmid删除房屋和交易信息
     * @param xmid 项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @DeleteMapping("/fwjyxx")
    public void removeFwJyxx(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到项目ID");
        }
        this.bdcSlJyxxFeignService.deleteBdcSlJyxxByXmid(xmid);
        this.bdcSlFwxxFeignService.deleteBdcSlFwxxByXmid(xmid);
    }

    /**
     *  获取交易信息后反馈交易状态
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @param htbh 合同编号
     * @param zjh 证件号
     */
    @ResponseBody
    @GetMapping("/backjyzt")
    public void fkjyzt(String htbh, String zjh) {
        if (StringUtils.isNoneBlank(htbh, zjh)) {
            this.bdcSlJyxxFeignService.fkzjzt(null, htbh, zjh);
        }
    }

    /**
     * @param fcjyxxQO
     * @return
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 获取淮安房屋交易信息
     */
    @ResponseBody
    @GetMapping("/queryHaFcjyxx")
    public FcjyClfHtxxDTO queryYcslFcjyxx(FcjyxxQO fcjyxxQO) {
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getHtbh()), "缺失合同编号");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getXmid()), "缺失当前项目ID");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getGzlslid()), "缺失当前工作流实例ID");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getLclx()), "缺失流程类型参数");
        return bdcSlJyxxFeignService.queryHaFcjyxx(fcjyxxQO);
    }

    /**
     * @description 查询淮安房屋信息是否签售
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/8/19 15:32
     * @param fwbh 房屋编号
     * @param xmmc 项目名称
     * @param ysxkzh 预售许可证号
     * @param qsdm 权属代码
     * @return Object
     */
    @ResponseBody
    @GetMapping("/queryHaFcjyFwsfqs")
    public Object queryHaFcjyFwsfqs(@RequestParam(value = "fwbh", required = false) String fwbh, @RequestParam(value = "xmmc", required = false) String xmmc,
                                    @RequestParam(value = "ysxkzh", required = false) String ysxkzh, @RequestParam(value = "fh", required = false) String fh,
                                    @RequestParam(value = "qsdm", required = false) String qsdm, @RequestParam(value = "beanName") String beanName) {
        if (StringUtils.isBlank(beanName)) {
            throw new AppException("缺失接口参数beanName");
        }
        return bdcSlJyxxFeignService.queryHaFcjyFwsfqs(fwbh, xmmc, ysxkzh, fh, qsdm, beanName);
    }

    /**
     * @param fcjyxxQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取二手房网签合同数据
     */
    @ResponseBody
    @GetMapping("/queryEsfWqHtxx")
    public Object queryEsfWqHtxx(FcjyxxQO fcjyxxQO) {
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getHtbh()), "缺失合同编号");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getXmid()), "缺失当前项目ID");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getGzlslid()), "缺失当前工作流实例ID");
        Map<String,String> map = new HashMap<>(1);
        map.put("msg", bdcSlJyxxFeignService.queryEsfWqHtxx(fcjyxxQO));
        return map;
    }
}
