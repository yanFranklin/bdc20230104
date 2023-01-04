package cn.gtmap.realestate.engine.util;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/11
 * @description 文件资源处理
 */
@Component
public class ResourceUtil {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtil.class);
    /**
     * 版本路径
     */
    @Value("${html.version:hefei}")
    private String filePath;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param filePath  文件路径
     * @return {BdcGzZhgzTsxxDTO} 提示信息
     * @description 组合规则验证，获取对应提示信息
     */
    public String getFileContent(String filePath){
        if(StringUtils.isBlank(filePath)){
            return null;
        }

        ClassPathResource classPathResource = new ClassPathResource(filePath);
        if(null == classPathResource){
            return null;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))){
            StringBuilder builder = new StringBuilder();
            String str = null;
            while (null != (str = bufferedReader.readLine())){
                builder.append(str);
            }
            return builder.toString();
        } catch (Exception e) {
            LOGGER.error("规则子系统，未获取规则文件{}！原因：{}", filePath, e.getMessage());
        }

        return null;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取强制验证文件内容
     */
    public String getQzyzFileContent(){
        if(StringUtils.isBlank(filePath)){
            return null;
        }
        return this.getFileContent("rulefiles/" + filePath + "/" + CommonConstantUtils.GZ_QZYZ_FILE_NAME + ".txt");
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取基础固化的子规则内容
     */
    public String getBaseZgzContent(){
        // 文件地址、名称是固定的
        return this.getFileContent("rulefiles/zgz/bdczgz.txt");
    }
}
