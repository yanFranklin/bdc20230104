package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcDsffjXxItemDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSlFjtzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSlFjtzFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/3,1.0
 * @description 收费信息
 */
@Controller
@RequestMapping("/fjtz")
@Validated
public class BdcFjtzController extends BaseController {

    @Autowired
    private BdcSlFjtzFeignService bdcSlFjtzFeignService;

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    StorageClientMatcher storageClient;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 附件重复提示信息日志输出
     */
    private static final String FJCF_TSXX = "附件重复，重复附件名为：{}";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 上传材料成功提示信息日志输出
     */
    private static final String SCFJ_TSXX = "上传附件成功，返回id：{}";

    /**
     * 当前流程附件
     *
     * @param gzlslid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/current/xm/{processInsId}")
    public Object getCurrentXm(@PathVariable(value = "processInsId") String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlXmDO> bdcSlXmDOS = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOS)) {
                return bdcSlXmDOS.get(0);
            } else {
                return null;
            }
        } else {
            throw new AppException("缺失工作流实例id");
        }
    }


    /**
     * 新增附件台账
     *
     * @param bdcSlFjtzDTO
     * @return
     */
    @ResponseBody
    @PostMapping("/addfj")
    public BdcDsffjXxItemDO djjfgl(@RequestBody BdcSlFjtzDTO bdcSlFjtzDTO) {
        return bdcSlFjtzFeignService.addFjxx(bdcSlFjtzDTO);
    }

    /**
     * 地块附件
     *
     * @param dkfjid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/fj/list/{dkfjid}")
    public Object getGdspxxFj(@PathVariable(value = "dkfjid") String dkfjid) {
        if (StringUtils.isNotBlank(dkfjid)) {
            List<StorageDto> storageDtoList = storageClient.queryMenus(CommonConstantUtils.WJZX_CLIENTID,
                    dkfjid, "", null, 0, null, null, null);
            return storageDtoList;
        } else {
            throw new AppException("地块附件id");
        }
    }

    /**
     * 当前流程附件
     *
     * @param gzlslid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/fj/current/list/{processInsId}")
    public Object getCurrentFj(@PathVariable(value = "processInsId") String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            return storageClient.queryMenus(CommonConstantUtils.WJZX_CLIENTID, gzlslid,
                    null, null, 0, null, null, null);
        } else {
            throw new AppException("缺失工作流实例id");
        }
    }

    /**
     * 保存附件
     *
     * @param json
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/fj/save/{processInsId}")
    public Object saveFj(@RequestBody String json, @PathVariable(value = "processInsId") String gzlslid) {
        List<StorageDto> storageDtos = JSON.parseArray(json, StorageDto.class);
        if (CollectionUtils.isNotEmpty(storageDtos)) {
            bdcUploadFileUtils.createFiles(
                    new StorageDto(),
                    storageDtos,
                    gzlslid,
                    true,
                    true,
                    true,
                    false
            );
        }
        return null;
    }
}
