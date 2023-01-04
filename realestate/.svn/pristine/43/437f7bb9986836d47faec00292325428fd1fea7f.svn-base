package cn.gtmap.realestate.accept.ui.web.rest.changzhou;


import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcLcTsjfGxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCxcsDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQuerySfztDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcTsdjfxxResponseDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfxxCzQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2021/11/2
 * @description
 */
@Controller
@RequestMapping("/rest/v1.0/cz/sfxx")
public class BdcSfxxCzController  extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSfxxCzController.class);

    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    BdcSfxxFeignService bdcSfxxFeignService;
    @Autowired
    BdcSlSfxmFeignService bdcSlSfxmFeignService;
    @Autowired
    BdcSlCxcsFeignService bdcSlCxcsFeignService;
    @Autowired
    BdcLcTsjfGxFeignService bdcLcTsjfGxFeignService;

    /**
     * 废除二维码信息
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/ewm/delete")
    public void deleteSfxxEwm(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID。");
        }
        // 判断收费项目是否已收费
        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSfxxFeignService.queryBdcSlSfxmBySfxxidWithFilter(bdcSfxxCzQO.getSfxxid(), bdcSfxxCzQO.isSftdsyj());
        if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
            if(Objects.equals(bdcSlSfxmDOList.get(0).getSfzt(), BdcSfztEnum.YJF.key())){
                throw new AppException(ErrorCode.CUSTOM, "已缴费不可废除二维码,请先退费。");
            }else{
                BdcQuerySfztDTO bdcQuerySfztDTO = this.bdcSfxxFeignService.querySfzt(bdcSfxxCzQO);
                if(Objects.equals(bdcQuerySfztDTO.getSfzt(), BdcSfztEnum.YJF.key() )){
                    throw new AppException(ErrorCode.CUSTOM, "已缴费不可废除二维码,请先退费。");
                }else{
                    this.bdcSfxxFeignService.abolishEwm(bdcSfxxCzQO);
                }
            }
        }
    }

    /**
     * 批量废除二维码信息
     * @param bdcSfxxCzQOList 不动产受理收费信息常州QO集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/ewm/delete/pl")
    public Object deleteSfxxEwmPl(@RequestBody List<BdcSfxxCzQO> bdcSfxxCzQOList) {
        if(CollectionUtils.isEmpty(bdcSfxxCzQOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        List<String> errorMsgList = new ArrayList<>();
        for(BdcSfxxCzQO bdcSfxxCzQO : bdcSfxxCzQOList){
            try{
                deleteSfxxEwm(bdcSfxxCzQO);
            }catch(Exception e){
                errorMsgList.add( "受理编号：" + bdcSfxxCzQO.getSlbh() + e.getMessage());
            }
        }
        return StringUtils.join(errorMsgList, CommonConstantUtils.ZF_YW_DH);
    }

    /**
     * 批量抵押收费，废除二维码信息
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/ewm/delete/dyapl")
    public void deleteSfxxEwmDyapl(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getGzlslid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        this.bdcSfxxFeignService.abolishEwmDyapl(bdcSfxxCzQO);
    }

    /**
     * 查询推送待缴信息
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/cxtsdjfxx")
    public BdcTsdjfxxResponseDTO cxtsdjfxx(BdcSfxxCzQO bdcSfxxCzQO) {
        if (StringUtils.isBlank(bdcSfxxCzQO.getPjdm())) {
            bdcSfxxCzQO.setPjdm(CommonConstantUtils.SF_PJDM);
        }
        return this.bdcSfxxFeignService.cxtsdjfxx(bdcSfxxCzQO);
    }

    /**
     * 查询收费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return 查询财政收费状态接口返回结果对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/querySfzt")
    public BdcQuerySfztDTO querySfzt(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        return this.bdcSfxxFeignService.querySfzt(bdcSfxxCzQO);
    }

    /**
     * 同步收费信息中的收费状态
     * <p>获取收费信息关联的所有收费项目，判断收费项目的收费状态是否为 已收费。
     * 全部为已收费则收费信息的收费状态为：已收费；否则为：未收费。</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/sync/sfzt")
    public void syncSfxxSfzt(@RequestParam String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "同步收费信息收费状态时，未获取到收费信息ID");
        }
        // 同步更新收费信息中的收费状态
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
            for(BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                this.bdcSfxxFeignService.syncSfxxSfzt(bdcSlSfxxDO.getSfxxid(), "");
            }
        }
    }

    /**
     * 退款申请
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return 退款申请接口返回结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/tksq")
    public Map tksq(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        return this.bdcSfxxFeignService.tksq(bdcSfxxCzQO);
    }

    /**
     * 收费信息退款
     * <p>收费信息页面，退款功能，先查询收费状态，收费状态为已缴费时，清空收费缴款码</p>
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/tk/delete/ewm")
    public void tkWithDeleteEwm(BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        this.bdcSfxxFeignService.abolishEwm(bdcSfxxCzQO);
    }

    /**
     * 修改收费项目的收费状态，同步更新收费信息的收费状态
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/sfzt")
    public void modifySfxmSfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        this.bdcSfxxFeignService.modifySfxmSfzt(bdcSfxxCzQO);
    }

    /**
     * 领取发票号
     * <p>更新收费项目FPH信息</p>
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @return 发票号
     */
    @ResponseBody
    @GetMapping("/lqfph")
    public String lqfph(BdcSfxxCzQO bdcSfxxCzQO){
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        return this.bdcSfxxFeignService.lqfph(bdcSfxxCzQO);
    }

    /**
     * 作废发票号
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @ResponseBody
    @GetMapping("/zffph")
    public void zffph(BdcSfxxCzQO bdcSfxxCzQO){
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        this.bdcSfxxFeignService.zffph(bdcSfxxCzQO);
    }

    /**
     * 更新收费信息ID
     * <p>常州缴费书编码作废后，需要更新收费信息ID后，才能继续推送财务</p>
     * @param bdcSfxxCzQOList 不动产收费信息操作QO集合
     */
    @ResponseBody
    @PostMapping("/rechange/sfxxid")
    public void plRecount(@RequestBody List<BdcSfxxCzQO> bdcSfxxCzQOList){
        if(CollectionUtils.isEmpty(bdcSfxxCzQOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        this.bdcSfxxFeignService.rechangeSfxxid(bdcSfxxCzQOList);
    }

    /**
     * 抵押批量缴费流程清除推送id
     * @param gzlslid 工作流实例ID
     */
    @ResponseBody
    @GetMapping("/clearTsid/{gzlslid}")
    public void plClearTsid(@PathVariable(value="gzlslid") String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = this.bdcLcTsjfGxFeignService.listLcTsjfGx(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)){
            for(BdcLcTsjfGxDO bdcLcTsjfGxDO: bdcLcTsjfGxDOList){
                bdcLcTsjfGxDO.setTsid(StringUtils.EMPTY);
            }
            this.bdcLcTsjfGxFeignService.batchUpdateLcTsjfGx(bdcLcTsjfGxDOList);
        }
    }


    /**
     * 取消发票号
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @ResponseBody
    @GetMapping("/qxfph")
    public void qxfph(BdcSfxxCzQO bdcSfxxCzQO){
        if(StringUtils.isBlank(bdcSfxxCzQO.getSfxxid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        this.bdcSfxxFeignService.qxfph(bdcSfxxCzQO);
    }

    /**
     * 批量取消发票号
     * @param bdcSfxxCzQOList 不动产受理收费信息常州QO集合
     */
    @ResponseBody
    @PostMapping("/qxfph/pl")
    public Object qxfphpl(@RequestBody List<BdcSfxxCzQO> bdcSfxxCzQOList){
        if(CollectionUtils.isEmpty(bdcSfxxCzQOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        List<String> errorList = new ArrayList<>();
        for(BdcSfxxCzQO bdcSfxxCzQO: bdcSfxxCzQOList){
            try{
                this.bdcSfxxFeignService.qxfph(bdcSfxxCzQO);
            }catch(Exception e){
                errorList.add(bdcSfxxCzQO.getSfxxid());
            }
        }
        return errorList;
    }

    /**
     * 缴款方式切换，更改收费状态、缴款时间
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     */
    @ResponseBody
    @PostMapping("/jkfs")
    public void changeJkfs(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        if(StringUtils.isAnyBlank(bdcSfxxCzQO.getSfxxid(), bdcSfxxCzQO.getJkfs())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID或缴款方式");
        }
        this.bdcSfxxFeignService.changeJkfsModifySfzt(bdcSfxxCzQO);
    }

    /**
     * 常州产权批量缴费台账
     * @param bdcSlSfxxQO  收费信息查询QO
     * @return 产权批量收费信息
     */
    @ResponseBody
    @GetMapping("/tsjf/slbh")
    public Object listCqfxx(BdcSlSfxxQO bdcSlSfxxQO) {
        if(StringUtils.isBlank(bdcSlSfxxQO.getGzlslid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        // 记录搜索受理编号搜索条件
        this.recordQueryParam(bdcSlSfxxQO);
        // 根据当前人所在区县，获取可查询到的qxdm项目数据
        bdcSlSfxxQO.setQxdmList(this.getCurrentUserPljfQxdm());
        return this.bdcSfxxFeignService.listCqTssfDTO(bdcSlSfxxQO);
    }

    /**
     * 常州抵押批量缴费台账
     * @param bdcSlSfxxQO  收费信息查询QO
     * @return 抵押权批量收费信息
     */
    @ResponseBody
    @GetMapping("/tsjf/dyaq")
    public Object listTsSfxxByDyaqxx(BdcSlSfxxQO bdcSlSfxxQO) {
        if(StringUtils.isBlank(bdcSlSfxxQO.getGzlslid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        // 记录搜索受理编号搜索条件
        this.recordQueryParam(bdcSlSfxxQO);
        // 根据当前人所在区县，获取可查询到的qxdm项目数据
        bdcSlSfxxQO.setQxdmList(this.getCurrentUserPljfQxdm());
        return this.bdcSfxxFeignService.listDyaqTssfDTO(bdcSlSfxxQO);
    }

    /**
     * 查询用户输入的查询参数
     * @param gzlslid  工作流实例ID
     * @return 查询参数
     */
    @ResponseBody
    @GetMapping("/getQueryParam/{gzlslid}")
    public BdcSlCxcsDO getQueryParam(@PathVariable(value="gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        List<BdcSlCxcsDO> bdcSlCxcsDOList = this.bdcSlCxcsFeignService.getBdcSlCxcs(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcSlCxcsDOList)){
            return bdcSlCxcsDOList.get(0);
        }
        return null;
    }

    private void recordQueryParam(BdcSlSfxxQO bdcSlSfxxQO){
        if(StringUtils.isNotBlank(bdcSlSfxxQO.getGzlslid()) && CollectionUtils.isNotEmpty(bdcSlSfxxQO.getSlbhList())){
            try{
                Map<String, Object> cxcsMap  = new HashMap<>(2);
                cxcsMap.put("slbh", bdcSlSfxxQO.getSlbhList());
                BdcSlCxcsDO bdcSlCxcsDO = new BdcSlCxcsDO();
                bdcSlCxcsDO.setCxr(this.userManagerUtils.getCurrentUserName());
                bdcSlCxcsDO.setGzlslid(bdcSlSfxxQO.getGzlslid());
                bdcSlCxcsDO.setCxcs(JSON.toJSONString(cxcsMap));
                bdcSlCxcsDO.setCzsj(new Date());
                this.bdcSlCxcsFeignService.saveBdcSlCxcsByGzlslid(bdcSlCxcsDO);
            }catch(Exception e){
                LOGGER.error("保存用户查询参数异常", e);
            }
        }
    }

    /**
     * 获取当前人的区县代码，根据配置获取能够查询出来的qxdm数据
     * 例：当前人所在 320400， 批量缴费数据能够查询到 320401，320402的数据，不配置默认为320400
     * <p>sfxx.pljf.320400 = 320401,320402</p>
     * @return 区县代码
     */
    private List<String> getCurrentUserPljfQxdm(){
        List<String> qxdmList = new ArrayList<>(10);
        // 获取当前人的区县代码
        String qxdm = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getCurrentUserName());
        if(StringUtils.isNotBlank(qxdm)){
            String qxdms = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("sfxx.pljf", qxdm, qxdm);
            if(StringUtils.isNotBlank(qxdms)){
                qxdmList.addAll(Arrays.asList( qxdms.split(",") ));
            }
        }
        return qxdmList;
    }

    /**
     * 查询需要更改小微企业的收费信息
     * @param bdcSlSfxxQO 不动产收费信息QO对象
     * @return 小微企业收费信息
     */
    @ResponseBody
    @GetMapping("/xwqy/sfxx")
    public Object listXwqySfxx(BdcSlSfxxQO bdcSlSfxxQO) {
        if(StringUtils.isBlank(bdcSlSfxxQO.getGzlslid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        // 记录搜索受理编号搜索条件
        this.recordQueryParam(bdcSlSfxxQO);
        // 根据当前人所在区县，获取可查询到的qxdm项目数据
        bdcSlSfxxQO.setQxdmList(this.getCurrentUserPljfQxdm());
        return this.bdcSfxxFeignService.listXwqySfxx(bdcSlSfxxQO);
    }

    /**
     * 1、更改收费信息减免原因为小微企业、是否收登记费为否
     * 2、修改收费项目登记费实收金额为0
     * 3、重新计算收费信息合计
     * @param bdcSfxxCzQO 不动产收费信息QO
     */
    @ResponseBody
    @PostMapping("/updateSfxxJmyy")
    public void updateSfxxJmyy(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        if (StringUtils.isAnyBlank(bdcSfxxCzQO.getGzlslid(), bdcSfxxCzQO.getJmyy())) {
            throw new AppException("缺失工作流实例ID或减免原因");
        }
        this.bdcSfxxFeignService.modifySfxxJmyyAndSfsdjf(bdcSfxxCzQO);
    }
}
