package cn.gtmap.realestate.building.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: realestate
 * @description: 从数据库或者ftp读取户室图数据
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-19 19:17
 **/
@Service
public class ReadHstFromDbAndFtpServiveImpl extends ReadHstAbstractServiceImpl {

    private static Logger LOGGER = LoggerFactory.getLogger(ReadHstFromDbAndFtpServiveImpl.class);

    @Resource(name = "readHstFromDbBase64ServiceImpl")
    private ReadHstFromDbBase64ServiceImpl readHstFromDbBase64ServiceImpl;

    @Resource(name = "readHstFromFtpServiceImpl")
    private ReadHstFromFtpServiceImpl readHstFromFtpServiceImpl;

    /**
     * @param fwHsIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据fwHsIndex 读取base 64位码
     */
    @Override
    public String readBase64ByFwHsIndex(String fwHsIndex) {
        String base64 = readHstFromDbBase64ServiceImpl.readBase64ByFwHsIndex(fwHsIndex);
        if (StringUtils.isNotBlank(base64)) {
            return base64;
        }
        return readHstFromFtpServiceImpl.readBase64ByFwHsIndex(fwHsIndex);
    }

    /**
     * @param fwHstIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FwHstIndex 读取 base 64码
     */
    @Override
    public String readBase64ByFwHstIndex(String fwHstIndex) {
        String base64 = readHstFromDbBase64ServiceImpl.readBase64ByFwHstIndex(fwHstIndex);
        if (StringUtils.isNotBlank(base64)) {
            return base64;
        }
        return readHstFromFtpServiceImpl.readBase64ByFwHstIndex(fwHstIndex);
    }
}
