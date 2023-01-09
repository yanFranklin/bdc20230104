package cn.gtmap.realestate.exchange.service.impl.national.upload;

import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.domain.exchange.uniformity.MessageModelBdc;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther xutao
 * @Date 2018-12-20
 * @Description
 */
@Service
@ConfigurationProperties(prefix = "province-access-ftp")
public class ProvinceAccessUploadFtpImpl extends AbstractProvinceAccessUpload implements NationalAccessUpload {
    @Autowired
    private AccessLogTypeService accessLogTypeService;
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProvinceAccessUploadFtpImpl.class);

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
                messageModel, xsdpath, path, respath, new ProvinceAccess());
    }

    /**
     * 一致性结构上报
     *
     * @param messageModel
     * @return 汇交结果  true 成功  false失败  null未配置
     */
    @Override
    public Boolean upload(MessageModelBdc messageModel) {
        return uploadFtpYzx(ip, username, password, port, downResponseRetryTimes,
                messageModel, xsdpath, path, respath, new ProvinceAccess());
    }

    @Override
    public Boolean checkData(MessageModel messageModel) {
        return checkMessageModel(messageModel, xsdpath);
    }

    /**
     * @param bizMsgIds 用逗号隔开的多个bizMsgId
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 用于省级接入台账手动获取响应报文
     */
    @Override
    public void getReponse(String bizMsgIds) {
        getFtpReponse(bizMsgIds, ip, username, password, port, respath, new ProvinceAccess());
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 用于检测ftp或sftp服务器连接状态
     */
    @Override
    public void checkStatus() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = FtpUtil.getFTPClient(ip, username, password, Integer.parseInt(port));
            //FTP服务器连接回答
            int reply = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                //连接成功
                LOGGER.info("FTP连接成功，reply：{}", reply);

            } else {
                //连接失败，发送短信提醒相关人员
                ftpClient.disconnect();
                LOGGER.info("FTP连接失败，reply：{}，日期：{}", reply, new Date());
                MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
                accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);

            }

        } catch (Exception e) {
            LOGGER.error("FTPerrorMsg:{}", e);
            //连接失败，发送短信提醒相关人员
            LOGGER.info("FTP连接失败，日期：{}", new Date());
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            LOGGER.info("短信通知完成！");
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

    public void setDownResponseRetryTimes(String downResponseRetryTimes) {
        this.downResponseRetryTimes = downResponseRetryTimes;
    }
}
