package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjkxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslYmxxDTO;
import cn.gtmap.realestate.common.core.dto.etl.HtbaSpfWqxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.response.YrbQslxdhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.response.YrbQswspzhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSwxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.etl.HtbaSpfFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSwJkmxxVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 受理页面税务信息相关控制层
 */
@Controller
@RequestMapping("/slym/sw")
public class SlymSwController extends BaseController {

    @Autowired
    BdcSwFeignService bdcSwFeignService;

    @Autowired
    BdcSlHsxxFeignService bdcSlHsxxFeignService;

    @Autowired
    BdcSlHsxxMxFeignService bdcSlHsxxMxFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    HtbaSpfFeignService htbaSpfFeignService;

    @Autowired
    BdcGzyzFeignService bdcGzyzFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * @param gzlslid 工作流实例ID
     * @return 推送结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 税务信息推送
     */
    @ResponseBody
    @GetMapping("/tsSwxx")
    public Object tsSwxx(String gzlslid, String beanName) {
        if(StringUtils.isBlank(gzlslid) ||StringUtils.isBlank(beanName)){
            throw new MissingArgumentException(messageProvider.getMessage("推送税务缺失有效参数"));
        }
        return bdcSwFeignService.tsSwxx(gzlslid,beanName);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 推送结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 税务信息推送（南通、常州税务信息增加配偶信息、家庭成员信息、附件传地址）
     */
    @ResponseBody
    @GetMapping("/tsJsxx/{gzlslid}/{beanName}")
    public Object tsJsxx(@PathVariable(value = "gzlslid") String gzlslid,
                         @PathVariable(value="beanName") String beanName, @RequestParam(value="xmid", required = false) String xmid){
        if(StringUtils.isAnyBlank(gzlslid, beanName)){
            throw new MissingArgumentException("缺失参数：工作流实例ID或接口标识名称。");
        }
        return bdcSwFeignService.tsjsxx(gzlslid, beanName, xmid);
    }

    /**
     * @param gzlslid
     * @param fclx clf/spf
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通一窗页面推送税务信息接口调整
     * @date : 2022/9/19 9:46
     */
    @ResponseBody
    @GetMapping("/tsjsxx/ycsl")
    public Object tsjsxx(String gzlslid, String xmid, String fclx){
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失参数：工作流实例ID或接口标识名称。");
        }
        return bdcSwFeignService.ntYcslTsjsxx(gzlslid, xmid, fclx);
    }

    /**
     * @param bdcSwxxQO 税务信息查询QO
     * @return 查询结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询税务信息
     */
    @ResponseBody
    @GetMapping("/getSwxx")
    public Object getSwxx(BdcSwxxQO bdcSwxxQO) {
        if(StringUtils.isBlank(bdcSwxxQO.getXmid()) ||StringUtils.isBlank(bdcSwxxQO.getSlbh())){
            throw new MissingArgumentException(messageProvider.getMessage("税务查询缺失有效参数"));
        }
        if(StringUtils.isBlank(bdcSwxxQO.getGxlx())) {
            bdcSwxxQO.setGxlx(CommonConstantUtils.SW_GXLX_CXSC);
        }
        return bdcSwFeignService.getSwxx(bdcSwxxQO);
    }

