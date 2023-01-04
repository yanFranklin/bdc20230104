package cn.gtmap.realestate.common.core.support.staticfile;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/7/30 9:41
 * @description 各项目启动完成后，清空外部静态资源目录
 */
@Component
public class CleanStaticFileDir implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(CleanStaticFileDir.class);

    @Value("${staticfile.path:}")
    private String staticFilePath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("开始清空外部静态资源目录");
        if (StringUtils.isBlank(staticFilePath)) {
            logger.warn("外部静态资源路径为空！");
            return;
        }
        File file = new File(staticFilePath);
        deleteDirectory(file);
        logger.info("外部静态资源目录清空完成");
    }

    /**
     * @return void
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [file]
     * @description 递归删除该目录下所有目录和文件
     */
    private void deleteDirectory(File file) {
        if (!file.exists()) {
            logger.error("外部静态资源路径不存在！");
            throw new AppException("外部静态资源路径访问异常,请手动删除外部静态资源！");
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    f.delete();
                } else {
                    deleteDirectory(f);
                    f.delete();
                }
            }
        }
    }
}
