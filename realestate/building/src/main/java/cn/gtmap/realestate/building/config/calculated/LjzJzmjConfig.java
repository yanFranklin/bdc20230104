package cn.gtmap.realestate.building.config.calculated;

import cn.gtmap.realestate.building.core.bo.LjzJzmjConfigBO;
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
public class LjzJzmjConfig {
    private static volatile LjzJzmjConfigBO ljzJzmjConfigBO;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LjzJzmjConfig.class);

    /**
     * 配置地址
     */
    private static final String LJZJZMJ_CONFIG_PATH = "/conf/calculated/ljzjzmj.xml";

    /**
     * 私有常量
     */
    private static final String ERROR_MSG = "初始化逻辑幢建筑面积计算配置失败";

    public static void initLjzLjzmConfig(){
        if(ljzJzmjConfigBO == null){
            ClassPathResource res = new ClassPathResource(LJZJZMJ_CONFIG_PATH);
            XStream xStream = new XStream();
            xStream.alias("ljzjzmj", LjzJzmjConfigBO.class);
            try {
                Reader reader = new InputStreamReader(res.getInputStream(), "UTF-8");
                xStream.autodetectAnnotations(true);
                Object object = xStream.fromXML(reader);
                if (object instanceof LjzJzmjConfigBO) {
                    ljzJzmjConfigBO = (LjzJzmjConfigBO) object;
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

    public static LjzJzmjConfigBO getLjzJzmjConfigBO() {
        return ljzJzmjConfigBO;
    }

    public static void setLjzJzmjConfigBO(LjzJzmjConfigBO ljzJzmjConfigBO) {
        LjzJzmjConfig.ljzJzmjConfigBO = ljzJzmjConfigBO;
    }
}