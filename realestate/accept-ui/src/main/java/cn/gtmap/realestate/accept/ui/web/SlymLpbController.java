package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/11/4
 * @description 楼盘表
 */
@Controller
@RequestMapping("slym/lpb")
public class SlymLpbController extends BaseController {
    @Autowired
    AcceptBdcdyFeignService acceptBdcdyFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例获取逻辑幢
     */
    @ResponseBody
    @GetMapping("")
    public List<FwLjzDO> listFwLjzByGzlslid(@RequestParam(name = "processInsId") String processInsId,@RequestParam(name = "qjgldm",required = false) String qjgldm) {
        if(StringUtils.isBlank(processInsId)){
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        return acceptBdcdyFeignService.listFwLjzByGzlslid(processInsId,qjgldm);

    }

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例获取逻辑幢分页
     */
    @ResponseBody
    @GetMapping(value = "/listFwLjzByPageJson")
    public Object listFwLjzByPageJson(@LayuiPageable Pageable pageable, String gzlslid,String qjgldm) {
        List<FwLjzDO> fwLjzDOList =listFwLjzByGzlslid(gzlslid,qjgldm);
        //封装返回分页信息
        return addLayUiCode(new PageImpl(fwLjzDOList, pageable, fwLjzDOList.size()));
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取三维楼盘表URL
     */
    @ResponseBody
    @GetMapping(value = "/url/swlpb")
    public Object swlpb(BdcSlCshDTO bdcSlCshDTO) {
        UserDto user = userManagerUtils.getCurrentUser();
        if (null == user) {
            throw new UserInformationAccessException(messageProvider.getMessage("message.nouser"));
        }
        bdcSlCshDTO.setCzrid(user.getId());
        return exchangeInterfaceFeignService.requestInterface("swlpb_url", bdcSlCshDTO);



    }

}
