package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.exchange.service.impl.national.upload.*;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UploadServiceUtil {

    /**
     * 类型：国家级上报
     */
    public static final String NATIONAL_ACCESS = "nationalAccess";
    /**
     * 类型：省级上报
     */
    public static final String PROVINCE_ACCESS = "provinceAccess";
    /**
     * 类型：市级上报
     */
    public static final String CITY_ACCESS = "cityAccess";

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
        boolean citySwtich = Boolean.valueOf(EnvironmentConfig.getEnvironment().getProperty("cityAccess.switch"));
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
        // 市级上报
        if(citySwtich){
            String cityType=EnvironmentConfig.getEnvironment().getProperty("cityAccess.type");
            if(StringUtils.equals(cityType,"ftp")){
                uploadList.add(Container.getBean(CityAccessUploadFtpImpl.class));
            }else if(StringUtils.equals(cityType,"sftp")){
                uploadList.add(Container.getBean(CityAccessUploadSftpImpl.class));
            }
        }
        return uploadList;
    }

    /**
     *
     * @param type  nationalAccess（国家上报）  provinceAccess（省级上报） cityAccess（市级上报）
     * @return
     */
    public static List<NationalAccessUpload> listNationalAccessUploadByType(String type){
        List<NationalAccessUpload> uploadList=new ArrayList<>();
        if(StringUtils.isNotBlank(type)){
            switch(type){
                case NATIONAL_ACCESS:
                    String nationalType=EnvironmentConfig.getEnvironment().getProperty("nationalAccess.type");
                    if(StringUtils.equals(nationalType,"ftp")){
                        uploadList.add(Container.getBean(NationalAccessUploadFtpImpl.class));
                    }else if(StringUtils.equals(nationalType,"sftp")){
                        uploadList.add(Container.getBean(NationalAccessUploadSftpImpl.class));
                    }
                    break;
                case CITY_ACCESS:
                    String cityType=EnvironmentConfig.getEnvironment().getProperty("cityAccess.type");
                    if(StringUtils.equals(cityType,"ftp")){
                        uploadList.add(Container.getBean(CityAccessUploadFtpImpl.class));
                    }else if(StringUtils.equals(cityType,"sftp")){
                        uploadList.add(Container.getBean(CityAccessUploadSftpImpl.class));
                    }
                    break;
                case PROVINCE_ACCESS:
                default:
                    String provinceType=EnvironmentConfig.getEnvironment().getProperty("provinceAccess.type");
                    if(StringUtils.equals(provinceType,"ftp")){
                        uploadList.add(Container.getBean(ProvinceAccessUploadFtpImpl.class));
                    }else if(StringUtils.equals(provinceType,"sftp")){
                        uploadList.add(Container.getBean(ProvinceAccessUploadSftpImpl.class));
                    }
            }
        }
        return uploadList;
    }
}
