package cn.gtmap.realestate.building.config.manage;

import cn.gtmap.realestate.building.core.bo.HsHbConfigBO;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-10
 * @description
 */
public class HsHbConfig {

    private static volatile HsHbConfigBO hsHbConfigBO;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HsHbConfig.class);

    /**
     * 配置地址
     */
    private static final String ZLSX_CONFIG_PATH = "/conf/manage/hshb.xml";

    /**
     * 私有常量
     */
    private static final String ERROR_MSG = "初始化坐落刷新配置失败";


    public static void initHsHbConfig(){
        if(hsHbConfigBO == null){
            ClassPathResource res = new ClassPathResource(ZLSX_CONFIG_PATH);
            XStream xStream = new XStream();
            xStream.alias("hshb", HsHbConfigBO.class);
            try {
                Reader reader = new InputStreamReader(res.getInputStream(), "UTF-8");
                xStream.autodetectAnnotations(true);
                Object object = xStream.fromXML(reader);
                if (object instanceof HsHbConfigBO) {
                    hsHbConfigBO = (HsHbConfigBO) object;
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

    public static HsHbConfigBO getHsHbConfigBO() {
        return hsHbConfigBO;
    }

    public static void setHsHbConfigBO(HsHbConfigBO hsHbConfigBO) {
        HsHbConfig.hsHbConfigBO = hsHbConfigBO;
    }

}
