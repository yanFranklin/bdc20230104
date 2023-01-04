package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.exchange.core.support.spring.Container;
import cn.gtmap.realestate.exchange.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.exchange.service.impl.national.upload.NationalAccessUploadFtpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.NationalAccessUploadSftpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.ProvinceAccessUploadFtpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.ProvinceAccessUploadSftpImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UploadServiceUtil {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return
     * @description 获取上传服务列表
     */
    public static List<NationalAccessUpload> listNationalAccessUpload(){
        List<NationalAccessUpload> uploadList=new ArrayList<>();
        boolean nationalSwtich=Boolean.valueOf(EnvironmentConfig.getEnvironment().getProperty("nationalAccess.switch"));
        boolean provinceSwtich=Boolean.valueOf(EnvironmentConfig.getEnvironment().getProperty("provinceAccess.switch"));
        if(nationalSwtich){
            String nationalType=EnvironmentConfig.getEnvironment().getProperty("nationalAccess.type");
            if(StringUtils.equals(nationalType,"ftp")){
                uploadList.add(Container.getBean(NationalAccessUploadFtpImpl.class));
            }else if(StringUtils.equals(nationalType,"sftp")){
                uploadList.add(Container.getBean(NationalAccessUploadSftpImpl.class));
            }
        }
        if(provinceSwtich){
            String provinceType=EnvironmentConfig.getEnvironment().getProperty("provinceAccess.type");
            if(StringUtils.equals(provinceType,"ftp")){
                uploadList.add(Container.getBean(ProvinceAccessUploadFtpImpl.class));
            }else if(StringUtils.equals(provinceType,"sftp")){
                uploadList.add(Container.getBean(ProvinceAccessUploadSftpImpl.class));
            }
        }
        return uploadList;
    }
}
