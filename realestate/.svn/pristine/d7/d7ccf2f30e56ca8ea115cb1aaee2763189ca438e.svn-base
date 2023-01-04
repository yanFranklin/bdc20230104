package cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, 2020/02/17 8:50
 * @description 电子证照市级中转接口
 */
public interface BdcDzzzAppearCityRestService {

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照签发生成接口v2.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgl/zzpdf")
    DzzzResponseModel zzpdf(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照注销接口v2.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgl/zzzt")
    DzzzResponseModel zzzt(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照文件下载接口v2.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgx/zzxxxz")
    DzzzResponseModel zzxxxz(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照文件地址获取接口v2.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgx/zzdzxz")
    DzzzResponseModel zzdzxz(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照证照查询接口v2.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgx/zzcx")
    DzzzResponseModel zzcx(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照证照检索接口v2.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgx/zzjs")
    DzzzResponseModel zzjs(@RequestParam("token") String token, @RequestBody String jsonString);
}
