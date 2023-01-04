package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.config.ConfigInit;
import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.core.support.spring.Container;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogNationalHandlerService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogProvinceHandlerServise;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class AccessLogTypeServiceImpl implements AccessLogTypeService {
    @Autowired
    XmlEntityConvertUtil xmlEntityConvertUtil;
    @Autowired
    ConfigInit configInit;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">liuyu</a>
     * @description ftp上传到服务器并生成本地xml文件
     */
    @Override
    public void generateXmlAndFtp(String xml, String xmlId) {
        if (StringUtils.isBlank(xml) || StringUtils.isBlank(xmlId)) {
            return;
        }
        String uploadFileName = "LogBiz" + xmlId + ".xml";
        //本地xml生成
        String path = configInit.getXmlPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        //将汇交数据写入路径
        FileOutputStream fileOutputStream = null;
        BufferedWriter writer = null;
        try {
            fileOutputStream = new FileOutputStream(path + "/" + uploadFileName);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            writer.append(xml);
            logger.info("本地保存AccessLog成功");
        } catch (FileNotFoundException e) {
            logger.error("errorMsg:", e);
        } catch (IOException e) {
            logger.info("本地保存AccessLog失败");
            logger.error("errorMsg:", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error("errorMsg:", e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    logger.error("errorMsg:", e);
                }
            }
        }
    }


    /**
     * @param xml           xml文件
     * @param bdcJrDbrzjlDO 日志接入表实体类
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 部级webservice日志上报
     **/
    @Override
    public void nationalAccessLogWeb(String xml, BdcJrDbrzjlDO bdcJrDbrzjlDO) {
        String dm = configInit.getNationalType();
        AccessLogNationalHandlerService accessLogNationalHandlerService = getNationalAccessLogServiceByDm(dm);
        if (accessLogNationalHandlerService != null) {
            accessLogNationalHandlerService.nationalAccessLogWeb(xml, bdcJrDbrzjlDO);
        } else {
            logger.info("未配置日志上报地区。。。");
        }
    }

    /**
     * @param xml           xml文件
     * @param bdcJrDbrzjlDO 日志接入表实体类
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 部级webservice日志上报
     **/
    @Override
    public void provinceAccessLogWeb(String xml, BdcJrDbrzjlDO bdcJrDbrzjlDO) {
        String result = null;
        String dm = configInit.getProvinceType();
        AccessLogProvinceHandlerServise accessLogProvinceHandlerServise = getAccessServiceByDm(dm);
        if (accessLogProvinceHandlerServise != null) {
            result = accessLogProvinceHandlerServise.provinceAccessLog(xml);
        } else {
            result = "配置文件错误:不存在配置的日志上报地区特殊服务";
        }
        if (StringUtils.equals("success", result)) {
            logger.info("省级接入日志成功");
            bdcJrDbrzjlDO.setSjcgbs(CommonConstantUtils.SF_S_DM);
            bdcJrDbrzjlDO.setSjxyxx("上报成功");
        } else {
            bdcJrDbrzjlDO.setSjxyxx(result);
            logger.info("省级接入日志失败，失败原因：" + result);
        }
    }

    private AccessLogProvinceHandlerServise getAccessServiceByDm(String dm) {
        AccessLogProvinceHandlerServise accessLogProvinceHandlerServise = null;
        if (StringUtils.isNotBlank(dm)) {
            accessLogProvinceHandlerServise = (AccessLogProvinceHandlerServise) Container.getBean("accessLog_" + dm);
        }
        return accessLogProvinceHandlerServise;
    }

    private AccessLogNationalHandlerService getNationalAccessLogServiceByDm(String dm) {
        AccessLogNationalHandlerService accessLogNationalHandlerService = null;
        if (StringUtils.isNotBlank(dm)) {
            accessLogNationalHandlerService = (AccessLogNationalHandlerService) Container.getBean("nationalAccessLog_" + dm);
        }
        return accessLogNationalHandlerService;
    }

}
