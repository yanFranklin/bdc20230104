package cn.gtmap.realestate.etl.service;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/15
 * @description etl 打印服务
 */
public interface EtlPrintService {

    /**
      * @param configParam 打印参数
      * @param dylx 打印类型
      * @return xml数据
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取打印xml
      */
    String generateXmlData(Map configParam, String dylx,String configBeanName);
}
