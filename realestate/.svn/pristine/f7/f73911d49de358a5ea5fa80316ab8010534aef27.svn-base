package cn.gtmap.realestate.accept.ui.web.rest.nantong;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.SjclUploadDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQzxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSwFeignService;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2022/11/17
 * @description 不动产一人办件Ontroller
 */
@Controller
@RequestMapping("/rest/v1.0/yrbj")
public class BdcYrbjController extends BaseController {

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    BdcSwFeignService bdcSwFeignService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    /**
     * 一人办件电子合同文件夹名称
     */
    @Value("${yrbj.dzhtwjjmc:商品房买卖合同}")
    private String dzhtWjjmc;
    /**
     * 一人办件权利人签字文件夹名称
     */
    @Value("${yrbj.qlrqz.wjjmc:权利人签名}")
    private String qlrqzWjjmc;
    /**
     * 一人办件义务人签字文件夹名称
     */
    @Value("${yrbj.ywrqz.wjjmc:义务人签名}")
    private String ywrqzWjjmc;


    /**
     * @param gzlslid 工作流实例ID
     * @return 推送结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 税务信息推送调用税务A001,A002接口（南通、常州税务信息增加配偶信息、家庭成员信息、附件传地址）
     */
    @ResponseBody
    @GetMapping("/tsJsxx/{gzlslid}/{beanName}")
    public Object tsYrbjJsxx(@PathVariable(value = "gzlslid") String gzlslid,
                             @PathVariable(value = "beanName") String beanName,
                             @RequestParam(value = "xmid", required = false) String xmid,
                             @RequestParam(value = "fwlx", required = false) String fwlx) {
        if (StringUtils.isAnyBlank(gzlslid, beanName)) {
            throw new MissingArgumentException("缺失参数：工作流实例ID或接口标识名称。");
        }
        return bdcSwFeignService.getYrbjSwxx(gzlslid, beanName, xmid, fwlx);
    }

    /**
     * @param fcjyBaxxDTO 房产交易备案信息
     * @param xmid 项目ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 一人办件业务获取交易信息
     */
    @ResponseBody
    @PostMapping("/jyxx")
    public void dealYrbjJyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO, @RequestParam(value = "xmid") String xmid) {
        bdcSlJyxxFeignService.dealYrbjJyxx(fcjyBaxxDTO, xmid);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送一人办件的交易信息附件
     * @date : 2022/8/12 11:33
     */
    @ResponseBody
    @PostMapping("/fjxx")
    public void uploadYrbjFjxx(@NotBlank(message = "上传一体化附件信息不可为空") @RequestBody String fjxx) throws IOException {
        JSONObject jsonObject = JSON.parseObject(fjxx);
        SjclUploadDTO sjclUploadDTO = new SjclUploadDTO();
        String fjnr = jsonObject.getString("fjnr");
        //先删除附件，重新上传
        if (StringUtils.isNotBlank(fjnr) && StringUtils.isNotBlank(jsonObject.getString("gzlslid"))) {
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(jsonObject.getString("gzlslid"), dzhtWjjmc);
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                bdcSlSjclFeignService.deleteBdcSlSjclBySjclid(bdcSlSjclDOList.get(0).getSjclid());
            }
            if (!fjnr.startsWith("data:")) {
                fjnr = CommonConstantUtils.BASE64_QZ_PDF + fjnr;
            }
            bdcUploadFileUtils.uploadBase64File(fjnr, jsonObject.getString("gzlslid"), dzhtWjjmc, "", CommonConstantUtils.WJHZ_PDF);
        }
    }

    /**
     * 交易核验（使用原产权证号和义务人证件号查询住建接口，根据xzzt和dyzt判断住建系统中该产权是否有抵押或者查封信息）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param xmid  项目ID
     * @return 交易核验结果
     */
    @ResponseBody
    @GetMapping("/jyhy")
    public Object jyhy(@RequestParam(value="gzlslid")String gzlslid, @RequestParam(value = "xmid") String xmid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数工作流实例ID");
        }
        return this.bdcSlJyxxFeignService.jyhy(gzlslid, xmid);
    }

    /**
     * 保存权利人、义务人签字信息，上传至附件中
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcQzxxDTO 签字信息DTO
     */
    @ResponseBody
    @PostMapping("/saveQzxx")
    public void saveQzxx(@RequestBody BdcQzxxDTO bdcQzxxDTO) {
        if(Objects.isNull(bdcQzxxDTO) && StringUtils.isBlank(bdcQzxxDTO.getGzlslid())
                && StringUtils.isBlank(bdcQzxxDTO.getQznr())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID或签字内容信息。");
        }
        // 上传签字信息值大云
        String base64 = bdcQzxxDTO.getQznr();
        if (!base64.startsWith("data:")) {
            base64 = CommonConstantUtils.BASE64_QZ_IMAGE + base64;
        }
        try {
            String wjjmc = "";
            if(CommonConstantUtils.QLRLB_QLR.equals(String.valueOf(bdcQzxxDTO.getQzrlb()))){
                wjjmc = qlrqzWjjmc;
            }else{
                wjjmc = ywrqzWjjmc;
            }
            bdcUploadFileUtils.uploadBase64File(base64, bdcQzxxDTO.getGzlslid(), wjjmc, bdcQzxxDTO.getGzlslid() + bdcQzxxDTO.getQzrlb(), ".jpg");
        } catch (IOException e) {
            LOGGER.error("当前流程{}上传签名附件失败", bdcQzxxDTO.getGzlslid(), e);
            throw new AppException(ErrorCode.CUSTOM, "上传签名附件失败");
        }
    }

    /**
     * 推送受理信息给房产交易系统
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     */
    @ResponseBody
    @GetMapping("/tszjslxx")
    public void tszjslxx(@RequestParam(value="gzlslid")String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID。");
        }
        this.bdcSlJyxxFeignService.tsZjSlxx(gzlslid);
    }

}
