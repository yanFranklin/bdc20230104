package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.qo.accept.BdcGzlwShQueryQO;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcXndyhFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/18
 * @description 规则验证
 */
@Controller
@RequestMapping("/bdcGzyz")
public class BdcGzYzController extends BaseController {

    @Autowired
    BdcXndyhFeignService bdcXndyhFeignService;
    @Autowired
    BdcGzyzFeignService bdcGzyzFeignService;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;
    @Autowired
    BdcGzlwFeignService bdcGzlwFeignService;
    @Autowired
    cn.gtmap.realestate.common.core.service.feign.engine.BdcGzlwFeignService engineBdcGzlwFeignService;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    ProcessDefinitionClient processDefinitionClient;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;


    @Value("#{'${noPpCj.gzldyid:}'.split(',')}")
    private List<String> noPpCjGzldyidList;

    /**
     * 预抵押登记小类
     */
    @Value("#{'${accept-ui.ydydjxl}'.split(',')}")
    private List<String> ydydjxl;

    /**
     * 是否仿冒合同编号
     */
    @Value("${accept-ui.gzyz.fakehtbh:false}")
    private boolean fakehtbh;

    /**
     * @param bdcGzYzQO 规则验证查询实体
     * @return 验证结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 流程规则验证
     */
    @ResponseBody
    @PostMapping("")
    public List<Map<String, Object>> yzBdcgz(@RequestBody(required = false) BdcGzYzQO bdcGzYzQO) {
        LOGGER.info("受理页面传入规则验证参数{}",bdcGzYzQO);
        if(fakehtbh && CollectionUtils.isNotEmpty(bdcGzYzQO.getParamList())){
            for (Map<String, Object> stringObjectMap : bdcGzYzQO.getParamList()) {
                if(!stringObjectMap.containsKey("htbh")){
                    stringObjectMap.put("htbh","0");
                }
            }
            LOGGER.info("受理页面传入规则验证参数,需要填入伪造合同编号{}",bdcGzYzQO);
        }
        return bdcGzyzFeignService.yzBdcgz(bdcGzYzQO);
    }

    /**
     * @param bdcGzYzQO 规则验证查询实体
     * @return 验证结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 其他规则验证（非流程）
     */
    @ResponseBody
    @PostMapping("/qtyz")
    public List<Map<String, Object>> qtyz(@RequestBody(required = false) BdcGzYzQO bdcGzYzQO) {
        return bdcGzyzFeignService.qtyz(bdcGzYzQO);
    }

    /**
     * @param bdcCshSlxmDTO 不动产初始化受理项目
     * @return 验证结果信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 验证虚拟不动产单元号
     */
    @ResponseBody
    @PostMapping("/yzxndyh")
    public List<BdcSlYwxxDTO> yzxnBdcdyh(@RequestBody BdcCshSlxmDTO bdcCshSlxmDTO) {
        return bdcXndyhFeignService.listYzXndyh(bdcCshSlxmDTO);

    }

    /**
     * @param gzldyid 工作流定义id
     * @return 验证结果信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 存在虚拟单元号判断是否是需要直接创建的流程
     */
    @ResponseBody
    @GetMapping("/sfcjlc")
    public boolean yzxnBdcdyh(String gzldyid) {
        // 没有配置时 默认为所有流程都可以忽略
        String pzGzldyid =  StringUtils.join(noPpCjGzldyidList.toArray(), ",");
        if(StringUtils.isBlank(pzGzldyid)){
            return true;
        }else{
            // 不为空的时候 只有配置的gzldyid的流程才有这个功能
            return noPpCjGzldyidList.indexOf(gzldyid) > -1;
        }
    }


