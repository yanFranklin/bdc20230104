package cn.gtmap.realestate.exchange.service.impl.national.access;

import cn.gtmap.realestate.common.core.domain.exchange.BdcJrXxtxDO;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessLogStausEnum;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.core.enums.BdcJrTxfsEnum;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.config.ConfigInit;
import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogNationalHandlerService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogProvinceHandlerServise;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.MessageUtils;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登簿日志服务
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0
 * @description
 */

@Service
public class AccessLogTypeServiceImpl implements AccessLogTypeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    XmlEntityConvertUtil xmlEntityConvertUtil;
    @Autowired
    ConfigInit configInit;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    MessageUtils messageUtils;

    /**
     * 异常信息提醒配置
     */
    private Map<String, BdcJrXxtxDO> bdcJrXxtxDOMap = new HashMap<>();

    @PostConstruct
    private void init() {
        Example example = new Example(BdcJrXxtxDO.class);
        List<BdcJrXxtxDO> bdcJrXxtxDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(bdcJrXxtxDOList)) {
            logger.error("未配置上报异常消息提醒模板");
        } else {
            for(BdcJrXxtxDO bdcJrXxtxDO : bdcJrXxtxDOList) {
                bdcJrXxtxDOMap.put(String.valueOf(bdcJrXxtxDO.getYjlx()), bdcJrXxtxDO);
            }
        }
    }

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
        if (StringUtils.equals(AccessLogStausEnum.STATUS_0.getCode(), result)) {
            logger.info("升级后省级接入日志成功");
            bdcJrDbrzjlDO.setSjcgbs(CommonConstantUtils.SF_S_DM);
            bdcJrDbrzjlDO.setSjxyxx("上报成功");
        } else {
            bdcJrDbrzjlDO.setSjxyxx(result);
            logger.info("升级后省级接入日志失败，失败原因：{}", result);
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

    /**
     * 上报异常提醒
     * @param msgNoticeDTO 通知类型
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public void sendMsgByMsgType(MsgNoticeDTO msgNoticeDTO) {
        if(null == msgNoticeDTO || StringUtils.isBlank(msgNoticeDTO.getYjlx())) {
            logger.error("上报异常未定义预警类型，无法发送提醒：{}", msgNoticeDTO);
            return;
        }

        BdcJrXxtxDO bdcJrXxtxDO = (BdcJrXxtxDO) MapUtils.getObject(bdcJrXxtxDOMap, msgNoticeDTO.getYjlx());
        if(null == bdcJrXxtxDO) {
            bdcJrXxtxDO = new BdcJrXxtxDO();
        }

        if(null == bdcJrXxtxDO.getTxfs()) {
            // 没配置提醒方式，则默认系统消息和短信
            bdcJrXxtxDO.setTxfs(BdcJrTxfsEnum.XXTXDX.getTxfs());
        }

        if(StringUtils.isBlank(bdcJrXxtxDO.getTxjs())) {
            // 没配置提醒角色，则默认管理员
            bdcJrXxtxDO.setTxjs("admin");
        }

        // 提示信息内容
        String tsxx = StringUtils.isBlank(bdcJrXxtxDO.getXxmb()) ? "" : bdcJrXxtxDO.getXxmb();
        if(StringUtils.isBlank(tsxx)) {
            if(StringUtils.isNotBlank(msgNoticeDTO.getSlbh())) {
                tsxx += "受理编号为" + msgNoticeDTO.getSlbh() + "，";
            }
            if(StringUtils.isNotBlank(msgNoticeDTO.getYwlx())) {
                tsxx += "业务类型为" + msgNoticeDTO.getYwlx() + "，";
            }
            tsxx += "上报异常，具体原因：" + AccessWarningEnum.getMcByYjlx(msgNoticeDTO.getYjlx());
        } else {
            if (StringUtils.isNotBlank(msgNoticeDTO.getSlbh())) {
                tsxx = tsxx.replaceAll("#\\{slbh\\}", msgNoticeDTO.getSlbh());
            }
            if (StringUtils.isNotBlank(msgNoticeDTO.getYwlx())) {
                tsxx = tsxx.replaceAll("#\\{ywlx\\}", msgNoticeDTO.getYwlx());
            }
            if (StringUtils.isNotBlank(msgNoticeDTO.getSbsj())) {
                tsxx = tsxx.replaceAll("#\\{sbsj\\}", msgNoticeDTO.getSbsj());
            }
        }

        // 判断提醒方式
        if(BdcJrTxfsEnum.XTXX.getTxfs().equals(bdcJrXxtxDO.getTxfs())) {
            // 系统提醒
            messageUtils.sendMessagesByRole(Arrays.asList(bdcJrXxtxDO.getTxjs().split(CommonConstantUtils.ZF_YW_DH)), tsxx, "EXCHANGE_JRHJ");
        }
        else if(BdcJrTxfsEnum.DX.getTxfs().equals(bdcJrXxtxDO.getTxfs())) {
            // 短信 TODO
        }
        else if(BdcJrTxfsEnum.XXTXDX.getTxfs().equals(bdcJrXxtxDO.getTxfs())) {
            // 系统提醒和短信
            messageUtils.sendMessagesByRole(Arrays.asList(bdcJrXxtxDO.getTxjs().split(CommonConstantUtils.ZF_YW_DH)), tsxx, "EXCHANGE_JRHJ");
            // 短信 TODO
        }
        else {
            // 无需提醒
            logger.info("上报当前消息无需提醒：{}", msgNoticeDTO);
        }
    }
}
