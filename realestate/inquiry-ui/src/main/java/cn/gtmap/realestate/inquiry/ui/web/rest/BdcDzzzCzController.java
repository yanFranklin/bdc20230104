package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.service.feign.init.BdcOfdToImgFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcEcertificateConfigDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.HefeiDzzzZzDataDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDzzzCxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzZzQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDzzzCzFeginService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/2/26
 * @description 证书证明电子证照操作
 */
@RestController
@RequestMapping(value = "/rest/v1.0/dzzzcz")
public class BdcDzzzCzController extends BaseController {

    @Autowired
    private BdcDzzzCzFeginService bdcDzzzCzFeginService;

    @Autowired
    private BdcOfdToImgFeignService bdcOfdToImgFeignService;

    @Autowired
    StorageClientMatcher storageClient;

    @Autowired
    ECertificateFeignService eCertificateFeignService;

    @Autowired
    private ProcessDefinitionClient processDefinitionClient;

    @Value("${url.storage}")
    String storageUrl;

    /**
     * 证书服务
     */
    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    /**
     * 查询证书证明台账
     *
     * @param bdcDzzzQO 查询参数
     * @return BdcDzzzCxDTO 分页台账
     * @Date 2020/3/2
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/page")
    public Object listBdcDzzzCzByPage(@LayuiPageable Pageable pageable, BdcDzzzQO bdcDzzzQO) {
        bdcDzzzQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("dzzzck", "", ""));
        Page<BdcDzzzCxDTO> bdcDzzzCxDTOS = bdcDzzzCzFeginService.listBdcDzzzByPage(pageable, JSON.toJSONString(bdcDzzzQO));
        return super.addLayUiCode(bdcDzzzCxDTOS);
    }

    /**
     * 创建电子证照
     *
     * @param xmids
     * @return 创建个数
     * @Date 2020/2/29
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/createDzzz")
    public Integer createDzzz(@RequestBody List<String> xmids) {
        return bdcDzzzCzFeginService.createDzzz(xmids);
    }

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/2/29
     * @description 注销电子证照
     */
    @PostMapping("/cancelDzzz")
    Integer cancelDzzz(@RequestBody List<String> xmids) {
        return bdcDzzzCzFeginService.cancelDzzz(xmids);
    }

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/2/29
     * @description 作废电子证照
     */
    @PostMapping("/zfDzzz")
    Integer zfDzzz(@RequestBody List<String> xmids) {
        return bdcDzzzCzFeginService.zfDzzz(xmids);
    }