    /**
     * @param fwdcbIndexs 多个逻辑幢主键,用逗号隔开
     * @return 户室信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据逻辑幢主键获取户室信息
     */
    @ResponseBody
    @GetMapping("/getAllHsByFwdcbIndex")
    public List<FwHsDO> getAllHsByFwdcbIndex(String fwdcbIndexs, String bdcdyfwlx,String qjgldm) {
        List<FwHsDO> fwHsDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(fwdcbIndexs)) {
            for (String fwdcbIndex : fwdcbIndexs.split(CommonConstantUtils.ZF_YW_DH)) {
                if (StringUtils.isNotBlank(fwdcbIndex)) {
                    List<FwHsDO> hsList = null;
                    List<FwYchsDO> fwYchsDOList;
                    String[] bdcdyfwlxArr;
                    // 除配置预测户室外都查实测户室  ccx 2019-10-04
                    if (!StringUtils.equals(bdcdyfwlx, "ychs")) {
                        hsList = fwHsFeignService.listFwhsByFwDcbIndex(fwdcbIndex,qjgldm);
                    }
                    // 实测户室没查到 如果配置中包含预测户室 则查询预测户室
                    if (StringUtils.isNotBlank(bdcdyfwlx)) {
                        bdcdyfwlxArr = bdcdyfwlx.split(",");
                        if (CollectionUtils.isEmpty(hsList) && CommonUtil.indexOfStrs(bdcdyfwlxArr, "ychs")) {
                            hsList =new ArrayList<>();
                            fwYchsDOList = fwYcHsFeignService.listFwYchsByFwDcbIndex(fwdcbIndex,qjgldm);
                            // 暂时先将预测户室的信息转到实测户室实体中返回  只转了bdcdyh字段 后面可以考虑使用copybean  ccx 2019-10-02
                            if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
                                for (FwYchsDO fwYchsDO : fwYchsDOList) {
                                    FwHsDO fwHsDO = new FwHsDO();
                                    fwHsDO.setBdcdyh(fwYchsDO.getBdcdyh());
                                    fwHsDO.setZl(fwYchsDO.getZl());
                                    fwHsDO.setFwHsIndex(fwYchsDO.getFwHsIndex());
                                    hsList.add(fwHsDO);
                                }
                            }
                        }
                    }
                    if (CollectionUtils.isEmpty(hsList)) {
                        throw new AppException("逻辑幢：" + fwdcbIndex + "未找到有效的户室，请检查数据");
                    } else {
                        fwHsDOList.addAll(hsList);
                    }
                }
            }
        }
        return fwHsDOList;
    }


    /**
     * @param dataMap 忽略信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  忽略规则提示记录日志信息
     */
    @ResponseBody
    @PostMapping("/addIgnoreLog")
    public void addIgnoreLog(@RequestBody Map<String,Object> dataMap) {
        String data = MapUtils.getString(dataMap,"data");
        String gzldyid = MapUtils.getString(dataMap,"gzldyid");
        String bdcdyh = MapUtils.getString(dataMap,"bdcdyh");
        ProcessDefData processDefData = new ProcessDefData();
        if(StringUtils.isNotBlank(gzldyid)) {
            processDefData = processDefinitionClient.getProcessDefByProcessDefKey(gzldyid);
        }
        if(processDefData != null && StringUtils.isNotBlank(processDefData.getName())) {
            data = "流程名称:" + processDefData.getName() + " "+data;
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        Map<String, Object> map = new HashMap<>();
        // 解析data中是否包含不动产单元号
        // 我们认为bdcdyh 里面必须包含GB俩个字母，
        // 以这个为根据判断data里面是否包含bdcdyh,有的话则根据以下规则截取出来
        // 目前的规则是 包含GB的则包含bdcdyh,往前截取12位，往后截取14位
        if(StringUtils.isNotBlank(bdcdyh)){
            map.put(CommonConstantUtils.BDCDYH, bdcdyh);
        }else {
            try {
                if (data.indexOf("GB") != -1) {
                    LOGGER.info("开始截取bdcdyh，原值：{}", data);
                    int index = data.lastIndexOf("GB");
                    String sfuStr = data.substring(index - 12, index);
                    String preStr = data.substring(index + 2, index + 2 + 14);
                    bdcdyh = sfuStr + "GB" + preStr;
                    map.put(CommonConstantUtils.BDCDYH, bdcdyh);

                }
            } catch (Exception e) {
                LOGGER.error("截取bdcdyh出错：{}", e.getMessage());
            }
        }
        map.put(CommonConstantUtils.VIEW_TYPE, Constants.ACCEPT_UI);
        map.put(CommonConstantUtils.VIEW_TYPE_NAME, Constants.HLLX);
        map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
        map.put(Constants.HLNR, data);
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), Constants.GZYZ_HL, map);
        zipkinAuditEventRepository.add(auditEvent);
    }

    @ResponseBody
    @PostMapping("/addLwLog")
    public void addLwLog(@RequestBody Map<String,Object> dataMap) {
        String data = MapUtils.getString(dataMap,"data");
        String slbh = MapUtils.getString(dataMap,"slbh");
        //增加例外日志信息记录
        UserDto userDto = userManagerUtils.getCurrentUser();
        Map<String, Object> map = new HashMap<>();
        // 解析data中是否包含不动产单元号
        // 我们认为bdcdyh 里面必须包含GB俩个字母，
        // 以这个为根据判断data里面是否包含bdcdyh,有的话则根据以下规则截取出来
        // 目前的规则是 包含GB的则包含bdcdyh,往前截取12位，往后截取14位
        try{
            if(data.contains("GB")) {
                LOGGER.info("开始截取bdcdyh，原值：{}", data);
                int index = data.lastIndexOf("GB");
                String sfuStr = data.substring(index - 12, index);
                String preStr = data.substring(index + 2, index + 2 + 14);
                String bdcdyh = sfuStr + "GB" + preStr;
                map.put(CommonConstantUtils.BDCDYH, bdcdyh);
            }
        }catch (Exception e){
            LOGGER.error("截取bdcdyh出错：{}",e.getMessage());
        }
        map.put(CommonConstantUtils.SLBH,slbh);
        map.put(CommonConstantUtils.VIEW_TYPE, Constants.ACCEPT_UI);
        map.put(CommonConstantUtils.VIEW_TYPE_NAME, Constants.LWLX);
        map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
        map.put(Constants.LWNR, data);
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), Constants.GZYZ_LW, map);
        zipkinAuditEventRepository.add(auditEvent);
    }

    @ResponseBody
    @PostMapping("/addShxxData")
    public BdcGzlwShDO addShxxData(String data, String slbh, String xmid) {

        return bdcGzlwFeignService.addShxxData(data, slbh, xmid);
    }

    @ResponseBody
    @PostMapping("/bdcgzlw")
    public Object bdcgzlw(Pageable pageable, BdcGzlwShQueryQO bdcGzlwShQueryQO) {
        pageable = delPageRequest(pageable);
        String paramJson = JSON.toJSONString(bdcGzlwShQueryQO);
        return addLayUiCode(bdcGzlwFeignService.queryBdcGzlw(pageable, paramJson));
    }

    @ResponseBody
    @PostMapping("/bdcgzlw/bdcdyh/fz")
    public Object bdcgzlwGroupByBdcdyh(Pageable pageable, BdcGzlwShDO bdcGzlwShDO) {
        pageable = delPageRequest(pageable);
        String paramJson = JSON.toJSONString(bdcGzlwShDO);
        return addLayUiCode(bdcGzlwFeignService.bdcgzlwGroupByBdcdyh(pageable, paramJson));
    }

    @ResponseBody
    @PostMapping("/lwyy")
    public void lwyy(String lwyy, String gzlslid) {
        List<BdcGzlwShDO> bdcGzlwShDOList = bdcGzlwFeignService.listBdcGzlw(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcGzlwShDOList)) {
            for (BdcGzlwShDO bdcGzlwShDO : bdcGzlwShDOList) {
                bdcGzlwShDO.setLwyy(lwyy);
            }
            bdcGzlwFeignService.updateBdcGzlwxx(bdcGzlwShDOList);
        }
    }

    @ResponseBody
    @DeleteMapping("/deletebdcgzlwsh")
    public void deleteBdcGzlwSh(String gzlwid) {
        bdcGzlwFeignService.deleteBdcGzlwSh(gzlwid);
    }

    @ResponseBody
    @DeleteMapping("/cleanBdcGzlwSh")
    public void cleanBdcGzlwSh(BdcGzlwShDO bdcGzlwShDO) {
        if (CheckParameter.checkAnyParameter(bdcGzlwShDO)) {
            bdcGzlwFeignService.deleteBdcGzlwShByGzlw(bdcGzlwShDO);
        }
    }

    @ResponseBody
    @PostMapping("/gzlwsh")
    public Object gzlwsh(String data, boolean accept, String shyj) {

        return bdcGzlwFeignService.updateBdcGzlw(data, accept, shyj);
    }


    /**
     * 验证当前项目是否线上交款
     * @param xmid 项目ID
     * @return 抵押类月结银行无需线上缴款 true: 线上交款；false: 非线上交款
     */
    @ResponseBody
    @GetMapping("/sfxsjk")
    public boolean checkOnlinePay(String xmid,String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID信息");
        }
        // 为获取到月结银行配置时，默认返回true,线上交款
        List<String> yjyhmcList = this.bdcXtJgFeignService.listYjyhmc();
        if(CollectionUtils.isEmpty(yjyhmcList)){
            return true;
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        if(StringUtils.isNotBlank(xmid)){
            bdcXmQO.setXmid(xmid);
        }
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("未查询到对应的项目信息");
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        //判断是否抵押
        if ((CollectionUtils.isNotEmpty(ydydjxl) && ydydjxl.contains(bdcXmDO.getDjxl())) || CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXmDO.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            // 验证当前权利人是否月结银行
            if(CollectionUtils.isNotEmpty(bdcQlrDOList)){

                for(BdcQlrDO bdcQlrDO:bdcQlrDOList){
                    if(yjyhmcList.contains(bdcQlrDO.getQlrmc())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 验证是否外联证书并且选择了注销
     * @param gzlslid 工作流实例ID
     * @return true 是； false 否
     */
    @ResponseBody
    @GetMapping("/checkWlzs")
    public boolean checkWlzs(@RequestParam(value = "gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        Integer result = this.bdcGzyzFeignService.checkWlzs(gzlslid);
        if(Objects.equals(2, result)){
            return true;
        }
        return false;
    }

    /**
     * @param bdcGzlwShDOList 规则例外数据
     * @param slbh 受理编号
     * @param lwyy 例外原因
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量添加审核信息
     */
    @ResponseBody
    @PostMapping("/addShxxDataPl")
    public BdcGzlwShDO addShxxDataPl(@RequestBody List<BdcGzlwShDO> bdcGzlwShDOList, String slbh, String lwyy) {
        return engineBdcGzlwFeignService.addShxxDataPl(bdcGzlwShDOList, slbh, lwyy);
    }
}
