package cn.gtmap.realestate.certificate.util;

import cn.gtmap.realestate.certificate.config.PropsConfig;
import cn.gtmap.realestate.certificate.core.bo.RequestHeadBO;
import cn.gtmap.realestate.certificate.core.dto.DzqzZzpzDTO;
import cn.gtmap.realestate.certificate.core.dto.ZzqzConfig;
import cn.gtmap.realestate.certificate.core.enums.BdcZzlxEnum;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EQlrxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsTimeStampDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseHead;
import cn.gtmap.realestate.common.core.enums.BdcGglxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ConfigurationException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 电子证照数据工具类
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 下午1:34 2021/1/8
 */
@Component
public class ECertificateUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ECertificateUtils.class.getName());

    /**
     * 审核登簿UI服务地址
     */
    @Value("${url.register-ui:}")
    protected String registerUiUrl;
    /**
     * 证书归档服务地址
     */
    @Value("${url.certificate:}")
    protected String certificateUrl;

    /**
     * 生成证书是否异步，
     */
    @Value("${eCertificate.sfyb: true}")
    private boolean sfyb;

    /**
     * 电子证照需要同步生成的流程定义KEY，其它流程异步，不配置则按照eCertificate.sfyb统一设置
     */
    @Value("${eCertificate.tbscgzl:}")
    private String tbscgzl;

    /**
     * 港澳台证件种类代码
     */
    @Value("${eCertificate.H-M-T-zjzldm:}")
    private Integer zjzldm_HMT;
    /**
     * 公钥
     */
    @Value("${eCertificate.publicKey:}")
    private String publicKey;

    /**
     * 证照签章的配置相关
     */
    @Autowired
    ZzqzConfig zzqzConfig;

    /**
     * 公钥集合
     */
    @Value("#{${eCertificate.dzzzpublicKeyMap: null}}")
    private Map<String, String> dzzzpublicKeyMap;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private PropsConfig propsConfig;
    @Autowired
    private BdcDypzFeignService bdcDypzFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private ECertificateParamUtils paramUtils;


    /**
     * 组织电子证照请求参数<br>
     * head.sign 数字签名 sm2 对 data 进行加密
     * data 请求参数
     *
     * @param data data 参数
     * @return java.util.LinkedHashMap
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    public LinkedHashMap getParamData(Object data , String qxdm) {
        //SM2加密
        String signStr="";
        if(MapUtils.isNotEmpty(dzzzpublicKeyMap) && StringUtils.isNotBlank(qxdm)) {
            signStr = SM2Util.encrypt(JSON.toJSONString(data), dzzzpublicKeyMap.get(qxdm));
        } else {
            signStr = SM2Util.encrypt(JSON.toJSONString(data), publicKey);
        }
        RequestHeadBO requestHeadBO = new RequestHeadBO();
        requestHeadBO.setSign(signStr);

        // 组织电子证照生成参数（需要有序）
        LinkedHashMap paramData = new LinkedHashMap();
        paramData.put(Constants.KEY_HEAD, requestHeadBO);
        paramData.put(Constants.KEY_DATA, data);
        return paramData;
    }

    /**
     * 常州电子签章 组织参数
     * <p>
     * 取值逻辑使用 generateEZsDTO(), 日期类型均转换为 13 位时间戳。
     * 其余部分字段取自 cz.zzqz 配置项
     * </p>
     *
     * @param bdcXmDO 项目对象
     * @param bdcZsDO 证书对象
     * @return cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsTimeStampDTO
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    public EZsTimeStampDTO generateEZsTimeStampDTO(BdcXmDO bdcXmDO, BdcZsDO bdcZsDO) {
        EZsDTO eZsDTO = this.generateEZsDTO(bdcZsDO, bdcXmDO, Constants.DZQZ);
        EZsTimeStampDTO eZsTimeStampDTO = new EZsTimeStampDTO();
        BeanUtils.copyProperties(eZsDTO, eZsTimeStampDTO);

        // 处理时间戳，所有时间均使用 13 位时间戳
        if (!Objects.isNull(eZsDTO.getFzrq())) {
            eZsTimeStampDTO.setFzrq(eZsDTO.getFzrq().getTime());
        }
        if (!Objects.isNull(eZsDTO.getFzrq())) {
            eZsTimeStampDTO.setZzbfrq(eZsDTO.getZzbfrq().getTime());
        }
        if (!Objects.isNull(eZsDTO.getZzyxqjzrq())) {
            eZsTimeStampDTO.setZzyxqjzrq(eZsDTO.getZzyxqjzrq().getTime());
        }
        if (!Objects.isNull(eZsDTO.getZzyxqqsrq())) {
            eZsTimeStampDTO.setZzyxqqsrq(eZsDTO.getZzyxqqsrq().getTime());
        }

        DzqzZzpzDTO zz = zzqzConfig.getZz();
        if (!Objects.isNull(zz)) {
            if (StringUtils.isNotBlank(zz.getZzbfjg())) {
                eZsTimeStampDTO.setZzbfjg(zz.getZzbfjg());
            }
            if (StringUtils.isNotBlank(zz.getZzbfjgdm())) {
                eZsTimeStampDTO.setZzbfjgdm(zz.getZzbfjgdm());
            } else {
                eZsDTO.setZzbfjgdm(bdcXmDO.getDjbmdm());
            }
        }
        return eZsTimeStampDTO;
    }


    /**
     * @param bdcZsDO,bdcXmDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织推送公告参数
     * @date : 2022/3/14 14:16
     */
    public EZsTimeStampDTO generateGgDTO(BdcXmDO bdcXmDO, BdcZsDO bdcZsDO, Integer gglx) {
        EZsDTO eZsDTO = this.generateGgxx(bdcZsDO, bdcXmDO, gglx);
        EZsTimeStampDTO eZsTimeStampDTO = new EZsTimeStampDTO();
        BeanUtils.copyProperties(eZsDTO, eZsTimeStampDTO);

        // 处理时间戳，所有时间均使用 13 位时间戳
        if (!Objects.isNull(eZsDTO.getFzrq())) {
            eZsTimeStampDTO.setFzrq(eZsDTO.getFzrq().getTime());
        }
        if (!Objects.isNull(eZsDTO.getFzrq())) {
            eZsTimeStampDTO.setZzbfrq(eZsDTO.getZzbfrq().getTime());
        }
        if (!Objects.isNull(eZsDTO.getZzyxqjzrq())) {
            eZsTimeStampDTO.setZzyxqjzrq(eZsDTO.getZzyxqjzrq().getTime());
        }
        if (!Objects.isNull(eZsDTO.getZzyxqqsrq())) {
            eZsTimeStampDTO.setZzyxqqsrq(eZsDTO.getZzyxqqsrq().getTime());
        }

        DzqzZzpzDTO zz = zzqzConfig.getZz();
        if (!Objects.isNull(zz)) {
            if (StringUtils.isNotBlank(zz.getZzbfjg())) {
                eZsTimeStampDTO.setZzbfjg(zz.getZzbfjg());
            }
            if (StringUtils.isNotBlank(zz.getZzbfjgdm())) {
                eZsTimeStampDTO.setZzbfjgdm(zz.getZzbfjgdm());
            } else {
                eZsDTO.setZzbfjgdm(bdcXmDO.getDjbmdm());
            }
        }
        return eZsTimeStampDTO;
    }

    /**
     * 组织增量电子证照（或常州电子签章）实体对象参数
     *
     * @param bdcZsDO 证书实体
     * @param bdcXmDO 项目实体
     * @param type 类型， DZZZ：电子证照， DZQZ 常州电子签章
     * @return cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsDTO
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    public EZsDTO generateEZsDTO(BdcZsDO bdcZsDO, BdcXmDO bdcXmDO, String type) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        EZsDTO eZsDTO = new EZsDTO();
        eZsDTO.setZzlxdm(BdcZzlxEnum.getZzlxEnum(bdcZsDO.getZslx()).getDm());
        eZsDTO.setZzbfjg(paramUtils.getZzbfjg(bdcXmDO));
        eZsDTO.setZzbfjgdm(paramUtils.getZzbfjgdm(bdcXmDO));
        this.setQlrxx(eZsDTO, bdcZsDO, bdcXmDO.getXmid(), type);
        this.setSyqx(eZsDTO, bdcZsDO, bdcXmDO);
        eZsDTO.setYwh(valueOf(bdcXmDO.getSlbh()));
        eZsDTO.setBdcqzh(valueOf(bdcZsDO.getBdcqzh()));
        eZsDTO.setQlqtzk(valueOf(bdcZsDO.getQlqtzk()));
        eZsDTO.setDwdm(paramUtils.getDwdm(bdcZsDO, bdcXmDO, type));
        eZsDTO.setSqsjc(valueOf(bdcZsDO.getSqsjc()));
        eZsDTO.setSzsxqc(valueOf(bdcZsDO.getSzsxqc()));
        // modified by lixin 2020/06/28 bug 25467 修改其他字段的取值为权利其他状况
        eZsDTO.setQt(valueOf(bdcZsDO.getQlqtzk()));
        eZsDTO.setFj(valueOf(bdcZsDO.getFj()));
        eZsDTO.setBdcdyh(valueOf(bdcZsDO.getBdcdyh()));
        eZsDTO.setZl(valueOf(bdcZsDO.getZl()));
        eZsDTO.setZmqlsx(valueOf(bdcZsDO.getZmqlsx()));
        eZsDTO.setGyqk(valueOf(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getGyfs(), zdMap.get(Constants.GYFS))));
        eZsDTO.setQllx(valueOf(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getQllx(), zdMap.get(Constants.QLLX))));

        eZsDTO.setSyqx(valueOf(bdcZsDO.getSyqx()));
        eZsDTO.setNf(valueOf(bdcZsDO.getNf()));
        this.setZhlsh(eZsDTO, bdcZsDO.getBdcqzh());
        eZsDTO.setYzh(valueOf(bdcZsDO.getQzysxlh()));
        eZsDTO.setZzbfrq(dateOf(bdcXmDO.getDjsj()));
        eZsDTO.setFzrq(dateOf(bdcXmDO.getDjsj()));

        if(StringUtils.isNotBlank(tbscgzl) && StringUtils.isNotBlank(bdcXmDO.getGzldyid())) {
            // 配置项涉及的流程同步生成电子证照，未涉及的流程默认异步
            Map<String, String> map = Arrays.stream(tbscgzl.split(CommonConstantUtils.ZF_YW_DH)).collect(Collectors.toMap(s -> s, s -> s));
            eZsDTO.setSfyb(!map.containsKey(bdcXmDO.getGzldyid()));
        } else {
            // 未按照流程来控制则全局统一配置
            eZsDTO.setSfyb(sfyb);
        }

        // 新版电子证照需要调整拼接符号的字段（权利人、义务人、用途、用途代码、面积、面积单位、面积单位代码、权利性质、权利性质代码）
        eZsDTO.setQlr(paramUtils.replaceSpace(bdcZsDO.getQlr(), type));
        eZsDTO.setYwr(paramUtils.replaceSpace(bdcZsDO.getYwr(), type));
        eZsDTO.setYt(paramUtils.replaceSpace(bdcZsDO.getYt(), type));
        eZsDTO.setMj(paramUtils.replaceSpace(bdcZsDO.getMj(), type));
        eZsDTO.setQlxz(paramUtils.replaceSpace(bdcZsDO.getQlxz(), type));

        // 新版-证书-电子证照 新增字段
        if(paramUtils.xbDzzzZs(bdcZsDO, type)) {
            // 权利类型代码
            String qllxdm = paramUtils.getDsfZdz(bdcXmDO.getXmid(), bdcZsDO.getZsid(), "BDC_QLLX", String.valueOf(bdcZsDO.getQllx()));
            eZsDTO.setQllxdm(qllxdm);
            // 权利性质代码，例如：出让/市场化商品房
            eZsDTO.setQlxzdm(paramUtils.getQlxzdm(bdcZsDO));
            // 用途代码，例如：城镇住宅用地/车库/车位
            eZsDTO.setYtdm(paramUtils.getYtdm(bdcZsDO));
            // 面积单位
            eZsDTO.setMjdw(paramUtils.getMjdwdmmc(bdcXmDO, type));
            // 面积单位代码
            eZsDTO.setMjdwdm(paramUtils.getMjdwdmdm(bdcXmDO, bdcZsDO.getZsid(), type));
        }

        // 新版-证明-电子证照 设置义务人、关联不动产权证号字段
        if(paramUtils.xbDzzzZm(bdcZsDO, type) && StringUtils.isNotBlank(bdcZsDO.getZsid())) {
            // 义务人信息
            eZsDTO.setYwrxx(paramUtils.getYwrxx(bdcZsDO));
            // 关联不动产权证号
            eZsDTO.setGlbdcqzh(paramUtils.getGlbdcqzh(bdcXmDO, bdcZsDO));
        }

        return eZsDTO;
    }

    /**
     * @param bdcXmDO,bdcZsDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织公告DTO
     * @date : 2022/3/14 14:18
     */
    public EZsDTO generateGgxx(BdcZsDO bdcZsDO, BdcXmDO bdcXmDO, Integer gglx) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        EZsDTO eZsDTO = new EZsDTO();
        eZsDTO.setZzlxdm(BdcGglxEnum.getGglxEnum(gglx).getDm());
        eZsDTO.setZzbfjg(paramUtils.getZzbfjg(bdcXmDO));
        eZsDTO.setZzbfjgdm(paramUtils.getZzbfjgdm(bdcXmDO));
        this.setQlrxx(eZsDTO, bdcZsDO, bdcXmDO.getXmid(), null);
        this.setSyqx(eZsDTO, bdcZsDO, bdcXmDO);
        eZsDTO.setYwh(valueOf(bdcXmDO.getSlbh()));
        eZsDTO.setBdcqzh(valueOf(bdcZsDO.getBdcqzh()));
        eZsDTO.setQlqtzk(valueOf(bdcZsDO.getQlqtzk()));
        eZsDTO.setDwdm(paramUtils.getDwdm(bdcZsDO, bdcXmDO, Constants.DZQZ));
        eZsDTO.setSqsjc(valueOf(bdcZsDO.getSqsjc()));
        eZsDTO.setSzsxqc(valueOf(bdcZsDO.getSzsxqc()));
        // modified by lixin 2020/06/28 bug 25467 修改其他字段的取值为权利其他状况
        eZsDTO.setQt(valueOf(bdcZsDO.getQlqtzk()));
        eZsDTO.setFj(valueOf(bdcZsDO.getFj()));
        eZsDTO.setBdcdyh(valueOf(bdcXmDO.getBdcdyh()));
        eZsDTO.setZl(valueOf(bdcXmDO.getZl()));
        eZsDTO.setZmqlsx(valueOf(bdcZsDO.getZmqlsx()));
        eZsDTO.setGyqk(valueOf(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get(Constants.GYFS))));
        eZsDTO.setQllx(valueOf(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get(Constants.QLLX))));
        eZsDTO.setQlxz(valueOf(bdcZsDO.getQlxz()));
        eZsDTO.setYt(valueOf(bdcZsDO.getYt()));
        eZsDTO.setMj(valueOf(bdcZsDO.getMj()));
        eZsDTO.setSyqx(valueOf(bdcZsDO.getSyqx()));
        eZsDTO.setNf(valueOf(bdcZsDO.getNf()));
        this.setZhlsh(eZsDTO, bdcZsDO.getBdcqzh());
        eZsDTO.setYzh(valueOf(bdcZsDO.getQzysxlh()));
        eZsDTO.setZzbfrq(dateOf(bdcXmDO.getDjsj()));
        eZsDTO.setFzrq(dateOf(bdcXmDO.getDjsj()));
        eZsDTO.setQlr(valueOf(bdcZsDO.getQlr()));
        eZsDTO.setYwr(valueOf(bdcZsDO.getYwr()));
        eZsDTO.setSfyb(sfyb);
        return eZsDTO;
    }

    /**
     * @param bdcZsDO 证书信息 -- 产权证号为固定值
     * @param bdcXmDO 项目信息 -- 必传登记机构，登记部门代码，区县代码，受理编号
     *                有单元号传单元号
     * @param qzlx    签章类型
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description set 签章必要信息
     * @date : 2022/8/24 13:58
     */
    public EZsDTO setDzqzxx(BdcZsDO bdcZsDO, BdcXmDO bdcXmDO, String qzlx) {
        EZsDTO eZsDTO = new EZsDTO();
        eZsDTO.setZzlxdm(qzlx);
        eZsDTO.setZzbfjg(paramUtils.getZzbfjg(bdcXmDO));
        eZsDTO.setZzbfjgdm(paramUtils.getZzbfjgdm(bdcXmDO));
        eZsDTO.setYwh(valueOf(bdcXmDO.getSlbh()));
        eZsDTO.setBdcqzh(valueOf(bdcZsDO.getBdcqzh()));
        eZsDTO.setDwdm(paramUtils.getDwdm(bdcZsDO, bdcXmDO, Constants.DZQZ));
        eZsDTO.setBdcdyh(valueOf(bdcXmDO.getBdcdyh()));
        eZsDTO.setZl(valueOf(bdcXmDO.getZl()));
        DzqzZzpzDTO zz = zzqzConfig.getZz();
        if (Objects.nonNull(zz)) {
            if (StringUtils.isNotBlank(zz.getZzbfjg())) {
                eZsDTO.setZzbfjg(zz.getZzbfjg());
            }
            if (StringUtils.isNotBlank(zz.getZzbfjgdm())) {
                eZsDTO.setZzbfjgdm(zz.getZzbfjgdm());
            } else {
                eZsDTO.setZzbfjgdm(bdcXmDO.getDjbmdm());
            }
        }
        return eZsDTO;
    }

    public static Date dateOf(Date date) {
        return (date == null) ? new Date() : date;
    }

    /**
     * 设置持证主体信息
     *
     * @param bdcZsDO 证书
     * @param type 类型， DZZZ：电子证照， DZQZ 常州电子签章
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    private void setQlrxx(EZsDTO eZsDTO, BdcZsDO bdcZsDO, String xmid, String type) {
        List<BdcQlrDO> bdcQlrDOList = Lists.newArrayList();
        if (StringUtils.isBlank(bdcZsDO.getZsid()) && StringUtils.isBlank(xmid)) {
            LOGGER.error("生成电子证照{}查询权利人信息未传值zsid或xmid", type);
            return;
        }
        if (StringUtils.isNotBlank(bdcZsDO.getZsid())) {
            Example example = new Example(BdcQlrDO.class);
            example.createCriteria().andEqualTo(Constants.ZSID, bdcZsDO.getZsid()).andEqualTo("qlrlb", CommonConstantUtils.QLRLB_QLR);
            bdcQlrDOList = entityMapper.selectByExampleNotNull(example);

        }
        if (CollectionUtils.isEmpty(bdcQlrDOList) && StringUtils.isNotBlank(xmid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        }
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            LOGGER.error("生成电子证照{}未查询到权利人信息，zsid：{}；xmid：{}", type, bdcZsDO.getZsid(), xmid);
            return;
        }

        // 对于权利人根据 qlrmc 和 zjh 去重
        List<BdcQlrDO> bdcQlrDOS = bdcQlrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc() + ";" + o.getZjh()))), ArrayList::new));

        StringBuilder czzt = new StringBuilder();
        StringBuilder czztdm = new StringBuilder();
        StringBuilder czztdmlx = new StringBuilder();
        StringBuilder czztdmlxdm = new StringBuilder();
        List<EQlrxxDTO> eQlrxxDTOList = new ArrayList();

        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
            if (bdcQlrDO != null && StringUtils.isNotBlank(bdcQlrDO.getQlrmc())) {
                if (StringUtils.isNotBlank(czzt)) {
                    czzt.append(CommonConstantUtils.ZF_GZH);
                }
                if (StringUtils.isNotBlank(czztdm)) {
                    czztdm.append(CommonConstantUtils.ZF_GZH);
                }
                if (StringUtils.isNotBlank(czztdmlx)) {
                    czztdmlx.append(CommonConstantUtils.ZF_GZH);
                }
                if (StringUtils.isNotBlank(czztdmlxdm)) {
                    czztdmlxdm.append(CommonConstantUtils.ZF_GZH);
                }

                EQlrxxDTO qlrxx = new EQlrxxDTO();
                qlrxx.setCzzt(bdcQlrDO.getQlrmc());
                czzt.append(bdcQlrDO.getQlrmc());
                if (StringUtils.isNoneBlank(bdcQlrDO.getZjh())) {
                    qlrxx.setCzztdm(bdcQlrDO.getZjh());
                    czztdm.append(bdcQlrDO.getZjh());
                }

                // 新版-证书-电子证照-共有方式为按份共有，添加权利人共有比例
                if(paramUtils.sftjQlbl(bdcZsDO, type, bdcQlrDO)) {
                    qlrxx.setCzztgybl(bdcQlrDO.getQlbl());
                }

                if (null != bdcQlrDO.getZjzl()) {
                    List<String> ztZjdmList;
                    // 港澳台证件种类特殊处理
                    if (null != zjzldm_HMT && zjzldm_HMT.equals(bdcQlrDO.getZjzl())) {
                        ztZjdmList = paramUtils.getHMTZzztDmXx(bdcQlrDO);
                    } else {
                        // 其它类型取配置信息
                        ztZjdmList = propsConfig.getZzztDmXx(bdcQlrDO.getZjzl());
                    }
                    if (CollectionUtils.isNotEmpty(ztZjdmList) && CollectionUtils.size(ztZjdmList) >= 2) {
                        String ztdmlxDm = ztZjdmList.get(0);
                        String ztdmlx = ztZjdmList.get(1);

                        qlrxx.setCzztdmlx(ztdmlx);
                        qlrxx.setCzztdmlxdm(ztdmlxDm);

                        czztdmlx.append(ztdmlx);
                        czztdmlxdm.append(ztdmlxDm);
                    }
                }
                eQlrxxDTOList.add(qlrxx);
            }
        }
        if (CollectionUtils.isNotEmpty(eQlrxxDTOList)) {
            eZsDTO.setQlrxx(eQlrxxDTOList);
        }
        if (StringUtils.isNotBlank(czzt)) {
            eZsDTO.setCzzt(czzt.toString());
        }
        if (StringUtils.isNotBlank(czztdm)) {
            eZsDTO.setCzztdm(czztdm.toString());
        }
        if (StringUtils.isNotBlank(czztdmlx)) {
            eZsDTO.setCzztdmlx(czztdmlx.toString());
        }
        if (StringUtils.isNotBlank(czztdmlxdm)) {
            eZsDTO.setCzztdmlxdm(czztdmlxdm.toString());
        }
    }

    /**
     * 获取证书流水号
     *
     * @param eZsDTO 电子证照对象
     * @param bdcqzh 不动产权证号
     * @return String 证流水号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    private void setZhlsh(EZsDTO eZsDTO, String bdcqzh) {
        if (StringUtils.isNotBlank(bdcqzh)) {
            Integer start = StringUtils.indexOf(bdcqzh, CommonConstantUtils.DI);
            Integer end = StringUtils.indexOf(bdcqzh, CommonConstantUtils.HAO);
            eZsDTO.setZhlsh(StringUtils.substring(bdcqzh, start + 1, end));
        }
    }

    /**
     * 生成证书的使用期限
     *
     * @param eZsDTO 电子证照对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    private void setSyqx(EZsDTO eZsDTO, BdcZsDO bdcZsDO, BdcXmDO bdcXmDO) {
        try {
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
            if (null == bdcQl) {
                eZsDTO.setZzyxqqsrq(bdcXmDO.getDjsj());
                eZsDTO.setZzyxqjzrq(DateUtils.parseDate("2099-12-31", "yyyy-MM-dd"));
                return;
            }

            if (bdcQl instanceof BdcJsydsyqDO) {
                eZsDTO.setZzyxqqsrq(((BdcJsydsyqDO) bdcQl).getSyqqssj());
                eZsDTO.setZzyxqjzrq(((BdcJsydsyqDO) bdcQl).getSyqjssj());
            }
            else if(bdcQl instanceof BdcFdcqDO) {
                eZsDTO.setZzyxqqsrq(((BdcFdcqDO) bdcQl).getTdsyqssj());
                eZsDTO.setZzyxqjzrq(((BdcFdcqDO) bdcQl).getTdsyjssj());
            }
            else if(bdcQl instanceof BdcGjzwsyqDO) {
                eZsDTO.setZzyxqqsrq(((BdcGjzwsyqDO) bdcQl).getTdhysyqssj());
                eZsDTO.setZzyxqjzrq(((BdcGjzwsyqDO) bdcQl).getTdhysyjssj());
            }
            else if(bdcQl instanceof BdcHysyqDO) {
                eZsDTO.setZzyxqqsrq(((BdcHysyqDO) bdcQl).getSyqqssj());
                eZsDTO.setZzyxqjzrq(((BdcHysyqDO) bdcQl).getSyqjssj());
            }
            else if(bdcQl instanceof BdcTdcbnydsyqDO) {
                eZsDTO.setZzyxqqsrq(((BdcTdcbnydsyqDO) bdcQl).getCbqssj());
                eZsDTO.setZzyxqjzrq(((BdcTdcbnydsyqDO) bdcQl).getCbjssj());
            }
            else if(bdcQl instanceof BdcNydjyqDO) {
                eZsDTO.setZzyxqqsrq(((BdcNydjyqDO) bdcQl).getCbqssj());
                eZsDTO.setZzyxqjzrq(((BdcNydjyqDO) bdcQl).getCbjssj());
            }
            else if(bdcQl instanceof BdcQtxgqlDO) {
                eZsDTO.setZzyxqqsrq(((BdcQtxgqlDO) bdcQl).getQlqssj());
                eZsDTO.setZzyxqjzrq(((BdcQtxgqlDO) bdcQl).getQljssj());
            }
            else if(bdcQl instanceof BdcLqDO) {
                eZsDTO.setZzyxqqsrq(((BdcLqDO) bdcQl).getLdsyqssj());
                eZsDTO.setZzyxqjzrq(((BdcLqDO) bdcQl).getLdsyjssj());
            }
            else if(bdcQl instanceof BdcDyiqDO) {
                eZsDTO.setZzyxqqsrq(((BdcDyiqDO) bdcQl).getQlqssj());
                eZsDTO.setZzyxqjzrq(((BdcDyiqDO) bdcQl).getQljssj());
            }
            else if(bdcQl instanceof BdcDyaqDO) {
                eZsDTO.setZzyxqqsrq(((BdcDyaqDO) bdcQl).getZwlxqssj());
                eZsDTO.setZzyxqjzrq(((BdcDyaqDO) bdcQl).getZwlxjssj());
            }
            else if(bdcQl instanceof BdcJzqDO) {
                eZsDTO.setZzyxqqsrq(((BdcJzqDO) bdcQl).getJzqkssj());
                eZsDTO.setZzyxqjzrq(((BdcJzqDO) bdcQl).getJzqjssj());
            }
            else if(bdcQl instanceof BdcYgDO) {
                eZsDTO.setZzyxqqsrq(((BdcYgDO) bdcQl).getZwlxqssj());
                eZsDTO.setZzyxqjzrq(((BdcYgDO) bdcQl).getZwlxjssj());
            }
            else if(bdcQl instanceof BdcYyDO) {
                eZsDTO.setZzyxqqsrq(((BdcYyDO) bdcQl).getYydjksrq());
                eZsDTO.setZzyxqjzrq(((BdcYyDO) bdcQl).getYydjjsrq());
            }
            else if(bdcQl instanceof BdcFwzlDO) {
                eZsDTO.setZzyxqqsrq(((BdcFwzlDO) bdcQl).getZlqssj());
                eZsDTO.setZzyxqjzrq(((BdcFwzlDO) bdcQl).getZljssj());
            }

            // 如果还为空直接设置默认值
            if(null == eZsDTO.getZzyxqqsrq()) {
                eZsDTO.setZzyxqqsrq(new Date());
            }
            if(null == eZsDTO.getZzyxqjzrq()) {
                eZsDTO.setZzyxqjzrq(DateUtils.parseDate("2099-12-31", "yyyy-MM-dd"));
            }
        } catch (Exception e) {
            // 有效期非必填字段，这里即使出现异常照常提交申请
            LOGGER.error("电子证照处理有效期异常{}", e.getMessage());
        }
    }

    /**
     * 从打印配置中 获取 pdf 模板地址
     * <ol>
     *  <li>获取打印配置中的 sjly</li>
     *  <li>组织 dylx 赋值到 bdcPrintDTO</li>
     * </ol>
     *
     * @param bdcZsDO     证书 DO
     * @param bdcPrintDTO 打印 DTO
     * @return {String} pdf 路径
     */
    private String getPdfPath(BdcZsDO bdcZsDO, BdcPrintDTO bdcPrintDTO) {
        String pdfPath;
        String dylx = "";

        if (CommonConstantUtils.ZSLX_ZS.equals(bdcZsDO.getZslx()) || CommonConstantUtils.ZSLX_ZMD.equals(bdcZsDO.getZslx())) {
            dylx = CommonConstantUtils.DYLX_ZS + CommonConstantUtils.DYLX_ZZQZ_SUFFIX;
        } else if (CommonConstantUtils.ZSLX_ZM.equals(bdcZsDO.getZslx())) {
            dylx = CommonConstantUtils.DYLX_ZM + CommonConstantUtils.DYLX_ZZQZ_SUFFIX;
        }
        if (StringUtils.isBlank(dylx)) {
            throw new AppException("未获取到对应的 dylx");
        }
        bdcPrintDTO.setDylx(dylx);

        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOS = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOS)) {
            throw new ConfigurationException("dylx: " + dylx + "未配置对应的打印数据");
        }

        pdfPath = bdcDysjPzDOS.get(0).getPdfpath();
        if (StringUtils.isBlank(pdfPath)) {
            throw new ConfigurationException("打印类型" + dylx + " 未配置对应 pdf 模板");
        }
        return pdfPath;
    }

    public ZzqzConfig getZzqzConfig() {
        return zzqzConfig;
    }

    /**
     * 处理电子证照的返回值
     * <p>
     * 返回值已经做判空处理
     * </p>
     *
     * @param response 电子证照响应结果
     * @param type     执行类型
     * @return cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    public DzzzResponseData dealDzzzResponse(Object response, String type) {
        if (null == response) {
            throw new AppException(type + ", 返回值为空");
        }

        LOGGER.info("{} 接口返回值：{}", type, JSON.toJSONString(response));
        DzzzResponseDTO dzzzResponseDTO = JSON.parseObject(JSON.toJSONString(response), DzzzResponseDTO.class);

        DzzzResponseHead head = dzzzResponseDTO.getHead();
        DzzzResponseData data = dzzzResponseDTO.getData();
        if (null == data || null == head) {
            LOGGER.error("{} 异常, 返回值 data 或 head 为空", type);
            return errorMsg(type + "返回值为空");
        }
        if (!StringUtils.equals("0", head.getStatus())) {
            LOGGER.error("{} :返回状态为:{} ;异常信息：{}", type, head.getStatus(), head.getMessage());
            return errorMsg(String.format("%s，返回状态：%s，错误信息为：%s", type, head.getStatus(), head.getMessage()));
        }
        return data;
    }

    /**
     * 处理电子证照的返回值多线程用
     * <p>
     * 返回值已经做判空处理
     * </p>
     *
     * @param response 电子证照响应结果
     * @param type     执行类型
     * @return cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    public DzzzResponseData dealDzzzResponsePrint(Object response, String type, String id) {
        if (null == response) {
            throw new AppException(type + ", 返回值为空");
        }

        DzzzResponseDTO dzzzResponseDTO = JSON.parseObject(JSON.toJSONString(response), DzzzResponseDTO.class);

        DzzzResponseHead head = dzzzResponseDTO.getHead();
        DzzzResponseData data = dzzzResponseDTO.getData();
        if (null == data || null == head) {
            return errorMsg(type + "返回值为空");
        }
        if (!StringUtils.equals("0", head.getStatus())) {
            return errorMsg(type + "返回状态为" + head.getStatus() + "; Msg:" + head.getMessage());
        }
        return data;
    }

    private DzzzResponseData errorMsg(String msg) {
        DzzzResponseData responseData = new DzzzResponseData();
        responseData.setStatus(false);
        responseData.setInfo(msg);
        return responseData;
    }

    private String valueOf(Object obj) {
        return (obj == null) ? CommonConstantUtils.ZF_YW_XG : obj.toString();
    }
}
