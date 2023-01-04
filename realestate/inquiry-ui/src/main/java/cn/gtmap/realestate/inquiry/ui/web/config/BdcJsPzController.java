package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcJsQxdmGxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcJsPzFeignService;
import cn.gtmap.realestate.common.util.TokenUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/5/18
 * @description 角色配置
 */
@RestController
@RequestMapping("/rest/v1.0/jspz")
public class BdcJsPzController extends BaseController {

    @Autowired
    BdcJsPzFeignService bdcJsPzFeignService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 需要刷新字典项的应用服务名称
     */
    public static final String REFRESH_APP = "init-app";
    /**
     * 刷新字典项数据的接口地址
     */
    public static final String REFRESH_URL = "/init/rest/v1.0/jspz/qxdm/refresh";

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询数据
     */
    @GetMapping("/page")
    public Object listBdcDsfZdxByPage(@LayuiPageable Pageable pageable, BdcJsQxdmGxDO bdcJsQxdmGxDO) {
        String queryJson = JSON.toJSONString(bdcJsQxdmGxDO);
        return super.addLayUiCode(bdcJsPzFeignService.listBdcJsQxdmGxByPage(pageable, queryJson));
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除角色区县代码配置数据
     */
    @DeleteMapping("")
    public void deleteJsQxdmPz(@RequestBody List<BdcJsQxdmGxDO> bdcJsQxdmGxDOList){
        if(CollectionUtils.isEmpty(bdcJsQxdmGxDOList)){
            throw new AppException("未获取到角色区县代码配置内容");
        }
        for(BdcJsQxdmGxDO jsQxdmGxDO:bdcJsQxdmGxDOList){
            bdcJsPzFeignService.deleteJsQxdmPzByRoleCode(jsQxdmGxDO.getRolecode());
            refreshJsQxdmPz(jsQxdmGxDO.getRolecode());
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存角色区县代码配置数据
     */
    @PatchMapping("/save")
    public void saveJsQxdmPz(@RequestBody BdcJsQxdmGxDO bdcJsQxdmGxDO){
        if(bdcJsQxdmGxDO ==null ||StringUtils.isAnyBlank(bdcJsQxdmGxDO.getQxdm(),bdcJsQxdmGxDO.getRolecode())){
            throw new AppException("未获取到角色区县代码配置内容");
        }
        bdcJsPzFeignService.saveJsQxdmPz(bdcJsQxdmGxDO);
        refreshJsQxdmPz(bdcJsQxdmGxDO.getRolecode());


    }

    /**
     * @param rolecode 角色编码
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 刷新角色区县代码配置内容
     */
    private void refreshJsQxdmPz(String rolecode){
        if(StringUtils.isBlank(rolecode)){
            return;
        }
        // 获取注册中心上所有的应用名称
        List<String> servicesList = discoveryClient.getServices();
        if(CollectionUtils.isNotEmpty(servicesList)){
            for(String service : servicesList){
                if(StringUtils.equals(service, REFRESH_APP)){
                    // 通过应用名称获取相同应用名称的注册实例信息
                    List<ServiceInstance> sis = discoveryClient.getInstances(service);
                    for(ServiceInstance instance: sis){
                        try {
                            // 通过Rpc接口调用，请求各实例的缓存刷新接口
                            RestTemplate template = new RestTemplate();
                            String requestUrl = new URIBuilder(instance.getUri().toString() + REFRESH_URL)
                                    .addParameter("rolecode", rolecode)
                                    .addParameter("access_token", tokenUtils.getAccessToken()).build().toString();
                            template.getForObject(requestUrl.trim(), String.class);
                        } catch (Exception e) {
                            LOGGER.error("请求刷新角色区县代码配置内容失败：{}", e.getMessage());
                        }
                    }
                }
            }
        }
    }
}