    @GetMapping("/getFilePath")
    Map<String, String> getFile(@RequestParam(value = "gzlslid") String gzlslid,
                                @RequestParam(value = "bdcqzh") String bdcqzh,
                                @RequestParam(value = "zsid", required = false) String zsid) {

        BdcEcertificateConfigDTO bdcEcertificateConfigDTO = eCertificateFeignService.queryConfig();
        StorageDto storageDto = new StorageDto();
        if (org.apache.commons.lang3.StringUtils.isAnyBlank(gzlslid, bdcqzh)) {
            throw new MissingArgumentException("gzlslid和bdcqzh不能为空！");
        }
        if (!org.apache.commons.lang3.StringUtils.equals(CommonConstantUtils.WJZX_CLIENTID_ECERTIFICATE, bdcEcertificateConfigDTO.getUploadStorageRoot())
                && !org.apache.commons.lang3.StringUtils.equals(CommonConstantUtils.WJZX_CLIENTID, bdcEcertificateConfigDTO.getUploadStorageRoot())) {
            throw new MissingArgumentException("配置错误！期望uploadStorageRoot为" + CommonConstantUtils.WJZX_CLIENTID_ECERTIFICATE +
                    "或" + CommonConstantUtils.WJZX_CLIENTID + "，实际值为" + bdcEcertificateConfigDTO.getUploadStorageRoot());
        }
        Map<String, String> map = new HashMap<>();
        //获取
        List<StorageDto> storageDtoList = storageClient.listStoragesByName(bdcEcertificateConfigDTO.getUploadStorageRoot(),
                gzlslid, null, bdcqzh + ".pdf", null, 3);
        if (CollectionUtils.isNotEmpty(storageDtoList)) {
            storageDto = storageDtoList.get(0);
        } else {
            if (StringUtils.isNotBlank(zsid)) {
                BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(zsid);
                if (StringUtils.isNotBlank(bdcZsDO.getStorageid())) {
                    map.put("id", bdcZsDO.getStorageid());
                }
            }

        }


        if (StringUtils.isNotBlank(storageDto.getId())) {
            map.put("id", storageDto.getId());
            // 前端传递的 zsid 不为空则更新证书表中的 stroageid
            if (StringUtils.isNotBlank(zsid)) {
                bdcDzzzCzFeginService.updateZsStroageId(storageDto.getId(), zsid);
            }
        }
        map.put("url", storageUrl);

        return map;
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @Date 2022/8/23
     * @description ofd转图片
     */
    @GetMapping("/getofdimg")
    Map<String, String> getofdimg(@RequestParam(value = "storageid") String storageid) {

        if (StringUtils.isBlank(storageid)) {
            throw new MissingArgumentException("参数storageid不能为空");
        }
        Map<String, String> map = new HashMap<>();
        String result = bdcOfdToImgFeignService.ofdtoimg(storageid);
        map.put("img", result);
        return map;
    }

    /**
     * 判断电子证照是否是历史状态
     *
     * @param zsids 证书ids
     * @return flag
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/queryDzzzZt")
    public Integer sfYzx(@RequestBody List<String> zsids) {
        return bdcDzzzCzFeginService.sfYzx(zsids);
    }

    /**
     * 下载电子证照并上传到登记
     *
     * @param xmid xmid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/reUpload")
    public void reUpload(@RequestParam String xmid) {
        bdcDzzzCzFeginService.reUploadDzzz(xmid);
    }

    /**
     * @return java.lang.Object
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [pageable, bdcDzzzZzQO]
     * @description 合肥 查询电子证照需要制证的台账 （权属状态为现势、流程状态已办结但没有生成电子证照的数据）
     */
    @PostMapping("/queryDzzzZz")
    public Object queryDzzzZz(@RequestBody BdcDzzzZzQO bdcDzzzZzQO) {
        if (StringToolUtils.isAllNullOrEmpty(bdcDzzzZzQO.getSlbh(), bdcDzzzZzQO.getStartTime(),
                bdcDzzzZzQO.getEndTime(), bdcDzzzZzQO.getZslx(), bdcDzzzZzQO.getProcessDefKey())) {
            throw new MissingArgumentException("所有查询参数都为空,无法查询！");
        }
        Page<HefeiDzzzZzDataDTO> hefeiDzzzZzDataDTOPage = bdcDzzzCzFeginService.queryDzzzZz(bdcDzzzZzQO);
        return addLayUiCode(hefeiDzzzZzDataDTOPage);
    }

    /**
     * @return void
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [zsids]
     * @description 合肥 生成电子证照
     */
    @PostMapping("/createHefeiDzzz")
    public Object createHefeiDzzz(@RequestBody List<String> zsids) {
        if (CollectionUtils.isNotEmpty(zsids)) {
            for (String zsid : zsids) {
                try {
                    eCertificateFeignService.createHefeiDzzz("", zsid, "", "");

                    LOGGER.info("制作电子证照完成，当前zsid：{}完成", zsid);
                } catch (Exception e) {
                    LOGGER.error("制作电子证照异常，当前zsid：{}", zsid);
                    LOGGER.error("异常信息:", e);
                    throw new AppException("生成电子证照失败！当前zsid：" + zsid);
                }
            }
        }
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "生成电子证照成功！");
        return map;
    }

    /**
     * @return void
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [zsids]
     * @description 合肥 注销电子证照
     */
    @PostMapping("/cancelHefeiDzzz")
    public Object cancelHefeiDzzz(@RequestBody List<String> zsids) {
        if (CollectionUtils.isNotEmpty(zsids)) {
            for (String zsid : zsids) {
                try {
                    eCertificateFeignService.zxHefeiDzzz("", zsid);
                    LOGGER.info("注销电子证照完成，当前zsid：{}完成", zsid);
                } catch (Exception e) {
                    LOGGER.error("注销电子证照异常，当前zsid：{}", zsid);
                    LOGGER.error("异常信息:", e);
                    throw new AppException("注销电子证照失败！当前zsid：" + zsid);
                }
            }
        }
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "注销电子证照成功！");
        return map;
    }


    /**
     * @return java.util.List<cn.gtmap.gtc.workflow.domain.manage.ProcessDefData>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @description 获取所有流程定义信息（用于查询条件-流程名称下拉框展示）
     */
    @GetMapping("/lcmc/all")
    @ApiOperation(value = "获取所有已经发布的最新版本的流程定义")
    @ResponseStatus(HttpStatus.OK)
    public List<ProcessDefData> getAllProcessDefData() {
        return processDefinitionClient.getAllProcessDefData();
    }

}