    /**
     * @param bdcSwxxQO 税务信息查询QO
     * @return 查询结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description  批量查询税务信息
     */
    @ResponseBody
    @GetMapping("/getPlSwxx")
    public Object getPlSwxx(BdcSwxxQO bdcSwxxQO) {
        if(StringUtils.isBlank(bdcSwxxQO.getSlbh())){
            throw new MissingArgumentException(messageProvider.getMessage("税务查询缺失受理编号"));
        }
        if(StringUtils.isBlank(bdcSwxxQO.getGxlx())) {
            bdcSwxxQO.setGxlx(CommonConstantUtils.SW_GXLX_CXSC);
        }
        return bdcSwFeignService.getPlSwxx(bdcSwxxQO);
    }
    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @date 2022/9/14 14:54
    * @param bdcSwxxQO
    * @return 查询结果
    * @description 获取契税完税凭证A20
    **/
    @ResponseBody
    @GetMapping("/getQswspz")
    public Object getQswspz(BdcSwxxQO bdcSwxxQO){
        if(StringUtils.isBlank(bdcSwxxQO.getGzlslid())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数：工作流实例ID。");
        }
        if(StringUtils.isBlank(bdcSwxxQO.getGxlx())) {
            bdcSwxxQO.setGxlx(CommonConstantUtils.SW_GXLX_CXSC);
        }
        return bdcSwFeignService.getQswspz(bdcSwxxQO);
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/9/14 14:54
     * @param bdcSwxxQO
     * @return 查询结果
     * @description 获取契税联系单A21
     **/
    @ResponseBody
    @GetMapping("/getQslxd")
    public Object getQslxd(BdcSwxxQO bdcSwxxQO){
        if(StringUtils.isBlank(bdcSwxxQO.getGzlslid())){
            if(StringUtils.isBlank(bdcSwxxQO.getXmid()) || StringUtils.isBlank(bdcSwxxQO.getSlbh()) ){
                throw new MissingArgumentException(messageProvider.getMessage("税务查询缺失有效参数"));
            }
        }
        if(StringUtils.isBlank(bdcSwxxQO.getGxlx())) {
            bdcSwxxQO.setGxlx(CommonConstantUtils.SW_GXLX_CXSC);
        }
        return bdcSwFeignService.getQslxd(bdcSwxxQO);
    }
    /**
     * 查询当前流程的htbh
     * @param processInsId
     * @return
     */
    @ResponseBody
    @GetMapping("/getHtbh/{processInsId}")
    public Map<String,String> getHtbh(@PathVariable(value = "processInsId") String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            return new HashMap<>(2);
        }
        Map<String,String> result = new HashMap<>(2);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(processInsId, CommonConstantUtils.QLRLB_QLR, "");
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            String qlrzjh = bdcQlrDOList.get(0).getZjh();
            if(StringUtils.isNotBlank(qlrzjh)){
                //权利人证件号
                result.put("qlrzjh",qlrzjh);
            }
        }
        List<HtbaSpfWqxxDTO> list = htbaSpfFeignService.getSpfWqhtxx(processInsId);
        if(CollectionUtils.isNotEmpty(list)) {
            String htbabm = list.get(0).getHtbabm();
            //合同编码
            result.put("htbabm",htbabm);
        }
        return result;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理保存税务查询结果
     */
    @ResponseBody
    @PostMapping("/saveSwxxResponse")
    public void handleQuerySwxxResponse(@RequestBody QuerySwxxResponseDTO responseDTO,@RequestParam(name = "xmid") String xmid,@RequestParam(name = "gxlx")String gxlx){
        bdcSwFeignService.handleQuerySwxxResponse(responseDTO, xmid, gxlx);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理保存契税完税凭证查询结果
     */
    @ResponseBody
    @PostMapping("/saveQswspzResponse")
    public void handleQswspzResponse(@RequestBody YrbQswspzhqDTO yrbQswspzhqDTO, @RequestParam(name = "xmid") String xmid, @RequestParam(name = "gxlx")String gxlx){
        bdcSwFeignService.handleQswspzResponse(yrbQswspzhqDTO, xmid, gxlx);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理保存契税完税凭证查询结果
     */
    @ResponseBody
    @PostMapping("/saveQslxdResponse")
    public void handleQslxdResponse(@RequestBody YrbQslxdhqDTO yrbQslxdhqDTO, @RequestParam(name = "xmid") String xmid, @RequestParam(name = "gxlx")String gxlx){
        bdcSwFeignService.handleQslxdResponse(yrbQslxdhqDTO, xmid);
    }
    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @param system 系统版本，合肥新税务系统德宽版
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  取消作废税务信息
     */
    @ResponseBody
    @GetMapping("/qxzfSwxx")
    public Object qxzfSwxx(String xmid,String slbh, String system) {
        if(StringUtils.isBlank(xmid) ||StringUtils.isBlank(slbh)){
            throw new MissingArgumentException(messageProvider.getMessage("取消作废税务信息缺失有效参数"));
        }
        if(StringUtils.isNotBlank(system) && StringUtils.equals(CommonConstantUtils.SYSTEM_SW_DK,system)) {
            return bdcSwFeignService.qxzfSwxxDk(xmid,slbh);
        }
        return bdcSwFeignService.qxzfSwxx(xmid, slbh);
    }

    /**
     * @param slbh 受理编号
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description  批量取消作废税务信息
     */
    @ResponseBody
    @GetMapping("/plQxzfSwxx")
    public Object plQxzfSwxx(String slbh) {
        if(StringUtils.isBlank(slbh)){
            throw new MissingArgumentException(messageProvider.getMessage("取消作废税务信息缺失受理编号"));
        }
        return bdcSwFeignService.plQxzfSwxxDk(slbh);
    }

    /**
     * @param json 集合
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新受理核税信息
     */
    @ResponseBody
    @PatchMapping("/hsxx")
    public int updateBdcSlHsxx(@RequestBody String json) {
        return bdcSlHsxxFeignService.updateBdcSlHsxx(JSONObject.parseObject(json, BdcSlHsxxDO.class));
    }

    /**
     * @param json 核税信息
     * @return
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 生成受理核税信息
     */
    @ResponseBody
    @PostMapping("/hsxx")
    public int insertBdcHsxx(@RequestBody String json) {
        return bdcSlHsxxFeignService.insertBdcSlHsxxDO(JSONObject.parseObject(json, BdcSlHsxxDO.class));
    }

    /**
     * @param json 集合
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新受理核税明细信息
     */
    @ResponseBody
    @PatchMapping("/hsxxmx")
    public int updateBdcSlHsxxMx(@RequestBody String json) {
        return bdcSlHsxxMxFeignService.batchUpdateBdcSlHsxxMx(json);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid获取税务信息
     */
    @ResponseBody
    @GetMapping("/swxx")
    public YcslYmxxDTO getSwxx(String xmid) {
        YcslYmxxDTO ycslYmxxDTO =new YcslYmxxDTO();
         //生成一窗税务信息
        //权利人税务信息
        List<BdcSwxxDTO> bdcZrfSwxxList = bdcSlHsxxFeignService.queryBdcSwxxDTO(xmid, CommonConstantUtils.QLRLB_QLR);
        if (CollectionUtils.isNotEmpty(bdcZrfSwxxList)) {
            ycslYmxxDTO.setBdcZrfSwxxList(bdcZrfSwxxList);
        }

        //权利人税务信息
        List<BdcSwxxDTO> bdcZcfSwxxList = bdcSlHsxxFeignService.queryBdcSwxxDTO(xmid, CommonConstantUtils.QLRLB_YWR);
        if (CollectionUtils.isNotEmpty(bdcZcfSwxxList)) {
            ycslYmxxDTO.setBdcZcfSwxxList(bdcZcfSwxxList);
        }
        return ycslYmxxDTO;

    }

    /**
     * 调用税务系统三要素接口，获取人员纳税信息并更新受理核税信息数据
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: gzlslid 工作流实例ID
     * @return: String
     */
    @ResponseBody
    @GetMapping("/swsys/hsxx")
    public String swsysHsxx(@RequestParam(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        return bdcSwFeignService.getSwsysHsxx(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Boolean 完税状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证商品房完税状态
     */
    @ResponseBody
    @GetMapping(value = "/sfws/spfwszt")
    public Object checkSfwsSpfwszt(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "fybh", required = false) String fybh) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }

        if (StringUtils.isBlank(fybh)) {
            LOGGER.warn("gzlslid{},未输入房源编号！", gzlslid);
            return null;
        }

        // 保存税务房源编号
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();

        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setSwfybh(fybh);
        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(bdcXmDO));

        Map map = new HashMap(1);
        map.put(CommonConstantUtils.GZLSLID, gzlslid);
        bdcDjxxUpdateQO.setWhereMap(map);

        bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("当前流程未查询到项目信息！");
        }

        QuerySwxxResponseDTO responseDTO = bdcSwFeignService.getSpfXmWszt(bdcXmDOList.get(0), CommonConstantUtils.SW_GXLX_INSERT_UPDATE);
        // 只有在完税状态下,保存完税信息，并返回完税信息
        if (responseDTO != null) {
            return responseDTO.getWszt();
        }
        return null;
    }


    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid获取核税信息
     * @date : 2020/5/18 16:41
     */
    @ResponseBody
    @GetMapping("/bengbu")
    public Object querySwxx(String xmid) {
        if(StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("获取税务信息缺失xmid");
        }
        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setXmid(xmid);
        bdcSlHsxxQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
            return bdcSlHsxxDOList.get(0);
        } else {
            return new BdcSlHsxxDO();
        }
    }

