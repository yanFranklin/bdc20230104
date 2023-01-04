package cn.gtmap.realestate.building.config.calculated;

import cn.gtmap.realestate.building.core.bo.FttdMjConfigBO;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/1/16
 * @description
 */
public class FttdmjConfig {
    private static volatile FttdMjConfigBO fttdMjConfigBO;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FttdMjConfigBO.class);

    /**
     * 配置地址
     */
    private static final String FTTDMJ_CONFIG_PATH = "/conf/calculated/fttdmj.xml";

    /**
     * 私有常量
     */
    private static final String ERROR_MSG = "初始化分摊土地面积计算配置失败";


    public static void initFttdmjConfig(){
        if(fttdMjConfigBO == null){
            ClassPathResource res = new ClassPathResource(FTTDMJ_CONFIG_PATH);
            XStream xStream = new XStream();
            xStream.alias("fttdmj", FttdMjConfigBO.class);
            try {
                Reader reader = new InputStreamReader(res.getInputStream(), "UTF-8");
                xStream.autodetectAnnotations(true);
                Object object = xStream.fromXML(reader);
                if (object instanceof FttdMjConfigBO) {
                    fttdMjConfigBO = (FttdMjConfigBO) object;
                }
                reader.close();
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(ERROR_MSG + ":UnsupportedEncodingException", e);
            } catch (FileNotFoundException e) {
                LOGGER.error(ERROR_MSG + ":FileNotFoundException", e);
            } catch (IOException e) {
                LOGGER.error(ERROR_MSG + ":IOException", e);
            }
        }
    }

    public static FttdMjConfigBO getFttdMjConfigBO() {
        return fttdMjConfigBO;
    }

    public static void setFttdMjConfigBO(FttdMjConfigBO fttdMjConfigBO) {
        FttdmjConfig.fttdMjConfigBO = fttdMjConfigBO;
    }
}