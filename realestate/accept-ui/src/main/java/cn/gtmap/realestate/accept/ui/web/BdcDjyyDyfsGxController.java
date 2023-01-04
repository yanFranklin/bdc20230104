package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcDjyyDyfsGxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcDjyyDyfsFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/02
 * @Description: 登记原因与抵押方式关系
 */
@RestController
@RequestMapping("/rest/v1.0/djyyDyfsGx")
public class BdcDjyyDyfsGxController {

    @Autowired
    BdcDjyyDyfsFeignService bdcDjyyDyfsFeignService;


    /**
     * @param bdcDjyyDyfsGxDO 登记原因与抵押方式关系DO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取登记原因与抵押方式关系
     */
    @ResponseBody
    @PostMapping("/list")
    public Object listBdcDjyyDyfsGx(@RequestBody BdcDjyyDyfsGxDO bdcDjyyDyfsGxDO) {
        List<BdcDjyyDyfsGxDO> list =  bdcDjyyDyfsFeignService.listBdcDjyyDyfsGx(bdcDjyyDyfsGxDO);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }
}
