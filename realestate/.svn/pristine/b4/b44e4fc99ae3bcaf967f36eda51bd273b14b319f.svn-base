package cn.gtmap.realestate.inquiry.ui.web.rest.changzhou;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.changzhou.BdcZzqzCzFeginService;
import cn.gtmap.realestate.inquiry.ui.core.qo.BdcDzzqQO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 电子签章操作
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午8:34 2021/1/11
 */
@RestController
@RequestMapping(value = "/rest/v1.0/zzqzcz")
public class BdcZzqzCzController extends BaseController {

    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    ECertificateFeignService eCertificateFeignService;
    @Value("${url.storage}")
    String storageUrl;
    @Autowired
    private BdcZzqzCzFeginService bdcZzqzCzFeginService;

    /**
     * 创建电子证照
     *
     * @param xmids
     * @return 创建个数
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/createDzzz")
    public Integer createDzzz(@RequestBody List<String> xmids) {
        return bdcZzqzCzFeginService.createDzzz(xmids);
    }

    /**
     * 注销
     *
     * @param xmids 项目 id 集合
     * @return java.lang.Integer 电子证照数目
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/cancelDzzz")
    Integer cancelDzzz(@RequestBody List<String> xmids) {
        return bdcZzqzCzFeginService.cancelDzzz(xmids);
    }

    /**
     * 作废
     *
     * @param xmids 项目 id 集合
     * @return java.lang.Integer 电子证照数目
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/zfDzzz")
    Integer zfDzzz(@RequestBody List<String> xmids) {
        return bdcZzqzCzFeginService.zfDzzz(xmids);
    }

    /**
     * 判断是否是历史状态
     *
     * @param zsids 证书ids
     * @return java.lang.Integer
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/queryDzzzZt")
    public Integer sfYzx(@RequestBody List<String> zsids) {
        return bdcZzqzCzFeginService.sfYzx(zsids);
    }

    /**
     * 推送生成单个证书对应的电子签章
     * @param zsid 证书id
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PutMapping("/zsdzqz")
    public void zsdzqz(@RequestParam("zsid")String zsid) {
        if(StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("未定义要生成签章的证书信息");
        }
        eCertificateFeignService.tsBdcZsDzqz(zsid);
    }

    /**
     * 批量推送生成证书对应的电子签章
     * @param bdcDzzqQOList 不动产电子签章QO集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/pl/zsdzqz")
    public String plzsdzqz(@RequestBody List<BdcDzzqQO> bdcDzzqQOList) {
        if (CollectionUtils.isEmpty(bdcDzzqQOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到证书ID");
        }
        List<String> errorList = new ArrayList<>(10);
        for (BdcDzzqQO bdcDzzqQO : bdcDzzqQOList) {
            try {
                eCertificateFeignService.tsBdcZsDzqz(bdcDzzqQO.getZsid());
            } catch (Exception e) {
                LOGGER.error("批量推送电子签章报错：{}", e.getMessage());
                errorList.add(bdcDzzqQO.getBdcqzh());
            }
        }
        if (CollectionUtils.isNotEmpty(errorList)) {
            return "以下证书推送电子签章出错：" + StringUtils.join(errorList, "，");
        }
        return null;
    }

    /**
     * 注销/作废单个证书对应的电子签章
     * @param zsid 证书id
     * @param bgyy 变更原因（1 注销 2 作废）
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @DeleteMapping("/zsdzqz")
    public void zxZfZsdzqz(@RequestParam("zsid")String zsid, @RequestParam("bgyy")Integer bgyy) {
        if(StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("未定义要注销签章的证书信息");
        }
        eCertificateFeignService.cancelBdcZsZzqz(zsid, bgyy);
    }

    /**
     * 下载单个证书对应的电子签章
     * @param zsid 证书id
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping("/zsdzqz/xz")
    public void xzZsdzqz(@RequestParam("zsid")String zsid) {
        if(StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("未定义要下载签章的证书信息");
        }
        eCertificateFeignService.zzxxxz("", zsid, null);
    }

    /**
     * 批量下载证书对应的电子签章
     * @param bdcDzzqQOList 不动产电子签章QO集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/pl/zsdzqz/xz")
    public String plZsgzqzXz(@RequestBody List<BdcDzzqQO> bdcDzzqQOList) {
        if (CollectionUtils.isEmpty(bdcDzzqQOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到证书ID");
        }
        List<String> errorList = new ArrayList<>(10);
        for (BdcDzzqQO bdcDzzqQO : bdcDzzqQOList) {
            try {
                eCertificateFeignService.zzxxxz("", bdcDzzqQO.getZsid(), null);
            } catch (Exception e) {
                LOGGER.error("批量下载电子签章报错：{}", e.getMessage());
                errorList.add(bdcDzzqQO.getBdcqzh());
            }
        }
        if (CollectionUtils.isNotEmpty(errorList)) {
            return "以下证书下载电子签章出错：" + StringUtils.join(errorList, "，");
        }
        return null;
    }
}
