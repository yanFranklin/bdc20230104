package cn.gtmap.realestate.common.core.enums;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/11/11
 * @Description:
 */
public enum  BdcServiceEnum {

    ACCEPT_UI("ACCEPT-UI","realestate-accept-ui"),
    BUILDING_UI("BUILDING-UI","realestate-building-ui"),
    INQUIRY_UI("INQUIRY-UI","realestate-inquiry-ui"),
    PORTAL_UI("PORTAL-UI","realestate-portal-ui"),
    REGISTER_UI("REGISTER-UI-APP","realestate-register-ui"),

    ACCEPT_APP("ACCEPT-APP","realestate-accept"),
    EXCHANGE_APP("EXCHANGE-APP","realestate-exchange"),
    BUILDING_APP("BUILDING-APP","realestate-building"),
    CERTIFICATE_APP("CERTIFICATE-APP","realestate-certificate"),
    E_CERTIFICATE_APP("E-CERTIFICATE-APP","realestate-e-certificate"),
    CONFIG_APP("REALESTATE-CONFIG","realestate-config"),
    ENGINE_APP("ENGINE-APP","realestate-engine"),
    INIT_APP("INIT-APP","realestate-init"),
    INQUIRY_APP("INQUIRY-APP","realestate-inquiry"),
    REGISTER_APP("REGISTER-APP","realestate-register"),
    ETL_APP("ETL-APP","realestate-etl"),
    CHECK_APP("CHECK-APP","realestate-check");




    private final String serviceId;
    private final String service;

    BdcServiceEnum(String serviceId,String service) {
        this.serviceId = serviceId;
        this.service = service;
    }
    public String service(){
        return service;
    }

    public String serviceId(){
        return serviceId;
    }

    public static String getServiceById(String serviceId){
        for (BdcServiceEnum serviceEnum : BdcServiceEnum.values()) {
            if (serviceEnum.serviceId().equals(serviceId)) {
                return serviceEnum.service;
            }
        }
        return null;
    }
}
