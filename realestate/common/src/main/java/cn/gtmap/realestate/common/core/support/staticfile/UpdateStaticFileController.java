package cn.gtmap.realestate.common.core.support.staticfile;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0 2020/7/17 13:42
 * @description 更新静态文件Controller
 */
@RestController
@RequestMapping("/uploadstaticfile")
public class UpdateStaticFileController {
    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateStaticFileController.class);

    @Value("${staticfile.path:}")
    private String staticFilePath;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * @return java.util.Map
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [file, path]
     * @description 更新静态文件
     */
    @PostMapping(value = "/import")
    public Map updateStaticFile(@NotNull(message = "更新文件不能为空") @RequestParam("file") MultipartFile file,
                                @RequestParam("path") String path) {
        if (StringUtils.isBlank(path)) {
            throw new AppException("上传路径未填写！");
        }

        // 1.读取静态文件内容
        String staticFileContent = readStaticFileContent(file);

        if (StringUtils.isBlank(staticFileContent)) {
            throw new AppException("更新静态文件失败，静态文件内容为空!");
        }

        // 2.在指定目录下创建新的静态文件
        writeStaticFile(path, staticFileContent);

        Map res = new HashMap(1);
        res.put("code", 0);
        return res;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @return java.lang.Object
     * @description 从注册中心获取ui服务列表
     */
    @ResponseBody
    @GetMapping("/getServices")
    public Object getServicesFromEureka() {
        List<String> services = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(services)) {
            LOGGER.error("获取服务列表失败");
            throw new AppException("获取服务列表失败");
        }

        List<String> uiServices = new ArrayList<>();
        for (String service : services) {
            if (StringUtils.isNotBlank(service)) {
                // building-ui暂不支持在线更新静态文件功能
                if (service.contains("building")) {
                    continue;
                }

                if (StringUtils.equals(service, "inquiry-app")) {
                    continue;
                }

                if (service.contains("ui") || service.contains("UI")) {
                    uiServices.add(service);
                }
            }
        }
        return uiServices;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [uiSystemName]
     * @return java.lang.Object
     * @description 根据UI子系统名称获取所有实例
     */
    @ResponseBody
    @GetMapping("/getInstances")
    public Object getInstancesBySystemName(@RequestParam("uiSystemName") String uiSystemName) {
        if (StringUtils.isBlank(uiSystemName)) {
            throw new AppException("UI子系统名称为空！");
        }

        List<String> services = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(services)) {
            LOGGER.error("获取服务列表失败");
            throw new AppException("获取服务列表失败");
        }

        List<ServiceInstance> instanceList = new ArrayList<>();
        for (String service : services) {
            if (StringUtils.equals(service, uiSystemName)) {
                instanceList = discoveryClient.getInstances(service);
            }
        }

        return instanceList;
    }

    /**
     * @return java.lang.String
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [file]
     * @description 读取静态文件内容
     */
    private String readStaticFileContent(MultipartFile file) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\r\n");
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("更新静态文件失败");
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("输入流关闭异常", e);
                }
            }
        }

        return builder.toString();
    }

    /**
     * @return void
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [path, content]
     * @description 写静态文件
     */
    private void writeStaticFile(String path, String content) {
        if (path.length() < 6) {
            throw new AppException("上传路径不符合规范，请重新填写！");
        }
        if (StringUtils.isBlank(staticFilePath)) {
            LOGGER.error("外部静态资源路径为空，请查看配置项");
            throw new AppException("外部静态资源路径为空，请查看配置项");
        }

        BufferedWriter bufferedWriter = null;
        try {
            path = path.substring(6); // 去掉重复的webapp目录
            String wholePath = staticFilePath + path;
            // 判断目录是否存在，不存在则新建目录
            String dirPath = wholePath.substring(0, wholePath.lastIndexOf("/"));
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            // 判断静态文件是否存在
            File staticFile = new File(wholePath);
            if (!staticFile.exists()) {
                staticFile.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(staticFile), "UTF-8"));
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
            LOGGER.info("成功更新静态文件");
        } catch (IOException e) {
            LOGGER.info(e.getMessage(), e);
            throw new AppException("更新静态文件失败");
        } finally {
            if (null != bufferedWriter) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    LOGGER.error("输出流关闭异常", e);
                }
            }
        }
    }
}
