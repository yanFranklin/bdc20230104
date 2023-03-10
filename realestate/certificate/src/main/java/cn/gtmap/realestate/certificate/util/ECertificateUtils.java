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
 * ???????????????????????????
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version ??????1:34 2021/1/8
 */
@Component
public class ECertificateUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ECertificateUtils.class.getName());

    /**
     * ????????????UI????????????
     */
    @Value("${url.register-ui:}")
    protected String registerUiUrl;
    /**
     * ????????????????????????
     */
    @Value("${url.certificate:}")
    protected String certificateUrl;

    /**
     * ???????????????????????????
     */
    @Value("${eCertificate.sfyb: true}")
    private boolean sfyb;

    /**
     * ?????????????????????????????????????????????KEY??????????????????????????????????????????eCertificate.sfyb????????????
     */
    @Value("${eCertificate.tbscgzl:}")
    private String tbscgzl;

    /**
     * ???????????????????????????
     */
    @Value("${eCertificate.H-M-T-zjzldm:}")
    private Integer zjzldm_HMT;
    /**
     * ??????
     */
    @Value("${eCertificate.publicKey:}")
    private String publicKey;

    /**
     * ???????????????????????????
     */
    @Autowired
    ZzqzConfig zzqzConfig;

    /**
     * ????????????
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
     * ??????????????????????????????<br>
     * head.sign ???????????? sm2 ??? data ????????????
     * data ????????????
     *
     * @param data data ??????
     * @return java.util.LinkedHashMap
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    public LinkedHashMap getParamData(Object data , String qxdm) {
        //SM2??????
        String signStr="";
        if(MapUtils.isNotEmpty(dzzzpublicKeyMap) && StringUtils.isNotBlank(qxdm)) {
            signStr = SM2Util.encrypt(JSON.toJSONString(data), dzzzpublicKeyMap.get(qxdm));
        } else {
            signStr = SM2Util.encrypt(JSON.toJSONString(data), publicKey);
        }
        RequestHeadBO requestHeadBO = new RequestHeadBO();
        requestHeadBO.setSign(signStr);

        // ????????????????????????????????????????????????
        LinkedHashMap paramData = new LinkedHashMap();
        paramData.put(Constants.KEY_HEAD, requestHeadBO);
        paramData.put(Constants.KEY_DATA, data);
        return paramData;
    }

    /**
     * ?????????????????? ????????????
     * <p>
     * ?????????????????? generateEZsDTO(), ???????????????????????? 13 ???????????????
     * ???????????????????????? cz.zzqz ?????????
     * </p>
     *
     * @param bdcXmDO ????????????
     * @param bdcZsDO ????????????
     * @return cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsTimeStampDTO
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    public EZsTimeStampDTO generateEZsTimeStampDTO(BdcXmDO bdcXmDO, BdcZsDO bdcZsDO) {
        EZsDTO eZsDTO = this.generateEZsDTO(bdcZsDO, bdcXmDO, Constants.DZQZ);
        EZsTimeStampDTO eZsTimeStampDTO = new EZsTimeStampDTO();
        BeanUtils.copyProperties(eZsDTO, eZsTimeStampDTO);

        // ??????????????????????????????????????? 13 ????????????
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
     * @description ????????????????????????
     * @date : 2022/3/14 14:16
     */
    public EZsTimeStampDTO generateGgDTO(BdcXmDO bdcXmDO, BdcZsDO bdcZsDO, Integer gglx) {
        EZsDTO eZsDTO = this.generateGgxx(bdcZsDO, bdcXmDO, gglx);
        EZsTimeStampDTO eZsTimeStampDTO = new EZsTimeStampDTO();
        BeanUtils.copyProperties(eZsDTO, eZsTimeStampDTO);

        // ??????????????????????????????????????? 13 ????????????
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
     * ?????????????????????????????????????????????????????????????????????
     *
     * @param bdcZsDO ????????????
     * @param bdcXmDO ????????????
     * @param type ????????? DZZZ?????????????????? DZQZ ??????????????????
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
        // modified by lixin 2020/06/28 bug 25467 ????????????????????????????????????????????????
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
            // ?????????????????????????????????????????????????????????????????????????????????
            Map<String, String> map = Arrays.stream(tbscgzl.split(CommonConstantUtils.ZF_YW_DH)).collect(Collectors.toMap(s -> s, s -> s));
            eZsDTO.setSfyb(!map.containsKey(bdcXmDO.getGzldyid()));
        } else {
            // ?????????????????????????????????????????????
            eZsDTO.setSfyb(sfyb);
        }

        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        eZsDTO.setQlr(paramUtils.replaceSpace(bdcZsDO.getQlr(), type));
        eZsDTO.setYwr(paramUtils.replaceSpace(bdcZsDO.getYwr(), type));
        eZsDTO.setYt(paramUtils.replaceSpace(bdcZsDO.getYt(), type));
        eZsDTO.setMj(paramUtils.replaceSpace(bdcZsDO.getMj(), type));
        eZsDTO.setQlxz(paramUtils.replaceSpace(bdcZsDO.getQlxz(), type));

        // ??????-??????-???????????? ????????????
        if(paramUtils.xbDzzzZs(bdcZsDO, type)) {
            // ??????????????????
            String qllxdm = paramUtils.getDsfZdz(bdcXmDO.getXmid(), bdcZsDO.getZsid(), "BDC_QLLX", String.valueOf(bdcZsDO.getQllx()));
            eZsDTO.setQllxdm(qllxdm);
            // ????????????????????????????????????/??????????????????
            eZsDTO.setQlxzdm(paramUtils.getQlxzdm(bdcZsDO));
            // ??????????????????????????????????????????/??????/??????
            eZsDTO.setYtdm(paramUtils.getYtdm(bdcZsDO));
            // ????????????
            eZsDTO.setMjdw(paramUtils.getMjdwdmmc(bdcXmDO, type));
            // ??????????????????
            eZsDTO.setMjdwdm(paramUtils.getMjdwdmdm(bdcXmDO, bdcZsDO.getZsid(), type));
        }

        // ??????-??????-???????????? ????????????????????????????????????????????????
        if(paramUtils.xbDzzzZm(bdcZsDO, type) && StringUtils.isNotBlank(bdcZsDO.getZsid())) {
            // ???????????????
            eZsDTO.setYwrxx(paramUtils.getYwrxx(bdcZsDO));
            // ????????????????????????
            eZsDTO.setGlbdcqzh(paramUtils.getGlbdcqzh(bdcXmDO, bdcZsDO));
        }

        return eZsDTO;
    }

    /**
     * @param bdcXmDO,bdcZsDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????DTO
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
        // modified by lixin 2020/06/28 bug 25467 ????????????????????????????????????????????????
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
     * @param bdcZsDO ???????????? -- ????????????????????????
     * @param bdcXmDO ???????????? -- ?????????????????????????????????????????????????????????????????????
     *                ????????????????????????
     * @param qzlx    ????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description set ??????????????????
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
     * ????????????????????????
     *
     * @param bdcZsDO ??????
     * @param type ????????? DZZZ?????????????????? DZQZ ??????????????????
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    private void setQlrxx(EZsDTO eZsDTO, BdcZsDO bdcZsDO, String xmid, String type) {
        List<BdcQlrDO> bdcQlrDOList = Lists.newArrayList();
        if (StringUtils.isBlank(bdcZsDO.getZsid()) && StringUtils.isBlank(xmid)) {
            LOGGER.error("??????????????????{}??????????????????????????????zsid???xmid", type);
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
            LOGGER.error("??????????????????{}??????????????????????????????zsid???{}???xmid???{}", type, bdcZsDO.getZsid(), xmid);
            return;
        }

        // ????????????????????? qlrmc ??? zjh ??????
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

                // ??????-??????-????????????-?????????????????????????????????????????????????????????
                if(paramUtils.sftjQlbl(bdcZsDO, type, bdcQlrDO)) {
                    qlrxx.setCzztgybl(bdcQlrDO.getQlbl());
                }

                if (null != bdcQlrDO.getZjzl()) {
                    List<String> ztZjdmList;
                    // ?????????????????????????????????
                    if (null != zjzldm_HMT && zjzldm_HMT.equals(bdcQlrDO.getZjzl())) {
                        ztZjdmList = paramUtils.getHMTZzztDmXx(bdcQlrDO);
                    } else {
                        // ???????????????????????????
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
     * ?????????????????????
     *
     * @param eZsDTO ??????????????????
     * @param bdcqzh ??????????????????
     * @return String ????????????
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
     * ???????????????????????????
     *
     * @param eZsDTO ??????????????????
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

            // ????????????????????????????????????
            if(null == eZsDTO.getZzyxqqsrq()) {
                eZsDTO.setZzyxqqsrq(new Date());
            }
            if(null == eZsDTO.getZzyxqjzrq()) {
                eZsDTO.setZzyxqjzrq(DateUtils.parseDate("2099-12-31", "yyyy-MM-dd"));
            }
        } catch (Exception e) {
            // ?????????????????????????????????????????????????????????????????????
            LOGGER.error("?????????????????????????????????{}", e.getMessage());
        }
    }

    /**
     * ?????????????????? ?????? pdf ????????????
     * <ol>
     *  <li>???????????????????????? sjly</li>
     *  <li>?????? dylx ????????? bdcPrintDTO</li>
     * </ol>
     *
     * @param bdcZsDO     ?????? DO
     * @param bdcPrintDTO ?????? DTO
     * @return {String} pdf ??????
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
            throw new AppException("????????????????????? dylx");
        }
        bdcPrintDTO.setDylx(dylx);

        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOS = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOS)) {
            throw new ConfigurationException("dylx: " + dylx + "??????????????????????????????");
        }

        pdfPath = bdcDysjPzDOS.get(0).getPdfpath();
        if (StringUtils.isBlank(pdfPath)) {
            throw new ConfigurationException("????????????" + dylx + " ??????????????? pdf ??????");
        }
        return pdfPath;
    }

    public ZzqzConfig getZzqzConfig() {
        return zzqzConfig;
    }

    /**
     * ??????????????????????????????
     * <p>
     * ??????????????????????????????
     * </p>
     *
     * @param response ????????????????????????
     * @param type     ????????????
     * @return cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    public DzzzResponseData dealDzzzResponse(Object response, String type) {
        if (null == response) {
            throw new AppException(type + ", ???????????????");
        }

        LOGGER.info("{} ??????????????????{}", type, JSON.toJSONString(response));
        DzzzResponseDTO dzzzResponseDTO = JSON.parseObject(JSON.toJSONString(response), DzzzResponseDTO.class);

        DzzzResponseHead head = dzzzResponseDTO.getHead();
        DzzzResponseData data = dzzzResponseDTO.getData();
        if (null == data || null == head) {
            LOGGER.error("{} ??????, ????????? data ??? head ??????", type);
            return errorMsg(type + "???????????????");
        }
        if (!StringUtils.equals("0", head.getStatus())) {
            LOGGER.error("{} :???????????????:{} ;???????????????{}", type, head.getStatus(), head.getMessage());
            return errorMsg(String.format("%s??????????????????%s?????????????????????%s", type, head.getStatus(), head.getMessage()));
        }
        return data;
    }

    /**
     * ??????????????????????????????????????????
     * <p>
     * ??????????????????????????????
     * </p>
     *
     * @param response ????????????????????????
     * @param type     ????????????
     * @return cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    public DzzzResponseData dealDzzzResponsePrint(Object response, String type, String id) {
        if (null == response) {
            throw new AppException(type + ", ???????????????");
        }

        DzzzResponseDTO dzzzResponseDTO = JSON.parseObject(JSON.toJSONString(response), DzzzResponseDTO.class);

        DzzzResponseHead head = dzzzResponseDTO.getHead();
        DzzzResponseData data = dzzzResponseDTO.getData();
        if (null == data || null == head) {
            return errorMsg(type + "???????????????");
        }
        if (!StringUtils.equals("0", head.getStatus())) {
            return errorMsg(type + "???????????????" + head.getStatus() + "; Msg:" + head.getMessage());
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
