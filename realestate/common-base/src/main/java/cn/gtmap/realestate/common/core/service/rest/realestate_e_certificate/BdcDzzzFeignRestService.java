package cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate;

import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.SyncDzzzClxxDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, 2020/02/17 8:50
 * @description 电子证照共享接口
 */
public interface BdcDzzzFeignRestService {

    /**
     * @param jsonString
     * @return
     * @description 电子证照签发生成接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v1.0/zzgl/zzpdf")
    DzzzResponseModel zzpdf(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照注销接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v1.0/zzgl/zzzt")
    DzzzResponseModel zzzt(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照注销接口v2.0(模板配置)
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgl/zzzx")
    DzzzResponseModel zzzx(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照签发生成接口v2.0(模板配置)
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgl/mbpzpdf")
    DzzzResponseModel mbpzpdf(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照检索接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v1.0/zzgx/zzjs")
    DzzzResponseModel zzjs(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照查询接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v1.0/zzgx/zzcx")
    DzzzResponseModel zzcx(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照文件下载接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v1.0/zzgx/zzxxxz")
    DzzzResponseModel zzxxxz(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照文件下载接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v1.0/zzgx/cl/zzxxxz")
    DzzzResponseModel clzzxxxz(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照地址下载接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v1.0/zzgx/zzdzxz")
    DzzzResponseModel zzdzxz(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照通过地址下载证照文件接口v1.0
     */
    @GetMapping(path= "/realestate-e-certificate/feign/v1.0/zzgx/zzxzfile")
    DzzzResponseModel zzxzfile(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照获取元数据接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v1.0/zzgx/zzysj")
    DzzzResponseModel zzysj(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照文件验证接口v1.0
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v1.0/zzgx/zzwjyz")
    DzzzResponseModel zzwjyz(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照签发生成接口v2.0(市级)
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgl/zzpdf")
    DzzzResponseModel zzpdf2(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照签发生成接口v2.0(市级存量)
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgl/clzzpdf")
    DzzzResponseModel clzzpdf(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照注销接口v2.0(市级)
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgl/zzzt")
    DzzzResponseModel zzzt2(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照文件下载接口v2.0(市级)
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgx/zzxxxz")
    DzzzResponseModel zzxxxz2(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照文件地址获取接口v2.0(市级)
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgx/zzdzxz")
    DzzzResponseModel zzdzxz2(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照证照查询接口v2.0(市级)
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgx/zzcx")
    DzzzResponseModel zzcx2(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照证照查询下载接口v2.0(市级)
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgx/zzcxxz")
    DzzzResponseModel zzcxxz(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param jsonString
     * @return
     * @description 电子证照证照检索接口v2.0(市级)
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgx/zzjs")
    DzzzResponseModel zzjs2(@RequestParam("yymc") String yymc, @RequestBody String jsonString);

    /**
     * @param
     * @return
     * @description 同步电子证照材料信息到市级证照库
     */
    @PostMapping(path = "/realestate-e-certificate/feign/v2.0/zzgx/syncclxx")
    void syncDzzzClxx(@RequestBody List<SyncDzzzClxxDTO> syncDzzzClxxDTOList);
}
