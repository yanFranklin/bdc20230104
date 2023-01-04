package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.RegisterWorkflowFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/08/03
 * @description 常用手动补偿服务
 */
@RestController
@RequestMapping(value = "/rest/v1.0/sdbcfw")
public class BdcSdbcfwController extends BaseController{

    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    private ECertificateFeignService eCertificateFeignService;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    private RegisterWorkflowFeignService registerWorkflowFeignService;

    /**
     * 生成证书
     *
     * @param gzlslid
     * @date 2022/08/03
     * @author wutao
     */
    @GetMapping("/sczs")
    public String sczs(@RequestParam(value = "gzlslid") String gzlslid) throws Exception {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失请求参数gzlslid");
        }
        List<BdcZsDO> bdcZsDOS = bdcZsInitFeignService.initBdcqzs(gzlslid, false);
        return "证书生成完成";
    }

    /**
     * 生成证号
     *
     * @param gzlslid
     * @date 2022/08/03
     * @author wutao
     */
    @GetMapping("/sczh")
    public String sczh(@RequestParam(value = "gzlslid") String gzlslid) throws Exception {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失请求参数gzlslid");
        }
        bdcZsFeignService.generateBdcqzhOfPro(gzlslid);
        return "证号生成完成";
    }

    /**
     * 生成电子签章
     *
     * @param gzlslid
     * @date 2022/08/03
     * @author wutao
     */
    @GetMapping("/scdzqz")
    public String scdzqz(@RequestParam(value = "gzlslid") String gzlslid) throws Exception {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失请求参数gzlslid");
        }
        eCertificateFeignService.changzhouZzqz(gzlslid);
        return "电子签章生成完成";
    }

    /**
     * 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     *
     * @param gzlslid
     * @date 2022/08/03
     * @author wutao
     */
    @GetMapping("/gxqszt")
    public String gxqszt(@RequestParam(value = "gzlslid") String gzlslid) throws Exception {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失请求参数gzlslid");
        }
        registerWorkflowFeignService.updateBdcDbxxAndQsztSyncQj(gzlslid, userManagerUtils.getCurrentUserName());
        return "改变权属状态，保存登簿完成";
    }

    /**
     * 生成电子证照
     *
     * @param gzlslid
     * @date 2022/08/03
     * @author wutao
     */
    @GetMapping("/scdzzz")
    public String scdzzz(@RequestParam(value = "gzlslid") String gzlslid) throws Exception {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失请求参数gzlslid");
        }
        eCertificateFeignService.createECertificate(gzlslid, userManagerUtils.getCurrentUserName());
        return "电子证照生成完成";
    }

    /**
     * 常州 推送单个证书电子签章
     * @param zsid 证书id
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @GetMapping("/tsBdcZsDzqz")
    public String tsBdcZsDzqz(@RequestParam(value = "zsid") String zsid) throws Exception {
        if(StringUtils.isBlank(zsid)){
            throw new MissingArgumentException("缺失请求参数zsid");
        }
        eCertificateFeignService.tsBdcZsDzqz(zsid);
        return "电子签章生成完成";
    }




}
