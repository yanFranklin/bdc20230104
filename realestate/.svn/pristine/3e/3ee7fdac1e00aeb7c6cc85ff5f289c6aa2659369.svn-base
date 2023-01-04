package cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, 2020/02/17 8:50
 * @description 电子证照管理接口
 */
public interface BdcDzzzGlRestService {

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照签发生成接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v1.0/zzgl/zzpdf")
    DzzzResponseModel zzpdf(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照注销接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v1.0/zzgl/zzzt")
    DzzzResponseModel zzzt(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照注销接口v2.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgl/zzzx")
    DzzzResponseModel zzzx(@RequestParam("token") String token, @RequestBody String jsonString);

    /**
     * @param token
     * @param jsonString
     * @return
     * @description 电子证照签发生成接口v2.0
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgl/mbpzpdf")
    DzzzResponseModel mbpzpdf(@RequestParam("token") String token, @RequestBody String jsonString);
}
