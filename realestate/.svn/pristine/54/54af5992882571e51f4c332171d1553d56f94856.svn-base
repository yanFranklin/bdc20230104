package cn.gtmap.realestate.certificate.util;

import cn.gtmap.realestate.certificate.config.PropsConfig;
import cn.gtmap.realestate.certificate.core.enums.BdcZzlxEnum;
import cn.gtmap.realestate.certificate.core.mapper.BdcZsMapper;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.*;
import cn.gtmap.realestate.common.core.enums.BdcZslxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.QLLX_DYAQ_DM;
import static cn.gtmap.realestate.common.util.DateUtils.*;

/**
 * 电子证照参数组织工具类 「新版」
 * <p>
 * 主要区别
 * 1. 参数拼接统一使用 ^
 * 2. 增加证明签发的参数：义务人信息和关联产权证号
 * 3. 必填项赋值逻辑修改： 必填为空传 / 非必填则传 "" 具体可以根据注解中的 group 判断
 * </p>
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
@Component
public class ECertificateParamUtils {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ECertificateParamUtils.class.getName());

    /**
     * 电子证照登记和省厅字典对照在第三方字典表“第三方系统标识”字段值
     */
    public static final String BDCDJ_DZZZ = "BDCDJ_DZZZ";

    /**
     * 证书归档服务地址
     */
    @Value("${eCertificate.default.zzbfjgdm:}")
    protected String defaultZzbfjgdm;
    /**
     * 证书归档服务地址
     */
    @Value("${eCertificate.default.zzbfjg:}")
    private String defaultZzbfjg;

    /**
     * 证照颁发机构
     */
    @Value("#{${eCertificate.zzbfjg: null}}")
    private Map<String, String> zzbfjg;
    /**
     * 证照颁发机构代码
     */
    @Value("#{${eCertificate.zzbfjgdm: null}}")
    private Map<String, String> zzbfjgdm;

    /**
     * 证照颁发机构（按照区县代码对照配置）
     */
    @Value("#{${eCertificate.zzbfjg_qxdm: null}}")
    private Map<String, String> zzbfjgQxdm;
    /**
     * 证照颁发机构代码（按照区县代码对照配置）
     */
    @Value("#{${eCertificate.zzbfjgdm_qxdm: null}}")
    private Map<String, String> zzbfjgdmQxdm;

    /**
     * 电子签章单位代码（按照区县代码对照配置）
     */
    @Value("#{${eCertificate.dwdm_qxdm: null}}")
    private Map<String, String> dwdmMap;
    /**
     * 电子证照单位代码（按照区县代码对照配置）
     */
    @Value("#{${eCertificate.dzzzDwdmQxdm: null}}")
    private Map<String, String> dzzzDwdmQxdm;
    /**
     * 单位代码是否要取不动产单元号前6位
     */
    @Value("${eCertificate.dwdm.bdcdyhqlw:false}")
    private Boolean dwdmBdcdyhqlw;

    /**
     * 生成证书是否异步，
     */
    @Value("${eCertificate.sfyb: true}")
    private boolean sfyb;
    /**
     * 港澳台证件种类代码
     */
    @Value("${eCertificate.H-M-T-zjzldm:}")
    private Integer zjzldm_HMT;

    @Value("${certificate.version:}")
    private String version;

    /**
     * 获取token的应用名称默认配置
     */
    @Value("${eCertificate.tokenYymc:}")
    private String tokenYymc;

    /**
     * 电子证照获取token应用名称根据区划配置
     */
    @Value("#{${eCertificate.dzzzTokenYymcOfqxdm: null}}")
    private Map<String, String> dzzzTokenYymcMap;

    /**
     * 常州电子签章获取token应用名称根据区划配置
     */
    @Value("#{${eCertificate.dzqzTokenYymcOfqxdm: null}}")
    private Map<String, String> dzqzTokenYymcMap;

    /**
     * 是否采用新版方案（2022年7月江苏省电子证照升级）
     */
    @Value("${eCertificate.newVersion:false}")
    public Boolean newVersion;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private PropsConfig propsConfig;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcZsMapper bdcZsMapper;


    /**
     * 生成存量电子证照参数
     * @param bdcXmDO 项目信息
     * @param bdcZsDO 证书信息
     * @return {DzzzClDTO} 存量电子证照参数
     */
    public DzzzClDTO generateClDzzzDTO(BdcXmDO bdcXmDO, BdcZsDO bdcZsDO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        DzzzClDTO dzzzClDTO = new DzzzClDTO();
        EZsTimeStampDTO eZsTimeStampDTO = this.generateEZsTimeStampDTO(bdcXmDO, bdcZsDO);
        BeanUtils.copyProperties(eZsTimeStampDTO, dzzzClDTO);
        dzzzClDTO.setSfyb(sfyb);
        dzzzClDTO.setQllxdm(valueOf(bdcXmDO.getQllx().toString()));
        this.setQlxz(dzzzClDTO, bdcXmDO, bdcZsDO);
        this.setYtxx(dzzzClDTO, bdcXmDO, bdcZsDO);
        this.setMjdw(dzzzClDTO, bdcXmDO, bdcZsDO, zdMap);

        if (CommonConstantUtils.ZSLX_ZM.equals(bdcZsDO.getZslx())) {
            dzzzClDTO.setGlbdcqzh(valueOf(bdcXmDO.getYcqzh()));
        }

        // 新版-证书-电子证照 新增字段
        if(xbDzzzZs(bdcZsDO, Constants.DZZZ)) {
            // 权利类型代码
            String qllxdm = getDsfZdz(bdcXmDO.getXmid(), bdcZsDO.getZsid(), "BDC_QLLX", String.valueOf(bdcZsDO.getQllx()));
            dzzzClDTO.setQllxdm(qllxdm);
        }
        // 新版-证明-电子证照 设置义务人、关联不动产权证号字段
        this.setYwrxx(dzzzClDTO, bdcXmDO, bdcZsDO);

        return dzzzClDTO;
    }


    /**
     * 设置权利性质
     * @param dzzzClDTO 存量电子证照参数
     * @param bdcXmDO 项目信息
     * @param bdcZsDO 证书信息
     */
    private void setQlxz(DzzzClDTO dzzzClDTO, BdcXmDO bdcXmDO, BdcZsDO bdcZsDO) {
        if(newVersion) {
            // 权利性质，例如：出让/市场化商品房
            dzzzClDTO.setQlxzdm(getQlxzdm(bdcZsDO));
            dzzzClDTO.setQlxz(valuesOf(bdcZsDO.getQlxz()));
        } else {
            this.setQlxzdm(dzzzClDTO, bdcXmDO, bdcZsDO);
            dzzzClDTO.setQlxz(valuesOf(bdcZsDO.getQlxz(), CommonConstantUtils.ZSLX_ZS, bdcZsDO.getZslx()));
        }
    }

    /**
     * 设置面积单位
     * @param dzzzClDTO 存量电子证照参数
     * @param bdcXmDO 项目信息
     * @param bdcZsDO 证书信息
     * @param zdMap 字典信息
     */
    private void setMjdw(DzzzClDTO dzzzClDTO, BdcXmDO bdcXmDO, BdcZsDO bdcZsDO, Map<String, List<Map>> zdMap) {
        if(newVersion) {
            dzzzClDTO.setMjdw(getMjdwdmmc(bdcXmDO, Constants.DZZZ));
            dzzzClDTO.setMjdwdm(getMjdwdmdm(bdcXmDO, bdcZsDO.getZsid(), Constants.DZZZ));
        } else {
            dzzzClDTO.setMjdw(valuesOf(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getMjdw(), zdMap.get("mjdw")), CommonConstantUtils.ZSLX_ZS, bdcZsDO.getZslx()));
            dzzzClDTO.setMjdwdm(valuesOf(bdcXmDO.getMjdw().toString(), CommonConstantUtils.ZSLX_ZS, bdcZsDO.getZslx()));
        }
    }

    /**
     * 设置用途信息
     * @param dzzzClDTO 存量电子证照参数
     * @param bdcXmDO 项目信息
     * @param bdcZsDO 证书信息
     */
    private void setYtxx(DzzzClDTO dzzzClDTO, BdcXmDO bdcXmDO, BdcZsDO bdcZsDO) {
        if(newVersion) {
            dzzzClDTO.setYt(replaceSpace(bdcZsDO.getYt(), Constants.DZZZ));
            dzzzClDTO.setYtdm(getYtdm(bdcZsDO));
        } else {
            String ytdm = StringUtils.defaultIfBlank(bdcXmDO.getDzwyt().toString(), bdcXmDO.getZdzhyt());
            dzzzClDTO.setYtdm(valuesOf(ytdm, CommonConstantUtils.ZSLX_ZS, bdcZsDO.getZslx()));

            String ytmc = StringUtils.EMPTY;
            if(StringUtils.isNotBlank(ytdm)) {
                String zdlx = null != bdcXmDO.getDzwyt() ? "fwyt" : "tdyt";
                List<Map> fwytMaps = bdcZdFeignService.queryBdcZd(zdlx);
                ytmc = StringToolUtils.convertBeanPropertyValueOfZdByString(ytdm, fwytMaps);
            }
            dzzzClDTO.setYt(valuesOf(ytmc, CommonConstantUtils.ZSLX_ZS, bdcZsDO.getZslx()));
        }
    }

    /**
     * 组织存量电子证照参数
     * <p>
     * 日期类型均转换为 13 位时间戳。
     * </p>
     *
     * @param bdcXmDO 项目对象
     * @param bdcZsDO 证书对象
     * @return cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsTimeStampDTO
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    public EZsTimeStampDTO generateEZsTimeStampDTO(BdcXmDO bdcXmDO, BdcZsDO bdcZsDO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        EZsTimeStampDTO eZsTimeStampDTO = new EZsTimeStampDTO();
        if (bdcZsDO == null || bdcZsDO.getZslx() == null) {
            throw new AppException("未生成证书信息");
        }
        Optional.ofNullable(BdcZzlxEnum.getZzlxEnum(bdcZsDO.getZslx())).ifPresent(bdcZzlxEnum -> eZsTimeStampDTO.setZzlxdm(bdcZzlxEnum.getDm()));

        this.setZzbfXx(eZsTimeStampDTO, bdcXmDO);
        this.setQlrxx(eZsTimeStampDTO, bdcZsDO, bdcZsDO.getGyfs());
        this.setZzyxq(eZsTimeStampDTO, bdcXmDO);
        eZsTimeStampDTO.setYwh(valueOf(bdcXmDO.getSlbh()));
        eZsTimeStampDTO.setBdcqzh(valueOf(bdcZsDO.getBdcqzh()));
        eZsTimeStampDTO.setDwdm(this.getDwdm(bdcZsDO, bdcXmDO, Constants.DZZZ));
        eZsTimeStampDTO.setSqsjc(valueOf(bdcZsDO.getSqsjc()));
        eZsTimeStampDTO.setSzsxqc(valueOf(bdcZsDO.getSzsxqc()));
        eZsTimeStampDTO.setFzrq(dateOf(bdcXmDO.getDjsj()));
        eZsTimeStampDTO.setBdcdyh(valueOf(bdcZsDO.getBdcdyh()));
        eZsTimeStampDTO.setZl(valueOf(bdcZsDO.getZl()));
        eZsTimeStampDTO.setQllx(valueOf(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getQllx(), zdMap.get(Constants.QLLX))));
        eZsTimeStampDTO.setNf(valueOf(bdcZsDO.getNf()));
        this.setZhlsh(eZsTimeStampDTO, bdcZsDO.getBdcqzh());
        eZsTimeStampDTO.setQlr(valuesOf(bdcZsDO.getQlr()));


        Integer zslx = bdcZsDO.getZslx();
        // 证书必填
        String gyfs = StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getGyfs(), zdMap.get(Constants.GYFS));
        eZsTimeStampDTO.setGyqk(valueOf(gyfs, CommonConstantUtils.ZSLX_ZS, zslx));
        eZsTimeStampDTO.setYt(valueOf(bdcZsDO.getYt(), CommonConstantUtils.ZSLX_ZS, zslx));
        eZsTimeStampDTO.setMj(valueOf(bdcZsDO.getMj(), CommonConstantUtils.ZSLX_ZS, zslx));
        eZsTimeStampDTO.setSyqx(valueOf(bdcZsDO.getSyqx()));


        // 证明必填
        eZsTimeStampDTO.setZmqlsx(valueOf(bdcZsDO.getZmqlsx(), CommonConstantUtils.ZSLX_ZM, zslx));
        eZsTimeStampDTO.setYwr(valuesOf(bdcZsDO.getYwr(), CommonConstantUtils.ZSLX_ZM, zslx));


        eZsTimeStampDTO.setYzh(valueOf(bdcZsDO.getQzysxlh()));
        eZsTimeStampDTO.setZzbfrq(dateOf(bdcXmDO.getDjsj()));

        // 非必填
        eZsTimeStampDTO.setQlqtzk(bdcZsDO.getQlqtzk());
        eZsTimeStampDTO.setFj(bdcZsDO.getFj());
        eZsTimeStampDTO.setQt(bdcZsDO.getQlqtzk());
        return eZsTimeStampDTO;
    }

    /**
     * 设置证照颁发信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void setZzbfXx(EZsTimeStampDTO eZsTimeStampDTO, BdcXmDO bdcXmDO) {
        LOGGER.info("电子证照存量数据设置ZzbfXx");
        eZsTimeStampDTO.setZzbfjg(valueOf(this.getZzbfjg(bdcXmDO)));
        eZsTimeStampDTO.setZzbfjgdm(valueOf(this.getZzbfjgdm(bdcXmDO)));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXmDO 项目信息
     * @return {String} 证照颁发机构
     * @description 获取证照颁发机构（各地电子证照、常州电子签章）
     *
     * <p>
     *  1. 存在 defaultZzbfjg 配置则先读取默认配置；
     *  2. 存在按照区县代码（qxdm）对照配置则通过 qxdm 获取对应的证照颁发机构信息
     *  3. 存在按照登记部门代码（djbmdm）对照配置则通过 djbmdm 获取对应的证照颁发机构信息
     *  4. 不存在配置直接使用 djjg
     * </p>
     */
    public String getZzbfjg(BdcXmDO bdcXmDO) {
        String jg = bdcXmDO.getDjjg();
        LOGGER.info("电子证照存量数据getZzbfjg方法");
        if (StringUtils.isNotBlank(defaultZzbfjg)) {
            LOGGER.info("电子证照存量数据defaultZzbfjg"+defaultZzbfjg);
            jg = defaultZzbfjg;
        } else if(MapUtils.isNotEmpty(zzbfjgQxdm)) {
            LOGGER.info("电子证照存量数据zzbfjgQxdm"+this.zzbfjgQxdm.get(bdcXmDO.getQxdm())+"====bdcXmDO.getDjjg()==:"+bdcXmDO.getDjjg());
            // 按照区县代码对照配置的证照颁发机构
            jg = MapUtils.isNotEmpty(zzbfjgQxdm) ? this.zzbfjgQxdm.get(bdcXmDO.getQxdm()) : bdcXmDO.getDjjg();
        }  else {
            LOGGER.info("电子证照存量数据zzbfjg"+this.zzbfjg.get(bdcXmDO.getDjbmdm())+"====bdcXmDO.getDjjg()==:"+bdcXmDO.getDjjg());
            jg = MapUtils.isNotEmpty(zzbfjg) ? this.zzbfjg.get(bdcXmDO.getDjbmdm()) : bdcXmDO.getDjjg();
        }
        LOGGER.info("电子证照存量数据Zzbfjg方法 jg： "+jg);
        return jg;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXmDO 项目信息
     * @return {String} 证照颁发机构代码
     * @description 获取证照颁发机构代码（各地电子证照、常州电子签章）
     *
     * <p>
     *  1. 存在 defaultZzbfjgdm 配置则先读取默认配置；
     *  2. 存在按照区县代码（qxdm）对照配置则通过 qxdm 获取对应的证照颁发机构代码信息
     *  3. 存在按照登记部门代码（djbmdm）对照配置则通过 djbmdm 获取对应的证照颁发机构代码信息
     *  4. 不存在配置直接使用 djbmdm
     * </p>
     */
    public String getZzbfjgdm(BdcXmDO bdcXmDO) {
        String jgdm = bdcXmDO.getDjbmdm();
        LOGGER.info("电子证照存量数据getZzbfjgdm方法");
        if (StringUtils.isNotBlank(defaultZzbfjgdm)) {
            LOGGER.info("电子证照存量数据defaultZzbfjgdm："+defaultZzbfjgdm);
            jgdm = defaultZzbfjgdm;
        } else if(MapUtils.isNotEmpty(zzbfjgdmQxdm)) {
            LOGGER.info("电子证照存量数据zzbfjgdmQxdm"+this.zzbfjgdmQxdm.get(bdcXmDO.getQxdm())+"====bdcXmDO.getDjbmdm()==:"+bdcXmDO.getDjbmdm());
            // 按照区县代码对照配置的证照颁发机构代码
            jgdm = MapUtils.isNotEmpty(zzbfjgdmQxdm) ? this.zzbfjgdmQxdm.get(bdcXmDO.getQxdm()) : bdcXmDO.getDjbmdm();
        }  else {
            LOGGER.info("电子证照存量数据zzbfjgdm"+this.zzbfjgdm.get(bdcXmDO.getDjbmdm())+"====bdcXmDO.getDjbmdm()==:"+bdcXmDO.getDjbmdm());
            jgdm = MapUtils.isNotEmpty(zzbfjgdm) ? this.zzbfjgdm.get(bdcXmDO.getDjbmdm()) : bdcXmDO.getDjbmdm();
        }
        LOGGER.info("电子证照存量数据Zzbfjgdm方法 jgdm： "+jgdm);
        return jgdm;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZsDO 证书信息
     * @param bdcXmDO 项目信息
     * @param type 类型， DZZZ：电子证照， DZQZ 常州电子签章
     * @return {String} 单位代码
     * @description 获取证照单位代码（电子证照、常州电子签章）
     *
     *  1、盐城：固定 3209
     *  2、常州： 电子签章：如果有 qxdm 对照配置则按照 qxdm 获取配置，否则按照 djbmdm 对照配置获取；电子证照优先按照区划配置，否则默认值
     *  3、其它地方：如果设置取 bdcdyh 前6位则取前6位；否则取项目 qxdm
     */
    public String getDwdm(BdcZsDO bdcZsDO, BdcXmDO bdcXmDO, String type) {
        String dwdm = bdcXmDO.getQxdm();

        if (CommonConstantUtils.SYSTEM_VERSION_YC.equals(version)) {
            dwdm = "3209";
        } else if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(version)){
            if(Constants.DZQZ.equals(type)) {
                // 常州电子签章
                if(MapUtils.isNotEmpty(dwdmMap)) {
                    // 按照区县代码对照配置单位代码
                    dwdm = this.dwdmMap.get(bdcXmDO.getQxdm());
                    LOGGER.info("常州电子签章单位代码读取配置：dwdmMap，对应bdcqzh: {}", bdcZsDO.getBdcqzh());
                } else {
                    // 按照登记部门代码对照配置单位代码
                    dwdm = MapUtils.isNotEmpty(zzbfjgdm) ? this.zzbfjgdm.get(bdcXmDO.getDjbmdm()) : bdcXmDO.getDjbmdm();
                    LOGGER.info("常州电子签章单位代码读取配置：zzbfjgdm，对应bdcqzh: {}", bdcZsDO.getBdcqzh());
                }
            } else {
                // 常州电子证照
                if(MapUtils.isNotEmpty(dzzzDwdmQxdm)) {
                    dwdm = dzzzDwdmQxdm.get(bdcXmDO.getQxdm());
                    LOGGER.info("常州电子证照单位代码读取配置：dzzzDwdmQxdm，对应bdcqzh: {}", bdcZsDO.getBdcqzh());
                } else {
                    dwdm = "320400";
                    LOGGER.info("常州电子证照单位代码默认320400，对应bdcqzh: {}", bdcZsDO.getBdcqzh());
                    // 溧阳市
                    if(StringUtils.isNotBlank(bdcXmDO.getQxdm()) && CommonConstantUtils.QXDM_LY_DZZZ.equals(bdcXmDO.getQxdm())) {
                        dwdm = CommonConstantUtils.QXDM_LY_DZZZ;
                        LOGGER.info("常州溧阳市电子证照单位代码默认320481，对应bdcqzh: {}", bdcZsDO.getBdcqzh());
                    }
                }
            }
        } else {
            if(dwdmBdcdyhqlw && StringUtils.isNotBlank(bdcZsDO.getBdcdyh())) {
                dwdm = BdcdyhToolUtils.subPreSixBdcdyh(bdcZsDO.getBdcdyh());
            } else {
                dwdm = valueOf(bdcZsDO.getQxdm());
            }
        }
        return dwdm;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qxdm 区县代码
     * @param type 类型， DZZZ：电子证照， DZQZ 常州电子签章
     * @return 应用名称
     * @description 获取电子证照、签章验证token应用名称
     */
    public String getTokenYymc(String qxdm, String type) {
        if(StringUtils.isBlank(qxdm)) {
            return tokenYymc;
        }

        if(Constants.DZQZ.equals(type)) {
            // 常州电子签章
            if(MapUtils.isNotEmpty(dzqzTokenYymcMap)) {
                return dzqzTokenYymcMap.get(qxdm);
            } else {
                return tokenYymc;
            }
        } else {
            // 电子证照
            if(MapUtils.isNotEmpty(dzzzTokenYymcMap)) {
                return dzzzTokenYymcMap.get(qxdm);
            } else {
                return tokenYymc;
            }
        }
    }

    /**
     * 设置持证主体信息和权利人信息
     *
     * @param bdcZsDO 证书
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    private void setQlrxx(EZsTimeStampDTO eZsTimeStampDTO, BdcZsDO bdcZsDO, Integer gyfs) {
        if (StringUtils.isBlank(bdcZsDO.getZsid())) {
            return;
        }
        Example example = new Example(BdcQlrDO.class);
        example.createCriteria().andEqualTo(Constants.ZSID, bdcZsDO.getZsid()).andEqualTo("qlrlb", CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = entityMapper.selectByExampleNotNull(example);

        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            return;
        }

        // 对于权利人根据 qlrmc 和 zjh 去重
        List<BdcQlrDO> bdcQlrDOS = bdcQlrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc() + ";" + o.getZjh()))), ArrayList::new));

        StringJoiner czzt = new StringJoiner(CommonConstantUtils.ZF_GZH);
        StringJoiner czztdm = new StringJoiner(CommonConstantUtils.ZF_GZH);
        StringJoiner czztdmlx = new StringJoiner(CommonConstantUtils.ZF_GZH);
        StringJoiner czztdmlxdm = new StringJoiner(CommonConstantUtils.ZF_GZH);
        List<EQlrxxDTO> eQlrxxDTOList = new ArrayList();

        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
            if (bdcQlrDO != null && StringUtils.isNotBlank(bdcQlrDO.getQlrmc())) {
                EQlrxxDTO qlrxx = new EQlrxxDTO();
                qlrxx.setCzzt(bdcQlrDO.getQlrmc());
                czzt.add(bdcQlrDO.getQlrmc());
                if (StringUtils.isNoneBlank(bdcQlrDO.getZjh())) {
                    qlrxx.setCzztdm(bdcQlrDO.getZjh());
                    czztdm.add(bdcQlrDO.getZjh());
                }

                // 新版-证书-电子证照-共有方式为按份共有，添加权利人共有比例
                if(sftjQlbl(bdcZsDO, Constants.DZZZ, bdcQlrDO)) {
                    qlrxx.setCzztgybl(bdcQlrDO.getQlbl());
                }

                if (null != bdcQlrDO.getZjzl()) {
                    List<String> ztZjdmList;
                    // 港澳台证件种类特殊处理
                    if (null != zjzldm_HMT && zjzldm_HMT.equals(bdcQlrDO.getZjzl())) {
                        ztZjdmList = this.getHMTZzztDmXx(bdcQlrDO);
                    } else {
                        // 其它类型取配置信息
                        ztZjdmList = propsConfig.getZzztDmXx(bdcQlrDO.getZjzl());
                    }
                    if (CollectionUtils.isNotEmpty(ztZjdmList) && CollectionUtils.size(ztZjdmList) >= 2) {
                        String ztdmlxDm = ztZjdmList.get(0);
                        String ztdmlx = ztZjdmList.get(1);

                        qlrxx.setCzztdmlx(ztdmlx);
                        qlrxx.setCzztdmlxdm(ztdmlxDm);

                        czztdmlx.add(ztdmlx);
                        czztdmlxdm.add(ztdmlxDm);
                    }
                }

                if (CommonConstantUtils.GYFS_AFGY.equals(gyfs)) {
                    qlrxx.setCzztgybl(bdcQlrDO.getQlbl());
                }
                eQlrxxDTOList.add(qlrxx);
            }
        }
        if (CollectionUtils.isNotEmpty(eQlrxxDTOList)) {
            eZsTimeStampDTO.setQlrxx(eQlrxxDTOList);
        }
        if (StringUtils.isNotBlank(czzt.toString())) {
            eZsTimeStampDTO.setCzzt(czzt.toString());
        }
        if (StringUtils.isNotBlank(czztdm.toString())) {
            eZsTimeStampDTO.setCzztdm(czztdm.toString());
        }
        if (StringUtils.isNotBlank(czztdmlx.toString())) {
            eZsTimeStampDTO.setCzztdmlx(czztdmlx.toString());
        }
        if (StringUtils.isNotBlank(czztdmlxdm.toString())) {
            eZsTimeStampDTO.setCzztdmlxdm(czztdmlxdm.toString());
        }
    }

    /**
     * 存量电子证照设置义务人信息、关联产权证号信息
     * @param dzzzClDTO 存量电子证照
     * @param bdcXmDO 项目
     * @param bdcZsDO 证书
     */
    private void setYwrxx(DzzzClDTO dzzzClDTO, BdcXmDO bdcXmDO, BdcZsDO bdcZsDO) {
        if(!xbDzzzZm(bdcZsDO, Constants.DZZZ) || StringUtils.isBlank(bdcZsDO.getZsid())) {
            return;
        }

        // 义务人信息
        dzzzClDTO.setYwrxx(getYwrxx(bdcZsDO));
        // 关联不动产权证号
        dzzzClDTO.setGlbdcqzh(getGlbdcqzh(bdcXmDO, bdcZsDO));
    }

    /**
     * 获取证书流水号
     *
     * @param eZsTimeStampDTO 电子证照对象
     * @param bdcqzh          不动产权证号
     * @return String 证流水号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    private void setZhlsh(EZsTimeStampDTO eZsTimeStampDTO, String bdcqzh) {
        if (StringUtils.isNotBlank(bdcqzh)) {
            Integer start = StringUtils.indexOf(bdcqzh, CommonConstantUtils.DI);
            Integer end = StringUtils.indexOf(bdcqzh, CommonConstantUtils.HAO);
            eZsTimeStampDTO.setZhlsh(StringUtils.substring(bdcqzh, start + 1, end));
        } else {
            eZsTimeStampDTO.setZhlsh(CommonConstantUtils.ZF_YW_XG);
        }
    }

    /**
     * 设置权利性质 「以证书显示内容为准」
     * <p>
     * 1. zs qlxz 为空直接设置 xm 的 qlxz
     * 2. zs qlxz 不为空并且 不包含 / 也就是非拼接，根据 zs qlxz 匹配代码
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void setQlxzdm(DzzzClDTO dzzzClDTO, BdcXmDO bdcXmDO, BdcZsDO bdcZsDO) {
        if (StringUtils.isBlank(bdcZsDO.getQlxz())) {
            dzzzClDTO.setQlxzdm(valueOf(bdcXmDO.getQlxz(), CommonConstantUtils.ZSLX_ZS, bdcZsDO.getZslx()));
            return;
        }

        if (!bdcZsDO.getQlxz().contains(CommonConstantUtils.ZF_YW_XG)) {
            String qlxz = bdcZsDO.getQlxz();
            List<Map> qlxzMaps = bdcZdFeignService.queryBdcZd("qlxz");
            List<Map> fwxzMaps = bdcZdFeignService.queryBdcZd("fwxz");
            for (Map map : qlxzMaps) {
                String mc = MapUtils.getString(map, "MC");
                if (StringUtils.equals(mc, qlxz)) {
                    dzzzClDTO.setQlxzdm(MapUtils.getString(map, "DM"));
                    return;
                }
            }
            for (Map map : fwxzMaps) {
                String mc = MapUtils.getString(map, "MC");
                if (StringUtils.equals(mc, qlxz)) {
                    dzzzClDTO.setQlxzdm(MapUtils.getString(map, "DM"));
                    return;
                }
            }
        }

        StringJoiner qlxzdm = new StringJoiner(CommonConstantUtils.ZF_GZH);
        if (StringUtils.isNotBlank(bdcXmDO.getQlxz())) {
            qlxzdm.add(bdcXmDO.getQlxz());
        }

        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
        if (bdcQl instanceof BdcFdcqDO && ((BdcFdcqDO) bdcQl).getFwxz() != null) {
            Integer fwxz = ((BdcFdcqDO) bdcQl).getFwxz();
            qlxzdm.add(fwxz.toString());
        }
        dzzzClDTO.setQlxzdm(valueOf(qlxzdm.toString(), CommonConstantUtils.ZSLX_ZS, bdcZsDO.getZslx()));
    }

    /**
     * 生成证书的使用期限
     *
     * @param eZsTimeStampDTO 电子证照对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    private void setZzyxq(EZsTimeStampDTO eZsTimeStampDTO, BdcXmDO bdcXmDO) {
        try {
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
            if (null == bdcQl) {
                eZsTimeStampDTO.setZzyxqqsrq(bdcXmDO.getDjsj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(DateUtils.parseDate("2099-12-31", "yyyy-MM-dd").getTime());
                return;
            }

            if (bdcQl instanceof BdcJsydsyqDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcJsydsyqDO) bdcQl).getSyqqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcJsydsyqDO) bdcQl).getSyqjssj().getTime());
            } else if (bdcQl instanceof BdcFdcqDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcFdcqDO) bdcQl).getTdsyqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcFdcqDO) bdcQl).getTdsyjssj().getTime());
            } else if (bdcQl instanceof BdcGjzwsyqDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcGjzwsyqDO) bdcQl).getTdhysyqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcGjzwsyqDO) bdcQl).getTdhysyjssj().getTime());
            } else if (bdcQl instanceof BdcHysyqDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcHysyqDO) bdcQl).getSyqqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcHysyqDO) bdcQl).getSyqjssj().getTime());
            } else if (bdcQl instanceof BdcTdcbnydsyqDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcTdcbnydsyqDO) bdcQl).getCbqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcTdcbnydsyqDO) bdcQl).getCbjssj().getTime());
            } else if (bdcQl instanceof BdcNydjyqDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcNydjyqDO) bdcQl).getCbqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcNydjyqDO) bdcQl).getCbjssj().getTime());
            } else if (bdcQl instanceof BdcQtxgqlDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcQtxgqlDO) bdcQl).getQlqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcQtxgqlDO) bdcQl).getQljssj().getTime());
            } else if (bdcQl instanceof BdcLqDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcLqDO) bdcQl).getLdsyqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcLqDO) bdcQl).getLdsyjssj().getTime());
            } else if (bdcQl instanceof BdcDyiqDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcDyiqDO) bdcQl).getQlqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcDyiqDO) bdcQl).getQljssj().getTime());
            } else if (bdcQl instanceof BdcDyaqDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcDyaqDO) bdcQl).getZwlxqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcDyaqDO) bdcQl).getZwlxjssj().getTime());
            } else if (bdcQl instanceof BdcJzqDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcJzqDO) bdcQl).getJzqkssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcJzqDO) bdcQl).getJzqjssj().getTime());
            } else if (bdcQl instanceof BdcYgDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcYgDO) bdcQl).getZwlxqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcYgDO) bdcQl).getZwlxjssj().getTime());
            } else if (bdcQl instanceof BdcYyDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcYyDO) bdcQl).getYydjksrq().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcYyDO) bdcQl).getYydjjsrq().getTime());
            } else if (bdcQl instanceof BdcFwzlDO) {
                eZsTimeStampDTO.setZzyxqqsrq(((BdcFwzlDO) bdcQl).getZlqssj().getTime());
                eZsTimeStampDTO.setZzyxqjzrq(((BdcFwzlDO) bdcQl).getZljssj().getTime());
            }

            // 如果还为空直接设置默认值
            if (null == eZsTimeStampDTO.getZzyxqqsrq()) {
                eZsTimeStampDTO.setZzyxqqsrq(System.currentTimeMillis());
            }
            if (null == eZsTimeStampDTO.getZzyxqjzrq()) {
                eZsTimeStampDTO.setZzyxqjzrq(DateUtils.parseDate("2099-12-31", "yyyy-MM-dd").getTime());
            }
        } catch (Exception e) {
            // 有效期非必填字段，这里即使出现异常照常提交申请
            LOGGER.error("电子证照处理有效期异常{}, bdcqzh: {}", e.getMessage(), bdcXmDO.getBdcqzh());
        }
    }

    /**
     * @param bdcQlrDO 权利人信息
     * @return 港澳台证照主体信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 港澳台证照主体代码信息处理
     */
    public List<String> getHMTZzztDmXx(BdcQlrDO bdcQlrDO) {
        if (!zjzldm_HMT.equals(bdcQlrDO.getZjzl())) {
            return Collections.emptyList();
        }
        String zjh = bdcQlrDO.getZjh();
        if (StringUtils.isBlank(zjh)) {
            return Collections.emptyList();
        }
        String firstChar = StringUtils.substring(zjh, 0, 1);
        List<String> zzdmXxList = new ArrayList(2);
        if (StringUtils.equals(firstChar, "H") || StringUtils.equals(firstChar, "M")) {
            zzdmXxList.add("516");
            zzdmXxList.add(newVersion ? "港澳居民来往内地通行证号码" : "港澳居民来往大陆通行证号码");
        } else if (StringUtils.equals(firstChar, "T")) {
            zzdmXxList.add("511");
            zzdmXxList.add(newVersion ? "台湾居民来往大陆通行证号码" : "台湾居民来往大陆通行证号码");
        }
        return zzdmXxList;
    }

    public static Long dateOf(Date date) {
        return (date == null) ? System.currentTimeMillis() : date.getTime();
    }

    /**
     * 必填项字符串需要处理
     */
    public static String valueOf(Object obj) {
        return (obj == null) ? CommonConstantUtils.ZF_YW_XG : obj.toString();
    }

    /**
     * 存量数据拼接字符串需要处理 替换 / 为 ^
     */
    public static String valuesOf(String str) {
        if (StringUtils.isNotBlank(str)) {
            return str.trim().replaceAll(CommonConstantUtils.ZF_YW_XG, CommonConstantUtils.ZF_GZH).replaceAll(" ", CommonConstantUtils.ZF_GZH);
        }
        return CommonConstantUtils.ZF_YW_XG;
    }

    /**
     * 必填项字端需要处理
     */
    public static String valueOf(Object obj, Integer zslx, Integer param) {
        if (obj == null) {
            return zslx.equals(param) ? CommonConstantUtils.ZF_YW_XG : StringUtils.EMPTY;
        }
        return obj.toString();
    }

    /**
     * 存量数据拼接字端需要处理 替换 / 为 ^
     */
    public static String valuesOf(String str, Integer zslx, Integer param) {
        if (StringUtils.isNotBlank(str)) {
            return str.trim().replaceAll(CommonConstantUtils.ZF_YW_XG, CommonConstantUtils.ZF_GZH).replaceAll(" ", CommonConstantUtils.ZF_GZH);
        }
        return zslx.equals(param) ? CommonConstantUtils.ZF_YW_XG : StringUtils.EMPTY;
    }

    /**
     * 新版电子证照需要将字段值拼接符改为用^连接
     * @param str 待替换字符串
     * @return {String} ^拼接的字符串
     */
    public String replaceSpace(String str, String type) {
        if(StringUtils.isBlank(str)) {
            return CommonConstantUtils.ZF_YW_XG;
        }

        if(newVersion && Constants.DZZZ.equals(type)) {
            return str.trim().replaceAll(" ", CommonConstantUtils.ZF_GZH)
                    .replaceAll(CommonConstantUtils.ZF_YW_XG, CommonConstantUtils.ZF_GZH);
        }
        return str;
    }

    /**
     * 获取权利性质代码
     * <p>
     *     用途取值证书qlxz字段，因为某些权利证书会存在拼接情况，例如房地产权证书会拼接权利性质、房屋性质，
     *     而qlxz本身又是在证书模板配置里设置的SQL，所以这里采用匹配字典名称反向拼接代码值得方案
     * </p>
     *
     * @param bdcZsDO 证书信息
     * @return {String} 权利性质
     */
    public String getQlxzdm(BdcZsDO bdcZsDO) {
        if(StringUtils.isBlank(bdcZsDO.getQlxz())) {
            return CommonConstantUtils.ZF_YW_XG;
        }

        String qlxz = bdcZsDO.getQlxz().trim();
        if(qlxz.startsWith(CommonConstantUtils.ZF_YW_XG)) {
            qlxz = StringUtils.replaceOnce(qlxz, CommonConstantUtils.ZF_YW_XG, "");
        }
        if(qlxz.endsWith(CommonConstantUtils.ZF_YW_XG)) {
            qlxz = StringUtils.removeEnd(qlxz, CommonConstantUtils.ZF_YW_XG);
        }

        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setDsfxtbs(BDCDJ_DZZZ);
        bdcZdDsfzdgxDO.setZdbs("BDC_QLXZDM");
        List<BdcZdDsfzdgxDO> zdxxList = bdcZdFeignService.queryDsfZdxBybs(bdcZdDsfzdgxDO);
        if(CollectionUtils.isEmpty(zdxxList)) {
            LOGGER.error("新版电子证照未查询到权利性质代码BDC_QLXZDM第三方字典值，zsid：{}，", bdcZsDO.getZsid());
            return CommonConstantUtils.ZF_YW_XG;
        }

        // 因为权利性质可能是拼接的，所以需要挨个匹配（这里权利性质代码为了和原汉字对应，也取证书表权利性质，但是不同权利权利性质取值逻辑不同，是在证书模板配置里设置的，所以这里直接字典值匹配处理）
        String[] qlxzs = qlxz.split(CommonConstantUtils.ZF_YW_XG);
        Map<String, String> zdxxMap = zdxxList.stream().collect(Collectors.toMap(BdcZdDsfzdgxDO::getBdczdz, BdcZdDsfzdgxDO::getDsfzdz, (k1, k2) -> k1));
        List<String> qlxzList = new ArrayList<>();
        for(String qlxzItem : qlxzs) {
            qlxzList.add(zdxxMap.get(qlxzItem));
        }
        return StringUtils.join(qlxzList, CommonConstantUtils.ZF_GZH);
    }

    /**
     * 获取用途代码
     * <p>
     *     用途取值证书yt字段，因为某些权利证书会存在拼接情况，例如房地产权证书会拼接房屋用途、土地用途，
     *     而yt本身又是在证书模板配置里设置的SQL，所以这里采用匹配字典名称反向拼接代码值得方案
     * </p>
     *
     * @param bdcZsDO 证书信息
     * @return {String} 用途代码
     */
    public String getYtdm(BdcZsDO bdcZsDO) {
        if(StringUtils.isBlank(bdcZsDO.getYt())) {
            return CommonConstantUtils.ZF_YW_XG;
        }

        String yt = bdcZsDO.getYt().trim();
        if(yt.startsWith(CommonConstantUtils.ZF_YW_XG)) {
            yt = StringUtils.replaceOnce(yt, CommonConstantUtils.ZF_YW_XG, "");
        }
        if(yt.endsWith(CommonConstantUtils.ZF_YW_XG)) {
            yt = StringUtils.removeEnd(yt, CommonConstantUtils.ZF_YW_XG);
        }

        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setDsfxtbs(BDCDJ_DZZZ);
        bdcZdDsfzdgxDO.setZdbs("BDC_YTDM");
        List<BdcZdDsfzdgxDO> zdxxList = bdcZdFeignService.queryDsfZdxBybs(bdcZdDsfzdgxDO);
        if(CollectionUtils.isEmpty(zdxxList)) {
            LOGGER.error("新版电子证照未查询到用途代码BDC_YTDM第三方字典值，zsid：{}，" , bdcZsDO.getZsid());
            return CommonConstantUtils.ZF_YW_XG;
        }

        Map<String, String> zdxxMap = zdxxList.stream().collect(Collectors.toMap(BdcZdDsfzdgxDO::getBdczdz, BdcZdDsfzdgxDO::getDsfzdz, (k1, k2) -> k1));

        String[] yts = yt.split(CommonConstantUtils.ZF_YW_XG);
        List<String> ytList = new ArrayList<>();
        for(int i = 0; i < yts.length; i++) {
            if(i < yts.length - 1 && "车库".equals(yts[i]) && "车位".equals(yts[i + 1])) {
                ytList.add(zdxxMap.get("车库/车位"));
                ++i;
            } else {
                ytList.add(zdxxMap.get(yts[i]));
            }
        }
        return StringUtils.join(ytList, CommonConstantUtils.ZF_GZH);
    }

    /**
     * 新版-证明-电子证照 增加义务人信息
     * @param bdcZsDO 证书信息
     * @return {List} 义务人
     */
    public List<EYwrxxDTO> getYwrxx(BdcZsDO bdcZsDO) {
        List<BdcQlrDO> bdcYwrDOList = bdcQlrFeignService.listBdcQlrByZsid(bdcZsDO.getZsid(), CommonConstantUtils.QLRLB_YWR);
        if (CollectionUtils.isEmpty(bdcYwrDOList)) {
            return null;
        }

        // 根据 qlrmc 和 zjh 去重
        List<BdcQlrDO> ywrDOList = bdcYwrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc() + ";" + o.getZjh()))), ArrayList::new));

        List<EYwrxxDTO> eYwrxxList = new ArrayList();
        for (BdcQlrDO bdcYwrDO : ywrDOList) {
            if (bdcYwrDO != null && StringUtils.isNotBlank(bdcYwrDO.getQlrmc())) {
                EYwrxxDTO ywrxx = new EYwrxxDTO();
                ywrxx.setYwr(bdcYwrDO.getQlrmc());
                ywrxx.setZjh(bdcYwrDO.getZjh());

                if (null != bdcYwrDO.getZjzl()) {
                    List<String> ztZjdmList;
                    // 港澳台证件种类特殊处理
                    if (null != zjzldm_HMT && zjzldm_HMT.equals(bdcYwrDO.getZjzl())) {
                        ztZjdmList = this.getHMTZzztDmXx(bdcYwrDO);
                    } else {
                        // 其它类型取配置信息
                        ztZjdmList = propsConfig.getZzztDmXx(bdcYwrDO.getZjzl());
                    }
                    if (CollectionUtils.isNotEmpty(ztZjdmList) && CollectionUtils.size(ztZjdmList) >= 2) {
                        String ztdmlxDm = ztZjdmList.get(0);
                        String ztdmlx = ztZjdmList.get(1);

                        ywrxx.setZjzl(ztdmlxDm);
                        ywrxx.setZjzlmc(ztdmlx);
                    }
                }
                eYwrxxList.add(ywrxx);
            }
        }
        return eYwrxxList;
    }

    /**
     * 获取第三方字典值
     * @param xmid 项目ID
     * @param zsid 证书ID
     * @param zdbs 字典标识
     * @param bdczdz 不动产字典值
     * @return {String} 第三方字典值
     */
    public String getDsfZdz(String xmid, String zsid, String zdbs, String bdczdz) {
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setDsfxtbs(BDCDJ_DZZZ);
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setBdczdz(bdczdz);
        BdcZdDsfzdgxDO zdxx = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        if (null == zdxx || StringUtils.isBlank(zdxx.getDsfzdz())) {
            LOGGER.error("新版电子证照字典信息{}生成错误，xmid：{}，zsid：{}，原因：未配置第三方字典对照表",zdbs, xmid, zsid);
            return null;
        }
        return zdxx.getDsfzdz();
    }

    /**
     * 新版-证明-电子证照增加关联不动产权证号字段
     * @param bdcXmDO 项目信息
     * @param bdcZsDO 证书信息
     * @return {String} 关联不动产权证号
     */
    public String getGlbdcqzh(BdcXmDO bdcXmDO, BdcZsDO bdcZsDO) {
        // 关联不动产权证号
        List<BdcZsDO> ycqZsList = bdcZsService.listBdcZsByZmid(bdcZsDO.getZsid());

        String glbdcqzh;
        if(CollectionUtils.isNotEmpty(ycqZsList)) {
            glbdcqzh = ycqZsList.stream().map(BdcZsDO::getBdcqzh).distinct().collect(Collectors.joining(CommonConstantUtils.ZF_GZH));
        } else {
            glbdcqzh = bdcXmDO.getYcqzh();
        }

        return valuesOf(glbdcqzh);
    }

    /**
     * 获取面积单位字典名称
     * @param bdcXmDO 项目信息
     * @param type DZZZ：电子证照， DZQZ 常州电子签章
     * @return {String} 面积单位字典名称
     */
    public String getMjdwdmmc(BdcXmDO bdcXmDO, String type) {
        List<Map> mjdwZdList = bdcZdFeignService.queryBdcZd("mjdw");
        String mjdwmc = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getMjdw(), mjdwZdList);

        if(null != bdcXmDO.getDzwmj() && null != bdcXmDO.getZdzhmj()) {
            mjdwmc = mjdwmc + CommonConstantUtils.ZF_GZH + mjdwmc;
        }

        return replaceSpace(mjdwmc, type);
    }

    /**
     * 获取面积单位代码对照省厅字典值
     * @param bdcXmDO 项目信息
     * @param zsid 证书ID
     * @param type DZZZ：电子证照， DZQZ 常州电子签章
     * @return {String} 面积单位代码
     */
    public String getMjdwdmdm(BdcXmDO bdcXmDO, String zsid, String type) {
        String dsfzdz = getDsfZdz(bdcXmDO.getXmid(), zsid,"BDC_ZD_MJDW", String.valueOf(bdcXmDO.getMjdw()));

        if(null != bdcXmDO.getDzwmj() && null != bdcXmDO.getZdzhmj()) {
            dsfzdz = dsfzdz + CommonConstantUtils.ZF_GZH + dsfzdz;
        }

        return replaceSpace(dsfzdz, type);
    }

    /**
     * 新版-电子证照 增量接口新增：批量抵押的抵押物信息
     * @param bdcXmDO 项目信息
     * @param bdcZsDO 证书信息
     * @param type DZZZ：电子证照， DZQZ 常州电子签章
     * @return {List} 抵押物信息
     */
    public List<EZzfsxxDTO> getZzfsxx(BdcXmDO bdcXmDO, BdcZsDO bdcZsDO, String type) {
        List<EZzfsxxDTO> eZzfsxxDTOList = bdcZsMapper.queryDyaqCqxx(bdcZsDO.getZsid());
        if(CollectionUtils.isEmpty(eZzfsxxDTOList)) {
            LOGGER.error("新版电子证照未查询到批量抵押附属信息，xmid：{}，zsid：{}，", bdcXmDO.getXmid(), bdcZsDO.getZsid());
            return null;
        }

        // 数据库房地产权竣工时间保存的是字符串，可能值：2022-01-01  、 2009
        for(EZzfsxxDTO eZzfsxxDTO : eZzfsxxDTOList) {
            if(StringUtils.isNotBlank(eZzfsxxDTO.getJgsj2())) {
                try {
                    Date date = DateUtils.parseDate(eZzfsxxDTO.getJgsj2(), sdf_China, sdf_ymd, sdf, sdf_ymdWithSpilt, sdf_ymdhms, sdf_ymdhm, DATE_NY, DATE_N);
                    eZzfsxxDTO.setJgsj(date.getTime());
                } catch (Exception e) {
                }
                // 中转字段无需传值
                eZzfsxxDTO.setJgsj2(null);
            }
        }

        return eZzfsxxDTOList;
    }

    /**
     * 是否需要添加权利比例
     * @param bdcZsDO 证书信息
     * @param type 类型， DZZZ：电子证照， DZQZ 常州电子签章
     * @param bdcQlrDO 权利人信息
     * @return 需要 true，不需要 false
     */
    public boolean sftjQlbl(BdcZsDO bdcZsDO, String type, BdcQlrDO bdcQlrDO) {
        return this.xbDzzzZs(bdcZsDO, type) && CommonConstantUtils.GYFS_AFGY.equals(bdcQlrDO.getGyfs());
    }

    /**
     * 是否是新版-证书-电子证照
     * @param bdcZsDO 证书信息
     * @param type 类型， DZZZ：电子证照， DZQZ 常州电子签章
     * @return 是 true，否 false
     */
    public boolean xbDzzzZs(BdcZsDO bdcZsDO, String type) {
        return newVersion
                && BdcZslxEnum.ZS.getZslx().equals(bdcZsDO.getZslx())
                && Constants.DZZZ.equals(type);
    }

    /**
     * 是否是新版-证明-电子证照
     * @param bdcZsDO 证书信息
     * @param type 类型， DZZZ：电子证照， DZQZ 常州电子签章
     * @return 是 true，否 false
     */
    public boolean xbDzzzZm(BdcZsDO bdcZsDO, String type) {
        return newVersion
                && BdcZslxEnum.ZM.getZslx().equals(bdcZsDO.getZslx())
                && Constants.DZZZ.equals(type);
    }

    /**
     * 是否是新版-批量-抵押且是一本证书对应一个项目场景
     * @param bdcZsDO 证书信息
     * @param bdcXmDO 项目信息
     * @return {boolean} 是 true 否 false
     */
    public boolean isXbPldy(BdcZsDO bdcZsDO, BdcXmDO bdcXmDO, String type) {
        if(newVersion && StringUtils.isNotBlank(bdcZsDO.getZsid()) && Constants.DZZZ.equals(type)) {
            // 查询当前证书关联几个单元
            List<BdcXmDO> xmDOList = bdcZsService.queryZsXmByZsid(bdcZsDO.getZsid());
            boolean isPlxm = CollectionUtils.size(xmDOList) > 1;

            // 是否抵押
            boolean isDyaq = false;
            if(CollectionUtils.size(xmDOList) > 0 && null != xmDOList.get(0) && QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                isDyaq = true;
            }

            return isDyaq && isPlxm;
        }
        return false;
    }
}
