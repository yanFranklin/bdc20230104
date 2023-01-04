package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.config.ftp.ZdtFtpConfig;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.FtpUtil;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static cn.gtmap.realestate.building.service.impl.ReadZdtAbstractServiceImpl.ZDT_PIC_NAME_SUF;

/**
 * @program: realestate
 * @description: 从ftp读取户室图信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-19 19:23
 **/
@Service
public class ReadHstFromFtpServiceImpl extends ReadHstAbstractServiceImpl {

    private static Logger LOGGER = LoggerFactory.getLogger(ReadHstFromFtpServiceImpl.class);

    /**
     * 默认ftp配置
     */
    @Autowired
    ZdtFtpConfig zdtFtpConfig;


    /**
     * @param fwHsIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据fwHsIndex 读取base 64位码
     */
    @Override
    public String readBase64ByFwHsIndex(String fwHsIndex) {
        FwHstDO fwHstDO = fwHstMapper.queryFwHstByFwHsIndex(fwHsIndex);
        if (Objects.nonNull(fwHstDO)) {
            return getHstByFTP(fwHstDO.getFwHstIndex());
        }
        return "";
    }

    /**
     * @param fwHstIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FwHstIndex 读取 base 64码
     */
    @Override
    public String readBase64ByFwHstIndex(String fwHstIndex) {
        return getHstByFTP(fwHstIndex);
    }

    public String getHstByFTP(String fwhstIndex) {
        String ftpPath = "";
        String baseCode = "";
        String picName = "";
        if (StringUtils.isNotBlank(fwhstIndex)) {
            //根据不动产单元号获取s_sj_zdt信息
            FwHstDO fwHstDO = fwHstMapper.queryFwHstByIndex(fwhstIndex);
            if (null != fwHstDO) {
                ftpPath = fwHstDO.getFwzdtpath();
                //开始拼接文件地址
                picName = fwHstDO.getFwzdtmc() + ZDT_PIC_NAME_SUF;
            }
        }
        if (StringUtils.isNotBlank(ftpPath)) {
            FTPClient ftpClient = null;
            InputStream in = null;
            try {
                ftpClient = FtpUtil.getFtpClient(zdtFtpConfig);
                LOGGER.info("读取户室图，fwhstindex:{},ftpPath:{},picName:{}", fwhstIndex, ftpPath, picName);
                in = FtpUtil.downloadFtpFile(ftpClient, ftpPath, picName);
                if (in != null) {
                    baseCode = BuildingUtils.getBase64FromInputStream(in);
                }
            } catch (IOException e) {
                LOGGER.error("根据bdcdyh查询户室图", e);
                throw new AppException("ftp连接查询异常！");
            } finally {
                if (ftpClient != null) {
                    try {
                        ftpClient.disconnect();
                    } catch (IOException e) {
                        LOGGER.error("关闭FTP链接异常", e);
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        LOGGER.error("关闭流异常", e);
                        throw new AppException("关闭流异常！");
                    }
                }
            }

        }
        return baseCode;
    }
}
