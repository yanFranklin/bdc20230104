package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.config.ftp.FtpConfig;
import cn.gtmap.realestate.building.config.ftp.ZdtFtpConfig;
import cn.gtmap.realestate.building.config.ftp.ZdtFtpMapConfig;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.FtpUtil;
import cn.gtmap.realestate.building.util.TzmUtils;
import cn.gtmap.realestate.common.core.domain.building.SSjZdtDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-21
 * @description
 */
@Service
public class ReadZdtHefeiFtpServiceImpl extends ReadZdtAbstractServiceImpl {

    private static Logger LOGGER = LoggerFactory.getLogger(ReadZdtHefeiFtpServiceImpl.class);

    @Autowired
    private Set<FtpConfig> ftpConfigSet;

    /**
     * 默认ftp配置
     */
    @Autowired
    ZdtFtpConfig zdtFtpConfig;

    @Value("${sysversion:hefei}")
    private String version;

    @Autowired
    ZdtFtpMapConfig zdtFtpMapConfig;

    /**
     * 常州宗地图存储在FTP,库中文件名称前缀和FTP可能不一致，这里设定可能情况（例如：宗地图,TDSYQ,ZDT）进行遍历
     */
    @Value("${zdtwjmcqz:}")
    private String zdtwjmcqz;

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH 读取base 64位码
     */
    @Override
    public String readBase64ByDjh(String djh) {
        return getZdtByDjhByFTP(djh,"");
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 读取base 64位码
     */
    @Override
    public String readBase64ByBdcdyh(String bdcdyh,String qjgldm) {
        return getZdtByDjhByFTP(bdcdyh,qjgldm);
    }

    /**
     * @param param 可能是djh 也可能是 bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据DJH查询宗地图(FTP请求方式)
     */
    private String getZdtByDjhByFTP(String param,String qjgldm) {
        String ftpPath = "";
        String djh = "";
        String baseCode = "";
        String picName = "";
        FtpConfig ftpConfig;
        //常州-金坛等地区有可能需要走额外的ftp配置
        ftpConfig = getBean(ftpConfigSet, qjgldm,zdtFtpMapConfig.getZdtFtpMap());
        if(Objects.isNull(ftpConfig)){
            //没有配置该区县的特定的ftp配置，则走默认的ftp配置
            ftpConfig = zdtFtpConfig;
        }
       // LOGGER.info("合肥读取宗地图，最终使用FTP配置{},{}", qjgldm,JSON.toJSONString(ftpConfig));
        if (Constants.SYSVERSION_STANDARD.equals(version)) {
            return getZdtByBdcdyhByFTP(param);
        } else {
            if (param.length() == 28) {
                djh = param.substring(0, 19);
                // 增加处理逻辑  如果 是土地的BDCDYH 则 使用path 对应路径 如果是 F 则使用pathF
                if (TzmUtils.isTdBdcdy(param)) {
                    ftpPath = ftpConfig.getPath();
                    picName = ZDT_PICNAME_PRE + djh + ZDT_PIC_NAME_SUF;
                } else {
                    // F 的 BDCDYH 读取幢号 拼接给文件名称
                    String zrzh = param.substring(20, 24);
                    int zrzhInt = Integer.parseInt(zrzh);
                    ftpPath = ftpConfig.getPathF();
                    picName = ZDT_PICNAME_PRE + djh + "(" + zrzhInt + ")" + ZDT_PIC_NAME_SUF;
                }
            } else if (param.length() == 19) {
                djh = param;
                ftpPath = ftpConfig.getPath();
                picName = ZDT_PICNAME_PRE + djh + ZDT_PIC_NAME_SUF;
            }
            if (StringUtils.isNotBlank(ftpPath) && StringUtils.isNotBlank(djh)) {
                FTPClient ftpClient = null;
                InputStream in = null;
                try {
                    ftpClient = FtpUtil.getFtpClient(ftpConfig);
                    LOGGER.info("合肥读取宗地图，param:{},ftpPath:{},picName:{}", param, ftpPath, picName);
                    in = FtpUtil.downloadFtpFile(ftpClient, ftpPath, picName);
                    if (in != null) {
                        baseCode = BuildingUtils.getBase64FromInputStream(in);
                    }
                } catch (IOException e) {
                    LOGGER.error("根据DJH查询宗地图", e);
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
                        }
                    }
                }

                // 如果  第一次查询 为空且 为F 查询  则 再使用DJH查询
                if (StringUtils.isBlank(baseCode)
                        && param.length() == 28
                        && !TzmUtils.isTdBdcdy(param)) {
                    String secDjh = BuildingUtils.getDjhByBdcdyh(param);
                    return getZdtByDjhByFTP(secDjh,qjgldm);
                }
            }
            return baseCode;
        }


    }

    /**
     * 3 7209 【常州】登记3.0新增读取宗地图接口
     * 地址、zdt/s_sj_zdt拼接
     *
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2021/2/22
     * @description
     */
    public String getZdtByBdcdyhByFTP(String param) {
        String ftpPath = "";
        String djh = "";
        String baseCode = "";
        String picName = "";
        if (param.length() == 28) {
            djh = param.substring(0, 19);
            //根据不动产单元号获取s_sj_zdt信息
            SSjZdtDO sSjZdtDO = super.queryZdtByDjh(djh);
            if (null != sSjZdtDO) {
                ftpPath = sSjZdtDO.getUploadpath();
                //开始拼接文件地址
                picName = sSjZdtDO.getWjmc() + ZDT_PIC_NAME_SUF;
            }

        }
        if (StringUtils.isNotBlank(ftpPath) && StringUtils.isNotBlank(djh)) {
            FTPClient ftpClient = null;
            InputStream in = null;
            try {
                ftpClient = FtpUtil.getFtpClient(zdtFtpConfig);
                LOGGER.info("读取宗地图，param:{},ftpPath:{},picName:{}", param, ftpPath, picName);
                in = FtpUtil.downloadFtpFile(ftpClient, ftpPath, picName);

                // 默认文件名称查询FTP为空，则遍历可能文件名称形式
                if(null == in && StringUtils.isNotBlank(zdtwjmcqz)) {
                    for(String type : zdtwjmcqz.split(CommonConstantUtils.ZF_YW_DH)) {
                        if(null == in) {
                            picName = type + djh + ZDT_PIC_NAME_SUF;
                            in = FtpUtil.downloadFtpFile(ftpClient, ftpPath, picName);
                            LOGGER.info("常州读取宗地图，param:{},ftpPath:{},picName:{},内容为空否：{}", param, ftpPath, picName, null == in);
                        }
                    }
                }

                if (in != null) {
                    baseCode = BuildingUtils.getBase64FromInputStream(in);
                }
            } catch (IOException e) {
                LOGGER.error("根据bdcdyh查询宗地图", e);
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
