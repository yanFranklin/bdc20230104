package cn.gtmap.realestate.common.core.support.request;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcGdxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.TokenUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/4/29
 * @description 获取各个公用的url地址的controller服务
 */
@RestController
@RequestMapping(value = "/rest/v1.0/url/common")
public class UrlController {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);
    @Autowired
    private BdcGdxxFeignService bdcGdxxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Value("${html.version:standard}")
    private String htmlVersion;

    /**
     * 页面是否加载姓名水印，默认true
     */
    @Value("${html.jzsy:true}")
    private String jzsy;

    /**
     * @description 盐城档案系统页面地址
     */
    @Value("${url.daxt:}")
    private String daxtUrl;
    /**
     * @description 盐城控制是否显示档案调用（旧）按钮
     */
    @Value("${yancheng.dadyj:}")
    private Boolean dadyj;

    @Autowired
    protected UserManagerUtils userManagerUtils;

    /**
     * 用户权限
     */
    @Autowired
    private TokenUtils tokenUtils;


    @GetMapping(value = "/bdcda")
    public String getBdcDaUrl(@RequestParam(value = "gzlslid") String gzlslid,
                              @RequestParam(value = "xmid") String xmid,
                              @RequestParam(value = "damx", required = false) String damx) {
        // 优先使用gzlslid,空值则用xmid
        if (StringUtils.isBlank(gzlslid)) {
            if(StringUtils.isBlank(xmid)){
                throw new MissingArgumentException("缺失参数！");
            }
            BdcXmQO BdcXmQO = new BdcXmQO();
            BdcXmQO.setXmid(xmid);
            List<BdcXmDO> listxm = bdcXmFeignService.listBdcXm(BdcXmQO);
            if(CollectionUtils.isNotEmpty(listxm)){
                gzlslid = listxm.get(0).getGzlslid();
                return bdcGdxxFeignService.getBdcGdxxByGzlslid(gzlslid, damx,xmid);
            }else{
                return "";
            }
        }else{
            return bdcGdxxFeignService.getBdcGdxxByGzlslid(gzlslid, damx,"");
        }
    }

    /**
     * @param xmid
     * @return url 档案系统的页面地址
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据xmid查询档案系统的页面地址
     */
    @GetMapping(value = "/bdcdaxturl")
    public String getBdcDaxtUrl(@RequestParam(value = "xmid") String xmid) {
        if(StringUtils.isBlank(daxtUrl)){
            throw new MissingArgumentException("未配置档案系统页面地址url.daxtUrl！");
        }
        if(StringUtils.isBlank(xmid)){
            throw new MissingArgumentException("缺失参数xmid！");
        }
        String url = daxtUrl;
        String id = null;

        //优先根据xmid取归档信息表的daid
        id = bdcGdxxFeignService.queryBdcDaid(xmid);

        //归档信息表中找不到对应daid时，用xmid取bdc_xm表的daywh字段
        if (StringUtils.isBlank(id)) {
            id = bdcXmFeignService.queryDaywh(xmid);
        }

        if (StringUtils.isNotBlank(id)) {
            url = url + id;
            return url;
        } else {
            return null;
        }
    }

    @GetMapping("/watermark")
    public Object getWatermark(){
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(userDto == null) {
            throw new AppException("水印获取用户信息失败");
        }
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("username",userDto.getAlias());
        resultMap.put("jzsy",jzsy);
        return resultMap;
    }

    @GetMapping("/token")
    public String getToken(){
        return tokenUtils.getToken();

    }

    @GetMapping("/dadyj")
    public Boolean getDadyj(){
        return dadyj;
    }

}
