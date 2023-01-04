package cn.gtmap.realestate.exchange.service.impl.national.upload;

import cn.gtmap.realestate.exchange.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.exchange.core.national.NationalAccess;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @Auther xutao
 * @Date 2018-12-17
 * @Description
 */
@Service
@ConfigurationProperties(prefix = "nationalAccessSftp")
public class NationalAccessUploadSftpImpl extends AbstractNationalAccessUpload implements NationalAccessUpload {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessUploadSftpImpl.class);

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


    /**
     * 将 xml 上传前置机
     *
     * @param messageModel
     */
    @Override
    public Boolean upload(MessageModel messageModel) {
        return uploadSftp(port, username, ip, password, downResponseRetryTimes, reEditFileEnable, connenttimes, messageModel, xsdpath, path, respath, new ProvinceAccess());
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
        getSftpReponse(bizMsgIds,ip,username,password,port,respath,new NationalAccess());
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
