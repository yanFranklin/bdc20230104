package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFpxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/5/11
 * @description 不动产受理发票信息
 */
@Controller
@RequestMapping("/fpxx")
public class BdcSlFpxxController extends BaseController {

    @Autowired
    BdcSlFpxxFeignService bdcSlFpxxFeignService;
    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 非税查询缴款情况成功，自动执行获取发票号、开具发票信息功能
     * 1、获取电子票号
     * 2、开具电子发票
     * 3、获取电子发票信息
     * @param sfxxid 收费信息ID
     * @param qlrlb 权利人类别
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/auto")
    public BdcSlSfxxDO auto(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value = "qlrlb") String qlrlb, @RequestParam(value="gzlslid") String gzlslid) throws InterruptedException {
        if(StringUtils.isAnyBlank(sfxxid, gzlslid, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID或收费信息ID");
        }
        // 1、 获取电子票号
        try{
            this.queryDzph(sfxxid);
        }catch(Exception e){
            throw new AppException(ErrorCode.CUSTOM, String.format("获取电子票号失败，错误信息：%s", e.getMessage()));
        }

        // 2、开具电子发票
        try{
            this.invocie(sfxxid, qlrlb, gzlslid);
        }catch(Exception e){
            throw new AppException(ErrorCode.CUSTOM, String.format("开具电子发票失败，错误信息：%s", e.getMessage()));
        }

        // 等待 5s， 开具发票后，需要等待5s在进行获取电子发票
        Thread.sleep(5000);

        // 3、获取电子发票信息
        BdcSlSfxxDO bdcSlSfxxDO = null;
        try{
            bdcSlSfxxDO =  this.queryDzfpxx(sfxxid, gzlslid);
        }catch(Exception e){
            throw new AppException(ErrorCode.CUSTOM, String.format("获取电子发票信息失败，错误信息：%s", e.getMessage()));
        }
        return bdcSlSfxxDO;
    }

    /**
     * 根据收费信息ID获取不动产受理发票信息
     * @param sfxxid  收费信息ID
     * @return 发票信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/getFpxx/{sfxxid}")
    public List<BdcSlFpxxDO> xxck(@PathVariable(value="sfxxid") String sfxxid) {
        if(StringUtils.isBlank(sfxxid)){
            throw new AppException("未获取到收费信息ID");
        }
        return this.bdcSlFpxxFeignService.queryBdcSlFpxxBySfxxid(sfxxid);
    }

    /**
     * 调用外部接口，获取电子发票号
     * @param sfxxid 收费信息
     * @return 电子发票号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/queryDzph")
    public String queryDzph(String sfxxid){
        if(StringUtils.isBlank(sfxxid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        String userCode = "";
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if (currentUser != null && StringUtils.isNotBlank(currentUser.getUsername())) {
            userCode = currentUser.getUsername();
        }
        return this.bdcSlFpxxFeignService.queryDzph(sfxxid, userCode);
    }

    /**
     * 开具电子发票
     * @param sfxxid 收费信息ID
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/invoice")
    public void invocie(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value = "qlrlb") String qlrlb, @RequestParam(value="gzlslid") String gzlslid){
        if(StringUtils.isAnyBlank(sfxxid, gzlslid, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID或收费信息ID");
        }
        this.bdcSlFpxxFeignService.inovice(sfxxid, qlrlb, gzlslid);
    }

    /**
     * 获取发票信息并上传电子发票附件
     * @param sfxxid 收费信息ID
     * @param gzlslid 工作流实例ID
     * @return {BdcSlSfxxDO} 不动产收费信息DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/getDzfpAndUpload")
    public BdcSlSfxxDO queryDzfpxx(String sfxxid, String gzlslid){
        if(StringUtils.isAnyBlank(sfxxid, gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID或工作流实例ID");
        }
        // 获取电子发票信息，保存电子发票信息，上传电子发票附件
        this.bdcSlFpxxFeignService.getFpxxAndUploadFpxx(sfxxid, gzlslid);

        // 获取最细收费信息，用于页面收费信息数据刷新
        return this.bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
    }

    /**
     * 作废电子缴款书
     * @param sfxxid  收费信息ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/invalidate")
    public void invalidate(@RequestParam(name="sfxxid") String sfxxid){
        if(StringUtils.isAnyBlank(sfxxid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息ID");
        }
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        if(StringUtils.isBlank(bdcSlSfxxDO.getJfsbm())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到缴费书编码信息");
        }
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("pay_code", bdcSlSfxxDO.getJfsbm());
        Object response = this.exchangeInterfaceFeignService.requestInterface("invalidate", reqMap);
        if(Objects.nonNull(response)){
            CommonResponse commonResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
            if(commonResponse.isSuccess()){
                JSONObject jsonObject = JSON.parseObject((String) commonResponse.getData());
                String succ_code = (String) jsonObject.get("succ_code");
                if(succ_code.equals("0000")){
                    // 将缴款码与缴费书URL置空，同时重新生成sfxxid，保证后续调用非税接口，流水号不会重复
                    BdcSlSfxxDO sfxx = new BdcSlSfxxDO();
                    sfxx.setSfxxid(UUIDGenerator.generate16());
                    sfxx.setJfsbm("");
                    sfxx.setJfsewmurl("");
                    this.bdcSlSfxxFeignService.updateBdcSlSfxxIdWithSfxm(sfxxid, sfxx);
                }else{
                    throw new AppException(ErrorCode.CUSTOM, "作废失败");
                }
            }else{
                throw new AppException(ErrorCode.CUSTOM, "作废失败");
            }
        }
    }

}
