package cn.gtmap.realestate.common.core.service.feign.zipkin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/8/23 13:53
 * @description
 */
public interface InquiryZipKin {

    @GetMapping(value = "/realestate-inquiry/rest/v1.0/zipkin/download")
    List<String> getLogFileInquiry(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);


    @GetMapping(value = "/realestate-accept/rest/v1.0/zipkin/download")
    List<String> getLogFileAccept(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/building/rest/v1.0/zipkin/download")
    List<String> getLogFileBuilding(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-certificate/rest/v1.0/zipkin/download")
    List<String> getLogFileCertificate(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-check/rest/v1.0/zipkin/download")
    List<String> getLogFileCheck(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-config/rest/v1.0/zipkin/download")
    List<String> getLogFileConfig(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-e-certificate/rest/v1.0/zipkin/download")
    List<String> getLogFileEcertificate(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-engine/rest/v1.0/zipkin/download")
    List<String> getLogFileEngine(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-etl/rest/v1.0/zipkin/download")
    List<String> getLogFileEtl(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-exchange/rest/v1.0/zipkin/download")
    List<String> getLogFileExchange(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-init/rest/v1.0/zipkin/download")
    List<String> getLogFileInit(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-public/rest/v1.0/zipkin/download")
    List<String> getLogFilePublic(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/rest/v1.0/zipkin/download")
    List<String> getLogFileRegisterUi(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-register/rest/v1.0/zipkin/download")
    List<String> getLogFileregister(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);

    @GetMapping(value = "/realestate-rule/rest/v1.0/zipkin/download")
    List<String> getLogFileRule(@RequestParam(name = "app") String app, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "start") String start, @RequestParam(name = "end") String end);
}
