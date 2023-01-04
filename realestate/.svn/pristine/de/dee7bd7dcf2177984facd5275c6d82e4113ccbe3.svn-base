package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.dto.config.BdcZdChangeDTO;
import cn.gtmap.realestate.common.core.enums.BdcSlZdEnum;
import cn.gtmap.realestate.common.core.enums.BdcZdEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.TokenUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0, 2020/8/4
 * @description 字典对照表
 */
@RestController
@RequestMapping("/rest/v1.0/bdczdpz")
public class BdcZdDzPzController extends BaseController {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZdDzPzController.class);
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private BdcSlZdFeignService bdcSlZdFeignService;

    /**
     * 需要刷新字典项的应用服务名称
     */
    public static final String REFRESH_APP = "register-app";
    /**
     * 需要刷新受理字典项的应用服务名称
     */
    public static final String REFRESH_SL_APP = "accept-app";
    /**
     * 刷新字典项数据的接口地址
     */
    public static final String REFRESH_URL = "/realestate-register/rest/v1.0/bdcZd/refresh/";
    /**
     * 刷新受理字典项数据的接口地址
     */
    public static final String REFRESH_SL_URL = "/realestate-accept/rest/v1.0/bdcslzd/refresh/";

    /**
     * @param tablename 表名
     * @return 获取所有有缓存的不动产字典表
     */
    @GetMapping("/list")
    public List list(String tablename,String zdname) {
        List<JSONObject> bdcZdEnumList = new ArrayList<>();
        if (StringUtils.isBlank(tablename) && StringUtils.isBlank(zdname)) {
            for (BdcZdEnum bdcZdEnum : BdcZdEnum.values()) {
                bdcZdEnumList.add(BdcZdEnum.toJsonObjByBdcZdEnum(bdcZdEnum));
            }
            for (BdcSlZdEnum bdcSlZdEnum : BdcSlZdEnum.values()) {
                bdcZdEnumList.add(BdcSlZdEnum.toJsonObjByBdcSlZdEnum(bdcSlZdEnum));
            }
        } else if (StringUtils.isNotBlank(tablename) && StringUtils.isBlank(zdname)) {
            if(BdcZdEnum.getBdcZdEnumByTableName(tablename) != null){
                bdcZdEnumList.add(BdcZdEnum.toJsonObjByBdcZdEnum(BdcZdEnum.getBdcZdEnumByTableName(tablename)));
            }
            if(BdcSlZdEnum.getBdcSlZdEnumByTableName(tablename) != null){
                bdcZdEnumList.add(BdcSlZdEnum.toJsonObjByBdcSlZdEnum(BdcSlZdEnum.getBdcSlZdEnumByTableName(tablename)));
            }
        }else if (StringUtils.isBlank(tablename) && StringUtils.isNotBlank(zdname)) {
            for (BdcZdEnum bdcZdEnum : BdcZdEnum.values()) {
                if(StringUtils.equals(zdname,bdcZdEnum.name())){
                    bdcZdEnumList.add(BdcZdEnum.toJsonObjByBdcZdEnum(bdcZdEnum));
                }
            }
            for (BdcSlZdEnum bdcSlZdEnum : BdcSlZdEnum.values()) {
                if(StringUtils.equals(zdname,bdcSlZdEnum.name())){
                    bdcZdEnumList.add(BdcSlZdEnum.toJsonObjByBdcSlZdEnum(bdcSlZdEnum));
                }
            }
        }else if (StringUtils.isNotBlank(tablename) && StringUtils.isNotBlank(zdname)) {
            for (BdcZdEnum bdcZdEnum : BdcZdEnum.values()) {
                if(StringUtils.equals(zdname,bdcZdEnum.name()) && StringUtils.equals(tablename,bdcZdEnum.getTableName())){
                    bdcZdEnumList.add(BdcZdEnum.toJsonObjByBdcZdEnum(bdcZdEnum));
                }
            }
            for (BdcSlZdEnum bdcSlZdEnum : BdcSlZdEnum.values()) {
                if(StringUtils.equals(zdname,bdcSlZdEnum.name()) && StringUtils.equals(tablename,bdcSlZdEnum.getTableName())){
                    bdcZdEnumList.add(BdcSlZdEnum.toJsonObjByBdcSlZdEnum(bdcSlZdEnum));
                }
            }
        }
        return bdcZdEnumList;
    }

    /**
     * 刷新
     * @param tablename
     */
    @GetMapping("/refresh")
    public void refresh(String tablename) {
        if (StringUtils.isNotBlank(tablename)) {
            if (BdcZdEnum.getBdcZdEnumByTableName(tablename) != null){
                refreshBdcZdx(tablename,REFRESH_APP,REFRESH_URL);
            }else{
                refreshBdcZdx(tablename,REFRESH_SL_APP,REFRESH_SL_URL);
            }
        }
    }

    /**
     * @param zdmc 表名
     * @return 获取这个字典list
     */
    @GetMapping("/listByName")
    public List listByTableName(String zdmc) {
        List bdcZdList = new ArrayList<>();
        if (StringUtils.isNotBlank(zdmc)) {
            if (BdcZdEnum.getBdcZdEnumByName(zdmc) != null){
                return bdcZdFeignService.queryBdcZd(StringUtils.lowerCase(zdmc));
            }else {
                bdcZdList = bdcSlZdFeignService.queryBdcSlzd(StringUtils.lowerCase(zdmc));
            }

        }
        return bdcZdList;
    }

    /**
     * @param zdmc 表名
     * @return 获取这个字典list
     */
    @GetMapping("/editable")
    public Boolean editable(String zdmc) {
        return getZdEditable(zdmc);
    }

    /**
     * 检测字典是否可编辑
     * @param zdmc
     * @return
     */
    private Boolean getZdEditable(String zdmc) {
        BdcZdEnum bdcZdEnumByName = BdcZdEnum.getBdcZdEnumByName(zdmc);
        if(Objects.nonNull(bdcZdEnumByName)){
            return bdcZdEnumByName.getEditable();
        }
        BdcSlZdEnum bdcSlZdEnumByName = BdcSlZdEnum.getBdcSlZdEnumByName(zdmc);
        if(Objects.nonNull(bdcSlZdEnumByName)){
            return bdcSlZdEnumByName.getEditable();
        }
        return false;
    }

    /**
     * 新增字典项
     *
     * @param bdcZdChangeDTO
     */
    @PostMapping("/addItem")
    public void addZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO) {
        if(!getZdEditable(bdcZdChangeDTO.getZdmc())){
            throw new AppException("当前字典不可编辑");
        }
        bdcZdFeignService.addZdItem(bdcZdChangeDTO);
    }

    /**
     * 删除字典项
     *
     * @param bdcZdChangeDTO
     */
    @PostMapping("/delItem")
    public void delZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO) {
        if(!getZdEditable(bdcZdChangeDTO.getZdmc())){
            throw new AppException("当前字典不可编辑");
        }
        bdcZdFeignService.delZdItem(bdcZdChangeDTO);
    }

    /**
     * 编辑字典项
     *
     * @param bdcZdChangeDTO
     */
    @PostMapping("/editItem")
    public void editZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO) {
        if(!getZdEditable(bdcZdChangeDTO.getZdmc())){
            throw new AppException("当前字典不可编辑");
        }
        bdcZdFeignService.editZdItem(bdcZdChangeDTO);
    }

    /**
     * 刷新不动产字典项内容
     *
     * @param tablename 表名
     */
    private void refreshBdcZdx(String tablename,String appName,String url) {
        if (StringUtils.isAnyBlank(tablename)) {
            return;
        }
        // 获取注册中心上所有的应用名称
        List<String> servicesList = discoveryClient.getServices();
        if (CollectionUtils.isNotEmpty(servicesList)) {
            for (String service : servicesList) {
                if (StringUtils.equals(service, appName)) {
                    // 通过应用名称获取相同应用名称的注册实例信息
                    List<ServiceInstance> sis = discoveryClient.getInstances(service);
                    for (ServiceInstance instance : sis) {
                        try {
                            // 通过Rpc接口调用，请求各实例的缓存刷新接口
                            RestTemplate template = new RestTemplate();
                            String requestUrl = new URIBuilder(instance.getUri().toString() + url + tablename)
                                    .addParameter("access_token", tokenUtils.getAccessToken()).build().toString();
                            template.getForObject(requestUrl.trim(), String.class);
                        } catch (Exception e) {
                            LOGGER.error("请求刷新不动产字典项数据失败：{}", e.getMessage());
                        }
                    }
                }
            }
        }
    }

}