    /**
     * 查询完税信息，已完税时上传电子发票至大云，并更新完税状态(常州)
     * @param gzlslid  工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 查询的完税信息
     */
    @ResponseBody
    @GetMapping("/getWsxx")
    public Object queryWsxx(String gzlslid) throws IOException {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失参数工作流实例ID");
        }
        return bdcSwFeignService.getAndHandleWsxx(gzlslid);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送附件材料接口
     * @date : 2020/10/27 10:40
     */
    @ResponseBody
    @GetMapping("/tsfjcl")
    public void tsFjcl(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失参数工作流实例ID");
        }
        bdcSwFeignService.tsFjcl(gzlslid);
    }

    /**
     * 根据sply和processInsId判断当前流程是否完税
     * @param processInsId
     * @param sply
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @return 是否需要提示 boolean
     */
    @ResponseBody
    @GetMapping("/showSpWsXx")
    public Object showSpWsXx(String processInsId,String sply){
        return bdcSwFeignService.showSpWsXx(processInsId, sply);
    }

    /**
     * 通过工作流实例ID获取税务信息，并推送税务信息（如东）
     * @param gzlslid 工作流实例ID
     * @return 推送返回结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/tsHsxx/{gzlslid}")
    public Object tsJsxx(@PathVariable(value = "gzlslid") String gzlslid) {
        if (StringUtils.isAnyBlank(gzlslid)) {
            throw new MissingArgumentException("缺失参数：工作流实例ID。");
        }
        return bdcSwFeignService.tshsxx(gzlslid);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税务信息申报单
     * @date : 2022/8/5 16:56
     */
    @ResponseBody
    @GetMapping("/sbdswxx")
    public void getSbdSwxx(String gzlslid, String fwlx, String htbh) {
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(htbh)) {
            bdcSwFeignService.getSwSbd(gzlslid, fwlx, htbh);
        }
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 申报单确认
     * @date : 2022/8/5 17:16
     */
    @ResponseBody
    @GetMapping("/sbdqr")
    public Object qrsbd(String gzlslid, String fwlx) {
        if (StringUtils.isNotBlank(gzlslid)) {
            return bdcSwFeignService.qrSbd(gzlslid, fwlx);
        }
        return null;
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断缴税状态，已缴税直接获取税票，未缴税先查询缴税状态，在调用【A009】获取税票信息
     * @date : 2022/8/5 17:44
     */
    @ResponseBody
    @GetMapping("/sw/sp")
    public CommonResponse getSwSpxx(String gzlslid, String jszt, String fwlx, String htbh) {
        if(StringUtils.isAnyBlank(gzlslid, jszt, htbh)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或缴税状态或合同编号");
        }
        return bdcSwFeignService.getSpxx(gzlslid, jszt, fwlx, htbh);
    }

    /**
     * 获取契税完税信息，调用税务【A009】获取契税完税
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 调用【A009】获取契税完税
     * @date : 2022/10/13 12:02
     */
    @ResponseBody
    @GetMapping("/hqqsws")
    public CommonResponse hqwsqs(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数：工作流实例ID");
        }
       return this.bdcSwFeignService.hqqsws(gzlslid);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取缴税二维码
     * @date : 2022/8/8 10:11
     */
    @ResponseBody
    @GetMapping("/jsewm")
    public List<BdcSwJkmxxVO> getJsEwm(String gzlslid, String htbh, String fwlx) {
        if(StringUtils.isNotBlank(gzlslid)){
            return bdcSwFeignService.getJsewm(gzlslid, htbh, fwlx);
        }
        return null;
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 打印之后调用接口5确认结果
     * @date : 2022/9/21 11:36
     */
    @ResponseBody
    @GetMapping("/qrjg")
    public void qrjg(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcSwFeignService.qrswjg(gzlslid);
        }
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 接口10 获取完税信息
     * @date : 2022/9/21 13:38
     */
    @ResponseBody
    @GetMapping("/wsxx")
    public void getWsxx(String gzlslid, String htbh) {
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(htbh)) {
            bdcSwFeignService.swWsxx(gzlslid, htbh);
        }
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税票
     * @date : 2022/9/21 14:20
     */
    @ResponseBody
    @GetMapping("/ycslsp")
    public Object getSp(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return null;
        }
        //判断权利人义务人的完税状态
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
            bdcSlHsxxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
            List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                //判断完税状态
                for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                    if (!Objects.equals(10, bdcSlHsxxDO.getWszt())) {
                        if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlHsxxDO.getSqrlb())) {
                            throw new AppException("权利人未完税");
                        } else {
                            throw new AppException("义务人未完税");
                        }
                    }
                }
            }
        }
        return bdcSwFeignService.getSwSp(gzlslid);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 税费统缴推送税务
     * @date : 2022/9/28 13:54
     */
    @ResponseBody
    @GetMapping("/sftj")
    public Object tcslSftj(@RequestParam("gzlslid") String gzlslid, @RequestParam(value = "xmid", required = false) String xmid,
                           @RequestParam("qlrlb") String qlrlb, @RequestParam(value = "sfxxid", required = false) String sfxxid) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isNotBlank(qlrlb)) {
            throw new AppException("税费统缴推送异常,缺失工作流实例id和权利人类别");
        }
        return bdcSlSfxxFeignService.bdcSftj(gzlslid, xmid, qlrlb, sfxxid);
    }

    /**
     * @param
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 税务信息列表
     */
    @ResponseBody
    @GetMapping(value = "/listswxxbypage")
    public Object listSwxxByPage(@LayuiPageable Pageable pageable, String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询缺失参数gzlslid");
        }
        Map map =new HashMap();
        map.put("gzlslid",gzlslid);
        String swxxQO = JSON.toJSONString(map);

        return addLayUiCode(bdcSwFeignService.listSwxxByPage(pageable,swxxQO));

    }
    /**
     * @description 获取银行税费信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/20 15:05
     * @param bdcSwxxQO  税务信息查询QO
     * @return Object
     */
    @ResponseBody
    @GetMapping("/getYhsfxx")
    public Object getYhsfxx(BdcSwxxQO bdcSwxxQO) {
        if(StringUtils.isAnyBlank(bdcSwxxQO.getHtbh(), bdcSwxxQO.getNsrsbh(), bdcSwxxQO.getGzlslid(), bdcSwxxQO.getQlrlb())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到合同编号、纳税人识别号、工作流实例ID或权利人类别");
        }
        return bdcSwFeignService.getYhsfxx(bdcSwxxQO);
    }

    /**
     * 通知税务任务状态【A003】
     * <p>提供对从外部（如政务平台）获取的任务，跟踪任务的当前的状态，如果任务状态为作废、冻结的，需要作废税务息系统针对该任务已经生成的所有信息。</p>
     * @param bdcSwxxQO  税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 通知结果 {"fhm":"返回码","fhxx":"返回信息","fwuuid":"房屋编号","htbh":"合同编号","jyuuid":"交易编号","sjbh":"收件编号"}
     */
    @ResponseBody
    @GetMapping("/tz/rwzt")
    public Object tzSwRwzt(BdcSwxxQO bdcSwxxQO) {
        if(StringUtils.isBlank(bdcSwxxQO.getGzlslid())){
            throw new MissingArgumentException("缺失参数：工作流实例ID");
        }
        return bdcSwFeignService.tzSwRwzt(bdcSwxxQO);
    }

    /**
     * 房产交易信息申报信息(pdf文件格式)【A014】
     * <p>获取税务的房产交易申报表PDF文件，并上传至大云</p>
     * @param bdcSwxxQO  税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/sbd")
    public void getSwxxSbdwj(BdcSwxxQO bdcSwxxQO){
        if(StringUtils.isBlank(bdcSwxxQO.getGzlslid())){
            throw new MissingArgumentException("缺失参数：工作流实例ID");
        }
        bdcSwFeignService.getSwxxSbdwj(bdcSwxxQO);
    }

    /**
     * 获取代缴的税款信息清单
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/jkxx")
    public List<BdcSlYjkxxDO> getFcjyJkxxQd(BdcSwxxQO bdcSwxxQO){
        if(StringUtils.isBlank(bdcSwxxQO.getGzlslid())){
            throw new MissingArgumentException("缺失参数：工作流实例ID");
        }
        return this.bdcSwFeignService.getFcjyJkxxQd(bdcSwxxQO);
    }

    /**
     * 获取缴税的二维码内容
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/jkewm")
    public Object getFcjyJkEwm(BdcSwxxQO bdcSwxxQO){
        if(StringUtils.isBlank(bdcSwxxQO.getGzlslid())){
            throw new MissingArgumentException("缺失参数：工作流实例ID");
        }
        return this.bdcSwFeignService.getFcjyJkEwm(bdcSwxxQO);
    }

    /**
     * 获取税务缴款状态
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 缴款状态信息
     */
    @ResponseBody
    @GetMapping("/jkzt")
    public Object getSwJkzt(BdcSwxxQO bdcSwxxQO){
        if(StringUtils.isBlank(bdcSwxxQO.getGzlslid())){
            throw new MissingArgumentException("缺失参数：工作流实例ID");
        }
        return this.bdcSwFeignService.getSwJkzt(bdcSwxxQO);
    }

    /**
     * 获取存量房计税信息
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/clfjsxx")
    public Object getClfjsxx(BdcSwxxQO bdcSwxxQO) {
        if(StringUtils.isBlank(bdcSwxxQO.getGzlslid())){
            throw new MissingArgumentException("缺失参数：工作流实例ID");
        }
        return this.bdcSwFeignService.getClfJsxx(bdcSwxxQO);
    }

    /**
     * 获取存量房计税信息
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/zlfjsxx")
    public Object getZlfjsxx(BdcSwxxQO bdcSwxxQO) {
        if(StringUtils.isBlank(bdcSwxxQO.getGzlslid())){
            throw new MissingArgumentException("缺失参数：工作流实例ID");
        }
        return this.bdcSwFeignService.getZlfJsxx(bdcSwxxQO);
    }

    /**
     * 获取税务操作缓存信息
     * @param redisKey 缓存key
     * @return Map 操作内容信息 Key：操作按钮名称， value：是否操作成功标识
     */
    @ResponseBody
    @GetMapping("/cz/cache/{redisKey}")
    public Map getSwCzztFromCache(@PathVariable(value="redisKey") String redisKey) {
        if(StringUtils.isNotBlank(redisKey)){
            String value = this.redisUtils.getStringValue(redisKey);
            if(StringUtils.isNotBlank(value)){
                return JSON.parseObject(value, Map.class);
            }
        }
        return new HashMap();
    }
    /**
     * 获取税务操作缓存信息
     * @param redisKey 缓存key
     * @param redisValue 缓存值（Map 操作内容信息 Key：操作按钮名称， value：是否操作成功标识）
     */
    @ResponseBody
    @PutMapping("/cz/cache/{redisKey}")
    public void setSwCzztCache(@PathVariable(value="redisKey") String redisKey,@RequestBody String redisValue) {
        if(StringUtils.isNotBlank(redisKey) && StringUtils.isNotBlank(redisValue)){
            String cacheValue = this.redisUtils.getStringValue(redisKey);
            if(StringUtils.isNotBlank(cacheValue)){
                Map cache = JSON.parseObject(cacheValue, Map.class);
                Map currentMap = JSON.parseObject(redisValue, Map.class);
                for(Object key : currentMap.keySet()){
                    cache.put(key, currentMap.get(key));
                }
                long expireTime  = this.redisUtils.getExpire(redisKey);
                this.redisUtils.addStringValue(redisKey, JSON.toJSONString(cache), expireTime);
            }else{
                this.redisUtils.addStringValue(redisKey, redisValue, 5*3600);
            }
        }
    }


    /**
     * 税费托管推送缴库入库
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 缴库入库结果
     */
    @ResponseBody
    @GetMapping("/sftg/tsjk")
    public CommonResponse sftgTsjk(@RequestParam(value = "gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
           throw new AppException(ErrorCode.MISSING_ARG, "缺少参数工作流实例ID");
        }
        return this.bdcSwFeignService.ykqTsJkrk(gzlslid);
    }

    /**
     * 税费托管账户结清
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 账户结清状态
     */
    @ResponseBody
    @GetMapping("/sftg/zhjq")
    public Object tsjk(@RequestParam(value = "gzlslid") String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数工作流实例ID");
        }
        return this.bdcSwFeignService.ykqZhjq(gzlslid);
    }

    /**
     * @param
     * @return
     * @description 根据xmid、核税信息类型获取核税信息
     */
    @ResponseBody
    @GetMapping("/getHsxx")
    public YcslYmxxDTO getHsxx(String xmid,String hsxxlx) {
        YcslYmxxDTO ycslYmxxDTO =new YcslYmxxDTO();
        //生成一窗税务信息
        //权利人税务信息
        List<BdcSwxxDTO> bdcZrfSwxxList = bdcSlHsxxFeignService.queryBdcSwxxDTO(xmid, CommonConstantUtils.QLRLB_QLR);
        bdcZrfSwxxList = bdcZrfSwxxList.stream().filter(bdcSwxxDTO -> bdcSwxxDTO.getBdcSlHsxxDO()!=null && hsxxlx.equals(bdcSwxxDTO.getBdcSlHsxxDO().getHsxxlx())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(bdcZrfSwxxList)) {
            ycslYmxxDTO.setBdcZrfSwxxList(bdcZrfSwxxList);
        }
        //权利人税务信息
        List<BdcSwxxDTO> bdcZcfSwxxList = bdcSlHsxxFeignService.queryBdcSwxxDTO(xmid, CommonConstantUtils.QLRLB_YWR);
        bdcZcfSwxxList = bdcZcfSwxxList.stream().filter(bdcSwxxDTO -> bdcSwxxDTO.getBdcSlHsxxDO()!=null && hsxxlx.equals(bdcSwxxDTO.getBdcSlHsxxDO().getHsxxlx())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(bdcZcfSwxxList)) {
            ycslYmxxDTO.setBdcZcfSwxxList(bdcZcfSwxxList);
        }
        return ycslYmxxDTO;
    }

    /**
     * 根据工作流实例ID 查询核税信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @return BdcSlHsxx 不动产核税信息
     */
    @ResponseBody
    @GetMapping("/queryHsxxZt")
    public Object queryHsxxZt(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
            BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
            bdcSlHsxxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
            List<BdcSlHsxxDO> bdcSlHsxxDOList = this.bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
            if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
                return bdcSlHsxxDOList.get(0);
            }
        }
        return null;
    }

    /**
     * 获取税务申报状态
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return YrbEwmjkxxResponse 缴款状态查询结果
     */
    @ResponseBody
    @GetMapping("/hqsbzt")
    public Object hqsbzt(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        BdcSwxxQO bdcSwxxQO = new BdcSwxxQO();
        bdcSwxxQO.setGzlslid(gzlslid);
        return this.bdcSwFeignService.hqswsbzt(bdcSwxxQO);
    }
}