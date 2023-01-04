package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmLsgxFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/12
 * @description 选择不动产单元
 */
@Controller
@RequestMapping("/addbdcdyh")
public class AddBdcdyhController extends BaseController {
    @Autowired
    private BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    private BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;
    @Autowired
    private BdcGzlwFeignService bdcGzlwFeignService;


    /**
     * @param bdcCshSlxmDTO 不动产初始化受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 添加不动产单元号
     */
    @ResponseBody
    @PostMapping("")
    public void addBdcdyh(@RequestBody BdcCshSlxmDTO bdcCshSlxmDTO) {
        UserDto user = userManagerUtils.getCurrentUser();
        if (null == user) {
            throw new UserInformationAccessException(messageProvider.getMessage("message.nouser"));
        }
        bdcSlXmFeignService.cshYxxm(user.getId(), bdcCshSlxmDTO);
    }

    /**
     * @param data 不动产初始化受理项目
     * @param qllx 权利类型
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 添加规则例外
     */
    @ResponseBody
    @PostMapping("/addGzlw")
    public void addGzlw(@RequestParam("data") String data, @RequestParam("qllx") String qllx) {
        bdcGzlwFeignService.addShxxDataWithoutSlbh(data, qllx);
    }

    /**
     * @param bdcCshSlxmDTO 不动产初始化受理项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 添加外联证书
     */
    @ResponseBody
    @PostMapping("/addWlzs")
    public void addWlzs(@RequestBody BdcCshSlxmDTO bdcCshSlxmDTO) {
        Date start =new Date();
        bdcSlXmFeignService.wlZs(bdcCshSlxmDTO);
        LOGGER.info("基本信息：{}外联证书耗时：{}",bdcCshSlxmDTO.getJbxxid(),System.currentTimeMillis()-start.getTime());
    }


    /**
     * @param bdcCshSlxmDTO 不动产初始化受理项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 添加外联证书--有抵押时带入抵押信息
     */
    @ResponseBody
    @PostMapping("addWlzs/dyaxx")
    public void addWlzsAndDyaxx(@RequestBody BdcCshSlxmDTO bdcCshSlxmDTO, @RequestParam(name = "xmids", required = false) String xmids) {
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcCshSlxmDTO.getJbxxid());
        if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
            throw new AppException("请先选择不动产单元");
        }
        if (CollectionUtils.isNotEmpty(bdcCshSlxmDTO.getBdcSlYwxxDTOList())) {
            for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcCshSlxmDTO.getBdcSlYwxxDTOList()) {
                if (StringUtils.isNotBlank(bdcSlYwxxDTO.getXmid())) {
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDOList.get(0).getXmid(), bdcSlYwxxDTO.getXmid(), null);
                    if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                        throw new AppException("外联证书已存在，请勿重复关联");
                    }
                }
            }
        }
        bdcSlXmFeignService.wlZsDyaqxx(bdcCshSlxmDTO, xmids);
    }
}
