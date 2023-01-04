package cn.gtmap.realestate.building.config.manage;

import cn.gtmap.realestate.building.core.bo.ZlsxConfigBO;
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
public class ZlsxConfig {

    private static volatile ZlsxConfigBO zlsxConfigBO;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZlsxConfig.class);

    /**
     * 配置地址
     */
    private static final String ZLSX_CONFIG_PATH = "/conf/manage/zlsx.xml";

    /**
     * 私有常量
     */
    private static final String ERROR_MSG = "初始化坐落刷新配置失败";


    public static void initZlsxConfig(){
        if(zlsxConfigBO == null){
            ClassPathResource res = new ClassPathResource(ZLSX_CONFIG_PATH);
            XStream xStream = new XStream();
            xStream.alias("zlsx", ZlsxConfigBO.class);
            try {
                Reader reader = new InputStreamReader(res.getInputStream(), "UTF-8");
                xStream.autodetectAnnotations(true);
                Object object = xStream.fromXML(reader);
                if (object instanceof ZlsxConfigBO) {
                    zlsxConfigBO = (ZlsxConfigBO) object;
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

    public static ZlsxConfigBO getZlsxConfigBO() {
        return zlsxConfigBO;
    }

    public static void setZlsxConfigBO(ZlsxConfigBO zlsxConfigBO) {
        ZlsxConfig.zlsxConfigBO = zlsxConfigBO;
    }
}
