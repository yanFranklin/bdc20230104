package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfssDdxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.PosJfDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfssDdxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CheckParameter;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/5/28
 * @description 不动产收费收税订单信息控制器
 */
@Controller
@RequestMapping("/sfss/ddxx")
public class BdcSfssDdxxController extends BaseController {

    @Autowired
    BdcSlSfssDdxxFeignService bdcSlSfssDdxxFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
     * @return 不动产收费收税订单信息集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询订单信息
     */
    @ResponseBody
    @GetMapping("")
    public Object listBdcSlSfssDdxx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        if (!CheckParameter.checkAnyParameter(bdcSlSfssDdxxQO, "gzlslid", "sfglid","yjdh")) {
            throw new AppException("缺失参数异常");
        }
        return bdcSlSfssDdxxFeignService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
    }

    /**
     * @param bdcSlSfssDdxxDO 不动产收费收税订单信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  更新订单信息
     */
    @ResponseBody
    @PatchMapping("")
    public int updateBdcSlSfssDdxxDO(@RequestBody BdcSlSfssDdxxDO bdcSlSfssDdxxDO) {
        if (StringUtils.isAnyBlank(bdcSlSfssDdxxDO.getDsfddbh())) {
            throw new AppException("未获取到第三方订单编号");
        }
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setDsfddbh(bdcSlSfssDdxxDO.getDsfddbh());
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = bdcSlSfssDdxxFeignService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if(CollectionUtils.isEmpty(bdcSlSfssDdxxDOList)){
            throw new AppException("第三方订单编号"+bdcSlSfssDdxxDO.getDsfddbh() +"未查询到对应的订单信息");
        }
        bdcSlSfssDdxxDO.setId(bdcSlSfssDdxxDOList.get(0).getId());
        return bdcSlSfssDdxxFeignService.updateBdcSlSfssDdxxDO(bdcSlSfssDdxxDO);
    }

    /**
     * @param posJfDTO pos缴费信息
     * @return 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description POS根据订单号更新订单(页面传参作为入参)
     */
    @ResponseBody
    @GetMapping("/gxDdxxByDdbh")
    public Object gxDdxxByDdbh(PosJfDTO posJfDTO) {
        if (StringUtils.isAnyBlank(posJfDTO.getCkh(), posJfDTO.getDdbh(), posJfDTO.getShdm(), posJfDTO.getZdh())) {
            throw new AppException("未获取到订单编号、参考号、商户代码、终端号参数，请POS缴费后再通知政融！");
        }
        //此处订单编号为第三方订单编号
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setDsfddbh(posJfDTO.getDdbh());
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = bdcSlSfssDdxxFeignService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
            BdcSlSfssDdxxDO bdcSlSfssDdxxDO = bdcSlSfssDdxxDOList.get(0);
            bdcSlSfssDdxxDO.setCkh(posJfDTO.getCkh());
            bdcSlSfssDdxxDO.setShdm(posJfDTO.getShdm());
            bdcSlSfssDdxxDO.setZdh(posJfDTO.getZdh());
            bdcSlSfssDdxxFeignService.updateBdcSlSfssDdxxDO(bdcSlSfssDdxxDO);
            // 获取受理编号用于日志内容记录
            if(StringUtils.isNotBlank(posJfDTO.getGzlslid())){
                posJfDTO.setSlbh(this.getSlbhByGzlslid(posJfDTO.getGzlslid()));
            }
            //更新商户代码、终端号、参考号等信息
            final Object response = exchangeInterfaceFeignService.requestInterface("wwsq_pos_gxdd", posJfDTO);
            LOGGER.info("POS更新订单接口返回值：{},对应订单号：{}", response, posJfDTO.getDdbh());
            return response;
        }
        return null;
    }

    /**
     * 根据工作流实例ID获取受理编号
     */
    private String getSlbhByGzlslid(String gzlslid){
        String slbh = "";
        if(StringUtils.isNotBlank(gzlslid)){
            List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                slbh = bdcXmDTOList.get(0).getSlbh();
            }
        }
        return slbh;
    }


    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @return 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description POS根据工作流实例ID更新订单(查询数据库获取入参)
     */
    @ResponseBody
    @GetMapping("/gxDdxxByGzlslid")
    public Object gxDdxxByGzlslid(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        Object response = null;
        if(!CheckParameter.checkAnyParameter(bdcSlSfssDdxxQO,"gzlslid","yjdh")){
            throw new MissingArgumentException("缺失参数工作流实例ID或月结单号信息。");
        }
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = bdcSlSfssDdxxFeignService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if (CollectionUtils.isEmpty(bdcSlSfssDdxxDOList)) {
            throw new AppException("未查询到订单信息，请先POS缴费");
        }
        String slbh = "";
        if(StringUtils.isNotBlank(bdcSlSfssDdxxQO.getGzlslid())){
            slbh = this.getSlbhByGzlslid(bdcSlSfssDdxxQO.getGzlslid());
        }
        for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcSlSfssDdxxDOList) {
            PosJfDTO posJfDTO = new PosJfDTO();
            BeanUtils.copyProperties(bdcSlSfssDdxxDO, posJfDTO);
            //订单编号为第三方订单编号
            posJfDTO.setDdbh(bdcSlSfssDdxxDO.getDsfddbh());
            posJfDTO.setSlbh(slbh);
            if (StringUtils.isAnyBlank(posJfDTO.getCkh(), posJfDTO.getDdbh(), posJfDTO.getShdm(), posJfDTO.getZdh())) {
                throw new AppException("未获取到订单编号、参考号、商户代码、终端号参数，请POS缴费后再通知政融！");
            }
            LOGGER.info("POS更新订单接口入参：{},对应第三方订单号：{}", posJfDTO, posJfDTO.getDdbh());
            response = exchangeInterfaceFeignService.requestInterface("wwsq_pos_gxdd", posJfDTO);
            LOGGER.info("POS更新订单接口返回值：{},对应第三方订单号：{}", response, posJfDTO.getDdbh());
        }
        return response;
    }

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询收费状态
     * @date : 2020/5/28 19:10
     */
    @ResponseBody
    @GetMapping("/sfzt")
    public void updateSfzt(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        if(!CheckParameter.checkAnyParameter(bdcSlSfssDdxxQO, "gzlslid","yjdh")){
            throw new AppException("缺失参数工作流实例ID或月结单号");
        }
        bdcSlSfssDdxxFeignService.querySfzt(bdcSlSfssDdxxQO);
    }
    /**
     * 获取权利人与义务人税费总金额
     *
     * @param xmid     项目ID
     * @param gzlslid  工作流实例ID
     * @return 权利人税费总金额（qlrzje）和义务人税费总金额（ywrzje）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/getSfzje")
    public Object computeQlrAndYwrSfje(@RequestParam(name = "xmid") String xmid, @RequestParam(name= "gzlslid") String gzlslid) {
        if(StringUtils.isAnyBlank(xmid,gzlslid)){
            throw new AppException("未获取到项目ID信息或工作流实例ID。");
        }
        return bdcSlSfssDdxxFeignService.computeQlrAndYwrSfje(xmid, gzlslid);
    }

    /**
     * @param bdcSlSfssDdxxDTOList 不动产税费订单信息DTO集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 创建税费订单信息
     */
    @ResponseBody
    @PostMapping("/scddxx")
    public List<BdcSlSfssDdxxDO> saveAndCreateDdxx(@RequestParam("jkfs") String jkfs,@RequestBody List<BdcSlSfssDdxxDTO> bdcSlSfssDdxxDTOList) {
        List<BdcSlSfssDdxxDO> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(bdcSlSfssDdxxDTOList)) {
            throw new AppException("未获取到税费信息。");
        }
        BdcSlSfssDdxxDTO bdcSlSfssDdxxDTO = bdcSlSfssDdxxDTOList.get(0);
        if (StringUtils.isBlank(bdcSlSfssDdxxDTO.getGzlslid()) && StringUtils.isBlank(bdcSlSfssDdxxDTO.getYjdh())) {
            throw new AppException("缺失参数工作流实例ID或月结单号信息。");
        }
        // 普通税费缴费订单处理
        if(StringUtils.isNotBlank(bdcSlSfssDdxxDTO.getGzlslid())){
            if(StringUtils.isBlank(bdcSlSfssDdxxDTO.getXmid())){
                List<BdcXmDTO> bdcXmDTOList=bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlSfssDdxxDTO.getGzlslid());
                if(CollectionUtils.isEmpty(bdcXmDTOList)){
                    throw new AppException("未获取到项目");
                }
                bdcSlSfssDdxxDTOList.forEach(slSfssDdxxDTO -> slSfssDdxxDTO.setXmid(bdcXmDTOList.get(0).getXmid()));
            }
            list = bdcSlSfssDdxxFeignService.saveAndCreateDdxx(jkfs, bdcSlSfssDdxxDTOList);
        }
        // 月结缴费订单信息处理
        if(StringUtils.isNotBlank(bdcSlSfssDdxxDTO.getYjdh())){
            list = this.bdcSlSfssDdxxFeignService.createYjSfOrder(bdcSlSfssDdxxDTO.getYjdh(), null, null);
        }
        return list;
    }

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询一卡清订单信息并更新入库
     */
    @ResponseBody
    @GetMapping("/ykq/ddxx")
    public Object queryYkqDdxx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO){
        if (StringUtils.isBlank(bdcSlSfssDdxxQO.getGzlslid()) && StringUtils.isBlank(bdcSlSfssDdxxQO.getYjdh())) {
            throw new AppException("查询订单缺失参数：工作流实例ID或月结单号。");
        }
        List<BdcSlSfssDdxxDO> responseDdxxList = new ArrayList<>();
        Map paramMap = new HashMap(2);
        // 流程中普通订单缴费查询
        if(StringUtils.isNotBlank(bdcSlSfssDdxxQO.getGzlslid())){
            List<BdcXmDTO> bdcXmDTOList=bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlSfssDdxxQO.getGzlslid());
            if(CollectionUtils.isEmpty(bdcXmDTOList)){
                return responseDdxxList;
            }
            paramMap.put("slbh", bdcXmDTOList.get(0).getSlbh());
            paramMap.put("xmid", bdcXmDTOList.get(0).getXmid());
        }
        // 月结订单缴费查询
        if(StringUtils.isNotBlank(bdcSlSfssDdxxQO.getYjdh())){
            paramMap.put("slbh", bdcSlSfssDdxxQO.getYjdh());
            paramMap.put("xmid", "");
        }
        Object response = exchangeInterfaceFeignService.requestInterface("wwsq_ykqjfztcx", paramMap);
        LOGGER.info("查询订单信息：{},受理编号：{}", response, paramMap.get("slbh"));
        if(Objects.nonNull(response)){
            responseDdxxList = JSONObject.parseArray(JSONObject.toJSONString(response), BdcSlSfssDdxxDO.class);
            //保存订单信息
            bdcSlSfssDdxxFeignService.saveAndUpdateDdxxByDdbh(responseDdxxList, bdcSlSfssDdxxQO.getGzlslid(), bdcSlSfssDdxxQO.getYjdh());
        }
        return responseDdxxList;
    }

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @return 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费信息退款申请
     */
    @ResponseBody
    @GetMapping("/tksq")
    public Object tksq(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        if(StringUtils.isBlank(bdcSlSfssDdxxQO.getGzlslid())&&StringUtils.isBlank(bdcSlSfssDdxxQO.getYjdh()) ){
            throw new AppException("缺失参数：工作流实例ID或月结单号。");
        }
        return bdcSlSfssDdxxFeignService.ddxxTksq(bdcSlSfssDdxxQO);
    }

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @retur 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费信息退款申请
     */
    @ResponseBody
    @GetMapping("/querySfxxTkqk")
    public Object querySfxxTkqk(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        if(StringUtils.isBlank(bdcSlSfssDdxxQO.getGzlslid())&&StringUtils.isBlank(bdcSlSfssDdxxQO.getYjdh()) ){
            throw new AppException("缺失参数：工作流实例ID或月结单号。");
        }
        return bdcSlSfssDdxxFeignService.querySfxxTkqk(bdcSlSfssDdxxQO);
    }

   /**
    * @param
    * @return
    * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
    * @description  合并缴费
    */
    @ResponseBody
    @GetMapping("/scddxx/hbjf")
    public List<BdcSlSfssDdxxDO> hbjf(@RequestParam("jkfs")String jkfs, String  gzlslid) {
        return bdcSlSfssDdxxFeignService.hbjf(jkfs,gzlslid);
    }

    /**
     * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
     * @return true:已失效 false:未失效
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  验证订单是否失效
     */
    @ResponseBody
    @GetMapping("/issx")
    public boolean checkDdxxIsSx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        return bdcSlSfssDdxxFeignService.checkDdxxIsSx(bdcSlSfssDdxxQO);
    }


    /**
     * 批量更新不动产收费收税信息
     * @param ddxxDOList 不动产收费收税订单信息集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping("/batchModify")
    public void batchModifyDdxx(@RequestBody List<BdcSlSfssDdxxDO> ddxxDOList) {
        if(CollectionUtils.isNotEmpty(ddxxDOList)){
            for(BdcSlSfssDdxxDO ddxx: ddxxDOList){
                this.bdcSlSfssDdxxFeignService.updateBdcSlSfssDdxxDO(ddxx);
            }
        }
    }

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @return 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 作废订单信息
     */
    @ResponseBody
    @GetMapping("/zfddxx")
    public void zfDdxx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        if(StringUtils.isBlank(bdcSlSfssDdxxQO.getGzlslid())){
            throw new AppException("缺失参数：工作流实例ID。");
        }
        bdcSlSfssDdxxFeignService.zfDdxx(bdcSlSfssDdxxQO);
    }

}
