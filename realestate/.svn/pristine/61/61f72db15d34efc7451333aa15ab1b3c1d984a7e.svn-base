package cn.gtmap.realestate.common.core.service.rest.zipkin;

import cn.gtmap.realestate.common.core.service.feign.zipkin.InquiryZipKin;
import cn.gtmap.realestate.common.util.ReadFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/8/23 13:55
 * @description
 */
@RestController
public class InquiryZipKinImpl implements InquiryZipKin{
    private static final Logger LOGGER = LoggerFactory.getLogger(InquiryZipKinImpl.class);
    @Value("${zipkin.log.path:/home}")
    private String logPath;

    @Override
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/zipkin/download")
    public List<String> getLogFileInquiry(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-accept/rest/v1.0/zipkin/download")
    public List<String> getLogFileAccept(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/building/rest/v1.0/zipkin/download")
    public List<String> getLogFileBuilding(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-certificate/rest/v1.0/zipkin/download")
    public List<String> getLogFileCertificate(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-check/rest/v1.0/zipkin/download")
    public List<String> getLogFileCheck(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-config/rest/v1.0/zipkin/download")
    public List<String> getLogFileConfig(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-e-certificate/rest/v1.0/zipkin/download")
    public List<String> getLogFileEcertificate(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-engine/rest/v1.0/zipkin/download")
    public List<String> getLogFileEngine(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-etl/rest/v1.0/zipkin/download")
    public List<String> getLogFileEtl(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-exchange/rest/v1.0/zipkin/download")
    public List<String> getLogFileExchange(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/init/rest/v1.0/zipkin/download")
    public List<String> getLogFileInit(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/rest/v1.0/zipkin/download")
    public List<String> getLogFileRegisterUi(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-public/rest/v1.0/zipkin/download")
    public List<String> getLogFilePublic(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-register/rest/v1.0/zipkin/download")
    public List<String> getLogFileregister(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    @Override
    @GetMapping(value = "/realestate-rule/rest/v1.0/zipkin/download")
    public List<String> getLogFileRule(String app,String fileName,String start,String end){
        return getLogFile(app,fileName,start,end);
    }

    private List<String> getLogFile(String app,String fileName,String start,String end){
        // home/realestate-inquiry-3.1.0/logs
        LOGGER.info(app + "获取日志的路径:" + logPath + "/realestate-" + app + "-3.1.0/logs/" + fileName);
        File file = new File(logPath + "/realestate-" + app + "-3.1.0/logs/" + fileName);
        return ReadFileUtils.readLogFile(file,app,start,end);
    }

}
