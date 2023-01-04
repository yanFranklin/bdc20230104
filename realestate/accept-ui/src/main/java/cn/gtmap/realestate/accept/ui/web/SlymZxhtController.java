package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlWjscDTO;
import cn.gtmap.realestate.common.core.dto.etl.HtbaDTO;
import cn.gtmap.realestate.common.core.service.feign.etl.BdcHtbaFeginService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 2022/11/18
 * @description 信息注销流程
 */
@Controller
@RequestMapping("/htzx")
public class SlymZxhtController extends BaseController {

    @Autowired
    private BdcHtbaFeginService bdcHtbaFeginService;

    @Autowired
    private StorageClientMatcher storageClient;

    private static final String wjglqx = "{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanDelete\":0,\"CanRename\":0,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":0,\"CanTakePhoto\":0,\"CanScan\":0,\"CanEdit\":-1}";


    /**
     * 分页查询合同备案信息
     *
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("")
    public Object queryHtba(@LayuiPageable Pageable pageable, HtbaDTO htbaDTO) {
        return super.addLayUiCode(bdcHtbaFeginService.listHtbaByPageJson(pageable, JSON.toJSONString(htbaDTO)));
    }


    /**
     * 根据合同编号更新合同状态
     *
     * @return
     * @descripton 备案id，退房原因（注销原因）
     */
    @ResponseBody
    @PostMapping("/htzt")
    public Integer updateHtzt(@RequestParam("baid") String baid, @RequestParam("tfyy") String tfyy) {
        return bdcHtbaFeginService.updateHtzt(baid, tfyy);
    }

    /**
     * 新建附件文件夹
     *
     * @param id 主键id
     * @return String 存储id
     */
    @GetMapping(value = "/createFjgl")
    @ResponseBody
    public String createWjzxid(@RequestParam("id")String id){
        // 获取owner
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userid = userDto == null ? "" : userDto.getId();
        String wjjmc = "合同备案";
        // 创建新文件夹
        StorageDto storageDto = storageClient.createRootFolder("clientId",id,wjjmc,userid);
        LOGGER.info("新建附件文件夹: spaceid{}", storageDto.getSpaceId());
        return storageDto.getId();
    }

    /**
     * 查询附件文件夹是否存在
     *
     * @param id   主键id
     * @return String 存储id
     */
    @PostMapping(value = "/fj")
    @ResponseBody
    public String queryWjzxid(@RequestParam("id") String id) {
        String storageid = "";
        // 获取自定义附件文件夹名称，默认为"附件文件夹"
        String wjjmc = "合同备案";

        // 查询文件夹是否存在
        boolean wjzxidExist = storageClient.checkExist("",  id, "", wjjmc, "", 0);
        LOGGER.info("文件夹是否存在: {}", wjzxidExist);
        if (wjzxidExist) {
            List<StorageDto> storageDtoList = storageClient.listAllRootStorages("", id, "", wjjmc, null, 0, null, "");
            storageid = storageDtoList.get(0).getId();
            LOGGER.info("存在附件文件夹: spaceid={}", storageDtoList.get(0).getSpaceId());
        }
        return storageid;
    }

    @ResponseBody
    @GetMapping("/getFileCs")
    public BdcSlWjscDTO bdcSlWjscDTO(Boolean sfhqqx) {
        BdcSlWjscDTO bdcSlWjscDTO = new BdcSlWjscDTO();
        bdcSlWjscDTO.setToken(queryToken());
        bdcSlWjscDTO.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        if (Objects.nonNull(sfhqqx) && sfhqqx) {
            bdcSlWjscDTO.setsFrmOption(wjglqx);
        }
        return bdcSlWjscDTO;
    }
}
