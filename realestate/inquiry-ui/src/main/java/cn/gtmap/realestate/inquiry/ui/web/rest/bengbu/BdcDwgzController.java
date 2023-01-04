package cn.gtmap.realestate.inquiry.ui.web.rest.bengbu;


import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcDwgzglDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlWjscDTO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.bengbu.BdcDwgzglFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 16:02 2020/7/27
 * @description 单位公章管理controller
 */
@RestController
@RequestMapping(value = "/rest/v1.0/dwgz")
public class BdcDwgzController extends BaseController {

    @Value("${app.oauth}")
    public String authUrl;
    @Value("${security.oauth2.client.client-id}")
    public String clientId;
    @Value("${security.oauth2.client.client-secret}")
    public String clientSecret;

    @Autowired
    private BdcDwgzglFeignService bdcDwgzglFeignService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    UserManagerUtils userManagerUtils;

    private static final String wjglqx = "{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanDelete\":0,\"CanRename\":0,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":0,\"CanTakePhoto\":0,\"CanScan\":0,\"CanEdit\":-1}";


    /**
     * 分页查询单位公章信息
     *
     * @param pageable
     * @param dwgzglQO
     * @return BdcCfxxDTO                                                             ;>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/list")
    public Object dwgzListByPage(@LayuiPageable Pageable pageable, BdcDwgzglDO dwgzglQO) {
        Page<BdcDwgzglDO> bdcDwgzglDOPage = bdcDwgzglFeignService.listBdcDwgzByPage(pageable, JSON.toJSONString(dwgzglQO));
        return super.addLayUiCode(bdcDwgzglDOPage);
    }

    /**
     * @param bdcDwgzglDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 插入新的单位公章信息
     */
    @ResponseBody
    @PostMapping(value = "/saveDwgz")
    @ResponseStatus(HttpStatus.OK)
    public BdcDwgzglDO saveDwgz(@RequestBody BdcDwgzglDO bdcDwgzglDO) {
        return bdcDwgzglFeignService.insertBdcDwgz(bdcDwgzglDO);
    }

    /**
     * 更新单位公章信息
     *
     * @param bdcDwgzglDO
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 19:52 2020/7/27
     */
    @ResponseBody
    @PutMapping("/updateDwgz")
    public Integer updateDwgz(@RequestBody BdcDwgzglDO bdcDwgzglDO) {
        return bdcDwgzglFeignService.updateBdcDwgz(bdcDwgzglDO);
    }

    @ResponseBody
    @GetMapping("/getFileCs")
    public BdcSlWjscDTO bdcSlWjscDTO(Boolean sfhqqx) {
        BdcSlWjscDTO bdcSlWjscDTO = new BdcSlWjscDTO();
        bdcSlWjscDTO.setToken(getAccountToken());
        bdcSlWjscDTO.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        if (Objects.nonNull(sfhqqx) && sfhqqx) {
            bdcSlWjscDTO.setsFrmOption(wjglqx);
        }
        return bdcSlWjscDTO;
    }

    /**
     * 上传文件夹
     *
     * @param spaceId
     * @param wjjmc 文件夹名称
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseBody
    @GetMapping("/folder")
    public Object createFolder(@RequestParam("spaceId") String spaceId, @RequestParam("wjjmc") String wjjmc) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userid="";
        if(userDto != null){
            userid =userDto.getId();
        }
        // 新建文件夹
        return storageClient.createRootFolder("clientId", spaceId, wjjmc, userid);
    }

}
