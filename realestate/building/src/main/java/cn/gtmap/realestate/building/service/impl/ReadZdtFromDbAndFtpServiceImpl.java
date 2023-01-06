package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.config.ftp.ZdtFtpConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: realestate
 * @description: 从数据库和ftp读取宗地图数据
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-19 16:52
 **/
@Service
public class ReadZdtFromDbAndFtpServiceImpl extends ReadZdtAbstractServiceImpl {


    private static Logger LOGGER = LoggerFactory.getLogger(ReadZdtFromDbAndFtpServiceImpl.class);

    @Resource(name = "readZdtFromDbBase64ServiceImpl")
    private ReadZdtFromDbBase64ServiceImpl readZdtFromDbBase64ServiceImpl;

    @Resource(name = "readZdtHefeiFtpServiceImpl")
    private ReadZdtHefeiFtpServiceImpl readZdtHefeiFtpServiceImpl;


    /**
     * 默认ftp配置
     */
    @Autowired
    ZdtFtpConfig zdtFtpConfig;

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH 读取base 64位码
     */
    @Override
    public String readBase64ByDjh(String djh) {
        return null;
    }

    /**
     * @param bdcdyh
     * @param qjgldm
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 读取base 64位码
     */
    @Override
    public String readBase64ByBdcdyh(String bdcdyh, String qjgldm) {
        //1. 先查询数据库表是否有数据
        String base64 = readZdtFromDbBase64ServiceImpl.readBase64ByBdcdyh(bdcdyh, qjgldm);
        if (StringUtils.isNotBlank(base64)) {
            return base64;
        }
        //2.没有数据查ftp
        return readZdtHefeiFtpServiceImpl.getZdtByBdcdyhByFTP(bdcdyh,qjgldm);

    }
}
