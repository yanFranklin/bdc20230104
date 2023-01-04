package cn.gtmap.realestate.exchange.service.impl.national.upload;

import cn.gtmap.realestate.exchange.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.exchange.core.national.NationalAccess;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @Auther xutao
 * @Date 2018-12-20
 * @Description
 */
@Service
@ConfigurationProperties(prefix = "nationalAccessFtp")
public class NationalAccessUploadFtpImpl extends AbstractNationalAccessUpload implements NationalAccessUpload  {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessUploadFtpImpl.class);


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
     * 上传路径
     */
    private String path;

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

    @Override
    public Boolean upload(MessageModel messageModel) {
        return uploadFtp(ip, username, password, port, downResponseRetryTimes,
                messageModel, xsdpath, path, respath, new NationalAccess());
    }

    @Override
    public Boolean checkData(MessageModel messageModel) {
        return checkMessageModel(messageModel, xsdpath);
    }

    /**
     * @param bizMsgIds 用逗号隔开的多个bizMsgId
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 用于国家接入台账手动获取响应报文
     */
    @Override
    public void getReponse(String bizMsgIds) {
        getFtpReponse(bizMsgIds,ip,username,password,port,respath,new NationalAccess());
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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