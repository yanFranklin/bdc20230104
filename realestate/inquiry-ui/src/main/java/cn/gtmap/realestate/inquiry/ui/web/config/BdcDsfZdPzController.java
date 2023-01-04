package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcZdDsfzdgxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.TokenUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/7/17
 * @description 第三方字典项配置
 */
@RestController
@RequestMapping("/rest/v1.0/dsfzdpz")
public class BdcDsfZdPzController extends BaseController {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDsfZdPzController.class);
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    /**
     * 需要刷新字典项的应用服务名称
     */
    public static final String REFRESH_APP = "register-app";
    /**
     * 刷新字典项数据的接口地址
     */
    public static final String REFRESH_URL = "/realestate-register/rest/v1.0/bdcZdDsfzdgx/refresh";

    /**
     * 分页查询第三方字典项数据
     * 按字典标识与第三方系统标识进行分组查询
     * @param pageable       分页查询参数
     * @param bdcZdDsfzdgxDO 第三方字典项查询参数
     * @return 分页查询数据
     */
    @GetMapping("/page")
    public Object listBdcDsfZdxByPage(@LayuiPageable Pageable pageable, BdcZdDsfzdgxDO bdcZdDsfzdgxDO) {
        String queryJson = JSON.toJSONString(bdcZdDsfzdgxDO);
        return super.addLayUiCode(bdcZdFeignService.listBbcDsfZdxGxByPage(pageable, queryJson));
    }

    /**
     * 根据第三方字典项ID删除字典项数据
     * @param id 第三方字典项ID
     */
    @DeleteMapping("")
    public void deleteDsfZdxById(String id){
        if(StringUtils.isNotBlank(id)){
            throw new AppException("未获取到第三方子典项ID。");
        }
        this.bdcZdFeignService.deleteDsfZdxById(id);
    }

    /**
     *  根据字典标识与第三方字典项标识删除字典项
     * @param bdcZdDsfzdgxDOList 第三方字典项内容DTO
     */
    @DeleteMapping("/bs")
    public void deleteDsfZdxBybs(@RequestBody List<BdcZdDsfzdgxDO> bdcZdDsfzdgxDOList){
        if(CollectionUtils.isEmpty(bdcZdDsfzdgxDOList)){
            throw new AppException("未获取到第三方字典项内容");
        }
        for(BdcZdDsfzdgxDO zdx:bdcZdDsfzdgxDOList){
            this.bdcZdFeignService.deleteDsfZdxBybs(zdx);
            refreshDsfZdx(zdx.getZdbs(), zdx.getDsfxtbs());
        }
    }

    /**
     * 通过字典项标识与第三方字典项标识获取第三方字典项内容
     * @param bdcZdDsfzdgxDO 第三方字典项关系实查询参数
     * @return 第三方字典项实体
     */
    @PostMapping("/getDsfZdxBybs")
    public Object getDsfZdxBybs(@RequestBody BdcZdDsfzdgxDO bdcZdDsfzdgxDO){
        if (StringUtils.isBlank(bdcZdDsfzdgxDO.getZdbs()) || StringUtils.isBlank(bdcZdDsfzdgxDO.getDsfxtbs())) {
            throw new MissingArgumentException("缺少参数：字典表名称,第三方系统标识");
        }
        return bdcZdFeignService.queryDsfZdxBybs(bdcZdDsfzdgxDO);
    }

    /**
     * 修改第三方字典项关系内容
     * @param bdcZdDsfzdgxDTO 第三方字典项DTO
     * @return 第三方字典项关系集合
     */
    @PostMapping("/save")
    public List<BdcZdDsfzdgxDO> saveBdcZdDsfzdgx(@RequestBody BdcZdDsfzdgxDTO bdcZdDsfzdgxDTO){
        if (CollectionUtils.isEmpty(bdcZdDsfzdgxDTO.getBdcZdDsfzdgxDOList())) {
            throw new MissingArgumentException("未获取到第三方字典项内容");
        }
        List<BdcZdDsfzdgxDO> bdcZdDsfzdgxDOList = bdcZdFeignService.saveBdcZdDsfzdgx(bdcZdDsfzdgxDTO);
        // 通知register-app实例更新内存缓存
        if(CollectionUtils.isNotEmpty(bdcZdDsfzdgxDOList)){
            this.refreshDsfZdx(bdcZdDsfzdgxDTO.getZdbs(), bdcZdDsfzdgxDTO.getDsfxtbs());
        }
        return bdcZdDsfzdgxDOList;
    }

    /**
     * 刷新第三方字典项内容
     * @param zdbs     字典项标识
     * @param dsfxtbs  第三方系统标识
     */
    private void refreshDsfZdx(String zdbs, String dsfxtbs){
        if(StringUtils.isAnyBlank(zdbs,dsfxtbs)){
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
                                    .addParameter("zdbs", zdbs)
                                    .addParameter("dsfxtbs", dsfxtbs)
                                    .addParameter("access_token", tokenUtils.getAccessToken()).build().toString();
                            template.getForObject(requestUrl.trim(), String.class);
                        } catch (Exception e) {
                            LOGGER.error("请求刷新第三方字典项数据失败：{}", e.getMessage());
                        }
                    }
                }
            }
        }
    }

}
