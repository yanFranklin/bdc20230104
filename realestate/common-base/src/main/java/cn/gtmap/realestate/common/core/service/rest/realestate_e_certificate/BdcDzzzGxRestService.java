package cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, 2020/02/17 8:50
 * @description 电子证照共享接口
 */
public interface BdcDzzzGxRestService {

    /**
     * @param jsonString
     * @return
     * @description 电子证照获取token
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v1.0/zzgx/token")
    DzzzResponseModel creatToken(@RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照检索接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v1.0/zzgx/zzjs")
    DzzzResponseModel zzjs(@RequestParam("token") String token,  @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照查询接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v1.0/zzgx/zzcx")
    DzzzResponseModel zzcx(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照文件下载接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v1.0/zzgx/zzxxxz")
    DzzzResponseModel zzxxxz(@RequestParam("token") String token, @RequestBody String jsonString);


    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照文件下载接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v1.0/zzgx/cl/zzxxxz")
    DzzzResponseModel clzzxxxz(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照地址下载接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v1.0/zzgx/zzdzxz")
    DzzzResponseModel zzdzxz(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照通过地址下载证照文件接口v1.0
     */
    @GetMapping(path= "/realestate-e-certificate/rest/v1.0/zzgx/zzxzfile")
    DzzzResponseModel zzxzfile(@RequestParam("token") String token,  @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照获取元数据接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v1.0/zzgx/zzysj")
    DzzzResponseModel zzysj(@RequestParam("token") String token,  @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照文件验证接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v1.0/zzgx/zzwjyz")
    DzzzResponseModel zzwjyz(@RequestParam("token") String token,  @RequestBody String jsonString);
}
