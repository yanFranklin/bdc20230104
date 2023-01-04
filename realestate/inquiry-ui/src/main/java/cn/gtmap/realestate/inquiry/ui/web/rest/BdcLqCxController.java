package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcLqtjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcLqCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/11/21
 * @description 宣城林权类查询
 */
@RestController
@RequestMapping("/rest/v1.0/lqcx")
public class BdcLqCxController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcLqCxController.class);
    @Autowired
    private BdcLqCxFeignService bdcLqCxFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private UserManagerUtils userManagerUtils;

    /**
     * 统计林权类数据
     *
     * @param bdcLqtjQO
     * @return java.lang.Object
     * @date 2022.11.21
     * @author wutao
     */
    @PostMapping(value = "/tjcx")
    public Object tjCx(@RequestBody BdcLqtjQO bdcLqtjQO) {
        if(bdcLqtjQO == null || StringUtils.isBlank(bdcLqtjQO.getKssj())  || StringUtils.isBlank(bdcLqtjQO.getJzsj()) || CollectionUtils.isEmpty(bdcLqtjQO.getQxdmList())){
            throw new MissingArgumentException("缺失查询条件，开始时间、结束时间和区县代码都不能为空");
        }
       return bdcLqCxFeignService.listTjCx(bdcLqtjQO);
    }

    /**
     * 通过角色获取区县代码
     *
     * @param
     * @return java.lang.Object
     * @date 2022.11.21
     * @author wutao
     */
    @PostMapping(value = "/getQxdmByJs")
    public Object getQxdmByJs() {
        List<Map> result = new ArrayList<Map>();
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if(currentUser ==null){
            //用户信息为空
            LOGGER.info("当前用户不存在");
            return result;
        }
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        List<Map> qxzdList = zdMap.get("qx");
        // 管理员不做限制处理
        if (currentUser.getAdmin() == 1) {
            return qxzdList;
        }
        // 根据角色过滤区县代码
        List<String> qxdmCodeList = Container.getBean(BdcConfigUtils.class).qxdmFilter("lqtj","","");
        for(String qxdm : qxdmCodeList){
            for(Map qxzdMap :qxzdList){
                if(qxdm.equals(qxzdMap.get("DM").toString())){
                    result.add(qxzdMap);
                }
            }
        }
        return result;
    }
}