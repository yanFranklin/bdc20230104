package cn.gtmap.realestate.exchange.service.impl.national.upload;


import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.domain.exchange.uniformity.MessageModelBdc;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.exchange.core.national.CityAccess;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @Date 2022-05-17
 * @Description 市级上报SFTP 实现类
 */
@Service
@ConfigurationProperties(prefix = "city-access-sftp")
public class CityAccessUploadSftpImpl extends AbstractCityAccessUpload implements NationalAccessUpload {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CityAccessUploadSftpImpl.class);

    /**
     * ip
     */
    private String ip;

    /**
     * port
     */
    private String port;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * 重新获取连接次数
     */
    private Integer connenttimes;

    /**
     * 上传路径
     */
    private String path;

    /**
     * 是否开启重写功能
     */
    private Boolean reEditFileEnable;

    /**
     * XSD 校验文件路径
     */
    private String xsdpath;

    /**
     * 获取响应报文路径
     */
    private String respath;

    /**
     * 下载响应报文的次数
     */
    private String downResponseRetryTimes;

    @Autowired
    AccessLogTypeService accessLogTypeService;

    /**
     * 将 xml 上传前置机
     *
     * @param messageModel
     */
    @Override
    public Boolean upload(MessageModel messageModel) {
        return uploadSftp(port, username, ip, password, downResponseRetryTimes, reEditFileEnable, connenttimes,
                messageModel, xsdpath, path, respath, new CityAccess());
    }

    /**
     * 一致性结构上报
     *
     * @param messageModel
     * @return 汇交结果  true 成功  false失败  null未配置
     */
    @Override
    public Boolean upload(MessageModelBdc messageModel) {
        return uploadSftpYzx(port, username, ip, password, downResponseRetryTimes, reEditFileEnable, connenttimes,
                messageModel, xsdpath, path, respath, new CityAccess());
    }

    @Override
    public Boolean checkData(MessageModel messageModel) {
        return checkMessageModel(messageModel, xsdpath);
    }

    /**
     * @param bizMsgIds 用逗号隔开的多个bizMsgId
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 用于国家接入台账手动获取响应报文
     */
    @Override
    public void getReponse(String bizMsgIds) {
        getSftpReponse(bizMsgIds, ip, username, password, port, respath, new CityAccess());
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 用于检测ftp或sftp服务器连接状态
     */
    @Override
    public void checkStatus() {
        Session session = getSession(port, username, ip, password);
        if (ObjectUtils.isEmpty(session)) {
            //sftp连接失败
            LOGGER.info("尝试获取前置机连接失败，日期：{}", new Date());
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);

        } else {
            LOGGER.info("sftp尝试获取前置机连接成功，日期：{}", new Date());

        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getConnenttimes() {
        return connenttimes;
    }

    public void setConnenttimes(Integer connenttimes) {
        this.connenttimes = connenttimes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getReEditFileEnable() {
        return reEditFileEnable;
    }

    public void setReEditFileEnable(Boolean reEditFileEnable) {
        this.reEditFileEnable = reEditFileEnable;
    }

    public String getXsdpath() {
        return xsdpath;
    }

    public void setXsdpath(String xsdpath) {
        this.xsdpath = xsdpath;
    }

    public String getRespath() {
        return respath;
    }

    public void setRespath(String respath) {
        this.respath = respath;
    }

    public String getDownResponseRetryTimes() {
        return downResponseRetryTimes;
    }

    public void setDownResponseRetryTimes(String downResponseRetryTimes) {
        this.downResponseRetryTimes = downResponseRetryTimes;
    }
}
